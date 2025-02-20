package de.ilyes.server;

import de.ilyes.grpc.product.proto.ProductRequest;
import de.ilyes.grpc.product.proto.ProductResponse;
import de.ilyes.mapper.ProductMapper;
import de.ilyes.repository.ProductRepository;
import de.ilyes.repository.entity.ProductEntity;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GrpcProductServiceTest {

    private ProductRepository productRepository;

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private GrpcProductService productService;

    @BeforeEach
    public void setup() {
        this.productRepository = mock(ProductRepository.class);

        this.productService = new GrpcProductService(this.productRepository, this.productMapper);
    }

    @Test
    void create() throws Exception {
        when(this.productRepository.save(any())).thenReturn(mockProductEntity());
        ProductRequest request = mockProductRequest();
        ProductResponse expectedResponse = mockProductResponse();

        StreamRecorder<ProductResponse> responseObserver = StreamRecorder.create();
        productService.create(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            Assert.fail("The call did not terminate in time");
        }
        Assertions.assertNull(responseObserver.getError());
        List<ProductResponse> results = responseObserver.getValues();
        Assertions.assertEquals(1, results.size());
        ProductResponse response = results.get(0);
        Assertions.assertEquals(expectedResponse, response);
    }

    private ProductEntity mockProductEntity() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(6);
        productEntity.setName("test name");
        productEntity.setPrice(50);
        return productEntity;
    }

    private ProductRequest mockProductRequest() {
        return ProductRequest.newBuilder().setPrice(50).setName("test name").setId(6).build();
    }

    private ProductResponse mockProductResponse() {
        return ProductResponse.newBuilder().setPrice(50).setName("test name").setId(6).build();
    }
}
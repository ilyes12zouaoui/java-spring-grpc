package de.ilyes.server;

import de.ilyes.grpc.product.proto.ProductRequest;
import de.ilyes.grpc.product.proto.ProductResponse;
import de.ilyes.grpc.product.proto.ProductServiceGrpc;
import de.ilyes.repository.ProductRepository;
import de.ilyes.repository.entity.ProductEntity;
import de.ilyes.server.config.ProductServiceIntegrationTestConfiguration;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "grpc.server.inProcessName=test",
        "grpc.server.port=-1",
        "grpc.client.inProcess.address=in-process:test"
})
@SpringJUnitConfig(classes = {ProductServiceIntegrationTestConfiguration.class})
@DirtiesContext
class GrpcProductServiceIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @GrpcClient("inProcess")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;


    @Test
    @DirtiesContext
    void create() {
        when(this.productRepository.save(any())).thenReturn(mockProductEntity());
        ProductRequest request = mockProductRequest();
        ProductResponse expectedResponse = mockProductResponse();

        ProductResponse response = productServiceBlockingStub.create(request);

        Assertions.assertNotNull(response);
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
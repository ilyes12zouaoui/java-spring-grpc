package de.ilyes.server;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import de.ilyes.grpc.product.proto.ProductListResponse;
import de.ilyes.grpc.product.proto.ProductRequest;
import de.ilyes.grpc.product.proto.ProductResponse;
import de.ilyes.grpc.product.proto.ProductServiceGrpc;
import de.ilyes.interceptor.ContextKeysMetadata;
import de.ilyes.interceptor.GrpcExceptionAdvice;
import de.ilyes.mapper.ProductMapper;
import de.ilyes.repository.ProductRepository;
import de.ilyes.repository.entity.ProductEntity;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class GrpcProductService extends ProductServiceGrpc.ProductServiceImplBase {
    Logger logger = LoggerFactory.getLogger(GrpcExceptionAdvice.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GrpcProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public void find(Empty request, StreamObserver<ProductListResponse> responseObserver) {
        logger.info("INFO ----------- RECEIVED GRPC CALL WITH CORRELATION-ID {}", ContextKeysMetadata.CORRELATION_ID_CONTEXT.get());
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductResponse> productResponses = productEntities.stream().map(productMapper::mapToProductResponse).toList();
        ProductListResponse productListResponse = ProductListResponse.newBuilder().addAllResultList(productResponses).setResultCount(Int64Value.of(productResponses.size())).build();
        responseObserver.onNext(productListResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void create(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("price is lower than 0!");
        }
        responseObserver.onNext(productMapper.mapToProductResponse(productRepository.save(productMapper.mapToProductEntity(request))));
        responseObserver.onCompleted();
    }

    @Override
    public void delete(Int64Value request, StreamObserver<Empty> responseObserver) {
        productRepository.deleteById(request.getValue());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void update(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        responseObserver.onNext(productMapper.mapToProductResponse(productRepository.save(productMapper.mapToProductEntity(request))));
        responseObserver.onCompleted();
    }
}

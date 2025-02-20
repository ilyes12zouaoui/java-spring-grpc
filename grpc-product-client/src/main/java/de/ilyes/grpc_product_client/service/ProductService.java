package de.ilyes.grpc_product_client.service;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import de.ilyes.grpc.product.proto.ProductListResponse;
import de.ilyes.grpc.product.proto.ProductRequest;
import de.ilyes.grpc.product.proto.ProductResponse;
import de.ilyes.grpc.product.proto.ProductServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @GrpcClient("product-server")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    public ProductListResponse find() {
        return productServiceBlockingStub.find(Empty.newBuilder().build());
    }

    public ProductResponse create(ProductRequest productRequest) {
        return productServiceBlockingStub.create(productRequest);
    }

    public ProductResponse update(ProductRequest productRequest) {
        return productServiceBlockingStub.update(productRequest);
    }

    public ProductListResponse update() {
        return productServiceBlockingStub.find(Empty.newBuilder().build());
    }

    public void delete(long id) {
        productServiceBlockingStub.delete(Int64Value.of(id));
    }
}

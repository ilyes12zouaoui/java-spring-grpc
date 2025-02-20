package de.ilyes.service;

import de.ilyes.grpc.product.proto.ProductRequest;
import de.ilyes.grpc.product.proto.ProductResponse;
import de.ilyes.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse create(ProductRequest productRequest) {

        return null;
    }

    public List<ProductResponse> find() {
        return null;
    }

    public ProductResponse update(ProductRequest productRequest) {
        return null;
    }

    public void delete(long id) {

    }

}

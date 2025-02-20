package de.ilyes.grpc_product_client.resource;

import de.ilyes.grpc.product.proto.ProductListResponse;
import de.ilyes.grpc.product.proto.ProductRequest;
import de.ilyes.grpc.product.proto.ProductResponse;
import de.ilyes.grpc_product_client.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductResource {

    private ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductListResponse find() {
        return productService.find();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse create(@RequestBody ProductRequest productRequest) {
        return productService.create(productRequest);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse update(@RequestBody ProductRequest productRequest) {
        return productService.update(productRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        productService.delete(id);
    }

}

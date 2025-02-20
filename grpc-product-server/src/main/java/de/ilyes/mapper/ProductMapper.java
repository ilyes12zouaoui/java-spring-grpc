package de.ilyes.mapper;

import de.ilyes.grpc.product.proto.ProductRequest;
import de.ilyes.grpc.product.proto.ProductResponse;
import de.ilyes.repository.entity.ProductEntity;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper {

    ProductEntity mapToProductEntity(ProductRequest productRequest);

    ProductResponse mapToProductResponse(ProductEntity productEntity);

}

package de.ilyes.server.config;

import de.ilyes.mapper.ProductMapper;
import de.ilyes.repository.ProductRepository;
import de.ilyes.server.GrpcProductService;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ImportAutoConfiguration({
        GrpcServerAutoConfiguration.class,
        GrpcServerFactoryAutoConfiguration.class,
        GrpcClientAutoConfiguration.class})
public class ProductServiceIntegrationTestConfiguration {
    @Bean
    ProductRepository foobar() {
        return mock(ProductRepository.class);
    }

    @Bean
    ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    GrpcProductService myServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        return new GrpcProductService(productRepository, productMapper);
    }
}

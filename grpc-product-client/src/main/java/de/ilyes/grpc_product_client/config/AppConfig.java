package de.ilyes.grpc_product_client.config;

import com.google.protobuf.util.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

@Configuration
public class AppConfig {
    @Bean
    public ProtobufJsonFormatHttpMessageConverter protobufHttpMessageConverter() {

        return new ProtobufJsonFormatHttpMessageConverter(
        );
    }
}

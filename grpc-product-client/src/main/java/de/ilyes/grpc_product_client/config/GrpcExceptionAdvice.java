package de.ilyes.grpc_product_client.config;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GrpcExceptionAdvice {

    @ExceptionHandler
    public StatusRuntimeException handleStatusRuntimeException(StatusRuntimeException e) {
        log.error("ERROR ---- received from grpc call", e);
        return e;
    }
}

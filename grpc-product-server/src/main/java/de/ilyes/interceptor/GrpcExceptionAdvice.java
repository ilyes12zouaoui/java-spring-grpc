package de.ilyes.interceptor;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcAdvice
public class GrpcExceptionAdvice {
    Logger logger = LoggerFactory.getLogger(GrpcExceptionAdvice.class);

    @GrpcExceptionHandler
    public StatusException handleInvalidArgument(IllegalArgumentException e) {
        Status status = Status.INVALID_ARGUMENT.withDescription("Illegal argument desc").withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("errorKeyMeta", Metadata.ASCII_STRING_MARSHALLER), "meta value");
        logger.error("ERROR ----- while calling grpc service", e);
        return status.asException(metadata);
    }
}

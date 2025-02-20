package de.ilyes.interceptor;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@GrpcGlobalServerInterceptor
public class HeaderMetadataInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        Context context = Context.current();
        context = context.withValue(ContextKeysMetadata.METADATA_CONTEXT,metadata);
        if (metadata.containsKey(ContextKeysMetadata.CORRELATION_ID_METADATA)) {
            context = context.withValue(ContextKeysMetadata.CORRELATION_ID_CONTEXT, metadata.get(ContextKeysMetadata.CORRELATION_ID_METADATA));
        } else {
            context = context.withValue(ContextKeysMetadata.CORRELATION_ID_CONTEXT, UUID.randomUUID().toString());
        }
        return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
    }
}

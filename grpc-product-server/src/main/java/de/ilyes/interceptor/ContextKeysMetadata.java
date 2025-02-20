package de.ilyes.interceptor;

import io.grpc.Context;
import io.grpc.Metadata;

public class ContextKeysMetadata {
    public static final Context.Key<Metadata> METADATA_CONTEXT = Context.key("metadata");
    public static final Context.Key<String> CORRELATION_ID_CONTEXT = Context.key("correlation-id");
    public static final Metadata.Key<String> CORRELATION_ID_METADATA = Metadata.Key.of("correlation-id", Metadata.ASCII_STRING_MARSHALLER);
}

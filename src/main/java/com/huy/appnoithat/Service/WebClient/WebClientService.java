package com.huy.appnoithat.Service.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huy.appnoithat.DataModel.MultipartForm;
import com.huy.appnoithat.DataModel.WebClient.Response;
import lombok.NonNull;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WebClientService {
    default <X> Optional<X> unauthorizedHttpPostJson(@NonNull URIBuilder uri,
                                                     @NonNull Object data, @NonNull Class<X> responseClass) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Optional<X> unauthorizedHttpGetJson(@NonNull URIBuilder uri, @NonNull Class<X> responseClass) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Optional<X> authorizedHttpPost(@NonNull URIBuilder uri, @NonNull Object data, @NonNull Class<X> responseClass) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Optional<X> authorizedHttpGet(@NonNull URIBuilder uri, @NonNull Class<X> responseClass) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Optional<List<X>> authorizedHttpGet(@NonNull URIBuilder uri, @NonNull Class<X> responseClass,
                                                    @NonNull Class<? extends Collection> collectionClass) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Optional<X> authorizedHttpPutJson(@NonNull URIBuilder uri, @NonNull Object data, @NonNull Class<X> responseClass) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Optional<X> authorizedHttpDeleteJson(@NonNull URIBuilder uri, @NonNull Class<X> responseClass) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Response<X> authorizedPostMultipartUpload(URIBuilder uri, MultipartForm multipartForm, TypeReference<X> typeReference) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Response<X> unauthorizedHttpPost(URIBuilder uri, Object object, TypeReference<X> typeReference) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Response<X> authorizedPutMultipartUpload(URIBuilder uri, MultipartForm multipartForm, TypeReference<X> typeReference) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Response<X> authorizedHttpGet(URIBuilder uri, TypeReference<X> typeReference) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Response<X> authorizedHttpPost(URIBuilder uri, Object body, TypeReference<X> typeReference) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Response<X> authorizedHttpDelete(URIBuilder uri, TypeReference<X> typeReference) {
        throw new RuntimeException("Check for implementation");
    }

    default <X> Response<X> authorizedHttpPut(URIBuilder uri, Object body, TypeReference<X> typeReference) {
        throw new RuntimeException("Check for implementation");
    }

    default Response<InputStream> authorizedHttpGetFile(URIBuilder uri) {
        throw new RuntimeException("Check for implementation");
    }
}

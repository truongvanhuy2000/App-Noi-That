package com.huy.appnoithat.IOC.Module;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.huy.appnoithat.Service.WebClient.ApacheHttpClient;
import com.huy.appnoithat.Service.WebClient.JavaNetHttpClient;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import jakarta.inject.Qualifier;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class HttpClientModule extends AbstractModule {
    @Provides
    @Singleton
    HttpClient apacheHttpClient() {
        return HttpClients.createDefault();
    }

    @Provides
    @Singleton
    java.net.http.HttpClient javaNetHttpClient() {
        return java.net.http.HttpClient.newHttpClient();
    }

    @Provides
    @ApacheHttp
    WebClientService apacheHttpClient(ApacheHttpClient apacheHttpClient) {
        return apacheHttpClient;
    }

    @Provides
    @JavaNet
    WebClientService javaNetHttpClient(JavaNetHttpClient javaNetHttpClient) {
        return javaNetHttpClient;
    }

    @Qualifier
    @Target({ FIELD, PARAMETER, METHOD })
    @Retention(RUNTIME)
    public @interface JavaNet {}

    @BindingAnnotation
    @Target({ FIELD, PARAMETER, METHOD })
    @Retention(RUNTIME)
    public @interface ApacheHttp {}
}

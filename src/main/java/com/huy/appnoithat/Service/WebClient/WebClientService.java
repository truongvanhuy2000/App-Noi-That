package com.huy.appnoithat.Service.WebClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WebClientService {
    public void randomPost() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/login");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity("{\"username\":\"admin\",\"password\":\"admin\"}"));
            httpclient.execute(httpPost, response -> {
//                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity2 = response.getEntity();
                EntityUtils.consume(entity2);
                return null;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

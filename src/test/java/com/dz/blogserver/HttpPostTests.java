package com.dz.blogserver;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author dz
 * @date 2020/7/9
 * @time 16:45
 */
@SpringBootTest
public class HttpPostTests {
    @Test
    public void postTest() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:3333/postStream");
        try {
            InputStream inputStream = new FileInputStream("D:\\1.txt");
            InputStreamEntity inputStreamEntity = new InputStreamEntity(inputStream);
            httpPost.setEntity(inputStreamEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

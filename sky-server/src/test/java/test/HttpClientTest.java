package test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

public class HttpClientTest {

    @Test
    public void getTest() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/admin/shop/status");
        httpGet.setHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJlbXBJZCI6MSwiZXhwIjoxNzM2Njc2ODIzfQ.mArCUceFNzP7SJ10O7LuI1wgRxU-mJnjS5rxgd3Dzug");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("返回的状态码:"+statusCode);
        HttpEntity entity = response.getEntity();
        String entityString = EntityUtils.toString(entity);
        System.out.println("返回的数据为"+entityString);
        httpClient.close();
        response.close();
    }


    @Test
    public void postTest() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");
        StringEntity stringEntity = new StringEntity("{\"username\":\"admin\",\"password\":\"123456\"}");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("encoding", "utf-8");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("返回的状态码："+statusCode);
        HttpEntity entity = response.getEntity();
        String entityString = EntityUtils.toString(entity);
        System.out.println("返回的实体："+entityString);

        response.close();
        httpClient.close();
    }
}

package sh.rescue.helper;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;

public class HttpHelper {
    private HttpHelper() {
    }

    public static byte[] post(String api, String json) {
        byte[] bytes = new byte[]{};

        try {
            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                HttpPost httpPost = new HttpPost(api);
                httpPost.setConfig(RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).build());
                httpPost.setHeader("Content-Type", "application/json;charset=utf8");
                httpPost.setEntity(new StringEntity(json, "UTF-8"));

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    HttpEntity responseEntity = response.getEntity();
                    bytes = EntityUtils.toString(responseEntity).getBytes(StandardCharsets.UTF_8);
                }
            }
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println("ERROR "+ (msg != null ? msg : "签名/验证网络请求失败"));
        }

        return bytes;
    }
}

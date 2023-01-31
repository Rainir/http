import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Employee> jsonList;
        ObjectMapper mapper = new ObjectMapper();
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            jsonList = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
            });
            List<Employee> upvotes = jsonList.stream().filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0).toList();
            System.out.println(upvotes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
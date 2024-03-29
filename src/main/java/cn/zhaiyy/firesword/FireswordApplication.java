package cn.zhaiyy.firesword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@ServletComponentScan
@SpringBootApplication
public class FireswordApplication {

    public static void main(String[] args) {
        SpringApplication.run(FireswordApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(1000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
}

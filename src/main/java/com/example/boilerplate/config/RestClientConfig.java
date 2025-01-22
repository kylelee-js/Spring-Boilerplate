package com.example.boilerplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
//        RestClient openaiClient = RestClient.builder().baseUrl("https://openai.com").build();
//        RestClientAdapter adapter = RestClientAdapter.create(openaiClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder().exchangeAdapter(adapter).build();
//
//        return factory.createClient(RestClient.class);
//    }

        return RestClient.create();
    }
}

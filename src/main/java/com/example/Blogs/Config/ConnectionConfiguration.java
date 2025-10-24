package com.example.Blogs.Config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

    @Bean
    public TomcatConnectorCustomizer asyncTimeoutCustomize(){
        return connector -> connector.setAsyncTimeout(30000);
    }
}

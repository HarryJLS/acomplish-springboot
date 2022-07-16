package com.example.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author JLS
 * @description: web请求类型自动判断，并向spring容器注入bean、
 * @since 2022-07-16 18:11
 */
@Configuration
public class WebServerAutoConfiguration implements AutoConfiguration{

    @Bean
    @ConditionalOnClass("org.apache.catalina.startup.Tomcat") // 判断条件是否生效，生效才会产生对应的bean
    public TomcatWebServer tomcatWebServer() {
        return new TomcatWebServer();
    }

    @Bean
    @ConditionalOnClass("org.eclipse.jetty.server.Server")
    public JettyWebServer jettyWebServer() {
        return new JettyWebServer();
    }
}

package com.example.springboot;

import java.util.Map;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static com.example.springboot.TomcatWebServer.startTomcat;

/**
 * @author JLS
 * @description:
 * @since 2022-07-16 17:07
 */
public class JlsSpringApplication {

    public static void run(Class clazz) {

        // 声明一个Spring容器
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        // 传进去clazz相对于是容器的配置类 设置给当前容器 （最后是会到解析MyApplication上的注解）
        applicationContext.register(clazz);
        // 启动容器,开始解析类
        applicationContext.refresh();

//        // 接收浏览器请求  返回请求类型是jetty还是tomcat的bean 主要是测试切换bean注入到spring容器
//        WebServer webServer = getWebServer(applicationContext);
//        webServer.start();

        // 启动tomcat 主要使用内嵌的tomcat
        startTomcat(applicationContext);
    }

    public static WebServer getWebServer(WebApplicationContext applicationContext) {

        // 依赖tomcat jetty undertow
        Map<String, WebServer> beansOfType = applicationContext.getBeansOfType(WebServer.class);

        if (beansOfType.isEmpty()) {
            throw new NullPointerException();
        }

        if (beansOfType.size() > 1) {
            throw new IllegalStateException();
        }

        return beansOfType.values().stream().findFirst().get();
    }

}

package com.example.springboot;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration //
@ComponentScan // 告诉容器去扫描，默认扫描当前MyApplication所在包路径
@Import(JlsImportSelect.class)
public @interface JlsSpringBootApplication {
}

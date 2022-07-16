package com.example.springboot;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author JLS
 * @description: 批量返回自动配置类 javaspi实现
 * @since 2022-07-16 17:30
 */
public class JlsImportSelect implements DeferredImportSelector {

    @Override    // DeferredImportSelector来源于spring，把所有配置类执行完后才会执行这个函数，保证了用户自己配置的bean解析完才来解析springboot配置的bean
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        // 自动配置                                 ServiceLoader.load读取resources/META-INF/SERVICES/com.example.springboot.AutoConfiguration文件内容
        ServiceLoader<AutoConfiguration> loader = ServiceLoader.load(AutoConfiguration.class);
        List<String> list = new ArrayList<>();
        for (AutoConfiguration configuration : loader) {
            list.add(configuration.getClass().getName());
        }
        return list.toArray(new String[0]);
    }
}

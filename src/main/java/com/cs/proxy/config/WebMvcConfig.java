package com.cs.proxy.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.TEXT_HTML);
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        converters.add(converter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 允许所有路径
                .allowedOrigins("*")  // 允许所有来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的方法
                .allowedHeaders("*")  // 允许所有头
                .allowCredentials(true)  // 允许凭证
                .maxAge(3600);  // 预检请求的缓存时间
    }

//    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

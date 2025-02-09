package com.cs.proxy.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
@Getter
public class Config {

    @Value("${deepSeek.apiKey}")
    private String deepSeekApiKey;

    @Value("${deepSeek.url}")
    private String deepSeekUrl;

}


package com.cs.proxy.controller;

import com.cs.proxy.common.BaseResponse;
import com.cs.proxy.common.ResultUtils;
import com.cs.proxy.config.Config;
import com.cs.proxy.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@Slf4j
public class CustomerController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Config config;

    @PostMapping("/v1/chat")
    public BaseResponse<String> getCustomerList(@RequestBody Map<String, Object> data) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + config.getDeepSeekApiKey());

        data.put("model", "deepseek-chat");
        log.error("发送消息内容：" + JsonUtil.serialize(data));
        // 创建请求实体
        HttpEntity<Map> requestEntity = new HttpEntity<>(data, headers);
        // 发送 POST 请求
        ResponseEntity<String> response = restTemplate.exchange(config.getDeepSeekUrl(), HttpMethod.POST, requestEntity, String.class);
        // 处理响应
        String result = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            log.error("请求结果：" + response.getBody());
            Map<String, Object> StringMap = JsonUtil.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {
            });
            result = response.getBody();
        } else {
            log.error("Failed to send message: " + response.getStatusCode());
            throw new RuntimeException("Failed to send message: " + response.getStatusCode());
        }
        return ResultUtils.success(result);
    }
}

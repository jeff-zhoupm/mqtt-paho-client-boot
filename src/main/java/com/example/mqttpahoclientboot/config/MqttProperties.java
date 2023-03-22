package com.example.mqttpahoclientboot.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* MQTT配置类
* @description: MQTT配置类
* @return:
*/
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    /**
     * EMQX服务地址
     */

    private String host;
    /**
     * 发送客户端ID
     */

    private String clientInId;
    /**
     * 订阅客户端ID
     */

    private String clientOutId;
    /**
     * 服务ID
     */

    private String serviceId;

    /**
     * 主题
     */

    private String topic;
    /**
     * 消息质量级别
     */

    private String qosLevel;
    /**
     * 用户名
     */

    private String username;
    /**
     * 密码
     */

    private String password;
    /**
     * 超时时间
     */

    private Integer timeout;
    /**
     * 心跳间隔
     */

    private Integer keepalive;



}

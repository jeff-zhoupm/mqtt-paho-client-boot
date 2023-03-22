package com.example.mqttpahoclientboot.config;


import com.example.mqttpahoclientboot.service.EmqClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: mqtt-paho-client
 * @description: MQTT消息配置类   自动启动类
 **/
@Component
public class MqttConfig {
    @Autowired
    private EmqClient emqClient;

    @Autowired
    private MqttProperties properties;

    @PostConstruct
    public void init(){
        //连接服务端
        emqClient.connect();
        //订阅一个主题
        //emqClient.subscribe(properties.getTopic(), QosEnum.Qos2);
    }
}

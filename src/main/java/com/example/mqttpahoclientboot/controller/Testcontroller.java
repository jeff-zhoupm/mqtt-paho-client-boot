package com.example.mqttpahoclientboot.controller;

import cn.hutool.json.JSONUtil;
import com.example.mqttpahoclientboot.config.MqttProperties;
import com.example.mqttpahoclientboot.enums.QosEnum;
import com.example.mqttpahoclientboot.service.EmqClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Testcontroller   也可使用mqtt客户端如mqtt.fx,mqttX等工具测试
 */
@RestController
@RequestMapping("/mqtt")
public class Testcontroller {

    @Autowired
    private EmqClient emqClient;
    @Autowired
    private MqttProperties mqttProperties;

    @PostMapping("/publish")
    @ResponseBody
    public synchronized void mqttMsg(String message) {

        HashMap<String, String> data = new HashMap<>();
        data.put("msg",message);
        data.put("sn","00024");
        data.put("channel","1");
        data.put("distance","250.12");
        String pushMessageJson = JSONUtil.toJsonStr(data);

        emqClient.publishMessage(mqttProperties.getTopic(), pushMessageJson, QosEnum.Qos2,false);
    }

}

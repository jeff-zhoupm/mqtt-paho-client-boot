package com.example.mqttpahoclientboot.service;

import com.example.mqttpahoclientboot.config.MqttProperties;
import com.example.mqttpahoclientboot.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * @program: mqtt-test
 * @description:
 **/
@Slf4j
@Component
public class MessageListener implements IMqttMessageListener {


    /**
     *  这里使用SpringUtils.getBean(MqttProperties.class)获取bean
     *  因为在spring中监听器获取不到容器中的bean 所以使用这种方式获取
     */
    private final MqttProperties properties = SpringUtils.getBean(MqttProperties.class);


    /**
     * 处理消息
     * @param topic 主题
     * @param mqttMessage 消息
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info(String.format("MQTT: 订阅主题[%s]发来消息[%s]", topic, new String(mqttMessage.getPayload())));
        // 以下可以根据自己的业务逻辑进行处理
        try {
            if (topic.equals(properties.getTopic())){
                log.info(String.format("MQTT: 订阅主题[%s]发来消息[%s]", topic, new String(mqttMessage.getPayload())));
            }
        }catch (Exception e){
            log.error("消息接收异常 : ",e);
        }


    }

}

package com.example.mqttpahoclientboot.service;

import com.example.mqttpahoclientboot.config.MqttProperties;
import com.example.mqttpahoclientboot.enums.QosEnum;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;

/**
* MQTT客户端
* @description: MQTT客户端
* @return:
*/
@Slf4j
@Component
public class EmqClient {

    private MqttClient mqttClient;
    @Autowired
    private MqttProperties mqttProperties;
    @Autowired
    private MessageCallback messageCallback;

    @PostConstruct
    public void init(){
        String host = mqttProperties.getHost();
        String clientInId = mqttProperties.getClientInId();
        //String clientOutId = mqttProperties.getClientOutId() + UUID.randomUUID();
        //数据持久化方式：内存
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient(host,clientInId,memoryPersistence);
        } catch (MqttException e) {
            log.error("初始化客户段MQTTClient对象失败，errorMessage={},broker={},clientId={}",e.getMessage(),host,clientInId);
        }
    }

    /**
     * 连接服务端broker
     */
    public void connect(){
        String username = mqttProperties.getUsername();
        String password = mqttProperties.getPassword();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        //自动重连
        options.setAutomaticReconnect(true);
        //是否临时会话   cleanSession 为 false 表示断线后会话不会被清除，服务器会保留客户端订阅信息等待客户端重连，在断线期间发布的消息也会保留，当同一客户端重连后会自动订阅原订阅的主题，并且可以订阅到断线时发布的主题消息（ 同一clientId 表示同一个客户端 ）。
        options.setCleanSession(true);
        //超时时间
        options.setConnectionTimeout(mqttProperties.getTimeout());
        //方法回调
        mqttClient.setCallback(messageCallback);
        try {
            mqttClient.connect(options);
        } catch (MqttException e) {
            log.error("MQTT客户端连接服务器失败，失败原因：{}",e.getMessage());
        }
    }

    /**
     * 断开连接
     */
    @PreDestroy
    public void disConnect(){
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            log.error("断开连接产生异常，异常信息：{}",e.getMessage());
        }
    }

    /**
     * 断开重连
     */
    public void reConnect(){
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
            log.error("重新连接服务端失败，异常信息：{}",e.getMessage());
        }
    }

    /**
     * 判断是否连接
     * @return boolean
     */
    public boolean isConnected(){

        return mqttClient.isConnected();

    }



    /**
     * 发布消息
     * @param topic 主题
     * @param message 消息信息
     * @param qos 消息质量
     * @param retain 是否保留
     */
    public void publishMessage(String topic, String message, QosEnum qos, boolean retain){
        //发布消息
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes(StandardCharsets.UTF_8));
        mqttMessage.setQos(qos.getValue());
        mqttMessage.setRetained(retain);
        try {
            mqttClient.publish(topic,mqttMessage);
        } catch (MqttException e) {
            log.error("发布消息失败,errorMsg={},topic={},msg={},qos={},retain={}",e.getMessage(),topic,message,qos.getValue(),retain);
        }
    }


    /**
     * 订阅
     * @param topicFilter 订阅的主题（可以带通配符）
     * @param qos
     */
    public void subscribe(String topicFilter,QosEnum qos){
        try {
            mqttClient.subscribe(topicFilter,qos.getValue(),new MessageListener());
            System.out.println("订阅了  topicFilter = " + topicFilter);
        } catch (MqttException e) {
            log.error("订阅主题失败,errorMsg={},topicFilter={},qos={}",e.getMessage(),topicFilter,qos.getValue());
        }

    }

    /**
     * 取消订阅
     * @param topicFilter
     */
    public void unSubscribe(String topicFilter){
        try {
            mqttClient.unsubscribe(topicFilter);
        } catch (MqttException e) {
            log.error("取消订阅失败,errorMsg={},topicfiler={}",e.getMessage(),topicFilter);
        }
    }
}

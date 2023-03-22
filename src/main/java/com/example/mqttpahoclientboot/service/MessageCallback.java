package com.example.mqttpahoclientboot.service;

import com.example.mqttpahoclientboot.config.MqttProperties;
import com.example.mqttpahoclientboot.enums.QosEnum;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* 消息回调
* @description: 消息回调
* @return:
*/
@Slf4j
@Component
public class MessageCallback implements MqttCallbackExtended {

    @Autowired
    private EmqClient emqClient;
    @Autowired
    private MqttProperties properties;



    /**
     * 客户端丢失了对服务端的连接后触发的回调
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        // 资源的清理  重连
        log.info("客户端丢失了对服务端的连接!");
        log.info("准备重新链接服务端......");
        //emqClient.reConnect();

        log.error("mqtt connectionLost 连接断开，5S之后尝试重连: {}", throwable.getMessage());
        long reconnectTimes = 1;
        while (true) {

                if (emqClient.isConnected()) {
                    //判断已经重新连接成功  需要重新订阅主题 可以在这个if里面订阅主题  或者 connectComplete（方法里面）  看你们自己选择
                    log.warn("mqtt reconnect success end  重新连接  重新订阅成功");
                    return;
                }
                reconnectTimes+=1;
                log.warn("mqtt reconnect times = {} try again...  mqtt重新连接时间 {}", reconnectTimes, reconnectTimes);
                emqClient.reConnect();

            try {
                Thread.sleep(5000);
                System.out.println("睡了 "+ 5 +" s");
            } catch (InterruptedException e) {
            }
        }
    }



    /**
     * 应用收到消息后触发的回调
     * 该方法由mqtt客户端同步调用,在此方法未正确返回之前，不会发送ack确认消息到broker
     * 一旦该方法向外抛出了异常客户端将异常关闭，当再次连接时；所有QoS1,QoS2且客户端未进行ack确认的消息都将由
     * broker服务器再次发送到客户端
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("订阅者订阅到了消息,topic={},messageId={},qos={},payload={}",
                topic,
                message.getId(),
                message.getQos(),
                new String(message.getPayload()));
    }

    @Override
    public void connectComplete(boolean reconnect,String serverURI){
        log.info("MQTT 连接成功，连接方式：{}",reconnect?"重连":"直连");
        // 连接成功  订阅主题或重新订阅主题
        emqClient.subscribe(properties.getTopic(), QosEnum.Qos2);
    }

    /**
     * 消息发布完成且收到ack确认后的回调
     * QoS0：消息被网络发出后触发一次
     * QoS1：当收到broker的PUBACK消息后触发
     * QoS2：当收到broer的PUBCOMP消息后触发
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        int messageId = token.getMessageId();
        String[] topics = token.getTopics();
        log.info("消息发布完成,messageId={},topics={}",messageId,topics);
    }
}

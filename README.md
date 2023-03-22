# mqtt-paho-client-boot

## 🌱 介绍
Paho Java客户端实现(一个简单的开箱即用的IoT开发的Java交互后端，也可以用来做即时通信）

### mqtt简介

MQTT 协议是 IBM 开发的即时通讯协议，相对于 IM 的实际上的准标准协议 XMPP 来说，MQTT 更小，更快，更轻量。MQTT 适合于任何计算能力有限，工作在低带宽、不可靠的网络中的设备，包括手机，传感器等等。

## 🍱 使用场景

- 物联网（边缘端消息通信）

- 类 IM

- 消息推送

- 简单易用的 mqtt 客户端

  ​

## 🚀 优势

- 平凡却不单调，简单却不失精彩。
- 手动档（更加易于二次开发或扩展）。
- 牛犊初生，无限可能。

## ✨ 功能

- [x] 支持 MQTT v3.1、v3.1.1 以及 v5.0 协议。
- [x] 支持 websocket mqtt 子协议（支持 mqtt.js）。
- [x] 支持 发布订阅功能 遗言通知
- [x] 支持 MQTT client 客户端 
- [x] 实现标准的 qos0 qos1 qos2消息确认机制
- [x] 支持 MQTT 遗嘱消息。
- [x] 支持 Spring boot 项目快速接入。

## 🚨 默认端口

| 端口号  | 协议   | 说明            |
| ---- | ---- | ------------- |
| 1883 | tcp  | mqtt tcp 端口   |
| 8888 | http | http api 协议端口 |

## 🏗️ mqtt 客户端工具

- [mqttx 优雅的跨平台 MQTT 5.0 客户端工具](https://mqttx.app/cn/)
- [mqtt websocket 调试](http://tools.emqx.io/)
- [mqttx.fx mqtt 客户端](http://mqttfx.org/)


## 📦️安装使用教程

1.  安装lombok插件
2.  下载源码
3.  springboot 项目
4.  jdk8 或以上
5.  导入IDE
6.  配置yml 或者properties 文件 [yml](https://github.com/1ssqq1lxr/iot_push/blob/master/iot_push_server_starter_test/src/main/resources/application.yml)
7.  简单测试：运行包 example 下的 测试 文件，即可开启测试客户端。
8.  压力测试：推荐使用jmeter 的mqtt插件 [插件](https://github.com/tuanhiep/mqtt-jmeter)

### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request





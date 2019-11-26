package com.javashitang.controller;

@ServerEndpoint("/websocket")
public class WebSocketServerController {

    /**
     * 收到消息调用的方法
     */
    @OnMessage
    public void onMessage(String message) {

    }

    /**
     * 建立连接调用的方法
     */
    @OnOpen
    public void onOpen() {
        System.out.println("Client connected");
    }

    /**
     * 关闭连接调用的方法
     */
    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }
}

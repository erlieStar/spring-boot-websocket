package com.javashitang.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint("/forshow/websocket/{sid}")
public class WebSocketServerController {

    // 收到消息调用的方法
    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 建立连接调用的方法
    @OnOpen
    public void onOpen(@PathParam("sid") String sid) {
        System.out.println("Client connected");
    }

    // 关闭连接调用的方法
    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }

    // 传输消息错误调用的方法
    @OnError
    public void OnError(Throwable error) {
        System.out.println("Connection error");
    }
}

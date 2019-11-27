package com.javashitang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/groupChat/{sid}/{username}")
public class GroupChatController {

    // 保存 组id->组成员 的映射关系
    private static ConcurrentHashMap<String, List<Session>> groupMemberInfoMap = new ConcurrentHashMap<>();

    // 收到消息调用的方法，群成员发送消息
    @OnMessage
    public void onMessage(@PathParam("sid") String sid,
                          @PathParam("username") String username, String message) {
        List<Session> sessionList = groupMemberInfoMap.get(sid);
        // 先一个群组内的成员发送消息
        sessionList.forEach(item -> {
            try {
                String text = username + ": " + message;
                item.getBasicRemote().sendText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // 建立连接调用的方法，群成员加入
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        List<Session> sessionList = groupMemberInfoMap.get(sid);
        if (sessionList == null) {
            sessionList = new ArrayList<>();
            groupMemberInfoMap.put(sid,sessionList);
        }
        sessionList.add(session);
        log.info("Connection connected");
        log.info("sid: {}, sessionList size: {}", sid, sessionList.size());
    }

    // 关闭连接调用的方法，群成员退出
    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        List<Session> sessionList = groupMemberInfoMap.get(sid);
        sessionList.remove(session);
        log.info("Connection closed");
        log.info("sid: {}, sessionList size: {}", sid, sessionList.size());
    }

    // 传输消息错误调用的方法
    @OnError
    public void OnError(Throwable error) {
        log.info("Connection error");
    }
}

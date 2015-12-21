package com.hsae.ims.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsae.ims.constants.ImsConstants;
import com.hsae.ims.entity.AuthenticatedUser;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.message.Message;
import com.hsae.ims.service.MessageService;
import com.hsae.ims.utils.RightUtil;

public class NoticeWebSocketHandler implements WebSocketHandler {
	 
	private static final Logger logger = LoggerFactory.getLogger(NoticeWebSocketHandler.class);
	
	private static final String WEBSOCKET_USERNAME="username";
    private static final ArrayList<WebSocketSession> users;
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MessageService messageService;
    static {
        users = new ArrayList();
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	 logger.debug("websocket afterConnectionEstablished closed......");
        users.add(session);
        Long userid=(Long)session.getAttributes().get(ImsConstants.WebSoket.USERID);
        User user=new User(userid);
        List<Message> readedmessages= messageService.findReaded(user);
        List<Message> unReadmessages= messageService.findUnRead(user);
        Map<String,List<Message>> map=new HashMap<String,List<Message>>(); 
        map.put("readed", readedmessages);
        map.put("unread", unReadmessages);
        String Json =  mapper.writeValueAsString(map);
        session.sendMessage(new TextMessage(Json));
    }
 
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	  logger.debug("websocket handleMessage closed......");
    	 session.sendMessage(new TextMessage("{a:'ss',b:'ccc'}"));
    }
 
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
    }
 
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        users.remove(session);
    }
 
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
 
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(WEBSOCKET_USERNAME).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
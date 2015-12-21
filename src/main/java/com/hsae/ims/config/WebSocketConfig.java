package com.hsae.ims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.hsae.ims.websocket.NoticeWebSocketHandler;
import com.hsae.ims.websocket.NoticeWebSocketHandshakeInterceptor;
/*@Configuration
@EnableWebMvc
@EnableWebSocket*/
/***
 * websoket消息提醒配置
 * @author panchaoyang
 *
 */
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(systemWebSocketHandler(),"/noticeWebSocketServer").addInterceptors(new NoticeWebSocketHandshakeInterceptor());
 
        registry.addHandler(systemWebSocketHandler(), "/sockjs/noticeWebSocketServer").withSockJS();
    }
    @Bean
    public WebSocketHandler systemWebSocketHandler(){
        return new NoticeWebSocketHandler();
    }
 
}
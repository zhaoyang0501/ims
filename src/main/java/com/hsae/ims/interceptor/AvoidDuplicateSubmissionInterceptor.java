package com.hsae.ims.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {
	   private Logger LOG = LoggerFactory.getLogger(AvoidDuplicateSubmissionInterceptor.class);
 
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.needSaveToken();
                if (needSaveSession) {
                    request.getSession(false).setAttribute("token", UUID.randomUUID().toString());
                }
 
                boolean needRemoveSession = annotation.needRemoveToken();
                if (needRemoveSession&&isRepeatSubmit(request)) {
                    if (isRepeatSubmit(request)) {
                    	if(request.getHeader("Accept").contains("json")){
                    		response.setCharacterEncoding("utf-8");
                    		response.getWriter().print("{\"code\":\"0111\",\"msg\":\"请不要重复提交！\"}");
                    		return false;
                    	}else{
                    		 LOG.warn("form 重复提交" + request.getServletPath() + "]"+request.getHeader("Referer"));
                         	throw new RuntimeException("对不起，您点击过于频繁，亲稍作休息！");
                    	}
                    }else{
                    	request.getSession(false).removeAttribute("token");
                    }
                }
           }
        return true;
    }
    
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
 
}
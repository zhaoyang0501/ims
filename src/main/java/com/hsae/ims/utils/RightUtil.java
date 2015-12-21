package com.hsae.ims.utils;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import com.hsae.ims.entity.AuthenticatedUser;
import com.hsae.ims.entity.Rights;

public class RightUtil {
	
    public static boolean hasRight(String rolename, String rightchar){
    	AuthenticatedUser c = (AuthenticatedUser)SecurityContextHolder.getContext() 
    	        .getAuthentication().getPrincipal(); 
    	
    	List<Rights> currentRights = c.getAllrights();
    	if (currentRights != null){
    		for(Rights right : currentRights){
    			if (right.getName().equals(rolename.toUpperCase())) {
    				if (right.getRights().equals(rightchar.toUpperCase())) {
    					return true;
					}
				}
    		}
    	}
    	return false;
    }
    
    public static long getCurrentUserId()
    {
    	AuthenticatedUser c = (AuthenticatedUser)SecurityContextHolder.getContext() 
    	        .getAuthentication().getPrincipal(); 
    	return c.getUserid();
    }
    
    public static String getCurrentChinesename(){
    	AuthenticatedUser c = (AuthenticatedUser)SecurityContextHolder.getContext() 
    	        .getAuthentication().getPrincipal(); 
    	return c.getChinesename();
    }

}

package com.hsae.ims.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.hsae.ims.utils.AntUrlPathMatcher;
import com.hsae.ims.utils.UrlMatcher;

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public MySecurityMetadataSource() {
		loadResourceDefine();
	}
	
	private void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> configCollection1 = new ArrayList<ConfigAttribute>();
		ConfigAttribute config1 = new SecurityConfig("ROLE_USER");
		configCollection1.add(config1);
		resourceMap.put("/index", configCollection1);

		Collection<ConfigAttribute> configCollection2 = new ArrayList<ConfigAttribute>();
		ConfigAttribute config2 = new SecurityConfig("ROLE_ADMIN");
		configCollection2.add(config2);
		resourceMap.put("/admin", configCollection2);
		
		Collection<ConfigAttribute> configCollection3 = new ArrayList<ConfigAttribute>();
		ConfigAttribute config3 = new SecurityConfig("ROLE_ADMIN");
		configCollection3.add(config3);
		resourceMap.put("/sandbox/rest", configCollection3);
	}
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (urlMatcher.pathMatchesUrl(resURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}

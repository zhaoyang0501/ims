package com.hsae.ims.controller;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class BaseController {
	/**
	 * Gets an integer parameter from the request
	 * 
	 * @param request
	 *            request
	 * @param name
	 *            parameter name
	 * @return the integer parameter
	 */
	protected Integer getIntParam(HttpServletRequest request, String name) {
		String[] p = request.getParameterValues(name);

		if (p == null) {
			return null;
		} else {
			return NumberUtils.createInteger(p[0]);
		}
	}

	/**
	 * Gets a String parameter from the request
	 * 
	 * @param request
	 *            request
	 * @param name
	 *            parameter name
	 * @return the String parameter
	 */
	protected String getStringParam(HttpServletRequest request, String name) {
		String[] p = request.getParameterValues(name);

		if (p == null) {
			return null;
		} else {
			return p[0];
		}
	}

	@InitBinder
    public void dataBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        PropertyEditor propertyEditor = new CustomDateEditor(dateFormat, true );
        binder.registerCustomEditor(Date.class , propertyEditor);
    }
}

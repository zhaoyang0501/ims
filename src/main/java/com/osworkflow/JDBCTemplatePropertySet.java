package com.osworkflow;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.module.propertyset.AbstractPropertySet;
import com.opensymphony.module.propertyset.PropertyException;
/**
 * @@author <a href="mailto:cucuchen520@yahoo.com.cn">Chris Chen</a>
 */

public class JDBCTemplatePropertySet extends AbstractPropertySet {
    protected static Log log = LogFactory.getLog(JDBCTemplatePropertySet.class);
    private JDBCTemplatePropertySetDAO jDBCTemplatePropertySetDAO;
    
    @Override
	public void init(Map config, Map args) {
		super.init(config, args);
		this.jDBCTemplatePropertySetDAO.setGlobalKey((String)args.get("globalKey"));
	}

	public JDBCTemplatePropertySetDAO getjDBCTemplatePropertySetDAO() {
		return jDBCTemplatePropertySetDAO;
	}

	public void setjDBCTemplatePropertySetDAO(
			JDBCTemplatePropertySetDAO jDBCTemplatePropertySetDAO) {
		this.jDBCTemplatePropertySetDAO = jDBCTemplatePropertySetDAO;
	}

	public Collection getKeys(String prefix, int type) throws PropertyException {
        return jDBCTemplatePropertySetDAO.getKeys(prefix, type);
    }

    public int getType(String key) throws PropertyException {
        return jDBCTemplatePropertySetDAO.getType(key);
    }

    public boolean exists(String key) throws PropertyException {
        return jDBCTemplatePropertySetDAO.exists(key);
    }

    public void remove(String key) throws PropertyException {
    	jDBCTemplatePropertySetDAO.remove(key);
    }

    protected void setImpl(int type, String key, Object value) throws PropertyException {
    	jDBCTemplatePropertySetDAO.setImpl(type, key, value);
    }

    protected Object get(int type, String key) throws PropertyException {
        return jDBCTemplatePropertySetDAO.get(type, key);
    }

    public void remove() throws PropertyException {

    }
}

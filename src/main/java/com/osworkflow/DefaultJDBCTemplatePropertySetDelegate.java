package com.osworkflow;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.module.propertyset.PropertySetManager;
import com.opensymphony.module.propertyset.config.PropertySetConfig;
import com.opensymphony.workflow.util.PropertySetDelegate;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:cucuchen520@yahoo.com.cn">Chris Chen</a>
 */
public class DefaultJDBCTemplatePropertySetDelegate implements PropertySetDelegate  , ApplicationContextAware {
    //~ Instance fields ////////////////////////////////////////////////////////
	private static ApplicationContext applicationContext;
	
    private DataSource dataSource;
    
    private PropertySet propertySet;

    //~ Constructors ///////////////////////////////////////////////////////////

    public PropertySet getPropertySet() {
		return propertySet;
	}

	public void setPropertySet(PropertySet propertySet) {
		this.propertySet = propertySet;
	}

	public DefaultJDBCTemplatePropertySetDelegate() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////
	public void fuckyou(){
		 /* PropertySetConfig psc = PropertySetConfig.getConfig();
	        String clazz = psc.getClassName(name);
	        Map config = psc.getArgs(name);
	        Class psClass;

	        try {
	            psClass = loader.loadClass(clazz);
	        } catch (ClassNotFoundException ex) {
	            return null;
	        }

	        try {
	            PropertySet ps = (PropertySet) psClass.newInstance();
	            ps.init(config, args);

	            return ps;
	        } catch (InstantiationException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        }

	        return null;*/
	}
    public PropertySet getPropertySet(long entryId) {
      Map args = new HashMap(1);
        args.put("globalKey", "osff_temp_" + entryId);
    	PropertySet propertySet =applicationContext.getBean("propertySet", PropertySet.class);
    	propertySet.init(null, args);
    	return propertySet;
    }

    private DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		  this.applicationContext=applicationContext;
	}
}

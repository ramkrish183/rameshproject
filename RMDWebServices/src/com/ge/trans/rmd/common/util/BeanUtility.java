/**
 * ============================================================
 * Classification: GE Confidential
 * File : BeanUtils.java
 * Description :
 * Package : com.ge.trans.rmd.services.util
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : July 21, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ge.trans.rmd.utilities.RMDPropertiesLoader;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Common File for Webservices and provide the
 *              Case related funtionalities
 * @History :
 ******************************************************************************/
public class BeanUtility {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(BeanUtility.class);

    @Autowired
    OMDResourceMessagesIntf omdResourceMessagesIntf;

    public static Object copyBeanProperty(final Object source, final Object target)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, FileNotFoundException,
            IOException, ClassNotFoundException {

        final BeanUtilsBean bub = new BeanUtilsBean();
        final Map<Object, Object> sorceVOMap = BeanUtils.describe(source);
        Properties pro = new Properties();
        try {
            pro = RMDPropertiesLoader.loadProperties(OMDConstants.BEAN_PROPERTIES);

            for (Map.Entry entry : sorceVOMap.entrySet()) {

                final String sourceKey = stripXSSCharacters((String) entry.getKey());
                final String sourcename = stripXSSCharacters(source.getClass().getCanonicalName());
                final String sourceClassName = stripXSSCharacters(sourcename.substring(sourcename.lastIndexOf(OMDConstants.DOT) + 1,
                        sourcename.length()));
                final String targetname = stripXSSCharacters(target.getClass().getCanonicalName());
                final String targetClassName = stripXSSCharacters(targetname.substring(targetname.lastIndexOf(OMDConstants.DOT) + 1,
                        targetname.length()));
                final String name = stripXSSCharacters(sourceClassName + OMDConstants.DOT + targetClassName + OMDConstants.DOT + sourceKey);
                final String targetkey = stripXSSCharacters(pro.getProperty(name));
                String sourceType = stripXSSCharacters((String) sorceVOMap.get(sourceKey));
                if (targetkey != null) {

                    if (!targetkey.equals(OMDConstants.EMPTY_STRING) && sourceType != null
                            && sourceType.trim().indexOf(OMDConstants.SYMBOL_ATRATE) > -1) {
                        String soucreobjtype = stripXSSCharacters(bub.getPropertyUtils().getPropertyType(source, sourceKey).toString());

                        if (soucreobjtype.equalsIgnoreCase(OMDConstants.INTERFACE_JAVAUTIL)) {

                            final List propList = (List) bub.getPropertyUtils().getProperty(source, sourceKey);

                            final Iterator iterator = propList.iterator();
                            Object srcObj;
                            final List targetList = new ArrayList();
                            while (iterator.hasNext()) {
                                srcObj = iterator.next();

                                Object insClass;
                                try {
                                    final String instanceForClass = stripXSSCharacters(source.getClass().getCanonicalName()
                                            + OMDConstants.DOT +  stripXSSCharacters(target.getClass().getCanonicalName()) + OMDConstants.DOT
                                            + stripXSSCharacters(sourceKey)+ OMDConstants.CLOSED_TO_LOAD);
                                    insClass = Class.forName(stripXSSCharacters(pro.getProperty(stripXSSCharacters(instanceForClass)))).newInstance();
                                    copyBeanProperty(srcObj, insClass);
                                    targetList.add(insClass);
                                } catch (InstantiationException excep) {
                                    LOG.error("Error occured in BeanUtility - copyBeanProperty method" + excep);
                                }

                            }
                            bub.copyProperty(target, targetkey, targetList);
                        } else {
                            String targetType = stripXSSCharacters(bub.getPropertyUtils().getPropertyType(target, targetkey).toString());
                            targetType = targetType.substring(targetType.indexOf(OMDConstants.STRING_EMPTY),
                                    targetType.length());
                            if (!RMDCommonUtility.isNullOrEmpty(soucreobjtype)) {
                                if (soucreobjtype.equalsIgnoreCase(OMDConstants.CLASS_JAVASTRING)) {
                                    soucreobjtype = soucreobjtype.substring(
                                            soucreobjtype.indexOf(OMDConstants.STRING_EMPTY), soucreobjtype.length());
                                    if (targetType.equalsIgnoreCase(soucreobjtype)) {
                                        new BeanUtilsBean().copyProperty(target, targetkey, bub.getPropertyUtils()
                                                .getNestedProperty(source, (String) entry.getKey()));
                                    }
                                } else {
                                    final int sourceIndex = sourceType.indexOf(OMDConstants.SYMBOL_ATRATE);
                                    if (sourceIndex > -1) {
                                        sourceType = sourceType.substring(0, sourceIndex);
                                        if (sourceType.trim().equals(targetType.trim())) {
                                            bub.copyProperty(target, targetkey, bub.getPropertyUtils()
                                                    .getNestedProperty(source, (String) entry.getKey()));
                                        } else {
                                            final Object subSource = bub.getPropertyUtils().getProperty(source,
                                                    (String) entry.getKey());
                                            final Object subTarget = bub.getPropertyUtils().getProperty(target,
                                                    targetkey);

                                            copyBeanProperty(subSource, subTarget);

                                            bub.copyProperty(target, targetkey, subTarget);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        new BeanUtilsBean().copyProperty(target, targetkey,
                                bub.getPropertyUtils().getNestedProperty(source, (String) entry.getKey()));// bub.getPropertyUtils().getNestedProperty(source,
                        // (String)entry.getKey())
                    }
                }
            }
        } catch (Exception excep) {
            LOG.error("Error occured in copyBeanProperty - BeanUtility" + excep);
        }
        return target;
    }

    private Properties getPropertiesFromClasspath(final String propFileName) throws IOException {
        final Properties props = new Properties();
        // LOG.debug(" this.getClass().getClassLoader()"+
        // this.getClass().getClassLoader());
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
        try {
            // LOG.debug("inputStream"+inputStream);
            if (inputStream == null) {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");

            } else {
                props.load(inputStream);
            }
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
        return props;
    }

    public static XMLGregorianCalendar convertDateToXMLDate(final Date dateValue,
            final XMLGregorianCalendar dtXMLDate) {
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar dtXMLGregorianDate = dtXMLDate;
        try {
            if (dateValue != null) {
                objGregorianCalendar = new GregorianCalendar();
                objGregorianCalendar.setTime(dateValue);
                dtXMLGregorianDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
            }
        } catch (Exception excep) {
            LOG.error("Error occured in convertDateToXMLDate - BeanUtility" + excep);
        }
        return dtXMLGregorianDate;
    }

    public static String getErrorCode(final String key) {
        String name = OMDConstants.EMPTY_STRING;
        try {
            name = new BeanUtility().getPropertiesFromClasspath(OMDConstants.PROPERTIES_FILE_NAME).getProperty(key);
        } catch (IOException excep) {
            LOG.error("Error occured in getErrorCode - BeanUtility" + excep);
        }
        return name;
    }

    public static Locale getLocale(final String language) {
        Locale local = Locale.ENGLISH;
        if (OMDConstants.CHINESE_LANGUAGE.equals(language)) {
            local = Locale.SIMPLIFIED_CHINESE;
        }
        return local;
    }

    /**
     * @Author:
     * @param list
     * @return
     * @Description: This method will check whether the passed collection is not
     *               null & not empty
     */
    public static boolean isCollectionNotEmpty(final Collection list) {
        return (list != null && !list.isEmpty());
    }
    
    //Added by Vamshi
    
    /**
	 * @Author:
	 * @param : String
	 * @return: String
	 * @Description: This method will Strip the XSS Characters in the String.
	 */
	
	public static String stripXSSCharacters(String input) {
		String encodedString = null, decodedString = null;
		if (null != input) {
			encodedString = ESAPI.encoder().encodeForHTML(input);
			decodedString = ESAPI.encoder().decodeForHTML(encodedString);
		}
		return decodedString;
	}
	
	
	
	
	/**
	 * @Author:
	 * @param : Long
	 * @return: Long
	 * @Description: This method will Strip the XSS Characters from an Array List of Strings.
	 */
	public static List<String> stripXSSCharctersfromList(List<String> arrList)
	{
		List<String> strippedArray=new ArrayList<String>();
		
		for(String input:arrList)
		{
		String encodedString = null, decodedString = null;
		if (null != input) {
		encodedString = ESAPI.encoder().encodeForHTML(input);
		decodedString = ESAPI.encoder().decodeForHTML(encodedString);
		strippedArray.add(decodedString);
		}
		}
		return strippedArray;
	}
}
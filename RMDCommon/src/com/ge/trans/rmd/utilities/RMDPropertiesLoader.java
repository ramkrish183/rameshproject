/**
 * ============================================================
 * File : RMDPropertiesLoader.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.utilities
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 30, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.utilities;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.core.io.ClassPathResource;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDPropertiesLoaderException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Class for loading properties file
 * @History :
 ******************************************************************************/
public final class RMDPropertiesLoader {

    /**
     * Private Constructor
     */
    private RMDPropertiesLoader() {
    }

    /**
     * @Author:
     * @param path
     * @return Properties
     * @throws RMDPropertiesLoaderException
     * @Description: load the properties file from the specified path
     */
    public static Properties loadProperties(String path) throws RMDPropertiesLoaderException {
        Properties properties = null;
        try {
            properties = new Properties();
            ClassPathResource classPathResource = new ClassPathResource(path);
            properties.load(classPathResource.getInputStream());
        } catch (IOException ex) {
            if (path.equals(RMDCommonConstants.PROPERTIES_FILE_NAME)) {
                throw new RMDPropertiesLoaderException(ex);
            } else {
                throw new RMDPropertiesLoaderException();
            }
        }
        return properties;
    }

    /**
     * @Author:
     * @param path
     * @return Properties
     * @throws RMDPropertiesLoaderException
     * @Description: load the xml properties file
     */
    public static Properties loadLog4jXML(String path) throws RMDPropertiesLoaderException {
        Properties properties = null;
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            DOMConfigurator.configure(classPathResource.getURL());
        } catch (Exception e) {
            throw new RMDPropertiesLoaderException(e);
        }
        return properties;
    }
}
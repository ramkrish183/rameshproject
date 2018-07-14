/**
 * ============================================================
 * File : RMDLoggerUtility.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.services.cases.utils
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
package com.ge.trans.rmd.logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.core.io.ClassPathResource;

import com.ge.trans.rmd.exception.RMDRunTimeException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Utility class for initializing the logger
 * @History :
 ******************************************************************************/
public final class RMDLoggerUtility {

    /**
     * Private Constructor
     */
    private RMDLoggerUtility() {
    }

    /**
     * @Author:
     * @Description: Method to load the log4j.properties file
     */
    public static void initializeLogger(final String logFilePath) {
        final Properties logProperties = new Properties();
        final ClassPathResource classPathResource = new ClassPathResource(logFilePath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(classPathResource.getFile());
            logProperties.load(inputStream);
            PropertyConfigurator.configure(logProperties);
        } catch (IOException e) {
            throw new RMDRunTimeException(e,
                    "Unable to load logging Configuration properties File " + "log4j.properties ");
        } finally {

            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RMDRunTimeException(e, "error occured while closing the stream");
            }
        }
    }

    /**
     * @Author:
     * @Description: Method to load the log4j.xml file
     */
    public static void initializeLoggerXML(String logFilePath) {
        ClassPathResource classPathResource = new ClassPathResource(logFilePath);
        try {
            DOMConfigurator.configure(classPathResource.getURL());
        } catch (Exception e) {
            throw new RMDRunTimeException(e, "Unable to load logging Configuration XML File " + "log4j.xml ");
        }
    }
}
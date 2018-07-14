/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDServiceLocator.java
 * Description : 
 * 
 * Package :com.ge.trans.rmd.common.locator
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2008 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.common.locator;

import java.util.Properties;

import javax.ejb.EJBHome;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDServiceLocatorException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Class for Resource Locator This class locates EJB, and other
 *              resources.
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public final class RMDServiceLocator {

    private static RMDServiceLocator resourceLocator;
    private InitialContext context = null;

    /**
     * Get the Context
     * 
     * @throws RMDServiceLocatorException
     */
    private RMDServiceLocator() throws RMDServiceLocatorException {
        try {
            Properties env = new Properties();
            env.put(RMDCommonConstants.JAVA_NAMING_FACTORY_INITIAL, RMDCommonUtility
                    .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.JAVA_NAMING_FACTORY_INITIAL));
            env.put(RMDCommonConstants.JAVA_NAMING_PROVIDER_URL, RMDCommonUtility
                    .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.JAVA_NAMING_PROVIDER_URL));
            context = new InitialContext(env);
        } catch (NamingException ne) {
            throw new RMDServiceLocatorException("Error while Trying to get Initial Context", ne);
        }
    }

    /**
     * Returns the instance of EJBServiceLocator class
     * 
     * @return
     */
    public static RMDServiceLocator getInstance() throws RMDServiceLocatorException {
        if (resourceLocator == null) {
            resourceLocator = new RMDServiceLocator();
        }
        return resourceLocator;
    }

    /**
     * Returns the EJBHome object for requested service name. Throws
     * ServiceLocatorException If Any Error occurs in lookup
     * 
     * @param name
     * @param clazz
     * @return
     * @throws RMDServiceLocatorException
     */
    public EJBHome getHome(final String name, final Class clazz) throws RMDServiceLocatorException {
        try {
            final Object objref = context.lookup(name);
            return (EJBHome) PortableRemoteObject.narrow(objref, clazz);
        } catch (NamingException ex) {
            throw new RMDServiceLocatorException("Naming Not Found", ex);
        }
    }

    /**
     * Returns the Resource object for requested service name. Throws
     * ServiceLocatorException If Any Error occurs in lookup
     * 
     * @param name
     * @return
     * @throws RMDServiceLocatorException
     */
    public Object getResource(final String name) throws RMDServiceLocatorException {
        try {
            final String workerClassName = (String) context.lookup(name);
            final Class clazz = Class.forName(workerClassName);
            return clazz.newInstance();
        } catch (NamingException ex) {
            throw new RMDServiceLocatorException("Naming Not Found", ex);
        } catch (ClassNotFoundException ex) {
            throw new RMDServiceLocatorException("Class Not Found", ex);
        } catch (Exception ex) {
            throw new RMDServiceLocatorException("Error in Getting Resource", ex);
        }
    }

    /**
     * Returns the Message Source for requested service name. Throws
     * ServiceLocatorException If Any Error occurs in lookup
     * 
     * @param name
     * @return
     * @throws RMDServiceLocatorException
     */
    public Object getMessageSource(final String name) throws RMDServiceLocatorException {
        try {
            return context.lookup(name);
        } catch (NamingException ex) {
            throw new RMDServiceLocatorException("Naming Not Found", ex);
        } catch (Exception ex) {
            throw new RMDServiceLocatorException("Error in Getting Resource", ex);
        }
    }
}

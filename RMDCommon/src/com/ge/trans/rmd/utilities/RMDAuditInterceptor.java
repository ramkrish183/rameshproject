/**
 * ============================================================
 * File : RMDAuditInterceptor.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.caseapi.services.utils;
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

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDAuditInterceptor extends EmptyInterceptor implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7114170753579082188L;
    private String user;
    private static Logger log = null;

    /**
     * 
     */
    public RMDAuditInterceptor() {
        super();
    }

    /**
     * @param user
     */
    public RMDAuditInterceptor(String user) {
        super();
        this.user = user;
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onLoad(java.lang.Object,
     * java.io.Serializable, java.lang.Object[], java.lang.String[],
     * org.hibernate.type.Type[])
     */
    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object,
     * java.io.Serializable, java.lang.Object[], java.lang.String[],
     * org.hibernate.type.Type[])
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        log = Logger.getLogger(entity.getClass());
        if (entity instanceof RMDAuditableIntf) {
            try {
                int size = propertyNames.length;
                for (int counter = 0; counter < size; counter++) {
                    if ("createdBy".equals(propertyNames[counter])) {
                        state[counter] = this.user;
                    }
                    if ("creationDate".equals(propertyNames[counter])) {
                        state[counter] = RMDCommonUtility
                                .getESTDateTime(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
                        log.debug("Creation Date::" + state[counter]);
                    }
                    if ("lastUpdatedBy".equals(propertyNames[counter])) {
                        state[counter] = this.user;
                    }
                    if ("lastUpdatedDate".equals(propertyNames[counter])) {
                        state[counter] = RMDCommonUtility
                                .getESTDateTime(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
                        log.debug("Last Updated Date::" + state[counter]);
                    }
                }
            } catch (Exception e) {
                log.debug(e.getMessage(), e);
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object,
     * java.io.Serializable, java.lang.Object[], java.lang.Object[],
     * java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) {
        if (entity instanceof RMDAuditableIntf) {
            try {
                int size = propertyNames.length;
                for (int counter = 0; counter < size; counter++) {
                    if (("lastUpdatedBy").equals(propertyNames[counter])) {
                        currentState[counter] = this.user;
                    }
                    if (("lastUpdatedDate").equals(propertyNames[counter])) {
                        currentState[counter] = RMDCommonUtility
                                .getESTDateTime(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
                    }
                }
            } catch (Exception e) {
                log.debug(e.getMessage(), e);
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onDelete(java.lang.Object,
     * java.io.Serializable, java.lang.Object[], java.lang.String[],
     * org.hibernate.type.Type[])
     */
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#afterTransactionBegin(org.hibernate.
     * Transaction)
     */
    @Override
    public void afterTransactionBegin(Transaction arg0) {
    }

    /*
     * (non-Javadoc)
     * @see
     * org.hibernate.EmptyInterceptor#afterTransactionCompletion(org.hibernate.
     * Transaction)
     */
    @Override
    public void afterTransactionCompletion(Transaction arg0) {
    }

    /*
     * (non-Javadoc)
     * @see
     * org.hibernate.EmptyInterceptor#beforeTransactionCompletion(org.hibernate.
     * Transaction)
     */
    @Override
    public void beforeTransactionCompletion(Transaction arg0) {
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#getEntity(java.lang.String,
     * java.io.Serializable)
     */
    @Override
    public Object getEntity(String arg0, Serializable arg1) throws CallbackException {
        return null;
    }

    /**
     * @Author:
     * @param arg0
     * @return
     * @throws CallbackException
     * @Description:
     */
    public String getEntityJName(Object arg0) throws CallbackException {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#instantiate(java.lang.String,
     * org.hibernate.EntityMode, java.io.Serializable)
     */
    @Override
    public Object instantiate(String arg0, EntityMode arg1, Serializable arg2) throws CallbackException {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#isTransient(java.lang.Object)
     */
    @Override
    public Boolean isTransient(Object arg0) {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.hibernate.EmptyInterceptor#onCollectionRecreate(java.lang.Object,
     * java.io.Serializable)
     */
    @Override
    public void onCollectionRecreate(Object arg0, Serializable arg1) throws CallbackException {
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onCollectionRemove(java.lang.Object,
     * java.io.Serializable)
     */
    @Override
    public void onCollectionRemove(Object arg0, Serializable arg1) throws CallbackException {
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onCollectionUpdate(java.lang.Object,
     * java.io.Serializable)
     */
    @Override
    public void onCollectionUpdate(Object arg0, Serializable arg1) throws CallbackException {
    }
}

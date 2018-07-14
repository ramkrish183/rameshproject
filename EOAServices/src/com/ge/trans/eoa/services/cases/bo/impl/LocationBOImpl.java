/**
 * ============================================================
 * File : LocationBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.bo.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 14, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.bo.impl;

import java.util.List;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.bo.intf.LocationBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.LocationDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CreateLocationServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocationServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 14, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class LocationBOImpl implements LocationBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(LocationBOImpl.class);
    private LocationDAOIntf objLocationDAOIntf;

    /**
     * @param objLocationDAOIntf
     */
    public LocationBOImpl(final LocationDAOIntf objLocationDAOIntf) {
        this.objLocationDAOIntf = objLocationDAOIntf;
    }

    /**
     * @return the objLocationDAOIntf
     */
    public LocationDAOIntf getObjLocationDAOIntf() {
        return objLocationDAOIntf;
    }

    /**
     * @param objLocationDAOIntf
     *            the objLocationDAOIntf to set
     */
    public void setObjLocationDAOIntf(final LocationDAOIntf objLocationDAOIntf) {
        this.objLocationDAOIntf = objLocationDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.LocationBOIntf
     * #findLocation(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FindLocationServiceVO)
     *//*
       * This Method is used for call the findLocation method in LocationDAOImpl
       */
    @Override
    public List findLocation(FindLocationServiceVO objFindLocationServiceVO) throws RMDBOException {
        List arlLocation = null;
        try {
            arlLocation = objLocationDAOIntf.findLocation(objFindLocationServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlLocation;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.LocationBOIntf#
     * getEditLocationDetails(java.lang.String)
     *//*
       * This Method is used for call the getEditLocationDetails method in
       * LocationDAOImpl
       */
    @Override
    public List getEditLocationDetails(final String strLocationId, String strLanguage) throws RMDBOException {
        List arlLocation = null;
        try {
            arlLocation = objLocationDAOIntf.getEditLocationDetails(strLocationId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlLocation;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.LocationBOIntf#
     * getLocationContactsList(java.lang.String)
     *//*
       * This Method is used for call the getLocationContactsList method in
       * LocationDAOImpl
       */
    @Override
    public List getLocationContactsList(final String strLocationId, final String strLanguage) throws RMDBOException {
        List arlContactLocation = null;
        try {
            arlContactLocation = objLocationDAOIntf.getLocationContactsList(strLocationId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlContactLocation;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.LocationBOIntf#
     * createLocation(com.ge.trans.rmd.services.cases.service.valueobjects.
     * CreateLocationServiceVO)
     *//*
       * This Method is used for call the createLocation method in
       * LocationDAOImpl
       */
    @Override
    public String createLocation(final CreateLocationServiceVO objCreateLocationServiceVO,
            final String strEditCreateLocation) throws RMDBOException {
        String strViewID = null;
        try {
            if (null != strEditCreateLocation && RMDCommonConstants.YES.equalsIgnoreCase(strEditCreateLocation)) {
                strViewID = objLocationDAOIntf.updateLocation(objCreateLocationServiceVO);
            } else {
                strViewID = objLocationDAOIntf.checkLocationId(objCreateLocationServiceVO);
                if (strViewID != RMDServiceConstants.LOCATIONID_ALREADY_EXIST) {
                    strViewID = objLocationDAOIntf.createLocation(objCreateLocationServiceVO);
                }
            }
        } catch (RMDDAOException e) {
            throw e;
        }
        return strViewID;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.LocationBOIntf#loadStatusDropDown
     * (java.lang.String)
     *//*
       * This Method is used for call the loadStatusDropDown method in
       * LocationDAOImpl
       */
    @Override
    public List loadStatusDropDown(final String strLanguage) throws RMDBOException {
        List arlStatus = null;
        try {
            arlStatus = objLocationDAOIntf.loadStatusDropDown(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlStatus;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.LocationBOIntf#loadTypeDropDown(
     * java.lang.String)
     *//*
       * This Method is used for call the loadTypeDropDown method in
       * LocationDAOImpl
       */
    @Override
    public List loadTypeDropDown(final String strLanguage) throws RMDBOException {
        List arlType = null;
        try {
            arlType = objLocationDAOIntf.loadTypeDropDown(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlType;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.LocationBOIntf#
     * loadTimeZoneDropDown(java.lang.String)
     *//*
       * This Method is used for call the loadTypeDropDown method in
       * LocationDAOImpl
       */
    @Override
    public List loadTimeZoneDropDown(final String strLanguage) throws RMDBOException {
        List arlTimeZone = null;
        try {
            arlTimeZone = objLocationDAOIntf.loadTimeZoneDropDown(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlTimeZone;
    }
}

/**
 * ============================================================
 * File : LocationServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.impl
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
package com.ge.trans.eoa.services.cases.service.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.bo.intf.LocationBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.LocationServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CreateLocationServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocationServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

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
public class LocationServiceImpl implements LocationServiceIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(LocationServiceImpl.class);
    private LocationBOIntf objLocationBOIntf;

    /**
     * @param objLocationBOIntf
     */
    public LocationServiceImpl(LocationBOIntf objLocationBOIntf) {
        this.objLocationBOIntf = objLocationBOIntf;
    }

    /**
     * @return the objLocationBOIntf
     */
    public LocationBOIntf getObjLocationBOIntf() {
        return objLocationBOIntf;
    }

    /**
     * @param objLocationBOIntf
     *            the objLocationBOIntf to set
     */
    public void setObjLocationBOIntf(LocationBOIntf objLocationBOIntf) {
        this.objLocationBOIntf = objLocationBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.LocationServiceIntf#
     * findLocation(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FindLocationServiceVO)
     *//*
       * This Method is used for call the findLocation method in LocationBOImpl
       */
    @Override
    public List findLocation(FindLocationServiceVO objFindLocationServiceVO) throws RMDServiceException {
        LOG.debug("Begin LocationServiceImpl findLocation method");
        List arlLocation = null;
        try {
            arlLocation = objLocationBOIntf.findLocation(objFindLocationServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFindLocationServiceVO.getStrLanguage());
        }
        LOG.debug("End LocationServiceImpl findLocation method");
        return arlLocation;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.LocationServiceIntf#
     * getEditLocationDetails(java.lang.String)
     *//*
       * This Method is used for call the getEditLocationDetails method in
       * LocationBOImpl
       */
    @Override
    public List getEditLocationDetails(String strLocationId, String strLanguage) throws RMDServiceException {
        LOG.debug("Begin LocationServiceImpl getEditLocationDetails method");
        List arlLocation = null;
        try {
            arlLocation = objLocationBOIntf.getEditLocationDetails(strLocationId, strLanguage);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End LocationServiceImpl getEditLocationDetails method");
        return arlLocation;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.LocationServiceIntf#
     * getLocationContactsList(java.lang.String)
     *//*
       * This Method is used for call the getLocationContactsList method in
       * LocationBOImpl
       */
    @Override
    public List getLocationContactsList(String strLocationId, String strLanguage) throws RMDServiceException {
        LOG.debug("Begin LocationServiceImpl getLocationContactsList method");
        List arlContactLocation = null;
        try {
            arlContactLocation = objLocationBOIntf.getLocationContactsList(strLocationId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End LocationServiceImpl getLocationContactsList method");
        return arlContactLocation;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.LocationServiceIntf#
     * createLocation(com.ge.trans.rmd.services.cases.service.valueobjects.
     * CreateLocationServiceVO)
     *//*
       * This Method is used for call the createLocation method in
       * LocationBOImpl
       */
    @Override
    public String createLocation(CreateLocationServiceVO objCreateLocationServiceVO, String strEditCreateLocation)
            throws RMDServiceException {
        LOG.debug("Begin LocationServiceImpl createLocation method");
        String strViewID = null;
        try {
            strViewID = objLocationBOIntf.createLocation(objCreateLocationServiceVO, strEditCreateLocation);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objCreateLocationServiceVO.getStrLanguage());
        }
        LOG.debug("End LocationServiceImpl createLocation method");
        return strViewID;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.LocationServiceIntf#
     * loadStatusDropDown(java.lang.String)
     *//*
       * This Method is used for call the loadStatusDropDown method in
       * LocationBOImpl
       */
    @Override
    public List loadStatusDropDown(String strLanguage) throws RMDServiceException {
        LOG.debug("Begin LocationServiceImpl loadStatusDropDown method");
        List arlStatus = null;
        try {
            arlStatus = objLocationBOIntf.loadStatusDropDown(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End LocationServiceImpl loadStatusDropDown method");
        return arlStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.LocationServiceIntf#
     * loadTypeDropDown(java.lang.String)
     *//*
       * This Method is used for call the loadTypeDropDown method in
       * LocationBOImpl
       */
    @Override
    public List loadTypeDropDown(String strLanguage) throws RMDServiceException {
        LOG.debug("Begin LocationServiceImpl loadTypeDropDown method");
        List arlType = null;
        try {
            arlType = objLocationBOIntf.loadTypeDropDown(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End LocationServiceImpl loadTypeDropDown method");
        return arlType;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.LocationServiceIntf#
     * loadTimeZoneDropDown(java.lang.String)
     *//*
       * This Method is used for call the loadTimeZoneDropDown method in
       * LocationBOImpl
       */
    @Override
    public List loadTimeZoneDropDown(String strLanguage) throws RMDServiceException {
        LOG.debug("Begin LocationServiceImpl loadTimeZoneDropDown method");
        List arlTimeZone = null;
        try {
            arlTimeZone = objLocationBOIntf.loadTimeZoneDropDown(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End LocationServiceImpl loadTimeZoneDropDown method");
        return arlTimeZone;
    }
}

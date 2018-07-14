/**
 * ============================================================
 * File : LocationDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.dao.impl
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
package com.ge.trans.eoa.services.cases.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstLocationHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmAddressHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmContactInfoHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmCountryHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetTimeZoneHVO;
import com.ge.trans.eoa.services.cases.dao.intf.LocationDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CreateLocationServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocContResultServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocationResultServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocationServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

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
public class LocationDAOImpl extends BaseDAO implements LocationDAOIntf {

    private static final long serialVersionUID = -2867922336421427950L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(LocationDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#findLocation
     * (com.
     * ge.trans.rmd.services.cases.service.valueobjects.FindLocationServiceVO)
     *//**
       * This method is used for fetch the location details based on the search
       * criteria from database
       */
    @Override
    public List findLocation(FindLocationServiceVO objFindLocationServiceVO) throws RMDDAOException {
        List<FindLocationResultServiceVO> arlLocationResult = null;
        List arlResults = null;
        Session objHibernateSession = null;
        FindLocationResultServiceVO objFindLocationResultServiceVO = null;
        try {
            arlResults = new ArrayList();
            arlLocationResult = new ArrayList<FindLocationResultServiceVO>();
            objHibernateSession = getHibernateSession();
            StringBuilder query = new StringBuilder();
            query.append("SELECT TBS.OBJID,TBS.NAME,TBA.ADDRESS,TBA.CITY,TBA.STATE,TBA.ZIPCODE ");
            query.append("FROM TABLE_SITE TBS, TABLE_BUS_ORG TBO, TABLE_ADDRESS TBA ");
            query.append("WHERE TBS.PRIMARY2BUS_ORG = TBO.OBJID AND TBS.CUST_PRIMADDR2ADDRESS = TBA.OBJID ");
            if (null != objFindLocationServiceVO.getCustomerId()
                    && !objFindLocationServiceVO.getCustomerId().isEmpty()) {
                query.append("AND TBO.ORG_ID =:customerId");
            }
            Query locationQry = objHibernateSession.createSQLQuery(query.toString());
            if (null != objFindLocationServiceVO.getCustomerId()
                    && !objFindLocationServiceVO.getCustomerId().isEmpty()) {
                locationQry.setParameter(RMDCommonConstants.CUSTOMER_ID, objFindLocationServiceVO.getCustomerId());
            }
            arlResults = locationQry.list();
            if (null != arlResults && !arlResults.isEmpty()) {
                for (int i = 0; i < arlResults.size(); i++) {
                    Object[] locObj = (Object[]) arlResults.get(i);
                    objFindLocationResultServiceVO = new FindLocationResultServiceVO();
                    objFindLocationResultServiceVO.setStrLocationId(RMDCommonUtility.convertObjectToString(locObj[0]));
                    objFindLocationResultServiceVO
                            .setStrLocationName(RMDCommonUtility.convertObjectToString(locObj[1]));
                    objFindLocationResultServiceVO.setStrSiteAddress(RMDCommonUtility.convertObjectToString(locObj[2]));
                    objFindLocationResultServiceVO.setStrCity(RMDCommonUtility.convertObjectToString(locObj[3]));
                    objFindLocationResultServiceVO.setStrState(RMDCommonUtility.convertObjectToString(locObj[4]));
                    objFindLocationResultServiceVO.setStrZipCode(RMDCommonUtility.convertObjectToString(locObj[5]));
                    arlLocationResult.add(objFindLocationResultServiceVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFindLocationServiceVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);

            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFindLocationServiceVO.getStrLanguage()),
                    e, RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(objHibernateSession);
        }
        return arlLocationResult;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#checkLocationId
     * (com
     * .ge.trans.rmd.services.cases.service.valueobjects.CreateLocationServiceVO
     * )
     *//***
       * *This method is used for checking the location id values already exists
       */
    @Override
    public String checkLocationId(CreateLocationServiceVO objCreateLocationServiceVO) throws RMDDAOException {
        String strViewId = RMDServiceConstants.FAILURE;
        ArrayList arlLocation = null;
        Session objHibernateSession = null;
        Criteria locationIDCriteria = null;
        try {
            objHibernateSession = getHibernateSession(objCreateLocationServiceVO.getStrUserName());
            locationIDCriteria = objHibernateSession.createCriteria(GetAsstLocationHVO.class);
            if (null != objCreateLocationServiceVO.getStrLocationId()
                    && objCreateLocationServiceVO.getStrLocationId() != RMDCommonConstants.EMPTY_STRING) {
                locationIDCriteria.add(
                        Restrictions.eq(RMDCommonConstants.LOCATIONID, objCreateLocationServiceVO.getStrLocationId()));
            }
            arlLocation = (ArrayList) locationIDCriteria.list();
            if (null != arlLocation && !arlLocation.isEmpty()) {
                strViewId = RMDServiceConstants.LOCATIONID_ALREADY_EXIST;
            }
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            releaseSession(objHibernateSession);
            arlLocation = null;
        }
        return strViewId;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#createLocation
     * (com
     * .ge.trans.rmd.services.cases.service.valueobjects.CreateLocationServiceVO
     * )
     *//* This method is used for save the location details into the database */
    @Override
    public String createLocation(CreateLocationServiceVO objCreateLocationServiceVO) throws RMDDAOException {
        String strViewID = null;
        Session objHibernateSession = null;
        GetAsstLocationHVO objGetAsstLocationHVO = null;
        GetCmCountryHVO objGetCmCountryHVO = null;
        GetTimeZoneHVO objGetCmTimeZoneHVO = null;
        GetSysLookupHVO objGetSysLookupHVO = null;
        GetCmAddressHVO objGetCmAddressHVO = null;
        List initialRuleList = null;
        try {
            objHibernateSession = getHibernateSession(objCreateLocationServiceVO.getStrUserName());
            objGetAsstLocationHVO = new GetAsstLocationHVO();
            if (objCreateLocationServiceVO != null) {
                if (null != objCreateLocationServiceVO.getStrLocationId() && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrLocationId())) {
                    objGetAsstLocationHVO.setLocationId(objCreateLocationServiceVO.getStrLocationId());
                }
                if (null != objCreateLocationServiceVO.getStrLocationName() && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrLocationName())) {
                    objGetAsstLocationHVO.setName(objCreateLocationServiceVO.getStrLocationName());
                }
                /***************************************************************
                 * *This code is used for insert type value into
                 * (GET_ASST_LOCATION)location table (fetch the sequence id from
                 * syslookup table and insert that value into
                 * (GET_ASST_LOCATION)location table)
                 **************************************************************/
                if ((objCreateLocationServiceVO.getStrLocationType() != null) && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrLocationType())) {
                    objGetSysLookupHVO = getLookupHVO(RMDServiceConstants.LOCATION_TYPE,
                            objCreateLocationServiceVO.getStrLocationType(),
                            objCreateLocationServiceVO.getStrLanguage());
                    if (null != objGetSysLookupHVO) {
                        objGetAsstLocationHVO.setGetSysLookup(objGetSysLookupHVO);
                    }
                }
                /***************************************************************
                 * *This code is used for insert type value into
                 * (GET_ASST_LOCATION)location table (fetch the sequence id from
                 * syslookup table and insert that value into
                 * (GET_ASST_LOCATION)location table)
                 **************************************************************/
                if ((objCreateLocationServiceVO.getStrStatus() != null) && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrStatus())) {
                    objGetSysLookupHVO = getLookupHVO(RMDServiceConstants.TYPE_STATUS,
                            objCreateLocationServiceVO.getStrStatus(), objCreateLocationServiceVO.getStrLanguage());
                    if (null != objGetSysLookupHVO) {
                        objGetAsstLocationHVO.setStatus(objGetSysLookupHVO.getGetSysLookupSeqId());
                    }
                }
                /** *Country table link** */
                objGetCmAddressHVO = new GetCmAddressHVO();
                objGetCmCountryHVO = new GetCmCountryHVO();
                if (null != objCreateLocationServiceVO.getStrCountry() && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrCountry())) {
                    objGetCmCountryHVO.setName(objCreateLocationServiceVO.getStrCountry());
                }
                objGetCmCountryHVO.setStrLanguage(objCreateLocationServiceVO.getStrLanguage());
                objHibernateSession.save(objGetCmCountryHVO);
                objGetCmAddressHVO.setGetCmCountry(objGetCmCountryHVO);
                /** *TimeZone table link** */
                if ((objCreateLocationServiceVO.getStrTimeZone() != null) && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrTimeZone())) {
                    Criteria criteria = objHibernateSession.createCriteria(GetTimeZoneHVO.class);
                    criteria.add(Restrictions.eq(RMDServiceConstants.TIME_ZONE_SEQUENCEID,
                            Long.valueOf(objCreateLocationServiceVO.getStrTimeZone())));
                    initialRuleList = criteria.list();
                    if (null != initialRuleList && !initialRuleList.isEmpty()) {
                        int iInitialRuleListSize = initialRuleList.size();
                        for (int i = 0; i < iInitialRuleListSize; i++) {
                            objGetCmTimeZoneHVO = (GetTimeZoneHVO) initialRuleList.get(i);
                        }
                    }
                    objGetCmAddressHVO.setGetCmTimeZone(objGetCmTimeZoneHVO);
                }
                /** *ContactInfo table link** */
                if (null != objCreateLocationServiceVO.getStrSiteAddress1() && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrSiteAddress1())) {
                    objGetCmAddressHVO.setAddress1(objCreateLocationServiceVO.getStrSiteAddress1());
                }
                if (null != objCreateLocationServiceVO.getStrSiteAddress2() && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrSiteAddress2())) {
                    objGetCmAddressHVO.setAddress2(objCreateLocationServiceVO.getStrSiteAddress2());
                }
                if (null != objCreateLocationServiceVO.getStrCity()
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCreateLocationServiceVO.getStrCity())) {
                    objGetCmAddressHVO.setCity(objCreateLocationServiceVO.getStrCity());
                }
                if (null != objCreateLocationServiceVO.getStrState() && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrState())) {
                    objGetCmAddressHVO.setStateProvince(objCreateLocationServiceVO.getStrState());
                }
                if (null != objCreateLocationServiceVO.getStrZipCode() && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrZipCode())) {
                    objGetCmAddressHVO.setZip(objCreateLocationServiceVO.getStrZipCode());
                }
                if (null != objCreateLocationServiceVO.getStrPhone() && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objCreateLocationServiceVO.getStrPhone())) {
                    objGetCmAddressHVO.setPhone(objCreateLocationServiceVO.getStrPhone());
                }
                objGetCmAddressHVO.setStrLanguage(objCreateLocationServiceVO.getStrLanguage());
                objHibernateSession.save(objGetCmAddressHVO);
                objGetAsstLocationHVO.setStrLanguage(objCreateLocationServiceVO.getStrLanguage());
                objGetAsstLocationHVO.setGetCmAddress(objGetCmAddressHVO);
                objHibernateSession.save(objGetAsstLocationHVO);
                objHibernateSession.flush();
            }
            strViewID = RMDServiceConstants.CREATE_LOCATION;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objCreateLocationServiceVO.getStrLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objCreateLocationServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return strViewID;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#
     * loadStatusDropDown (java.lang.String)
     *//***********************************************************************
       * This method is used to fetch the Status DropDown values from database
       **********************************************************************/
    @Override
    public List loadStatusDropDown(String strLanguage) throws RMDDAOException {
        List arlResults = null;
        Session objHibernateSession = null;
        try {
            arlResults = getLookupListValues(RMDServiceConstants.TYPE_STATUS, strLanguage);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_LOCATION_INITIAL_STATUS_DROPDOWN_LOAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_LOCATION_INITIAL_STATUS_DROPDOWN_LOAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return arlResults;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#loadTypeDropDown
     * (java.lang.String)
     *//***********************************************************************
       * This method is used to fetch the Type DropDown values from database
       **********************************************************************/
    @Override
    public List loadTypeDropDown(String strLanguage) throws RMDDAOException {
        List arlTypeResults = null;
        Session objHibernateSession = null;
        try {
            arlTypeResults = getLookupListValues(RMDServiceConstants.LOCATION_TYPE, strLanguage);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_LOCATION_INITIAL_TYPE_DROPDOWN_LOAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_LOCATION_INITIAL_TYPE_DROPDOWN_LOAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return arlTypeResults;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#
     * loadTimeZoneDropDown (java.lang.String)
     *//***********************************************************************
       * This method is used to fetch the TimeZone DropDown values from database
       **********************************************************************/
    @Override
    public List loadTimeZoneDropDown(String strLanguage) throws RMDDAOException {
        List initialRuleList = null;
        List arlTimeZone = null;
        ElementVO objElementVO = null;
        Session objHibernateSession = null;
        try {
            arlTimeZone = new ArrayList();
            objHibernateSession = getHibernateSession();
            Criteria criteria = objHibernateSession.createCriteria(GetTimeZoneHVO.class);
            criteria.addOrder(Order.asc(RMDCommonConstants.FULLNAME));
            criteria.add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION, strLanguage));
            initialRuleList = criteria.list();
            if (null != initialRuleList && !initialRuleList.isEmpty()) {
                int iInitialRuleListSize = initialRuleList.size();
                for (int i = 0; i < iInitialRuleListSize; i++) {
                    GetTimeZoneHVO objGetCmTimeZoneHVO = (GetTimeZoneHVO) initialRuleList.get(i);
                    objElementVO = new ElementVO();
                    objElementVO.setId(objGetCmTimeZoneHVO.getGetCmTimeZoneSeqId().toString());
                    objElementVO.setName(objGetCmTimeZoneHVO.getFullName());
                    arlTimeZone.add(objElementVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_LOCATION_TIMEZONE_DROPDOWN_LOAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_LOCATION_TIMEZONE_DROPDOWN_LOAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
            initialRuleList = null;
        }
        return arlTimeZone;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#
     * getEditLocationDetails(java.lang.String, java.lang.String)
     */
    @Override
    public List getEditLocationDetails(String strLocationId, String strLanguage) throws RMDDAOException {
        List arlEditResults = null;
        List arlEditLocationResult = null;
        int iEditCount = 0;
        Session objHibernateSession = null;
        Criteria locationCriteria = null;
        GetAsstLocationHVO objGetAsstLocationHVO = null;
        GetSysLookupHVO objGetSysLookupHVO = null;
        FindLocationResultServiceVO objFindLocationResultServiceVO = null;
        try {
            arlEditResults = new ArrayList();
            arlEditLocationResult = new ArrayList();
            objHibernateSession = getHibernateSession();
            locationCriteria = objHibernateSession.createCriteria(GetAsstLocationHVO.class)
                    .setFetchMode(RMDServiceConstants.GET_SYSLOOKUP, FetchMode.JOIN)
                    .setFetchMode(RMDServiceConstants.ADDRESS, FetchMode.JOIN)
                    .createAlias(RMDServiceConstants.GET_SYSLOOKUP, RMDServiceConstants.ALIAS_SYS_LOOKUP)
                    .createAlias(RMDServiceConstants.ADDRESS, RMDServiceConstants.ALIAS_ADDRESS)
                    .setFetchMode(RMDServiceConstants.ADDRESS_COUNTRY, FetchMode.JOIN)
                    .setFetchMode(RMDServiceConstants.ADDRESS_TIMEZONE, FetchMode.JOIN)
                    .createAlias(RMDServiceConstants.ADDRESS_COUNTRY, RMDServiceConstants.ALIAS_COUNTRY)
                    .createAlias(RMDServiceConstants.ADDRESS_TIMEZONE, RMDServiceConstants.ALIAS_TIMEZONE);
            if (!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strLocationId) && strLocationId != null) {
                locationCriteria.add(Restrictions.eq("locationId", strLocationId));
            }
            arlEditResults = locationCriteria.list();
            if (null != arlEditResults) {
                iEditCount = arlEditResults.size();
                for (int i = 0; i < iEditCount; i++) {
                    objGetAsstLocationHVO = (GetAsstLocationHVO) arlEditResults.get(i);
                    objFindLocationResultServiceVO = new FindLocationResultServiceVO();
                    if (objGetAsstLocationHVO.getLocationId() != null
                            && objGetAsstLocationHVO.getLocationId() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO.setStrLocationId(objGetAsstLocationHVO.getLocationId());
                    }
                    if (objGetAsstLocationHVO.getName() != null
                            && objGetAsstLocationHVO.getName() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO.setStrLocationName(objGetAsstLocationHVO.getName());
                    }
                    if (null != objGetAsstLocationHVO.getGetCmAddress().getAddress1() && objGetAsstLocationHVO
                            .getGetCmAddress().getAddress1() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO
                                .setStrSiteAddress1(objGetAsstLocationHVO.getGetCmAddress().getAddress1());
                    }
                    if (null != objGetAsstLocationHVO.getGetCmAddress().getAddress2() && objGetAsstLocationHVO
                            .getGetCmAddress().getAddress2() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO
                                .setStrSiteAddress2(objGetAsstLocationHVO.getGetCmAddress().getAddress2());
                    }
                    if (objGetAsstLocationHVO.getGetCmAddress().getCity() != null
                            && objGetAsstLocationHVO.getGetCmAddress().getCity() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO.setStrCity(objGetAsstLocationHVO.getGetCmAddress().getCity());
                    }
                    if (objGetAsstLocationHVO.getGetCmAddress().getStateProvince() != null && objGetAsstLocationHVO
                            .getGetCmAddress().getStateProvince() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO
                                .setStrState(objGetAsstLocationHVO.getGetCmAddress().getStateProvince());
                    }
                    if (objGetAsstLocationHVO.getGetCmAddress().getPhone() != null
                            && objGetAsstLocationHVO.getGetCmAddress().getPhone() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO.setStrPhone(objGetAsstLocationHVO.getGetCmAddress().getPhone());
                    }
                    if (objGetAsstLocationHVO.getGetCmAddress().getZip() != null
                            && objGetAsstLocationHVO.getGetCmAddress().getZip() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO.setStrZipCode(objGetAsstLocationHVO.getGetCmAddress().getZip());
                    }
                    if (objGetAsstLocationHVO.getGetCmAddress().getGetCmTimeZone().getGetCmTimeZoneSeqId() != null) {
                        objFindLocationResultServiceVO.setStrTimeZone(objGetAsstLocationHVO.getGetCmAddress()
                                .getGetCmTimeZone().getGetCmTimeZoneSeqId().toString());
                    }
                    if (objGetAsstLocationHVO.getGetCmAddress().getGetCmCountry().getName() != null
                            && objGetAsstLocationHVO.getGetCmAddress().getGetCmCountry()
                                    .getName() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO
                                .setStrCountry(objGetAsstLocationHVO.getGetCmAddress().getGetCmCountry().getName());
                    }
                    if (objGetAsstLocationHVO.getGetSysLookup().getLookValue() != null && objGetAsstLocationHVO
                            .getGetSysLookup().getLookValue() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocationResultServiceVO
                                .setStrLocationType(objGetAsstLocationHVO.getGetSysLookup().getLookValue());
                    }
                    if (null != objGetAsstLocationHVO.getStatus()) {
                        objGetSysLookupHVO = getLookupHVOWithID(RMDServiceConstants.TYPE_STATUS,
                                Long.valueOf(objGetAsstLocationHVO.getStatus()),
                                objGetAsstLocationHVO.getStrLanguage());
                        if (null != objGetSysLookupHVO) {
                            objFindLocationResultServiceVO.setStrStatus(objGetSysLookupHVO.getLookValue());
                        }
                    }
                    arlEditLocationResult.add(objFindLocationResultServiceVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EDIT_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EDIT_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
            arlEditResults = null;
        }
        return arlEditLocationResult;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#
     * getLocationContactsList(java.lang.String)
     *//**
       * This method is used for fetching the location details for the given
       * input
       */
    @Override
    public List getLocationContactsList(String strLocationId, String strLanguage) throws RMDDAOException {
        List arlPhone = null;
        List arlContactList = null;
        Session objHibernateSession = null;
        Criteria phoneCriteria = null;
        GetCmContactInfoHVO objGetCmContactInfoHVO = null;
        FindLocContResultServiceVO objFindLocContResultServiceVO = null;
        try {
            arlPhone = new ArrayList();
            arlContactList = new ArrayList();
            objHibernateSession = getHibernateSession();
            phoneCriteria = objHibernateSession.createCriteria(GetCmContactInfoHVO.class)
                    .setFetchMode("getAsstLocation", FetchMode.JOIN).createAlias("getAsstLocation", "asstLocation");
            if (!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strLocationId) && strLocationId != null) {
                phoneCriteria.add(Restrictions.eq("asstLocation.locationId", strLocationId));
            }
            arlPhone = phoneCriteria.list();
            if (null != arlPhone && !arlPhone.isEmpty()) {
                int intPhone = arlPhone.size();
                for (int j = 0; j < intPhone; j++) {
                    objGetCmContactInfoHVO = (GetCmContactInfoHVO) arlPhone.get(j);
                    objFindLocContResultServiceVO = new FindLocContResultServiceVO();
                    if (objGetCmContactInfoHVO.getGetCmContactInfoSeqId() != null) {
                        objFindLocContResultServiceVO
                                .setContactInfoSeqId(objGetCmContactInfoHVO.getGetCmContactInfoSeqId());
                    }
                    if (objGetCmContactInfoHVO.getPhone() != null
                            && objGetCmContactInfoHVO.getPhone() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocContResultServiceVO.setStrPhone(objGetCmContactInfoHVO.getPhone());
                    }
                    if (objGetCmContactInfoHVO.getFirstName() != null
                            && objGetCmContactInfoHVO.getFirstName() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocContResultServiceVO.setStrFirstName(objGetCmContactInfoHVO.getFirstName());
                    }
                    if (objGetCmContactInfoHVO.getLastName() != null
                            && objGetCmContactInfoHVO.getLastName() != RMDCommonConstants.EMPTY_STRING) {
                        objFindLocContResultServiceVO.setStrLastName(objGetCmContactInfoHVO.getLastName());
                    }
                    arlContactList.add(objFindLocContResultServiceVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_CONTACT_FOR_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_CONTACT_FOR_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
            arlPhone = null;
        }
        return arlContactList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.LocationDAOIntf#updateLocation
     * (com
     * .ge.trans.rmd.services.cases.service.valueobjects.CreateLocationServiceVO
     * )
     *//** This method is used for update the location details** */
    @Override
    public String updateLocation(CreateLocationServiceVO objCreateLocationServiceVO) throws RMDDAOException {
        String strViewID = null;
        List arlLocationResult = null;
        Session objHibernateSession = null;
        Criteria locationCriteria = null;
        GetAsstLocationHVO objGetAsstLocationHVO = null;
        GetCmCountryHVO objGetCmCountryHVO = null;
        GetTimeZoneHVO objGetCmTimeZoneHVO = null;
        GetSysLookupHVO objGetSysLookupHVO = null;
        GetCmAddressHVO objGetCmAddressHVO = null;
        List initialRuleList = null;
        try {
            objHibernateSession = getHibernateSession(objCreateLocationServiceVO.getStrUserName());
            locationCriteria = objHibernateSession.createCriteria(GetAsstLocationHVO.class)
                    .setFetchMode(RMDServiceConstants.GET_SYSLOOKUP, FetchMode.JOIN)
                    .setFetchMode(RMDServiceConstants.ADDRESS, FetchMode.JOIN)
                    .createAlias(RMDServiceConstants.GET_SYSLOOKUP, RMDServiceConstants.ALIAS_SYS_LOOKUP)
                    .createAlias(RMDServiceConstants.ADDRESS, RMDServiceConstants.ALIAS_ADDRESS)
                    .setFetchMode(RMDServiceConstants.ADDRESS_COUNTRY, FetchMode.JOIN)
                    .setFetchMode(RMDServiceConstants.ADDRESS_TIMEZONE, FetchMode.JOIN)
                    .createAlias(RMDServiceConstants.ADDRESS_COUNTRY, RMDServiceConstants.ALIAS_COUNTRY)
                    .createAlias(RMDServiceConstants.ADDRESS_TIMEZONE, RMDServiceConstants.ALIAS_TIMEZONE);
            if (!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCreateLocationServiceVO.getStrLocationId())) {
                locationCriteria.add(Restrictions.eq("locationId", objCreateLocationServiceVO.getStrLocationId()));
            }
            arlLocationResult = locationCriteria.list();
            if (null != arlLocationResult && !arlLocationResult.isEmpty()) {
                objGetAsstLocationHVO = (GetAsstLocationHVO) arlLocationResult.get(0);
                if (objCreateLocationServiceVO != null) {
                    objGetAsstLocationHVO.setLocationId(objCreateLocationServiceVO.getStrLocationId());
                    objGetAsstLocationHVO.setName(objCreateLocationServiceVO.getStrLocationName());
                    /***********************************************************
                     * *This code is used for insert type value into
                     * (GET_ASST_LOCATION)location table (fetch the sequence id
                     * from syslookup table and insert that value into
                     * (GET_ASST_LOCATION)location table)
                     **********************************************************/
                    if ((objCreateLocationServiceVO.getStrLocationType() != null) && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objCreateLocationServiceVO.getStrLocationType())) {
                        objGetSysLookupHVO = getLookupHVO(RMDServiceConstants.LOCATION_TYPE,
                                objCreateLocationServiceVO.getStrLocationType(),
                                objCreateLocationServiceVO.getStrLanguage());
                        if (null != objGetSysLookupHVO) {
                            objGetAsstLocationHVO.setGetSysLookup(objGetSysLookupHVO);
                        }
                    }
                    /***********************************************************
                     * *This code is used for insert type value into
                     * (GET_ASST_LOCATION)location table (fetch the sequence id
                     * from syslookup table and insert that value into
                     * (GET_ASST_LOCATION)location table)
                     **********************************************************/
                    if ((objCreateLocationServiceVO.getStrStatus() != null) && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objCreateLocationServiceVO.getStrStatus())) {
                        objGetSysLookupHVO = getLookupHVO(RMDServiceConstants.TYPE_STATUS,
                                objCreateLocationServiceVO.getStrStatus(), objCreateLocationServiceVO.getStrLanguage());
                        if (null != objGetSysLookupHVO) {
                            objGetAsstLocationHVO.setStatus(objGetSysLookupHVO.getGetSysLookupSeqId());
                        }
                    }
                    /** *Country table link** */
                    objGetCmCountryHVO = objGetAsstLocationHVO.getGetCmAddress().getGetCmCountry();
                    objGetCmCountryHVO.setName(objCreateLocationServiceVO.getStrCountry());
                    objGetCmCountryHVO.setStrLanguage(objCreateLocationServiceVO.getStrLanguage());
                    objHibernateSession.saveOrUpdate(objGetCmCountryHVO);
                    objGetAsstLocationHVO.getGetCmAddress().setGetCmCountry(objGetCmCountryHVO);
                    /** *TimeZone table link** */
                    objGetCmTimeZoneHVO = objGetAsstLocationHVO.getGetCmAddress().getGetCmTimeZone();
                    if ((objCreateLocationServiceVO.getStrTimeZone() != null) && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objCreateLocationServiceVO.getStrTimeZone())) {
                        Criteria criteria = objHibernateSession.createCriteria(GetTimeZoneHVO.class);

                        criteria.add(Restrictions.eq(RMDServiceConstants.TIME_ZONE_SEQUENCEID,
                                Long.valueOf(objCreateLocationServiceVO.getStrTimeZone())));
                        initialRuleList = criteria.list();
                        if (null != initialRuleList && !initialRuleList.isEmpty()) {
                            int iInitialRuleListSize = initialRuleList.size();
                            for (int i = 0; i < iInitialRuleListSize; i++) {
                                objGetCmTimeZoneHVO = (GetTimeZoneHVO) initialRuleList.get(i);
                            }
                        }
                    }
                    objHibernateSession.saveOrUpdate(objGetCmTimeZoneHVO);
                    objGetAsstLocationHVO.getGetCmAddress().setGetCmTimeZone(objGetCmTimeZoneHVO);
                    objGetCmAddressHVO = objGetAsstLocationHVO.getGetCmAddress();
                    objGetCmAddressHVO.setAddress1(objCreateLocationServiceVO.getStrSiteAddress1());
                    objGetCmAddressHVO.setAddress2(objCreateLocationServiceVO.getStrSiteAddress2());
                    objGetCmAddressHVO.setCity(objCreateLocationServiceVO.getStrCity());
                    objGetCmAddressHVO.setStateProvince(objCreateLocationServiceVO.getStrState());
                    objGetCmAddressHVO.setZip(objCreateLocationServiceVO.getStrZipCode());
                    objGetCmAddressHVO.setPhone(objCreateLocationServiceVO.getStrPhone());
                    objGetCmAddressHVO.setStrLanguage(objCreateLocationServiceVO.getStrLanguage());
                    objHibernateSession.saveOrUpdate(objGetCmAddressHVO);
                    objGetAsstLocationHVO.setStrLanguage(objCreateLocationServiceVO.getStrLanguage());
                    objGetAsstLocationHVO.setGetCmAddress(objGetCmAddressHVO);
                    objHibernateSession.saveOrUpdate(objGetAsstLocationHVO);
                    objHibernateSession.flush();
                }
            }
            strViewID = RMDServiceConstants.SITE_DETAILS;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objCreateLocationServiceVO.getStrLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objCreateLocationServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return strViewID;
    }
}

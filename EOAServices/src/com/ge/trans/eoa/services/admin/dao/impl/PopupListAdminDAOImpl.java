/**
 * =================================================================
 * Classification: GE Confidential
 * File : PopupListAdminDAOImpl.java
 * Description : DAO Impl for Popup Admin List
 * 
 * Package : com.ge.trans.rmd.services.admin.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 13, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * =================================================================
 */
package com.ge.trans.eoa.services.admin.dao.impl;

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.EMPTY_STRING;
import static com.ge.trans.rmd.common.constants.RMDCommonConstants.UPDATE_SUCCESS;
import static com.ge.trans.rmd.common.constants.RMDCommonConstants.DateConstants.MMddyyyyHHmmssa;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.GetSysParameterVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.LookupSearchServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupMultilangVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.ErrorDetail;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupMultilangHVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : DAO Impl for Popup Admin List
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class PopupListAdminDAOImpl extends BaseDAO implements PopupListAdminDAOIntf {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = -2696947735607447252L;

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * getMaxListValueSortOrder
     * (com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public Long getMaxListValueSortOrder(final GetSysLookupVO sysLookupVO) {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "SELECT MAX(SORTORDER) FROM GETSYSLOOKUPHVO LU WHERE LU.LISTNAME = :listName";
            final Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.LIST_NAME, sysLookupVO.getListName());
            return (Long) query.uniqueResult();
        } catch (Exception ex) {
            throw new RMDDAOException("DAO_EXCEPTION_POPUP_ADMIN", ex);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf
     * #deletePopupListValue
     * (com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */

    @Override
    public void deletePopupListValue(final GetSysLookupVO sysLookupVO) {
        Session session = null;
        List<GetSysLookupHVO> lookupList;
        String strListName;
        String queryString;
        Query query;
        try {
            session = getHibernateSession(sysLookupVO.getStrUserName());
            Codec ORACLE_CODEC = new OracleCodec();
            strListName = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, sysLookupVO.getListName());
            if (RMDCommonUtility.isNull(strListName)) {
                queryString = "FROM GETSYSLOOKUPHVO LU WHERE LU.GETSYSLOOKUPSEQID = :lookupId";
                query = session.createQuery(queryString);
                query.setParameter("lookupId", sysLookupVO.getGetSysLookupSeqId());
                lookupList = query.list();
                if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
                    GetSysLookupHVO lookupHVO = lookupList.get(0);
                    strListName = lookupHVO.getListName();
                }
            }

            queryString = "DELETE FROM GETSYSLOOKUPMULTILANGHVO LU WHERE LU.GETSYSLOOKUP.GETSYSLOOKUPSEQID = :lookupSeqId";
            query = session.createQuery(queryString);
            query.setLong("lookupSeqId", sysLookupVO.getGetSysLookupSeqId());
            query.executeUpdate();

            queryString = "DELETE FROM GETSYSLOOKUPHVO LU WHERE LU.GETSYSLOOKUPSEQID = :lookupSeqId";
            query = session.createQuery(queryString);
            query.setLong("lookupSeqId", sysLookupVO.getGetSysLookupSeqId());
            query.executeUpdate();
            session.flush();

            queryString = "FROM GETSYSLOOKUPHVO LU WHERE LU.LISTNAME = :listName ORDER BY LU.SORTORDER";
            query = session.createQuery(queryString);
            query.setString("listName", strListName);
            // Get the list based on the search criteria(if any)
            lookupList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
                Long sortOrder = 1l;
                for (GetSysLookupHVO lookupHVO : lookupList) {
                    lookupHVO.setSortOrder(sortOrder++);
                    session.update(lookupHVO);
                    session.flush();
                }
            }
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc) *
     * @see com.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf
     * #getPopupList (com.ge.trans.rmd.services.admin.service.valueobjects.
     * LookupSearchServiceVO )
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<GetSysLookupVO> getPopupList(final LookupSearchServiceVO objLookupServiceSearch) {
        Session session = null;
        try {
            session = getHibernateSession();
            final Criteria criteria = session.createCriteria(GetSysLookupHVO.class);
            criteria.add(Restrictions.eq(RMDCommonConstants.ISEDITABLE, RMDCommonConstants.LETTER_Y));
            final ProjectionList projList = Projections.projectionList();
            projList.add(Projections.groupProperty(RMDCommonConstants.LIST_NAME));
            projList.add(Projections.groupProperty(RMDCommonConstants.LIST_DESC));
            criteria.setProjection(projList);
            if (StringUtils.isNotBlank(objLookupServiceSearch.getStrValue())) {
                criteria.add(Restrictions.ilike(objLookupServiceSearch.getStrSearchBy(),
                        objLookupServiceSearch.getStrValue(), MatchMode.ANYWHERE));
            }
            criteria.addOrder(Order.asc(RMDCommonConstants.LIST_NAME).ignoreCase());
            // Get the list based on the search criteria(if any)
            List lookupList = criteria.list();
            List<GetSysLookupVO> searchResults = null;
            if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
                // Convert the HVOs to service VOs
                GetSysLookupVO lookupVO = null;
                searchResults = new ArrayList<GetSysLookupVO>();
                for (Object lookupValues : lookupList) {
                    final Object[] columns = (Object[]) lookupValues;
                    lookupVO = new GetSysLookupVO();
                    lookupVO.setListName((String) columns[0]);
                    lookupVO.setListDescription((String) columns[1]);
                    searchResults.add(lookupVO);
                }
            }
            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objLookupServiceSearch.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objLookupServiceSearch.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * getPopupListValues(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     * fetches lookup value from GETS_RMD.GETS_RMD_LOOKUP table
     */
    // @Cacheable(value = "lookUpCache", key = "#lookupString")
    @Override
    public List<GetSysLookupVO> getPopupListValues(final String lookupString) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            final String queryString = "select OBJID, LIST_NAME, LOOK_VALUE, LIST_DESCRIPTION, LOOK_STATE, SORT_ORDER,LOOK_VALUE_DESC FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME=:listName ORDER BY SORT_ORDER ASC";
            Query query = session.createSQLQuery(queryString);
            query.setParameter(RMDCommonConstants.LIST_NAME, lookupString);
            query.setFetchSize(10);
            // Get the list based on the search criteria(if any)
            List<Object> lookupList = query.list();
            List<GetSysLookupVO> searchResults = null;

            if (lookupList != null && !lookupList.isEmpty()) {
                GetSysLookupVO objSysLookupVO = null;
                searchResults = new ArrayList<GetSysLookupVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    objSysLookupVO = new GetSysLookupVO();
                    final Object[] lookupRecord = (Object[]) iter.next();
                    objSysLookupVO.setGetSysLookupSeqId(RMDCommonUtility.convertObjectToLong(lookupRecord[0]));
                    objSysLookupVO.setListName(ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(lookupRecord[1])));
                    objSysLookupVO.setLookValue(ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(lookupRecord[2])));
                    objSysLookupVO.setListDescription(ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(lookupRecord[3])));
                    objSysLookupVO.setLookState(RMDCommonUtility.convertObjectToString(lookupRecord[4]));
                    objSysLookupVO.setSortOrder(RMDCommonUtility.convertObjectToLong(lookupRecord[5]));
                    objSysLookupVO.setLookValueDesc(ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(lookupRecord[6])));
                    searchResults.add(objSysLookupVO);
                }
            }

            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * @param lookupString
     * @return
     * @throws RMDDAOException
     */
    @Override
    public List<GetSysLookupVO> getHCLookUpValues(final String lookupString) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            final String queryString = "select OBJID, LIST_NAME, LOOK_VALUE, LIST_DESCRIPTION, LOOK_STATE, SORT_ORDER FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME=:listName ORDER BY SORT_ORDER ASC";
            Query query = session.createSQLQuery(queryString);
            query.setParameter(RMDCommonConstants.LIST_NAME, lookupString);
            query.setFetchSize(10);
            // Get the list based on the search criteria(if any)
            List<Object> lookupList = query.list();
            List<GetSysLookupVO> searchResults = null;

            if (lookupList != null && !lookupList.isEmpty()) {
                GetSysLookupVO objSysLookupVO = null;
                searchResults = new ArrayList<GetSysLookupVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    objSysLookupVO = new GetSysLookupVO();
                    final Object[] lookupRecord = (Object[]) iter.next();
                    objSysLookupVO.setGetSysLookupSeqId(RMDCommonUtility.convertObjectToLong(lookupRecord[0]));
                    objSysLookupVO.setListName(RMDCommonUtility.convertObjectToString(lookupRecord[1]));
                    objSysLookupVO.setLookValue(RMDCommonUtility.convertObjectToString(lookupRecord[2]));
                    objSysLookupVO.setListDescription(RMDCommonUtility.convertObjectToString(lookupRecord[3]));
                    objSysLookupVO.setLookState(RMDCommonUtility.convertObjectToString(lookupRecord[4]));
                    objSysLookupVO.setSortOrder(RMDCommonUtility.convertObjectToLong(lookupRecord[5]));
                    searchResults.add(objSysLookupVO);
                }
            }

            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * getPopupListMultilangValues
     * (com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public Set<GetSysLookupMultilangVO> getPopupListMultilangValues(final GetSysLookupVO sysLookupVO)
            throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "FROM GETSYSLOOKUPMULTILANGHVO MULTILANG WHERE MULTILANG.GETSYSLOOKUP.GETSYSLOOKUPSEQID = :lookupSeqId ORDER BY MULTILANG.LANGUAGE";
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.LOOKUPSEQID, sysLookupVO.getGetSysLookupSeqId());
            // Get the list based on the search criteria(if any)
            List<GetSysLookupMultilangHVO> lookupMultilangList = query.list();
            Set<GetSysLookupMultilangVO> searchResults = null;
            if (RMDCommonUtility.isCollectionNotEmpty(lookupMultilangList)) {
                // Convert the HVOs to service VOs
                GetSysLookupMultilangVO objSysLookupMultilangVO = null;
                searchResults = new TreeSet<GetSysLookupMultilangVO>();
                for (GetSysLookupMultilangHVO objSysLookupMultilangHVO : lookupMultilangList) {
                    objSysLookupMultilangVO = new GetSysLookupMultilangVO();
                    BeanUtils.copyProperties(objSysLookupMultilangVO, objSysLookupMultilangHVO);
                    searchResults.add(objSysLookupMultilangVO);
                }
            }
            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * savePopupList (com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public int savePopupList(final GetSysLookupVO sysLookupVO) {
        Session session = null;
        try {
            session = getHibernateSession(sysLookupVO.getStrUserName());
            String queryString = "UPDATE GETSYSLOOKUPHVO LU SET LU.LISTDESCRIPTION = :descriptionNew, "
                    + " LASTUPDATEDBY = :lastUpdatedBy, LASTUPDATEDDATE = :lastUpdatedDate WHERE LU.LISTNAME = :listName";
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.DESCRIPTIONNEW, sysLookupVO.getListDescription());
            query.setParameter(RMDCommonConstants.LAST_UPDATED, sysLookupVO.getStrUserName());
            query.setParameter(RMDCommonConstants.LAST_UPDATED_DATE, RMDCommonUtility.getGMTDateTime(MMddyyyyHHmmssa));
            query.setParameter(RMDCommonConstants.LIST_NAME, sysLookupVO.getListName());
            return query.executeUpdate();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * savePopupListValue(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public Serializable savePopupListValue(final GetSysLookupVO sysLookupVO) {
        Session session = null;
        try {
            session = getHibernateSession(sysLookupVO.getStrUserName());
            GetSysLookupHVO sysLookupHVO = null;
            if (sysLookupVO.getGetSysLookupSeqId() != null) {
                sysLookupHVO = (GetSysLookupHVO) session.get(GetSysLookupHVO.class, sysLookupVO.getGetSysLookupSeqId());
            }
            if (null == sysLookupHVO)
                sysLookupHVO = new GetSysLookupHVO();
            sysLookupHVO.setListName(sysLookupVO.getListName());
            sysLookupHVO.setListDescription(sysLookupVO.getListDescription());
            sysLookupHVO.setIsEditable(sysLookupVO.getIsEditable());
            sysLookupHVO.setLookValue(sysLookupVO.getLookValue());
            sysLookupHVO.setLookState(sysLookupVO.getLookState());
            sysLookupHVO.setSortOrder(sysLookupVO.getSortOrder());
            session.saveOrUpdate(sysLookupHVO);
            session.flush();
            GetSysLookupVO onjSysLookupVO = new GetSysLookupVO();
            BeanUtils.copyProperties(onjSysLookupVO, sysLookupHVO);
            return onjSysLookupVO;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * savePopupListMultiLangValues
     * (com.ge.trans.rmd.common.valueobjects.GetSysLookupMultilangVO)
     */
    @Override
    public int savePopupListMultiLangValues(final GetSysLookupMultilangVO sysLookupMultilangVO) {
        Session session = null;
        try {
            session = getHibernateSession(sysLookupMultilangVO.getStrUserName());
            GetSysLookupMultilangHVO sysLookupMultilangHVO = null;
            if (sysLookupMultilangVO.getGetSysLookupMultilSeqId() != null) {
                sysLookupMultilangHVO = (GetSysLookupMultilangHVO) session.get(GetSysLookupMultilangHVO.class,
                        sysLookupMultilangVO.getGetSysLookupMultilSeqId());
            }
            if (null == sysLookupMultilangHVO)
                sysLookupMultilangHVO = new GetSysLookupMultilangHVO();
            GetSysLookupHVO sysLookupHVO = (GetSysLookupHVO) session.get(GetSysLookupHVO.class,
                    sysLookupMultilangVO.getGetSysLookupSeqId());
            sysLookupMultilangHVO.setGetSysLookup(sysLookupHVO);
            sysLookupMultilangHVO.setDisplayName(sysLookupMultilangVO.getDisplayName());
            sysLookupMultilangHVO.setLanguage(sysLookupMultilangVO.getLanguage());
            session.saveOrUpdate(sysLookupMultilangHVO);
            session.flush();
            return UPDATE_SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupMultilangVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupMultilangVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * freezePopupList(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public int freezePopupList(final GetSysLookupVO sysLookupVO) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession(sysLookupVO.getStrUserName());
            String queryString = "UPDATE GETSYSLOOKUPHVO LU SET LU.ISEDITABLE = 'N', "
                    + " LASTUPDATEDBY = :lastUpdatedBy, LASTUPDATEDDATE = :lastUpdatedDate WHERE LU.LISTNAME = :listName";
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.LAST_UPDATED, sysLookupVO.getStrUserName());
            query.setParameter(RMDCommonConstants.LAST_UPDATED_DATE, RMDCommonUtility.getGMTDateTime(MMddyyyyHHmmssa));
            query.setParameter(RMDCommonConstants.LIST_NAME, sysLookupVO.getListName());
            return query.executeUpdate();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * updateSortOrders(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO,
     * com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public int updateSortOrders(GetSysLookupVO sysLookupVO1, GetSysLookupVO sysLookupVO2) {
        Session session = null;
        try {
            session = getHibernateSession(EMPTY_STRING);
            int updateCount = 0;
            String queryString = "UPDATE GETSYSLOOKUPHVO LU SET LU.SORTORDER = :sortOrder,"
                    + " LASTUPDATEDBY = :lastUpdatedBy, LASTUPDATEDDATE = :lastUpdatedDate WHERE LU.GETSYSLOOKUPSEQID = :lookupSeqId";
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.SORT_ORDER, sysLookupVO1.getSortOrder());
            query.setParameter(RMDCommonConstants.LAST_UPDATED, sysLookupVO1.getStrUserName());
            query.setParameter(RMDCommonConstants.LAST_UPDATED_DATE, RMDCommonUtility.getGMTDateTime(MMddyyyyHHmmssa));
            query.setParameter(RMDCommonConstants.LOOKUPSEQID, sysLookupVO1.getGetSysLookupSeqId());
            updateCount = query.executeUpdate();
            query.setParameter(RMDCommonConstants.SORT_ORDER, sysLookupVO2.getSortOrder());
            query.setParameter(RMDCommonConstants.LAST_UPDATED, sysLookupVO2.getStrUserName());
            query.setParameter(RMDCommonConstants.LAST_UPDATED_DATE, RMDCommonUtility.getGMTDateTime(MMddyyyyHHmmssa));
            query.setParameter(RMDCommonConstants.LOOKUPSEQID, sysLookupVO2.getGetSysLookupSeqId());
            updateCount += query.executeUpdate();
            return updateCount;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO1.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO1.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * getWindowPropertiesFromLookup(java.lang.String)
     */
    @Override
    public Map<String, String> getWindowPropertiesFromLookup(final String language) throws RMDDAOException {
        Session session = null;
        Map<String, String> searchResults = null;
        try {
            session = getHibernateSession();
            Criteria criteria = session.createCriteria(GetSysLookupHVO.class);
            criteria.add(new LikeExpressionWithEscapeCharacters(RMDCommonConstants.LIST_NAME,
                    RMDServiceConstants.RMD_SCREEN_POPERTIES_LIST_NAME, MatchMode.START));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.ACTIVE));
            ProjectionList projList = Projections.projectionList();
            projList.add(Projections.property(RMDCommonConstants.LIST_NAME));
            projList.add(Projections.property(RMDCommonConstants.LOOKVALUE));
            criteria.setProjection(projList);
            List lookupList = criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
                searchResults = new HashMap<String, String>();
                for (Object lookupValues : lookupList) {
                    Object[] columns = (Object[]) lookupValues;
                    searchResults.put((String) columns[0], (String) columns[1]);
                }
            }
            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * @Author:
     * @param:lookupString
     * @Description:This method is used for fetching the details for map legends
     *                   from lookup table
     */

    @Override
    public List<GetSysLookupVO> getMapLegends(final String lookupString) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            final String queryString = "select OBJID, LIST_NAME, LOOK_VALUE, LIST_DESCRIPTION, LOOK_STATE, SORT_ORDER,TO_CHAR(LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS') FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME=:listName ORDER BY SORT_ORDER ASC";
            Query query = session.createSQLQuery(queryString);
            query.setParameter(RMDCommonConstants.LIST_NAME, lookupString);
            // Get the list based on the search criteria(if any)
            query.setFetchSize(20);
            List<Object> lookupList = query.list();
            List<GetSysLookupVO> searchResults = null;

            if (lookupList != null && !lookupList.isEmpty()) {
                GetSysLookupVO objSysLookupVO = null;
                searchResults = new ArrayList<GetSysLookupVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    objSysLookupVO = new GetSysLookupVO();
                    final Object[] lookupRecord = (Object[]) iter.next();
                    objSysLookupVO.setGetSysLookupSeqId(RMDCommonUtility.convertObjectToLong(lookupRecord[0]));
                    objSysLookupVO.setListName(RMDCommonUtility.convertObjectToString(lookupRecord[1]));
                    objSysLookupVO.setLookValue(RMDCommonUtility.convertObjectToString(lookupRecord[2]));
                    objSysLookupVO.setListDescription(RMDCommonUtility.convertObjectToString(lookupRecord[3]));
                    objSysLookupVO.setLookState(RMDCommonUtility.convertObjectToString(lookupRecord[4]));
                    objSysLookupVO.setSortOrder(RMDCommonUtility.convertObjectToLong(lookupRecord[5]));
                    /*
                     * added for map last refresh time phase 2 sprint 6 MISC
                     * change
                     */
                    DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                    Date lastUpdatedDate = formatter.parse(RMDCommonUtility.convertObjectToString(lookupRecord[6]));
                    objSysLookupVO.setLastUpdatedDate(lastUpdatedDate);
                    searchResults.add(objSysLookupVO);
                }
            }

            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LEGENDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LEGENDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List<GetSysLookupVO> getAllPopupListValues() throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            final String queryString = "select OBJID, LIST_NAME, LOOK_VALUE, LIST_DESCRIPTION, LOOK_STATE, SORT_ORDER FROM GETS_RMD.GETS_RMD_LOOKUP ORDER BY LIST_NAME, SORT_ORDER ASC";
            Query query = session.createSQLQuery(queryString);
            query.setFetchSize(1000);
            List<Object> lookupList = query.list();
            List<GetSysLookupVO> searchResults = null;

            if (lookupList != null && !lookupList.isEmpty()) {
                GetSysLookupVO objSysLookupVO = null;
                searchResults = new ArrayList<GetSysLookupVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    objSysLookupVO = new GetSysLookupVO();
                    final Object[] lookupRecord = (Object[]) iter.next();
                    objSysLookupVO.setGetSysLookupSeqId(RMDCommonUtility.convertObjectToLong(lookupRecord[0]));
                    objSysLookupVO.setListName(RMDCommonUtility.convertObjectToString(lookupRecord[1]));
                    objSysLookupVO.setLookValue(RMDCommonUtility.convertObjectToString(lookupRecord[2]));
                    objSysLookupVO.setListDescription(RMDCommonUtility.convertObjectToString(lookupRecord[3]));
                    objSysLookupVO.setLookState(RMDCommonUtility.convertObjectToString(lookupRecord[4]));
                    objSysLookupVO.setSortOrder(RMDCommonUtility.convertObjectToLong(lookupRecord[5]));
                    searchResults.add(objSysLookupVO);
                }
            }

            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the system parameter title
     *                   and value
     */
    @Override
    public List<GetSysParameterVO> getAllSystemParamValues() throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            final String queryString = "select title, value from gets_rmd_sysparm";
            Query query = session.createSQLQuery(queryString);
            // Get the list of results
            List<Object> systemParamList = query.list();
            List<GetSysParameterVO> searchResults = null;
            // populate the list to VO
            if (RMDCommonUtility.isCollectionNotEmpty(systemParamList)) {
                GetSysParameterVO objGetSysParameterVO = null;
                searchResults = new ArrayList<GetSysParameterVO>();
                for (final Iterator<Object> iter = systemParamList.iterator(); iter.hasNext();) {
                    objGetSysParameterVO = new GetSysParameterVO();
                    final Object[] lookupRecord = (Object[]) iter.next();
                    objGetSysParameterVO.setTitle(RMDCommonUtility.convertObjectToString(lookupRecord[0]));
                    objGetSysParameterVO.setValue(RMDCommonUtility.convertObjectToString(lookupRecord[1]));
                    searchResults.add(objGetSysParameterVO);
                }
            }

            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SYSPARAM_FETCH);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SYSPARAM_FETCH);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List<GetSysLookupVO> getLookupValues(final String lookupString) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            final String queryString = "SELECT OBJID, LIST_NAME, LOOK_VALUE, LIST_DESCRIPTION, LOOK_STATE, SORT_ORDER FROM GETS_RMD.GETS_RMD_LOOKUP WHERE UPPER(LOOK_STATE) IN ('ACTIVE','DEFAULT') AND LIST_NAME=:listName ORDER BY SORT_ORDER ASC";
            Query query = session.createSQLQuery(queryString);
            query.setParameter(RMDCommonConstants.LIST_NAME, lookupString);
            query.setFetchSize(10);
            // Get the list based on the search criteria(if any)
            List<Object> lookupList = query.list();
            List<GetSysLookupVO> searchResults = null;

            if (lookupList != null && !lookupList.isEmpty()) {
                GetSysLookupVO objSysLookupVO = null;
                searchResults = new ArrayList<GetSysLookupVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    objSysLookupVO = new GetSysLookupVO();
                    final Object[] lookupRecord = (Object[]) iter.next();
                    objSysLookupVO.setGetSysLookupSeqId(RMDCommonUtility.convertObjectToLong(lookupRecord[0]));
                    objSysLookupVO.setListName(RMDCommonUtility.convertObjectToString(lookupRecord[1]));
                    objSysLookupVO.setLookValue(RMDCommonUtility.convertObjectToString(lookupRecord[2]));
                    objSysLookupVO.setListDescription(RMDCommonUtility.convertObjectToString(lookupRecord[3]));
                    objSysLookupVO.setLookState(RMDCommonUtility.convertObjectToString(lookupRecord[4]));
                    objSysLookupVO.setSortOrder(RMDCommonUtility.convertObjectToLong(lookupRecord[5]));
                    searchResults.add(objSysLookupVO);
                }
            }

            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List<GetSysLookupVO> getCustomerPopupListValues(String customerId) throws RMDDAOException {
        Session session = null;
        List<GetSysLookupVO> searchResults = null;
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(
                    " SELECT /*+  NO_PARALLEL  USE_NL (SH)*/ DISTINCT CASERECOM.URGENCY URGENCY FROM TABLE_CASE C ,GETS_SD_RECOM RECOM , ");
            caseQry.append(
                    " GETS_SD_CASE_RECOM CASERECOM ,GETS_SD_RECOM_DELV DELV ,TABLE_SITE_PART TSP ,GETS_SD_CUST_FDBK FDBK ,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO ");
            caseQry.append(
                    " WHERE DELV.RECOM_DELV2CASE = C.OBJID AND DELV.RECOM_DELV2RECOM = RECOM.OBJID AND CASERECOM.CASE_RECOM2CASE  = C.OBJID AND CASERECOM.CASE_RECOM2RECOM = RECOM.OBJID ");
            caseQry.append(
                    " AND TBO.S_ORG_ID =:customerId AND CASE_PROD2SITE_PART = TSP.OBJID AND TSP.OBJID= VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID ");
            caseQry.append(
                    "AND VEHHDR.VEH_HDR2BUSORG      = TBO.OBJID AND FDBK.CUST_FDBK2CASE        = C.OBJID AND CASESTS2GBST_ELM          IN (268435544,268435947,268435478,268435523) AND FDBK.RX_CLOSE_DATE        IS NULL AND FDBK.OBJID                 = DELV.RECOM_DELV2CUST_FDBK ");
            Query query = session.createSQLQuery(caseQry.toString());
            query.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId);
            query.setFetchSize(10);
            // Get the list based on the search criteria(if any)
            List<Object> lookupList = query.list();

            if (lookupList != null && !lookupList.isEmpty()) {
                GetSysLookupVO objCustLookupVO = null;
                searchResults = new ArrayList<GetSysLookupVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    objCustLookupVO = new GetSysLookupVO();
                    String lookupRecord = (String) iter.next();
                    objCustLookupVO.setUrgency(lookupRecord);
                    searchResults.add(objCustLookupVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return searchResults;
    }

    public String getLookUpValue(String listName) {

        List<GetSysLookupVO> arlSysLookUp = new ArrayList<GetSysLookupVO>();
        String lookupValue = RMDCommonConstants.EMPTY_STRING;
        try {

            arlSysLookUp = getPopupListValues(listName);
            if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {

                for (Iterator iterator = arlSysLookUp.iterator(); iterator.hasNext();) {
                    GetSysLookupVO objSysLookupVO = (GetSysLookupVO) iterator.next();
                    lookupValue = objSysLookupVO.getLookValue();

                }

            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return lookupValue;
    }

    /**
     * @Author:
     * @param:GetSysLookupVO
     * @return:List<GetSysLookupVO>
     * @throws:RMDDAOException
     * @Description: This method is used to get poup list Details.
     */

    @Override
    public List<GetSysLookupVO> getLookupValuesShowAll(GetSysLookupVO sysLookupVO) throws RMDDAOException {

        String popupFilterSearchBy = sysLookupVO.getFilterSearchBy();
        String popupFilterOption = sysLookupVO.getFilterOption();
        String popupFieldValue = sysLookupVO.getFieldValue();
        Session session = null;
        String filterOptions = "";
        String filter = "";
        Query popupHqry = null;
        List<Object[]> resultList = null;
        List<GetSysLookupVO> objGetSysLookupVOlist = null;
        try {
            session = getHibernateSession();
            StringBuilder popupQry = new StringBuilder();
            popupQry.append("select distinct LIST_NAME, LIST_DESCRIPTION from GETS_RMD.GETS_RMD_LOOKUP");

            if ((null != popupFieldValue) && (!popupFieldValue.isEmpty())) {
                popupQry.append(" WHERE UPPER(");
                if (popupFilterSearchBy.equalsIgnoreCase(RMDCommonConstants.POPUP_LISTNAME)) {
                    filterOptions = RMDCommonConstants.POPUP_LIST_NAME;
                } else if (popupFilterSearchBy.equalsIgnoreCase(RMDCommonConstants.POPUP_DESCRIPTION)) {
                    filterOptions = RMDCommonConstants.POP_UP_DESCRIPTION;
                }

                if (popupFilterOption.equalsIgnoreCase(RMDCommonConstants.POPUP_LIST_ADMIN_STARTS_WITH)
                        || popupFilterOption.equalsIgnoreCase(RMDCommonConstants.POPUP_LIST_ADMIN_ENDS_WITH)
                        || popupFilterOption.equalsIgnoreCase(RMDCommonConstants.POPUP_LIST_ADMIN_CONTAINS)) {
                    filter = (filterOptions + ") LIKE UPPER(:popupFieldValue)");
                } else if (popupFilterOption.equalsIgnoreCase(RMDCommonConstants.POPUP_LIST_ADMIN_IS_EQUAL_TO)) {
                    filter = (filterOptions + ") = UPPER(:popupFieldValue)");

                }
                popupQry.append(filter);
            }

            popupHqry = session.createSQLQuery(popupQry.toString());
            popupHqry.setFetchSize(1000);
            if ((null != popupFieldValue) && (!popupFieldValue.isEmpty())) {
                if (popupFilterOption.equalsIgnoreCase(RMDCommonConstants.POPUP_LIST_ADMIN_STARTS_WITH)) {
                    popupHqry.setParameter(RMDCommonConstants.POPUP_FILED_VALUE,
                            popupFieldValue + RMDServiceConstants.PERCENTAGE);
                } else if (popupFilterOption.equalsIgnoreCase(RMDCommonConstants.POPUP_LIST_ADMIN_ENDS_WITH)) {
                    popupHqry.setParameter(RMDCommonConstants.POPUP_FILED_VALUE,
                            RMDServiceConstants.PERCENTAGE + popupFieldValue);
                } else if (popupFilterOption.equalsIgnoreCase(RMDCommonConstants.POPUP_LIST_ADMIN_CONTAINS)) {
                    popupHqry.setParameter(RMDCommonConstants.POPUP_FILED_VALUE,
                            RMDServiceConstants.PERCENTAGE + popupFieldValue + RMDServiceConstants.PERCENTAGE);
                } else {
                    popupHqry.setParameter(RMDCommonConstants.POPUP_FILED_VALUE, popupFieldValue);
                }
            }

            resultList = popupHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                objGetSysLookupVOlist = new ArrayList<GetSysLookupVO>();
                for (final Iterator<Object[]> obj = resultList.iterator(); obj.hasNext();) {
                    sysLookupVO = new GetSysLookupVO();
                    final Object[] GetSysLookupVO = obj.next();
                    sysLookupVO.setListName(ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(GetSysLookupVO[0])));
                    sysLookupVO.setListDescription(ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(GetSysLookupVO[1])));
                    objGetSysLookupVOlist.add(sysLookupVO);
                }
            }
        }

        catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_POPUP_LISTADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }
        return objGetSysLookupVOlist;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.admin.dao.intf.PopupListAdminDAOIntf#
     * getMaxListValueSortOrder
     * (com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public Long getMaxListValueSortOrderNew(final GetSysLookupVO sysLookupVO) {
        Session session = null;
        StringBuilder query = new StringBuilder();
        Query hibernateQuery = null;
        Long maxSortOrder = 0L;
        try {
            session = getHibernateSession();
            query.append("SELECT MAX(SORT_ORDER) FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME = :listName");
            hibernateQuery = session.createSQLQuery(query.toString());
            hibernateQuery.setParameter(RMDCommonConstants.LIST_NAME, sysLookupVO.getListName());
            hibernateQuery.setFetchSize(1);
            List<Object> maxSortOrderLimit = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(maxSortOrderLimit)) {
                maxSortOrder = RMDCommonUtility.convertObjectToLong(maxSortOrderLimit.get(0));
            }
        } catch (Exception ex) {
            throw new RMDDAOException("DAO_EXCEPTION_POPUP_ADMIN", ex);
        } finally {
            releaseSession(session);
        }
        return maxSortOrder;
    }

    @Override
    public int updatePopupList(GetSysLookupVO sysLookupVO) throws RMDDAOException {
        String updatetlistnamedes = RMDCommonConstants.EMPTY_STRING;
        Query hibernateQuery = null;
        String strEoaUserName = null;
        Session session = null;
        String userId = sysLookupVO.getStrUserName();
        try {

            StringBuilder eoaUserQry = new StringBuilder();
            session = getHibernateSession(sysLookupVO.getStrUserName());
            eoaUserQry.append("SELECT A.LOGIN_NAME FROM TABLE_USER A WHERE UPPER(A.WEB_LOGIN) =:userId");
            if (session != null) {
                hibernateQuery = session.createSQLQuery(eoaUserQry.toString());
                hibernateQuery.setParameter(RMDCommonConstants.USERID, userId);
                Object result = hibernateQuery.uniqueResult();
                if (null != result)
                    strEoaUserName = result.toString();
            }

            session = getHibernateSession(sysLookupVO.getStrUserName());
            updatetlistnamedes = "UPDATE GETS_RMD.GETS_RMD_LOOKUP SET LIST_NAME = :listnameNew,LIST_DESCRIPTION = :descriptionNew, "
                    + " LAST_UPDATED_BY = :lastUpdatedBy, LAST_UPDATED_DATE = :lastUpdatedDate WHERE LIST_NAME = :oldListName";
            hibernateQuery = session.createSQLQuery(updatetlistnamedes);
            hibernateQuery.setParameter(RMDCommonConstants.LISTNAMENNEW, ESAPI.encoder().decodeForHTML(sysLookupVO.getListName()));
            hibernateQuery.setParameter(RMDCommonConstants.DESCRIPTIONNEW, ESAPI.encoder().decodeForHTML(sysLookupVO.getListDescription()));
            hibernateQuery.setParameter(RMDCommonConstants.LAST_UPDATED, strEoaUserName);
            hibernateQuery.setParameter(RMDCommonConstants.LAST_UPDATED_DATE,
                    RMDCommonUtility.getGMTDateTime(MMddyyyyHHmmssa));
            hibernateQuery.setParameter(RMDCommonConstants.OLD_LIST_NAME,  ESAPI.encoder().decodeForHTML(sysLookupVO.getOldListName()));

            return hibernateQuery.executeUpdate();

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

    }

    @Override
    public void deletePopupListValueNew(GetSysLookupVO sysLookupVO) throws RMDDAOException {
        Session session = null;
        String deleteterecords = RMDCommonConstants.EMPTY_STRING;
        Query hibernateQuery = null;

        try {
            session = getHibernateSession(sysLookupVO.getStrUserName());

            deleteterecords = "DELETE FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME = :listnameNew";
            hibernateQuery = session.createSQLQuery(deleteterecords);
            hibernateQuery.setParameter(RMDCommonConstants.LISTNAMENNEW, ESAPI.encoder().decodeForHTML(sysLookupVO.getListName()));
            hibernateQuery.executeUpdate();

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public String savePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDDAOException {

        Session session = null;
        String uniquerecord = RMDCommonConstants.EMPTY_STRING;
        String insertlookvalue = RMDCommonConstants.EMPTY_STRING;
        StringBuilder query = new StringBuilder();
        Query hibernateQuery = null;
        String strEoaUserName = null;
        String userId = sysLookupVO.getStrUserName();
        try {
            session = getHibernateSession(sysLookupVO.getStrUserName());
            query.append("select gets_rmd_lookup_seq.nextval from dual");
            hibernateQuery = session.createSQLQuery(query.toString());
            hibernateQuery.setFetchSize(1);
            List<Object> uniquerecordlimit = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(uniquerecordlimit)) {
            	uniquerecord = EsapiUtil.stripXSSCharacters((String)
            			(RMDCommonUtility.convertObjectToString(uniquerecordlimit.get(0))));
            }

            StringBuilder eoaUserQry = new StringBuilder();
            eoaUserQry.append("SELECT A.LOGIN_NAME FROM TABLE_USER A WHERE A.WEB_LOGIN =:userId");
                hibernateQuery = session.createSQLQuery(eoaUserQry.toString());
                hibernateQuery.setParameter(RMDCommonConstants.USERID, userId);
                Object result = hibernateQuery.uniqueResult();
                if (null != result)
                    strEoaUserName = result.toString();

            insertlookvalue = "INSERT INTO GETS_RMD.GETS_RMD_LOOKUP (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,LIST_NAME,LIST_DESCRIPTION,LOOK_VALUE,LOOK_STATE,SORT_ORDER,LOOK_VALUE_DESC) "
                    + " VALUES (:uniquerecord,sysdate,:lastUpdatedBy,sysdate,:createdBy,:listName,:listDescription,:lookValue,'Active',:sortOrder,:lookValueDesc)";
            hibernateQuery = session.createSQLQuery(insertlookvalue);
            hibernateQuery.setParameter(RMDCommonConstants.UNIQUE_RECORD, uniquerecord);
            hibernateQuery.setParameter(RMDCommonConstants.LAST_UPDATED, strEoaUserName);
            hibernateQuery.setParameter(RMDCommonConstants.CREATED_BY, strEoaUserName);

            hibernateQuery.setParameter(RMDCommonConstants.LIST_NAME, ESAPI.encoder().decodeForHTML(sysLookupVO.getListName()));

            hibernateQuery.setParameter(RMDCommonConstants.LIST_DESC, ESAPI.encoder().decodeForHTML(sysLookupVO.getListDescription()));

            hibernateQuery.setParameter(RMDCommonConstants.LOOKVALUE, ESAPI.encoder().decodeForHTML(sysLookupVO.getLookValue()));

            hibernateQuery.setParameter(RMDCommonConstants.SORT_ORDER, sysLookupVO.getSortOrder());
            hibernateQuery.setParameter(RMDCommonConstants.LOOKVALUEDESC, sysLookupVO.getLookValueDesc());

            hibernateQuery.executeUpdate();

            return uniquerecord;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

    }

    @Override
    public void removePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDDAOException {
        Session session = null;
        String strListName;
        String deletetelookvalue = RMDCommonConstants.EMPTY_STRING;
        String updateSortOrderQry = RMDCommonConstants.EMPTY_STRING;
        Query hibernateQuery = null;

        try {
            session = getHibernateSession(sysLookupVO.getStrUserName());

            strListName = ESAPI.encoder().decodeForHTML(sysLookupVO.getListName());
            deletetelookvalue = "DELETE FROM GETS_RMD.GETS_RMD_LOOKUP WHERE OBJID = :lookupSeqId";

            hibernateQuery = session.createSQLQuery(deletetelookvalue);
            hibernateQuery.setParameter(RMDCommonConstants.LOOKUPSEQID, sysLookupVO.getGetSysLookupSeqId());
            hibernateQuery.executeUpdate();

            if (sysLookupVO.getSortOrder() != null) {
                    updateSortOrderQry = "UPDATE GETS_RMD.GETS_RMD_LOOKUP SET SORT_ORDER =sort_Order-1  where sort_order > :sortOrder and LIST_NAME= :listName ";
                    hibernateQuery = session.createSQLQuery(updateSortOrderQry);
                    hibernateQuery.setParameter(RMDCommonConstants.LIST_NAME, strListName);
                    hibernateQuery.setParameter(RMDCommonConstants.SORT_ORDER, sysLookupVO.getSortOrder());
                    hibernateQuery.executeUpdate();
               
            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public String updatePopupListValues(List<GetSysLookupVO> voList) throws RMDDAOException {
        int executedRow = 0;
        Session objHibernateSession = null;
        Transaction tx = null;
        String updatetlookvalues = RMDCommonConstants.EMPTY_STRING;
        String pass = RMDServiceConstants.FAILURE;
        Query hibernateQuery = null;
        String strEoaUserName = null;

        try {
            objHibernateSession = getHibernateSession();
            tx = objHibernateSession.beginTransaction();
            for (GetSysLookupVO getsysvoList : voList) {
                String userId = getsysvoList.getStrUserName();

                StringBuilder eoaUserQry = new StringBuilder();
                eoaUserQry.append("SELECT A.LOGIN_NAME FROM TABLE_USER A WHERE A.WEB_LOGIN =:userId");
                if (objHibernateSession != null) {
                    hibernateQuery = objHibernateSession.createSQLQuery(eoaUserQry.toString());
                    hibernateQuery.setParameter(RMDCommonConstants.USERID, userId);
                    Object result = hibernateQuery.uniqueResult();
                    if (null != result)
                        strEoaUserName = result.toString();
                }

                if (getsysvoList.getGetSysLookupSeqId() != 0) {
                    updatetlookvalues = "UPDATE GETS_RMD.GETS_RMD_LOOKUP SET LOOK_VALUE = :lookValue,LOOK_STATE = :lookState,SORT_ORDER = :sortOrder,LAST_UPDATED_BY = :lastUpdatedBy, LAST_UPDATED_DATE = :lastUpdatedDate,LOOK_VALUE_DESC = :lookValueDesc WHERE OBJID = :lookupSeqId";
                    hibernateQuery = objHibernateSession.createSQLQuery(updatetlookvalues);
                    hibernateQuery.setParameter(RMDCommonConstants.LOOKUPSEQID, getsysvoList.getGetSysLookupSeqId());
                    hibernateQuery.setParameter(RMDCommonConstants.LOOKVALUE, ESAPI.encoder().decodeForHTML(getsysvoList.getLookValue()));
                    hibernateQuery.setParameter(RMDCommonConstants.LOOKSTATE, getsysvoList.getLookState());
                    hibernateQuery.setParameter(RMDCommonConstants.SORT_ORDER, getsysvoList.getSortOrder());
                    hibernateQuery.setParameter(RMDCommonConstants.LOOKVALUEDESC, ESAPI.encoder().decodeForHTML(getsysvoList.getLookValueDesc()));
                    hibernateQuery.setParameter(RMDCommonConstants.LAST_UPDATED, strEoaUserName);
                    hibernateQuery.setParameter(RMDCommonConstants.LAST_UPDATED_DATE,
                            RMDCommonUtility.getGMTDateTime(MMddyyyyHHmmssa));

                    executedRow = hibernateQuery.executeUpdate();
                }

            }
            if (executedRow > 0) {
                pass = RMDServiceConstants.SUCCESS;
            }
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }

            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST, e);
        } finally {
            releaseSession(objHibernateSession);
        }
        return pass;
    }

    @Override
    public String savePopupListNew(GetSysLookupVO sysLookupVO) throws RMDDAOException {

        Session session = null;
        String uniquerecord = RMDCommonConstants.EMPTY_STRING;
        String uniquerecordlistname = RMDCommonConstants.EMPTY_STRING;
        String insertlistnamedes = RMDCommonConstants.EMPTY_STRING;
        StringBuilder query = new StringBuilder();
        Query hibernateQuery = null;
        Query hibernateQuery1 = null;
        Query hibernateQuery2 = null;
        String strEoaUserName = null;
        String userId = sysLookupVO.getStrUserName();
        int listnamecount = 0;
        try {

            session = getHibernateSession(sysLookupVO.getStrUserName());
            query.append("select gets_rmd_lookup_seq.nextval from dual");
            hibernateQuery = session.createSQLQuery(query.toString());
            hibernateQuery.setFetchSize(1);
            List<Object> uniquerecordlimit = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(uniquerecordlimit)) {
                uniquerecord = RMDCommonUtility.convertObjectToString(uniquerecordlimit.get(0));
            }

            StringBuilder eoaUserQry = new StringBuilder();
            eoaUserQry.append("SELECT A.LOGIN_NAME FROM TABLE_USER A WHERE UPPER(A.WEB_LOGIN) =:userId");
            if (session != null) {
                hibernateQuery = session.createSQLQuery(eoaUserQry.toString());
                hibernateQuery.setParameter(RMDCommonConstants.USERID, userId);
                Object result = hibernateQuery.uniqueResult();
                if (null != result)
                    strEoaUserName = result.toString();
            }

            StringBuilder query2 = new StringBuilder();
            session = getHibernateSession(sysLookupVO.getStrUserName());
            query2.append("SELECT count(1) FROM  GETS_RMD.GETS_RMD_LOOKUP WHERE UPPER(LIST_NAME) = UPPER(:listName) ");

            hibernateQuery2 = session.createSQLQuery(query2.toString());
            hibernateQuery2.setParameter(RMDCommonConstants.LIST_NAME, ESAPI.encoder().decodeForHTML(sysLookupVO.getListName()));

            List<Object> ListnameTypeList = hibernateQuery2.list();
            if (RMDCommonUtility.isCollectionNotEmpty(ListnameTypeList)) {
                listnamecount = RMDCommonUtility.convertObjectToInt(ListnameTypeList.get(0));
            }
            if (listnamecount == 0) {
                insertlistnamedes = "INSERT INTO GETS_RMD.GETS_RMD_LOOKUP (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,LIST_NAME,LIST_DESCRIPTION,LOOK_VALUE,LOOK_STATE,SORT_ORDER) "
                        + " VALUES (:uniquerecord,sysdate,:lastUpdatedBy,sysdate,:createdBy,:listName,:listDescription,null,'Active',1)";
                hibernateQuery = session.createSQLQuery(insertlistnamedes);
                hibernateQuery.setParameter(RMDCommonConstants.UNIQUE_RECORD, uniquerecord);
                hibernateQuery.setParameter(RMDCommonConstants.LAST_UPDATED, strEoaUserName);
                hibernateQuery.setParameter(RMDCommonConstants.CREATED_BY, strEoaUserName);

                hibernateQuery.setParameter(RMDCommonConstants.LIST_NAME, ESAPI.encoder().decodeForHTML(sysLookupVO.getListName()));

                hibernateQuery.setParameter(RMDCommonConstants.LIST_DESC, ESAPI.encoder().decodeForHTML(sysLookupVO.getListDescription()));
                hibernateQuery.executeUpdate();

                StringBuilder query1 = new StringBuilder();
                session = getHibernateSession(sysLookupVO.getStrUserName());
                query1.append("SELECT LIST_NAME FROM  GETS_RMD.GETS_RMD_LOOKUP WHERE OBJID=:uniquerecord");
                hibernateQuery1 = session.createSQLQuery(query1.toString());
                hibernateQuery1.setParameter(RMDCommonConstants.UNIQUE_RECORD, uniquerecord);

                hibernateQuery1.setFetchSize(1);
                List<Object> uniquerecordlist = hibernateQuery1.list();
                if (RMDCommonUtility.isCollectionNotEmpty(uniquerecordlist)) {
                    //uniquerecordlistname = RMDCommonUtility.convertObjectToString(uniquerecordlist.get(0));
                	 uniquerecordlistname = EsapiUtil.stripXSSCharacters((String)
                			( RMDCommonUtility.convertObjectToString(uniquerecordlist.get(0))));

                }

            } else {
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LIST_ALREADY_EXSIT);
                throw new RMDDAOException(errorCode);
            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            if (e instanceof RMDDAOException) {
                RMDDAOException rmd = (RMDDAOException) e;
                ErrorDetail errDetails = rmd.getErrorDetail();
                throw new RMDDAOException(errDetails.getErrorCode(), new String[] {}, null, null,
                        RMDCommonConstants.MINOR_ERROR);

            } else {
                final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
                throw new RMDDAOException(errorCode);
            }
        } finally {

            releaseSession(session);
        }
        return uniquerecordlistname;

    }

}

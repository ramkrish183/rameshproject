package com.ge.trans.eoa.services.common.dao.impl;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : BaseDAO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.services.common.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.TypedValue;
import org.hibernate.sql.JoinFragment;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmCaseHistoryHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupMultilangHVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.intf.BaseDAOIntf;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class BaseDAO extends RMDCommonDAO implements BaseDAOIntf {

    private static final long serialVersionUID = 1L;
    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(BaseDAO.class);
    private static final Object OBJECT = null;

    public BaseDAO() {
        super();
    }

    /**
     * @param strLookupState
     * @param strLookupValue
     * @param strLanguage
     * @return Method to obtain lookup vo for particular state and value
     */
    public GetSysLookupHVO getLookupHVO(final String strListName, final String strLookupValue,
            final String strLanguage) {
        GetSysLookupHVO lookupHVO = null;
        Session objSession = null;
        List critList = null;
        try {
            objSession = getHibernateSession();
            Criteria criteria = objSession.createCriteria(GetSysLookupHVO.class).createAlias(
                    RMDCommonConstants.GETSYS_LOOKUP_MULTILANG, RMDCommonConstants.MULTILANG,
                    JoinFragment.LEFT_OUTER_JOIN);
            criteria.add(Restrictions.eq(RMDServiceConstants.LIST_NAME, strListName));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_VALUE, strLookupValue));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.ACTIVE));
            criteria.add(Restrictions.eq(RMDCommonConstants.MULTILANG_LANG, strLanguage));
            criteria.setFetchSize(1);
            critList = criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(critList)) {
                lookupHVO = (GetSysLookupHVO) critList.get(0);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return lookupHVO;
    }

    public String formatString(String strSource) throws Exception {
        if (!(strSource == null || strSource.equals(""))) {
            strSource = replaceString(strSource, "/", "");
            strSource = replaceString(strSource, " ", "");
            strSource = replaceString(strSource, "-", "");
            strSource = replaceString(strSource, "\r", "");
        }
        return strSource;
    }

    public String replaceString(String strSource, String strFromString, String strToString) throws Exception {
        StringBuilder strbuffOutPutString = new StringBuilder();
        int intIndex = 0;
        int intStart = 0;
        while ((intIndex = strSource.indexOf(strFromString, intStart)) != -1) {
            intStart = intIndex;
            strbuffOutPutString.setLength(0);
            strbuffOutPutString.append(strSource.substring(0, intIndex));
            strbuffOutPutString.append(strToString);
            strbuffOutPutString.append(strSource.substring(intIndex + strFromString.length(), strSource.length()));
            strSource = strbuffOutPutString.toString();
            intStart = intStart + strToString.length();
        }
        return (strSource);
    }

    /**
     * @param strLookupState
     * @param strLookupValue
     * @param strLanguage
     * @return Method to obtain lookup vo for particular state and value
     */
    public GetSysLookupHVO getParentLookupHVO(final String strListName, final String strLookupValue,
            final String language) {
        GetSysLookupHVO lookupHVO = null;
        Session objSession = null;
        List critList = null;
        try {
            objSession = getHibernateSession();
            Criteria criteria = objSession.createCriteria(GetSysLookupHVO.class);
            criteria.add(Restrictions.eq(RMDServiceConstants.LIST_NAME, strListName));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_VALUE, strLookupValue));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.ACTIVE));
            criteria.setFetchSize(1);
            critList = criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(critList)) {
                lookupHVO = (GetSysLookupHVO) critList.get(0);
            }
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return lookupHVO;
    }

    /**
     * @param strLookupState
     * @param strLookupValue
     * @param strLanguage
     * @return Method to obtain lookup vo for particular state and value
     */
    public GetSysLookupHVO getLookupHVOWithID(final String strListName, final Long strSequenceId,
            final String strLanguage) {
        GetSysLookupHVO lookupHVO = null;
        Session objSession = null;
        List critList = null;
        try {
            objSession = getHibernateSession();
            Criteria criteria = objSession.createCriteria(GetSysLookupHVO.class).createAlias(
                    RMDCommonConstants.GETSYS_LOOKUP_MULTILANG, RMDCommonConstants.MULTILANG,
                    JoinFragment.LEFT_OUTER_JOIN);
            criteria.add(Restrictions.eq(RMDServiceConstants.LIST_NAME, strListName));
            criteria.add(Restrictions.eq(RMDServiceConstants.SYSLOOKUP_ID, strSequenceId));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.ACTIVE));
            criteria.add(Restrictions.eq(RMDCommonConstants.MULTILANG_LANG, strLanguage));
            criteria.setFetchSize(1);
            critList = criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(critList)) {
                lookupHVO = (GetSysLookupHVO) critList.get(0);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return lookupHVO;
    }

    /**
     * @param lookUpSet
     * @return
     */
    public String getDisplayName(final long lngSysLookupId, final String strLanguage) {
        String strDisplayName = RMDCommonConstants.EMPTY_STRING;
        Session objSession = null;
        List critList = null;
        GetSysLookupMultilangHVO objMultilangHVO = null;
        try {
            objSession = getHibernateSession();
            Criteria criteria = objSession.createCriteria(GetSysLookupMultilangHVO.class);
            criteria.setFetchMode(RMDCommonConstants.GETSYSLOOKUP, FetchMode.JOIN);
            criteria.add(Restrictions.eq(RMDCommonConstants.GETSYSLOOKUP_GETSYSLOOKUPSEQID, lngSysLookupId));
            criteria.add(Restrictions.eq(RMDCommonConstants.LANGUAGE, strLanguage));
            criteria.setFetchSize(1);
            criteria.setCacheable(true);
            critList = criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(critList)) {
                objMultilangHVO = (GetSysLookupMultilangHVO) critList.get(0);
            }
            if (objMultilangHVO != null)
                strDisplayName = objMultilangHVO.getDisplayName();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return strDisplayName;
    }

    /**
     * @Author:
     * @param listName
     * @param lookValue
     * @param strLanguage
     * @return
     * @Description:
     */
    public String getDisplayName(final String listName, final String lookValue, final String strLanguage) {
        String strDisplayName = RMDCommonConstants.EMPTY_STRING;
        Session objSession = null;
        List critList = null;
        GetSysLookupMultilangHVO objMultilangHVO = null;
        try {
            objSession = getHibernateSession();
            Criteria criteria = objSession.createCriteria(GetSysLookupMultilangHVO.class);
            criteria.setFetchMode(RMDCommonConstants.GETSYSLOOKUP, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETSYSLOOKUP, RMDCommonConstants.LOOKUP);
            criteria.add(Restrictions.eq(RMDCommonConstants.LOOKUP_LISTNAME, listName));
            criteria.add(Restrictions.eq(RMDCommonConstants.LOOKUP_LOOKVALUE, lookValue));
            criteria.add(Restrictions.eq(RMDCommonConstants.LANGUAGE, strLanguage));
            criteria.setFetchSize(1);
            criteria.setCacheable(true);
            critList = criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(critList)) {
                objMultilangHVO = (GetSysLookupMultilangHVO) critList.get(0);
            }
            if (objMultilangHVO != null)
                strDisplayName = objMultilangHVO.getDisplayName();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return strDisplayName;
    }

    /**
     * @Author:
     * @param listName
     * @param strLanguage
     * @param key
     * @return
     * @Description: this method will set id and name if the Key passed is not
     *               'lookvalue'
     */
    public Map<Object, String> getDisplayNameMap(final String listName, final String strLanguage, final String key) {
        Session session = null;
        Map<Object, String> valueAndDisplayName = null;
        Query sqlQuery = null;
        StringBuilder stringBuilder = null;
        try {
            session = getHibernateSession();
            stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT OBJID,LOOK_VALUE FROM   GETS_RMD_LOOKUP ");
            stringBuilder.append("WHERE  LIST_NAME =:listName AND LOOK_STATE != 'InActive' ORDER BY SORT_ORDER ASC ");
            sqlQuery = session.createSQLQuery(stringBuilder.toString());
            sqlQuery.setParameter(RMDCommonConstants.LIST_NAME, listName);
            List<Object> lookList = sqlQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookList)) {
                valueAndDisplayName = new LinkedHashMap<Object, String>();
                if ((RMDCommonConstants.LOOKVALUE).equals(key)) {
                    for (final Iterator<Object> iter = lookList.iterator(); iter.hasNext();) {

                        final Object[] lookupRecord = (Object[]) iter.next();

                        valueAndDisplayName.put(lookupRecord[1].toString(), lookupRecord[1].toString());
                    }
                } else {
                    for (final Iterator<Object> iter = lookList.iterator(); iter.hasNext();) {

                        final Object[] lookupRecord = (Object[]) iter.next();

                        valueAndDisplayName.put(lookupRecord[0].toString(), lookupRecord[1].toString());
                    }
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return valueAndDisplayName;
    }

    /**
     * @Author:
     * @param listName
     * @param strLanguage
     * @param key
     * @param strServiceName
     * @return
     * @Description:
     */
    public Map<Object, String> getDisplayNameMap(final String listName, final String strLanguage, final String key,
            final String strServiceName) {
        Session objSession = null;
        Map<Object, String> valueAndDisplayName = null;
        StringBuilder stringBuilder = new StringBuilder();
        Query nativeQuery = null;
        try {
            objSession = getHibernateSession();
            stringBuilder.append("SELECT OBJID,LOOK_VALUE FROM GETS_RMD_LOOKUP ");
            stringBuilder.append(
                    "WHERE  LIST_NAME =:listName AND LOOK_VALUE=:look_value AND LOOK_STATE != 'InActive' ORDER BY SORT_ORDER ");
            nativeQuery = objSession.createSQLQuery(stringBuilder.toString());
            nativeQuery.setParameter(RMDCommonConstants.LIST_NAME, listName);
            nativeQuery.setParameter(RMDCommonConstants.LOOK_VALUE, strServiceName);
            List<Object> lookList = nativeQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookList)) {
                valueAndDisplayName = new LinkedHashMap<Object, String>();
                if (RMDCommonConstants.LOOKVALUE.equals(key)) {
                    for (final Iterator<Object> iter = lookList.iterator(); iter.hasNext();) {

                        final Object[] lookupRecord = (Object[]) iter.next();

                        valueAndDisplayName.put(lookupRecord[1].toString(), lookupRecord[1].toString());
                    }
                } else {
                    for (final Iterator<Object> iter = lookList.iterator(); iter.hasNext();) {

                        final Object[] lookupRecord = (Object[]) iter.next();

                        valueAndDisplayName.put(lookupRecord[0].toString(), lookupRecord[1].toString());
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return valueAndDisplayName;
    }

    /**
     * @Author:
     * @param listName
     * @param strLanguage
     * @param key
     * @return
     * @Description:
     */
    public Map<Object, String> getLookupValuesList(final String listName) {
        Session objSession = null;
        List<GetSysLookupHVO> sysLookList = null;
        Map<Object, String> valueName = null;
        try {

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return valueName;
    }

    /**
     * @param strLookupState
     * @param strLookupValue
     * @return
     */
    public GetSysLookupHVO getParentLookupValue(final String strListName) {
        GetSysLookupHVO lookupHVO = null;
        Session objSession = null;
        List critList = new ArrayList();
        try {
            lookupHVO = null;
            objSession = getHibernateSession();
            Criteria criteria = objSession.createCriteria(GetSysLookupHVO.class);
            criteria.add(Restrictions.eq(RMDServiceConstants.LIST_NAME, strListName));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.ACTIVE));
            criteria.setFetchSize(1);
            critList = criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(critList)) {
                lookupHVO = (GetSysLookupHVO) critList.get(0);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.CHINESE_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.CHINESE_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return lookupHVO;
    }

    /**
     * @param strListName
     * @param strLanguage
     * @return
     */
    public List getLookupList(final String strListName, final String strLanguage) {

        List arlLookupList = null;
        try {
            Map<Object, String> lookupDisplayNames = getDisplayNameMap(strListName, strLanguage, null);
            if (lookupDisplayNames != null && !lookupDisplayNames.isEmpty()) {
                Set<Object> key = lookupDisplayNames.keySet();
                arlLookupList = new ArrayList();
                for (Object id : key) {
                    arlLookupList.add(new ElementVO(id.toString(), lookupDisplayNames.get(id)));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return arlLookupList;
    }

    /**
     * @param strListName
     * @param strLanguage
     * @return
     */
    public List getLookupList(final String strListName, final String strLanguage, final String strServiceName) {

        List arlLookupList = null;
        try {
            Map<Object, String> lookupDisplayNames = getDisplayNameMap(strListName, strLanguage, null, strServiceName);
            if (lookupDisplayNames != null && !lookupDisplayNames.isEmpty()) {
                Set<Object> key = lookupDisplayNames.keySet();
                arlLookupList = new ArrayList();
                for (Object id : key) {
                    arlLookupList.add(new ElementVO(id.toString(), lookupDisplayNames.get(id)));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return arlLookupList;
    }

    /**
     * @param strListName
     * @param strLanguage
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List getLookupListValues(final String strListName, final String strLanguage) {
        List arlLookupList = null;
        try {
            Map<Object, String> lookupDisplayNames = getDisplayNameMap(strListName, strLanguage,
                    RMDCommonConstants.LOOKVALUE);

            if (lookupDisplayNames != null && !lookupDisplayNames.isEmpty()) {
                Set<Object> key = lookupDisplayNames.keySet();
                arlLookupList = new ArrayList();
                for (Object id : key) {
                    arlLookupList.add(new ElementVO(id.toString(), lookupDisplayNames.get(id)));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return arlLookupList;
    }

    /**
     * @param strLookupState
     * @param strLookupValue
     * @return
     */
    @Override
    public String getLookupValue(final String strListName) {
        GetSysLookupHVO lookupHVO = null;
        Session objSession = null;
        List critList = new ArrayList();
        String lookupValue = null;
        try {
            lookupHVO = null;

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.CHINESE_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.CHINESE_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return lookupValue;
    }

    /**
     * @Author:
     * @param objCaseHistoryHVO
     * @param strUserName
     * @Description:
     */
    public void insertActivityTable(final GetCmCaseHistoryHVO objCaseHistoryHVO, String strUserName) {
        LOG.debug("Enter insertActivityTable in BaseCaseDAO");
        Session session = null;
        try {
            session = getHibernateSession(strUserName);
            session.save(objCaseHistoryHVO);
            session.flush();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objCaseHistoryHVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objCaseHistoryHVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        LOG.debug("Exiting insertActivityTable in BaseCaseDAO");
    }
}

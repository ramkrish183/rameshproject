/**
 * ============================================================
 * Classification: GE Confidential
 * File : PopupListAdminBOIntf.java
 * Description : BO Impl for Popup Admin List
 *
 * Package : com.ge.trans.rmd.services.admin.bo.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 13, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.admin.bo.impl;

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.UPDATE_SUCCESS;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupMultilangVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

import com.ge.trans.eoa.services.admin.bo.intf.PopupListAdminBOIntf;
import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.GetSysParameterVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.LookupSearchServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated: May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : BO Impl for Popup Admin List
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class PopupListAdminBOImpl implements PopupListAdminBOIntf {

    /** popupListAdminDAO of Type PopupListAdminDAOIntf **/
    private PopupListAdminDAOIntf objpopupListAdminDAO;

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf#
     * deletePopupList(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public void deletePopupList(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            final List<GetSysLookupVO> arlLookupValues = getPopupListValues(sysLookupVO);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupValues)) {
                for (GetSysLookupVO objSysLookupVO : arlLookupValues) {
                    objSysLookupVO.setStrUserName(sysLookupVO.getStrUserName());
                    deletePopupListValue(objSysLookupVO);
                }
            }
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf
     * #deletePopupListValue
     * (com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public void deletePopupListValue(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            objpopupListAdminDAO.deletePopupListValue(sysLookupVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf#
     * getPopupList (com.ge.trans.rmd.services.admin.service.valueobjects.
     * LookupSearchServiceVO )
     */
    @Override
    public List<GetSysLookupVO> getPopupList(final LookupSearchServiceVO objLookupServiceSearch) throws RMDBOException {
        try {
            return objpopupListAdminDAO.getPopupList(objLookupServiceSearch);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf#
     * getPopupListValues(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<GetSysLookupVO> getPopupListValues(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            String lookupString = sysLookupVO.getListName();
            return objpopupListAdminDAO.getPopupListValues(lookupString);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf#
     * getPopupListMultilangValues
     * (com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public Set<GetSysLookupMultilangVO> getPopupListMultilangValues(final GetSysLookupVO sysLookupVO)
            throws RMDBOException {
        try {
            return objpopupListAdminDAO.getPopupListMultilangValues(sysLookupVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf#
     * savePopupList(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public int savePopupList(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            Long maxSortOrder = null;
            if (null == sysLookupVO.getSortOrder() || sysLookupVO.getSortOrder() == 0) {
                maxSortOrder = objpopupListAdminDAO.getMaxListValueSortOrder(sysLookupVO);
                if (null == maxSortOrder)
                    maxSortOrder = 0L;
                sysLookupVO.setSortOrder(++maxSortOrder);
            }
            objpopupListAdminDAO.savePopupList(sysLookupVO);
            if (StringUtils.isNotBlank(sysLookupVO.getLookValue())) {
                final GetSysLookupVO sysLookupVOPersisted = (GetSysLookupVO) objpopupListAdminDAO
                        .savePopupListValue(sysLookupVO);
                final Set<GetSysLookupMultilangVO> multiLangValuesVO = sysLookupVO.getGetSysLookupMultilangs();
                if (RMDCommonUtility.isCollectionNotEmpty(multiLangValuesVO)) {
                    for (GetSysLookupMultilangVO objMultilangVO : multiLangValuesVO) {
                        objMultilangVO.setGetSysLookupSeqId(sysLookupVOPersisted.getGetSysLookupSeqId());
                        objMultilangVO.setStrUserName(sysLookupVO.getStrUserName());
                        if (StringUtils.isNotBlank(objMultilangVO.getDisplayName()))
                            objpopupListAdminDAO.savePopupListMultiLangValues(objMultilangVO);
                    }
                }
            }
            return UPDATE_SUCCESS;
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf#
     * freezePopupList(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public int freezePopupList(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            return objpopupListAdminDAO.freezePopupList(sysLookupVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf#
     * updateSortOrders(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO,
     * com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public int updateSortOrders(final GetSysLookupVO sysLookupVO1, final GetSysLookupVO sysLookupVO2)
            throws RMDBOException {
        try {
            return objpopupListAdminDAO.updateSortOrders(sysLookupVO1, sysLookupVO2);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf#
     * getWindowPropertiesFromLookup(java.lang.String)
     */
    @Override
    public Map<String, String> getWindowPropertiesFromLookup(final String language) throws RMDBOException {
        try {
            return objpopupListAdminDAO.getWindowPropertiesFromLookup(language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @Author:
     * @param popupListAdminDAO
     * @Description:
     */

    public void setObjpopupListAdminDAO(PopupListAdminDAOIntf objpopupListAdminDAO) {
        this.objpopupListAdminDAO = objpopupListAdminDAO;
    }

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the legends list for Map
     */

    @Override
    public List<GetSysLookupVO> getMapLegends(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            String lookupString = sysLookupVO.getListName();
            return objpopupListAdminDAO.getMapLegends(lookupString);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<GetSysLookupVO> getAllPopupListValues() throws RMDBOException {
        try {
            return objpopupListAdminDAO.getAllPopupListValues();
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:This method is used for fetching the system parameter title
     *                   and value
     */

    @Override
    public List<GetSysParameterVO> getAllSystemParamValues() throws RMDBOException {
        try {
            return objpopupListAdminDAO.getAllSystemParamValues();
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<GetSysLookupVO> getLookupValues(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            String lookupString = sysLookupVO.getListName();
            return objpopupListAdminDAO.getLookupValues(lookupString);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GetSysLookupVO> getCustomerPopupListValues(String customerId) throws RMDBOException {
        try {
            return objpopupListAdminDAO.getCustomerPopupListValues(customerId);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<GetSysLookupVO> getLookupValuesShowAll(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            return objpopupListAdminDAO.getLookupValuesShowAll(sysLookupVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public int updatePopupListNew(GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {

            objpopupListAdminDAO.updatePopupList(sysLookupVO);
            return UPDATE_SUCCESS;
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    @Override
    public void deletePopupListNew(GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            objpopupListAdminDAO.deletePopupListValueNew(sysLookupVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.PopupListAdminBOIntf
     * #deletePopupListValue
     * (com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    public void deletePopupListValueNew(final GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            objpopupListAdminDAO.deletePopupListValueNew(sysLookupVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public void removePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            objpopupListAdminDAO.removePopupListlookvalue(sysLookupVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public String savePopupListNew(GetSysLookupVO sysLookupVO) throws RMDBOException {
        try {
            String uniquerecord = null;
            uniquerecord = objpopupListAdminDAO.savePopupListNew(sysLookupVO);
            return uniquerecord;
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    @Override
    public String updatePopupListValues(List<GetSysLookupVO> rowList) throws RMDBOException {
        try {
            return objpopupListAdminDAO.updatePopupListValues(rowList);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public String savePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDBOException {
        String uniquerecord = null;
        try {
            Long maxSortOrder = null;
            if (null == sysLookupVO.getSortOrder() || sysLookupVO.getSortOrder() == 0) {
                maxSortOrder = objpopupListAdminDAO.getMaxListValueSortOrderNew(sysLookupVO);
                if (null == maxSortOrder)
                    maxSortOrder = 0L;
                sysLookupVO.setSortOrder(++maxSortOrder);
            }
            uniquerecord = objpopupListAdminDAO.savePopupListlookvalue(sysLookupVO);
            return uniquerecord;
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

}
/**
 * ============================================================
 * Classification: GE Confidential
 * File : PopupListAdminServiceImpl.java
 * Description : Service Impl for Popup Admin List
 *
 * Package : com.ge.trans.rmd.services.admin.service.impl
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
package com.ge.trans.eoa.services.admin.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.admin.bo.intf.PopupListAdminBOIntf;
import com.ge.trans.eoa.services.admin.service.intf.PopupListAdminServiceIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.GetSysParameterVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.LookupSearchServiceVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupMultilangVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated: May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : Service Implementation for Popup Admin List
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class PopupListAdminServiceImpl implements PopupListAdminServiceIntf {

    /** popupListAdminBO of Type PopupListAdminBOIntf **/
    private PopupListAdminBOIntf objpopupListAdminBO;

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * deletePopupList(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public void deletePopupList(final GetSysLookupVO sysLookupVO) throws RMDServiceException {
        try {
            objpopupListAdminBO.deletePopupList(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * deletePopupListValue(com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public void deletePopupListValue(final GetSysLookupVO sysLookupVO) throws RMDServiceException {
        try {
            objpopupListAdminBO.deletePopupListValue(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * getPopupList(com.ge.trans.rmd.services
     * .admin.service.valueobjects.LookupSearchServiceVO)
     */
    @Override
    public List<GetSysLookupVO> getPopupList(final LookupSearchServiceVO objLookupServiceSearch)
            throws RMDServiceException {
        List<GetSysLookupVO> popupList = null;
        try {
            popupList = objpopupListAdminBO.getPopupList(objLookupServiceSearch);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objLookupServiceSearch.getStrLanguage());
        }
        return popupList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * getPopupListValues(com.ge.trans.rmd.common .valueobjects.GetSysLookupVO)
     */
    @Override
    public List<GetSysLookupVO> getPopupListValues(final GetSysLookupVO sysLookupVO) throws RMDServiceException {
        List<GetSysLookupVO> popupListValues = null;
        try {
            popupListValues = objpopupListAdminBO.getPopupListValues(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return popupListValues;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * getPopupListMultilangValues(com.ge.trans.rmd
     * .common.valueobjects.GetSysLookupVO)
     */
    @Override
    public Set<GetSysLookupMultilangVO> getPopupListMultilangValues(final GetSysLookupVO sysLookupVO)
            throws RMDServiceException {
        Set<GetSysLookupMultilangVO> mulitLangValues = null;
        try {
            mulitLangValues = objpopupListAdminBO.getPopupListMultilangValues(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return mulitLangValues;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * savePopupList(com.ge.trans.rmd.common.valueobjects .GetSysLookupVO)
     */
    @Override
    public int savePopupList(final GetSysLookupVO sysLookupVO) throws RMDServiceException {
        int rowsUpdated = 0;
        try {
            rowsUpdated = objpopupListAdminBO.savePopupList(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return rowsUpdated;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * freezePopupList(com.ge.trans.rmd.common.valueobjects .GetSysLookupVO)
     */
    @Override
    public int freezePopupList(final GetSysLookupVO sysLookupVO) throws RMDServiceException {
        int rowsUpdated = 0;
        try {
            rowsUpdated = objpopupListAdminBO.freezePopupList(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return rowsUpdated;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * updateSortOrders(com.ge.trans.rmd.common.valueobjects .GetSysLookupVO,
     * com.ge.trans.rmd.common.valueobjects.GetSysLookupVO)
     */
    @Override
    public int updateSortOrders(final GetSysLookupVO sysLookupVO1, final GetSysLookupVO sysLookupVO2)
            throws RMDServiceException {
        int rowsUpdated = 0;
        try {
            rowsUpdated = objpopupListAdminBO.updateSortOrders(sysLookupVO1, sysLookupVO2);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO1.getStrLanguage());
        }
        return rowsUpdated;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.PopupListAdminServiceIntf#
     * getWindowPropertiesFromLookup(java.lang.String)
     */
    @Override
    public Map<String, String> getWindowPropertiesFromLookup(final String language) throws RMDServiceException {
        Map<String, String> windowProperties = null;
        try {
            windowProperties = objpopupListAdminBO.getWindowPropertiesFromLookup(language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return windowProperties;
    }

    /**
     * @Author:
     * @param popupListAdminBO
     * @Description:
     */
    public void setObjpopupListAdminBO(PopupListAdminBOIntf objpopupListAdminBO) {
        this.objpopupListAdminBO = objpopupListAdminBO;
    }

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the legends list for Map
     */
    @Override
    public List<GetSysLookupVO> getMapLegends(final GetSysLookupVO sysLookupVO) throws RMDServiceException {
        List<GetSysLookupVO> popupListValues = null;
        try {
            popupListValues = objpopupListAdminBO.getMapLegends(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return popupListValues;
    }

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the system parameter title
     *                   and value
     */

    @Override
    public List<GetSysParameterVO> getAllSystemParamValues() throws RMDServiceException {
        List<GetSysParameterVO> objGetSysParameterVO = null;
        try {
            objGetSysParameterVO = objpopupListAdminBO.getAllSystemParamValues();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objGetSysParameterVO;
    }

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the lookup values
     */
    @Override
    public List<GetSysLookupVO> getLookupValues(final GetSysLookupVO sysLookupVO) throws RMDServiceException {
        List<GetSysLookupVO> popupListValues = null;
        try {
            popupListValues = objpopupListAdminBO.getPopupListValues(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return popupListValues;
    }

    @Override
    public List<GetSysLookupVO> getLookupValuesShowAll(final GetSysLookupVO sysLookupVO) throws RMDServiceException {
        List<GetSysLookupVO> popupListValues = null;
        try {
            popupListValues = objpopupListAdminBO.getLookupValuesShowAll(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return popupListValues;
    }

    @Override
    public int updatePopupListNew(GetSysLookupVO sysLookupVO) throws RMDServiceException {
        int rowsUpdated = 0;
        try {
            rowsUpdated = objpopupListAdminBO.updatePopupListNew(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return rowsUpdated;
    }

    @Override
    public void deletePopupListNew(GetSysLookupVO sysLookupVO) throws RMDServiceException {
        try {
            objpopupListAdminBO.deletePopupListNew(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
    }

    @Override
    public void removePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDServiceException {
        try {
            objpopupListAdminBO.removePopupListlookvalue(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
    }

    @Override
    public String savePopupListNew(GetSysLookupVO sysLookupVO) throws RMDServiceException {
        String uniquerecord = null;
        try {
            uniquerecord = objpopupListAdminBO.savePopupListNew(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return uniquerecord;
    }

    @Override
    public String updatePopupListValues(List<GetSysLookupVO> rowList) throws RMDServiceException {
        String rowupddated = null;
        try {
            rowupddated = objpopupListAdminBO.updatePopupListValues(rowList);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return rowupddated;
    }

    @Override
    public String savePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDServiceException {
        String uniquerecord = null;
        try {
            uniquerecord = objpopupListAdminBO.savePopupListlookvalue(sysLookupVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO.getStrLanguage());
        }
        return uniquerecord;
    }
}
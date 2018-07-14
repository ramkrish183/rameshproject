/**
 * ============================================================
 * Classification: GE Confidential
 * File : PopupListAdminDAOIntf.java
 * Description : DAO Interface for Popup Admin List
 *
 * Package : com.ge.trans.rmd.services.admin.dao.intf
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
package com.ge.trans.eoa.services.admin.dao.intf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ge.trans.rmd.common.valueobjects.GetSysLookupMultilangVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.admin.service.valueobjects.GetSysParameterVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.LookupSearchServiceVO;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @DateCreated: May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact 	:
 * @Description : DAO Interface for Popup Admin List
 * @History		:
 *
 ******************************************************************************/
/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 18, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface PopupListAdminDAOIntf {

    /**
     * @Author:
     * @param objLookupServiceSearch
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<GetSysLookupVO> getPopupList(LookupSearchServiceVO objLookupServiceSearch) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<GetSysLookupVO> getAllPopupListValues() throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<GetSysLookupVO> getPopupListValues(final String lookupString) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    Set<GetSysLookupMultilangVO> getPopupListMultilangValues(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    String savePopupListNew(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    int updatePopupList(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    void deletePopupListValueNew(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    String savePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    void removePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    String updatePopupListValues(List<GetSysLookupVO> voList) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int freezePopupList(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    Serializable savePopupListValue(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupMultilangVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int savePopupListMultiLangValues(GetSysLookupMultilangVO sysLookupMultilangVO) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @throws RMDDAOException
     * @Description:
     */
    void deletePopupListValue(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO1
     * @param sysLookupVO2
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int updateSortOrders(GetSysLookupVO sysLookupVO1, GetSysLookupVO sysLookupVO2) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    Long getMaxListValueSortOrder(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    Long getMaxListValueSortOrderNew(GetSysLookupVO sysLookupVO) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    Map<String, String> getWindowPropertiesFromLookup(final String language) throws RMDDAOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the legends list for Map
     */
    List<GetSysLookupVO> getMapLegends(final String lookupString) throws RMDDAOException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDDAOException
     * @Description:this is method declaration for fetching the system parameter
     *                   values
     */
    List<GetSysParameterVO> getAllSystemParamValues() throws RMDDAOException;

    /**
     * @param lookupString
     * @return
     * @throws RMDDAOException
     */
    public List<GetSysLookupVO> getHCLookUpValues(final String lookupString) throws RMDDAOException;

    /**
     * @param lookupString
     * @return
     * @throws RMDDAOException
     */
    public List<GetSysLookupVO> getLookupValues(final String lookupString) throws RMDDAOException;

    /**
     * @param customerId
     * @return List<GetSysLookupVO>
     * @throws RMDDAOException
     */

    public List<GetSysLookupVO> getCustomerPopupListValues(String customerId) throws RMDDAOException;

    public List<GetSysLookupVO> getLookupValuesShowAll(final GetSysLookupVO sysLookupVO) throws RMDDAOException;

    int savePopupList(GetSysLookupVO sysLookupVO) throws RMDDAOException;

}
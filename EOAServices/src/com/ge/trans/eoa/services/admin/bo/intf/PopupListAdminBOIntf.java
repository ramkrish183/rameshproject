/**
 * ============================================================
 * Classification: GE Confidential
 * File : PopupListAdminBOIntf.java
 * Description : BO Interface for Popup Admin List
 *
 * Package : com.ge.trans.rmd.services.admin.bo.intf
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
package com.ge.trans.eoa.services.admin.bo.intf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupMultilangVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

import com.ge.trans.eoa.services.admin.service.valueobjects.GetSysParameterVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.LookupSearchServiceVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated: May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : BO Interface for Popup Admin List
 * @History :
 ******************************************************************************/
public interface PopupListAdminBOIntf {

    /**
     * @Author:
     * @param objLookupServiceSearch
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<GetSysLookupVO> getPopupList(LookupSearchServiceVO objLookupServiceSearch) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<GetSysLookupVO> getAllPopupListValues() throws RMDBOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<GetSysLookupVO> getPopupListValues(GetSysLookupVO sysLookupVO) throws RMDBOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    Set<GetSysLookupMultilangVO> getPopupListMultilangValues(GetSysLookupVO sysLookupVO) throws RMDBOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int savePopupList(GetSysLookupVO sysLookupVO) throws RMDBOException;

    String savePopupListNew(GetSysLookupVO sysLookupVO) throws RMDBOException;

    void deletePopupListNew(GetSysLookupVO sysLookupVO) throws RMDBOException;

    String savePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDBOException;

    void removePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDBOException;

    String updatePopupListValues(List<GetSysLookupVO> rowList) throws RMDBOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int freezePopupList(GetSysLookupVO sysLookupVO) throws RMDBOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @throws RMDBOException
     * @Description:
     */
    void deletePopupList(GetSysLookupVO sysLookupVO) throws RMDBOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @throws RMDBOException
     * @Description:
     */
    void deletePopupListValue(GetSysLookupVO sysLookupVO) throws RMDBOException;

    /**
     * @Author:
     * @param sysLookupVO1
     * @param sysLookupVO2
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int updateSortOrders(GetSysLookupVO sysLookupVO1, GetSysLookupVO sysLookupVO2) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    Map<String, String> getWindowPropertiesFromLookup(final String language) throws RMDBOException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the legends list for Map
     */
    List<GetSysLookupVO> getMapLegends(GetSysLookupVO sysLookupVO) throws RMDBOException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the system parameters
     */
    List<GetSysParameterVO> getAllSystemParamValues() throws RMDBOException;

    public List<GetSysLookupVO> getLookupValuesShowAll(final GetSysLookupVO sysLookupVO) throws RMDBOException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the system parameters
     */
    public List<GetSysLookupVO> getLookupValues(final GetSysLookupVO sysLookupVO) throws RMDBOException;

    /**
     * @Author:
     * @param :customerId
     * @return : List<GetSysLookupVO>
     * @throws RMDBOException
     * @Description:this is a method to populate the customer specified
     *                   urgencies
     */
    public List<GetSysLookupVO> getCustomerPopupListValues(String customerId) throws RMDBOException;

    int updatePopupListNew(GetSysLookupVO sysLookupVO) throws RMDBOException;
}
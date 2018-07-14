/**
 * ============================================================
 * Classification: GE Confidential
 * File : PopupListAdminServiceIntf.java
 * Description : Service Interface for Popup Admin List
 *
 * Package : com.ge.trans.rmd.services.admin.service.intf
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
package com.ge.trans.eoa.services.admin.service.intf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupMultilangVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
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
 * @Description : Service Interface for Popup Admin List
 * @History :
 ******************************************************************************/
public interface PopupListAdminServiceIntf {

    /**
     * @Author:
     * @param objLookupServiceSearch
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<GetSysLookupVO> getPopupList(LookupSearchServiceVO objLookupServiceSearch) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<GetSysLookupVO> getPopupListValues(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    Set<GetSysLookupMultilangVO> getPopupListMultilangValues(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int savePopupList(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int freezePopupList(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO
     * @throws RMDServiceException
     * @Description:
     */
    void deletePopupList(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO
     * @throws RMDServiceException
     * @Description:
     */
    void deletePopupListValue(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO1
     * @param sysLookupVO2
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int updateSortOrders(GetSysLookupVO sysLookupVO1, GetSysLookupVO sysLookupVO2) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    Map<String, String> getWindowPropertiesFromLookup(final String language) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the legends list for Map
     */
    List<GetSysLookupVO> getMapLegends(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the system parameter title
     *                   and value
     */
    List<GetSysParameterVO> getAllSystemParamValues() throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the lookup values
     */
    public List<GetSysLookupVO> getLookupValues(final GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the lookup values
     */
    String savePopupListNew(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the lookup values
     */
    int updatePopupListNew(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the lookup values
     */
    void deletePopupListNew(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the lookup values
     */
    String savePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the lookup values
     */
    void removePopupListlookvalue(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    /**
     * @Author:
     * @param sysLookupVO1
     * @param sysLookupVO2
     * @return
     * @throws RMDServiceException
     * @Description:
     */

    public String updatePopupListValues(List<GetSysLookupVO> rowList) throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to populate the lookup values
     */
    public List<GetSysLookupVO> getLookupValuesShowAll(final GetSysLookupVO sysLookupVO) throws RMDServiceException;

}
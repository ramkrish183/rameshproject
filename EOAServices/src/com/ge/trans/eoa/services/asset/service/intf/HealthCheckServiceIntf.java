/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthCheckRequestServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.service.intf;
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
package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAttributeVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAvailableVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckSubmitEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HlthChckRqstServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewReqHistoryEoaVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewRespHistoryEoaVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsSubmitRequestVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 5, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface HealthCheckServiceIntf {
    /**
     * @Author:
     * @param objHlthChckRqstServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    String sendHealthRequest(HlthChckRqstServiceVO objHlthChckRqstServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param strAssetId,strCustomerId,strGrpName
     * @return HealthCheckAvailableVO
     * @throws RMDServiceException
     * @Description:
     */
    public HealthCheckAvailableVO IsHCAvailable(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDServiceException;
    
    /**
     * @Author: Raj.S(212687474)
     * @param userId
     * @return RDRNotificationsDataVO
     * @throws RMDServiceException
     * @Description:
     */
    public List<RDRNotificationsDataVO> getRDRNotifications(String userId) throws RMDServiceException;
    
    /**
     * @Author: Raj.S(212687474)
     * @param notification
     * @return boolean
     * @throws RMDServiceException
     * @Description:
     */
    public boolean updateRDRNotification(RDRNotificationsSubmitRequestVO notification) throws RMDServiceException;
    
    /**
     * @Author: Raj.S(212687474)
     * @param notification
     * @return boolean
     * @throws RMDServiceException
     * @Description:
     */
    public boolean insertRDRNotification(RDRNotificationsSubmitRequestVO notification) throws RMDServiceException;
    
    /**
     * @Author:
     * @param strAssetId,strCustomerId,strGrpName
     * @return String
     * @throws RMDServiceException
     * @Description:
     */
    public String getHCAssetType(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param strAssetId,strCustomerId,strGrpName
     * @return String
     * @throws RMDServiceException
     * @Description:
     */
    public List<ElementVO> getAssetHCMPGroups(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage, String requestType, String assetType, String deviceName) throws RMDServiceException;

    /**
     * @Author:
     * @param strInput,strAssetType
     * @return String
     * @throws RMDServiceException
     * @Description:
     */
    public HealthCheckAttributeVO getSendMessageAttributes(String strInput, String strAssetType, String strLanguage,
            String typeOfUser, String vehObjId, String device) throws RMDServiceException;

    /**
     * @Author:
     * @param strInput,strAssetType
     * @return String
     * @throws RMDServiceException
     * @Description:
     */
    public Map<String, String> getCustrnhDetails(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param healthCheckSubmitEoaVO
     * @return Map<String, String>
     * @throws @Description:
     */
    public String submitHealthCheckRequest(HealthCheckSubmitEoaVO healthCheckSubmitEoaVO) throws RMDServiceException;

    /**
     * @Author:
     * @param viewReqHistoryEoaVo
     * @return List<ViewRespHistoryEoaVo>
     * @throws @Description:
     */
    public List<ViewRespHistoryEoaVo> viewRequestHistory(ViewReqHistoryEoaVo viewReqHistoryEoaVo)
            throws RMDServiceException;

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method fetches the device information.
     */
    public String getDeviceInfo(HealthCheckVO objHealthCheckVO) throws RMDServiceException;

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method saves the health check details.
     */
    public String saveHCDetails(HealthCheckVO objHealthCheckVO) throws RMDServiceException;

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method validates EGA HC.
     */
    public String validateEGAHC(HealthCheckVO objHealthCheckVO) throws RMDServiceException;

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method validates NT HC.
     */
    public String validateNTHC(HealthCheckVO objHealthCheckVO) throws RMDServiceException;

    /**
     * @Author:
     * @param String,String
     * @return String
     * @throws RMDServiceException
     * @Description:This method filters records based on type of user logged in
     */
    public List<String> filterRecords(String messageIdList, String filterCondition) throws RMDServiceException;

}

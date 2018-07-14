/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthCheckRequestDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.dao.intf
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
package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAttributeVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAvailableVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckSubmitEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewReqHistoryEoaVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewRespHistoryEoaVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsSubmitRequestVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface HealthCheckDAOIntf {
    /**
     * @Author:
     * @param strAssetNumber,strLanguage
     * @return HealthCheckVO
     * @throws RMDDAOException
     * @Description:
     */
    HealthCheckVO getRoadNumberDetails(String strAssetNumber, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param strAssetId,strCustomerId,strGrpName
     * @return HealthCheckAvailableVO
     * @throws RMDDAOException
     * @Description:
     */
    public HealthCheckAvailableVO IsHCAvailable(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDBOException;
    
    /**
     * @Author: Raj.S(212687474)
     * @param userId
     * @return RDRNotificationsDataVO
     * @throws RMDDAOException
     * @Description:
     */
    public List<RDRNotificationsDataVO> getRDRNotifications(String userId) throws RMDBOException;
    
    /**
     * @Author: Raj.S(212687474)
     * @param notification
     * @return boolean
     * @throws RMDDAOException
     * @Description:
     */
    public boolean updateRDRNotification(RDRNotificationsSubmitRequestVO notification) throws RMDBOException;

    /**
    * @Author: Raj.S(212687474)
    * @param notification
    * @return boolean
    * @throws RMDDAOException
    * @Description:
    */
   public boolean insertRDRNotification(RDRNotificationsSubmitRequestVO notification) throws RMDBOException;
    
    /**
     * @Author:
     * @param strAssetId,strCustomerId,strGrpName
     * @return List<ElementVO>
     * @throws RMDDAOException
     * @Description:
     */
    public List<ElementVO> getAssetHCMPGroups(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage, String requestType, String assetType, String deviceName) throws RMDBOException;

    /**
     * @Author:
     * @param strAssetId,strCustomerId,strGrpName
     * @return String
     * @throws RMDDAOException
     * @Description:
     */
    public String getHCAssetType(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strInput
     * @return HealthCheckAttributeVO
     * @throws RMDDAOException
     * @Description:
     */
    public HealthCheckAttributeVO getSendMessageAttributes(String strInput, String strAssetType, String strLanguage,
            String typeOfUser, String vehObjId, String device) throws RMDBOException;

    /**
     * @Author:
     * @param strInput
     * @return Map<String, String>
     * @throws RMDDAOException
     * @Description:
     */
    public Map<String, String> getCustrnhDetails(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param healthCheckSubmitEoaVO
     * @return String
     * @throws RMDDAOException
     * @Description:
     */
    public String submitHealthCheckRequest(HealthCheckSubmitEoaVO healthCheckSubmitEoaVO) throws RMDDAOException;

    /**
     * @Author:
     * @param healthCheckSubmitEoaVO
     * @return boolean
     * @throws RMDDAOException
     * @Description:
     */
    public boolean insertOutBndMsgHist(HealthCheckSubmitEoaVO healthCheckSubmitEoaVO, Session hibernateSession)
            throws RMDDAOException;

    /**
     * @Author:
     * @param healthCheckSubmitEoaVO
     * @return void
     * @throws RMDDAOException
     * @Description:
     */
    public void getConfigType(HealthCheckSubmitEoaVO healthCheckSubmitEoaVO, Session hibernateSession)
            throws RMDDAOException;

    /**
     * @Author:
     * @param viewReqHistoryEoaVo
     * @return ViewRespHistoryEoaVo
     * @throws RMDDAOException
     * @Description:
     */
    public List<ViewRespHistoryEoaVo> viewRequestHistory(ViewReqHistoryEoaVo viewReqHistoryEoaVo)
            throws RMDDAOException;

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method fetches the device information.
     */
    public String getDeviceInfo(HealthCheckVO objHealthCheckVO) throws RMDDAOException;

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method saves the HC details.
     */
    public String saveHCDetails(HealthCheckVO objHealthCheckVO) throws RMDDAOException;

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method validates EGA HC.
     */
    public String validateEGAHC(HealthCheckVO objHealthCheckVO) throws RMDDAOException;

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method validates NT HC.
     */
    public String validateNTHC(HealthCheckVO objHealthCheckVO) throws RMDDAOException;

    /**
     * @Author:
     * @param String,String
     * @return String
     * @throws RMDDAOException
     * @Description:This method filters records based on type of user logged in
     */
    public List<String> filterRecords(String messageIdList, String filterCondition) throws RMDDAOException;
}

/**
 * ============================================================
 * Classification: GE Confidential
 * File : CMUPassthroughDAOIntf.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.dao.intf;
 * Author : Capgemini.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2017 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.asset.service.valueobjects.CMUPassthroughVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March,08 2017
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface CMUPassthroughDAOIntf {

    /**
     * 
     * @param vehicleOjectIdList
     * @return List of CMUPassthroughVO messages to be put in CMU Pass-through
     *         MQ
     * @throws RMDDAOException
     */
    public List<CMUPassthroughVO> getCmuMQMessages(List<String> vehicleOjectIdList) throws RMDDAOException;

    /**
     * 
     * @return Map<String,String> : It has CMU MQ details.
     * @throws RMDDAOException
     */
    public Map<String, String> getCmuMQDetails() throws RMDDAOException;

    /**
     * 
     * @return List of list of Controller Config types for which XML messages to
     *         should be sent to CMU
     */
    public List<String> getCmuCtrlCfgList();


}

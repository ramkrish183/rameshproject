/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgServiceIntf.java
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


import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.ControllerConfigVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MdscStartUpControllersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: March,17 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public interface VehicleCfgServiceIntf {

    
    /**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDWebException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     * 
     */
    public String deleteVehicleCfgTemplate(
            VehicleCfgTemplateVO objVehicleCfgTemplateVO)
            throws RMDServiceException;
    
    public List<VehicleCfgVO> getVehicleBOMConfigs (String customer,String rnh,String roadNumber) throws RMDServiceException;
    public MdscStartUpControllersVO getMDSCStartUpControllersInfo(String customer,String rnh,String roadNumber) throws RMDServiceException;
    public String saveVehicleBOMConfigs(List<VehicleCfgVO> VehicleCfgTemplate,CaseInfoServiceVO objCaseInfoServiceVO,String isCaseVehicleCfg) throws RMDServiceException;
    public List<VehicleCfgTemplateVO> getVehicleCfgTemplates(String customer,String rnh,String roadNumber) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<ControllerConfigVO>
     * @param :
     * @throws :RMDServiceException
     * @Description: This method is Responsible for fetching controller configs
     * 
     */
    public List<ControllerConfigVO> getControllerConfigs()
            throws RMDServiceException;

    /**
     * @Author :
     * @return :List<VehicleCfgTemplateVO>
     * @param : String,String
     * @throws :RMDServiceException
     * @Description: This method is Responsible for fetching controller configs
     *               templates
     * 
     */
    public List<VehicleCfgTemplateVO> getControllerConfigTemplates(
            String cntrlCnfg, String cnfgFile) throws RMDServiceException;
    /**
     * @Author :
     * @return :String
     * @param : List<VehicleCfgTemplateVO>
     * @throws :RMDServiceException,SQLException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     * 
     */
    public String deleteVehicleCfg(List<VehicleCfgTemplateVO> arlVehicleCfgTemplateVO)throws RMDServiceException,SQLException;
    /**
     * @Author :
     * @return :List<ControllerConfigVO>
     * @param :
     * @throws :RMDServiceException
     * @Description: This method is Responsible for fetching lookup value tooltip
     * 
     */
    public List<GetSysLookupVO> getLookupValueTooltip()
            throws RMDServiceException;


}

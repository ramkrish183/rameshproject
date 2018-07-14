/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.bo.intf;
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
package com.ge.trans.eoa.services.asset.bo.intf;

import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.ControllerConfigVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MdscStartUpControllersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;

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
public interface VehicleCfgBOIntf {
    
    
    public List<VehicleCfgVO> getVehicleBOMConfigs (String customer,String rnh,String roadNumber) throws RMDBOException;
    public MdscStartUpControllersVO getMDSCStartUpControllersInfo(String customer,String rnh,String roadNumber) throws RMDBOException;
    public String saveVehicleBOMConfigs(List<VehicleCfgVO> VehicleCfgTemplate,CaseInfoServiceVO objCaseInfoServiceVO,String isCaseVehicleCfg) throws RMDBOException;
    public List<VehicleCfgTemplateVO> getVehicleCfgTemplates(String customer,String rnh,String roadNumber) throws RMDBOException;
    
    /**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDBOException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     * 
     */
    public String deleteVehicleCfgTemplate(
            VehicleCfgTemplateVO objVehicleCfgTemplateVO) throws RMDBOException;

    /**
     * @Author :
     * @return :List<ControllerConfigVO>
     * @param :
     * @throws :RMDBOException
     * @Description: This method is Responsible for fetching controller configs
     * 
     */
    public List<ControllerConfigVO> getControllerConfigs()
            throws RMDBOException;

    /**
     * @Author :
     * @return :List<VehicleCfgTemplateVO>
     * @param : String,String
     * @throws :RMDBOException
     * @Description: This method is Responsible for fetching controller configs
     *               templates
     * 
     */
    public List<VehicleCfgTemplateVO> getControllerConfigTemplates(
            String cntrlCnfg, String cnfgFile) throws RMDBOException;

    /**
     * @Author :
     * @return :String
     * @param : List<VehicleCfgTemplateVO>
     * @throws :RMDBOException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     * 
     */
    public String deleteVehicleCfg(
            List<VehicleCfgTemplateVO> arlVehicleCfgTemplateVO)
            throws RMDBOException, SQLException;
    /**
     * @Author :
     * @return :List<ControllerConfigVO>
     * @param :
     * @throws :RMDBOException
     * @Description: This method is Responsible for fetching lookup value tooltip
     * 
     */
    public List<GetSysLookupVO> getLookupValueTooltip()
            throws RMDBOException;
}

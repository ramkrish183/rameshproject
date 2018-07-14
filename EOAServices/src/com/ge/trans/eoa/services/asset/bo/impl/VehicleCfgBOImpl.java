/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgBOImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.bo.impl;
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
package com.ge.trans.eoa.services.asset.bo.impl;

import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.VehicleCfgBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.VehicleCfgDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.ControllerConfigVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MdscStartUpControllersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;

public class VehicleCfgBOImpl implements VehicleCfgBOIntf {

    private VehicleCfgDAOIntf objVehicleCfgDAOIntf;
    public static final RMDLogger LOG = RMDLogger.getLogger(VehicleCfgBOImpl.class);
    
    /**
     * @param objVehicleCfgDAOIntf
     */
    public VehicleCfgBOImpl(VehicleCfgDAOIntf objVehicleCfgDAOIntf) {
        this.objVehicleCfgDAOIntf = objVehicleCfgDAOIntf;
    }
    
    
    @Override
    public List<VehicleCfgVO> getVehicleBOMConfigs(String customer, String rnh,
            String roadNumber) throws RMDBOException {
        List<VehicleCfgVO> vehicleVO = null;
        try {
            if (customer != null && rnh != null && roadNumber != null) {

                vehicleVO = objVehicleCfgDAOIntf.getVehicleBOMConfigs(customer,
                        rnh, roadNumber);
            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return vehicleVO;
    }
    

    @Override
    public MdscStartUpControllersVO getMDSCStartUpControllersInfo(
            String customer, String rnh, String roadNumber)
            throws RMDBOException {
        
        MdscStartUpControllersVO mdscStartUpControllersVO = null;
        try {
            if (customer != null && rnh!=null && roadNumber !=null ) {
                
                mdscStartUpControllersVO = objVehicleCfgDAOIntf.getMDSCStartUpControllersInfo(customer, rnh, roadNumber);
                } 

        } catch (RMDDAOException e) {
            throw e;
        }
        return mdscStartUpControllersVO;
    }

    @Override
    public String saveVehicleBOMConfigs(List<VehicleCfgVO> vehicleCfgVO,
            CaseInfoServiceVO objCaseInfoServiceVO, String isCaseVehicleCfg)
            throws RMDBOException {
        String vehicleCfgVOStatus = null;
        try {
            vehicleCfgVOStatus = objVehicleCfgDAOIntf.saveVehicleBOMConfigs(
                    vehicleCfgVO, objCaseInfoServiceVO, isCaseVehicleCfg);
        } catch (RMDDAOException ex) {
            try {
                throw new RMDBOException(ex.getErrorDetail(), ex);
            } catch (RMDBOException e) {
            	 LOG.error(e); 
            }
        }
        return vehicleCfgVOStatus;
    }

    @Override
    public List<VehicleCfgTemplateVO> getVehicleCfgTemplates(String customer,
            String rnh, String roadNumber) throws RMDBOException {
        
        List<VehicleCfgTemplateVO> vehicleCfgTemplateVO = null;
        try {
            if (customer != null && rnh!=null && roadNumber !=null ) {
                
                vehicleCfgTemplateVO = objVehicleCfgDAOIntf.getVehicleCfgTemplates(customer, rnh, roadNumber);
                } 

        } catch (RMDDAOException e) {
            throw e;
        }
        return vehicleCfgTemplateVO;
    }
    

    /**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDWebException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     * 
     */
    @Override
    public String deleteVehicleCfgTemplate(
            VehicleCfgTemplateVO objVehicleCfgTemplateVO) throws RMDBOException {
        String result = null;
        try {
            
            if(RMDCommonConstants.RCI_CFG_FILE.equals(objVehicleCfgTemplateVO.getConfigFile())){
                result=objVehicleCfgDAOIntf.validateTemplateVehStatus(objVehicleCfgTemplateVO.getVehStatusObjId());
                if(!RMDCommonConstants.SUCCESS.equals(result))
                    return result;
            }
                result = objVehicleCfgDAOIntf
                        .deleteVehicleCfgTemplate(objVehicleCfgTemplateVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author :
     * @return :List<ControllerConfigVO>
     * @param :
     * @throws :RMDBOException
     * @Description: This method is Responsible for fetching controller configs
     * 
     */
    @Override
    public List<ControllerConfigVO> getControllerConfigs()
            throws RMDBOException {
        List<ControllerConfigVO> cnfgList = null;
        try {
            cnfgList = objVehicleCfgDAOIntf.getControllerConfigs();
        } catch (RMDDAOException e) {
            throw e;
        }
        return cnfgList;
    }

    /**
     * @Author :
     * @return :List<VehicleCfgTemplateVO>
     * @param : String,String
     * @throws :RMDBOException
     * @Description: This method is Responsible for fetching controller configs
     *               templates
     * 
     */
    @Override
    public List<VehicleCfgTemplateVO> getControllerConfigTemplates(
            String cntrlCnfg, String cnfgFile) throws RMDBOException {
        List<VehicleCfgTemplateVO> tempList = null;
        try {
            tempList = objVehicleCfgDAOIntf.getControllerConfigTemplates(
                    cntrlCnfg, cnfgFile);
        } catch (RMDDAOException e) {
            throw e;
        }
        return tempList;
    }

    /**
     * @Author :
     * @return :String
     * @param : List<VehicleCfgTemplateVO>
     * @throws :RMDBOException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     * 
     */
    @Override
    public String deleteVehicleCfg(
            List<VehicleCfgTemplateVO> arlVehicleCfgTemplateVO)
            throws RMDBOException, SQLException {
        String result = null;
        try {
            result = objVehicleCfgDAOIntf
                    .deleteVehicleCfg(arlVehicleCfgTemplateVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return result;
    }
    /**
     * @Author :
     * @return :List<ControllerConfigVO>
     * @param :
     * @throws :RMDBOException
     * @Description: This method is Responsible for fetching Lookup value tooltip
     * 
     */
    @Override
    public List<GetSysLookupVO> getLookupValueTooltip()
            throws RMDBOException {
        List<GetSysLookupVO> toolTipList = null;
        try {
        	toolTipList = objVehicleCfgDAOIntf.getLookupValueTooltip();
        } catch (RMDDAOException e) {
            throw e;
        }
        return toolTipList;
    }
}

/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgServiceImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.service.impl;
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
package com.ge.trans.eoa.services.asset.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.VehicleCfgBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.VehicleCfgServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.ControllerConfigVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MdscStartUpControllersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March,17 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/

public class VehicleCfgServiceImpl implements VehicleCfgServiceIntf {

    private VehicleCfgBOIntf objVehicleCfgBOIntf;

    /**
     * @param objVehicleCfgBOIntf
     */
    public VehicleCfgServiceImpl(VehicleCfgBOIntf objVehicleCfgBOIntf) {
        this.objVehicleCfgBOIntf = objVehicleCfgBOIntf;
    }

    @Override
    public List<VehicleCfgVO> getVehicleBOMConfigs(String customer, String rnh, String roadNumber)
            throws RMDServiceException {

        List<VehicleCfgVO> vehicleVO = null;
        try {
            vehicleVO = objVehicleCfgBOIntf.getVehicleBOMConfigs(customer, rnh, roadNumber);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return vehicleVO;
    }

    @Override
    public MdscStartUpControllersVO getMDSCStartUpControllersInfo(String customer, String rnh, String roadNumber)
            throws RMDServiceException {

        MdscStartUpControllersVO mdscStartUpControllersVO = null;
        try {
            mdscStartUpControllersVO = objVehicleCfgBOIntf.getMDSCStartUpControllersInfo(customer, rnh, roadNumber);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return mdscStartUpControllersVO;
    }

    @Override
    public String saveVehicleBOMConfigs(List<VehicleCfgVO> vehicleCfgVO,
            CaseInfoServiceVO objCaseInfoServiceVO, String isCaseVehicleCfg)
            throws RMDServiceException {
        String vehicleCfgVOStatus = null;
        try {
            vehicleCfgVOStatus = objVehicleCfgBOIntf.saveVehicleBOMConfigs(
                    vehicleCfgVO, objCaseInfoServiceVO, isCaseVehicleCfg);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return vehicleCfgVOStatus;
    }
    


    @Override
    public List<VehicleCfgTemplateVO> getVehicleCfgTemplates(String customer,
            String rnh, String roadNumber) throws RMDServiceException {
        List<VehicleCfgTemplateVO> vehicleCfgTemplateVO = null;
        try {
            vehicleCfgTemplateVO = objVehicleCfgBOIntf.getVehicleCfgTemplates(customer, rnh, roadNumber);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return vehicleCfgTemplateVO;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            VehicleCfgTemplateVO
     * @throws :RMDServiceException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     */
    @Override
    public String deleteVehicleCfgTemplate(VehicleCfgTemplateVO objVehicleCfgTemplateVO) throws RMDServiceException {
        String result = null;
        try {
            result = objVehicleCfgBOIntf.deleteVehicleCfgTemplate(objVehicleCfgTemplateVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author :
     * @return :List<ControllerConfigVO>
     * @param :
     * @throws :RMDWebException
     * @Description: This method is Responsible for fetching controller configs
     */
    @Override
    public List<ControllerConfigVO> getControllerConfigs() throws RMDServiceException {
        List<ControllerConfigVO> cnfgList = null;
        try {
            cnfgList = objVehicleCfgBOIntf.getControllerConfigs();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return cnfgList;
    }

    /**
     * @Author :
     * @return :List<VehicleCfgTemplateVO>
     * @param :
     *            String,String
     * @throws :RMDWebException
     * @Description: This method is Responsible for fetching controller configs
     *               templates
     */
    @Override
    public List<VehicleCfgTemplateVO> getControllerConfigTemplates(String cntrlCnfg, String cnfgFile)
            throws RMDServiceException {
        List<VehicleCfgTemplateVO> tempList = null;
        try {
            tempList = objVehicleCfgBOIntf.getControllerConfigTemplates(cntrlCnfg, cnfgFile);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return tempList;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<VehicleCfgTemplateVO>
     * @throws :RMDWebException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     */
    @Override
    public String deleteVehicleCfg(List<VehicleCfgTemplateVO> arlVehicleCfgTemplateVO)
            throws RMDServiceException, SQLException {
        String result = null;
        try {
            result = objVehicleCfgBOIntf.deleteVehicleCfg(arlVehicleCfgTemplateVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }
    /**
     * @Author :
     * @return :List<ControllerConfigVO>
     * @param :
     * @throws :RMDWebException
     * @Description: This method is Responsible for fetching lookup value tooltip
     */
    @Override
    public List<GetSysLookupVO> getLookupValueTooltip() throws RMDServiceException {
        List<GetSysLookupVO> toolTipList = null;
        try {
        	toolTipList = objVehicleCfgBOIntf.getLookupValueTooltip();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return toolTipList;
    }
}

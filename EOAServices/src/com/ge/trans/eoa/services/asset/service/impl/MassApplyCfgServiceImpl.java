/**
 * ============================================================
 * Classification: GE Confidential
 * File : MassApplyCfgServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.assets.resources;
 * Author : Capgemini India
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.MassApplyCfgBOIntf;
/*******************************************************************************
 * 
 * @Author : Capgemini
 * @Version : 1.0
 * @Date Created: Sep 29, 2016
 * @Date Modified : Sep 29, 2016
 * @Modified By :
 * @Contact :
 * @Description : 
 * @History :
 * 
 ******************************************************************************/
import com.ge.trans.eoa.services.asset.service.intf.MassApplyCfgServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyEFICfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ConfigSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MassApplyCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.SelectedCfgTemplatesVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VerifyCfgTemplateVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class MassApplyCfgServiceImpl implements MassApplyCfgServiceIntf {

	private MassApplyCfgBOIntf objMassApplyCfgBOIntf;

	public MassApplyCfgServiceImpl(MassApplyCfgBOIntf objMassApplyCfgBOIntf) {
		this.objMassApplyCfgBOIntf = objMassApplyCfgBOIntf;
	}

	
	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type EDP.
	 * 
	 */

	@Override
	public List<MassApplyCfgVO> getEDPConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDServiceException {
		List<MassApplyCfgVO> arlEDPConfigs = null;
		try {
			arlEDPConfigs = objMassApplyCfgBOIntf
					.getEDPConfigs(objConfigSearchVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlEDPConfigs;
	}

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FFD.
	 * 
	 */

	@Override
	public List<MassApplyCfgVO> getFFDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDServiceException {
		List<MassApplyCfgVO> arlFFDConfigs = null;
		try {
			arlFFDConfigs = objMassApplyCfgBOIntf
					.getFFDConfigs(objConfigSearchVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlFFDConfigs;
	}

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FRD.
	 * 
	 */

	@Override
	public List<MassApplyCfgVO> getFRDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDServiceException {
		List<MassApplyCfgVO> arlFRDConfigs = null;
		try {
			arlFRDConfigs = objMassApplyCfgBOIntf
					.getFRDConfigs(objConfigSearchVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlFRDConfigs;
	}

	/**
	 * @Author :
	 * @return :List<VerifyCfgTempaltesVO>
	 * @param :SelectedCfgTemplatesVO objSelectedCfgTemplatesVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	@Override
	public List<VerifyCfgTemplateVO> verifyConfigTemplates(
			SelectedCfgTemplatesVO objSelectedCfgTemplatesVO)
			throws RMDServiceException {
		List<VerifyCfgTemplateVO> arlVerifyCfgTempaltesVOs = null;
		try {
			arlVerifyCfgTempaltesVOs = objMassApplyCfgBOIntf
					.verifyConfigTemplates(objSelectedCfgTemplatesVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlVerifyCfgTempaltesVOs;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String ctrlCfgObjId
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	@Override
	public List<String> getOnboardSoftwareVersion(String ctrlCfgObjId)
			throws RMDServiceException {
		List<String> arlOnboardSoftwareVersion = null;
		try {
			arlOnboardSoftwareVersion = objMassApplyCfgBOIntf
					.getOnboardSoftwareVersion(ctrlCfgObjId);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlOnboardSoftwareVersion;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String cfgType, String templateObjId, String ctrlCfgObjId
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */

	@Override
	public List<String> getCfgTemplateVersions(String cfgType,
			String templateObjId, String ctrlCfgObjId)
			throws RMDServiceException {
		List<String> arlCfgTemplateVersions = null;
		try {
			arlCfgTemplateVersions = objMassApplyCfgBOIntf
					.getCfgTemplateVersions(cfgType, templateObjId,
							ctrlCfgObjId);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlCfgTemplateVersions;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :AssetSearchVO objAssetSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */

	@Override
	public List<String> getSpecificAssetNumbers(AssetSearchVO objAssetSearchVO)
			throws RMDServiceException {
		List<String> arlAssetNumbers = null;
		try {
			arlAssetNumbers = objMassApplyCfgBOIntf
					.getSpecificAssetNumbers(objAssetSearchVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlAssetNumbers;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for applying the config
	 *               templates to the selected assets.
	 * 
	 */
	@Override
	public List<String> applyCfgTemplates(
			ApplyCfgTemplateVO objApplyCfgTemplateVO)
			throws RMDServiceException {
		List<String> arlLogMessages = null;
		try {
			arlLogMessages = objMassApplyCfgBOIntf
					.applyCfgTemplates(objApplyCfgTemplateVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlLogMessages;
	}
	
	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for applying the EFI config
	 *               templates to the selected assets.
	 * 
	 */
	@Override
	public List<String> applyEFIConfig(ApplyEFICfgVO objApplyEFICfgVO)
			throws RMDServiceException {
		List<String> arlLogMessages = null;
		try {
			arlLogMessages = objMassApplyCfgBOIntf
					.applyEFIConfig(objApplyEFICfgVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlLogMessages;
	}
	
	/**
     * @Author :
     * @return :List<MassApplyCfgVO>
     * @param :ConfigSearchVO objConfigSearchVO
     * @throws :RMDServiceException
     * @Description: This method is Responsible for fetching AHC config templates
     * 
     */

    @Override
    public List<MassApplyCfgVO> getAHCConfigs(ConfigSearchVO objConfigSearchVO)
            throws RMDServiceException {
        List<MassApplyCfgVO> arlAHCConfigs = null;
        try {
            arlAHCConfigs = objMassApplyCfgBOIntf
                    .getAHCConfigs(objConfigSearchVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlAHCConfigs;
    }

    /**
     * @Author :
     * @return :List<MassApplyCfgVO>
     * @param :ConfigSearchVO objConfigSearchVO
     * @throws :RMDServiceException
     * @Description: This method is Responsible for fetching RCI config templates
     * 
     */
	@Override
	public List<MassApplyCfgVO> getRCIConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDServiceException {
		  List<MassApplyCfgVO> arlRCIConfigs = null;
	        try {
	            arlRCIConfigs = objMassApplyCfgBOIntf
	                    .getRCIConfigs(objConfigSearchVO);
	        } catch (RMDBOException ex) {
	            throw new RMDServiceException(ex.getErrorDetail(), ex);
	        }
	        return arlRCIConfigs;
	}

	/**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDServiceException
     * @Description: This method is Responsible to re-apply vehicle
     *               Configuration Template.
     * 
     */
    @Override
    public String reApplyTemplate(VehicleCfgTemplateVO objCfgTemplateVO)
            throws RMDServiceException {
        String result=null;
        try{
            result=objMassApplyCfgBOIntf
                    .reApplyTemplate(objCfgTemplateVO);
        }catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }


    /**
     * @Author :
     * @return :String
     * @param : String vehStausObjId,String userId
     * @throws :RMDServiceException
     * @Description: This method is Responsible to re-notify vehicle
     *               Configuration Template.
     * 
     */
    @Override
    public String reNotifyTemplateAssociation(String vehStausObjId,
            String userId) throws RMDServiceException {
        String result = RMDCommonConstants.EMPTY_STRING;
        try{
            result = objMassApplyCfgBOIntf.reNotifyTemplateAssociation(vehStausObjId,userId);
        }catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author :
     * @return :String
     * @param : String tempObjId,String templateNo,String versionNo, String userId,String status
     * @throws :RMDServiceException
     * @Description: This method is Responsible to update the RCI Template
     * 
     */

    @Override
    public String updateRCITemplate(String tempObjId,String templateNo,String versionNo, String userId,String status)
            throws RMDServiceException {
        String result = RMDCommonConstants.EMPTY_STRING;
        try{
            result = objMassApplyCfgBOIntf.updateRCITemplate(tempObjId,templateNo,versionNo,userId,status);
        }catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }
}

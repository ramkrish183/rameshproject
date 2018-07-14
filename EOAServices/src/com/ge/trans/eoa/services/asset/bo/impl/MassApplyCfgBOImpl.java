/**
 * ============================================================
 * Classification: GE Confidential
 * File : MassApplyCfgBOImpl.java
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
package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import com.ge.trans.eoa.services.asset.bo.intf.MassApplyCfgBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.MassApplyCfgDAOIntf;
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
import com.ge.trans.rmd.exception.RMDConcurrencyException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

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
public class MassApplyCfgBOImpl implements MassApplyCfgBOIntf {

	private MassApplyCfgDAOIntf objMassApplyCfgDAOIntf;

	public MassApplyCfgBOImpl(MassApplyCfgDAOIntf objMassApplyCfgDAOIntf) {
		this.objMassApplyCfgDAOIntf = objMassApplyCfgDAOIntf;
	}


	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type EDP.
	 * 
	 */

	@Override
	public List<MassApplyCfgVO> getEDPConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDBOException {
		List<MassApplyCfgVO> arlEDPConfigs = null;
		try {
			arlEDPConfigs = objMassApplyCfgDAOIntf
					.getEDPConfigs(objConfigSearchVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlEDPConfigs;
	}

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FFD.
	 * 
	 */
	@Override
	public List<MassApplyCfgVO> getFFDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDBOException {
		List<MassApplyCfgVO> arlFFDConfigs = null;
		try {
			arlFFDConfigs = objMassApplyCfgDAOIntf
					.getFFDConfigs(objConfigSearchVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlFFDConfigs;
	}

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FRD.
	 * 
	 */
	@Override
	public List<MassApplyCfgVO> getFRDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDBOException {
		List<MassApplyCfgVO> arlFRDConfigs = null;
		try {
			arlFRDConfigs = objMassApplyCfgDAOIntf
					.getFRDConfigs(objConfigSearchVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlFRDConfigs;
	}

	/**
	 * @Author :
	 * @return :List<VerifyCfgTempaltesVO>
	 * @param :SelectedCfgTemplatesVO objSelectedCfgTemplatesVO
	 * @throws InterruptedException
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	@Override
	public List<VerifyCfgTemplateVO> verifyConfigTemplates(
			SelectedCfgTemplatesVO objSelectedCfgTemplatesVO)
			throws RMDBOException {
		List<VerifyCfgTemplateVO> arlVerifyCfgTempaltesVOs = new ArrayList<VerifyCfgTemplateVO>();
		Future<List<VerifyCfgTemplateVO>> futureArlEDPTemplates = null;
		Future<List<VerifyCfgTemplateVO>> futureArlFFDTemplates = null;
		Future<List<VerifyCfgTemplateVO>> futureFRDTemplates = null;
		Future<List<VerifyCfgTemplateVO>> futureArlAHCTemplates = null;
		Future<List<VerifyCfgTemplateVO>> futureArlRCITemplates = null;
		Future<VerifyCfgTemplateVO> futureEFITemplate = null;
		List<VerifyCfgTemplateVO> arlEDPTemplates = null;
		List<VerifyCfgTemplateVO> arlFFDTemplates = null;
		List<VerifyCfgTemplateVO> arlfrdTemplates = null;
		List<VerifyCfgTemplateVO> arlAHCTemplates = null;
		List<VerifyCfgTemplateVO> arlRCITemplates = null;
		VerifyCfgTemplateVO efiTemplate = null;
		try {
			if (RMDCommonUtility.isCollectionNotEmpty(objSelectedCfgTemplatesVO.getEdpObjIdList())) {
				futureArlEDPTemplates = objMassApplyCfgDAOIntf
						.getEDPTemplates(objSelectedCfgTemplatesVO
								.getEdpObjIdList());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(objSelectedCfgTemplatesVO.getFfdObjIdList())) {
				futureArlFFDTemplates = objMassApplyCfgDAOIntf
						.getFFDTemplates(objSelectedCfgTemplatesVO
								.getFfdObjIdList());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(objSelectedCfgTemplatesVO.getFrdObjId())) {
				futureFRDTemplates = objMassApplyCfgDAOIntf
						.getFRDTemplates(objSelectedCfgTemplatesVO
								.getFrdObjId());
			}
			
			if (!RMDCommonUtility.isNullOrEmpty(objSelectedCfgTemplatesVO
					.getEfiObjId())) {
				futureEFITemplate = objMassApplyCfgDAOIntf
						.getEFIConfigTempaltes(objSelectedCfgTemplatesVO
								.getEfiObjId());
			}
			
			if (RMDCommonUtility.isCollectionNotEmpty(objSelectedCfgTemplatesVO.getAhcObjId())) {
			    futureArlAHCTemplates = objMassApplyCfgDAOIntf
                        .getAHCTemplates(objSelectedCfgTemplatesVO
                                .getAhcObjId());
            }
			
			if (RMDCommonUtility.isCollectionNotEmpty(objSelectedCfgTemplatesVO.getRciObjId())) {
                futureArlRCITemplates = objMassApplyCfgDAOIntf
                        .getRCITemplates(objSelectedCfgTemplatesVO
                                .getRciObjId());
            }

			if (null != futureArlEDPTemplates) {
				arlEDPTemplates = futureArlEDPTemplates.get();
				arlVerifyCfgTempaltesVOs.addAll(arlEDPTemplates);
			}
			if (null != futureArlFFDTemplates) {
				arlFFDTemplates = futureArlFFDTemplates.get();
				arlVerifyCfgTempaltesVOs.addAll(arlFFDTemplates);
			}
			if (null != futureFRDTemplates) {
				arlfrdTemplates = futureFRDTemplates.get();
				arlVerifyCfgTempaltesVOs.addAll(arlfrdTemplates);
			}
			if (null != futureEFITemplate) {
				efiTemplate = futureEFITemplate.get();
				arlVerifyCfgTempaltesVOs.add(efiTemplate);
			}
			if (null != futureArlAHCTemplates) {
			    arlAHCTemplates = futureArlAHCTemplates.get();
                arlVerifyCfgTempaltesVOs.addAll(arlAHCTemplates);
            }
			if (null != futureArlRCITemplates) {
                arlRCITemplates = futureArlRCITemplates.get();
                arlVerifyCfgTempaltesVOs.addAll(arlRCITemplates);
            }
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		} catch (Exception ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDCommonConstants.BOEXCEPTION);
			throw new RMDBOException(errorCode, new String[] {},
					ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
		}
		return arlVerifyCfgTempaltesVOs;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String ctrlCfgObjId
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	@Override
	public List<String> getOnboardSoftwareVersion(String ctrlCfgObjId)
			throws RMDBOException {
		List<String> arlOnboardSoftwareVersion = null;
		try {
			arlOnboardSoftwareVersion = objMassApplyCfgDAOIntf
					.getOnboardSoftwareVersion(ctrlCfgObjId);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlOnboardSoftwareVersion;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String cfgType, String templateObjId, String ctrlCfgObjId
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */

	@Override
	public List<String> getCfgTemplateVersions(String cfgType,
			String templateObjId, String ctrlCfgObjId)
			throws RMDBOException {
		List<String> arlCfgTemplateVersions = null;
		try {
			arlCfgTemplateVersions = objMassApplyCfgDAOIntf
					.getCfgTemplateVersions(cfgType, templateObjId,
							ctrlCfgObjId);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlCfgTemplateVersions;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :AssetSearchVO objAssetSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */

	@Override
	public List<String> getSpecificAssetNumbers(AssetSearchVO objAssetSearchVO)
			throws RMDBOException {
		List<String> arlAssetNumbers = null;
		try {
			arlAssetNumbers = objMassApplyCfgDAOIntf
					.getSpecificAssetNumbers(objAssetSearchVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlAssetNumbers;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for applying the config
	 *               templates to the selected assets.
	 * 
	 */
	@Override
	public List<String> applyCfgTemplates(
			ApplyCfgTemplateVO objApplyCfgTemplateVO)
			throws RMDBOException {
		List<String> arlLogMessages = null;
		try {
			arlLogMessages = objMassApplyCfgDAOIntf
					.applyCfgTemplates(objApplyCfgTemplateVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlLogMessages;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for applying the EFI config
	 *               templates to the selected assets.
	 * 
	 */
	@Override
	public List<String> applyEFIConfig(ApplyEFICfgVO objApplyEFICfgVO)
			throws RMDBOException {
		List<String> arlLogMessages = null;
		try {
			arlLogMessages = objMassApplyCfgDAOIntf
					.applyEFIConfig(objApplyEFICfgVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlLogMessages;
	}
	
	/**
     * @Author :
     * @return :List<MassApplyCfgVO>
     * @param :ConfigSearchVO objConfigSearchVO
     * @throws :RMDBOException
     * @Description: This method is Responsible for fetching AHC config templates
     * 
     */
	@Override
    public List<MassApplyCfgVO> getAHCConfigs(ConfigSearchVO objConfigSearchVO)
            throws RMDBOException {
        List<MassApplyCfgVO> arlAHCConfigs = null;
        try {
            arlAHCConfigs = objMassApplyCfgDAOIntf
                    .getAHCConfigs(objConfigSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlAHCConfigs;
    }


	@Override
	public List<MassApplyCfgVO> getRCIConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDBOException {
		List<MassApplyCfgVO> arlRCIConfigs = null;
		try {
			arlRCIConfigs = objMassApplyCfgDAOIntf
					.getRCIConfigs(objConfigSearchVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		return arlRCIConfigs;
	}

	/**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDBOException
     * @Description: This method is Responsible to re-apply vehicle
     *               Configuration Template.
     * 
     */
    @Override
    public String reApplyTemplate(VehicleCfgTemplateVO objCfgTemplateVO)
            throws RMDBOException {
        String result=null;
        try{
            result=objMassApplyCfgDAOIntf.reApplyTemplate(objCfgTemplateVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return result;
    }


    /**
     * @Author :
     * @return :String
     * @param : String vehStausObjId,String userId
     * @throws :RMDBOException
     * @Description: This method is Responsible to re-notify vehicle
     *               Configuration Template.
     * 
     */
    @Override
    public String reNotifyTemplateAssociation(String vehStausObjId,
            String userId) throws RMDBOException {
        String result=RMDCommonConstants.EMPTY_STRING;
        try{
            result=objMassApplyCfgDAOIntf.reNotifyTemplateAssociation(vehStausObjId,userId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return result;
    }


    /**
     * @Author :
     * @return :String
     * @param : String tempObjId,String templateNo,String versionNo, String userId,String status
     * @throws :RMDBOException
     * @Description: This method is Responsible to update the RCI Template
     * 
     */
    @Override
    public String updateRCITemplate(String tempObjId,String templateNo,String versionNo, String userId,String status)
            throws RMDBOException {
        String result=RMDCommonConstants.EMPTY_STRING;
        try{
            result=objMassApplyCfgDAOIntf.updateRCITemplate(tempObjId,templateNo,versionNo,userId,status);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return result;
    }
	
}

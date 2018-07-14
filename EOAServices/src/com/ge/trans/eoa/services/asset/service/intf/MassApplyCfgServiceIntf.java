/**
 * ============================================================
 * Classification: GE Confidential
 * File : MassApplyCfgServiceIntf.java
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

package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyEFICfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ConfigSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MassApplyCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.SelectedCfgTemplatesVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VerifyCfgTemplateVO;
import com.ge.trans.rmd.exception.RMDServiceException;




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
public interface MassApplyCfgServiceIntf {

	
	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type EDP.
	 * 
	 */
	public List<MassApplyCfgVO> getEDPConfigs(ConfigSearchVO objConfigSearchVO) throws RMDServiceException;

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FFD.
	 * 
	 */
	public List<MassApplyCfgVO> getFFDConfigs(ConfigSearchVO objConfigSearchVO) throws RMDServiceException;

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FFD.
	 * 
	 */
	public List<MassApplyCfgVO> getFRDConfigs(ConfigSearchVO objConfigSearchVO) throws RMDServiceException;
	
	/**
	 * @Author :
	 * @return :List<VerifyCfgTempaltesVO>
	 * @param :SelectedCfgTemplatesVO objSelectedCfgTemplatesVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public List<VerifyCfgTemplateVO> verifyConfigTemplates(
			SelectedCfgTemplatesVO objSelectedCfgTemplatesVO)
			throws RMDServiceException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String ctrlCfgObjId
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching software Versions.
	 * 
	 */
	public List<String> getOnboardSoftwareVersion(String ctrlCfgObjId)
			throws RMDServiceException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String cfgType, String templateObjId, String ctrlCfgObjId
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching template versions.
	 * 
	 */
	public List<String> getCfgTemplateVersions(String cfgType,
			String templateObjId, String ctrlCfgObjId)
			throws RMDServiceException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :AssetSearchVO objAssetSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching specific
	 *               assetNumbers
	 * 
	 */
	public List<String> getSpecificAssetNumbers(AssetSearchVO objAssetSearchVO)
			throws RMDServiceException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for applying the config
	 *               templates to the selected assets.
	 * 
	 */
	
	public List<String> applyCfgTemplates(ApplyCfgTemplateVO objApplyCfgTemplateVO) throws RMDServiceException;
	
	/**
	 * @Author :
	 * @return :List<Strings>
	 * @param : ApplyEFICfgVO objApplyEFICfgVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for applying EFI Configuration.
	 * 
	 */

	public List<String> applyEFIConfig(ApplyEFICfgVO objApplyEFICfgVO)
			throws RMDServiceException;

	/**
     * @Author :
     * @return :List<MassApplyCfgVO>
     * @param :ConfigSearchVO objConfigSearchVO
     * @throws :RMDServiceException
     * @Description: This method is Responsible for fetching AHC config templates
     * 
     */
    public List<MassApplyCfgVO> getAHCConfigs(ConfigSearchVO objConfigSearchVO) throws RMDServiceException;
	
	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type RCI.
	 * 
	 */
	public List<MassApplyCfgVO> getRCIConfigs(ConfigSearchVO objConfigSearchVO) throws RMDServiceException;

	/**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDServiceException
     * @Description: This method is Responsible to re-apply vehicle
     *               Configuration Template.
     * 
     */
    public String reApplyTemplate(VehicleCfgTemplateVO objCfgTemplateVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param : String vehStausObjId,String userId
     * @throws :RMDServiceException
     * @Description: This method is Responsible to re-notify vehicle
     *               Configuration Template.
     * 
     */
    public String reNotifyTemplateAssociation(String vehStausObjId,String userId) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param : String tempObjId,String templateNo,String versionNo, String userId,Stringstatus
     * @throws :RMDServiceException
     * @Description: This method is Responsible to update the RCI Template
     * 
     */
    public String updateRCITemplate(String tempObjId, String templateNo,
            String versionNo, String userId,String status) throws RMDServiceException;

}

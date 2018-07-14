/**
 * ============================================================
 * Classification: GE Confidential
 * File : MassApplyCfgBOIntf.java
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
package com.ge.trans.eoa.services.asset.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyEFICfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ConfigSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MassApplyCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.SelectedCfgTemplatesVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VerifyCfgTemplateVO;
import com.ge.trans.rmd.exception.RMDBOException;

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
public interface MassApplyCfgBOIntf {

	

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type EDP.
	 * 
	 */
	public List<MassApplyCfgVO> getEDPConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FFD.
	 * 
	 */
	public List<MassApplyCfgVO> getFFDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FRD.
	 * 
	 */
	public List<MassApplyCfgVO> getFRDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<VerifyCfgTempaltesVO>
	 * @param :SelectedCfgTemplatesVO objSelectedCfgTemplatesVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public List<VerifyCfgTemplateVO> verifyConfigTemplates(
			SelectedCfgTemplatesVO objSelectedCfgTemplatesVO)
			throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String ctrlCfgObjId
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public List<String> getOnboardSoftwareVersion(String ctrlCfgObjId)
			throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String cfgType, String templateObjId, String ctrlCfgObjId
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public List<String> getCfgTemplateVersions(String cfgType,
			String templateObjId, String ctrlCfgObjId) throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :AssetSearchVO objAssetSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public List<String> getSpecificAssetNumbers(AssetSearchVO objAssetSearchVO)
			throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for applying the config
	 *               templates to the selected assets.
	 * 
	 */

	public List<String> applyCfgTemplates(
			ApplyCfgTemplateVO objApplyCfgTemplateVO) throws RMDBOException;

	
	/**
	 * @Author :
	 * @return :List<Strings>
	 * @param : ApplyEFICfgVO objApplyEFICfgVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for applying EFI Configuration.
	 * 
	 */

	public List<String> applyEFIConfig(ApplyEFICfgVO objApplyEFICfgVO)
			throws RMDBOException;

	/**
     * @Author :
     * @return :List<MassApplyCfgVO>
     * @param :ConfigSearchVO objConfigSearchVO
     * @throws :RMDBOException
     * @Description: This method is Responsible for fetching AHC config templates
     * 
     */
    public List<MassApplyCfgVO> getAHCConfigs(ConfigSearchVO objConfigSearchVO) throws RMDBOException;
    
    /**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type RCI.
	 * 
	 */
	public List<MassApplyCfgVO> getRCIConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDBOException;

	/**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDBOException
     * @Description: This method is Responsible to re-apply vehicle
     *               Configuration Template.
     * 
     */
    public String reApplyTemplate(VehicleCfgTemplateVO objCfgTemplateVO) throws RMDBOException;

    /**
     * @Author :
     * @return :String
     * @param : String vehStausObjId,String userId
     * @throws :RMDBOException
     * @Description: This method is Responsible to re-notify vehicle
     *               Configuration Template.
     * 
     */
    public String reNotifyTemplateAssociation(String vehStausObjId,
            String userId) throws RMDBOException;
    
    /**
     * @Author :
     * @return :String
     * @param : String tempObjId,String templateNo,String versionNo, String userId,String status
     * @throws :RMDBOException
     * @Description: This method is Responsible to update the RCI Template
     * 
     */
    public String updateRCITemplate(String tempObjId, String templateNo,
            String versionNo, String userId,String status) throws RMDBOException;;

}

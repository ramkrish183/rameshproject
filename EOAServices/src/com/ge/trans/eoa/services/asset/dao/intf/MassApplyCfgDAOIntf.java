/**
 * ============================================================
\ * Classification: GE Confidential
 * File : MassApplyCfgBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.dao.intf;
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
package com.ge.trans.eoa.services.asset.dao.intf;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyEFICfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CfgAssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ConfigSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MassApplyCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.TemplateInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VerifyCfgTemplateVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * 
 * @Author : Capgemini
 * @Version : 1.0
 * @Date Created: Sep 29, 2016
 * @Date Modified : Feb 6, 2017
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public interface MassApplyCfgDAOIntf {

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type EDP.
	 * 
	 */
	public List<MassApplyCfgVO> getEDPConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDDAOException;

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FFD.
	 * 
	 */
	public List<MassApplyCfgVO> getFFDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDDAOException;

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FRD.
	 * 
	 */
	public List<MassApplyCfgVO> getFRDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDDAOException;

	/**
	 * @Author :
	 * @return :Future<List<VerifyCfgTempaltesVO>>
	 * @param :List<String> edpObjIdList
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public Future<List<VerifyCfgTemplateVO>> getEDPTemplates(
			List<String> edpObjIdList) throws RMDDAOException;

	/**
	 * @Author :
	 * @return :Future<List<VerifyCfgTemplateVO>>
	 * @param :List<String> ffdObjIdList
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public Future<List<VerifyCfgTemplateVO>> getFFDTemplates(
			List<String> ffdObjIdList) throws RMDDAOException;

	/**
	 * @Author :
	 * @return :Future<List<VerifyCfgTemplateVO>>
	 * @param :String frdObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public Future<List<VerifyCfgTemplateVO>> getFRDTemplates(
			List<String> frdObjId) throws RMDDAOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String ctrlCfgObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public List<String> getOnboardSoftwareVersion(String ctrlCfgObjId)
			throws RMDDAOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String cfgType, String templateObjId, String ctrlCfgObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public List<String> getCfgTemplateVersions(String cfgType,
			String templateObjId, String ctrlCfgObjId) throws RMDDAOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :AssetSearchVO objAssetSearchVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	public List<String> getSpecificAssetNumbers(AssetSearchVO objAssetSearchVO)
			throws RMDDAOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for applying the config
	 *               templates to the selected assets.
	 * 
	 */
	public List<String> applyCfgTemplates(
			ApplyCfgTemplateVO objApplyCfgTemplateVO) throws RMDDAOException;

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO,List<VehicleDetailsVO>
	 *        arlVehicleDetailsVOs
	 * @throws SQLException
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for applying the config
	 *               templates to the selected assets.
	 * 
	 */

	public List<String> processContentsForApply(
			ApplyCfgTemplateVO objApplyCfgTemplateVO,
			List<VehicleDetailsVO> arlVehicleDetailsVOs)
			throws RMDDAOException, SQLException;

	
	
	/**
	 * @Author :
	 * @return :String
	 * @param : List<Integer> vehicleObjId, String userName, List<Integer>
	 *        cfgDefObjId
	 * @throws SQLException 
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for Applying templates of type
	 *               EDP.
	 * 
	 */

	
	public Future<String> createNewMTMVehRecordForEdp(List<Integer> vehicleObjId,
			String userName, List<Integer> cfgDefObjId) throws RMDDAOException, SQLException;

	/**
	 * @Author :
	 * @return :int
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching EFI Records
	 * 
	 */
	Future<VerifyCfgTemplateVO> getEFIConfigTempaltes(String efiObjId)
			throws RMDDAOException;

	/**
	 * @Author :
	 * @return :List<Strings>
	 * @param : ApplyEFICfgVO objApplyEFICfgVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for applying EFI Configuration.
	 * 
	 */

	public List<String> applyEFIConfig(ApplyEFICfgVO objApplyEFICfgVO)
			throws RMDDAOException;

	
	/**
	 * @Author :
	 * @return :List<VehicleDetailsVO>
	 * @param  :CfgAssetSearchVO objCfgAssetSearchVO, String ctrlCfgObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Vehicle Details for the Below Combinations
	 * 				1.Fleet + Upgrade Version
	 * 				2.Model + Upgrade Version
	 * 				3.RNH   + Upgrade Version
	 */
	
	public List<VehicleDetailsVO> getVehicleDetailsUpgrade(
			CfgAssetSearchVO objCfgAssetSearchVO,
			VerifyCfgTemplateVO objVerifyCfgTemplateVO, String ctrlCfgObjId);
	
	/**
	 * @Author :
	 * @return :List<VehicleDetailsVO>
	 * @param  :CfgAssetSearchVO objCfgAssetSearchVO, String ctrlCfgObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Vehicle Details for the Below Combinations
	 * 				1.Fleet
	 * 				2.Model	
	 * 				3.RNH
	 * 				4.Fleet + Software Version
	 * 				5.Model + Software Version
	 * 				6.RNH   + Software Version
	 */

	public List<VehicleDetailsVO> getVehicleDetails(
			CfgAssetSearchVO objCfgAssetSearchVO, String ctrlCfgObjId);
	
	/**
	 * @Author :
	 * @return :List<VehicleDetailsVO>
	 * @param :List<Object[]> arlVehicleDetails
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for mapping result Set data to
	 *               List of VO's
	 */
	public List<VehicleDetailsVO> populateEFIConfigDataToVO(
			List<Object[]> arlDetails) ;
	
	/**
	 * @Author :
	 * @return :List<VehicleDetailsVO>
	 * @param :List<Object[]> arlVehicleDetails
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for mapping result Set data to
	 *               List of VO's
	 */

	public List<VehicleDetailsVO> populateDataToVO(
			List<Object[]> arlVehicleDetails);
			
	
	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for Creating a new MTM Vehicle
	 *               Record.
	 * 
	 */
	
	public List<String> createNewMtmVehRecordEFI(
			List<TemplateInfoVO> arlTemplateInfoVOs) throws RMDDAOException ;
	
	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Units For Applying
	 *               EFI Config.
	 * 
	 */
	
	public List<VehicleDetailsVO> getVehicleDetailsEFIFromRn(String customer,
			String roadNumberHdr, String roadNumberFrom, String roadNumberTo) ;
	
	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Units For Applying
	 *               EFI Config
	 * 
	 */
	public List<VehicleDetailsVO> getVehicleDetailsEFIUpdateVersion(
			String customer, String roadNumberHdr, String roadFrom,
			String roadTo, String version);
	
	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for Applying Cfg Template of
	 *               Type EFI
	 */

	public List<String> processContentsForEFIApply(
			List<VehicleDetailsVO> arlVehicleDetailsVOs,
			VerifyCfgTemplateVO objVerifyCfgTemplateVO, String userName)
			throws RMDDAOException;

	/**
     * @Author :
     * @return :List<MassApplyCfgVO>
     * @param :ConfigSearchVO objConfigSearchVO
     * @throws :RMDDAOException
     * @Description: This method is Responsible for fetching AHC config templates
     * 
     */
    public List<MassApplyCfgVO> getAHCConfigs(ConfigSearchVO objConfigSearchVO) throws RMDDAOException;

    /**
     * @Author :
     * @return :Future<List<VerifyCfgTemplateVO>>
     * @param :List<String> ahcObjId
     * @throws :RMDDAOException
     * @Description: This method is Responsible for fetching selected AHC
     *               Configurations for applying.
     * 
     */
    public Future<List<VerifyCfgTemplateVO>> getAHCTemplates(List<String> ahcObjId) throws RMDDAOException;
    
    /**
     * @Author :
     * @return :String
     * @param : List<Integer> vehicleObjId, String userName, List<Integer>
     *        cfgDefObjId
     * @throws SQLException 
     * @throws :RMDDAOException
     * @Description: This method is Responsible for Applying templates of type
     *               AHC.
     * 
     */
    public Future<List<String>> createNewMTMVehRecordForAHC(
            List<VehicleDetailsVO> arlVehicleDetailsVOs,
            List<Integer> cfgDefObjId, String userName) throws RMDDAOException,
            SQLException;
    /**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :ConfigSearchVO objConfigSearchVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type RCI.
	 * 
	 */
	public List<MassApplyCfgVO> getRCIConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDDAOException;

	 /**
     * @Author :
     * @return :Future<List<VerifyCfgTemplateVO>>
     * @param :List<String> rciObjId
     * @throws :RMDDAOException
     * @Description: This method is Responsible for fetching selected RCI
     *               Configurations for applying.
     * 
     */
    public Future<List<VerifyCfgTemplateVO>> getRCITemplates(
            List<String> rciObjId) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDDAOException
     * @Description: This method is Responsible to re-apply vehicle
     *               Configuration Template.
     * 
     */
    public String reApplyTemplate(VehicleCfgTemplateVO objCfgTemplateVO) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param : String vehStausObjId,String userId
     * @throws :RMDDAOException
     * @Description: This method is Responsible to re-notify vehicle
     *               Configuration Template.
     * 
     */
    public String reNotifyTemplateAssociation(String vehStausObjId,
            String userId) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param : String tempObjId, String templateNo,String versionNo, String userId,String status
     * @throws :RMDDAOException
     * @Description: This method is Responsible to update the RCI Template
     * 
     */
    String updateRCITemplate(String tempObjId, String templateNo,
            String versionNo, String userId,String status) throws RMDDAOException;

}

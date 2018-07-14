/**
 * ============================================================
 * Classification: GE Confidential
 * File : MassApplyCfgResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.assets.resources;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : March 17, 2016
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.assets.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.asset.service.intf.MassApplyCfgServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyEFICfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CfgAssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ConfigSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MassApplyCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.SelectedCfgTemplatesVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VerifyCfgTemplateVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.ApplyCfgTemplateRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.ApplyEFICfgRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetNumberResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.CfgAssetSearchRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.LogMessagesResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.MassApplyCfgResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.SoftwareVersionReponseType;
import com.ge.trans.rmd.services.assets.valueobjects.TemplateVersionResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.VehCfgTemplateRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.VerifyCfgTemplatesRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.VerifyCfgTemplatesResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_MASS_APPLY_CFG)
@Component
public class MassApplyCfgResource {
	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(MassApplyCfgResource.class);

	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;

	@Autowired
	private MassApplyCfgServiceIntf massApplyCfgServiceIntf;

	/**
	 * @Author :
	 * @return :UriInfo ui
	 * @param :String ctrlCfgObjId,String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FFD.
	 * 
	 */

	@GET
	@Path(OMDConstants.GET_FFD_CONFIGS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<MassApplyCfgResponseType> getFFDConfigs(@Context UriInfo ui)
			throws RMDServiceException {
		List<MassApplyCfgResponseType> arlFFDCfgResponseTypes = null;
		MassApplyCfgResponseType objMassApplyCfgResponseType = null;
		String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
		String vehicleObjId = OMDConstants.EMPTY_STRING;
		boolean isCaseVehicleCfg = false;
		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJID)) {
				ctrlCfgObjId = queryParams
						.getFirst(OMDConstants.CTRL_CFG_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.VEHICLE_OBJID)) {
				vehicleObjId = queryParams.getFirst(OMDConstants.VEHICLE_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.IS_CASE_MASS_APPLY_CFG)) {
				String fromCaseScreen = queryParams
						.getFirst(OMDConstants.IS_CASE_MASS_APPLY_CFG);
				isCaseVehicleCfg = OMDConstants.Y
						.equalsIgnoreCase(fromCaseScreen) ? true : false;
			}
			if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNumeric(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_CTRL_CFG_OBJID);
			}
			if (!RMDCommonUtility.isNullOrEmpty(vehicleObjId)
					&& !RMDCommonUtility.isNumeric(vehicleObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_VEHICLE_OBJID);
			}
			ConfigSearchVO objConfigSearchVO = new ConfigSearchVO();
			objConfigSearchVO.setCtrlcfgObjId(ctrlCfgObjId);
			objConfigSearchVO.setVehicleObjId(vehicleObjId);
			objConfigSearchVO.setCaseMassApplyCFG(isCaseVehicleCfg);
			List<MassApplyCfgVO> arlFFDCfgVOs = massApplyCfgServiceIntf
					.getFFDConfigs(objConfigSearchVO);

			if (null != arlFFDCfgVOs && !arlFFDCfgVOs.isEmpty()) {
				arlFFDCfgResponseTypes = new ArrayList<MassApplyCfgResponseType>(
						arlFFDCfgVOs.size());

				for (MassApplyCfgVO objMassApplyCfgVO : arlFFDCfgVOs) {
					objMassApplyCfgResponseType = new MassApplyCfgResponseType();
					objMassApplyCfgResponseType.setObjId(objMassApplyCfgVO
							.getOmiObjid());
					objMassApplyCfgResponseType.setTemplate(objMassApplyCfgVO
							.getTemplate());
					objMassApplyCfgResponseType.setTitle(ESAPI.encoder()
							.encodeForXML(objMassApplyCfgVO.getTitle()));
					objMassApplyCfgResponseType.setVersion(objMassApplyCfgVO
							.getVersion());
					if (isCaseVehicleCfg) {
						objMassApplyCfgResponseType
								.setCfgStatus(objMassApplyCfgVO.getTempStatus());
						objMassApplyCfgResponseType
								.setVehicleStatus(objMassApplyCfgVO
										.getVehStatus());
						objMassApplyCfgResponseType
								.setVehicleStatusDate(objMassApplyCfgVO
										.getVehStatusDate());
					}
					arlFFDCfgResponseTypes.add(objMassApplyCfgResponseType);
				}
			}
			arlFFDCfgVOs = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}

		return arlFFDCfgResponseTypes;
	}

	/**
	 * @Author :
	 * @return :List<MassApplyCfgResponseType>
	 * @param :UriInfo ui
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FRD.
	 * 
	 */

	@GET
	@Path(OMDConstants.GET_FRD_CONFIGS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<MassApplyCfgResponseType> getFRDConfigs(@Context UriInfo ui)
			throws RMDServiceException {
		List<MassApplyCfgResponseType> arlFRDCfgResponseTypes = null;
		MassApplyCfgResponseType objMassApplyCfgResponseType = null;
		String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
		String vehicleObjId = OMDConstants.EMPTY_STRING;
		boolean isCaseVehicleCfg = false;
		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJID)) {
				ctrlCfgObjId = queryParams
						.getFirst(OMDConstants.CTRL_CFG_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.VEHICLE_OBJID)) {
				vehicleObjId = queryParams.getFirst(OMDConstants.VEHICLE_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.IS_CASE_MASS_APPLY_CFG)) {
				String fromCaseScreen = queryParams
						.getFirst(OMDConstants.IS_CASE_MASS_APPLY_CFG);
				isCaseVehicleCfg = OMDConstants.Y
						.equalsIgnoreCase(fromCaseScreen) ? true : false;
			}
			if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNumeric(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_CTRL_CFG_OBJID);
			}
			if (!RMDCommonUtility.isNullOrEmpty(vehicleObjId)
					&& !RMDCommonUtility.isNumeric(vehicleObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_VEHICLE_OBJID);
			}
			ConfigSearchVO objConfigSearchVO = new ConfigSearchVO();
			objConfigSearchVO.setCtrlcfgObjId(ctrlCfgObjId);
			objConfigSearchVO.setVehicleObjId(vehicleObjId);
			objConfigSearchVO.setCaseMassApplyCFG(isCaseVehicleCfg);
			List<MassApplyCfgVO> arlFRDCfgVOs = massApplyCfgServiceIntf
					.getFRDConfigs(objConfigSearchVO);

			if (null != arlFRDCfgVOs && !arlFRDCfgVOs.isEmpty()) {
				arlFRDCfgResponseTypes = new ArrayList<MassApplyCfgResponseType>(
						arlFRDCfgVOs.size());

				for (MassApplyCfgVO objMassApplyCfgVO : arlFRDCfgVOs) {
					objMassApplyCfgResponseType = new MassApplyCfgResponseType();
					objMassApplyCfgResponseType.setObjId(objMassApplyCfgVO
							.getOmiObjid());
					objMassApplyCfgResponseType.setTemplate(objMassApplyCfgVO
							.getTemplate());
					objMassApplyCfgResponseType.setTitle(ESAPI.encoder()
							.encodeForXML(objMassApplyCfgVO.getTitle()));
					objMassApplyCfgResponseType.setVersion(objMassApplyCfgVO
							.getVersion());
					if (isCaseVehicleCfg) {
						objMassApplyCfgResponseType
								.setCfgStatus(objMassApplyCfgVO.getTempStatus());
						objMassApplyCfgResponseType
								.setVehicleStatus(objMassApplyCfgVO
										.getVehStatus());
						objMassApplyCfgResponseType
								.setVehicleStatusDate(objMassApplyCfgVO
										.getVehStatusDate());
					}
					arlFRDCfgResponseTypes.add(objMassApplyCfgResponseType);
				}
			}
			arlFRDCfgVOs = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return arlFRDCfgResponseTypes;
	}

	/**
	 * @Author :
	 * @return :List<MassApplyCfgResponseType>
	 * @param :String ctrlCfgObjId,String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type EDP.
	 * 
	 */

	@GET
	@Path(OMDConstants.GET_EDP_CONFIGS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<MassApplyCfgResponseType> getEDPConfigs(@Context UriInfo ui)
			throws RMDServiceException {
		List<MassApplyCfgResponseType> arlEDPCfgResponseTypes = null;
		MassApplyCfgResponseType objMassApplyCfgResponseType = null;
		String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
		String vehicleObjId = OMDConstants.EMPTY_STRING;
		boolean isCaseVehicleCfg = false;
		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJID)) {
				ctrlCfgObjId = queryParams
						.getFirst(OMDConstants.CTRL_CFG_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.VEHICLE_OBJID)) {
				vehicleObjId = queryParams.getFirst(OMDConstants.VEHICLE_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.IS_CASE_MASS_APPLY_CFG)) {
				String fromCaseScreen = queryParams
						.getFirst(OMDConstants.IS_CASE_MASS_APPLY_CFG);
				isCaseVehicleCfg = OMDConstants.Y
						.equalsIgnoreCase(fromCaseScreen) ? true : false;
			}
			if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNumeric(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_CTRL_CFG_OBJID);
			}
			if (!RMDCommonUtility.isNullOrEmpty(vehicleObjId)
					&& !RMDCommonUtility.isNumeric(vehicleObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_VEHICLE_OBJID);
			}
			ConfigSearchVO objConfigSearchVO = new ConfigSearchVO();
			objConfigSearchVO.setCtrlcfgObjId(ctrlCfgObjId);
			objConfigSearchVO.setVehicleObjId(vehicleObjId);
			objConfigSearchVO.setCaseMassApplyCFG(isCaseVehicleCfg);
			List<MassApplyCfgVO> arlEDPCfgVOs = massApplyCfgServiceIntf
					.getEDPConfigs(objConfigSearchVO);

			if (null != arlEDPCfgVOs && !arlEDPCfgVOs.isEmpty()) {
				arlEDPCfgResponseTypes = new ArrayList<MassApplyCfgResponseType>(
						arlEDPCfgVOs.size());

				for (MassApplyCfgVO objMassApplyCfgVO : arlEDPCfgVOs) {
					objMassApplyCfgResponseType = new MassApplyCfgResponseType();
					objMassApplyCfgResponseType.setObjId(objMassApplyCfgVO
							.getOmiObjid());
					objMassApplyCfgResponseType.setTemplate(objMassApplyCfgVO
							.getTemplate());
					objMassApplyCfgResponseType.setTitle(ESAPI.encoder()
							.encodeForXML(objMassApplyCfgVO.getTitle()));
					objMassApplyCfgResponseType.setVersion(objMassApplyCfgVO
							.getVersion());
					if (isCaseVehicleCfg) {
						objMassApplyCfgResponseType
								.setCfgStatus(objMassApplyCfgVO.getTempStatus());
						objMassApplyCfgResponseType
								.setVehicleStatus(objMassApplyCfgVO
										.getVehStatus());
						objMassApplyCfgResponseType
								.setVehicleStatusDate(objMassApplyCfgVO
										.getVehStatusDate());
					}
					arlEDPCfgResponseTypes.add(objMassApplyCfgResponseType);
				}
			}

			arlEDPCfgVOs = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return arlEDPCfgResponseTypes;
	}

	/**
	 * @Author :
	 * @return :List<VerifyCfgTemplatesResponseType>
	 * @param :UriInfo ui
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations
	 *               selected for applying.
	 * 
	 */
	@GET
	@Path(OMDConstants.VERIFY_CONFIG_TEMPLATES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<VerifyCfgTemplatesResponseType> verifyConfigTemplates(
			@Context UriInfo ui) throws RMDServiceException {
		List<VerifyCfgTemplatesResponseType> arlVerifyCfgTemplatesResponseType = null;
		VerifyCfgTemplatesResponseType objVerifyCfgTemplatesResponseType = null;
		List<VerifyCfgTemplateVO> arlVerifyCfgTemplateVOs = null;
		String edpObjId = OMDConstants.EMPTY_STRING;
		String ffdObjId = OMDConstants.EMPTY_STRING;
		String frdObjId = OMDConstants.EMPTY_STRING;
		String efiObjId = OMDConstants.EMPTY_STRING;
		String ahcObjId = OMDConstants.EMPTY_STRING;
		String rciObjId = OMDConstants.EMPTY_STRING;
		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.EDP_OBJID)) {
				edpObjId = queryParams.getFirst(OMDConstants.EDP_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.FFD_OBJID)) {
				ffdObjId = queryParams.getFirst(OMDConstants.FFD_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.FRD_OBJID)) {
				frdObjId = queryParams.getFirst(OMDConstants.FRD_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.EFI_OBJID)) {
				efiObjId = queryParams.getFirst(OMDConstants.EFI_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.AHC_OBJID)) {
			    ahcObjId = queryParams.getFirst(OMDConstants.AHC_OBJID);
            }
			if (queryParams.containsKey(OMDConstants.RCI_OBJID)) {
                rciObjId = queryParams.getFirst(OMDConstants.RCI_OBJID);
            }
			SelectedCfgTemplatesVO objSelectedCfgTemplatesVO = new SelectedCfgTemplatesVO();

			if (!RMDCommonUtility.isNullOrEmpty(edpObjId)) {
				objSelectedCfgTemplatesVO.setEdpObjIdList(Arrays
						.asList(edpObjId.split(OMDConstants.COMMA)));

			}
			if (!RMDCommonUtility.isNullOrEmpty(ffdObjId)) {
				objSelectedCfgTemplatesVO.setFfdObjIdList(Arrays
						.asList(ffdObjId.split(OMDConstants.COMMA)));
			}
			if (!RMDCommonUtility.isNullOrEmpty(frdObjId)) {
				objSelectedCfgTemplatesVO.setFrdObjId(Arrays
						.asList(frdObjId.split(OMDConstants.COMMA)));
			}
			if (!RMDCommonUtility.isNullOrEmpty(efiObjId)) {
				objSelectedCfgTemplatesVO.setEfiObjId(efiObjId);
			}
			if (!RMDCommonUtility.isNullOrEmpty(ahcObjId)) {
                objSelectedCfgTemplatesVO.setAhcObjId(Arrays
                        .asList(ahcObjId.split(OMDConstants.COMMA)));
            }
			if (!RMDCommonUtility.isNullOrEmpty(rciObjId)) {
                objSelectedCfgTemplatesVO.setRciObjId(Arrays
                        .asList(rciObjId.split(OMDConstants.COMMA)));
            }

			arlVerifyCfgTemplateVOs = massApplyCfgServiceIntf
					.verifyConfigTemplates(objSelectedCfgTemplatesVO);

			if (null != arlVerifyCfgTemplateVOs
					&& !arlVerifyCfgTemplateVOs.isEmpty()) {
				arlVerifyCfgTemplatesResponseType = new ArrayList<VerifyCfgTemplatesResponseType>(
						arlVerifyCfgTemplateVOs.size());

				for (VerifyCfgTemplateVO objCfgTemplateVO : arlVerifyCfgTemplateVOs) {
					objVerifyCfgTemplatesResponseType = new VerifyCfgTemplatesResponseType();
					objVerifyCfgTemplatesResponseType
							.setCfgFile(objCfgTemplateVO.getCfgFile());
					objVerifyCfgTemplatesResponseType
							.setCfgType(objCfgTemplateVO.getCfgType());
					objVerifyCfgTemplatesResponseType.setObjid(objCfgTemplateVO
							.getObjid());
					objVerifyCfgTemplatesResponseType
							.setTemplate(objCfgTemplateVO.getTemplate());
					objVerifyCfgTemplatesResponseType.setTitle(objCfgTemplateVO
							.getTitle());
					objVerifyCfgTemplatesResponseType
							.setVersion(objCfgTemplateVO.getVersion());
					objVerifyCfgTemplatesResponseType
							.setStatus(objCfgTemplateVO.getStatus());
					objVerifyCfgTemplatesResponseType
							.setCustomer(objCfgTemplateVO.getCustomer());
					objVerifyCfgTemplatesResponseType
                        .setFaultCode(objCfgTemplateVO.getFaultCode());
					objVerifyCfgTemplatesResponseType
                        .setFileName(objCfgTemplateVO.getFileName());
					objVerifyCfgTemplatesResponseType
					    .setFileContent(objCfgTemplateVO.getFileContent());
					arlVerifyCfgTemplatesResponseType
							.add(objVerifyCfgTemplatesResponseType);
				}
			}
			arlVerifyCfgTemplateVOs = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return arlVerifyCfgTemplatesResponseType;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :UriInfo ui
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching list of software
	 *               versions.
	 * 
	 */

	@GET
	@Path(OMDConstants.GET_ON_BOARD_SOFTWARE_VERSION)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public SoftwareVersionReponseType getOnboardSoftwareVersion(
			@Context UriInfo ui) throws RMDServiceException {
		SoftwareVersionReponseType objSoftwareVersionReponseType = null;
		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
			if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJID)) {
				ctrlCfgObjId = queryParams
						.getFirst(OMDConstants.CTRL_CFG_OBJID);
			}
			if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNumeric(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_CTRL_CFG_OBJID);
			}
			List<String> arlsfwVersion = massApplyCfgServiceIntf
					.getOnboardSoftwareVersion(ctrlCfgObjId);
			objSoftwareVersionReponseType = new SoftwareVersionReponseType();
			objSoftwareVersionReponseType.setSoftwareVersionList(arlsfwVersion);
			arlsfwVersion = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return objSoftwareVersionReponseType;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :UriInfo ui
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching list of software
	 *               versions.
	 * 
	 */

	@GET
	@Path(OMDConstants.GET_CGF_TEMPLATE_VERSIONS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public TemplateVersionResponseType getCfgTemplateVersions(
			@Context UriInfo ui) throws RMDServiceException {
		TemplateVersionResponseType objTemplateVersionResponseType = null;
		String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
		String cfgFile = OMDConstants.EMPTY_STRING;
		String template = OMDConstants.EMPTY_STRING;
		try {

			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJID)) {
				ctrlCfgObjId = queryParams
						.getFirst(OMDConstants.CTRL_CFG_OBJID);
			}

			if (queryParams.containsKey(OMDConstants.CFG_FILE)) {
				cfgFile = queryParams.getFirst(OMDConstants.CFG_FILE);
			}
			if (queryParams.containsKey(OMDConstants.TEMPLATE)) {
				template = queryParams.getFirst(OMDConstants.TEMPLATE);
			}
			if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNumeric(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_CTRL_CFG_OBJID);
			}

			if (RMDCommonUtility.isNullOrEmpty(template)) {
				throw new OMDInValidInputException(
						OMDConstants.TEMPLATE_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNumeric(template)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_TEMPLATE_OBJID);
			}
			if (RMDCommonUtility.isNullOrEmpty(cfgFile)) {
				throw new OMDInValidInputException(
						OMDConstants.CFG_FILE_NOT_PROVIDED);
			}
			List<String> templateVersionList = massApplyCfgServiceIntf
					.getCfgTemplateVersions(cfgFile, template, ctrlCfgObjId);
			objTemplateVersionResponseType = new TemplateVersionResponseType();
			objTemplateVersionResponseType
					.setTemplateVersionList(templateVersionList);
			templateVersionList = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}
		return objTemplateVersionResponseType;
	}

	/**
	 * @Author :
	 * @return :AssetNumberResponseType
	 * @param :UriInfo ui
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching list of asset
	 *               Numbers.
	 * 
	 */

	@GET
	@Path(OMDConstants.GET_SPECIFIC_ASSET_NUMBERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public AssetNumberResponseType getSpecificAssetNumbers(@Context UriInfo ui)
			throws RMDServiceException {
		AssetNumberResponseType objAssetNumberResponseType = null;
		AssetSearchVO objAssetSearchVO = null;
		String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
		String assetGrpName = OMDConstants.EMPTY_STRING;
		String customerId = OMDConstants.EMPTY_STRING;
		String assetNumberFrom = OMDConstants.EMPTY_STRING;
		String assetNumberTo = OMDConstants.EMPTY_STRING;
		try {

			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJID)) {
				ctrlCfgObjId = queryParams
						.getFirst(OMDConstants.CTRL_CFG_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
				assetGrpName = queryParams
						.getFirst(OMDConstants.ASSET_GROUP_NAME);
			}
			if (queryParams.containsKey(OMDConstants.ASSET_NUMBER_FROM)) {
				assetNumberFrom = queryParams
						.getFirst(OMDConstants.ASSET_NUMBER_FROM);
			}
			if (queryParams.containsKey(OMDConstants.ASSET_NUMBER_TO)) {
				assetNumberTo = queryParams
						.getFirst(OMDConstants.ASSET_NUMBER_TO);
			}

			if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
				customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
			}

			if (RMDCommonUtility.isNullOrEmpty(customerId)) {
				throw new OMDInValidInputException(
						OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
			}

			if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNumeric(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_CTRL_CFG_OBJID);
			}

			if (RMDCommonUtility.isNullOrEmpty(assetGrpName)) {
				throw new OMDInValidInputException(
						OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isAlphaNumeric(assetGrpName)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_ASSET_GRP_NAME);
			}
			if (RMDCommonUtility.isNullOrEmpty(assetNumberFrom)) {
				throw new OMDInValidInputException(
						OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isAlphaNumeric(assetNumberFrom)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_ASSET_NUMBER);
			}
			if (RMDCommonUtility.isNullOrEmpty(assetNumberTo)) {
				throw new OMDInValidInputException(
						OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isAlphaNumeric(assetNumberTo)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_ASSET_NUMBER);
			}
			objAssetSearchVO = new AssetSearchVO();
			objAssetSearchVO.setAssetGroupName(assetGrpName);
			objAssetSearchVO.setAssetFrom(assetNumberFrom);
			objAssetSearchVO.setAssetTo(assetNumberTo);
			objAssetSearchVO.setCtrlCfgObjId(ctrlCfgObjId);
			objAssetSearchVO.setCustomerId(customerId);
			List<String> arlAssetNumbers = massApplyCfgServiceIntf
					.getSpecificAssetNumbers(objAssetSearchVO);
			objAssetNumberResponseType = new AssetNumberResponseType();
			objAssetNumberResponseType.setArlAssetNumbers(arlAssetNumbers);
			arlAssetNumbers = null;
			objAssetSearchVO = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}
		return objAssetNumberResponseType;
	}

	/**
	 * @Author :
	 * @return :LogMessagesResponseType
	 * @param :ApplyCfgTemplateRequestType objApplyCfgTemplateRequestType
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for applying Config Templates
	 */

	@POST
	@Path(OMDConstants.APPLY_CFG_TEMPLATES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public LogMessagesResponseType applyCfgTemplates(
			ApplyCfgTemplateRequestType objApplyCfgTemplateRequestType)
			throws RMDServiceException {
		LogMessagesResponseType objLogMessagesResponseType = null;
		ApplyCfgTemplateVO objApplyCfgTemplateVO = null;
		VerifyCfgTemplateVO objVerifyCfgTemplateVO = null;
		List<VerifyCfgTemplateVO> arlVerifyCfgTemplateVOs = null;
		List<CfgAssetSearchVO> arlCfgAssetSearchVOs=null;
		CfgAssetSearchVO objCfgAssetSearchVO=null;
		try {
			if (RMDCommonUtility.isNullOrEmpty(objApplyCfgTemplateRequestType
					.getUserName())) {
				throw new OMDInValidInputException(
						OMDConstants.USER_NAME_VALUE_NOT_PROVIDED);
			}

			if (RMDCommonUtility.isNullOrEmpty(objApplyCfgTemplateRequestType
					.getCtrlCfgObjId())) {
				throw new OMDInValidInputException(
						OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
			}

			if (!RMDCommonUtility.isNumeric(objApplyCfgTemplateRequestType
					.getCtrlCfgObjId())) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_CTRL_CFG_OBJID);
			}

			if (null == objApplyCfgTemplateRequestType.getCfgTemplateList()
					|| !RMDCommonUtility
							.isCollectionNotEmpty(objApplyCfgTemplateRequestType
									.getCfgTemplateList())) {
				throw new OMDInValidInputException(
						OMDConstants.CFG_TEMPALTES_NOT_PROVIDED);
			}

			if (!RMDCommonConstants.ALL
					.equalsIgnoreCase(objApplyCfgTemplateRequestType
							.getSearchCriteria())
					&& (null == objApplyCfgTemplateRequestType
							.getArlAssetSearchRequestTypes() || !RMDCommonUtility
							.isCollectionNotEmpty(objApplyCfgTemplateRequestType
									.getArlAssetSearchRequestTypes()))) {
				throw new OMDInValidInputException(
						OMDConstants.ITEMS_ARE_NOT_SELECTED_FOR_APPLYING);
			}

			

			objApplyCfgTemplateVO = new ApplyCfgTemplateVO();
			objApplyCfgTemplateVO
					.setCtrlCfgObjId(objApplyCfgTemplateRequestType
							.getCtrlCfgObjId());
			objApplyCfgTemplateVO.setSearchType(objApplyCfgTemplateRequestType
					.getSearchCriteria());
			objApplyCfgTemplateVO.setUserName(objApplyCfgTemplateRequestType
					.getUserName());
			objApplyCfgTemplateVO
					.setDeleteConfig(objApplyCfgTemplateRequestType
							.isDeleteCfg());
			objApplyCfgTemplateVO.setCtrlCfgName(objApplyCfgTemplateRequestType
					.getCtrlCfgName());

			if (null != objApplyCfgTemplateRequestType.getCfgTemplateList()
					&& ! objApplyCfgTemplateRequestType.getCfgTemplateList()
							.isEmpty()) {
				arlVerifyCfgTemplateVOs = new ArrayList<VerifyCfgTemplateVO>(
						objApplyCfgTemplateRequestType.getCfgTemplateList()
								.size());

				for (VerifyCfgTemplatesRequestType objVerifyCfgTemplatesRequestType : objApplyCfgTemplateRequestType
						.getCfgTemplateList()) {
					objVerifyCfgTemplateVO = new VerifyCfgTemplateVO();

					if (!RMDCommonUtility
							.isNullOrEmpty(objVerifyCfgTemplatesRequestType
									.getObjid())) {
						objVerifyCfgTemplateVO
								.setObjid(objVerifyCfgTemplatesRequestType
										.getObjid());
					} else {
						throw new OMDInValidInputException(
								OMDConstants.OBJID_NOT_PROVIDED);
					}
					if (!RMDCommonUtility
							.isNullOrEmpty(objVerifyCfgTemplatesRequestType
									.getCfgFile())) {
						objVerifyCfgTemplateVO
								.setCfgFile(objVerifyCfgTemplatesRequestType
										.getCfgFile());
					} else {
						throw new OMDInValidInputException(
								OMDConstants.CNFGFILE_NOT_PROVIDED);
					}
					if (!RMDCommonUtility
							.isNullOrEmpty(objVerifyCfgTemplatesRequestType
									.getTemplate())) {
						objVerifyCfgTemplateVO
								.setTemplate(objVerifyCfgTemplatesRequestType
										.getTemplate());
					} else {
						throw new OMDInValidInputException(
								OMDConstants.TEMPLATE_NOT_PROVIDED);
					}
					if (!RMDCommonUtility
							.isNullOrEmpty(objVerifyCfgTemplatesRequestType
									.getVersion())) {
						objVerifyCfgTemplateVO
								.setVersion(objVerifyCfgTemplatesRequestType
										.getVersion());
					} else {
						throw new OMDInValidInputException(
								OMDConstants.VERSION_NOT_PROVIDED);
					}
					objVerifyCfgTemplateVO
							.setTitle(objVerifyCfgTemplatesRequestType
									.getTitle());
					objVerifyCfgTemplateVO
							.setStatus(objVerifyCfgTemplatesRequestType
									.getStatus());
					objVerifyCfgTemplateVO
							.setCfgType(objVerifyCfgTemplatesRequestType
									.getCfgType());
					objVerifyCfgTemplateVO
							.setCustomer(objVerifyCfgTemplatesRequestType
									.getCustomer());
					arlVerifyCfgTemplateVOs.add(objVerifyCfgTemplateVO);
				}
			}
			objApplyCfgTemplateVO.setCfgTemplateList(arlVerifyCfgTemplateVOs);
			
			if (null != objApplyCfgTemplateRequestType
					.getArlAssetSearchRequestTypes()
					&& ! objApplyCfgTemplateRequestType
							.getArlAssetSearchRequestTypes().isEmpty()) {
				arlCfgAssetSearchVOs = new ArrayList<CfgAssetSearchVO>(
						objApplyCfgTemplateRequestType
								.getArlAssetSearchRequestTypes().size());

				for (CfgAssetSearchRequestType objCfgAssetSearchRequestType : objApplyCfgTemplateRequestType
						.getArlAssetSearchRequestTypes()) {
					objCfgAssetSearchVO = new CfgAssetSearchVO();

					objCfgAssetSearchVO
							.setAssetGrpName(objCfgAssetSearchRequestType
									.getAssetGrpName());
					objCfgAssetSearchVO
							.setAssetNumbers(objCfgAssetSearchRequestType
									.getAssetNumbers());
					objCfgAssetSearchVO
							.setCustomer(objCfgAssetSearchRequestType
									.getCustomer());
					objCfgAssetSearchVO.setFleet(objCfgAssetSearchRequestType
							.getFleet());
					objCfgAssetSearchVO
							.setFromVersion(objCfgAssetSearchRequestType
									.getFromVersion());
					objCfgAssetSearchVO
							.setToVersion(objCfgAssetSearchRequestType
									.getToVersion());
					objCfgAssetSearchVO.setModel(objCfgAssetSearchRequestType
							.getModel());
					objCfgAssetSearchVO
							.setOnboardSWVersion(objCfgAssetSearchRequestType
									.getOnboardSWVersion());
					objCfgAssetSearchVO
							.setSearchType(objCfgAssetSearchRequestType
									.getSearchType());
					arlCfgAssetSearchVOs.add(objCfgAssetSearchVO);
				}
			}
			objApplyCfgTemplateVO.setArlCfgAssetSearchVOs(arlCfgAssetSearchVOs);
			List<String> logMessages = massApplyCfgServiceIntf
					.applyCfgTemplates(objApplyCfgTemplateVO);
			objLogMessagesResponseType = new LogMessagesResponseType();
			objLogMessagesResponseType.setArlLogMessages(logMessages);
			logMessages = null;

		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		} finally {
			arlVerifyCfgTemplateVOs = null;
			arlCfgAssetSearchVOs = null;
		}
		return objLogMessagesResponseType;
	}

	/**
	 * @Author :
	 * @return :LogMessagesResponseType
	 * @param :ApplyEFICfgVO objApplyEFICfgVO
	 * @throws :RMDServiceException
	 * @Description: This method is Responsible for applying the EFI config
	 *               templates to the selected assets.
	 * 
	 */
	@POST
	@Path(OMDConstants.APPLY_EFI_CFG)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public LogMessagesResponseType applyEFIConfig(
			ApplyEFICfgRequestType objApplyEFICfgRequestType)
			throws RMDServiceException {
		LogMessagesResponseType objLogMessagesResponseType = null;
		try {
			if (RMDCommonUtility.isNullOrEmpty(objApplyEFICfgRequestType
					.getCustomer())) {
				throw new OMDInValidInputException(
						OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
			}
			if (RMDCommonUtility.isNullOrEmpty(objApplyEFICfgRequestType
					.getAssetGrpName())) {
				throw new OMDInValidInputException(
						OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED);
			}
			if (RMDCommonUtility.isNullOrEmpty(objApplyEFICfgRequestType
					.getFromAssetNumber())) {
				throw new OMDInValidInputException(
						OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isAlphaNumeric(objApplyEFICfgRequestType
					.getFromAssetNumber())) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_ASSET_NUMBER);
			}
			if (RMDCommonUtility.isNullOrEmpty(objApplyEFICfgRequestType
					.getToAssetNumber())) {
				throw new OMDInValidInputException(
						OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isAlphaNumeric(objApplyEFICfgRequestType
					.getToAssetNumber())) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_ASSET_NUMBER);
			}

			if (objApplyEFICfgRequestType.isChangeVersion()
					&& !RMDCommonUtility
							.isAlphaNumeric(objApplyEFICfgRequestType
									.getFromVersion())) {
				throw new OMDInValidInputException(
						OMDConstants.VERSION_NOT_PROVIDED);
			}

			ApplyEFICfgVO objEfiCfgVO = new ApplyEFICfgVO();
			objEfiCfgVO.setCustomer(objApplyEFICfgRequestType.getCustomer());
			objEfiCfgVO.setAssetGrpName(objApplyEFICfgRequestType
					.getAssetGrpName());
			objEfiCfgVO.setFromAssetNumber(objApplyEFICfgRequestType
					.getFromAssetNumber());
			objEfiCfgVO.setToAssetNumber(objApplyEFICfgRequestType
					.getToAssetNumber());
			objEfiCfgVO.setUserName(objApplyEFICfgRequestType.getUserName());
			objEfiCfgVO.setFromVersion(objApplyEFICfgRequestType
					.getFromVersion());
			objEfiCfgVO.setChangeVersion(objApplyEFICfgRequestType
					.isChangeVersion());
			if (null != objApplyEFICfgRequestType
					.getObjVerifyCfgTemplatesRequestType()) {
				VerifyCfgTemplateVO objVerifyCfgTemplateVO = new VerifyCfgTemplateVO();

				if (RMDCommonUtility.isNullOrEmpty(objApplyEFICfgRequestType
						.getObjVerifyCfgTemplatesRequestType().getObjid())) {
					throw new OMDInValidInputException(
							OMDConstants.OBJID_NOT_PROVIDED);
				}
				if (RMDCommonUtility.isNullOrEmpty(objApplyEFICfgRequestType
						.getObjVerifyCfgTemplatesRequestType().getCfgFile())) {

					throw new OMDInValidInputException(
							OMDConstants.CNFGFILE_NOT_PROVIDED);
				}
				if (RMDCommonUtility.isNullOrEmpty(objApplyEFICfgRequestType
						.getObjVerifyCfgTemplatesRequestType().getTemplate())) {

					throw new OMDInValidInputException(
							OMDConstants.TEMPLATE_NOT_PROVIDED);
				}
				if (RMDCommonUtility.isNullOrEmpty(objApplyEFICfgRequestType
						.getObjVerifyCfgTemplatesRequestType().getVersion())) {

					throw new OMDInValidInputException(
							OMDConstants.VERSION_NOT_PROVIDED);
				}
				objVerifyCfgTemplateVO.setObjid(objApplyEFICfgRequestType
						.getObjVerifyCfgTemplatesRequestType().getObjid());
				objVerifyCfgTemplateVO.setCfgFile(objApplyEFICfgRequestType
						.getObjVerifyCfgTemplatesRequestType().getCfgFile());
				objVerifyCfgTemplateVO.setVersion(objApplyEFICfgRequestType
						.getObjVerifyCfgTemplatesRequestType().getVersion());
				objVerifyCfgTemplateVO.setTemplate(objApplyEFICfgRequestType
						.getObjVerifyCfgTemplatesRequestType().getTemplate());
				objEfiCfgVO.setObjVerifyCfgTemplateVO(objVerifyCfgTemplateVO);
			}
			List<String> logMessages = massApplyCfgServiceIntf
					.applyEFIConfig(objEfiCfgVO);
			objLogMessagesResponseType = new LogMessagesResponseType();
			objLogMessagesResponseType.setArlLogMessages(logMessages);
			logMessages = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}
		return objLogMessagesResponseType;
	}
	
	/**
     * @Author :
     * @return :List<MassApplyCfgResponseType>
     * @param :String ctrlCfgObjId,String vehicleObjId
     * @throws :RMDDAOException
     * @Description: This method is Responsible for fetching AHC config templates
     * 
     */

    @GET
    @Path(OMDConstants.GET_AHC_CONFIGS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<MassApplyCfgResponseType> getAHCConfigs(@Context UriInfo ui)
            throws RMDServiceException {
        List<MassApplyCfgResponseType> arlAHCCfgResponseTypes = null;
        MassApplyCfgResponseType objMassApplyCfgResponseType = null;
        String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
        String vehicleObjId = OMDConstants.EMPTY_STRING;
        boolean isCaseVehicleCfg = false;
        try {
            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJID)) {
                ctrlCfgObjId = queryParams
                        .getFirst(OMDConstants.CTRL_CFG_OBJID);
            }
            if (queryParams.containsKey(OMDConstants.VEHICLE_OBJID)) {
                vehicleObjId = queryParams.getFirst(OMDConstants.VEHICLE_OBJID);
            }
            if (queryParams.containsKey(OMDConstants.IS_CASE_MASS_APPLY_CFG)) {
                String fromCaseScreen = queryParams
                        .getFirst(OMDConstants.IS_CASE_MASS_APPLY_CFG);
                isCaseVehicleCfg = OMDConstants.Y
                        .equalsIgnoreCase(fromCaseScreen) ? true : false;
            }
            if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
                throw new OMDInValidInputException(
                        OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(ctrlCfgObjId)) {
                throw new OMDInValidInputException(
                        OMDConstants.INVALID_CTRL_CFG_OBJID);
            }
            if (!RMDCommonUtility.isNullOrEmpty(vehicleObjId)
                    && !RMDCommonUtility.isNumeric(vehicleObjId)) {
                throw new OMDInValidInputException(
                        OMDConstants.INVALID_VEHICLE_OBJID);
            }
            ConfigSearchVO objConfigSearchVO = new ConfigSearchVO();
            objConfigSearchVO.setCtrlcfgObjId(ctrlCfgObjId);
            objConfigSearchVO.setVehicleObjId(vehicleObjId);
            objConfigSearchVO.setCaseMassApplyCFG(isCaseVehicleCfg);
            List<MassApplyCfgVO> arlAHCCfgVOs = massApplyCfgServiceIntf
                    .getAHCConfigs(objConfigSearchVO);

            if (null != arlAHCCfgVOs && !arlAHCCfgVOs.isEmpty()) {
                arlAHCCfgResponseTypes = new ArrayList<MassApplyCfgResponseType>(
                        arlAHCCfgVOs.size());

                for (MassApplyCfgVO objMassApplyCfgVO : arlAHCCfgVOs) {
                    objMassApplyCfgResponseType = new MassApplyCfgResponseType();
                    objMassApplyCfgResponseType.setObjId(objMassApplyCfgVO
                            .getOmiObjid());
                    objMassApplyCfgResponseType.setTemplate(objMassApplyCfgVO
                            .getTemplate());
                    objMassApplyCfgResponseType.setTitle(ESAPI.encoder()
                            .encodeForXML(objMassApplyCfgVO.getTitle()));
                    objMassApplyCfgResponseType.setVersion(objMassApplyCfgVO
                            .getVersion());
                    if (isCaseVehicleCfg) {
                        objMassApplyCfgResponseType
                                .setCfgStatus(objMassApplyCfgVO.getTempStatus());
                        objMassApplyCfgResponseType
                                .setVehicleStatus(objMassApplyCfgVO
                                        .getVehStatus());
                        objMassApplyCfgResponseType
                                .setVehicleStatusDate(objMassApplyCfgVO
                                        .getVehStatusDate());
                    }
                    arlAHCCfgResponseTypes.add(objMassApplyCfgResponseType);
                }
            }

            arlAHCCfgVOs = null;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);

        }
        return arlAHCCfgResponseTypes;
    }
    /**
	 * @Author :
	 * @return :List<MassApplyCfgResponseType>
	 * @param :String ctrlCfgObjId,String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type EDP.
	 * 
	 */

	@GET
	@Path(OMDConstants.GET_RCI_CONFIGS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<MassApplyCfgResponseType> getRCIConfigs(@Context UriInfo ui)
			throws RMDServiceException {
		List<MassApplyCfgResponseType> arlRCICfgResponseTypes = null;
		MassApplyCfgResponseType objMassApplyCfgResponseType = null;
		String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
		String vehicleObjId = OMDConstants.EMPTY_STRING;
		boolean isCaseVehicleCfg = false;
		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJID)) {
				ctrlCfgObjId = queryParams
						.getFirst(OMDConstants.CTRL_CFG_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.VEHICLE_OBJID)) {
				vehicleObjId = queryParams.getFirst(OMDConstants.VEHICLE_OBJID);
			}
			if (queryParams.containsKey(OMDConstants.IS_CASE_MASS_APPLY_CFG)) {
				String fromCaseScreen = queryParams
						.getFirst(OMDConstants.IS_CASE_MASS_APPLY_CFG);
				isCaseVehicleCfg = OMDConstants.Y
						.equalsIgnoreCase(fromCaseScreen) ? true : false;
			}
			if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.CTRL_CFG_OBJID_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNumeric(ctrlCfgObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_CTRL_CFG_OBJID);
			}
			if (!RMDCommonUtility.isNullOrEmpty(vehicleObjId)
					&& !RMDCommonUtility.isNumeric(vehicleObjId)) {
				throw new OMDInValidInputException(
						OMDConstants.INVALID_VEHICLE_OBJID);
			}
			ConfigSearchVO objConfigSearchVO = new ConfigSearchVO();
			objConfigSearchVO.setCtrlcfgObjId(ctrlCfgObjId);
			objConfigSearchVO.setVehicleObjId(vehicleObjId);
			objConfigSearchVO.setCaseMassApplyCFG(isCaseVehicleCfg);
			List<MassApplyCfgVO> arlRCICfgVOs = massApplyCfgServiceIntf
					.getRCIConfigs(objConfigSearchVO);

			if (null != arlRCICfgVOs && !arlRCICfgVOs.isEmpty()) {
				arlRCICfgResponseTypes = new ArrayList<MassApplyCfgResponseType>(
						arlRCICfgVOs.size());

				for (MassApplyCfgVO objMassApplyCfgVO : arlRCICfgVOs) {
					objMassApplyCfgResponseType = new MassApplyCfgResponseType();
					objMassApplyCfgResponseType.setObjId(objMassApplyCfgVO
							.getOmiObjid());
					objMassApplyCfgResponseType.setTemplate(objMassApplyCfgVO
							.getTemplate());
					objMassApplyCfgResponseType.setTitle(ESAPI.encoder()
							.encodeForXML(objMassApplyCfgVO.getTitle()));
					objMassApplyCfgResponseType.setVersion(objMassApplyCfgVO
							.getVersion());
					if (isCaseVehicleCfg) {
						objMassApplyCfgResponseType
								.setCfgStatus(objMassApplyCfgVO.getTempStatus());
						objMassApplyCfgResponseType
								.setOffboardStatus(objMassApplyCfgVO
										.getOffboardStatus());
						objMassApplyCfgResponseType
								.setOnBoardStatus(objMassApplyCfgVO
										.getOnBoardStatus());
						objMassApplyCfgResponseType
                                .setOnBoardStatusDate(objMassApplyCfgVO
                                        .getOnBoardStatusDate());
					}
					arlRCICfgResponseTypes.add(objMassApplyCfgResponseType);
				}
			}

			arlRCICfgVOs = null;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return arlRCICfgResponseTypes;
	}
	
	/**
     * @Author :
     * @return :String
     * @param : VehCfgTemplateRequestType
     * @throws :RMDWebException
     * @Description: This method is Responsible to re-apply vehicle
     *               Configuration Template.
     * 
     */
    @POST
    @Path(OMDConstants.RE_APPLY_TEMPLATE)
    @Consumes(MediaType.APPLICATION_XML)
    public String reApplyTemplate(
            VehCfgTemplateRequestType objCfgTemplateRequestType)
            throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        try {
            VehicleCfgTemplateVO objCfgTemplateVO = new VehicleCfgTemplateVO();

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getConfigFile())) {
                objCfgTemplateVO.setConfigFile(objCfgTemplateRequestType
                        .getConfigFile());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.OBJID_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getCustomer())) {
                objCfgTemplateVO.setCustomer(objCfgTemplateRequestType
                        .getCustomer());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.CUSTOMERNAME_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getObjId())) {
                objCfgTemplateVO.setObjId(objCfgTemplateRequestType.getObjId());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.OBJID_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getAssetGrpName())) {
                objCfgTemplateVO.setAssetGrpName(objCfgTemplateRequestType
                        .getAssetGrpName());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getAssetNumber())) {
                objCfgTemplateVO.setAssetNumber(objCfgTemplateRequestType
                        .getAssetNumber());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.ASSETNUMBER_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getStatus())) {
                objCfgTemplateVO.setStatus(objCfgTemplateRequestType
                        .getStatus());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.STATUS_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getTemplate())) {
                objCfgTemplateVO.setTemplate(objCfgTemplateRequestType
                        .getTemplate());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.TEMPLATE_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getTitle())) {
                objCfgTemplateVO.setTitle(objCfgTemplateRequestType
                        .getTitle());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.TITLE_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getVersion())) {
                objCfgTemplateVO.setVersion(objCfgTemplateRequestType
                        .getVersion());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.TITLE_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getUserName())) {
                objCfgTemplateVO.setUserName(objCfgTemplateRequestType
                        .getUserName());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.USERID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getCustomerId())) {
                objCfgTemplateVO.setCustomerId(objCfgTemplateRequestType
                        .getCustomerId());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.CUSTOMERID_NOT_PROVIDED);
            }

            objCfgTemplateVO.setVehStatusObjId(objCfgTemplateRequestType
                    .getVehStatusObjId());
            
            result = massApplyCfgServiceIntf
                    .reApplyTemplate(objCfgTemplateVO);
        } catch (Exception ex) {
            result = OMDConstants.FAILURE;
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return result;
    }
    
    /**
     * @Author :
     * @return :String
     * @param : UriInfo ui
     * @throws :RMDWebException
     * @Description: This method is Responsible to re-notify vehicle
     *               Configuration Template.
     * 
     */
    @GET
    @Path(OMDConstants.RE_NOTIFY_TEMPLATE_ASSOCIATION)
    public String reNotifyTemplateAssociation(@Context UriInfo ui){
        String result = RMDCommonConstants.EMPTY_STRING;
        MultivaluedMap<String,String> queryParamMap = null;
        String vehStausObjId = RMDCommonConstants.EMPTY_STRING;
        String userId = RMDCommonConstants.EMPTY_STRING;
        
        try{
            queryParamMap = ui.getQueryParameters();
            if(queryParamMap.containsKey(RMDCommonConstants.VEH_STATUS_OBJID)){
                vehStausObjId=queryParamMap.getFirst(RMDCommonConstants.VEH_STATUS_OBJID);
            }
            if(queryParamMap.containsKey(RMDCommonConstants.USERID)){
                userId=queryParamMap.getFirst(RMDCommonConstants.USERID);
            }
            
            if(!RMDCommonUtility.isNullOrEmpty(vehStausObjId) && !RMDCommonUtility.isNullOrEmpty(userId))
                result=massApplyCfgServiceIntf.reNotifyTemplateAssociation(vehStausObjId,userId);
            else
                result = RMDCommonConstants.NO_DATA_FOUND;
            
        }catch (Exception ex) {
            result = OMDConstants.FAILURE;
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        
        
        return result;
        
    }
    
    /**
     * @Author :
     * @return :String
     * @param : UriInfo ui
     * @throws :RMDServiceException
     * @Description: This method is Responsible to update the RCI template
     * 
     */
    @GET
    @Path(OMDConstants.UPDATE_RCI_TEMPLATE)
    public String updateRCITemplate(@Context UriInfo ui) throws RMDServiceException{
        String result = RMDCommonConstants.EMPTY_STRING;
        MultivaluedMap<String,String> queryParamMap = null;
        String tempObjId = RMDCommonConstants.EMPTY_STRING;
        String templateNo = RMDCommonConstants.EMPTY_STRING;
        String versionNo = RMDCommonConstants.EMPTY_STRING;
        String userId = RMDCommonConstants.EMPTY_STRING;
        String status = RMDCommonConstants.EMPTY_STRING;
        try{
            queryParamMap = ui.getQueryParameters();
            if(queryParamMap.containsKey(RMDCommonConstants.TEMP_OBJ_ID)){
                tempObjId=queryParamMap.getFirst(RMDCommonConstants.TEMP_OBJ_ID);
            }
            if(queryParamMap.containsKey(RMDCommonConstants.TEMPLATE_NO)){
                templateNo=queryParamMap.getFirst(RMDCommonConstants.TEMPLATE_NO);
            }
            if(queryParamMap.containsKey(RMDCommonConstants.VERSION_NO)){
                versionNo=queryParamMap.getFirst(RMDCommonConstants.VERSION_NO);
            }
            if(queryParamMap.containsKey(RMDCommonConstants.USERID)){
                userId=queryParamMap.getFirst(RMDCommonConstants.USERID);
            }
            if(queryParamMap.containsKey(RMDCommonConstants.STATUS)){
                status=queryParamMap.getFirst(RMDCommonConstants.STATUS);
            }
            
            if(!RMDCommonUtility.isNullOrEmpty(tempObjId) && !RMDCommonUtility.isNullOrEmpty(userId) && !RMDCommonUtility.isNullOrEmpty(templateNo) && !RMDCommonUtility.isNullOrEmpty(versionNo))
                result=massApplyCfgServiceIntf.updateRCITemplate(tempObjId,templateNo,versionNo,userId,status);
            else
                result = RMDCommonConstants.NO_DATA_FOUND;
            
        }catch (Exception ex) {
            result = OMDConstants.FAILURE;
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        
        
        return result;
        
    }
	
}

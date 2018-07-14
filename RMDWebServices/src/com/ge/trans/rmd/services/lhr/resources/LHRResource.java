package com.ge.trans.rmd.services.lhr.resources;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.tools.lhr.service.intf.LhrServiceIntf;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRuleFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthScoreRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LhrResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesCommResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.PerformanceHealthDataVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.RxVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.VehicleHealthDataVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.lhr.valueobjects.AssetRequest;
import com.ge.trans.rmd.services.lhr.valueobjects.HealthRuleFiring;
import com.ge.trans.rmd.services.lhr.valueobjects.HealthRuleFiringRequestType;
import com.ge.trans.rmd.services.lhr.valueobjects.HealthRuleRequest;
import com.ge.trans.rmd.services.lhr.valueobjects.HealthRulesFiringResponseType;
import com.ge.trans.rmd.services.lhr.valueobjects.HealthRulesRequest;
import com.ge.trans.rmd.services.lhr.valueobjects.HealthRulesResponse;
import com.ge.trans.rmd.services.lhr.valueobjects.HealthScoreRequest;
import com.ge.trans.rmd.services.lhr.valueobjects.LocomotiveCommunicationRequest;
import com.ge.trans.rmd.services.lhr.valueobjects.LocomotiveCommunicationResponse;
import com.ge.trans.rmd.services.lhr.valueobjects.Locomotives;
import com.ge.trans.rmd.services.lhr.valueobjects.PerformanceHealthDataResponse;
import com.ge.trans.rmd.services.lhr.valueobjects.RecommendationResponse;
import com.ge.trans.rmd.services.lhr.valueobjects.VehicleHealthDataListResponse;
import com.ge.trans.rmd.services.lhr.valueobjects.VehicleHealthDataResponse;

@Path(OMDConstants.LHRSERVICE)
@Component
public class LHRResource extends BaseResource {
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(LHRResource.class);
	
	@Autowired
	LhrServiceIntf lhrServiceIntf;
	@Autowired
	OMDResourceMessagesIntf omdResourceMessagesIntf;
	
	@POST
	@Path(OMDConstants.GET_LHR_HEALTH_FOR_VEHICLES)
	@Produces({MediaType.APPLICATION_JSON })
	public VehicleHealthDataListResponse getVehicleHealthDataScore(HealthScoreRequest healthScoreRequest) throws Exception {
		GregorianCalendar objGregorianCalendar = null;
		XMLGregorianCalendar lastUpdatedDate;
		LhrResponseVO lhrResponseVO;
		VehicleHealthDataListResponse vehicleHealthDataListResponse = null;
		
		try {
			
			if(null == healthScoreRequest || null == healthScoreRequest.getAssets()){
				throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			
			List<HealthScoreRequestVO> assetList = new ArrayList<HealthScoreRequestVO>();
			
			for(AssetRequest assetData : healthScoreRequest.getAssets()){
				HealthScoreRequestVO requestVo = new HealthScoreRequestVO();
				requestVo.setRoadNumberHeader(assetData.getRoadNumberHeader());
				requestVo.setRoadNumber(assetData.getRoadNumber());
				assetList.add(requestVo);
			}
			
			lhrResponseVO = lhrServiceIntf.getVehicleHealthData(assetList);

			vehicleHealthDataListResponse = new VehicleHealthDataListResponse();

			if (null != lhrResponseVO.getVehicleHealthDataVOList()
					&& !lhrResponseVO.getVehicleHealthDataVOList().isEmpty()) {

				List<VehicleHealthDataResponse> vehicleHealthDataResponseList = new ArrayList<VehicleHealthDataResponse>();

				for (VehicleHealthDataVO vehicleVO : lhrResponseVO
						.getVehicleHealthDataVOList()) {
					VehicleHealthDataResponse vehicleHealthDataResponse = new VehicleHealthDataResponse();
					vehicleHealthDataResponse.setCustomerId(vehicleVO
							.getVehicle().getCustomerId());
					vehicleHealthDataResponse.setRoadNumber(vehicleVO
							.getVehicle().getRoadNumber());
					vehicleHealthDataResponse.setFleetId(vehicleVO.getVehicle()
							.getFleetId());
					vehicleHealthDataResponse.setModelId(vehicleVO.getVehicle()
							.getModelId());
					vehicleHealthDataResponse.setHealthScore(vehicleVO
							.getHealthFactor());
					vehicleHealthDataResponse.setDefectLevelCode(vehicleVO
							.getDefectLevelCode());
					vehicleHealthDataResponse.setConfidence(vehicleVO
							.getConfidence());
					if (null != vehicleVO.getCalculationDate()) {
						objGregorianCalendar = new GregorianCalendar();
						objGregorianCalendar.setTime(vehicleVO
								.getCalculationDate());
						lastUpdatedDate = DatatypeFactory.newInstance()
								.newXMLGregorianCalendar(objGregorianCalendar);
						vehicleHealthDataResponse
								.setCalculationDate(lastUpdatedDate);
					}

					List<PerformanceHealthDataVO> perfHealthVOList = vehicleVO
							.getComponentHealthList();
					if(null != perfHealthVOList && !perfHealthVOList.isEmpty()){
						List<PerformanceHealthDataResponse> perfHealthDataResponseList = new ArrayList<PerformanceHealthDataResponse>();
						for (PerformanceHealthDataVO perfHealthVO : perfHealthVOList) {
							PerformanceHealthDataResponse perfHealthDataResponse = new PerformanceHealthDataResponse();

							perfHealthDataResponse
									.setPerformanceCheckName(perfHealthVO
											.getPerformanceCheckName());
							perfHealthDataResponse
									.setPerformanceCheckCode(perfHealthVO
											.getPerformanceCheckCode());
							perfHealthDataResponse.setHealthScore(perfHealthVO
									.getHealthScore());
							perfHealthDataResponse.setConfidence(perfHealthVO
									.getConfidence());
							if (null != perfHealthVO.getCalculationDate()) {
								objGregorianCalendar = new GregorianCalendar();
								objGregorianCalendar.setTime(perfHealthVO
										.getCalculationDate());
								lastUpdatedDate = DatatypeFactory.newInstance()
										.newXMLGregorianCalendar(
												objGregorianCalendar);
								perfHealthDataResponse
										.setCalculationDate(lastUpdatedDate);
							}

							RxVO rxVO = perfHealthVO.getMostCriticalRx();
							if (null != rxVO) {
								RecommendationResponse rxResponse = new RecommendationResponse();
								rxResponse.setTitle(rxVO.getTitle());
								rxResponse.setUrgency(rxVO.getUrgency());
								rxResponse.setImpactDescription(rxVO
										.getImpactDescription());
								rxResponse.setEstimatedRepairTime(rxVO
										.getEstimatedRepairTime());

								if (null != rxVO.getOpenedOn()) {
									objGregorianCalendar = new GregorianCalendar();
									objGregorianCalendar
											.setTime(rxVO.getOpenedOn());
									lastUpdatedDate = DatatypeFactory.newInstance()
											.newXMLGregorianCalendar(
													objGregorianCalendar);
									rxResponse.setOpenedOn(lastUpdatedDate);
								} else {
									rxResponse.setOpenedOn(null);
								}

								if (null != rxVO.getDeliveredOn()) {
									objGregorianCalendar = new GregorianCalendar();
									objGregorianCalendar.setTime(rxVO
											.getDeliveredOn());
									lastUpdatedDate = DatatypeFactory.newInstance()
											.newXMLGregorianCalendar(
													objGregorianCalendar);
									rxResponse.setDeliveredOn(lastUpdatedDate);
								} else {
									rxResponse.setDeliveredOn(null);
								}

								if (null != rxVO.getClosedOn()) {
									objGregorianCalendar = new GregorianCalendar();
									objGregorianCalendar
											.setTime(rxVO.getClosedOn());
									lastUpdatedDate = DatatypeFactory.newInstance()
											.newXMLGregorianCalendar(
													objGregorianCalendar);
									rxResponse.setClosedOn(lastUpdatedDate);
								} else {
									rxResponse.setClosedOn(null);
								}
								perfHealthDataResponse.getMostCriticalRx().add(
										rxResponse);
							}
							perfHealthDataResponseList.add(perfHealthDataResponse);
						}
						vehicleHealthDataResponse.getComponentHealthList().addAll(
								perfHealthDataResponseList);
					}

					vehicleHealthDataResponseList
							.add(vehicleHealthDataResponse);
				}
				vehicleHealthDataListResponse.getVehicleHealthDataList()
						.addAll(vehicleHealthDataResponseList);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return vehicleHealthDataListResponse;
	}
	
	@POST
	@Path(OMDConstants.GET_HEALTH_RULES_FIRING)
	@Produces({ MediaType.APPLICATION_JSON })
	public HealthRulesFiringResponseType getHealthRulesFiring(HealthRuleFiringRequestType healthRuleFiringRequestType) throws Exception {
		HealthRulesFiringResponseType healthRuleFiringResponse = null;
		GregorianCalendar objGregorianCalendar = null;
		XMLGregorianCalendar lastUpdatedDate;
		try {
			
			String lmsLocoId = null;
			List<Long> ruleIds = new ArrayList<Long>();
			
			if(null == healthRuleFiringRequestType){
				throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			
			lmsLocoId = String.valueOf(healthRuleFiringRequestType.getLocomotiveId());
						
			if (null == lmsLocoId) {	
				throw new OMDInValidInputException(OMDConstants.LOCO_ID_NOT_PROVIDED);
			}
			
			for (HealthRuleRequest healthRule : healthRuleFiringRequestType
					.getHealthRules()) {
				if (healthRule.getRuleId() > 0)
					ruleIds.add(healthRule.getRuleId());
			}
			
			if (ruleIds.isEmpty()) {	
				throw new OMDInValidInputException(OMDConstants.HEALTH_RULES_LIST_ID);
			}
			
			List<HealthRuleFiringVO> healthRuleFiringVOList = lhrServiceIntf.getHealthRulesData(lmsLocoId,
					ruleIds);
			
			healthRuleFiringResponse = new HealthRulesFiringResponseType();
			healthRuleFiringResponse.setLocomotiveId(Long.valueOf(lmsLocoId));
			List<HealthRuleFiring> healthRuleList = new ArrayList<HealthRuleFiring>();
			if (null != healthRuleFiringVOList
					&& !healthRuleFiringVOList.isEmpty()) {
				for (HealthRuleFiringVO healthRuleFiringVO : healthRuleFiringVOList) {
					if (null != healthRuleFiringVO) {
						HealthRuleFiring healthRule = new HealthRuleFiring();
						healthRule.setRuleId(healthRuleFiringVO.getRuleId());
						healthRule.setHasFired(healthRuleFiringVO.isHasFired());
						if (null != healthRuleFiringVO.getLastRunDate()) {
							objGregorianCalendar = new GregorianCalendar();
							objGregorianCalendar.setTime(healthRuleFiringVO
									.getLastRunDate());
							lastUpdatedDate = DatatypeFactory.newInstance()
									.newXMLGregorianCalendar(
											objGregorianCalendar);
							healthRule.setRunDate(lastUpdatedDate);
						} else {
							healthRule.setRunDate(null);
						}
						healthRuleList.add(healthRule);
					}
				}
			}	
			healthRuleFiringResponse.setHealthRules(healthRuleList);
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return healthRuleFiringResponse;
	}
	@GET
	@Path(OMDConstants.GET_HEALTH_RULES)
	@Produces({ MediaType.APPLICATION_JSON })
	public List<HealthRulesResponse> getHealthRules() throws Exception {
		List<HealthRulesResponse> healthRuleList = null;
		try {

			List<HealthRulesVO> healthRulesVOList = lhrServiceIntf
					.getHealthRules();

			healthRuleList = new ArrayList<HealthRulesResponse>();

			if (null != healthRulesVOList && !healthRulesVOList.isEmpty()) {

				for (HealthRulesVO healthRulesVO : healthRulesVOList) {
					HealthRulesResponse healthRule = new HealthRulesResponse();
					healthRule.setRuleId(healthRulesVO.getRuleId());
					healthRule.setRuleTitle(EsapiUtil.stripXSSCharacters(healthRulesVO.getRuleTitle()));
					healthRule.setRuleType(EsapiUtil.stripXSSCharacters(healthRulesVO.getRuleType()));
					healthRule.setFamily(EsapiUtil.stripXSSCharacters(healthRulesVO.getFamily()));
					healthRuleList.add(healthRule);
				}
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return healthRuleList;
	}
	
	@POST
	@Path(OMDConstants.GET_HEALTH_RULES_FIRING_DETAILS)
	@Produces({ MediaType.APPLICATION_JSON })
	public List<HealthRulesFiringResponseType> getHealthRulesFiringDetails(HealthRulesRequest healthRulesRequest) throws Exception {
		GregorianCalendar objGregorianCalendar = null;
		XMLGregorianCalendar lastUpdatedDate;
		List<HealthRulesFiringResponseType> healthRulesFiringResponseTypeList = null;
		try {
			
			
			if(null == healthRulesRequest.getLocomotives() && !healthRulesRequest.getLocomotives().isEmpty()){
				throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			
			List<Locomotives> locomotivesList = healthRulesRequest.getLocomotives();
			List<LocomotivesRequestVO> locomotivesVOList = new ArrayList<LocomotivesRequestVO>();
			
			for(Locomotives locoRequest : locomotivesList){
				
				LocomotivesRequestVO locoRequestVO = new LocomotivesRequestVO();
				locoRequestVO.setLocomotiveId(locoRequest.getLocomotiveId());
				
				List<Long> ruleIds = new ArrayList<Long>();
				for(HealthRuleRequest healthRuleRequest: locoRequest.getHealthRules()){
					ruleIds.add(healthRuleRequest.getRuleId());
				}
				locoRequestVO.setHealthRules(ruleIds);
				locomotivesVOList.add(locoRequestVO);
			}
			
			List<LocomotivesResponseVO> healthRuleFiringVOList = lhrServiceIntf.getHealthFiringDetails(locomotivesVOList,"Health");
			
			healthRulesFiringResponseTypeList = new ArrayList<HealthRulesFiringResponseType>();
			
			for(LocomotivesResponseVO locomotivesResponseVO: healthRuleFiringVOList){

				locomotivesResponseVO.getHealthRules();
				
				HealthRulesFiringResponseType healthRuleFiringResponse = new HealthRulesFiringResponseType();
				healthRuleFiringResponse.setLocomotiveId(locomotivesResponseVO.getLocomotiveId()); 
				List<HealthRuleFiring> healthRuleList = new ArrayList<HealthRuleFiring>();
				
				if (null != locomotivesResponseVO.getHealthRules()
						&& !locomotivesResponseVO.getHealthRules().isEmpty()) {
					
					for (HealthRulesFiringVO healthRuleFiringVO : locomotivesResponseVO.getHealthRules()) {
						if (null != healthRuleFiringVO) {
							HealthRuleFiring healthRule = new HealthRuleFiring();
							healthRule.setRuleId(healthRuleFiringVO.getRuleId());
							healthRule.setHasFired(healthRuleFiringVO.isHasFired());
							if (null != healthRuleFiringVO.getLastRunDate()) {
								objGregorianCalendar = new GregorianCalendar();
								objGregorianCalendar.setTime(healthRuleFiringVO
										.getLastRunDate());
								lastUpdatedDate = DatatypeFactory.newInstance()
										.newXMLGregorianCalendar(
												objGregorianCalendar);
								healthRule.setRunDate(lastUpdatedDate);
							} else {
								healthRule.setRunDate(null);
							}
							healthRuleList.add(healthRule);
						}
					}
				}	
				healthRuleFiringResponse.setHealthRules(healthRuleList);
				healthRulesFiringResponseTypeList.add(healthRuleFiringResponse);
			}
		
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return healthRulesFiringResponseTypeList;
	}
	@POST
	@Path(OMDConstants.GET_DIAGNOSTIC_RULES_FIRING_DETAILS)
	@Produces({ MediaType.APPLICATION_JSON })
	public List<HealthRulesFiringResponseType> getDiagnosticRulesFiringDetails(HealthRulesRequest healthRulesRequest) throws Exception {
		GregorianCalendar objGregorianCalendar = null;
		XMLGregorianCalendar lastUpdatedDate;
		List<HealthRulesFiringResponseType> healthRulesFiringResponseTypeList = null;
		try {
			
			
			if(null == healthRulesRequest.getLocomotives() && !healthRulesRequest.getLocomotives().isEmpty()){
				throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			
			List<Locomotives> locomotivesList = healthRulesRequest.getLocomotives();
			List<LocomotivesRequestVO> locomotivesVOList = new ArrayList<LocomotivesRequestVO>();
			
			for(Locomotives locoRequest : locomotivesList){
				
				LocomotivesRequestVO locoRequestVO = new LocomotivesRequestVO();
				locoRequestVO.setLocomotiveId(locoRequest.getLocomotiveId());
				
				List<Long> ruleIds = new ArrayList<Long>();
				for(HealthRuleRequest healthRuleRequest: locoRequest.getHealthRules()){
					ruleIds.add(healthRuleRequest.getRuleId());
				}
				locoRequestVO.setHealthRules(ruleIds);
				locomotivesVOList.add(locoRequestVO);
			}
			
			List<LocomotivesResponseVO> healthRuleFiringVOList = lhrServiceIntf.getHealthFiringDetails(locomotivesVOList,"Diagnostic");
			
			healthRulesFiringResponseTypeList = new ArrayList<HealthRulesFiringResponseType>();
			
			for(LocomotivesResponseVO locomotivesResponseVO: healthRuleFiringVOList){

				locomotivesResponseVO.getHealthRules();
				
				HealthRulesFiringResponseType healthRuleFiringResponse = new HealthRulesFiringResponseType();
				healthRuleFiringResponse.setLocomotiveId(locomotivesResponseVO.getLocomotiveId()); 
				List<HealthRuleFiring> healthRuleList = new ArrayList<HealthRuleFiring>();
				
				if (null != locomotivesResponseVO.getHealthRules()
						&& !locomotivesResponseVO.getHealthRules().isEmpty()) {
					
					for (HealthRulesFiringVO healthRuleFiringVO : locomotivesResponseVO.getHealthRules()) {
						if (null != healthRuleFiringVO) {
							HealthRuleFiring healthRule = new HealthRuleFiring();
							healthRule.setRuleId(healthRuleFiringVO.getRuleId());
							healthRule.setHasFired(healthRuleFiringVO.isHasFired());
							if (null != healthRuleFiringVO.getLastRunDate()) {
								objGregorianCalendar = new GregorianCalendar();
								objGregorianCalendar.setTime(healthRuleFiringVO
										.getLastRunDate());
								lastUpdatedDate = DatatypeFactory.newInstance()
										.newXMLGregorianCalendar(
												objGregorianCalendar);
								healthRule.setRunDate(lastUpdatedDate);
							} else {
								healthRule.setRunDate(null);
							}
							healthRuleList.add(healthRule);
						}
					}
				}	
				healthRuleFiringResponse.setHealthRules(healthRuleList);
				healthRulesFiringResponseTypeList.add(healthRuleFiringResponse);
			}
		
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return healthRulesFiringResponseTypeList;
	}	
	@POST
	@Path(OMDConstants.GET_LOCOMOTIVE_COMMUNICATION_DETAILS)
	@Produces({ MediaType.APPLICATION_JSON })
	public List<LocomotiveCommunicationResponse> getLocomotiveCommunicationDetails(LocomotiveCommunicationRequest locoCommunicationRequest) throws Exception {
		List<LocomotiveCommunicationResponse> locomotiveCommunicationResponseList = null;
		try {
			
			if(null == locoCommunicationRequest.getLocomotives() && !locoCommunicationRequest.getLocomotives().isEmpty()){
				throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
						
			List<LocomotivesCommResponseVO> locomotivesCommResponseVOList = lhrServiceIntf.getLocoCommunicationDetails(locoCommunicationRequest.getLocomotives());
			
			locomotiveCommunicationResponseList = new ArrayList<LocomotiveCommunicationResponse>();
			
			for(LocomotivesCommResponseVO locomotivesCommResponseVO: locomotivesCommResponseVOList){
				LocomotiveCommunicationResponse locomotiveCommunicationResponse = new LocomotiveCommunicationResponse();
				locomotiveCommunicationResponse.setLocomotiveId(locomotivesCommResponseVO.getLocomotiveId());
				locomotiveCommunicationResponse.setRoadNumber(locomotivesCommResponseVO.getRoadNumber());
				locomotiveCommunicationResponse.setVehicleHeader(locomotivesCommResponseVO.getVehicleHeader());
				locomotiveCommunicationResponse.setNextMaintenanceType(locomotivesCommResponseVO.getNextMaintenanceType());
				locomotiveCommunicationResponse.setNextMaintenanceDate(locomotivesCommResponseVO.getNextMaintenanceDate());
				locomotiveCommunicationResponse.setLastFaultMsgDate(locomotivesCommResponseVO.getLastFaultMsgDate());
				locomotiveCommunicationResponse.setLastATSMsgDate(locomotivesCommResponseVO.getLastATSMsgDate());
				locomotiveCommunicationResponse.setLastTOStatusMsgDate(locomotivesCommResponseVO.getLastTOStatusMsgDate());
				locomotiveCommunicationResponseList.add(locomotiveCommunicationResponse);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return locomotiveCommunicationResponseList;
	}
}

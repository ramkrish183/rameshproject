/**
 * ============================================================
 * Classification: GE Confidential
 * File : AssetResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.assets.resources;
 * Author : iGATE Global Solutions Ltd.
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
package com.ge.trans.rmd.services.assets.resources;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.admin.bo.intf.PopupListAdminBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.AssetEoaServiceIntf;
import com.ge.trans.eoa.services.asset.service.intf.CallLogNotesServiceIntf;
import com.ge.trans.eoa.services.asset.service.intf.HealthCheckServiceIntf;
import com.ge.trans.eoa.services.asset.service.intf.SoftwareHistoryServiceIntf;
import com.ge.trans.eoa.services.asset.service.intf.UnitRenumberServiceIntf;
import com.ge.trans.eoa.services.asset.service.intf.VehicleCommStatusEoaServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetHeaderServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetLocationDetailVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetsLocationFromShopVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CallLogNotesVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultCodeDetailsServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FleetVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckSubmitEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HlthChckRqstServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.KeepAliveHisServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LifeStatisticsEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RegionVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RoleAssetsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleComStatusInputEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCommServiceResultEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewReqHistoryEoaVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewRespHistoryEoaVo;
import com.ge.trans.eoa.services.cases.service.intf.CaseEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.intf.FindCaseLiteEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.DataScreenServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ParameterServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SoftwareHistoryRequestVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleFaultEoaServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleCommonServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ParameterRequestServiceVO;
import com.ge.trans.rmd.asset.valueobjects.AssetInstalledProductVO;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorBean;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAttributeVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAvailableVO;
import com.ge.trans.rmd.common.valueobjects.SoftwareHistoryVO;
import com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.admin.valueobjects.ApplicationParametersResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.Asset;
import com.ge.trans.rmd.services.assets.valueobjects.AssetDeliveredCasesResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetHeaderSearchResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetInstalledProductResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetLocRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetLocationDetail;
import com.ge.trans.rmd.services.assets.valueobjects.AssetLocationRequest;
import com.ge.trans.rmd.services.assets.valueobjects.AssetLocationResponse;
import com.ge.trans.rmd.services.assets.valueobjects.AssetLocatorResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetNumberResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetServicesResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetsRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.CallLogNotesResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.Customer;
import com.ge.trans.rmd.services.assets.valueobjects.FamilyDetail;
import com.ge.trans.rmd.services.assets.valueobjects.FaultCodeDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultDataDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultDataResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultLogServiceResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.Fleet;
import com.ge.trans.rmd.services.assets.valueobjects.FleetResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.HcViewHistoryRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.HealthCheckAvailableResponse;
import com.ge.trans.rmd.services.assets.valueobjects.HealthCheckRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.HealthCheckResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.HealthCheckSubmitRequest;
import com.ge.trans.rmd.services.assets.valueobjects.HealthCheckViewResponse;
import com.ge.trans.rmd.services.assets.valueobjects.HealthcheckSubmitResponseType;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsSubmitRequestVO;
import com.ge.trans.rmd.services.assets.valueobjects.RDRNotificationsSubmitRequest;
import com.ge.trans.rmd.services.assets.valueobjects.KeepAliveHistoryType;
import com.ge.trans.rmd.services.assets.valueobjects.LastDownLoadStatusResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.LastFaultStatusResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.LifeStatisticsRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.LifeStatisticsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.Model;
import com.ge.trans.rmd.services.assets.valueobjects.ParameterResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ProductResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.RegionResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.SotwareHistoryResponceType;
import com.ge.trans.rmd.services.assets.valueobjects.SubDivisionResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ToolParamInfoResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ToolsParamGroupResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.UnitHeaderUpdateRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.UnitRenumberingRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.VehicleCommStatusResponseType;
import com.ge.trans.rmd.services.cases.resources.CaseLiteResource;
import com.ge.trans.rmd.services.cases.valueobjects.CasesRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionConfig;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionParameterRequest;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.getransporatation.railsolutions.mcs.Fault;
import com.getransporatation.railsolutions.mcs.SendMultiDeviceMessageRequest;
import com.getransporatation.railsolutions.mcs.SendMultiDeviceMessageResponse;
import com.getransporatation.railsolutions.mcs.SendMultiDeviceMessageService;
import com.getransporatation.railsolutions.mcs.SendMultiDeviceMessageServiceInterface;
/*******************************************************************************
 * 
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as assetservice Webservices and provide the
 *              Asset related funtionalities
 * @History :
 * 
 ******************************************************************************/

@Path(OMDConstants.ASSET_SERVICE)

@Component
public class AssetResource extends BaseResource {
    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(AssetResource.class);

    @Autowired
    private RuleCommonServiceIntf objRuleCommonServiceInf;
    @Autowired
    private CaseLiteResource caseLiteResource;    
    @Autowired
	private FindCaseLiteEoaServiceIntf findCaseLiteEoaServiceIntf;
    @Autowired
    private VehicleCommStatusEoaServiceIntf objVehicleCommStatusEoaServiceIntf;
    @Autowired
    private HealthCheckServiceIntf objHealthChkServiceIntf;
    @Autowired
    @Qualifier("ruleServiceInf")
    private RuleServiceIntf objRuleServiceIntf;
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    private AssetEoaServiceIntf objAssetEoaServiceIntf;
    @Autowired
    private CaseEoaServiceIntf objCaseEoaServiceIntf;
    @Autowired
    private CallLogNotesServiceIntf objCallLogNotesServiceIntf;
    @Autowired
	private PopupListAdminBOIntf popupListAdminBOIntf;
    @Autowired
    private UnitRenumberServiceIntf unitRenumberServiceIntf;
    @Autowired
    private SoftwareHistoryServiceIntf softwareHistoryServiceIntf;
    
    @Value("${" + OMDConstants.HC_MCS_SENDMESSAGE_URL + "}")
	String hcSendMessageServiceURL;
	@Value("${" + OMDConstants.HC_MCS_SENDMESSAGE_USERNAME + "}")
	String hcSendMessageServiceUsername;
	@Value("${" + OMDConstants.HC_MCS_SENDMESSAGE_PASSWORD + "}")
	String hcSendMessageServicePassowrd;
    
    
    @Value("${" + OMDConstants.MCS_ASSETLIST_URL + "}")
    String assetServiceURL;
    @Value("${" + OMDConstants.MCS_USERNAME + "}")
    String assetServiceUsername;
    @Value("${" + OMDConstants.MCS_PASSWORD + "}")
    String assetServicePassword;

    /**
     * This Method is used for retrieving list of Model details for the
     * parameters passed in UriInfo
     * 
     * @param ui
     * @return List of Model - id,name
     * @throws RMDServiceException
     *             Change made in method to retrive list of Model details based
     *             on Customer, Fleet, Unit No and Config
     */
    @GET
    @Path(OMDConstants.GET_MODELS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Model> getModel(@Context final UriInfo ui) throws RMDServiceException {
        List<ElementVO> arlModel = new ArrayList<ElementVO>();
        String strLanguage = null;
        String strFamily = null;
        Model objModelResponse = null;
        String customer = null;
        String fleet = null;
        String config = null;
        String unitNumber = null;
        String[] strConfig = null;
        String strCfg = null;
        final List<Model> lstModelResponse = new ArrayList<Model>();
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            // Passing only Families to bring Models.
            if (queryParams.containsKey(OMDConstants.FAMILY) && !(queryParams.containsKey(OMDConstants.CUSTOMER)
                    || queryParams.containsKey(OMDConstants.FLEET) || queryParams.containsKey(OMDConstants.CONFIG)
                    || queryParams.containsKey(OMDConstants.UNIT_NUMBER))) {
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                strFamily = queryParams.getFirst(OMDConstants.FAMILY);
                int[] paramFlag = { OMDConstants.ALPHA_NUMERIC_HYPEN };

                if ((!RMDCommonUtility.isNullOrEmpty(strFamily) && (!RMDCommonUtility.isNullOrEmpty(strLanguage)))
                        && AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.FAMILY)) {
                    arlModel = objRuleServiceIntf.getModelList(strFamily, strLanguage,
                            queryParams.getFirst(OMDConstants.KEP_MODELS));
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_SEARCH_CRITERIA);
                }

            }
            // Passing all or any Params including family to bring the Models.
            else if (queryParams.containsKey(OMDConstants.CUSTOMER) || queryParams.containsKey(OMDConstants.FLEET)
                    || queryParams.containsKey(OMDConstants.CONFIG) || queryParams.containsKey(OMDConstants.UNIT_NUMBER)
                    || queryParams.containsKey(OMDConstants.FAMILY)) {
                int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_HYPEN,
                        RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.DOUBLE_HYPHEN,
                        RMDCommonConstants.AlPHA_NUMERIC, OMDConstants.ALPHA_NUMERIC_UNDERSCORE };

                if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.CUSTOMER,
                        OMDConstants.FLEET, OMDConstants.CONFIG, OMDConstants.UNIT_NUMBER, OMDConstants.FAMILY)) {
                    strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                    fleet = queryParams.getFirst(OMDConstants.FLEET);
                    config = queryParams.getFirst(OMDConstants.CONFIG);
                    unitNumber = queryParams.getFirst(OMDConstants.UNIT_NUMBER);
                    if (null != config) {
                        strCfg = config.replaceAll(OMDConstants.SYMBOL_NEGETION, OMDConstants.EMPTY_STRING);
                        strConfig = strCfg.split(OMDConstants.SYMBOL_DOUBLESLASH + RMDCommonConstants.CAPSYMBOL);
                    }
                    if (queryParams.getFirst(OMDConstants.FAMILY) != null) {
                        strFamily = queryParams.getFirst(OMDConstants.FAMILY);
                    }
                    arlModel = objRuleServiceIntf.getModelList(customer, fleet, strConfig, unitNumber, strFamily,
                            strLanguage, queryParams.getFirst(OMDConstants.KEP_MODELS));
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }
            } else {
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                arlModel = objRuleCommonServiceInf.getModel(strLanguage);
            }

            if (RMDCommonUtility.isCollectionNotEmpty(arlModel)) {
                for (final Iterator iterator = arlModel.iterator(); iterator.hasNext();) {
                    final ElementVO objElementVO = (ElementVO) iterator.next();
                    objModelResponse = new Model();
                    BeanUtility.copyBeanProperty(objElementVO, objModelResponse);
                    lstModelResponse.add(objModelResponse);
                }
            }
            else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }
            LOG.debug("Arraylist of Model Response " + lstModelResponse);
        } catch (Exception ex) {
        	RMDLOGGER.error(ex, ex);
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return lstModelResponse;
    }
    
    /**
     * This Method is used for retrieving notifications of a particular user
     * Added by Raj.S(212687474)
     * @param ui
     * @return RDRNotificationsDataVO - value object
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.ASSET_SERVICE_GET_RDR_NOTIFICATIONS)
    @Produces({MediaType.APPLICATION_JSON})
    public List<RDRNotificationsDataVO> getRDRNotifications(@Context final UriInfo ui) throws RMDServiceException {
    	RMDLOGGER.debug("AssetResource : Inside getRDRNotifications() method:::::Start ");
    	List<RDRNotificationsDataVO> notifications = null;
    	MultivaluedMap<String,String> queryParamMap = null;
    	String userId = null;
    	try {
    		if(null!=ui.getQueryParameters()) {
    			queryParamMap = ui.getQueryParameters();
    		}
    		userId = queryParamMap.getFirst(OMDConstants.GET_RDR_NOTIFICATIONS_USER_ID);
    		int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC };
    		if (AppSecUtil.validateWebServiceInput(queryParamMap, null, methodConstants,
    				OMDConstants.GET_RDR_NOTIFICATIONS_USER_ID)) {
                notifications = objHealthChkServiceIntf.getRDRNotifications(userId);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
    	}
    	catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside getRDRNotifications() method:::::End ");
    	return notifications;
    }
    
    /**
     * This Method is used for updating a notification
     * Added by Raj.S(212687474)
     * @param ui
     * @return boolean
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.ASSET_SERVICE_UPDATE_RDR_NOTIFICATION)
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON})
    public boolean updateRDRNotification(RDRNotificationsSubmitRequest submitRequest) throws RMDServiceException {
    	RMDLOGGER.debug("AssetResource : Inside updateRDRNotification() method:::::Start ");
    	RDRNotificationsSubmitRequestVO notification = new RDRNotificationsSubmitRequestVO();
    	notification.setAssetGrpName(submitRequest.getAssetGrpName());
    	notification.setAssetNumber(submitRequest.getAssetNumber());
    	notification.setCustomerID(submitRequest.getCustomerID());
    	notification.setFromDate(submitRequest.getFromDate());
    	notification.setStatus(submitRequest.getStatus());
    	notification.setToDate(submitRequest.getToDate());
    	notification.setUserID(submitRequest.getUserID());
    	boolean status = false;
    	try {
    		status = objHealthChkServiceIntf.updateRDRNotification(notification);
    	}
    	catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside updateRDRNotifications() method:::::End ");
    	return status;
    }
    
    /**
     * This Method is used for inserting a notification
     * Added by Raj.S(212687474)
     * @param ui
     * @return boolean
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.ASSET_SERVICE_INSERT_RDR_NOTIFICATION)
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON})
    public boolean insertRDRNotification(RDRNotificationsSubmitRequest submitRequest) throws RMDServiceException {
    	RMDLOGGER.debug("AssetResource : Inside insertRDRNotification() method:::::Start ");
    	RDRNotificationsSubmitRequestVO notification = new RDRNotificationsSubmitRequestVO();
    	notification.setAssetGrpName(submitRequest.getAssetGrpName());
    	notification.setAssetNumber(submitRequest.getAssetNumber());
    	notification.setCustomerID(submitRequest.getCustomerID());
    	notification.setFromDate(submitRequest.getFromDate());
    	notification.setStatus(submitRequest.getStatus());
    	notification.setToDate(submitRequest.getToDate());
    	notification.setUserID(submitRequest.getUserID());
    	boolean status = false;
    	try {
    		status = objHealthChkServiceIntf.insertRDRNotification(notification);
    	}
    	catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside insertRDRNotifications() method:::::End ");
    	return status;
    }
    
    /**
     * This Method is used for retrieving details about Vehicle Common status
     * for the input passed in UriInfo
     * 
     * @param ui
     * @return VehicleCommStatusResponseType - value object
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_VEHICLE_COMMON_STATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public VehicleCommStatusResponseType[] getVehicleCommStatus(@Context final UriInfo ui) throws RMDServiceException {

        VehicleCommServiceResultEoaVO[] commServiceResultVO = null;
        VehicleComStatusInputEoaServiceVO comStatusInputServiceVO = null;
        final List lstCommStatusResponseType = new ArrayList();
        String timeZone = getDefaultTimeZone();
        try {
            DatatypeFactory dataFactory = DatatypeFactory.newInstance();
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.isEmpty()) {
                /* Added By iGate Patni */

                commServiceResultVO = objVehicleCommStatusEoaServiceIntf
                        .getVehicleCommStatusDetails(comStatusInputServiceVO);

            } else {
                int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC,
                        RMDCommonConstants.AlPHA_NUMERIC };
                if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ASSET_NUMBER,
                        OMDConstants.GROUP_NAME, OMDConstants.CUSTOMER_ID)) {
                    comStatusInputServiceVO = new VehicleComStatusInputEoaServiceVO();
                    String assetNumber = OMDConstants.EMPTY_STRING;
                    final String language = getRequestHeader(OMDConstants.LANGUAGE);
                    comStatusInputServiceVO.setStrLanguage(language);
                    if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                        assetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
                        if (assetNumber != null && !assetNumber.isEmpty())
                            comStatusInputServiceVO.setStrRoadNumber(assetNumber);
                    }
                    if (queryParams.containsKey(OMDConstants.GROUP_NAME)) {
                        if (queryParams.getFirst(OMDConstants.GROUP_NAME) != null
                                && !queryParams.getFirst(OMDConstants.GROUP_NAME).isEmpty())
                            comStatusInputServiceVO.setAssetGroupName(queryParams.getFirst(OMDConstants.GROUP_NAME));
                    }
                    if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                        if (queryParams.getFirst(OMDConstants.CUSTOMER_ID) != null
                                && !queryParams.getFirst(OMDConstants.CUSTOMER_ID).isEmpty())
                            comStatusInputServiceVO.setCustomerID(queryParams.getFirst(OMDConstants.CUSTOMER_ID));
                    }
                    if (queryParams.containsKey(OMDConstants.LAST_FAULT_FLAG)) {
                        if (queryParams.getFirst(OMDConstants.LAST_FAULT_FLAG) != null
                                && !queryParams.getFirst(OMDConstants.LAST_FAULT_FLAG).isEmpty() && queryParams
                                        .getFirst(OMDConstants.LAST_FAULT_FLAG).equalsIgnoreCase(OMDConstants.YES))
                            comStatusInputServiceVO.setLastFault(true);
                    }

                    commServiceResultVO = objVehicleCommStatusEoaServiceIntf
                            .getVehicleCommStatusDetails(comStatusInputServiceVO);

                    LOG.debug("VehicleCommStatusDetails returned from RMDService Layer");
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                            omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                    new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
            }

            for (int i = 0; i < commServiceResultVO.length; i++) {
                VehicleCommStatusResponseType commStatusResponseType = new VehicleCommStatusResponseType();
                GregorianCalendar objGregorianCalendar1 = null;
                GregorianCalendar objGregorianCalendar2 = null;
                GregorianCalendar objGregorianCalendar3 = null;
                GregorianCalendar objGregorianCalendar4 = null;
                GregorianCalendar objGregorianCalendar5 = null;
                XMLGregorianCalendar dtLastFaultResetTime = null;
                XMLGregorianCalendar dtLastFaultReserved = null;
                XMLGregorianCalendar dtLastHealthChkRequest = null;
                XMLGregorianCalendar dtLastKeepAliveMsgRevd = null;
                XMLGregorianCalendar dtLastProcessedDate = null;
                XMLGregorianCalendar date = null;
                List<KeepAliveHisServiceVO> listKeepAliveHis = null;
                ListIterator<KeepAliveHisServiceVO> listIterator = null;
                //
                if (null != commServiceResultVO && null == commServiceResultVO[i].getStrAssetNumber()) {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                } else {
                    // Set last fault reset time
                    if (null != commServiceResultVO[i].getDtLastFaultResetTime()) {
                        objGregorianCalendar1 = new GregorianCalendar();
                        objGregorianCalendar1.setTime(commServiceResultVO[i].getDtLastFaultResetTime());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar1, timeZone);
                        dtLastFaultResetTime = dataFactory.newXMLGregorianCalendar(objGregorianCalendar1);
                    }
                    // Set last fault reserved date/ last download date
                    if (null != commServiceResultVO[i].getDtLastFaultReserved()) {
                        objGregorianCalendar2 = new GregorianCalendar();
                        objGregorianCalendar2.setTime(commServiceResultVO[i].getDtLastFaultReserved());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar2, timeZone);
                        dtLastFaultReserved = dataFactory.newXMLGregorianCalendar(objGregorianCalendar2);
                    }
                    // Set last health check request date
                    if (null != commServiceResultVO[i].getDtLastHealthChkRequest()) {
                        objGregorianCalendar3 = new GregorianCalendar();
                        objGregorianCalendar3.setTime(commServiceResultVO[i].getDtLastHealthChkRequest());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar3, timeZone);
                        dtLastHealthChkRequest = dataFactory.newXMLGregorianCalendar(objGregorianCalendar3);
                    }
                    // Set last keep alive msg received date
                    if (null != commServiceResultVO[i].getDtLastKeepAliveMsgRevd()) {
                        objGregorianCalendar4 = new GregorianCalendar();
                        objGregorianCalendar4.setTime(commServiceResultVO[i].getDtLastKeepAliveMsgRevd());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar4, timeZone);
                        dtLastKeepAliveMsgRevd = dataFactory.newXMLGregorianCalendar(objGregorianCalendar4);
                    }
                    // Set last processed date
                    if (null != commServiceResultVO[i].getDtLastProcessedDate()) {
                        objGregorianCalendar5 = new GregorianCalendar();
                        objGregorianCalendar5.setTime(commServiceResultVO[i].getDtLastProcessedDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar5, timeZone);
                        dtLastProcessedDate = dataFactory.newXMLGregorianCalendar(objGregorianCalendar5);
                    }
                    CMBeanUtility.copyVehicleResultVOToVehicleResType(commServiceResultVO[i], commStatusResponseType);
                    // Set keep alive history deatails
                    if (commServiceResultVO[i].getArlKeepAliveHisServiceVO() != null
                            && !commServiceResultVO[i].getArlKeepAliveHisServiceVO().isEmpty()) {
                        listKeepAliveHis = commServiceResultVO[i].getArlKeepAliveHisServiceVO();
                        listIterator = listKeepAliveHis.listIterator();
                        while (listIterator.hasNext()) {
                            KeepAliveHisServiceVO keepAliveHisServiceVO = new KeepAliveHisServiceVO();
                            final KeepAliveHistoryType keepAliveHistoryType = new KeepAliveHistoryType();
                            keepAliveHisServiceVO = listIterator.next();

                            if (null != keepAliveHisServiceVO.getDate()) {
                                final GregorianCalendar objGregorianCalendar = new GregorianCalendar();
                                objGregorianCalendar.setTime(keepAliveHisServiceVO.getDate());
                                RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                                date = dataFactory.newXMLGregorianCalendar(objGregorianCalendar);
                            }
                            keepAliveHistoryType.setModel(keepAliveHisServiceVO.getStrCCA());
                            keepAliveHistoryType.setKeepAliveHistDate(date);

                            commStatusResponseType.getKeepAliveHistory().add(keepAliveHistoryType);
                        }
                    }
                    // Set the values in VehicleCommStatusResponseType
                    commStatusResponseType.setLastFaultResetTime(dtLastFaultResetTime);
                    commStatusResponseType.setLastFaultReceivedTime(dtLastFaultReserved);
                    commStatusResponseType.setLastHealthChkRequestTime(dtLastHealthChkRequest);
                    commStatusResponseType.setLastKeepAliveMsgRevdTime(dtLastKeepAliveMsgRevd);
                    commStatusResponseType.setLastProcessedDate(dtLastProcessedDate);
                    lstCommStatusResponseType.add(commStatusResponseType);
                }
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return (VehicleCommStatusResponseType[]) lstCommStatusResponseType
                .toArray(new VehicleCommStatusResponseType[lstCommStatusResponseType.size()]);

    }

    /**
     * This method is used for retrieving Fault details for the Asset id passed
     * along with the parameters passed in UriInfo
     * 
     * @param roadNumber
     * @param ui
     * @return FaultDataDetailsResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_FAULTS)
    // Updated for making path params instead of query params as per code review
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public FaultDataDetailsServiceVO getFaults(@PathParam(OMDConstants.ASSET_NUMBER) final String roadNumber,
            @PathParam(OMDConstants.ASSET_GROUP_NAME) final String assetGrpName,
            @PathParam(OMDConstants.CUSTOMER_ID) final String customerId, @Context final UriInfo ui)
            throws RMDServiceException {
        final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat(OMDConstants.DATE_FORMAT);
        FaultDataDetailsServiceVO faultData = null;
        String limitedParam = null;
        String hideL3Faults = null;
        String initLoad = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            final VehicleFaultEoaServiceVO faultVO = new VehicleFaultEoaServiceVO();
            faultVO.setStrRoadNo(roadNumber); // Road No
            queryParams.add(OMDConstants.ASSET_NUMBER, roadNumber);
            queryParams.add(OMDConstants.ASSET_GROUP_NAME, assetGrpName);
            queryParams.add(OMDConstants.CUSTOMER_ID, customerId);
            int[] methodConstants = { RMDCommonConstants.VALID_DATE, RMDCommonConstants.VALID_DATE,
                    RMDCommonConstants.ISBOOLEAN, RMDCommonConstants.NUMERIC, RMDCommonConstants.NUMERIC,
                    RMDCommonConstants.NUMERIC, RMDCommonConstants.NUMERIC, RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.AlPHA_NUMERIC, RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(queryParams, OMDConstants.DATE_FORMAT, methodConstants,
                    OMDConstants.FROM_DATE, OMDConstants.TO_DATE, OMDConstants.IS_HEALTH_CHECK, OMDConstants.DAYS,
                    OMDConstants.DAYS_SELECTED, OMDConstants.STARTROW, OMDConstants.ENDROW, OMDConstants.ASSET_NUMBER,
                    OMDConstants.ASSET_GROUP_NAME, OMDConstants.CUSTOMER_ID)) {
                if (!queryParams.isEmpty()) {
                    if (customerId != null) {
                        faultVO.setCustomerId(customerId); // Customer
                    }
                    if (assetGrpName != null) {
                        faultVO.setAssetGrpName(assetGrpName); // Road Initial
                    }
                    if (queryParams.containsKey(OMDConstants.FROM_DATE)) { // From
                                                                           // Date
                        faultVO.setFromDate(SDF_FORMAT.parse(queryParams.getFirst(OMDConstants.FROM_DATE)));
                    }
                    if (queryParams.containsKey(OMDConstants.TO_DATE)) { // To
                                                                         // Date
                        faultVO.setToDate(SDF_FORMAT.parse(queryParams.getFirst(OMDConstants.TO_DATE)));
                    }
                    if (queryParams.containsKey(OMDConstants.IS_HEALTH_CHECK)) { // Health
                                                                                 // Check
                                                                                 // Flag
                                                                                 // -
                                                                                 // true
                                                                                 // /
                                                                                 // false
                        faultVO.setBlnHealtCheck(new Boolean(queryParams.getFirst(OMDConstants.IS_HEALTH_CHECK)));
                    }
                    if (queryParams.containsKey(OMDConstants.SORT_OPTIONS)) { // Sort
                                                                              // Option
                        faultVO.setStrSortOption(queryParams.getFirst(OMDConstants.SORT_OPTIONS));
                    }
                    if (queryParams.containsKey(OMDConstants.DAYS)) { // No of
                                                                      // Days
                        faultVO.setStrDays(queryParams.getFirst(OMDConstants.DAYS));
                    }
                    if (queryParams.containsKey(OMDConstants.START_ROW)) { // Pagination
                                                                           // start
                                                                           // row
                        faultVO.setIntStartPos(Integer.valueOf(queryParams.getFirst(OMDConstants.START_ROW)));
                    }
                    if (queryParams.containsKey(OMDConstants.CASE_Id)) { // Case
                                                                         // ID
                        faultVO.setCaseId(queryParams.getFirst(OMDConstants.CASE_Id));
                    }
                    if (queryParams.containsKey(OMDConstants.CASE_FROM)) { // Case
                                                                           // From
                        faultVO.setStrCaseFrom(queryParams.getFirst(OMDConstants.CASE_FROM));
                    }
                    if (queryParams.containsKey(OMDConstants.CASE_TYPE)) { // Case
                                                                           // Type
                        faultVO.setStrCaseType(queryParams.getFirst(OMDConstants.CASE_TYPE));
                    }
                    if (queryParams.containsKey(OMDConstants.RULE_ID)) { // Rule
                                                                         // ID
                        faultVO.setStrRuleDefId(queryParams.getFirst(OMDConstants.RULE_ID));
                    }
                    if (queryParams.containsKey(OMDConstants.RULE_TYPE)) { // Rule
                                                                           // Type
                        faultVO.setStrJDPADRadio(queryParams.getFirst(OMDConstants.RULE_TYPE));
                    }
                    if (queryParams.containsKey(OMDConstants.DATA_SCREEN)) { // Screen
                                                                             // Type
                        faultVO.setStrScreen(queryParams.getFirst(OMDConstants.DATA_SCREEN));
                    }
                    if (queryParams.containsKey(OMDConstants.ALL_RECORDS)) { // All
                                                                             // Records
                                                                             // Flag
                                                                             // -
                                                                             // Y
                                                                             // or
                                                                             // N
                        faultVO.setStrAllRecords(queryParams.getFirst(OMDConstants.ALL_RECORDS));
                    }
                    if (queryParams.containsKey(OMDConstants.NOTCH_8)) { // Notch
                                                                         // 8 -
                                                                         // true
                                                                         // or
                                                                         // false
                        faultVO.setNotch8(true);
                    }
                    if (queryParams.containsKey(OMDConstants.IS_LIMITED_PARAM)) { // Limited
                                                                                  // Param
                        limitedParam = queryParams.getFirst(OMDConstants.IS_LIMITED_PARAM);
                        if (OMDConstants.YES.equalsIgnoreCase(limitedParam)) {
                            faultVO.setLimitedParam(true);
                        }
                    }
                    if (queryParams.containsKey(OMDConstants.IS_HIDE_L3)) { // Hide
                                                                            // L3
                        hideL3Faults = queryParams.getFirst(OMDConstants.IS_HIDE_L3);
                        if (OMDConstants.YES.equalsIgnoreCase(hideL3Faults)) {
                            faultVO.setHideL3Faults(true);
                        }
                    }
                    if (queryParams.containsKey(OMDConstants.INIT_LOAD)) { // Init
                                                                           // Load
                        initLoad = queryParams.getFirst(OMDConstants.INIT_LOAD);
                        if (OMDConstants.YES.equalsIgnoreCase(initLoad)) {
                            faultVO.setInitLoad(true);
                        }
                    }
                    final String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                    final String userLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
                    if (RMDCommonUtility.isNull(strLanguage)) {
                        throw new OMDInValidInputException(OMDConstants.INVALID_LANGUAGE);
                    }
                    if (RMDCommonUtility.isNull(userLanguage)) {
                        throw new OMDInValidInputException(OMDConstants.INVALID_USER_LANGUAGE);
                    }
                    if (RMDCommonUtility.isNull(roadNumber)) {
                        throw new OMDInValidInputException(OMDConstants.INVALID_ASSET_NUMBER);
                    }
                    faultVO.setStrLanguage(strLanguage);
                    faultVO.setStrUserLanguage(userLanguage);
                    faultVO.setRoleName(queryParams.getFirst(OMDConstants.ROLE_NAME));
                    faultVO.setUserCustomer(queryParams.getFirst(OMDConstants.USER_CUSTOMER));
                    faultVO.setUserTimeZone(queryParams.getFirst(OMDConstants.USER_TIMEZONE));
                    faultVO.setUserTimeZoneCode(queryParams.getFirst(OMDConstants.USER_TIMEZONE_CODE));
                    faultVO.setEnableCustomColumns(Boolean.valueOf(queryParams.getFirst(OMDConstants.ENABLE_CUSTOM_COLUMNS)));
                }
                faultData = objCaseEoaServiceIntf.getFaultDetails(faultVO);
                if (faultData == null) {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return faultData;
    }

    /**
     * This method is used for retrieving requestId for the parameters passed to
     * RMDService layer
     * 
     * @param objHealthCheckRequestType
     * @return requestId
     */
    @POST
    @Path(OMDConstants.REQUEST_HEALTH_CHECK)
    @Consumes(MediaType.APPLICATION_XML)
    public void requestHealthCheck(final HealthCheckRequestType objHealthCheckRequestType) {
        HlthChckRqstServiceVO objHlthChckRqstServiceVO = null;
        String strRequest = null;
        try {
            objHlthChckRqstServiceVO = new HlthChckRqstServiceVO();

            objHlthChckRqstServiceVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
            objHlthChckRqstServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            objHlthChckRqstServiceVO.setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));

            BeanUtility.copyBeanProperty(objHealthCheckRequestType, objHlthChckRqstServiceVO);
            strRequest = objHealthChkServiceIntf.sendHealthRequest(objHlthChckRqstServiceVO);
            LOG.debug("Health Check Message ::" + strRequest);
        } catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used to get list of faults for the case id and inputs
     * passed
     * 
     * @param caseID
     * @param uriParam
     * @return FaultDataDetailsResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_FAULTS_CASEID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public FaultDataDetailsResponseType getFaultsCaseID(@PathParam(OMDConstants.CASE_ID) final String caseID,
            @Context final UriInfo uriParam) throws RMDServiceException {
        ToolParamInfoResponseType toolsParam = null;
        final FaultDataDetailsResponseType faultDataDetails = new FaultDataDetailsResponseType();
        FaultDataDetailsServiceVO faultData = null;
        FaultLogServiceVO faultLogService = null;
        ToolsParamGroupResponseType toolsParamGroup = null;
        FaultDataResponseType faultDataResponse = null;
        List<FaultLogServiceResponseType> lstLogResponse = null;
        List<FaultDetailsResponseType> lstFaultDetailsResponse = null;
        FaultDetailsServiceVO faultDetails = null;
        FaultDetailsResponseType faultDetailsResponse = null;
        Iterator<FaultDetailsServiceVO> itFaultDataDetails = null;
        FaultLogServiceResponseType faultLogResponse = null;
        Iterator<FaultLogServiceVO> itFaultServiceVO = null;
        Iterator<ToolParamInfoResponseType> itfaultData = null;
        Iterator<ToolsParamGroupResponseType> groupit = null;
        DataScreenServiceVO objDataScreenServiceVO = null;
        String limitedParam = null;
        try {
            final MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            objDataScreenServiceVO = new DataScreenServiceVO();
            if (caseID != null) {
                objDataScreenServiceVO.setStrCaseID(caseID);
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.CASEID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.CASEID_NOT_PROVIDED),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.FROM_DATE)) {
                    objDataScreenServiceVO.setFromDate(queryParams.getFirst(OMDConstants.FROM_DATE));
                }
                if (queryParams.containsKey(OMDConstants.TO_DATE)) {
                    objDataScreenServiceVO.setToDate(queryParams.getFirst(OMDConstants.TO_DATE));
                }

                if (queryParams.containsKey(OMDConstants.IS_HEALTH_CHECK)) {
                    objDataScreenServiceVO.setBlnHC(new Boolean(queryParams.getFirst(OMDConstants.IS_HEALTH_CHECK)));
                }
                if (queryParams.containsKey(OMDConstants.SORT_SELECTION)) {
                    objDataScreenServiceVO.setStrSortSelection(queryParams.getFirst(OMDConstants.SORT_SELECTION));
                }
                if (queryParams.containsKey(OMDConstants.CASE_SELECTION)) {
                    objDataScreenServiceVO.setStrCaseSelection(queryParams.getFirst(OMDConstants.CASE_SELECTION));
                }
                if (queryParams.containsKey(OMDConstants.RULE_DEFINITION_ID)) {
                    objDataScreenServiceVO.setStrRuleDefId(queryParams.getFirst(OMDConstants.RULE_DEFINITION_ID));
                }
                if (queryParams.containsKey(OMDConstants.JDPADRADIO)) {
                    objDataScreenServiceVO.setStrJDPADRadio(queryParams.getFirst(OMDConstants.JDPADRADIO));
                }
                objDataScreenServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                objDataScreenServiceVO.setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));

                if (queryParams.containsKey(OMDConstants.DAYS_SELECTED)) {
                    objDataScreenServiceVO.setStrDaySelection(queryParams.getFirst(OMDConstants.DAYS_SELECTED));
                }
                if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                    objDataScreenServiceVO.setStrAssetNo(queryParams.getFirst(OMDConstants.ASSET_NUMBER));
                }
                if (queryParams.containsKey(OMDConstants.START_ROW)) {
                    objDataScreenServiceVO
                            .setIntStartPos(Integer.valueOf(queryParams.getFirst(OMDConstants.START_ROW)));
                }
                if (queryParams.containsKey(OMDConstants.END_ROW)) {
                    objDataScreenServiceVO.setIntNoOfRecs(Integer.valueOf(queryParams.getFirst(OMDConstants.END_ROW)));
                }
                // Added for MULTI Customer: Rajesh:START
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    objDataScreenServiceVO.setCustomerId(queryParams.getFirst(OMDConstants.CUSTOMER_ID));
                }
                if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                    objDataScreenServiceVO.setAssetGrpName(queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME));
                }
                if (queryParams.containsKey(OMDConstants.NO_OF_DAYS)) {
                    objDataScreenServiceVO.setStrNoOfDays(queryParams.getFirst(OMDConstants.NO_OF_DAYS));
                }
                if (queryParams.containsKey(OMDConstants.CASE_FROM)) {
                    objDataScreenServiceVO.setStrCaseFrom(queryParams.getFirst(OMDConstants.CASE_FROM));
                    objDataScreenServiceVO.setStrCaseSelection(queryParams.getFirst(OMDConstants.CASE_FROM));
                }
                if (queryParams.containsKey(OMDConstants.DATA_SET)) {
                    objDataScreenServiceVO.setDataSets(queryParams.getFirst(OMDConstants.DATA_SET));
                }
                if (queryParams.containsKey(OMDConstants.IS_LIMITED_PARAM)) {
                    limitedParam = queryParams.getFirst(OMDConstants.IS_LIMITED_PARAM);
                    if (OMDConstants.YES.equalsIgnoreCase(limitedParam)) {
                        objDataScreenServiceVO.setLimitedParam(true);
                    }
                }
                // Added for MULTI Customer by Rajesh:END
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.QUERY_PARAMETERS_NOT_PASSED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.QUERY_PARAMETERS_NOT_PASSED), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            faultData = objCaseEoaServiceIntf.getFaultDetails(objDataScreenServiceVO);

            // faultData.getArlHeader() copy to ToolParamInfoResponseType -
            // faultDataDetailsResponseType()
            if (faultData != null) {
                itfaultData = faultData.getArlHeader().iterator();
                while (itfaultData.hasNext()) {
                    toolsParam = new ToolParamInfoResponseType();
                    faultDataDetails.getHeader().add(
                            (ToolParamInfoResponseType) BeanUtility.copyBeanProperty(itfaultData.next(), toolsParam));
                }

                groupit = faultData.getArlGrpHeader().iterator();
                while (groupit.hasNext()) {
                    toolsParamGroup = new ToolsParamGroupResponseType();
                    faultDataDetails.getGrpheader().add((ToolsParamGroupResponseType) BeanUtility
                            .copyBeanProperty(groupit.next(), toolsParamGroup));
                }

                itFaultServiceVO = faultData.getObjFaultServiceVO().getArlFaultData().iterator();

                lstLogResponse = new ArrayList<FaultLogServiceResponseType>();
                lstFaultDetailsResponse = new ArrayList<FaultDetailsResponseType>();
                while (itFaultServiceVO.hasNext()) {
                    faultLogService = new FaultLogServiceVO();
                    faultLogService = itFaultServiceVO.next();
                    faultLogResponse = new FaultLogServiceResponseType();

                    itFaultDataDetails = faultLogService.getArlFaultDataDetails().iterator();
                    lstFaultDetailsResponse = new ArrayList<FaultDetailsResponseType>();
                    while (itFaultDataDetails.hasNext()) {
                        faultDetails = new FaultDetailsServiceVO();
                        faultDetailsResponse = new FaultDetailsResponseType();
                        faultDetails = itFaultDataDetails.next();

                        BeanUtility.copyBeanProperty(faultDetails, faultDetailsResponse);

                        lstFaultDetailsResponse.add(faultDetailsResponse);
                    }
                    faultLogResponse.getLstFaultdataDetails().addAll(lstFaultDetailsResponse);

                    lstFaultDetailsResponse = null;
                    BeanUtility.copyBeanProperty(faultLogService, faultLogResponse);

                    lstLogResponse.add(faultLogResponse);
                }

                faultDataResponse = new FaultDataResponseType();
                faultDataResponse.getFaultdata().addAll(lstLogResponse);
                faultDataDetails.setFaultDataResponse(faultDataResponse);
                BeanUtility.copyBeanProperty(faultData, faultDataDetails);
            } /*else {
                throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }*/
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return faultDataDetails;
    }

    /**
     * This method is used to get list of fleet details for the parameters
     * passed in UriInfo
     * 
     * @param ui
     * @return List of Fleet object Change made in method to retrive list of
     *         Model details based on Customer, Model, Unit No and Config
     */

    @GET
    @Path(OMDConstants.GET_FLEETS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Fleet> getFleets(@Context final UriInfo ui) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams;
        List<Fleet> lstFleet = null;
        Fleet objFleet = null;
        String strLanguage = null;
        String strCustomerID = null;
        List<ElementVO> listElementVO = null;
        String customer = null;
        String model = null;
        String config = null;
        String unitNumber = null;
        String[] strConfig = null;
        String strCfg = null;
        List<ElementVO> arlFleet = null;
        ElementVO elementVO = null;
        ListIterator<ElementVO> listIterator = null;
        try {
            queryParams = ui.getQueryParameters();
            lstFleet = new ArrayList<Fleet>();
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);

            if (queryParams.containsKey(RMDCommonConstants.CUSTOMERID)) {
                // getting the language from the header

                strCustomerID = queryParams.getFirst(RMDCommonConstants.CUSTOMERID);
                listElementVO = objRuleServiceIntf.getFleetsList(strCustomerID, strLanguage);
                if (null != listElementVO) {
                    if (RMDCommonUtility.isCollectionNotEmpty(listElementVO)) {
                        for (final Iterator iterator = listElementVO.iterator(); iterator.hasNext();) {
                            elementVO = (ElementVO) iterator.next();
                            objFleet = new Fleet();
                            BeanUtility.copyBeanProperty(elementVO, objFleet);
                            lstFleet.add(objFleet);
                        }
                    } else {
                        throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                    }
                }
            } else if (queryParams.containsKey(OMDConstants.CUSTOMER)
                    || queryParams.containsKey(RMDCommonConstants.MODEL) || queryParams.containsKey(OMDConstants.CONFIG)
                    || queryParams.containsKey(OMDConstants.UNIT_NUMBER)) {
                int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_HYPEN,
                        RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.DOUBLE_HYPHEN,
                        RMDCommonConstants.AlPHA_NUMERIC };

                if (AppSecUtil.validateWebServiceInput(queryParams, RMDServiceConstants.DATE_FORMAT2, methodConstants,
                        OMDConstants.CUSTOMER, RMDCommonConstants.MODEL, OMDConstants.CONFIG,
                        OMDConstants.UNIT_NUMBER)) {
                    LOG.debug("Inside fleet else part....");

                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                    model = queryParams.getFirst(RMDCommonConstants.MODEL);
                    config = queryParams.getFirst(OMDConstants.CONFIG);
                    unitNumber = queryParams.getFirst(OMDConstants.UNIT_NUMBER);
                    LOG.debug("Values inside fleet are--->" + customer + "------->" + model + "------------>" + config
                            + "------>" + unitNumber);
                    if (null != customer) {
                        customer.split(RMDCommonConstants.COMMMA_SEPARATOR);
                    }
                    if (null != model) {
                        model.split(RMDCommonConstants.COMMMA_SEPARATOR);
                    }
                    if (null != config) {
                        strCfg = config.replaceAll(OMDConstants.SYMBOL_NEGETION, OMDConstants.EMPTY_STRING);
                        strConfig = strCfg.split(OMDConstants.SYMBOL_DOUBLESLASH + RMDCommonConstants.CAPSYMBOL);
                    }
                    if (null != unitNumber) {
                        unitNumber.split(RMDCommonConstants.COMMMA_SEPARATOR);
                    }
                    arlFleet = objAssetEoaServiceIntf.getFleetList(customer, model, strConfig, unitNumber, strLanguage);
                    if (null != arlFleet) {
                        if (RMDCommonUtility.isCollectionNotEmpty(arlFleet)) {
                            lstFleet = new ArrayList<Fleet>();
                            listIterator = arlFleet.listIterator();
                            while (listIterator.hasNext()) {
                                objFleet = new Fleet();
                                elementVO = listIterator.next();
                                /*
                                 * The below code has been changed as part of
                                 * OMD perf.Removed the copyBeanProperty as only
                                 * two fields are required from ElementVO for
                                 * this functionality
                                 */
                                objFleet.setFleet(elementVO.getName());
                                objFleet.setFleetID(elementVO.getId());
                                objFleet.setCustomerObjid(elementVO.getCustomerSeqId());
                                lstFleet.add(objFleet);
                            }
                        } else {
                            throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                        }

                    } else {
                        throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

                    }

                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }

            } else {
                arlFleet = objRuleServiceIntf.getFleetList(customer, model, strConfig, unitNumber, strLanguage);
                if (null != arlFleet) {
                    if (RMDCommonUtility.isCollectionNotEmpty(arlFleet)) {
                        lstFleet = new ArrayList();
                        listIterator = arlFleet.listIterator();
                        while (listIterator.hasNext()) {
                            objFleet = new Fleet();
                            elementVO = listIterator.next();
                            BeanUtility.copyBeanProperty(elementVO, objFleet);
                            lstFleet.add(objFleet);
                        }
                    }
                }

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return lstFleet;
    }

    /**
     * This method is used for retrieving Asset details for the parameters
     * passed in UriInfo
     * 
     * @param @PathParam(OMDConstants.CUSTOMER_ID)
     *            , ui
     * @return List of AssetResponseType
     * @throws RMDServiceException
     *             Change made in method to retrive list of asset group
     */
    @GET
    @Path(OMDConstants.GET_ASSET_GROUPS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetResponseType> getAssetGroups(@PathParam(OMDConstants.CUSTOMER_ID) final String customerId,
            @Context final UriInfo ui) throws RMDServiceException {

        Iterator iterAssets;
        AssetServiceVO objAssetServiceVO;
        List<AssetServiceVO> arlAssets = null;
        final List<AssetResponseType> arlAssetResponseType = new ArrayList<AssetResponseType>();
        try {
            final MultivaluedMap<String, String> pathParams = ui.getPathParameters();
            getRequestHeader(OMDConstants.LANGUAGE);
            objAssetServiceVO = new AssetServiceVO();
            int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC };

            if (AppSecUtil.validateWebServiceInput(pathParams, null, methodConstants, OMDConstants.CUSTOMER_ID)) {
                if (null != customerId) {
                    objAssetServiceVO.setCustomerID(customerId);
                } else {
                    objAssetServiceVO.setCustomerID(RMDCommonConstants.EMPTY_STRING);
                }
                arlAssets = objAssetEoaServiceIntf.getAssetGroups(objAssetServiceVO);
                if (RMDCommonUtility.isCollectionNotEmpty(arlAssets)) {
                    iterAssets = arlAssets.iterator();
                    while (iterAssets.hasNext()) {
                        objAssetServiceVO = (AssetServiceVO) iterAssets.next();
                        final AssetResponseType objAssetResponseType = new AssetResponseType();
                        objAssetResponseType.setAssetGroupName(objAssetServiceVO.getAssetGroupName());
                        objAssetResponseType.setAssetGroupNumber(objAssetServiceVO.getAssetGroupNumber());
                        arlAssetResponseType.add(objAssetResponseType);
                    }
                }
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        HashSet hs = new HashSet();
        hs.addAll(arlAssetResponseType);
        arlAssetResponseType.clear();
        arlAssetResponseType.addAll(hs);
        return arlAssetResponseType;
    }

    /**
     * This method is used for retrieving Asset details for the parameters
     * passed in UriInfo
     * 
     * @param ui
     * @return List of AssetResponseType
     * @throws RMDServiceException
     * 
     *             Change made in method to retrive list of Model details based
     *             on Customer, Fleet, Model and Config
     */
    @POST
    @Path(OMDConstants.GET_ASSETS)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetResponseType> getAssets(final AssetsRequestType objAssetReqType) throws RMDServiceException {


        List<AssetServiceVO> eoaAssetsLst = new ArrayList<AssetServiceVO>();
        AssetServiceVO objAssetServiceVO;
        List<AssetResponseType> arlAssetResponseType = new ArrayList<AssetResponseType>();
        AssetResponseType objAssetResponseType = null;
        AssetServiceVO objAssetServiceVO2=null;
        try {
            objAssetServiceVO = new AssetServiceVO();
            // if any one of the search values are present

                    if (validateGetAssets(objAssetReqType)) {
                        
                        if (null != objAssetReqType.getAssetGrpName()
                                && !objAssetReqType.getAssetGrpName().isEmpty()) {
                            objAssetServiceVO.setAssetGroupName(objAssetReqType
                                    .getAssetGrpName());
                        } else {
                            objAssetServiceVO
                                    .setAssetGroupName(RMDCommonConstants.EMPTY_STRING);
                        }
                        
                        if (null != objAssetReqType.getModelId()
                                && !objAssetReqType.getModelId().isEmpty()) {
                            objAssetServiceVO.setStrModelId(objAssetReqType
                                    .getModelId());
                        } else {
                            objAssetServiceVO.setModel(RMDCommonConstants.EMPTY_STRING);
                        }
                        
                        if (null != objAssetReqType.getFleetId()
                                && !objAssetReqType.getFleetId().isEmpty()) {
                            objAssetServiceVO.setStrFleetId(objAssetReqType
                                    .getFleetId());
                        } else {
                            objAssetServiceVO.setFleet(RMDCommonConstants.EMPTY_STRING);
                        }
                        
					if (null != objAssetReqType.getAssetNumberLike()
							&& !objAssetReqType.getAssetNumberLike().isEmpty()) {
						objAssetServiceVO.setAssetNumberLike(objAssetReqType
								.getAssetNumberLike());
					} else {
						objAssetServiceVO
								.setAssetNumberLike(RMDCommonConstants.EMPTY_STRING);
					}
					if (null != objAssetReqType.getCustomerId()
							&& !objAssetReqType.getCustomerId().isEmpty()) {
						
						if (RMDCommonConstants.ALL_CUSTOMER.equalsIgnoreCase(objAssetReqType.getCustomerId())) {						
							objAssetServiceVO.setCustomerID(RMDCommonConstants.EMPTY_STRING);
						}else{
							objAssetServiceVO.setCustomerID(objAssetReqType
								.getCustomerId());							
						}
						
					} else {
						objAssetServiceVO
								.setCustomerID(RMDCommonConstants.EMPTY_STRING);
					}
					if (null != objAssetReqType.getAssetNumber()
							&& !objAssetReqType.getAssetNumber().isEmpty()) {
						objAssetServiceVO.setStrAssetNumber(objAssetReqType
								.getAssetNumber());
					} else {
						objAssetServiceVO
								.setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
					}
					if (null != objAssetReqType.getAssetFrom()) {
						objAssetServiceVO.setAssetFrom(objAssetReqType
								.getAssetFrom());
					}
					if (null != objAssetReqType.getAssetTo()) {
						objAssetServiceVO.setAssetTo(objAssetReqType
								.getAssetTo());
					}
					if (null != objAssetReqType.getProdAssetMap()
							&& objAssetReqType.getProdAssetMap().size() > 0) {
						CMBeanUtility.copyCustomerAssetToServiceVO(
								objAssetReqType.getProdAssetMap(), objAssetServiceVO);
					}
					
					
				eoaAssetsLst = objAssetEoaServiceIntf
					.getAssets(objAssetServiceVO);
				if(eoaAssetsLst!=null && eoaAssetsLst.size()>0){
					arlAssetResponseType = new ArrayList<AssetResponseType>(eoaAssetsLst.size());
					for (Iterator iterator = eoaAssetsLst.iterator(); iterator
							.hasNext();) {
						objAssetResponseType = new AssetResponseType();
						 objAssetServiceVO2 = (AssetServiceVO) iterator
								.next();
						CMBeanUtility.copyAsstEoaServiceVOToAsstRespType(
								objAssetServiceVO2, objAssetResponseType);
						arlAssetResponseType.add(objAssetResponseType);

					}
				}
				eoaAssetsLst=null;	
				} else {
					throw new OMDInValidInputException(
							OMDConstants.INVALID_VALUE);
				}
			
				 Collections.sort(arlAssetResponseType, new Comparator() {
			          public int compare(Object o1, Object o2) {
			        	  AssetResponseType a1 =(AssetResponseType) o1;
			        	  AssetResponseType a2 =(AssetResponseType) o2;
			        	  if(a1.getAssetNumber().length()<a2.getAssetNumber().length()){
			      			return -1;
			      		}else if(a1.getAssetNumber().length()>a2.getAssetNumber().length()){
			      			return 1;
			      		}
			      		return a1.getAssetNumber().compareTo(a2.getAssetNumber());
			      	}
			     });
		            
			        
                } catch (RMDServiceException rmdServiceException) {
                    throw rmdServiceException;
                } catch (Exception e) {
                	 RMDWebServiceErrorHandler.handleException(e,
                             omdResourceMessagesIntf);
                }
                return arlAssetResponseType;
            }
    
    /**
     * This method is used for retrieving Asset details for the parameters
     * passed in UriInfo including BAD vehicles
     * 
     * @param ui
     * @return List of AssetResponseType
     * @throws RMDServiceException
  	 *
     */
    @POST
    @Path(OMDConstants.GET_ASSETS_UNIT_RENUM)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetResponseType> getAssetsForUnitRenum(final AssetsRequestType objAssetReqType) throws RMDServiceException {


        List<AssetServiceVO> eoaAssetsLst = new ArrayList<AssetServiceVO>();
        AssetServiceVO objAssetServiceVO;
        List<AssetResponseType> arlAssetResponseType = new ArrayList<AssetResponseType>();
        AssetResponseType objAssetResponseType = null;
        AssetServiceVO objAssetServiceVO2=null;
        try {
            objAssetServiceVO = new AssetServiceVO();
            // if any one of the search values are present

                    if (validateGetAssets(objAssetReqType)) {
                        
                        if (null != objAssetReqType.getAssetGrpName()
                                && !objAssetReqType.getAssetGrpName().isEmpty()) {
                            objAssetServiceVO.setAssetGroupName(objAssetReqType
                                    .getAssetGrpName());
                        } else {
                            objAssetServiceVO
                                    .setAssetGroupName(RMDCommonConstants.EMPTY_STRING);
                        }
                        
                        if (null != objAssetReqType.getModelId()
                                && !objAssetReqType.getModelId().isEmpty()) {
                            objAssetServiceVO.setStrModelId(objAssetReqType
                                    .getModelId());
                        } else {
                            objAssetServiceVO.setModel(RMDCommonConstants.EMPTY_STRING);
                        }
                        
                        if (null != objAssetReqType.getFleetId()
                                && !objAssetReqType.getFleetId().isEmpty()) {
                            objAssetServiceVO.setStrFleetId(objAssetReqType
                                    .getFleetId());
                        } else {
                            objAssetServiceVO.setFleet(RMDCommonConstants.EMPTY_STRING);
                        }
                        
					if (null != objAssetReqType.getAssetNumberLike()
							&& !objAssetReqType.getAssetNumberLike().isEmpty()) {
						objAssetServiceVO.setAssetNumberLike(objAssetReqType
								.getAssetNumberLike());
					} else {
						objAssetServiceVO
								.setAssetNumberLike(RMDCommonConstants.EMPTY_STRING);
					}
					if (null != objAssetReqType.getCustomerId()
							&& !objAssetReqType.getCustomerId().isEmpty()) {
						
						if (RMDCommonConstants.ALL_CUSTOMER.equalsIgnoreCase(objAssetReqType.getCustomerId())) {						
							objAssetServiceVO.setCustomerID(RMDCommonConstants.EMPTY_STRING);
						}else{
							objAssetServiceVO.setCustomerID(objAssetReqType
								.getCustomerId());							
						}
						
					} else {
						objAssetServiceVO
								.setCustomerID(RMDCommonConstants.EMPTY_STRING);
					}
					if (null != objAssetReqType.getAssetNumber()
							&& !objAssetReqType.getAssetNumber().isEmpty()) {
						objAssetServiceVO.setStrAssetNumber(objAssetReqType
								.getAssetNumber());
					} else {
						objAssetServiceVO
								.setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
					}
					if (null != objAssetReqType.getAssetFrom()) {
						objAssetServiceVO.setAssetFrom(objAssetReqType
								.getAssetFrom());
					}
					if (null != objAssetReqType.getAssetTo()) {
						objAssetServiceVO.setAssetTo(objAssetReqType
								.getAssetTo());
					}
					if (null != objAssetReqType.getProdAssetMap()
							&& objAssetReqType.getProdAssetMap().size() > 0) {
						CMBeanUtility.copyCustomerAssetToServiceVO(
								objAssetReqType.getProdAssetMap(), objAssetServiceVO);
					}
					
					
				eoaAssetsLst = unitRenumberServiceIntf
					.getAssetsForUnitRenum(objAssetServiceVO);
				if(eoaAssetsLst!=null && eoaAssetsLst.size()>0){
					arlAssetResponseType = new ArrayList<AssetResponseType>(eoaAssetsLst.size());
					for (Iterator iterator = eoaAssetsLst.iterator(); iterator
							.hasNext();) {
						objAssetResponseType = new AssetResponseType();
						 objAssetServiceVO2 = (AssetServiceVO) iterator
								.next();
						CMBeanUtility.copyAsstEoaServiceVOToAsstRespType(
								objAssetServiceVO2, objAssetResponseType);
						arlAssetResponseType.add(objAssetResponseType);

					}
				}
				eoaAssetsLst=null;	
				} else {
					throw new OMDInValidInputException(
							OMDConstants.INVALID_VALUE);
				}
			
				 Collections.sort(arlAssetResponseType, new Comparator() {
			          public int compare(Object o1, Object o2) {
			        	  AssetResponseType a1 =(AssetResponseType) o1;
			        	  AssetResponseType a2 =(AssetResponseType) o2;
			        	  if(a1.getAssetNumber().length()<a2.getAssetNumber().length()){
			      			return -1;
			      		}else if(a1.getAssetNumber().length()>a2.getAssetNumber().length()){
			      			return 1;
			      		}
			      		return a1.getAssetNumber().compareTo(a2.getAssetNumber());
			      	}
			     });
		            
			        
                } catch (RMDServiceException rmdServiceException) {
                    throw rmdServiceException;
                } catch (Exception e) {
                	 RMDWebServiceErrorHandler.handleException(e,
                             omdResourceMessagesIntf);
                }
                return arlAssetResponseType;
            }

    /**
     * This method is used for retrieving Asset's Installed Product details for
     * the parameters passed in UriInfo
     * 
     * @param ui
     * @return List of AssetResponseType
     * @throws RMDServiceException
     *             Change made in method to retrive list of Model details based
     *             on Customer, Fleet, Model and Config
     */
    @POST
    @Path(OMDConstants.GET_INSTALLED_PRODUCT)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetInstalledProductResponseType> getInstalledProduct(final AssetsRequestType objAssetReqType)
            throws RMDServiceException {

        List<AssetInstalledProductVO> assetInstalledProdLst = new ArrayList<AssetInstalledProductVO>();
        final List<AssetInstalledProductResponseType> arlAssetProdResponseType = new ArrayList<AssetInstalledProductResponseType>();
        AssetInstalledProductVO assetInstalledProductVO = null;
        AssetServiceVO objAssetServiceVO = null;
        XMLGregorianCalendar dtWarrantyEndDate = null;
        GregorianCalendar objGregorianCalendar;
        String timeZone = getDefaultTimeZone();

        try {
            objAssetServiceVO = new AssetServiceVO();
            // if any one of the search values are present
            if (validateGetAssets(objAssetReqType)) {
                if (null != objAssetReqType.getAssetGrpName() && !objAssetReqType.getAssetGrpName().isEmpty()) {
                    objAssetServiceVO.setAssetGroupName(objAssetReqType.getAssetGrpName());
                } else {
                    objAssetServiceVO.setAssetGroupName(RMDCommonConstants.EMPTY_STRING);
                }

                if (null != objAssetReqType.getCustomerId() && !objAssetReqType.getCustomerId().isEmpty()) {

                    if (RMDCommonConstants.ALL_CUSTOMER.equalsIgnoreCase(objAssetReqType.getCustomerId())) {
                        objAssetServiceVO.setCustomerID(RMDCommonConstants.EMPTY_STRING);
                    } else {
                        objAssetServiceVO.setCustomerID(objAssetReqType.getCustomerId());
                    }

                } else {
                    objAssetServiceVO.setCustomerID(RMDCommonConstants.EMPTY_STRING);
                }
                if (null != objAssetReqType.getAssetNumber() && !objAssetReqType.getAssetNumber().isEmpty()) {
                    objAssetServiceVO.setStrAssetNumber(objAssetReqType.getAssetNumber());
                } else {
                    objAssetServiceVO.setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
                }

                assetInstalledProdLst = objAssetEoaServiceIntf.getInstalledProduct(objAssetServiceVO);
                DatatypeFactory df = DatatypeFactory.newInstance();
                if (null != assetInstalledProdLst && !assetInstalledProdLst.isEmpty()) {

                    for (Iterator iterator = assetInstalledProdLst.iterator(); iterator.hasNext();) {

                        AssetInstalledProductResponseType objAssetProdResponseType = new AssetInstalledProductResponseType();
                        assetInstalledProductVO = new AssetInstalledProductVO();
                        assetInstalledProductVO = (AssetInstalledProductVO) iterator.next();
                        objAssetProdResponseType.setPartNumber(assetInstalledProductVO.getPartNumber());
                        objAssetProdResponseType.setDescription(assetInstalledProductVO.getDescription());

                        if (null != assetInstalledProductVO.getWarrantyEndDate()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar
                                    .setTimeInMillis(assetInstalledProductVO.getWarrantyEndDate().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtWarrantyEndDate = df.newXMLGregorianCalendar(objGregorianCalendar);
                            objAssetProdResponseType.setWarrantyEndDate(dtWarrantyEndDate);
                        }

                        arlAssetProdResponseType.add(objAssetProdResponseType);
                    }

                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return arlAssetProdResponseType;
    }

    /**
     * This method is used for retrieving customer details for the parameters
     * passed in UriInfo
     * 
     * @return List of Customer object
     * @throws RMDServiceException
     * @Description To get list of customers associated with a user
     */

    @GET
    @Path(OMDConstants.GET_USER_CUSTOMERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Customer> getUserCustomers(@PathParam(OMDConstants.USERId) String userId) throws RMDServiceException {
        List<Customer> resCustomerList = null;
        Customer respCustomer = null;
        List<ElementVO> arlCust = null;
        ElementVO elementVO = null;
        ListIterator<ElementVO> listIterator = null;
        try {

            resCustomerList = new ArrayList<Customer>();
            arlCust = objRuleServiceIntf.getCustomerList(getRequestHeader(OMDConstants.LANGUAGE), userId);
            LOG.debug("Customer List retrieved from RMD Service ");
            if (null != arlCust) {
                if (RMDCommonUtility.isCollectionNotEmpty(arlCust)) {
                    resCustomerList = new ArrayList<Customer>();
                    listIterator = arlCust.listIterator();
                    while (listIterator.hasNext()) {
                        respCustomer = new Customer();
                        elementVO = listIterator.next();
                        CMBeanUtility.copyElementVOToCustomer(elementVO, respCustomer);
                        resCustomerList.add(respCustomer);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/

            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

            }*/

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return resCustomerList;
    }

    /**
     * This method is used for retrieving customer details for the parameters
     * passed in UriInfo
     * 
     * @return List of Customer object
     * @throws RMDServiceException
     * @Description To get list of all the customer s
     */

    @GET
    @Path(OMDConstants.GET_ALL_CUSTOMERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Customer> getAllCustomers(@Context final UriInfo ui) throws RMDServiceException {
        List<Customer> resCustomerList = null;
        Customer respCustomer = null;
        List<ElementVO> arlCust = null;
        ElementVO elementVO = null;
        ListIterator<ElementVO> listIterator = null;

        MultivaluedMap<String, String> queryParams;
        String strLanguage = null;
        String model = null;
        String fleet = null;
        String config = null;
        String unitNumber = null;
        String strCfg = null;
        String[] strConfig = null;
        String userId = null;

        try {
            queryParams = ui.getQueryParameters();
            resCustomerList = new ArrayList<Customer>();
            String isGERuleAUthor = getRequestHeader(OMDConstants.IS_GE_RULE_AUTHOR);
            if(RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){
            	userId=getRequestHeader(OMDConstants.STR_USERNAME);	
			}if (queryParams.containsKey(OMDConstants.USER_ID)) {
                userId = queryParams.getFirst(OMDConstants.USER_ID);
            }
            
            if (queryParams.containsKey(OMDConstants.FLEET) || queryParams.containsKey(RMDCommonConstants.MODEL)
                    || queryParams.containsKey(OMDConstants.CONFIG)
                    || queryParams.containsKey(OMDConstants.UNIT_NUMBER)) {
                int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                        RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.DOUBLE_HYPHEN,
                        RMDCommonConstants.AlPHA_NUMERIC };

                if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.FLEET,
                        RMDCommonConstants.MODEL, OMDConstants.CONFIG, OMDConstants.UNIT_NUMBER)) {
                    strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                    model = queryParams.getFirst(RMDCommonConstants.MODEL);
                    fleet = queryParams.getFirst(OMDConstants.FLEET);
                    config = queryParams.getFirst(OMDConstants.CONFIG);
                    unitNumber = queryParams.getFirst(OMDConstants.UNIT_NUMBER);
                    if (null != config) {
                        strCfg = config.replaceAll(OMDConstants.SYMBOL_NEGETION, OMDConstants.EMPTY_STRING);
                        strConfig = strCfg.split(OMDConstants.SYMBOL_DOUBLESLASH + RMDCommonConstants.CAPSYMBOL);
                    }
                    arlCust = objRuleServiceIntf.getCustomerList(model, fleet, strConfig, unitNumber, strLanguage);
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

                }
            } else {

                arlCust = objRuleServiceIntf.getCustomerList(getRequestHeader(OMDConstants.LANGUAGE), userId);
            }
            LOG.debug("Customer List retrieved from RMD Service ");
            if (null != arlCust) {
                if (RMDCommonUtility.isCollectionNotEmpty(arlCust)) {
                    resCustomerList = new ArrayList<Customer>();
                    listIterator = arlCust.listIterator();
                    while (listIterator.hasNext()) {
                        respCustomer = new Customer();
                        elementVO = listIterator.next();
                        CMBeanUtility.copyElementVOToCustomer(elementVO, respCustomer);
                        resCustomerList.add(respCustomer);
                    }
                } else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }

            } else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return resCustomerList;
    }

    /**
     * This method is used for Configuration details of rule for the Parameter
     * passed in UriInfo
     * 
     * @param ui
     * @return
     * @throws RMDServiceException
     *             Change made in method to retrive list of Model details based
     *             on Customer, Fleet, Unit No and Model
     */
    @GET
    @Path(OMDConstants.GET_CONFIGURATIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RuleDefinitionConfig> getConfigurations(@Context UriInfo ui) throws RMDServiceException {
        List<RuleDefinitionConfig> resCongfigList = null;
        List<ElementVO> arlConfig = null;
        ElementVO elementVO = null;
        ListIterator<ElementVO> listIterator = null;
        String strLanguage = null;
        String customer = null;
        String model = null;
        String unitNumber = null;
        String fleet = null;
        String[] strCustomer = null;
        String[] strModel = null;
        String[] strUnitNumber = null;
        String[] strFleet = null;
        RuleDefinitionConfig objRuleDefinitionConfig = null;
        String strFamily = null;

        try {
            final MultivaluedMap<String, String> queryParams = ui
            .getQueryParameters();
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            if (queryParams.containsKey(OMDConstants.CUSTOMER)
                    || queryParams.containsKey(OMDConstants.UNIT_NUMBER)
                    || queryParams.containsKey(OMDConstants.FLEET)||queryParams.containsKey(OMDConstants.FAMILY)) {
                    int[] methodConstants = {RMDCommonConstants.ALPHA_NUMERIC_HYPEN,
                        RMDCommonConstants.AlPHA_NUMERIC,
                        RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE};

                if(AppSecUtil.validateWebServiceInput(queryParams,
                        null, methodConstants, OMDConstants.CUSTOMER, 
                        OMDConstants.UNIT_NUMBER,OMDConstants.FLEET,RMDCommonConstants.MODEL,OMDConstants.FAMILY)){

                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                    model = queryParams.getFirst(RMDCommonConstants.MODEL);
                    unitNumber = queryParams.getFirst(OMDConstants.UNIT_NUMBER);
                    fleet = queryParams.getFirst(OMDConstants.FLEET);
                    strFamily=queryParams.getFirst(OMDConstants.FAMILY);
                    arlConfig = objRuleServiceIntf.getConfigurationList(customer,
                            model, fleet, unitNumber,strFamily, strLanguage);
                }else{
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }

            } else if (queryParams.containsKey(RMDCommonConstants.MODEL)) {
                int[] methodConstants = {RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE};
                model = queryParams.getFirst(RMDCommonConstants.MODEL);
                if (RMDCommonUtility.isNull(strLanguage)) {
                    throw new OMDInValidInputException(OMDConstants.INVALID_LANGUAGE);
                }
                if (RMDCommonUtility.isNull(model) ||
                        !AppSecUtil.validateWebServiceInput(queryParams,
                                null, methodConstants, RMDCommonConstants.MODEL)) {
                    throw new OMDInValidInputException(OMDConstants.INVALID_MODE);
                }
                if (RMDCommonUtility.isNull(model)) {
                    throw new OMDInValidInputException(OMDConstants.INVALID_MODE);
                }
                strModel = model.split(RMDCommonConstants.COMMMA_SEPARATOR);
                arlConfig = objRuleServiceIntf.getConfigurationList(strModel,
                        strLanguage);
            } else {
                arlConfig = objRuleServiceIntf.getConfigurationList(strModel,
                        strLanguage);
            }
            if (!RMDCommonUtility.isCollectionNotEmpty(arlConfig)) {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }

            if (RMDCommonUtility.isCollectionNotEmpty(arlConfig)) {
                resCongfigList = new ArrayList();
                LOG.debug("Coming from service call objRuleServiceIntf.getConfigurationList");
                listIterator = arlConfig.listIterator();
                while (listIterator.hasNext()) {
                    objRuleDefinitionConfig = new RuleDefinitionConfig();
                    elementVO = (ElementVO) listIterator.next();
                    BeanUtility.copyBeanProperty(elementVO,
                            objRuleDefinitionConfig);
                    resCongfigList.add(objRuleDefinitionConfig);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return resCongfigList;
    }

	/**
	 * This method is used to get the list of parameters
	 * 
	 * @param ui
	 * @return List<ParameterResponseType>
	 * @throws RMDServiceException
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path(OMDConstants.GET_PARAMETERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ParameterResponseType> getParameters(@Context final UriInfo ui)
	throws RMDServiceException {
		List<ParameterResponseType> lstElementResponse = new ArrayList<ParameterResponseType>();

		ParameterResponseType objParameterResponseType;
		List<ParameterServiceVO> lstParameterServiceVO = null;
		ParameterServiceVO pserviceVO = null;
		String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
		String defaultUom=getRequestHeader("measurementSystem");
		String isGERuleAUthor = getRequestHeader(OMDConstants.IS_GE_RULE_AUTHOR);
		ParameterRequestServiceVO objParameterRequestServiceVO=null;
		String customer=null;
		String ruleType=null;
		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			int[] paramFlag = {OMDConstants.ALPHA_NUMERIC_HYPEN};
			if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag,OMDConstants.FAMILY)){
				objParameterRequestServiceVO=new ParameterRequestServiceVO();
				String family = OMDConstants.EMPTY_STRING;
				if (queryParams.containsKey(OMDConstants.FAMILY)) {
					family = queryParams.getFirst(OMDConstants.FAMILY);
					objParameterRequestServiceVO.setFamily(family);
				}
				if(RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){
					objParameterRequestServiceVO.setCustomer(getRequestHeader("strUserCustomer"));
					objParameterRequestServiceVO.setRuleType(OMDConstants.ALERTS);
					
				} else {

					if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
						customer = queryParams.getFirst(OMDConstants.CUSTOMER);
						objParameterRequestServiceVO.setCustomer(customer);
					}

					if (queryParams.containsKey(OMDConstants.RULE_TYPE)) {
						ruleType = queryParams.getFirst(OMDConstants.RULE_TYPE);
						objParameterRequestServiceVO.setRuleType(ruleType);
					}
				}
				objParameterRequestServiceVO.setIsGERuleAUthor(isGERuleAUthor);
				objParameterRequestServiceVO.setUom(defaultUom);
				objParameterRequestServiceVO.setStrLanguage(strLanguage);				
				lstParameterServiceVO = new ArrayList<ParameterServiceVO>();
				lstParameterServiceVO = objRuleServiceIntf.getInitialLoadForAddRule(
						objParameterRequestServiceVO);

				if (RMDCommonUtility.isCollectionNotEmpty(lstParameterServiceVO)) {
					for (ParameterServiceVO objParameterServiceVO : lstParameterServiceVO) {
						objParameterResponseType = new ParameterResponseType();
						BeanUtility.copyBeanProperty(objParameterServiceVO,
								objParameterResponseType);
						objParameterResponseType.setUomAbbr(objParameterServiceVO.getUomAbbr());
						lstElementResponse.add(objParameterResponseType);
					}
				} else {
					throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
				}
			}else{
				throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
			}
		}  catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
		}
		return lstElementResponse;
	}

    /**
     * This method invokes the web service to get the Latitude,Longitude and max
     * occurrence time information of the asset
     * 
     * @param assetNumber
     * @return AssetLocatorResponseType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_ASSET_LOCATION)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetLocatorResponseType> getAssetLocation(final AssetLocRequestType objAssetRequestType)
            throws RMDServiceException {
        RMDLOGGER.debug("In RMDWebservices : AssetResource : getAssetLocation():::START");
        AssetLocatorResponseType assetLocatorResponseType = null;
        final AssetLocatorBean assetLocatorBean = new AssetLocatorBean();
        List<AssetLocatorVO> assetLocatorVoLst = null;
        String modelId = RMDCommonConstants.EMPTY_STRING;
        String fleetId = RMDCommonConstants.EMPTY_STRING;
        String[] modelIdArray;
        String[] fleetIdArray;
        List<AssetLocatorResponseType> lstAssetLocatorResponse = new ArrayList<AssetLocatorResponseType>();

        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar dtProcessed = null;
        XMLGregorianCalendar dtFault = null;
        XMLGregorianCalendar latlonDate = null;
        try {
            assetLocatorBean.setLastFault(objAssetRequestType.isLastFault());
            String timeZone = getDefaultTimeZone();
            String strLanguage = objAssetRequestType.getLanguage();
            assetLocatorBean.setLanuage(strLanguage);
            if (null != objAssetRequestType.getProdAssetMap() && !objAssetRequestType.getProdAssetMap().isEmpty()) {
                
                // Copy product assets from request type to service
                CMBeanUtility.copyCustomerAssetToServiceVO(objAssetRequestType.getProdAssetMap(), assetLocatorBean);
            }
            if (validateAssetLocation(objAssetRequestType)) {
                if (null != objAssetRequestType.getAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(objAssetRequestType.getAssetNumber())) {
                    assetLocatorBean.setAssetNumber(objAssetRequestType.getAssetNumber());
                } else {
                    assetLocatorBean.setAssetNumber(RMDCommonConstants.EMPTY_STRING);
                }
                if (null != objAssetRequestType.getCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(objAssetRequestType.getCustomerId())) {
                    assetLocatorBean.setCustomerId(objAssetRequestType.getCustomerId());
                } else {
                    assetLocatorBean.setCustomerId(RMDCommonConstants.EMPTY_STRING);
                }
                if (null != objAssetRequestType.getAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING.equals(objAssetRequestType.getAssetGrpName())) {
                    assetLocatorBean.setAssetGrpName(objAssetRequestType.getAssetGrpName());
                } else {
                    assetLocatorBean.setAssetGrpName(RMDCommonConstants.EMPTY_STRING);
                }

                /* Filter stories phase2 sprint4 changes model & fleets */
                if (null != objAssetRequestType.getModelId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(objAssetRequestType.getModelId())) {
                    modelId = objAssetRequestType.getModelId();
                    if (!RMDCommonUtility.isNullOrEmpty(modelId)) {
                        // Converting the comma String to String Array
                        modelIdArray = RMDCommonUtility.splitString(modelId, RMDCommonConstants.COMMMA_SEPARATOR);
                        assetLocatorBean.setModelId(modelIdArray);
                    }
                }

                if (null != objAssetRequestType.getFleetId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(objAssetRequestType.getFleetId())) {
                    fleetId = objAssetRequestType.getFleetId();
                    if (!RMDCommonUtility.isNullOrEmpty(fleetId)) {
                        // Converting the comma String to String Array
                        fleetIdArray = RMDCommonUtility.splitString(fleetId, RMDCommonConstants.COMMMA_SEPARATOR);
                        assetLocatorBean.setFleetId(fleetIdArray);
                    }
                }
                /* Filter stories phase2 sprint4 changes model & fleets */
                assetLocatorBean.setScreenName(objAssetRequestType.getScreenName());
                assetLocatorVoLst = objAssetEoaServiceIntf.getAssetLocation(assetLocatorBean);
                if (null != assetLocatorVoLst && !assetLocatorVoLst.isEmpty()) {
                    DatatypeFactory df = DatatypeFactory.newInstance();
                    for (AssetLocatorVO assetLocatorVO : assetLocatorVoLst) {
                        assetLocatorResponseType = new AssetLocatorResponseType();
                        assetLocatorResponseType.setAssetNumber(assetLocatorVO.getAssetNumber());
                        assetLocatorResponseType.setAssetGrpName(assetLocatorVO.getAssetGrpName());
                        assetLocatorResponseType.setCustomerId(assetLocatorVO.getCustomerId());
                        assetLocatorResponseType.setLatitude(assetLocatorVO.getLatitude());
                        assetLocatorResponseType.setLongitude(assetLocatorVO.getLongitude());
                        assetLocatorResponseType.setModelName(assetLocatorVO.getModelName());
                        if (assetLocatorVO.getLatlonSource() != null) {
                            assetLocatorResponseType.setLatlonsource(assetLocatorVO.getLatlonSource());
                        }
                        if (null != assetLocatorVO.getLatlongDate()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLatlongDate().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            latlonDate = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLatlonDate(latlonDate);
                        }
                        if (null != assetLocatorVO.getLstEOAFaultHeader()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstEOAFaultHeader().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtFault = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstEOAFaultHeader(dtFault);
                        }

                        if (null != assetLocatorVO.getLstPPATSMsgHeader()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstPPATSMsgHeader().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtFault = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstPPATSMsgHeader(dtFault);
                        }

                        if (null != assetLocatorVO.getLstESTPDownloadHeader()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstESTPDownloadHeader().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtFault = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstESTPDownloadHeader(dtFault);
                        }
                        /*
                         * Added as part of Sprint 8 MISC - Start
                         */
                        if (null != assetLocatorVO.getMaxOccurTime()) {
                            GregorianCalendar gc = RMDCommonUtility
                                    .getGMTGregorianCalendar(assetLocatorVO.getMaxOccurTime());
                            XMLGregorianCalendar lastFaultOccurTime = df.newXMLGregorianCalendar(gc);

                            assetLocatorResponseType.setMaxOccurTime(lastFaultOccurTime);
                        }
                        /*
                         * Added as part of Sprint 8 MISC - End
                         */
                        if (null != assetLocatorVO.getLstFaultdt()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstFaultdt().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtFault = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstFaultdt(dtFault);

                        }
                        if (null != assetLocatorVO.getLstPrcddt()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstPrcddt().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtProcessed = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstPrcddt(dtProcessed);
                        }
                        if (null != assetLocatorVO.getDtLastFaultResetTime()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getDtLastFaultResetTime().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtProcessed = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setDtLastFaultResetTime(dtProcessed);
                        }
                        if (null != assetLocatorVO.getDtLastFaultReserved()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getDtLastFaultReserved().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtProcessed = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setDtLastFaultReserved(dtProcessed);
                        }
                        if (null != assetLocatorVO.getDtLastHealthChkRequest()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getDtLastHealthChkRequest().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtProcessed = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setDtLastHealthChkRequest(dtProcessed);
                        }
                        if (null != assetLocatorVO.getDtLastKeepAliveMsgRevd()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getDtLastKeepAliveMsgRevd().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtProcessed = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setDtLastKeepAliveMsgRevd(dtProcessed);
                        }
                        if (null != assetLocatorVO.getLstFalultDateCell()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstFalultDateCell().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtProcessed = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstFaultDateCell(dtProcessed);
                        }
                        lstAssetLocatorResponse.add(assetLocatorResponseType);
                    }
                }
            }

            else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("In AssetResource : getAssetLocation():::END");
        return lstAssetLocatorResponse;
    }

    /**
     * This method is used to get the list of parameters
     * 
     * @param ui
     * @return List<LookupResponseType>
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_FAMILIES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FamilyDetail> getFamilies(@Context final UriInfo uriParam) {

        LOG.debug("Inside Asset resource in getFamilies Method");
        List<FamilyDetail> arlLookupResponseType = new ArrayList<FamilyDetail>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        String familyName = OMDConstants.EMPTY_STRING;
        FamilyDetail objFamilyDetails = null;
        try {
            queryParams = uriParam.getQueryParameters();
            strLanguage = OMDConstants.DEFAULT_LANGUAGE;
            if (queryParams.containsKey(OMDConstants.FAMILY)) {
                familyName = queryParams.getFirst(OMDConstants.FAMILY);

            }
            arlLookupDetails = objRuleCommonServiceInf.getFamilies(strLanguage, familyName);

            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objFamilyDetails = new FamilyDetail();
                    BeanUtility.copyBeanProperty(elementVO, objFamilyDetails);
                    arlLookupResponseType.add(objFamilyDetails);
                }
            }

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        }

        catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return arlLookupResponseType;
    }

    /**
     * This method is used to get the last download status of an asset
     * 
     * @param ui
     * @return List<LookupResponseType>
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_LASTDOWNLOAD_STATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public LastDownLoadStatusResponseType getLastDowloadStatus(@Context UriInfo uriParam) {

        LOG.debug("Inside Asset resource in getLastDowloadStatus Method");
        LastDownloadStatusResponseEoaVO lstDownloadStatusResp = null;
        LastDownLoadStatusResponseType lstDownloadRespType = new LastDownLoadStatusResponseType();
        MultivaluedMap<String, String> queryParams = null;
        GregorianCalendar objGregorianCalendar;
        final LastDownloadStatusEoaVO lstDownloadStatus = new LastDownloadStatusEoaVO();
        String timeZone = getDefaultTimeZone();
        try {
            queryParams = uriParam.getQueryParameters();
            String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            lstDownloadStatus.setLanguage(strLanguage);

            int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.NUMERIC };

            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ASSET_NUMBER,
                    OMDConstants.CUSTOMER_ID, OMDConstants.ASSET_GROUP_NAME, OMDConstants.NO_OF_DAYS)) {
                if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                    lstDownloadStatus.setAssetNumber(queryParams.getFirst(OMDConstants.ASSET_NUMBER));
                } else {
                    lstDownloadStatus.setAssetNumber(RMDCommonConstants.EMPTY_STRING);
                }
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    lstDownloadStatus.setCustomerId(queryParams.getFirst(OMDConstants.CUSTOMER_ID));
                } else {
                    lstDownloadStatus.setCustomerId(RMDCommonConstants.EMPTY_STRING);
                }
                if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                    lstDownloadStatus.setAssetGrpName(queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME));
                } else {
                    lstDownloadStatus.setAssetGrpName(RMDCommonConstants.EMPTY_STRING);
                }

                if (queryParams.containsKey(OMDConstants.NO_OF_DAYS)) {
                    lstDownloadStatus.setNoDays(queryParams.getFirst(OMDConstants.NO_OF_DAYS));
                } else {
                    lstDownloadStatus.setNoDays(RMDCommonConstants.EMPTY_STRING);
                }

                lstDownloadStatusResp = objAssetEoaServiceIntf.getLastDowloadStatus(lstDownloadStatus);

                if (null != lstDownloadStatusResp && !lstDownloadStatusResp.toString().isEmpty()) {
                    DatatypeFactory dtf = DatatypeFactory.newInstance();
                    DateFormat dFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.LAST_DOWNLOAD_DATE);
                    CMBeanUtility.copyDwnldStatVOToDwnLdStatResType(lstDownloadStatusResp, lstDownloadRespType);
                    if (lstDownloadStatusResp.getVaxDt() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(lstDownloadStatusResp.getVaxDt());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lstDownloadRespType.setVaxDt(dtf.newXMLGregorianCalendar(objGregorianCalendar));
                    }

                    if (lstDownloadStatusResp.getEguDt() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(lstDownloadStatusResp.getEguDt());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lstDownloadRespType.setEguDt(dtf.newXMLGregorianCalendar(objGregorianCalendar));
                    }

                    if (lstDownloadStatusResp.getIncDt() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(lstDownloadStatusResp.getIncDt());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lstDownloadRespType.setIncDt(dtf.newXMLGregorianCalendar(objGregorianCalendar));
                    }

                    if (lstDownloadStatusResp.getSnpDt() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(lstDownloadStatusResp.getSnpDt());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lstDownloadRespType.setSnpDt(dtf.newXMLGregorianCalendar(objGregorianCalendar));
                    }

                    if (lstDownloadStatusResp.getLstDownloadTm() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(dFormat.parse(
                                RMDCommonUtility.convertObjectToString(lstDownloadStatusResp.getLstDownloadTm())));
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lstDownloadRespType.setLstDownloadTm(dtf.newXMLGregorianCalendar(objGregorianCalendar));
                    }

                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        }

        catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return lstDownloadRespType;
    }

    /**
     * @Author:iGATE
     * @return list of ParameterResponseType
     * @Description: This method will return the SDP parameters and its values
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_SDPVALUES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ParameterResponseType> getSDPValues(@Context final UriInfo ui) throws RMDServiceException {
        List<ParameterResponseType> lstElementResponse = null;

        List<ParameterServiceVO> lstParameterServiceVO = null;
        String strLanguage = null;
        String family = null;
        ParameterResponseType objParameterResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_NUMERIC_HYPEN };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.FAMILY)) {
                if (queryParams.containsKey(OMDConstants.FAMILY)) {
                    family = queryParams.getFirst(OMDConstants.FAMILY);
                }
                lstParameterServiceVO = objRuleServiceIntf.getSDPValues(family, strLanguage);
                if (RMDCommonUtility.isCollectionNotEmpty(lstParameterServiceVO)) {
                    lstElementResponse = new ArrayList<ParameterResponseType>();
                    for (ParameterServiceVO objParameterServiceVO : lstParameterServiceVO) {
                        objParameterResponseType = new ParameterResponseType();
                        BeanUtility.copyBeanProperty(objParameterServiceVO, objParameterResponseType);
                        lstElementResponse.add(objParameterResponseType);
                    }
                } else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return lstElementResponse;

    }

    /**
     * @Author:iGATE
     * @return list of Services
     * @Description: This method will returns the services for an asset
     */

    @GET
    @Path(OMDConstants.GET_ASSET_SERVICES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public AssetServicesResponseType getAssetServices(@Context final UriInfo ui) {
        List<String> serviceList = new ArrayList<String>();
        MultivaluedMap<String, String> queryParams = null;
        AssetServiceVO objAssetServiceVO = null;
        AssetServicesResponseType objAssetServicesVO = null;
        try {
            String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            objAssetServiceVO = new AssetServiceVO();
            objAssetServiceVO.setStrLanguage(strLanguage);
            queryParams = ui.getQueryParameters();
            int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC,
                    RMDCommonConstants.AlPHA_NUMERIC };

            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ASSET_NUMBER,
                    OMDConstants.GROUP_NAME, OMDConstants.CUSTOMER_ID)) {
                if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                    if (null != queryParams.getFirst(OMDConstants.ASSET_NUMBER)
                            && !queryParams.getFirst(OMDConstants.ASSET_NUMBER).isEmpty()) {
                        objAssetServiceVO.setStrAssetNumber(queryParams.getFirst(OMDConstants.ASSET_NUMBER));
                    } else {
                        throw new OMDInValidInputException(OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
                    }
                }
                if (queryParams.containsKey(OMDConstants.GROUP_NAME)) {
                    if (null != queryParams.getFirst(OMDConstants.GROUP_NAME)
                            && !queryParams.getFirst(OMDConstants.GROUP_NAME).isEmpty()) {
                        objAssetServiceVO.setAssetGroupName(queryParams.getFirst(OMDConstants.GROUP_NAME));
                    } else {
                        throw new OMDInValidInputException(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED);
                    }
                }
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    if (null != queryParams.getFirst(OMDConstants.CUSTOMER_ID)
                            && !queryParams.getFirst(OMDConstants.CUSTOMER_ID).isEmpty()) {

                        objAssetServiceVO.setCustomerID(queryParams.getFirst(OMDConstants.CUSTOMER_ID));
                    } else {
                        throw new OMDInValidInputException(OMDConstants.CUSTOMERID_NOT_PROVIDED);
                    }
                }
                serviceList = objAssetEoaServiceIntf.getAssetServices(objAssetServiceVO);
                if (null != serviceList && !serviceList.isEmpty()) {
                    objAssetServicesVO = new AssetServicesResponseType();
                    objAssetServicesVO.setAssetService(serviceList);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        } catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objAssetServicesVO;
    }

    /**
     * This method is used to get the last fault status of an asset
     *
     * @param ui
     * @return List<LookupResponseType>
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_LAST_FAULT_STATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public LastFaultStatusResponseType getLastFaultStatus(@Context UriInfo uriParam) {

        LOG.debug("Inside Asset resource in getLastFaultStatus Method");
        LastFaultStatusResponseEoaVO lstFaultStatusResp = null;
        LastFaultStatusResponseType lstFaultRespType = new LastFaultStatusResponseType();
        MultivaluedMap<String, String> queryParams = null;
        GregorianCalendar objGregorianCalendar;
        final LastFaultStatusEoaVO lstFaultStatus = new LastFaultStatusEoaVO();
        String timeZone = getDefaultTimeZone();
        try {
            queryParams = uriParam.getQueryParameters();
            String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            lstFaultStatus.setLanguage(strLanguage);

            int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE };

            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ASSET_NUMBER,
                    OMDConstants.CUSTOMER_ID, OMDConstants.ASSET_GROUP_NAME)) {
                if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                    lstFaultStatus.setAssetNumber(queryParams.getFirst(OMDConstants.ASSET_NUMBER));
                } else {
                    lstFaultStatus.setAssetNumber(RMDCommonConstants.EMPTY_STRING);
                }
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    lstFaultStatus.setCustomerId(queryParams.getFirst(OMDConstants.CUSTOMER_ID));
                } else {
                    lstFaultStatus.setCustomerId(RMDCommonConstants.EMPTY_STRING);
                }
                if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                    lstFaultStatus.setAssetGrpName(queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME));
                } else {
                    lstFaultStatus.setAssetGrpName(RMDCommonConstants.EMPTY_STRING);
                }

                lstFaultStatusResp = objAssetEoaServiceIntf.getLastFaultStatus(lstFaultStatus);

                if (null != lstFaultStatusResp) {

                } else {

                }
                CMBeanUtility.copyFaultStatVOToFaultStatResType(lstFaultStatusResp, lstFaultRespType);

                if (null != lstFaultStatusResp && !lstFaultStatusResp.toString().isEmpty()) {
                    DatatypeFactory dtf = DatatypeFactory.newInstance();
                    DateFormat dFormat = new SimpleDateFormat(
                            RMDCommonConstants.DateConstants.LAST_DOWNLOAD_DATE_FORMAT);

                    if (lstFaultStatusResp.getLstEOAFault() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(dFormat
                                .parse(RMDCommonUtility.convertObjectToString(lstFaultStatusResp.getLstEOAFault())));
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lstFaultRespType.setLstEOAFault(dtf.newXMLGregorianCalendar(objGregorianCalendar));
                    }

                    if (lstFaultStatusResp.getLstESTPDownload() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(dFormat.parse(
                                RMDCommonUtility.convertObjectToString(lstFaultStatusResp.getLstESTPDownload())));
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lstFaultRespType.setLstESTPDownload(dtf.newXMLGregorianCalendar(objGregorianCalendar));
                    }

                    if (lstFaultStatusResp.getLstPPATSMsg() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(dFormat
                                .parse(RMDCommonUtility.convertObjectToString(lstFaultStatusResp.getLstPPATSMsg())));
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lstFaultRespType.setLstPPATSMsg(dtf.newXMLGregorianCalendar(objGregorianCalendar));
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        }

        catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return lstFaultRespType;
    }

    /**
     * This method invokes the web service to get List of assets for a role
     * 
     * @return CustomerAssetResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_ASSETS_FOR_ROLE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ProductResponseType> getAssetsForRole(@Context final UriInfo ui) throws RMDServiceException {

        final RoleAssetsVO objRoleAssetsVO = new RoleAssetsVO();
        MultivaluedMap<String, String> queryParams = null;
        RoleAssetsVO roleAssetVO = null;
        ProductResponseType objProdRespType = null;
        List<ProductResponseType> objProdRespTypeLst = new ArrayList<ProductResponseType>();
        try {
            String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            objRoleAssetsVO.setLanguage(strLanguage);
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC, };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ROLE_NAME,
                    OMDConstants.CUSTOMER_ID)) {
                if (queryParams.containsKey(OMDConstants.ROLE_NAME)) {

                    objRoleAssetsVO.setRoleName(queryParams.getFirst(OMDConstants.ROLE_NAME));
                } else {
                    objRoleAssetsVO.setRoleName(RMDCommonConstants.EMPTY_STRING);
                }
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    objRoleAssetsVO.setCustomerId(queryParams.getFirst(OMDConstants.CUSTOMER_ID));
                } else {
                    objRoleAssetsVO.setCustomerId(RMDCommonConstants.EMPTY_STRING);
                }

                roleAssetVO = objAssetEoaServiceIntf.getAssetsForRole(objRoleAssetsVO);
                if (null != roleAssetVO && roleAssetVO.getPrdIdMap().size() > 0) {

                    for (Map.Entry<String, String> entry : roleAssetVO.getPrdIdMap().entrySet()) {
                        objProdRespType = new ProductResponseType();
                        objProdRespType.setProductId(entry.getKey());
                        objProdRespType.setProductName(entry.getValue());
                        objProdRespTypeLst.add(objProdRespType);
                    }
                }
            }

            else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objProdRespTypeLst;
    }

    /**
     * This method is used to validate the data used for getting latitude and
     * longitude AssetLocRequestType
     * 
     * @return boolean
     */
    public static boolean validateAssetLocation(final AssetLocRequestType objAssetRequestType) {

        if (null != objAssetRequestType.getAssetNumber() && !objAssetRequestType.getAssetNumber().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objAssetRequestType.getAssetNumber())) {
                return false;
            }
        }

        if (null != objAssetRequestType.getCustomerId() && !objAssetRequestType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetRequestType.getCustomerId())) {
                return false;
            }
        }

        if (null != objAssetRequestType.getAssetGrpName() && !objAssetRequestType.getAssetGrpName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetRequestType.getAssetGrpName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to validate the data used for getting assets
     * AssetsRequestType
     * 
     * @return boolean
     */
    public static boolean validateGetAssets(final AssetsRequestType objAssetsRequestType) {

        if (null != objAssetsRequestType.getAssetNumber() && !objAssetsRequestType.getAssetNumber().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objAssetsRequestType.getAssetNumber())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getAssetNumberLike() && !objAssetsRequestType.getAssetNumberLike().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objAssetsRequestType.getAssetNumberLike())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getCustomerId() && !objAssetsRequestType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericComma(objAssetsRequestType.getCustomerId())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getCustomer() && !objAssetsRequestType.getCustomer().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getCustomer())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getAssetGrpName() && !objAssetsRequestType.getAssetGrpName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getAssetGrpName())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getModel() && !objAssetsRequestType.getModel().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getModel())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getConfig() && !objAssetsRequestType.getConfig().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getConfig())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getFleet() && !objAssetsRequestType.getFleet().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getFleet())) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to validate the data used for getting assets
     * AssetsRequestType
     * 
     * @return boolean
     */
    public static boolean validateLifeStatisticRequestType(final LifeStatisticsRequestType objAssetsRequestType) {
        if (null != objAssetsRequestType.getAssetNumber() && !objAssetsRequestType.getAssetNumber().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objAssetsRequestType.getAssetNumber())) {
                return false;
            }
        }
        if (null != objAssetsRequestType.getCustomerID() && !objAssetsRequestType.getCustomerID().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getCustomerID())) {
                return false;
            }
        }
        if (null != objAssetsRequestType.getAssetGroupName() && !objAssetsRequestType.getAssetGroupName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getAssetGroupName())) {
                return false;
            }
        }
        if (null != objAssetsRequestType.getControllerCfg() && !objAssetsRequestType.getControllerCfg().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getControllerCfg())) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used for retrieving RequestHistory details for the
     * parameters passed in UriInfo.
     * 
     * @param ui
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_ASSETHCMPGROUPS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<HealthCheckResponseType> getAssetHCMPGroups(@Context final UriInfo ui) {
        RMDLOGGER.debug("AssetResource : Inside getAssetHCMPGroups() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strLanguage = RMDCommonConstants.ENG;
        String strAssetNumber = OMDConstants.EMPTY_STRING;
        String strAssetGroupName = OMDConstants.EMPTY_STRING;
        String assetType = OMDConstants.EMPTY_STRING;
        String deviceName = OMDConstants.EMPTY_STRING;
        List<ElementVO> lstElementVO = null;
        ElementVO elementVO = null;
        List<HealthCheckResponseType> lstHcResponse = null;
        String requestType = OMDConstants.EMPTY_STRING;
        Iterator iterMpGroup;
        try {
            if (getRequestHeader(OMDConstants.LANGUAGE) != null)
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }

            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                strAssetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                strCustomer = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                strAssetGroupName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
            }
            if (queryParams.containsKey(OMDConstants.REQUEST_TYPE_HC)) {
                requestType = queryParams.getFirst(OMDConstants.REQUEST_TYPE_HC);
            }
            if (queryParams.containsKey(OMDConstants.REQ_PARAM_ASSET_TYPE)) {
                assetType = queryParams.getFirst(OMDConstants.REQ_PARAM_ASSET_TYPE);
            }
            if (queryParams.containsKey(OMDConstants.REQ_PARAM_DEVICE_NAME)) {
                deviceName = queryParams.getFirst(OMDConstants.REQ_PARAM_DEVICE_NAME);
            }
            int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ASSET_NUMBER,
                    OMDConstants.GROUP_NAME, OMDConstants.CUSTOMER_ID)) {
                lstElementVO = objHealthChkServiceIntf.getAssetHCMPGroups(strCustomer, strAssetNumber,
                        strAssetGroupName, strLanguage, requestType, assetType, deviceName);
                if (RMDCommonUtility.isCollectionNotEmpty(lstElementVO)) {
                    lstHcResponse = new ArrayList();
                    iterMpGroup = lstElementVO.iterator();
                    HealthCheckResponseType objHCResponseType;
                    while (iterMpGroup.hasNext()) {
                        elementVO = (ElementVO) iterMpGroup.next();
                        objHCResponseType = new HealthCheckResponseType();
                        objHCResponseType.setMpNum(elementVO.getId());
                        objHCResponseType.setMpName(elementVO.getName());
                        lstHcResponse.add(objHCResponseType);
                    }
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside getAssetHCMPGroups() method:::::End ");
        return lstHcResponse;
    }

    /**
     * This method is used for retrieving RequestHistory details for the
     * parameters passed in UriInfo.
     * 
     * @param ui
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.IS_HC_AVAILABLE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HealthCheckAvailableResponse IsHCAvailable(@Context final UriInfo ui) {
        RMDLOGGER.debug("AssetResource : Inside IsHCAvailable() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strLanguage = RMDCommonConstants.ENG;
        String strAssetNumber = OMDConstants.EMPTY_STRING;
        String strAssetGroupName = OMDConstants.EMPTY_STRING;
        HealthCheckAvailableVO result = null;
        HealthCheckAvailableResponse hcAvailableResponse = null;
        try {
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                strAssetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                strCustomer = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                strAssetGroupName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
            }
            if (getRequestHeader(OMDConstants.LANGUAGE) != null)
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ASSET_NUMBER,
                    OMDConstants.GROUP_NAME, OMDConstants.CUSTOMER_ID)) {
                result = objHealthChkServiceIntf.IsHCAvailable(strCustomer, strAssetNumber, strAssetGroupName,
                        strLanguage);
                hcAvailableResponse = new HealthCheckAvailableResponse();
                hcAvailableResponse.setDefaultPlatform(result.getDefaultPlatform());
                hcAvailableResponse.setStrHCMessage(result.getStrHCMessage());
                hcAvailableResponse.setPlatform(result.getPlatform());
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside getIsHCAvailable() method:::::End ");
        return hcAvailableResponse;
    }

    /**
     * This method is used for AssetType (Legacy or EVO) parameters passed in
     * UriInfo.
     * 
     * @param ui
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_HC_ASSET_TYPE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getHCAssetType(@Context final UriInfo ui) {
        RMDLOGGER.debug("AssetResource : Inside getHCAssetType() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strLanguage = RMDCommonConstants.ENG;
        String strAssetNumber = OMDConstants.EMPTY_STRING;
        String strAssetGroupName = OMDConstants.EMPTY_STRING;
        String result = null;

        try {
            if (getRequestHeader(OMDConstants.LANGUAGE) != null)
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }

            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                strAssetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                strCustomer = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                strAssetGroupName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
            }
            int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ASSET_NUMBER,
                    OMDConstants.GROUP_NAME, OMDConstants.CUSTOMER_ID)) {
                result = objHealthChkServiceIntf.getHCAssetType(strCustomer, strAssetNumber, strAssetGroupName,
                        strLanguage);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside getHCAssetType() method:::::End ");
        return result;
    }

    /**
     * This method is used for retrieving RequestHistory details for the
     * parameters passed in UriInfo.
     * 
     * @param ui
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_MCS_WS_ATTRIBUTE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HealthCheckResponseType getSendMessageAttributes(@Context final UriInfo ui) {
        RMDLOGGER.debug("AssetResource : Inside getSendMessageAttributes() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String strAssetType = OMDConstants.EMPTY_STRING;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strInput = OMDConstants.EMPTY_STRING;
        String strAssetNumber = OMDConstants.EMPTY_STRING;
        String strAssetGroupName = OMDConstants.EMPTY_STRING;
        String strLanguage = RMDCommonConstants.ENG;
        String typeOfUser = OMDConstants.EMPTY_STRING;
        String vehObjId = OMDConstants.EMPTY_STRING;
        String device = OMDConstants.EMPTY_STRING;
        HealthCheckAttributeVO hcAttrVO = null;
        HealthCheckResponseType hcRespType = new HealthCheckResponseType();
        Map<String, String> mapCustRnh = new HashMap<String, String>();
        try {
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey("Input")) {
                strInput = queryParams.getFirst("Input");
            }
            if (queryParams.containsKey("assetType")) {
                strAssetType = queryParams.getFirst("assetType");
            }
            if (getRequestHeader(OMDConstants.LANGUAGE) != null)
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                strAssetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                strCustomer = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                strAssetGroupName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
            }
            if (queryParams.containsKey(OMDConstants.TYPE_OF_USER)) {
                typeOfUser = queryParams.getFirst(OMDConstants.TYPE_OF_USER);
            }
            if (queryParams.containsKey(OMDConstants.DEVICE)) {
                device = queryParams.getFirst(OMDConstants.DEVICE);
            }

            if (!RMDCommonUtility.isSpecialCharactersFound(strInput)) {
                mapCustRnh = objHealthChkServiceIntf.getCustrnhDetails(strCustomer, strAssetNumber, strAssetGroupName,
                        strLanguage);
                if (mapCustRnh != null) {
                    for (Map.Entry<String, String> entry : mapCustRnh.entrySet()) {
                        hcRespType.setVehObjId(entry.getKey());
                        hcRespType.setVehicleHDRNum(entry.getValue());
                    }
                }
                vehObjId = hcRespType.getVehObjId();
                hcAttrVO = objHealthChkServiceIntf.getSendMessageAttributes(strInput, strAssetType, strLanguage,
                        typeOfUser, vehObjId, device);
                if (hcAttrVO != null) {
                    hcRespType.setDestType(hcAttrVO.getDestType());
                    hcRespType.setSigned(hcAttrVO.getSigned());
                    hcRespType.setNoOfBytes(hcAttrVO.getNoOfBytes());
                    hcRespType.setSourceApp(hcAttrVO.getSourceApp());
                    hcRespType.setMpRateNum(hcAttrVO.getmPRateNum());
                    hcRespType.setMpReqNum(hcAttrVO.getRequestParamNum());
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside getSendMessageAttributes() method:::::End ");
        return hcRespType;
    }

    @GET
    @Path(OMDConstants.GET_HC_CUST_RNH_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getCustrnhDetails(@Context final UriInfo ui) {
        RMDLOGGER.debug("AssetResource : Inside getCustrnhDetails() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strLanguage = RMDCommonConstants.ENG;
        String strAssetNumber = OMDConstants.EMPTY_STRING;
        String strAssetGroupName = OMDConstants.EMPTY_STRING;
        String result = null;
        Map<String, String> mapCustRnh = new HashMap<String, String>();
        try {
            if (getRequestHeader(OMDConstants.LANGUAGE) != null)
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }

            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                strAssetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                strCustomer = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                strAssetGroupName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
            }
            int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.ASSET_NUMBER,
                    OMDConstants.GROUP_NAME, OMDConstants.CUSTOMER_ID)) {
                mapCustRnh = objHealthChkServiceIntf.getCustrnhDetails(strCustomer, strAssetNumber, strAssetGroupName,
                        strLanguage);

                if (mapCustRnh != null) {
                    for (Map.Entry<String, String> entry : mapCustRnh.entrySet()) {
                        result = entry.getValue();
                    }
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside getCustrnhDetails() method:::::End ");
        return result;
    }

    /**
     * @Author:
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the legends list for Map
     */
    @GET
    @Path(OMDConstants.GET_MAP_LAST_REFRESH_TIME)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ApplicationParametersResponseType> getMapLastRefreshTime(@Context final UriInfo ui)
            throws RMDBOException {
        MultivaluedMap<String, String> queryParams = null;
        List<GetSysLookupVO> lstSysLookupVO = null;
        List<ApplicationParametersResponseType> appParamResTypeLst = new ArrayList<ApplicationParametersResponseType>();
        GetSysLookupVO getSysLookupVO = null;
        ApplicationParametersResponseType appParamResType = null;
        final GetSysLookupVO sysLookupVO = new GetSysLookupVO();
        String lookupName = RMDCommonConstants.EMPTY_STRING;
        try {
            queryParams = ui.getQueryParameters();
            String timeZone = getDefaultTimeZone();

            int[] methodConstants = { RMDCommonConstants.ALPHA_UNDERSCORE };

            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.LIST_NAME)) {

                lookupName = queryParams.getFirst(OMDConstants.LIST_NAME);
                sysLookupVO.setListName(lookupName);

                lstSysLookupVO = objAssetEoaServiceIntf.getMapLastRefreshTime(sysLookupVO);
                if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {

                    DatatypeFactory df = DatatypeFactory.newInstance();

                    for (int i = 0; i < lstSysLookupVO.size(); i++) {
                        appParamResType = new ApplicationParametersResponseType();
                        getSysLookupVO = lstSysLookupVO.get(i);
                        GregorianCalendar gc = new GregorianCalendar();

                        gc.setTimeInMillis(getSysLookupVO.getLastUpdatedDate().getTime());
                        RMDCommonUtility.setZoneOffsetTime(gc, timeZone);
                        XMLGregorianCalendar lastUpdatedDate = df.newXMLGregorianCalendar(gc);
                        appParamResType.setLastUpdatedDate(lastUpdatedDate);
                        /*
                         * added for map last refresh time phase 2 sprint 6 MISC
                         * change - End
                         */
                        appParamResTypeLst.add(appParamResType);
                    }
                }
                return appParamResTypeLst;
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, sysLookupVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    @GET
    @Path(OMDConstants.GET_FAULT_CODE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultCodeDetailsResponseType> getFaultCode(@Context final UriInfo ui) throws RMDServiceException {
        FaultCodeDetailsResponseType faultDataDetails = null;
        String controllerCfg = RMDCommonConstants.EMPTY_STRING;
        String faultCode = RMDCommonConstants.EMPTY_STRING;
        List<FaultCodeDetailsResponseType> arlFaultCodeDetailsResponseType = new ArrayList<FaultCodeDetailsResponseType>();

        try {

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();

            int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants,
                    OMDConstants.CONTROLLER_CONFIG)) {
                if (!queryParams.isEmpty()) {
                    if (queryParams.containsKey(OMDConstants.CONTROLLER_CONFIG)) {
                        controllerCfg = queryParams.getFirst(OMDConstants.CONTROLLER_CONFIG);
                    }
                    if (queryParams.containsKey(OMDConstants.FAULT_CODE)) {
                        faultCode = queryParams.getFirst(OMDConstants.FAULT_CODE);
                    }

                    final String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                    final String userLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
                    if (RMDCommonUtility.isNull(strLanguage)) {
                        throw new OMDInValidInputException(OMDConstants.INVALID_LANGUAGE);
                    }
                    if (RMDCommonUtility.isNull(userLanguage)) {
                        throw new OMDInValidInputException(OMDConstants.INVALID_USER_LANGUAGE);
                    }

                    List<FaultCodeDetailsServiceVO> arlFltCode = objAssetEoaServiceIntf.getFaultCode(controllerCfg,
                            faultCode, strLanguage);

                    if (arlFltCode != null) {
                        for (Iterator iterator = arlFltCode.iterator(); iterator.hasNext();) {
                            faultDataDetails = new FaultCodeDetailsResponseType();
                            FaultCodeDetailsServiceVO objFaultCodeDetailsServiceVO = (FaultCodeDetailsServiceVO) iterator
                                    .next();
                            faultDataDetails.setControllerConfig(objFaultCodeDetailsServiceVO.getControllerConfig());
                            faultDataDetails.setFaultCode(objFaultCodeDetailsServiceVO.getFaultCode());
                            faultDataDetails
                                    .setFaultDesc(faultCodehtmlEscaping(objFaultCodeDetailsServiceVO.getFaultDesc()));
                            faultDataDetails.setFaultDetail(
                                    faultCodehtmlEscaping(objFaultCodeDetailsServiceVO.getFaultDetail()));
                            objFaultCodeDetailsServiceVO.getFaultDetail();
                            arlFaultCodeDetailsResponseType.add(faultDataDetails);

                        }
                    } /*else {
                        throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                    }*/
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

                }*/
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        return arlFaultCodeDetailsResponseType;
    }

    private static String faultCodehtmlEscaping(final String fieldValue) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < fieldValue.length(); i++) {
            final char character = fieldValue.charAt(i);
            if (character == 25) {
                result.append(RMDCommonConstants.EMPTY_STRING);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    /**
     * This method is used to submit healthCheck Request for EGA platform
     * 
     * @param healthCheckSubmitRequest
     * @return String
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.SUBMIT_HC_REQUEST)
    @Consumes(MediaType.APPLICATION_XML)
    public String submitHealthCheckRequest(final HealthCheckSubmitRequest healthCheckSubmitRequest) {
        RMDLOGGER.debug("AssetResource : Inside submitHealthCheckRequest() method:::::START ");
        HealthCheckSubmitEoaVO hcSubmitEoaVO = new HealthCheckSubmitEoaVO();
        String msgRequestId = null;
        Map<String, String> result = new HashMap<String, String>();
        try {
            result = validateUserInput(healthCheckSubmitRequest);
            if (null != result) {
            	if(null !=healthCheckSubmitRequest.getType() && OMDConstants.HC_REQ_TYPE_MOBILITY.equalsIgnoreCase(healthCheckSubmitRequest.getType()))
            	{
            		 hcSubmitEoaVO.setUserId(healthCheckSubmitRequest.getUserId());
            	}
            	else
            	{
                    hcSubmitEoaVO.setUserId(getRequestHeader(OMDConstants.USER_ID));
            	}
                hcSubmitEoaVO.setCustomerID(healthCheckSubmitRequest.getCustomerID());
                hcSubmitEoaVO.setRoadIntial(healthCheckSubmitRequest.getRoadIntial());
                hcSubmitEoaVO.setRoadNumber(healthCheckSubmitRequest.getRoadNumber());
                hcSubmitEoaVO.setAssetType(healthCheckSubmitRequest.getAssetType());
                hcSubmitEoaVO.setMpGroupId(healthCheckSubmitRequest.getMpGroupId());
                hcSubmitEoaVO.setMpGroupName(healthCheckSubmitRequest.getMpGroupName());
                hcSubmitEoaVO.setSampleRate(healthCheckSubmitRequest.getSampleRate());
                hcSubmitEoaVO.setPostSamples(healthCheckSubmitRequest.getPostSamples());
                hcSubmitEoaVO.setPlatform(healthCheckSubmitRequest.getPlatform());
                hcSubmitEoaVO.setMpNumber(healthCheckSubmitRequest.getMpNumber());
                hcSubmitEoaVO.setTypeOfUser(healthCheckSubmitRequest.getTypeOfUser());
                msgRequestId = objHealthChkServiceIntf.submitHealthCheckRequest(hcSubmitEoaVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("AssetResource : Inside submitHealthCheckRequest() method:::::End ");
        return msgRequestId;
    }

    /**
     * @Author:GE
     * @param
     * @return Map<String,String>
     * @throws @Description
     *             * This method is used for Validating user input for request
     *             healthcheck page
     * 
     */

    public Map<String, String> validateUserInput(HealthCheckSubmitRequest healthCheckSubmitRequest) {
        final Map<String, String> result = new HashMap<String, String>();
        RMDLOGGER.debug("HealthCheckController : Inside validateUserInput() method:::::Start ");
        try {
            if (healthCheckSubmitRequest.getSampleRate() == 0) {
                result.put(OMDConstants.SAMPLE_RATE, OMDConstants.INVALID_INPUTS);
            }
            if (healthCheckSubmitRequest.getPostSamples() == 0) {
                result.put(OMDConstants.NO_OF_POST_SAMPLES, OMDConstants.INVALID_INPUTS);
            }

            if (RMDCommonConstants.HC_ASSET_TYPE_EVO
                    .equalsIgnoreCase(healthCheckSubmitRequest.getAssetType())
                    && ((RMDCommonUtility
                            .convertObjectToString(healthCheckSubmitRequest
                                    .getMpGroupId())).isEmpty())
                    || RMDCommonUtility
                            .isSpecialCharactersFound(RMDCommonUtility
                                    .convertObjectToString(healthCheckSubmitRequest
                                            .getMpGroupId()))) {
                result.put(OMDConstants.MP_NUMBERS,
                        OMDConstants.INVALID_CHARACTER);
            }
            }catch(
    Exception exception)
    {
        LOG.debug("", exception);
        RMDLOGGER.error("Error occured in validateUserInput" + exception.getMessage());
    }RMDLOGGER.debug("HealthCheckController : Inside validateUserInput() method:::::End ");return result;
    }

    /**
     * This method is used to submit healthCheck Request for EGA platform
     * 
     * @param healthCheckSubmitRequest
     * @return Map<String, String>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.VIEW_HC_REQUEST_HISTORY)
    @Consumes(MediaType.APPLICATION_XML)
    public List<HealthCheckViewResponse> viewRequestHistory(HcViewHistoryRequestType hcViewHistoryRequestType) {
        RMDLOGGER.debug("AssetResource : Inside getSendMessageAttributes() method:::::START ");

        ViewReqHistoryEoaVo viewReqHistoryEoaVo = new ViewReqHistoryEoaVo();
        List<ViewRespHistoryEoaVo> viewHistoryList = null;
		HealthCheckViewResponse healthCheckViewResponse = null;

        List<HealthCheckViewResponse> historyList = new ArrayList<HealthCheckViewResponse>();
        try {
            
            viewReqHistoryEoaVo.setCustomerID(hcViewHistoryRequestType.getCustomerId());

            viewReqHistoryEoaVo.setRoadIntial(hcViewHistoryRequestType.getRoadInitial());

            viewReqHistoryEoaVo
                    .setRoadNumber(RMDCommonUtility.convertObjectToLong(hcViewHistoryRequestType.getRoadNumber()));

            viewReqHistoryEoaVo.setMessageID(hcViewHistoryRequestType.getMessageId());
            viewReqHistoryEoaVo.setMessageIdCustomer(hcViewHistoryRequestType.getMessageIdCustomer());
            viewReqHistoryEoaVo.setMessageIdAuto(hcViewHistoryRequestType.getMessageIdAuto());
            viewReqHistoryEoaVo.setToDate(hcViewHistoryRequestType.getToDate());

            viewReqHistoryEoaVo.setFromDate(hcViewHistoryRequestType.getFromDate());
            viewReqHistoryEoaVo.setIsInternal(hcViewHistoryRequestType.getIsInternal());
            viewReqHistoryEoaVo.setDevice(hcViewHistoryRequestType.getPlatform());
            viewHistoryList = objHealthChkServiceIntf.viewRequestHistory(viewReqHistoryEoaVo);
            
            if(viewHistoryList != null) {
                
                historyList = new ArrayList<HealthCheckViewResponse>(viewHistoryList.size());
            
            
            for (ViewRespHistoryEoaVo viewRespHistoryEoaVo : viewHistoryList) {

                healthCheckViewResponse = new HealthCheckViewResponse();

                healthCheckViewResponse.setMessageDirection(viewRespHistoryEoaVo.getMessageDirection());
                healthCheckViewResponse.setMessageId(viewRespHistoryEoaVo.getMessageId());
                healthCheckViewResponse.setMessageType(viewRespHistoryEoaVo.getMessageType());
                healthCheckViewResponse.setRequestor(viewRespHistoryEoaVo.getRequestor());

					healthCheckViewResponse.setRequestDate(viewRespHistoryEoaVo.getRequestDate());
					healthCheckViewResponse.setStatus(viewRespHistoryEoaVo
							.getStatus());
					healthCheckViewResponse.setRetryCount(viewRespHistoryEoaVo
							.getRetryCount());

					healthCheckViewResponse.setStatusDate(viewRespHistoryEoaVo.getStatusDate());
                healthCheckViewResponse.setTemplateVersion(viewRespHistoryEoaVo.getTemplateVersion());
                healthCheckViewResponse.setMessageDirection(viewRespHistoryEoaVo.getMessageDirection());
                healthCheckViewResponse.setOffboardSequence(viewRespHistoryEoaVo.getOffboardSequence());
                historyList.add(healthCheckViewResponse);
            }
            }

        } catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		} finally {
			viewHistoryList = null;
			healthCheckViewResponse = null;
			viewReqHistoryEoaVo = null;
        }
		RMDLOGGER
				.debug("AssetResource : Inside getSendMessageAttributes() method:::::End ");
        return historyList;
    }

    /**
     * This method is used to submit healthCheck Request for EGA platform
     * 
     * @param healthCheckSubmitRequest
     * @return String
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.LIFE_STATISTICS_DATA)
    @Consumes(MediaType.APPLICATION_XML)
    public List<LifeStatisticsResponseType> getLifeStatisticsData(final LifeStatisticsRequestType objRequestType)
            throws RMDServiceException {
        RMDLOGGER.debug("AssetResource : Inside getLifeStatisticsData() method:::::START ");
        AssetServiceVO objAssetServiceVO;
        List<LifeStatisticsEoaVO> lifeStatisticsResultlist = new ArrayList<LifeStatisticsEoaVO>();
        LifeStatisticsResponseType objLifeStatisticsResponseType = null;
        List<LifeStatisticsResponseType> resultStatisticsList = new ArrayList<LifeStatisticsResponseType>();
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar gatheringDate = null;
        DatatypeFactory dataFactory = null;
        DateFormat dFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        String timeZone = getDefaultTimeZone();
        try {
            dFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            if (validateLifeStatisticRequestType(objRequestType)) {
                objAssetServiceVO = new AssetServiceVO();
                objAssetServiceVO.setAssetGroupName(objRequestType.getAssetGroupName());
                objAssetServiceVO.setCustomerID(objRequestType.getCustomerID());
                objAssetServiceVO.setStrAssetNumber(objRequestType.getAssetNumber());
                objAssetServiceVO.setControllerConfig(objRequestType.getControllerCfg());
                objAssetServiceVO.setHideL3Fault(objRequestType.isHideL3Fault());
                if (null != objRequestType.getFromDate()) {

                    objAssetServiceVO
                            .setFromDate(dFormat.format(objRequestType.getFromDate().toGregorianCalendar().getTime()));
                }
                if (null != objRequestType.getToDate()) {
                    objAssetServiceVO
                            .setToDate(dFormat.format(objRequestType.getToDate().toGregorianCalendar().getTime()));
                }
                lifeStatisticsResultlist = objAssetEoaServiceIntf.getLifeStatisticsData(objAssetServiceVO);
                dataFactory = DatatypeFactory.newInstance();
                for (LifeStatisticsEoaVO currentStatisticsVO : lifeStatisticsResultlist) {
                    objLifeStatisticsResponseType = new LifeStatisticsResponseType();
                    objLifeStatisticsResponseType.setAttributeName(currentStatisticsVO.getAttributeName());
                    objLifeStatisticsResponseType.setAttributeValue(currentStatisticsVO.getAttributeValue());
                    objLifeStatisticsResponseType.setSortOrder(currentStatisticsVO.getSortOrder());
                    objLifeStatisticsResponseType.setAttributeToolTip(currentStatisticsVO.getAttributeToolTip());
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(currentStatisticsVO.getStatisticGatheringDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    gatheringDate = dataFactory.newXMLGregorianCalendar(objGregorianCalendar);
                    objLifeStatisticsResponseType.setUnits(currentStatisticsVO.getUnit());
                    objLifeStatisticsResponseType.setStatisticGatheringDate(gatheringDate);
                    resultStatisticsList.add(objLifeStatisticsResponseType);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            LOG.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        RMDLOGGER.debug("AssetResource : Inside getLifeStatisticsData() method:::::End ");
        return resultStatisticsList;
    }

    @GET
    @Path(OMDConstants.GET_ALL_FLEETS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FleetResponseType> getAllFleets(@Context final UriInfo ui) throws RMDServiceException {
        List<FleetResponseType> resFleetList = new ArrayList<FleetResponseType>();
        FleetResponseType respFleet = null;
        List<FleetVO> fleetList = null;

        try {
            fleetList = objAssetEoaServiceIntf.getAllFleets();
            LOG.debug("Customer List retrieved from RMD Service ");
            if (null != fleetList) {
                if (RMDCommonUtility.isCollectionNotEmpty(fleetList)) {
                    for (FleetVO fleetVo : fleetList) {
                        respFleet = new FleetResponseType();
                        respFleet.setFleetId(fleetVo.getFleetId());
                        respFleet.setFleetNo(fleetVo.getFleetNo());
                        respFleet.setCustomerId(fleetVo.getCustomerId());
                        resFleetList.add(respFleet);
                    }
                } else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return resFleetList;
    }

    @GET
    @Path(OMDConstants.GET_ALL_REGIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RegionResponseType> getAllRegions(@Context final UriInfo ui) throws RMDServiceException {
        List<RegionResponseType> resRegionList = new ArrayList<RegionResponseType>();
        RegionResponseType respRegion = null;
        List<RegionVO> regionList = null;
        
        try {
            regionList = objAssetEoaServiceIntf.getAllRegions();
            LOG.debug("Region List retrieved from RMD Service ");
            if (null != regionList) {
                if (RMDCommonUtility.isCollectionNotEmpty(regionList)) {

                    for (RegionVO regionVo : regionList) {
                        respRegion = new RegionResponseType();
                        respRegion.setRegionId(regionVo.getRegionId());
                        respRegion.setRegionName(regionVo.getRegionName());
                        respRegion.setCustomerId(regionVo.getCustomerId());
                        resRegionList.add(respRegion);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return resRegionList;
    }

    @GET
    @Path(OMDConstants.GET_SUB_DIVISION)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SubDivisionResponseType> getAllSubDivisions(@Context final UriInfo ui) throws RMDServiceException {
        List<SubDivisionResponseType> resRegionList = new ArrayList<SubDivisionResponseType>();
        SubDivisionResponseType respRegion = null;
        List<RegionVO> regionList = null;
        String region = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();

            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.REGION)) {
                    region = queryParams.getFirst(OMDConstants.REGION);
                }
            }
            regionList = objAssetEoaServiceIntf.getAllSubDivisions(region);
            LOG.debug("Region List retrieved from RMD Service ");
            if (null != regionList) {
                if (RMDCommonUtility.isCollectionNotEmpty(regionList)) {

                    for (RegionVO regionVo : regionList) {
                        respRegion = new SubDivisionResponseType();
                        respRegion.setSubDivisionId(regionVo.getSubDivisionId());
                        respRegion.setSubDivisionName(regionVo.getSubDivisionName());
                        respRegion.setRegionId(regionVo.getRegionId());
                        respRegion.setCustomerId(regionVo.getCustomerId());
                        resRegionList.add(respRegion);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return resRegionList;
    }

    @GET
    @Path(OMDConstants.GET_CALL_LOG_NOTES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CallLogNotesResponseType> getCallLogNotes( @Context final UriInfo ui)
    throws RMDServiceException {
        List<CallLogNotesResponseType> lstCallLogNotesType = new ArrayList<CallLogNotesResponseType>();
        CallLogNotesResponseType respNotesType = null;
        String timezone = OMDConstants.EMPTY_STRING;
        try {
            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
        if(queryParams!=null){                      
                CallLogNotesVO callLogNotesVO = new CallLogNotesVO();
                callLogNotesVO.setFromDate(queryParams.getFirst(OMDConstants.FROM_DATE));
                callLogNotesVO.setToDate(queryParams.getFirst(OMDConstants.TO_DATE));
                callLogNotesVO.setCustomerId(queryParams.getFirst(OMDConstants.CUSTOMER_ID));
                callLogNotesVO.setAssetNumber(queryParams.getFirst(OMDConstants.ASSET_NUMBER));
                callLogNotesVO.setAssetGroupName(queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME));
                if (queryParams.containsKey(OMDConstants.TIMEZONE)){
                    timezone = queryParams.getFirst(OMDConstants.TIMEZONE);
                }
                
                List<CallLogNotesVO> callLogNotesList = objCallLogNotesServiceIntf.getCallLogNotes(callLogNotesVO);
                if (null != callLogNotesList) {
                    if (RMDCommonUtility.isCollectionNotEmpty(callLogNotesList)) {                      
                        for(CallLogNotesVO callLogNotes : callLogNotesList){
                            respNotesType = new CallLogNotesResponseType();                             
                            respNotesType.setCallLogID(callLogNotes.getCallLogID());
                            respNotesType.setCallerName(callLogNotes.getCallerName());
                            respNotesType.setCustomerId(callLogNotes.getCustomerId());          
                            respNotesType.setAgentSSO(callLogNotes.getAgentSSO());                          
                            respNotesType.setBusniessArea(callLogNotes.getBusniessArea());
                            respNotesType.setCallType(callLogNotes.getCallType());                          
                            respNotesType.setLocation(callLogNotes.getLocation());
                            respNotesType.setCreationDate(callLogNotes.getCreationDate());
                          /*  
                            if(null!=callLogNotes.getCreationDate()){
                                
                                respNotesType.setCreationDate(RMDCommonUtility.convertXMLGregorianCalenderToString(
                                        RMDCommonUtility
                                        .convertDateToXMLGregorianCalender(
                                                callLogNotes.getCreationDate(),
                                                getDefaultTimeZone()),"Canada/Eastern"));
                            }          */             
                            
                            respNotesType.setNotes(callLogNotes.getNotes());
                            respNotesType.setIssueType(callLogNotes.getIssueType());                            
                            respNotesType.setAgentName(callLogNotes.getAgentName());
                            respNotesType.setCallDurationSeconds(callLogNotes.getCallDurationSeconds());
                            respNotesType.setCallEndedOn(callLogNotes.getCallEndedOn());
                            //Added for US276325    GPOC: SFDC - OMD Integration - Showing two additional columns in OMD call log screen
                            respNotesType.setCallStartedOn(callLogNotes.getCallStartedOn());
                            lstCallLogNotesType.add(respNotesType);
                        }
                    }/*else { 
                        throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                    }*/
                }/*else { 
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            }
        }  catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return lstCallLogNotesType;
    }
    /**
     * @Author:
     * @param UriInfo
     * @return String
     * @throws RMDServiceException
     * @Description:This method fetches the device information.
     */
    @GET
    @Path(OMDConstants.GET_DEVICE_INFO)
    @Produces({ MediaType.APPLICATION_JSON })
    public String getDeviceInfo(@Context final UriInfo ui)
            throws RMDServiceException {
        RMDLOGGER
                .debug("AssetResource : Inside getDeviceInfo() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String result = null;
        HealthCheckVO objHealthCheckVO = new HealthCheckVO();
        try {
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                objHealthCheckVO.setRoadNumber(queryParams
                        .getFirst(OMDConstants.ASSET_NUMBER));
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                objHealthCheckVO.setCustomerId(queryParams
                        .getFirst(OMDConstants.CUSTOMER_ID));
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                objHealthCheckVO.setAssetGrpName(queryParams
                        .getFirst(OMDConstants.ASSET_GROUP_NAME));
            }
            if (queryParams.containsKey(OMDConstants.TYPE_OF_USER)) {
                objHealthCheckVO.setTypeOfUser(queryParams
                        .getFirst(OMDConstants.TYPE_OF_USER));
            }
            result = objHealthChkServiceIntf.getDeviceInfo(objHealthCheckVO);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        RMDLOGGER
                .debug("AssetResource : Inside getDeviceInfo() method:::::End ");
        return result;
    }
    /**
     * @Author:
     * @param HealthCheckSubmitRequest
     * @return String
     * @throws RMDServiceException
     * @Description:This method saves the health check details.
     */
    @POST
    @Path(OMDConstants.SAVE_HC_DETAILS)
    @Produces( {MediaType.APPLICATION_JSON })
    public String saveHCDetails(
            HealthCheckSubmitRequest objHealthCheckSubmitRequest)
            throws RMDServiceException {
        RMDLOGGER
                .debug("AssetResource : Inside saveHCDetails() method:::::START ");
        String result = null;
        HealthCheckVO objHealthCheckVO = new HealthCheckVO();
        try {
            if (null != objHealthCheckSubmitRequest) {
                if (null != String.valueOf(objHealthCheckSubmitRequest
                        .getRoadNumber())
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(String
                                        .valueOf(objHealthCheckSubmitRequest
                                                .getRoadNumber()))) {
                    objHealthCheckVO.setRoadNumber(String
                            .valueOf(objHealthCheckSubmitRequest
                                    .getRoadNumber()));
                }

                if (null != objHealthCheckSubmitRequest.getRoadIntial()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(objHealthCheckSubmitRequest
                                        .getRoadIntial())) {
                    objHealthCheckVO
                            .setAssetGrpName(objHealthCheckSubmitRequest
                                    .getRoadIntial());
                }

                if (null != objHealthCheckSubmitRequest.getCustomerID()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(objHealthCheckSubmitRequest
                                        .getCustomerID())) {
                    objHealthCheckVO.setCustomerId(objHealthCheckSubmitRequest
                            .getCustomerID());
                }
                if (null != objHealthCheckSubmitRequest.getUserId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(objHealthCheckSubmitRequest
                                        .getUserId())) {
                    objHealthCheckVO.setUserId(objHealthCheckSubmitRequest
                            .getUserId());
                }
                if (null != objHealthCheckSubmitRequest.getTypeOfUser()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(objHealthCheckSubmitRequest
                                        .getTypeOfUser())) {
                    objHealthCheckVO.setTypeOfUser(objHealthCheckSubmitRequest
                            .getTypeOfUser());
                }

                if (null != String.valueOf(objHealthCheckSubmitRequest
                        .getPostSamples())
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(String
                                        .valueOf(objHealthCheckSubmitRequest
                                                .getPostSamples()))) {
                    objHealthCheckVO.setNoOfPostSamples(String
                            .valueOf(objHealthCheckSubmitRequest
                                    .getPostSamples()));
                }

                if (null != objHealthCheckSubmitRequest.getMsgId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(objHealthCheckSubmitRequest
                                        .getMsgId())) {
                    objHealthCheckVO.setMessageId(objHealthCheckSubmitRequest
                            .getMsgId());
                }
                result = objHealthChkServiceIntf
                        .saveHCDetails(objHealthCheckVO);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        RMDLOGGER
                .debug("AssetResource : Inside saveHCDetails() method:::::End ");
        return result;
    }
    /**
     * @Author:
     * @param:String vehicleObjId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used check whether PP Asset History Button
     *               Has to Disable or Enable.
     */
    @GET
    @Path(OMDConstants.GET_PP_ASSET_HST_BTN_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String displayPPAssetHistoryBtn(@Context UriInfo ui)
            throws RMDServiceException {
        String vehicleObjId = null;
        String result = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            if (queryParams.containsKey(OMDConstants.VEHICLE_OBJID)) {
                vehicleObjId = queryParams.getFirst(OMDConstants.VEHICLE_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(vehicleObjId)) {
                throw new OMDInValidInputException(
                        OMDConstants.CASE_ID_NOT_PROVIDED);
            }
            result = objAssetEoaServiceIntf
                    .displayPPAssetHistoryBtn(vehicleObjId);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  displayPPAssetHistoryBtn() METHOD OF CASERESOURCE ");
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return result;
    }

    /**
     * @Author:
     * @param:
     * @return:List<AssetResponseType>
     * @throws:RMDWebException
     * @Description: This method is used for fetching the Road Number.
     */
    @GET
    @Path(OMDConstants.GET_CUSTOMER_RNH)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetResponseType> getCustNameRNH(
            @Context final UriInfo uriParam) throws RMDServiceException {
        List<AssetServiceVO> eoaAssetsLst = new ArrayList<AssetServiceVO>();
        final List<AssetResponseType> lstAssetResponseType = new ArrayList<AssetResponseType>();
        AssetResponseType objAssetResponseType = null;
        try {
            MultivaluedMap<String, String> queryParams = uriParam
                    .getQueryParameters();
            queryParams = uriParam.getQueryParameters();
            eoaAssetsLst = objAssetEoaServiceIntf.getCustNameRNH();
            if (RMDCommonUtility.isCollectionNotEmpty(eoaAssetsLst)) {
                for (AssetServiceVO objAssetServiceVO : eoaAssetsLst) {
                    objAssetResponseType = new AssetResponseType();
                    objAssetResponseType.setAssetGroupName(objAssetServiceVO
                            .getAssetGroupName());
                    objAssetResponseType.setCustomerName(objAssetServiceVO
                            .getCustomerName());
                    lstAssetResponseType.add(objAssetResponseType);
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return lstAssetResponseType;
    }
    
    /**
     * @Author:
     * @param:
     * @return:List<AssetResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    @GET
    @Path(OMDConstants.GET_ROAD_NUM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetResponseType> getRoadNumbers(
            @Context final UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<AssetServiceVO> eoaAssetsLst = new ArrayList<AssetServiceVO>();
        final List<AssetResponseType> lstAssetResponseType = new ArrayList<AssetResponseType>();
        AssetResponseType objAssetResponseType = null;
        AssetServiceVO objAssetServiceVO = new AssetServiceVO();
        try {
            queryParams = uriParam.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.RNH_ID)) {
                objAssetServiceVO.setAssetGroupName(queryParams
                        .getFirst(OMDConstants.RNH_ID));
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_NAME)) {
                objAssetServiceVO.setCustomerName(queryParams
                        .getFirst(OMDConstants.CUSTOMER_NAME));
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
            eoaAssetsLst = objAssetEoaServiceIntf
                    .getRoadNumbers(objAssetServiceVO);
            if (RMDCommonUtility.isCollectionNotEmpty(eoaAssetsLst)) {
                for (AssetServiceVO objAssetSrvcVO : eoaAssetsLst) {
                    objAssetResponseType = new AssetResponseType();
                    objAssetResponseType.setAssetNumber(objAssetSrvcVO
                            .getStrAssetNumber());
                    lstAssetResponseType.add(objAssetResponseType);
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return lstAssetResponseType;
    }
    
    /**
     * @Author:
     * @param:
     * @return:List<AssetResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    @GET
    @Path(OMDConstants.GET_ROAD_NUM_WITH_FILTER)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetResponseType> getRoadNumbersWithFilter(
            @Context final UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<AssetServiceVO> eoaAssetsLst = new ArrayList<AssetServiceVO>();
        final List<AssetResponseType> lstAssetResponseType = new ArrayList<AssetResponseType>();
        AssetResponseType objAssetResponseType = null;
        AssetServiceVO objAssetServiceVO = new AssetServiceVO();
        String customer = null;
        String rnh = null;
        String rnSearchString = null;
        String rnFilter = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.RNH_ID)) {
                rnh = queryParams
                        .getFirst(OMDConstants.RNH_ID);
            }
            if (queryParams.containsKey(OMDConstants.RN_SEARCH_STRING)) {
                rnSearchString = queryParams
                        .getFirst(OMDConstants.RN_SEARCH_STRING);
            }
            if (queryParams.containsKey(OMDConstants.RN_FILTER)) {
                rnFilter = queryParams
                        .getFirst(OMDConstants.RN_FILTER);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_NAME)) {
                customer = queryParams
                        .getFirst(OMDConstants.CUSTOMER_NAME);
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
            eoaAssetsLst = objAssetEoaServiceIntf
                    .getRoadNumbersWithFilter(customer, rnh, rnSearchString, rnFilter);
            if (RMDCommonUtility.isCollectionNotEmpty(eoaAssetsLst)) {
                for (AssetServiceVO objAssetSrvcVO : eoaAssetsLst) {
                    objAssetResponseType = new AssetResponseType();
                    objAssetResponseType.setAssetNumber(objAssetSrvcVO
                            .getStrAssetNumber());
                    lstAssetResponseType.add(objAssetResponseType);
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return lstAssetResponseType;
    }
    /**
     * @Author:
     * @param UriInfo
     * @return String
     * @throws RMDServiceException
     * @Description:This method validates EGA HC Request.
     */
    @GET
    @Path(OMDConstants.VALIDATE_EGA_HC)
    @Produces({ MediaType.APPLICATION_JSON })
    public String validateEGAHC(@Context final UriInfo ui)
            throws RMDServiceException {
        RMDLOGGER
                .debug("AssetResource : Inside validateEGAHC() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String result = null;
        HealthCheckVO objHealthCheckVO = new HealthCheckVO();
        try {
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                objHealthCheckVO.setRoadNumber(queryParams
                        .getFirst(OMDConstants.ASSET_NUMBER));
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                objHealthCheckVO.setCustomerId(queryParams
                        .getFirst(OMDConstants.CUSTOMER_ID));
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                objHealthCheckVO.setAssetGrpName(queryParams
                        .getFirst(OMDConstants.ASSET_GROUP_NAME));
            }
            if (queryParams.containsKey(OMDConstants.TYPE_OF_USER)) {
                objHealthCheckVO.setTypeOfUser(queryParams
                        .getFirst(OMDConstants.TYPE_OF_USER));
            }
            result = objHealthChkServiceIntf.validateEGAHC(objHealthCheckVO);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        RMDLOGGER
                .debug("AssetResource : Inside validateEGAHC() method:::::End ");
        return result;
    }

    /**
     * @Author:
     * @param UriInfo
     * @return String
     * @throws RMDServiceException
     * @Description:This method validates NT HC Request.
     */
    @GET
    @Path(OMDConstants.VALIDATE_NT_HC)
    @Produces({ MediaType.APPLICATION_JSON })
    public String validateNTHC(@Context final UriInfo ui)
            throws RMDServiceException {
        RMDLOGGER
                .debug("AssetResource : Inside validateNTHC() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String result = null;
        HealthCheckVO objHealthCheckVO = new HealthCheckVO();
        try {
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                objHealthCheckVO.setRoadNumber(queryParams
                        .getFirst(OMDConstants.ASSET_NUMBER));
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                objHealthCheckVO.setCustomerId(queryParams
                        .getFirst(OMDConstants.CUSTOMER_ID));
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                objHealthCheckVO.setAssetGrpName(queryParams
                        .getFirst(OMDConstants.ASSET_GROUP_NAME));
            }
            result = objHealthChkServiceIntf.validateNTHC(objHealthCheckVO);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        RMDLOGGER
                .debug("AssetResource : Inside validateNTHC() method:::::End ");
        return result;
    }

    /**
     * This method invokes the web service to get the Latitude,Longitude and max
     * occurrence time information of the asset
     * 
     * @param AssetLocRequestType objAssetRequestType
     * @return AssetLocatorResponseType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_ASSET_CASE_COMMON_SECTION)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetLocatorResponseType> getAssetCaseCommSection(
            final AssetLocRequestType objAssetRequestType)
            throws RMDServiceException {
        RMDLOGGER
                .debug("In RMDWebservices : AssetResource : getAssetCaseCommSection():::START");
        AssetLocatorResponseType assetLocatorResponseType = null;
        final AssetLocatorBean assetLocatorBean = new AssetLocatorBean();
        List<AssetLocatorVO> assetLocatorVoLst = null;
        List<AssetLocatorResponseType> lstAssetLocatorResponse = new ArrayList<AssetLocatorResponseType>();
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar dtProcessed = null;
        XMLGregorianCalendar dtFault = null;
        try {
            assetLocatorBean.setLastFault(objAssetRequestType.isLastFault());
            String timeZone = getDefaultTimeZone();
            String strLanguage = objAssetRequestType.getLanguage();
            assetLocatorBean.setLanuage(strLanguage);
            if (validateAssetLocation(objAssetRequestType)) {
                if (null != objAssetRequestType.getAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objAssetRequestType.getAssetNumber())) {
                    assetLocatorBean.setAssetNumber(objAssetRequestType
                            .getAssetNumber());
                } else {
                    assetLocatorBean
                            .setAssetNumber(RMDCommonConstants.EMPTY_STRING);
                }
                if (null != objAssetRequestType.getCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objAssetRequestType.getCustomerId())) {
                    assetLocatorBean.setCustomerId(objAssetRequestType
                            .getCustomerId());
                } else {
                    assetLocatorBean
                            .setCustomerId(RMDCommonConstants.EMPTY_STRING);
                }
                if (null != objAssetRequestType.getAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objAssetRequestType.getAssetGrpName())) {
                    assetLocatorBean.setAssetGrpName(objAssetRequestType
                            .getAssetGrpName());
                } else {
                    assetLocatorBean
                            .setAssetGrpName(RMDCommonConstants.EMPTY_STRING);
                }
                assetLocatorVoLst = objAssetEoaServiceIntf.getAssetCaseCommSection(assetLocatorBean);
                if (null != assetLocatorVoLst && assetLocatorVoLst.size() > 0) {
                    DatatypeFactory df = DatatypeFactory.newInstance();
                    for (AssetLocatorVO assetLocatorVO : assetLocatorVoLst) {
                        assetLocatorResponseType = new AssetLocatorResponseType();
                        assetLocatorResponseType.setAssetNumber(assetLocatorVO.getAssetNumber());
                        assetLocatorResponseType.setLongitude(assetLocatorVO.getLongitude());
                        assetLocatorResponseType.setLatitude(assetLocatorVO.getLatitude());
                        if(null!=assetLocatorVO.getLstEOAFaultHeader()){
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstEOAFaultHeader().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar,timeZone);          
                            dtFault = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstEOAFaultHeader(dtFault); 
                        }
                        if(null!=assetLocatorVO.getLstPPATSMsgHeader()){
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstPPATSMsgHeader().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar,timeZone);          
                            dtFault = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstPPATSMsgHeader(dtFault); 
                        }
                        if(null!=assetLocatorVO.getLstESTPDownloadHeader()){
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstESTPDownloadHeader().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar,timeZone);          
                            dtFault = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstESTPDownloadHeader(dtFault); 
                        }
                        if(null!=assetLocatorVO.getLstFalultDateCell()){
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTimeInMillis(assetLocatorVO.getLstFalultDateCell().getTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar,timeZone);          
                            dtProcessed = df.newXMLGregorianCalendar(objGregorianCalendar);
                            assetLocatorResponseType.setLstFaultDateCell(dtProcessed);
                        }
                        assetLocatorResponseType.setServices(assetLocatorVO
								.getServices());
						assetLocatorResponseType
								.setNextScheduledRun(assetLocatorVO
										.getNextScheduledRun());
						assetLocatorResponseType
						.setLastToolRun(assetLocatorVO
								.getLastToolRun());
						assetLocatorResponseType
						.setLastRecord(assetLocatorVO
								.getLastRecord());
                        lstAssetLocatorResponse.add(assetLocatorResponseType);
                    }
                }
            }

            else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("In AssetResource : getAssetCaseCommSection():::END");
        return lstAssetLocatorResponse;
    }
    /**
     * @Author:
     * @param UriInfo
     * @return String
     * @throws RMDServiceException
     * @Description:This method filters messages based on type of user.
     */
    @GET
    @Path(OMDConstants.FILTER_RECORDS)
    @Produces({ MediaType.APPLICATION_JSON })
    public String filterRecords(@Context final UriInfo ui)
            throws RMDServiceException {
        RMDLOGGER
                .debug("AssetResource : Inside filterRecords() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        List<String> result = null;
        String messageIdList = null;
        String filterCondition = null;
        String records = null;
        StringBuilder sb = new StringBuilder();
        try {
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey(OMDConstants.MESSAGE_ID_LIST)) {
                messageIdList = queryParams
                        .getFirst(OMDConstants.MESSAGE_ID_LIST);
            }
            if (queryParams.containsKey(OMDConstants.MSG_TITLE)) {
                filterCondition = queryParams.getFirst(OMDConstants.MSG_TITLE);
            }
            result = objHealthChkServiceIntf.filterRecords(messageIdList,
                    filterCondition);
            if(result.size()>0)
            {
            for (String str : result) {
                sb.append(str).append(RMDCommonConstants.COMMMA_SEPARATOR);
            }
            sb.deleteCharAt(sb.length() - 1);
            records = sb.toString();
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        RMDLOGGER
                .debug("AssetResource : Inside filterRecords() method:::::End ");
        return records;
    }
    
    /**
     * @Author:
     * @param UriInfo
     * @return String
     * @throws RMDServiceException
     * @Description:This method is used for fetching Customer Id based on
     *                   assetNumber and assetGrpName.
     */

    @GET
    @Path(OMDConstants.GET_CUSTOMER_ID)
    @Produces({ MediaType.APPLICATION_JSON })
    public String getCustomerId(@Context final UriInfo ui)
            throws RMDServiceException {
        RMDLOGGER
                .debug("AssetResource : Inside getCustomerId() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String assetNumber = null;
        String assetGrpName = null;
        String customerId = null;
        try {
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                assetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                assetGrpName = queryParams
                        .getFirst(OMDConstants.ASSET_GROUP_NAME);
            }

            if (RMDCommonUtility.isNullOrEmpty(assetNumber)) {
                throw new OMDInValidInputException(
                        OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
            }

            if (RMDCommonUtility.isNullOrEmpty(assetGrpName)) {
                throw new OMDInValidInputException(
                        OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isAlphaNumeric(assetNumber)) {
                throw new OMDInValidInputException(
                        OMDConstants.INVALID_ASSET_NUMBER);
            }

            if (!RMDCommonUtility.isAlphaNumeric(assetGrpName)) {
                throw new OMDInValidInputException(
                        OMDConstants.INVALID_ASSET_GRP_NAME);
            }
            customerId = objAssetEoaServiceIntf.getCustomerId(assetNumber,
                    assetGrpName);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return customerId;
    }
    
    /**
     * This method is used for retrieving Asset details for the parameters
     * passed in UriInfo
     * 
     * @param ui
     * @return List of AssetHeaderSearchResponseType
     * @throws RMDServiceException
     * 
     *             Change made in method to retrive list of Model details based
     *             on Customer, Fleet, Model and Config
     */
    @POST
    @Path(OMDConstants.GET_ASSETS_HEADER)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetHeaderSearchResponseType> getAssetsData(
            final AssetsRequestType objAssetReqType) throws RMDServiceException {

        List<AssetHeaderServiceVO> eoaAssetsLst = new ArrayList<AssetHeaderServiceVO>();
        AssetServiceVO objAssetServiceVO;
        List<AssetHeaderSearchResponseType> arlAssetResponseType = new ArrayList<AssetHeaderSearchResponseType>();
        AssetHeaderSearchResponseType objAssetResponseType = null;
        AssetHeaderServiceVO objAssetServiceVO2=null;
        try {
            objAssetServiceVO = new AssetServiceVO();
            // if any one of the search values are present
            

                if (validateGetAssets(objAssetReqType)) {
                    
                    if (null != objAssetReqType.getAssetGrpName()
                            && !objAssetReqType.getAssetGrpName().isEmpty()) {
                        objAssetServiceVO.setAssetGroupName(objAssetReqType
                                .getAssetGrpName());
                    } else {
                        objAssetServiceVO
                                .setAssetGroupName(RMDCommonConstants.EMPTY_STRING);
                    }
                    
                    if (null != objAssetReqType.getModelId()
                            && !objAssetReqType.getModelId().isEmpty()) {
                        objAssetServiceVO.setStrModelId(objAssetReqType
                                .getModelId());
                    } else {
                        objAssetServiceVO.setModel(RMDCommonConstants.EMPTY_STRING);
                    }
                    
                    if (null != objAssetReqType.getFleetId()
                            && !objAssetReqType.getFleetId().isEmpty()) {
                        objAssetServiceVO.setStrFleetId(objAssetReqType
                                .getFleetId());
                    } else {
                        objAssetServiceVO.setFleet(RMDCommonConstants.EMPTY_STRING);
                    }

                    if (null != objAssetReqType.getAssetNumberLike()
                            && !objAssetReqType.getAssetNumberLike().isEmpty()) {
                        objAssetServiceVO.setAssetNumberLike(objAssetReqType
                                .getAssetNumberLike());
                    } else {
                        objAssetServiceVO
                                .setAssetNumberLike(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != objAssetReqType.getCustomerId()
                            && !objAssetReqType.getCustomerId().isEmpty()) {
                        
                        if (RMDCommonConstants.ALL_CUSTOMER.equalsIgnoreCase(objAssetReqType.getCustomerId())) {                        
                            objAssetServiceVO.setCustomerID(RMDCommonConstants.EMPTY_STRING);
                        }else{
                            objAssetServiceVO.setCustomerID(objAssetReqType
                                .getCustomerId());                          
                        }
                        
                    } else {
                        objAssetServiceVO
                                .setCustomerID(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != objAssetReqType.getAssetNumber()
                            && !objAssetReqType.getAssetNumber().isEmpty()) {
                        objAssetServiceVO.setStrAssetNumber(objAssetReqType
                                .getAssetNumber());
                    } else {
                        objAssetServiceVO
                                .setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != objAssetReqType.getAssetFrom()) {
                        objAssetServiceVO.setAssetFrom(objAssetReqType
                                .getAssetFrom());
                    }
                    if (null != objAssetReqType.getAssetTo()) {
                        objAssetServiceVO.setAssetTo(objAssetReqType
                                .getAssetTo());
                    }
                    if (null != objAssetReqType.getProdAssetMap()
                            && objAssetReqType.getProdAssetMap().size() > 0) {
                        CMBeanUtility.copyCustomerAssetToServiceVO(
                                objAssetReqType.getProdAssetMap(), objAssetServiceVO);
                    }
                    
                    
                eoaAssetsLst = objAssetEoaServiceIntf
                    .getAssetsData(objAssetServiceVO);
                if(eoaAssetsLst!=null && eoaAssetsLst.size()>0){
                    arlAssetResponseType = new ArrayList<AssetHeaderSearchResponseType>(eoaAssetsLst.size());
                    for (Iterator iterator = eoaAssetsLst.iterator(); iterator
                            .hasNext();) {
                        objAssetResponseType = new AssetHeaderSearchResponseType();
                         objAssetServiceVO2 = (AssetHeaderServiceVO) iterator
                                .next();
                         //objAssetResponseType.setAssetNumber(objAssetServiceVO2.getStrAssetNumber());
                         objAssetResponseType.setAssetNumber(EsapiUtil.stripXSSCharacters((String)
                     			(objAssetServiceVO2.getStrAssetNumber())));
                         
                         //objAssetResponseType.setAssetGroupName(objAssetServiceVO2.getAssetGroupName());
                         objAssetResponseType.setAssetGroupName(EsapiUtil.stripXSSCharacters((String)
                      			(objAssetServiceVO2.getAssetGroupName())));
                         
                         //objAssetResponseType.setCustomerID(objAssetServiceVO2.getCustomerID());
                         objAssetResponseType.setCustomerID(EsapiUtil.stripXSSCharacters((String)
                       			(objAssetServiceVO2.getCustomerID())));
                         objAssetResponseType.setVehicleObjId(EsapiUtil.stripXSSCharacters((String)
                                 (objAssetServiceVO2.getVehicleObjId())));
                         
                         arlAssetResponseType.add(objAssetResponseType);
                    }
                        
                        
                        
                }
                eoaAssetsLst=null;  
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.INVALID_VALUE);
                }
            
                 Collections.sort(arlAssetResponseType, new Comparator() {
                      public int compare(Object o1, Object o2) {
                          AssetHeaderSearchResponseType a1 =(AssetHeaderSearchResponseType) o1;
                          AssetHeaderSearchResponseType a2 =(AssetHeaderSearchResponseType) o2;
                          if(a1.getAssetNumber().length()<a2.getAssetNumber().length()){
                            return -1;
                        }else if(a1.getAssetNumber().length()>a2.getAssetNumber().length()){
                            return 1;
                        }
                        return a1.getAssetNumber().compareTo(a2.getAssetNumber());
                    }
                 });
            
        
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return arlAssetResponseType;
    }
	/**
	 * @Author:iGATE
	 * @return list of ParameterResponseType
	 * @Description: This method will return the SDP parameters and its values
	*/
	@SuppressWarnings("unchecked")
	@GET
	@Path(OMDConstants.GET_GEO_LOCATION_VALUES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ParameterResponseType> getGeoLocationValues(@Context final UriInfo ui)
			throws RMDServiceException {
		List<ParameterResponseType> lstElementResponse = null;
		List<ParameterServiceVO> lstParameterServiceVO = null;
		ParameterServiceVO pserviceVO = null;
		String strLanguage = null;
		String customer =null;
		String family =null;
		ParameterResponseType objParameterResponseType=null;
		try {
			String isGERuleAUthor = getRequestHeader(OMDConstants.IS_GE_RULE_AUTHOR);
			strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
			final MultivaluedMap<String, String> queryParams = ui
			.getQueryParameters();
			int[] paramFlag = {OMDConstants.ALPHA_NUMERIC_HYPEN};
			if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag,OMDConstants.CUSTOMER)){
				if(RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){
					customer=getRequestHeader("strUserCustomer");				
				}else if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
					customer = queryParams.getFirst(OMDConstants.CUSTOMER);
				}
			
			if (queryParams.containsKey(OMDConstants.FAMILY)) {
				family = queryParams.getFirst(OMDConstants.FAMILY);
			}
			lstParameterServiceVO=objRuleServiceIntf.getGeoLocationValues(customer,family,strLanguage);
			if(RMDCommonUtility.isCollectionNotEmpty(lstParameterServiceVO)){
				lstElementResponse=new ArrayList<ParameterResponseType>();
				for (ParameterServiceVO objParameterServiceVO : lstParameterServiceVO) {
					objParameterResponseType = new ParameterResponseType();
					BeanUtility.copyBeanProperty(objParameterServiceVO,
							objParameterResponseType);
					lstElementResponse.add(objParameterResponseType);
				}
			}
		 else {
			throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
		 }
			}else{
			throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
		}
		}  catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
		}
		return lstElementResponse;

	}
	
	
	/**
	 * @Author:iGATE
	 * @return list of ParameterResponseType
	 * @Description: This method will return the SDP parameters and its values
	*/
	@SuppressWarnings("unchecked")
	@GET
	@Path(OMDConstants.GET_ATS_PARAMETERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ParameterResponseType> getATSParameters(@Context final UriInfo ui)
			throws RMDServiceException {
		List<ParameterResponseType> lstElementResponse = null;
		List<ParameterServiceVO> lstParameterServiceVO = null;
		ParameterServiceVO pserviceVO = null;
		String strLanguage = null;
		String family =null;
		String customer=null;
		ParameterResponseType objParameterResponseType=null;		
		
		try {
			strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
			String uom=getRequestHeader("measurementSystem");
			String isGERuleAUthor = getRequestHeader(OMDConstants.IS_GE_RULE_AUTHOR);
			final MultivaluedMap<String, String> queryParams = ui
			.getQueryParameters();
			int[] paramFlag = {OMDConstants.ALPHA_NUMERIC_HYPEN};
			if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag,OMDConstants.FAMILY)){
			if (queryParams.containsKey(OMDConstants.FAMILY)) {
				family = queryParams.getFirst(OMDConstants.FAMILY);
			}
			
			if(RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){
				customer=getRequestHeader("strUserCustomer");				
			}else if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
				customer = queryParams.getFirst(OMDConstants.CUSTOMER);
			}
			lstParameterServiceVO=objRuleServiceIntf.getATSParameters(family,strLanguage,uom,customer);
			if(RMDCommonUtility.isCollectionNotEmpty(lstParameterServiceVO)){
				lstElementResponse=new ArrayList<ParameterResponseType>();
				for (ParameterServiceVO objParameterServiceVO : lstParameterServiceVO) {
					objParameterResponseType = new ParameterResponseType();
					BeanUtility.copyBeanProperty(objParameterServiceVO,
							objParameterResponseType);
					objParameterResponseType.setUomAbbr(objParameterServiceVO.getUomAbbr());
					lstElementResponse.add(objParameterResponseType);
				}
			}
		 else {
			throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
		}
			}else{
			throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
		}
		}  catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
		}
		return lstElementResponse;

	}
	
	/**
	 * @Author:iGATE
	 * @return list of ParameterResponseType
	 * @Description: This method will return the SDP parameters and its values
	*/
	@SuppressWarnings("unchecked")
	@GET
	@Path(OMDConstants.GET_COLVALUES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ParameterResponseType> getColValues(@Context final UriInfo ui)
			throws RMDServiceException {
		List<ParameterResponseType> lstElementResponse = null;

		List<ParameterServiceVO> lstParameterServiceVO = null;
		ParameterServiceVO pserviceVO = null;
		String strLanguage = null;
		String family =null;
		ParameterResponseType objParameterResponseType=null;
		try {
			strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
			final MultivaluedMap<String, String> queryParams = ui
			.getQueryParameters();
			int[] paramFlag = {OMDConstants.ALPHA_NUMERIC_HYPEN};
			if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag,OMDConstants.FAMILY)){
			if (queryParams.containsKey(OMDConstants.FAMILY)) {
				family = queryParams.getFirst(OMDConstants.FAMILY);
			}
			lstParameterServiceVO=objRuleServiceIntf.getColValues(family,strLanguage);
			if(RMDCommonUtility.isCollectionNotEmpty(lstParameterServiceVO)){
				lstElementResponse=new ArrayList<ParameterResponseType>();
				for (ParameterServiceVO objParameterServiceVO : lstParameterServiceVO) {
					objParameterResponseType = new ParameterResponseType();
					BeanUtility.copyBeanProperty(objParameterServiceVO,
							objParameterResponseType);
					lstElementResponse.add(objParameterResponseType);
				}
			}
		 else {
			throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
		}
			}else{
			throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
		}
		}  catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
		}
		return lstElementResponse;

	}
	
	/**
	 * This method is used to submit healthCheck Request for EGA platform
	 * 
	 * 
	 * @param healthCheckSubmitRequest
	 * @return String
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.SUBMIT_HC_MOBILE_REQUEST)
	@Consumes(MediaType.APPLICATION_XML)
	public HealthcheckSubmitResponseType submitHealthCheckMobileRequest(
			@Context final UriInfo ui) throws Exception {
		RMDLOGGER
				.debug("AssetResource : Inside submitHealthCheckMobileRequest() method:::::START ");
		javax.ws.rs.core.MultivaluedMap<String, String> queryParams = null;
		HealthCheckSubmitRequest objHealthCheckSubmitRequest = new HealthCheckSubmitRequest();
		SendMultiDeviceMessageResponse sendMultiDeviceMessageResponse = null;
		HealthCheckVO objHealthCheckVO = new HealthCheckVO();
		Map<String, String> samplesMap = new HashMap<String, String>();
		String device, sampleRate = null, postSamples = null, strCustomerId = null, strAssetId = null, strCustomerName = null, strLanguage = null, assetType, typeOfUser = "", requestType = "", result, userId = "", mpNumber = "", mpGroupName = "", userType = "", internalFlag = "";
		String ntHcResponse = "",flag="",check=OMDConstants.YES;
		HealthCheckResponseType hcResType = null;
		List<HealthCheckResponseType> hcResList = null;
		HealthcheckSubmitResponseType objHealthcheckSubmitResponseType = new HealthcheckSubmitResponseType();
		try {
			if (getRequestHeader(OMDConstants.LANGUAGE) != null)
				strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
			if (null != ui.getQueryParameters()) {
				queryParams = ui.getQueryParameters();
			}
			if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
				strAssetId = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
				objHealthCheckSubmitRequest.setRoadNumber(Long
						.parseLong(strAssetId));
				objHealthCheckVO.setRoadNumber(strAssetId);
			}
			if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
				strCustomerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
				objHealthCheckSubmitRequest.setCustomerID(strCustomerId);
				objHealthCheckVO.setCustomerId(strCustomerId);
			}
			if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
				strCustomerName = queryParams
						.getFirst(OMDConstants.ASSET_GROUP_NAME);
				objHealthCheckSubmitRequest.setRoadIntial(strCustomerName);
				objHealthCheckVO.setAssetGrpName(strCustomerName);
			}
			if (queryParams.containsKey(OMDConstants.USER_ID)) {
				userId = queryParams.getFirst(OMDConstants.USER_ID);
				objHealthCheckSubmitRequest.setUserId(userId);
			}
			
			if (queryParams.containsKey(OMDConstants.USER_TYPE)) {
				userType = queryParams.getFirst(OMDConstants.USER_TYPE);
				
				if (userType != null ) {
					if (OMDConstants.USER_TYPE_INTERNAL.equals(userType)) {
						requestType = "internalHC";
						typeOfUser = OMDConstants.STR_YES;
						internalFlag = RMDCommonConstants.STR_TRUE;
					} else if (OMDConstants.USER_TYPE_EXTERNAL.equals(userType)) {
						requestType = "externalHC";
						typeOfUser = OMDConstants.STR_NO;
						internalFlag = RMDCommonConstants.STR_FALSE;
					}
				}

			}
			
			objHealthCheckVO.setTypeOfUser(typeOfUser);
			objHealthCheckSubmitRequest.setTypeOfUser(internalFlag);
			ntHcResponse = validateNTHCMobileRequest(objHealthCheckVO);
			if (null != ntHcResponse
					&& ntHcResponse.equalsIgnoreCase(OMDConstants.NUMBER)) {
				objHealthcheckSubmitResponseType.setStatus("Error");
				objHealthcheckSubmitResponseType
						.setMessage("NT Health Check is not supported");
				check=OMDConstants.STR_NO;
			} else {
				device = getDeviceInfoHC(objHealthCheckVO);
				if (device==null || device=="null" || device=="failure") {
					objHealthcheckSubmitResponseType.setStatus("Error");
					objHealthcheckSubmitResponseType
							.setMessage("Invalid configuration, please contact system administrator.");
					check=OMDConstants.STR_NO;
				} 
				else if(device.equalsIgnoreCase(RMDCommonConstants.PLATFORM_EGA))
				{
					String egaHCValidationResponse = validateEGAHCMobileRequest(objHealthCheckVO);
					if (null != egaHCValidationResponse
							&& egaHCValidationResponse
									.equalsIgnoreCase(OMDConstants.SUCCESS)) {
						check=OMDConstants.YES;
					} else {
						objHealthcheckSubmitResponseType.setStatus("Error");
						objHealthcheckSubmitResponseType
								.setMessage(egaHCValidationResponse);
						check=OMDConstants.STR_NO;
					}
				}
				  if(check.equalsIgnoreCase(OMDConstants.YES)){
					assetType = getAssetType(strCustomerId, strAssetId,
							strCustomerName, strLanguage);
					objHealthCheckSubmitRequest.setAssetType(assetType);
					if (null != assetType
							&& assetType
									.equalsIgnoreCase(OMDConstants.ASSETTYPE_LEGACY)
							&& typeOfUser.equalsIgnoreCase(OMDConstants.STR_NO)) {
						mpNumber = "";
						flag=OMDConstants.YES;
					} else {
						hcResList = getAssetMPGroups(strCustomerId, strAssetId,
								strCustomerName, strLanguage, requestType,
								assetType, device);
						if (RMDCommonUtility.isCollectionNotEmpty(hcResList)) {
							for (HealthCheckResponseType obj : hcResList) {
								mpNumber = obj.getMpNum();
								mpGroupName = obj.getMpName();
							}
							objHealthCheckSubmitRequest.setMpGroupId(Long
									.parseLong(mpNumber));
							objHealthCheckSubmitRequest
									.setMpGroupName(mpGroupName);
							
							flag=OMDConstants.YES;
						} else {
							if (typeOfUser
									.equalsIgnoreCase(OMDConstants.STR_NO)) {
								objHealthcheckSubmitResponseType
										.setStatus("Error");
								objHealthcheckSubmitResponseType
										.setMessage("Invalid configuration, please contact system administrator.");
								
							} else if (typeOfUser
									.equalsIgnoreCase(OMDConstants.STR_YES)
									&& !device
											.equalsIgnoreCase(OMDConstants.DEVICE_EGA)
									&& assetType
											.equalsIgnoreCase(OMDConstants.ASSETTYPE_LEGACY)) {
								objHealthcheckSubmitResponseType
										.setStatus("Error");
								objHealthcheckSubmitResponseType
										.setMessage("MP Groups could not be retrieved for this asset due to an invalid configuration or no available active templates");
								
							}
						}
					}
					if(flag.equalsIgnoreCase(OMDConstants.YES)){
						hcResType = getSendMsgAttributes(strCustomerId, strAssetId,
								strCustomerName, strLanguage, mpNumber, assetType,
								internalFlag, device);
						objHealthCheckSubmitRequest.setMpNumber(hcResType.getMpReqNum());
						samplesMap = getHCLookup();
						if (null != samplesMap) {
							if (samplesMap.containsKey(OMDConstants.NO_OF_SAMPLES)) {
								postSamples = samplesMap
										.get(OMDConstants.NO_OF_SAMPLES);
							}
							if (samplesMap.containsKey(OMDConstants.SAMPLE_RATE)) {
								sampleRate = samplesMap
										.get(OMDConstants.SAMPLE_RATE);
							}

							objHealthCheckSubmitRequest.setSampleRate(Long
									.parseLong(sampleRate));
							objHealthCheckSubmitRequest.setPostSamples(Long
									.parseLong(postSamples));
						}
						if (!device.equalsIgnoreCase(OMDConstants.DEVICE_EGA)) {
							SendMultiDeviceMessageServiceInterface serviceIntf = new SendMultiDeviceMessageServiceInterface(
									new URL(hcSendMessageServiceURL),
									new QName(
											"http://www.getransporatation.com/railsolutions/mcs",
											"SendMultiDeviceMessageServiceInterface"));
							SendMultiDeviceMessageService service = serviceIntf
									.getSendMultiDeviceMessageServiceImplPort();
							BindingProvider bp = (BindingProvider) service;
							bp.getRequestContext().put(
									BindingProvider.USERNAME_PROPERTY,
									assetServiceUsername);
							bp.getRequestContext().put(
									BindingProvider.PASSWORD_PROPERTY,
									assetServicePassword);
							RMDLOGGER.info(service);
							SendMultiDeviceMessageRequest request = new SendMultiDeviceMessageRequest();
							request.setApplicationId(RMDCommonConstants.MCS_APPLICATION_ID);
							request.setMessageId(RMDCommonConstants.MCS_MESSAGE_ID);
							request.setAssetOwnerId(strCustomerId);
							request.setRoadInitial(hcResType.getVehicleHDRNum());
							request.setRoadNumber(strAssetId);
							request.setRequestorName(userId);
							request.setDevice(device);
							request.getAttribute().addAll(
									PopulateListWsAttribute(sampleRate,
											postSamples, hcResType));
							sendMultiDeviceMessageResponse = service
									.sendMultiDeviceMessage(request);
							if (null != String
									.valueOf(sendMultiDeviceMessageResponse
											.getMessageOutId().intValue())
									&& sendMultiDeviceMessageResponse
											.getMessageOutId().intValue() > 0) {
								objHealthCheckSubmitRequest.setMsgId(String
										.valueOf(sendMultiDeviceMessageResponse
												.getMessageOutId().intValue()));
								result = saveHCDetails(objHealthCheckSubmitRequest);
								if (result
										.equalsIgnoreCase(RMDCommonConstants.SUCCESS)) {
									objHealthcheckSubmitResponseType
											.setStatus("Success");
									objHealthcheckSubmitResponseType
											.setMessage(String
													.valueOf(sendMultiDeviceMessageResponse
															.getMessageOutId()
															.intValue()));
								}
							}
						} else {
							objHealthCheckSubmitRequest
									.setPlatform(OMDConstants.DEVICE_EGA);
							objHealthCheckSubmitRequest
									.setType(OMDConstants.HC_REQ_TYPE_MOBILITY);
								String egaRequestId = submitHealthCheckRequest(objHealthCheckSubmitRequest);
								objHealthcheckSubmitResponseType
										.setStatus("Success");
								objHealthcheckSubmitResponseType
										.setMessage(egaRequestId);
							} 
				}
					
						}
					
			}
		} catch (Fault fault) {
			String errorCode = RMDCommonUtility.getErrorCode("MCS_FAULT_"
					+ fault.getFaultInfo().getFaultCode());
			RMDLOGGER.info("error code submit health check " + errorCode);
			if (errorCode != null) {
				RMDLOGGER.info("error message submit health check "
						+ RMDCommonUtility.getMessage(errorCode,
								new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE));
				objHealthcheckSubmitResponseType.setStatus("Error");
				objHealthcheckSubmitResponseType
						.setMessage("Invalid Input Parameter");

			} else {
				objHealthcheckSubmitResponseType.setStatus("Error");
				objHealthcheckSubmitResponseType.setMessage(fault
						.getFaultInfo().getFaultMessage());
			}
		} catch (Exception ex) {
			RMDLOGGER
					.error("Exception occured in submitHealthCheckRequest method ",
							ex);
			String errorCode = ((OMDApplicationException) ex).getErrorCode();
			ex.printStackTrace();
			if (ex.getClass().getName().indexOf("WebServiceException") > -1) {
				RMDLOGGER.info("Got WSException from MCS :: ");
				RMDWebServiceErrorHandler.handleException(ex,
						omdResourceMessagesIntf);
			} else if (ex.getClass().getName().indexOf("WSException") > -1) {
				RMDLOGGER.info("Got WSException from MCS :: ");
				RMDWebServiceErrorHandler.handleException(ex,
						omdResourceMessagesIntf);
			} else if (errorCode.endsWith(OMDConstants.EXCEPTION_EOA_3032)) {
				objHealthcheckSubmitResponseType.setStatus("Error");
				objHealthcheckSubmitResponseType
						.setMessage("Request of the same type is already pending.");
			} else {
				/*
				 * objHealthcheckSubmitResponseType.setStatus("Error");
				 * objHealthcheckSubmitResponseType .setMessage(
				 * "Invalid configuration, please contact system administrator."
				 * );
				 */
				RMDWebServiceErrorHandler.handleException(ex,
						omdResourceMessagesIntf);
			}
		}
		return objHealthcheckSubmitResponseType;
	}

	public String getDeviceInfoHC(HealthCheckVO objHealthCheckVO) 
	{
		RMDLOGGER.debug("AssetResource : Inside getDeviceInfo() method:::::START ");
		String device=RMDCommonConstants.FAILURE;
		try {
			if (null != objHealthCheckVO) {
				if(null !=objHealthCheckVO.getRoadNumber() && null !=objHealthCheckVO.getCustomerId() && null !=objHealthCheckVO.getAssetGrpName() && null !=objHealthCheckVO.getTypeOfUser())
				{
					device=objHealthChkServiceIntf.getDeviceInfo(objHealthCheckVO);
				}
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("AssetResource : Inside getDeviceInfoHC() method:::::End ");
		return device;
	}
	
	public String getAssetType( String strCustomer,String strAssetNumber,String strAssetGroupName,String strLanguage) {
		RMDLOGGER
				.debug("AssetResource : Inside getAssetType() method:::::START ");
		String assetType = null;
		try {
			if(null !=strCustomer && null != strAssetNumber && null !=strAssetGroupName  && null !=strLanguage)
				assetType = objHealthChkServiceIntf.getHCAssetType(strCustomer,
						strAssetNumber, strAssetGroupName, strLanguage);
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("AssetResource : Inside getAssetType() method:::::End ");
		return assetType;
	}
	
	public List<HealthCheckResponseType> getAssetMPGroups(String strCustomer,String strAssetNumber,String strAssetGroupName,String strLanguage,String requestType,String assetType,String deviceName
			) {
		RMDLOGGER
				.debug("AssetResource : Inside getAssetMPGroups() method:::::START ");
		List<ElementVO> lstElementVO = null;
		ElementVO elementVO = null;
		List<HealthCheckResponseType> lstHcResponse = null;
		Iterator iterMpGroup;
		try {
		if(null !=strCustomer && null !=strAssetNumber && null !=strAssetGroupName && null !=strLanguage && null !=requestType && null !=assetType && null !=deviceName)
		{
				lstElementVO = objHealthChkServiceIntf.getAssetHCMPGroups(
						strCustomer, strAssetNumber, strAssetGroupName,
						strLanguage,requestType,assetType,deviceName);
				if (RMDCommonUtility.isCollectionNotEmpty(lstElementVO)) {
					lstHcResponse = new ArrayList();
					iterMpGroup = lstElementVO.iterator();
					HealthCheckResponseType objHCResponseType;
					while (iterMpGroup.hasNext()) {
						elementVO = (ElementVO) iterMpGroup.next();
						objHCResponseType = new HealthCheckResponseType();
						objHCResponseType.setMpNum(elementVO.getId());
						objHCResponseType.setMpName(elementVO.getName());
						lstHcResponse.add(objHCResponseType);
					}
				}

			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}

		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("AssetResource : Inside getAssetMPGroups() method:::::End ");
		return lstHcResponse;
	}
	
	public Map<String,String> getHCLookup() {
		RMDLOGGER
				.debug("AssetResource : Inside getHCLookup() method:::::START ");
	Map<String, String> result = new LinkedHashMap<String, String>();
	String noOfPostSamples = null, sampleRate = null;
	String listName = OMDConstants.EMPTY_STRING;
	final GetSysLookupVO sysLookupVO = new GetSysLookupVO();
	List<GetSysLookupVO> lstSysLookupVO = null;
		try {
			listName = OMDConstants.HEALH_CHECK_NO_OF_SAMPLES_LOOKUP;
			sysLookupVO.setListName(listName);
			lstSysLookupVO=popupListAdminBOIntf
					.getPopupListValues(sysLookupVO);
			if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {
				noOfPostSamples=lstSysLookupVO.get(0).getLookValue();
			}
			result.put(OMDConstants.NO_OF_SAMPLES, noOfPostSamples);
			listName = OMDConstants.HEALH_CHECK_SAMPLE_RATE_LOOKUP;
			sysLookupVO.setListName(listName);
			lstSysLookupVO =popupListAdminBOIntf
					.getPopupListValues(sysLookupVO);
			if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {
				sampleRate=lstSysLookupVO.get(0).getLookValue();
			}
			result.put(OMDConstants.SAMPLE_RATE, sampleRate);
		
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("AssetResource : Inside getHCLookup() method:::::End ");
		return result;
	}
	
	public HealthCheckResponseType getSendMsgAttributes(String strCustomer,String strAssetNumber,String strAssetGroupName,
			String strLanguage,String strInput,String strAssetType,String typeOfUser,String device) {
		RMDLOGGER
				.debug("AssetResource : Inside getSendMsgAttributes() method:::::START ");
		HealthCheckAttributeVO hcAttrVO = null;
		HealthCheckResponseType hcRespType = new HealthCheckResponseType();
		Map<String,String> mapCustRnh = new HashMap<String, String>();
		String vehObjId;
		try {
			if (null != strCustomer && null !=strAssetNumber && null !=strAssetGroupName
			&& null !=strLanguage && null !=strInput && null !=strAssetType && null !=typeOfUser && null !=device) {
			if (!RMDCommonUtility.isSpecialCharactersFound(strInput)) {
			    mapCustRnh = objHealthChkServiceIntf.getCustrnhDetails(strCustomer, strAssetNumber, strAssetGroupName, strLanguage);
			    if(mapCustRnh!=null){		
					for (Map.Entry<String, String> entry : mapCustRnh.entrySet()) {
						hcRespType.setVehObjId(entry.getKey());
						hcRespType.setVehicleHDRNum(entry.getValue());
					}					
				}
			    vehObjId = hcRespType.getVehObjId();
 				hcAttrVO = objHealthChkServiceIntf.getSendMessageAttributes(strInput,
						strAssetType,strLanguage,typeOfUser,vehObjId,device);				
				if(hcAttrVO!=null){					
					hcRespType.setDestType(hcAttrVO.getDestType());
					hcRespType.setSigned(hcAttrVO.getSigned());
					hcRespType.setNoOfBytes(hcAttrVO.getNoOfBytes());
					hcRespType.setSourceApp(hcAttrVO.getSourceApp());
					hcRespType.setMpRateNum(hcAttrVO.getmPRateNum());
					hcRespType.setMpReqNum(hcAttrVO.getRequestParamNum());					
				}
			}
		}
	}catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("AssetResource : Inside getSendMsgAttributes() method:::::End ");
		return hcRespType;
	}	
	
	public static List<SendMultiDeviceMessageRequest.Attribute> PopulateListWsAttribute(
			String strSampleRate, String noOfPostSamples,
			HealthCheckResponseType hcResType) {
		SendMultiDeviceMessageRequest.Attribute attribute = null;
		List<SendMultiDeviceMessageRequest.Attribute> lstAttribute = new ArrayList<SendMultiDeviceMessageRequest.Attribute>();
		attribute = new SendMultiDeviceMessageRequest.Attribute();
		attribute.setName("numberOfPostSamples");
		attribute.setValue(noOfPostSamples);
		lstAttribute.add(attribute);
		attribute = new SendMultiDeviceMessageRequest.Attribute();
		attribute.setName("rateOfSamples");
		attribute.setValue(strSampleRate);
		lstAttribute.add(attribute);

		attribute = new SendMultiDeviceMessageRequest.Attribute();
		attribute.setName("mpNumbers");
		attribute.setValue(hcResType.getMpReqNum());
		lstAttribute.add(attribute);

		attribute = new SendMultiDeviceMessageRequest.Attribute();
		attribute.setName("sourceApps");
		attribute.setValue(hcResType.getSourceApp());
		lstAttribute.add(attribute);

		attribute = new SendMultiDeviceMessageRequest.Attribute();
		attribute.setName("noOfBytes");
		attribute.setValue(hcResType.getNoOfBytes());
		lstAttribute.add(attribute);

		attribute = new SendMultiDeviceMessageRequest.Attribute();
		attribute.setName("signed");
		attribute.setValue(hcResType.getSigned());
		lstAttribute.add(attribute);

		attribute = new SendMultiDeviceMessageRequest.Attribute();
		attribute.setName("dataType");
		attribute.setValue(hcResType.getDestType());
		lstAttribute.add(attribute);

		attribute = new SendMultiDeviceMessageRequest.Attribute();
		attribute.setName("mpRateEnum");
		attribute.setValue(hcResType.getMpRateNum());
		lstAttribute.add(attribute);

		return lstAttribute;

	}
	/**
	 * @Author:
	 * @param UriInfo
	 * @return String
	 * @throws RMDServiceException
	 * @Description:This method is used for fetching Customer Id based on
	 *                   assetNumber and assetGrpName.
	 */

	@GET
	@Path(OMDConstants.GET_MODEL_BASED_FILTER)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<AssetNumberResponseType> getModelByFilter(@Context UriInfo ui)
			throws RMDServiceException {
		RMDLOGGER
				.debug("AssetResource : Inside getCustomerId() method:::::START ");
		String strModel = RMDCommonConstants.EMPTY_STRING;
		AssetHeaderServiceVO filterVo=new AssetHeaderServiceVO();
		List<AssetNumberResponseType> resultList=null;
		List<AssetNumberVO> valueList = new ArrayList<AssetNumberVO>();
		AssetNumberResponseType assetNumberResponseType=null;
		String[] strModelArray;
		final MultivaluedMap<String, String> queryParams = ui
				.getQueryParameters();
		try {
			if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
				filterVo.setCustomerID(queryParams.getFirst(OMDConstants.CUSTOMER));
			}
			if (queryParams.containsKey(OMDConstants.FLEET)) {
				filterVo.setFleet(queryParams.getFirst(OMDConstants.FLEET));
			}
			if (queryParams.containsKey(OMDConstants.RNH)) {
				filterVo.setAssetGroupName(queryParams.getFirst(OMDConstants.RNH));
			}
			if (queryParams.containsKey(OMDConstants.ASSET_LIST)) {
				 strModel = queryParams.getFirst(OMDConstants.ASSET_LIST);
                 if (null != strModel
                         && !RMDCommonConstants.EMPTY_STRING
                                 .equals(strModel)) {
                     strModelArray = RMDCommonUtility.splitString(strModel,
                             RMDCommonConstants.COMMMA_SEPARATOR);
                     filterVo.setAssetList(strModelArray);
                 }
			}
			valueList = objAssetEoaServiceIntf.getModelByFilter(filterVo);
			if (valueList != null && !valueList.isEmpty()) {
				resultList = new ArrayList<AssetNumberResponseType>(valueList.size());
			
			for (AssetNumberVO objAssetNumberVO : valueList) {
				assetNumberResponseType = new AssetNumberResponseType();
				assetNumberResponseType.setModel(objAssetNumberVO.getModel());
				assetNumberResponseType.setModelGeneral(objAssetNumberVO.getModelGeneral());
				resultList.add(assetNumberResponseType);
			}
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		finally
		{
			valueList = null;
		}
		return resultList;
	}
	
	 /**
     * @Author:
     * @param UriInfo
     * @return String
     * @throws RMDServiceException
     * @Description:This method validates EGA HC Request.
     */
    
    private String validateEGAHCMobileRequest(HealthCheckVO objHealthCheckVO )
            throws RMDServiceException {
        RMDLOGGER
                .debug("AssetResource : Inside validateEGAHC() method:::::START ");
        String result = null;
        try {
            if (null !=objHealthCheckVO.getRoadNumber() && null !=objHealthCheckVO.getCustomerId() && null !=objHealthCheckVO.getAssetGrpName()
            	&& null!=objHealthCheckVO.getTypeOfUser())
            {
                result = objHealthChkServiceIntf.validateEGAHC(objHealthCheckVO);

            } 
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        RMDLOGGER
                .debug("AssetResource : Inside validateEGAHC() method:::::End ");
        return result;
    }
    
    private String validateNTHCMobileRequest(HealthCheckVO objHealthCheckVO)
            throws RMDServiceException {
        RMDLOGGER
                .debug("AssetResource : Inside validateNTHC() method:::::START ");
        String result = null;
        try {
            if (null !=objHealthCheckVO.getRoadNumber() && null !=objHealthCheckVO.getCustomerId() && null !=objHealthCheckVO.getAssetGrpName())
            result = objHealthChkServiceIntf.validateNTHC(objHealthCheckVO);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        RMDLOGGER
                .debug("AssetResource : Inside validateNTHC() method:::::End ");
        return result;
    }
    
    /**
     * @param @Context UriInfo ui
     * @return List<AssetLocationDetail>
     * @throws RMDServiceException
     * @Description This method is used to get the Assets location from shop.
     */
    @POST
    @Path(OMDConstants.GET_ASSETS_LOCATION_FROM_SHOP)
    @Produces({ MediaType.APPLICATION_JSON })
    public AssetLocationResponse getAssetsLocationFromShop(AssetLocationRequest assetLocationRequest) throws RMDServiceException {
          
    	AssetLocationResponse assetLocationResponse = new AssetLocationResponse();
    	List<AssetLocationDetailVO> assetsLocationDetail = new ArrayList<AssetLocationDetailVO>();
    	AssetsLocationFromShopVO objAssetsLocationFromShopVO = null;
    	AssetVO objAssetVO =null;
    	AssetLocationDetail assetdetails=null;
    	List<AssetVO> assetVOs = null;
    	
            try {
            	objAssetsLocationFromShopVO = new AssetsLocationFromShopVO();
            	objAssetsLocationFromShopVO.setEServicesOrgId(assetLocationRequest.getEServicesOrgId());
            	assetVOs = new ArrayList<AssetVO>(assetLocationRequest.getAssets().size());
            	for (Asset objAsset : assetLocationRequest.getAssets()) {
            		objAssetVO = new AssetVO();
            		objAssetVO.setRnh(objAsset.getRoadNumberHeader());
            		objAssetVO.setRoadNumber(objAsset.getRoadNumber());
            		assetVOs.add(objAssetVO);
            	}
            	objAssetsLocationFromShopVO.setAssets(assetVOs);
            	
            	assetsLocationDetail =  objAssetEoaServiceIntf.getAssetsLocation(objAssetsLocationFromShopVO);
            	
            	for (AssetLocationDetailVO assetDetailList : assetsLocationDetail) {
            		if(assetDetailList != null){
            		assetdetails = new AssetLocationDetail();
            		assetdetails.setRoadNumberHeader(assetDetailList.getRnh());
            		assetdetails.setRoadNumber(assetDetailList.getRoadNumber());
            		assetdetails.setLatitude(assetDetailList.getLatitude());
            		assetdetails.setLongitude(assetDetailList.getLongitude());
            		assetdetails.setLatlongTime(assetDetailList.getLatlongTime());
            		assetdetails.setGpsSystem(assetDetailList.getGpsSystem());
            		assetdetails.setDistanceToShop(assetDetailList.getDistanceToShop());
            		assetdetails.setDirectionFromShop(assetDetailList.getDirectionFromShop());
            		
            		
            		assetLocationResponse.getAssetLocationDetails().add(assetdetails);
            		}
            	}

            	
            } catch (Exception e) {
            	 RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
            }

        return assetLocationResponse;
    }
    
    /**
     * @Author :
     * @return :String
     * @param :UnitRenumberingRequestType
     * @throws :RMDWebException
     * @Description: This method is used for asset renumbering
     * 
     */
    @POST
    @Path(OMDConstants.UPDATE_UNIT_NUMBER )
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String updateUnitNumber(final UnitRenumberingRequestType objUnitRenumberingRequest) throws RMDServiceException
    {
    	String  status=null;
    	try{
    		status = null;
    		if(null != objUnitRenumberingRequest) {
    			boolean oltpStatus = unitRenumberServiceIntf.updateUnitNumber(objUnitRenumberingRequest.getOldUnitNumber(), objUnitRenumberingRequest.getNewUnitNumber(), objUnitRenumberingRequest.getVehicleHeader(), objUnitRenumberingRequest.getCustomerId());
    			boolean dwStatus = unitRenumberServiceIntf.updateUnitNumberDW(objUnitRenumberingRequest.getOldUnitNumber(), objUnitRenumberingRequest.getNewUnitNumber(), objUnitRenumberingRequest.getVehicleHeader(), objUnitRenumberingRequest.getCustomerId());
    			if(oltpStatus && dwStatus) {
    				status = "Update successful";
    			} else if(oltpStatus) {
    				status = "Partial success";
    			}
    		} else{
                throw new OMDInValidInputException(
                        OMDConstants.GETTING_NULL_REQUEST_OBJECT);      
            }
    		return status;
    	} catch(Exception e)
        {
            LOG.error("ERROR IN UPDATE UNIT NUMBER IN ASSETRESOURCE:"+e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }
    
    /**
     * @Author :
     * @return :String
     * @param :UnitHeaderUpdateRequestType
     * @throws :RMDWebException
     * @Description: This method is used for asset header change
     * 
     */
    @POST
    @Path(OMDConstants.UPDATE_UNIT_HEADER )
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String updateUnitHeader(final UnitHeaderUpdateRequestType objUnitHeaderUpdateRequestType) throws RMDServiceException
    {
    	String  status=null;
    	try{
    		status = null;
    		if(null != objUnitHeaderUpdateRequestType) {
    			UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO = new UnitRoadHeaderUpdateVO();
    			objUnitRoadHeaderUpdateVO.setCustomer(objUnitHeaderUpdateRequestType.getCustomerId());
    			objUnitRoadHeaderUpdateVO.setCurrentRoadHeader(objUnitHeaderUpdateRequestType.getCurrentVehicleHeader());
    			objUnitRoadHeaderUpdateVO.setNewRoadHeader(objUnitHeaderUpdateRequestType.getNewVehicleHeader());
    			objUnitRoadHeaderUpdateVO.setVehicleNumbers(objUnitHeaderUpdateRequestType.getVehicleNumber());
    			boolean oltpStatus = unitRenumberServiceIntf.updateUnitHeader(objUnitRoadHeaderUpdateVO);
    			boolean dwStatus = unitRenumberServiceIntf.updateUnitHeaderDW(objUnitRoadHeaderUpdateVO);
    			if(oltpStatus && dwStatus) {
    				status = "Update successful";
    			} else if(oltpStatus) {
    				status = "Partial success";
    			}
    		} else{
                throw new OMDInValidInputException(
                        OMDConstants.GETTING_NULL_REQUEST_OBJECT);      
            }
    		return status;
    	} catch(Exception e)
        {
            LOG.error("ERROR IN UPDATE UNIT NUMBER IN ASSETRESOURCE:"+e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    } 
      
    /**
     * @Author :
     * @return :AssetDeliveredCasesResponseType
     * @param :CasesRequestType
     * @throws :RMDServiceException
     * @Description: This method is Combine Web-services for Mobility of DeliveredCases and AssetLocation
    */	
	@POST
	@Path(OMDConstants.GET_ASSET_DELIVERED_CASES)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public AssetDeliveredCasesResponseType getDeliveredCasesAndLocation(final CasesRequestType casesRequestType)
			throws RMDServiceException, Exception {
		RMDLOGGER.debug("In RMDWebservices : AssetResource : getDeliveredCasesAndLocation():::START");
		
		AssetDeliveredCasesResponseType assetDeliveredCasesResponseType = new AssetDeliveredCasesResponseType();
		try {
			AssetLocRequestType assetLocRequestType = new AssetLocRequestType();
			assetLocRequestType.setLanguage(casesRequestType.getLanguage());
			assetLocRequestType.setCustomerId(casesRequestType.getCustomerId());
			assetLocRequestType.setAssetNumber(casesRequestType.getAssetNumber());
			assetLocRequestType.setAssetGrpName(casesRequestType.getAssetGrName());
			assetLocRequestType.setProdAssetMap(casesRequestType.getProdAssetMap());
			assetLocRequestType.setModelId(casesRequestType.getModelId());
			assetLocRequestType.setFleetId(casesRequestType.getFleetId());			
			assetLocRequestType.setLastFault(casesRequestType.isLastFault());
			
			assetDeliveredCasesResponseType.setAssetLocatorResponseType(getAssetLocation(assetLocRequestType));			
			assetDeliveredCasesResponseType.setCaseResponseType(caseLiteResource.getDeliveredCases(casesRequestType));			
			
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}

		RMDLOGGER.debug("In AssetResource : getDeliveredCasesAndLocation():::END");

		return assetDeliveredCasesResponseType;
	}	
	
	@POST
	@Path(OMDConstants.GET_SOLUTIONS_PARAMETERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ParameterResponseType> getSolutionParameters(final SolutionParameterRequest solutionParameterRequest)
			throws RMDServiceException {

		List<ParameterResponseType> lstElementResponse = new ArrayList<ParameterResponseType>();
		List<ParameterServiceVO> lstParameterServiceVO = null;

		try {

			if (null != solutionParameterRequest.getSolutionModels()) {

				lstParameterServiceVO = objRuleServiceIntf
						.getSolutionParameters(solutionParameterRequest
								.getSolutionModels());

				if (RMDCommonUtility
						.isCollectionNotEmpty(lstParameterServiceVO)) {
					ParameterResponseType objParameterResponseType;
					for (ParameterServiceVO objParameterServiceVO : lstParameterServiceVO) {
						objParameterResponseType = new ParameterResponseType();
						objParameterResponseType
								.setColumnType(objParameterServiceVO
										.getColumnType());
						objParameterResponseType
								.setDisplayName(objParameterServiceVO
										.getDisplayName());
						objParameterResponseType
								.setParameterName(objParameterServiceVO
										.getParameterName());
						objParameterResponseType
								.setParameterNameID(objParameterServiceVO
										.getParameterNameID());
						objParameterResponseType
								.setVirtualId(objParameterServiceVO
										.getVirtualId());

						lstElementResponse.add(objParameterResponseType);
					}
				}
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return lstElementResponse;
	}
	
	@GET
	@Path(OMDConstants.SOFTWARE_HISTORY_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<SotwareHistoryResponceType> getsoftwareHistoryDetails(@Context final UriInfo uriParam) throws RMDServiceException {
		
		RMDLOGGER.debug("In RMDWebservices : AssetResource : getsoftwareHistoryDetails():::START");
		MultivaluedMap<String, String> queryParams = null;
		//SotwareHistoryInfoResponceType historyInfoResponceType = null;
		List<SoftwareHistoryVO> list;
		List<SoftwareHistoryVO> softwareListResponse;
		List<SotwareHistoryResponceType> sotwareHistoryResponceTypeList = new ArrayList<SotwareHistoryResponceType>();
		try {
		queryParams = uriParam.getQueryParameters();			
		if (!queryParams.isEmpty()) {	
		String 	customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
		String	assetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
		//if(null != softwareHistRequest) {
			SoftwareHistoryRequestVO softwareHistoryRequestVO=  new SoftwareHistoryRequestVO();
			//softwareHistoryRequestVO.setAssetNumber(softwareHistRequest.getAssetNumber());
			//softwareHistoryRequestVO.setCustomerId(softwareHistRequest.getCustomerId());
			softwareHistoryRequestVO.setAssetNumber(assetNumber);
			softwareHistoryRequestVO.setCustomerId(customerId);
			 softwareListResponse=softwareHistoryServiceIntf.getSoftwareHistory(softwareHistoryRequestVO);	
			 if(softwareListResponse != null && !softwareListResponse.isEmpty()){
				 for(SoftwareHistoryVO historyVO: softwareListResponse){
				 SotwareHistoryResponceType historyResponceType = new SotwareHistoryResponceType();
				 historyResponceType.setActive(historyVO.getActive().toString());
					historyResponceType.setConfigurationFile(historyVO.getConfigurationFile());
					historyResponceType.setDataSoftwarewasReported(historyVO.getDataSoftwarewasReported());
					historyResponceType.setDataSyncHdr(historyVO.getDataSyncHdr());
					historyResponceType.setDataSyncRd(historyVO.getDataSyncRd());
					historyResponceType.setDeviceReporting(historyVO.getDeviceReporting());
					historyResponceType.setSdisVersion(historyVO.getSdisVersion());
					historyResponceType.setUpdatedBy(historyVO.getUpdatedBy());
					sotwareHistoryResponceTypeList.add(historyResponceType);
				 }
				 
			 }
			 RMDLOGGER.debug("In RMDWebservices : AssetResource : getsoftwareHistoryDetails():::END"+ sotwareHistoryResponceTypeList);
		}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
	//	}
		return sotwareHistoryResponceTypeList;
		
	}
	@GET
	@Path(OMDConstants.GET_LOCO_ID)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getLocoId(@Context UriInfo ui)
			throws RMDServiceException {
			AssetServiceVO objAssetServiceVO;
			String locoId = null;
			LOG.debug("*****getLocoId WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			   final MultivaluedMap<String, String> queryParams = ui
	                    .getQueryParameters();
	            String assetGroupName = OMDConstants.EMPTY_STRING;
	            String assetNumber = OMDConstants.EMPTY_STRING;
	            String customerId = OMDConstants.EMPTY_STRING;
	            try {
	            	objAssetServiceVO = new AssetServiceVO();
	                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
	                	customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
	                	objAssetServiceVO.setCustomerID(customerId);
	                }
	                if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
	                	assetGroupName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
	                	objAssetServiceVO.setAssetGroupName(assetGroupName);
	                }
	                if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
	                	assetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
	                	objAssetServiceVO.setAssetNumberLike(assetNumber);
	                }
	                
	               
	         locoId = objAssetEoaServiceIntf.getLocoId(objAssetServiceVO);

		} catch (Exception e) {
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}

		return locoId;
		
	}
}
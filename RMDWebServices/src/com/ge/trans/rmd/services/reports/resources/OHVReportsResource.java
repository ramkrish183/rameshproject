package com.ge.trans.rmd.services.reports.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVMineRequestVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsGraphRequestVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ComStatusVO;
import com.ge.trans.rmd.common.valueobjects.MineVO;
import com.ge.trans.rmd.common.valueobjects.ReportsRxVO;
import com.ge.trans.rmd.common.valueobjects.ReportsTimeSeriesVO;
import com.ge.trans.rmd.common.valueobjects.ReportsTruckEventsVO;
import com.ge.trans.rmd.common.valueobjects.TruckGraphVO;
import com.ge.trans.rmd.common.valueobjects.TruckVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.assets.valueobjects.ListWrapperResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsComStatusResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsGraphRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsListWrapper;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsMineResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsRxRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsRxResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsTimeSeriesResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsTruckEventsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsTruckGraphResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsTruckInfoResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ReportsTruckParamListResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_OHV_REPORTS)
@Component
public class OHVReportsResource extends BaseResource{
	
	@Autowired
    private OHVReportsServiceIntf ohvReportsServiceIntf;
	@Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
	/**
	 * @author Koushik B
	 * @param customerId
	 * @return
	 * @throws DatatypeConfigurationException
	 */	
	@GET
	@Path(OMDConstants.REPORTS_GET_MINE)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ReportsMineResponseType getMine(@Context final UriInfo uriParam) throws RMDServiceException {
		ReportsMineResponseType mineReponse = null;
		String customerId = null;
		String fromDate = null;
		String toDate = null;
		OHVMineRequestVO mineRequest = null;
		MultivaluedMap<String, String> queryParams = null;
		LOG.info("getMine service triggered");
		try {
			queryParams = uriParam.getQueryParameters();			
			if (!queryParams.isEmpty()) {				
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
					customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);					
				} else {
					throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
				}
				fromDate = queryParams.getFirst(OMDConstants.REPORTS_FROM_DATE);
				toDate = queryParams.getFirst(OMDConstants.REPORTS_TO_DATE);
				mineRequest = new OHVMineRequestVO();
				mineRequest.setMineId(customerId);
				mineRequest.setFromDate(fromDate);
				mineRequest.setToDate(toDate);
				LOG.info("CustomerID:"+customerId+" FromDate:"+fromDate+" ToDate:"+toDate);
				if(null != customerId){
					mineReponse = new ReportsMineResponseType();
					LOG.info("Calling service from resource");
					MineVO mine = ohvReportsServiceIntf.getMine(mineRequest);			
					mineReponse.setCommStatus(mine.getCommStatus());
					mineReponse.setHeader(mine.getHeader());
					mineReponse.setHealthReport(mine.getHealthReport());
					mineReponse.setMessageReceived(mine.getMessageReceived());
					mineReponse.setMine(mine.getMine());
					mineReponse.setMineStatus(mine.getMineStatus());
					List<TruckVO> trucks = mine.getTrucks();
					List<ReportsTruckInfoResponseType> truckResponseList = new ArrayList<ReportsTruckInfoResponseType>();
					for(TruckVO truck : trucks) {
						ReportsTruckInfoResponseType truckResponse = new ReportsTruckInfoResponseType();
						truckResponse.setActiveTime(truck.getActiveTime());
						truckResponse.setControllerConfig(truck.getControllerConfig());
						truckResponse.setCustomerModel(truck.getCustomerModel());
						truckResponse.setFleet(truck.getFleet());
						truckResponse.setGeModel(truck.getGeModel());
						truckResponse.setHeader(truck.getHeader());
						truckResponse.setLoads(truck.getLoads());
						truckResponse.setManufacturer(truck.getManufacturer());
						truckResponse.setMine(truck.getMine());
						truckResponse.setOpenRx(truck.getOpenRx());
						truckResponse.setSoftware(truck.getSoftware());
						truckResponse.setTruck(truck.getTruck());
						truckResponse.setUrgency(truck.getUrgency());
						truckResponseList.add(truckResponse);
					}
					mineReponse.setTrucks(truckResponseList);
					Map<String, List<String>> params = mine.getMineParam();
					Map<String, ReportsListWrapper>  reportsParams = new LinkedHashMap<String, ReportsListWrapper>();
					ReportsListWrapper listWrapper = null;
					Iterator it = params.entrySet().iterator();
				    while (it.hasNext()) {
				        Map.Entry<String, List<String>> pair = (Map.Entry<String, List<String>>)it.next();
				        listWrapper = new ReportsListWrapper();
				        listWrapper.setList(pair.getValue());
				        reportsParams.put(pair.getKey(), listWrapper);
				        it.remove();
				    }
					mineReponse.setMineParams(reportsParams);
					
				} else {
					LOG.info("CustomerID is null");
					throw new OMDInValidInputException(
							OMDConstants.GETTING_NULL_REQUEST_OBJECT);
				}
			}
		} catch (Exception ex) {
			LOG.info("Exception occured in getMine of OHVReportsResource "+ex.getMessage());
			LOG.error(ex,ex);
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
		return mineReponse;
	}
	/**
     * @Author: Koushik B
     * @param: ReportsRxRequestType
     * @return:List<ReportsRxResponseType>
	 * @throws DatatypeConfigurationException 
     * @throws:RMDServiceException
     * @Description: 
     */
    @POST
    @Path(OMDConstants.REPORTS_GET_RX)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ReportsRxResponseType> getRx(final ReportsRxRequestType rxRequest) throws DatatypeConfigurationException{
    	List<ReportsRxVO> rxListResponse;
    	List<ReportsRxResponseType> rxListResponseTypeList = new ArrayList<ReportsRxResponseType>();
    	GregorianCalendar objGregorianCalendar;
    	String timeZone = getDefaultTimeZone();
    	if(null != rxRequest) {
    		OHVReportsRxRequestVO rxRequestVO = new OHVReportsRxRequestVO();
    		rxRequestVO.setMineId(rxRequest.getMineId());
    		rxRequestVO.setTruckId(rxRequest.getTruckId());
    		rxRequestVO.setRxType(rxRequest.getRxType());
    		rxRequestVO.setUrgency(rxRequest.getUrgency());
    		rxRequestVO.setFromDate(rxRequest.getFromDate());
    		rxRequestVO.setToDate(rxRequest.getToDate());
    		rxListResponse = ohvReportsServiceIntf.getReportsRx(rxRequestVO );
			
    		if(rxListResponse != null && !rxListResponse.isEmpty()){
    			rxListResponseTypeList = new ArrayList<ReportsRxResponseType>();    		
				for(ReportsRxVO reportsRxResp : rxListResponse) {
					ReportsRxResponseType reportsRxRespType = new ReportsRxResponseType();
					reportsRxRespType.setRxObjid(reportsRxResp.getRxObjid());
					reportsRxRespType.setRxTitle(reportsRxResp.getRxTitle());
					reportsRxRespType.setUrgency(reportsRxResp.getUrgency());
					reportsRxRespType.setTruckId(reportsRxResp.getTruckId());
					SimpleDateFormat formatter=new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);					
					if(reportsRxResp.getRxDeliverDate() != null){
						objGregorianCalendar = new GregorianCalendar();						  
						Date deliverDate = null;
						try {
							deliverDate = formatter.parse(reportsRxResp.getRxDeliverDate());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						objGregorianCalendar.setTime(deliverDate);
						RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
						XMLGregorianCalendar deliverDateTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
						reportsRxRespType.setRxDeliverDate(deliverDateTime);
					}					
					reportsRxRespType.setRxOpenTime(reportsRxResp.getRxOpenTime());
					if(reportsRxResp.getRxClosedDate() != null){
						objGregorianCalendar = new GregorianCalendar();
						Date closedDate = null;
						try {
							closedDate = formatter.parse(reportsRxResp.getRxClosedDate());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						objGregorianCalendar.setTime(closedDate);
						RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
						XMLGregorianCalendar rxClosedDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
						reportsRxRespType.setRxClosedDate(rxClosedDate);
					}
					reportsRxRespType.setTimezone(timeZone);
					reportsRxRespType.setCaseId(reportsRxResp.getCaseId());
					reportsRxRespType.setEstmRepairTime(reportsRxResp.getEstmRepairTime());
					reportsRxRespType.setLocoImpact(reportsRxResp.getLocoImpact());
					rxListResponseTypeList.add(reportsRxRespType);
				}
    		}
    	} else{
            throw new OMDInValidInputException(
                    OMDConstants.GETTING_NULL_REQUEST_OBJECT);      
        }
    	return rxListResponseTypeList;
    }
    
    
    /**
     * @Author: Koushik B
     * @param: ReportsRxRequestType
     * @return:List<ReportsRxResponseType>
     * @throws DatatypeConfigurationException 
     * @throws:RMDServiceException
     * @Description: 
     */
    @POST
    @Path(OMDConstants.REPORTS_GET_TRUCK_EVENTS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ReportsTruckEventsResponseType> getTruckEvents(final ReportsRxRequestType truckEventRequest) throws DatatypeConfigurationException{
    	List<ReportsTruckEventsVO> truckEventsListResponse;
    	List<ReportsTruckEventsResponseType> reportsTruckEventsResponseTypeList = new ArrayList<ReportsTruckEventsResponseType>();
    	if(null != truckEventRequest) {
    		OHVReportsRxRequestVO truckEventRequestVO = new OHVReportsRxRequestVO();
    		truckEventRequestVO.setMineId(truckEventRequest.getMineId());
    		truckEventRequestVO.setTruckId(truckEventRequest.getTruckId());
    		truckEventRequestVO.setFromDate(truckEventRequest.getFromDate());
    		truckEventRequestVO.setToDate(truckEventRequest.getToDate());
    		truckEventsListResponse = ohvReportsServiceIntf.getReportsTruckEvents(truckEventRequestVO);
			
    		if(truckEventsListResponse != null && !truckEventsListResponse.isEmpty()){   		
				for(ReportsTruckEventsVO reportsTruckEventsResp : truckEventsListResponse) {
					ReportsTruckEventsResponseType reportsTruckEventsRespType = new ReportsTruckEventsResponseType();
					reportsTruckEventsRespType.setEventNumber(reportsTruckEventsResp.getEventNumber());
					reportsTruckEventsRespType.setSubId(reportsTruckEventsResp.getSubId());
					reportsTruckEventsRespType.setDescription(reportsTruckEventsResp.getDescription());					
					reportsTruckEventsRespType.setOccurTime(reportsTruckEventsResp.getOccurTime());					
					reportsTruckEventsRespType.setResetTime(reportsTruckEventsResp.getResetTime());
					reportsTruckEventsResponseTypeList.add(reportsTruckEventsRespType);
				}
    		}
    	} else{
            throw new OMDInValidInputException(
                    OMDConstants.GETTING_NULL_REQUEST_OBJECT);      
        }
    	return reportsTruckEventsResponseTypeList;
    }
    
    /**
     * @Author: Koushik B
     * @param: ReportsRxRequestType
     * @return: ReportsTruckInfoResponseType
     * @throws DatatypeConfigurationException 
     * @throws:RMDServiceException
     * @Description: 
     */
    @POST
    @Path(OMDConstants.REPORTS_GET_TRUCK_INFO)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ReportsTruckInfoResponseType getTruckInfo(final ReportsRxRequestType truckInfoRequest) throws DatatypeConfigurationException{
    	ReportsTruckInfoResponseType truckInfoResponse = null;
    	if(null != truckInfoRequest) {
    		OHVReportsRxRequestVO truckInfoVO = new OHVReportsRxRequestVO();
    		truckInfoVO.setMineId(truckInfoRequest.getMineId());
    		truckInfoVO.setTruckId(truckInfoRequest.getTruckId());
    		truckInfoVO.setFromDate(truckInfoRequest.getFromDate());
    		truckInfoVO.setToDate(truckInfoRequest.getToDate());
			TruckVO truckInfoResponseVO = ohvReportsServiceIntf.getTruckInfo(truckInfoVO);
			if(null != truckInfoResponseVO){
				truckInfoResponse = new ReportsTruckInfoResponseType();
				truckInfoResponse.setControllerConfig(truckInfoResponseVO.getControllerConfig());
				truckInfoResponse.setCustomerModel(truckInfoResponseVO.getCustomerModel());
				truckInfoResponse.setFleet(truckInfoResponseVO.getFleet());
				truckInfoResponse.setGeModel(truckInfoResponseVO.getGeModel());
				truckInfoResponse.setHeader(truckInfoResponseVO.getHeader());
				truckInfoResponse.setManufacturer(truckInfoResponseVO.getManufacturer());
				truckInfoResponse.setMine(truckInfoResponseVO.getMine());
				truckInfoResponse.setSoftware(truckInfoResponseVO.getSoftware());
				truckInfoResponse.setTruck(truckInfoResponseVO.getTruck());
			}
    	} else {
    		throw new OMDInValidInputException(
                    OMDConstants.GETTING_NULL_REQUEST_OBJECT);
    	}
    	return truckInfoResponse;
    }
    
    /**
     * @Author: Koushik B
     * @param: ReportsRxRequestType
     * @return: ReportsComStatusResponseType
     * @throws DatatypeConfigurationException 
     * @throws:RMDServiceException
     * @Description: 
     */
    @POST
    @Path(OMDConstants.REPORTS_GET_COM_STATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ReportsComStatusResponseType getComStatus(final ReportsRxRequestType comStatusRequest) throws DatatypeConfigurationException{
    	ReportsComStatusResponseType comStatusResponse = null;
    	if(null != comStatusRequest) {
    		OHVReportsRxRequestVO comStatusVO = new OHVReportsRxRequestVO();
    		comStatusVO.setMineId(comStatusRequest.getMineId());
    		comStatusVO.setTruckId(comStatusRequest.getTruckId());
    		comStatusVO.setFromDate(comStatusRequest.getFromDate());
    		comStatusVO.setToDate(comStatusRequest.getToDate());
    		ComStatusVO comStatusResponseVO = ohvReportsServiceIntf.getComStatus(comStatusVO);
    		if(null != comStatusResponseVO) {
    			comStatusResponse = new ReportsComStatusResponseType();
    			comStatusResponse.setEoaEquip(comStatusResponseVO.getEoaEquip());
    			comStatusResponse.setHealthReports(comStatusResponseVO.getHealthReports());
    			comStatusResponse.setMessageReceived(comStatusResponseVO.getMessageReceived());
    		}
    	} else {
    		throw new OMDInValidInputException(
                    OMDConstants.GETTING_NULL_REQUEST_OBJECT);
    	}
    	return comStatusResponse;
    }
    
    @POST
    @Path(OMDConstants.REPORTS_GET_TOP_EVENTS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ReportsTruckEventsResponseType> getTopEvents(final ReportsRxRequestType topEventRequest) {
    	List<ReportsTruckEventsVO> truckEventsListResponse;
    	List<ReportsTruckEventsResponseType> reportsTruckEventsResponseTypeList = new ArrayList<ReportsTruckEventsResponseType>();
    	if(null != topEventRequest) {
    		OHVMineRequestVO mineRequest = new OHVMineRequestVO();
    		mineRequest.setMineId(topEventRequest.getMineId());
    		mineRequest.setGeLevelReport(topEventRequest.isGeLevelReport());
    		mineRequest.setFromDate(topEventRequest.getFromDate());
    		mineRequest.setToDate(topEventRequest.getToDate());
    		truckEventsListResponse = ohvReportsServiceIntf.getReportsTopEvents(mineRequest);
			
    		if(truckEventsListResponse != null && !truckEventsListResponse.isEmpty()){   		
				for(ReportsTruckEventsVO reportsTruckEventsResp : truckEventsListResponse) {
					ReportsTruckEventsResponseType reportsTruckEventsRespType = new ReportsTruckEventsResponseType();
					reportsTruckEventsRespType.setEventNumber(reportsTruckEventsResp.getEventNumber());
					reportsTruckEventsRespType.setSubId(reportsTruckEventsResp.getSubId());
					reportsTruckEventsRespType.setDescription(reportsTruckEventsResp.getDescription());
					reportsTruckEventsRespType.setNumberOfOccurance(reportsTruckEventsResp.getNumberOfOccurance());
					reportsTruckEventsRespType.setTrucksPerDay(reportsTruckEventsResp.getTrucksPerDay());
					reportsTruckEventsResponseTypeList.add(reportsTruckEventsRespType);
				}
    		}
    	} else{
            throw new OMDInValidInputException(
                    OMDConstants.GETTING_NULL_REQUEST_OBJECT);      
        }
    	return reportsTruckEventsResponseTypeList;
    }
    
    @POST
    @Path(OMDConstants.REPORTS_GET_TRUCK_GRAPH_DATA)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ReportsTruckGraphResponseType getTruckGraphData(final ReportsGraphRequestType graphDataRequest) {
    	ReportsTruckGraphResponseType graphDataResponse = new ReportsTruckGraphResponseType();
    	TruckGraphVO graphData;
		if(null != graphDataRequest) {
    		OHVReportsGraphRequestVO graphRequest = new OHVReportsGraphRequestVO();
    		graphRequest.setMineId(graphDataRequest.getMineId());
    		graphRequest.setTruckId(graphDataRequest.getTruckId());
    		graphRequest.setFromDate(graphDataRequest.getFromDate());
    		graphRequest.setToDate(graphDataRequest.getToDate());
    		graphRequest.setPeriod(graphDataRequest.getPeriod());
    		graphData = ohvReportsServiceIntf.getTruckGraphData(graphRequest);
    		if(graphData != null) {
    			List<ReportsTimeSeriesVO> loads = graphData.getLoads();
    			List<ReportsTimeSeriesResponseType> loadsReponse = new ArrayList<ReportsTimeSeriesResponseType>();
    			for(ReportsTimeSeriesVO load : loads){
    				ReportsTimeSeriesResponseType loadResp = new ReportsTimeSeriesResponseType();
    				loadResp.setTimestamp(load.getTimestamp());
    				loadResp.setValue(load.getValue());
    				loadsReponse.add(loadResp);
    			}
    			graphDataResponse.setLoads(loadsReponse);
    			
    			List<ReportsTimeSeriesVO> engineOpHrs = graphData.getEngineOpHrs();
    			List<ReportsTimeSeriesResponseType> engineOpHrsResponse = new ArrayList<ReportsTimeSeriesResponseType>();
    			for(ReportsTimeSeriesVO engineOpHr : engineOpHrs){
    				ReportsTimeSeriesResponseType engineOpHrResp = new ReportsTimeSeriesResponseType();
    				engineOpHrResp.setTimestamp(engineOpHr.getTimestamp());
    				engineOpHrResp.setValue(engineOpHr.getValue());
    				engineOpHrsResponse.add(engineOpHrResp);
    			}
    			graphDataResponse.setEngineOpHrs(engineOpHrsResponse);
    			
    			List<ReportsTimeSeriesVO> engineIdleTime = graphData.getEngineIdleTime();
    			List<ReportsTimeSeriesResponseType> engineIdleTimeResponse = new ArrayList<ReportsTimeSeriesResponseType>();
    			for(ReportsTimeSeriesVO engineIdleTm : engineIdleTime){
    				ReportsTimeSeriesResponseType engineIdleTmResp = new ReportsTimeSeriesResponseType();
    				engineIdleTmResp.setTimestamp(engineIdleTm.getTimestamp());
    				engineIdleTmResp.setValue(engineIdleTm.getValue());
    				engineIdleTimeResponse.add(engineIdleTmResp);
    			}
    			graphDataResponse.setEngineIdleTime(engineIdleTimeResponse);
    			
    			List<ReportsTimeSeriesVO> overloads = graphData.getOverloads();
    			List<ReportsTimeSeriesResponseType> overloadsResponseList = new ArrayList<ReportsTimeSeriesResponseType>();
    			for(ReportsTimeSeriesVO overload : overloads){
    				ReportsTimeSeriesResponseType overloadsResp = new ReportsTimeSeriesResponseType();
    				overloadsResp.setTimestamp(overload.getTimestamp());
    				overloadsResp.setValue(overload.getValue());
    				overloadsResponseList.add(overloadsResp);
    			}
    			graphDataResponse.setOverloads(overloadsResponseList);
    			
    			List<ReportsTimeSeriesVO> avgHpVals = graphData.getAverageHP();
    			List<ReportsTimeSeriesResponseType> avgHpRespList = new ArrayList<ReportsTimeSeriesResponseType>();
    			for(ReportsTimeSeriesVO avgHp : avgHpVals){
    				ReportsTimeSeriesResponseType avgHpResp = new ReportsTimeSeriesResponseType();
    				avgHpResp.setTimestamp(avgHp.getTimestamp());
    				avgHpResp.setValue(avgHp.getValue());
    				avgHpRespList.add(avgHpResp);
    			}
    			graphDataResponse.setAverageHP(avgHpRespList);
    		}
    	} else{
            throw new OMDInValidInputException(
                    OMDConstants.GETTING_NULL_REQUEST_OBJECT);      
        }
    	return graphDataResponse;
    }
    
    @POST
    @Path(OMDConstants.REPORTS_GET_TRUCK_PARAM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ListWrapperResponseType getTruckParam(final ReportsRxRequestType truckInfoRequest) throws DatatypeConfigurationException{
     List<String> truckParamList = null;
     LOG.info("Service is invoked");
     ListWrapperResponseType truckParams = new ListWrapperResponseType();
     if(null != truckInfoRequest) {
    	 OHVReportsRxRequestVO truckInfoVO = new OHVReportsRxRequestVO();
    	 truckInfoVO.setMineId(truckInfoRequest.getMineId());
    	 truckInfoVO.setTruckId(truckInfoRequest.getTruckId());
    	 truckInfoVO.setFromDate(truckInfoRequest.getFromDate());
    	 truckInfoVO.setToDate(truckInfoRequest.getToDate());
    	 truckInfoVO.setAvgCalc(truckInfoRequest.isAvgCalc());
    	 truckParamList = ohvReportsServiceIntf.getTruckParm(truckInfoVO);

    	 truckParams.setElements(truckParamList);
     } else {
    	 throw new OMDInValidInputException(
    			 OMDConstants.GETTING_NULL_REQUEST_OBJECT);
     }
     return truckParams;
    }
    
    
    @POST
    @Path(OMDConstants.REPORTS_GET_TRUCK_PARAM_LIST)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ReportsTruckParamListResponseType getTruckVariablesParamList(final ReportsRxRequestType truckInfoRequest) throws DatatypeConfigurationException{
     LOG.info("Service is invoked");
     ReportsTruckParamListResponseType truckParams = new ReportsTruckParamListResponseType();
     Map<String, ReportsListWrapper>  truckreportsParams = new HashMap<String, ReportsListWrapper>();
     if(null != truckInfoRequest) {
    	 OHVReportsRxRequestVO truckInfoVO = new OHVReportsRxRequestVO();
    	 truckInfoVO.setMineId(truckInfoRequest.getMineId());
    	 truckInfoVO.setTruckId(truckInfoRequest.getTruckId());
    	 truckInfoVO.setFromDate(truckInfoRequest.getFromDate());
    	 truckInfoVO.setToDate(truckInfoRequest.getToDate());
    	 truckInfoVO.setAvgCalc(truckInfoRequest.isAvgCalc());
    	 Map<String,List<String>> truckParamListMap = ohvReportsServiceIntf.getTruckParmList(truckInfoVO);
    	 
    	 ReportsListWrapper listWrapper = null;
		 for(Map.Entry<String, List<String>> pair : truckParamListMap.entrySet()){
	        listWrapper = new ReportsListWrapper();
	        listWrapper.setList(pair.getValue());
	        truckreportsParams.put(pair.getKey(), listWrapper);
	     }
	     truckParams.setTruckParamsList(truckreportsParams);
    	 
     } else {
    	 throw new OMDInValidInputException(
    			 OMDConstants.GETTING_NULL_REQUEST_OBJECT);
     }
     return truckParams;
    }
    
    
    
    
    @POST
    @Path(OMDConstants.REPORTS_GET_TRUCK_PARAM_LIST_SCHEDULER)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ReportsTruckParamListResponseType getTruckVariablesParamListScheduler(final ReportsRxRequestType truckInfoRequest) throws DatatypeConfigurationException{
     LOG.info("Service is invoked");
     ReportsTruckParamListResponseType truckParams = new ReportsTruckParamListResponseType();
     Map<String, ReportsListWrapper>  truckreportsParams = new HashMap<String, ReportsListWrapper>();
     if(null != truckInfoRequest) {
    	 OHVReportsRxRequestVO truckInfoVO = new OHVReportsRxRequestVO();
    	 truckInfoVO.setMineId(truckInfoRequest.getMineId());
    	 truckInfoVO.setTruckId(truckInfoRequest.getTruckId());
    	 Map<String,List<String>> truckParamListMap = ohvReportsServiceIntf.getTruckParmListScheduler(truckInfoVO);
    	 
    	 ReportsListWrapper listWrapper = null;
		 for(Map.Entry<String, List<String>> pair : truckParamListMap.entrySet()){
	        listWrapper = new ReportsListWrapper();
	        listWrapper.setList(pair.getValue());
	        truckreportsParams.put(pair.getKey(), listWrapper);
	     }
	     truckParams.setTruckParamsList(truckreportsParams);
    	 
     } else {
    	 throw new OMDInValidInputException(
    			 OMDConstants.GETTING_NULL_REQUEST_OBJECT);
     }
     return truckParams;
    }
}

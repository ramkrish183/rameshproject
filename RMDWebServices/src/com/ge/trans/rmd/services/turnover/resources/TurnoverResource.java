package com.ge.trans.rmd.services.turnover.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.cases.service.valueobjects.CallLogVO;
import com.ge.trans.eoa.services.turnover.service.intf.TurnoverEoaServiceIntf;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.GetInboundTurnoverVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.CallLogDetailsResponse;
import com.ge.trans.rmd.services.turnover.valueobjects.TurnoverInboundResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.TURNOVER_SERVICE)
@Component
public class TurnoverResource extends BaseResource {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(TurnoverResource.class);

	@Autowired
	TurnoverEoaServiceIntf turnoverEoaServiceIntf;
	@Autowired
	OMDResourceMessagesIntf omdResourceMessagesIntf;

	@GET
	@Path(OMDConstants.GET_INBOUND_REPORT)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<TurnoverInboundResponseType> getInboundReportData()
			throws RMDServiceException {

		List<TurnoverInboundResponseType> returnValue = new ArrayList<TurnoverInboundResponseType>();
		try {
			List<GetInboundTurnoverVO> val = turnoverEoaServiceIntf
					.getInboundReportData();

			if (RMDCommonUtility.isCollectionNotEmpty(val)) {
				for (GetInboundTurnoverVO inboundTurnoverVO : val) {
					TurnoverInboundResponseType turnoverInboundResponseType = new TurnoverInboundResponseType();
					turnoverInboundResponseType.setRoadNumberHeader(inboundTurnoverVO
							.getRoadNumberHeader());
					turnoverInboundResponseType.setGpocComments(inboundTurnoverVO.getGpocComments());
					turnoverInboundResponseType.setCount(inboundTurnoverVO.getCount());

					returnValue.add(turnoverInboundResponseType);
				}
			} /*else {
				throw new OMDNoResultFoundException(
						BeanUtility
								.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}*/
		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (RMDServiceException rmdServiceException) {
			throw rmdServiceException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}
		return returnValue;
	}
    
    /**
     * @Author :
     * @return :List<CallLogDetailsResponse>
     * @param : 
     * @throws :RMDServiceException
     * @Description: This method is used to get the call count by location.
     */
    @GET
    @Path(OMDConstants.GET_CALL_COUNT_BY_LOCATION)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CallLogDetailsResponse> getCallCountByLocation(@Context UriInfo ui) throws RMDServiceException {
        List<CallLogDetailsResponse> arlCallLogDetailsResponse = null;
        List<CallLogVO> arlCallLogVo = new ArrayList<CallLogVO>();
        CallLogDetailsResponse objCallLogReportResponse = null;
        try {
            LOG.debug("*****getCallCountByLocation() WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            arlCallLogVo = turnoverEoaServiceIntf.getCallCountByLocation();
            if (RMDCommonUtility.isCollectionNotEmpty(arlCallLogVo)) {
                arlCallLogDetailsResponse = new ArrayList<CallLogDetailsResponse>(arlCallLogVo.size());
                for (CallLogVO objCallLogReportVO : arlCallLogVo) {
                    objCallLogReportResponse = new CallLogDetailsResponse();
                    objCallLogReportResponse.setLocation(objCallLogReportVO.getLocation());
                    objCallLogReportResponse.setCount(objCallLogReportVO.getCount());
                    arlCallLogDetailsResponse.add(objCallLogReportResponse);
                }
            }
            
        } catch (Exception e) {
            LOG.error("Exception occuered in getCallCountByLocation() method of TurnoverResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return arlCallLogDetailsResponse;
    }
    
    /**
     * @Author :
     * @return :List<CallLogDetailsResponse>
     * @param : 
     * @throws :RMDServiceException
     * @Description: This method is used to get the sum of mins of duration of call count for customers.
     */
    @GET
    @Path(OMDConstants.GET_CUST_BREAK_DOWN_MINS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CallLogDetailsResponse> getCustBrkDownByMins(@Context UriInfo ui) throws RMDServiceException {
        List<CallLogDetailsResponse> arlCallLogDetailsResponse = null;
        List<CallLogVO> arlCallLogVo = new ArrayList<CallLogVO>();
        CallLogDetailsResponse objCallLogReportResponse = null;
        try {
            LOG.debug("*****getCustBrkDownByMins() WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            arlCallLogVo = turnoverEoaServiceIntf.getCustBrkDownByMins();
            if (RMDCommonUtility.isCollectionNotEmpty(arlCallLogVo)) {
                arlCallLogDetailsResponse = new ArrayList<CallLogDetailsResponse>(arlCallLogVo.size());
                for (CallLogVO objCallLogReportVO : arlCallLogVo) {
                	objCallLogReportResponse = new CallLogDetailsResponse();
                	objCallLogReportResponse.setCustomer(objCallLogReportVO.getCustomer());
                	objCallLogReportResponse.setCount(objCallLogReportVO.getCount());
                	arlCallLogDetailsResponse.add(objCallLogReportResponse);
                }
            }
            
        } catch (Exception e) {
            LOG.error("Exception occuered in getCustBrkDownByMins() method of TurnoverResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return arlCallLogDetailsResponse;
    }
    
    /**
     * @Author :
     * @return :List<CallLogDetailsResponse>
     * @param : 
     * @throws :RMDServiceException
     * @Description: This method is used to get the count of calls group by bussiness type for customers.
     */
    @GET
    @Path(OMDConstants.GET_CALL_COUNT_BY_BUSS_AREA)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CallLogDetailsResponse> getCallCntByBusnsArea(@Context UriInfo ui) throws RMDServiceException {
        List<CallLogDetailsResponse> arlCallLogDetailsResponse = null;
        List<CallLogVO> arlCallLogVo = new ArrayList<CallLogVO>();
        CallLogDetailsResponse objCallLogReportResponse = null;
        try {
            LOG.debug("*****getCallCntByBusnsArea() WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            arlCallLogVo = turnoverEoaServiceIntf.getCallCntByBusnsArea();
            if (RMDCommonUtility.isCollectionNotEmpty(arlCallLogVo)) {
                arlCallLogDetailsResponse = new ArrayList<CallLogDetailsResponse>(arlCallLogVo.size());
                for (CallLogVO objCallLogReportVO : arlCallLogVo) {
                    objCallLogReportResponse = new CallLogDetailsResponse();
                    objCallLogReportResponse.setCustomer(objCallLogReportVO.getCustomer());
                    objCallLogReportResponse.setCount(objCallLogReportVO.getCount());
                    objCallLogReportResponse.setBussinessType(objCallLogReportVO.getBussinessType());
                    arlCallLogDetailsResponse.add(objCallLogReportResponse);
                }
            }
            
        } catch (Exception e) {
            LOG.error("Exception occuered in getCallCntByBusnsArea() method of TurnoverResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        
        return arlCallLogDetailsResponse;
    }
    
    /**
     * @Author :
     * @return :List<CallLogDetailsResponse>
     * @param : 
     * @throws :RMDServiceException
     * @Description: This method is used to get the count of calls group by bussiness type for customers.
     */
    @GET
    @Path(OMDConstants.GET_WEEKLY_CALL_COUNT_BY_BUSS_AREA)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CallLogDetailsResponse> getWeeklyCallCntByBusnsArea(@Context UriInfo ui) throws RMDServiceException {
        List<CallLogDetailsResponse> arlCallLogDetailsResponse = null;
        List<CallLogVO> arlCallLogVo = new ArrayList<CallLogVO>();
        CallLogDetailsResponse objCallLogReportResponse = null;
        try {
            LOG.debug("*****getWeeklyCallCntByBusnsArea() WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            arlCallLogVo = turnoverEoaServiceIntf.getWeeklyCallCntByBusnsArea();
            if (RMDCommonUtility.isCollectionNotEmpty(arlCallLogVo)) {
                arlCallLogDetailsResponse = new ArrayList<CallLogDetailsResponse>(arlCallLogVo.size());
                for (CallLogVO objCallLogReportVO : arlCallLogVo) {
                    objCallLogReportResponse = new CallLogDetailsResponse();
                    objCallLogReportResponse.setDate(objCallLogReportVO.getDate());
                    objCallLogReportResponse.setBussinessType(objCallLogReportVO.getBussinessType());
                    objCallLogReportResponse.setCount(objCallLogReportVO.getCount());
                    arlCallLogDetailsResponse.add(objCallLogReportResponse);
                }
            }
            
        } catch (Exception e) {
            LOG.error("Exception occuered in getWeeklyCallCntByBusnsArea() method of TurnoverResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        
        return arlCallLogDetailsResponse;
    }
    
    /**
     * @Author :
     * @return :List<CallLogDetailsResponse>
     * @param : 
     * @throws :RMDServiceException
     * @Description: This method is used to get the count of calls group by bussiness type for assets.
     */
    @GET
    @Path(OMDConstants.GET_VEH_CALL_COUNT_BY_BUSS_AREA)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CallLogDetailsResponse> getVehCallCntByBusnsArea(@Context UriInfo ui) throws RMDServiceException {
        List<CallLogDetailsResponse> arlCallLogDetailsResponse = null;
        List<CallLogVO> arlCallLogVo = new ArrayList<CallLogVO>();
        CallLogDetailsResponse objCallLogReportResponse = null;
        try {
            LOG.debug("*****getVehCallCntByBusnsArea() WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            arlCallLogVo = turnoverEoaServiceIntf.getVehCallCntByBusnsArea();
            if (RMDCommonUtility.isCollectionNotEmpty(arlCallLogVo)) {
                arlCallLogDetailsResponse = new ArrayList<CallLogDetailsResponse>(arlCallLogVo.size());
                for (CallLogVO objCallLogReportVO : arlCallLogVo) {
                    objCallLogReportResponse = new CallLogDetailsResponse();
                    objCallLogReportResponse.setAsset(objCallLogReportVO.getAsset());
                    objCallLogReportResponse.setBussinessType(objCallLogReportVO.getBussinessType());
                    objCallLogReportResponse.setCount(objCallLogReportVO.getCount());
                    arlCallLogDetailsResponse.add(objCallLogReportResponse);
                }
            }
            
        } catch (Exception e) {
            LOG.error("Exception occuered in getVehCallCntByBusnsArea() method of TurnoverResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return arlCallLogDetailsResponse;
    }
    
    /**
     * @Author :
     * @return :String
     * @param : UriInfo ui
     * @throws :RMDWebException
     * @Description: This method is used to get the manual call count.
     */
    @GET
    @Path(OMDConstants.GET_MANUAL_CALL_COUNT)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getManualCallCount(@Context UriInfo ui) throws RMDServiceException {
        String convPercentage = null;
        try {
            LOG.debug("*****getManualCallCount WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            convPercentage = turnoverEoaServiceIntf.getManualCallCount();

        } catch (Exception e) {
            LOG.error("Exception occuered in getManualCallCount() method of TurnoverResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return convPercentage;
    }
    
}

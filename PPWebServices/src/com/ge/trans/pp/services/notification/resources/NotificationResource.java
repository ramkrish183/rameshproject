package com.ge.trans.pp.services.notification.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.pp.common.constants.OMDConstants;
import com.ge.trans.pp.common.exception.OMDInValidInputException;
import com.ge.trans.pp.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.pp.common.resources.BaseResource;
import com.ge.trans.pp.common.util.BeanUtility;
import com.ge.trans.pp.common.util.PPRMDWebServiceErrorHandler;
import com.ge.trans.pp.services.notification.service.intf.NotificationServiceIntf;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryReqVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPRoadInitialVO;
import com.ge.trans.pp.services.notification.valueobjects.PpAssetServiceStatusRequestType;
import com.ge.trans.pp.services.notification.valueobjects.PpNotificationRequestType;
import com.ge.trans.pp.services.notification.valueobjects.PpNotificationResponseType;
import com.ge.trans.pp.services.notification.valueobjects.RegionType;
import com.ge.trans.pp.services.notification.valueobjects.RoadInitialType;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: May 24, 2013
 * @Date Modified : May 24, 2013
 * @Modified By :
 * @Contact :
 * @Description : This Class act as notificationservice Webservices and provide
 *              the proximity related funtionalities
 * @History :
 ******************************************************************************/
@Path(OMDConstants.NOTIFICATION_SERVICE)
@Component
public class NotificationResource extends BaseResource {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(NotificationResource.class);

    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(NotificationResource.class);

    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    private NotificationServiceIntf objNotificationServiceIntf;

    /**
     * This Method is used for retrieving List of regions for a customer.
     * 
     * @param customerId
     * @return RegionType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.PP_CUSTOMER_REGIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public RegionType getPPCustomerRegions(@PathParam(OMDConstants.CUSTOMER_ID) final String customerId)
            throws RMDServiceException {
        List<String> objRegionLst = null;
        String strLanguage = null;
        RegionType objRegionType = new RegionType();
        try {

            if (validateCustomerId(customerId)) {
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);

                objRegionLst = objNotificationServiceIntf.getPPCustomerRegions(customerId, strLanguage);

                if (RMDCommonUtility.isCollectionNotEmpty(objRegionLst)) {

                    objRegionType.setRegion(objRegionLst);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("NotificationResource : Inside getPPCustomerRegions() method:::END");
        return objRegionType;
    }

    /**
     * This Method is used for retrieving notification history details.
     * 
     * @param PpNotificationRequestType
     * @return List<PpNotificationResponseType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.PP_NOTIFICATION_HISTORY)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<PpNotificationResponseType> getNotificationHistory(
            final PpNotificationRequestType objPpNotificationReqType) throws RMDServiceException {
        final PPNotificationHistoryReqVO objPPNotHistoryReqVO = new PPNotificationHistoryReqVO();

        PpNotificationResponseType objPPNotRespType = null;
        List<PpNotificationResponseType> objPPNotRespTypeLst = new ArrayList<PpNotificationResponseType>();
        List<PPNotificationHistoryVO> objPPNotHistVOLst = null;
        String objDate;
        DateFormat dFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.NOSPACE_MMddyyyyHHmmss);
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar notificationDt = null;
        String timeZone = getDefaultTimeZone();
        String toDate = null, fromDate = null;
		XMLGregorianCalendar xmlFromDate = null;
		XMLGregorianCalendar xmlToDate = null;
		Date fromdate = null, todate = null;
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
		GregorianCalendar fromgregorianCalendar, togregorianCalendar;
        try {
            dFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            if (validateNotificationHistory(objPpNotificationReqType)) {
                if (null != objPpNotificationReqType.getRoadNo() && !objPpNotificationReqType.getRoadNo().isEmpty()) {
                    objPPNotHistoryReqVO
                            .setRoadNo(BeanUtility.stripXSSCharacters(objPpNotificationReqType.getRoadNo()));
                }

                if (null != objPpNotificationReqType.getCustomerId()
                        && !objPpNotificationReqType.getCustomerId().isEmpty()) {
                    objPPNotHistoryReqVO
                            .setCustomerId(BeanUtility.stripXSSCharacters(objPpNotificationReqType.getCustomerId()));
                }

                if (null != objPpNotificationReqType.getRoadInitialName()
                        && !objPpNotificationReqType.getRoadInitialName().isEmpty()) {
                    objPPNotHistoryReqVO.setRoadInitial(
                            BeanUtility.stripXSSCharacters(objPpNotificationReqType.getRoadInitialName()));
                }

                if (null != objPpNotificationReqType.getFromDate()) {
                	fromdate = simpleDateFormat.parse(objPpNotificationReqType.getFromDate());
    				fromgregorianCalendar = (GregorianCalendar) GregorianCalendar
    						.getInstance();
    				fromgregorianCalendar.setTime(fromdate);
    				RMDCommonUtility.setZoneOffsetTime(fromgregorianCalendar,objPpNotificationReqType.getUserTimeZone());
    				xmlFromDate = DatatypeFactory.newInstance()
    						.newXMLGregorianCalendar(fromgregorianCalendar);
                    objDate = dFormat.format(xmlFromDate.toGregorianCalendar().getTime());
                    objPPNotHistoryReqVO.setFromDate(BeanUtility.stripXSSCharacters(objDate));
                }

                if (null != objPpNotificationReqType.getToDate()) {
                	todate = simpleDateFormat.parse(objPpNotificationReqType.getToDate());
    				togregorianCalendar = (GregorianCalendar) GregorianCalendar
    						.getInstance();				
    				togregorianCalendar.setTime(todate);
    				RMDCommonUtility.setZoneOffsetTime(togregorianCalendar,objPpNotificationReqType.getUserTimeZone());
    				xmlToDate = DatatypeFactory.newInstance()
    						.newXMLGregorianCalendar(togregorianCalendar);
                    objPPNotHistoryReqVO.setToDate(BeanUtility.stripXSSCharacters(
                            dFormat.format(xmlToDate.toGregorianCalendar().getTime())));
                }

                if (null != objPpNotificationReqType.getNotificationType()
                        && !objPpNotificationReqType.getNotificationType().isEmpty()) {
                    objPPNotHistoryReqVO.setNotificationTypes(
                            BeanUtility.stripXSSCharacters(objPpNotificationReqType.getNotificationType()));
                }

                if (objPpNotificationReqType.isInactiveNotification()) {
                    objPPNotHistoryReqVO.setInactiveNotification(BeanUtility
                            .stripXSSCharactersFromBoolean(objPpNotificationReqType.isInactiveNotification()));
                } else {
                    objPPNotHistoryReqVO.setInactiveNotification(false);
                }

                if (null != objPpNotificationReqType.getRegion() && !objPpNotificationReqType.getRegion().isEmpty()) {
                    objPPNotHistoryReqVO
                            .setRegion(BeanUtility.stripXSSCharacters(objPpNotificationReqType.getRegion()));
                }

                if (null != objPpNotificationReqType.getTimeZone()
                        && !objPpNotificationReqType.getTimeZone().isEmpty()) {
                    objPPNotHistoryReqVO
                            .setTimeZone(BeanUtility.stripXSSCharacters(objPpNotificationReqType.getTimeZone()));
                }
                if (null != objPpNotificationReqType.getProductNames()
                        && !objPpNotificationReqType.getProductNames().isEmpty()) {
                    BeanUtility.copyProductNameToServiceVO(objPpNotificationReqType.getProductNames(),
                            objPPNotHistoryReqVO);
                }

                objPPNotHistVOLst = objNotificationServiceIntf.getNotificationHistory(objPPNotHistoryReqVO);

                if (RMDCommonUtility.isCollectionNotEmpty(objPPNotHistVOLst)) {
                    for (PPNotificationHistoryVO objPpNotificationHistoryVO : objPPNotHistVOLst) {
                        objPPNotRespType = new PpNotificationResponseType();
                        objPPNotRespType.setRoadInitialName(objPpNotificationHistoryVO.getAssetGrpName());
                        objPPNotRespType.setRoadNo(objPpNotificationHistoryVO.getAssetNumber());

                        if (null != objPpNotificationHistoryVO.getDateTime()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar
                                    .setTime(new SimpleDateFormat(RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT)
                                            .parse(objPpNotificationHistoryVO.getDateTime()));
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            notificationDt = DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(objGregorianCalendar);
                            objPPNotRespType.setNotificationDate(notificationDt);
                        }
                        objPPNotRespType.setComment(objPpNotificationHistoryVO.getComments());
                        objPPNotRespType.setNotificationType(objPpNotificationHistoryVO.getNotificationType());
                        objPPNotRespType.setEmail(objPpNotificationHistoryVO.getEmail());
                        objPPNotRespType.setStatus(objPpNotificationHistoryVO.getStatus());
                        objPPNotRespType.setCityName(objPpNotificationHistoryVO.getLocation());
                        objPPNotRespType.setStateCode(objPpNotificationHistoryVO.getState());
                        objPPNotRespType.setMilepost(objPpNotificationHistoryVO.getMilepost());
                        objPPNotRespType.setCurrentRegion(objPpNotificationHistoryVO.getCurrRegion());
                        objPPNotRespType.setSubRegion(objPpNotificationHistoryVO.getSubRegion());
                        if (null != objPpNotificationHistoryVO.getFuelLevel()
                                && !objPpNotificationHistoryVO.getFuelLevel().isEmpty()) {
//                        	if(Integer.parseInt(objPpNotificationHistoryVO.getFuelLevel()) >10000){
//                        		String desc = "error code";
//    							StringBuilder codeStr = new StringBuilder();
//    							codeStr.append(objPpNotificationHistoryVO.getFuelLevel()).append("~").append(desc);
//    							objPPNotRespType.setFuelLevel(codeStr.toString());
//                        	}
                        	
                            objPPNotRespType.setFuelLevel(objPpNotificationHistoryVO.getFuelLevel());
                        }
                        objPPNotRespType.setFuelAdded(objPpNotificationHistoryVO.getFuelAdded());
                        if (null != objPpNotificationHistoryVO.getGpsLatDisplay()
                                && !objPpNotificationHistoryVO.getGpsLatDisplay().isEmpty()) {
                        	objPPNotRespType.setGpsLatDisplay(objPpNotificationHistoryVO.getGpsLatDisplay());
                        }
                        if (null != objPpNotificationHistoryVO.getGpsLonDisplay()
                                && !objPpNotificationHistoryVO.getGpsLonDisplay().isEmpty()) {
                        	objPPNotRespType.setGpsLonDisplay(objPpNotificationHistoryVO.getGpsLonDisplay());
                        }
                        objPPNotRespTypeLst.add(objPPNotRespType);

                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {

            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        RMDLOGGER.debug("NotificationResource : Inside getNotificationHistory() method:::END");
        return objPPNotRespTypeLst;
    }

    /**
     * @param assetServiceStatusReq
     * @return This method is used for validating asset status
     */
    private static boolean validateAssetServiceStatusObject(
            final PpAssetServiceStatusRequestType assetServiceStatusReq) {
        if (assetServiceStatusReq != null) {
            if (null != assetServiceStatusReq.getCustomerId()
                    && !assetServiceStatusReq.getCustomerId().trim().equals(OMDConstants.EMPTY_STRING)
                    && !AppSecUtil.checkAlphaNumeric(assetServiceStatusReq.getCustomerId())) {
                return false;
            }
            if (null != assetServiceStatusReq.getRoadInitialName()
                    && !assetServiceStatusReq.getRoadInitialName().trim().equals(OMDConstants.EMPTY_STRING)
                    && !AppSecUtil.checkAlphaNumeric(assetServiceStatusReq.getRoadInitialName())) {
                return false;
            }
            if (null != assetServiceStatusReq.getRoadNo()
                    && !assetServiceStatusReq.getRoadNo().trim().equals(OMDConstants.EMPTY_STRING)
                    && !AppSecUtil.checkNumbersDot(assetServiceStatusReq.getRoadNo())) {
                return false;
            }
            if (null != assetServiceStatusReq.isPpService() && !assetServiceStatusReq.isPpService().booleanValue()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * This Method is used for retrieving cities for given country and state
     * code
     * 
     * @param countryCode
     *            , stateCode
     * @return List of cities
     * @throws RMDServiceException
     */

    @GET
    @Path(OMDConstants.GET_CUST_ROAD_INITIALS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RoadInitialType> getRoadInitialsForCustomer(@PathParam(OMDConstants.CUSTOMERID) final String customerId)
            throws RMDServiceException {
        List<RoadInitialType> roadInitialsList = null;
        try {
            MultivaluedMap<String, String> map = new MultivaluedMapImpl();
            map.add(OMDConstants.CUSTOMERID, customerId);
            int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(map, null, methodConstants, OMDConstants.CUSTOMERID)) {
                List<PPRoadInitialVO> custRoadInitialVOList = objNotificationServiceIntf
                        .getRoadInitialsForCustomer(customerId);
                if (RMDCommonUtility.isCollectionNotEmpty(custRoadInitialVOList)) {
                    roadInitialsList = new ArrayList<RoadInitialType>();
                    RoadInitialType roadInitialType = null;
                    PPRoadInitialVO roadInitialVO = null;
                    for (int i = 0; i < custRoadInitialVOList.size(); i++) {
                        roadInitialType = new RoadInitialType();
                        roadInitialVO = custRoadInitialVOList.get(i);
                        if (roadInitialVO.getRoadInitialName() != null) {
                            roadInitialType.setRoadInitialName(roadInitialVO.getRoadInitialName());
                        }
                        if (roadInitialVO.getRoadInitialNo() != null) {
                            roadInitialType.setRoadInitialNo(roadInitialVO.getRoadInitialNo());
                        }
                        roadInitialsList.add(roadInitialType);
                    }
                }
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (Exception ex) {
            
            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return roadInitialsList;
    }

    /* Validating the customer Id */
    private static boolean validateCustomerId(String customerId) {

        if (null != customerId && !customerId.isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(customerId)) {
                return false;
            }
        }

        return true;
    }

    /*
     * This method is used to validate the notification history detail method
     * inputs
     */
    public static boolean validateNotificationHistory(final PpNotificationRequestType objPpNotificationReqType) {

        if (null != objPpNotificationReqType.getCustomerId() && !objPpNotificationReqType.getCustomerId().isEmpty()) {
            if (!AppSecUtil
                    .checkAlphaNumeric(BeanUtility.stripXSSCharacters(objPpNotificationReqType.getCustomerId()))) {
                return false;
            }
        }

        if (null != objPpNotificationReqType.getRegion() && !objPpNotificationReqType.getRegion().isEmpty()) {
            if (!AppSecUtil
                    .checkAlphaNumericBrackets(BeanUtility.stripXSSCharacters(objPpNotificationReqType.getRegion()))) {
                return false;
            }
        }

        if (null != objPpNotificationReqType.getRoadInitialName()
                && !objPpNotificationReqType.getRoadInitialName().isEmpty()) {
            if (!AppSecUtil
                    .checkAlphaNumeric(BeanUtility.stripXSSCharacters(objPpNotificationReqType.getRoadInitialName()))) {
                return false;
            }
        }

        if (null != objPpNotificationReqType.getRoadNo() && !objPpNotificationReqType.getRoadNo().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(BeanUtility.stripXSSCharacters(objPpNotificationReqType.getRoadNo()))) {
                return false;
            }
        }

        return true;
    }

}

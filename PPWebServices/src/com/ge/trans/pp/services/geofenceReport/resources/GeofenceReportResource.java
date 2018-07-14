package com.ge.trans.pp.services.geofenceReport.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
import com.ge.trans.pp.services.geofenceReport.valueobjects.GeofenceReportRequestType;
import com.ge.trans.pp.services.geofenceReport.valueobjects.GeofenceReportResponseType;
import com.ge.trans.pp.services.geofenceReport.valueobjects.GeofenceReportListRequestType;
import com.ge.trans.pp.services.geofencereport.service.intf.GeofenceReportServiceIntf;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportListReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: May 14, 2014
 * @Date Modified : May 14, 2014
 * @Modified By :
 * @Contact :
 * @Description : This Class act as GeofenceReportResource Webservices and
 *              provide the proximity related funtionalities
 * @History :
 ******************************************************************************/
@Path(OMDConstants.GEOFENCE_REPORT_SERVICE)
@Component
public class GeofenceReportResource extends BaseResource {

    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(GeofenceReportResource.class);

    @Autowired
    private GeofenceReportServiceIntf objGeofenceReportServiceIntf;

    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    /**
     * This Method is used for retrieving GeofenceReport.
     * 
     * @param GeofenceReportRequestType
     * @return List<GeofenceReportResponseType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GEOFENCE_REPORT)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<GeofenceReportResponseType> getGeofenceReport(
            final GeofenceReportListRequestType geofenceRnRangeReqType) throws RMDServiceException {
        GeofenceReportReqVO objGeofenceReportReqVO = null;
        GeofenceReportResponseType objGeofenceReportRespType = null;
        List<GeofenceReportResponseType> objGeofenceReportRespTypeLst = new ArrayList<GeofenceReportResponseType>();
        List<GeofenceReportVO> objGeofenceReportVOList = null;
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar entryDt = null;
        String timeZone = getDefaultTimeZone();
        List<GeofenceReportRequestType> geofenceRoadNoRangeList = null;
        DateFormat dFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        List<GeofenceReportReqVO> geofenceReportReqVOList = new ArrayList<GeofenceReportReqVO>();
        GeofenceReportListReqVO objGeofenceRoadRangeReqVO = new GeofenceReportListReqVO();
        try {
            dFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            geofenceRoadNoRangeList = geofenceRnRangeReqType != null
                    ? geofenceRnRangeReqType.getGeofenceRequestTypeList() : null;

            if (geofenceRoadNoRangeList != null && geofenceRoadNoRangeList.size() > 0
                    && validateGeofenceReport(geofenceRoadNoRangeList)) {

                for (GeofenceReportRequestType objGeofenceReportReqType : geofenceRoadNoRangeList) {
                    objGeofenceReportReqVO = new GeofenceReportReqVO();
                    if (null != objGeofenceReportReqType.getCustomerId()
                            && !objGeofenceReportReqType.getCustomerId().isEmpty()) {
                        objGeofenceReportReqVO.setCustomerId(
                                BeanUtility.stripXSSCharacters(objGeofenceReportReqType.getCustomerId()));
                    }

                    if (null != objGeofenceReportReqType.getFleets()
                            && !objGeofenceReportReqType.getFleets().isEmpty()) {
                        objGeofenceReportReqVO
                                .setFleets(BeanUtility.stripXSSCharctersfromList(objGeofenceReportReqType.getFleets()));
                    }

                    if (null != objGeofenceReportReqType.getFromRoadNo()
                            && !objGeofenceReportReqType.getFromRoadNo().isEmpty()) {
                        objGeofenceReportReqVO.setFromRoadNo(
                                BeanUtility.stripXSSCharacters(objGeofenceReportReqType.getFromRoadNo()));
                    }

                    if (null != objGeofenceReportReqType.getToRoadNo()
                            && !objGeofenceReportReqType.getToRoadNo().isEmpty()) {
                        objGeofenceReportReqVO
                                .setToRoadNo(BeanUtility.stripXSSCharacters(objGeofenceReportReqType.getToRoadNo()));
                    }

                    if (null != objGeofenceReportReqType.getRoadInitial()
                            && !objGeofenceReportReqType.getRoadInitial().isEmpty()) {
                        objGeofenceReportReqVO.setRoadInitial(
                                BeanUtility.stripXSSCharacters(objGeofenceReportReqType.getRoadInitial()));
                    }

                    if (null != objGeofenceReportReqType.getFromDate()) {
                        objGeofenceReportReqVO.setFromDate(BeanUtility.stripXSSCharacters(dFormat
                                .format(objGeofenceReportReqType.getFromDate().toGregorianCalendar().getTime())));
                    }

                    if (null != objGeofenceReportReqType.getToDate()) {
                        objGeofenceReportReqVO.setToDate(BeanUtility.stripXSSCharacters(
                                dFormat.format(objGeofenceReportReqType.getToDate().toGregorianCalendar().getTime())));
                    }

                    geofenceReportReqVOList.add(objGeofenceReportReqVO);
                }

                objGeofenceRoadRangeReqVO.setGeofenceReportReqVOList(geofenceReportReqVOList);
                objGeofenceRoadRangeReqVO.setRoleId(geofenceRnRangeReqType.getRoleId());

                if (null != geofenceRnRangeReqType.getGeofences() && !geofenceRnRangeReqType.getGeofences().isEmpty()) {
                    objGeofenceRoadRangeReqVO
                            .setGeofences(BeanUtility.stripXSSCharctersfromList(geofenceRnRangeReqType.getGeofences()));
                }
                objGeofenceReportVOList = objGeofenceReportServiceIntf.getGeofenceReport(objGeofenceRoadRangeReqVO);

                if (RMDCommonUtility.isCollectionNotEmpty(objGeofenceReportVOList)) {
                    DatatypeFactory dt = DatatypeFactory.newInstance();
                    objGregorianCalendar = new GregorianCalendar();
                    for (GeofenceReportVO objGeofenceReportVO : objGeofenceReportVOList) {
                        objGeofenceReportRespType = new GeofenceReportResponseType();
                        objGeofenceReportRespType.setCustomerId(objGeofenceReportVO.getCustomerId());
                        objGeofenceReportRespType.setRoadInitial(objGeofenceReportVO.getRoadInitial());
                        objGeofenceReportRespType.setRoadNumber(objGeofenceReportVO.getRoadNumber());
                        objGeofenceReportRespType.setGeofenceName(objGeofenceReportVO.getGeofenceName());
                        if (null != objGeofenceReportVO.getEntryTime()) {
                            objGregorianCalendar.setTime(objGeofenceReportVO.getEntryTime());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            entryDt = dt.newXMLGregorianCalendar(objGregorianCalendar);
                            objGeofenceReportRespType.setEntryTime(entryDt);

                        }
                        objGeofenceReportRespType.setTotalTime(objGeofenceReportVO.getTotalTime());
                        
//                        if (null != objGeofenceReportVO.getEntryFuelLevel() && objGeofenceReportVO.getEntryFuelLevel().length()!=0 && Integer.parseInt(objGeofenceReportVO.getEntryFuelLevel()) > 10000) {
//                        	String desc = "ERC";
//                        	String codeVal = desc + objGeofenceReportVO.getEntryFuelLevel();
//							StringBuilder codeStr = new StringBuilder();
//							codeStr.append(objGeofenceReportVO.getEntryFuelLevel()).append("~").append(desc);
//							objGeofenceReportRespType.setEntryFuelLevel(codeStr.toString());
//                        }else{
//                        	objGeofenceReportRespType.setEntryFuelLevel(objGeofenceReportVO.getEntryFuelLevel());
//                        }
                        
                        objGeofenceReportRespType.setEntryFuelLevel(objGeofenceReportVO.getEntryFuelLevel());
                        objGeofenceReportRespType.setTotalFuel(objGeofenceReportVO.getTotalFuel());

                        objGeofenceReportRespTypeLst.add(objGeofenceReportRespType);

                        objGeofenceReportRespType.setFleet(objGeofenceReportVO.getFleet());

                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {

            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        RMDLOGGER.debug("GeofenceReportResource : Inside getGeofenceReport() method:::END");
        return objGeofenceReportRespTypeLst;
    }

    /*
     * This method is used to validate the GeofenceReport detail method inputs
     */
    public static boolean validateGeofenceReport(final List<GeofenceReportRequestType> geofenceRoadNoRangeList) {

        for (GeofenceReportRequestType objGeofenceReportReqType : geofenceRoadNoRangeList) {

            if (null != objGeofenceReportReqType.getCustomerId()
                    && !objGeofenceReportReqType.getCustomerId().isEmpty()) {
                if (!AppSecUtil
                        .checkAlphaNumeric(BeanUtility.stripXSSCharacters(objGeofenceReportReqType.getCustomerId()))) {
                    return false;
                }
            }

            // Changes for Fleets Based Search - RI is not mandatory
            if (null != objGeofenceReportReqType.getRoadInitial()
                    && !objGeofenceReportReqType.getRoadInitial().isEmpty()) {

                if (!AppSecUtil
                        .checkAlphaNumeric(BeanUtility.stripXSSCharacters(objGeofenceReportReqType.getRoadInitial()))) {
                    return false;
                }

                if (null != objGeofenceReportReqType.getFromRoadNo()
                        && !objGeofenceReportReqType.getFromRoadNo().isEmpty()) {
                    if (!AppSecUtil.checkAlphaNumeric(
                            BeanUtility.stripXSSCharacters(objGeofenceReportReqType.getFromRoadNo()))) {
                        return false;
                    }
                }

                if (null != objGeofenceReportReqType.getToRoadNo()
                        && !objGeofenceReportReqType.getToRoadNo().isEmpty()) {
                    if (!AppSecUtil.checkAlphaNumeric(
                            BeanUtility.stripXSSCharacters(objGeofenceReportReqType.getToRoadNo()))) {
                        return false;
                    }
                }
            }
        }

        /*
         * if (null != objGeofenceReportReqType.getRoadInitial() &&
         * !objGeofenceReportReqType.getRoadInitial().isEmpty()) { if
         * (!AppSecUtil.checkAlphaNumeric(objGeofenceReportReqType
         * .getRoadInitial())) { return false; } } if (null !=
         * objGeofenceReportReqType.getFromRoadNo() &&
         * !objGeofenceReportReqType.getFromRoadNo().isEmpty()) { if
         * (!AppSecUtil.checkAlphaNumeric(objGeofenceReportReqType
         * .getFromRoadNo())) { return false; } } if (null !=
         * objGeofenceReportReqType.getToRoadNo() &&
         * !objGeofenceReportReqType.getToRoadNo().isEmpty()) { if
         * (!AppSecUtil.checkAlphaNumeric(objGeofenceReportReqType
         * .getToRoadNo())) { return false; } } }
         */
        return true;

    }
}

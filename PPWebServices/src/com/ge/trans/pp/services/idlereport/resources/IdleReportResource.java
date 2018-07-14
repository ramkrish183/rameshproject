package com.ge.trans.pp.services.idlereport.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.ge.trans.pp.services.idlereport.service.intf.IdleReportServiceIntf;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportDetailsResponseVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryReqVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryResponseVO;
import com.ge.trans.pp.services.idlereport.valueobjects.IdleReportDetailResponseRowType;
import com.ge.trans.pp.services.idlereport.valueobjects.IdleReportDetailResponseType;
import com.ge.trans.pp.services.idlereport.valueobjects.IdleReportDetailType;
import com.ge.trans.pp.services.idlereport.valueobjects.IdleReportRequestType;
import com.ge.trans.pp.services.idlereport.valueobjects.IdleReportSummaryResponseRowType;
import com.ge.trans.pp.services.idlereport.valueobjects.IdleReportSummaryResponseType;
import com.ge.trans.pp.services.proximity.resources.ProximityResource;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;

@Path(OMDConstants.IDLE_REPORT_SERVICE)
@Component
public class IdleReportResource extends BaseResource {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ProximityResource.class);

    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(ProximityResource.class);

    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    IdleReportServiceIntf objIdleReportServiceIntf;

    /**
     * This Method is used for retrieving IDleReport Summary parameters.Input
     * parameters used here is a object of type IdleReportRequestType
     * 
     * @param objIdleReportReqType
     * @return IdleReportSummaryResponseType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_IDLE_REPORT_SUMMARY)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public IdleReportSummaryResponseType getIdleReportSummary(final IdleReportRequestType objIdleReportReqType)
            throws RMDServiceException {
        IdleReportSummaryReqVO objIdleReportSummaryVO = new IdleReportSummaryReqVO();
        List<IdleReportSummaryResponseVO> objSummaryRespVOLst = null;
        IdleReportSummaryResponseRowType objIdleReportSummRespRowType = null;
        List<IdleReportSummaryResponseRowType> objIdleReportSummRespRowTypeLst = new ArrayList<IdleReportSummaryResponseRowType>();
        IdleReportSummaryResponseType objIdleReportSummRespType = new IdleReportSummaryResponseType();
        try {

            if (validateSummaryRequest(objIdleReportReqType)) {
                if (null != objIdleReportReqType.getCustomerId() && !objIdleReportReqType.getCustomerId().isEmpty()) {
                    objIdleReportSummaryVO
                            .setCustomerId(BeanUtility.stripXSSCharacters(objIdleReportReqType.getCustomerId()));
                }

                if (null != objIdleReportReqType.getRegion() && !objIdleReportReqType.getRegion().isEmpty()) {
                    objIdleReportSummaryVO.setRegion(BeanUtility.stripXSSCharacters(objIdleReportReqType.getRegion()));
                }

                if (null != objIdleReportReqType.getProductNames()
                        && !objIdleReportReqType.getProductNames().isEmpty()) {
                    BeanUtility.copyProductNameToServiceVO(objIdleReportReqType.getProductNames(),
                            objIdleReportSummaryVO);
                }
                objIdleReportSummaryVO.setElapsedTime(
                        BeanUtility.stripXSSCharacters(String.valueOf(objIdleReportReqType.getElapsedTime())));

                objSummaryRespVOLst = objIdleReportServiceIntf.getIdleReportSummary(objIdleReportSummaryVO);

                if (RMDCommonUtility.isCollectionNotEmpty(objSummaryRespVOLst)) {
                    for (IdleReportSummaryResponseVO objIdleReportSummResponseVO : objSummaryRespVOLst) {
                       objIdleReportSummRespRowType = new IdleReportSummaryResponseRowType();
						objIdleReportSummRespRowType
								.setRegion(EsapiUtil.stripXSSCharacters((String)
	                        			(objIdleReportSummResponseVO
										.getRegion())));
						if (null != objIdleReportSummResponseVO
								.getTotalReported()
								&& !objIdleReportSummResponseVO
										.getTotalReported().isEmpty()) {
							objIdleReportSummRespRowType
									.setTotalRepotedCnt(Integer
											.parseInt(EsapiUtil.stripXSSCharacters((String)
				                        			(objIdleReportSummResponseVO
													.getTotalReported()))));
						}
						if (null != objIdleReportSummResponseVO
								.getNtMovingEngOn()
								&& !objIdleReportSummResponseVO
										.getNtMovingEngOn().isEmpty()) {
							objIdleReportSummRespRowType
									.setNotMovingEngineOnCnt(Integer
											.parseInt(EsapiUtil.stripXSSCharacters((String)
				                        			(objIdleReportSummResponseVO
													.getNtMovingEngOn()))));
						}
						if (null != objIdleReportSummResponseVO
								.getNtMovingEngOff()
								&& !objIdleReportSummResponseVO
										.getNtMovingEngOff().isEmpty()) {
							objIdleReportSummRespRowType
									.setNotMovingEngineOffCnt(Integer
											.parseInt(EsapiUtil.stripXSSCharacters((String)
				                        			(objIdleReportSummResponseVO
													.getNtMovingEngOff()))));
						}
						if (null != objIdleReportSummResponseVO
								.getNtMovingCnt()
								&& !objIdleReportSummResponseVO
										.getNtMovingCnt().isEmpty()) {
							objIdleReportSummRespRowType
									.setNotMovingCnt(Integer
											.parseInt(EsapiUtil.stripXSSCharacters((String)
				                        			(objIdleReportSummResponseVO
													.getNtMovingCnt()))));
						}
						if (null != objIdleReportSummResponseVO.getEngOnCnt()
								&& !objIdleReportSummResponseVO.getEngOnCnt()
										.isEmpty()) {
							objIdleReportSummRespRowType.setEngineOnCnt(Integer
									.parseInt(EsapiUtil.stripXSSCharacters((String)
		                        			(objIdleReportSummResponseVO
											.getEngOnCnt()))));
						}
						if (null != objIdleReportSummResponseVO.getDwellCnt()
								&& !objIdleReportSummResponseVO.getDwellCnt()
										.isEmpty()) {
							objIdleReportSummRespRowType.setDwellCnt(Integer
									.parseInt(EsapiUtil.stripXSSCharacters((String)
		                        			(objIdleReportSummResponseVO
											.getDwellCnt()))));
						}
						if (null != objIdleReportSummResponseVO.getOrderBy()
								&& !objIdleReportSummResponseVO.getOrderBy()
										.isEmpty()) {
							objIdleReportSummRespRowType.setOrderBy(Integer
									.parseInt(EsapiUtil.stripXSSCharacters((String)
		                        			(objIdleReportSummResponseVO
											.getOrderBy()))));
						}
                        objIdleReportSummRespRowTypeLst.add(objIdleReportSummRespRowType);
                    }
                    objIdleReportSummRespType.setIdleReportSummaryResponseRow(objIdleReportSummRespRowTypeLst);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {

            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("IdleReportResource : Inside getIdleReportSummary() method:::END");
        return objIdleReportSummRespType;

    }

    /**
     * This method is used to validate the data passed as an input for getting
     * IdlereportSummary Details
     * 
     * @param objIdleReportReqType
     * @return boolean
     */
    public static boolean validateSummaryRequest(final IdleReportRequestType objIdleReportReqType) {

        if (null != objIdleReportReqType.getRegion() && !objIdleReportReqType.getRegion().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericBrackets(objIdleReportReqType.getRegion())) {
                return false;
            }
        }

        if (null != objIdleReportReqType.getCustomerId() && !objIdleReportReqType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objIdleReportReqType.getCustomerId())) {
                return false;
            }
        }

        if (objIdleReportReqType.getElapsedTime() > 0) {
            if (!AppSecUtil.checkNumbersHyphen(String.valueOf(objIdleReportReqType.getElapsedTime()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * This Method is used for retrieving IDleReport Details Input parameters
     * used here is a object of type IdleReportDetailType
     * 
     * @param objIdleReportDetailType
     * @return IdleReportDetailResponseType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_IDLE_REPORT_DETAILS)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public IdleReportDetailResponseType getIdleReportDetails(final IdleReportDetailType objIdleReportDetailType)
            throws RMDServiceException {
        IdleReportSummaryReqVO objIdleReportSummaryVO = new IdleReportSummaryReqVO();
        List<IdleReportDetailsResponseVO> objDetailRespVOLst = null;
        IdleReportDetailResponseRowType objIdleReportDevRespRowType = null;
        List<IdleReportDetailResponseRowType> objIdleReportDetRespRowTypeLst = new ArrayList<IdleReportDetailResponseRowType>();
        IdleReportDetailResponseType objIdleReportDetRespType = new IdleReportDetailResponseType();
        XMLGregorianCalendar dtXmlCalTime = null;
        GregorianCalendar objGregorianCalendar = null;
        final SimpleDateFormat dFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT);
        String timeZone = getDefaultTimeZone();
        try {

            if (validateDetailRequest(objIdleReportDetailType)) {
                if (null != objIdleReportDetailType.getCustomerId()
                        && !objIdleReportDetailType.getCustomerId().isEmpty()) {
                    objIdleReportSummaryVO
                            .setCustomerId(BeanUtility.stripXSSCharacters(objIdleReportDetailType.getCustomerId()));
                }

                if (null != objIdleReportDetailType.getRegion() && !objIdleReportDetailType.getRegion().isEmpty()) {
                    objIdleReportSummaryVO
                            .setRegion(BeanUtility.stripXSSCharacters(objIdleReportDetailType.getRegion()));
                }

                objIdleReportSummaryVO.setElapsedTime(
                        BeanUtility.stripXSSCharacters(String.valueOf(objIdleReportDetailType.getElapsedTime())));
                if (null != objIdleReportDetailType.getIdleType() && !objIdleReportDetailType.getIdleType().isEmpty()) {
                    objIdleReportSummaryVO
                            .setIdleType(BeanUtility.stripXSSCharacters(objIdleReportDetailType.getIdleType()));
                }
                if (null != objIdleReportDetailType.getProductNames()
                        && !objIdleReportDetailType.getProductNames().isEmpty()) {
                    BeanUtility.copyProductNameToServiceVO(objIdleReportDetailType.getProductNames(),
                            objIdleReportSummaryVO);
                }
                objDetailRespVOLst = objIdleReportServiceIntf.getIdleReportDetails(objIdleReportSummaryVO);

                if (RMDCommonUtility.isCollectionNotEmpty(objDetailRespVOLst)) {
                    for (IdleReportDetailsResponseVO objIdleReportDetResponseVO : objDetailRespVOLst) {
                        objIdleReportDevRespRowType = new IdleReportDetailResponseRowType();
                        objIdleReportDevRespRowType.setRegion(objIdleReportDetResponseVO.getRegion());
                        objIdleReportDevRespRowType.setSubRegion(objIdleReportDetResponseVO.getSubRegion());
                        objIdleReportDevRespRowType.setCustomerId(objIdleReportDetResponseVO.getCustomerId());
                        objIdleReportDevRespRowType.setRoadInitialName(objIdleReportDetResponseVO.getRoadInitial());
                        objIdleReportDevRespRowType.setRoadInitialNo(objIdleReportDetResponseVO.getRoadNumber());
                        if (null != objIdleReportDetResponseVO.getNtMovingDuration()
                                && !objIdleReportDetResponseVO.getNtMovingDuration().isEmpty()) {
                            objIdleReportDevRespRowType.setNotMovingDuration(
                                    Float.parseFloat(objIdleReportDetResponseVO.getNtMovingDuration()));
                        }
                        if (null != objIdleReportDetResponseVO.getEngOnDuration()
                                && !objIdleReportDetResponseVO.getEngOnDuration().isEmpty()) {
                            objIdleReportDevRespRowType
                                    .setEngineDuration(Float.parseFloat(objIdleReportDetResponseVO.getEngOnDuration()));
                        }

                        objIdleReportDevRespRowType.setEngineState(objIdleReportDetResponseVO.getCurrEngState());
                        if (null != objIdleReportDetResponseVO.getDwellDuration()
                                && !objIdleReportDetResponseVO.getDwellDuration().isEmpty()) {
                            objIdleReportDevRespRowType
                                    .setDwellDuration(Float.parseFloat(objIdleReportDetResponseVO.getDwellDuration()));
                        }
                        objIdleReportDevRespRowType.setDistance(objIdleReportDetResponseVO.getDistance());
                        objIdleReportDevRespRowType.setLocation(objIdleReportDetResponseVO.getLocation());
                        objIdleReportDevRespRowType.setStateCode(objIdleReportDetResponseVO.getState());

                        if (null != objIdleReportDetResponseVO.getIdleSince()
                                && !objIdleReportDetResponseVO.getIdleSince().isEmpty()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(dFormat.parse(objIdleReportDetResponseVO.getIdleSince()));
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            dtXmlCalTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            objIdleReportDevRespRowType.setIdleSince(dtXmlCalTime);
                        }
                        if (null != objIdleReportDetResponseVO.getLastMsgTime()
                                && !objIdleReportDetResponseVO.getLastMsgTime().isEmpty()) {
                            dtXmlCalTime = null;
                            objGregorianCalendar = new GregorianCalendar();
                            Date lastMsgTime = RMDCommonUtility.stringToGMTDate(
                                    RMDCommonUtility.convertObjectToString(objIdleReportDetResponseVO.getLastMsgTime()),
                                    RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT);

                            objGregorianCalendar = RMDCommonUtility.getGMTGregorianCalendar(lastMsgTime);
                            dtXmlCalTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            objIdleReportDevRespRowType.setLastMsgTime(dtXmlCalTime);
                        }

                        objIdleReportDetRespRowTypeLst.add(objIdleReportDevRespRowType);
                    }
                    objIdleReportDetRespType.setIdleReportDetailResponseRow(objIdleReportDetRespRowTypeLst);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {

            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("IdleReportResource : Inside getIdleReportDetails() method:::END");
        return objIdleReportDetRespType;

    }

    /**
     * This method is used to validate the data passed as an input for getting
     * IdlereportDetails
     * 
     * @param objIdleReportReqType
     * @return
     */
    public static boolean validateDetailRequest(final IdleReportDetailType objIdleReportReqType) {

        if (null != objIdleReportReqType.getRegion() && !objIdleReportReqType.getRegion().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericBrackets(objIdleReportReqType.getRegion())) {
                return false;
            }
        }

        if (null != objIdleReportReqType.getCustomerId() && !objIdleReportReqType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objIdleReportReqType.getCustomerId())) {
                return false;
            }
        }

        if (objIdleReportReqType.getElapsedTime() > 0) {
            if (!AppSecUtil.checkNumbersHyphen(String.valueOf(objIdleReportReqType.getElapsedTime()))) {
                return false;
            }
        }

        return true;
    }
}

package com.ge.trans.rmd.services.cases.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.type.DateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.cases.service.intf.FindCaseLiteEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.CaseInfoType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CasesRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.SolutionInfoType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.CASE_SERVICE_LITE)
@Component
public class CaseLiteResource extends BaseResource {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CaseLiteResource.class);
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    private FindCaseLiteEoaServiceIntf findCaseLiteEoaServiceIntf;

    @SuppressWarnings("unchecked")
    @POST
    @Path(OMDConstants.GET_DELIVERED_CASES_LITE)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CaseResponseType> getDeliveredCases(final CasesRequestType objCasesRequestType)
            throws RMDServiceException {

        final List<CaseResponseType> caseResLst = new ArrayList<CaseResponseType>();
        List<SelectCaseHomeVO> caseList = null;
        ListIterator<SelectCaseHomeVO> caseLstIter = null;
        FindCaseServiceVO findCaseServiceVO = null;
        SelectCaseHomeVO selCaseHomeInfo = null;
        // End - EOA
        GregorianCalendar objGregorianCalendar;
        GregorianCalendar objCloseGregorianCalendar;
        XMLGregorianCalendar createdDate;
        CaseInfoType caseInfo = null;
        CaseResponseType caseResType = null;
        String timeZone = getDefaultTimeZone();
        XMLGregorianCalendar delvDate;
        try {
            findCaseServiceVO = new FindCaseServiceVO();
            if (null != objCasesRequestType.getProdAssetMap() && objCasesRequestType.getProdAssetMap().size() > 0) {
                CMBeanUtility.copyCustomerAssetToServiceVO(objCasesRequestType.getProdAssetMap(), findCaseServiceVO);
            }
            if (validateGetCases(objCasesRequestType)) {
                CMBeanUtility.copyJaxbObjToServiceVO(findCaseServiceVO, objCasesRequestType);

                if (null != objCasesRequestType.getUrgency() && !objCasesRequestType.getUrgency().isEmpty()) {
                    String urgency = objCasesRequestType.getUrgency();
                    if (!RMDCommonUtility.isNullOrEmpty(urgency)) {
                        String[] strUrgencyArray = RMDCommonUtility.splitString(urgency,
                                RMDCommonConstants.COMMMA_SEPARATOR);
                        findCaseServiceVO.setUrgency(strUrgencyArray);
                    }
                }

                if (null != objCasesRequestType.getRxId() && !objCasesRequestType.getRxId().isEmpty()) {
                    String rxId = objCasesRequestType.getRxId();
                    if (!RMDCommonUtility.isNullOrEmpty(rxId)) {
                        String[] strRxIdArray = RMDCommonUtility.splitString(rxId, RMDCommonConstants.COMMMA_SEPARATOR);
                        findCaseServiceVO.setRxIds(strRxIdArray);
                    }
                }

                if (null != objCasesRequestType.getCaseType() && !objCasesRequestType.getCaseType().isEmpty()) {
                    String casetype = objCasesRequestType.getCaseType();
                    if (!RMDCommonUtility.isNullOrEmpty(casetype)) {
                        String[] strCasetypeArray = RMDCommonUtility.splitString(casetype,
                                RMDCommonConstants.COMMMA_SEPARATOR);
                        findCaseServiceVO.setCaseType(strCasetypeArray);
                    }
                }

                if (null != objCasesRequestType.getModelId() && !objCasesRequestType.getModelId().isEmpty()) {
                    String modelId = objCasesRequestType.getModelId();
                    if (!RMDCommonUtility.isNullOrEmpty(modelId)) {
                        String[] modelIdArray = RMDCommonUtility.splitString(modelId,
                                RMDCommonConstants.COMMMA_SEPARATOR);
                        findCaseServiceVO.setModelId(modelIdArray);
                    }
                }

                if (null != objCasesRequestType.getFleetId() && !objCasesRequestType.getFleetId().isEmpty()) {
                    String fleetId = objCasesRequestType.getFleetId();
                    if (!RMDCommonUtility.isNullOrEmpty(fleetId)) {
                        String[] fleetIdArray = RMDCommonUtility.splitString(fleetId,
                                RMDCommonConstants.COMMMA_SEPARATOR);
                        findCaseServiceVO.setFleetId(fleetIdArray);
                    }
                }

                if (null != objCasesRequestType.getRxCaseID() && !objCasesRequestType.getRxCaseID().isEmpty()) {
                    String rxCaseID = objCasesRequestType.getRxCaseID();
                    if (!RMDCommonUtility.isNullOrEmpty(rxCaseID)) {
                        findCaseServiceVO.setStrRxCaseId(rxCaseID);
                    }
                }

                if (null != objCasesRequestType.getCustomerId() && !objCasesRequestType.getCustomerId().isEmpty()) {
                    String customerId = objCasesRequestType.getCustomerId();
                    if (!RMDCommonUtility.isNullOrEmpty(customerId)) {
                        findCaseServiceVO.setStrCustomerId(customerId);
                    }
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCasesRequestType.getScreenName())) {
                    findCaseServiceVO.setScreenName(objCasesRequestType.getScreenName());
                }
                if (null != objCasesRequestType.getCaseID() && !objCasesRequestType.getCaseID().isEmpty()) {
       
                        findCaseServiceVO.setStrRxCaseId(objCasesRequestType.getCaseID());
                }
                if (null != objCasesRequestType.getAssetNumber() && !objCasesRequestType.getAssetNumber().isEmpty()) {
                    
                    findCaseServiceVO.setStrAssetNumber(objCasesRequestType.getAssetNumber());
            }
                caseList = findCaseLiteEoaServiceIntf.getDeliveredCases(findCaseServiceVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

            if (RMDCommonUtility.isCollectionNotEmpty(caseList)) {
                LOG.debug("Size of the list " + caseList.size());

                caseLstIter = caseList.listIterator();
                DatatypeFactory dtf = DatatypeFactory.newInstance();

                while (caseLstIter.hasNext()) {
                    selCaseHomeInfo = caseLstIter.next();
                    caseInfo = new CaseInfoType();
                    caseResType = new CaseResponseType();
                    CMBeanUtility.copyCaseHomeVOToCaseInfoType(selCaseHomeInfo, caseInfo);
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(selCaseHomeInfo.getDtCreationDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    createdDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                    caseInfo.setCreatedDate(createdDate);
                    caseResType.setCustomerName(selCaseHomeInfo.getStrcustomerName());
                    CMBeanUtility.copyCaseHomeVOToCaseResType(selCaseHomeInfo, caseResType);

                    if (selCaseHomeInfo.getArlRecomDelv() != null && !selCaseHomeInfo.getArlRecomDelv().isEmpty()) {

                        final List<SolutionInfoType> lSolutionInfoType = new ArrayList<SolutionInfoType>();
                        /*
                         * for (RecomDelvServiceVO recomDelvServiceVO :
                         * selCaseHomeInfo .getArlRecomDelv()) {
                         */
                        // End -OMD
                        // start-EOA
                        for (RecomDelvServiceVO recomDelvServiceVO : selCaseHomeInfo.getArlRecomDelv()) {
                            // End - EOA
                            final SolutionInfoType solutionInfoType = new SolutionInfoType();
                            solutionInfoType.setSolutionCaseID(recomDelvServiceVO.getStrRxCaseId());
                            solutionInfoType.setSolutionTitle(recomDelvServiceVO.getStrRxTitle());
                            solutionInfoType.setUrgency(recomDelvServiceVO.getStrUrgRepair());
                            solutionInfoType.setLocomotiveImpact(recomDelvServiceVO.getLocomotiveImpact());
                            solutionInfoType.setSolutionNotes(recomDelvServiceVO.getSolutionNotes());
                            solutionInfoType.setEstmRepTime(recomDelvServiceVO.getStrEstmRepTime());
							if (recomDelvServiceVO.getDtRxClosedDate() != null) {
								solutionInfoType
										.setSolutionCloseDate(getGregorianCalendarTime(
												recomDelvServiceVO
														.getDtRxClosedDate(),
												timeZone, dtf));
							}
                          
							if (null != recomDelvServiceVO.getDtRxDelvDate()) {
								solutionInfoType
										.setSolutionDelvDate(getGregorianCalendarTime(
												recomDelvServiceVO
														.getDtRxDelvDate(),
												timeZone, dtf));
							}
                           
                            //New Field "reissueRxDelvDate" added for User Story US291422
							if (recomDelvServiceVO.getReissuedRxDelvDate() != null) {
								solutionInfoType
										.setReissueRxDelvDate(getGregorianCalendarTime(
												recomDelvServiceVO
														.getReissuedRxDelvDate(),
												timeZone, dtf));
							}
                            
                            lSolutionInfoType.add(solutionInfoType);
                            caseResType.setSolutionInfo(lSolutionInfoType);
                        }

                    }

                    caseResType.setCaseInfo(caseInfo);
                    caseResLst.add(caseResType);

                }

            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

            }*/
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseResLst;
    }

    /**
     * This is a method to get all the cases for an asset
     * 
     * @param FindCaseServiceVO
     * @return List<SelectCaseHomeVO>
     * @throws RMDDAOException
     */
    @SuppressWarnings("unchecked")
    @POST
    @Path(OMDConstants.GET_ASSET_CASES_LITE)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CaseResponseType> getAssetCases(final CasesRequestType objCasesRequestType) throws RMDServiceException {
        List<SelectCaseHomeVO> caseList = null;
        final List<CaseResponseType> caseResLst = new ArrayList<CaseResponseType>();
        ListIterator<SelectCaseHomeVO> caseLstIter = null;
        FindCaseServiceVO findCaseServiceVO = null;
        GregorianCalendar objGregorianCalendar;
        GregorianCalendar objCloseGregorianCalendar;
        XMLGregorianCalendar createdDate;
        XMLGregorianCalendar closedDate;
        SelectCaseHomeVO selCaseHomeInfo = null;
        CaseInfoType caseInfo = null;
        CaseResponseType caseResType = null;
        String timeZone = getDefaultTimeZone();
        try {
            findCaseServiceVO = new FindCaseServiceVO();
            if (null != objCasesRequestType.getProdAssetMap() && objCasesRequestType.getProdAssetMap().size() > 0) {
                CMBeanUtility.copyCustomerAssetToServiceVO(objCasesRequestType.getProdAssetMap(), findCaseServiceVO);
            }
            if (validateGetCases(objCasesRequestType)) {
                CMBeanUtility.copyJaxbObjToServiceVO(findCaseServiceVO, objCasesRequestType);
                if ((findCaseServiceVO.getStrCaseStatus()).equalsIgnoreCase(RMDCommonConstants.CLOSED)) {
                    caseList = findCaseLiteEoaServiceIntf.getAssetClosedCases(findCaseServiceVO);
                } else {
                    caseList = findCaseLiteEoaServiceIntf.getAssetNonClosedCases(findCaseServiceVO);
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
            if (RMDCommonUtility.isCollectionNotEmpty(caseList)) {
                LOG.debug("Size of the list " + caseList.size());
                long startTime = System.currentTimeMillis();
                caseLstIter = caseList.listIterator();
                DatatypeFactory dtf = DatatypeFactory.newInstance();
                while (caseLstIter.hasNext()) {
                    selCaseHomeInfo = caseLstIter.next();
                    caseInfo = new CaseInfoType();
                    caseResType = new CaseResponseType();
                    CMBeanUtility.copyCaseHomeVOToCaseInfoType(selCaseHomeInfo, caseInfo);
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(selCaseHomeInfo.getDtCreationDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    createdDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                    caseInfo.setCreatedDate(createdDate);
                    CMBeanUtility.copyCaseHomeVOToCaseResType(selCaseHomeInfo, caseResType);
                    if ((OMDConstants.CLOSED).equalsIgnoreCase(caseInfo.getCaseStatus())
                            && selCaseHomeInfo.getDtLastUpdatedDate() != null) {
                        objCloseGregorianCalendar = new GregorianCalendar();
                        objCloseGregorianCalendar.setTime(selCaseHomeInfo.getDtLastUpdatedDate());
                        RMDCommonUtility.setZoneOffsetTime(objCloseGregorianCalendar, timeZone);
                        closedDate = dtf.newXMLGregorianCalendar(objCloseGregorianCalendar);
                        caseInfo.setClosedDate(closedDate);

                    }
                    caseResType.setCaseInfo(caseInfo);
                    caseResLst.add(caseResType);

                }
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

            }*/
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseResLst;
    }

    public static boolean validateGetCases(final CasesRequestType objCasesRequestType) {

        if (null != objCasesRequestType.getAssetNumber() && !objCasesRequestType.getAssetNumber().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objCasesRequestType.getAssetNumber())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getRxCaseID() && !objCasesRequestType.getRxCaseID().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getRxCaseID())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getCustomerId() && !objCasesRequestType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getCustomerId())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getAssetGrName() && !objCasesRequestType.getAssetGrName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getAssetGrName())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getEstRepTm() && !objCasesRequestType.getEstRepTm().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getEstRepTm())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getSolutionId() && !objCasesRequestType.getSolutionId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getSolutionId())) {
                return false;
            }
        }
        if (null != objCasesRequestType.getNoDays()) {
            if (!AppSecUtil.checkIntNumber(objCasesRequestType.getNoDays())) {
                return false;
            }
        }
        return true;
    }
    
	private XMLGregorianCalendar getGregorianCalendarTime(Date inputdate,
			String timeZone, DatatypeFactory dtf) {
		XMLGregorianCalendar objXMLGregorianCalendar = null;
		GregorianCalendar objGregorianCalendar = new GregorianCalendar();
		objGregorianCalendar.setTime(inputdate);
		RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
		objXMLGregorianCalendar = dtf
				.newXMLGregorianCalendar(objGregorianCalendar);
		return objXMLGregorianCalendar;
	}
}

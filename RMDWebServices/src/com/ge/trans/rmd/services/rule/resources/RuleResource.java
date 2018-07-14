/*** ============================================================
 * Classification: GE Confidential
 * File : RuleResource.java
 * Description :
 * Package : com.ge.trans.rmd.services.rule.resources
 * Author : iGATE Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 11, 2011
 * History
 * Modified By : iGATE Patni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.rule.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.common.valueobjects.DpdCmprulSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.DpdFinrulSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.DpdRulMetricsSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.DpdRuldefSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.DpdRulhisSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.DpdSimrulSearchVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.ManualRunServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleCommonServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleTesterServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleTracerServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RunRecreatorServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.ViewRuleServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.VirtualServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ClearingLogicRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ComplexRuleLinkServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ComplexRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalVirtualServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.JdpadResultServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.JdpadSearchVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ManualRunServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefConfigServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefCustomerServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefFleetServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefModelServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefinitionServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleHistoryServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerServiceCriteriaVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerServiceResultVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTrackingServiceResultVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceCriteriaVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SimpleRuleClauseServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SimpleRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualDefConfigServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualDefCustomerServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualDefFleetServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualDefinitionServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualEquationServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualFilterServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualServiceVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDActiveRuleException;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.AlertRuleParmVO;
import com.ge.trans.rmd.common.valueobjects.AlertRuleVO;
import com.ge.trans.rmd.common.valueobjects.AlertRunsVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RuleByFamilyVO;
import com.ge.trans.rmd.common.valueobjects.RuleParmVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.Fleet;
import com.ge.trans.rmd.services.cases.valueobjects.AssetHeaderResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.AlertFiringDetailsResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.AlertRuleParmResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.AlertRuleResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.AlertRunSearchResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.ClearingLogicRule;
import com.ge.trans.rmd.services.rule.valueobjects.ComplexRule;
import com.ge.trans.rmd.services.rule.valueobjects.ComplexRuleLink;
import com.ge.trans.rmd.services.rule.valueobjects.FinalRuleRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.FinalRuleResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.FinalVirtualRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.FinalVirtualResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.ManualRunRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.ManualRunResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefResponse;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinition;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionConfig;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionCustomer;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionFleet;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionModel;
import com.ge.trans.rmd.services.rule.valueobjects.RuleFunction;
import com.ge.trans.rmd.services.rule.valueobjects.RuleHistory;
import com.ge.trans.rmd.services.rule.valueobjects.RuleMetricsResponse;
import com.ge.trans.rmd.services.rule.valueobjects.RuleParmData;
import com.ge.trans.rmd.services.rule.valueobjects.RuleRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.RuleResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.RuleTesterRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.RuleTrackingResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.RunReCreateRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.RunRecreatorResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.SearchComplexRule;
import com.ge.trans.rmd.services.rule.valueobjects.SearchFinalRuleResponse;
import com.ge.trans.rmd.services.rule.valueobjects.SearchSimpleRule;
import com.ge.trans.rmd.services.rule.valueobjects.SimpleRule;
import com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause;
import com.ge.trans.rmd.services.rule.valueobjects.TraceSummaryResponseType;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualDefConfig;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualDefCustomer;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualDefFleet;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualDefinition;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualEquation;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualFilter;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualHistService;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.LookupResponseType;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: August 11, 2011
 * @Date Modified : August 11, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Rule Web services and provide the Rule
 *              related funtionalities
 * @History :
 ******************************************************************************/

@Path(OMDConstants.RULE_SERVICE)
@Component
public class RuleResource extends BaseResource {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleResource.class);

    @Autowired
    ManualRunServiceIntf objManualRunServiceIntf;
    @Autowired
    ViewRuleServiceIntf objViewRuleServiceIntf;
    @Autowired
    VirtualServiceIntf objVirtualServiceIntf;
    @Autowired
    RuleTracerServiceIntf objRuleTracerServiceIntf;
    @Autowired
    RuleTesterServiceIntf objRuleTesterServiceIntf;
    @Autowired
    RunRecreatorServiceIntf objRunRecreatorServiceIntf;
    @Autowired
    @Qualifier("searchruleServiceInf")
    RuleServiceIntf searchruleServiceInf;
    @Autowired
    @Qualifier("activateruleServiceInf")
    RuleServiceIntf activateruleServiceInf;
    @Autowired
    @Qualifier("ruleServiceInf")
    RuleServiceIntf objRuleServiceIntf;
    @Autowired
    RuleCommonServiceIntf ruleCommonServiceInf;
    @Autowired
    OMDResourceMessagesIntf omdResourceMessagesIntf;

    /**
     * This method is used for Searching Manual run for the input passed
     * 
     * @param uriParam
     * @return List of ManualRunResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETMANUALRUN_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ManualRunResponseType> getManualRunDetails(
            @Context UriInfo uriParam) throws RMDServiceException {
        Iterator<JdpadSearchVO> iterSearchVO;
        List<JdpadSearchVO> arlSearchVO;
        JdpadSearchVO objJdpadSearchVO;
        JdpadResultServiceVO objJdpadResultServiceVO;
        final List<ManualRunResponseType> arlManualResponseType = new ArrayList<ManualRunResponseType>();
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar creationDate;
        XMLGregorianCalendar finishDate;
        ManualRunResponseType objManualResponseType;
        try {
            final MultivaluedMap<String, String> queryParams = uriParam
            .getQueryParameters();
            objJdpadResultServiceVO = new JdpadResultServiceVO();
            if (queryParams.containsKey(OMDConstants.TRACKINGID)) {
                objJdpadResultServiceVO.setStrTrackingId(queryParams
                        .getFirst(OMDConstants.TRACKINGID));
            } else {
                objJdpadResultServiceVO
                .setStrTrackingId(OMDConstants.EMPTY_STRING);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                objJdpadResultServiceVO.setStrAssetId(queryParams
                        .getFirst(OMDConstants.ASSET_NUMBER));
            } else {
                objJdpadResultServiceVO
                .setStrAssetId(OMDConstants.EMPTY_STRING);
            }
            if (queryParams.containsKey(OMDConstants.NOOFDAYS)) {
                objJdpadResultServiceVO.setStrDay(queryParams
                        .getFirst(OMDConstants.NOOFDAYS));
            } else {
                objJdpadResultServiceVO.setStrDay(OMDConstants.EMPTY_STRING);
            }

            arlSearchVO = objManualRunServiceIntf
            .searchManualRun(objJdpadResultServiceVO);
            iterSearchVO = arlSearchVO.iterator();
            if (RMDCommonUtility.isCollectionNotEmpty(arlSearchVO)) {
                while (iterSearchVO.hasNext()) {

                    objJdpadSearchVO = (JdpadSearchVO) iterSearchVO.next();
                    objManualResponseType = new ManualRunResponseType();
                    BeanUtility.copyBeanProperty(objJdpadSearchVO,
                            objManualResponseType);
                    if (null != objJdpadSearchVO.getDtCreatedTime()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objJdpadSearchVO
                                .getDtCreatedTime());
                        creationDate = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(objGregorianCalendar);
                        objManualResponseType.setCreationDate(creationDate);
                    }
                    if (null != objJdpadSearchVO.getDtFinishTime()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objJdpadSearchVO
                                .getDtFinishTime());
                        finishDate = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(objGregorianCalendar);
                        objManualResponseType.setFinishedDate(finishDate);
                    }
                    arlManualResponseType.add(objManualResponseType);
                }
            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return arlManualResponseType;
    }

    /**
     * This method is used for searching Manual run for the input
     * 
     * @param trackingIDValue
     * @return List of ManualRunResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETMANUALRUN_DETAILS_TRACKINGID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ManualRunResponseType> getManualRunDetails(
            @PathParam(OMDConstants.TRACKINGID) final String trackingIDValue)
            throws RMDServiceException {
        Iterator<JdpadSearchVO> iterSearchVO;
        List<JdpadSearchVO> arlSearchVO;
        JdpadSearchVO objJdpadSearchVO;
        JdpadResultServiceVO objJdpadResultServiceVO;
        final List<ManualRunResponseType> arlManualResponseType = new ArrayList<ManualRunResponseType>();
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar creationDate;
        XMLGregorianCalendar finishDate;
        ManualRunResponseType objManualResponseType;
        try {
            objJdpadResultServiceVO = new JdpadResultServiceVO();
            if (trackingIDValue != null) {
                objJdpadResultServiceVO.setStrTrackingId(trackingIDValue);
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.TRACKINGID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.TRACKINGID_NOT_PROVIDED),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            objJdpadResultServiceVO.setStrAssetId(OMDConstants.EMPTY_STRING);
            arlSearchVO = objManualRunServiceIntf
            .searchManualRun(objJdpadResultServiceVO);
            if (RMDCommonUtility.isCollectionNotEmpty(arlSearchVO)) {
                iterSearchVO = arlSearchVO.iterator();
                while (iterSearchVO.hasNext()) {

                    objJdpadSearchVO = (JdpadSearchVO) iterSearchVO.next();
                    objManualResponseType = new ManualRunResponseType();
                    BeanUtility.copyBeanProperty(objJdpadSearchVO,
                            objManualResponseType);
                    if (objJdpadSearchVO.getDtCreatedTime() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objJdpadSearchVO
                                .getDtCreatedTime());
                        creationDate = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(objGregorianCalendar);
                        objManualResponseType.setCreationDate(creationDate);
                    }
                    if (objJdpadSearchVO.getDtFinishTime() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objJdpadSearchVO
                                .getDtFinishTime());
                        finishDate = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(objGregorianCalendar);
                        objManualResponseType.setFinishedDate(finishDate);
                    }
                    arlManualResponseType.add(objManualResponseType);
                }
            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return arlManualResponseType;
    }

    /**
     * This method is used for fetching the rule details based on the inputs
     * 
     * @param uriParam
     * @return FinalRuleResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETRULEDETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public FinalRuleResponseType getRuleDetails(
            @PathParam(OMDConstants.RULEID) final String ruleID, @Context UriInfo uriParam)
    throws RMDServiceException {

        final MultivaluedMap<String, String> queryParams = uriParam
        .getQueryParameters();

        FinalRuleServiceVO objFinalRuleServiceVO = null;
        RuleHistory objRuleHistory = null;
        SimpleRule objSimpleRule = null;
        ComplexRule objComplexRule = null;
        FinalRuleResponseType objFinalRuleResponseType = null;
        RuleDefinitionServiceVO objRuleDefinitionServiceVO;
        RuleDefinition objRuleDefinition = new RuleDefinition();
        String strRuleID = null;
        String strCloneRule = null;
        String strlanguage = null;
        String cloneRule = null;
        List<ComplexRuleServiceVO> listComplexRule = null;
        List<SimpleRuleServiceVO> listSimpleRuleServiceVO = null;
        List<RuleDefinitionServiceVO> listRuleDefinitionServiceVO = null;
        RuleDefinition ruleDefinition = null;
        SimpleRuleClause objSimpleRuleClause = null;
        List<SimpleRuleClauseServiceVO> listSimpleRuleClause = null;
        List<ComplexRuleLinkServiceVO> listComplexRuleLinkServiceVO = null;
        ComplexRuleLink objComplexRuleLink = null;
        GregorianCalendar objGregorianCalendar=null;
        List<RuleDefCustomerServiceVO> listRuleDefCustomerServiceVO = null;
        RuleDefinitionCustomer objRuleDefinitionCustomer = null;
        XMLGregorianCalendar createdDate=null;
        List<RuleDefModelServiceVO> listRuleDefModelServiceVO = null;
        RuleDefinitionModel objRuleDefinitionModel = null;
        RuleDefinitionConfig innerObjRuleDefinitionConfig = null;
        List<RuleDefConfigServiceVO> innerlistRuleDefConfigServiceVO = null;

        List<RuleHistoryServiceVO> listRuleHistoryServiceVO = null;
        RuleDefinitionConfig objRuleDefinitionConfig = null;
        List<RuleDefConfigServiceVO> listRuleDefConfigServiceVO = null;
        RuleHistoryServiceVO ruleHistoryServiceVO = null;
        ComplexRuleServiceVO complexRuleServiceVO = null;
        ComplexRuleLinkServiceVO complexRuleLinkServiceVO = null;
        SimpleRuleServiceVO simpleRuleServiceVO = null;
        SimpleRuleClauseServiceVO simpleRuleClauseServiceVO = null;
        RuleDefinitionServiceVO ruleDefinitionServiceVO = null;
        RuleDefCustomerServiceVO ruleDefCustomerServiceVO = null;
        RuleDefModelServiceVO ruleDefModelServiceVO = null;
        RuleDefConfigServiceVO ruleDefConfigServiceVO = null;
        SimpleRule objClearingLogicSimpleRule = null;
        ClearingLogicRuleServiceVO objClearingLogicRuleServiceVO;
        List<SimpleRuleServiceVO> listClearingLogicSimpleRuleServiceVO = null;
        ClearingLogicRule clearingLogicRule = null;
        SimpleRuleClause objClearingLogicSimpleRuleClause = null;
        List<SimpleRuleClauseServiceVO> listClearingLogicSimpleRuleClause = null;
        SimpleRuleServiceVO clearingLogicSimpleRuleServiceVO = null;
        SimpleRuleClauseServiceVO clearingLogicSimpleRuleClauseServiceVO = null;
        ArrayList<ComplexRuleLinkServiceVO> listClearingLogicComplexRuleLinkServiceVO;
        ArrayList<ComplexRuleServiceVO> listClearingLogicComplexRule;
        ComplexRuleServiceVO clearingLogicComplexRuleServiceVO;
        ComplexRule objClearingLogicComplexRule;
        ComplexRuleLinkServiceVO clearingLogicComplexRuleLinkServiceVO;
        ComplexRuleLink objClearingLogicComplexRuleLink;
		String userCustomer=null;
		String userCustomerID=null;
		String isGERuleAUthor = null;
		boolean onlyRoRPresent=false;
        try {
			isGERuleAUthor = getRequestHeader(OMDConstants.IS_GE_RULE_AUTHOR);
			userCustomer = getRequestHeader("strUserCustomer");
			String defaultUom=getRequestHeader("measurementSystem");
			String appTimeZone=getRequestHeader("appTime");			
			int[] paramFlag = {OMDConstants.ALPHABETS, OMDConstants.NUMERIC};
			queryParams.add(OMDConstants.RULE_ID, ruleID);
			if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag,OMDConstants.CLONERULE, OMDConstants.RULE_ID)){

                if (ruleID != null) {
                    strRuleID = ruleID;
                } else {
                    throw new OMDInValidInputException(OMDConstants.RULEID_NOT_PROVIDED);
                }

                if (queryParams.containsKey(OMDConstants.CLONERULE)) {
                    cloneRule = queryParams.getFirst(OMDConstants.CLONERULE);
                } else {
                    cloneRule = OMDConstants.CLONE;
                }
                if (cloneRule != null) {
                    strCloneRule = cloneRule;
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_SEARCH_CRITERIA);
                }
                
                String explode_ror = null;
                if (queryParams.containsKey(OMDConstants.EXPLODE_ROR)) {
                    explode_ror = queryParams.getFirst(OMDConstants.EXPLODE_ROR);
                }

				
				strlanguage = getRequestHeader(OMDConstants.LANGUAGE);
				objFinalRuleServiceVO = objViewRuleServiceIntf.getRuleDetails(
						strRuleID, strCloneRule, strlanguage,explode_ror,defaultUom);

				objFinalRuleResponseType = new FinalRuleResponseType();
				listRuleDefinitionServiceVO = objFinalRuleServiceVO
						.getArlRuleDefinition();
				if (null != objFinalRuleServiceVO&&null!=listRuleDefinitionServiceVO) {
					// Populate the RULE HISTORY details
					RuleDefinitionServiceVO objRuleDef=listRuleDefinitionServiceVO.get(0);
					if (null != isGERuleAUthor
							&& isGERuleAUthor
									.equalsIgnoreCase(RMDCommonConstants.N_LETTER_UPPER)) {		
							if (!(objRuleDef.getStrRuleType()
									.equalsIgnoreCase(OMDConstants.ALERTS))
									) {
								throw new OMDInValidInputException( OMDConstants.NO_RULE_PERMISSION_TO_VIEW);
							}
								
						}
                    listRuleHistoryServiceVO = objFinalRuleServiceVO
                    .getArlRuleHistoryVO();
                    if (null != listRuleHistoryServiceVO) {
                        for (final Iterator<RuleHistoryServiceVO> iteratorRuleHistoryServiceVO = listRuleHistoryServiceVO
                                .iterator(); iteratorRuleHistoryServiceVO.hasNext();) {
                            ruleHistoryServiceVO = (RuleHistoryServiceVO) iteratorRuleHistoryServiceVO
                            .next();
                            objRuleHistory = new RuleHistory();
                            if(null!=ruleHistoryServiceVO.getStrDateCreated()){
                            	if(null!=appTimeZone){
								objRuleHistory.setDateCreated(RMDCommonUtility.getConvertedDate(ruleHistoryServiceVO.getStrDateCreated(), appTimeZone));
                            	}else{
                            		objRuleHistory.setDateCreated(ruleHistoryServiceVO.getStrDateCreated());
                            	}
                            }
                            BeanUtility.copyBeanProperty(ruleHistoryServiceVO,
                                    objRuleHistory);
                            objFinalRuleResponseType.getRuleHistory().add(
                                    objRuleHistory);
                        }
                    }
                    // Populate the RULE HISTORY details

                    // Populate the COMPLEX rule details
                    listComplexRule = objFinalRuleServiceVO.getArlComplexRule();
                    
                    if (null != listComplexRule && !listComplexRule.isEmpty()) {
                        for (final Iterator<ComplexRuleServiceVO> iteratorComplexRuleServiceVO = listComplexRule
                                .iterator(); iteratorComplexRuleServiceVO.hasNext();) {
                            complexRuleServiceVO = (ComplexRuleServiceVO) iteratorComplexRuleServiceVO
                            .next();
                            objComplexRule = new ComplexRule();

                            listComplexRuleLinkServiceVO = complexRuleServiceVO
                            .getArlComplexRuleLinkVO();
                            if (RMDCommonUtility
                                    .isCollectionNotEmpty(listComplexRuleLinkServiceVO)) {

                                for (final Iterator<ComplexRuleLinkServiceVO> iteratorComplexRuleLinkServiceVO = listComplexRuleLinkServiceVO
                                        .iterator(); iteratorComplexRuleLinkServiceVO
                                        .hasNext();) {
                                    complexRuleLinkServiceVO = (ComplexRuleLinkServiceVO) iteratorComplexRuleLinkServiceVO
                                    .next();
                                    objComplexRuleLink = new ComplexRuleLink();
                                    BeanUtility.copyBeanProperty(complexRuleLinkServiceVO,
                                            objComplexRuleLink);
                                    objComplexRule.getComplexRuleLink().add(
                                            objComplexRuleLink);
                                }
                            }
                            if("F".equalsIgnoreCase(complexRuleServiceVO.getStrRule1Type())&&"F".equalsIgnoreCase(complexRuleServiceVO.getStrRule2Type())){
                            	onlyRoRPresent=true;
                            }
                            BeanUtility.copyBeanProperty(complexRuleServiceVO,
                                    objComplexRule);
                            objFinalRuleResponseType.getComplexRule().add(objComplexRule);
                        }
                    }
                    // Populate the COMPLEX rule details end

                    // Populate the SIMPLE rule details start
                    listSimpleRuleServiceVO = objFinalRuleServiceVO.getArlSimpleRule();
                    if (listSimpleRuleServiceVO == null&&!onlyRoRPresent) {
                        throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                    }
                    if (listSimpleRuleServiceVO != null) {
                    for (final Iterator<SimpleRuleServiceVO> iteratorSimpleRuleServiceVO = listSimpleRuleServiceVO
                            .iterator(); iteratorSimpleRuleServiceVO.hasNext();) {
                        simpleRuleServiceVO = (SimpleRuleServiceVO) iteratorSimpleRuleServiceVO
                        .next();
                        objSimpleRule = new SimpleRule();
                        objSimpleRuleClause = new SimpleRuleClause();

                        listSimpleRuleClause = simpleRuleServiceVO
                        .getArlSimpleRuleClauseVO();
                        if (RMDCommonUtility.isCollectionNotEmpty(listSimpleRuleClause)) {
                            for (final Iterator<SimpleRuleClauseServiceVO> iteratorSimpleRuleClauseServiceVO = listSimpleRuleClause
                                    .iterator(); iteratorSimpleRuleClauseServiceVO
                                    .hasNext();) {
                                simpleRuleClauseServiceVO = (SimpleRuleClauseServiceVO) iteratorSimpleRuleClauseServiceVO
                                .next();
                                objSimpleRuleClause = new SimpleRuleClause();
                                BeanUtility.copyBeanProperty(simpleRuleClauseServiceVO,
                                        objSimpleRuleClause);
								objSimpleRuleClause.setUomAbbr(simpleRuleClauseServiceVO.getUomAbbr());
                                objSimpleRule.getSimpleRuleClause().add(
                                        objSimpleRuleClause);
                            }
                        }
                        BeanUtility
                        .copyBeanProperty(simpleRuleServiceVO, objSimpleRule);
                        objFinalRuleResponseType.getSimpleRule().add(objSimpleRule);
                    }
                    }
                    // Populate the SIMPLE rule details end

					boolean isUserCustomerSelected=false;
                    if (listRuleDefinitionServiceVO == null) {
                        throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                    }
                    // Populate the RULE Definition Service details
                    if(null!=listRuleDefinitionServiceVO&&!listRuleDefinitionServiceVO.isEmpty()){
                        for (final Iterator<RuleDefinitionServiceVO> iterator = listRuleDefinitionServiceVO
                                .iterator(); iterator.hasNext();) {
                            ruleDefinitionServiceVO = (RuleDefinitionServiceVO) iterator
                            .next();
                            objRuleDefinition = new RuleDefinition();
                            listRuleDefCustomerServiceVO = ruleDefinitionServiceVO
                            .getArlRuleDefCustomer();
                            if (RMDCommonUtility
                                    .isCollectionNotEmpty(listRuleDefCustomerServiceVO)) {
								int customerSize=listRuleDefCustomerServiceVO.size();
                                for (final Iterator<RuleDefCustomerServiceVO> iterator2 = listRuleDefCustomerServiceVO
                                        .iterator(); iterator2.hasNext();) {
                                    ruleDefCustomerServiceVO = (RuleDefCustomerServiceVO) iterator2
                                    .next();
                                    objRuleDefinitionCustomer = new RuleDefinitionCustomer();
									if(null!=ruleDefCustomerServiceVO){
									if (null != isGERuleAUthor
										&& isGERuleAUthor
												.equalsIgnoreCase(RMDCommonConstants.N_LETTER_UPPER)&&customerSize==1) {		
										if (ruleDefCustomerServiceVO.getStrCustomer()
												.equalsIgnoreCase(userCustomer)
												) {
											isUserCustomerSelected = true;
										}
											
									}
									}
                                    BeanUtility.copyBeanProperty(
                                            ruleDefCustomerServiceVO,
                                            objRuleDefinitionCustomer);
                                    if (null != ruleDefCustomerServiceVO
                                            .getArlRuleDefFleet()
                                            && !ruleDefCustomerServiceVO
                                            .getArlRuleDefFleet().isEmpty()) {
                                        List arlRuleDefFleet = ruleDefCustomerServiceVO
                                        .getArlRuleDefFleet();
                                        int arlRuleDefFleetSize = arlRuleDefFleet
                                        .size();
    
                                        for (int i = 0; i < arlRuleDefFleetSize; i++) {
                                            ElementVO elementVO = (ElementVO) arlRuleDefFleet
                                            .get(i);
                                            Fleet fleet = new Fleet();
                                            fleet.setFleetID(elementVO.getId());
                                            fleet.setFleet(elementVO.getName());
                                            objRuleDefinitionCustomer.getRuleDefFleet()
                                            .add(fleet);
                                        }
                                    }
                                    objRuleDefinition.getRuleDefinitionCustomer().add(
                                            objRuleDefinitionCustomer);
                                }
                            }
                            listRuleDefModelServiceVO = ruleDefinitionServiceVO
                            .getArlRuleDefModel();
                            if (RMDCommonUtility
                                    .isCollectionNotEmpty(listRuleDefModelServiceVO)) {
                                for (final Iterator<RuleDefModelServiceVO> iteratorRuleDefModelServiceVO = listRuleDefModelServiceVO
                                        .iterator(); iteratorRuleDefModelServiceVO
                                        .hasNext();) {
                                    ruleDefModelServiceVO = (RuleDefModelServiceVO) iteratorRuleDefModelServiceVO
                                    .next();
                                    objRuleDefinitionModel = new RuleDefinitionModel();
                                    BeanUtility.copyBeanProperty(ruleDefModelServiceVO,
                                            objRuleDefinitionModel);
                                    innerlistRuleDefConfigServiceVO = ruleDefModelServiceVO
                                    .getArlConfigService();
                                    if (RMDCommonUtility
                                            .isCollectionNotEmpty(innerlistRuleDefConfigServiceVO)) {
                                        for (final Iterator<RuleDefConfigServiceVO> iteratorRuleDefConfigServiceVO = innerlistRuleDefConfigServiceVO
                                                .iterator(); iteratorRuleDefConfigServiceVO
                                                .hasNext();) {
                                            ruleDefConfigServiceVO = (RuleDefConfigServiceVO) iteratorRuleDefConfigServiceVO
                                            .next();
                                            innerObjRuleDefinitionConfig = new RuleDefinitionConfig();
                                            BeanUtility.copyBeanProperty(
                                                    ruleDefConfigServiceVO,
                                                    innerObjRuleDefinitionConfig);
                                            objRuleDefinitionModel
                                            .getRuleDefinitionConfig().add(
                                                    innerObjRuleDefinitionConfig);
                                            objRuleDefinition.getRuleDefinitionModelList()
                                            .add(objRuleDefinitionModel);
                                        }
                                    } else {
                                        objRuleDefinition.getRuleDefinitionModelList().add(
                                                objRuleDefinitionModel);
                                    }
    
                                }
                            }
    
                            listRuleDefConfigServiceVO = ruleDefinitionServiceVO
                            .getArlRuleDefConfig();
                            if (RMDCommonUtility
                                    .isCollectionNotEmpty(listRuleDefConfigServiceVO)) {
                                for (final Iterator<RuleDefConfigServiceVO> iteratorRuleDefConfigServiceVO = listRuleDefConfigServiceVO
                                        .iterator(); iteratorRuleDefConfigServiceVO
                                        .hasNext();) {
                                    ruleDefConfigServiceVO = (RuleDefConfigServiceVO) iteratorRuleDefConfigServiceVO
                                    .next();
                                    objRuleDefinitionConfig = new RuleDefinitionConfig();
                                    BeanUtility.copyBeanProperty(ruleDefConfigServiceVO,
                                            objRuleDefinitionConfig);
                                    objRuleDefinition.getRuleDefinitionConfig().add(
                                            objRuleDefinitionConfig);
                                }
                            }
    
                            BeanUtility.copyBeanProperty(ruleDefinitionServiceVO,
                                    objRuleDefinition);
                            /*
                             * Include Flag - Exclude mapping should be negated, 
                             * hence this doesn't use the BeanUtility for mapping
                             */
                            if( null != ruleDefinitionServiceVO.getStrExclude() ) {
                                boolean excludeFlag = OMDConstants.Y.equalsIgnoreCase(ruleDefinitionServiceVO.getStrExclude());
                                if(!excludeFlag){
                                    objRuleDefinition.setIncludeFlag(OMDConstants.Y);   
                                }else {
                                    objRuleDefinition.setIncludeFlag(OMDConstants.N);
                                }                               
                            }else{ //Sometimes EoA stores exclude as null, meaning def should be included
                                objRuleDefinition.setIncludeFlag(OMDConstants.Y);
                            }
                            objFinalRuleResponseType.getRuleDefinitionList().add(
                                    objRuleDefinition);
                        }
                    }
					if (null != isGERuleAUthor
							&& isGERuleAUthor
									.equalsIgnoreCase(RMDCommonConstants.N_LETTER_UPPER)&&!isUserCustomerSelected) {		
						throw new OMDInValidInputException( OMDConstants.NO_RULE_PERMISSION_TO_VIEW);
					}
                    clearingLogicRule = new ClearingLogicRule();
                    objClearingLogicRuleServiceVO = objFinalRuleServiceVO.getClearingLogicRule();
                    if(objClearingLogicRuleServiceVO!=null) {
                        BeanUtility.copyBeanProperty(objClearingLogicRuleServiceVO, clearingLogicRule);
                        objFinalRuleResponseType.setClearingLogicRule(clearingLogicRule);   
                    listClearingLogicSimpleRuleServiceVO = objClearingLogicRuleServiceVO.getArlSimpleRule();
                    if (listClearingLogicSimpleRuleServiceVO != null) {
                        for (final Iterator<SimpleRuleServiceVO> iteratorClearingLogicSimpleRuleServiceVO = listClearingLogicSimpleRuleServiceVO
                                .iterator(); iteratorClearingLogicSimpleRuleServiceVO.hasNext();) {
                            clearingLogicSimpleRuleServiceVO = (SimpleRuleServiceVO) iteratorClearingLogicSimpleRuleServiceVO
                            .next();
                            objClearingLogicSimpleRule = new SimpleRule();
                            objClearingLogicSimpleRuleClause = new SimpleRuleClause();

                            listClearingLogicSimpleRuleClause = clearingLogicSimpleRuleServiceVO
                            .getArlSimpleRuleClauseVO();
                            if (RMDCommonUtility.isCollectionNotEmpty(listClearingLogicSimpleRuleClause)) {
                                for (final Iterator<SimpleRuleClauseServiceVO> iteratorClearingLogicSimpleRuleClauseServiceVO = listClearingLogicSimpleRuleClause
                                        .iterator(); iteratorClearingLogicSimpleRuleClauseServiceVO
                                        .hasNext();) {
                                    clearingLogicSimpleRuleClauseServiceVO = (SimpleRuleClauseServiceVO) iteratorClearingLogicSimpleRuleClauseServiceVO
                                    .next();
                                    objClearingLogicSimpleRuleClause = new SimpleRuleClause();
                                    BeanUtility.copyBeanProperty(clearingLogicSimpleRuleClauseServiceVO,
                                            objClearingLogicSimpleRuleClause);
									objClearingLogicSimpleRuleClause.setUomAbbr(clearingLogicSimpleRuleClauseServiceVO.getUomAbbr());									
                                    objClearingLogicSimpleRule.getSimpleRuleClause().add(objClearingLogicSimpleRuleClause);
                                }
                            }
                            BeanUtility
                            .copyBeanProperty(clearingLogicSimpleRuleServiceVO, objClearingLogicSimpleRule);
                            objFinalRuleResponseType.getClearingLogicRule().getSimpleRule().add(objClearingLogicSimpleRule);
                        }
                    }
                    listClearingLogicComplexRule = objClearingLogicRuleServiceVO.getArlComplexRule();
                    if (null != listClearingLogicComplexRule && !listClearingLogicComplexRule.isEmpty()) {
                        for (final Iterator<ComplexRuleServiceVO> iteratorClearingLogicComplexRuleServiceVO = listClearingLogicComplexRule
                                .iterator(); iteratorClearingLogicComplexRuleServiceVO.hasNext();) {
                            clearingLogicComplexRuleServiceVO = (ComplexRuleServiceVO) iteratorClearingLogicComplexRuleServiceVO
                            .next();
                            objClearingLogicComplexRule = new ComplexRule();

                            listClearingLogicComplexRuleLinkServiceVO = clearingLogicComplexRuleServiceVO.getArlComplexRuleLinkVO();
                            if (RMDCommonUtility.isCollectionNotEmpty(listClearingLogicComplexRuleLinkServiceVO)) {

                                for (final Iterator<ComplexRuleLinkServiceVO> iteratorClearingLogicComplexRuleLinkServiceVO = listClearingLogicComplexRuleLinkServiceVO
                                        .iterator(); iteratorClearingLogicComplexRuleLinkServiceVO
                                        .hasNext();) {
                                    clearingLogicComplexRuleLinkServiceVO = (ComplexRuleLinkServiceVO) iteratorClearingLogicComplexRuleLinkServiceVO
                                    .next();
                                    objClearingLogicComplexRuleLink = new ComplexRuleLink();
                                    BeanUtility.copyBeanProperty(clearingLogicComplexRuleLinkServiceVO,
                                            objClearingLogicComplexRuleLink);
                                    objClearingLogicComplexRule.getComplexRuleLink().add(
                                            objClearingLogicComplexRuleLink);
                                }
                            }
                            BeanUtility.copyBeanProperty(clearingLogicComplexRuleServiceVO,
                                    objClearingLogicComplexRule);
                            objFinalRuleResponseType.getClearingLogicRule().getComplexRule().add(objClearingLogicComplexRule);
                        }
                    }
                    }
                    ruleDefinition = new RuleDefinition();
                    objRuleDefinitionServiceVO = objFinalRuleServiceVO
                    .getRuleDefinition();

                    BeanUtility.copyBeanProperty(objRuleDefinitionServiceVO,
                            ruleDefinition);
                    BeanUtility.copyBeanProperty(objFinalRuleServiceVO,
                            objFinalRuleResponseType);
                    objFinalRuleResponseType.setRuleDefinition(ruleDefinition);
                } else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            }else{
                throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return objFinalRuleResponseType;
    }

    /**
     * This method is used for fetching the rule details based on the inputs
     * 
     * @param uriParam
     * @return FinalRuleResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETRULEDETAILSCL)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public FinalRuleResponseType getRuleDetailsForCL(
            @PathParam(OMDConstants.RULEID) final String ruleID,
            @PathParam(OMDConstants.CL) final String clearingLogic,@Context UriInfo uriParam)
    throws RMDServiceException {
        String isClearingLogic=(null==clearingLogic)?"0":clearingLogic;
        if (isClearingLogic.equals(OMDConstants.NUMBER)) {
            return getRuleDetailsCL(ruleID, uriParam);
        } else {
            return getRuleDetails(ruleID, uriParam);
        }
    }
    
    /**
     * This method is used for fetching trace Details
     * 
     * @param uriParam
     * @return TraceSummaryResponseType
     * @throws RMDServiceException
     * @Revision 9-Nov-2016 Changed for Stripping XSS Characters
     */
	@GET
	@Path(OMDConstants.GETTRACEDETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public TraceSummaryResponseType getTraceDetails(@Context UriInfo uriParam)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		final TraceSummaryResponseType traceResponse = new TraceSummaryResponseType();
		final TracerServiceVO traceVO = new TracerServiceVO();
		try {
			queryParams = uriParam.getQueryParameters();
			if (queryParams != null) {
				if (queryParams.containsKey(OMDConstants.TESTER_TRACE_ID)) {
					traceVO.setStrTrackingID(BeanUtility
							.stripXSSCharacters(queryParams
									.getFirst(OMDConstants.TESTER_TRACE_ID)));
					traceVO.setMode(OMDConstants.RULE_TESTER);
				} else if (queryParams
						.containsKey(OMDConstants.RUN_CREATE_TRACK_ID)) {

					traceVO.setStrTrackingID(BeanUtility.stripXSSCharacters(queryParams
							.getFirst(OMDConstants.RUN_CREATE_TRACK_ID)));
					traceVO.setMode(OMDConstants.RUN_RECREATOR);

				} else if (queryParams.containsKey(OMDConstants.MANUAL_RUN_ID)) {
					traceVO.setStrTrackingID(BeanUtility
							.stripXSSCharacters(queryParams
									.getFirst(OMDConstants.MANUAL_RUN_ID)));
					traceVO.setMode(OMDConstants.MANUALRUN);

				} else if (queryParams.containsKey(OMDConstants.CASE_ID1)) {
					traceVO.setStrTrackingID(BeanUtility
							.stripXSSCharacters(queryParams
									.getFirst(OMDConstants.CASE_ID1)));
					traceVO.setMode(OMDConstants.ON_THE_FLY_DS);
				} else {
					throw new OMDInValidInputException(
							BeanUtility
									.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA),
							omdResourceMessagesIntf.getMessage(
									BeanUtility
											.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA),
									new String[] {},
									BeanUtility
											.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
				}
				// add condition for other Mode
				if (queryParams.containsKey(OMDConstants.RULEID)) {
					traceVO.setStrFinalRuleID(BeanUtility
							.stripXSSCharacters(queryParams
									.getFirst(OMDConstants.RULEID)));
				} else {
					throw new OMDInValidInputException(
							BeanUtility
									.getErrorCode(OMDConstants.RULEID_NOT_PROVIDED),
							omdResourceMessagesIntf.getMessage(
									BeanUtility
											.getErrorCode(OMDConstants.RULEID_NOT_PROVIDED),
									new String[] {},
									BeanUtility
											.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
				}
				traceVO.setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
				objRuleTracerServiceIntf.getTraceID(traceVO);
				BeanUtility.copyBeanProperty(traceVO, traceResponse);
			} else {
				throw new OMDInValidInputException(
						BeanUtility
								.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}

		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (RMDServiceException rmdServiceException) {
			throw rmdServiceException;
		} catch (Exception e) {
			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}
		return traceResponse;
	}

    /**
     * This method is used for retrieving all the Test rule details
     * 
     * @param uiuriParam
     * @return List of RuleTrackingResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETRULETESTER_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RuleTrackingResponseType> getRuleTestsDetails(
            @Context UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        final RuleTesterSeachCriteriaServiceVO ruleTesterSeachCriteriaServiceVO = new RuleTesterSeachCriteriaServiceVO();
        final List<RuleTrackingResponseType> lstruleTrackinResponseType = new ArrayList<RuleTrackingResponseType>();
        final List<RuleTrackingServiceResultVO> lstRuleTesterVO = new ArrayList<RuleTrackingServiceResultVO>();
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        try {
            queryParams = uriParam.getQueryParameters();

            if (queryParams != null) {
                if (queryParams.containsKey(OMDConstants.TRACKINGID)) {
                    ruleTesterSeachCriteriaServiceVO
                    .setStrTrackingID(queryParams
                            .getFirst(OMDConstants.TRACKINGID));
                }

                if (queryParams.containsKey(OMDConstants.RULEID)) {
                    ruleTesterSeachCriteriaServiceVO.setStrRuleID(queryParams
                            .getFirst(OMDConstants.RULEID));
                }

                if (queryParams.containsKey(OMDConstants.USERID)) {
                    ruleTesterSeachCriteriaServiceVO.setStrUserName(queryParams
                            .getFirst(OMDConstants.USERID));
                } else {
                    ruleTesterSeachCriteriaServiceVO.setStrUserName(OMDConstants.SELECT);
                }
                if (queryParams.containsKey(OMDConstants.LOOK_BACK_WEEKS)) {
                    ruleTesterSeachCriteriaServiceVO
                    .setStrLookBackWeeks(queryParams
                            .getFirst(OMDConstants.LOOK_BACK_WEEKS));
                } else {
                    ruleTesterSeachCriteriaServiceVO
                    .setStrLookBackWeeks(OMDConstants.SELECT);
                }

                lstRuleTesterVO
                .addAll(objRuleTesterServiceIntf
                        .searchTrackingResult(ruleTesterSeachCriteriaServiceVO));
                RuleTrackingResponseType ruleResonse = null;
                RuleTrackingServiceResultVO ruleTrackingServiceVo = new RuleTrackingServiceResultVO();
                if (RMDCommonUtility.isCollectionNotEmpty(lstRuleTesterVO)) {

                    final Iterator<RuleTrackingServiceResultVO> iterRuleTrackingServiceResultVO = lstRuleTesterVO
                    .iterator();
                    while (iterRuleTrackingServiceResultVO.hasNext()) {
                        ruleResonse = new RuleTrackingResponseType();
                        ruleTrackingServiceVo = (RuleTrackingServiceResultVO) iterRuleTrackingServiceResultVO
                        .next();
                        BeanUtility.copyBeanProperty(ruleTrackingServiceVo,
                                ruleResonse);

                        if (ruleTrackingServiceVo.getStrCreationDate() != null) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(ruleTrackingServiceVo
                                    .getStrCreationDate());
                            createdDate = DatatypeFactory.newInstance()
                            .newXMLGregorianCalendar(
                                    objGregorianCalendar);
                            ruleResonse.setCreadtionDate(createdDate);
                        }
                        if (ruleTrackingServiceVo.getStrLastUpdatedDate() != null) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(ruleTrackingServiceVo
                                    .getStrLastUpdatedDate());
                            createdDate = DatatypeFactory.newInstance()
                            .newXMLGregorianCalendar(
                                    objGregorianCalendar);
                            ruleResonse.setLastUpdatedDate(createdDate);
                        }
                        lstruleTrackinResponseType.add(ruleResonse);
                    }
                } else {
                    throw new OMDNoResultFoundException(
                            BeanUtility
                            .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility
                                    .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                    new String[] {},
                                    BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.QUERY_PARAMETERS_NOT_PASSED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.QUERY_PARAMETERS_NOT_PASSED),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return lstruleTrackinResponseType;
    }

    /**
     * This method is used for retrieving all the rules
     * 
     * @param uriParam
     * @return List of RuleResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETRULES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RuleResponseType> getRules(@Context UriInfo uriParam)
    throws RMDServiceException {
        MultivaluedMap<String, String> queryParams;
        String strRuleID = null;
        String strFamily = null;
        String strLanguage = null;
        String strCustomer = null;
        RuleResponseType objRuleResponseType = null;
        List<DpdFinrulSearchVO> lstFinalRuleList = null;
        List lstFiredRuleList = null;
        String strTraceID = null;
        SearchRuleServiceCriteriaVO objSearchRuleServiceCriteriaVO = null;
        List<DpdRulhisSearchVO> lstSearchResults = null;
        final List<RuleResponseType> lstRuleResponseType = new ArrayList<RuleResponseType>();
        DpdFinrulSearchVO objDpdFinrulSearchVO = null;
        DpdRuldefSearchVO objDpdRuldefSearchVO = null;
        DpdRulMetricsSearchVO objDpdRulMetricsSearchVO = null;
        SearchFinalRuleResponse objsearchFinalRuleResponse = null;
        RuleDefResponse ruleDefResponse = null;
        RuleMetricsResponse ruleMetricsResponse = null;
        DpdCmprulSearchVO objDpdCmprulSearchVO = null;
        DpdSimrulSearchVO objDpdSimrulSearchVO = null;
        SearchComplexRule objSearchComplexRule = null;
        SearchSimpleRule objSearchSimpleRule = null;
        XMLGregorianCalendar dtXMLCreatedDate = null;
        XMLGregorianCalendar dtXMLUpdatedDate = null;
        Object strFinalRules = null;
        DpdRulhisSearchVO objDpdRulhisSearchVO = null;
        String strRuleType=null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            queryParams = uriParam.getQueryParameters();
            objRuleResponseType = new RuleResponseType();
            lstFinalRuleList = new ArrayList<DpdFinrulSearchVO>();
            int[] paramFlag = {OMDConstants.NUMERIC,OMDConstants.ALPHABETS,OMDConstants.ALPHABETS,OMDConstants.NUMERIC,OMDConstants.ALPHA_NUMERIC_HYPEN,OMDConstants.ALPHABETS,OMDConstants.AlPHA_NUMERIC,OMDConstants.DOUBLE_HYPHEN,OMDConstants.ALPHA_NUMERIC_UNDERSCORE,OMDConstants.NUMERIC,OMDConstants.DOUBLE_HYPHEN,OMDConstants.ALPHABETS,OMDConstants.ALPHABETS,OMDConstants.AlPHA_NUMERIC,OMDConstants.ALPHA_AMPERSAND,OMDConstants.AlPHA_NUMERIC,OMDConstants.VALID_DATE,OMDConstants.VALID_DATE,OMDConstants.VALID_DATE,OMDConstants.VALID_DATE,OMDConstants.NUMERIC};
            String[] userInput = {OMDConstants.RULE_ID,OMDConstants.TYPE,OMDConstants.ROR,OMDConstants.TRACE_ID,OMDConstants.FAULT_CODE,OMDConstants.RULE_TYPE,OMDConstants.CREATED_BY,OMDConstants.RECOMMENDATION,OMDConstants.ACTIVATE_BY,OMDConstants.IS_ACTIVE,OMDConstants.RULE_TITLE,OMDConstants.VERSION,OMDConstants.STATUS,OMDConstants.FAMILY,OMDConstants.SUB_SYSTEM,OMDConstants.LAST_UPDATED_BY,OMDConstants.LAST_UPDATED_FROM_DATE,OMDConstants.LAST_UPDATED_TO_DATE,OMDConstants.CREATED_BY_FROM_DATE,OMDConstants.CREATED_BY_TO_DATE,OMDConstants.BLN_DEFAULT_LOAD};
            if(AppSecUtil.validateWebServiceInput(queryParams, OMDConstants.DATE_FORMAT, paramFlag,userInput)){
            // To find the list of final rules
                if (queryParams.containsKey(OMDConstants.RULE_ID)
                        && queryParams.containsKey(OMDConstants.TYPE)
                        && queryParams.containsKey(OMDConstants.FAMILY)
                        && queryParams.containsKey(OMDConstants.RULE_TYPE)) {
                    if (queryParams.getFirst(OMDConstants.TYPE)
                            .equalsIgnoreCase(OMDConstants.ROR)) {
                        strRuleID = queryParams.getFirst(OMDConstants.RULE_ID);
                        strFamily = queryParams.getFirst(OMDConstants.FAMILY);
                        strRuleType=queryParams.getFirst(OMDConstants.RULE_TYPE);
                        if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                            strCustomer=queryParams
                                    .getFirst(OMDConstants.CUSTOMER);
                        }
                        lstFinalRuleList = objViewRuleServiceIntf
                                .getFinalRuleList(strRuleID, strFamily,strRuleType,
                                        strLanguage,strCustomer);
                        if (RMDCommonUtility
                                .isCollectionNotEmpty(lstFinalRuleList)) {
                            SearchFinalRuleResponse objFinalRuleResponse=null;
                            for (Iterator iterator = lstFinalRuleList
                                    .iterator(); iterator.hasNext();) {
                                DpdFinrulSearchVO objDpdFinrulSearch = (DpdFinrulSearchVO) iterator
                                        .next();
                                objRuleResponseType = new RuleResponseType();
                                objFinalRuleResponse=new SearchFinalRuleResponse();
                                objFinalRuleResponse.setFinalRuleSeqID(objDpdFinrulSearch.getKmDpdFinrulSeqId());
                                // objFinalRuleResponse.setRuleTitle(objDpdFinrulSearch.getRuleTitle());
                                objFinalRuleResponse.setRuleTitle(EsapiUtil.stripXSSCharacters((String)
			                        			(objDpdFinrulSearch.getRuleTitle())));
                                objRuleResponseType.setSearchFinalRuleResponse(objFinalRuleResponse);
                                lstRuleResponseType.add(objRuleResponseType);
                                }
                                
                            
                        } else {
                            throw new OMDNoResultFoundException(
                                    OMDConstants.NORECORDFOUNDEXCEPTION);
                        }
                    }
                    return lstRuleResponseType;
                }
            // To find the list of fired Rules
            lstFiredRuleList = new ArrayList();
            if (queryParams.containsKey(OMDConstants.TRACE_ID)) {
                strTraceID = queryParams.getFirst(OMDConstants.TRACE_ID);
                lstFiredRuleList = objViewRuleServiceIntf.getFiredRuleList(
                        strTraceID, strLanguage);
                if (RMDCommonUtility.isCollectionNotEmpty(lstFiredRuleList)) {
                    for (final Iterator<Object> iterator = lstFiredRuleList
                            .iterator(); iterator.hasNext();) {
                        strFinalRules = (Object) iterator.next();
                        objRuleResponseType = new RuleResponseType();
                        objRuleResponseType.getRulelist().add(
                                String.valueOf(strFinalRules));
                        lstRuleResponseType.add(objRuleResponseType);
                    }
                } else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
                return lstRuleResponseType;
            }
            objSearchRuleServiceCriteriaVO = new SearchRuleServiceCriteriaVO();
            if (queryParams.containsKey(OMDConstants.FAULT_CODE)) {
                objSearchRuleServiceCriteriaVO.setStrFault(queryParams
                        .getFirst(OMDConstants.FAULT_CODE));
            }

            if (queryParams.containsKey(OMDConstants.RULE_TYPE)) {
                objSearchRuleServiceCriteriaVO.setStrRuleType(queryParams
                        .getFirst(OMDConstants.RULE_TYPE));
            }
            if (queryParams.containsKey(OMDConstants.CREATED_BY)) {
                objSearchRuleServiceCriteriaVO.setStrCreatedBy(queryParams
                        .getFirst(OMDConstants.CREATED_BY));
            }
            if (queryParams.containsKey(OMDConstants.CREATED_SINCE)) {
                objSearchRuleServiceCriteriaVO.setStrCreatedSince(queryParams
                        .getFirst(OMDConstants.CREATED_SINCE));
            }
            if (queryParams.containsKey(OMDConstants.RECOMMENDATION)) {
                objSearchRuleServiceCriteriaVO.setStrRecommendation(queryParams
                        .getFirst(OMDConstants.RECOMMENDATION));
            }
            if (queryParams.containsKey(OMDConstants.ACTIVATE_BY)) {
                objSearchRuleServiceCriteriaVO.setStrActivateBy(queryParams
                        .getFirst(OMDConstants.ACTIVATE_BY));
            }

            if (queryParams.containsKey(OMDConstants.ACTIVATE_SINCE)) {
                objSearchRuleServiceCriteriaVO.setStrActivateSince(queryParams
                        .getFirst(OMDConstants.ACTIVATE_SINCE));
            }

            if (queryParams.containsKey(OMDConstants.IS_ACTIVE)) {
                objSearchRuleServiceCriteriaVO.setStrIsActive(queryParams
                        .getFirst(OMDConstants.IS_ACTIVE));
            }
            if (queryParams.containsKey(OMDConstants.RULE_TITLE)) {
                objSearchRuleServiceCriteriaVO.setStrRuleTitle(queryParams
                        .getFirst(OMDConstants.RULE_TITLE));
            }

            if (queryParams.containsKey(OMDConstants.RULE_ID)) {
                LOG.debug("inside rule id second..............");
                objSearchRuleServiceCriteriaVO.setStrRuleID(queryParams
                        .getFirst(OMDConstants.RULE_ID));
            }

            if (queryParams.containsKey(OMDConstants.VERSION)) {
                objSearchRuleServiceCriteriaVO.setStrVersion(queryParams
                        .getFirst(OMDConstants.VERSION));
            }
            // Newly Added cases on April 2012
            if (queryParams.containsKey(OMDConstants.STATUS)) {
                objSearchRuleServiceCriteriaVO.setStrStatus(queryParams
                        .getFirst(OMDConstants.STATUS));
            }
            if (queryParams.containsKey(OMDConstants.LAST_UPDATED_FROM_DATE)) {
                objSearchRuleServiceCriteriaVO
                .setStrLastUpdatedFromDate(queryParams
                        .getFirst(OMDConstants.LAST_UPDATED_FROM_DATE));
            }
            if (queryParams.containsKey(OMDConstants.LAST_UPDATED_TO_DATE)) {
                objSearchRuleServiceCriteriaVO
                .setStrLastUpdatedToDate(queryParams
                        .getFirst(OMDConstants.LAST_UPDATED_TO_DATE));
            }
            if (queryParams.containsKey(OMDConstants.CREATED_BY_FROM_DATE)) {
                objSearchRuleServiceCriteriaVO
                .setStrCreatedByFromDate(queryParams
                        .getFirst(OMDConstants.CREATED_BY_FROM_DATE));
            }
            if (queryParams.containsKey(OMDConstants.CREATED_BY_TO_DATE)) {
                objSearchRuleServiceCriteriaVO
                .setStrCreatedByToDate(queryParams
                        .getFirst(OMDConstants.CREATED_BY_TO_DATE));
            }
            if (queryParams.containsKey(OMDConstants.FAMILY)) {
                objSearchRuleServiceCriteriaVO.setStrFamily(queryParams
                        .getFirst(OMDConstants.FAMILY));
            }

            if (queryParams.containsKey(OMDConstants.SUB_SYSTEM)) {
                objSearchRuleServiceCriteriaVO.setStrSubSystem(queryParams
                        .getFirst(OMDConstants.SUB_SYSTEM));
            }
            if (queryParams.containsKey(OMDConstants.LAST_UPDATED_BY)) {
                objSearchRuleServiceCriteriaVO.setStrLastUpdatedBy(queryParams
                        .getFirst(OMDConstants.LAST_UPDATED_BY));
            }
            if (queryParams.containsKey(OMDConstants.RX_TITLE)) {
                objSearchRuleServiceCriteriaVO.setStrRxTitle(
                    (String)queryParams.getFirst(OMDConstants.RX_TITLE));
            }
            // Added for the Initial load check
            if (queryParams.containsKey(OMDConstants.BLN_DEFAULT_LOAD)) {
                if (queryParams.getFirst(OMDConstants.BLN_DEFAULT_LOAD)
                        .equalsIgnoreCase(OMDConstants.NUMBER)) {
                    objSearchRuleServiceCriteriaVO
                    .setBlnDefaultLoad(RMDCommonConstants.TRUE);
                } else {
                    objSearchRuleServiceCriteriaVO
                    .setBlnDefaultLoad(RMDCommonConstants.FALSE);
                }
            }
            
            if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                objSearchRuleServiceCriteriaVO.setStrCustomer(queryParams
                        .getFirst(OMDConstants.CUSTOMER));
            }
            objSearchRuleServiceCriteriaVO
            .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

            lstSearchResults = searchruleServiceInf
            .getSearchRuleResult(objSearchRuleServiceCriteriaVO);
            if (RMDCommonUtility.isCollectionNotEmpty(lstSearchResults)) {
                
                Iterator<DpdRulhisSearchVO> iteratorDpdRulhisSearchVO = lstSearchResults.iterator();                
                while( iteratorDpdRulhisSearchVO.hasNext() ) {
                    
                /*for (final Iterator<DpdRulhisSearchVO> iteratorDpdRulhisSearchVO = lstSearchResults
                        .iterator(); iteratorDpdRulhisSearchVO.hasNext();) { */
                    objDpdRulhisSearchVO = (DpdRulhisSearchVO) iteratorDpdRulhisSearchVO
                    .next();
                    objRuleResponseType = new RuleResponseType();
                    objDpdFinrulSearchVO = objDpdRulhisSearchVO
                    .getDpdFinrulHVO();
                    objDpdRuldefSearchVO = objDpdRulhisSearchVO
                    .getDpdRuldefSearchVO();
                    objDpdRulMetricsSearchVO = objDpdRulhisSearchVO
                    .getDpdRulMetricsSearchVO();

                    objsearchFinalRuleResponse = new SearchFinalRuleResponse();
                    ruleDefResponse = new RuleDefResponse();
                    ruleMetricsResponse = new RuleMetricsResponse();

                    objDpdCmprulSearchVO = objDpdFinrulSearchVO
                    .getDpdCmprulHVO();
                    objDpdSimrulSearchVO = objDpdFinrulSearchVO
                    .getDpdSimrulHVO();
                    objSearchComplexRule = new SearchComplexRule();
                    objSearchSimpleRule = new SearchSimpleRule();

                    if (objDpdCmprulSearchVO != null) {
                        BeanUtility.copyBeanProperty(objDpdCmprulSearchVO,
                                objSearchComplexRule);
                        if (objDpdCmprulSearchVO.getCreationDate() != null) {
                            dtXMLCreatedDate = BeanUtility
                            .convertDateToXMLDate(objDpdCmprulSearchVO
                                    .getCreationDate(),
                                    dtXMLCreatedDate);

                            objSearchComplexRule
                            .setCreationDate(dtXMLCreatedDate);
                        }
                        if (objDpdCmprulSearchVO.getLastUpdatedDate() != null) {
                            dtXMLUpdatedDate = BeanUtility
                            .convertDateToXMLDate(objDpdCmprulSearchVO
                                    .getLastUpdatedDate(),
                                    dtXMLUpdatedDate);

                            objSearchComplexRule
                            .setLastUpdatedDate(dtXMLUpdatedDate);
                        }
                    }
                    objsearchFinalRuleResponse
                    .setSearchComplexRule(objSearchComplexRule);
                    if (objDpdSimrulSearchVO != null) {
                        BeanUtility.copyBeanProperty(objDpdSimrulSearchVO,
                                objSearchSimpleRule);
                        if (objDpdSimrulSearchVO.getCreationDate() != null) {

                            dtXMLCreatedDate = BeanUtility
                            .convertDateToXMLDate(objDpdSimrulSearchVO
                                    .getCreationDate(),
                                    dtXMLCreatedDate);

                            objSearchSimpleRule
                            .setCreationDate(dtXMLCreatedDate);
                        }
                        if (objDpdSimrulSearchVO.getLastUpdatedDate() != null) {
                            dtXMLUpdatedDate = BeanUtility
                            .convertDateToXMLDate(objDpdSimrulSearchVO
                                    .getLastUpdatedDate(),
                                    dtXMLUpdatedDate);

                            objSearchSimpleRule
                            .setLastUpdatedDate(dtXMLUpdatedDate);
                        }
                    }
                    objsearchFinalRuleResponse
                    .setSearchSimpleRule(objSearchSimpleRule);

                    BeanUtility.copyBeanProperty(objDpdFinrulSearchVO,
                            objsearchFinalRuleResponse);
					if (objDpdFinrulSearchVO.getCreationDate() != null) {
						objsearchFinalRuleResponse
						.setCreationDate(objDpdFinrulSearchVO.getCreationDate());
					}
					if (objDpdFinrulSearchVO.getLastUpdatedDate() != null) {						
						objsearchFinalRuleResponse
						.setLastUpdatedDate(objDpdFinrulSearchVO.getLastUpdatedDate());
					}

                    BeanUtility.copyBeanProperty(objDpdRuldefSearchVO,
                            ruleDefResponse);
                    BeanUtility.copyBeanProperty(objDpdRulMetricsSearchVO,
                            ruleMetricsResponse);

                    objRuleResponseType
                    .setSearchFinalRuleResponse(objsearchFinalRuleResponse);
                    objRuleResponseType.setRuleDefResponse(ruleDefResponse);
                    objRuleResponseType
                    .setRuleMetricsResponse(ruleMetricsResponse);
                    BeanUtility.copyBeanProperty(objDpdRulhisSearchVO,
                            objRuleResponseType);

                    lstRuleResponseType.add(objRuleResponseType);
                }
            } else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }
            }else{
                throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
                }
        }catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return lstRuleResponseType;
    }


    /**
     * This method is used for searching Tracking result
     * 
     * @param ui
     * @return List of RunRecreatorResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETRUNRECREATE_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RunRecreatorResponseType> getRunRecreateDetails(
            @Context UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<RuleTracerServiceResultVO> arlServiceResultVOs;
        RuleTracerSeachCriteriaServiceVO objTestSeachCritServiceVO = new RuleTracerSeachCriteriaServiceVO();
        RunRecreatorResponseType objRunRecreatorResponseType;
        List<RunRecreatorResponseType> arlResponse = new ArrayList<RunRecreatorResponseType>();
        try {
            queryParams = uriParam.getQueryParameters();
            if (queryParams != null) {
                if (queryParams.containsKey(OMDConstants.RULEID)) {
                    objTestSeachCritServiceVO.setStrRuleID(queryParams
                            .getFirst(OMDConstants.RULEID));
                }

                if (queryParams.containsKey(OMDConstants.RUN_RECREATOR_ID)) {
                    objTestSeachCritServiceVO.setStrTraceID(queryParams
                            .getFirst(OMDConstants.RUN_RECREATOR_ID));
                }

                if (queryParams.containsKey(OMDConstants.USERID)) {
                    objTestSeachCritServiceVO.setStrUserName(queryParams
                            .getFirst(OMDConstants.USERID));
                } else {
                    objTestSeachCritServiceVO.setStrUserName(OMDConstants.SELECT);
                }

                if (queryParams.containsKey(OMDConstants.LKBK_WEEKS)) {
                    objTestSeachCritServiceVO.setStrLookBackWeeks(queryParams
                            .getFirst(OMDConstants.LKBK_WEEKS));
                } else {
                    objTestSeachCritServiceVO.setStrLookBackWeeks(OMDConstants.SELECT);
                }
                if (queryParams.containsKey(OMDConstants.CASE_ID1)) {
                    objTestSeachCritServiceVO.setStrCaseID(queryParams
                            .getFirst(OMDConstants.CASE_ID1));
                }

                arlServiceResultVOs = objRunRecreatorServiceIntf
                .searchTrackingResult(objTestSeachCritServiceVO);
                if (RMDCommonUtility.isCollectionNotEmpty(arlServiceResultVOs)) {
                    for (RuleTracerServiceResultVO ruleTracerServiceResultVO : arlServiceResultVOs) {
                        objRunRecreatorResponseType = new RunRecreatorResponseType();

                        BeanUtility.copyBeanProperty(ruleTracerServiceResultVO,
                                objRunRecreatorResponseType);

                        if (null != ruleTracerServiceResultVO
                                .getStrCreationDate()) {
                            objRunRecreatorResponseType
                            .setCreationTime(ruleTracerServiceResultVO
                                    .getStrCreationDate().toString());
                        }
                        if (null != ruleTracerServiceResultVO
                                .getStrLastUpdatedDate()) {
                            objRunRecreatorResponseType
                            .setFinishTime(ruleTracerServiceResultVO
                                    .getStrLastUpdatedDate().toString());
                        }
                        arlResponse.add(objRunRecreatorResponseType);

                    }
                } else {
                    throw new OMDNoResultFoundException(
                            BeanUtility
                            .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility
                                    .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                    new String[] {},
                                    BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.QUERY_PARAMETERS_NOT_PASSED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.QUERY_PARAMETERS_NOT_PASSED),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {

            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return arlResponse;
    }

    /**
     * This method is used for saving run test
     * 
     * @param objRuleTesterRequestType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.REQUESTRULETEST)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void requestRuleTest(RuleTesterRequestType objRuleTesterRequestType)
    throws RMDServiceException {
        RuleTesterServiceVO objRuleTesterServiceVO;
        List<String> arlAsset;
        String[] assetNo;
        int seqId;
        String strLanguage = null;
        String strUserLanguage = null;
        String strUserID = null;
        try {
            // Start getting the header values
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            strUserID = getRequestHeader(OMDConstants.USERID);
            // End getting the header values
            objRuleTesterServiceVO = new RuleTesterServiceVO();
            if (objRuleTesterRequestType != null) {
                BeanUtility.copyBeanProperty(objRuleTesterRequestType,
                        objRuleTesterServiceVO);
                arlAsset = objRuleTesterRequestType.getAssetNumbers();
                assetNo = arlAsset.toArray(new String[arlAsset.size()]);
                objRuleTesterServiceVO.setStrAssetNumber(assetNo);
                objRuleTesterServiceVO.setStrLanguage(strLanguage);
                objRuleTesterServiceVO.setStrUserName(strUserID);
                objRuleTesterServiceVO.setStrUserLanguage(strUserLanguage);
                seqId = objRuleTesterServiceIntf
                .saveRunTest(objRuleTesterServiceVO);
                LOG.debug("New Test Id is ::" + seqId);
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for saving rule
     * 
     * @param FinalRuleRequestType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.SAVERULE)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String saveRule(FinalRuleRequestType objFinalRuleRequestType)
    throws RMDServiceException {
        FinalRuleServiceVO objFinalRuleServiceVO = null;
        RuleHistoryServiceVO objRuleHistoryServiceVO = null;
        SimpleRuleServiceVO objSimpleRuleServiceVO = null;
        ComplexRuleServiceVO objComplexRuleServiceVO = null;
        RuleDefinitionServiceVO objRuleDefinitionServiceVO = null;

        ArrayList<RuleDefinitionServiceVO> listRuleDefinitionServiceVO = new ArrayList<RuleDefinitionServiceVO>();
        ArrayList<SimpleRuleServiceVO> listSimpleRuleServiceVO = new ArrayList<SimpleRuleServiceVO>();
        ArrayList<ComplexRuleServiceVO> listComplexRuleServiceVO = new ArrayList<ComplexRuleServiceVO>();
        ArrayList<RuleHistoryServiceVO> listRuleHistoryServiceVO = new ArrayList<RuleHistoryServiceVO>();

        List<ComplexRule> listComplexRule = null;
        List<SimpleRule> listSimpleRule = null;
        List<RuleHistory> listRuleHistory = null;
        List<RuleDefinition> listRuleDefinition = null;
        String strFinalRuleId = null;

        List<RuleDefinitionModel> ruleDefinitionModelList;
        List<RuleDefinitionConfig> ruleDefinitionConfig;
        List<RuleDefinitionFleet> ruleDefinitionFleet;
        List<RuleDefinitionCustomer> ruleDefinitionCustomerList;

        ArrayList<RuleDefCustomerServiceVO> arlRuleDefCustomer;
        RuleDefCustomerServiceVO objRuleDefCustomerServiceVO = null;
        ArrayList<RuleDefModelServiceVO> arlRuleDefModel;
        ArrayList<RuleDefConfigServiceVO> innerarlRuleDefModel;
        RuleDefModelServiceVO objRuleDefModelServiceVO = null;
        ArrayList<RuleDefConfigServiceVO> arlRuleDefConfig;
        RuleDefConfigServiceVO objRuleDefConfigServiceVO = null;
        ArrayList<RuleDefFleetServiceVO> arlRuleDefFleet;
        RuleDefFleetServiceVO objRuleDefFleetServiceVO = null;

        RuleDefConfigServiceVO innerRuleDefConfigServiceVO = null;

        List<RuleDefinitionConfig> innerListRuleconfig = null;

        ComplexRule complexRule = null;
        SimpleRule simpleRule = null;
        RuleHistory ruleHistory = null;
        RuleDefinition ruleDefinition = null;
        RuleDefinitionCustomer ruleDefinitionCustomer = null;
        RuleDefinitionModel ruleDefinitionModel1 = null;
        RuleDefinitionConfig ruleDefinitionConfig2 = null;
        RuleDefinitionConfig ruleDefinitionConfig3 = null;
        RuleDefinitionFleet ruleDefinitionFleet1 = null;
        String strLanguage = null;
        String strUserLanguage = null;
        String strUserID = null;
		String userCustomer=null;
		String userCustomerObjidID=null;
		String isGERuleAUthor = null;
        List<SimpleRuleClause> clauses = null;
        
        SimpleRuleServiceVO objClearingLogicSimpleRuleServiceVO = null;
        ComplexRuleServiceVO objClearingLogicComplexRuleServiceVO = null;
        ArrayList<SimpleRuleServiceVO> listClearingLogicSimpleRuleServiceVO = new ArrayList<SimpleRuleServiceVO>();
        ArrayList<ComplexRuleServiceVO> listClearingLogicComplexRuleServiceVO = new ArrayList<ComplexRuleServiceVO>();
        List<ComplexRule> listClearingLogicComplexRule = null;
        List<SimpleRule> listClearingLogicSimpleRule = null;
        ComplexRule clearingLogicComplexRule = null;
        SimpleRule clearingLogicSimpleRule = null;
        List<SimpleRuleClause> clearingLogicClauses = null;
        ClearingLogicRuleServiceVO objClearingLogicRuleServiceVO = null;
        String healthCheck = null;
        String perfCheckID = null;
        String defaultUom=null;
        List<String> rorRules=new ArrayList<String>();
        List<String> nonRoRRules=null;
        try {

            // Start getting the header values
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            strUserID = getRequestHeader(OMDConstants.STR_USERNAME);
			isGERuleAUthor = getRequestHeader(OMDConstants.IS_GE_RULE_AUTHOR);
			userCustomerObjidID = getRequestHeader("userCustomerObjid");
			userCustomer= getRequestHeader("strUserCustomer");
			defaultUom=getRequestHeader("measurementSystem");
            // End getting the header values
            if (objFinalRuleRequestType == null) {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }

    // iterate simple rules and 
            List<SimpleRule> simpleRules = objFinalRuleRequestType.getSimpleRule();
            if(null != simpleRules && simpleRules.size() >0){
                clauses = new ArrayList<SimpleRuleClause>();
                Iterator<SimpleRule> iterator = simpleRules.iterator();
                while(iterator.hasNext()){
                    clauses= iterator.next().getSimpleRuleClause();
                }
            }
            
			if(null!=isGERuleAUthor&&isGERuleAUthor.equalsIgnoreCase(RMDCommonConstants.N_LETTER_UPPER)){
			List<ComplexRule> ComplexRules = objFinalRuleRequestType.getComplexRule();
			if(null!=ComplexRules&&ComplexRules.size()>=15){
				
				throw new OMDInValidInputException(OMDConstants.MAXIMUM_10_COMPLEX);
			}							
			
			if(null==objFinalRuleRequestType.getRuleType() || !OMDConstants.ALERTS.equalsIgnoreCase(objFinalRuleRequestType.getRuleType())){
				if (null != objFinalRuleRequestType.getFinalruleID()
						&& !objFinalRuleRequestType.getFinalruleID().isEmpty()) {
					throw new OMDInValidInputException(OMDConstants.NO_RULE_PERMISSION_TO_MODIFY);
				}else{
					throw new OMDInValidInputException(OMDConstants.NO_RULE_PERMISSION_TO_CREATE);
				}
			}
			boolean familyMatch=false;
			List<ElementVO> arlLookupDetails = ruleCommonServiceInf
					.getFamiliesForAlertsRuleAuthor(strLanguage, userCustomer);
			if (RMDCommonUtility.isCollectionNotEmpty(arlLookupDetails)) {
				for (ElementVO elementVO : arlLookupDetails) {
					if(elementVO.getName().equalsIgnoreCase(objFinalRuleRequestType.getFamily()))
						familyMatch=true;
				}
			}
			
			if(!familyMatch){
				if (null != objFinalRuleRequestType.getFinalruleID()
						&& !objFinalRuleRequestType.getFinalruleID().isEmpty()) {
					throw new OMDInValidInputException(OMDConstants.NO_RULE_PERMISSION_TO_MODIFY);
				}else{
					throw new OMDInValidInputException(OMDConstants.NO_RULE_PERMISSION_TO_CREATE);
				}
			}
			
			
				boolean isUserCustomerSelected = false;
				if (null != objFinalRuleRequestType.getFinalruleID()
						&& !objFinalRuleRequestType.getFinalruleID().isEmpty()) {
					List<ElementVO> lstCustomer = objRuleServiceIntf
							.getCustomerForRule(
									objFinalRuleRequestType.getFinalruleID(),
									strLanguage);
					if(null!=lstCustomer&&!lstCustomer.isEmpty()&&lstCustomer.size()==1){
					for (Iterator iterator = lstCustomer.iterator(); iterator
							.hasNext();) {
						ElementVO elementVO = (ElementVO) iterator.next();
						if (null != elementVO.getCustomerSeqId()
								&& !elementVO.getCustomerSeqId().isEmpty()
								&& null != elementVO.getName()
								&& !elementVO.getName().isEmpty()) {
							if (elementVO.getName().equalsIgnoreCase(
									userCustomer)
									&& elementVO.getCustomerSeqId()
											.equalsIgnoreCase(
													userCustomerObjidID)) {
								isUserCustomerSelected = true;
							}
						}
					}
					}
					if (!isUserCustomerSelected) {
						throw new OMDInValidInputException(
								OMDConstants.NO_RULE_PERMISSION_TO_CREATE);
					}else{
						isUserCustomerSelected=false;
					}
				}
					
					List<RuleDefinition> ruleDefinitions = objFinalRuleRequestType
							.getRuleDefinitionList();
					Iterator<RuleDefinition> ruleDefinitionList = ruleDefinitions
							.iterator();

					while (ruleDefinitionList.hasNext()) {
						RuleDefinition definition = (RuleDefinition) ruleDefinitionList
								.next();
						List<RuleDefinitionCustomer> customers = definition
								.getRuleDefinitionCustomer();
						if (null != customers && customers.size()==1) {
							Iterator<RuleDefinitionCustomer> customerList = customers
									.iterator();
							while (customerList.hasNext()) {

								RuleDefinitionCustomer definitionCustomer = (RuleDefinitionCustomer) customerList
										.next();

								if (null != definitionCustomer.getCustomer()
										&& !definitionCustomer.getCustomer()
												.isEmpty()
										&& null != definitionCustomer
												.getCustomerID()
										&& !definitionCustomer.getCustomerID()
												.isEmpty()) {
									if (definitionCustomer.getCustomer()
											.equalsIgnoreCase(userCustomer)
											&& definitionCustomer
													.getCustomerID()
													.equalsIgnoreCase(
															userCustomerObjidID)) {
										isUserCustomerSelected = true;
									}
								}
							}
						}
					}
					if (!isUserCustomerSelected) {
						if (null != objFinalRuleRequestType.getFinalruleID()
								&& !objFinalRuleRequestType.getFinalruleID().isEmpty()) {
							throw new OMDInValidInputException(OMDConstants.NO_RULE_PERMISSION_TO_MODIFY);
						}else{
							throw new OMDInValidInputException(OMDConstants.NO_RULE_PERMISSION_TO_CREATE);
						}
					}
				}
            if (validateSaveRule(objFinalRuleRequestType,
                    objFinalRuleRequestType.getSimpleRule(), clauses,
                    objFinalRuleRequestType.getComplexRule(),
                    objFinalRuleRequestType.getRuleHistory(),
                    objFinalRuleRequestType.getRuleDefinitionList(),
                    objFinalRuleRequestType.getClearingLogicRule())) {
            // Complex Rule mapping
            objFinalRuleServiceVO = new FinalRuleServiceVO();
            listComplexRule = objFinalRuleRequestType.getComplexRule();
            for (final Iterator<ComplexRule> iteratorComplexRule = listComplexRule
                    .iterator(); iteratorComplexRule.hasNext();) {
                complexRule = (ComplexRule) iteratorComplexRule.next();
				if("P".equalsIgnoreCase(complexRule.getTimeFlag())&&("C".equalsIgnoreCase(complexRule.getRule1Type())||"C".equalsIgnoreCase(complexRule.getRule2Type()))){
					throw new OMDInValidInputException(OMDConstants.ONLY_SIMRULES_ALLOWED);					
				}
				if("F".equalsIgnoreCase(complexRule.getRule1Type())){
					rorRules.add(complexRule.getRule1());
				}
				if("F".equalsIgnoreCase(complexRule.getRule2Type())){
					rorRules.add(complexRule.getRule2());
				}
                objComplexRuleServiceVO = new ComplexRuleServiceVO();
                BeanUtility.copyBeanProperty(complexRule,
                        objComplexRuleServiceVO);
                listComplexRuleServiceVO.add(objComplexRuleServiceVO);
            }
            objFinalRuleServiceVO.setArlComplexRule(listComplexRuleServiceVO);

            // Simple Rule mapping

            listSimpleRule = objFinalRuleRequestType.getSimpleRule();
            if (RMDCommonUtility.isCollectionNotEmpty(listSimpleRule)) {
                for (final Iterator<SimpleRule> iteratorSimpleRule = listSimpleRule
                        .iterator(); iteratorSimpleRule.hasNext();) {
                    simpleRule = (SimpleRule) iteratorSimpleRule.next();
                    objSimpleRuleServiceVO = new SimpleRuleServiceVO();
                    BeanUtility.copyBeanProperty(simpleRule,
                            objSimpleRuleServiceVO);
                    listSimpleRuleServiceVO.add(objSimpleRuleServiceVO);
                }
                objFinalRuleServiceVO.setArlSimpleRule(listSimpleRuleServiceVO);
            }/* else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }*/
            // rule history
            listRuleHistory = objFinalRuleRequestType.getRuleHistory();
            for (Iterator<RuleHistory> iteratorRuleHistory = listRuleHistory
                    .iterator(); iteratorRuleHistory.hasNext();) {
                ruleHistory = (RuleHistory) iteratorRuleHistory.next();
                objRuleHistoryServiceVO = new RuleHistoryServiceVO();
                BeanUtility.copyBeanProperty(ruleHistory,
                        objRuleHistoryServiceVO);
                listRuleHistoryServiceVO.add(objRuleHistoryServiceVO);
            }
            objFinalRuleServiceVO.setArlRuleHistoryVO(listRuleHistoryServiceVO);
            // rule definition
            listRuleDefinition = objFinalRuleRequestType
            .getRuleDefinitionList();

            for (final Iterator<RuleDefinition> iteratorRuleDefinition = listRuleDefinition
                    .iterator(); iteratorRuleDefinition.hasNext();) {
                ruleDefinition = (RuleDefinition) iteratorRuleDefinition.next();
                if (healthCheck == null && ruleDefinition.getHealthFactor() != null) {
                    healthCheck = ruleDefinition.getHealthFactor();
                }
                else if (healthCheck != null &&  ruleDefinition.getHealthFactor() == null) {
                    ruleDefinition.setHealthFactor(healthCheck);
                }
                if (perfCheckID == null && ruleDefinition.getPerfHealthCheckID() != null) {
                    perfCheckID = ruleDefinition.getPerfHealthCheckID();
                }else if (perfCheckID != null &&  ruleDefinition.getPerfHealthCheckID() == null) {
                    ruleDefinition.setPerfHealthCheckID(perfCheckID);
                }
                objRuleDefinitionServiceVO = new RuleDefinitionServiceVO();
                // this Rule defintion in turn has 4 list
                // List<RuleDefinitionCustomer> List<RuleDefinitionModel>
                // List<RuleDefinitionConfig> List<RuleDefinitionFleet>
                // so need to iterate all the list and collect the values
                ruleDefinitionCustomerList = ruleDefinition
                .getRuleDefinitionCustomer();
                arlRuleDefCustomer = new ArrayList<RuleDefCustomerServiceVO>();
                if (RMDCommonUtility
                        .isCollectionNotEmpty(ruleDefinitionCustomerList)) {
                    Iterator<RuleDefinitionCustomer> iteratorRuleDef = ruleDefinitionCustomerList
                    .iterator();
                    while (iteratorRuleDef.hasNext()) {
                        ruleDefinitionCustomer = (RuleDefinitionCustomer) iteratorRuleDef
                        .next();
                        objRuleDefCustomerServiceVO = new RuleDefCustomerServiceVO();
                        BeanUtility.copyBeanProperty(ruleDefinitionCustomer,
                                objRuleDefCustomerServiceVO);
                        arlRuleDefCustomer.add(objRuleDefCustomerServiceVO);

                    }
                }

                objRuleDefinitionServiceVO
                .setArlRuleDefCustomer(arlRuleDefCustomer);

                // List<RuleDefinitionModel>

                ruleDefinitionModelList = ruleDefinition
                .getRuleDefinitionModelList();
                arlRuleDefModel = new ArrayList<RuleDefModelServiceVO>();
                for (final Iterator<RuleDefinitionModel> iteratorRuleDefinitionModel = ruleDefinitionModelList
                        .iterator(); iteratorRuleDefinitionModel.hasNext();) {
                    ruleDefinitionModel1 = (RuleDefinitionModel) iteratorRuleDefinitionModel
                    .next();
                    objRuleDefModelServiceVO = new RuleDefModelServiceVO();
                    BeanUtility.copyBeanProperty(ruleDefinitionModel1,
                            objRuleDefModelServiceVO);

                    innerListRuleconfig = ruleDefinitionModel1
                    .getRuleDefinitionConfig();
                    innerarlRuleDefModel = new ArrayList<RuleDefConfigServiceVO>();
                    for (final Iterator<RuleDefinitionConfig> iteratorRuleDefinitionConfig = innerListRuleconfig
                            .iterator(); iteratorRuleDefinitionConfig.hasNext();) {
                        ruleDefinitionConfig2 = (RuleDefinitionConfig) iteratorRuleDefinitionConfig
                        .next();
                        innerRuleDefConfigServiceVO = new RuleDefConfigServiceVO();
                        BeanUtility.copyBeanProperty(ruleDefinitionConfig2,
                                innerRuleDefConfigServiceVO);
                        innerarlRuleDefModel.add(innerRuleDefConfigServiceVO);
                    }
                    objRuleDefModelServiceVO
                    .setArlConfigService(innerarlRuleDefModel);
                    arlRuleDefModel.add(objRuleDefModelServiceVO);
                }
                objRuleDefinitionServiceVO.setArlRuleDefModel(arlRuleDefModel);

                // List<RuleDefinitionConfig>
                arlRuleDefConfig = new ArrayList<RuleDefConfigServiceVO>();
                ruleDefinitionConfig = ruleDefinition.getRuleDefinitionConfig();
                for (final Iterator<RuleDefinitionConfig> iteratorRuleDefinitionConfig = ruleDefinitionConfig
                        .iterator(); iteratorRuleDefinitionConfig.hasNext();) {
                    ruleDefinitionConfig3 = (RuleDefinitionConfig) iteratorRuleDefinitionConfig
                    .next();
                    objRuleDefConfigServiceVO = new RuleDefConfigServiceVO();
                    BeanUtility.copyBeanProperty(ruleDefinitionConfig3,
                            objRuleDefConfigServiceVO);
                    arlRuleDefConfig.add(objRuleDefConfigServiceVO);
                }
                objRuleDefinitionServiceVO
                .setArlRuleDefConfig(arlRuleDefConfig);

                // List<RuleDefinitionFleet>
                arlRuleDefFleet = new ArrayList<RuleDefFleetServiceVO>();
                ruleDefinitionFleet = ruleDefinition.getRuleDefinitionFleet();
                for (final Iterator<RuleDefinitionFleet> iteratorRuleDefinitionFleet = ruleDefinitionFleet
                        .iterator(); iteratorRuleDefinitionFleet.hasNext();) {
                    ruleDefinitionFleet1 = (RuleDefinitionFleet) iteratorRuleDefinitionFleet
                    .next();
                    objRuleDefFleetServiceVO = new RuleDefFleetServiceVO();
                    BeanUtility.copyBeanProperty(ruleDefinitionFleet1,
                            objRuleDefFleetServiceVO);
                    arlRuleDefFleet.add(objRuleDefFleetServiceVO);
                }
                objRuleDefinitionServiceVO.setArlRuleDefFleet(arlRuleDefFleet);

                BeanUtility.copyBeanProperty(ruleDefinition,
                        objRuleDefinitionServiceVO);
                /*
                 * Include Flag - Exclude mapping should be negated, 
                 * hence this doesn't use the BeanUtility for mapping
                 */
                if( null != ruleDefinition.getIncludeFlag() ) {
                    boolean includeFlag = OMDConstants.Y.equalsIgnoreCase(ruleDefinition.getIncludeFlag());
                    if(!includeFlag){
                        objRuleDefinitionServiceVO.setStrExclude(OMDConstants.Y);
                    }else{
                        objRuleDefinitionServiceVO.setStrExclude(OMDConstants.N);
                    }
                }
                
                listRuleDefinitionServiceVO.add(objRuleDefinitionServiceVO);
            }
            objFinalRuleServiceVO
            .setArlRuleDefinition(listRuleDefinitionServiceVO);

            ClearingLogicRule clearingLogicRule = objFinalRuleRequestType.getClearingLogicRule();
            if(null!=clearingLogicRule && ((clearingLogicRule.getComplexRule()!=null && clearingLogicRule.getComplexRule().size()>0) || (clearingLogicRule.getSimpleRule()!=null && clearingLogicRule.getSimpleRule().size()>0))){
                List<SimpleRule> clearingLogicSimpleRules = clearingLogicRule.getSimpleRule();
                if(null != clearingLogicSimpleRules && clearingLogicSimpleRules.size() >0){
                    clearingLogicClauses = new ArrayList<SimpleRuleClause>();
                    Iterator<SimpleRule> iterator = clearingLogicSimpleRules.iterator();
                    while(iterator.hasNext()){
                        clearingLogicClauses= iterator.next().getSimpleRuleClause();
                    }
                }
                //TODO add validate clearingLogicSimpleRule
                listClearingLogicComplexRule = clearingLogicRule.getComplexRule();
                if(listClearingLogicComplexRule!=null) {
                    for (final Iterator<ComplexRule> iteratorClearingLogicComplexRule = listClearingLogicComplexRule
                            .iterator(); iteratorClearingLogicComplexRule.hasNext();) {
                        clearingLogicComplexRule = (ComplexRule) iteratorClearingLogicComplexRule.next();
						if("P".equalsIgnoreCase(clearingLogicComplexRule.getTimeFlag())&&("C".equalsIgnoreCase(clearingLogicComplexRule.getRule1Type())||"C".equalsIgnoreCase(clearingLogicComplexRule.getRule2Type()))){
							throw new OMDInValidInputException(OMDConstants.ONLY_SIMRULES_ALLOWED_CL);					
						}
						if("F".equalsIgnoreCase(clearingLogicComplexRule.getRule1Type())){
							rorRules.add(clearingLogicComplexRule.getRule1());
						}
						if("F".equalsIgnoreCase(clearingLogicComplexRule.getRule2Type())){
							rorRules.add(clearingLogicComplexRule.getRule2());
						}
                        objClearingLogicComplexRuleServiceVO = new ComplexRuleServiceVO();
                        BeanUtility.copyBeanProperty(clearingLogicComplexRule,
                                objClearingLogicComplexRuleServiceVO);
                        listClearingLogicComplexRuleServiceVO.add(objClearingLogicComplexRuleServiceVO);
                    }   
                }
                objClearingLogicRuleServiceVO = new ClearingLogicRuleServiceVO();
                objClearingLogicRuleServiceVO.setArlComplexRule(listClearingLogicComplexRuleServiceVO);
                listClearingLogicSimpleRule = clearingLogicRule.getSimpleRule();
                if (RMDCommonUtility.isCollectionNotEmpty(listClearingLogicSimpleRule)) {
                    for (final Iterator<SimpleRule> iteratorClearingLogicSimpleRule = listClearingLogicSimpleRule
                            .iterator(); iteratorClearingLogicSimpleRule.hasNext();) {
                        clearingLogicSimpleRule = (SimpleRule) iteratorClearingLogicSimpleRule.next();
                        objClearingLogicSimpleRuleServiceVO = new SimpleRuleServiceVO();
                        BeanUtility.copyBeanProperty(clearingLogicSimpleRule,
                                objClearingLogicSimpleRuleServiceVO);
                        listClearingLogicSimpleRuleServiceVO.add(objClearingLogicSimpleRuleServiceVO);
                    }
                    objClearingLogicRuleServiceVO.setArlSimpleRule(listClearingLogicSimpleRuleServiceVO);
                }
                BeanUtility.copyBeanProperty(clearingLogicRule,
                        objClearingLogicRuleServiceVO);

                objClearingLogicRuleServiceVO.setStrUserLanguage(strUserLanguage);
                objClearingLogicRuleServiceVO.setStrLanguage(strLanguage);
                objClearingLogicRuleServiceVO.setStrUserName(strUserID);
                objFinalRuleServiceVO.setClearingLogicRule(objClearingLogicRuleServiceVO);
            }
            
            BeanUtility.copyBeanProperty(objFinalRuleRequestType,
                    objFinalRuleServiceVO);
            objFinalRuleServiceVO.setStrUserLanguage(strUserLanguage);
            objFinalRuleServiceVO.setStrLanguage(strLanguage);
            objFinalRuleServiceVO.setStrUserName(strUserID);
			objFinalRuleServiceVO.setDefaultUOM(defaultUom);
			if (RMDCommonUtility.isCollectionNotEmpty(objFinalRuleServiceVO
					.getArlRuleHistoryVO())&&RMDCommonUtility.isCollectionNotEmpty(rorRules)) {
				nonRoRRules=objRuleServiceIntf
		            .getRoRRuleInformation(rorRules, objFinalRuleServiceVO.getStrFinalRuleID());
				if(RMDCommonUtility.isCollectionNotEmpty(nonRoRRules)){
					throw new OMDInValidInputException(OMDConstants.NON_ROR_RULES_ERROR,OMDConstants.NON_ROR_RULES_ERROR_MESSAGE+nonRoRRules.toString());
				}
			}
            strFinalRuleId = objRuleServiceIntf
            .saveFinalRule(objFinalRuleServiceVO);
            LOG.debug("Final Rule Id ::" + strFinalRuleId);
    }else{
			throw new OMDInValidInputException(OMDConstants.RULE_INVALID_VALUE);
        }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return strFinalRuleId;
    }

    /**
     * This method is used to Activate the deactivated rule and deactivate the
     * activated rule
     * 
     * @param requestType
     * @return status
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.ACTIVEDEACTIVATIVATE_RULE)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void activateDeactivateRule(RuleRequestType requestType)
    throws RMDServiceException {
        LOG.debug("in side the activateDeactivateRule");
        List<String> rulesList;
        String strRuleID = null;
        String strLanguage = null;
        String strUserID = null;
        StringBuffer strBfrStatus=new StringBuffer("");
        List<String> activeRuleID;
        
        try {
            // Start getting the header values
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserID = getRequestHeader(OMDConstants.STR_USERNAME);
            // End getting the header values
            if (requestType != null) {
                if (requestType.getRuleID() != null && strLanguage != null
                        && strUserID != null) {
                    strRuleID = requestType.getRuleID();

                    if ((OMDConstants.CLONE).equals(requestType.getIsactive())) {

                        LOG.debug(requestType.getRuleID() + strLanguage
                                + strUserID);
                        rulesList = activateruleServiceInf.activateRule(
                                strRuleID, strLanguage, strUserID);
                        
                        if (RMDCommonUtility.isCollectionNotEmpty(rulesList)) {
                            StringBuffer strDeactiveChildRules=new StringBuffer();
                                for (Iterator iterator = rulesList.iterator(); iterator
                                    .hasNext();) {
                                    String strDeActiveRules = (String) iterator.next();
                                    strDeactiveChildRules.append(strDeActiveRules).append(RMDCommonConstants.COMMMA_SEPARATOR);
                                }
                                if(strDeactiveChildRules.length()>0){
                                    strDeactiveChildRules=strDeactiveChildRules.deleteCharAt(strDeactiveChildRules.length()-1);
                                    throw new OMDActiveRuleException(OMDConstants.ACTIVE_UPDATION_FAILURE, strDeactiveChildRules.toString(),OMDConstants.MINOR_ERROR);
                                }
                        } 

                    } else if ((OMDConstants.Y).equals(requestType.getIsactive())) {
                        activeRuleID = activateruleServiceInf.deActivateRule(strRuleID,strLanguage,strUserID);
                        LOG.debug("Status of the deActivate process::" + activeRuleID);
                        if( null!=activeRuleID && !activeRuleID.isEmpty()){
                            strBfrStatus= strBfrStatus.append(".Rule Ids :: "+activeRuleID.toString());
                            throw new OMDActiveRuleException(OMDConstants.DEACTIVE_UPDATION_FAILURE,strBfrStatus.toString(),OMDConstants.MINOR_ERROR);
                        }
                    } else {
                        throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
                    }
                } else {
                    throw new OMDInValidInputException( OMDConstants.INVALID_INPUTS);
                }
            }
        } catch(OMDActiveRuleException omdActiveRuleException){
            throw omdActiveRuleException;
        }catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
    }

    /**
     * This method is used to list the Rule functions
     * 
     * @param ui
     * @return
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GETRULEFUNCTIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RuleFunction> getRuleFunctions(@Context UriInfo ui)
    throws RMDServiceException {
        LOG.debug("control inside the getRuleFunctions");
        List<RuleFunction> resCongfigList = null;
        RuleFunction respRuleFunction = null;
        List<ElementVO> arlConfig = null;
        ElementVO elementVO = null;
        ListIterator<ElementVO> listIterator = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui
            .getQueryParameters();
            String level =OMDConstants.EMPTY_STRING;

            if (queryParams.containsKey(OMDConstants.RULE_LEVEL)) {
                level = queryParams.getFirst(OMDConstants.RULE_LEVEL);
            }

            if (OMDConstants.RULE_SIMPLE.equals(level)) {
                arlConfig = objRuleServiceIntf
                .getconfigFcnValues(getRequestHeader(OMDConstants.LANGUAGE));
            }
            
            if (RMDCommonUtility.isCollectionNotEmpty(arlConfig)) {
                resCongfigList = new ArrayList<RuleFunction>();
                LOG.debug("Coming from service call objRuleServiceIntf.getconfigFcnValues");
                listIterator = arlConfig.listIterator();

                while (listIterator.hasNext()) {
                    respRuleFunction = new RuleFunction();
                    elementVO = (ElementVO) listIterator.next();
                    BeanUtility.copyBeanProperty(elementVO, respRuleFunction);

                    resCongfigList.add(respRuleFunction);
                }
            } else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }
        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return resCongfigList;

    }

    /**
     * This method is used to save Run trace details
     * 
     * @param objRunReCreateRequestType
     * @return
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.REQUESTRUNRECREATE)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void requestRunrecreate(
            RunReCreateRequestType objRunReCreateRequestType)
    throws RMDServiceException {
        RuleTracerServiceCriteriaVO objServiceCriteriaVO;
        int seqId;
        String strLanguage = null;
        String strUserLanguage = null;
        String strUserID = null;
        try {
            // Start getting the header values
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            strUserID = getRequestHeader(OMDConstants.USERID);
            // End getting the header values
            objServiceCriteriaVO = new RuleTracerServiceCriteriaVO();
            if (objRunReCreateRequestType != null) {
                BeanUtility.copyBeanProperty(objRunReCreateRequestType,
                        objServiceCriteriaVO);
                objServiceCriteriaVO.setStrLanguage(strLanguage);
                objServiceCriteriaVO.setStrUserLanguage(strUserLanguage);
                objServiceCriteriaVO.setStrUserName(strUserID);
                seqId = objRunRecreatorServiceIntf
                .saveRunTrace(objServiceCriteriaVO);
                LOG.debug("Requested RunRecreate Id : " + seqId);
            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for saving Virtual details
     * 
     * @param objFinalVirtualRequestType
     * @return
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.SAVEVIRTUAL)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String saveVirtual(FinalVirtualRequestType objFinalVirtualRequestType)
    throws RMDServiceException {
        LOG.debug("Inside saveVirtual");

        //String strSaved;
        FinalVirtualServiceVO objVirtualServiceVO = new FinalVirtualServiceVO();
        List<VirtualEquationServiceVO> arlVirtualEquation = new ArrayList<VirtualEquationServiceVO>();
        List<VirtualDefinitionServiceVO> arlVirtualDef = null;
        List<VirtualFilterServiceVO> arlVirtualFilter = new ArrayList<VirtualFilterServiceVO>();
        VirtualEquationServiceVO objVirtualEquationVO;
        VirtualDefinitionServiceVO objVirtualDefinitionVO;
        VirtualFilterServiceVO objVirtualFilterVO;
        List<VirtualDefCustomerServiceVO> arlVirtualDefCustomer = null;
        ArrayList<VirtualDefConfigServiceVO> arlVirtualDefConfig = null;
        VirtualDefCustomerServiceVO objCustomerServiceVO;
        VirtualDefConfigServiceVO objConfigServiceVO;
        List<VirtualDefFleetServiceVO> arlVirtualDefFleet = null;
        VirtualDefFleetServiceVO objVirtualDefFleetServiceVO;
        String strFinalVirtualId = null;
        try {

            objVirtualServiceVO
            .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            objVirtualServiceVO
            .setStrUserName(getRequestHeader(OMDConstants.STR_USERNAME));
            // End getting the header values
            if (objFinalVirtualRequestType == null) {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
            if (validateSaveVirtual(objFinalVirtualRequestType,
                    objFinalVirtualRequestType.getVirtualEquation(),
                    objFinalVirtualRequestType.getVirtualDefinition(),
                    objFinalVirtualRequestType.getVirtualFilter())) {
                    
                    BeanUtility.copyBeanProperty(objFinalVirtualRequestType,
                            objVirtualServiceVO);               
                    if (RMDCommonUtility
                            .isCollectionNotEmpty(objFinalVirtualRequestType
                                    .getVirtualEquation())) {
                        for (VirtualEquation equation : objFinalVirtualRequestType
                                .getVirtualEquation()) {
                            objVirtualEquationVO = new VirtualEquationServiceVO();
                            BeanUtility
                                    .copyBeanProperty(equation, objVirtualEquationVO);
                            arlVirtualEquation.add(objVirtualEquationVO);
                        }
                        objVirtualServiceVO.setArlVirtualEquation(arlVirtualEquation);
                    }
        
                    if (RMDCommonUtility
                            .isCollectionNotEmpty(objFinalVirtualRequestType
                                    .getVirtualFilter())) {
                        for (VirtualFilter filter : objFinalVirtualRequestType
                                .getVirtualFilter()) {
                            objVirtualFilterVO = new VirtualFilterServiceVO();
                            BeanUtility.copyBeanProperty(filter, objVirtualFilterVO);
                            arlVirtualFilter.add(objVirtualFilterVO);
                        }
                        objVirtualServiceVO.setArlVirtualFilter(arlVirtualFilter);
                    }
                    arlVirtualDef = new ArrayList<VirtualDefinitionServiceVO>();
                        if(!RMDCommonUtility.isCollectionNotEmpty(objFinalVirtualRequestType.getVirtualDefinition())){
                        for (VirtualDefinition definition : objFinalVirtualRequestType
                                .getVirtualDefinition()) {
                            objVirtualDefinitionVO = new VirtualDefinitionServiceVO();
                            BeanUtility
                            .copyBeanProperty(definition, objVirtualDefinitionVO);
            
                            arlVirtualDefCustomer = new ArrayList<VirtualDefCustomerServiceVO>();
                            for (VirtualDefCustomer cust : definition
                                    .getVirtualDefCustomer()) {
                                objCustomerServiceVO = new VirtualDefCustomerServiceVO();
                                BeanUtility.copyBeanProperty(cust, objCustomerServiceVO);
                                arlVirtualDefFleet = new ArrayList<VirtualDefFleetServiceVO>();
                                for (VirtualDefFleet fleet : cust.getVirtualDefFleet()) {
                                    objVirtualDefFleetServiceVO = new VirtualDefFleetServiceVO();
                                    BeanUtility.copyBeanProperty(fleet,
                                            objVirtualDefFleetServiceVO);
                                    arlVirtualDefFleet.add(objVirtualDefFleetServiceVO);
                                }
                                objCustomerServiceVO
                                .setArlVirtualDefFleet(arlVirtualDefFleet);
                                arlVirtualDefCustomer.add(objCustomerServiceVO);
                            }
                            objVirtualDefinitionVO
                            .setArlVirtualDefCustomer(arlVirtualDefCustomer);
            
                            arlVirtualDefConfig = new ArrayList<VirtualDefConfigServiceVO>();
                            for (VirtualDefConfig config : definition.getVirtualDefConfig()) {
                                objConfigServiceVO = new VirtualDefConfigServiceVO();
                                BeanUtility.copyBeanProperty(config, objConfigServiceVO);
                                arlVirtualDefConfig.add(objConfigServiceVO);
                            }
                            objVirtualDefinitionVO
                            .setArlVirtualDefConfig(arlVirtualDefConfig);
                            arlVirtualDef.add(objVirtualDefinitionVO);
                        }
                    }
                    objVirtualServiceVO.setArlVirtualDef(arlVirtualDef);
        
                    strFinalVirtualId = objVirtualServiceIntf.saveVirtual(objVirtualServiceVO);
                    LOG.debug("New Virtual Created :::" + strFinalVirtualId);
            }
            else{
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return strFinalVirtualId;
    }

    /**
     * This method is used for fetching Virtual details
     * 
     * @param uriParam
     * @return list of FinalVirtualResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETVIRTUAL_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public FinalVirtualResponseType getVirtualDetails(@PathParam(OMDConstants.VIRUTALS_ID) final String virtualId)
    throws RMDServiceException {
        FinalVirtualServiceVO objFinalVirtualServiceVO;
        FinalVirtualResponseType objVirtualResponseType = null;
        FinalVirtualServiceVO finalVirtualServiceVO = new FinalVirtualServiceVO();
        String strLanguage=null;
        try {
            strLanguage=getRequestHeader(OMDConstants.LANGUAGE);
            String appTimeZone=getRequestHeader("appTime");	
            if(RMDCommonUtility.isNumeric(virtualId)){
            finalVirtualServiceVO.setStrFinalVirtualId(virtualId);
            objFinalVirtualServiceVO = objVirtualServiceIntf.getVirtualDetails(virtualId,strLanguage);
            if (!RMDCommonUtility.checkNull(objFinalVirtualServiceVO)) {
                objVirtualResponseType=new FinalVirtualResponseType();
                BeanUtility.copyBeanProperty(objFinalVirtualServiceVO, objVirtualResponseType);
                if(!RMDCommonUtility.checkNull(objVirtualResponseType)){
                	List<VirtualHistService> virtualHistory = new ArrayList<VirtualHistService>();
                	for(VirtualHistService history : objVirtualResponseType.getVirtualHistory()){
                		history.setStrDateCreated(RMDCommonUtility.getConvertedDate(history
								.getStrDateCreated(),appTimeZone));
                		virtualHistory.add(history);
                	}
                	objVirtualResponseType.setVirtualHistory(virtualHistory);
                }
                
            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        }
        else {
            throw new OMDInValidInputException(
                    BeanUtility
                    .getErrorCode(OMDConstants.INVALID_VIRTUAL_ID),
                    omdResourceMessagesIntf.getMessage(
                            BeanUtility
                            .getErrorCode(OMDConstants.INVALID_VIRTUAL_ID),
                            new String[] {},
                            BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objVirtualResponseType;
    }
    /**
     * This method is used for fetching Virtual details
     * 
     * @param uriParam
     * @return list of FinalVirtualResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETVIRTUALS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VirtualResponseType> getVirtuals(@Context UriInfo uriParam)
    throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<VirtualServiceVO> arlVirtualServiceVO=null;
        List<VirtualResponseType> arlSearchVirtualsResult = null;
        VirtualServiceVO objVirtualServiceVO = new VirtualServiceVO();
        VirtualResponseType objVirtualResponseType=null;
        String strFamily=null;
        String strStatus=null;
        String strVirtualId=null;
        String strVirtualTitle=null;
        String strVirtualLastUpdatedBy=null;
        String strVirtualCreatedBy=null;        
        String strVirtualCreatedDate=null;
        String strVirtualLastUpdatedDate=null;
        String strDefaultLoad=null;
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar createddate = null;
        XMLGregorianCalendar lastUpdateddate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    OMDConstants.DATE_FORMAT);
            queryParams = uriParam.getQueryParameters();
            String[] userInput = {OMDConstants.VIRUTALS_ID,OMDConstants.VIRUTALS_TITLE,OMDConstants.VIRUTALS_STATUS,OMDConstants.VIRUTALS_FAMILY,OMDConstants.VIRUTALS_CREATED_BY,OMDConstants.VIRUTALS_CREATED_DATE,OMDConstants.VIRUTALS_LAST_UPDATED_BY,OMDConstants.VIRUTALS_LAST_UPDATED_DATE};
            int[] paramFlag = {OMDConstants.NUMERIC,OMDConstants.ALPHA_NUMERIC_UNDERSCORE,OMDConstants.ALPHABETS,OMDConstants.AlPHA_NUMERIC,OMDConstants.AlPHA_NUMERIC,OMDConstants.VALID_DATE,OMDConstants.AlPHA_NUMERIC,OMDConstants.VALID_DATE};
            if(AppSecUtil.validateWebServiceInput(queryParams, OMDConstants.DATE_FORMAT, paramFlag,userInput)){
                objVirtualServiceVO
                .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                if (queryParams.containsKey(OMDConstants.VIRUTALS_ID)) {
                    strVirtualId=queryParams.getFirst(OMDConstants.VIRUTALS_ID);
                    if(!RMDCommonUtility.isNullOrEmpty(strVirtualId)){
                        objVirtualServiceVO.setStrObjId(strVirtualId);  
                    }                   
                }
                if (queryParams.containsKey(OMDConstants.VIRUTALS_TITLE)) {
                    strVirtualTitle=queryParams.getFirst(OMDConstants.VIRUTALS_TITLE);
                    if(!RMDCommonUtility.isNullOrEmpty(strVirtualTitle)){
                        objVirtualServiceVO.setStrDescription(strVirtualTitle); 
                    }                   
                }
                if (queryParams.containsKey(OMDConstants.VIRUTALS_STATUS)) {
                    strStatus=queryParams.getFirst(OMDConstants.VIRUTALS_STATUS);
                    if(!RMDCommonUtility.isNullOrEmpty(strStatus)){
                        objVirtualServiceVO.setStatus(strStatus);   
                    }                   
                }               
                if (queryParams.containsKey(OMDConstants.VIRUTALS_FAMILY)) {
                    strFamily=queryParams.getFirst(OMDConstants.VIRUTALS_FAMILY);
                    if(!RMDCommonUtility.isNullOrEmpty(strFamily)){
                        objVirtualServiceVO.setFamily(strFamily);   
                    }                   
                }
                if (queryParams.containsKey(OMDConstants.VIRUTALS_CREATED_BY)) {
                    strVirtualCreatedBy=queryParams.getFirst(OMDConstants.VIRUTALS_CREATED_BY);
                    if(!RMDCommonUtility.isNullOrEmpty(strVirtualCreatedBy)){
                        objVirtualServiceVO.setCreatedBy(strVirtualCreatedBy);  
                    }                   
                }
                if (queryParams.containsKey(OMDConstants.VIRUTALS_LAST_UPDATED_BY)) {
                    strVirtualLastUpdatedBy=queryParams.getFirst(OMDConstants.VIRUTALS_LAST_UPDATED_BY);
                    if(!RMDCommonUtility.isNullOrEmpty(strVirtualLastUpdatedBy)){
                        objVirtualServiceVO.setLastUpdatedBy(strVirtualLastUpdatedBy);  
                    }                   
                }
                if (queryParams.containsKey(OMDConstants.VIRUTALS_CREATED_FROM_DATE)) {
                    strVirtualCreatedDate=queryParams.getFirst(OMDConstants.VIRUTALS_CREATED_FROM_DATE);
                    if(!RMDCommonUtility.isNullOrEmpty(strVirtualCreatedDate)){
                        Date creationDate = dateFormat.parse(strVirtualCreatedDate);
                        objVirtualServiceVO.setCreationFromDate(creationDate);
                    }                   
                }
                if (queryParams.containsKey(OMDConstants.VIRUTALS_CREATED_TO_DATE)) {
                    strVirtualCreatedDate=queryParams.getFirst(OMDConstants.VIRUTALS_CREATED_TO_DATE);
                    if(!RMDCommonUtility.isNullOrEmpty(strVirtualCreatedDate)){
                        Date creationDate = dateFormat.parse(strVirtualCreatedDate);
                        objVirtualServiceVO.setCreationToDate(creationDate);
                    }                   
                }
                if (queryParams.containsKey(OMDConstants.VIRUTALS_LAST_UPDATED_FROM_DATE)) {
                    strVirtualLastUpdatedDate=queryParams.getFirst(OMDConstants.VIRUTALS_LAST_UPDATED_FROM_DATE);
                    if(!RMDCommonUtility.isNullOrEmpty(strVirtualLastUpdatedDate)){
                        Date lastUpdateDate = dateFormat.parse(strVirtualLastUpdatedDate);
                        objVirtualServiceVO.setLastUpdatedFromDate(lastUpdateDate);
                    }                   
                }
                
                if (queryParams.containsKey(OMDConstants.VIRUTALS_LAST_UPDATED_TO_DATE)) {
                    strVirtualLastUpdatedDate=queryParams.getFirst(OMDConstants.VIRUTALS_LAST_UPDATED_TO_DATE);
                    if(!RMDCommonUtility.isNullOrEmpty(strVirtualLastUpdatedDate)){
                        Date lastUpdateDate = dateFormat.parse(strVirtualLastUpdatedDate);
                        objVirtualServiceVO.setLastUpdatedToDate(lastUpdateDate);
                    }                   
                }
                // Added for the Initial load check
                if (queryParams.containsKey(OMDConstants.IS_DEFAULT_LOAD)) {
                    strDefaultLoad=queryParams.getFirst(OMDConstants.IS_DEFAULT_LOAD);
                    if(strDefaultLoad.equalsIgnoreCase(RMDCommonConstants.LETTER_Y)){
                        objVirtualServiceVO.setDefaultLoad(true);
                    }
                    else if(strDefaultLoad.equalsIgnoreCase(RMDCommonConstants.LETTER_N)){
                        objVirtualServiceVO.setDefaultLoad(false);
                    }
                    
                }
                            
                arlVirtualServiceVO = objVirtualServiceIntf.searchVirtuals(objVirtualServiceVO);
                arlSearchVirtualsResult=new ArrayList<VirtualResponseType>();
                if(RMDCommonUtility.isCollectionNotEmpty(arlVirtualServiceVO)){
                    for (VirtualServiceVO objServiceVO : arlVirtualServiceVO) {
                        objVirtualResponseType=new VirtualResponseType();
                        BeanUtility.copyBeanProperty(objServiceVO, objVirtualResponseType);
                        if(!RMDCommonUtility.checkNull(objServiceVO.getLastUpdatedDate())){
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(objServiceVO.getLastUpdatedDate());
                            lastUpdateddate = DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(objGregorianCalendar);
                            objVirtualResponseType.setLastUpdatedDate(lastUpdateddate);
                        }
                        if(!RMDCommonUtility.checkNull(objServiceVO.getCreationDate())){
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(objServiceVO.getCreationDate());
                            createddate = DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(objGregorianCalendar);
                            objVirtualResponseType.setCreationDate(createddate);
                        }
                        arlSearchVirtualsResult.add(objVirtualResponseType);    
                    }
                                    
                }else {
                    throw new OMDNoResultFoundException(
                            BeanUtility
                            .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility
                                    .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                    new String[] {},
                                    BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

            }
            
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return arlSearchVirtualsResult;
    }

    /**
     * This method is used to request Jdpad activity
     * 
     * @param objManualRunRequestType
     * @return void
     * @throws
     */
    @POST
    @Path(OMDConstants.REQUEST_MANUALRUN)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void requestManualRun(ManualRunRequestType objManualRunRequestType)
    throws RMDServiceException {
        ManualRunServiceVO objManualRunServiceVO;
        int seqId;
        String strLanguage = null;
        String strUserLanguage = null;
        String strUserID = null;
        try {
            // Start getting the header values
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            strUserID = getRequestHeader(OMDConstants.USERID);
            // End getting the header values
            objManualRunServiceVO = new ManualRunServiceVO();
            BeanUtility.copyBeanProperty(objManualRunRequestType,
                    objManualRunServiceVO);
            objManualRunServiceVO.setDtStart(objManualRunRequestType
                    .getFromDate().toGregorianCalendar().getTime());
            objManualRunServiceVO.setDtEnd(objManualRunRequestType.getToDate()
                    .toGregorianCalendar().getTime());
            objManualRunServiceVO.setStrLanguage(strLanguage);
            objManualRunServiceVO.setStrUserLanguage(strUserLanguage);
            objManualRunServiceVO.setStrUserName(strUserID);
            seqId = objManualRunServiceIntf.runJdpad(objManualRunServiceVO);
            LOG.debug("New Tracking Record is ::" + seqId);
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This is the method used for fetching the Rule Type values through jquery
     * call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GETRULETYPES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getRuleTypes() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getRuleTypes Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.OMD_RULE_TYPE, strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GETRULE_STATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getStatus() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getStatus Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);

            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.OMD_RULE_STATUS, strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param
     * @return List<SystemLookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GETSUBSYSTEM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSubSystem() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getSubSystem Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.OMD_SUBSYSTEM, strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        }  catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param
     * @return List<SystemLookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GETVERSION)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getVersion() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getVersion Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.RULE_VERSION, strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param
     * @return List<SystemLookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_FAMILIES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getFamilies() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getFamily Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {

            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);

            arlLookupDetails = ruleCommonServiceInf.getFamily(strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param
     * @return List<SystemLookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GETFAULTCODE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getFaultCode(@Context UriInfo uriParam)
    throws RMDServiceException {

        LOG.debug("Inside Rule resource in getFaultCode Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<String> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strFaultCode = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = {OMDConstants.ALPHA_NUMERIC_HYPEN};
            if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag,OMDConstants.FAULT_CODE)){
                if (queryParams.containsKey(OMDConstants.FAULT_CODE)) {
                    strFaultCode = queryParams.getFirst(OMDConstants.FAULT_CODE);
                }
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                arlLookupDetails = ruleCommonServiceInf.getFaultCode(strFaultCode,
                        strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (Iterator<String> iterator = arlLookupDetails.iterator(); iterator
                    .hasNext();) {
                        String strFaultcode = (String) iterator.next();
                        objLookupResponseType = new LookupResponseType();
                        objLookupResponseType.setLookupID(strFaultcode);
                        objLookupResponseType.setLookupValue(strFaultcode);
                        arlLookupResponseType.add(objLookupResponseType);
                    }
                }
                else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            }else{
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GETRULECREATEDBY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getRuleCreatedBy()
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getRuleCreatedBy Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleCreatedBy(strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GETRULESLASTUPDATESBY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getRuleLastUpdatedBy()
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getRuleLastUpdatedBy Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf
            .getRuleLastUpdatedBy(strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param
     * @return List<SystemLookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GETMETRICSDAYS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getMetricsDays(
            @PathParam("metricsDays") final String metricsDays)
            throws RMDServiceException {

        LOG.debug("Inside Rule resource in getMetricsDays Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        String strLookupDetails = null;

        LookupResponseType objLookupResponseType = new LookupResponseType();
        try {
            strLookupDetails = objRuleServiceIntf
            .getRuleLookupValues(metricsDays);
            objLookupResponseType.setLookupID(strLookupDetails);
            objLookupResponseType.setLookupValue(strLookupDetails);
            arlLookupResponseType.add(objLookupResponseType);
        }  catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }


    /**
     * This is the method used for fetching the Services values through jquery
     * call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_SERVICES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getServices() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getServices Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.EOA_SERVICES_LIST_NAME, strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    
    
    
    
    /**
     * @param finalRuleRequestType
     * @param clearingLogicRule 
     * @return
     * Description :  Method validates the user input for saveRule()
     */
    public static boolean validateSaveRule(
            FinalRuleRequestType finalRuleRequestType,
            List<SimpleRule> simpleRules, List<SimpleRuleClause> ruleClauses,
            List<ComplexRule> complexRules, List<RuleHistory> ruleHistories,
            List<RuleDefinition> ruleDefinitions,ClearingLogicRule clearingLogicRule) {
        boolean isValidData = true;
        String strFaultCodes=null;
        try{
            // final rule id
            if(null != finalRuleRequestType.getFinalruleID() && !finalRuleRequestType.getFinalruleID().isEmpty()){
                if(! AppSecUtil.checkIntNumber(finalRuleRequestType.getFinalruleID())){
                    return false;
                }
            }
            // original id
            if(null != finalRuleRequestType.getOriginalID() && !finalRuleRequestType.getOriginalID().isEmpty()){
                if(! AppSecUtil.checkIntNumber(finalRuleRequestType.getOriginalID())){
                    return false;
                }
            }
            
            // rule description,
            if(null != finalRuleRequestType.getRuleDescription() && !finalRuleRequestType.getRuleDescription().isEmpty()){
                if(! AppSecUtil.checkEncode(finalRuleRequestType.getRuleDescription())){
                    return false;
                }
            }
            
            // rule type
            if(null != finalRuleRequestType.getRuleType() && !finalRuleRequestType.getRuleType().isEmpty()){
                if(! AppSecUtil.checkAlphabets(finalRuleRequestType.getRuleType())){
                    return false;
                }
            }
            
            // look back
            if(null != finalRuleRequestType.getLookback() && !finalRuleRequestType.getLookback().isEmpty()){
                if(! AppSecUtil.checkIntNumber(finalRuleRequestType.getLookback())){
                    return false;
                }
            }
            
            // family, 
            if(null != finalRuleRequestType.getFamily() && !finalRuleRequestType.getFamily().isEmpty()){
                if(! AppSecUtil.checkAlphaNumericHypen(finalRuleRequestType.getFamily())){
                    return false;
                }
            }
            
            // rule title,
            if(null != finalRuleRequestType.getRuleTitle() && !finalRuleRequestType.getRuleTitle().isEmpty()){
                if(! AppSecUtil.checkEncode(finalRuleRequestType.getRuleTitle())){
                    return false;
                }
            }
            
            // username
            if(null != finalRuleRequestType.getUserName() && !finalRuleRequestType.getUserName().isEmpty()){
                if(! AppSecUtil.checkAlphaUnderScore(finalRuleRequestType.getUserName())){
                    return false;
                }
            }
            // language
            if(null != finalRuleRequestType.getLanguage() && !finalRuleRequestType.getLanguage().isEmpty()){
                if(! AppSecUtil.checkAlphabets(finalRuleRequestType.getLanguage())){
                    return false;
                }
            }
            // user language
            if(null != finalRuleRequestType.getUserlanguage() && !finalRuleRequestType.getUserlanguage().isEmpty()){
                if(! AppSecUtil.checkAlphabets(finalRuleRequestType.getUserlanguage())){
                    return false;
                }
            }
            
            // history reason,
            if(null != finalRuleRequestType.getHistoryReason() && !finalRuleRequestType.getHistoryReason().isEmpty()){
                if(! AppSecUtil.checkEncode(finalRuleRequestType.getHistoryReason())){
                    return false;
                }
            }
            
            // top level rule id
            if(null != finalRuleRequestType.getTopLevelRuleID() && !finalRuleRequestType.getTopLevelRuleID().isEmpty()){
                if(! AppSecUtil.checkIntNumber(finalRuleRequestType.getTopLevelRuleID())){
                    return false;
                }
            }
            
            // subs system
            if(null != finalRuleRequestType.getSubSystem() && !finalRuleRequestType.getSubSystem().isEmpty()){
                if(! AppSecUtil.checkAlphaAmpersand(finalRuleRequestType.getSubSystem())){
                    return false;
                }
            }
            
            // pixels
            if(null != finalRuleRequestType.getPixels() && !finalRuleRequestType.getPixels().isEmpty()) {
                String pixels[] = finalRuleRequestType.getPixels().split(",");
                int pixelSize = pixels.length;
                for (int i = 0; i < pixelSize; i++) {
                    if (!AppSecUtil.checkIntNumber(pixels[i])) {
                        return false;
                    }
                }
            }
            
            // simple rule
            if(null != simpleRules && simpleRules.size()>0 ){
                // iterate simple rules and validate the data type
                Iterator<SimpleRule> simpleRuleList = simpleRules.iterator();
                
                while(simpleRuleList.hasNext()){
                    SimpleRule objSimpleRule = (SimpleRule)simpleRuleList.next();
                    //name
                    if(null != objSimpleRule.getName() && !objSimpleRule.getName().isEmpty()){
                        if(! AppSecUtil.checkAlphaNumeric(objSimpleRule.getName())){
                            return false;
                        }
                    }
                    // fault code
                    /*if(null != objSimpleRule.getFaultCode() && !objSimpleRule.getFaultCode().isEmpty()){
                        if(! AppSecUtil.checkAlphaNumericHypen(objSimpleRule.getFaultCode())){
                            return false;
                        }
                    }*/
                    strFaultCodes=objSimpleRule.getFaultCode();
                    if(!RMDCommonUtility.isNullOrEmpty(strFaultCodes)){
                        if(strFaultCodes.contains(RMDCommonConstants.COMMMA_SEPARATOR))
                        {
                            String strFaultCodesArray[]=strFaultCodes.split(RMDCommonConstants.COMMMA_SEPARATOR);
                            for (int i = 0; i < strFaultCodesArray.length; i++) {
                                if(! AppSecUtil.checkAlphaNumericHypen(strFaultCodesArray[i])){
                                    return false;
                                }   
                            }                           
                        }
                        else{
                            if(! AppSecUtil.checkAlphaNumericHypen(strFaultCodes)){
                                return false;
                            }   
                        }                       
                    }
                    
                    // sub id
                    /*  if(null != objSimpleRule.getSubID() && !objSimpleRule.getSubID().isEmpty()){
                        if(! AppSecUtil.checkAlphaNumeric(objSimpleRule.getSubID())){
                            return false;
                        }
                    }*/
                    
                    
                    String strSubIds=objSimpleRule.getSubID();
                    if(!RMDCommonUtility.isNullOrEmpty(strSubIds)){
                        if(strSubIds.contains(RMDCommonConstants.COMMMA_SEPARATOR))
                        {
                            String strSubIdArray[]=strSubIds.split(RMDCommonConstants.COMMMA_SEPARATOR);
                            for (int i = 0; i < strSubIdArray.length; i++) {
                                if(!strSubIdArray[i].equals("") && ! AppSecUtil.checkAlphaNumericHypen(strSubIdArray[i])){
                                    return false;
                                }   
                            }                           
                        }
                        else{
                            if(! AppSecUtil.checkAlphaNumericHypen(strSubIds)){
                                return false;
                            }   
                        }                       
                    }                   
                    // simple rule id
                    if(null != objSimpleRule.getSimpleruleID() && !objSimpleRule.getSimpleruleID().isEmpty()){
                        if(! AppSecUtil.checkIntNumber(objSimpleRule.getSimpleruleID())){
                            return false;
                        }
                    }
                    
                    // rule on rule flag
                    if(null != objSimpleRule.getRuleOnRuleflag() && !objSimpleRule.getRuleOnRuleflag().isEmpty()){
                        if(! AppSecUtil.checkAlphabets(objSimpleRule.getRuleOnRuleflag())){
                            return false;
                        }
                    }
                    // fault description
                    if(null != objSimpleRule.getFaultDesc() && !objSimpleRule.getFaultDesc().isEmpty()){
                        if(!AppSecUtil.checkAlphaNumericHypen(objSimpleRule.getFaultDesc())){
                            return false;
                        }
                    }
                    // pixels
                    if(null != objSimpleRule.getPixels() && !objSimpleRule.getPixels().isEmpty()){
                        String pixels[] = objSimpleRule.getPixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                    
                    //PixelsFault
                    List<String> lstPixels=objSimpleRule.getPixelsMultifaults();
                    if(RMDCommonUtility.isCollectionNotEmpty(lstPixels)){
                        for (String strPixels: lstPixels) {
                            String pixels[] = strPixels.split(",");
                            int pixelSize = pixels.length;
                            for (int i = 0; i < pixelSize; i++) {
                                if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            
            // simple rule clause, check clause id, col seq id
            if(null != ruleClauses && ruleClauses.size()>0){
                // iterate rule clause list and validate the data type
                Iterator<SimpleRuleClause> ruleClauseList = ruleClauses.iterator();
                while(ruleClauseList.hasNext()){
                    SimpleRuleClause ruleClause = (SimpleRuleClause)ruleClauseList.next();
                    // clause id
                    if(ruleClause.getClauseID() > 0){
                        if(!AppSecUtil.checkIntNumber(String.valueOf(ruleClause.getClauseID()))){
                            return false;
                        }
                    }
                    // col seq id
                    if(ruleClause.getColSeqID() > 0){
                        if(!AppSecUtil.checkIntNumber(String.valueOf(ruleClause.getColSeqID()))){
                            return false;
                        }
                    }
                    
                    // function
                    if(null != ruleClause.getFunction() && !ruleClause.getFunction().isEmpty()){
                        if(!AppSecUtil.checkConfigfilterFunction(ruleClause.getFunction())){
                            return false;
                        }
                    }
                    // value1
                    if(null != ruleClause.getValue1() && !ruleClause.getValue1().isEmpty()){
                        if(!AppSecUtil.checkDoubleHyphen(ruleClause.getValue1())){
                            return false;
                        }
                    }
                    // value2
                    if(null != ruleClause.getValue2() && !ruleClause.getValue2().isEmpty()){
                        if(!AppSecUtil.checkDoubleHyphen(ruleClause.getValue2())){
                            return false;
                        }
                    }
                    // column type
                    if(null != ruleClause.getColumnType() && !ruleClause.getColumnType().isEmpty()){
                        if(!AppSecUtil.checkAlphaNumeric(ruleClause.getColumnType())){
                            return false;
                        }
                    }
                    // anom technique
                    if(null != ruleClause.getAnomTechniqueId() && !ruleClause.getAnomTechniqueId().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(ruleClause.getAnomTechniqueId())){
                            return false;
                        }
                    }
                    // seq val
                    if(null != ruleClause.getSeqVal() && !ruleClause.getSeqVal().isEmpty()){
                        if(!AppSecUtil.checkSeqVal(ruleClause.getSeqVal())){
                            return false;
                        }
                    }
                    // pixels
                    if(null != ruleClause.getPixels() && !ruleClause.getPixels().isEmpty()){
                        String pixels[] = ruleClause.getPixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                }
            }
            // complex rules, config method needs to be included
            if(null !=complexRules && complexRules.size() >0){
                // iterate complex rule and validate user data
                Iterator<ComplexRule> complexRuleList = complexRules.iterator();
                while(complexRuleList.hasNext()){
                    ComplexRule complexRule = (ComplexRule)complexRuleList.next();
                    // complex rule id
                    if(null !=complexRule.getComplexRuleID() && !complexRule.getComplexRuleID().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(complexRule.getComplexRuleID())){
                            return false;
                        }
                    }
                    // frequency
                    if(null !=complexRule.getFrequency() && !complexRule.getFrequency().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(complexRule.getFrequency())){
                            return false;
                        }
                    }
                    // function
                    if(null !=complexRule.getFunction() && !complexRule.getFunction().isEmpty()){
                        if(!AppSecUtil.checkConfigfilterFunction(complexRule.getFunction())){
                            return false;
                        }
                    }
                    // hr
                    if(null !=complexRule.getHr() && !complexRule.getHr().isEmpty()){
                        if(!AppSecUtil.checkNumbersDot(complexRule.getHr())){
                            return false;
                        }
                    }
                    // min
                    if(null !=complexRule.getMin() && !complexRule.getMin().isEmpty()){
                        if(!AppSecUtil.checkNumbersDot(complexRule.getMin())){
                            return false;
                        }
                    }
                    
                    if(null !=complexRule.getPixels() && !complexRule.getPixels().isEmpty()){
                        String pixels[] = complexRule.getPixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                    if(null !=complexRule.getRule1Pixels() && !complexRule.getRule1Pixels().isEmpty()){
                        String pixels[] = complexRule.getRule1Pixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                    if(null !=complexRule.getRule2Pixels() && !complexRule.getRule2Pixels().isEmpty()){
                        String pixels[] = complexRule.getRule2Pixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                    // pt, 
                    if(null !=complexRule.getPt() && !complexRule.getPt().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(complexRule.getPt())){
                            return false;
                        }
                    }
                    // rule 1 
                    if(null !=complexRule.getRule1() && !complexRule.getRule1().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(complexRule.getRule1())){
                            return false;
                        }
                    }
                    
                    // getRule1Type
                    if(null !=complexRule.getRule1Type() && !complexRule.getRule1Type().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(complexRule.getRule1Type())){
                            return false;
                        }
                    }
                    
                    // rule 2
                    if(null !=complexRule.getRule2() && !complexRule.getRule2().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(complexRule.getRule2())){
                            return false;
                        }
                    }
                    
                    // rule 2 type
                    if(null !=complexRule.getRule2Type() && !complexRule.getRule2Type().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(complexRule.getRule2Type())){
                            return false;
                        }
                    }
                    
                            
                    //time flag
                    if(null !=complexRule.getTimeFlag() && !complexRule.getTimeFlag().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(complexRule.getTimeFlag())){
                            return false;
                        }
                    }
                    
                    //time window
                    if(null !=complexRule.getTimewindow() && !complexRule.getTimewindow().isEmpty()){
						if(!AppSecUtil.checkNumbersDotWithoutMinus(complexRule.getTimewindow())){
                            return false;
                        }
                    }
                
                    //subtime
                    if(null !=complexRule.getSubtime() && !complexRule.getSubtime().trim().isEmpty()){
                        if(!AppSecUtil.checkNumbersDot(complexRule.getSubtime())){
                            return false;
                        }
                    }

                    //subgoal
                    if(null != complexRule.getSubgoal() && !complexRule.getSubgoal().trim().isEmpty()) {
                        if(!AppSecUtil.checkIntNumber(complexRule.getSubgoal())){
                            return false;
                        }
                    }

                    if((null == complexRule.getSubtime() || complexRule.getSubtime().isEmpty()) &&
                            (!(null == complexRule.getSubgoal() || complexRule.getSubgoal().isEmpty()))) {
                        return false;
                    }
                    
                    if((null == complexRule.getSubgoal() || complexRule.getSubgoal().isEmpty()) &&
                            (!(null == complexRule.getSubtime() || complexRule.getSubtime().isEmpty()))) {
                        return false;
                    }
                    
                    
                    
                    if(null !=complexRule.getPt() && !complexRule.getPt().isEmpty()){
                        if("P".equals(complexRule.getPt())) {
                            complexRule.setSubtime("-1");
                            complexRule.setSubgoal("-1");
                        }
                    }

                    
                    if(null ==complexRule.getSubtime() || complexRule.getSubtime().trim().isEmpty()){
                        complexRule.setSubtime("-1");
                    }


                    if(null == complexRule.getSubgoal() || complexRule.getSubgoal().trim().isEmpty()) {
                        complexRule.setSubgoal("-1");
                    }

                    
                }
            }
            if(!validateClearingLogicRule(clearingLogicRule)) {
                return false;
            }
            // rule history, 
            if(null != ruleHistories && ruleHistories.size()>0){
                // iterate rule history and validate user input
                Iterator<RuleHistory> ruleHistoryList = ruleHistories.iterator();
                while(ruleHistoryList.hasNext()){
                    RuleHistory ruleHistory = (RuleHistory)ruleHistoryList.next();
                    // created by
                    if(null != ruleHistory.getCreatedBY() && !ruleHistory.getCreatedBY().isEmpty()){                        
                        if(!AppSecUtil.checkAlphaNumericUnderscore(ruleHistory.getCreatedBY())){
                        //if(!AppSecUtil.checkAlphaUnderScore(ruleHistory.getCreatedBY())){
                            return false;
                        }
                    }
                    
                    // original id
                    if(null != ruleHistory.getOriginalID() && !ruleHistory.getOriginalID().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(ruleHistory.getOriginalID())){
                            return false;
                        }
                    }
                    
                    // revision history 
                    if(null != ruleHistory.getRevisionHistory() && !ruleHistory.getRevisionHistory().isEmpty()){
                        if(!AppSecUtil.checkEncode(ruleHistory.getRevisionHistory())){
                            return false;
                        }
                    }
                    
                    // rule id number
                    if(null != ruleHistory.getRuleID() && !ruleHistory.getRuleID().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(ruleHistory.getRuleID())){
                            return false;
                        }
                    }
                    
                    // status alpha
                    if(null != ruleHistory.getStatus() && !ruleHistory.getStatus().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(ruleHistory.getStatus())){
                            return false;
                        }
                    }
                    
                    // version number , number
                    if(null != ruleHistory.getVersionNumber() && !ruleHistory.getVersionNumber().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(ruleHistory.getVersionNumber())){
                            return false;
                        }
                    }
                }
            }
            // rule definition
            if(null != ruleDefinitions && ruleDefinitions.size()>0){
                //iterate rule definition list and validates user input
                Iterator<RuleDefinition> ruleDefinitionList =  ruleDefinitions.iterator();
                while(ruleDefinitionList.hasNext()){
                    RuleDefinition  definition = (RuleDefinition)ruleDefinitionList.next();
                    //final rule id data type, number
                    if(null != definition.getFinalRuleID() && !definition.getFinalRuleID().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(definition.getFinalRuleID())){
                            return false;
                        }
                    }
                    // include flag, data type, alpha
                    if(null != definition.getIncludeFlag() && !definition.getIncludeFlag().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(definition.getIncludeFlag())){
                            return false;
                        }
                    }
                    // message, data type,
                    if(null != definition.getMessage() && !definition.getMessage().isEmpty()){
                        if(!AppSecUtil.checkDoubleHyphen(definition.getMessage())){
                            return false;
                        }
                    }   
                    // recommendation, checked, save as rx title
                    if(null != definition.getRecommendation() && !definition.getRecommendation().isEmpty()){
                        if(!AppSecUtil.checkDoubleHyphen(definition.getRecommendation())){
                            return false;
                        }
                    }
                    // rule def id, date type, numer
                    if(null != definition.getRuleDefID() && !definition.getRuleDefID().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(definition.getRuleDefID())){
                            return false;
                        }
                    }   
                    
                    
                    // rule type id, date type alpha
                    if(null != definition.getRuleType() && !definition.getRuleType().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(definition.getRuleType())){
                            return false;
                        }
                    }   
                        
                    if(definition.getRuleType().equalsIgnoreCase(OMDConstants.HEALTH_RULE_TYPE)){
                        if(null != definition.getPerfHealthCheckID() && !definition.getPerfHealthCheckID().isEmpty()){                          
                            if(!AppSecUtil.checkIntNumber((definition.getPerfHealthCheckID()))){
                                return false;
                            }
                        }
                            
                        if(null != definition.getHealthFactor() && !definition.getHealthFactor().isEmpty()){                                
                                if(!AppSecUtil.checkIntNumber(definition.getHealthFactor())){
                                    return false;
                                }
                        }
                            
                    }
                
                    // service type, date type, alpha
                    if(null != definition.getServiceType() && !definition.getServiceType().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(definition.getServiceType())){
                            return false;
                        }
                    }
                    
                    List<RuleDefinitionCustomer> customers = definition.getRuleDefinitionCustomer();
                    
                    // customer
                    if(null != customers && customers.size() >0){
                        Iterator<RuleDefinitionCustomer> customerList = customers.iterator();
                        while(customerList.hasNext()){
                            
                            RuleDefinitionCustomer definitionCustomer = (RuleDefinitionCustomer)customerList.next();
                            // customer
                            if(null != definitionCustomer.getCustomer() && !definitionCustomer.getCustomer().isEmpty()){
                                if(!AppSecUtil.checkAlphaNumeric(definitionCustomer.getCustomer())){
                                    return false;
                                }
                            }
                            //customer id
                            if(null != definitionCustomer.getCustomerID()&& !definitionCustomer.getCustomerID().isEmpty()){
                                if(!AppSecUtil.checkIntNumber(definitionCustomer.getCustomerID())){
                                    return false;
                                }
                            }
                            //exclude
                            if(null != definitionCustomer.getExclude()&& !definitionCustomer.getExclude().isEmpty()){
                                if(!AppSecUtil.checkAlphabets(definitionCustomer.getExclude())){
                                    return false;
                                }
                            }
                            
                            //iterating fleet
                            List<Fleet> definitionFleets = definitionCustomer.getRuleDefFleet();
                            // fleet
                            if(null != definitionFleets && definitionFleets.size() >0){
                                Iterator<Fleet> fleetList = definitionFleets.iterator();
                                while(fleetList.hasNext()){
                                    Fleet definitionFleet = (Fleet)fleetList.next();
                                    //fleet
                                    if(null != definitionFleet.getFleet()&& !definitionFleet.getFleet().isEmpty()){
                                        if(!AppSecUtil.checkAlphaNumeralsUnderscore(definitionFleet.getFleet())){
                                            return false;
                                        }
                                    }
                                    if(null != definitionFleet.getFleetID()&& !definitionFleet.getFleetID().isEmpty()){
                                        if(!AppSecUtil.checkIntNumber(definitionFleet.getFleetID())){
                                            return false;
                                        }
                                    }
                                    
                                }
                            }
                        }
                    }
                    
                    List<RuleDefinitionModel> definitionModels = definition.getRuleDefinitionModelList();
                    
                    // model, 
                    if(null != definitionModels && definitionModels.size() >0){
                        Iterator<RuleDefinitionModel> modelList = definitionModels.iterator();
                        while(modelList.hasNext()){
                            RuleDefinitionModel definitionModel = (RuleDefinitionModel)modelList.next();
                            //model id
                            if(null != definitionModel.getModelID()&& !definitionModel.getModelID().isEmpty()){
                                if(!AppSecUtil.checkIntNumber(definitionModel.getModelID())){
                                    return false;
                                }
                            }
                            //model name,
                            if(null != definitionModel.getModelName()&& !definitionModel.getModelName().isEmpty()){
                                if(!AppSecUtil.validateColumnName(definitionModel.getModelName())){
                                    return false;
                                }
                            }
                        }
                    }
                    
                    
                    
                    List<RuleDefinitionConfig> configs = definition.getRuleDefinitionConfig();
                    
                    // config 
                    if(null !=configs && configs.size() >0){
                        Iterator<RuleDefinitionConfig> configList = configs.iterator();
                        while(configList.hasNext()){
                            RuleDefinitionConfig definitionConfig = (RuleDefinitionConfig)configList.next();
                            //configuration
                            if(null != definitionConfig.getConfiguration()&& !definitionConfig.getConfiguration().isEmpty()){
                                if(!AppSecUtil.checkAlphaBrackets(definitionConfig.getConfiguration())){
                                    return false;
                                }
                            }
                            
                            //config id
                            if(null != definitionConfig.getConfigurationID()&& !definitionConfig.getConfigurationID().isEmpty()){
                                if(!AppSecUtil.checkIntNumber(definitionConfig.getConfigurationID())){
                                    return false;
                                }
                            }
                            // config name
                            if(null != definitionConfig.getConfigurationName()&& !definitionConfig.getConfigurationName().isEmpty()){
                                if(!AppSecUtil.checkAlphaNumeralsUnderscore(definitionConfig.getConfigurationName())){
                                    return false;
                                }
                            }
                            // value 1
                            if(null != definitionConfig.getValue1()&& !definitionConfig.getValue1().isEmpty()){
                            
                                if((!AppSecUtil.checkDoubleHyphen(definitionConfig.getValue1()))){
                                    return false;
                                }
                            }
                            // value 2
                            if(null != definitionConfig.getValue2()&& !definitionConfig.getValue2().isEmpty()){
                                if((!AppSecUtil.checkDoubleHyphen(definitionConfig.getValue2()))){
                                    return false;
                                }
                            }
                        }
                }
            }
            
     }
        }catch(Exception exception){
            LOG.error("Error occured in validate save rule" + exception.getMessage());
        }
        return isValidData;
    }
    private static boolean validateClearingLogicRule(
            ClearingLogicRule clearingLogicRule) {
        boolean isValidData = true;
        String strFaultCodes=null;
        try{
        // clearing logic rule
            List<SimpleRule> simpleRules = clearingLogicRule.getSimpleRule();
            // simple rule
            if(null != simpleRules && simpleRules.size()>0 ){
                // iterate simple rules and validate the data type
                Iterator<SimpleRule> simpleRuleList = simpleRules.iterator();
                
                while(simpleRuleList.hasNext()){
                    SimpleRule objSimpleRule = (SimpleRule)simpleRuleList.next();
                    //name
                    if(null != objSimpleRule.getName() && !objSimpleRule.getName().isEmpty()){
                        if(! AppSecUtil.checkAlphaNumeric(objSimpleRule.getName())){
                            return false;
                        }
                    }
                    // fault code
                    /*if(null != objSimpleRule.getFaultCode() && !objSimpleRule.getFaultCode().isEmpty()){
                        if(! AppSecUtil.checkAlphaNumericHypen(objSimpleRule.getFaultCode())){
                            return false;
                        }
                    }*/
                    strFaultCodes=objSimpleRule.getFaultCode();
                    if(!RMDCommonUtility.isNullOrEmpty(strFaultCodes)){
                        if(strFaultCodes.contains(RMDCommonConstants.COMMMA_SEPARATOR))
                        {
                            String strFaultCodesArray[]=strFaultCodes.split(RMDCommonConstants.COMMMA_SEPARATOR);
                            for (int i = 0; i < strFaultCodesArray.length; i++) {
                                if(! AppSecUtil.checkAlphaNumericHypen(strFaultCodesArray[i])){
                                    return false;
                                }   
                            }                           
                        }
                        else{
                            if(! AppSecUtil.checkAlphaNumericHypen(strFaultCodes)){
                                return false;
                            }   
                        }                       
                    }
                    
                    // sub id
                    /*  if(null != objSimpleRule.getSubID() && !objSimpleRule.getSubID().isEmpty()){
                        if(! AppSecUtil.checkAlphaNumeric(objSimpleRule.getSubID())){
                            return false;
                        }
                    }*/
                    
                    
                    String strSubIds=objSimpleRule.getSubID();
                    if(!RMDCommonUtility.isNullOrEmpty(strSubIds)){
                        if(strSubIds.contains(RMDCommonConstants.COMMMA_SEPARATOR))
                        {
                            String strSubIdArray[]=strSubIds.split(RMDCommonConstants.COMMMA_SEPARATOR);
                            for (int i = 0; i < strSubIdArray.length; i++) {
                                if(!strSubIdArray[i].equals("") && ! AppSecUtil.checkAlphaNumericHypen(strSubIdArray[i])){
                                    return false;
                                }   
                            }                           
                        }
                        else{
                            if(! AppSecUtil.checkAlphaNumericHypen(strSubIds)){
                                return false;
                            }   
                        }                       
                    }                   
                    // simple rule id
                    if(null != objSimpleRule.getSimpleruleID() && !objSimpleRule.getSimpleruleID().isEmpty()){
                        if(! AppSecUtil.checkIntNumber(objSimpleRule.getSimpleruleID())){
                            return false;
                        }
                    }
                    
                    // rule on rule flag
                    if(null != objSimpleRule.getRuleOnRuleflag() && !objSimpleRule.getRuleOnRuleflag().isEmpty()){
                        if(! AppSecUtil.checkAlphabets(objSimpleRule.getRuleOnRuleflag())){
                            return false;
                        }
                    }
                    // fault description
                    if(null != objSimpleRule.getFaultDesc() && !objSimpleRule.getFaultDesc().isEmpty()){
                        if(!AppSecUtil.checkAlphaNumericHypen(objSimpleRule.getFaultDesc())){
                            return false;
                        }
                    }
                    // pixels
                    if(null != objSimpleRule.getPixels() && !objSimpleRule.getPixels().isEmpty()){
                        String pixels[] = objSimpleRule.getPixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                    
                    //PixelsFault
                    List<String> lstPixels=objSimpleRule.getPixelsMultifaults();
                    if(RMDCommonUtility.isCollectionNotEmpty(lstPixels)){
                        for (String strPixels: lstPixels) {
                            String pixels[] = strPixels.split(",");
                            int pixelSize = pixels.length;
                            for (int i = 0; i < pixelSize; i++) {
                                if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }

            // simple rule clause, check clause id, col seq id
            List<SimpleRuleClause> ruleClauses = null;
            if(null != simpleRules && simpleRules.size() >0){
                ruleClauses = new ArrayList<SimpleRuleClause>();
                Iterator<SimpleRule> iterator = simpleRules.iterator();
                while(iterator.hasNext()){
                    ruleClauses= iterator.next().getSimpleRuleClause();
                }
            }
            if(null != ruleClauses && ruleClauses.size()>0){
                // iterate rule clause list and validate the data type
                Iterator<SimpleRuleClause> ruleClauseList = ruleClauses.iterator();
                while(ruleClauseList.hasNext()){
                    SimpleRuleClause ruleClause = (SimpleRuleClause)ruleClauseList.next();
                    // clause id
                    if(ruleClause.getClauseID() > 0){
                        if(!AppSecUtil.checkIntNumber(String.valueOf(ruleClause.getClauseID()))){
                            return false;
                        }
                    }
                    // col seq id
                    if(ruleClause.getColSeqID() > 0){
                        if(!AppSecUtil.checkIntNumber(String.valueOf(ruleClause.getColSeqID()))){
                            return false;
                        }
                    }
                    
                    // function
                    if(null != ruleClause.getFunction() && !ruleClause.getFunction().isEmpty()){
                        if(!AppSecUtil.checkConfigfilterFunction(ruleClause.getFunction())){
                            return false;
                        }
                    }
                    // value1
                    if(null != ruleClause.getValue1() && !ruleClause.getValue1().isEmpty()){
                        if(!AppSecUtil.checkDoubleHyphen(ruleClause.getValue1())){
                            return false;
                        }
                    }
                    // value2
                    if(null != ruleClause.getValue2() && !ruleClause.getValue2().isEmpty()){
                        if(!AppSecUtil.checkDoubleHyphen(ruleClause.getValue2())){
                            return false;
                        }
                    }
                    // column type
                    if(null != ruleClause.getColumnType() && !ruleClause.getColumnType().isEmpty()){
                        if(!AppSecUtil.checkAlphaNumeric(ruleClause.getColumnType())){
                            return false;
                        }
                    }
                    // anom technique
                    if(null != ruleClause.getAnomTechniqueId() && !ruleClause.getAnomTechniqueId().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(ruleClause.getAnomTechniqueId())){
                            return false;
                        }
                    }
                    // seq val
                    if(null != ruleClause.getSeqVal() && !ruleClause.getSeqVal().isEmpty()){
                        if(!AppSecUtil.checkSeqVal(ruleClause.getSeqVal())){
                            return false;
                        }
                    }
                    // pixels
                    if(null != ruleClause.getPixels() && !ruleClause.getPixels().isEmpty()){
                        String pixels[] = ruleClause.getPixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                }
            }
            List<ComplexRule> complexRules = clearingLogicRule.getComplexRule();
            // complex rules, config method needs to be included
            if(null !=complexRules && complexRules.size() >0){
                // iterate complex rule and validate user data
                Iterator<ComplexRule> complexRuleList = complexRules.iterator();
                while(complexRuleList.hasNext()){
                    ComplexRule complexRule = (ComplexRule)complexRuleList.next();
                    // complex rule id
                    if(null !=complexRule.getComplexRuleID() && !complexRule.getComplexRuleID().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(complexRule.getComplexRuleID())){
                            return false;
                        }
                    }
                    // frequency
                    if(null !=complexRule.getFrequency() && !complexRule.getFrequency().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(complexRule.getFrequency())){
                            return false;
                        }
                    }
                    // function
                    if(null !=complexRule.getFunction() && !complexRule.getFunction().isEmpty()){
                        if(!AppSecUtil.checkConfigfilterFunction(complexRule.getFunction())){
                            return false;
                        }
                    }
                    // hr
                    if(null !=complexRule.getHr() && !complexRule.getHr().isEmpty()){
                        if(!AppSecUtil.checkNumbersDot(complexRule.getHr())){
                            return false;
                        }
                    }
                    // min
                    if(null !=complexRule.getMin() && !complexRule.getMin().isEmpty()){
                        if(!AppSecUtil.checkNumbersDot(complexRule.getMin())){
                            return false;
                        }
                    }
                    
                    if(null !=complexRule.getPixels() && !complexRule.getPixels().isEmpty()){
                        String pixels[] = complexRule.getPixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                    if(null !=complexRule.getRule1Pixels() && !complexRule.getRule1Pixels().isEmpty()){
                        String pixels[] = complexRule.getRule1Pixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                    if(null !=complexRule.getRule2Pixels() && !complexRule.getRule2Pixels().isEmpty()){
                        String pixels[] = complexRule.getRule2Pixels().split(",");
                        int pixelSize = pixels.length;
                        for (int i = 0; i < pixelSize; i++) {
                            if (!AppSecUtil.checkIntNumber(pixels[i])) {
                                return false;
                            }
                        }
                    }
                    // pt, 
                    if(null !=complexRule.getPt() && !complexRule.getPt().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(complexRule.getPt())){
                            return false;
                        }
                    }
                    // rule 1 
                    if(null !=complexRule.getRule1() && !complexRule.getRule1().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(complexRule.getRule1())){
                            return false;
                        }
                    }
                    
                    // getRule1Type
                    if(null !=complexRule.getRule1Type() && !complexRule.getRule1Type().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(complexRule.getRule1Type())){
                            return false;
                        }
                    }
                    
                    // rule 2
                    if(null !=complexRule.getRule2() && !complexRule.getRule2().isEmpty()){
                        if(!AppSecUtil.checkIntNumber(complexRule.getRule2())){
                            return false;
                        }
                    }
                    
                    // rule 2 type
                    if(null !=complexRule.getRule2Type() && !complexRule.getRule2Type().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(complexRule.getRule2Type())){
                            return false;
                        }
                    }
                    
                            
                    //time flag
                    if(null !=complexRule.getTimeFlag() && !complexRule.getTimeFlag().isEmpty()){
                        if(!AppSecUtil.checkAlphabets(complexRule.getTimeFlag())){
                            return false;
                        }
                    }
                    
                    //time window
                    if(null !=complexRule.getTimewindow() && !complexRule.getTimewindow().isEmpty()){
						if(!AppSecUtil.checkNumbersDotWithoutMinus(complexRule.getTimewindow())){
                            return false;
                        }
                    }
                    
                    
                    //subtime
                    if(null !=complexRule.getSubtime() && !complexRule.getSubtime().isEmpty()){
                        if(!AppSecUtil.checkNumbersDot(complexRule.getSubtime())){
                            return false;
                        }
                    }

                    //subgoal
                    if(null != complexRule.getSubgoal() && !complexRule.getSubgoal().isEmpty()) {
                        if(!AppSecUtil.checkIntNumber(complexRule.getSubgoal())){
                            return false;
                        }
                    }
                    
                    if((null == complexRule.getSubtime() || complexRule.getSubtime().isEmpty()) &&
                            (!(null == complexRule.getSubgoal() || complexRule.getSubgoal().isEmpty()))) {
                        return false;
                    }
                    
                    if((null == complexRule.getSubgoal() || complexRule.getSubgoal().isEmpty()) &&
                            (!(null == complexRule.getSubtime() || complexRule.getSubtime().isEmpty()))) {
                        return false;
                    }
                    
                     
                    if(null !=complexRule.getPt() && !complexRule.getPt().isEmpty()){
                        if("P".equals(complexRule.getPt())) {
                            complexRule.setSubtime("-1");
                            complexRule.setSubgoal("-1");
                        }
                    }
                    
                    if(null ==complexRule.getSubtime() || complexRule.getSubtime().trim().isEmpty()){
                        complexRule.setSubtime("-1");
                    }


                    if(null == complexRule.getSubgoal() || complexRule.getSubgoal().trim().isEmpty()) {
                        complexRule.setSubgoal("-1");
                    }

                }
            }
        }catch(Exception exception){
            LOG.error("Error occured in validate save rule" + exception.getMessage());
        }
        return isValidData; 
    }

    /**
     * This is the method used for fetching the ANOM Technique values from Lookup table
     * call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_ANOM_TECHNIQUE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getAnomTechniques() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getRuleTypes Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.ANOM_TECHNIQUE_LISTNAME, strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    /**
     * This is the method used for fetching the EDP values from Lookup table
     * call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_EDP_TECHNIQUE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getEDPTechniques() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getRuleTypes Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.EDP_TECHNIQUE_LISTNAME, strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    
    /**
     * This is the method used for fetching the SDP functions from Lookup table
     * call 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_SDP_FUNCTIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSDPFunctions() throws RMDServiceException {

        LOG.debug("Inside Rule resource in getSDPFunctions Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.SDP_FUNCTIONS_LISTNAME, strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            } else {
                throw new OMDNoResultFoundException(
                        OMDConstants.NORECORDFOUNDEXCEPTION);
            }

        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }

        return arlLookupResponseType;
    }
    /**
     * This is the method used for fetching the Virtual Created By Values through
     * jquery call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_VIRUTALS_CREATEDBY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VirtualResponseType> getVirtualCreatedBy()
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getVirtualCreatedBy Method");
        List<VirtualResponseType> arlVirtualResponseType = new ArrayList<VirtualResponseType>();
        List<VirtualServiceVO> arlVirtualServiceVO = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        VirtualResponseType objVirtualResponseType=null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlVirtualServiceVO = objVirtualServiceIntf.getVirtualCreatedBy(strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlVirtualServiceVO)) {
                for (VirtualServiceVO objVirtualServiceVO : arlVirtualServiceVO) {
                    objVirtualResponseType=new VirtualResponseType();
                    BeanUtility.copyBeanProperty(objVirtualServiceVO,
                            objVirtualResponseType);
                    arlVirtualResponseType.add(objVirtualResponseType);
                }
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlVirtualResponseType;
    }
    
    
    /**
     * This is the method used for fetching the Virtuals Last Updated By Values through
     * jquery call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_VIRUTALS_LASTUPDATEDBY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VirtualResponseType> getVirtualLastUpdatedBy()
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getVirtualLastUpdatedBy Method");
        List<VirtualResponseType> arlVirtualResponseType = new ArrayList<VirtualResponseType>();
        List<VirtualServiceVO> arlVirtualServiceVO = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        VirtualResponseType objVirtualResponseType=null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlVirtualServiceVO = objVirtualServiceIntf.getVirtualLastUpdatedBy(strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlVirtualServiceVO)) {
                for (VirtualServiceVO objVirtualServiceVO : arlVirtualServiceVO) {
                    objVirtualResponseType=new VirtualResponseType();
                    BeanUtility.copyBeanProperty(objVirtualServiceVO,
                            objVirtualResponseType);
                    arlVirtualResponseType.add(objVirtualResponseType);
                }
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlVirtualResponseType;
    }
    
    /**
     * This is the method used for fetching the Virtuals Status Values through
     * jquery call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_VIRUTALS_STATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getVirtualStatus()
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getVirtualStatus Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLookupValues(
                    OMDConstants.VIRTUAL_STATUS, strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    /**
     * This is the method used for fetching the Virtuals Family Dropdown Values through
     * jquery call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_VIRUTALS_FAMILY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getVirtualFamilies()
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getVirtualFamilies Method");
        List<LookupResponseType> arlLookupResponseType = null;
        List<VirtualServiceVO> arlVirtualServiceVO = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType=null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlVirtualServiceVO = objVirtualServiceIntf.getVirtualFamily(strLanguage);
            arlLookupResponseType=new ArrayList<LookupResponseType>();
            if (RMDCommonUtility.isCollectionNotEmpty(arlVirtualServiceVO)) {
                for (VirtualServiceVO objVirtualServiceVO : arlVirtualServiceVO) {
                    objLookupResponseType=new LookupResponseType();
                    /*BeanUtility.copyBeanProperty(objVirtualServiceVO,
                            objLookupResponseType);*/
                    objLookupResponseType.setLookupID(objVirtualServiceVO.getFamily());
                    objLookupResponseType.setLookupValue(objVirtualServiceVO.getFamily());
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    
    /**
     * This is the method used for fetching the Virtual Title Values through
     * jquery call
     * 
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_VIRUTALS_TITLES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VirtualResponseType> getVirtualTitles(@Context UriInfo uriParam)
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getVirtualTitles Method");
        List<VirtualResponseType> arlVirtualResponseType = new ArrayList<VirtualResponseType>();
        List<VirtualServiceVO> arlVirtualServiceVO = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        VirtualResponseType objVirtualResponseType=null;
        MultivaluedMap<String, String> queryParams = null;
        String strVirtualTitle=null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = {OMDConstants.ALPHA_NUMERIC_UNDERSCORE};
            String[] userInput = {OMDConstants.VIRUTALS_TITLE};
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            
            if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, userInput)){                
                if(queryParams.containsKey(OMDConstants.VIRUTALS_TITLE)){
                    strVirtualTitle=queryParams.getFirst(OMDConstants.VIRUTALS_TITLE);  
                }
            arlVirtualServiceVO = objVirtualServiceIntf.getVirtualTitles(strVirtualTitle,strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlVirtualServiceVO)) {
                for (VirtualServiceVO objVirtualServiceVO : arlVirtualServiceVO) {
                    objVirtualResponseType=new VirtualResponseType();
                    BeanUtility.copyBeanProperty(objVirtualServiceVO,
                            objVirtualResponseType);
                    arlVirtualResponseType.add(objVirtualResponseType);
                }
            }
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlVirtualResponseType;
    }
    
    
    
    /**
     * @param finalRuleRequestType
     * @return
     * Description :  Method validates the user input for saveRule()
     */
    public static boolean validateSaveVirtual(
            FinalVirtualRequestType objFinalVirtualRequestType,
            List<VirtualEquation> virtualEquation,
            List<VirtualDefinition> virtualDefinition,
            List<VirtualFilter> virtualFilter) {
        boolean isValid = true;
        try {
            if (!RMDCommonUtility.isNullOrEmpty(objFinalVirtualRequestType
                    .getFinalVirtualId())) {
                if (!AppSecUtil.checkIntNumber(objFinalVirtualRequestType
                        .getFinalVirtualId())) {
                    return false;
                }
            }

            /*if (!RMDCommonUtility.isNullOrEmpty(objFinalVirtualRequestType
                    .getVirtualName())) {
                if (!AppSecUtil.checkEncode(objFinalVirtualRequestType
                        .getVirtualName())) {
                    return false;
                }

            }*/
            /*if (!RMDCommonUtility.isNullOrEmpty(objFinalVirtualRequestType
                    .getVirtualDesc())) {
                if (!AppSecUtil.checkEncode(objFinalVirtualRequestType
                        .getVirtualDesc())) {
                    return false;
                }

            }*/
            if (!RMDCommonUtility.isNullOrEmpty(objFinalVirtualRequestType
                    .getFamily())) {
                if (!AppSecUtil.checkAlphaNumeric(objFinalVirtualRequestType
                        .getFamily())) {
                    return false;
                }

            }

            if (!RMDCommonUtility.isNullOrEmpty(objFinalVirtualRequestType
                    .getVirtualType())) {
                if (!AppSecUtil.checkAlphabets(objFinalVirtualRequestType
                        .getVirtualType())) {
                    return false;
                }

            }

            if (!RMDCommonUtility.isNullOrEmpty(objFinalVirtualRequestType
                    .getActive())) {
                if (!AppSecUtil.checkAlphabets(objFinalVirtualRequestType
                        .getActive())) {
                    return false;
                }

            }

            if (RMDCommonUtility.isCollectionNotEmpty(virtualEquation)) {
                for (VirtualEquation objVirtualEquation : virtualEquation) {
                    if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getEquationId())) {
                        if (!AppSecUtil.checkIntNumber(objVirtualEquation
                                .getEquationId())) {
                            return false;
                        }

                    }

                    /*if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getEquation())) {
                        if (!AppSecUtil.checkEncode(objVirtualEquation
                                .getEquation())) {
                            return false;
                        }

                    }*/
                    /*if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getCustomFileName())) {
                        if (!AppSecUtil.checkEncode(objVirtualEquation
                                .getCustomFileName())) {
                            return false;
                        }

                    }
                    if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getEquationText())) {
                        if (!AppSecUtil.checkAlphaNumeric(objVirtualEquation
                                .getEquationText())) {
                            return false;
                        }

                    }*/
                    if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getLookBackPoints())) {
                        if (!AppSecUtil.checkIntNumber(objVirtualEquation
                                .getLookBackPoints())) {
                            return false;
                        }

                    }
                    if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getActive())) {
                        if (!AppSecUtil.checkAlphabets(objVirtualEquation
                                .getActive())) {
                            return false;
                        }

                    }
                    if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getLookBackTime())) {
                        if (!AppSecUtil.checkIntNumber(objVirtualEquation
                                .getLookBackTime())) {
                            return false;
                        }

                    }
                    if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getVirtualType())) {
                        if (!AppSecUtil.checkAlphabets(objVirtualEquation
                                .getVirtualType())) {
                            return false;
                        }

                    }
                    /*if (!RMDCommonUtility.isNullOrEmpty(objVirtualEquation
                            .getEquationJsonTxt())) {
                        if (!AppSecUtil.checkEncode(objVirtualEquation
                                .getEquationJsonTxt())) {
                            return false;
                        }

                    }*/
                }

            }

            if (RMDCommonUtility.isCollectionNotEmpty(virtualDefinition)) {
                for (VirtualDefinition objVirtualDef : virtualDefinition) {
                    if (!RMDCommonUtility.isNullOrEmpty(objVirtualDef
                            .getMasterVirtualId())) {
                        if (!AppSecUtil.checkIntNumber(objVirtualDef
                                .getMasterVirtualId())) {
                            return false;
                        }

                    }

                    if (!RMDCommonUtility.isNullOrEmpty(objVirtualDef
                            .getVirtualDefID())) {
                        if (!AppSecUtil.checkIntNumber(objVirtualDef
                                .getVirtualDefID())) {
                            return false;
                        }

                    }
                    
                    List<String> lstModel = objVirtualDef.getModel();
                    if (RMDCommonUtility.isCollectionNotEmpty(lstModel)) {
                        for (String modelString : lstModel) {
                            if (!RMDCommonUtility.isNullOrEmpty(modelString)) {
                                if (!AppSecUtil.checkAlphaNumeric(modelString)) {
                                    return false;
                                }

                            }
                        }
                    }

                    List<VirtualDefCustomer> virtualDefCustomer = objVirtualDef
                            .getVirtualDefCustomer();
                    if (RMDCommonUtility
                            .isCollectionNotEmpty(virtualDefCustomer)) {

                        for (VirtualDefCustomer virtualDefCustomer2 : virtualDefCustomer) {
                            if (!RMDCommonUtility
                                    .isNullOrEmpty(virtualDefCustomer2
                                            .getCustomer())) {
                                if (!AppSecUtil
                                        .checkAlphabets(virtualDefCustomer2
                                                .getCustomer())) {
                                    return false;
                                }
                            }
                            if (!RMDCommonUtility
                                    .isNullOrEmpty(virtualDefCustomer2
                                            .getCustomerID())) {
                                if (!AppSecUtil
                                        .checkIntNumber(virtualDefCustomer2
                                                .getCustomerID())) {
                                    return false;
                                }
                            }
                            if (!RMDCommonUtility
                                    .isNullOrEmpty(virtualDefCustomer2
                                            .getIsExclude())) {
                                if (!AppSecUtil
                                        .checkAlphabets(virtualDefCustomer2
                                                .getIsExclude())) {
                                    return false;
                                }
                            }
                            List<VirtualDefFleet> virtualDefFleet = virtualDefCustomer2
                                    .getVirtualDefFleet();
                            if (RMDCommonUtility
                                    .isCollectionNotEmpty(virtualDefFleet)) {
                                for (VirtualDefFleet virtualDefFleet2 : virtualDefFleet) {
                                    if (!RMDCommonUtility
                                            .isNullOrEmpty(virtualDefFleet2
                                                    .getFleet())) {
                                        if (!AppSecUtil
                                                .checkAlphaNumeric(virtualDefFleet2
                                                        .getFleet())) {
                                            return false;
                                        }
                                    }
                                    if (!RMDCommonUtility
                                            .isNullOrEmpty(virtualDefFleet2
                                                    .getIsExclude())) {
                                        if (!AppSecUtil
                                                .checkAlphabets(virtualDefFleet2
                                                        .getIsExclude())) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                    }

                }

            }

            if (RMDCommonUtility.isCollectionNotEmpty(virtualFilter)) {
                for (VirtualFilter virtualFilter2 : virtualFilter) {
                    if (!RMDCommonUtility.isNullOrEmpty(virtualFilter2
                            .getFilterSeqId())) {
                        if (!AppSecUtil.checkIntNumber(virtualFilter2
                                .getFilterSeqId())) {
                            return false;
                        }
                    }
                    if (!RMDCommonUtility.isNullOrEmpty(virtualFilter2
                            .getFunction())) {
                        if (!AppSecUtil
                                .checkConfigfilterFunction(virtualFilter2
                                        .getFunction())) {
                            return false;
                        }
                    }
                    if (!RMDCommonUtility.isNullOrEmpty(virtualFilter2
                            .getParameter())) {
                        if (!AppSecUtil.checkAlphabets(virtualFilter2
                                .getParameter())) {
                            return false;
                        }
                    }
                    if (!RMDCommonUtility.isNullOrEmpty(virtualFilter2
                            .getValue1())) {
                        if (!AppSecUtil.checkIntNumber(virtualFilter2
                                .getValue1())) {
                            return false;
                        }
                    }
                    if (!RMDCommonUtility.isNullOrEmpty(virtualFilter2
                            .getValue2())) {
                        if (!AppSecUtil.checkIntNumber(virtualFilter2
                                .getValue2())) {
                            return false;
                        }
                    }
                }
            }

        } catch (Exception exception) {
            LOG.error("Error occured in validate save virtual"
                    + exception.getMessage());
        }
        return isValid;

    }
    
    /**
     * This is the method used for fetching the performance values  
     * @param
     * @return List<LookupResponseType>
     * @throws
     */
    @GET
    @Path(OMDConstants.GET_PERFORMANCE_CHECK_VALUES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getPerformanceCheckValues() throws RMDServiceException {
        LOG.debug("Inside Rule resource in getperformanceCheckValues() Method");
        List<LookupResponseType> performanceLookupResponseType = null;
        List<ElementVO> performanceCheckList = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            performanceCheckList =  objRuleServiceIntf.getPerformanceCheckValues(strLanguage);                  
            if (BeanUtility.isCollectionNotEmpty(performanceCheckList)) {
                performanceLookupResponseType = new ArrayList<LookupResponseType>();
                for (ElementVO elementVO : performanceCheckList) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    performanceLookupResponseType.add(objLookupResponseType);
                }
            }

        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return performanceLookupResponseType;
    }   
    
    
    private FinalRuleResponseType getRuleDetailsCL(String ruleID,
            UriInfo uriParam) {
        final MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();

        FinalRuleServiceVO objFinalRuleServiceVO = null;
        RuleHistory objRuleHistory = null;
        FinalRuleResponseType objFinalRuleResponseType = null;
        RuleDefinitionServiceVO objRuleDefinitionServiceVO;
        RuleDefinition objRuleDefinition = new RuleDefinition();
        String strRuleID = null;
        String strCloneRule = null;
        String strlanguage = null;
        String cloneRule = null;
        List<RuleDefinitionServiceVO> listRuleDefinitionServiceVO = null;
        RuleDefinition ruleDefinition = null;
        GregorianCalendar objGregorianCalendar = null;
        List<RuleDefCustomerServiceVO> listRuleDefCustomerServiceVO = null;
        RuleDefinitionCustomer objRuleDefinitionCustomer = null;
        XMLGregorianCalendar createdDate = null;
        List<RuleDefModelServiceVO> listRuleDefModelServiceVO = null;
        RuleDefinitionModel objRuleDefinitionModel = null;
        RuleDefinitionConfig innerObjRuleDefinitionConfig = null;
        List<RuleDefConfigServiceVO> innerlistRuleDefConfigServiceVO = null;

        List<RuleHistoryServiceVO> listRuleHistoryServiceVO = null;
        RuleDefinitionConfig objRuleDefinitionConfig = null;
        List<RuleDefConfigServiceVO> listRuleDefConfigServiceVO = null;
        RuleHistoryServiceVO ruleHistoryServiceVO = null;
        RuleDefinitionServiceVO ruleDefinitionServiceVO = null;
        RuleDefCustomerServiceVO ruleDefCustomerServiceVO = null;
        RuleDefModelServiceVO ruleDefModelServiceVO = null;
        RuleDefConfigServiceVO ruleDefConfigServiceVO = null;
        SimpleRule objClearingLogicSimpleRule = null;
        ClearingLogicRuleServiceVO objClearingLogicRuleServiceVO;
        List<SimpleRuleServiceVO> listClearingLogicSimpleRuleServiceVO = null;
        ClearingLogicRule clearingLogicRule = null;
        SimpleRuleClause objClearingLogicSimpleRuleClause = null;
        List<SimpleRuleClauseServiceVO> listClearingLogicSimpleRuleClause = null;
        SimpleRuleServiceVO clearingLogicSimpleRuleServiceVO = null;
        SimpleRuleClauseServiceVO clearingLogicSimpleRuleClauseServiceVO = null;
        ArrayList<ComplexRuleLinkServiceVO> listClearingLogicComplexRuleLinkServiceVO;
        ArrayList<ComplexRuleServiceVO> listClearingLogicComplexRule;
        ComplexRuleServiceVO clearingLogicComplexRuleServiceVO;
        ComplexRule objClearingLogicComplexRule;
        ComplexRuleLinkServiceVO clearingLogicComplexRuleLinkServiceVO;
        ComplexRuleLink objClearingLogicComplexRuleLink;

        try {
            int[] paramFlag = { OMDConstants.ALPHABETS, OMDConstants.NUMERIC };
            queryParams.add(OMDConstants.RULE_ID, ruleID);
            if (AppSecUtil.validateWebServiceInput(queryParams, null,
                    paramFlag, OMDConstants.CLONERULE, OMDConstants.RULE_ID)) {

                if (ruleID != null) {
                    strRuleID = ruleID;
                } else {
                    throw new OMDInValidInputException(OMDConstants.RULEID_NOT_PROVIDED);
                }

                if (queryParams.containsKey(OMDConstants.CLONERULE)) {
                    cloneRule = queryParams.getFirst(OMDConstants.CLONERULE);
                } else {
                    cloneRule = OMDConstants.CLONE;
                }

                if (cloneRule != null) {
                    strCloneRule = cloneRule;
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_SEARCH_CRITERIA);
                }

                String explode_ror = null;
                if (queryParams.containsKey(OMDConstants.EXPLODE_ROR)) {
                    explode_ror = queryParams.getFirst(OMDConstants.EXPLODE_ROR);
                }

				strlanguage = getRequestHeader(OMDConstants.LANGUAGE);
				objFinalRuleServiceVO = objViewRuleServiceIntf.getRuleDetails(
						strRuleID, strCloneRule, strlanguage, explode_ror,null);

                objFinalRuleResponseType = new FinalRuleResponseType();
                if (null != objFinalRuleServiceVO) {
                    // Populate the RULE HISTORY details
                    listRuleHistoryServiceVO = objFinalRuleServiceVO.getArlRuleHistoryVO();
                    if (null != listRuleHistoryServiceVO) {
                        for (final Iterator<RuleHistoryServiceVO> iteratorRuleHistoryServiceVO = listRuleHistoryServiceVO
                                .iterator(); iteratorRuleHistoryServiceVO.hasNext();) {
                            ruleHistoryServiceVO = (RuleHistoryServiceVO) iteratorRuleHistoryServiceVO.next();
                            objRuleHistory = new RuleHistory();
                            if (null != ruleHistoryServiceVO.getStrDateCreated()) {
                                objGregorianCalendar = new GregorianCalendar();
								//objGregorianCalendar.setTime(ruleHistoryServiceVO.getStrDateCreated());
                                createdDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                                        objGregorianCalendar);
								//objRuleHistory.setDateCreated(createdDate);
                            }
                            BeanUtility.copyBeanProperty(ruleHistoryServiceVO,objRuleHistory);
                            objFinalRuleResponseType.getRuleHistory().add(objRuleHistory);
                        }
                    }
                    // Populate the RULE HISTORY details

                    listRuleDefinitionServiceVO = objFinalRuleServiceVO.getArlRuleDefinition();
                    /*
                     * if (listRuleDefinitionServiceVO == null) { throw new
                     * OMDNoResultFoundException
                     * (OMDConstants.NORECORDFOUNDEXCEPTION); }
                     */
                    // Populate the RULE Definition Service details
                    if (null != listRuleDefinitionServiceVO && !listRuleDefinitionServiceVO.isEmpty()) {
                        for (final Iterator<RuleDefinitionServiceVO> iterator = listRuleDefinitionServiceVO
                                .iterator(); iterator.hasNext();) {
                            ruleDefinitionServiceVO = (RuleDefinitionServiceVO) iterator.next();
                            objRuleDefinition = new RuleDefinition();
                            listRuleDefCustomerServiceVO = ruleDefinitionServiceVO.getArlRuleDefCustomer();
                            if (RMDCommonUtility.isCollectionNotEmpty(listRuleDefCustomerServiceVO)) {

                                for (final Iterator<RuleDefCustomerServiceVO> iterator2 = listRuleDefCustomerServiceVO
                                        .iterator(); iterator2.hasNext();) {
                                    ruleDefCustomerServiceVO = (RuleDefCustomerServiceVO) iterator2.next();
                                    objRuleDefinitionCustomer = new RuleDefinitionCustomer();
                                    BeanUtility.copyBeanProperty(ruleDefCustomerServiceVO, objRuleDefinitionCustomer);
                                    if (null != ruleDefCustomerServiceVO.getArlRuleDefFleet()
                                            && !ruleDefCustomerServiceVO.getArlRuleDefFleet().isEmpty()) {
                                        List arlRuleDefFleet = ruleDefCustomerServiceVO.getArlRuleDefFleet();
                                        int arlRuleDefFleetSize = arlRuleDefFleet.size();

                                        for (int i = 0; i < arlRuleDefFleetSize; i++) {
                                            ElementVO elementVO = (ElementVO) arlRuleDefFleet.get(i);
                                            Fleet fleet = new Fleet();
                                            fleet.setFleetID(elementVO.getId());
                                            fleet.setFleet(elementVO.getName());
                                            objRuleDefinitionCustomer.getRuleDefFleet().add(fleet);
                                        }
                                    }
                                    objRuleDefinition.getRuleDefinitionCustomer().add(objRuleDefinitionCustomer);
                                }
                            }
                            listRuleDefModelServiceVO = ruleDefinitionServiceVO.getArlRuleDefModel();
                            if (RMDCommonUtility.isCollectionNotEmpty(listRuleDefModelServiceVO)) {
                                for (final Iterator<RuleDefModelServiceVO> iteratorRuleDefModelServiceVO = listRuleDefModelServiceVO
                                        .iterator(); iteratorRuleDefModelServiceVO.hasNext();) {
                                    ruleDefModelServiceVO = (RuleDefModelServiceVO) iteratorRuleDefModelServiceVO.next();
                                    objRuleDefinitionModel = new RuleDefinitionModel();
                                    BeanUtility.copyBeanProperty(ruleDefModelServiceVO, objRuleDefinitionModel);
                                    innerlistRuleDefConfigServiceVO = ruleDefModelServiceVO.getArlConfigService();
                                    if (RMDCommonUtility.isCollectionNotEmpty(innerlistRuleDefConfigServiceVO)) {
                                        for (final Iterator<RuleDefConfigServiceVO> iteratorRuleDefConfigServiceVO = innerlistRuleDefConfigServiceVO
                                                .iterator(); iteratorRuleDefConfigServiceVO.hasNext();) {
                                            ruleDefConfigServiceVO = (RuleDefConfigServiceVO) iteratorRuleDefConfigServiceVO.next();
                                            innerObjRuleDefinitionConfig = new RuleDefinitionConfig();
                                            BeanUtility.copyBeanProperty(ruleDefConfigServiceVO,innerObjRuleDefinitionConfig);
                                            objRuleDefinitionModel.getRuleDefinitionConfig().add(innerObjRuleDefinitionConfig);
                                            objRuleDefinition.getRuleDefinitionModelList().add(objRuleDefinitionModel);
                                        }
                                    } else {
                                        objRuleDefinition.getRuleDefinitionModelList().add(objRuleDefinitionModel);
                                    }

                                }
                            }

                            listRuleDefConfigServiceVO = ruleDefinitionServiceVO.getArlRuleDefConfig();
                            if (RMDCommonUtility.isCollectionNotEmpty(listRuleDefConfigServiceVO)) {
                                for (final Iterator<RuleDefConfigServiceVO> iteratorRuleDefConfigServiceVO = listRuleDefConfigServiceVO
                                        .iterator(); iteratorRuleDefConfigServiceVO.hasNext();) {
                                    ruleDefConfigServiceVO = (RuleDefConfigServiceVO) iteratorRuleDefConfigServiceVO.next();
                                    objRuleDefinitionConfig = new RuleDefinitionConfig();
                                    BeanUtility.copyBeanProperty(ruleDefConfigServiceVO,objRuleDefinitionConfig);
                                    objRuleDefinition.getRuleDefinitionConfig().add(objRuleDefinitionConfig);
                                }
                            }

                            BeanUtility.copyBeanProperty(ruleDefinitionServiceVO, objRuleDefinition);
                            /*
                             * Include Flag - Exclude mapping should be negated,
                             * hence this doesn't use the BeanUtility for
                             * mapping
                             */
                            if (null != ruleDefinitionServiceVO.getStrExclude()) {
                                boolean excludeFlag = OMDConstants.Y.equalsIgnoreCase(ruleDefinitionServiceVO
                                                .getStrExclude());
                                if (!excludeFlag) {
                                    objRuleDefinition.setIncludeFlag(OMDConstants.Y);
                                } else {
                                    objRuleDefinition.setIncludeFlag(OMDConstants.N);
                                }
                            } else { // Sometimes EoA stores exclude as null,
                                        // meaning def should be included
                                objRuleDefinition.setIncludeFlag(OMDConstants.Y);
                            }
                            objFinalRuleResponseType.getRuleDefinitionList().add(objRuleDefinition);
                        }
                    }
                    // add the logic so th
                    clearingLogicRule = new ClearingLogicRule();
                    objClearingLogicRuleServiceVO = objFinalRuleServiceVO.getClearingLogicRule();
                    // objClearingLogicRuleServiceVO = isCL ?
                    // objFinalRuleServiceVO.getClearingLogicRule() : null;
                    if (objClearingLogicRuleServiceVO != null) {
                        BeanUtility.copyBeanProperty(objClearingLogicRuleServiceVO, clearingLogicRule);
                        listClearingLogicSimpleRuleServiceVO = objClearingLogicRuleServiceVO.getArlSimpleRule();
                        if (listClearingLogicSimpleRuleServiceVO != null) {
                            for (final Iterator<SimpleRuleServiceVO> iteratorClearingLogicSimpleRuleServiceVO = listClearingLogicSimpleRuleServiceVO
                                    .iterator(); iteratorClearingLogicSimpleRuleServiceVO.hasNext();) {
                                clearingLogicSimpleRuleServiceVO = (SimpleRuleServiceVO) iteratorClearingLogicSimpleRuleServiceVO.next();
                                objClearingLogicSimpleRule = new SimpleRule();
                                objClearingLogicSimpleRuleClause = new SimpleRuleClause();

                                listClearingLogicSimpleRuleClause = clearingLogicSimpleRuleServiceVO.getArlSimpleRuleClauseVO();
                                if (RMDCommonUtility.isCollectionNotEmpty(listClearingLogicSimpleRuleClause)) {
                                    for (final Iterator<SimpleRuleClauseServiceVO> iteratorClearingLogicSimpleRuleClauseServiceVO = listClearingLogicSimpleRuleClause
                                            .iterator(); iteratorClearingLogicSimpleRuleClauseServiceVO.hasNext();) {
                                        clearingLogicSimpleRuleClauseServiceVO = (SimpleRuleClauseServiceVO) iteratorClearingLogicSimpleRuleClauseServiceVO.next();
                                        objClearingLogicSimpleRuleClause = new SimpleRuleClause();
                                        BeanUtility.copyBeanProperty(clearingLogicSimpleRuleClauseServiceVO,objClearingLogicSimpleRuleClause);
                                        objClearingLogicSimpleRule.getSimpleRuleClause().add(objClearingLogicSimpleRuleClause);
                                    }
                                }
                                BeanUtility.copyBeanProperty(clearingLogicSimpleRuleServiceVO,objClearingLogicSimpleRule);
                                objFinalRuleResponseType.getSimpleRule().add(objClearingLogicSimpleRule);
                            }
                        }
                        listClearingLogicComplexRule = objClearingLogicRuleServiceVO.getArlComplexRule();
                        if (null != listClearingLogicComplexRule && !listClearingLogicComplexRule.isEmpty()) {
                            for (final Iterator<ComplexRuleServiceVO> iteratorClearingLogicComplexRuleServiceVO = listClearingLogicComplexRule
                                    .iterator(); iteratorClearingLogicComplexRuleServiceVO.hasNext();) {
                                clearingLogicComplexRuleServiceVO = (ComplexRuleServiceVO) iteratorClearingLogicComplexRuleServiceVO.next();
                                objClearingLogicComplexRule = new ComplexRule();

                                listClearingLogicComplexRuleLinkServiceVO = clearingLogicComplexRuleServiceVO.getArlComplexRuleLinkVO();
                                if (RMDCommonUtility.isCollectionNotEmpty(listClearingLogicComplexRuleLinkServiceVO)) {

                                    for (final Iterator<ComplexRuleLinkServiceVO> iteratorClearingLogicComplexRuleLinkServiceVO = listClearingLogicComplexRuleLinkServiceVO
                                            .iterator(); iteratorClearingLogicComplexRuleLinkServiceVO.hasNext();) {
                                        clearingLogicComplexRuleLinkServiceVO = (ComplexRuleLinkServiceVO) iteratorClearingLogicComplexRuleLinkServiceVO.next();
                                        objClearingLogicComplexRuleLink = new ComplexRuleLink();
                                        BeanUtility .copyBeanProperty(clearingLogicComplexRuleLinkServiceVO,objClearingLogicComplexRuleLink);
                                        objClearingLogicComplexRule.getComplexRuleLink().add(objClearingLogicComplexRuleLink);
                                    }
                                }
                                BeanUtility.copyBeanProperty(clearingLogicComplexRuleServiceVO,objClearingLogicComplexRule);
                                objFinalRuleResponseType.getComplexRule().add(objClearingLogicComplexRule);
                            }
                        }
                    }
                    ruleDefinition = new RuleDefinition();
                    objRuleDefinitionServiceVO = objFinalRuleServiceVO.getRuleDefinition();

                    BeanUtility.copyBeanProperty(objRuleDefinitionServiceVO,ruleDefinition);
                    BeanUtility.copyBeanProperty(objFinalRuleServiceVO, objFinalRuleResponseType);
                    objFinalRuleResponseType.setRuleDefinition(ruleDefinition);
                    objFinalRuleResponseType.setFinalruleID(clearingLogicRule.getClearingLogicRuleID());
                    objFinalRuleResponseType.setTopLevelRuleID(clearingLogicRule.getTopLevelRuleID());
                    objFinalRuleResponseType.setRuleDescription("Clearing Logic Rule");
                } else {

                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return objFinalRuleResponseType;
    }
    @GET
    @Path(OMDConstants.GET_VIRUTALS_COLUMN_STATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getVirtualColumnStatus()
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getVirtualColumnStatus Method");
        List<LookupResponseType> arlLookupResponseType = null;
        LookupResponseType objLookupResponseType=null;
        try {
            String virtualColumnValue = objVirtualServiceIntf.isVirtualColumnExist();
            arlLookupResponseType=new ArrayList<LookupResponseType>();
            if (null != virtualColumnValue) {
                    objLookupResponseType=new LookupResponseType();
                    objLookupResponseType.setLookupID("VirtualColumn");
                    objLookupResponseType.setLookupValue(virtualColumnValue);
                    arlLookupResponseType.add(objLookupResponseType);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    @GET
    @Path(OMDConstants.GET_VIRUTAL_ACTIVE_RULES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getVirtualActiveRules(@Context final UriInfo uriParam)
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getVirtualActiveRules Method");
        List<LookupResponseType> arlLookupResponseType = null;
        LookupResponseType objLookupResponseType=null;
        List<ElementVO> arlLookupDetails = null;
        int virtualId = 0;
        String family = null;
        try {
            MultivaluedMap<String, String> queryParams = null;
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey("virtualId")) {
                    virtualId = Integer.valueOf(queryParams.getFirst("virtualId"));
                }
                if (queryParams.containsKey("family")) {
                    family = queryParams.getFirst("family");
                }
            }
            arlLookupDetails = objVirtualServiceIntf.getVirtualActiveRules(virtualId,family);
            arlLookupResponseType=new ArrayList<LookupResponseType>();
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }else{
                objLookupResponseType = new LookupResponseType();
                arlLookupResponseType.add(objLookupResponseType);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    

    
    /**
     * This is the method used for fetching the families based on external
     * customer for Rule Search
     * 
     * @param @Context final UriInfo uriParam
     * @return List<LookupResponseType>
     * @throws RMDServiceException
     * 
     */
    @GET
    @Path(OMDConstants.GET_FAMILIES_EXTERNAL_RULE_SEARCH)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getFamiliesForExternalRuleSearch(
            @Context final UriInfo uriParam) throws RMDServiceException {
        LOG.debug("Inside Rule resource in getFamiliesForExternalRuleSearch Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        String customerId = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            MultivaluedMap<String, String> queryParams = null;
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER);
                }
            }
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);

            arlLookupDetails = ruleCommonServiceInf
                    .getFamiliesForExternalRuleSearch(strLanguage, customerId);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the families based on external
     * customer for Rule Authoring
     * 
     * @param @Context final UriInfo uriParam
     * @return List<LookupResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_FAMILIES_RULE_AUTHOR)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getFamiliesForRuleAuthor(
            @Context final UriInfo uriParam) throws RMDServiceException {
        LOG.debug("Inside Rule resource in getFamiliesForRuleAuthor Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        String customerId = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        try {
            MultivaluedMap<String, String> queryParams = null;
            String isGERuleAUthor = getRequestHeader(OMDConstants.IS_GE_RULE_AUTHOR);
            queryParams = uriParam.getQueryParameters();
            if(RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){
            	customerId=getRequestHeader("strUserCustomer");				
			}else if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER);
                }
            }
            
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);

            arlLookupDetails = ruleCommonServiceInf
                    .getFamiliesForAlertsRuleAuthor(strLanguage, customerId);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the Created by for external customer
     * roles
     * 
     * @param @Context final UriInfo uriParam
     * @return List<LookupResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETRULECREATEDBYEXTERNAL)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getRuleCreatedByExternal(
            @Context final UriInfo uriParam) throws RMDServiceException {
        LOG.debug("Inside Rule resource in getRuleCreatedByExternal Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        String customer = OMDConstants.EMPTY_STRING;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            MultivaluedMap<String, String> queryParams = null;
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                }
            }
            arlLookupDetails = objRuleServiceIntf.getRuleCreatedByExternal(
                    strLanguage, customer);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }

    /**
     * This is the method used for fetching the last updated by for external
     * customer roles
     * 
     * @param @Context final UriInfo uriParam
     * @return List<LookupResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETRULESLASTUPDATESBYEXTERNAL)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getRuleLastUpdatedByExternal(
            @Context final UriInfo uriParam) throws RMDServiceException {
        LOG.debug("Inside Rule resource in getRuleLastUpdatedByExternal Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        String customer = OMDConstants.EMPTY_STRING;
        try {
            MultivaluedMap<String, String> queryParams = null;
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                }
            }
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlLookupDetails = objRuleServiceIntf.getRuleLastUpdatedByExternal(
                    strLanguage, customer);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(elementVO,
                            objLookupResponseType);
                    arlLookupResponseType.add(objLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    
    /**
     * This method returns rules fired for selected criteria
     * @param uriParam
     * @return List of AlertRuleResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.FETCH_RECENT_ALERT_FIRING)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AlertRuleResponseType> getRulesFired(@Context final UriInfo uriParam) throws RMDServiceException {
    	List<AlertRuleResponseType> arlLookupResponseType = new ArrayList<AlertRuleResponseType>();
    	MultivaluedMap<String, String> queryParams;
    	List<AlertRuleVO> listAlertRuleVO = null;
    	String strLanguage = OMDConstants.DEFAULT_LANGUAGE;
    	AlertRuleResponseType alertRuleResponseType = null;
    	String ruleId = OMDConstants.EMPTY_STRING;
    	String vehicleId = OMDConstants.EMPTY_STRING;
    	String dateStart = OMDConstants.EMPTY_STRING;
    	String dateEnd = OMDConstants.EMPTY_STRING;
    	String isGECustomer = OMDConstants.EMPTY_STRING;
    	String ruleType = OMDConstants.EMPTY_STRING;
    	String family = OMDConstants.EMPTY_STRING;
    	String vehHdr =  OMDConstants.EMPTY_STRING;
    	String roadNo =  OMDConstants.EMPTY_STRING;
    	String ruleTitle = OMDConstants.EMPTY_STRING;
    	String strCustomer = OMDConstants.EMPTY_STRING;
    	
    	try {
    		queryParams = uriParam.getQueryParameters();
    		
    		if(!queryParams.isEmpty()) {
    			
    			if(queryParams.containsKey(OMDConstants.HIT_RULE_ID)) {
    				ruleId = queryParams.getFirst(OMDConstants.HIT_RULE_ID);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.HIT_RULE_TITLE)) {
    				ruleTitle = queryParams.getFirst(OMDConstants.HIT_RULE_TITLE);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.DATE_RANGE_START)) {
    				dateStart = queryParams.getFirst(OMDConstants.DATE_RANGE_START);
    			}
    			if(queryParams.containsKey(OMDConstants.DATE_RANGE_END)) {
    				dateEnd = queryParams.getFirst(OMDConstants.DATE_RANGE_END);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.RULEE_TYPE)) {
    				ruleType = queryParams.getFirst(OMDConstants.RULEE_TYPE);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.HIT_RULE_FAMILY)) {
    				family = queryParams.getFirst(OMDConstants.HIT_RULE_FAMILY);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.VEH_HDR)) {
    				vehHdr = queryParams.getFirst(OMDConstants.VEH_HDR);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.HIT_ROAD_NO)) {
    				roadNo = queryParams.getFirst(OMDConstants.HIT_ROAD_NO);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.IS_GE_USER)) {
    				isGECustomer = queryParams.getFirst(OMDConstants.IS_GE_USER);
    			}
    			
    			 if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                     strCustomer=queryParams
                             .getFirst(OMDConstants.CUSTOMER);
                 }
    		}
    		
			listAlertRuleVO = objRuleServiceIntf.getRecentAurizonFiring(
					strLanguage, isGECustomer, ruleType, family, ruleId,
					ruleTitle, vehHdr, roadNo, vehicleId, dateStart, dateEnd,
					strCustomer);
   		if(BeanUtility.isCollectionNotEmpty(listAlertRuleVO)) {
    			for(AlertRuleVO ruleData:listAlertRuleVO) {
    				alertRuleResponseType = new AlertRuleResponseType();
    				alertRuleResponseType.setFireId(ruleData.getFireId());
    				alertRuleResponseType.setRuleId(ruleData.getRuleId());
    				alertRuleResponseType.setTitle(ruleData.getTitle());
    				alertRuleResponseType.setFamily(ruleData.getFamily());
    				alertRuleResponseType.setVersion(ruleData.getVersion());
    				alertRuleResponseType.setVehObjId(ruleData.getVehObjId());
    				alertRuleResponseType.setVehHdr(ruleData.getVehHdr());
    				alertRuleResponseType.setVehSerialNo(ruleData.getVehSerialNo());
    				alertRuleResponseType.setCreatedOn(ruleData.getCreatedOn());
    				alertRuleResponseType.setDiagService(ruleData.getDiagService());
    				alertRuleResponseType.setAlertStatus(ruleData.getAlertStatus());
    				alertRuleResponseType.setStrCustomer(ruleData.getStrCustomer());
    				arlLookupResponseType.add(alertRuleResponseType);
    			}
    		}
    		
    	} catch(Exception ex) {
    		RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
    	}
    	
    	return arlLookupResponseType;
    }
    
    /**
     * This method returns Alert Runs for last 24 hrs.
     * @param uriParam
     * @return List of AlertRunSearchResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.FETCH_ALERT_RUNS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AlertRunSearchResponseType> getAlertRuns(@Context final UriInfo uriParam) throws RMDServiceException {
    	List<AlertRunSearchResponseType> arlLookupResponseType = new ArrayList<AlertRunSearchResponseType>();
    	MultivaluedMap<String, String> queryParams;
    	List<AlertRunsVO> listAlertRunVO = null;
    	String strLanguage = OMDConstants.EMPTY_STRING;
    	AlertRunSearchResponseType alertRunsResponseType = null;
    	String dateStart = OMDConstants.EMPTY_STRING;
    	String dateEnd = OMDConstants.EMPTY_STRING;
    	String isGECustomer = OMDConstants.EMPTY_STRING;
    	String diagService = OMDConstants.EMPTY_STRING;
    	String vehHdr = OMDConstants.EMPTY_STRING;
    	String roadNo = OMDConstants.EMPTY_STRING;
    	String strCustomer = OMDConstants.EMPTY_STRING;
    	
    	try {
    		queryParams = uriParam.getQueryParameters();
    		
    		//Query params should not be empty..
    		if(!queryParams.isEmpty()) {

    			if(queryParams.containsKey(OMDConstants.DATE_RANGE_START)) {
    				dateStart = queryParams.getFirst(OMDConstants.DATE_RANGE_START);
    			}
    			if(queryParams.containsKey(OMDConstants.DATE_RANGE_END)) {
    				dateEnd = queryParams.getFirst(OMDConstants.DATE_RANGE_END);
    			}
    			if(queryParams.containsKey(OMDConstants.DIAG_SERVICE_ID)) {
    				diagService = queryParams.getFirst(OMDConstants.DIAG_SERVICE_ID);
    			}
    			if(queryParams.containsKey(OMDConstants.VEH_HDR)) {
    				vehHdr = queryParams.getFirst(OMDConstants.VEH_HDR);
    			}
    			if(queryParams.containsKey(OMDConstants.HIT_ROAD_NO)) {
    				roadNo = queryParams.getFirst(OMDConstants.HIT_ROAD_NO);
    			}
    			if(queryParams.containsKey(OMDConstants.IS_GE_USER)) {
    				isGECustomer = queryParams.getFirst(OMDConstants.IS_GE_USER);
    			}
    			if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    strCustomer=queryParams
                            .getFirst(OMDConstants.CUSTOMER);
                }
    		}
    		
			listAlertRunVO = objRuleServiceIntf.getAlertRuns(strLanguage,
					isGECustomer, vehHdr, roadNo, diagService, dateStart,
					dateEnd, strCustomer);
   		if(BeanUtility.isCollectionNotEmpty(listAlertRunVO)) {
    			for(AlertRunsVO runData:listAlertRunVO) {
    				alertRunsResponseType = new AlertRunSearchResponseType();
    				alertRunsResponseType.setRunObjId(runData.getRunObjId());
    				alertRunsResponseType.setRunDate(runData.getRunDate());
    				alertRunsResponseType.setRun2Vehicle(runData.getRun2Vehicle());
    				alertRunsResponseType.setVehHdrCust(runData.getVehHdrCust());
    				alertRunsResponseType.setSerialNo(runData.getSerialNo());
    				alertRunsResponseType.setFamily(runData.getFamily());
    				alertRunsResponseType.setRuleId(runData.getRuleId());
    				alertRunsResponseType.setRuleTitle(runData.getRuleTitle());
    				alertRunsResponseType.setStrCustomer(runData.getStrCustomer());
    				arlLookupResponseType.add(alertRunsResponseType);
    			}
    		}
    		
    	} catch(Exception ex) {
    		RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
    	}
    	
    	return arlLookupResponseType;
    }
    
    /**
     * This method returns Alert Rules for selected family.
     * @param uriParam
     * @return List of AlertRunSearchResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.FETCH_RULES_FOR_FAMILY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AlertRuleResponseType> getAlertRulesForFamily(@Context final UriInfo uriParam) throws RMDServiceException {
    	List<AlertRuleResponseType> arlLookupResponseType = new ArrayList<AlertRuleResponseType>();
    	MultivaluedMap<String, String> queryParams;
    	List<AlertRuleVO> listAlertRuleVO = null;
    	String strLanguage = OMDConstants.EMPTY_STRING;
    	AlertRuleResponseType alertRuleResponseType = null;
    	String family = OMDConstants.EMPTY_STRING;
    	String customerId = OMDConstants.EMPTY_STRING;
    	String isGECustomer = OMDConstants.EMPTY_STRING;
    	
    	try {
    		
    		queryParams = uriParam.getQueryParameters();
    		
    		//Query params should not be empty..
    		if(!queryParams.isEmpty()) {
    			
    			if(queryParams.containsKey(OMDConstants.HIT_RULE_FAMILY)) {
    				family = queryParams.getFirst(OMDConstants.HIT_RULE_FAMILY);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.IS_GE_USER)) {
    				isGECustomer = queryParams.getFirst(OMDConstants.IS_GE_USER);
    			}
    			
    			if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
    				customerId=queryParams
                             .getFirst(OMDConstants.CUSTOMER);
                 }
    		}
    		
			listAlertRuleVO = objRuleServiceIntf.getAlertRulesForFamily(
					strLanguage, isGECustomer, customerId, family);
			
    		if(BeanUtility.isCollectionNotEmpty(listAlertRuleVO)) {
    			for(AlertRuleVO runData:listAlertRuleVO) {
    				alertRuleResponseType = new AlertRuleResponseType();
    				alertRuleResponseType.setRuleId(runData.getRuleId());
    				alertRuleResponseType.setTitle(runData.getTitle());
    				arlLookupResponseType.add(alertRuleResponseType);
    			}
    		}
    		
    	} catch(Exception ex) {
    		RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
    	}
    	
    	return arlLookupResponseType;
    }
        
    /**
     * Gets firing details for an Alert
     * @param uriParam
     * @return 
     * @throws RMDServiceException
     * @author 212556286
     */
    @GET
    @Path(OMDConstants.FETCH_FIRING_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public AlertFiringDetailsResponseType getAlertFiringDetails(@Context final UriInfo uriParam) throws RMDServiceException {
    	MultivaluedMap<String, String> queryParams;
    	AlertRunsVO firingData = null;
    	String strLanguage = OMDConstants.EMPTY_STRING;
    	AlertFiringDetailsResponseType alertFiringResponseType = null;
    	String isGECustomer = OMDConstants.EMPTY_STRING;
    	String alertFiringId = OMDConstants.EMPTY_STRING;
    	String strCustomer = OMDConstants.EMPTY_STRING;
    	String custUOM = OMDConstants.EMPTY_STRING;
    	
    	
    	try {
	
    		queryParams = uriParam.getQueryParameters();
		
    		//Query params should not be empty..
    		if(!queryParams.isEmpty()) {

    			if(queryParams.containsKey(OMDConstants.ALERT_FIRING_ID)) {
    				alertFiringId = queryParams.getFirst(OMDConstants.ALERT_FIRING_ID);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.IS_GE_USER)) {
    				isGECustomer = queryParams.getFirst(OMDConstants.IS_GE_USER);
    			}
    			
    			 if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                     strCustomer=queryParams
                             .getFirst(OMDConstants.CUSTOMER);
                 }
    			
    			if(queryParams.containsKey(OMDConstants.CUST_UOM)) {
    				custUOM = queryParams.getFirst(OMDConstants.CUST_UOM);
    			}
    		}
    		
			firingData = objRuleServiceIntf.getAlertFiringDetails(strLanguage,
					isGECustomer, alertFiringId, custUOM, strCustomer);

    		alertFiringResponseType = new AlertFiringDetailsResponseType();
    		alertFiringResponseType.setRuleId(firingData.getRuleId());
    		alertFiringResponseType.setRuleTitle(firingData.getRuleTitle());
    		alertFiringResponseType.setVehicle(firingData.getVehHdrCust().concat(" ").concat(firingData.getSerialNo()));
    		alertFiringResponseType.setGroupName(firingData.getVehHdrCust());
    		alertFiringResponseType.setAssetNo(firingData.getSerialNo());
    		alertFiringResponseType.setProcessedOn(firingData.getRuleFiredDate());
    		alertFiringResponseType.setFiring2LastDataRecord(firingData.getFiredDataRecord());
    		alertFiringResponseType.setAlertSent(firingData.isAlertSent());
    		alertFiringResponseType.setStrCustomer(firingData.getStrCustomer());
    		List<AlertRuleParmResponseType> alertParmData = new ArrayList<AlertRuleParmResponseType>();
    		for(AlertRuleParmVO data:firingData.getRuleParmData()) {
    			AlertRuleParmResponseType obj = new AlertRuleParmResponseType();
    			obj.setFaultObjId(data.getFaultObjId());
    			obj.setRecordType(data.getRecordType());
    			obj.setCmuTime(data.getCmuTime());
    			obj.setIncidentTime(data.getIncidentTime());
    			obj.setFaultCode(data.getFaultCode());
    			obj.setParmNames(data.getParmNames());
    			obj.setParmDisplayNames(data.getParmDisplayNames());
    			List<RuleParmData> ruleParmData = new ArrayList<RuleParmData>();
    			for(RuleParmVO dat1:data.getParmSet()){
    				RuleParmData parmData = new RuleParmData();
    				parmData.setParmName(dat1.getParmName());
    				parmData.setParmValue(dat1.getParmValue());
    				ruleParmData.add(parmData);
    			}
    			obj.setParmSet(ruleParmData);
    			alertParmData.add(obj);
    		}
    		alertFiringResponseType.setRuleParmData(alertParmData);
    		
    		
    	} catch(Exception ex) {
    		RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
    	}
    	
    	return alertFiringResponseType;
    }
    
    /**
     * Fetches firing details for a Run for selected Rule (Misses Tab)
     * @param uriParam
     * @return AlertFiringDetailsResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.FETCH_RULE_MISS_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public AlertFiringDetailsResponseType getAlertRuleMissDetails(@Context final UriInfo uriParam) throws RMDServiceException {
    	MultivaluedMap<String, String> queryParams;
    	AlertRunsVO firingData = null;
    	String strLanguage = OMDConstants.EMPTY_STRING;
    	AlertFiringDetailsResponseType alertFiringResponseType = null;
    	String strCustomer = OMDConstants.EMPTY_STRING;
    	String isGECustomer = OMDConstants.EMPTY_STRING;
    	String runId = OMDConstants.EMPTY_STRING;
    	String ruleId = OMDConstants.EMPTY_STRING;
    	String custUOM = OMDConstants.EMPTY_STRING;
    	
    	
    	try {
    		queryParams = uriParam.getQueryParameters();
       		
    		//Query params should not be empty..
    		if(!queryParams.isEmpty()) {
    			
    			if(queryParams.containsKey(OMDConstants.RUN_OBJ_ID)) {
    				runId = queryParams.getFirst(OMDConstants.RUN_OBJ_ID);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.RULE_ID)) {
    				ruleId = queryParams.getFirst(OMDConstants.RULE_ID);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.IS_GE_USER)) {
    				isGECustomer = queryParams.getFirst(OMDConstants.IS_GE_USER);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.CUST_UOM)) {
    				custUOM = queryParams.getFirst(OMDConstants.CUST_UOM);
    			}
    			 if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                     strCustomer=queryParams
                             .getFirst(OMDConstants.CUSTOMER);
                 }
    		}
    		
			firingData = objRuleServiceIntf.getAlertMissingDetails(strLanguage,
					isGECustomer, ruleId, runId, custUOM, strCustomer);

    		alertFiringResponseType = new AlertFiringDetailsResponseType();
    		alertFiringResponseType.setRuleId(firingData.getRuleId());
    		alertFiringResponseType.setRuleTitle(firingData.getRuleTitle());
    		alertFiringResponseType.setVehicle(firingData.getVehHdrCust().concat(" ").concat(firingData.getSerialNo()));
    		alertFiringResponseType.setGroupName(firingData.getVehHdrCust());
    		alertFiringResponseType.setAssetNo(firingData.getSerialNo());
    		alertFiringResponseType.setProcessedOn(firingData.getRunDate());
    		alertFiringResponseType.setFiring2LastDataRecord(firingData.getFiredDataRecord());
    		alertFiringResponseType.setAlertSent(false);
    		alertFiringResponseType.setStrCustomer(firingData.getStrCustomer());
    		List<AlertRuleParmResponseType> alertParmData = new ArrayList<AlertRuleParmResponseType>();
    		for(AlertRuleParmVO data:firingData.getRuleParmData()) {
    			AlertRuleParmResponseType obj = new AlertRuleParmResponseType();
    			obj.setFaultObjId(data.getFaultObjId());
    			obj.setRecordType(data.getRecordType());
    			obj.setCmuTime(data.getCmuTime());
    			obj.setIncidentTime(data.getIncidentTime());
    			obj.setParmNames(data.getParmNames());
    			obj.setParmDisplayNames(data.getParmDisplayNames());
    			List<RuleParmData> ruleParmData = new ArrayList<RuleParmData>();
    			for(RuleParmVO dat1:data.getParmSet()){
    				RuleParmData parmData = new RuleParmData();
    				parmData.setParmName(dat1.getParmName());
    				parmData.setParmValue(dat1.getParmValue());
    				ruleParmData.add(parmData);
    			}
    			obj.setParmSet(ruleParmData);
    			alertParmData.add(obj);
    		}
    		alertFiringResponseType.setRuleParmData(alertParmData);
    		
    		
    	} catch(Exception ex) {
    		RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
    	}
    	
    	return alertFiringResponseType;
    }
    
    /**
     * Fetches rules based on family
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_RULE_BY_FAMILY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getRuleByFamily(@Context final UriInfo uriParam)
    throws RMDServiceException {
        LOG.debug("Inside Rule resource in getRuleByFamily Method");
        List<LookupResponseType> arlLookupResponseType = new ArrayList<LookupResponseType>();
        List<RuleByFamilyVO> arlLookupDetails = new ArrayList<RuleByFamilyVO>();
        String strLanguage = OMDConstants.EMPTY_STRING;
        String  family = OMDConstants.EMPTY_STRING;
        LookupResponseType objLookupResponseType = null;
        MultivaluedMap<String, String> queryParams;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            queryParams = uriParam.getQueryParameters();
            if(!queryParams.isEmpty() && queryParams.containsKey(OMDConstants.FAMILY)) {
            	family = queryParams.getFirst(OMDConstants.FAMILY);
            }
            arlLookupDetails = objRuleServiceIntf.getRuleByFamily(strLanguage, family);
                for (RuleByFamilyVO elementVO : arlLookupDetails) {
                    objLookupResponseType = new LookupResponseType();
                    objLookupResponseType.setLookupID(elementVO.getRuleId());
                    objLookupResponseType.setLookupValue(elementVO.getTitle());
                    arlLookupResponseType.add(objLookupResponseType);
                }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupResponseType;
    }
    
    @GET
    @Path(OMDConstants.GET_ROADNUM_HEADERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetHeaderResponseType> getRoadnumberHeaders(
            @Context final UriInfo uriParam) throws RMDServiceException {

        List<ElementVO> arlRoadInitials = new ArrayList<ElementVO>();
        AssetHeaderResponseType responseType = null;
        List<AssetHeaderResponseType> arlResponseType = new ArrayList<AssetHeaderResponseType>();
        MultivaluedMap<String, String> queryParams = null;
        String customerId = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
                }
                arlRoadInitials = objRuleServiceIntf.getRoadNumberHeaders(customerId);

                if (RMDCommonUtility.isCollectionNotEmpty(arlRoadInitials)) {
                    for (ElementVO objElementVO : arlRoadInitials) {
                        responseType = new AssetHeaderResponseType();
                        responseType.setAssetGroupNameOrder(objElementVO
                                .getId());
                        responseType.setAssetGroupName(objElementVO.getName());
                        arlResponseType.add(responseType);
                    }
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return arlResponseType;
    }
    @GET
    @Path(OMDConstants.GET_RULE_CACHE_REFRESH)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getNextRuleCacheRefresh(
			@PathParam(OMDConstants.RULE_TYPE) final String ruleType)
			throws RMDServiceException {
        LOG.debug("Inside Rule resource in getNextRuleCacheRefresh Method");
        String nextRuleCacheRefreshTime = null;
        try {
             nextRuleCacheRefreshTime = objRuleServiceIntf.getNextRuleCacheRefresh(ruleType);
        } catch (Exception ex) {
        	ex.printStackTrace();
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return nextRuleCacheRefreshTime;
    }
    
    
    /**
     * This method used to invoke the service layer to get the ror rule information
     * @param String
     * @param UriInfo
     * @return 
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_ROR_RULES_DETAILS)
    @Produces({ MediaType.APPLICATION_JSON})
    public List<String> getRoRRuleInformation(@Context UriInfo uriParam) throws RMDServiceException {        
    	MultivaluedMap<String, String> queryParams;
    	String ruleId=RMDCommonConstants.EMPTY_STRING;
    	String rorRules=RMDCommonConstants.EMPTY_STRING;
    	List<String> ruleIdList=new ArrayList<String>();
    	List<String> lstNotRorRules=new ArrayList<String>();
        try {                     
        	queryParams = uriParam.getQueryParameters();
       		
    		if(!queryParams.isEmpty()) {
    			
    			
    			if(queryParams.containsKey(OMDConstants.RULE_ID)) {
    				ruleId = queryParams.getFirst(OMDConstants.RULE_ID);
    			}
    			
    			if(queryParams.containsKey(OMDConstants.ROR_RULES)) {
    				rorRules = queryParams.getFirst(OMDConstants.ROR_RULES);
    				if(null!=rorRules)
    				ruleIdList=Arrays.asList(rorRules.split(","));
    			}
    			    			
    		}
             
                if (RMDCommonUtility.isCollectionNotEmpty(ruleIdList)&&null!=ruleId&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(ruleId)) {
                	lstNotRorRules = objRuleServiceIntf.getRoRRuleInformation(ruleIdList, ruleId);
                } 
        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return lstNotRorRules;
    }
}

/**
 * ============================================================
 * File : ViewRuleDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.common.valueobjects.DpdFinrulSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.UnitOfMeasureVO;
import com.ge.trans.eoa.services.tools.fault.dao.intf.FaultServiceStgyDAOIntf;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceStgyServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.ViewRuleDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ClearingLogicRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ComplexRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.IDListServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefConfigServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefCustomerServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefModelServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefinitionServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleHistoryServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SimpleRuleClauseServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SimpleRuleServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings({ "serial", "unchecked" })
public class ViewRuleDAOImpl extends BaseDAO implements ViewRuleDAOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ViewRuleDAOImpl.class);

    FaultServiceStgyDAOIntf objFaultServiceStgyDAOIntf;

    public void setObjFaultServiceStgyDAOIntf(FaultServiceStgyDAOIntf objFaultServiceStgyDAOIntf) {
        this.objFaultServiceStgyDAOIntf = objFaultServiceStgyDAOIntf;
    }

    /**
     * This is the method used for fetching the rule details for the given Input
     * 
     * @param strRuleId
     *            ,strCloneRule,strLanguage
     * @return FinalRuleServiceVO
     * @throws RMDDAOException
     */
    @Override
	public FinalRuleServiceVO getRuleDetails(String strRuleId,
			String strCloneRule, String strLanguage,String flgRORExplode,String defaultUom) throws RMDDAOException,
			RMDDAOConnectionException {
        LOG.debug("ViewRuleDAOImpl.getRuleDetails() Started");
        Session hibSession = null;
        FinalRuleServiceVO objFinalRuleServiceVO = null;
        ArrayList<RuleDefinitionServiceVO> arlRuleDefinitionServiceVO;
        ArrayList<RuleHistoryServiceVO> arlRuleHistoryServiceVO;
        ArrayList<SimpleRuleServiceVO> arlSimpleRule;
        ArrayList<ComplexRuleServiceVO> arlComplexRule;
        ArrayList<SimpleRuleServiceVO> arlClearingLogicSimpleRule;
        ArrayList<ComplexRuleServiceVO> arlClearingLogicComplexRule;
        ClearingLogicRuleServiceVO objClearingLogicRuleServiceVO = null;
        LOG.debug("strRuleId : " + strRuleId + " strCloneRule : " + strCloneRule + " strLanguage : " + strLanguage
                + " flgRORExplode : " + flgRORExplode);
        try {
            String strFamily = null;
            String originalId = null;
            hibSession = getHibernateSession();
            StringBuilder strBuffer = new StringBuilder();
            // @formatter:off
            strBuffer.append(" SELECT DISTINCT" + "  FINRUL.OBJID," + "  FINRUL.FAMILY," + "  FINRUL.SUBSYSTEM,"
                    + "  FINRUL.RULE_TITLE," + "  RULEHIS.VERSION_NO," + "  RULEHIS.ACTIVE," + "  RULEHIS.COMPLETED,"
                    + "  RULEHIS.PREVIOUS_VERSION_ID," + "  RULEHIS.ORIGINAL_ID," + "  FINRUL.LOOKBACK,"
                    + "  FINRUL.PIXELS," + "  FINRUL.FINRUL2SIMRUL," + "  FINRUL.FINRUL2CMPRUL,"
                    + "  FINRUL.RULE_DESCRIPTION," + "  CL_RUL.OBJID CL_RUL_OBJID," + "  CL_RUL.CL_RUL2SIMRUL,"
                    + "  CL_RUL.CL_RUL2CMPRUL," + "  CL_RUL.PIXELS CL_RUL_PIXELS" + " FROM"
                    + "  GETS_TOOL_DPD_FINRUL FINRUL," + "  GETS_TOOL_DPD_CL_RUL CL_RUL,"
                    + "  GETS_TOOL_DPD_RULHIS RULEHIS" + " WHERE" + " CL_RUL.CL_RUL2FINRUL(+)=FINRUL.OBJID"
                    + " AND RULEHIS.RULHIS2FINRUL=FINRUL.OBJID" + " AND FINRUL.OBJID       =:ruleId");
            // @formatter:on
            Query qry = hibSession.createSQLQuery(strBuffer.toString());
            qry.setParameter(RMDServiceConstants.RULE_ID, strRuleId);
            List<Object> arlRuleDetails = qry.list();
            if (null != arlRuleDetails && !arlRuleDetails.isEmpty()) {
                objFinalRuleServiceVO = new FinalRuleServiceVO();
                for (Iterator<Object> iterator = arlRuleDetails.iterator(); iterator.hasNext();) {

                    Object obj[] = (Object[]) iterator.next();
                    strFamily = RMDCommonUtility.convertObjectToString(obj[1]);
                    originalId = RMDCommonUtility.convertObjectToString(obj[8]);
                    objFinalRuleServiceVO.setStrFinalRuleID(RMDCommonUtility.convertObjectToString(obj[0]));
                    objFinalRuleServiceVO.setStrFamily(strFamily);
                    objFinalRuleServiceVO.setStrSubsystem(RMDCommonUtility.convertObjectToString(obj[2]));
                    objFinalRuleServiceVO
                            .setStrRuleTitle(AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(obj[3])));
                    objFinalRuleServiceVO.setStrOriginalId(RMDCommonUtility.convertObjectToString(obj[8]));
                    objFinalRuleServiceVO.setStrLookBack(RMDCommonUtility.convertObjectToString(obj[9]));
                    objFinalRuleServiceVO.setStrRuleDescription(
                            AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(obj[13])));
                    objFinalRuleServiceVO.setStrPixels(RMDCommonUtility.convertObjectToString(obj[10]));
                    if ((obj[11] != null)) {
                        if (!obj[11].toString().equals(RMDServiceConstants.NEGATIVE_ONE)) {
                            objFinalRuleServiceVO.setStrTopLevelRuleId(obj[11].toString());
                        }
                    }
                    if ((obj[12] != null)) {
                        if (!obj[12].toString().equals(RMDServiceConstants.NEGATIVE_ONE)) {
                            objFinalRuleServiceVO.setStrTopLevelRuleId(obj[12].toString());
                        }
                    }

                    // Fetching the Simple Rule datas
					arlSimpleRule = getSimpleRuleDetails(strRuleId,strFamily, strLanguage,defaultUom);
                    if (null != arlSimpleRule && !arlSimpleRule.isEmpty()) {
                        objFinalRuleServiceVO.setArlSimpleRule(arlSimpleRule);
                    }
					arlComplexRule = getComplexRuleDetails(strRuleId,
							strFamily,strLanguage,arlSimpleRule,flgRORExplode,defaultUom);
                            
                    if (null != arlComplexRule && !arlComplexRule.isEmpty()) {
                        objFinalRuleServiceVO.setArlComplexRule(arlComplexRule);
                        // Setting the toplevel rule id here
                    }

                    // fetching the rule history details if it is 'N' for clone
                    // rule
                    if (!(RMDCommonConstants.LETTER_Y).equalsIgnoreCase(strCloneRule)) {
                        arlRuleHistoryServiceVO = getRuleHistoryDetails(originalId, strLanguage);
                        if (null != arlRuleHistoryServiceVO && !arlRuleHistoryServiceVO.isEmpty()) {
                            objFinalRuleServiceVO.setArlRuleHistoryVO(arlRuleHistoryServiceVO);
                        }
                    }

                    String strClearingLogicRuleID = RMDCommonUtility.convertObjectToString(obj[14]);
                    if (strClearingLogicRuleID != null) {
                        objClearingLogicRuleServiceVO = new ClearingLogicRuleServiceVO();
                        objClearingLogicRuleServiceVO.setStrClearingLogicRuleID(strClearingLogicRuleID);
                        objClearingLogicRuleServiceVO
                                .setStrFinalRuleID(RMDCommonUtility.convertObjectToString(obj[15]));
                        if ((obj[15] != null)) {
                            if (!obj[15].toString().equals(RMDServiceConstants.NEGATIVE_ONE)) {
                                objClearingLogicRuleServiceVO.setStrTopLevelRuleID(obj[15].toString());
                            }
                        }
                        if ((obj[16] != null)) {
                            if (!obj[16].toString().equals(RMDServiceConstants.NEGATIVE_ONE)) {
                                objClearingLogicRuleServiceVO.setStrTopLevelRuleID(obj[16].toString());
                            }
                        }
                        objClearingLogicRuleServiceVO.setStrPixels(RMDCommonUtility.convertObjectToString(obj[17]));

                        // Fetching the Simple Rule datas
						arlClearingLogicSimpleRule = getSimpleRuleDetails(strClearingLogicRuleID,strFamily, strLanguage,defaultUom);
                                
                        if (null != arlClearingLogicSimpleRule && !arlClearingLogicSimpleRule.isEmpty()) {
                            objClearingLogicRuleServiceVO.setArlSimpleRule(arlClearingLogicSimpleRule);
                            // Setting the toplevel rule id here
                        }
						arlClearingLogicComplexRule = getComplexRuleDetails(strClearingLogicRuleID,
								strFamily,strLanguage,arlClearingLogicSimpleRule,flgRORExplode,defaultUom);
                        if (null != arlClearingLogicComplexRule && !arlClearingLogicComplexRule.isEmpty()) {
                            objClearingLogicRuleServiceVO.setArlComplexRule(arlClearingLogicComplexRule);
                            // Setting the toplevel rule id here
                        }
                    }
                }
                objFinalRuleServiceVO.setClearingLogicRule(objClearingLogicRuleServiceVO);

                // fetching the rule definition details
                arlRuleDefinitionServiceVO = getRuleDefinitionDetails(strRuleId, strFamily, strLanguage);
                if (null != arlRuleDefinitionServiceVO && !arlRuleDefinitionServiceVO.isEmpty()) {
                    objFinalRuleServiceVO.setArlRuleDefinition(arlRuleDefinitionServiceVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        LOG.debug("ViewRuleDAOImpl.getRuleDetails() Ended");
        return objFinalRuleServiceVO;

    }

    /**
     * This is the method used for fetching the ruleDefinition Details for the
     * particular RuleId
     * 
     * @param strRuleId
     *            ,strFamily,strLanguage
     * @return ArrayList<RuleDefinitionServiceVO>
     * @throws RMDDAOException
     */

    private ArrayList<RuleDefinitionServiceVO> getRuleDefinitionDetails(String strRuleId, String strFamily,
            String strLanguage) {

        Session hibSession = null;
        RuleDefinitionServiceVO objRuleDefinitionServiceVO = null;
        ArrayList<RuleDefConfigServiceVO> arlConfigServiceVO;
        ArrayList<RuleDefModelServiceVO> arlRuleDefModelServiceVO;
        ArrayList<RuleDefinitionServiceVO> arlRuleDefinitionServiceVO = null;
        ArrayList<RuleDefCustomerServiceVO> arlRuleDefCustomerServiceVO;
        try {
            hibSession = getHibernateSession();
            arlRuleDefinitionServiceVO = new ArrayList<RuleDefinitionServiceVO>();
            StringBuilder strQry = new StringBuilder();
            // query to bring the rule definition basic details from DB for the
            // particular ruleId
            strQry.append(
                    "SELECT RULEDEF.OBJID,RULEDEF.MESSAGE,RULEDEF.RULEDEF2APP,RULEDEF.RULEDEF2FINRUL,RULEDEF.RULEDEF2RECOM,RULEDEF.RULE_TYPE");
            strQry.append(" FROM GETS_TOOL_DPD_RULEDEF RULEDEF");
            strQry.append(" WHERE RULEDEF.RULEDEF2FINRUL=:ruleId");
            Query ruleDefQry = hibSession.createSQLQuery(strQry.toString());
            ruleDefQry.setParameter(RMDServiceConstants.RULE_ID, strRuleId);
            List<Object> arlRuleDefDetails = ruleDefQry.list();
            if (null != arlRuleDefDetails && !arlRuleDefDetails.isEmpty()) {

                for (Iterator<Object> ruleDefIterator = arlRuleDefDetails.iterator(); ruleDefIterator.hasNext();) {

                    objRuleDefinitionServiceVO = new RuleDefinitionServiceVO();
                    Object objRuleDef[] = (Object[]) ruleDefIterator.next();
                    objRuleDefinitionServiceVO.setStrFinalRuleID(RMDCommonUtility.convertObjectToString(objRuleDef[3]));
                    objRuleDefinitionServiceVO.setStrRuleDefID(RMDCommonUtility.convertObjectToString(objRuleDef[0]));
                    objRuleDefinitionServiceVO.setStrMessage(RMDCommonUtility.convertObjectToString(objRuleDef[1]));
                    objRuleDefinitionServiceVO.setStrRuleType(RMDCommonUtility.convertObjectToString(objRuleDef[5]));
                    String strRecomId = RMDCommonUtility.convertObjectToString(objRuleDef[4]);
                    String strRuleDefId = RMDCommonUtility.convertObjectToString(objRuleDef[0]);
                    if (null != strRecomId) {
                        StringBuilder strRecomQry = new StringBuilder();
                        strRecomQry.append("select title from gets_sd_recom where objid =:recomId");
                        Query recomQry = hibSession.createSQLQuery(strRecomQry.toString());
                        recomQry.setParameter(RMDServiceConstants.RECOM_ID, strRecomId);
                        List<Object> arlRecomDetails = recomQry.list();
                        for (Iterator<Object> iterator = arlRecomDetails.iterator(); iterator.hasNext();) {
                            Object objRecom = iterator.next();
                            objRuleDefinitionServiceVO
                                    .setStrRecommendation(RMDCommonUtility.convertObjectToString(objRecom));
                        }
                    }
                    // This condition included to check performance data if rule
                    // type is Health
                    if (null != objRuleDef[5]
                            && objRuleDef[5].toString().equalsIgnoreCase(RMDServiceConstants.HEALTH_RULE_TYPE)) {

                        Query qry = hibSession.createSQLQuery(
                                "SELECT HEALTH_FACTOR, PERF_CHECK_ID FROM GETS_TOOLS.GETS_TOOL_LHR_PERFCHK2RULEDEF WHERE rule_def_id = :ruleDefId");
                        qry.setParameter(RMDServiceConstants.RULEDEF_ID, strRuleDefId);
                        List<Object> hlthFactorList = qry.list();

                        if (RMDCommonUtility.isCollectionNotEmpty(hlthFactorList)) {
                            for (Iterator<Object> iterator = hlthFactorList.iterator(); iterator.hasNext();) {
                                Object object[] = (Object[]) iterator.next();

                                if (null != object[0]) { // HEALTH_FACTOR
                                    objRuleDefinitionServiceVO.setHealthFactor(object[0].toString());
                                }

                                if (null != object[1]) { // PERF_CHECK_ID
                                    objRuleDefinitionServiceVO.setPerfHealthCheckID(object[1].toString());
                                }
                            }
                        }
                    }

                    // calling the getRuleDefConfigDetails method to populate
                    // the list of configuration details for the particular
                    // ruleDefinition
                    arlConfigServiceVO = getRuleDefConfigDetails(strRuleDefId, strLanguage);
                    // calling the getRuleDefModelDetails method to populate the
                    // list of model details for the particular ruleDefinition
                    arlRuleDefModelServiceVO = getRuleDefModelDetails(strRuleDefId, strLanguage, strFamily);
                    // calling the getRuleDefModelDetails method to populate the
                    // list of model details for the particular ruleDefinition
                    arlRuleDefCustomerServiceVO = getRuleDefCustomerDetails(strRuleDefId, strLanguage,
                            objRuleDefinitionServiceVO);

                    if (null != arlConfigServiceVO && !arlConfigServiceVO.isEmpty()) {

                        objRuleDefinitionServiceVO.setArlRuleDefConfig(arlConfigServiceVO);
                    }

                    if (null != arlRuleDefCustomerServiceVO && !arlRuleDefCustomerServiceVO.isEmpty()) {
                        objRuleDefinitionServiceVO.setArlRuleDefCustomer(arlRuleDefCustomerServiceVO);
                    }

                    if (null != arlRuleDefModelServiceVO && !arlRuleDefModelServiceVO.isEmpty()) {
                        objRuleDefinitionServiceVO.setArlRuleDefModel(arlRuleDefModelServiceVO);
                    }
                    arlRuleDefinitionServiceVO.add(objRuleDefinitionServiceVO);
                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlRuleDefinitionServiceVO;

    }

    /**
     * This method is used for fetching the customer and corresponding fleet
     * details for the particular Rule Definition
     * 
     * @param String
     *            strRuleDefId, String strLanguage
     * @return ArrayList<RuleDefCustomerServiceVO>
     * @throws RMDDAOException
     */

    private ArrayList<RuleDefCustomerServiceVO> getRuleDefCustomerDetails(String strRuleDefId, String strLanguage,
            RuleDefinitionServiceVO objRuleDefinitionServiceVO) {

        Session hibSession = null;
        RuleDefCustomerServiceVO objRuleDefCustomerServiceVO;
        ElementVO objElementVO;
        ArrayList<ElementVO> arlRuleDefFleet;
        ArrayList<RuleDefCustomerServiceVO> arlRuleDefCustomerServiceVO = null;
        try {
            hibSession = getHibernateSession();
            StringBuilder strCustQuery = new StringBuilder();
            // query used for fetching the customers based on ruleDefinition Id
            strCustQuery.append(
                    "SELECT DISTINCT CUST.OBJID, CUST.CUST2BUSORG,CUST.EXCLUDE,CUST.CUST2RULEDEF,BUSORG.S_ORG_ID");
            strCustQuery.append(" FROM GETS_TOOL_DPD_CUST CUST,TABLE_BUS_ORG BUSORG");
            strCustQuery.append(" WHERE CUST.CUST2BUSORG=BUSORG.OBJID AND CUST.CUST2RULEDEF=:ruleDefId");
            Query CustQry = hibSession.createSQLQuery(strCustQuery.toString());
            CustQry.setParameter(RMDServiceConstants.RULEDEF_ID, strRuleDefId);
            List<Object> arlCustDetails = CustQry.list();

            arlRuleDefCustomerServiceVO = new ArrayList<RuleDefCustomerServiceVO>();
            arlRuleDefFleet = new ArrayList<ElementVO>();
            if (null != arlCustDetails && !arlCustDetails.isEmpty()) {

                for (Iterator<Object> custIterator = arlCustDetails.iterator(); custIterator.hasNext();) {

                    Object objCustDetails[] = (Object[]) custIterator.next();
                    objRuleDefCustomerServiceVO = new RuleDefCustomerServiceVO();
                    objRuleDefCustomerServiceVO
                            .setStrCustomerID(RMDCommonUtility.convertObjectToString(objCustDetails[1]));
                    objRuleDefCustomerServiceVO
                            .setStrCustomer(RMDCommonUtility.convertObjectToString(objCustDetails[4]));
                    objRuleDefCustomerServiceVO
                            .setStrIsExclude(RMDCommonUtility.convertObjectToString(objCustDetails[2]));
                    objRuleDefinitionServiceVO.setStrExclude(RMDCommonUtility.convertObjectToString(objCustDetails[2]));
                    StringBuilder strFleetQuery = new StringBuilder();
                    // fetches the fleet details for the particular customer
                    strFleetQuery.append("SELECT FLEET.FLEET_NUMBER,DPDFLEET.FLEET2FLEET,DPDFLEET.EXCLUDE");
                    strFleetQuery.append(" FROM GETS_TOOL_DPD_FLEET DPDFLEET,GETS_RMD_FLEET FLEET");
                    strFleetQuery.append(" WHERE DPDFLEET.FLEET2FLEET=FLEET.OBJID AND DPDFLEET.FLEET2CUST=:customerId");
                    Query fleetQry = hibSession.createSQLQuery(strFleetQuery.toString());
                    fleetQry.setParameter(RMDServiceConstants.CUSTOMER_ID,
                            RMDCommonUtility.convertObjectToString(objCustDetails[0]));
                    List<Object> arlFleetDetails = fleetQry.list();
                    if (null != arlFleetDetails && !arlFleetDetails.isEmpty()) {

                        for (Iterator<Object> fleetIterator = arlFleetDetails.iterator(); fleetIterator.hasNext();) {

                            Object objFleetDetails[] = (Object[]) fleetIterator.next();
                            objElementVO = new ElementVO();
                            objElementVO.setId(RMDCommonUtility.convertObjectToString(objFleetDetails[1]));
                            objElementVO.setName(RMDCommonUtility.convertObjectToString(objFleetDetails[0]));
                            objRuleDefinitionServiceVO
                                    .setStrExclude(RMDCommonUtility.convertObjectToString(objFleetDetails[2]));
                            arlRuleDefFleet.add(objElementVO);

                        }
                        objRuleDefCustomerServiceVO.setArlRuleDefFleet(arlRuleDefFleet);
                    }

                    arlRuleDefCustomerServiceVO.add(objRuleDefCustomerServiceVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlRuleDefCustomerServiceVO;

    }

    /**
     * This method is used for fetching the configuration details which contains
     * configparameter, value1,value2 and function values for the particular
     * Rule Definition
     * 
     * @param String
     *            strRuleDefId, String strLanguage
     * @return ArrayList<RuleDefConfigServiceVO>
     * @throws RMDDAOException
     */

    private ArrayList<RuleDefConfigServiceVO> getRuleDefConfigDetails(String strRuleDefId, String strLanguage) {
        Session hibSession = null;

        RuleDefConfigServiceVO objRuleDefConfigServiceVO;
        ArrayList<RuleDefConfigServiceVO> arlConfigServiceVO = null;

        try {
            hibSession = getHibernateSession();
            arlConfigServiceVO = new ArrayList<RuleDefConfigServiceVO>();
            StringBuilder strCfgQuery = new StringBuilder();
            strCfgQuery.append("SELECT A.CONFIG_ITEM,B.FCN,A.VALUE1,A.VALUE2");
            strCfgQuery.append(" FROM GETS_TOOL_DPD_CFGFEA A,GETS_TOOL_DPD_SIMFCN B");
            strCfgQuery.append(" WHERE A.CFGFEA2SIMFCN = B.OBJID AND A.CFGFEA2RULDEF =:ruleDefId");
            Query cfgDetailsQry = hibSession.createSQLQuery(strCfgQuery.toString());
            cfgDetailsQry.setParameter(RMDServiceConstants.RULEDEF_ID, strRuleDefId);
            List<Object> arlCfgDetails = cfgDetailsQry.list();
            if (null != arlCfgDetails && !arlCfgDetails.isEmpty()) {

                for (Iterator<Object> CfgIterator = arlCfgDetails.iterator(); CfgIterator.hasNext();) {

                    objRuleDefConfigServiceVO = new RuleDefConfigServiceVO();
                    Object objCfgDetails[] = (Object[]) CfgIterator.next();
                    objRuleDefConfigServiceVO
                            .setStrConfiguration(RMDCommonUtility.convertObjectToString(objCfgDetails[0]));
                    objRuleDefConfigServiceVO.setStrFunction(RMDCommonUtility.convertObjectToString(objCfgDetails[1]));
                    objRuleDefConfigServiceVO.setStrValue1(RMDCommonUtility.convertObjectToString(objCfgDetails[2]));
                    objRuleDefConfigServiceVO.setStrValue2(RMDCommonUtility.convertObjectToString(objCfgDetails[3]));
                    arlConfigServiceVO.add(objRuleDefConfigServiceVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlConfigServiceVO;

    }

    /**
     * This method is used for fetching the model details for the particular
     * rule definition.
     * 
     * @param String
     *            strRuleDefId, String strLanguage
     * @return ArrayList<RuleDefConfigServiceVO>
     * @throws RMDDAOException
     */
    private ArrayList<RuleDefModelServiceVO> getRuleDefModelDetails(String strRuleDefId, String strLanguage,
            String strFamily) {

        Session hibSession = null;
        RuleDefModelServiceVO objRuleDefModelServiceVO;
        ArrayList<RuleDefModelServiceVO> arlRuleDefModelServiceVO = null;
        try {
            hibSession = getHibernateSession();
            arlRuleDefModelServiceVO = new ArrayList<RuleDefModelServiceVO>();
            // for fetching the families which has models

            StringBuilder strModelQuery = new StringBuilder();
            strModelQuery.append(
                    "SELECT A.OBJID,A.MODEL_NAME FROM GETS_RMD_MODEL A ,GETS_TOOL_DPD_MODEL B WHERE B.MODEL2MODEL =  A.OBJID AND B.MODEL2RULEDEF =:ruleDefId");
            Query modelsQry = hibSession.createSQLQuery(strModelQuery.toString());
            modelsQry.setParameter(RMDServiceConstants.RULEDEF_ID, strRuleDefId);
            List<Object> arlModelDetails = modelsQry.list();
            // if the selected family contains the model in DB it
            // will fetch those models.
            // if the above query is giving no records,then in EOA
            // it means all the models are selected for the
            // family,so
            // we are retrieving all the models.For CCA we are
            // retrieving only the models related to ACCCA and DCCCA
            if (null != arlModelDetails && !arlModelDetails.isEmpty()) {

                for (Iterator<Object> modelIterator = arlModelDetails.iterator(); modelIterator.hasNext();) {

                    Object objModelDetails[] = (Object[]) modelIterator.next();
                    objRuleDefModelServiceVO = new RuleDefModelServiceVO();
                    objRuleDefModelServiceVO.setModelId(RMDCommonUtility.convertObjectToString(objModelDetails[0]));
                    objRuleDefModelServiceVO.setModelName(RMDCommonUtility.convertObjectToString(objModelDetails[1]));
                    arlRuleDefModelServiceVO.add(objRuleDefModelServiceVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlRuleDefModelServiceVO;

    }

    /**
     * This method is used for fetching the list of simple Rule Details for the
     * particular rule
     * 
     * @param String
     *            strRuleId, String strLanguage
     * @return ArrayList<SimpleRuleServiceVO>
     * @throws RMDDAOException
     */

	private ArrayList<SimpleRuleServiceVO> getSimpleRuleDetails(
			String strRuleId,String strFamily, String strLanguage,String defaultUom) {
        Session hibSession = null;
		List<String> strMpname=null;
        SimpleRuleServiceVO objSimpleRuleServiceVO;
        SimpleRuleClauseServiceVO objSimpleRuleClauseServiceVO;
        ArrayList<SimpleRuleServiceVO> arlSimpleRule = null;
        ArrayList<SimpleRuleClauseServiceVO> arlRuleClauseServiceVO;
		Map<String,UnitOfMeasureVO> mpUom = null;
        try {
            hibSession = getHibernateSession();
            arlSimpleRule = new ArrayList<SimpleRuleServiceVO>();
            StringBuilder strSimRulQry = new StringBuilder();
			strSimRulQry
					.append("SELECT SIMRUL.OBJID,SIMRUL.FAULT,SIMRUL.SUB_ID,SIMRUL.PIXELS,SIMRUL.CONTROLLER_ID,SIMRUL.NOTES FROM GETS_TOOL_DPD_SIMRUL SIMRUL WHERE SIMRUL.SIMRUL2FINRUL=:ruleId");
			Query simRuleQry = hibSession.createSQLQuery(strSimRulQry
					.toString());
            simRuleQry.setParameter(RMDServiceConstants.RULE_ID, strRuleId);
            List<Object> arlSimRuleQry = simRuleQry.list();
            if (null != arlSimRuleQry && !arlSimRuleQry.isEmpty()) {

					mpUom=convertSourceToTarget(defaultUom);
                for (Iterator<Object> arlSimRuleIterator = arlSimRuleQry.iterator(); arlSimRuleIterator.hasNext();) {
                    Object objSimRule[] = (Object[]) arlSimRuleIterator.next();
                    objSimpleRuleServiceVO = new SimpleRuleServiceVO();
                    String faultCode = RMDCommonUtility.convertObjectToString(objSimRule[1]);
                    objSimpleRuleServiceVO.setStrFaultCode(faultCode);
                    objSimpleRuleServiceVO.setStrSimpleRuleId(RMDCommonUtility.convertObjectToString(objSimRule[0]));
                    objSimpleRuleServiceVO.setStrSubID(RMDCommonUtility.convertObjectToString(objSimRule[2]));
                    objSimpleRuleServiceVO.setStrPixels(RMDCommonUtility.convertObjectToString(objSimRule[3]));
                    String controller_id = (objSimRule[4] == null) ? "-1"
                            : RMDCommonUtility.convertObjectToString(objSimRule[4]);
                    objSimpleRuleServiceVO.setStrControllerid(controller_id);
                    objSimpleRuleServiceVO.setNotes(RMDCommonUtility.convertObjectToString(objSimRule[5]));
                    if (null != faultCode) {
						List<FaultServiceStgyServiceVO> arlfaultDesc=objFaultServiceStgyDAOIntf.findFaultCode(strFamily, faultCode,false);
                        if (null != arlfaultDesc && !arlfaultDesc.isEmpty()) {
                            for (Iterator<FaultServiceStgyServiceVO> faultIterator = arlfaultDesc
                                    .iterator(); faultIterator.hasNext();) {

                                FaultServiceStgyServiceVO objFaultDesc = faultIterator.next();
                                objSimpleRuleServiceVO.setStrFaultDesc(objFaultDesc.getStrDescription());

                            }
                        }
                    }
                    // condition to load the Simple rule multifault details
                    else {
                        getMultifaultDetails(objSimpleRuleServiceVO, hibSession,
                                RMDCommonUtility.convertObjectToString(objSimRule[0]),strFamily);
                    }
                    arlRuleClauseServiceVO = new ArrayList<SimpleRuleClauseServiceVO>();
                    /*
                     * Below query is used for bringing the simple rule clause
                     * details.This query will fetch the details of anomaly,edp
                     * and normal rules.For Anomoly records,first column fetched
                     * from query will come with "ANOM" as the first string
                     * followed by anomolyId. For EDP records,first column
                     * fetched from query will come with "EDP" as the first
                     * string then followed by '&' and then the edp parameter
                     * name. For other records,table name will be the first
                     * string then followed by # and then the objid of the
                     * parameter and then column name after the '&' symbol
                     */
                    StringBuilder strSimRulClauseQry = new StringBuilder();
                    strSimRulClauseQry.append(
                            "SELECT DECODE(A.PARM_TYPE,'EDP','MP',A.PARM_TYPE) ||'#'  ||A.OBJID  ||'&'  ||A.COLUMN_NAME,  C.FCN,  B.VALUE1,  B.VALUE2,  B.SEQ_VALUE,  B.ANOM_TECHNIQUE, B.PIXELS , B.OBJID SIMFEAOBJID,B.NOTES");
                    strSimRulClauseQry
                            .append(" FROM GETS_TOOL_DPD_COLNAME A,GETS_TOOL_DPD_SIMFEA B, GETS_TOOL_DPD_SIMFCN C");
                    strSimRulClauseQry.append(
                            " WHERE A.OBJID       = B.SIMFEA2COLNAME AND C.OBJID = B.SIMFEA2SIMFCN AND B.SIMFEA2SIMRUL =:simRuleId");
                    strSimRulClauseQry.append(
                            " UNION SELECT 'EDP' ||'#'||(select OBJID from GETS_TOOL_DPD_COLNAME where COLUMN_NAME=A.EDP_PARMDEF AND PARM_TYPE='EDP' AND FAMILY=:Family) ||'&'  ||A.EDP_PARMDEF,  B.FCN,  A.VALUE1,  A.VALUE2,  A.SEQ_VALUE,  A.ANOM_TECHNIQUE, A.PIXELS,  A.OBJID SIMFEAOBJID,A.NOTES");
                    strSimRulClauseQry.append(" FROM GETS_TOOL_DPD_SIMFEA A,GETS_TOOL_DPD_SIMFCN B");
                    strSimRulClauseQry.append(
                            " WHERE B.OBJID          = A.SIMFEA2SIMFCN AND A.SIMFEA2SIMRUL    =:simRuleId AND A.SIMFEA2COLNAME   = -1 AND SIMFEA2ANOM_ADMIN IS NULL");
                    strSimRulClauseQry.append(
                            " UNION SELECT 'ANOM'  || A.SIMFEA2ANOM_ADMIN,  B.FCN,  A.VALUE1,  A.VALUE2,  A.SEQ_VALUE,  A.ANOM_TECHNIQUE, A.PIXELS, A.OBJID SIMFEAOBJID,A.NOTES");
                    strSimRulClauseQry.append(" FROM GETS_TOOL_DPD_SIMFEA A, GETS_TOOL_DPD_SIMFCN B");
                    strSimRulClauseQry.append(
                            " WHERE B.OBJID          = A.SIMFEA2SIMFCN AND A.SIMFEA2SIMRUL    =:simRuleId AND A.SIMFEA2COLNAME   = -1 AND SIMFEA2ANOM_ADMIN IS NOT NULL");
                    strSimRulClauseQry.append(" ORDER BY SIMFEAOBJID ASC");
                    Query simRulClauseQry = hibSession.createSQLQuery(strSimRulClauseQry.toString());
                    simRulClauseQry.setParameter(RMDServiceConstants.SIMPLE_RULE_ID,
                            RMDCommonUtility.convertObjectToString(objSimRule[0]));
                    simRulClauseQry.setParameter(RMDServiceConstants.FAMILY,
                            strFamily);
                    List<Object> arlSimRuleClause = simRulClauseQry.list();
                    if (null != arlSimRuleClause && !arlSimRuleClause.isEmpty()) {

                        for (Iterator<Object> simRuleIterator = arlSimRuleClause.iterator(); simRuleIterator
                                .hasNext();) {

                            Object objRuleClause[] = (Object[]) simRuleIterator.next();
                            objSimpleRuleClauseServiceVO = new SimpleRuleClauseServiceVO();
                            // following logic to convert number to string value
                            // as UI only needs string values Shift or Residual.
                            String anomTechique = RMDCommonUtility.convertObjectToString(objRuleClause[5]);
                            if (RMDCommonConstants.TWO.equalsIgnoreCase(anomTechique)) {
                                objSimpleRuleClauseServiceVO.setAnomTechniqueId("Shift");
                            } else {
                                objSimpleRuleClauseServiceVO.setAnomTechniqueId("Residual");
                            }

                            objSimpleRuleClauseServiceVO
                                    .setStrSeqVal(RMDCommonUtility.convertObjectToString(objRuleClause[4]));
                            objSimpleRuleClauseServiceVO
                                    .setStrFunction(RMDCommonUtility.convertObjectToString(objRuleClause[1]));
                            objSimpleRuleClauseServiceVO
                                    .setStrValue1(RMDCommonUtility.convertObjectToString(objRuleClause[2]));
                            objSimpleRuleClauseServiceVO
                                    .setStrValue2(RMDCommonUtility.convertObjectToString(objRuleClause[3]));
                            String strColumnName = RMDCommonUtility.convertObjectToString(objRuleClause[0]);
                            objSimpleRuleClauseServiceVO
                                    .setStrPixels(RMDCommonUtility.convertObjectToString(objRuleClause[6]));
                            objSimpleRuleClauseServiceVO
                                    .setStrClauseId(RMDCommonUtility.convertObjectToString(objRuleClause[7]));
                            // Added By Amuthan for Notes in Rule Module
                            objSimpleRuleClauseServiceVO
                                    .setNotes(RMDCommonUtility.convertObjectToString(objRuleClause[8]));

                            int colNameLength = (strColumnName.trim()).length();
                            String dpModel = (strColumnName.trim()).substring(0, 4);
                            // Fetching the records related to Anomoly
                            // rules by using the anomoly Id which is got from
                            // the above query
							if (RMDServiceConstants.ANOMOLY
									.equalsIgnoreCase(dpModel)) {
								objSimpleRuleClauseServiceVO
										.setStrColumnType(RMDServiceConstants.ANM);
								String anomID = (strColumnName.trim())
										.substring(4, colNameLength);
								objSimpleRuleClauseServiceVO
										.setStrColumnId(anomID);
								StringBuffer strAnomParamQry = new StringBuffer();
								strAnomParamQry
										.append("SELECT DISPLAY_PARM_NAME  ||DECODE(NVL(PARM_NUMBER,'xx'),'xx',' ',' - '  ||PARM_NUMBER) PARM");
								strAnomParamQry
										.append(" FROM GETS_TOOLS.GETS_TOOL_ANOM_ADMIN");
								strAnomParamQry
										.append(" WHERE OBJID = :anomolyId");
								Query anomParamQry = hibSession
										.createSQLQuery(strAnomParamQry
												.toString());
								anomParamQry.setParameter(
										RMDServiceConstants.ANOMOLY_ID, anomID);
								List<Object> arlAnomParam = anomParamQry.list();
								if (null != arlAnomParam
										&& !arlAnomParam.isEmpty()) {

									for (Iterator<Object> AnomParamIterator = arlAnomParam
											.iterator(); AnomParamIterator
											.hasNext();) {

										Object objAnom = (Object) AnomParamIterator
												.next();
										objSimpleRuleClauseServiceVO
												.setStrColumnName(RMDCommonUtility
														.convertObjectToString(objAnom));
									}
								}

							} else {
								String edpParameter = (strColumnName.trim())
										.substring(0, 3);

								String columnName = (strColumnName.trim())
										.substring(
												strColumnName
														.lastIndexOf(RMDServiceConstants.AMPERSAND) + 1,
														colNameLength);
								
								
								objSimpleRuleClauseServiceVO
										.setStrColumnName(columnName);

								if (RMDServiceConstants.EDP
										.equalsIgnoreCase(edpParameter)) {
									
									String colId = (strColumnName.trim())
											.substring(
													strColumnName
															.lastIndexOf(RMDServiceConstants.HASH_SYMBOL) + 1,
													strColumnName
															.lastIndexOf(RMDServiceConstants.AMPERSAND));
									objSimpleRuleClauseServiceVO
											.setStrColumnId(colId);
									
									objSimpleRuleClauseServiceVO
											.setStrColumnType(RMDServiceConstants.EDP);
								} else if (RMDServiceConstants.ATS
										.equalsIgnoreCase(edpParameter)) {
									String colId = (strColumnName.trim())
											.substring(
													strColumnName
															.lastIndexOf(RMDServiceConstants.HASH_SYMBOL) + 1,
													strColumnName
															.lastIndexOf(RMDServiceConstants.AMPERSAND));
									objSimpleRuleClauseServiceVO
											.setStrColumnType(RMDServiceConstants.ATS);
									objSimpleRuleClauseServiceVO
									.setStrColumnId(colId);
								}
								else if (RMDServiceConstants.GEO
											.equalsIgnoreCase(edpParameter)) {
									String colId = (strColumnName.trim())
											.substring(
													strColumnName
															.lastIndexOf(RMDServiceConstants.HASH_SYMBOL) + 1,
													strColumnName
															.lastIndexOf(RMDServiceConstants.AMPERSAND));
									String strGeoFenceDesc=RMDCommonConstants.EMPTY_STRING;
									StringBuffer geoBuffQry=new StringBuffer();
									geoBuffQry.append("SELECT DISTINCT PROXIMITY_DESC");
									geoBuffQry.append(" FROM GETS_MCS_PP_CALC_GEOFENCE_DEF GD,GETS_RMD_PP_PROXIMITY_DEF PD");
									geoBuffQry.append(" WHERE PPCALC_GEO_DEF2PROX_DEF = PD.OBJID AND GEOFENCE_ID=:geoFenceId");
									Query geoQry=hibSession.createSQLQuery(geoBuffQry.toString());
									geoQry.setParameter(RMDServiceConstants.GEOFENCE_ID, objSimpleRuleClauseServiceVO.getStrValue1());
									List<String> arlGeo = geoQry.list();
									for (Iterator iterator = arlGeo.iterator(); iterator
											.hasNext();) {
										strGeoFenceDesc = (String) iterator
												.next();
										
									}
										objSimpleRuleClauseServiceVO.setStrColumnName(strGeoFenceDesc);
										objSimpleRuleClauseServiceVO
												.setStrColumnType(RMDServiceConstants.GEO);
										objSimpleRuleClauseServiceVO
										.setStrColumnId(colId);
								}
								
								
								else {
									String colId = (strColumnName.trim())
											.substring(
													strColumnName
															.lastIndexOf(RMDServiceConstants.HASH_SYMBOL) + 1,
													strColumnName
															.lastIndexOf(RMDServiceConstants.AMPERSAND));
									objSimpleRuleClauseServiceVO
											.setStrColumnId(colId);
									StringBuffer sdpBuffQry=new StringBuffer();
									sdpBuffQry.append("SELECT COLNAME.COLUMN_NAME,CLMVAL.VAL");
									sdpBuffQry.append(" FROM GETS_TOOL_DPD_COLVAL CLMVAL,GETS_TOOL_DPD_COLNAME COLNAME");
									sdpBuffQry.append(" WHERE CLMVAL.COLVAL2COLNAME=COLNAME.OBJID AND COLNAME.COLUMN_NAME=:columnName");
									Query sdpQry=hibSession.createSQLQuery(sdpBuffQry.toString());
									sdpQry.setParameter(RMDServiceConstants.COLUMN_NAME, columnName);
									List<Object> arlSdp = sdpQry.list();
									if (null != arlSdp
											&& !arlSdp.isEmpty()) {
										objSimpleRuleClauseServiceVO
										.setStrColumnType(RMDServiceConstants.SDP);
									}else if (columnName
											.startsWith((RMDServiceConstants.V_))) {
										objSimpleRuleClauseServiceVO
												.setStrColumnType(RMDServiceConstants.VPM);
									} else {
										objSimpleRuleClauseServiceVO
												.setStrColumnType(RMDServiceConstants.ENG);
									}
								
									
								}
								
													
								//Get MP param for column Name
								strMpname=getMPName(hibSession,columnName,strFamily);
								if(null!=strMpname){
								objSimpleRuleClauseServiceVO.setStrParamName(strMpname.get(0));
								if(null!=defaultUom&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(defaultUom)&&!RMDCommonConstants.US.equalsIgnoreCase(defaultUom)){
									if(null!=strMpname.get(1)){
										UnitOfMeasureVO objUnitOfMeasureVO = mpUom.get(strMpname.get(1));
										if (null != objUnitOfMeasureVO&&RMDCommonConstants.ONE_STRING.equalsIgnoreCase(objUnitOfMeasureVO.getIsConversionRequired())) {
											String conversionFormula=objUnitOfMeasureVO.getConversionExp();
											objSimpleRuleClauseServiceVO.setStrValue1(AppSecUtil.convertMeasurementSystem(conversionFormula, objSimpleRuleClauseServiceVO.getStrValue1()));
											objSimpleRuleClauseServiceVO.setStrValue2(AppSecUtil.convertMeasurementSystem(conversionFormula, objSimpleRuleClauseServiceVO.getStrValue2()));
											objSimpleRuleClauseServiceVO.setUomAbbr(objUnitOfMeasureVO.getTargetAbbr());
									   }else{
										   objSimpleRuleClauseServiceVO.setUomAbbr(objUnitOfMeasureVO.getSourceAbbr());
									   }
									 }	
								  }else{
									  if(null!=strMpname.get(1)){
											UnitOfMeasureVO objUnitOfMeasureVO = mpUom.get(strMpname.get(1));
											if (null != objUnitOfMeasureVO) {												
												objSimpleRuleClauseServiceVO.setUomAbbr(objUnitOfMeasureVO.getSourceAbbr());
										   }
										 }	
								  }
								}
								
								
							}
							arlRuleClauseServiceVO
									.add(objSimpleRuleClauseServiceVO);

						}
					}
					objSimpleRuleServiceVO
							.setArlSimpleRuleClauseVO(arlRuleClauseServiceVO);

					arlSimpleRule.add(objSimpleRuleServiceVO);

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlSimpleRule;
    }

    /**
     * This method is used for fetching the list of complex Rule Details for the
     * particular rule
     * 
     * @param arlSimpleRule
     * @param String
     *            strRuleId, String strLanguage
     * @return ArrayList<SimpleRuleServiceVO>
     * @throws RMDDAOException
     */
	private ArrayList<ComplexRuleServiceVO> getComplexRuleDetails(
			String strRuleId, String strFamily,String strLanguage, ArrayList<SimpleRuleServiceVO> arlSimpleRule, String flgRORExplode,String defaultUom) {

        Session hibSession = null;
        ComplexRuleServiceVO objComplexRuleServiceVO;
        ArrayList<ComplexRuleServiceVO> arlComplexRule = null;

        try {
            hibSession = getHibernateSession();

            StringBuilder strComplexRulQry = new StringBuilder();
            strComplexRulQry.append(
                    "SELECT OBJID,TIME_WINDOW,FREQUENCY,COMPLEX_FUNCTION,RULE1_2SIMRUL,RULE1_2CMPRUL,RULE2_2SIMRUL,RULE2_2CMPRUL,RULE1_2FINRUL,RULE2_2FINRUL,PIXELS,RULE1_PIXELS,RULE2_PIXELS,SUB_TIME_WINDOW,SUB_WINDOW_GOAL,NOTES");
            strComplexRulQry.append(" FROM GETS_TOOL_DPD_CMPRUL WHERE CMPRUL2FINRUL =:ruleId");
            Query complexRuleQry = hibSession.createSQLQuery(strComplexRulQry.toString());
            complexRuleQry.setParameter(RMDServiceConstants.RULE_ID, strRuleId);
            List<Object> arlCmpRuleQry = complexRuleQry.list();
            HashMap<Long, SimpleRuleServiceVO> hmSimrules = prepareSimpleRuleIdMap(arlSimpleRule);
            ArrayList<Long> arlUsedSimRules = new ArrayList<Long>();
            ArrayList<String> arlUsedRoRules = new ArrayList<String>();
            if (null != arlCmpRuleQry && !arlCmpRuleQry.isEmpty()) {
                arlComplexRule = new ArrayList<ComplexRuleServiceVO>();
                long simRuleUniqueId = 1;
                long rorCounter = 1;
                for (Iterator<Object> CmpRuleIterator = arlCmpRuleQry.iterator(); CmpRuleIterator.hasNext();) {

                    Object objCmpRule[] = (Object[]) CmpRuleIterator.next();
                    objComplexRuleServiceVO = new ComplexRuleServiceVO();
                    String strTimeWindow = RMDCommonUtility.convertObjectToString(objCmpRule[1]);
                    objComplexRuleServiceVO.setStrTimeWindow(strTimeWindow);
                    objComplexRuleServiceVO.setStrFrequency(RMDCommonUtility.convertObjectToString(objCmpRule[2]));
                    objComplexRuleServiceVO.setStrFunction(RMDCommonUtility.convertObjectToString(objCmpRule[3]));
                    objComplexRuleServiceVO.setCsequenceID(RMDCommonUtility.convertObjectToLong(objCmpRule[0]));
                    objComplexRuleServiceVO.setStrPixels(RMDCommonUtility.convertObjectToString(objCmpRule[10]));
                    objComplexRuleServiceVO.setStrRule1Pixels(RMDCommonUtility.convertObjectToString(objCmpRule[11]));
                    objComplexRuleServiceVO.setStrRule2Pixels(RMDCommonUtility.convertObjectToString(objCmpRule[12]));
                    objComplexRuleServiceVO.setStrSubTimeWindow(RMDCommonUtility.convertObjectToString(objCmpRule[13]));
                    objComplexRuleServiceVO.setStrSubWindowGoal(RMDCommonUtility.convertObjectToString(objCmpRule[14]));
                    objComplexRuleServiceVO.setNotes(RMDCommonUtility.convertObjectToString(objCmpRule[15]));
                    // setting the time flag as H by default or if time
                    // window is positive else if it is negative time
                    // flag as P
                    if (Float.valueOf(strTimeWindow) < 0) {
						objComplexRuleServiceVO.setStrTimeWindow(strTimeWindow.replace("-", ""));
                        objComplexRuleServiceVO.setStrTimeFlag(RMDServiceConstants.STR_P);
                        objComplexRuleServiceVO.setBPT(true);
                        objComplexRuleServiceVO.setBHR(false);                        
                        objComplexRuleServiceVO.setStrTimeWindow(strTimeWindow.replace(RMDCommonConstants.HYPHEN,RMDCommonConstants.EMPTY_STRING));

                    } else if (Float.valueOf(strTimeWindow) > 0) {
                        objComplexRuleServiceVO.setStrTimeFlag(RMDServiceConstants.STR_H);
                        objComplexRuleServiceVO.setBPT(false);
                        objComplexRuleServiceVO.setBHR(true);

                    }
                    objComplexRuleServiceVO.setBMIN(false);
                    // setting the values for Rule1 and Rule2 in complex rule VO

                    // RULE1_2SIMRUL
                    Long simpleRule1 = RMDCommonUtility.convertObjectToLong(objCmpRule[4]);
                    if (simpleRule1 > 0) {
                        if (arlUsedSimRules.contains(simpleRule1)) {
                            SimpleRuleServiceVO tmpSimpleRuleServiceVO = hmSimrules.get(simpleRule1);
                            SimpleRuleServiceVO cpSimrule = createCopy(tmpSimpleRuleServiceVO, simRuleUniqueId);
                            arlSimpleRule.add(cpSimrule);
                            objComplexRuleServiceVO.setStrRule1(cpSimrule.getStrSimpleRuleId());
                            ++simRuleUniqueId;
                        } else {
                            arlUsedSimRules.add(simpleRule1);
                            objComplexRuleServiceVO.setStrRule1(RMDCommonUtility.convertObjectToString(objCmpRule[4]));
                        }

                        objComplexRuleServiceVO.setStrRule1Type(RMDServiceConstants.STR_S);

                    } else {
                        // RULE1_2CMPRUL
                        Long complexRule1 = RMDCommonUtility.convertObjectToLong(objCmpRule[5]);
                        if (complexRule1 > 0) {
                            objComplexRuleServiceVO.setStrRule1(RMDCommonUtility.convertObjectToString(objCmpRule[5]));
                            objComplexRuleServiceVO.setStrRule1Type(RMDServiceConstants.STR_C);
                        } else {

                            if (null != flgRORExplode && "Y".equalsIgnoreCase(flgRORExplode)) { // RULE1_2FINRUL
                                if (null != RMDCommonUtility.convertObjectToString(objCmpRule[8])
                                        && !"-1".equals(RMDCommonUtility.convertObjectToString(objCmpRule[8]))) {
                                    String topleveCmpRuleId = getTopLevelComplexRuleID(hibSession,
                                            RMDCommonUtility.convertObjectToString(objCmpRule[8]));
                                    objComplexRuleServiceVO
                                            .setStrRule1(RMDCommonUtility.convertObjectToString(topleveCmpRuleId));
                                    objComplexRuleServiceVO.setStrRule1Type(RMDServiceConstants.STR_C);
                                    // Modified to avoid the null pointer
                                    // exception if there is no simple rules
									ArrayList<SimpleRuleServiceVO> arlSimpleRuleNew = getSimpleRuleDetails(RMDCommonUtility
											.convertObjectToString(objCmpRule[8]),strFamily, strLanguage,defaultUom);
                                    if (null != arlSimpleRuleNew && !arlSimpleRuleNew.isEmpty()) {
                                        arlSimpleRule.addAll(arlSimpleRuleNew);
                                    }
                                    // Modified to avoid the null pointer
                                    // exception if there is no complex rules
									ArrayList<ComplexRuleServiceVO> arlComplexRuleNew = getComplexRuleDetails(RMDCommonUtility
											.convertObjectToString(objCmpRule[8]), strFamily,strLanguage, arlSimpleRule, flgRORExplode,defaultUom);
                                    if (null != arlComplexRuleNew && !arlComplexRuleNew.isEmpty()) {
                                        arlComplexRule.addAll(arlComplexRuleNew);
                                    }
                                }
                            } else {
                                String strRule1 = RMDCommonUtility.convertObjectToString(objCmpRule[8]);
                                if (arlUsedRoRules.contains(strRule1)) {
                                    objComplexRuleServiceVO.setStrRule1(strRule1 + "-" + rorCounter);
                                    ++rorCounter;
                                } else {
                                    arlUsedRoRules.add(strRule1);
                                    objComplexRuleServiceVO
                                            .setStrRule1(RMDCommonUtility.convertObjectToString(objCmpRule[8]));
                                }
                                objComplexRuleServiceVO.setStrRule1Type(RMDServiceConstants.STR_F);
                            }
                        }
                    }
                    // RULE2_2SIMRUL
                    Long simpleRule2 = RMDCommonUtility.convertObjectToLong(objCmpRule[6]);
                    if (simpleRule2 > 0) {
                        if (arlUsedSimRules.contains(simpleRule2)) {
                            SimpleRuleServiceVO tmpSimpleRuleServiceVO = hmSimrules.get(simpleRule2);
                            SimpleRuleServiceVO cpSimrule = createCopy(tmpSimpleRuleServiceVO, simRuleUniqueId);
                            arlSimpleRule.add(cpSimrule);
                            objComplexRuleServiceVO.setStrRule2(cpSimrule.getStrSimpleRuleId());
                            ++simRuleUniqueId;
                        } else {
                            arlUsedSimRules.add(simpleRule2);
                            objComplexRuleServiceVO.setStrRule2(RMDCommonUtility.convertObjectToString(objCmpRule[6]));
                        }

                        objComplexRuleServiceVO.setStrRule2Type(RMDServiceConstants.STR_S);
                    } else {
                        // RULE2_2CMPRUL
                        Long complexRule2 = RMDCommonUtility.convertObjectToLong(objCmpRule[7]);
                        if (complexRule2 > 0) {
                            objComplexRuleServiceVO.setStrRule2(RMDCommonUtility.convertObjectToString(objCmpRule[7]));
                            objComplexRuleServiceVO.setStrRule2Type(RMDServiceConstants.STR_C);
                        } else {
                            if (null != flgRORExplode && "Y".equalsIgnoreCase(flgRORExplode)) { // RULE2_2FINRUL
                                if (null != RMDCommonUtility.convertObjectToString(objCmpRule[9])
                                        && !"-1".equals(RMDCommonUtility.convertObjectToString(objCmpRule[9]))) {
                                    String topleveCmpRuleId = getTopLevelComplexRuleID(hibSession,
                                            RMDCommonUtility.convertObjectToString(objCmpRule[9]));
                                    objComplexRuleServiceVO
                                            .setStrRule2(RMDCommonUtility.convertObjectToString(topleveCmpRuleId));
                                    objComplexRuleServiceVO.setStrRule2Type(RMDServiceConstants.STR_C);
                                    // Modified to avoid the null pointer
                                    // exception if there is no simple rules
									ArrayList<SimpleRuleServiceVO> arlSimpleRuleNew = getSimpleRuleDetails(RMDCommonUtility
											.convertObjectToString(objCmpRule[9]),strFamily, strLanguage,defaultUom);
                                            
                                    if (null != arlSimpleRuleNew && !arlSimpleRuleNew.isEmpty()) {
                                        arlSimpleRule.addAll(arlSimpleRuleNew);
                                    }
                                    // Modified to avoid the null pointer
                                    // exception if there is no complex rules
									ArrayList<ComplexRuleServiceVO> arlComplexRuleNew = getComplexRuleDetails(RMDCommonUtility
											.convertObjectToString(objCmpRule[9]), strFamily,strLanguage, arlSimpleRule, flgRORExplode,defaultUom); 
                                    if (null != arlComplexRuleNew && !arlComplexRuleNew.isEmpty()) {
                                        arlComplexRule.addAll(arlComplexRuleNew);
                                    }
                                }
                            } else {
                                String strRule2 = RMDCommonUtility.convertObjectToString(objCmpRule[9]);
                                if (arlUsedRoRules.contains(strRule2)) {
                                    objComplexRuleServiceVO.setStrRule2(
                                            RMDCommonUtility.convertObjectToString(strRule2 + "-" + rorCounter));
                                    ++rorCounter;
                                } else {
                                    arlUsedRoRules.add(strRule2);
                                    objComplexRuleServiceVO
                                            .setStrRule2(RMDCommonUtility.convertObjectToString(objCmpRule[9]));
                                }
                                objComplexRuleServiceVO.setStrRule2Type(RMDServiceConstants.STR_F);
                            }
                        }
                    }
                    arlComplexRule.add(objComplexRuleServiceVO);
                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlComplexRule;
    }

    /**
     * This method is used for fetching the list of simple Rule Details for the
     * particular rule
     * 
     * @param String
     *            strRuleId, String strLanguage
     * @return ArrayList<SimpleRuleServiceVO>
     * @throws RMDDAOException
     */

    private ArrayList<SimpleRuleServiceVO> getClearingLogicSimpleRuleDetails(String strClearingLogicSimpleRuleId,
            String strFamily, String strLanguage) {
        Session hibSession = null;
		List<String> strMpname=null;
        SimpleRuleServiceVO objSimpleRuleServiceVO;
        SimpleRuleClauseServiceVO objSimpleRuleClauseServiceVO;
        ArrayList<SimpleRuleServiceVO> arlSimpleRule = null;
        ArrayList<SimpleRuleClauseServiceVO> arlRuleClauseServiceVO;
        try {
            hibSession = getHibernateSession();
            arlSimpleRule = new ArrayList<SimpleRuleServiceVO>();
            StringBuilder strSimRulQry = new StringBuilder();
            strSimRulQry.append("SELECT OBJID,FAULT,SUB_ID,PIXELS,NOTES FROM GETS_TOOL_DPD_SIMRUL WHERE OBJID=:ruleId");
            Query simRuleQry = hibSession.createSQLQuery(strSimRulQry.toString());
            simRuleQry.setParameter(RMDServiceConstants.RULE_ID, strClearingLogicSimpleRuleId);
            List<Object> arlSimRuleQry = simRuleQry.list();
            if (null != arlSimRuleQry && !arlSimRuleQry.isEmpty()) {
                for (Iterator<Object> arlSimRuleIterator = arlSimRuleQry.iterator(); arlSimRuleIterator.hasNext();) {
                    Object objSimRule[] = (Object[]) arlSimRuleIterator.next();
                    objSimpleRuleServiceVO = new SimpleRuleServiceVO();
                    String faultCode = RMDCommonUtility.convertObjectToString(objSimRule[1]);
                    objSimpleRuleServiceVO.setStrFaultCode(faultCode);
                    objSimpleRuleServiceVO.setStrSimpleRuleId(RMDCommonUtility.convertObjectToString(objSimRule[0]));
                    objSimpleRuleServiceVO.setStrSubID(RMDCommonUtility.convertObjectToString(objSimRule[2]));
                    objSimpleRuleServiceVO.setStrPixels(RMDCommonUtility.convertObjectToString(objSimRule[3]));
                    objSimpleRuleServiceVO.setNotes(RMDCommonUtility.convertObjectToString(objSimRule[4]));
                    if (null != faultCode) {
                        StringBuilder strFaultDescQry = new StringBuilder();
                        strFaultDescQry.append(
                                "SELECT FAULTCODE.FAULT_DESC FROM GETS_RMD_FAULT_CODES FAULTCODE WHERE FAULTCODE.FAULT_CODE=:faultCode");
                        Query faultDescQry = hibSession.createSQLQuery(strFaultDescQry.toString());
                        faultDescQry.setParameter(RMDServiceConstants.FAULT_CODE, faultCode);
                        List<Object> arlfaultDesc = faultDescQry.list();
                        if (null != arlfaultDesc && !arlfaultDesc.isEmpty()) {
                            for (Iterator<Object> faultIterator = arlfaultDesc.iterator(); faultIterator.hasNext();) {

                                Object objFaultDesc = faultIterator.next();
                                objSimpleRuleServiceVO
                                        .setStrFaultDesc(RMDCommonUtility.convertObjectToString(objFaultDesc));

                            }
                        }
                    }
                    // condition to load the Simple rule multifault details
                    else {
                        getMultifaultDetails(objSimpleRuleServiceVO, hibSession,
                                RMDCommonUtility.convertObjectToString(objSimRule[0]),strFamily);
                    }
                    arlRuleClauseServiceVO = new ArrayList<SimpleRuleClauseServiceVO>();
                    /*
                     * Below query is used for bringing the simple rule clause
                     * details.This query will fetch the details of anomaly,edp
                     * and normal rules.For Anomoly records,first column fetched
                     * from query will come with "ANOM" as the first string
                     * followed by anomolyId. For EDP records,first column
                     * fetched from query will come with "EDP" as the first
                     * string then followed by '&' and then the edp parameter
                     * name. For other records,table name will be the first
                     * string then followed by # and then the objid of the
                     * parameter and then column name after the '&' symbol
                     */
                    StringBuilder strSimRulClauseQry = new StringBuilder();
                    strSimRulClauseQry.append(
                            "SELECT A.PARM_TYPE  ||'#'  ||A.OBJID  ||'&'  ||A.COLUMN_NAME,  C.FCN,  B.VALUE1,  B.VALUE2,  B.SEQ_VALUE,  B.ANOM_TECHNIQUE, B.PIXELS , B.OBJID SIMFEAOBJID,B.NOTES");
                    strSimRulClauseQry
                            .append(" FROM GETS_TOOL_DPD_COLNAME A,GETS_TOOL_DPD_SIMFEA B, GETS_TOOL_DPD_SIMFCN C");
                    strSimRulClauseQry.append(
                            " WHERE A.OBJID       = B.SIMFEA2COLNAME AND C.OBJID = B.SIMFEA2SIMFCN AND B.SIMFEA2SIMRUL =:simRuleId");
                    strSimRulClauseQry.append(
                            " UNION SELECT 'EDP'  ||'#'||(select OBJID from GETS_TOOL_DPD_COLNAME where COLUMN_NAME=A.EDP_PARMDEF AND PARM_TYPE='EDP' AND FAMILY=:Family) ||'&'  ||A.EDP_PARMDEF,  B.FCN,  A.VALUE1,  A.VALUE2,  A.SEQ_VALUE,  A.ANOM_TECHNIQUE, A.PIXELS,  A.OBJID SIMFEAOBJID,A.NOTES");
                    strSimRulClauseQry.append(" FROM GETS_TOOL_DPD_SIMFEA A,GETS_TOOL_DPD_SIMFCN B");
                    strSimRulClauseQry.append(
                            " WHERE B.OBJID          = A.SIMFEA2SIMFCN AND A.SIMFEA2SIMRUL    =:simRuleId AND A.SIMFEA2COLNAME   = -1 AND SIMFEA2ANOM_ADMIN IS NULL");
                    strSimRulClauseQry.append(
                            " UNION SELECT 'ANOM'  || A.SIMFEA2ANOM_ADMIN,  B.FCN,  A.VALUE1,  A.VALUE2,  A.SEQ_VALUE,  A.ANOM_TECHNIQUE, A.PIXELS, A.OBJID SIMFEAOBJID,A.NOTES");
                    strSimRulClauseQry.append(" FROM GETS_TOOL_DPD_SIMFEA A, GETS_TOOL_DPD_SIMFCN B");
                    strSimRulClauseQry.append(
                            " WHERE B.OBJID          = A.SIMFEA2SIMFCN AND A.SIMFEA2SIMRUL    =:simRuleId AND A.SIMFEA2COLNAME   = -1 AND SIMFEA2ANOM_ADMIN IS NOT NULL");
                    strSimRulClauseQry.append(" ORDER BY SIMFEAOBJID ASC");
                    Query simRulClauseQry = hibSession.createSQLQuery(strSimRulClauseQry.toString());
                    simRulClauseQry.setParameter(RMDServiceConstants.SIMPLE_RULE_ID,
                            RMDCommonUtility.convertObjectToString(objSimRule[0]));
                    simRulClauseQry.setParameter(RMDServiceConstants.FAMILY,
                            strFamily);
                    List<Object> arlSimRuleClause = simRulClauseQry.list();
                    if (null != arlSimRuleClause && !arlSimRuleClause.isEmpty()) {

                        for (Iterator<Object> simRuleIterator = arlSimRuleClause.iterator(); simRuleIterator
                                .hasNext();) {

                            Object objRuleClause[] = (Object[]) simRuleIterator.next();
                            objSimpleRuleClauseServiceVO = new SimpleRuleClauseServiceVO();
                            // following logic to convert number to string value
                            // as UI only needs string values Shift or Residual.
                            String anomTechique = RMDCommonUtility.convertObjectToString(objRuleClause[5]);
                            if (RMDCommonConstants.TWO.equalsIgnoreCase(anomTechique)) {
                                objSimpleRuleClauseServiceVO.setAnomTechniqueId("Shift");
                            } else {
                                objSimpleRuleClauseServiceVO.setAnomTechniqueId("Residual");
                            }

                            objSimpleRuleClauseServiceVO
                                    .setStrSeqVal(RMDCommonUtility.convertObjectToString(objRuleClause[4]));
                            objSimpleRuleClauseServiceVO
                                    .setStrFunction(RMDCommonUtility.convertObjectToString(objRuleClause[1]));
                            objSimpleRuleClauseServiceVO
                                    .setStrValue1(RMDCommonUtility.convertObjectToString(objRuleClause[2]));
                            objSimpleRuleClauseServiceVO
                                    .setStrValue2(RMDCommonUtility.convertObjectToString(objRuleClause[3]));
                            String strColumnName = RMDCommonUtility.convertObjectToString(objRuleClause[0]);
                            objSimpleRuleClauseServiceVO
                                    .setStrPixels(RMDCommonUtility.convertObjectToString(objRuleClause[6]));
                            objSimpleRuleClauseServiceVO
                                    .setStrClauseId(RMDCommonUtility.convertObjectToString(objRuleClause[7]));
                            objSimpleRuleClauseServiceVO
                                    .setNotes(RMDCommonUtility.convertObjectToString(objRuleClause[8]));
                            int colNameLength = (strColumnName.trim()).length();
                            String dpModel = (strColumnName.trim()).substring(0, 4);
                            // Fetching the records related to Anomoly
                            // rules by using the anomoly Id which is got from
                            // the above query
                            if (RMDServiceConstants.ANOMOLY.equalsIgnoreCase(dpModel)) {
                                objSimpleRuleClauseServiceVO.setStrColumnType(RMDServiceConstants.ANM);
                                String anomID = (strColumnName.trim()).substring(4, colNameLength);
                                objSimpleRuleClauseServiceVO.setStrColumnId(anomID);
                                StringBuilder strAnomParamQry = new StringBuilder();
                                strAnomParamQry.append(
                                        "SELECT DISPLAY_PARM_NAME  ||DECODE(NVL(PARM_NUMBER,'xx'),'xx',' ',' - '  ||PARM_NUMBER) PARM");
                                strAnomParamQry.append(" FROM GETS_TOOLS.GETS_TOOL_ANOM_ADMIN");
                                strAnomParamQry.append(" WHERE OBJID = :anomolyId");
                                Query anomParamQry = hibSession.createSQLQuery(strAnomParamQry.toString());
                                anomParamQry.setParameter(RMDServiceConstants.ANOMOLY_ID, anomID);
                                List<Object> arlAnomParam = anomParamQry.list();
                                if (null != arlAnomParam && !arlAnomParam.isEmpty()) {

                                    for (Iterator<Object> AnomParamIterator = arlAnomParam.iterator(); AnomParamIterator
                                            .hasNext();) {

                                        Object objAnom = AnomParamIterator.next();
                                        objSimpleRuleClauseServiceVO
                                                .setStrColumnName(RMDCommonUtility.convertObjectToString(objAnom));
                                    }
                                }

                            } else {
                                String edpParameter = (strColumnName.trim()).substring(0, 3);

                                String columnName = (strColumnName.trim()).substring(
                                        strColumnName.lastIndexOf(RMDServiceConstants.AMPERSAND) + 1,
                                        strColumnName.length());
                                objSimpleRuleClauseServiceVO.setStrColumnName(columnName);

                                if (RMDServiceConstants.EDP.equalsIgnoreCase(edpParameter)) {
                                	String colId = (strColumnName.trim()).substring(
                                            strColumnName.lastIndexOf(RMDServiceConstants.HASH_SYMBOL) + 1,
                                            strColumnName.lastIndexOf(RMDServiceConstants.AMPERSAND));
                                    objSimpleRuleClauseServiceVO.setStrColumnId(colId);
                                    objSimpleRuleClauseServiceVO.setStrColumnType(RMDServiceConstants.EDP);
                                } else {
                                    String colId = (strColumnName.trim()).substring(
                                            strColumnName.lastIndexOf(RMDServiceConstants.HASH_SYMBOL) + 1,
                                            strColumnName.lastIndexOf(RMDServiceConstants.AMPERSAND));
                                    objSimpleRuleClauseServiceVO.setStrColumnId(colId);
                                    StringBuilder sdpBuffQry = new StringBuilder();
                                    sdpBuffQry.append("SELECT COLNAME.COLUMN_NAME,CLMVAL.VAL");
                                    sdpBuffQry
                                            .append(" FROM GETS_TOOL_DPD_COLVAL CLMVAL,GETS_TOOL_DPD_COLNAME COLNAME");
                                    sdpBuffQry.append(
                                            " WHERE CLMVAL.COLVAL2COLNAME=COLNAME.OBJID AND COLNAME.COLUMN_NAME=:columnName");
                                    Query sdpQry = hibSession.createSQLQuery(sdpBuffQry.toString());
                                    sdpQry.setParameter(RMDServiceConstants.COLUMN_NAME, columnName);
                                    List<Object> arlSdp = sdpQry.list();
                                    if (null != arlSdp && !arlSdp.isEmpty()) {
                                        objSimpleRuleClauseServiceVO.setStrColumnType(RMDServiceConstants.SDP);
                                    } else if (columnName.startsWith((RMDServiceConstants.V_))) {
                                        objSimpleRuleClauseServiceVO.setStrColumnType(RMDServiceConstants.VPM);
                                    } else {
                                        objSimpleRuleClauseServiceVO.setStrColumnType(RMDServiceConstants.ENG);
                                    }

                                }
                                // Get MP param for column Name
								strMpname=getMPName(hibSession,columnName,strFamily);
								if(null!=strMpname){
								objSimpleRuleClauseServiceVO.setStrParamName(strMpname.get(0));
								}
								
							}
                            arlRuleClauseServiceVO.add(objSimpleRuleClauseServiceVO);

                        }
                    }
                    objSimpleRuleServiceVO.setArlSimpleRuleClauseVO(arlRuleClauseServiceVO);

                    arlSimpleRule.add(objSimpleRuleServiceVO);

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlSimpleRule;
    }

    /**
     * This method is used for fetching the list of complex Rule Details for the
     * particular rule
     * 
     * @param arlSimpleRule
     * @param String
     *            strRuleId, String strLanguage
     * @return ArrayList<SimpleRuleServiceVO>
     * @throws RMDDAOException
     */
    private ArrayList<ComplexRuleServiceVO> getClearingLogicComplexRuleDetails(String strClearingLogicComplexRuleId,
            String strFamily, String strLanguage, ArrayList<SimpleRuleServiceVO> arlSimpleRule, String flgRORExplode) {

        Session hibSession = null;
        ComplexRuleServiceVO objComplexRuleServiceVO;
        ArrayList<ComplexRuleServiceVO> arlComplexRule = null;

        try {
            hibSession = getHibernateSession();

            StringBuilder strComplexRulQry = new StringBuilder();
            strComplexRulQry.append(
                    "SELECT OBJID,TIME_WINDOW,FREQUENCY,COMPLEX_FUNCTION,RULE1_2SIMRUL,RULE1_2CMPRUL,RULE2_2SIMRUL,RULE2_2CMPRUL,RULE1_2FINRUL,RULE2_2FINRUL,PIXELS,RULE1_PIXELS,RULE2_PIXELS,SUB_TIME_WINDOW,SUB_WINDOW_GOAL,NOTES");
            strComplexRulQry.append(" FROM GETS_TOOL_DPD_CMPRUL WHERE OBJID =:ruleId");
            Query complexRuleQry = hibSession.createSQLQuery(strComplexRulQry.toString());
            complexRuleQry.setParameter(RMDServiceConstants.RULE_ID, strClearingLogicComplexRuleId);
            List<Object> arlCmpRuleQry = complexRuleQry.list();
            HashMap<Long, SimpleRuleServiceVO> hmSimrules = prepareSimpleRuleIdMap(arlSimpleRule);
            ArrayList<Long> arlUsedSimRules = new ArrayList<Long>();
            ArrayList<String> arlUsedRoRules = new ArrayList<String>();
            if (null != arlCmpRuleQry && !arlCmpRuleQry.isEmpty()) {
                arlComplexRule = new ArrayList<ComplexRuleServiceVO>();
                long simRuleUniqueId = 1;
                long rorCounter = 1;
                for (Iterator<Object> CmpRuleIterator = arlCmpRuleQry.iterator(); CmpRuleIterator.hasNext();) {

                    Object objCmpRule[] = (Object[]) CmpRuleIterator.next();
                    objComplexRuleServiceVO = new ComplexRuleServiceVO();
                    String strTimeWindow = RMDCommonUtility.convertObjectToString(objCmpRule[1]);
                    objComplexRuleServiceVO.setStrTimeWindow(strTimeWindow);
                    objComplexRuleServiceVO.setStrFrequency(RMDCommonUtility.convertObjectToString(objCmpRule[2]));
                    objComplexRuleServiceVO.setStrFunction(RMDCommonUtility.convertObjectToString(objCmpRule[3]));
                    objComplexRuleServiceVO.setCsequenceID(RMDCommonUtility.convertObjectToLong(objCmpRule[0]));
                    objComplexRuleServiceVO.setStrPixels(RMDCommonUtility.convertObjectToString(objCmpRule[10]));
                    objComplexRuleServiceVO.setStrRule1Pixels(RMDCommonUtility.convertObjectToString(objCmpRule[11]));
                    objComplexRuleServiceVO.setStrRule2Pixels(RMDCommonUtility.convertObjectToString(objCmpRule[12]));
                    objComplexRuleServiceVO.setStrSubTimeWindow(RMDCommonUtility.convertObjectToString(objCmpRule[13]));
                    objComplexRuleServiceVO.setStrSubWindowGoal(RMDCommonUtility.convertObjectToString(objCmpRule[14]));
                    objComplexRuleServiceVO.setNotes(RMDCommonUtility.convertObjectToString(objCmpRule[15]));
                    // setting the time flag as H by default or if time
                    // window is positive else if it is negative time
                    // flag as P
                    if (Float.valueOf(strTimeWindow) < 0) {
						objComplexRuleServiceVO.setStrTimeWindow(strTimeWindow.replace("-", ""));
                        objComplexRuleServiceVO.setStrTimeFlag(RMDServiceConstants.STR_P);
                        objComplexRuleServiceVO.setBPT(true);
                        objComplexRuleServiceVO.setBHR(false);

                    } else if (Float.valueOf(strTimeWindow) > 0) {
                        objComplexRuleServiceVO.setStrTimeFlag(RMDServiceConstants.STR_H);
                        objComplexRuleServiceVO.setBPT(false);
                        objComplexRuleServiceVO.setBHR(true);

                    }
                    objComplexRuleServiceVO.setBMIN(false);
                    // setting the values for Rule1 and Rule2 in complex rule VO

                    // RULE1_2SIMRUL
                    Long simpleRule1 = RMDCommonUtility.convertObjectToLong(objCmpRule[4]);
                    if (simpleRule1 > 0) {
                        if (arlUsedSimRules.contains(simpleRule1)) {
                            SimpleRuleServiceVO tmpSimpleRuleServiceVO = hmSimrules.get(simpleRule1);
                            SimpleRuleServiceVO cpSimrule = createCopy(tmpSimpleRuleServiceVO, simRuleUniqueId);
                            arlSimpleRule.add(cpSimrule);
                            objComplexRuleServiceVO.setStrRule1(cpSimrule.getStrSimpleRuleId());
                            ++simRuleUniqueId;
                        } else {
                            arlUsedSimRules.add(simpleRule1);
                            objComplexRuleServiceVO.setStrRule1(RMDCommonUtility.convertObjectToString(objCmpRule[4]));
                        }

                        objComplexRuleServiceVO.setStrRule1Type(RMDServiceConstants.STR_S);

                    } else {
                        // RULE1_2CMPRUL
                        Long complexRule1 = RMDCommonUtility.convertObjectToLong(objCmpRule[5]);
                        if (complexRule1 > 0) {
                            objComplexRuleServiceVO.setStrRule1(RMDCommonUtility.convertObjectToString(objCmpRule[5]));
                            objComplexRuleServiceVO.setStrRule1Type(RMDServiceConstants.STR_C);
                        } else {

                            if (null != flgRORExplode && "Y".equalsIgnoreCase(flgRORExplode)) { // RULE1_2FINRUL
                                if (null != RMDCommonUtility.convertObjectToString(objCmpRule[8])
                                        && !"-1".equals(RMDCommonUtility.convertObjectToString(objCmpRule[8]))) {
                                    String topleveCmpRuleId = getTopLevelComplexRuleID(hibSession,
                                            RMDCommonUtility.convertObjectToString(objCmpRule[8]));
                                    objComplexRuleServiceVO
                                            .setStrRule1(RMDCommonUtility.convertObjectToString(topleveCmpRuleId));
                                    objComplexRuleServiceVO.setStrRule1Type(RMDServiceConstants.STR_C);
                                    // Modified to avoid the null pointer
                                    // exception if there is no simple rules
                                    ArrayList<SimpleRuleServiceVO> arlSimpleRuleNew = getClearingLogicSimpleRuleDetails(
                                            RMDCommonUtility.convertObjectToString(objCmpRule[4]), strFamily,
                                            strLanguage);
                                    if (null != arlSimpleRuleNew && !arlSimpleRuleNew.isEmpty()) {
                                        arlSimpleRule.addAll(arlSimpleRuleNew);
                                    }
                                    // Modified to avoid the null pointer
                                    // exception if there is no complex rules
                                    ArrayList<ComplexRuleServiceVO> arlComplexRuleNew = getClearingLogicComplexRuleDetails(
                                            RMDCommonUtility.convertObjectToString(objCmpRule[5]), strFamily,
                                            strLanguage, arlSimpleRule, flgRORExplode);
                                    if (null != arlComplexRuleNew && !arlComplexRuleNew.isEmpty()) {
                                        arlComplexRule.addAll(arlComplexRuleNew);
                                    }
                                }
                            } else {
                                String strRule1 = RMDCommonUtility.convertObjectToString(objCmpRule[8]);
                                if (arlUsedRoRules.contains(strRule1)) {
                                    objComplexRuleServiceVO.setStrRule1(strRule1 + "-" + rorCounter);
                                    ++rorCounter;
                                } else {
                                    arlUsedRoRules.add(strRule1);
                                    objComplexRuleServiceVO
                                            .setStrRule1(RMDCommonUtility.convertObjectToString(objCmpRule[8]));
                                }
                                objComplexRuleServiceVO.setStrRule1Type(RMDServiceConstants.STR_F);
                            }
                        }
                    }
                    // RULE2_2SIMRUL
                    Long simpleRule2 = RMDCommonUtility.convertObjectToLong(objCmpRule[6]);
                    if (simpleRule2 > 0) {
                        if (arlUsedSimRules.contains(simpleRule2)) {
                            SimpleRuleServiceVO tmpSimpleRuleServiceVO = hmSimrules.get(simpleRule2);
                            SimpleRuleServiceVO cpSimrule = createCopy(tmpSimpleRuleServiceVO, simRuleUniqueId);
                            arlSimpleRule.add(cpSimrule);
                            objComplexRuleServiceVO.setStrRule2(cpSimrule.getStrSimpleRuleId());
                            ++simRuleUniqueId;
                        } else {
                            arlUsedSimRules.add(simpleRule2);
                            objComplexRuleServiceVO.setStrRule2(RMDCommonUtility.convertObjectToString(objCmpRule[6]));
                        }

                        objComplexRuleServiceVO.setStrRule2Type(RMDServiceConstants.STR_S);
                    } else {
                        // RULE2_2CMPRUL
                        Long complexRule2 = RMDCommonUtility.convertObjectToLong(objCmpRule[7]);
                        if (complexRule2 > 0) {
                            objComplexRuleServiceVO.setStrRule2(RMDCommonUtility.convertObjectToString(objCmpRule[7]));
                            objComplexRuleServiceVO.setStrRule2Type(RMDServiceConstants.STR_C);
                        } else {
                            if (null != flgRORExplode && "Y".equalsIgnoreCase(flgRORExplode)) { // RULE2_2FINRUL
                                if (null != RMDCommonUtility.convertObjectToString(objCmpRule[9])
                                        && !"-1".equals(RMDCommonUtility.convertObjectToString(objCmpRule[9]))) {
                                    String topleveCmpRuleId = getTopLevelComplexRuleID(hibSession,
                                            RMDCommonUtility.convertObjectToString(objCmpRule[9]));
                                    objComplexRuleServiceVO
                                            .setStrRule2(RMDCommonUtility.convertObjectToString(topleveCmpRuleId));
                                    objComplexRuleServiceVO.setStrRule2Type(RMDServiceConstants.STR_C);
                                    // Modified to avoid the null pointer
                                    // exception if there is no simple rules
                                    ArrayList<SimpleRuleServiceVO> arlSimpleRuleNew = getClearingLogicSimpleRuleDetails(
                                            RMDCommonUtility.convertObjectToString(objCmpRule[6]), strFamily,
                                            strLanguage);
                                    if (null != arlSimpleRuleNew && !arlSimpleRuleNew.isEmpty()) {
                                        arlSimpleRule.addAll(arlSimpleRuleNew);
                                    }
                                    // Modified to avoid the null pointer
                                    // exception if there is no complex rules
                                    ArrayList<ComplexRuleServiceVO> arlComplexRuleNew = getClearingLogicComplexRuleDetails(
                                            RMDCommonUtility.convertObjectToString(objCmpRule[7]), strFamily,
                                            strLanguage, arlSimpleRule, flgRORExplode);
                                    if (null != arlComplexRuleNew && !arlComplexRuleNew.isEmpty()) {
                                        arlComplexRule.addAll(arlComplexRuleNew);
                                    }
                                }
                            } else {
                                String strRule2 = RMDCommonUtility.convertObjectToString(objCmpRule[9]);
                                if (arlUsedRoRules.contains(strRule2)) {
                                    objComplexRuleServiceVO.setStrRule2(
                                            RMDCommonUtility.convertObjectToString(strRule2 + "-" + rorCounter));
                                    ++rorCounter;
                                } else {
                                    arlUsedRoRules.add(strRule2);
                                    objComplexRuleServiceVO
                                            .setStrRule2(RMDCommonUtility.convertObjectToString(objCmpRule[9]));
                                }
                                objComplexRuleServiceVO.setStrRule2Type(RMDServiceConstants.STR_F);
                            }
                        }
                    }
                    arlComplexRule.add(objComplexRuleServiceVO);
                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlComplexRule;
    }

    /**
     * This method is used for fetching the history details of the particular
     * rule by passing the original rule Id
     * 
     * @param String
     *            originalId, String strLanguage
     * @return ArrayList<SimpleRuleServiceVO>
     * @throws RMDDAOException
     */
    private ArrayList<RuleHistoryServiceVO> getRuleHistoryDetails(String originalId, String strLanguage) {

        Session hibSession = null;
        RuleHistoryServiceVO objRuleHistoryServiceVO;
        ArrayList<RuleHistoryServiceVO> arlRuleHistoryServiceVO = null;

        try {
            hibSession = getHibernateSession();
            arlRuleHistoryServiceVO = new ArrayList<RuleHistoryServiceVO>();
            StringBuilder strRulHisQry = new StringBuilder();
            strRulHisQry.append(
                    "SELECT RH.VERSION_NO,TO_CHAR(FR.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS'), FR.CREATED_BY, RH.REV_HISTORY, RH.RULHIS2FINRUL, RH.ORIGINAL_ID, RH.ACTIVE, RH.COMPLETED");
            strRulHisQry.append(" FROM GETS_TOOL_DPD_FINRUL FR, GETS_TOOL_DPD_RULHIS RH");
            strRulHisQry.append(
                    " WHERE RULHIS2FINRUL=FR.OBJID  AND ORIGINAL_ID=:originalId ORDER BY FR.CREATION_DATE DESC");
            Query rulHisQry = hibSession.createSQLQuery(strRulHisQry.toString());
            rulHisQry.setParameter(RMDServiceConstants.ORIGINAL_ID, originalId);
            List<Object> arlRuleHisDetails = rulHisQry.list();
            if (null != arlRuleHisDetails && !arlRuleHisDetails.isEmpty()) {

                for (Iterator<Object> ruleHisIterator = arlRuleHisDetails.iterator(); ruleHisIterator.hasNext();) {

                    objRuleHistoryServiceVO = new RuleHistoryServiceVO();
                    Object objRuleHis[] = (Object[]) ruleHisIterator.next();
                    objRuleHistoryServiceVO.setStrVersionNumber(RMDCommonUtility.convertObjectToString(objRuleHis[0]));
                    objRuleHistoryServiceVO
					.setStrDateCreated(
							RMDCommonUtility
									.convertObjectToString(objRuleHis[1]
											.toString()));
                    objRuleHistoryServiceVO.setStrCreatedBy(RMDCommonUtility.convertObjectToString(objRuleHis[2]));
                    objRuleHistoryServiceVO.setStrRevisionHistory(
                            AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(objRuleHis[3])));
                    objRuleHistoryServiceVO.setStrRuleID(RMDCommonUtility.convertObjectToString(objRuleHis[4]));
                    objRuleHistoryServiceVO.setStrOriginalID(RMDCommonUtility.convertObjectToString(objRuleHis[5]));

                    // In DB if the column active is "0" and completed is "1"
                    // then the rule is in Deactive status
                    // and if column active is "1" then the rule is in active
                    // status
                    // and if column active is "0" and completed is "1" then the
                    // rule is in draft status
                    if (RMDServiceConstants.ZERO.equalsIgnoreCase(objRuleHis[6].toString())
                            && RMDServiceConstants.ONE.equalsIgnoreCase(objRuleHis[7].toString())) {
                        objRuleHistoryServiceVO.setStatus(RMDServiceConstants.DEACTIVE);
                    }
                    if (RMDServiceConstants.ONE.equalsIgnoreCase(objRuleHis[6].toString())) {
                        objRuleHistoryServiceVO.setStatus(RMDServiceConstants.ACTIVE);
                    }
                    if (RMDServiceConstants.ZERO.equalsIgnoreCase(objRuleHis[6].toString())
                            && RMDServiceConstants.ZERO.equalsIgnoreCase(objRuleHis[7].toString())) {
                        objRuleHistoryServiceVO.setStatus(RMDServiceConstants.DRAFT);
                    }

                    arlRuleHistoryServiceVO.add(objRuleHistoryServiceVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULEVIEW);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibSession);
        }
        return arlRuleHistoryServiceVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.ViewRuleDAOIntf
     * #lockEditRule()
     */// This method is used for lock/release the rule for edit
    @Override
    public String lockEditRule(boolean isLockRule, String strRuleId, String strUserId, String strLanguage)
            throws RMDDAOException, RMDDAOConnectionException {
        Session session = null;
        Long strOrgRuleId = Long.valueOf(0);
        String strDetail = null;
        try {
            session = getHibernateSession(strUserId);
            Query getOrgId = session.createQuery(
                    "select distinct(originalId) from GetKmDpdRulhisHVO his where his.getKmDpdFinrul.getKmDpdFinrulSeqId = :finRuleId ");
            getOrgId.setParameter("finRuleId", Long.valueOf(strRuleId));
            List lstOrgId = getOrgId.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lstOrgId)) {
                strOrgRuleId = (Long) lstOrgId.get(0);
            }
            if (isLockRule) {
                StringBuilder queryString = new StringBuilder();
                queryString.append("update GetKmDpdFinrulHVO as fin1 set fin1.owner = :owner where ");
                queryString.append(" not exists (from GetKmDpdFinrulHVO fin2 where fin2.getKmDpdFinrulSeqId in ");
                queryString.append(
                        " (select his.getKmDpdFinrul.getKmDpdFinrulSeqId from GetKmDpdRulhisHVO his where his.originalId = :orignalRecommId)");
                queryString.append(" and fin2.owner is not null) ");
                queryString.append(
                        " and fin1.getKmDpdFinrulSeqId in (select his.getKmDpdFinrul.getKmDpdFinrulSeqId from GetKmDpdRulhisHVO as his where his.originalId = :orignalRecommId) ");

                session = getHibernateSession(strUserId);
                Query query = session.createQuery(queryString.toString());
                query.setParameter("owner", strUserId);
                query.setParameter("orignalRecommId", strOrgRuleId);
                int updatedRows = query.executeUpdate();
                if (updatedRows == 0) {
                    session = getHibernateSession();
                    StringBuilder strQuery = new StringBuilder();
                    strQuery.append("select distinct r.owner from GetKmDpdFinrulHVO r where r.getKmDpdFinrulSeqId in ");
                    strQuery.append(
                            "(select h.getKmDpdFinrul.getKmDpdFinrulSeqId from GetKmDpdRulhisHVO as h where h.originalId = :orignalRecommId)");
                    strQuery.append(" and r.owner is not null");
                    Query qryLockedBy = session.createQuery(strQuery.toString());
                    qryLockedBy.setParameter(RMDCommonConstants.ORIGIONAL_RECOMID, strOrgRuleId);
                    strDetail = (String) qryLockedBy.uniqueResult();
                }
            } else {
                session = getHibernateSession();
                StringBuilder strQuery = new StringBuilder();
                strQuery.append("select distinct r.owner from GetKmDpdFinrulHVO r where r.getKmDpdFinrulSeqId in ");
                strQuery.append(
                        "(select h.getKmDpdFinrul.getKmDpdFinrulSeqId from GetKmDpdRulhisHVO as h where h.originalId = :orignalRecommId)");
                strQuery.append(" and r.owner is not null");
                Query qryLockedBy = session.createQuery(strQuery.toString());
                qryLockedBy.setParameter(RMDCommonConstants.ORIGIONAL_RECOMID, strOrgRuleId);
                String strLockedBy = (String) qryLockedBy.uniqueResult();

                if (strLockedBy != null && strLockedBy.equalsIgnoreCase(strUserId)) {
                    session = getHibernateSession(strUserId);
                    StringBuilder queryString = new StringBuilder();
                    queryString.append("update GetKmDpdFinrulHVO r set r.owner = :owner where ");
                    queryString.append(
                            " r.getKmDpdFinrulSeqId in (select h.getKmDpdFinrul.getKmDpdFinrulSeqId from GetKmDpdRulhisHVO h where h.originalId = :orignalRecommId)");
                    queryString.append(" and r.owner = :lockedBy");
                    Query query = session.createQuery(queryString.toString());
                    query.setParameter(RMDCommonConstants.OWNER, RMDCommonConstants.EMPTY_STRING);
                    query.setParameter(RMDCommonConstants.LOCKEDBY, strUserId);
                    query.setParameter(RMDCommonConstants.ORIGIONAL_RECOMID, strOrgRuleId);
                    query.executeUpdate();
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_LOCK_RELEASE_RULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_LOCK_RELEASE_RULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return strDetail;
    }

    /**
     * @Author:iGATE
     * @param: strRuleId,family,language
     * @return arlFinalRule
     * @throws RMDBOException
     * @Description:This method will retrieve all final active rules suitable to
     *                   add as ROR
     */
    @Override
    public List<DpdFinrulSearchVO> getFinalRuleList(String strRuleId, String strFamily, String strRuleType,
            String language, String strCustomer) {
        Session session = null;
        List<Object[]> arlActiveFinRule = null;
        List<DpdFinrulSearchVO> arlFinalRule = null;
        String strFinalRuleId = null;
        Query finalRuleQuery = null;
        DpdFinrulSearchVO objDpdFinrulSearchVO = null;
        try {
            session = getHibernateSession();
            if (!RMDCommonUtility.isNullOrEmpty(strFamily) && !RMDCommonUtility.isNullOrEmpty(strRuleId)) {
                StringBuilder strQuery = new StringBuilder();
                strQuery.append(
                        "SELECT FINRUL.OBJID,FINRUL.RULE_TITLE FROM GETS_TOOL_DPD_FINRUL FINRUL,GETS_TOOL_DPD_RULHIS RULHIS,GETS_TOOL_DPD_RULEDEF RULEDEF");
                if (null != strCustomer && !RMDCommonConstants.EMPTY_STRING.equals(strCustomer)
                        && !RMDCommonConstants.SELECT.equalsIgnoreCase(strCustomer)) {

                    strQuery.append(",GETS_TOOL_DPD_CUST CUST,SA.TABLE_BUS_ORG TABLEBUS");

                }
                strQuery.append(
                        " WHERE FINRUL.OBJID=RULHIS.RULHIS2FINRUL AND RULHIS.ACTIVE =1 AND FINRUL.FAMILY =:Family AND FINRUL.OBJID <> :finRuleObjid AND RULEDEF.RULEDEF2FINRUL=FINRUL.OBJID AND RULEDEF.RULE_TYPE =:Rule_type");
                if (null != strCustomer && !RMDCommonConstants.EMPTY_STRING.equals(strCustomer)
                        && !RMDCommonConstants.SELECT.equalsIgnoreCase(strCustomer)) {

                    strQuery.append(
                            " AND CUST.CUST2RULEDEF    = RULEDEF.OBJID AND CUST.CUST2BUSORG     = TABLEBUS.OBJID AND TABLEBUS.ORG_ID = :CUSTOMER");

                }

                strQuery.append(
                        " GROUP BY RULEDEF.RULEDEF2FINRUL, FINRUL.OBJID, FINRUL.RULE_TITLE HAVING COUNT(DISTINCT RULEDEF.RULEDEF2FINRUL) = 1");
                // Commented by Amuthan the below to form the query based on
                // customer
                finalRuleQuery = session.createSQLQuery(strQuery.toString());
                finalRuleQuery.setParameter(RMDServiceConstants.FAMILY, strFamily);
                finalRuleQuery.setParameter(RMDServiceConstants.FINRULE_OBJID, strRuleId);
                finalRuleQuery.setParameter(RMDServiceConstants.RULE_TYPE, strRuleType);
                if (null != strCustomer && !RMDCommonConstants.EMPTY_STRING.equals(strCustomer)
                        && !RMDCommonConstants.SELECT.equalsIgnoreCase(strCustomer)) {

                    finalRuleQuery.setParameter(RMDServiceConstants.CUSTOMER, strCustomer);

                }
                arlActiveFinRule = finalRuleQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlActiveFinRule)) {
                    arlFinalRule = new ArrayList<DpdFinrulSearchVO>();

                    for (Object objRule[] : arlActiveFinRule) {
                        objDpdFinrulSearchVO = new DpdFinrulSearchVO();
                        strFinalRuleId = RMDCommonUtility.convertObjectToString(objRule[0]);
                        //commented out for performance of editor screen.
                        //if (!checkFinalRuleLinked(session, strRuleId, strFinalRuleId)) {

                            objDpdFinrulSearchVO.setKmDpdFinrulSeqId(Long.parseLong(strFinalRuleId));
                            objDpdFinrulSearchVO.setRuleTitle(RMDCommonUtility.convertObjectToString(objRule[1]));
                            arlFinalRule.add(objDpdFinrulSearchVO);
                       // }
                    }
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_LOCK_RELEASE_RULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_LOCK_RELEASE_RULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlFinalRule;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.ViewRuleDAOIntf
     * #getFiredRuleList(String,String,String)
     */
    @Override
    public List getFiredRuleList(IDListServiceVO listServiceVO) {
        Session session = null;
        List firedRuleListin = null;
        List firedRuleList = null;
        String strTraceID = null;
        StringBuilder querySB = null;
        Query creatorQuery;
        Codec ORACLE_CODEC = new OracleCodec();
        try {
            strTraceID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, listServiceVO.getStrTraceID());
            session = getHibernateSession();

            querySB = new StringBuilder();
            querySB.append(" SELECT DISTINCT LINK_SIMRUL RULE FROM GET_KM.GET_KM_FIRED_SIMRUL WHERE LINK_TRACER = "
                    + strTraceID + " ");
            querySB.append(" UNION  ");
            querySB.append(" SELECT DISTINCT LINK_CMPRUL RULE FROM GET_KM.GET_KM_FIRED_COMPLEX WHERE LINK_TRACER = "
                    + strTraceID + " ");
            querySB.append(" UNION ");
            querySB.append(" SELECT GET_KM_DPD_FINRUL_SEQ_ID RULE FROM GET_KM.GET_KM_DPD_FINRUL FINRUL, ");
            querySB.append(" (SELECT DISTINCT LINK_SIMRUL FROM GET_KM.GET_KM_FIRED_SIMRUL WHERE LINK_TRACER = "
                    + strTraceID + ") FIREDSIMPLE  ");
            querySB.append(" WHERE FIREDSIMPLE.LINK_SIMRUL = FINRUL.LINK_SIMRUL  ");
            querySB.append(" UNION ");
            querySB.append(" SELECT GET_KM_DPD_FINRUL_SEQ_ID RULE FROM GET_KM.GET_KM_DPD_FINRUL FINRUL, ");
            querySB.append(" (SELECT DISTINCT LINK_CMPRUL FROM GET_KM.GET_KM_FIRED_COMPLEX WHERE LINK_TRACER = "
                    + strTraceID + ") FIREDCMP ");
            querySB.append(" WHERE FIREDCMP.LINK_CMPRUL = FINRUL.LINK_CMPRUL ");
            creatorQuery = session.createSQLQuery(querySB.toString());
            firedRuleListin = creatorQuery.list();

            if (firedRuleListin != null) {
                firedRuleList = new ArrayList();
                for (Iterator iterator = firedRuleListin.iterator(); iterator.hasNext();) {
                    Object object = iterator.next();
                    firedRuleList.add(String.valueOf(object));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_LOCK_RELEASE_RULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, listServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_LOCK_RELEASE_RULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, listServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return firedRuleList;
    }

    /**
     * @Author:iGATE
     * @param: Session,strFinalRule,
     *             strChoosedRuleOnRule
     * @return lstRuleId
     * @throws RMDBOException
     * @Description:This method is used for checking the ROR rule is having
     *                   circular reference with the edit final rule if it has
     *                   circular reference then it will return yes else it will
     *                   return no. This method will recursively called again to
     *                   check all the inner Rules are having circular reference
     *                   with final rules.
     */
    private boolean checkFinalRuleLinked(Session hibernateSession, String strFinalRule, String strChoosedRuleOnRule) {
        Query cmpRuleQuery = null;
        Query checkFinalRule = null;
        List<BigDecimal> arlCmpRules = new ArrayList<BigDecimal>();
        List<Object[]> arlFinalRule = new ArrayList<Object[]>();
        String strRule1 = null;
        String strRule2 = null;
        String strCmpRuleId = null;
        try {
            cmpRuleQuery = hibernateSession.createSQLQuery(RMDServiceConstants.QueryConstants.GET_CMPRULE_RULES_SQL);
            cmpRuleQuery.setParameter(RMDServiceConstants.FINRULE_OBJID, strChoosedRuleOnRule);
            arlCmpRules = cmpRuleQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arlCmpRules)) {
                checkFinalRule = hibernateSession
                        .createSQLQuery(RMDServiceConstants.QueryConstants.CHECK_ROR_RULE_SQL.toString());
                for (BigDecimal bigDecCmpRuleId : arlCmpRules) {
                    strCmpRuleId = bigDecCmpRuleId.toString();
                    checkFinalRule.setParameter(RMDServiceConstants.CMPRULE_OBJID, strCmpRuleId);
                    arlFinalRule = checkFinalRule.list();
                    for (Object[] objFinalRule : arlFinalRule) {
                        strRule1 = RMDCommonUtility.convertObjectToString(objFinalRule[0]);
                        strRule2 = RMDCommonUtility.convertObjectToString(objFinalRule[1]);
                        if (null != strRule1 && !RMDServiceConstants.NEGATIVE_ONE.equals(strRule1)
                                && strRule1.equalsIgnoreCase(strFinalRule)) {
                            return true;
                        } else if (null != strRule2 && !RMDServiceConstants.NEGATIVE_ONE.equals(strRule2)
                                && strRule2.equalsIgnoreCase(strFinalRule)) {
                            return true;
                        } else {
                            if (!RMDServiceConstants.NEGATIVE_ONE.equals(strRule1)
                                    && checkFinalRuleLinked(hibernateSession, strFinalRule, strRule1)) {
                                return true;
                            } else if (!RMDServiceConstants.NEGATIVE_ONE.equals(strRule2)
                                    && checkFinalRuleLinked(hibernateSession, strFinalRule, strRule2)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_ROR_RULES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_ROR_RULES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        }
        return false;

    }

    /**
     * @Author:iGATE
     * @return
     * @throws Exception
     * @Description: This method will return the MP name for the column passed
     */
    private List<String> getMPName(Session hibernateSession, String columnName,
			String strFamily) throws Exception {
		Query queryMPName = null;
		List<String> strMpName = null;
		List<Object[]> lstMPName = null;
		String[] strFamilyArray=null;
		try {
			if (!RMDCommonUtility.checkNull(hibernateSession)
					&& !RMDCommonUtility.isNullOrEmpty(columnName)
					&& !RMDCommonUtility.isNullOrEmpty(strFamily)) {
				queryMPName = hibernateSession.createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_MP_PARAM
								.toString());

				strFamilyArray=strFamily.split(RMDCommonConstants.COMMMA_SEPARATOR);			
				queryMPName.setParameterList(RMDServiceConstants.FAMILY, strFamilyArray);		
				queryMPName.setParameter(RMDServiceConstants.COLUMN_NAME, columnName);
				lstMPName = queryMPName.list();
				if (RMDCommonUtility.isCollectionNotEmpty(lstMPName)) {
					strMpName=new ArrayList<String>();
					for (Object obj[] : lstMPName) {
					strMpName.add(RMDCommonUtility.convertObjectToString(obj[0]));
					if(null!=obj[1]){
					BigDecimal objBigDecimal = (BigDecimal) obj[1];							
					strMpName.add(objBigDecimal.toString());
					}else{
						strMpName.add(null);
					}
				  }
				}
			}
		} catch (RMDDAOConnectionException objRMDDAOConnectionException) {
			throw objRMDDAOConnectionException;
		} catch (RMDDAOException objRMDDAOException) {
			throw objRMDDAOException;
		} catch (Exception objException) {
			throw objException;
		}
		finally{
			queryMPName=null;
		}
		return strMpName;
	}
    /**
     * @Author:iGATE
     * @return
     * @throws Exception
     * @Description: This method will return the objSimpleRuleServiceVO after
     *               populating multi fault details
     */
    private void getMultifaultDetails(SimpleRuleServiceVO objSimpleRuleServiceVO, Session hibSession,
            String strSimRuleObjid,String strFamily) throws Exception {
        Query multifaultQuery = null;
        List<Object> multiFaultList = null;
        String strFaultCode = RMDCommonConstants.EMPTY_STRING;
        String strFaultCodeDesc = RMDCommonConstants.EMPTY_STRING;
        List<String> lstPixels = new ArrayList<String>();
        String strSubID = RMDCommonConstants.EMPTY_STRING;
        String strControllerid = RMDCommonConstants.EMPTY_STRING;
        List<String> lstNotesFault = new ArrayList<String>();

        try {
            multifaultQuery = hibSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.SELECT_MULTIFAULT_DETAILS.toString());
            multifaultQuery.setParameter(RMDServiceConstants.SIMRULE_OBJID, strSimRuleObjid);
            multiFaultList = multifaultQuery.list();
            int i = 0;
            if (RMDCommonUtility.isCollectionNotEmpty(multiFaultList)) {
                for (Iterator<Object> iterator = multiFaultList.iterator(); iterator.hasNext();) {
                    Object object[] = (Object[]) iterator.next();
                    if (null != object[0]) {
                        strFaultCode = strFaultCode + RMDCommonUtility.convertObjectToString(object[0])
                                + RMDCommonConstants.COMMMA_SEPARATOR;
                    }
                    if (null != object[4]) {
                        strSubID = strSubID + RMDCommonUtility.convertObjectToString(object[4])
                                + RMDCommonConstants.COMMMA_SEPARATOR;
                    } else {
                        strSubID = strSubID + RMDCommonConstants.COMMMA_SEPARATOR;
                    }
                    if (null != object[5]) {
                        strControllerid = strControllerid + RMDCommonUtility.convertObjectToString(object[5])
                                + RMDCommonConstants.COMMMA_SEPARATOR;
                    } else {
                        strControllerid = strControllerid + RMDCommonConstants.COMMMA_SEPARATOR;
                    }

                    lstNotesFault.add(i, RMDCommonUtility.convertObjectToString(object[6]));
                    lstPixels.add(i++, RMDCommonUtility.convertObjectToString(object[1]));
                    if (null != RMDCommonUtility.convertObjectToString(object[0])) {
						List<FaultServiceStgyServiceVO> arlfaultDesc=objFaultServiceStgyDAOIntf.findFaultCode(strFamily, RMDCommonUtility.convertObjectToString(object[0]),false);
                        if (null != arlfaultDesc && !arlfaultDesc.isEmpty()) {
                            for (Iterator<FaultServiceStgyServiceVO> faultIterator = arlfaultDesc
                                    .iterator(); faultIterator.hasNext();) {

                                FaultServiceStgyServiceVO objFaultDesc = faultIterator.next();
                                strFaultCodeDesc = strFaultCodeDesc + objFaultDesc.getStrDescription()
                                        + RMDCommonConstants.COMMMA_SEPARATOR;
                               
                            }
                        }
                    }
                    

                }
                objSimpleRuleServiceVO.setPixelsMultifaults(lstPixels);
                objSimpleRuleServiceVO.setNotesMultifaults(lstNotesFault);
                if (null != strFaultCode && !"".equals(strFaultCode)) {
                    objSimpleRuleServiceVO.setStrFaultCode(strFaultCode.substring(0, strFaultCode.length() - 1));
                    objSimpleRuleServiceVO
                            .setStrFaultDesc(strFaultCodeDesc.substring(0, strFaultCodeDesc.length() - 1));
                }
                if (null != strSubID && !"".equals(strSubID)) {
                    objSimpleRuleServiceVO.setStrSubID(strSubID.substring(0, strSubID.length() - 1));
                }
                if (null != strControllerid && !"".equals(strControllerid)) {
                    objSimpleRuleServiceVO
                            .setStrControllerid(strControllerid.substring(0, strControllerid.length() - 1));
                }
            }

        } catch (RMDDAOConnectionException objRMDDAOConnectionException) {
            throw objRMDDAOConnectionException;
        } catch (RMDDAOException objRMDDAOException) {
            throw objRMDDAOException;
        } catch (Exception objException) {
            throw objException;
        }
    }

    private HashMap<Long, SimpleRuleServiceVO> prepareSimpleRuleIdMap(ArrayList<SimpleRuleServiceVO> arlSimpleRule) {
        HashMap<Long, SimpleRuleServiceVO> hmSimRules = new HashMap<Long, SimpleRuleServiceVO>();
        for (SimpleRuleServiceVO tempVO : arlSimpleRule)
            hmSimRules.put(Long.valueOf(tempVO.getStrSimpleRuleId()), tempVO);
        return hmSimRules;
    }

    private SimpleRuleServiceVO createCopy(SimpleRuleServiceVO tmpSimpleRuleServiceVO, long simUniqueId) {
        SimpleRuleClauseServiceVO tempNewClsVO = null;
        SimpleRuleServiceVO tmpNewSimRule = (SimpleRuleServiceVO) tmpSimpleRuleServiceVO.clone();
        ArrayList<SimpleRuleClauseServiceVO> arlClauses = tmpSimpleRuleServiceVO.getArlSimpleRuleClauseVO();
        ArrayList<SimpleRuleClauseServiceVO> arlNewClauses = new ArrayList<SimpleRuleClauseServiceVO>();
        for (SimpleRuleClauseServiceVO tmpClause : arlClauses) {
            tempNewClsVO = (SimpleRuleClauseServiceVO) tmpClause.clone();
            tempNewClsVO.setStrClauseId(Integer.parseInt(tmpClause.getStrClauseId()) + "" + simUniqueId);
            arlNewClauses.add(tempNewClsVO);
        }
        tmpNewSimRule.setArlSimpleRuleClauseVO(arlNewClauses);
        tmpNewSimRule.setStrSimpleRuleId(Integer.parseInt(tmpNewSimRule.getStrSimpleRuleId()) + "" + simUniqueId);
        return tmpNewSimRule;
    }

    /**
     * @Author:iGATE
     * @return
     * @throws Exception
     * @Description: This method will return top level complex rule id for ROR
     */
    private String getTopLevelComplexRuleID(Session hibSession, String strRuleObjid) throws Exception {
        Query topLevelCmpRulidQry = null;
        List<BigDecimal> cmpruleIdList = null;
        String topCmpRuleid = null;
        try {
            topLevelCmpRulidQry = hibSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.SELECT_TOP_LEVEL_COMPLEX_RULEID.toString());

            topLevelCmpRulidQry.setParameter(RMDServiceConstants.FINRULE_OBJID, strRuleObjid);
            cmpruleIdList = topLevelCmpRulidQry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(cmpruleIdList)) {
                for (Iterator<BigDecimal> iterator = cmpruleIdList.iterator(); iterator.hasNext();) {
                    BigDecimal object = iterator.next();
                    if (null != object) {
                        topCmpRuleid = RMDCommonUtility.convertObjectToString(object);
                    }
                }
            }
        } catch (RMDDAOConnectionException objRMDDAOConnectionException) {
            throw objRMDDAOConnectionException;
        } catch (RMDDAOException objRMDDAOException) {
            throw objRMDDAOException;
        } catch (Exception objException) {
            throw objException;
        }
        return topCmpRuleid;
    }
}
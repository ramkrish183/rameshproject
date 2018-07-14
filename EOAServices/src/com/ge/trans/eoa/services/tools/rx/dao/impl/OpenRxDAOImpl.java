/**
 * ============================================================
 * File : RxExecutionDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmRecomDelvHVO;
import com.ge.trans.eoa.services.tools.rx.dao.intf.OpenRxDAOIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.OpenRxServiceVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : Viji
 * @Version : 1.0
 * @Date Created: May 20, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to fetch the list of Open Rxs from database
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class OpenRxDAOImpl extends BaseDAO implements OpenRxDAOIntf {

    private static final long serialVersionUID = -4218069361144677022L;
    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(OpenRxDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rx.dao.intf.OpenRxDAOIntf#fetchOpenRxs
     * (com.ge.trans.rmd.services.tools.rx.service.valueobjects.OpenRxServiceVO)
     */
    @Override
    public List fetchOpenRxs(OpenRxServiceVO objOpenRxServiceVO) throws RMDDAOException {
        Session session = null;
        List arlOpenRxs;
        List arlFinalResults;
        OpenRxServiceVO objRxServiceVO;
        String strUrgency = RMDCommonConstants.EMPTY_STRING;
        String strEstimatedRepTime = RMDCommonConstants.EMPTY_STRING;
        try {
            session = getHibernateSession(objOpenRxServiceVO.getStrUserName());
            final StringBuilder query = new StringBuilder();
            query.append("from GetCmRecomDelvHVO objGetCmRecomDelv ");
            query.append(
                    "left join fetch objGetCmRecomDelv.getCmCase left join fetch objGetCmRecomDelv.getCmCase.getAsstAsset ");
            query.append(
                    "left join fetch objGetCmRecomDelv.getKmRecom left join fetch objGetCmRecomDelv.getKmRecom.getKmRecomHistsForLinkCurrentRecom ");
            query.append("left outer join fetch objGetCmRecomDelv.getCmRxStatus ");
            query.append("where objGetCmRecomDelv.strLanguage = :lang ");
            query.append("order by objGetCmRecomDelv.creationDate ASC");
            final Query q = session.createQuery(query.toString()).setString(RMDCommonConstants.LANG,
                    objOpenRxServiceVO.getStrLanguage());
            arlOpenRxs = q.list();
            arlFinalResults = new ArrayList();
            if (!arlOpenRxs.isEmpty()) {
                final Map<Object, String> urgencyRepair = getDisplayNameMap(RMDServiceConstants.URGENCY_LIST_NAME,
                        objOpenRxServiceVO.getStrUserLanguage(), RMDCommonConstants.LOOKVALUE);
                final Map<Object, String> estimatedTimeRepair = getDisplayNameMap(
                        RMDServiceConstants.ESTIMATED_LIST_NAME, objOpenRxServiceVO.getStrUserLanguage(),
                        RMDCommonConstants.LOOKVALUE);
                final int size = arlOpenRxs.size();
                for (int i = 0; i < size; i++) {
                    GetCmRecomDelvHVO objGetCmRecomDelvHVO = (GetCmRecomDelvHVO) arlOpenRxs.get(i);
                    Set rxStatusSet = objGetCmRecomDelvHVO.getGetCmRxStatus();
                    int iRxStatusSize = rxStatusSet.size();
                    if (iRxStatusSize == 0) {
                        objRxServiceVO = new OpenRxServiceVO();
                        objRxServiceVO.setStrRoadNumber(
                                objGetCmRecomDelvHVO.getGetCmCase().getGetAsstAsset().getAssetNumber());
                        objRxServiceVO.setLongAssetNumber(
                                objGetCmRecomDelvHVO.getGetCmCase().getGetAsstAsset().getLongAssetNumber());
                        objRxServiceVO.setStrCaseId(objGetCmRecomDelvHVO.getGetCmCase().getCaseId());
                        objRxServiceVO.setStrRxObjid(objGetCmRecomDelvHVO.getRxCaseId());
                        // objRxServiceVO.setStrRxTitle(objGetCmRecomDelvHVO
                        // .getGetKmRecom().getTitle());
                        objRxServiceVO.setDtHistDate(objGetCmRecomDelvHVO.getCreationDate());
                        if (objGetCmRecomDelvHVO.getUrgency() != null
                                && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objGetCmRecomDelvHVO.getUrgency())
                                && objGetCmRecomDelvHVO.getEstRepairTime() != null && !RMDCommonConstants.EMPTY_STRING
                                        .equalsIgnoreCase(objGetCmRecomDelvHVO.getEstRepairTime())) {

                            strUrgency = urgencyRepair.get(objGetCmRecomDelvHVO.getUrgency());
                            strEstimatedRepTime = estimatedTimeRepair.get(objGetCmRecomDelvHVO.getEstRepairTime());
                            objRxServiceVO
                                    .setStrUrgRepair(strUrgency + RMDCommonConstants.BLANK_SPACE + strEstimatedRepTime);
                        } else if (objGetCmRecomDelvHVO.getUrgency() != null
                                && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objGetCmRecomDelvHVO.getUrgency())
                                && objGetCmRecomDelvHVO.getEstRepairTime() == null) {
                            strUrgency = urgencyRepair.get(objGetCmRecomDelvHVO.getUrgency());
                            ;
                            objRxServiceVO.setStrUrgRepair(strUrgency);
                        } else if (objGetCmRecomDelvHVO.getEstRepairTime() != null
                                && !RMDCommonConstants.EMPTY_STRING
                                        .equalsIgnoreCase(objGetCmRecomDelvHVO.getEstRepairTime())
                                && objGetCmRecomDelvHVO.getUrgency() == null) {
                            strEstimatedRepTime = estimatedTimeRepair.get(objGetCmRecomDelvHVO.getEstRepairTime());
                            objRxServiceVO.setStrUrgRepair(strEstimatedRepTime);
                        }
                        arlFinalResults.add(objRxServiceVO);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_OPEN_RXS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objOpenRxServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in OpenRxDAOImpl fetchOpenRxs()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_OPEN_RXS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objOpenRxServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
        return arlFinalResults;
    }
}

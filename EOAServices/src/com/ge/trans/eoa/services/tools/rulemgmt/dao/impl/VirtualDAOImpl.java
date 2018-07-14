/**
 * ============================================================
 * Classification: GE Confidential
 * File :VirtualDAOImpl.java
 * Description : DAO Implements for Virtual Screen
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.VirtualDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalVirtualServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualEquationServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualHistServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxConfigVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxHistServiceVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : DAO Implements for Virtual Screen
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class VirtualDAOImpl extends RMDCommonDAO implements VirtualDAOIntf {

    private static final long serialVersionUID = -7728891002778046496L;
    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(VirtualDAOImpl.class);

    /**
     * @param objfinalVirtualServiceVO
     * @return String
     * @throws RMDDAOException
     * @Description: This method is used to save/update the virtual in
     *               corresponding table
     */
    @Override
    public String saveVirtual(FinalVirtualServiceVO objfinalVirtualServiceVO) throws RMDDAOException {
        String strLanguage = null;
        // Criteria criteria;
        Session virtualSession = null;
        String strEquation;
        // List lstResult = null;
        String strDpdColnameSeqId = RMDCommonConstants.EMPTY_STRING;
        String strVirtualSeqId = RMDCommonConstants.EMPTY_STRING;
        String strEquationSeqId = RMDCommonConstants.EMPTY_STRING;
        Query ddpcolnamQuery = null;
        Query virtualQuery = null;
        List<VirtualEquationServiceVO> arlVirtualEquation = null;
        String strEquationDescTxt = RMDCommonConstants.EMPTY_STRING;
        int lookbackPoints = 0;
        int lookbackTime = 0;
        String strVirtualType = null;
        int virtUpdate = 0;
        int virtEqUpdate = 0;
        try {
            strLanguage = objfinalVirtualServiceVO.getStrLanguage();

            virtualSession = getHibernateSession(objfinalVirtualServiceVO.getStrUserName());
            virtualSession.beginTransaction();
            // Insert into DPD Colname
            strDpdColnameSeqId = getSequenceId(RMDServiceConstants.QueryConstants.SELECT_DPD_COLNAME_SEQ,
                    virtualSession);

            ddpcolnamQuery = virtualSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_COLNAME_BUILDER.toString());
            ddpcolnamQuery.setParameter(RMDServiceConstants.COLUMN_OBJID, strDpdColnameSeqId);
            ddpcolnamQuery.setParameter(RMDServiceConstants.LAST_UPDATED_BY, objfinalVirtualServiceVO.getStrUserName());
            ddpcolnamQuery.setParameter(RMDServiceConstants.CREATED_BY, objfinalVirtualServiceVO.getStrUserName());
            ddpcolnamQuery.setParameter(RMDServiceConstants.FAMILY, objfinalVirtualServiceVO.getStrFamily());
            ddpcolnamQuery.setParameter(RMDServiceConstants.COLUMN_NAME,
                    AppSecUtil.stripNonValidXMLCharactersForKM(objfinalVirtualServiceVO.getStrVirtualName().trim()));
            ddpcolnamQuery.setParameter(RMDServiceConstants.TABLE_NAME, RMDServiceConstants.STR_TOOL_FAULT);
            ddpcolnamQuery.setParameter(RMDServiceConstants.TABLE_NAME_DISC, RMDServiceConstants.STR_TOOL_FAULT);
            ddpcolnamQuery.setParameter(RMDServiceConstants.PARM_TYPE, RMDServiceConstants.STR_TOOL_VIRTUAL);
            ddpcolnamQuery.setParameter(RMDServiceConstants.DB_COLUMN_NAME,
                    AppSecUtil.stripNonValidXMLCharactersForKM(objfinalVirtualServiceVO.getStrVirtualName().trim()));
            ddpcolnamQuery.executeUpdate();

            strEquationDescTxt = AppSecUtil
                    .stripNonValidXMLCharactersForKM(objfinalVirtualServiceVO.getStrVirtualDesc());
            // To avoid null insertion, Need to remove later
            // if (RMDCommonUtility.isNullOrEmpty(strEquationDescTxt)) {
            // strEquationDescTxt = "Empty Desc";
            // }
            // insert into GETS_TOOLS.GETS_TOOL_VIRTUAL
            strVirtualSeqId = getSequenceId(RMDServiceConstants.QueryConstants.SELECT_VIRTUAL_SEQ, virtualSession);

            // get virtual column
            String strVirtualColumn = getVirtualColumn(
                    RMDServiceConstants.QueryConstants.SELECT_VIRTUAL_COLUMN_NAME.toString(), virtualSession);

            virtualQuery = virtualSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_TOOLS_VIRTUAL_BUILDER.toString());
            virtualQuery.setParameter(RMDServiceConstants.VIRTUAL_OBJID, strVirtualSeqId);
            virtualQuery.setParameter(RMDServiceConstants.COLUMN_OBJID, strDpdColnameSeqId);
            virtualQuery.setParameter(RMDServiceConstants.EQUATION_DESC_TXT, (strEquationDescTxt));
            virtualQuery.setParameter(RMDServiceConstants.FAMILY, objfinalVirtualServiceVO.getStrFamily());
            virtualQuery.setParameter(RMDServiceConstants.ACTIVE_FLAG, RMDCommonConstants.LETTER_Y);
            // Inserting Admin as a default value here, need to remove later
            virtualQuery.setParameter(RMDServiceConstants.LAST_UPDATED_BY, objfinalVirtualServiceVO.getStrUserName());
            virtualQuery.setParameter(RMDServiceConstants.CREATED_BY, objfinalVirtualServiceVO.getStrUserName());
            virtualQuery.setParameter(RMDServiceConstants.ROW_VERSION, 1);
            virtualQuery.setParameter(RMDServiceConstants.STR_TOOL_VIRTUAL_COLUMN, strVirtualColumn);
            virtUpdate = virtualQuery.executeUpdate();
            
            arlVirtualEquation = objfinalVirtualServiceVO.getArlVirtualEquation();
            if (RMDCommonUtility.isCollectionNotEmpty(arlVirtualEquation)) {
                for (VirtualEquationServiceVO virtualEquationServiceVO : arlVirtualEquation) {
                    // Calculating Equation
                    strEquation = virtualEquationServiceVO.getStrEquation();
                    // strEquation=AppSecUtil.decodeString(strEquation);
                    if (!RMDCommonUtility.isNullOrEmpty(virtualEquationServiceVO.getStrLookBackTime())) {
                        lookbackTime = Integer.parseInt(virtualEquationServiceVO.getStrLookBackTime());
                    } else {
                        lookbackTime = 0;
                    }

                    if (!RMDCommonUtility.isNullOrEmpty(virtualEquationServiceVO.getStrLookBackPoints())) {
                        lookbackPoints = Integer.parseInt(virtualEquationServiceVO.getStrLookBackPoints());
                    } else {
                        lookbackPoints = 0;
                    }
                    if (lookbackPoints > 0 && lookbackTime > 0) {
                        strVirtualType = RMDServiceConstants.STR_C;
                    } else {
                        strVirtualType = RMDServiceConstants.STR_S;
                    }
                    strEquationSeqId = getSequenceId(RMDServiceConstants.QueryConstants.SELECT_EQUATION_SEQ,
                            virtualSession);
                    // INSERT INTO GETS_TOOLS.GETS_TOOL_EQUATION
                    virtualQuery = virtualSession.createSQLQuery(
                            RMDServiceConstants.QueryConstants.INSERT_TOOLS_EQUATION_BUILDER.toString());
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_OBJID, strEquationSeqId);
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_TXT, strEquation);

                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_DESC_TXT,
                            (virtualEquationServiceVO.getStrEquationDescTxt()));
                    virtualQuery.setParameter(RMDServiceConstants.CUSTOM_PROCESS_FILE,
                            virtualEquationServiceVO.getStrCustomFileName());

                    virtualQuery.setParameter(RMDServiceConstants.LOOKBACKTIME, lookbackTime);

                    virtualQuery.setParameter(RMDServiceConstants.LOOK_BACK_FAULT_COUNT, lookbackPoints);
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_TYPE, strVirtualType);

                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_JSON_TXT,
                            virtualEquationServiceVO.getStrEquationJSONTxt());
                    virtualQuery.setParameter(RMDServiceConstants.PARENT_EQUATION_SEQ_ID, 0);
                    virtualQuery.setParameter(RMDServiceConstants.ROW_VERSION, 1);
                    virtualQuery.setParameter(RMDServiceConstants.LAST_UPDATED_BY,
                            objfinalVirtualServiceVO.getStrUserName());
                    virtualQuery.setParameter(RMDServiceConstants.CREATED_BY,
                            objfinalVirtualServiceVO.getStrUserName());

                    virtualQuery.executeUpdate();

                    virtualQuery = virtualSession.createSQLQuery(
                            RMDServiceConstants.QueryConstants.INSERT_TOOLS_VIRTUAL_EQUATION_BUILDER.toString());
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_OBJID, strEquationSeqId);
                    virtualQuery.setParameter(RMDServiceConstants.VIRTUAL_OBJID, strVirtualSeqId);
                    virtEqUpdate = virtualQuery.executeUpdate();

                }
            }
            /*Added by Koushik as part of OMDKM-1869 - Add Revision Notes and History to Virtual Parameters */
            if(objfinalVirtualServiceVO.getRevisionNote() != null && (virtUpdate >0 || virtEqUpdate >0)){
            	StringBuilder virtHistQuery = new StringBuilder();
            	virtHistQuery.append("INSERT INTO GETS_TOOLS.GETS_TOOL_VIRTUAL_HIST(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,VIRTUAL_HIST2VIRTUAL,VERSION,NOTES) ");
    			virtHistQuery.append("VALUES (GETS_TOOL_VIRTUAL_HIST_SEQ.nextval, SYSDATE, :lastUpdatedBy,SYSDATE, :createdBy, :virtualObjid, (select (NVL(MAX(VERSION), 0)+1) from GETS_TOOLS.GETS_TOOL_VIRTUAL_HIST WHERE VIRTUAL_HIST2VIRTUAL = :virtualObjid), :note)");
            	Query virtualHistQuery = virtualSession.createSQLQuery(virtHistQuery.toString());
            	virtualHistQuery.setParameter(RMDServiceConstants.VIRTUAL_OBJID, strVirtualSeqId);
            	virtualHistQuery.setParameter(RMDServiceConstants.VIRTUAL_HIST_UPDATED_BY, objfinalVirtualServiceVO.getStrUserName());
            	virtualHistQuery.setParameter(RMDServiceConstants.VIRTUAL_HIST_CREATED_BY, objfinalVirtualServiceVO.getStrUserName());
            	virtualHistQuery.setParameter(RMDServiceConstants.VIRTUAL_HIST_NOTE, objfinalVirtualServiceVO.getRevisionNote());
            	virtualHistQuery.executeUpdate();
            }
            /*End of OMDKM-1869*/
            
            if (!virtualSession.getTransaction().wasCommitted()) {
                virtualSession.getTransaction().commit();
            }
        } catch (RMDDAOConnectionException ex) {
            virtualSession.getTransaction().rollback();
            LOG.error("ERROR in saveVirtual method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_VIRTUAL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            virtualSession.getTransaction().rollback();
            LOG.error("ERROR in saveVirtual method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_VIRTUAL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return strVirtualSeqId;
    }

    /**
     * @param objfinalVirtualServiceVO
     * @return List
     * @throws RMDDAOException
     * @Description: This method is used to list out all the existing virtual
     */
    @Override
    public FinalVirtualServiceVO getVirtualDetails(String strVirtualId, String strLanguage) throws RMDDAOException {
        FinalVirtualServiceVO objFinalVirtualServiceVO = null;
        Session virtualSession = null;
        Query virtualQuery = null;
        VirtualEquationServiceVO objVirEq = null;
        ArrayList<VirtualEquationServiceVO> arlVirtualEquation = null;
        try {
            virtualSession = getHibernateSession();
            virtualQuery = virtualSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_VIRTUAL_DETAILS.toString());
            virtualQuery.setParameter(RMDServiceConstants.VIRTUAL_ID, strVirtualId);

            List<Object> virtualDetails = virtualQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(virtualDetails)) {
                arlVirtualEquation = new ArrayList<VirtualEquationServiceVO>();
                for (Iterator<Object> virtualIterator = virtualDetails.iterator(); virtualIterator.hasNext();) {
                    Object objVirtuals[] = (Object[]) virtualIterator.next();
                    objFinalVirtualServiceVO = new FinalVirtualServiceVO();
                    objVirEq = new VirtualEquationServiceVO();
                    objFinalVirtualServiceVO
                            .setStrFinalVirtualId(RMDCommonUtility.convertObjectToString(objVirtuals[0]));
                    objFinalVirtualServiceVO.setStrVirtualName(AppSecUtil.stripNonValidXMLCharactersForKM(
                            AppSecUtil.decodeString(RMDCommonUtility.convertObjectToString(objVirtuals[1]))));
                    objVirEq.setStrEquationId(RMDCommonUtility.convertObjectToString(objVirtuals[2]));
                    objVirEq.setStrEquation(
                            AppSecUtil.decodeString(RMDCommonUtility.convertObjectToString(objVirtuals[3])));

                    objVirEq.setStrCustomFileName(
                            AppSecUtil.decodeString(RMDCommonUtility.convertObjectToString(objVirtuals[4])));
                    objFinalVirtualServiceVO.setStrFamily(RMDCommonUtility.convertObjectToString(objVirtuals[6]));
                    objFinalVirtualServiceVO.setStrActive(RMDCommonUtility.convertObjectToString(objVirtuals[7]));
                    objFinalVirtualServiceVO.setStrVirtualDesc(AppSecUtil.stripNonValidXMLCharactersForKM(
                            AppSecUtil.decodeString(RMDCommonUtility.convertObjectToString(objVirtuals[8]))));
                    objVirEq.setStrLookBackPoints(RMDCommonUtility.convertObjectToString(objVirtuals[9]));
                    objVirEq.setStrLookBackTime(RMDCommonUtility.convertObjectToString(objVirtuals[10]));
                    objFinalVirtualServiceVO.setLastUpdatedBy(RMDCommonUtility.convertObjectToString(objVirtuals[11]));
                    try {
                        objFinalVirtualServiceVO.setLastUpdatedDate(
                                RMDCommonUtility.stringToDate(RMDCommonUtility.convertObjectToString(objVirtuals[12]),
                                        RMDCommonConstants.DateConstants.YYYYMMDDHHMMSS));
                    } catch (Exception e) {
                        LOG.error("Exception while parsing date: ", e);
                    }
                    objFinalVirtualServiceVO.setCreatedBy(RMDCommonUtility.convertObjectToString(objVirtuals[13]));
                    try {
                        objFinalVirtualServiceVO.setCreationDate(
                                RMDCommonUtility.stringToDate(RMDCommonUtility.convertObjectToString(objVirtuals[14]),
                                        RMDCommonConstants.DateConstants.YYYYMMDDHHMMSS));
                    } catch (Exception e) {
                        LOG.error("Exception while parsing date: ", e);
                    }
                    objVirEq.setStrEquationDescTxt(
                            AppSecUtil.decodeString(RMDCommonUtility.convertObjectToString(objVirtuals[15])));
                    Clob cb = (Clob) objVirtuals[16];

                    objVirEq.setStrEquationJSONTxt(
                            AppSecUtil.decodeString(new BufferedReader(cb.getCharacterStream()).readLine()));
                    arlVirtualEquation.add(objVirEq);
                    objFinalVirtualServiceVO.setArlVirtualEquation(arlVirtualEquation);
                }
                
                /*Added by Koushik as part of OMDKM-1869 - Add Revision Notes and History to Virtual Parameters */
                StringBuilder virtHistQueryBuilder = new StringBuilder();
                virtHistQueryBuilder.append("SELECT VERSION, TO_CHAR(CREATION_DATE,'MM/DD/YYYY HH24:MI:SS') CREATION_DATE,CREATED_BY,NOTES ");
                virtHistQueryBuilder.append("FROM GETS_TOOLS.GETS_TOOL_VIRTUAL_HIST ");
                virtHistQueryBuilder.append("WHERE VIRTUAL_HIST2VIRTUAL=:virtualId ORDER BY OBJID DESC ");
                SQLQuery nativeQuery = virtualSession.createSQLQuery(virtHistQueryBuilder.toString());
				nativeQuery.setParameter(RMDServiceConstants.VIRTUAL_ID, strVirtualId);
				List virtHistDetail = nativeQuery.list();
				VirtualHistServiceVO objVirtualHistServiceVO = null;
				List<VirtualHistServiceVO> virtualHistory = new ArrayList<VirtualHistServiceVO>();
				if (RMDCommonUtility
						.isCollectionNotEmpty(virtHistDetail)) {
					for (int idx = 0; idx < virtHistDetail.size(); idx++) {
						Object histDetails[] = (Object[]) virtHistDetail
								.get(idx);
						objVirtualHistServiceVO = new VirtualHistServiceVO();
						objVirtualHistServiceVO
						.setVersionNumber(RMDCommonUtility
								.convertObjectToString(histDetails[0]));
						objVirtualHistServiceVO
						.setStrDateCreated(RMDCommonUtility
								.convertObjectToString(histDetails[1]));
						objVirtualHistServiceVO.setCreatedBy(RMDCommonUtility
								.convertObjectToString(histDetails[2]));
						objVirtualHistServiceVO.setNote(RMDCommonUtility
								.convertObjectToString(histDetails[3]));
						virtualHistory.add(objVirtualHistServiceVO);
					}
				}
				objFinalVirtualServiceVO.setVirtualHistory(virtualHistory);
				/*End of OMDKM-1869*/
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.error("ERROR in getVirtualDetails method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SEARCH_VIRTUAL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("ERROR in getVirtualDetails method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SEARCH_VIRTUAL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return objFinalVirtualServiceVO;
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     * @Description: This method is used to fetch Virtuals Lastupdated By
     */
    @Override
    public List<VirtualServiceVO> getVirtualLastUpdatedBy(String strLanguage) {
        List<VirtualServiceVO> arlVirtualServiceVO = null;
        Session virtualSession = null;
        Query virtualQuery = null;
        VirtualServiceVO objVirtualServiceVO = null;
        try {
            virtualSession = getHibernateSession();
            virtualQuery = virtualSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_VIRTUALS_LASTUPDATEDBY.toString());
            List<String> lstString = virtualQuery.list();
            arlVirtualServiceVO = new ArrayList<VirtualServiceVO>();
            if (RMDCommonUtility.isCollectionNotEmpty(lstString)) {
                for (String lastUpdatedBy : lstString) {
                    objVirtualServiceVO = new VirtualServiceVO();
                    objVirtualServiceVO.setLastUpdatedBy(lastUpdatedBy);
                    arlVirtualServiceVO.add(objVirtualServiceVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.error("ERROR in getVirtualLastUpdatedBy method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_LASTUPDATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("ERROR in getVirtualLastUpdatedBy method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_LASTUPDATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return arlVirtualServiceVO;
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     * @Description: This method is used to fetch Virtuals Created By
     */

    @Override
    public List<VirtualServiceVO> getVirtualCreatedBy(String strLanguage) {
        List<VirtualServiceVO> arlVirtualServiceVO = null;
        Session virtualSession = null;
        VirtualServiceVO objVirtualServiceVO = null;
        Query virtualQuery = null;
        try {
            virtualSession = getHibernateSession();
            virtualQuery = virtualSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_VIRTUALS_CREATED_BY.toString());
            List<String> lstString = virtualQuery.list();
            arlVirtualServiceVO = new ArrayList<VirtualServiceVO>();
            if (RMDCommonUtility.isCollectionNotEmpty(lstString)) {
                for (String createdBy : lstString) {
                    objVirtualServiceVO = new VirtualServiceVO();
                    objVirtualServiceVO.setCreatedBy(createdBy);
                    arlVirtualServiceVO.add(objVirtualServiceVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error("ERROR in getVirtualCreatedBy method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("ERROR in getVirtualCreatedBy method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return arlVirtualServiceVO;
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     * @Description: This method is used to fetch Virtuals Titles
     */
    @Override
    public List<VirtualServiceVO> getVirtualTitles(String strVirtualTitles, String strLanguage) {
        List<VirtualServiceVO> arlVirtualServiceVO = null;
        Session virtualSession = null;
        VirtualServiceVO objVirtualServiceVO = null;
        Query virtualQuery = null;
        try {
            virtualSession = getHibernateSession();
            virtualQuery = virtualSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_VIRTUALS_TITLES.toString());
            virtualQuery.setParameter(RMDServiceConstants.COLUMN_NAME,
                    RMDServiceConstants.PERCENTAGE + strVirtualTitles + RMDServiceConstants.PERCENTAGE);
            List<String> lstString = virtualQuery.list();
            arlVirtualServiceVO = new ArrayList<VirtualServiceVO>();
            if (RMDCommonUtility.isCollectionNotEmpty(lstString)) {
                for (String strVirtualTitle : lstString) {
                    objVirtualServiceVO = new VirtualServiceVO();
                    objVirtualServiceVO.setStrDisplayName(strVirtualTitle);
                    arlVirtualServiceVO.add(objVirtualServiceVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.error("ERROR in getVirtualTitles method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_TITLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("ERROR in getVirtualTitles method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_TITLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return arlVirtualServiceVO;
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     * @Description: This method is used to fetch virtual families
     */
    @Override
    public List<VirtualServiceVO> getVirtualFamily(String strLanguage) {
        List<VirtualServiceVO> arlVirtualServiceVO = null;
        Session virtualSession = null;
        VirtualServiceVO objVirtualServiceVO = null;
        Query virtualQuery = null;
        try {
            virtualSession = getHibernateSession();
            virtualQuery = virtualSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_VIRTUALS_FAMILY.toString());
            List<String> lstString = virtualQuery.list();
            arlVirtualServiceVO = new ArrayList<VirtualServiceVO>();
            if (RMDCommonUtility.isCollectionNotEmpty(lstString)) {
                for (String family : lstString) {
                    objVirtualServiceVO = new VirtualServiceVO();
                    objVirtualServiceVO.setFamily(family);
                    arlVirtualServiceVO.add(objVirtualServiceVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error("ERROR in getVirtualFamily method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_FAMILY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("ERROR in getVirtualFamily method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_FAMILY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return arlVirtualServiceVO;
    }

    /**
     * @param objVirtualServiceVO
     * @return List
     * @throws RMDDAOException
     * @Description: This method is used to finding the virtuals based on the
     *               filter criteria.
     */
    @Override
    public List<VirtualServiceVO> searchVirtuals(VirtualServiceVO objVirtualServiceVO) throws RMDDAOException {
        Session virtualSession = null;
        String strFamily = null;
        String strStatus = null;
        String strVirtualId = null;
        String strVirtualTitle = null;
        String strVirtualLastUpdatedBy = null;
        String strVirtualCreatedBy = null;
        Date createdFromDate = null;
        Date createdToDate = null;
        Date lastUpdatedFromDate = null;
        Date lastUpdatedToDate = null;
        List<VirtualServiceVO> arlSearchResult = null;
        VirtualServiceVO objVirtualServiceVOResult = null;
        boolean isDefaultLoad = false;
        Query virtualQuery = null;
        StringBuilder objStringBuilder = new StringBuilder();
        List<Object> virtualSearchresult = new ArrayList<Object>();
        try {
            virtualSession = getHibernateSession();

            objStringBuilder.append(RMDServiceConstants.QueryConstants.SEARCH_VIRTUALS);
            strVirtualId = objVirtualServiceVO.getStrObjId();
            if (!RMDCommonUtility.isNullOrEmpty(strVirtualId)) {
                objStringBuilder.append("AND VIRTUAL.GETS_TOOL_VIRTUAL_SEQ_ID= :virtualId ");
            }
            strVirtualTitle = objVirtualServiceVO.getStrDescription();
            if (!RMDCommonUtility.isNullOrEmpty(strVirtualTitle)) {
				objStringBuilder
						.append(" AND COLNAME.COLUMN_NAME LIKE  (:virtualTitle) ").append(RMDCommonConstants.ESCAPE_LIKE);
            }

            strVirtualCreatedBy = objVirtualServiceVO.getCreatedBy();
            if (!RMDCommonUtility.isNullOrEmpty(strVirtualCreatedBy)) {
                // objStringBuilder.append(" AND VIRTUAL.CREATED_BY LIKE
                // :createdBy ");
                objStringBuilder.append(" AND VIRTUAL.CREATED_BY =:createdBy ");
            }

            strVirtualLastUpdatedBy = objVirtualServiceVO.getLastUpdatedBy();
            if (!RMDCommonUtility.isNullOrEmpty(strVirtualLastUpdatedBy)) {
                // objStringBuilder.append(" AND VIRTUAL.LAST_UPDATED_BY LIKE
                // :lastUpdatedBy ");
                objStringBuilder.append(" AND VIRTUAL.LAST_UPDATED_BY =:lastUpdatedBy ");
            }
            createdFromDate = objVirtualServiceVO.getCreationFromDate();
            createdToDate = objVirtualServiceVO.getCreationToDate();
            if (!RMDCommonUtility.checkNull(createdFromDate) && !RMDCommonUtility.checkNull(createdToDate)) {
                objStringBuilder.append(
                        " AND VIRTUAL.CREATION_DATE BETWEEN TO_DATE( :createdFromDate,'MM/DD/YYYY HH24:MI:SS') AND  TO_DATE(:createdToDate,'MM/DD/YYYY HH24:MI:SS') ");
            }
            lastUpdatedFromDate = objVirtualServiceVO.getLastUpdatedFromDate();
            lastUpdatedToDate = objVirtualServiceVO.getLastUpdatedToDate();
            if (!RMDCommonUtility.checkNull(lastUpdatedFromDate) && !RMDCommonUtility.checkNull(lastUpdatedToDate)) {
                objStringBuilder.append(
                        " AND VIRTUAL.LAST_UPDATED_DATE BETWEEN  TO_DATE( :lastUpdatedFromDate,'MM/DD/YYYY HH24:MI:SS') AND  TO_DATE(:lastUpdatedToDate,'MM/DD/YYYY HH24:MI:SS')  ");
            }
            strFamily = objVirtualServiceVO.getFamily();
            if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
                objStringBuilder.append("	AND VIRTUAL.FAMILY_CD IN (:Family) ");
            }

            isDefaultLoad = objVirtualServiceVO.isDefaultLoad();
            // if defaultload is true the bring 30 days old record
            if (isDefaultLoad) {
                objStringBuilder.append(" AND VIRTUAL.LAST_UPDATED_DATE BETWEEN sysdate-"
                        + RMDCommonConstants.Numeric_30_DAYS + " AND sysdate ");
            }

            objStringBuilder.append(" ORDER BY LAST_UPDATED_DATE DESC )");
            strStatus = objVirtualServiceVO.getStatus();
            if (!RMDCommonUtility.isNullOrEmpty(strStatus)) {
                if (strStatus.equalsIgnoreCase(RMDCommonConstants.ACTIVE)) {
                    objStringBuilder.append(" WHERE status = 1");
                } else if (strStatus.equalsIgnoreCase(RMDCommonConstants.DEACTIVE)) {
                    objStringBuilder.append("WHERE status <> 1 ");
                }
            }

            virtualQuery = virtualSession.createSQLQuery(objStringBuilder.toString());
            if (!RMDCommonUtility.isNullOrEmpty(strVirtualId)) {
                virtualQuery.setParameter(RMDServiceConstants.VIRTUAL_ID, strVirtualId);
            }
            if (!RMDCommonUtility.isNullOrEmpty(strVirtualTitle)) {
                virtualQuery.setParameter(RMDServiceConstants.VIRTUAL_TITLE,
						RMDServiceConstants.PERCENTAGE + AppSecUtil.escapeLikeCharacters(strVirtualTitle)
								+ RMDServiceConstants.PERCENTAGE);
            }
            if (!RMDCommonUtility.isNullOrEmpty(strVirtualCreatedBy)) {
                virtualQuery.setParameter(RMDServiceConstants.CREATED_BY, strVirtualCreatedBy);
            }
            if (!RMDCommonUtility.isNullOrEmpty(strVirtualLastUpdatedBy)) {
                virtualQuery.setParameter(RMDServiceConstants.LAST_UPDATED_BY, strVirtualLastUpdatedBy);
            }
            if (!RMDCommonUtility.checkNull(createdFromDate) && !RMDCommonUtility.checkNull(createdToDate)) {
                String strCreatedFromDate = RMDCommonUtility.formatDate(createdFromDate,
                        RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                String strCreatedToDate = RMDCommonUtility.formatDate(createdToDate,
                        RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                virtualQuery.setParameter(RMDServiceConstants.createdFromDate, strCreatedFromDate);
                virtualQuery.setParameter(RMDServiceConstants.createdToDate, strCreatedToDate);
            }
            if (!RMDCommonUtility.checkNull(lastUpdatedFromDate) && !RMDCommonUtility.checkNull(lastUpdatedToDate)) {
                String strlastUpdatedFromDate = RMDCommonUtility.formatDate(lastUpdatedFromDate,
                        RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                String strlastUpdatedToDate = RMDCommonUtility.formatDate(lastUpdatedToDate,
                        RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                virtualQuery.setParameter(RMDServiceConstants.LastUpdatedFromDate, strlastUpdatedFromDate);
                virtualQuery.setParameter(RMDServiceConstants.LastUpdateToDate, strlastUpdatedToDate);
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
                String strFamilyArray[] = strFamily.split(RMDCommonConstants.COMMMA_SEPARATOR);
                virtualQuery.setParameterList(RMDServiceConstants.FAMILY, strFamilyArray);
            }

            virtualSearchresult = virtualQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(virtualSearchresult)) {
                arlSearchResult = new ArrayList<VirtualServiceVO>();
                for (Iterator<Object> virtualIter = virtualSearchresult.iterator(); virtualIter.hasNext();) {
                    objVirtualServiceVOResult = new VirtualServiceVO();
                    Object[] objVirtual = (Object[]) virtualIter.next();
                    objVirtualServiceVOResult.setStrObjId(RMDCommonUtility.convertObjectToString(objVirtual[0]));
                    objVirtualServiceVOResult.setStrDisplayName(RMDCommonUtility.convertObjectToString(objVirtual[1]));
                    objVirtualServiceVOResult.setLastUpdatedBy(RMDCommonUtility.convertObjectToString(objVirtual[2]));
                    try {
                        objVirtualServiceVOResult.setLastUpdatedDate(
                                RMDCommonUtility.stringToDate(RMDCommonUtility.convertObjectToString(objVirtual[3]),
                                        RMDCommonConstants.DateConstants.YYYYMMDDHHMMSS));
                    } catch (Exception e) {
                        LOG.error("Exception while parsing date: ", e);
                    }
                    objVirtualServiceVOResult.setCreatedBy(RMDCommonUtility.convertObjectToString(objVirtual[4]));
                    try {
                        objVirtualServiceVOResult.setCreationDate(
                                RMDCommonUtility.stringToDate(RMDCommonUtility.convertObjectToString(objVirtual[5]),
                                        RMDCommonConstants.DateConstants.YYYYMMDDHHMMSS));
                    } catch (Exception e) {
                        LOG.error("Exception while parsing date: ", e);
                    }
                    objVirtualServiceVOResult.setFamily(RMDCommonUtility.convertObjectToString(objVirtual[6]));
                    // strColumnObjid=RMDCommonUtility.convertObjectToString(objVirtual[7]);
                    String strRecStatus = RMDCommonUtility.convertObjectToString(objVirtual[7]);
                    if (strRecStatus.equals("1"))
                        objVirtualServiceVOResult.setStatus(RMDServiceConstants.ACTIVE);
                    else
                        objVirtualServiceVOResult.setStatus(RMDServiceConstants.DEACTIVE);

                    arlSearchResult.add(objVirtualServiceVOResult);

                }
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error("ERROR in searchVirtual method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SEARCH_VIRTUAL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objVirtualServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("ERROR in searchVirtual method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SEARCH_VIRTUAL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objVirtualServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return arlSearchResult;
    }

    /**
     * @Author:iGATE
     * @param: strQuery,hibernateSession
     * @return seqID
     * @throws @Description:This
     *             method is used to generate the Sequence Id
     */

    private String getSequenceId(String strQuery, Session hibernateSession) {
        String strSeqId = null;
        Query simRuleQuery;
        BigDecimal bdSeqId;
        try {

            if (!RMDCommonUtility.isNullOrEmpty(strQuery) && !RMDCommonUtility.checkNull(hibernateSession)) {
                simRuleQuery = hibernateSession.createSQLQuery(strQuery);
                List<BigDecimal> arlSimplRuleSeq = simRuleQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlSimplRuleSeq)) {
                    bdSeqId = arlSimplRuleSeq.get(0);
                    strSeqId = bdSeqId.toString();
                }
            }
            return strSeqId;
        } catch (RMDDAOConnectionException objRMDDAOConnectionException) {
            throw objRMDDAOConnectionException;
        }
    }

    /***
     * @param FinalVirtualServiceVO
     *            objFinalVirtualServiceVO
     * @throws RMDDAOException
     * @Description: This method will update the existing Virtuals by
     */
    @Override
    public String updateVirtual(FinalVirtualServiceVO objFinalVirtualServiceVO) throws RMDDAOException {
        String strLanguage = null;
        String strVirtualSeqId = RMDCommonConstants.EMPTY_STRING;
        Session virtualSession = null;
        List<VirtualEquationServiceVO> arlVirtualEquation = null;
        Query virtualQuery = null;
        String strEquationDescTxt = RMDCommonConstants.EMPTY_STRING;
        String strEquation = null;
        int lookbackPoints = 0;
        int lookbackTime = 0;
        String strEquationSeqId = RMDCommonConstants.EMPTY_STRING;
        String strVirtualType = null;
        int virtUpdate = 0;
        int virtEqUpdate = 0;
        try {
            strLanguage = objFinalVirtualServiceVO.getStrLanguage();
            strVirtualSeqId = objFinalVirtualServiceVO.getStrFinalVirtualId();
            virtualSession = getHibernateSession(objFinalVirtualServiceVO.getStrUserName());
            strEquationDescTxt = AppSecUtil
                    .stripNonValidXMLCharactersForKM(objFinalVirtualServiceVO.getStrVirtualDesc());
            // To avoid null insertion, Need to remove later
            // if (RMDCommonUtility.isNullOrEmpty(strEquationDescTxt)) {
            // strEquationDescTxt = "Empty Desc";
            // }
            // Updating the Virtual Table Starts
            virtualQuery = virtualSession
                    .createSQLQuery(RMDServiceConstants.QueryConstants.UPDATE_TOOLS_VIRTUAL_BUILDER.toString());
            virtualQuery.setParameter(RMDServiceConstants.VIRTUAL_OBJID, strVirtualSeqId);
            // virtualQuery.setParameter(RMDServiceConstants.EQUATION_DESC_TXT,AppSecUtil.decodeString(strEquationDescTxt));
            virtualQuery.setParameter(RMDServiceConstants.EQUATION_DESC_TXT, (strEquationDescTxt));
            virtualQuery.setParameter(RMDServiceConstants.ACTIVE_FLAG, RMDCommonConstants.LETTER_Y);
            // Inserting Admin as a default value here, need to remove later
            virtualQuery.setParameter(RMDServiceConstants.LAST_UPDATED_BY, objFinalVirtualServiceVO.getStrUserName());

            virtualQuery.setParameter(RMDServiceConstants.ROW_VERSION, 1);
            virtUpdate = virtualQuery.executeUpdate();
            virtualQuery = null;

            // UPDATING THE Equation AGAIN

            arlVirtualEquation = objFinalVirtualServiceVO.getArlVirtualEquation();
            if (RMDCommonUtility.isCollectionNotEmpty(arlVirtualEquation)) {
                for (VirtualEquationServiceVO virtualEquationServiceVO : arlVirtualEquation) {
                    // Calculating Equation
                    strEquation = virtualEquationServiceVO.getStrEquation();
                    strEquationSeqId = virtualEquationServiceVO.getStrEquationId();
                    // strEquation=AppSecUtil.decodeString(strEquation);
                    if (!RMDCommonUtility.isNullOrEmpty(virtualEquationServiceVO.getStrLookBackTime())) {
                        lookbackTime = Integer.parseInt(virtualEquationServiceVO.getStrLookBackTime());
                    } else {
                        lookbackTime = 0;
                    }

                    if (!RMDCommonUtility.isNullOrEmpty(virtualEquationServiceVO.getStrLookBackPoints())) {
                        lookbackPoints = Integer.parseInt(virtualEquationServiceVO.getStrLookBackPoints());
                    } else {
                        lookbackPoints = 0;
                    }
                    if (lookbackPoints > 0 && lookbackTime > 0) {
                        strVirtualType = RMDServiceConstants.STR_C;
                    } else {
                        strVirtualType = RMDServiceConstants.STR_S;
                    }

                    // strEquationSeqId=getSequenceId(RMDServiceConstants.QueryConstants.SELECT_EQUATION_SEQ,
                    // virtualSession);
                    // INSERT INTO GETS_TOOLS.GETS_TOOL_EQUATION
                    virtualQuery = virtualSession.createSQLQuery(
                            RMDServiceConstants.QueryConstants.UPDATE_TOOLS_EQUATION_BUILDER.toString());
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_OBJID, strEquationSeqId);
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_TXT, strEquation);
                    // virtualQuery.setParameter(RMDServiceConstants.EQUATION_DESC_TXT,
                    // AppSecUtil.decodeString(strEquationDescTxt));
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_DESC_TXT,
                            (virtualEquationServiceVO.getStrEquationDescTxt()));
                    virtualQuery.setParameter(RMDServiceConstants.CUSTOM_PROCESS_FILE,
                            virtualEquationServiceVO.getStrCustomFileName());
                    // virtualQuery.setParameter(RMDServiceConstants.CUSTOM_PROCESS_FILE,
                    // AppSecUtil.decodeString(virtualEquationServiceVO.getStrCustomFileName()));
                    // virtualQuery.setParameter(RMDServiceConstants.LOOKBACKTIME,
                    // virtualEquationServiceVO.getStrLookBackTime());
                    virtualQuery.setParameter(RMDServiceConstants.LOOKBACKTIME, lookbackTime);
                    // virtualQuery.setParameter(RMDServiceConstants.LOOK_BACK_FAULT_COUNT,
                    // virtualEquationServiceVO.getStrLookBackPoints());
                    virtualQuery.setParameter(RMDServiceConstants.LOOK_BACK_FAULT_COUNT, lookbackPoints);
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_TYPE, strVirtualType);
                    // virtualQuery.setParameter(RMDServiceConstants.EQUATION_JSON_TXT,
                    // AppSecUtil.decodeString(virtualEquationServiceVO.getStrEquationJSONTxt()));
                    virtualQuery.setParameter(RMDServiceConstants.EQUATION_JSON_TXT,
                            virtualEquationServiceVO.getStrEquationJSONTxt());
                    virtualQuery.setParameter(RMDServiceConstants.PARENT_EQUATION_SEQ_ID, 0);
                    virtualQuery.setParameter(RMDServiceConstants.ROW_VERSION, 1);
                    virtualQuery.setParameter(RMDServiceConstants.LAST_UPDATED_BY,
                            objFinalVirtualServiceVO.getStrUserName());

                    virtEqUpdate = virtualQuery.executeUpdate();
                }
            }
            /*Added by Koushik as part of OMDKM-1869 - Add Revision Notes and History to Virtual Parameters */
            if(objFinalVirtualServiceVO.getRevisionNote() != null && (virtUpdate >0 || virtEqUpdate >0)){
            	StringBuilder virtHistQuery = new StringBuilder();
            	virtHistQuery.append("INSERT INTO GETS_TOOLS.GETS_TOOL_VIRTUAL_HIST(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,VIRTUAL_HIST2VIRTUAL,VERSION,NOTES) ");
    			virtHistQuery.append("VALUES (GETS_TOOL_VIRTUAL_HIST_SEQ.nextval, SYSDATE, :lastUpdatedBy,SYSDATE, :createdBy, :virtualObjid, (select (NVL(MAX(VERSION), 0)+1) from GETS_TOOLS.GETS_TOOL_VIRTUAL_HIST WHERE VIRTUAL_HIST2VIRTUAL = :virtualObjid), :note)");
            	Query virtualHistQuery = virtualSession.createSQLQuery(virtHistQuery.toString());
            	virtualHistQuery.setParameter(RMDServiceConstants.VIRTUAL_OBJID, strVirtualSeqId);
            	virtualHistQuery.setParameter(RMDServiceConstants.VIRTUAL_HIST_UPDATED_BY, objFinalVirtualServiceVO.getStrUserName());
            	virtualHistQuery.setParameter(RMDServiceConstants.VIRTUAL_HIST_CREATED_BY, objFinalVirtualServiceVO.getStrUserName());
            	virtualHistQuery.setParameter(RMDServiceConstants.VIRTUAL_HIST_NOTE, objFinalVirtualServiceVO.getRevisionNote());
            	virtualHistQuery.executeUpdate();
            }
            /*End of OMDKM-1869*/

        } catch (RMDDAOConnectionException ex) {
            LOG.error("ERROR in saveVirtual method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_VIRTUAL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("ERROR in saveVirtual method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_VIRTUAL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return strVirtualSeqId;
    }

    private String getVirtualColumn(String strQuery, Session hibernateSession) {
        String strSeqId = null;
        Query simRuleQuery;
        try {

            if (!RMDCommonUtility.isNullOrEmpty(strQuery) && !RMDCommonUtility.checkNull(hibernateSession)) {
                simRuleQuery = hibernateSession.createSQLQuery(strQuery);
                List<String> arlSimplRuleSeq = simRuleQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlSimplRuleSeq)) {
                    strSeqId = arlSimplRuleSeq.get(0);
                }
            }
            return strSeqId;
        } catch (RMDDAOConnectionException objRMDDAOConnectionException) {
            throw objRMDDAOConnectionException;
        }
    }

    @Override
    public String isVirtualColumnExist() throws RMDDAOException {

        String strVirtualColumn = "true";
        Session virtualSession = null;
        String strLanguage = null;

        try {
            virtualSession = getHibernateSession();
            strVirtualColumn = getVirtualColumn(
                    RMDServiceConstants.QueryConstants.SELECT_VIRTUAL_COLUMN_NAME.toString(), virtualSession);
            if (null == strVirtualColumn) {
                strVirtualColumn = "false";
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.error("ERROR in isVirtualColumnExist method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_FAMILY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("ERROR in isVirtualColumnExist method of VirtualDAOImpl");
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIRTUAL_FAMILY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(virtualSession);
        }
        return strVirtualColumn;
    } // Do you want to save this recommendation

    @Override
    public List<ElementVO> getVirtualActiveRules(int virtualId, String family) throws RMDBOException {
        ArrayList<ElementVO> lstLookupValues = null;
        Session session = null;
        Query sqlQuery = null;
        StringBuilder stringBuilder = null;
        try {
            session = getHibernateSession();
            stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT distinct finrule.objid,finrule.rule_title ");
            stringBuilder.append(
                    "FROM gets_tool_dpd_ruledef ruledef, gets_tool_dpd_finrul finrule, GETS_TOOL_DPD_RULHIS rulehis ");
            stringBuilder.append(
                    "where ruledef.RULEDEF2FINRUL = finrule.objid AND rulehis.ACTIVE = 1 AND rulehis.RULHIS2FINRUL = finrule.objid ");
            stringBuilder.append(
                    "and finrule.objid in ( SELECT simrul2finrul FROM GETS_TOOLS.GETS_TOOL_DPD_SIMFEA SIMFEA,GETS_TOOLS.GETS_TOOL_DPD_SIMRUL SIMRUL, ");
            stringBuilder.append(
                    "gets_tools.GETS_TOOL_VIRTUAL v, gets_tool_dpd_colname c WHERE c.OBJID = SIMFEA.SIMFEA2COLNAME and v.link_dpd_column_name= c.objid ");
            stringBuilder.append(
                    "AND SIMFEA.SIMFEA2SIMRUL = SIMRUL.OBJID and gets_tool_virtual_seq_id = :virtualId and c.family = :family )");

            sqlQuery = session.createSQLQuery(stringBuilder.toString());
            sqlQuery.setParameter(RMDServiceConstants.VIRTUAL_ID, virtualId);
            sqlQuery.setParameter("family", family);
            List<Object> lookList = sqlQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookList)) {
                lstLookupValues = new ArrayList<ElementVO>();
                Object[] lookupRecord = (Object[]) lookList.get(0);
                lstLookupValues.add(new ElementVO(lookupRecord[0].toString(), lookupRecord[1].toString()));
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, null), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, null), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return lstLookupValues;

    }

}

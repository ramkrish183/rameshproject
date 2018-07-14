package com.ge.trans.eoa.services.asset.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.owasp.esapi.ESAPI;

import com.ge.trans.eoa.services.asset.dao.intf.RepairFacilityDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CustomerSiteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RepairFacilityDetailsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class RepairFacilityDAOImpl extends BaseDAO implements RepairFacilityDAOIntf {

    public static final RMDLogger LOG = RMDLogger.getLogger(NotesEoaDAOImpl.class);

    /**
     * @Author :
     * @return :List<CustomerSiteVO>
     * @param :
     *            String customerId
     * @throws :RMDDAOException
     * @Description: This method is used for fetching the sites for the selected
     *               customer.
     */
    @Override
    public List<CustomerSiteVO> getCustomerSites(String customerId) throws RMDDAOException {
        Session session = null;
        StringBuilder custSiteQuery = new StringBuilder();
        List<CustomerSiteVO> custSiteVOList = new ArrayList<CustomerSiteVO>();
        CustomerSiteVO siteVO = null;
        try {
            session = getHibernateSession();
            custSiteQuery.append("SELECT S_NAME,OBJID FROM TABLE_SITE WHERE PRIMARY2BUS_ORG = (SELECT OBJID FROM ");
            custSiteQuery.append("TABLE_BUS_ORG  WHERE ORG_ID = :customerId) AND STATUS='0' ORDER BY S_NAME");
            final Query custSiteHQuery = session.createSQLQuery(custSiteQuery.toString());
            custSiteHQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, customerId);
            List<Object> resultList = custSiteHQuery.list();
            if (resultList != null && !resultList.isEmpty()) {
                custSiteVOList = new ArrayList<CustomerSiteVO>(resultList.size());
            }
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (final Iterator<Object> obj = resultList.iterator(); obj.hasNext();) {
                    final Object[] siteDetails = (Object[]) obj.next();
                    siteVO = new CustomerSiteVO();
                    siteVO.setSiteName(RMDCommonUtility.convertObjectToString(siteDetails[0]));
                    siteVO.setSiteObjId(RMDCommonUtility.convertObjectToString(siteDetails[1]));
                    custSiteVOList.add(siteVO);
                }
            }
            resultList = null;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CUSTOMER_SITES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CUSTOMER_SITES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);
        }
        return custSiteVOList;
    }

    /**
     * @param String
     *            customerId, String site, String status
     * @return List<RepairFacilityDetailsResponseType>
     * @throws RMDDAOException
     * @Description This method is used to get the Repair Facility Details.
     */
    @Override
    public List<RepairFacilityDetailsVO> getRepairFacilityDetails(String customerId, String site, String status)
            throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        Query repairDetailsHQry = null;
        RepairFacilityDetailsVO objRepairDetails = null;
        List<RepairFacilityDetailsVO> repairDetailsList = new ArrayList<RepairFacilityDetailsVO>();
        try {
            session = getHibernateSession();
            StringBuilder repairDetailsQry = new StringBuilder();
            if (RMDCommonConstants.ALL_SITES.equalsIgnoreCase(site)) {
                repairDetailsQry.append(
                        "SELECT GSSL.OBJID,GSSL.CODE,GSSL.DESCRIPTION,GSSL.CLOSE_LOC_CODE,GSSL.STATUS FROM GETS_SD_SITE_LOC GSSL,");
                repairDetailsQry.append(
                        "TABLE_SITE TS,TABLE_BUS_ORG TBO  WHERE GSSL.SITE_LOC2SITE = TS.OBJID  AND TS.PRIMARY2BUS_ORG = TBO.OBJID ");
                repairDetailsQry.append("AND TBO.ORG_ID=:customerId AND TS.STATUS='0'");
                if (null != status && !RMDCommonConstants.ALL.equalsIgnoreCase(status)) {
                    repairDetailsQry.append(" AND GSSL.STATUS = :status");
                }
                repairDetailsQry.append(" ORDER BY TS.S_NAME,GSSL.DESCRIPTION");
            } else {
                repairDetailsQry.append(
                        "SELECT OBJID,CODE,DESCRIPTION,CLOSE_LOC_CODE,STATUS FROM GETS_SD_SITE_LOC WHERE SITE_LOC2SITE=:site");
                if (null != status && !RMDCommonConstants.ALL.equalsIgnoreCase(status)) {
                    repairDetailsQry.append(" AND STATUS = :status");
                }
                repairDetailsQry.append(" ORDER BY DESCRIPTION");
            }
            repairDetailsHQry = session.createSQLQuery(repairDetailsQry.toString());
            if (RMDCommonConstants.ALL_SITES.equalsIgnoreCase(site)) {
                repairDetailsHQry.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId);
            } else
                repairDetailsHQry.setParameter(RMDCommonConstants.SITE, site);
            if (null != status && !RMDCommonConstants.ALL.equalsIgnoreCase(status)) {
                repairDetailsHQry.setParameter(RMDCommonConstants.STATUS, status);
            }
            resultList = repairDetailsHQry.list();
            if (resultList != null && !resultList.isEmpty()) {
                repairDetailsList = new ArrayList<RepairFacilityDetailsVO>(resultList.size());
            }
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (final Iterator<Object[]> obj = resultList.iterator(); obj.hasNext();) {
                    final Object[] repairDetails = obj.next();
                    objRepairDetails = new RepairFacilityDetailsVO();
                    objRepairDetails.setObjId(RMDCommonUtility.convertObjectToString(repairDetails[0]));
                    objRepairDetails.setRailWayDesigCode(
                            ESAPI.encoder().encodeForXML(EsapiUtil.escapeSpecialChars(RMDCommonUtility.convertObjectToString(repairDetails[1]))));
                    objRepairDetails.setRepairLocation(
                            ESAPI.encoder().encodeForXML(EsapiUtil.escapeSpecialChars(RMDCommonUtility.convertObjectToString(repairDetails[2]))));
                    objRepairDetails.setLocationCode(
                            ESAPI.encoder().encodeForXML(EsapiUtil.escapeSpecialChars(RMDCommonUtility.convertObjectToString(repairDetails[3]))));
                    if (RMDCommonConstants.ACTIVE_REPAIR_RAILWAY_CODE
                            .equalsIgnoreCase(RMDCommonUtility.convertObjectToString(repairDetails[4]))) {
                        objRepairDetails.setStatus(RMDCommonConstants.RAILWAY_CODE_ACTIVE);
                    } else {
                        objRepairDetails.setStatus(RMDCommonConstants.RAILWAY_CODE_INACTIVE);
                    }
                    repairDetailsList.add(objRepairDetails);
                }
            }
            resultList = null;
        } catch (Exception e) {
            LOG.error("Exception occurred in getRepairFacilityDetails()  method of RepairFacilityDAOImpl :", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_GET_REPAIR_FACILITY_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return repairDetailsList;
    }

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to insert new repair/site location
     *              details.
     */
    @Override
    public String insertRepairSiteLoc(RepairFacilityDetailsVO objRepairDetailsVO) throws RMDDAOException {
        Session session = null;
        String repairStatus = null;
        String checkExistRailCodeStatus = null;
        String result = RMDCommonConstants.SUCCESS;
        StringBuilder insertQuery = new StringBuilder();
        try {
            session = getHibernateSession();
            repairStatus = objRepairDetailsVO.getStatus();
            if (RMDCommonConstants.ACTIVE_REPAIR_RAILWAY_CODE.equalsIgnoreCase(repairStatus)) {
                checkExistRailCodeStatus = checkActiveDupRailWayDesigCode(objRepairDetailsVO.getCustomerId(),
                        objRepairDetailsVO.getRailWayDesigCode());
                if (null != checkExistRailCodeStatus && RMDServiceConstants.ACTIVE_RAILWAY_DESIG_CODE_EXISTS
                        .equalsIgnoreCase(checkExistRailCodeStatus)) {
                    result = RMDServiceConstants.ACTIVE_RAILWAY_DESIG_CODE_EXISTS;
                }
            }
            if (!RMDCommonConstants.ACTIVE_REPAIR_RAILWAY_CODE.equalsIgnoreCase(repairStatus)
                    || !RMDServiceConstants.ACTIVE_RAILWAY_DESIG_CODE_EXISTS.equalsIgnoreCase(result)) {
                insertQuery.append(
                        "INSERT INTO GETS_SD_SITE_LOC(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,CODE,");
                insertQuery.append(
                        "DESCRIPTION,CLOSE_LOC_CODE,STATUS,SITE_LOC2SITE,SITE_LOC_RMDGRP2SITE) VALUES (GETS_SD_SITE_LOC_SEQ.NEXTVAL,SYSDATE,");
                insertQuery.append(
                        ":userName,SYSDATE,:userName,:railWayDesigCode,:repairLocation,:locationCode,:status,:siteObjId,:siteObjId)");
                Query insertHQuery = session.createSQLQuery(insertQuery.toString());
                insertHQuery.setParameter(RMDCommonConstants.USER_NAME, objRepairDetailsVO.getUserName());
                insertHQuery.setParameter(RMDCommonConstants.RAILWAY_DESIG_CODE, objRepairDetailsVO.getRailWayDesigCode());
                insertHQuery.setParameter(RMDCommonConstants.REPAIR_LOCATION, objRepairDetailsVO.getRepairLocation());
                insertHQuery.setParameter(RMDCommonConstants.LOCATION_CODE,   objRepairDetailsVO.getLocationCode());
                insertHQuery.setParameter(RMDCommonConstants.STATUS, objRepairDetailsVO.getStatus());
                insertHQuery.setParameter(RMDCommonConstants.SITE_OBJ_ID, objRepairDetailsVO.getSite());
                insertHQuery.executeUpdate();
            }
        } catch (Exception e) {
            LOG.error("Exception occurred IN insertRepairSiteLoc method of RepairFacilityDAOImpl:", e);
            result = RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_INSERT_REPAIR_LOCATION_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }
        return result;
    }

    /**
     * @param List
     *            <RepairFacilityRequestType>
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to update existing repair/site location
     *              details.
     */
    @Override
    public String updateRepairSiteLoc(List<RepairFacilityDetailsVO> arlReapirDetailsVO) throws RMDDAOException {
        Session session = null;
        Transaction transaction = null;
        String currRailWayCodeStatus = null;
        StringBuilder updateQryWithStatus = new StringBuilder();
        StringBuilder updateQryWithOutStatus = new StringBuilder();
        String result = RMDServiceConstants.SUCCESS;
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();
            updateQryWithStatus
                    .append("UPDATE GETS_SD_SITE_LOC SET LAST_UPDATED_DATE = SYSDATE,LAST_UPDATED_BY = :userName,");
            updateQryWithStatus.append(
                    "DESCRIPTION = :repairLocation, CLOSE_LOC_CODE = :locationCode,STATUS = :status WHERE OBJID=:objId");
            updateQryWithOutStatus
                    .append("UPDATE GETS_SD_SITE_LOC SET LAST_UPDATED_DATE = SYSDATE,LAST_UPDATED_BY = :userName,");
            updateQryWithOutStatus
                    .append("DESCRIPTION = :repairLocation, CLOSE_LOC_CODE = :locationCode WHERE OBJID=:objId");

            for (RepairFacilityDetailsVO objRepairDetailsVO : arlReapirDetailsVO) {
                Query updateHQry = null;
                String checkStatus = null;
                currRailWayCodeStatus = objRepairDetailsVO.getStatus();

                if (RMDCommonConstants.ACTIVE_REPAIR_RAILWAY_CODE.equalsIgnoreCase(currRailWayCodeStatus)) {
                    checkStatus = checkActiveDupRailWayDesigCode(objRepairDetailsVO.getCustomerId(),
                            objRepairDetailsVO.getRailWayDesigCode());
                    if (null != checkStatus
                            && RMDServiceConstants.ACTIVE_RAILWAY_DESIG_CODE_EXISTS.equalsIgnoreCase(checkStatus)) {
                        updateHQry = session.createSQLQuery(updateQryWithOutStatus.toString());
                    } else {
                        updateHQry = session.createSQLQuery(updateQryWithStatus.toString());
                    }
                } else if (!RMDCommonConstants.ACTIVE_REPAIR_RAILWAY_CODE.equalsIgnoreCase(currRailWayCodeStatus)) {
                    updateHQry = session.createSQLQuery(updateQryWithStatus.toString());
                }

                updateHQry.setParameter(RMDCommonConstants.USER_NAME, objRepairDetailsVO.getUserName());

                updateHQry.setParameter(RMDCommonConstants.REPAIR_LOCATION, objRepairDetailsVO.getRepairLocation());
                updateHQry.setParameter(RMDCommonConstants.LOCATION_CODE,  objRepairDetailsVO.getLocationCode());
                if (!RMDCommonConstants.ACTIVE_REPAIR_RAILWAY_CODE.equalsIgnoreCase(currRailWayCodeStatus)
                        || !RMDServiceConstants.ACTIVE_RAILWAY_DESIG_CODE_EXISTS.equalsIgnoreCase(checkStatus)) {
                    updateHQry.setParameter(RMDCommonConstants.STATUS, objRepairDetailsVO.getStatus());
                }

                updateHQry.setParameter(RMDCommonConstants.OBJ_ID, objRepairDetailsVO.getObjId());
                updateHQry.executeUpdate();

            }
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            result = RMDServiceConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_UPDATE_REPAIR_LOCATION_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return result;
    }

    /**
     * @param String
     *            customerId,String railWayDesigCode
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to check duplicate active record exist
     *              for the give customer and railWayDesigCode
     */
    public String checkActiveDupRailWayDesigCode(String customerId, String railWayDesigCode) {
        String result = RMDServiceConstants.NO_ACTIVE_RAILWAY_DESIG_CODE_EXISTS;
        StringBuilder query = new StringBuilder();
        Session session = null;

        try {
            query.append(
                    "SELECT STATUS FROM GETS_SD_SITE_LOC WHERE STATUS ='0' AND SITE_LOC2SITE IN (SELECT TS.OBJID FROM TABLE_SITE TS,TABLE_BUS_ORG TBO");
            query.append(
                    " WHERE PRIMARY2BUS_ORG = TBO.OBJID AND TBO.ORG_ID=:customerId AND TS.STATUS='0')  AND CODE=:railWayDesigCode");
            session = getHibernateSession();
            Query hQry = session.createSQLQuery(query.toString());
            hQry.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId);
            hQry.setParameter(RMDCommonConstants.RAILWAY_DESIG_CODE, ESAPI.encoder().decodeForHTML(railWayDesigCode));
            ArrayList existRep = (ArrayList) hQry.list();
            if (null != existRep && !existRep.isEmpty()) {
                result = RMDServiceConstants.ACTIVE_RAILWAY_DESIG_CODE_EXISTS;
            }
        } catch (Exception e) {
            LOG.error("Exception occurred IN checkActiveDupRailWayDesigCode method of RepairFacilityDAOImpl:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_CHECK_ACTIVE_DUP_RAILWAY_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return result;
    }

}

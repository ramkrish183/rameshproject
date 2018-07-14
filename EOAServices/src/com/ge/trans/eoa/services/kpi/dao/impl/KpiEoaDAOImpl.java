package com.ge.trans.eoa.services.kpi.dao.impl;

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.EMPTY_STRING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.kpi.dao.intf.KpiEoaDAOIntf;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataCountResponseEoaVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataEoaBean;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountEoaServiceVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountResponseVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.RxUrgencyInfoEoaVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class KpiEoaDAOImpl extends RMDCommonDAO implements KpiEoaDAOIntf {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(KpiEoaDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.kpi.dao.intf.KpiDAOIntf#getAssetKPICounts(com
     * .ge.trans.rmd.services.kpi.service.valueobjects.KpiAssetCountServiceVO)
     * This method is used to get red,yellow and white rxs
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<KpiAssetCountResponseVO> getAssetKPICounts(final KpiAssetCountEoaServiceVO kpiAssetCountVO)
            throws RMDDAOException, RMDDAOConnectionException {
        LOG.debug("KpiDAOImpl : Inside getAssetKPICounts() method:::START");
        Session hibernateSession = null;
        final StringBuilder strQuery = new StringBuilder();
        List<KpiAssetCountResponseVO> kpiAsstCountResVO = null;
        try {
            hibernateSession = getHibernateSession(EMPTY_STRING);

            strQuery.append("SELECT TBO.ORG_ID,");
            strQuery.append("TSP.X_VEH_HDR,");
            strQuery.append("TSP.SERIAL_NO,");
            strQuery.append("CASERECOM.URGENCY,");
            strQuery.append("COUNT(DELV.OBJID)");
            strQuery.append(" FROM TABLE_CASE C,");
            strQuery.append("GETS_SD_CASE_RECOM CASERECOM,");
            strQuery.append("GETS_SD_RECOM_DELV DELV,");
            strQuery.append("TABLE_SITE_PART TSP,");
            strQuery.append("GETS_RMD_VEHICLE VEH,");
            strQuery.append("GETS_RMD_VEH_HDR VEHHDR,");
            strQuery.append("TABLE_BUS_ORG TBO");
            strQuery.append(" WHERE C.CASE_PROD2SITE_PART         = TSP.OBJID");
            strQuery.append(" AND CASERECOM.CASE_RECOM2CASE       = C.OBJID");
            strQuery.append(" AND CASERECOM.CASE_RECOM2RECOM_DELV = DELV.OBJID");
            strQuery.append(" AND TSP.OBJID                       = VEH.VEHICLE2SITE_PART");
            strQuery.append(" AND VEH.VEHICLE2VEH_HDR             = VEHHDR.OBJID");
            strQuery.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%'");
            strQuery.append(" AND VEHHDR.VEH_HDR2BUSORG           = TBO.OBJID");

            if (kpiAssetCountVO.getNumDays() != null) {
                strQuery.append(" AND delv.creation_date  > SYSDATE - :numDays");
            }

            if (null != kpiAssetCountVO.getCustomerId() && kpiAssetCountVO.getCustomerId().length() > 0) {
                strQuery.append(" AND TBO.ORG_ID      =:customerId");
            }

            if (null != kpiAssetCountVO.getAssetNumber() && kpiAssetCountVO.getAssetNumber().length() > 0) {
                strQuery.append(" AND TSP.SERIAL_NO      =:assetNumber");
            }

            strQuery.append(" GROUP BY TBO.ORG_ID, TSP.X_VEH_HDR, TSP.SERIAL_NO, CASERECOM.URGENCY");

            final Query query = hibernateSession.createSQLQuery(strQuery.toString());

            if (kpiAssetCountVO.getNumDays() != null) {
                query.setParameter(RMDCommonConstants.NUMDAYS, kpiAssetCountVO.getNumDays());
            }

            if (null != kpiAssetCountVO.getCustomerId() && kpiAssetCountVO.getCustomerId().length() > 0) {
                query.setParameter(RMDCommonConstants.CUSTOMER_ID, kpiAssetCountVO.getCustomerId());
            }

            if (null != kpiAssetCountVO.getAssetNumber() && kpiAssetCountVO.getAssetNumber().length() > 0) {
                query.setParameter(RMDCommonConstants.ASSET_NUMBER, kpiAssetCountVO.getAssetNumber());
            }

            final List<Object> lsKpiCounts = query.list();
            if (null != lsKpiCounts && !lsKpiCounts.isEmpty()) {
                kpiAsstCountResVO = new ArrayList<KpiAssetCountResponseVO>();
                KpiAssetCountResponseVO kpiAsstRespVO = null;
                List<RxUrgencyInfoEoaVO> lsUrgInfoVO = null;
                final Map<String, List<RxUrgencyInfoEoaVO>> rxInfoMap = new HashMap<String, List<RxUrgencyInfoEoaVO>>();
                String rxAssetNumber = null;
                String rxCustomerId = null;
                String rxAssetGrpName = null;
                String rxUrgencyType = null;
                StringBuilder rxKey = null;
                Long rxCount = null;

                for (final Iterator<Object> iter = lsKpiCounts.iterator(); iter.hasNext();) {
                    final Object[] ObjKpiCount = (Object[]) iter.next();

                    rxCustomerId = RMDCommonUtility.convertObjectToString(ObjKpiCount[0]);
                    rxAssetGrpName = RMDCommonUtility.convertObjectToString(ObjKpiCount[1]);
                    rxAssetNumber = RMDCommonUtility.convertObjectToString(ObjKpiCount[2]);
                    rxUrgencyType = RMDCommonUtility.convertObjectToString(ObjKpiCount[3]);
                    rxKey = new StringBuilder();
                    rxCount = RMDCommonUtility.convertObjectToLong(ObjKpiCount[4]);
                    /*
                     * key will be created as a combination of
                     * rxAssetNumber+"|"+rxCustomerId+"|"+rxAssetGrpName
                     */
                    rxKey.append(rxAssetNumber + RMDCommonConstants.PIPELINE_CHARACTER + rxCustomerId
                            + RMDCommonConstants.PIPELINE_CHARACTER + rxAssetGrpName);

                    final RxUrgencyInfoEoaVO urgInfoVO = new RxUrgencyInfoEoaVO();
                    urgInfoVO.setRxUrgencyType(rxUrgencyType);
                    urgInfoVO.setRxCount(rxCount);

                    if (rxInfoMap.containsKey(rxKey.toString())) {
                        lsUrgInfoVO = rxInfoMap.get(rxKey.toString());
                        boolean prevEntryFound = false;
                        for (RxUrgencyInfoEoaVO eachUrgInfoVO : lsUrgInfoVO) {
                            if (eachUrgInfoVO.getRxUrgencyType().equalsIgnoreCase(rxUrgencyType)) {
                                prevEntryFound = true;
                                eachUrgInfoVO.setRxCount(eachUrgInfoVO.getRxCount() + rxCount);
                            }
                        }
                        if (prevEntryFound == false) {
                            lsUrgInfoVO.add(urgInfoVO);
                        }
                        rxInfoMap.put(rxKey.toString(), lsUrgInfoVO);
                    } else {
                        lsUrgInfoVO = new ArrayList<RxUrgencyInfoEoaVO>();
                        lsUrgInfoVO.add(urgInfoVO);
                        rxInfoMap.put(rxKey.toString(), lsUrgInfoVO);
                    }
                }

                for (Map.Entry<String, List<RxUrgencyInfoEoaVO>> entry : rxInfoMap.entrySet()) {
                    String key = entry.getKey();
                    List<RxUrgencyInfoEoaVO> rxUrgencyInfoVO = entry.getValue();
                    Long totalCount = Long.valueOf(0);

                    /*
                     * key was created with the combination of
                     * rxAssetNumber+"|"+rxCustomerId+"|"+rxAssetGrpName
                     */
                    String[] assetInfo = key.split(RMDCommonConstants.PIPELINE_ESCAPE);

                    for (RxUrgencyInfoEoaVO urgInfoVO : rxUrgencyInfoVO) {
                        totalCount += urgInfoVO.getRxCount();
                    }

                    kpiAsstRespVO = new KpiAssetCountResponseVO();
                    kpiAsstRespVO.setAssetNumber(assetInfo[0]);
                    kpiAsstRespVO.setCustomerId(assetInfo[1]);
                    kpiAsstRespVO.setAssetGroupName(assetInfo[2]);

                    kpiAsstRespVO.setNumDays(kpiAssetCountVO.getNumDays());
                    kpiAsstRespVO.setKpiName(kpiAssetCountVO.getKpiName());
                    kpiAsstRespVO.setTotalCount(totalCount);
                    kpiAsstRespVO.setUrgInfoVO(rxUrgencyInfoVO);
                    kpiAsstCountResVO.add(kpiAsstRespVO);
                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiAssetCountVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiAssetCountVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("KpiDAOImpl : Inside getAssetKPICounts() method:::END");
        return kpiAsstCountResVO;
    }

    /**
     * @Author:
     * @param KPIDataBean
     * @param List
     *            <KPIDataBean>
     * @return
     * @Description: get the Rx total Count : KPI services
     */

    @Override
    @SuppressWarnings("unchecked")
    public List<KPIDataCountResponseEoaVO> getRxTotalCount(final KPIDataEoaBean kpiDataBean) {

        LOG.debug("EOAService :KpiDAOImpl :getRxTotalCount() ::::START");
        Session hibernateSession = null;
        KPIDataCountResponseEoaVO kpiDataCountResponseVO = null;
        List<KPIDataCountResponseEoaVO> rxResponseDataList = new ArrayList<KPIDataCountResponseEoaVO>();
        List<Object> rxItrDataList = null;
        String language = null;
        String kpiName = null;
        long days = 0L;
        String rxDataQry = null;
        long totalCount = 0;

        if (null != kpiDataBean) {
            language = kpiDataBean.getUserLanguage();
            kpiName = kpiDataBean.getKpiName();
            days = kpiDataBean.getDays();
        }

        // get RxData count query
        if (null != kpiDataBean) {
            rxDataQry = getRxDataQuery(kpiDataBean);
        }

        try {
            hibernateSession = getHibernateSession();
            if (null != hibernateSession) {
                Query hibernateQuery = hibernateSession.createSQLQuery(rxDataQry);
                if ((null != kpiDataBean.getCustomerId())
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                    hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, kpiDataBean.getCustomerId());
                }

                hibernateQuery.setParameter(RMDServiceConstants.NO_OF_DAYS, days);

                /* Added for Product Asset Configuration */

                /* Added for Product Asset Configuration */
                if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                    if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                kpiDataBean.getProducts());
                    }
                }
                /* Added for Product Asset Configuration */
                rxItrDataList = hibernateQuery.list();

                // get the information about totalRxcount
                if (RMDCommonUtility.isCollectionNotEmpty(rxItrDataList)) {
                    for (int i = 0; i < rxItrDataList.size(); i++) {
                        Object rxResponseData[] = (Object[]) rxItrDataList.get(i);
                        if (null != rxResponseData) {
                            totalCount += Long.valueOf(rxResponseData[1].toString());
                        }
                    }
                }
                // populate response object
                LinkedHashMap<String, Integer> rxTypeCntMap = new LinkedHashMap<String, Integer>();
                if (RMDCommonUtility.isCollectionNotEmpty(rxItrDataList)) {
                    List<String> urgencyTypLst = new ArrayList<String>();
                    for (int i = 0; i < rxItrDataList.size(); i++) {
                        Object rxResponseData[] = (Object[]) rxItrDataList.get(i);
                        /*
                         * Adding the KPI counts if there is multiple occurance
                         * of same urgency values
                         */
                        if (urgencyTypLst.contains(RMDCommonUtility.convertObjectToString(rxResponseData[0]))) {
                            int tmpCnt = rxTypeCntMap.get(RMDCommonUtility.convertObjectToString(rxResponseData[0]));
                            tmpCnt += Integer.parseInt(RMDCommonUtility.convertObjectToString(rxResponseData[1]));
                            rxTypeCntMap.put(RMDCommonUtility.convertObjectToString(rxResponseData[0]), tmpCnt);
                        } else {
                            int tmpCnt = Integer.parseInt(RMDCommonUtility.convertObjectToString(rxResponseData[1]));
                            urgencyTypLst.add(RMDCommonUtility.convertObjectToString(rxResponseData[0]));

                            rxTypeCntMap.put(RMDCommonUtility.convertObjectToString(rxResponseData[0]), tmpCnt);
                        }
                        /*
                         * Adding the KPI counts if there is multiple occurance
                         * of same urgency values
                         */
                    }
                    /* Putting the data from map to KPI response VO */
                    for (Map.Entry<String, Integer> entry : rxTypeCntMap.entrySet()) {
                        kpiDataCountResponseVO = new KPIDataCountResponseEoaVO();
                        kpiDataCountResponseVO
                                .setCustomerId(RMDCommonUtility.convertObjectToString(kpiDataBean.getCustomerId()));
                        kpiDataCountResponseVO.setRxName(RMDCommonUtility.convertObjectToString(kpiName));
                        kpiDataCountResponseVO.setTotalCount(RMDCommonUtility
                                .convertObjectToString(RMDCommonUtility.convertObjectToLong(totalCount)));
                        kpiDataCountResponseVO.setRxType(RMDCommonUtility.convertObjectToString(entry.getKey()));
                        kpiDataCountResponseVO.setRxTypeCount(RMDCommonUtility.convertObjectToString(entry.getValue()));

                        rxResponseDataList.add(kpiDataCountResponseVO);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.debug("Unexpected Error occured in KpiDAOImpl getRxTotalCount()" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in KpiDAOImpl getRxTotalCount()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            hibernateSession.close();
        }
        LOG.debug("EOAService :KpiDAOImpl :getRxTotalCount() ::::END");
        return rxResponseDataList;
    }

    public String getRxDataQuery(final KPIDataEoaBean kpiDataBean) {

        StringBuilder rxDataQry = new StringBuilder();
        /* For rxDelivered */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDServiceConstants.KPI_RX_DELIVERED))) {

            rxDataQry.append(
                    "SELECT CASERECOM.URGENCY, COUNT(DELV.OBJID) FROM TABLE_CASE C, GETS_SD_CASE_RECOM CASERECOM, GETS_SD_RECOM_DELV DELV, TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH, ");
            rxDataQry.append(
                    "GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO WHERE C.CASE_PROD2SITE_PART =  TSP.OBJID  AND CASERECOM.CASE_RECOM2CASE  = C.OBJID  AND CASERECOM.CASE_RECOM2RECOM_DELV  = DELV.OBJID ");
            rxDataQry.append(
                    "AND TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID  AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            rxDataQry.append(" AND DELV.CREATION_DATE > SYSDATE - :days ");

            if ((null != kpiDataBean.getCustomerId())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND TBO.ORG_ID = :customerId ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(" AND TSP.OBJID in (" + getProductQuery(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */
            rxDataQry.append(" GROUP BY CASERECOM.URGENCY ");

        }
        /* For rxDelivered */
        /* For rxClosedByUrgency */
        else if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDServiceConstants.KPI_RX_CLOSED_URGENCY))) {

            rxDataQry.append(
                    "SELECT CASERECOM.URGENCY, COUNT(DELV.OBJID) FROM TABLE_CASE C, GETS_SD_CASE_RECOM CASERECOM, GETS_SD_RECOM_DELV DELV, GETS_SD_CUST_FDBK FDBK, TABLE_SITE_PART TSP, ");
            rxDataQry.append(
                    "GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR,  TABLE_BUS_ORG TBO WHERE C.CASE_PROD2SITE_PART  = TSP.OBJID AND CASERECOM.CASE_RECOM2CASE = C.OBJID AND CASERECOM.CASE_RECOM2RECOM_DELV  = DELV.OBJID ");
            rxDataQry.append(
                    "AND TSP.OBJID  = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND FDBK.CUST_FDBK2CASE  = C.OBJID  AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            rxDataQry.append(" AND FDBK.RX_CLOSE_DATE > SYSDATE - :days ");

            if ((null != kpiDataBean.getCustomerId())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND TBO.ORG_ID = :customerId ");
            }
            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(" AND TSP.OBJID in (" + getProductQuery(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */
            rxDataQry.append(" GROUP BY CASERECOM.URGENCY ");

        }
        /* For rxClosedByUrgency */
        /* For rxClosedByType */
        else if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDServiceConstants.KPI_RX_CLOSED_TYPE))) {

            rxDataQry.append(
                    "SELECT TYPE,COUNT(TYPE) FROM (SELECT CASE WHEN CASE_SUCCESS = 'Save'  THEN 'Repaired'  WHEN CASE_SUCCESS = 'Miss' AND MISS_CODE = '4F'  THEN 'Deferred' ");
            rxDataQry.append(
                    "WHEN CASE_SUCCESS = 'Miss' AND MISS_CODE = '4B'  THEN 'NTF'  END  AS TYPE  FROM GETS_SD.GETS_SD_CUST_FDBK FDBK,   TABLE_SITE_PART TSP,   GETS_RMD_VEHICLE VEH, ");
            rxDataQry.append(
                    "GETS_RMD_VEH_HDR VEHHDR,  TABLE_BUS_ORG TBO WHERE FDBK.CUST_FDBK2VEHICLE = VEH.OBJID AND TSP.OBJID  = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID ");
            rxDataQry.append("AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID  AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            rxDataQry.append(" AND FDBK.CREATION_DATE > SYSDATE - :days ");

            if ((null != kpiDataBean.getCustomerId())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND TBO.ORG_ID = :customerId ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(" AND TSP.OBJID in (" + getProductQuery(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */
            rxDataQry.append(" ) WHERE TYPE IS NOT NULL GROUP BY TYPE");

        }
        /* For rxClosedByType */
        return rxDataQry.toString();
    }

    /**
     * For Generating the data of new KPIs Rx Accuracy, no trouble found
     */
    @Override
    public List<KPIDataCountResponseEoaVO> getKPICounts(KPIDataEoaBean kpiDataBean) throws RMDDAOException {

        LOG.debug("EOAService :KpiDAOImpl :getKPICounts() ::::START");
        List<KPIDataCountResponseEoaVO> rxResponseDataList = null;
        Session hibernateSession = null;
        StringBuilder noDataQuery = new StringBuilder();
        List<Object> noDataList = null;
        Integer noOfRows = new Integer(0);
        try {
            if (null != kpiDataBean) {
                if (RMDCommonConstants.KPI_RX_ACCURACY.equalsIgnoreCase(kpiDataBean.getKpiName())
                        || RMDCommonConstants.KPI_NTF.equalsIgnoreCase(kpiDataBean.getKpiName())) {
                    noDataQuery.append(
                            "SELECT count(1) from GETS_RMD_KPI_DAILY A,GETS_RMD_DATE B,TABLE_BUS_ORG BUS,GETS_RMD_KPI_CUSTOMER_CONFIG CUST  ");

                    noDataQuery
                            .append("WHERE A.GETS_RMD_DATE_ID  = B.GETS_RMD_DATE_ID AND BUS.OBJID = A.CUSTOMER_ID AND "
                                    + "A.THRESHOLD_DAY = CUST.CLOSURE_THRESHOLD_DAY AND A.CUSTOMER_ID = CUST.CUSTOMER_ID AND ");
                    noDataQuery.append("TRANSACTION_DATE > TRUNC (ADD_MONTHS (SYSDATE, -12), 'MM') ");
                    noDataQuery.append("AND BUS.ORG_ID = :customerId ");

                    /* For Product asset Config story changes */
                    if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                        if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                            noDataQuery.append(" AND A.VEHICLE_ID IN ("
                                    + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                        }
                    }
                    /* For Product asset Config story changes */
                } else if (RMDCommonConstants.KPI_RESPONSE_TIME.equalsIgnoreCase(kpiDataBean.getKpiName())) {
                    noDataQuery.append(
                            "SELECT count(1) from GETS_RMD_VEHICLE_DWNLD_DTL A,GETS_RMD_DATE B,TABLE_BUS_ORG BUS ");

                    noDataQuery
                            .append("WHERE A.DOWNLOAD_DATE_ID = B.GETS_RMD_DATE_ID AND BUS.OBJID = A.CUSTOMER_ID AND ");
                    noDataQuery.append("TRANSACTION_DATE > TRUNC (ADD_MONTHS (SYSDATE, -12), 'MM') ");
                    noDataQuery.append("AND BUS.ORG_ID = :customerId AND average_response_tm > 0 ");
                    /* For Product asset Config story changes */
                    if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                        if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                            noDataQuery.append(" AND A.VEHICLE_ID  IN ("
                                    + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                        }
                    }
                    /* For Product asset Config story changes */

                }
                hibernateSession = getHibernateSession();
                if (null != hibernateSession) {
                    // get last 4 week data query

                    Query hibernateQuery = hibernateSession.createSQLQuery(noDataQuery.toString());
                    if ((null != kpiDataBean.getCustomerId())
                            && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                        hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, kpiDataBean.getCustomerId());

                    }
                    /* Added for Product Asset Configuration */

                    if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                        if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                            hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                    kpiDataBean.getProducts());
                        }
                    }
                    /* Added for Product Asset Configuration */

                    noDataList = hibernateQuery.list();
                    for (Object object : noDataList) {

                        noOfRows = Integer.parseInt(object.toString());

                    }
                }

                if (noOfRows > 0) {
                    rxResponseDataList = new ArrayList<KPIDataCountResponseEoaVO>();
                    Map<String, String> monthlyData = getMonthlyData(kpiDataBean);
                    String currentYear = getCurrentYearData(kpiDataBean);
                    String last4week = getLast4weekData(kpiDataBean);
                    String lastQuarter = getLastQuaterData(kpiDataBean);
                    String lastUpdatedDate = getLastUpdatedDate(kpiDataBean);
                    KPIDataCountResponseEoaVO kpiDataCountResponseVO = null;
                    for (Map.Entry<String, String> entry : monthlyData.entrySet()) {
                        kpiDataCountResponseVO = new KPIDataCountResponseEoaVO();
                        kpiDataCountResponseVO.setRxType(entry.getKey());
                        kpiDataCountResponseVO.setRxTypeCount(entry.getValue() + RMDCommonConstants.EMPTY_STRING);
                        kpiDataCountResponseVO.setCurrentYearAvg(currentYear);
                        kpiDataCountResponseVO.setLastFourWeekAvg(last4week);
                        kpiDataCountResponseVO.setLastQuarterAvg(lastQuarter);
                        kpiDataCountResponseVO.setRxName(kpiDataBean.getKpiName());
                        kpiDataCountResponseVO.setLastUpdatedDate(lastUpdatedDate);
                        kpiDataCountResponseVO.setCustomerId(kpiDataBean.getCustomerId());
                        rxResponseDataList.add(kpiDataCountResponseVO);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.debug("Unexpected Error occured in KpiDAOImpl getKPICounts()" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in KpiDAOImpl getKPICounts()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            hibernateSession.close();
        }
        LOG.debug("EOAService :KpiDAOImpl :getKPICounts() ::::END");
        return rxResponseDataList;
    }

    /**
     * For Getting last updated date in kpi daily table
     * 
     * @param kpiDataBean
     * @return
     */
    public String getLastUpdatedDate(KPIDataEoaBean kpiDataBean) {
        StringBuilder rxDataQry = new StringBuilder();
        Session hibernateSession = null;
        List<Object> rxItrDataList = null;
        String lastUpdatedDate = RMDCommonConstants.EMPTY_STRING;
        /* For rxAccuracy */
        if ((null != kpiDataBean.getKpiName())) {

            if (kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_RX_ACCURACY)
                    || kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_NTF)) {

                rxDataQry.append("SELECT TO_CHAR(MAX(TRANSACTION_DATE),'mm/dd/yyyy') from GETS_RMD_DATE DATES"
                        + "		, GETS_RMD_KPI_DAILY KPI,GETS_RMD_KPI_CUSTOMER_CONFIG CUST,TABLE_BUS_ORG BUS   ");
                rxDataQry.append(
                        "  WHERE KPI.GETS_RMD_DATE_ID  = DATES.GETS_RMD_DATE_ID AND BUS.OBJID = KPI.CUSTOMER_ID ");
                rxDataQry.append(
                        " AND THRESHOLD_DAY = CUST.CLOSURE_THRESHOLD_DAY AND KPI.CUSTOMER_ID = CUST.CUSTOMER_ID ");

            } else if (kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_RESPONSE_TIME)) {

                rxDataQry.append("SELECT TO_CHAR(MAX(TRANSACTION_DATE),'mm/dd/yyyy') from GETS_RMD_DATE DATES ");
                rxDataQry.append(", GETS_RMD_VEHICLE_DWNLD_DTL A,GETS_RMD_KPI_CUSTOMER_CONFIG CUST,TABLE_BUS_ORG BUS ");
                rxDataQry.append(" WHERE A.DOWNLOAD_DATE_ID   = DATES.GETS_RMD_DATE_ID AND BUS.OBJID = A.CUSTOMER_ID ");
            }

            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append("AND BUS.ORG_ID = :customerId ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    if (kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_RX_ACCURACY)
                            || kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_NTF)) {
                        rxDataQry.append(
                                " AND KPI.VEHICLE_ID IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                    } else if (kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_RESPONSE_TIME)) {
                        rxDataQry.append(
                                " AND A.VEHICLE_ID IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                    }
                }
            }
            /* For Product asset Config story changes */
            try {
                hibernateSession = getHibernateSession();
                if (null != hibernateSession) {
                    // get last 4 week data query

                    Query hibernateQuery = hibernateSession.createSQLQuery(rxDataQry.toString());
                    if ((null != kpiDataBean.getCustomerId())
                            && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                        hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, kpiDataBean.getCustomerId());
                    }

                    /* Added for Product Asset Configuration */
                    if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                        if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                            hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                    kpiDataBean.getProducts());
                        }
                    }
                    /* Added for Product Asset Configuration */
                    rxItrDataList = hibernateQuery.list();
                    for (Object object : rxItrDataList) {
                        if (null != object)
                            lastUpdatedDate = object.toString();

                    }
                }

            } catch (RMDDAOConnectionException ex) {
                LOG.debug("Unexpected Error occured in KpiDAOImpl getLastUpdatedDate()" + ex);
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), ex,
                        RMDCommonConstants.FATAL_ERROR);
            } catch (Exception e) {
                LOG.error("Unexpected Error occured in KpiDAOImpl getLastUpdatedDate()", e);
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), e,
                        RMDCommonConstants.MAJOR_ERROR);
            } finally {
                hibernateSession.close();
            }
        }
        return lastUpdatedDate;
    }

    /**
     * Generating a query for last 4 week data for rxAccuracy and ntf
     * 
     * @param kpiDataBean
     * @return
     */
    public String getLast4weekData(KPIDataEoaBean kpiDataBean) {
        StringBuilder rxDataQry = new StringBuilder();
        Session hibernateSession = null;
        List<Object> rxItrDataList = null;
        String fourWeekData = "0";
        /* For rxAccuracy */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RX_ACCURACY))) {
            rxDataQry.append("SELECT DECODE((NVL(sum(A)-sum(B)-sum(D),0)),0,'NA',"
                    + "(((sum(A)-sum(B)-sum(C)-sum(D))/(NVL(sum(A)-sum(B)-sum(D),0))))*100) AS RxAccuracy "
                    + "FROM (SELECT sum(TOTAL_RX_CLOSED_CNT) AS A,sum(OUT_OF_SCOPE_RX_CNT) AS B, "
                    + "sum(TOTAL_NTF_CNT) AS C,sum(THRESHOLD_EXCEED_CNT ) AS D   FROM GETS_RMD_KPI_DAILY DAILY "
                    + ",GETS_RMD_KPI_CUSTOMER_CONFIG CUST,TABLE_BUS_ORG BUS  ");
            /*
             * if (null != kpiDataBean.getproduct() &&
             * !RMDCommonUtility.checkNull(kpiDataBean .getproduct())) {
             * rxDataQry .append(", GETS_RMD_VEHICLE VEHC "); }
             */
            rxDataQry.append(" WHERE  BUS.OBJID = DAILY.CUSTOMER_ID AND DAILY.GETS_RMD_DATE_ID   "
                    + "IN (SELECT GETS_RMD_DATE_ID FROM GETS_RMD_DATE "
                    + " WHERE TRANSACTION_DATE > sysdate-29 AND TRANSACTION_DATE!=SYSDATE) ");

            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND BUS.ORG_ID=:customerId ");
                rxDataQry.append(
                        " AND THRESHOLD_DAY = CUST.CLOSURE_THRESHOLD_DAY AND DAILY.CUSTOMER_ID = CUST.CUSTOMER_ID ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND DAILY.VEHICLE_ID IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

            rxDataQry.append(")");
        }
        /* For NTF rate */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_NTF))) {
            rxDataQry
                    .append("SELECT DECODE((NVL(sum(A)-sum(D),0)),0,'NA',(sum(C)/(NVL(sum(A)-sum(D),0)))*100)  AS NTF_RATE "
                            + "FROM(SELECT sum(TOTAL_RX_CLOSED_CNT) AS A,sum(OUT_OF_SCOPE_RX_CNT) AS B,"
                            + "sum(TOTAL_NTF_CNT) AS C,sum(THRESHOLD_EXCEED_CNT ) AS D   "
                            + "FROM GETS_RMD_KPI_DAILY DAILY,GETS_RMD_KPI_CUSTOMER_CONFIG CUST,TABLE_BUS_ORG BUS ");
            /*
             * if (null != kpiDataBean.getproduct() &&
             * !RMDCommonUtility.checkNull(kpiDataBean .getproduct())) {
             * rxDataQry .append(", GETS_RMD_VEHICLE VEHC "); }
             */
            rxDataQry.append(" WHERE BUS.OBJID = DAILY.CUSTOMER_ID AND  DAILY.GETS_RMD_DATE_ID  IN "
                    + "(SELECT GETS_RMD_DATE_ID FROM GETS_RMD_DATE WHERE TRANSACTION_DATE > sysdate-29 "
                    + "AND TRANSACTION_DATE!=SYSDATE) ");
            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND BUS.ORG_ID=:customerId ");
                rxDataQry.append(
                        " AND THRESHOLD_DAY = CUST.CLOSURE_THRESHOLD_DAY AND DAILY.CUSTOMER_ID = CUST.CUSTOMER_ID ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND DAILY.VEHICLE_ID IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

            rxDataQry.append(")");
        }
        /* For Response Time */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RESPONSE_TIME))) {
            rxDataQry.append(
                    "SELECT DECODE(NVL(AVG(A.average_response_tm),0),0,'NA',AVG(A.average_response_tm)) AS RESPONSE_TIME ");
            rxDataQry.append("FROM GETS_RMD.GETS_RMD_VEHICLE_DWNLD_DTL A, TABLE_BUS_ORG BUS ");

            rxDataQry.append("WHERE BUS.OBJID = A.CUSTOMER_ID AND  download_date_id IN ");
            rxDataQry.append("(SELECT GETS_RMD_DATE_ID FROM GETS_RMD_DATE ");
            rxDataQry.append("WHERE TRANSACTION_DATE > sysdate-29 AND TRANSACTION_DATE!  =SYSDATE ");
            rxDataQry.append(") AND average_response_tm >0 ");
            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND BUS.ORG_ID=:customerId ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND  A.VEHICLE_ID IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

        }
        try {
            hibernateSession = getHibernateSession();
            if (null != hibernateSession) {
                // get last 4 week data query

                Query hibernateQuery = hibernateSession.createSQLQuery(rxDataQry.toString());
                if ((null != kpiDataBean.getCustomerId())
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                    hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, kpiDataBean.getCustomerId());
                }

                /* Added for Product Asset Configuration */
                if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                    if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                kpiDataBean.getProducts());
                    }
                }
                /* Added for Product Asset Configuration */
                rxItrDataList = hibernateQuery.list();

                // get the information about totalRxcount
                if (RMDCommonUtility.isCollectionNotEmpty(rxItrDataList)) {
                    for (Object object : rxItrDataList) {

                        if (!object.toString().equals(RMDCommonConstants.N_A)
                                && !fourWeekData.equals(RMDCommonConstants.N_A)) {
                            fourWeekData = RMDCommonUtility.convertObjectToString(
                                    Double.parseDouble(fourWeekData) + Double.parseDouble(object.toString()));
                        } else {
                            fourWeekData = object.toString();
                        }
                    }
                }

            }
        } catch (RMDDAOConnectionException ex) {
            LOG.debug("Unexpected Error occured in KpiDAOImpl getLast4weekQuery()" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in KpiDAOImpl getLast4weekQuery()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            hibernateSession.close();
        }

        return fourWeekData;
    }

    /**
     * Generating a query for last quarter data for rxAccuracy and ntf
     * 
     * @param kpiDataBean
     * @return
     */
    public String getLastQuaterData(KPIDataEoaBean kpiDataBean) {

        StringBuilder rxDataQry = new StringBuilder();
        Session hibernateSession = null;
        List<Object> rxItrDataList = null;
        String lastQuarterData = RMDCommonConstants.ZERO_STRING;
        /* For rxAccuracy */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RX_ACCURACY))) {
            rxDataQry.append("SELECT DECODE((NVL(sum(A)-sum(B)-sum(D),0)),0,'NA',"
                    + "(((sum(A)-sum(B)-sum(C)-sum(D))/(NVL(sum(A)-sum(B)-sum(D),0))))*100) AS RxAccuracy"
                    + " FROM (SELECT sum(TOTAL_RX_CLOSED_CNT) AS A,sum(OUT_OF_SCOPE_RX_CNT) AS B, "
                    + "sum(TOTAL_NTF_CNT) AS C,sum(THRESHOLD_EXCEED_CNT ) AS D  "
                    + "FROM GETS_RMD_KPI_DAILY DAILY,GETS_RMD_KPI_CUSTOMER_CONFIG CUST,TABLE_BUS_ORG BUS"
                    + "			 WHERE  BUS.OBJID = DAILY.CUSTOMER_ID AND   DAILY.GETS_RMD_DATE_ID  IN "
                    + "(SELECT GETS_RMD_DATE_ID FROM GETS_RMD_DATE WHERE "
                    + "TRANSACTION_DATE BETWEEN (SELECT ADD_MONTHS(D, -3) "
                    + "FROM (SELECT TRUNC(SYSDATE, 'Q') AS D FROM DUAL))"
                    + " AND (SELECT D - 1 FROM (SELECT TRUNC(SYSDATE, 'Q') AS D FROM DUAL)))");
            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND BUS.ORG_ID=:customerId ");
                rxDataQry.append(
                        " AND THRESHOLD_DAY = CUST.CLOSURE_THRESHOLD_DAY AND DAILY.CUSTOMER_ID = CUST.CUSTOMER_ID ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND  DAILY.VEHICLE_ID IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

            rxDataQry.append(")");
        }
        /* For NTF rate */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_NTF))) {
            rxDataQry.append("SELECT DECODE((NVL(sum(A)-sum(D),0)),0,'NA'"
                    + ",(sum(C)/(NVL(sum(A)-sum(D),0)))*100)  AS NTF_RATE "
                    + "FROM(SELECT sum(TOTAL_RX_CLOSED_CNT) AS A,sum(OUT_OF_SCOPE_RX_CNT) AS B,"
                    + "sum(TOTAL_NTF_CNT) AS C,sum(THRESHOLD_EXCEED_CNT ) AS D  "
                    + "FROM GETS_RMD_KPI_DAILY DAILY,GETS_RMD_KPI_CUSTOMER_CONFIG CUST,TABLE_BUS_ORG BUS"
                    + "  WHERE  BUS.OBJID = DAILY.CUSTOMER_ID AND   DAILY.GETS_RMD_DATE_ID  "
                    + "IN (SELECT GETS_RMD_DATE_ID "
                    + "FROM GETS_RMD_DATE WHERE TRANSACTION_DATE BETWEEN (SELECT ADD_MONTHS(D, -3) "
                    + "FROM (SELECT TRUNC(SYSDATE, 'Q') AS D FROM DUAL)) "
                    + "AND (SELECT D - 1 FROM (SELECT TRUNC(SYSDATE, 'Q') AS D FROM DUAL)))");
            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND BUS.ORG_ID=:customerId ");
                rxDataQry.append(
                        " AND THRESHOLD_DAY = CUST.CLOSURE_THRESHOLD_DAY AND DAILY.CUSTOMER_ID = CUST.CUSTOMER_ID ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND  DAILY.VEHICLE_ID IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

            rxDataQry.append(")");
        }
        /* For Response Time */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RESPONSE_TIME))) {
            rxDataQry.append(
                    "SELECT DECODE(NVL(AVG(A.average_response_tm),0),0,'NA',AVG(A.average_response_tm)) AS RESPONSE_TIME ");
            rxDataQry.append("FROM GETS_RMD.GETS_RMD_VEHICLE_DWNLD_DTL A, TABLE_BUS_ORG BUS ");
            rxDataQry.append("WHERE BUS.OBJID = A.CUSTOMER_ID AND  download_date_id IN ");
            rxDataQry.append("(SELECT GETS_RMD_DATE_ID ");
            rxDataQry.append("FROM GETS_RMD_DATE WHERE TRANSACTION_DATE BETWEEN ");
            rxDataQry.append("(SELECT ADD_MONTHS(D, -3) FROM ");
            rxDataQry.append("(SELECT TRUNC(SYSDATE, 'Q') AS D FROM DUAL ");
            rxDataQry.append("))  AND (SELECT D - 1 FROM (SELECT TRUNC(SYSDATE, 'Q') AS D FROM DUAL))) ");
            rxDataQry.append("AND average_response_tm >0 ");
            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND BUS.ORG_ID=:customerId ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND  A.VEHICLE_ID IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

        }
        try {
            hibernateSession = getHibernateSession();
            if (null != hibernateSession) {
                // get last 4 week data query

                Query hibernateQuery = hibernateSession.createSQLQuery(rxDataQry.toString());
                if ((null != kpiDataBean.getCustomerId())
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                    hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, kpiDataBean.getCustomerId());
                }

                /* Added for Product Asset Configuration */
                if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                    if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                kpiDataBean.getProducts());
                    }
                }
                /* Added for Product Asset Configuration */
                rxItrDataList = hibernateQuery.list();

                // get the information about totalRxcount
                if (RMDCommonUtility.isCollectionNotEmpty(rxItrDataList)) {
                    for (Object object : rxItrDataList) {
                        if (!object.toString().equals(RMDCommonConstants.N_A)
                                && !lastQuarterData.equals(RMDCommonConstants.N_A)) {
                            lastQuarterData = RMDCommonUtility.convertObjectToString(
                                    Double.parseDouble(lastQuarterData) + Double.parseDouble(object.toString()));
                        } else {
                            lastQuarterData = object.toString();
                        }

                    }
                }

            }
        } catch (RMDDAOConnectionException ex) {
            LOG.debug("Unexpected Error occured in KpiDAOImpl getLastQuaterData()" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in KpiDAOImpl getLastQuaterData()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            hibernateSession.close();
        }
        return lastQuarterData;
    }

    /**
     * Generating a query for current year data for rxAccuracy and ntf
     * 
     * @param kpiDataBean
     * @return
     */
    public String getCurrentYearData(KPIDataEoaBean kpiDataBean) {

        StringBuilder rxDataQry = new StringBuilder();
        Session hibernateSession = null;
        List<Object> rxItrDataList = null;
        String currentYearData = RMDCommonConstants.ZERO_STRING;
        /* For rxAccuracy */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RX_ACCURACY))) {
            rxDataQry.append("SELECT DECODE((NVL(sum(A)-sum(B)-sum(D),0)),0,'NA'"
                    + ",(((sum(A)-sum(B)-sum(C)-sum(D))/(NVL(sum(A)-sum(B)-sum(D),0))))*100) AS RxAccuracy "
                    + "FROM (SELECT sum(TOTAL_RX_CLOSED_CNT) AS A,sum(OUT_OF_SCOPE_RX_CNT) AS B,"
                    + "sum(TOTAL_NTF_CNT) AS C,sum(THRESHOLD_EXCEED_CNT ) AS D  FROM GETS_RMD_KPI_DAILY DAILY,GETS_RMD_KPI_CUSTOMER_CONFIG CUST "
                    + ",TABLE_BUS_ORG BUS WHERE BUS.OBJID = DAILY.CUSTOMER_ID AND  DAILY.GETS_RMD_DATE_ID  IN (SELECT GETS_RMD_DATE_ID FROM GETS_RMD_DATE "
                    + "WHERE  TRUNC(TRANSACTION_DATE) BETWEEN TO_DATE('01-jan-' || TO_CHAR(SYSDATE, 'yyyy'), 'dd-MON-yyyy') "
                    + "AND SYSDATE-1)");
            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append("  AND BUS.ORG_ID=:customerId ");
                rxDataQry.append(
                        " AND THRESHOLD_DAY = CUST.CLOSURE_THRESHOLD_DAY AND DAILY.CUSTOMER_ID = CUST.CUSTOMER_ID ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND  DAILY.VEHICLE_ID  IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

            rxDataQry.append(")");
        }
        /* For NTF rate */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_NTF))) {
            rxDataQry.append("SELECT DECODE((NVL(sum(A)-sum(D),0)),0,'NA'"
                    + ",(sum(C)/(NVL(sum(A)-sum(D),0)))*100)  AS NTF_RATE "
                    + "FROM( SELECT sum(TOTAL_RX_CLOSED_CNT) AS A,sum(OUT_OF_SCOPE_RX_CNT) AS B,"
                    + "sum(TOTAL_NTF_CNT) AS C,sum(THRESHOLD_EXCEED_CNT ) AS D  "
                    + "FROM GETS_RMD_KPI_DAILY DAILY,GETS_RMD_KPI_CUSTOMER_CONFIG CUST,TABLE_BUS_ORG BUS "
                    + "WHERE BUS.OBJID = DAILY.CUSTOMER_ID AND GETS_RMD_DATE_ID  IN "
                    + "(SELECT GETS_RMD_DATE_ID FROM GETS_RMD_DATE " + "WHERE  TRUNC(TRANSACTION_DATE) BETWEEN "
                    + "TO_DATE('01-jan-' || TO_CHAR(SYSDATE, 'yyyy'), 'dd-MON-yyyy') AND SYSDATE-1)");
            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND BUS.ORG_ID=:customerId ");
                rxDataQry.append(
                        " AND THRESHOLD_DAY = CUST.CLOSURE_THRESHOLD_DAY AND DAILY.CUSTOMER_ID = CUST.CUSTOMER_ID ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND  DAILY.VEHICLE_ID  IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

            rxDataQry.append(")");
        }
        /* For Response Time */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RESPONSE_TIME))) {
            rxDataQry.append(
                    "SELECT DECODE(NVL(AVG(A.average_response_tm),0),0,'NA',AVG(A.average_response_tm)) AS RESPONSE_TIME ");
            rxDataQry.append("FROM GETS_RMD.GETS_RMD_VEHICLE_DWNLD_DTL A,TABLE_BUS_ORG BUS ");
            rxDataQry.append("WHERE BUS.OBJID = A.CUSTOMER_ID AND  download_date_id IN ");
            rxDataQry.append("(SELECT GETS_RMD_DATE_ID ");
            rxDataQry.append("FROM GETS_RMD_DATE WHERE TRUNC(TRANSACTION_DATE) BETWEEN TO_DATE('01-jan-' ");
            rxDataQry.append("|| TO_CHAR(SYSDATE, 'yyyy'), 'dd-MON-yyyy') AND SYSDATE-1) ");
            rxDataQry.append("AND average_response_tm >0 ");
            if (null != kpiDataBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                rxDataQry.append(" AND BUS.ORG_ID=:customerId ");
            }

            /* For Product asset Config story changes */
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    rxDataQry.append(
                            " AND  A.VEHICLE_ID  IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")");
                }
            }
            /* For Product asset Config story changes */

        }
        try {
            hibernateSession = getHibernateSession();
            if (null != hibernateSession) {
                // get last 4 week data query

                Query hibernateQuery = hibernateSession.createSQLQuery(rxDataQry.toString());
                if ((null != kpiDataBean.getCustomerId())
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                    hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, kpiDataBean.getCustomerId());
                }

                /* Added for Product Asset Configuration */
                if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                    if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                kpiDataBean.getProducts());
                    }
                }
                /* Added for Product Asset Configuration */
                rxItrDataList = hibernateQuery.list();

                // get the information about totalRxcount
                if (RMDCommonUtility.isCollectionNotEmpty(rxItrDataList)) {
                    for (Object object : rxItrDataList) {
                        if (!object.toString().equals(RMDCommonConstants.N_A)
                                && !currentYearData.equals(RMDCommonConstants.N_A)) {
                            currentYearData = RMDCommonUtility.convertObjectToString(
                                    Double.parseDouble(currentYearData) + Double.parseDouble(object.toString()));
                        } else {
                            currentYearData = object.toString();
                        }
                    }
                }

            }
        } catch (RMDDAOConnectionException ex) {
            LOG.debug("Unexpected Error occured in KpiDAOImpl getCurrentYearData()" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in KpiDAOImpl getCurrentYearData()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            hibernateSession.close();
        }

        return currentYearData;
    }

    /**
     * Generating a query for monthly data for rxAccuracy and ntf
     * 
     * @param kpiDataBean
     * @return
     */

    public Map<String, String> getMonthlyData(KPIDataEoaBean kpiDataBean) {

        StringBuilder rxDataQry = new StringBuilder();
        Session hibernateSession = null;
        List<Object> rxItrDataList = null;
        LinkedHashMap<String, String> monthlyDataMap = new LinkedHashMap<String, String>();
        /* For rxAccuracy */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RX_ACCURACY))) {

            rxDataQry.append("	SELECT DECODE((NVL(SUM(A) - SUM(B) - SUM(D), 0)), ");
            rxDataQry.append(" 0, ");
            rxDataQry.append(" 'NA', ");
            rxDataQry.append(" (((SUM(A) - SUM(B) - SUM(C) - SUM(D)) /	");
            rxDataQry.append(" (NVL(SUM(A) - SUM(B) - SUM(D), 0)))) * 100) AS RxAccuracy, ");

        }
        /* For NTF rate */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_NTF))) {
            rxDataQry.append(
                    "	SELECT DECODE((NVL(sum(A)-sum(D),0)),0,'NA',(sum(C)/(NVL(sum(A)-sum(D),0)))*100)  AS NTF_RATE,");

        }
        /* For Response Time */
        if ((null != kpiDataBean.getKpiName())
                && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RESPONSE_TIME))) {
            rxDataQry.append("SELECT DECODE(NVL(AVG(AVG_RESP),0),0,'NA',AVG(AVG_RESP)) AS RESPONSE_TIME   , ");

        }
        try {
            if ((null != kpiDataBean.getKpiName())
                    && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RX_ACCURACY)
                            || (kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_NTF))) {
                rxDataQry.append(" to_char(TRUNC(to_date(CALENDAR_YR || CALENDAR_MTH, 'yyyymm')), ");
                rxDataQry.append("  'Mon''YY') monthyear, ");
                rxDataQry.append(" CALENDAR_YR, ");
                rxDataQry.append(" CALENDAR_MTH, ");
                rxDataQry.append(" CUSTOMER_ID	");
                rxDataQry.append("	  FROM (SELECT A.CUSTOMER_ID, ");
                rxDataQry.append("  VEHICLE_ID, ");
                rxDataQry.append("  CALENDAR_YR, ");
                rxDataQry.append("  CALENDAR_MTH, ");
                rxDataQry.append("  sum(TOTAL_RX_CLOSED_CNT) AS A, ");
                rxDataQry.append("  sum(OUT_OF_SCOPE_RX_CNT) AS B, ");
                rxDataQry.append("  sum(TOTAL_NTF_CNT) AS C, ");
                rxDataQry.append("  sum(THRESHOLD_EXCEED_CNT ) AS D	");
                rxDataQry.append("    FROM (SELECT CUSTOMER_ID, ");
                rxDataQry.append("          GETS_RMD_DATE_ID, ");
                rxDataQry.append("          CALENDAR_MTH, ");
                rxDataQry.append("          CALENDAR_YR, CLOSURE_THRESHOLD_DAY	");
                rxDataQry.append("     FROM GETS_RMD_KPI_CUSTOMER_CONFIG, GETS_RMD_DATE) A	");
                rxDataQry.append("    LEFT OUTER JOIN	");
                rxDataQry.append("  	");
                rxDataQry.append("   (SELECT GETS_RMD_DATE_ID , ");
                rxDataQry.append("   CUSTOMER_ID, ");
                rxDataQry.append("   VEHICLE_ID, ");
                rxDataQry.append("   TOTAL_RX_CLOSED_CNT, ");
                rxDataQry.append("   OUT_OF_SCOPE_RX_CNT, ");
                rxDataQry.append("   TOTAL_NTF_CNT, ");
                rxDataQry.append("   THRESHOLD_EXCEED_CNT , ");
                rxDataQry.append("   THRESHOLD_DAY	");
                rxDataQry.append("     FROM GETS_RMD_KPI_DAILY	");

                if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                    if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        rxDataQry.append("   WHERE    VEHICLE_ID IN ( "
                                + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ")	");
                    }
                }
                rxDataQry.append("      ) B ON A.GETS_RMD_DATE_ID =B.GETS_RMD_DATE_ID 	");
                rxDataQry.append(" AND A.CUSTOMER_ID =B.CUSTOMER_ID	 AND A.CLOSURE_THRESHOLD_DAY = B.THRESHOLD_DAY ");
                rxDataQry.append("  GROUP BY VEHICLE_ID, A.CUSTOMER_ID, CALENDAR_YR, CALENDAR_MTH	");
                rxDataQry.append("  ORDER BY A.CUSTOMER_ID, CALENDAR_YR, CALENDAR_MTH)	");
                rxDataQry.append("	 WHERE 	");
                if (null != kpiDataBean.getCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                    rxDataQry.append("	 CUSTOMER_ID = (SELECT BUS.OBJID	");
                    rxDataQry.append("           FROM TABLE_BUS_ORG BUS	");
                    rxDataQry.append("          WHERE BUS.ORG_ID = :customerId) AND	");
                }

            } else if ((null != kpiDataBean.getKpiName())
                    && ((kpiDataBean.getKpiName()).equalsIgnoreCase(RMDCommonConstants.KPI_RESPONSE_TIME))) {
                rxDataQry.append(" to_char(TRUNC(to_date(CALENDAR_YR || CALENDAR_MTH, 'yyyymm')), ");
                rxDataQry.append("  'Mon''YY') monthyear, ");
                rxDataQry.append(" CALENDAR_YR, ");
                rxDataQry.append(" CALENDAR_MTH, ");
                rxDataQry.append(" CUSTOMER_ID	");
                rxDataQry.append("FROM ( SELECT A.OBJID customer_id ,VEHICLE_ID , "
                        + "CALENDAR_YR , CALENDAR_MTH ,average_response_tm as AVG_RESP ");
                rxDataQry.append("FROM(SELECT  OBJID , GETS_RMD_DATE_ID, CALENDAR_MTH, CALENDAR_YR ");
                rxDataQry.append("FROM TABLE_BUS_ORG	, GETS_RMD_DATE	)   A LEFT OUTER JOIN ");
                rxDataQry.append("(SELECT download_date_id,CUSTOMER_ID,VEHICLE_ID,average_response_tm ");
                rxDataQry.append("FROM GETS_RMD_VEHICLE_DWNLD_DTL   WHERE ");

                /* For Product asset Config story changes */
                if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                    if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                        rxDataQry.append(
                                "  VEHICLE_ID  IN(" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ") AND ");
                    }
                }
                /* For Product asset Config story changes */

                rxDataQry.append(
                        " average_response_tm > 0 ) B ON A.GETS_RMD_DATE_ID = B.download_date_id AND A.OBJID = B.CUSTOMER_ID ");
                rxDataQry.append(")WHERE  ");
                if (null != kpiDataBean.getCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {
                    rxDataQry.append("  CUSTOMER_ID = (SELECT BUS.OBJID	");
                    rxDataQry.append("           FROM TABLE_BUS_ORG BUS	");
                    rxDataQry.append("          WHERE BUS.ORG_ID = :customerId) AND	");
                }

            }
            rxDataQry.append(" TRUNC(to_date(CALENDAR_YR || CALENDAR_MTH, 'yyyymm')) BETWEEN	");
            rxDataQry.append(" TRUNC(ADD_MONTHS(SYSDATE, -12), 'MM') AND	");
            rxDataQry.append(" TRUNC(LAST_DAY(ADD_MONTHS(SYSDATE, -1)))	");
            rxDataQry.append("	 GROUP BY CUSTOMER_ID, CALENDAR_YR, CALENDAR_MTH	");
            rxDataQry.append("	 ORDER BY to_date(monthyear, 'Mon''YY')	");
            hibernateSession = getHibernateSession();
            if (null != hibernateSession) {
                // get last 4 week data query

                Query hibernateQuery = hibernateSession.createSQLQuery(rxDataQry.toString());
                if ((null != kpiDataBean.getCustomerId())
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(kpiDataBean.getCustomerId())) {
                    hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, kpiDataBean.getCustomerId());
                }

                /* Added for Product Asset Configuration */
                if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                    if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                kpiDataBean.getProducts());
                    }
                }
                /* Added for Product Asset Configuration */

                rxItrDataList = hibernateQuery.list();
                List<String> monthLst = new ArrayList<String>();
                String tmpCnt = RMDCommonConstants.EMPTY_STRING;
                for (int i = 0; i < rxItrDataList.size(); i++) {
                    Object rxResponseData[] = (Object[]) rxItrDataList.get(i);

                    tmpCnt = RMDCommonUtility.convertObjectToString(rxResponseData[0]);

                    monthlyDataMap.put(RMDCommonUtility.convertObjectToString(rxResponseData[1]), tmpCnt);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.debug("Unexpected Error occured in KpiDAOImpl getMonthlyData()" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in KpiDAOImpl getMonthlyData()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            hibernateSession.close();
        }

        return monthlyDataMap;

    }

    /**
     * For Getting First customer from kpi daily table or
     * gets_rmd_vehicle_dwnld_dtl table based on kpi name
     * 
     * @param kpiDataBean
     * @return
     */
    @Override
    public String getFirstCustomer(KPIDataEoaBean kpiDataBean) throws RMDDAOException {
        StringBuilder firtstCustomerQry = new StringBuilder();
        Session hibernateSession = null;
        List<Object> firtstCustomerList = null;
        String firstCustomer = RMDCommonConstants.EMPTY_STRING;

        if (kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_RX_ACCURACY)
                || kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_NTF)) {
            firtstCustomerQry.append("SELECT ORG_ID FROM ");
            firtstCustomerQry.append(
                    "(SELECT ORG_ID  FROM GETS_RMD.GETS_RMD_KPI_DAILY A,TABLE_BUS_ORG B, GETS_RMD_KPI_CUSTOMER_CONFIG CUST  ");
            firtstCustomerQry.append(
                    "WHERE A.CUSTOMER_ID = B.objid AND THRESHOLD_DAY     = CUST.CLOSURE_THRESHOLD_DAY AND A.CUSTOMER_ID = CUST.CUSTOMER_ID ");
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    firtstCustomerQry.append(
                            " AND VEHICLE_ID  IN (" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ") ");
                }
            }
            firtstCustomerQry.append("ORDER BY B.S_NAME) WHERE ROWNUM <2 ");

        } else if (kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_RESPONSE_TIME)) {
            firtstCustomerQry.append("SELECT ORG_ID FROM ");
            firtstCustomerQry.append("(SELECT ORG_ID  FROM  GETS_RMD.GETS_RMD_VEHICLE_DWNLD_DTL A,TABLE_BUS_ORG B ");
            firtstCustomerQry.append("WHERE A.CUSTOMER_ID = b.objid  ");
            if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {

                    firtstCustomerQry.append(
                            " AND VEHICLE_ID  IN (" + getProductQueryForKpi(kpiDataBean.getCustomerId()) + ") ");
                }
            }
            firtstCustomerQry.append("ORDER BY B.S_NAME) WHERE ROWNUM <2 ");
        }

        try {
            hibernateSession = getHibernateSession();
            if (null != hibernateSession) {

                Query hibernateQuery = hibernateSession.createSQLQuery(firtstCustomerQry.toString());

                /* Added for Product Asset Configuration */
                if (null != kpiDataBean.getProducts() && !RMDCommonUtility.checkNull(kpiDataBean.getProducts())) {
                    if (!kpiDataBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                kpiDataBean.getProducts());
                    }
                }

                firtstCustomerList = hibernateQuery.list();
                for (Object object : firtstCustomerList) {

                    firstCustomer = object.toString();

                }
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.debug("Unexpected Error occured in KpiDAOImpl getFirstCustomer()" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in KpiDAOImpl getFirstCustomer()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, kpiDataBean.getUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            hibernateSession.close();
        }

        return firstCustomer;
    }

    protected String getProductQueryForKpi(String customerId) throws RMDDAOException {
        StringBuilder query = new StringBuilder();
        query.append("select VEH.objid from GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST,TABLE_SITE_PART TSP ");
        query.append(
                " ,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO where ELGASST.ASSET_ID=VEH.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART ");
        query.append(" AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
        query.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%' and ELGASST.PRODUCT_CD in" + "(:productNameLst)) ");
        if (null != customerId && !RMDCommonConstants.EMPTY_STRING.equals(customerId)) {
            query.append(" and TBO.org_id=:customerId");
        }

        return query.toString();
    }
}
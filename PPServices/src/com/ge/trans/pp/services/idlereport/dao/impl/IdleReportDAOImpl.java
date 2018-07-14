package com.ge.trans.pp.services.idlereport.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.pp.services.idlereport.dao.intf.IdleReportDAOIntf;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportDetailsResponseVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryReqVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryResponseVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

public class IdleReportDAOImpl extends RMDCommonDAO implements IdleReportDAOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(IdleReportDAOImpl.class);
    Codec ORACLE_CODEC = new OracleCodec();

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.idlereport.dao.intf.IdleReportDAOIntf#
     * getIdleReportSummary
     * (com.ge.trans.pp.services.idlereport.service.valueobjects
     * .IdleReportSummaryReqVO) This method returns the Idle Summary report from
     * the system
     */
    @Override
    public List<IdleReportSummaryResponseVO> getIdleReportSummary(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDDAOException {

        Session hibernateSession = null;
        StringBuilder idleRepSummaryQry = new StringBuilder();
        List<Object[]> objIdleSummReport = null;
        List<Object> objIdleReportProd = null;
        IdleReportSummaryResponseVO objIdleSummRespVO = null;
        List<IdleReportSummaryResponseVO> objIdleSummRespVOLst = new ArrayList<IdleReportSummaryResponseVO>();
        Query hibernateQuery = null;
        String productFromQuery = null;
        String productCdQuery = null;
        String productCd = "";
        try {
            hibernateSession = getHibernateSession();
            if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                    && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                productCdQuery = "SELECT GET_RMD_OMD_PRODUCT_SEQ_ID FROM GETS_RMD.GETS_RMD_OMD_PRODUCT WHERE PRODUCT_CD IN(:productNameLst)";
                hibernateQuery = hibernateSession.createSQLQuery(productCdQuery);
                hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                        objIdleReportSummaryVO.getProducts());
                objIdleReportProd = hibernateQuery.list();

                if (RMDCommonUtility.isCollectionNotEmpty(objIdleReportProd)) {
                    for (int i = 0; i < objIdleReportProd.size(); i++) {
                        productCd += RMDCommonConstants.SINGLE_QTE
                                + RMDCommonUtility.convertObjectToString(objIdleReportProd.get(i))
                                + RMDCommonConstants.SINGLE_QTE;
                        if (i < (objIdleReportProd.size() - 1)) {
                            productCd += ",";
                        }
                    }
                }
            }
            productFromQuery = " ,(SELECT TSP.objid FROM GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST, "
                    + " TABLE_SITE_PART TSP , GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO "
                    + " WHERE ELGASST.ASSET_ID    =VEH.OBJID AND TSP.OBJID             = VEH.VEHICLE2SITE_PART "
                    + " AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID "
                    + " AND TSP.SERIAL_NO NOT LIKE '%BAD%' AND TBO.ORG_ID = :customerId AND ELGASST.GET_RMD_OMD_PRODUCT_SEQ_ID IN "
                    + "(SELECT GET_RMD_OMD_PRODUCT_SEQ_ID "
                    + " FROM GETS_RMD.GETS_RMD_OMD_PRODUCT WHERE GET_RMD_OMD_PRODUCT_SEQ_ID IN(" + productCd
                    + "))) Prod ";
            /* If region is all selecting the if block otherwise else block */
            if (objIdleReportSummaryVO.getRegion().equalsIgnoreCase(RMDCommonConstants.ALL)) {

                idleRepSummaryQry.append(" SELECT ");
                idleRepSummaryQry.append("  REGION, ");
                idleRepSummaryQry.append("  TOTAL_REPORTED, ");
                idleRepSummaryQry.append("  NOT_MOVING_COUNT, ");
                idleRepSummaryQry.append("  NOT_MOVING_ENGINE_ON, ");
                idleRepSummaryQry.append("  NOT_MOVING_ENGINE_OFF, ");
                idleRepSummaryQry.append("  DWELL_COUNT, ");
                idleRepSummaryQry.append("  ENGINE_ON_COUNT, ");
                idleRepSummaryQry.append("  ORDER_BY ");
                idleRepSummaryQry.append(" FROM ( ");
                idleRepSummaryQry.append("  (SELECT  NVL(S2.REGION, ' ') REGION, ");
                idleRepSummaryQry.append("    S2.TOTAL_REPORTED, ");
                idleRepSummaryQry.append("    S.NOT_MOVING_COUNT, ");
                idleRepSummaryQry.append("    S.NOT_MOVING_ENGINE_ON, ");
                idleRepSummaryQry.append("    NOT_MOVING_ENGINE_OFF, ");
                idleRepSummaryQry.append("    S3.DWELL_COUNT, ");
                idleRepSummaryQry.append("    S4.ENGINE_ON_COUNT , ");
                idleRepSummaryQry.append("    '2' ORDER_BY ");
                idleRepSummaryQry.append("  FROM ");
                idleRepSummaryQry.append("    (SELECT NVL(c.REGION, ' ') REGION , ");
                idleRepSummaryQry.append("      COUNT(b.STATUS_HIST2VEHICLE) NOT_MOVING_COUNT , ");
                idleRepSummaryQry.append("      SUM(DECODE(SMS_FUEL_PUMP_RELAY,1,1,0)) NOT_MOVING_ENGINE_ON , ");
                idleRepSummaryQry.append("      SUM(DECODE(SMS_FUEL_PUMP_RELAY,0,1,0)) NOT_MOVING_ENGINE_OFF ");
                idleRepSummaryQry.append("    FROM GETS_TOOL_PP_STATUS_CURR a , ");
                idleRepSummaryQry.append("      GETS_TOOL_PP_STATUS_HIST b , ");
                idleRepSummaryQry.append("      GETS_RMD_PP_MILEPOST_DEF c, ");
                idleRepSummaryQry.append("      GETS_RMD_VEHICLE v, ");
                idleRepSummaryQry.append("      GETS_RMD_CUST_RNH_RN_V cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("  WHERE a.STATUS_CURR2VEHICLE   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append(" OR cust_view.vehicle_objid in ( ");
                idleRepSummaryQry.append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(" ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("    AND a.STATUS_CURR2VEHICLE     =v.objid ");
                idleRepSummaryQry.append("    AND a.STATUS_CURR2STATUS_HIST = b.objid ");
                idleRepSummaryQry.append("    AND b.STATUS_HIST2MILEPOST    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("    AND b.NOT_MOVING_TIMER        >    :elapsedTime ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("    GROUP BY c.REGION ");
                idleRepSummaryQry.append("    ) S, ");
                idleRepSummaryQry.append("    (SELECT NVL(c.REGION, ' ') REGION, ");
                idleRepSummaryQry.append("      COUNT(b.STATUS_HIST2VEHICLE) TOTAL_REPORTED ");
                idleRepSummaryQry.append("    FROM GETS_TOOL_PP_STATUS_CURR a , ");
                idleRepSummaryQry.append("      gets_tool_pp_status_hist b , ");
                idleRepSummaryQry.append("      gets_rmd_pp_milepost_def c, ");
                idleRepSummaryQry.append("      gets_rmd_vehicle v, ");
                idleRepSummaryQry.append("      gets_rmd_cust_rnh_rn_v cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("    WHERE a.status_curr2vehicle   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append(" OR cust_view.vehicle_objid in ( ");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("    AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("    AND a.status_curr2status_hist = b.objid ");
                idleRepSummaryQry.append("    AND b.status_hist2milepost    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");

                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("    GROUP BY c.region ");
                idleRepSummaryQry.append("    ) S2, ");
                idleRepSummaryQry.append("    (SELECT NVL(c.region, ' ') REGION, ");
                idleRepSummaryQry.append("      COUNT(b.status_hist2vehicle) DWELL_COUNT ");
                idleRepSummaryQry.append("    FROM gets_tool_pp_status_curr a , ");
                idleRepSummaryQry.append("      gets_tool_pp_status_hist b , ");
                idleRepSummaryQry.append("      gets_rmd_pp_milepost_def c, ");
                idleRepSummaryQry.append("      gets_rmd_vehicle v, ");
                idleRepSummaryQry.append("      gets_rmd_cust_rnh_rn_v cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("    WHERE a.status_curr2vehicle   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append(" OR cust_view.vehicle_objid in ( ");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId  )) ");
                idleRepSummaryQry.append("    AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("    AND a.status_curr2status_hist = b.objid ");
                idleRepSummaryQry.append("    AND b.status_hist2milepost    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("    AND b.dwell_timer             >      :elapsedTime ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("    GROUP BY c.region ");
                idleRepSummaryQry.append("    ) S3 , ");
                idleRepSummaryQry.append("    (SELECT NVL(c.region, ' ') REGION, ");
                idleRepSummaryQry.append("      COUNT(b.status_hist2vehicle) ENGINE_ON_COUNT ");
                idleRepSummaryQry.append("    FROM gets_tool_pp_status_curr a , ");
                idleRepSummaryQry.append("      gets_tool_pp_status_hist b , ");
                idleRepSummaryQry.append("      gets_rmd_pp_milepost_def c, ");
                idleRepSummaryQry.append("      gets_rmd_vehicle v, ");
                idleRepSummaryQry.append("      gets_rmd_cust_rnh_rn_v cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("    WHERE a.status_curr2vehicle   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append(" OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("    AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("    AND a.status_curr2status_hist = b.objid ");
                idleRepSummaryQry.append("    AND b.status_hist2milepost    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append(
                        "    AND b.not_moving_timer >    :elapsedTime     AND b.ENGINE_ON_TIMER >   :elapsedTime ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("    GROUP BY c.region ");
                idleRepSummaryQry.append("    ) S4 ");
                idleRepSummaryQry.append("  WHERE S2.REGION = S.REGION(+) ");
                idleRepSummaryQry.append("  AND S2.REGION   = S3.REGION(+) ");
                idleRepSummaryQry.append("  AND S2.REGION   = S4.REGION(+) ");
                idleRepSummaryQry.append("  ) ");
                idleRepSummaryQry.append(" UNION ");
                idleRepSummaryQry.append("  (SELECT ");
                idleRepSummaryQry.append("     'ALL', ");
                idleRepSummaryQry.append("    S2.TOTAL_REPORTED, ");
                idleRepSummaryQry.append("    S.NOT_MOVING_COUNT, ");
                idleRepSummaryQry.append("    S.NOT_MOVING_ENGINE_ON, ");
                idleRepSummaryQry.append("    NOT_MOVING_ENGINE_OFF, ");
                idleRepSummaryQry.append("    S3.DWELL_COUNT , ");
                idleRepSummaryQry.append("    S4.ENGINE_ON_COUNT, ");
                idleRepSummaryQry.append("    '1' ORDER_BY ");
                idleRepSummaryQry.append("  FROM ");
                idleRepSummaryQry.append("    (SELECT COUNT(b.status_hist2vehicle) NOT_MOVING_COUNT , ");
                idleRepSummaryQry.append("      SUM(DECODE(SMS_FUEL_PUMP_RELAY,1,1,0)) NOT_MOVING_ENGINE_ON , ");
                idleRepSummaryQry.append("      SUM(DECODE(SMS_FUEL_PUMP_RELAY,0,1,0)) NOT_MOVING_ENGINE_OFF ");
                idleRepSummaryQry.append("    FROM gets_tool_pp_status_curr a , ");
                idleRepSummaryQry.append("      gets_tool_pp_status_hist b , ");
                idleRepSummaryQry.append("      gets_rmd_pp_milepost_def c, ");
                idleRepSummaryQry.append("      gets_rmd_vehicle v, ");
                idleRepSummaryQry.append("      gets_rmd_cust_rnh_rn_v cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("    WHERE a.status_curr2vehicle   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append("  OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("    AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("    AND a.status_curr2status_hist = b.objid ");
                idleRepSummaryQry.append("    AND b.status_hist2milepost    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("    AND b.not_moving_timer        >     :elapsedTime ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("    ) S, ");
                idleRepSummaryQry.append("    (SELECT COUNT(b.status_hist2vehicle) TOTAL_REPORTED ");
                idleRepSummaryQry.append("    FROM gets_tool_pp_status_curr a , ");
                idleRepSummaryQry.append("      gets_tool_pp_status_hist b , ");
                idleRepSummaryQry.append("      gets_rmd_pp_milepost_def c, ");
                idleRepSummaryQry.append("      gets_rmd_vehicle v, ");
                idleRepSummaryQry.append("      gets_rmd_cust_rnh_rn_v cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("    WHERE a.status_curr2vehicle   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append(" OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("    AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("    AND a.status_curr2status_hist = b.objid ");
                idleRepSummaryQry.append("    AND b.status_hist2milepost    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("    ) S2, ");
                idleRepSummaryQry.append("    (SELECT COUNT(b.status_hist2vehicle) DWELL_COUNT ");
                idleRepSummaryQry.append("    FROM gets_tool_pp_status_curr a , ");
                idleRepSummaryQry.append("      gets_tool_pp_status_hist b , ");
                idleRepSummaryQry.append("      gets_rmd_pp_milepost_def c, ");
                idleRepSummaryQry.append("      gets_rmd_vehicle v, ");
                idleRepSummaryQry.append("      gets_rmd_cust_rnh_rn_v cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("    WHERE a.status_curr2vehicle   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append("    OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("    AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("    AND a.status_curr2status_hist = b.objid ");
                idleRepSummaryQry.append("    AND b.status_hist2milepost    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("    AND b.dwell_timer             >      :elapsedTime ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("    ) S3, ");
                idleRepSummaryQry.append("    (SELECT COUNT(b.STATUS_HIST2VEHICLE) ENGINE_ON_COUNT ");
                idleRepSummaryQry.append("    FROM GETS_TOOL_PP_STATUS_CURR a , ");
                idleRepSummaryQry.append("      GETS_TOOL_PP_STATUS_HIST b , ");
                idleRepSummaryQry.append("      GETS_RMD_PP_MILEPOST_DEF c, ");
                idleRepSummaryQry.append("      GETS_RMD_VEHICLE v, ");
                idleRepSummaryQry.append("      GETS_RMD_CUST_RNH_RN_V cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("    WHERE a.STATUS_CURR2VEHICLE   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append("    OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("    AND a.STATUS_CURR2VEHICLE     =v.objid ");
                idleRepSummaryQry.append("    AND a.STATUS_CURR2STATUS_HIST = b.objid ");
                idleRepSummaryQry.append("    AND b.STATUS_HIST2MILEPOST    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("    AND b.NOT_MOVING_TIMER        >     :elapsedTime ");
                idleRepSummaryQry.append("    AND b.ENGINE_ON_TIMER         >   :elapsedTime  ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("  ) S4) ) ");
                idleRepSummaryQry.append("    ORDER BY ORDER_BY ");
                hibernateQuery = hibernateSession.createSQLQuery(idleRepSummaryQry.toString());
                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objIdleReportSummaryVO.getCustomerId()));
                hibernateQuery.setParameter(RMDCommonConstants.ELAPSED_TIME, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objIdleReportSummaryVO.getElapsedTime()));
            } else {
                idleRepSummaryQry.append(" SELECT REGION, ");
                idleRepSummaryQry.append("   TOTAL_REPORTED, ");
                idleRepSummaryQry.append("   NOT_MOVING_COUNT, ");
                idleRepSummaryQry.append("   NOT_MOVING_ENGINE_ON, ");
                idleRepSummaryQry.append("   NOT_MOVING_ENGINE_OFF, ");
                idleRepSummaryQry.append("   DWELL_COUNT, ");
                idleRepSummaryQry.append("   ENGINE_ON_COUNT, ");
                idleRepSummaryQry.append("   ORDER_BY ");
                idleRepSummaryQry.append(" FROM ( ");
                idleRepSummaryQry.append("   (SELECT S2.REGION, ");
                idleRepSummaryQry.append("     S2.TOTAL_REPORTED, ");
                idleRepSummaryQry.append("     S.NOT_MOVING_COUNT, ");
                idleRepSummaryQry.append("     S.NOT_MOVING_ENGINE_ON, ");
                idleRepSummaryQry.append("     NOT_MOVING_ENGINE_OFF, ");
                idleRepSummaryQry.append("     S3.DWELL_COUNT, ");
                idleRepSummaryQry.append("     S4.ENGINE_ON_COUNT, ");
                idleRepSummaryQry.append("     '2' ORDER_BY ");
                idleRepSummaryQry.append("   FROM ");
                idleRepSummaryQry.append("     (SELECT c.region , ");
                idleRepSummaryQry.append("       COUNT(b.STATUS_HIST2VEHICLE) NOT_MOVING_COUNT , ");
                idleRepSummaryQry.append("       SUM(DECODE(SMS_FUEL_PUMP_RELAY,1,1,0)) NOT_MOVING_ENGINE_ON , ");
                idleRepSummaryQry.append("       SUM(DECODE(SMS_FUEL_PUMP_RELAY,0,1,0)) NOT_MOVING_ENGINE_OFF ");
                idleRepSummaryQry.append("     FROM GETS_TOOL_PP_STATUS_CURR a , ");
                idleRepSummaryQry.append("       GETS_TOOL_PP_STATUS_HIST b , ");
                idleRepSummaryQry.append("       GETS_RMD_PP_MILEPOST_DEF c, ");
                idleRepSummaryQry.append("       GETS_RMD_VEHICLE v, ");
                idleRepSummaryQry.append("       GETS_RMD_CUST_RNH_RN_V cust_view ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("     WHERE a.STATUS_CURR2VEHICLE   =cust_view.VEHICLE_OBJID ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append("  OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("     AND a.STATUS_CURR2VEHICLE     =v.objid ");
                idleRepSummaryQry.append("     AND a.STATUS_CURR2STATUS_HIST = b.objid ");
                idleRepSummaryQry.append("     AND b.STATUS_HIST2MILEPOST    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("     AND b.NOT_MOVING_TIMER        > :elapsedTime ");
                idleRepSummaryQry.append("     AND c.REGION                  =:region ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("     GROUP BY c.region ");
                idleRepSummaryQry.append("     ) S, ");
                idleRepSummaryQry.append("     (SELECT c.region, ");
                idleRepSummaryQry.append("       COUNT(b.status_hist2vehicle) TOTAL_REPORTED ");
                idleRepSummaryQry.append("     FROM gets_tool_pp_status_curr a , ");
                idleRepSummaryQry.append("       gets_tool_pp_status_hist b , ");
                idleRepSummaryQry.append("       gets_rmd_pp_milepost_def c, ");
                idleRepSummaryQry.append("       gets_rmd_vehicle v, ");
                idleRepSummaryQry.append("       gets_rmd_cust_rnh_rn_v cust_view ");
                
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("     WHERE a.STATUS_CURR2VEHICLE   =cust_view.VEHICLE_OBJID ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append("    OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("     AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("     AND a.status_curr2status_hist = b.objid ");
                idleRepSummaryQry.append("     AND b.status_hist2milepost    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("     AND c.region                  =:region ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("     GROUP BY c.region ");
                idleRepSummaryQry.append("     ) S2, ");
                idleRepSummaryQry.append("     (SELECT c.region, ");
                idleRepSummaryQry.append("       COUNT(b.status_hist2vehicle) DWELL_COUNT ");
                idleRepSummaryQry.append("     FROM gets_tool_pp_status_curr a , ");
                idleRepSummaryQry.append("       gets_tool_pp_status_hist b , ");
                idleRepSummaryQry.append("       gets_rmd_pp_milepost_def c, ");
                idleRepSummaryQry.append("       gets_rmd_vehicle v, ");
                idleRepSummaryQry.append("       gets_rmd_cust_rnh_rn_v cust_view ");
                
                
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("     WHERE a.status_curr2vehicle   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append("   OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("     AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("     AND a.STATUS_CURR2STATUS_HIST = b.objid ");
                idleRepSummaryQry.append("     AND b.STATUS_HIST2MILEPOST    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("     AND b.DWELL_TIMER             >     :elapsedTime ");
                idleRepSummaryQry.append("     AND c.REGION                  =:region ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("     GROUP BY c.REGION ");
                idleRepSummaryQry.append("     ) S3 , ");
                idleRepSummaryQry.append("     (SELECT c.REGION, ");
                idleRepSummaryQry.append("       COUNT(b.STATUS_HIST2VEHICLE) ENGINE_ON_COUNT ");
                idleRepSummaryQry.append("     FROM GETS_TOOL_PP_STATUS_CURR a , ");
                idleRepSummaryQry.append("       GETS_TOOL_PP_STATUS_HIST b , ");
                idleRepSummaryQry.append("       GETS_RMD_PP_MILEPOST_DEF c, ");
                idleRepSummaryQry.append("       GETS_RMD_VEHICLE v, ");
                idleRepSummaryQry.append("       GETS_RMD_CUST_RNH_RN_V cust_view ");
                
                
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(productFromQuery);
                }
                idleRepSummaryQry.append("     WHERE a.STATUS_CURR2VEHICLE   =cust_view.vehicle_objid ");
                idleRepSummaryQry.append("  AND ( ( cust_view.vehicle_objid IN (SELECT veh_cfg2vehicle FROM   gets_rmd_vehcfg WHERE  current_version = '1' AND vehcfg2master_bom IN (SELECT objid FROM   gets_rmd_master_bom bom WHERE  config_item = 'CMU')) ) ");
                idleRepSummaryQry.append("   OR cust_view.vehicle_objid in  (");
                idleRepSummaryQry
                        .append(" Select vehicle_objid from gets_mcs.mcs_asset a  ,gets_mcs.MCS_LOOKUP_VALUE st ");
                idleRepSummaryQry.append(
                        " ,gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, gets_rmd_cust_rnh_rn_v v ");
                idleRepSummaryQry.append(" where STATUS = st.objid  and app.APPLICATION_OBJID = service.objid ");
                idleRepSummaryQry.append(" and app.ASSET_OBJID = a.objid and v.vehicle2asset_objid = a.objid ");
                idleRepSummaryQry.append(" and APPLICATION_NAME = 'ATS' and v.org_id = :customerId )) ");
                idleRepSummaryQry.append("     AND a.status_curr2vehicle     =v.objid ");
                idleRepSummaryQry.append("     AND a.STATUS_CURR2STATUS_HIST = b.objid ");
                idleRepSummaryQry.append("     AND b.STATUS_HIST2MILEPOST    = c.objid(+) ");
                idleRepSummaryQry.append("    AND b.GPS_STATUS  = 'VALID' ");
                idleRepSummaryQry.append(" AND  cust_view.org_id = :customerId ");
                idleRepSummaryQry.append("     AND b.NOT_MOVING_TIMER        > :elapsedTime ");
                idleRepSummaryQry.append("     AND b.ENGINE_ON_TIMER         > :elapsedTime ");
                idleRepSummaryQry.append("    AND c.REGION                  =:region ");
                if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                        && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    idleRepSummaryQry.append(" AND cust_view.site_part_objid =Prod.objid ");
                }
                idleRepSummaryQry.append("     GROUP BY c.region ");
                idleRepSummaryQry.append("     ) S4 ");
                idleRepSummaryQry.append("   WHERE S2.REGION = S.REGION(+) ");
                idleRepSummaryQry.append("   AND S2.REGION   = S3.REGION(+) ");
                idleRepSummaryQry.append("   AND S2.REGION   = S4.REGION(+)) ");
                idleRepSummaryQry.append("   ) ORDER BY ORDER_BY ");

                hibernateQuery = hibernateSession.createSQLQuery(idleRepSummaryQry.toString());
                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objIdleReportSummaryVO.getCustomerId()));
                hibernateQuery.setParameter(RMDCommonConstants.ELAPSED_TIME, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objIdleReportSummaryVO.getElapsedTime()));
                hibernateQuery.setParameter(RMDCommonConstants.REGION, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objIdleReportSummaryVO.getRegion()));
            }
            objIdleSummReport = hibernateQuery.list();

            if (RMDCommonUtility.isCollectionNotEmpty(objIdleSummReport)) {
                Object[] objIdleSummaryRep = null;
                for (int i = 0; i < objIdleSummReport.size(); i++) {
                    objIdleSummaryRep = objIdleSummReport.get(i);
                    objIdleSummRespVO = new IdleReportSummaryResponseVO();
                    objIdleSummRespVO.setRegion(RMDCommonUtility.convertObjectToString(objIdleSummaryRep[0]));
                    objIdleSummRespVO.setTotalReported(RMDCommonUtility.convertObjectToString(objIdleSummaryRep[1]));
                    objIdleSummRespVO.setNtMovingCnt(RMDCommonUtility.convertObjectToString(objIdleSummaryRep[2]));
                    objIdleSummRespVO.setNtMovingEngOn(RMDCommonUtility.convertObjectToString(objIdleSummaryRep[3]));
                    objIdleSummRespVO.setNtMovingEngOff(RMDCommonUtility.convertObjectToString(objIdleSummaryRep[4]));
                    objIdleSummRespVO.setDwellCnt(RMDCommonUtility.convertObjectToString(objIdleSummaryRep[5]));
                    objIdleSummRespVO.setEngOnCnt(RMDCommonUtility.convertObjectToString(objIdleSummaryRep[6]));
                    objIdleSummRespVO.setOrderBy(RMDCommonUtility.convertObjectToString(objIdleSummaryRep[7]));
                    objIdleSummRespVOLst.add(objIdleSummRespVO);
                }
            }

        } catch (JDBCException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_SQL_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }

        return objIdleSummRespVOLst;

    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.idlereport.dao.intf.IdleReportDAOIntf#
     * getIdleReportDetails
     * (com.ge.trans.pp.services.idlereport.service.valueobjects
     * .IdleReportSummaryReqVO) This method is used for displaying Idle report
     * details
     */
    @Override
    public List<IdleReportDetailsResponseVO> getIdleReportDetails(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDDAOException {

        Session hibernateSession = null;
        StringBuilder idleRepDetailQry = new StringBuilder();
        List<Object[]> objIdleDetailReport = null;
        IdleReportDetailsResponseVO objIdleDetailRespVO = null;
        List<IdleReportDetailsResponseVO> objIdleDetRespVOLst = new ArrayList<IdleReportDetailsResponseVO>();
        String region = RMDCommonConstants.EMPTY_STRING;
        String timer = RMDCommonConstants.EMPTY_STRING;
        String startTime = RMDCommonConstants.EMPTY_STRING;
        Object[] objIdleDetailRep = null;
        Map<String, String> productObjidMap = new HashMap<String, String>();
        try {
            hibernateSession = getHibernateSession();
            if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                    && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                StringBuilder prodQueryString = new StringBuilder();
                prodQueryString.append(
                        " SELECT TSP.X_VEH_HDR,TSP.SERIAL_NO FROM GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST, ");
                prodQueryString.append(" TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, ");
                prodQueryString.append(" TABLE_BUS_ORG TBO WHERE ELGASST.ASSET_ID = VEH.OBJID ");
                prodQueryString
                        .append(" AND TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID ");
                prodQueryString.append(" AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
                prodQueryString.append(" AND TBO.ORG_ID = '" + objIdleReportSummaryVO.getCustomerId());
                prodQueryString
                        .append("' AND ELGASST.GET_RMD_OMD_PRODUCT_SEQ_ID IN (SELECT GET_RMD_OMD_PRODUCT_SEQ_ID ");
                prodQueryString.append(" FROM GETS_RMD.GETS_RMD_OMD_PRODUCT WHERE PRODUCT_CD IN (");
                for (int i = 0; i < objIdleReportSummaryVO.getProducts().size(); i++) {
                    prodQueryString.append("'" + objIdleReportSummaryVO.getProducts().get(i) + "'");
                    if ((i + 1) < objIdleReportSummaryVO.getProducts().size()) {
                        prodQueryString.append(", ");
                    }
                }
                prodQueryString.append(" ) ) ");
                Query query = hibernateSession.createSQLQuery(prodQueryString.toString());
                List<Object> productResList = query.list();
                if (RMDCommonUtility.isCollectionNotEmpty(productResList)) {
                    for (final Iterator<Object> iter = productResList.iterator(); iter.hasNext();) {
                        final Object[] prodRec = (Object[]) iter.next();
                        String key = RMDCommonUtility.convertObjectToString(prodRec[0]) + RMDCommonConstants.UNDERSCORE
                                + RMDCommonUtility.convertObjectToString(prodRec[1]);
                        productObjidMap.put(key, key);
                    }
                }
            }

            if (null != objIdleReportSummaryVO.getRegion() && !objIdleReportSummaryVO.getRegion().isEmpty()) {
                if (objIdleReportSummaryVO.getRegion().equalsIgnoreCase(RMDCommonConstants.CAPS_ALL)) {
                    region = RMDCommonConstants.EMPTY_STRING;
                } else {
                    region = " and c.region='" + objIdleReportSummaryVO.getRegion() + "'";
                }
            }
            /* Based on idle Type condition Populating the query */
            if (null != objIdleReportSummaryVO.getIdleType() && !objIdleReportSummaryVO.getIdleType().isEmpty()) {
                if (objIdleReportSummaryVO.getIdleType().equalsIgnoreCase(RMDCommonConstants.NOT_MOVING_ALL)) {
                    timer = " and b.not_moving_timer >" + objIdleReportSummaryVO.getElapsedTime();
                    startTime = " ,TO_CHAR(b.NOT_MOVING_SINCE,'MM/DD/YY HH24:MI:SS') Not_Moving_Since ";
                } else if (objIdleReportSummaryVO.getIdleType().equalsIgnoreCase(RMDCommonConstants.DWELL_ONLY)) {
                    timer = "  and b.dwell_timer >" + objIdleReportSummaryVO.getElapsedTime();
                    startTime = " ,TO_CHAR(b.DWELL_SINCE,'MM/DD/YY HH24:MI:SS') ";
                } else if (objIdleReportSummaryVO.getIdleType()
                        .equalsIgnoreCase(RMDCommonConstants.ENGINE_ON_NOT_MOVING)) {
                    timer = "  and b.engine_on_timer >" + objIdleReportSummaryVO.getElapsedTime()
                            + " and b.not_moving_timer > " + objIdleReportSummaryVO.getElapsedTime();
                    startTime = " ,TO_CHAR(b.NOT_MOVING_SINCE,'MM/DD/YY HH24:MI:SS') Not_Moving_Since ";
                } else if (objIdleReportSummaryVO.getIdleType().equalsIgnoreCase(RMDCommonConstants.DWELL_NOT_MOVING)) {
                    timer = "  and ((b.dwell_timer >" + objIdleReportSummaryVO.getElapsedTime()
                            + " ) or (b.not_moving_timer > " + objIdleReportSummaryVO.getElapsedTime() + " ))";
                    startTime = " ,TO_CHAR(b.DWELL_SINCE,'MM/DD/YY HH24:MI:SS')  Not_Moving_Since ";
                }
            }

            idleRepDetailQry.append(" SELECT * FROM ( ");
            idleRepDetailQry.append(" SELECT c.REGION, ");
            idleRepDetailQry.append("   c.SUB_REGION, cust_view.ORG_ID, ");
            idleRepDetailQry.append("   cust_view.vehicle_hdr, ");
            idleRepDetailQry.append("   cust_view.vehicle_no, ");
            idleRepDetailQry.append(
                    "   ROUND(b.NOT_MOVING_TIMER /60,1) Not_Moving_Hours,    ROUND((LEAST(b.NOT_MOVING_TIMER,b.ENGINE_ON_TIMER)/60),1) Engine_On_Hours, ");
            idleRepDetailQry.append("   DECODE(b.sms_fuel_pump_relay,1,'On','Off') SMS_FUEL_PUMP_RELAY, ");
            idleRepDetailQry.append("   ROUND(b.dwell_timer/60,1) Dwell_Hours, ");
            idleRepDetailQry
                    .append("   DECODE(NVL(b.nearest_location_miles,0),0,' ',ROUND(b.nearest_location_miles,2) ");
            idleRepDetailQry.append("   ||' Miles  ' ");
            idleRepDetailQry.append("   ||b.direction_from_location ");
            idleRepDetailQry.append("   || ' of ') Nearest_Location, ");
            idleRepDetailQry.append("   b.location_desc, ");
            idleRepDetailQry.append("   b.state_province ");
            idleRepDetailQry.append("" + startTime + "");
            idleRepDetailQry.append("   ,TO_CHAR(b.cmu_time,'MM/DD/YY HH24:MI:SS') CMU_Time ");
            idleRepDetailQry.append(" ,   B.GPS_LAT_DISPLAY, B.GPS_LON_DISPLAY");
            idleRepDetailQry.append(
                    "   FROM GETS_TOOL_PP_STATUS_CURR a , GETS_TOOL_PP_STATUS_HIST b , GETS_RMD_PP_MILEPOST_DEF c, GETS_RMD_VEHICLE v, GETS_RMD_CUST_RNH_RN_V cust_view, ");
            idleRepDetailQry.append("					   gets_rmd_vehcfg cfg, ");
            idleRepDetailQry.append("					   gets_rmd_master_bom bom ");
            idleRepDetailQry.append("                WHERE  a.status_curr2vehicle=cust_view.vehicle_objid ");
            idleRepDetailQry.append("				AND   cust_view.vehicle_objid = cfg.veh_cfg2vehicle ");
            idleRepDetailQry.append("				AND cfg.vehcfg2master_bom = bom.objid ");
            idleRepDetailQry.append("                AND    ((  bom.config_item = 'CMU' AND cfg.current_version = '1' AND    cust_view.service_pinpoint=1) ");
            idleRepDetailQry.append("                       OR     ( ( ( bom.config_item = 'CMU' AND cfg.current_version = '2' ) ");
            idleRepDetailQry.append("							   OR ( bom.config_item = 'HPEAP' AND cfg.current_version = '1' ) ");
            idleRepDetailQry.append("							   OR ( bom.config_item = 'LCV' AND cfg.current_version = '1' ) ");
            idleRepDetailQry.append("							   OR ( bom.config_item = 'LIG' AND cfg.current_version = '1' ) ) ");
            idleRepDetailQry.append("                              AND    cust_view.service_offboard_pp = 1)) ");
            idleRepDetailQry.append("                AND    a.status_curr2vehicle=v.objid ");
            idleRepDetailQry.append("                AND    a.status_curr2status_hist = b.objid ");
            idleRepDetailQry.append("                AND    b.status_hist2milepost = c.objid(+) ");
            idleRepDetailQry.append("                AND    b.gps_status = 'VALID' ");
            idleRepDetailQry.append(" AND  cust_view.org_id = :customerId ");
            idleRepDetailQry.append("" + region + "");
            idleRepDetailQry.append("" + timer + "");
            idleRepDetailQry.append(" UNION ");
            idleRepDetailQry.append(" SELECT c.REGION, ");
            idleRepDetailQry.append("   c.SUB_REGION, cust_view.ORG_ID, ");
            idleRepDetailQry.append("   cust_view.vehicle_hdr, ");
            idleRepDetailQry.append("   cust_view.vehicle_no, ");
            idleRepDetailQry.append(
                    "   ROUND(b.NOT_MOVING_TIMER /60,1) Not_Moving_Hours,    ROUND((LEAST(b.NOT_MOVING_TIMER,b.ENGINE_ON_TIMER)/60),1) Engine_On_Hours, ");
            idleRepDetailQry.append("   DECODE(b.sms_fuel_pump_relay,1,'On','Off') SMS_FUEL_PUMP_RELAY, ");
            idleRepDetailQry.append("   ROUND(b.dwell_timer/60,1) Dwell_Hours, ");
            idleRepDetailQry
                    .append("   DECODE(NVL(b.nearest_location_miles,0),0,' ',ROUND(b.nearest_location_miles,2) ");
            idleRepDetailQry.append("   ||' Miles  ' ");
            idleRepDetailQry.append("   ||b.direction_from_location ");
            idleRepDetailQry.append("   || ' of ') Nearest_Location, ");
            idleRepDetailQry.append("   b.location_desc, ");
            idleRepDetailQry.append("   b.state_province ");
            idleRepDetailQry.append("" + startTime + "");
            idleRepDetailQry.append("   ,TO_CHAR(b.cmu_time,'MM/DD/YY HH24:MI:SS') CMU_Time ");
            idleRepDetailQry.append(" ,   B.GPS_LAT_DISPLAY, B.GPS_LON_DISPLAY");
            idleRepDetailQry.append(
                    "  FROM GETS_TOOL_PP_STATUS_CURR a , GETS_TOOL_PP_STATUS_HIST b , GETS_RMD_PP_MILEPOST_DEF c, GETS_RMD_VEHICLE v, GETS_RMD_CUST_RNH_RN_V cust_view, ");
            idleRepDetailQry.append(
                    "  gets_mcs.mcs_asset a1, gets_mcs.MCS_LOOKUP_VALUE st, gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app ");
            idleRepDetailQry.append("  WHERE a.STATUS_CURR2VEHICLE=cust_view.VEHICLE_OBJID ");
            idleRepDetailQry.append("   AND STATUS                        = st.objid ");
            idleRepDetailQry.append("   AND app.APPLICATION_OBJID         = service.objid ");
            idleRepDetailQry.append("   AND app.ASSET_OBJID               = a1.objid ");
            idleRepDetailQry.append("   AND cust_view.vehicle2asset_objid = a1.objid ");
            idleRepDetailQry.append("   AND APPLICATION_NAME              = 'ATS' ");
            idleRepDetailQry.append("  AND a.STATUS_CURR2VEHICLE=v.OBJID ");
            idleRepDetailQry
                    .append("   AND a.STATUS_CURR2STATUS_HIST = b.OBJID AND b.STATUS_HIST2MILEPOST = c.OBJID(+) "
                            + "AND b.GPS_STATUS   = 'VALID' ");
            idleRepDetailQry.append(" AND  cust_view.org_id = :customerId ");
            idleRepDetailQry.append("" + region + "");
            idleRepDetailQry.append("" + timer + "");
            idleRepDetailQry.append(" ) ORDER BY REGION ");

            Query hibernateQuery = hibernateSession.createSQLQuery(idleRepDetailQry.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, objIdleReportSummaryVO.getCustomerId());

            objIdleDetailReport = hibernateQuery.list();

            if (RMDCommonUtility.isCollectionNotEmpty(objIdleDetailReport)) {

                for (int i = 0; i < objIdleDetailReport.size(); i++) {
                    objIdleDetailRep = objIdleDetailReport.get(i);
                    String key = RMDCommonUtility.convertObjectToString(objIdleDetailRep[3])
                            + RMDCommonConstants.UNDERSCORE
                            + RMDCommonUtility.convertObjectToString(objIdleDetailRep[4]);
                    if (objIdleReportSummaryVO.getProducts() != null && !objIdleReportSummaryVO.getProducts().isEmpty()
                            && !objIdleReportSummaryVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)
                            && productObjidMap.get(key) == null) {
                        continue;
                    }
                    objIdleDetailRespVO = new IdleReportDetailsResponseVO();
                    objIdleDetailRespVO.setRegion(RMDCommonUtility.convertObjectToString(objIdleDetailRep[0]));
                    objIdleDetailRespVO.setSubRegion(RMDCommonUtility.convertObjectToString(objIdleDetailRep[1]));
                    objIdleDetailRespVO.setCustomerId(RMDCommonUtility.convertObjectToString(objIdleDetailRep[2]));
                    objIdleDetailRespVO.setRoadInitial(RMDCommonUtility.convertObjectToString(objIdleDetailRep[3]));
                    objIdleDetailRespVO.setRoadNumber(RMDCommonUtility.convertObjectToString(objIdleDetailRep[4]));
                    objIdleDetailRespVO
                            .setNtMovingDuration(RMDCommonUtility.convertObjectToString(objIdleDetailRep[5]));
                    objIdleDetailRespVO.setEngOnDuration(RMDCommonUtility.convertObjectToString(objIdleDetailRep[6]));
                    objIdleDetailRespVO.setCurrEngState(RMDCommonUtility.convertObjectToString(objIdleDetailRep[7]));
                    objIdleDetailRespVO.setDwellDuration(RMDCommonUtility.convertObjectToString(objIdleDetailRep[8]));
                    objIdleDetailRespVO.setDistance(RMDCommonUtility.convertObjectToString(objIdleDetailRep[9]));
                    objIdleDetailRespVO.setLocation(RMDCommonUtility.convertObjectToString(objIdleDetailRep[10]));
                    objIdleDetailRespVO.setState(RMDCommonUtility.convertObjectToString(objIdleDetailRep[11]));
                    objIdleDetailRespVO.setIdleSince(RMDCommonUtility.convertObjectToString(objIdleDetailRep[12]));
                    objIdleDetailRespVO.setLastMsgTime(RMDCommonUtility.convertObjectToString(objIdleDetailRep[13]));
                    objIdleDetRespVOLst.add(objIdleDetailRespVO);
                }
            }

        } catch (JDBCException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_SQL_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }

        return objIdleDetRespVOLst;

    }

}
/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCommStatusDAOImpl.java
 * Description : DAO implementation for Vehicle Comm Status 
 * 
 * Package : com.ge.trans.rmd.services.asset.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 4, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.asset.dao.intf.VehicleCommStatusEoaDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleComStatusInputEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCommServiceResultEoaVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 4, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :DAO implementation for Vehicle Comm Status
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class VehicleCommStatusEoaDAOImpl extends RMDCommonDAO implements VehicleCommStatusEoaDAOIntf {

    /**
     * 
     */
    private static final long serialVersionUID = -604969764136077951L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(VehicleCommStatusEoaDAOImpl.class);

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.asset.dao.intf.AssetDAOIntf#
     * getVehicleCommStatusDetails
     * (com.ge.trans.rmd.services.cases.service.valueobjects
     * .VehicleComStatusInputServiceVO)
     *//*
       * This Method is used for fetch the details for the VehicleCommStatus
       * Pages from database
       */
    @Override
    public VehicleCommServiceResultEoaVO[] getVehicleCommStatusDetails(
            VehicleComStatusInputEoaServiceVO objComStatusInputServiceVO) throws RMDDAOException {
        List arlCtrlCfg;
        Session objHibernateSession = null;
        String strVehNo = null;
        String strAstGrp = null;
        String strCusID = null;

        List lstVehicleCommServiceResult = new ArrayList<VehicleCommServiceResultEoaVO>();

        Map<String, VehicleCommServiceResultEoaVO> downloadmap = new HashMap<String, VehicleCommServiceResultEoaVO>();
        Map<String, VehicleCommServiceResultEoaVO> mapLastProcessedDt = new HashMap<String, VehicleCommServiceResultEoaVO>();
        Map<String, VehicleCommServiceResultEoaVO> mapHlthChkReq = new HashMap<String, VehicleCommServiceResultEoaVO>();
        Map<String, VehicleCommServiceResultEoaVO> mapKeepAlive = new HashMap<String, VehicleCommServiceResultEoaVO>();
        Map<String, VehicleCommServiceResultEoaVO> mapCtrlConfigt = new HashMap<String, VehicleCommServiceResultEoaVO>();
        Map mapAsset = new HashMap();
        try {
            objHibernateSession = getHibernateSession();
            if (null != objComStatusInputServiceVO) {
                strVehNo = objComStatusInputServiceVO.getStrRoadNumber();
                strAstGrp = objComStatusInputServiceVO.getAssetGroupName();
                strCusID = objComStatusInputServiceVO.getCustomerID();
            }
            // To get asset num,asset grp name, customer id ,last fault reset
            // time and fault received time based on asset num,asset grp name,
            // customer id if passed
            StringBuilder sbLastFltRstRecQuery = new StringBuilder();
            sbLastFltRstRecQuery.append(
                    "SELECT TBO.ORG_ID, TSP.X_VEH_HDR, TSP.SERIAL_NO, TO_CHAR(MAX(TF.CREATION_DATE),'MM/DD/YYYY HH24:MI:SS'),TO_CHAR(MAX(TF.FAULT_RESET_DATE),'MM/DD/YYYY HH24:MI:SS') ");
            sbLastFltRstRecQuery.append(
                    " FROM GETS_TOOL_FAULT TF,TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO ");
            sbLastFltRstRecQuery.append(
                    "WHERE TF.FAULT2VEHICLE = VEH.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART	AND TSP.SERIAL_NO NOT LIKE '%BAD%' AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
            if (objComStatusInputServiceVO.isLastFault()) {
                sbLastFltRstRecQuery.append(" AND TF.RECORD_TYPE = 'FLT' ");
            } else {
                sbLastFltRstRecQuery.append(" AND TF.RECORD_TYPE IN ('STP','CEL','FLT') ");
            }
            if (null != strVehNo) {
                sbLastFltRstRecQuery.append("AND TSP.SERIAL_NO =  :vehNum AND tsp.x_veh_hdr=:grpNam ");
            }
            if (null != strCusID) {
                sbLastFltRstRecQuery.append("AND TBO.ORG_ID=:custID ");
            }
            // Added by Juno to limit search range -Start
            sbLastFltRstRecQuery.append(
                    "AND TF.OBJID > (SELECT MAX(OBJID) -100000 FROM GETS_TOOL_FAULT)  AND TF.LAST_UPDATED_DATE > SYSDATE -5 ");
            // Added by Juno to limit search range -End
            sbLastFltRstRecQuery.append("GROUP BY TBO.ORG_ID, TSP.X_VEH_HDR, TSP.SERIAL_NO ");
            Query fltRstRecQuery = objHibernateSession.createSQLQuery(sbLastFltRstRecQuery.toString());

            if (null != strVehNo) {
                fltRstRecQuery.setParameter(RMDCommonConstants.VEH_NUM, strVehNo);
                fltRstRecQuery.setParameter(RMDCommonConstants.GRPNAM, strAstGrp);
            }
            if (null != strCusID) {
                fltRstRecQuery.setParameter(RMDCommonConstants.CUSTID, strCusID);
            }

            fltRstRecQuery.setFetchSize(1000);
            List lstFltRstRecTime = fltRstRecQuery.list();

            if (null != lstFltRstRecTime && lstFltRstRecTime.size() > 0) {
                for (int j = 0; j < lstFltRstRecTime.size(); j++) {
                    Date dtLastFltRec = null;
                    Date dtLastFltRst = null;
                    VehicleCommServiceResultEoaVO lstfaultVehicleCommServRsltVO = new VehicleCommServiceResultEoaVO();
                    Object[] objFltRstRecTime = (Object[]) lstFltRstRecTime.get(j);
                    if (null != objFltRstRecTime) {

                        lstfaultVehicleCommServRsltVO
                                .setStrCustomerId(RMDCommonUtility.convertObjectToString(objFltRstRecTime[0]));

                        lstfaultVehicleCommServRsltVO
                                .setStrAssetGrpName(RMDCommonUtility.convertObjectToString(objFltRstRecTime[1]));

                        lstfaultVehicleCommServRsltVO
                                .setStrAssetNumber(RMDCommonUtility.convertObjectToString(objFltRstRecTime[2]));

                        if (null != objFltRstRecTime[3]) {
                            DateFormat formatter = new SimpleDateFormat(
                                    RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                            dtLastFltRec = formatter.parse(RMDCommonUtility.convertObjectToString(objFltRstRecTime[3]));
                            lstfaultVehicleCommServRsltVO.setDtLastFaultReserved(dtLastFltRec);
                        }

                        if (null != objFltRstRecTime[4]) {
                            DateFormat formatter = new SimpleDateFormat(
                                    RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                            dtLastFltRst = formatter.parse(RMDCommonUtility.convertObjectToString(objFltRstRecTime[4]));
                            lstfaultVehicleCommServRsltVO.setDtLastFaultResetTime(dtLastFltRst);
                        }

                    }
                    String key = lstfaultVehicleCommServRsltVO.getStrAssetNumber()
                            + lstfaultVehicleCommServRsltVO.getStrAssetGrpName()
                            + lstfaultVehicleCommServRsltVO.getStrCustomerId();
                    downloadmap.put(key, lstfaultVehicleCommServRsltVO);
                }
            }
            /* Added the if check as the query is taking too much time end */
            // To get asset num,asset grp name, customer id ,last processed date
            // based on asset num,asset grp name, customer id if passed
            StringBuilder sbLastPrcDateQuery = new StringBuilder();
            sbLastPrcDateQuery.append(
                    "SELECT TBO.ORG_ID,TSP.X_VEH_HDR, TSP.SERIAL_NO, TO_CHAR(MAX(TOOLRUN.LAST_UPDATED_DATE),'MM/DD/YYYY HH24:MI:SS') LASTTOOLRUNLOADDATE ");
            sbLastPrcDateQuery.append(
                    "FROM GETS_TOOL_RUN TOOLRUN,GETS_RMD_VEHICLE VEH,TABLE_SITE_PART TSP,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO ");
            sbLastPrcDateQuery.append(
                    "WHERE TOOLRUN.RUN2VEHICLE = VEH.OBJID AND VEH.VEHICLE2SITE_PART = TSP.OBJID AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            if (null != strVehNo) {
                sbLastPrcDateQuery.append(" AND TSP.SERIAL_NO = :vehNum AND TSP.X_VEH_HDR =:grpNam ");
            }
            if (null != strCusID) {
                sbLastPrcDateQuery.append("AND TBO.ORG_ID =:custID ");
            }
            sbLastPrcDateQuery.append("GROUP BY  TBO.ORG_ID,  TSP.X_VEH_HDR,TSP.SERIAL_NO ");
            Query lastPrcDateQuery = objHibernateSession.createSQLQuery(sbLastPrcDateQuery.toString());

            if (null != strVehNo) {
                lastPrcDateQuery.setParameter(RMDCommonConstants.VEH_NUM, strVehNo);
                lastPrcDateQuery.setParameter(RMDCommonConstants.GRPNAM, strAstGrp);
            }
            if (null != strCusID) {
                lastPrcDateQuery.setParameter(RMDCommonConstants.CUSTID, strCusID);
            }

            lastPrcDateQuery.setFetchSize(1000);
            List lstLastPrcDate = lastPrcDateQuery.list();
            if (null != lstLastPrcDate && lstLastPrcDate.size() > 0) {

                for (int i = 0; i < lstLastPrcDate.size(); i++) {
                    Date dtLastProcessDate = null;
                    VehicleCommServiceResultEoaVO lstPrcDtVehicleCommServRsltVO = new VehicleCommServiceResultEoaVO();
                    Object[] objLastPrcDate = (Object[]) lstLastPrcDate.get(i);

                    if (null != objLastPrcDate) {

                        lstPrcDtVehicleCommServRsltVO
                                .setStrCustomerId(RMDCommonUtility.convertObjectToString(objLastPrcDate[0]));

                        lstPrcDtVehicleCommServRsltVO
                                .setStrAssetGrpName(RMDCommonUtility.convertObjectToString(objLastPrcDate[1]));

                        lstPrcDtVehicleCommServRsltVO
                                .setStrAssetNumber(RMDCommonUtility.convertObjectToString(objLastPrcDate[2]));
                        if (null != RMDCommonUtility.convertObjectToString(objLastPrcDate[3])) {
                            DateFormat formatter = new SimpleDateFormat(
                                    RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                            dtLastProcessDate = formatter
                                    .parse(RMDCommonUtility.convertObjectToString(objLastPrcDate[3]));
                            lstPrcDtVehicleCommServRsltVO.setDtLastProcessedDate(dtLastProcessDate);
                        }

                        String key = lstPrcDtVehicleCommServRsltVO.getStrAssetNumber()
                                + lstPrcDtVehicleCommServRsltVO.getStrAssetGrpName()
                                + lstPrcDtVehicleCommServRsltVO.getStrCustomerId();
                        mapLastProcessedDt.put(key, lstPrcDtVehicleCommServRsltVO);
                    }
                }
            }

            // To get asset num,asset grp name, customer id,last Health check
            // request received based on asset num,asset grp name, customer id
            // if passed.
            StringBuilder sbLastHCReqQuery = new StringBuilder();
            if (null != objComStatusInputServiceVO.getStrRoadNumber()
                    && objComStatusInputServiceVO.getStrRoadNumber().length() > 0) {
                sbLastHCReqQuery.append(
                        "SELECT TBO.ORG_ID, TSP.X_VEH_HDR, TSP.SERIAL_NO, TO_CHAR(MAX(TF.CREATION_DATE),'MM/DD/YYYY HH24:MI:SS')");
                sbLastHCReqQuery.append(
                        " FROM GETS_TOOL_FAULT TF,TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO ");
                sbLastHCReqQuery.append(
                        "WHERE TF.FAULT2VEHICLE = VEH.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
                if (null != strVehNo) {
                    sbLastHCReqQuery.append(" AND TSP.SERIAL_NO = :vehNum AND TSP.X_VEH_HDR=:grpNam ");
                }
                if (null != strCusID) {
                    sbLastHCReqQuery.append(" AND TBO.ORG_ID =:custID ");
                }
                sbLastHCReqQuery.append(" GROUP BY TBO.ORG_ID, TSP.X_VEH_HDR, TSP.SERIAL_NO ");
                Query hcReqQuery = objHibernateSession.createSQLQuery(sbLastHCReqQuery.toString());

                if (null != strVehNo) {
                    hcReqQuery.setParameter(RMDCommonConstants.VEH_NUM, strVehNo);
                    hcReqQuery.setParameter(RMDCommonConstants.GRPNAM, strAstGrp);
                }
                if (null != strCusID) {
                    hcReqQuery.setParameter(RMDCommonConstants.CUSTID, strCusID);
                }
                hcReqQuery.setFetchSize(10);
                List lstHCReqTime = hcReqQuery.list();
                if (null != lstHCReqTime && lstHCReqTime.size() > 0) {
                    for (int i = 0; i < lstHCReqTime.size(); i++) {
                        Date dtLastHCReq = null;
                        VehicleCommServiceResultEoaVO hcReqTimeVehicleCommServRsltVO = new VehicleCommServiceResultEoaVO();
                        if (null != lstHCReqTime.get(i)) {
                            Object[] objHCReqTime = (Object[]) lstHCReqTime.get(i);

                            hcReqTimeVehicleCommServRsltVO
                                    .setStrCustomerId(RMDCommonUtility.convertObjectToString(objHCReqTime[0]));

                            hcReqTimeVehicleCommServRsltVO
                                    .setStrAssetGrpName(RMDCommonUtility.convertObjectToString(objHCReqTime[1]));

                            hcReqTimeVehicleCommServRsltVO
                                    .setStrAssetNumber(RMDCommonUtility.convertObjectToString(objHCReqTime[2]));
                            if (null != RMDCommonUtility.convertObjectToString(objHCReqTime[3])) {
                                DateFormat formatter = new SimpleDateFormat(
                                        RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                                dtLastHCReq = formatter.parse(RMDCommonUtility.convertObjectToString(objHCReqTime[3]));
                            }
                            hcReqTimeVehicleCommServRsltVO.setDtLastHealthChkRequest(dtLastHCReq);
                            String key = hcReqTimeVehicleCommServRsltVO.getStrAssetNumber()
                                    + hcReqTimeVehicleCommServRsltVO.getStrAssetGrpName()
                                    + hcReqTimeVehicleCommServRsltVO.getStrCustomerId();

                            mapHlthChkReq.put(key, hcReqTimeVehicleCommServRsltVO);
                        }
                    }
                }
            }
            // To get asset num,asset grp name, customer id,last keep alive
            // based on asset num,asset grp name, customer id if passed.
            if (null != objComStatusInputServiceVO.getStrRoadNumber()
                    && objComStatusInputServiceVO.getStrRoadNumber().length() > 0) {
                StringBuilder getVehicleObjid = new StringBuilder();
                getVehicleObjid.append(
                        "SELECT VEH.OBJID FROM  TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO ");
                getVehicleObjid.append(
                        "WHERE TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
                if (null != strVehNo) {
                    getVehicleObjid.append("AND TSP.SERIAL_NO = :vehNum AND TSP.X_VEH_HDR=:grpNam ");
                }
                if (null != strCusID) {
                    getVehicleObjid.append("AND TBO.ORG_ID =:custID ");
                }
                Query qGetVehicleObjidQry = objHibernateSession.createSQLQuery(getVehicleObjid.toString());
                if (null != strVehNo) {
                    qGetVehicleObjidQry.setParameter(RMDCommonConstants.VEH_NUM, strVehNo);
                    qGetVehicleObjidQry.setParameter(RMDCommonConstants.GRPNAM, strAstGrp);
                }
                if (null != strCusID) {
                    qGetVehicleObjidQry.setParameter(RMDCommonConstants.CUSTID, strCusID);
                }
                ArrayList arlGetVehObjId = (ArrayList) qGetVehicleObjidQry.list();

                if (null != arlGetVehObjId && arlGetVehObjId.size() > 0) {
                    String strVehObjIds[] = new String[arlGetVehObjId.size()];
                    for (int i = 0; i < arlGetVehObjId.size(); i++) {
                        strVehObjIds[i] = arlGetVehObjId.get(i).toString();
                    }

                    StringBuilder lastKeepAliveQuery = new StringBuilder();
                    lastKeepAliveQuery.append("SELECT TO_CHAR(STATUS.LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS') ");
                    lastKeepAliveQuery.append(" FROM GETS_RMD_FAULT_COMM_STATUS STATUS , GETS_RMD_VEHICLE VEH ");
                    lastKeepAliveQuery.append(
                            " WHERE VEH.OBJID = FAULT_COMM_STATUS2VEHICLE AND STATUS.OBJID = (SELECT MAX(OBJID) FROM GETS_RMD_FAULT_COMM_STATUS ");
                    lastKeepAliveQuery.append("WHERE FAULT_COMM_STATUS2VEHICLE IN (:vehicleObjid)) ");

                    Query qlastKeepAliveQuery = objHibernateSession.createSQLQuery(lastKeepAliveQuery.toString());

                    if (null != arlGetVehObjId && !arlGetVehObjId.isEmpty()) {
                        qlastKeepAliveQuery.setParameterList(RMDCommonConstants.VEHICLE_OBJID, strVehObjIds);
                    }
                    qlastKeepAliveQuery.setFetchSize(10);
                    List lstKeepAlive = qlastKeepAliveQuery.list();
                    if (null != lstKeepAlive && lstKeepAlive.size() > 0) {
                        for (int i = 0; i < lstKeepAlive.size(); i++) {

                            VehicleCommServiceResultEoaVO keepAliveVehicleCommServRsltVO = new VehicleCommServiceResultEoaVO();
                            DateFormat formatter = new SimpleDateFormat(
                                    RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                            Date dtLastKeepAlive = formatter.parse((String) lstKeepAlive.get(i));
                            keepAliveVehicleCommServRsltVO.setDtLastKeepAliveMsgRevd(dtLastKeepAlive);

                            keepAliveVehicleCommServRsltVO.setStrAssetGrpName(strAstGrp);
                            keepAliveVehicleCommServRsltVO.setStrAssetNumber(strVehNo);
                            keepAliveVehicleCommServRsltVO.setStrCustomerId(strCusID);

                            String key = keepAliveVehicleCommServRsltVO.getStrAssetNumber()
                                    + keepAliveVehicleCommServRsltVO.getStrAssetGrpName()
                                    + keepAliveVehicleCommServRsltVO.getStrCustomerId();
                            mapKeepAlive.put(key, keepAliveVehicleCommServRsltVO);
                            // }
                        }
                    }

                }
            }
            // To get asset num,asset grp name, customer id,ctrl config based on
            // asset num,asset grp name, customer id if passed

            StringBuilder sbControllerConfigQuery = new StringBuilder();
            sbControllerConfigQuery.append("SELECT TBO.ORG_ID,TSP.X_VEH_HDR,TSP.SERIAL_NO,X_CONTROLLER_CFG ");
            sbControllerConfigQuery.append(
                    "FROM TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO ");
            sbControllerConfigQuery.append(
                    "WHERE TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            if (null != strVehNo) {
                sbControllerConfigQuery.append(" AND TSP.SERIAL_NO = :vehNum AND TSP.X_VEH_HDR =:grpNam ");
            }
            if (null != strCusID) {
                sbControllerConfigQuery.append("AND TBO.ORG_ID =:custID ");
            }
            Query controllerConfigQuery = objHibernateSession.createSQLQuery(sbControllerConfigQuery.toString());
            if (null != strVehNo) {
                controllerConfigQuery.setParameter(RMDCommonConstants.VEH_NUM, strVehNo);
                controllerConfigQuery.setParameter(RMDCommonConstants.GRPNAM, strAstGrp);
            }
            if (null != strCusID) {
                controllerConfigQuery.setParameter(RMDCommonConstants.CUSTID, strCusID);
            }

            controllerConfigQuery.setFetchSize(1000);
            arlCtrlCfg = controllerConfigQuery.list();
            if (null != arlCtrlCfg && arlCtrlCfg.size() > 0) {
                for (int i = 0; i < arlCtrlCfg.size(); i++) {

                    VehicleCommServiceResultEoaVO ctrlCfgVehicleCommServiceResultVO = new VehicleCommServiceResultEoaVO();
                    Object[] objControllerConfig = (Object[]) arlCtrlCfg.get(i);
                    ctrlCfgVehicleCommServiceResultVO
                            .setStrCustomerId(RMDCommonUtility.convertObjectToString(objControllerConfig[0]));
                    ctrlCfgVehicleCommServiceResultVO
                            .setStrAssetGrpName(RMDCommonUtility.convertObjectToString(objControllerConfig[1]));
                    ctrlCfgVehicleCommServiceResultVO
                            .setStrAssetNumber(RMDCommonUtility.convertObjectToString(objControllerConfig[2]));
                    ctrlCfgVehicleCommServiceResultVO
                            .setStrControllerConfig(RMDCommonUtility.convertObjectToString(objControllerConfig[3]));

                    String key = ctrlCfgVehicleCommServiceResultVO.getStrAssetNumber()
                            + ctrlCfgVehicleCommServiceResultVO.getStrAssetGrpName()
                            + ctrlCfgVehicleCommServiceResultVO.getStrCustomerId();

                    mapCtrlConfigt.put(key, ctrlCfgVehicleCommServiceResultVO);
                }
            }

            // Start- The EOA query will be added for following fields in future
            // sprint

            // Get all assets based on asset num,asset grp name, customer id if
            // passed.

            StringBuilder sbGetAssetQuery = new StringBuilder();
            sbGetAssetQuery.append("SELECT tbo.org_id, tsp.x_veh_hdr, tsp.serial_no ");
            sbGetAssetQuery
                    .append("FROM table_site_part tsp,GETS_RMD_VEHICLE veh,GETS_RMD_VEH_HDR vehhdr,table_bus_org tbo ");
            sbGetAssetQuery.append(
                    "WHERE tsp.objid = veh.VEHICLE2SITE_PART AND veh.vehicle2veh_hdr   = vehhdr.objid AND vehhdr.veh_hdr2busorg = tbo.objid AND tsp.serial_no NOT LIKE '%BAD%'");
            if (null != strVehNo) {
                sbGetAssetQuery.append("and tsp.serial_no = :vehNum and tsp.x_veh_hdr=:grpNam");
            }
            if (null != strCusID) {
                sbGetAssetQuery.append(" and tbo.org_id=:custID");
            }

            Query getAssetsQuery = objHibernateSession.createSQLQuery(sbGetAssetQuery.toString());

            if (null != strVehNo) {
                getAssetsQuery.setParameter(RMDCommonConstants.VEH_NUM, strVehNo);
                getAssetsQuery.setParameter(RMDCommonConstants.GRPNAM, strAstGrp);
            }
            if (null != strCusID) {
                getAssetsQuery.setParameter(RMDCommonConstants.CUSTID, strCusID);
            }

            getAssetsQuery.setFetchSize(1000);
            List lstGetAssets = getAssetsQuery.list();
            if (null != lstGetAssets && lstGetAssets.size() > 0) {

                for (int i = 0; i < lstGetAssets.size(); i++) {

                    Object[] objGetAssets = (Object[]) lstGetAssets.get(i);

                    if (null != objGetAssets) {

                        String custId = RMDCommonUtility.convertObjectToString(objGetAssets[0]);
                        String assetGrpName = RMDCommonUtility.convertObjectToString(objGetAssets[1]);
                        String assetNum = RMDCommonUtility.convertObjectToString(objGetAssets[2]);

                        String key = assetNum + assetGrpName + custId;
                        mapAsset.put(key, null);
                    }

                }
            }

            /*
             * business logic to get output VO. Compare the key of each data map
             * with the key in assetMap and populate the output vo with the
             * value if matches.
             */

            if (mapAsset.size() > 0) {
                Iterator it = mapAsset.keySet().iterator();
                while (it.hasNext()) {
                    VehicleCommServiceResultEoaVO temp = null;
                    String asset = (String) it.next();
                    if (downloadmap.size() > 0 && downloadmap.containsKey(asset)) {
                        temp = createOutputVO(asset, mapAsset, downloadmap);
                        temp.setDtLastFaultReserved(downloadmap.get(asset).getDtLastFaultReserved());
                        temp.setDtLastFaultResetTime(downloadmap.get(asset).getDtLastFaultResetTime());
                        mapAsset.put(asset, temp);

                    }
                    if (mapLastProcessedDt.size() > 0 && mapLastProcessedDt.containsKey(asset)) {
                        temp = createOutputVO(asset, mapAsset, mapLastProcessedDt);
                        temp.setDtLastProcessedDate(mapLastProcessedDt.get(asset).getDtLastProcessedDate());
                        mapAsset.put(asset, temp);

                    }
                    if (mapKeepAlive.size() > 0 && mapKeepAlive.containsKey(asset)) {
                        temp = createOutputVO(asset, mapAsset, mapKeepAlive);
                        temp.setDtLastKeepAliveMsgRevd(mapKeepAlive.get(asset).getDtLastKeepAliveMsgRevd());
                        mapAsset.put(asset, temp);
                    }

                    if (mapHlthChkReq.size() > 0 && mapHlthChkReq.containsKey(asset)) {
                        temp = createOutputVO(asset, mapAsset, mapHlthChkReq);
                        temp.setDtLastHealthChkRequest(mapHlthChkReq.get(asset).getDtLastHealthChkRequest());
                        mapAsset.put(asset, temp);
                    }
                    if (mapCtrlConfigt.size() > 0 && mapCtrlConfigt.containsKey(asset)) {
                        temp = createOutputVO(asset, mapAsset, mapCtrlConfigt);
                        temp.setStrControllerConfig(mapCtrlConfigt.get(asset).getStrControllerConfig());
                        mapAsset.put(asset, temp);

                    }
                    if (null != temp) {
                        lstVehicleCommServiceResult.add(temp);
                    }

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VEHICLECOMM_SEARCH_RESULT);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objComStatusInputServiceVO.getStrLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VEHICLECOMM_SEARCH_RESULT);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objComStatusInputServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }

        VehicleCommServiceResultEoaVO[] arrVehicleCommServiceResultVO = null;
        if (lstVehicleCommServiceResult.size() > 0) {
            arrVehicleCommServiceResultVO = (VehicleCommServiceResultEoaVO[]) lstVehicleCommServiceResult
                    .toArray(new VehicleCommServiceResultEoaVO[lstVehicleCommServiceResult.size()]);
        }
        return arrVehicleCommServiceResultVO;
    }

    /**
     * @Author : IgatePatni
     * @param :
     *            asset, mapAsset,mapData
     * @return : VehicleCommServiceResultVO
     * @throws @Description:
     *             This method populate VehicleCommServiceResultVO with the
     *             retrieved data
     */
    private VehicleCommServiceResultEoaVO createOutputVO(String asset, Map mapAsset,
            Map<String, VehicleCommServiceResultEoaVO> mapData) {
        VehicleCommServiceResultEoaVO temp = null;
        if (null == mapAsset.get(asset)) {
            temp = new VehicleCommServiceResultEoaVO();
            temp.setStrAssetNumber(mapData.get(asset).getStrAssetNumber());
            temp.setStrAssetGrpName(mapData.get(asset).getStrAssetGrpName());
            temp.setStrCustomerId(mapData.get(asset).getStrCustomerId());
        } else {
            temp = (VehicleCommServiceResultEoaVO) mapAsset.get(asset);
        }

        return temp;
    }
    // End-modified by iGate Patni Fleet View Basic
}

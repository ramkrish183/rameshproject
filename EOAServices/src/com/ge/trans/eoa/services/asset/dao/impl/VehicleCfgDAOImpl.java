/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgDAOImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.dao.impl;
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

package com.ge.trans.eoa.services.asset.dao.impl;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ge.trans.eoa.cm.framework.service.CaseServiceAPI;
import com.ge.trans.eoa.services.asset.dao.intf.CMUPassthroughDAOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.VehicleCfgDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CloseCaseVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ControllerConfigVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MdscStartUpControllersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RCIMessageRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleDetailsVO;
import com.ge.trans.eoa.services.cases.dao.intf.CaseEoaDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.common.util.CMUPassthroughMessageSender;
import com.ge.trans.eoa.services.common.util.ConfigMaintenanceUtility;
import com.ge.trans.eoa.services.common.util.MessageSender;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created : March,17 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public class VehicleCfgDAOImpl extends BaseDAO implements VehicleCfgDAOIntf {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CaseEoaDAOIntf caseEoaDAOIntf;

	@Autowired
	private CaseServiceAPI caseServiceAPI;

	@Autowired
	CMUPassthroughDAOIntf objCmuPassthroughDAOIntf;

	@Autowired
	CMUPassthroughMessageSender objCmuPassthroughMessageSender;

    @Autowired @Qualifier("rciConfigMessageSender")
    private MessageSender mqSender;
	
	public CaseEoaDAOIntf getCaseEoaDAOIntf() {
		return caseEoaDAOIntf;
	}

	public void setCaseEoaDAOIntf(CaseEoaDAOIntf caseEoaDAOIntf) {
		this.caseEoaDAOIntf = caseEoaDAOIntf;
	}

	public static final RMDLogger vehCfgLogger = RMDLogger
			.getLogger(VehicleCfgDAOImpl.class);

	/**
	 * @Author :
	 * @return :List<VehicleCfgVO>
	 * @param :String customer, String rnh, String roadNumber
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching vehicle BOM
	 *               Configurations.
	 * 
	 */

	@Override
	public List<VehicleCfgVO> getVehicleBOMConfigs(String customer, String rnh,
			String roadNumber) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		VehicleCfgVO objVehicleCfgVO = null;
		List<VehicleCfgVO> vehicleCfgList = new ArrayList<VehicleCfgVO>();
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT B.CONFIG_ITEM CONFIGITEM,C.CURRENT_VERSION CVER, C.EXPECTED_VERSION EVER, ");
			caseQry.append(" C.SERIAL_NUMBER SRNO,A.PARM_NUMBER PARMNO,B.OBJID GRMBOBJID,C.OBJID VCOBJID,B.NOTIFICATION,C.VEH_CFG2VEHICLE FROM GETS_RMD_PARMDEF A, ");
			caseQry.append(" GETS_RMD_MASTER_BOM B, GETS_RMD_VEHCFG C WHERE A.OBJID(+) = B.MASTER_BOM2PARM_DEF AND ");
			caseQry.append(" B.OBJID(+) = C.VEHCFG2MASTER_BOM AND C.VEH_CFG2VEHICLE = (SELECT A.VEHICLE_OBJID  FROM GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE (A.CUST_NAME=:customerName) ");
			caseQry.append(" AND (A.VEHICLE_HDR = :vehicleHeader)");
			caseQry.append(" AND (A.VEHICLE_NO = :roadNumber)) AND B.BOM_STATUS='Y' ORDER BY  B.CONFIG_ITEM");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CUSTOMER_NAME, customer);
			caseHqry.setParameter(RMDCommonConstants.VEHICLE_HEADER, rnh);
			caseHqry.setParameter(RMDCommonConstants.ROAD_NUMBER, roadNumber);
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] vehicleCfgDetails =  obj.next();
					objVehicleCfgVO = new VehicleCfgVO();
					objVehicleCfgVO
							.setConfigItem(ESAPI
									.encoder()
									.encodeForXML(
											RMDCommonUtility
													.convertObjectToString(vehicleCfgDetails[0])));
					objVehicleCfgVO.setCurrentVersion(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[1]));
					objVehicleCfgVO.setExpectedVersion(EsapiUtil.escapeSpecialChars(ESAPI.encoder().encodeForXML(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[2]))));
					objVehicleCfgVO.setSerialNumber(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[3]));
					objVehicleCfgVO.setParmeterNo(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[4]));
					objVehicleCfgVO.setMasterBOMObjId(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[5]));
					objVehicleCfgVO.setConfigObjId(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[6]));
					objVehicleCfgVO.setNotificationFlag(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[7]));
					objVehicleCfgVO.setVehicleObjId(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[8]));
					vehicleCfgList.add(objVehicleCfgVO);
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return vehicleCfgList;
	}

	/**
	 * @Author :
	 * @return :MdscStartUpControllersVO
	 * @param : String customer, String rnh, String roadNumber
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Mdsc Start Up
	 *               Controllers Info
	 * 
	 */
	@Override
	public MdscStartUpControllersVO getMDSCStartUpControllersInfo(
			String customer, String rnh, String roadNumber)
			throws RMDDAOException {

		List<Object[]> resultList = null;
		Session session = null;
		MdscStartUpControllersVO objMdscStartUpControllersVO = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT GSUP.CAB_COMM_STRING,GSUP.CAX_COMM_STRING,GSUP.EXC_COMM_STRING,GSUP.AUX_COMM_STRING,GSUP.EFI_COMM_STRING,GSUP.SMS_ENABLED,GSUP.FLM_COMM_1_STRING, ");
			caseQry.append(" GSUP.AC6000_PORT_NO,GSUP.CCA_COMM_SOURCE,GSUP.SERVICE_PDP,GSUP.EAB_COMM_STRING,GSUP.ER_COMM_STRING FROM GETS_OMI_MDSC_SUP_DEF GSUP ");
			caseQry.append(" WHERE GSUP.OBJID = (SELECT MAX(GSUP1.OBJID) FROM   GETS_OMI_MDSC_SUP_DEF GSUP1, GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE GSUP1.MDSC_SUP_DEF2VEHICLE = A.VEHICLE_OBJID  AND  ");
			caseQry.append(" GSUP1.ACTIVE_CONFIG = 'Y' AND A.CUST_NAME = :customerName  AND A.VEHICLE_HDR = :vehicleHeader  AND A.VEHICLE_NO =:roadNumber) ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CUSTOMER_NAME, customer);
			caseHqry.setParameter(RMDCommonConstants.VEHICLE_HEADER, rnh);
			caseHqry.setParameter(RMDCommonConstants.ROAD_NUMBER, roadNumber);
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] mdscStartUpControllersDetails =  obj
							.next();
					objMdscStartUpControllersVO = new MdscStartUpControllersVO();
					objMdscStartUpControllersVO
							.setAcComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[7]));
					objMdscStartUpControllersVO
							.setAuxComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[3]));
					objMdscStartUpControllersVO
							.setCabComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[0]));
					objMdscStartUpControllersVO
							.setCaxComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[1]));
					objMdscStartUpControllersVO
							.setCcaComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[8]));
					objMdscStartUpControllersVO
							.setEabComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[10]));
					objMdscStartUpControllersVO
							.setEfiComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[4]));
					objMdscStartUpControllersVO
							.setErComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[11]));
					objMdscStartUpControllersVO
							.setExcComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[2]));
					objMdscStartUpControllersVO
							.setFlmComm(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[6]));
					objMdscStartUpControllersVO
							.setServicePdp(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[9]));
					objMdscStartUpControllersVO
							.setSmsEnabled(RMDCommonUtility
									.convertObjectToString(mdscStartUpControllersDetails[5]));

				}

			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MDSC_START_UP_CONTROLLERS_INFO);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MDSC_START_UP_CONTROLLERS_INFO);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return objMdscStartUpControllersVO;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : List<VehicleCfgVO>
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for saving BOm Vehicle
	 *               Configurations.
	 * 
	 */

	@Override
	public String saveVehicleBOMConfigs(List<VehicleCfgVO> vehicleCfgVo,
			CaseInfoServiceVO objCaseInfoServiceVO, String isCaseVehicleCfg)
			throws RMDDAOException {
		Session session = null;
		StringBuilder shipperUpdateQry = new StringBuilder();
		Query hibernateQuery = null;
		Transaction transaction = null;
		String status = RMDServiceConstants.SUCCESS;
		boolean mqMessageFlag = false;
		List<String> arlVehicleObjList = null;
		String userId = null;
		try {
			session = getHibernateSession();
			transaction = session.getTransaction();
			transaction.begin();
			shipperUpdateQry
					.append("UPDATE GETS_RMD.GETS_RMD_VEHCFG  A SET A.LAST_UPDATED_DATE = SYSDATE, A.LAST_UPDATED_BY =:userName,A.CURRENT_VERSION =:currentVersion,");
			shipperUpdateQry
					.append("A.EXPECTED_VERSION = :expectedVersion,A.SERIAL_NUMBER = :serialNumber WHERE  (A.OBJID  = :cgfObjId)");
			hibernateQuery = session
					.createSQLQuery(shipperUpdateQry.toString());
			for (VehicleCfgVO objVehicleCfgVO : vehicleCfgVo) {

				hibernateQuery.setParameter(
						RMDServiceConstants.CURRENT_VERSION,
						objVehicleCfgVO.getCurrentVersion());
				hibernateQuery.setParameter(
						RMDServiceConstants.EXPECTED_VERSION,
						objVehicleCfgVO.getExpectedVersion());
				hibernateQuery.setParameter(RMDServiceConstants.SERIAL_NO,
						objVehicleCfgVO.getSerialNumber());
				hibernateQuery.setParameter(RMDServiceConstants.CFG_OBJ_ID,
						objVehicleCfgVO.getConfigObjId());
				hibernateQuery.setParameter(RMDServiceConstants.USERNAME,
						objVehicleCfgVO.getUserName());
				hibernateQuery.executeUpdate();

				if (!mqMessageFlag
						&& null != objVehicleCfgVO.getNotificationFlag()) {
					mqMessageFlag = RMDCommonConstants.Y_LETTER
							.equalsIgnoreCase(objVehicleCfgVO
									.getNotificationFlag()) ? true : false;
				}

			}
			if (RMDCommonConstants.Y_LETTER_UPPER
					.equalsIgnoreCase(isCaseVehicleCfg)) {
				String userName = getEoaUserName(session,
						objCaseInfoServiceVO.getUserName());
				if (RMDCommonConstants.CASE_TYPE_SOFTWARE_VIGILANCE
						.equalsIgnoreCase(objCaseInfoServiceVO.getCaseType())
						&& !caseServiceAPI
								.isCaseAlreadyClosed(objCaseInfoServiceVO
										.getCaseNumber())
						&& userName.equalsIgnoreCase(objCaseInfoServiceVO
								.getStrOwner())) {
					closeCaseVehConfig(objCaseInfoServiceVO);
					status = RMDCommonConstants.SUCCESS;
				} else if (RMDCommonConstants.CASE_TYPE_SOFTWARE_VIGILANCE
						.equalsIgnoreCase(objCaseInfoServiceVO.getCaseType())
						&& caseServiceAPI
								.isCaseAlreadyClosed(objCaseInfoServiceVO
										.getCaseNumber())) {
					status = RMDCommonConstants.CASE_IS_CLOSED;
				} else if (RMDCommonConstants.CASE_TYPE_SOFTWARE_VIGILANCE
						.equalsIgnoreCase(objCaseInfoServiceVO.getCaseType())
						&& !userName.equalsIgnoreCase(objCaseInfoServiceVO
								.getStrOwner())) {
					status = RMDCommonConstants.NOT_CASE_CURRENT_OWNER;
				}
			}
			/* CMUPassThrough Changes */
			if (mqMessageFlag
					&& RMDCommonUtility.isCollectionNotEmpty(vehicleCfgVo)) {
				arlVehicleObjList = new ArrayList<String>(1);
				arlVehicleObjList.add(vehicleCfgVo.get(0).getVehicleObjId());
				userId = vehicleCfgVo.get(0).getUserName();
				objCmuPassthroughMessageSender.sendCmuMQMessages(
						arlVehicleObjList, userId,
						RMDCommonConstants.BOM_ACTION);

			}
			/* End of Changes */
			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			status = RMDServiceConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_VEHICLE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			arlVehicleObjList = null;
			releaseSession(session);
		}
		return status;
	}

	/**
	 * @Author :
	 * @return :List<VehicleCfgTemplateVO>
	 * @param : String customer, String rnh, String roadNumber
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching
	 *               VehicleCfgTemplates.
	 * 
	 */
	@Override
	public List<VehicleCfgTemplateVO> getVehicleCfgTemplates(String customer,
			String rnh, String roadNumber) throws RMDDAOException {

		List<Object[]> resultList = null;
		Session session = null;
		VehicleCfgTemplateVO objVehicleCfgTemplateVO = null;
		List<VehicleCfgTemplateVO> vehicleCfgTemplateVOList = new ArrayList<VehicleCfgTemplateVO>();
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("select objid,cfg_type,cfg_def_template,cfg_def_version,  relation, status, title, OFFBOARD_STATUS, ONBOARD_STATUS,RCI_ORDER,STATUS_OBJID from(");
			caseQry.append("select objid,cfg_type,cfg_def_template,cfg_def_version,  relation, status, title, OFFBOARD_STATUS, ONBOARD_STATUS,RCI_ORDER,STATUS_OBJID from( ");
			caseQry.append(" select objid,cfg_type,cfg_def_template,cfg_def_version, cfg_def2ctl_cfg relation, status, CFG_DEF_DESC title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID from GETS_OMI.GETS_OMI_CFG_DEF where objid  in (( select mtm2cfg_def from GETS_OMI.gets_omi_cfg_def_mtm_veh where mtm2veh in (SELECT A.VEHICLE_OBJID FROM  GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE A.CUST_NAME = :customerName AND A.VEHICLE_HDR = :vehicleHeader AND A.VEHICLE_NO = :roadNumber) and Upper(Status) = 'ACTIVE')) union ");
			caseQry.append(" select objid ,'PROXIMITY' cfg_type ,TEMPLATE_NUMBER cfg_def_template,VERSION_NUMBER cfg_def_version, prox_cfg2bus_org relation,status,' ' title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID from GETS_OMI.GETS_OMI_PROX_CFG_DEF where objid  in (( select mtm2prox_cfg_def from GETS_OMI.GETS_OMI_PROX_DEF_MTM_VEH where mtm2vehicle in (SELECT A.VEHICLE_OBJID FROM  GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE A.CUST_NAME = :customerName AND A.VEHICLE_HDR = :vehicleHeader AND A.VEHICLE_NO = :roadNumber) and Upper(Status) = 'ACTIVE')) union ");
			caseQry.append(" select objid ,'EFI' cfg_type ,EFI_cfg_template cfg_def_template,EFI_cfg_version cfg_def_version, 123 relation,decode(active_flag,'N','Not Active','Y','Active') status,' ' title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID from GETS_OMI.GETS_OMI_EFI_CFG_DEF where objid  in (( select mtm2EFI_CFG from GETS_OMI.GETS_OMI_EFI_CFG_MTM_VEH where mtm2vehicle in (SELECT A.VEHICLE_OBJID FROM  GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE A.CUST_NAME = :customerName AND A.VEHICLE_HDR = :vehicleHeader AND A.VEHICLE_NO = :roadNumber) and Upper(Status) = 'ACTIVE')) union ");
			caseQry.append(" select objid ,'FRD' cfg_type ,FLT_RANGE_DEF_template cfg_def_template,FLT_RANGE_DEF_version cfg_def_version, flt_range_def2ctl_cfg relation, status,FLT_RANGE_DEF_DESC title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID from GETS_OMI.GETS_OMI_FLT_RANGE_DEF where objid  in (( select mtm2flt_rng_def from GETS_OMI.GETS_OMI_FLTRNGDEF_MTM_VEH where mtm2vehicle in (SELECT A.VEHICLE_OBJID FROM  GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE A.CUST_NAME = :customerName AND A.VEHICLE_HDR = :vehicleHeader AND A.VEHICLE_NO = :roadNumber) and Upper(Status) = 'ACTIVE')) union ");
			caseQry.append(" select objid ,'FED' cfg_type ,FLT_XCL_TEMPLATE cfg_def_template,FLT_XCL_VERSION cfg_def_version, FLT_XCL_DEF2CTL_CFG relation, status,FLT_XCL_DESC title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID from GETS_OMI.GETS_OMI_FLT_XCL_DEF where objid  in (( select MTM2FLT_XCL_DEF from GETS_OMI.GETS_OMI_FLT_XCL_MTM_VEH where mtm2vehicle in (SELECT A.VEHICLE_OBJID FROM  GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE A.CUST_NAME = :customerName AND A.VEHICLE_HDR = :vehicleHeader AND A.VEHICLE_NO = :roadNumber) and Upper(Status) = 'ACTIVE')) union ");
			caseQry.append(" select objid ,'FFD' cfg_type ,FLT_FILTER_TEMPLATE cfg_def_template,FLT_FILTER_VERSION cfg_def_version, FLT_FILTER_DEF2CTL_CFG relation, status,FLT_FILTER_DESC title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID from GETS_OMI.GETS_OMI_FLT_FILTER_DEF where objid  in (( select MTM2FLT_FILTER_DEF from GETS_OMI.GETS_OMI_FLT_FLTR_MTM_VEH where mtm2vehicle in (SELECT A.VEHICLE_OBJID FROM  GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE A.CUST_NAME = :customerName AND A.VEHICLE_HDR = :vehicleHeader AND A.VEHICLE_NO = :roadNumber) and Upper(Status) = 'ACTIVE')) union ");
			caseQry.append("SELECT objid ,'PDD' cfg_type ,pdp_cfg_template cfg_def_template,pdp_cfg_version cfg_def_version,pdp_def2ctl_cfg relation, status,pdp_def_desc title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID FROM gets_omi.gets_omi_pdp_cfg_def WHERE objid  IN (( SELECT mtm2pdp_cfg FROM   gets_omi.gets_omi_pdp_cfg_mtm_veh WHERE  mtm2vehicle IN (SELECT a.vehicle_objid FROM   gets_rmd.gets_rmd_cust_rnh_rn_v a WHERE  a.cust_name   = :customerName AND a.vehicle_hdr = :vehicleHeader AND a.vehicle_no  = :roadNumber) AND upper(status) = 'ACTIVE')) union ");
			caseQry.append("SELECT objid ,'ERD' cfg_type ,ERD_cfg_template CFG_def_template,ERD_cfg_version cfg_def_version,ERD_CFG_def2ctl_cfg relation, status,ERD_DATA_desc title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID FROM gets_omi.gets_omi_erd_cfg_def WHERE objid  IN (( SELECT mtm2erd_cfg_def FROM   gets_omi.GETS_OMI_ERD_CFG_MTM_veh WHERE  mtm2vehicle IN (SELECT a.vehicle_objid FROM   gets_rmd.gets_rmd_cust_rnh_rn_v a WHERE  a.cust_name   = :customerName AND a.vehicle_hdr = :vehicleHeader AND a.vehicle_no  = :roadNumber) AND upper(status) = 'ACTIVE')) union ");
			caseQry.append("SELECT objid ,'ERP' cfg_type ,ERP_cfg_template cfg_def_template,ERP_cfg_version cfg_def_version,ERP_CFG_def2ctl_cfg relation, status,ERP_TYPE||'*'||ERP_DATA_DESC title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID FROM gets_omi.gets_omi_ERP_cfg_def WHERE objid  IN (( SELECT mtm2ERP_cfg_DEF FROM   gets_omi.gets_omi_ERP_cfg_mtm_veh WHERE  mtm2vehicle IN (SELECT a.vehicle_objid FROM   gets_rmd.gets_rmd_cust_rnh_rn_v a WHERE  a.cust_name   = :customerName AND a.vehicle_hdr = :vehicleHeader AND a.vehicle_no  = :roadNumber) AND upper(status) = 'ACTIVE')) union ");
			caseQry.append("SELECT sbcfg_def.objid ,'SMB' cfg_type,sbcfg_def.sb_cfg_template CFG_def_template,sbcfg_def.sb_cfg_version cfg_def_version, sbcfg_def.sb_CFG_def2ctl_cfg relation,sbcfg_def.status,sbcfg_def.sb_data_desc title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID FROM GETS_OMI.GETS_OMI_SB_CFG_DEF sbcfg_def WHERE sbcfg_def.objid  IN (( SELECT sb_mtmveh.mtm2_Sb_cfg_def FROM   GETS_OMI.GETS_OMI_SB_CFG_MTM_veh sb_mtmveh WHERE  sb_mtmveh.mtm2veh IN (SELECT a.vehicle_objid FROM   gets_rmd.gets_rmd_cust_rnh_rn_v a WHERE  a.cust_name   = :customerName AND a.vehicle_hdr = :vehicleHeader AND a.vehicle_no = :roadNumber) AND (upper(status_primary) = 'ACTIVE' AND upper(status_secondary) = 'ACTIVE'))) union ");
			caseQry.append("select objid ,'DE' cfg_type ,DIAG_EXEC_cfg_template cfg_def_template,DIAG_EXEC_cfg_version cfg_def_version, 123 relation,STATUS status,DIAG_EXEC_CFG_DESC title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID from GETS_OMI.GETS_OMI_DIAG_EXEC_CFG_DEF where objid  in (( select MTM2DIAG_EXEC_CFG_DEF from GETS_OMI.GETS_OMI_DE_CFG_MTM_VEH where mtm2vehicle in (SELECT A.VEHICLE_OBJID FROM  GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE A.CUST_NAME = :customerName AND A.VEHICLE_HDR = :vehicleHeader AND A.VEHICLE_NO = :roadNumber) and Upper(Status) = 'ACTIVE')) union ");
			caseQry.append("SELECT objid, 'GCD' cfg_type, gcd_cfg_template cfg_def_template, gcd_cfg_version cfg_def_version,gcd_cfg_def2ctl_cfg relation, status, gcd_data_desc title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID FROM gets_omi.gets_omi_gcd_cfg_def WHERE is_config_file = 'Y' AND objid  IN (( SELECT mtm2gcd_cfg_def FROM   gets_omi.GETS_OMI_GCD_CFG_MTM_VEH WHERE  mtm2vehicle IN (SELECT a.vehicle_objid FROM   gets_rmd.gets_rmd_cust_rnh_rn_v a WHERE  a.cust_name   = :customerName AND a.vehicle_hdr = :vehicleHeader AND a.vehicle_no  = :roadNumber) AND upper(status) = 'ACTIVE')) union ");
			caseQry.append("SELECT objid,'AHC' cfg_type,AHC_TEMPLATE cfg_def_template,AHC_VERSION cfg_def_version,AHC_HDR2CTRCFG relation,status,AHC_DESC title, NULL AS OFFBOARD_STATUS, NULL AS ONBOARD_STATUS,NULL AS RCI_ORDER,NULL AS STATUS_OBJID FROM GETS_OMI.GETS_OMI_AUTO_HC_CONFIG_HDR WHERE objid in((SELECT AUTO_HC_CONFIG2CONFIG_HDR FROM GETS_OMI.GETS_OMI_AUTO_HC_CONFIG WHERE AUTO_HC_CONFIG2VEHICLE IN (SELECT A.VEHICLE_OBJID FROM  GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE A.CUST_NAME = :customerName AND A.VEHICLE_HDR = :vehicleHeader AND A.VEHICLE_NO = :roadNumber) and Upper(Status) = 'ACTIVE')) ");
			caseQry.append(" ) Order by cfg_type ,cfg_def_template ,cfg_def_version ) UNION ALL ");
			//For RCI
			caseQry.append("select objid,cfg_type,cfg_def_template,cfg_def_version,  relation, status, title, OFFBOARD_STATUS, ONBOARD_STATUS,RCI_ORDER,STATUS_OBJID from(");
			caseQry.append("SELECT RCI_TABLE.objid ,CFG_TYPE,CFG_DEF_TEMPLATE,CFG_DEF_VERSION,RELATION,STATUS,CFG_DEF_DESC title,OFFBOARD_STATUS,ONBOARD_STATUS,RCI_ORDER,STATUS_OBJID FROM (");
			caseQry.append("SELECT cfgdef.objid,cfgdef.cfg_type,cfgdef.cfg_def_template,cfgdef.CFG_DEF_VERSION,cfgdef.cfg_def2ctl_cfg relation,cfgdef.status,cfgdef.cfg_def_desc,GOCVS.OFFBOARD_STATUS,GOCVS.ONBOARD_STATUS,");
			caseQry.append("STATUS ||'$'||  DECODE(OFFBOARD_STATUS,null,'EMPTY',OFFBOARD_STATUS) ||'$'|| ONBOARD_STATUS RCI_ORDER,GOCVS.OBJID STATUS_OBJID FROM GETS_OMI_CFG_DEF cfgdef,GETS_OMI.GETS_OMI_CFG_VEH_STATUS GOCVS,gets_rmd_cust_rnh_rn_v a WHERE cfgdef.RCI_FLT_CODE_ID IS NOT NULL ");
			caseQry.append("AND GOCVS.status2cfg_def =cfgdef.objid AND cfgdef.cfg_type ='RCI' AND UPPER(GOCVS.ONBOARD_STATUS) NOT IN ('OBSOLETE') AND a.vehicle_objid = GOCVS.status2vehicle AND a.cust_name   = :customerName AND a.vehicle_hdr = :vehicleHeader AND a.vehicle_no  = :roadNumber) RCI_TABLE,GETS_RMD.GETS_RMD_LOOKUP LKP WHERE RCI_TABLE.RCI_ORDER=lkp.LOOK_VALUE(+) order by lkp.SORT_ORDER)");

			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CUSTOMER_NAME, customer);
			caseHqry.setParameter(RMDCommonConstants.VEHICLE_HEADER, rnh);
			caseHqry.setParameter(RMDCommonConstants.ROAD_NUMBER, roadNumber);
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					objVehicleCfgTemplateVO = new VehicleCfgTemplateVO();
					final Object[] vehicleCfgDetails = obj.next();
					objVehicleCfgTemplateVO.setObjId(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[0]));
					objVehicleCfgTemplateVO.setConfigFile(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[1]));
					objVehicleCfgTemplateVO.setObjId(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[0]));
					objVehicleCfgTemplateVO.setStatus(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[5]));
					objVehicleCfgTemplateVO.setTemplate(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[2]));
					objVehicleCfgTemplateVO
							.setTitle(ESAPI
									.encoder()
									.encodeForXML(
											RMDCommonUtility
													.convertObjectToString(vehicleCfgDetails[6])));
					objVehicleCfgTemplateVO.setVersion(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[3]));
					objVehicleCfgTemplateVO.setOffboardStatus(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[7]));
					objVehicleCfgTemplateVO.setOnboardStatus(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[8]));
					objVehicleCfgTemplateVO.setVehStatusObjId(RMDCommonUtility
                            .convertObjectToString(vehicleCfgDetails[10]));
					vehicleCfgTemplateVOList.add(objVehicleCfgTemplateVO);

				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_CFG_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_CFG_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return vehicleCfgTemplateVOList;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : VehicleCfgTemplateVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for Deleting vehicle
	 *               Configuration Template.
	 * 
	 */

	@Override
	public String deleteVehicleCfgTemplate(
			VehicleCfgTemplateVO objVehicleCfgTemplateVO)
			throws RMDDAOException {
		String result = RMDCommonConstants.FAILURE;
		List<VehicleCfgTemplateVO> arlVehicleCfgTemplateVO = null;
		String ctrlCfgName = RMDCommonConstants.EMPTY_STRING;
		Map<String, List<String>> rnMap = null;
		List<String> arlRnNumbers = null;
		try {
			if (null != objVehicleCfgTemplateVO) {
				rnMap = new HashMap<String, List<String>>();
				arlRnNumbers = new ArrayList<String>();
				arlRnNumbers.add(objVehicleCfgTemplateVO.getAssetNumber());
				rnMap.put(objVehicleCfgTemplateVO.getAssetGrpName(),
						arlRnNumbers);
				ctrlCfgName = getControllerConfigName(
						objVehicleCfgTemplateVO.getAssetGrpName(),
						objVehicleCfgTemplateVO.getAssetNumber());
				objVehicleCfgTemplateVO.setRnMap(rnMap);
				objVehicleCfgTemplateVO.setCntrlCnfg(ctrlCfgName);
				arlVehicleCfgTemplateVO = new ArrayList<VehicleCfgTemplateVO>();
				arlVehicleCfgTemplateVO.add(objVehicleCfgTemplateVO);
				deleteVehicleCfg(arlVehicleCfgTemplateVO);
				result = RMDCommonConstants.SUCCESS;
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELETE_VEH_CFG_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			rnMap = null;
			arlRnNumbers = null;
		}
		return result;
	}

	/**
	 * @author Vamshi
	 * @param :VehicleCfgCaseClosure
	 * @return :String
	 * @throws RMDDAOException
	 * @description: This method is used for Closing Case From Vehicle
	 *               Configurtaion Screen
	 * 
	 */
	@Override
	public String closeCaseVehConfig(CaseInfoServiceVO objCaseInfoServiceVO)
			throws RMDDAOException {
		String result = RMDCommonConstants.FAILURE;
		Session objSession = null;
		StringBuilder existingRepairCodeQuery = new StringBuilder();
		StringBuilder updateQuery = new StringBuilder();
		StringBuilder codeQuery = new StringBuilder();
		StringBuilder qrystring = null;
		Query query = null;
		String userName = null;
		List<String> repairCodeList = null;
		List<String> existingRepCodeList = null;
		String repairCodeObjId = null;
		Transaction transaction = null;
		boolean isReapairCodeAddedAlready = false;
		try {
			objSession = getHibernateSession();
			transaction = objSession.beginTransaction();
			userName = getEoaUserName(objSession,
					objCaseInfoServiceVO.getUserName());
			/* Repair Code ObjId fetch Query */
			codeQuery
					.append("SELECT OBJID FROM GETS_SD_REPAIR_CODES WHERE REPAIR_CODE =:repairCode");
			query = objSession.createSQLQuery(codeQuery.toString());
			query.setParameter(RMDCommonConstants.REPAIR_CODES,
					RMDCommonConstants.REPAIR_CODE_9001);
			repairCodeList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(repairCodeList)) {
				repairCodeObjId = RMDCommonUtility
						.convertObjectToString(repairCodeList.get(0));
			}

			/* Query to fetch Existing Repair Codes */
			existingRepairCodeQuery
					.append("SELECT FINAL_REPCODE2REPCODE FROM GETS_SD_FINAL_REPCODE WHERE FINAL_REPCODE2CASE=:caseSeqId");
			query = objSession.createSQLQuery(existingRepairCodeQuery
					.toString());
			query.setParameter(RMDCommonConstants.CASESEQID,
					Long.parseLong(objCaseInfoServiceVO.getCaseObjId()));
			existingRepCodeList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(existingRepCodeList)
					&& !RMDCommonUtility.isNullOrEmpty(repairCodeObjId)) {
				if (existingRepCodeList
						.contains(new BigDecimal(repairCodeObjId))) {
					isReapairCodeAddedAlready = true;
				}
			}
			if (!isReapairCodeAddedAlready) {
				/* Insertion in Repair Code table */
				qrystring = new StringBuilder();
				qrystring
						.append(" INSERT INTO GETS_SD_FINAL_REPCODE (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY, ");
				qrystring
						.append(" CREATION_DATE,CREATED_BY,FINAL_REPCODE2CASE,FINAL_REPCODE2REPCODE) ");
				qrystring
						.append(" VALUES (GETS_SD_FINAL_REPCODE_SEQ.NEXTVAL,sysdate,:userName,sysdate,:userName,:caseSeqId,:repairCode )");
				query = objSession.createSQLQuery(qrystring.toString());
				query.setParameter(RMDCommonConstants.USERNAME, userName);
				query.setParameter(RMDCommonConstants.CASESEQID,
						Long.parseLong(objCaseInfoServiceVO.getCaseObjId()));
				query.setParameter(RMDCommonConstants.REPAIR_CODES,
						repairCodeObjId);
				query.executeUpdate();
			}

			/* Case Title Update */
			updateQuery
					.append("UPDATE TABLE_CASE SET TITLE = SUBSTR(TITLE || ', NO ACTION',1,80),S_TITLE = SUBSTR(S_TITLE || ',NO ACTION',1,80)  WHERE  ID_NUMBER=:caseId");
			query = objSession.createSQLQuery(updateQuery.toString());

			query.setParameter(RMDCommonConstants.CASE_ID,
					objCaseInfoServiceVO.getCaseNumber());

			query.executeUpdate();
			transaction.commit();
			/* Case Closure Logic */
			CloseCaseVO closeCaseVO = new CloseCaseVO();
			closeCaseVO.setStrCaseID(objCaseInfoServiceVO.getCaseNumber());
			closeCaseVO.setStrUserName(objCaseInfoServiceVO.getUserName());
			caseEoaDAOIntf.closeCase(closeCaseVO);
			result = RMDCommonConstants.SUCCESS;
		} catch (RMDDAOConnectionException ex) {
			if(null!=transaction){
			transaction.rollback();
			}
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			vehCfgLogger.error("Exception occurred:", e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.ADD_CASE_REPAIR_CODE_EXCEPTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		vehCfgLogger
				.debug("Ends closeCaseVehConfig method of CaseEoaDAOImpl");

		return result;
	}

	/**
	 * @Author :
	 * @return :List<ControllerConfigVO>
	 * @param :
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching controller configs
	 * 
	 */
	@Override
	public List<ControllerConfigVO> getControllerConfigs()
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		List<ControllerConfigVO> cnfgList = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT DISTINCT CFG.OBJID,CONTROLLER_CFG FROM GETS_RMD_CTL_CFG CFG,GETS_RMD_CTL_CFG_SRC WHERE CTL_CFG_SRC2CTL_CFG = CFG.OBJID ORDER BY CONTROLLER_CFG ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				cnfgList = new ArrayList<ControllerConfigVO>(resultList.size());
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] cnfgDetails =  obj.next();
					ControllerConfigVO objControllerConfigVO = new ControllerConfigVO();
					objControllerConfigVO.setObjId(RMDCommonUtility
							.convertObjectToString(cnfgDetails[0]));
					objControllerConfigVO.setCtrlCnfgName(RMDCommonUtility
							.convertObjectToString(cnfgDetails[1]));
					cnfgList.add(objControllerConfigVO);
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return cnfgList;
	}

	/**
	 * @Author :
	 * @return :List<VehicleCfgTemplateVO>
	 * @param : String,String
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching controller configs
	 *               templates
	 * 
	 */
	@Override
	public List<VehicleCfgTemplateVO> getControllerConfigTemplates(
			String cntrlCnfg, String cfgFileName) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		VehicleCfgTemplateVO objVehicleCfgTemplateVO = null;
		List<VehicleCfgTemplateVO> vehicleCfgTemplateVOList = new ArrayList<VehicleCfgTemplateVO>();
		try {
			session = getHibernateSession();
			StringBuilder cfgTempQuery = new StringBuilder();
			if (RMDCommonConstants.CNFG_FFD.equalsIgnoreCase(cfgFileName)) {
				cfgTempQuery
						.append("select flt.objid cfgobjid, cfg.controller_cfg ctrl_cfg,");
				cfgTempQuery.append(":cnfgFile cfgtype,");
				cfgTempQuery
						.append("flt.flt_filter_template temp,flt.flt_filter_version ver,  flt.flt_filter_desc title, ");
				cfgTempQuery
						.append("flt.status status  from gets_rmd_ctl_cfg cfg, gets_omi_flt_filter_def flt");
				cfgTempQuery
						.append("  where flt.flt_filter_def2ctl_cfg = cfg.objid  and cfg.objid = :cntrlCnfgObjId  and ");
				cfgTempQuery
						.append("upper(flt.status) = 'COMPLETE'  order by to_number(temp), to_number(ver) ");
			} else if (RMDCommonConstants.CNFG_FRD
					.equalsIgnoreCase(cfgFileName)) {
				cfgTempQuery
						.append("select flt.objid cfgobjid, cfg.controller_cfg ctrl_cfg,");
				cfgTempQuery.append(":cnfgFile cfgtype,");
				cfgTempQuery
						.append("flt.flt_range_def_template temp,flt.flt_range_def_version ver,  flt.flt_range_def_desc title, ");
				cfgTempQuery
						.append("flt.status status  from gets_rmd_ctl_cfg cfg,gets_omi_flt_range_def flt  ");
				cfgTempQuery
						.append("where flt.flt_range_def2ctl_cfg = cfg.objid  and cfg.objid = :cntrlCnfgObjId and ");
				cfgTempQuery
						.append(" upper(flt.status) = 'COMPLETE'   order by to_number(temp), to_number(ver)");
			} else if (RMDCommonConstants.CNFG_AHC
					.equalsIgnoreCase(cfgFileName)) {
				cfgTempQuery
						.append("select flt.objid cfgobjid, cfg.controller_cfg ctrl_cfg,");
				cfgTempQuery.append(":cnfgFile cfgtype,");
				cfgTempQuery
						.append("flt.AHC_TEMPLATE temp,flt.AHC_VERSION ver,  flt.AHC_DESC title, ");
				cfgTempQuery
						.append("flt.status status  from gets_rmd_ctl_cfg cfg, GETS_OMI.GETS_OMI_AUTO_HC_CONFIG_HDR flt  ");
				cfgTempQuery
						.append("where flt.AHC_HDR2CTRCFG = cfg.objid  and cfg.objid = :cntrlCnfgObjId and ");
				cfgTempQuery
						.append(" upper(flt.status) = 'COMPLETE'   order by to_number(temp), to_number(ver)");
			} else {	
				cfgTempQuery
						.append("SELECT  CFGDEF.OBJID CFGOBJID,  CFG.CONTROLLER_CFG CTRL_CFG, ");
				cfgTempQuery
						.append("CFGDEF.CFG_TYPE CFGTYPE, CFGDEF.CFG_DEF_TEMPLATE TEMP, CFGDEF.CFG_DEF_VERSION VER, ");
				cfgTempQuery
						.append("CFGDEF.CFG_DEF_DESC TITLE, CFGDEF.STATUS STATUS  FROM GETS_RMD_CTL_CFG CFG, GETS_OMI_CFG_DEF CFGDEF ");
				cfgTempQuery
						.append("WHERE CFG.OBJID=:cntrlCnfgObjId AND  UPPER(CFGDEF.CFG_TYPE) = UPPER(:cnfgFile) AND UPPER(CFGDEF.STATUS) = 'COMPLETE' AND CFG.OBJID=CFGDEF.CFG_DEF2CTL_CFG ORDER BY ");
				cfgTempQuery
						.append("TO_NUMBER(CFGDEF.CFG_DEF_TEMPLATE),TO_NUMBER(CFGDEF.CFG_DEF_VERSION) ");

			}
			Query caseHqry = session.createSQLQuery(cfgTempQuery.toString());
			if (RMDCommonConstants.CNFG_FFD.equalsIgnoreCase(cfgFileName)
					|| RMDCommonConstants.CNFG_FRD
							.equalsIgnoreCase(cfgFileName) ||RMDCommonConstants.CNFG_AHC.equalsIgnoreCase(cfgFileName) ) {
				caseHqry.setParameter(RMDCommonConstants.CNTRL_CNFG_OBJID,
						cntrlCnfg);
				caseHqry.setParameter(RMDCommonConstants.CONFIG_FILE,
						cfgFileName);
			} else {
				caseHqry.setParameter(RMDCommonConstants.CNTRL_CNFG_OBJID,
						cntrlCnfg);
				caseHqry.setParameter(RMDCommonConstants.CONFIG_FILE,
						cfgFileName);
			}
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					objVehicleCfgTemplateVO = new VehicleCfgTemplateVO();
					final Object[] vehicleCfgDetails =  obj.next();
					objVehicleCfgTemplateVO.setObjId(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[0]));
					objVehicleCfgTemplateVO.setCntrlCnfg(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[1]));
					objVehicleCfgTemplateVO.setConfigFile(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[2]));
					objVehicleCfgTemplateVO.setTemplate(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[3]));
					objVehicleCfgTemplateVO.setVersion(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[4]));
					objVehicleCfgTemplateVO
							.setTitle(ESAPI
									.encoder()
									.encodeForXML(
											RMDCommonUtility
													.convertObjectToString(vehicleCfgDetails[5])));
					objVehicleCfgTemplateVO.setStatus(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[6]));
					vehicleCfgTemplateVOList.add(objVehicleCfgTemplateVO);

				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_CFG_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_CFG_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return vehicleCfgTemplateVOList;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : List<VehicleCfgTemplateVO>
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for Deleting vehicle
	 *               Configuration Template.
	 * 
	 */
	@Override
	public String deleteVehicleCfg(
			List<VehicleCfgTemplateVO> arlVehicleCfgTemplateVO)
			throws RMDDAOException, SQLException {
		String result = RMDCommonConstants.EMPTY_STRING;
		Session session = null;
		Connection conn = null;
		CallableStatement callstmt = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
		List<VehicleDetailsVO> arlVehicleDetailsVOs = null;
		OracleConnection oracleConnection = null;
		Object[] templateDetails = null;
		List<String> arlLogMessages = null;
		List<String> arlVehicleObjId = null;
		List<String> cmuCntrlCfgList = null;
		int index = 0;
		STRUCT objStruct = null;
		StructDescriptor requestDescriptor = null;
		ArrayDescriptor requestArrayDescriptor = null;
		STRUCT[] arrStruct = null;
		ARRAY arrTempaltes = null;
		String cfgType = null;
		String userName = null;
		String ctrlCfgName = null;
		Array arr = null;
		Array vehilceObjArr = null;
		Object[] arrayObjs = null;
		List<String> arlRciXmllog=null;
		try {

			if (RMDCommonUtility.isCollectionNotEmpty(arlVehicleCfgTemplateVO)) {
				arlLogMessages = new ArrayList<String>();
				cmuCntrlCfgList = objCmuPassthroughDAOIntf.getCmuCtrlCfgList();
				arlVehicleDetailsVOs = getVehicleDetails(arlVehicleCfgTemplateVO
						.get(0).getRnMap());
				arlVehicleObjId = new ArrayList<String>(
						arlVehicleDetailsVOs.size());
				cfgType = arlVehicleCfgTemplateVO.get(0).getConfigFile();
				userName = arlVehicleCfgTemplateVO.get(0).getUserName();
				ctrlCfgName = arlVehicleCfgTemplateVO.get(0).getCntrlCnfg();
				session = getHibernateSession();
				conn = getConnection(session);
				conn.setAutoCommit(false);
				if (conn.isWrapperFor(OracleConnection.class)) {
					oracleConnection = conn.unwrap(OracleConnection.class);
				}
				requestDescriptor = StructDescriptor.createDescriptor(
						"GETS_RMD.DELETE_CFG_TEMPLATE", oracleConnection);
				arrStruct = new STRUCT[arlVehicleDetailsVOs.size()];
				for (VehicleDetailsVO objVehicleDetailsVO : arlVehicleDetailsVOs) {
					templateDetails = new Object[] {
							objVehicleDetailsVO.getVehicleObjid(),
							objVehicleDetailsVO.getVehicleNo(),
							objVehicleDetailsVO.getVehicleHdr() };
					objStruct = new STRUCT(requestDescriptor, oracleConnection,
							templateDetails);
					arrStruct[index] = objStruct;
					index++;
				}
				requestArrayDescriptor = ArrayDescriptor.createDescriptor(
						"GETS_RMD.DELETECFG_REQUEST_ARRY", oracleConnection);
				arrTempaltes = new ARRAY(requestArrayDescriptor,
						oracleConnection, arrStruct);

				for (VehicleCfgTemplateVO objCfgTemplateVO : arlVehicleCfgTemplateVO) {
					callstmt = conn
							.prepareCall("call GETS_SD_DELETE_CFG_PKG.GETS_RMD__DEL_CFG_PR(?,?,?,?,?,?,?,?,?)");
					callstmt.setString(1, objCfgTemplateVO.getConfigFile());
					callstmt.setArray(2, arrTempaltes);
					callstmt.setString(3, objCfgTemplateVO.getObjId());
					callstmt.setString(4, objCfgTemplateVO.getTemplate());
					callstmt.setString(5, objCfgTemplateVO.getVersion());
					callstmt.registerOutParameter(6, Types.NUMERIC);
					callstmt.registerOutParameter(7, Types.ARRAY,
							RMDCommonConstants.LOG_MESSAGES_ARR);
					callstmt.registerOutParameter(8, Types.ARRAY,
							RMDCommonConstants.VEH_OBJ_ARR);
					callstmt.registerOutParameter(9, Types.ARRAY,
		                    RMDCommonConstants.LOG_MESSAGES_ARR);
					vehCfgLogger
							.info("Before executing the Mass apply delete procedure "
									+ sdf.format(new Date()));
					callstmt.execute();
					vehCfgLogger
							.info("After executing the Mass apply delete procedure "
									+ sdf.format(new Date()));
					int returnCode = callstmt.getInt(6);
					if (returnCode == -1) {
						arlLogMessages.add(RMDCommonConstants.FAILURE);
					} else if (returnCode == 1) {
						arr = callstmt.getArray(7);
						if (arr != null) {
							arrayObjs = (Object[]) arr.getArray();
							for (Object msg : arrayObjs) {
								arlLogMessages.add(sdf.format(new Date())
										+ result + msg.toString());
							}
							arr = null;
						}
						vehilceObjArr = callstmt.getArray(8);
						if (vehilceObjArr != null) {
							arrayObjs = (Object[]) vehilceObjArr.getArray();
							for (Object vehicleObjId : arrayObjs) {
								arlVehicleObjId.add(RMDCommonUtility
										.convertObjectToString(vehicleObjId));
							}
							vehilceObjArr = null;
						}
						
						if (RMDCommonConstants.RCI_CFG_FILE.equalsIgnoreCase(cfgType)){
	                        arr=callstmt.getArray(9);
	                        if (arr != null) {   
	                            arrayObjs = (Object[]) arr.getArray();
	                             arlRciXmllog = new ArrayList<String>(arrayObjs.length);
	                            for (Object xmlMsg : arrayObjs) {
	                                arlRciXmllog.add(RMDCommonUtility
	                                        .convertObjectToString(xmlMsg));
	                            }
	                            arrayObjs=null;
	                            arr=null;
	                        }
	                    }
					}
					 //Sending RCI messages to EGA Agent Input MQ
					if(RMDCommonUtility.isCollectionNotEmpty(arlRciXmllog)){
						for (String rciInputMessage : arlRciXmllog) {
							sendRCIMessageToMQ(rciInputMessage,userName);
						}
					}
					conn.commit();
					
					 
					

					/* Added for CMU Pass Through Changes */
					if (RMDCommonConstants.FRD_CFG_FILE
							.equalsIgnoreCase(cfgType)
							&& RMDCommonUtility
									.isCollectionNotEmpty(arlVehicleObjId)
							&& RMDCommonUtility
									.isCollectionNotEmpty(cmuCntrlCfgList)
							&& cmuCntrlCfgList.contains(ctrlCfgName)) {
						objCmuPassthroughMessageSender.sendCmuMQMessages(
								arlVehicleObjId, userName,
								RMDCommonConstants.FRD_CFG_FILE);
					}
					/* End of Changes */
				}
			}
		} catch (Exception e) {
			vehCfgLogger.error(e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				vehCfgLogger.error("Roll back failed :" + e1);
			}
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_MASS_APPLY_DELETE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			arlVehicleObjId = null;
			cmuCntrlCfgList = null;
			try {
				if (null != callstmt) {
					callstmt.close();
				}
			} catch (Exception e) {
				vehCfgLogger.error(e);
				callstmt = null;
			}
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				vehCfgLogger.error(e);
				conn = null;
			}
			releaseSession(session);
		}
		Iterator<String> it = arlLogMessages.iterator();
		result = StringUtils.join(it, RMDCommonConstants.DOLLAR_SEPARATOR);
		return result;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : List<VehicleCfgTemplateVO>
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching vehicle Details.
	 * 
	 */
	public List<VehicleDetailsVO> getVehicleDetails(
			Map<String, List<String>> rnMap) throws RMDDAOException {

		StringBuilder assetQuery = new StringBuilder();
		Session objSession = null;
		Query hQuery = null;
		List<VehicleDetailsVO> arlVehicleDetailsVOs = null;
		VehicleDetailsVO objVehicleDetailsVO = null;
		List<Object[]> arrDetails = null;
		String tempQry = RMDCommonConstants.EMPTY_STRING;
		try {
			arrDetails = new ArrayList<Object[]>();
			objSession = getHibernateSession();
			arlVehicleDetailsVOs = new ArrayList<VehicleDetailsVO>();
			assetQuery
					.append("  SELECT GRV.OBJID,TSP.SERIAL_NO,TSP.X_VEH_HDR  FROM GETS_RMD_VEHICLE GRV, TABLE_SITE_PART TSP WHERE GRV.VEHICLE2SITE_PART = TSP.OBJID   ");

			for (Map.Entry<String, List<String>> entrySet : rnMap.entrySet()) {

				/* Logic to bring records more than 1000 Assets are selected */

				if (entrySet.getValue().size() < 1000) {
					assetQuery
							.append("  AND TSP.SERIAL_NO IN(:assetNumber) AND TSP.X_VEH_HDR =:assetGrpName ORDER BY LPAD_FUNCTION(TSP.SERIAL_NO)");

				} else {
					for (String assetNumber : entrySet.getValue()) {
						tempQry+=" SELECT '" + assetNumber+ "' FROM DUAL UNION ";
					}
					tempQry = tempQry
							.substring(0, tempQry.lastIndexOf("UNION"));
					assetQuery
							.append(" AND TSP.SERIAL_NO IN(" + tempQry + ") ");
					assetQuery
							.append(" AND TSP.X_VEH_HDR =:assetGrpName ORDER BY LPAD_FUNCTION(TSP.SERIAL_NO) ");

				}
				hQuery = objSession.createSQLQuery(assetQuery.toString());
				hQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
						entrySet.getKey());
				if (entrySet.getValue().size() < 1000) {
					hQuery.setParameterList(RMDCommonConstants.ASSET_NUMBER,
							entrySet.getValue());
				}
				arrDetails = hQuery.list();
				for (Object[] currentItem : arrDetails) {
					objVehicleDetailsVO = new VehicleDetailsVO();
					objVehicleDetailsVO.setVehicleObjid(RMDCommonUtility
							.convertObjectToString(currentItem[0]));
					objVehicleDetailsVO.setVehicleNo(RMDCommonUtility
							.convertObjectToString(currentItem[1]));
					objVehicleDetailsVO.setVehicleHdr(RMDCommonUtility
							.convertObjectToString(currentItem[2]));
					arlVehicleDetailsVOs.add(objVehicleDetailsVO);
				}
			}
		} catch (RMDDAOConnectionException ex) {

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_CFG_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_DETAILS_FILTER_BY_RNH);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
			arrDetails = null;
		}

		return arlVehicleDetailsVOs;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : String assetGrpName, String assetNumber
	 * @throws :RMDWebException
	 * @Description:This method is used for getting ControllerCofig Type of
	 *                   Particular Asset.
	 * 
	 */

	public String getControllerConfigName(String assetGrpName,
			String assetNumber) throws RMDDAOException {
		String controllerConfig = null;
		Session session = null;
		Query hibernateQuery = null;
		StringBuilder controllerConfigQuery = new StringBuilder();
		try {
			session = getHibernateSession();
			controllerConfigQuery
					.append("SELECT X_CONTROLLER_CFG FROM TABLE_SITE_PART WHERE S_SERIAL_NO=:assetNumber AND X_VEH_HDR=:assetGrpName ");
			hibernateQuery = session.createSQLQuery(controllerConfigQuery
					.toString());
			hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER,
					assetNumber);
			hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
					assetGrpName);
			List<Object> configList = hibernateQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(configList)) {
				controllerConfig = RMDCommonUtility
						.convertObjectToString(configList.get(0));
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_CONTROLLER_CONFIG);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return controllerConfig;
	}
	/**
	 * @Author :
	 * @return :List<GetSysLookupVO>
	 * @param :
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching Lookup value tooltip
	 * 
	 */
	@Override
	public List<GetSysLookupVO> getLookupValueTooltip()
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		List<GetSysLookupVO> cnfgList = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT LOOK_VALUE,LOOK_VALUE_DESC FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME= 'Configuration Item' AND LOOK_VALUE_DESC IS NOT NULL");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				cnfgList = new ArrayList<GetSysLookupVO>(resultList.size());
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] cnfgDetails =  obj.next();
					GetSysLookupVO objGetSysLookupVO = new GetSysLookupVO();
					objGetSysLookupVO.setLookValue(RMDCommonUtility
							.convertObjectToString(cnfgDetails[0]));
					objGetSysLookupVO.setLookValueDesc(ESAPI.encoder().encodeForXML(RMDCommonUtility
							.convertObjectToString(cnfgDetails[1])));
					cnfgList.add(objGetSysLookupVO);
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LOOKUP_VALUE_TOOLTIP);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LOOKUP_VALUE_TOOLTIP);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return cnfgList;
	}
	
	/**
     * @Author :
     * @return :String
     * @param : String vehStatusObjId
     * @throws :RMDDAOException
     * @Description:This method is used for getting the vehicle status for the selected template
     * 
     */

    public String validateTemplateVehStatus(String vehStatusObjId) throws RMDDAOException {
        String offBoardStatus = null;
        String onBoardStatus = null;
        Session session = null;
        Query hibernateQuery = null;
        String result =null;
        StringBuilder tempStatusQry = null;
        try {
            session = getHibernateSession();
            tempStatusQry = new StringBuilder();
            tempStatusQry
                    .append("SELECT OFFBOARD_STATUS,ONBOARD_STATUS FROM GETS_OMI.GETS_OMI_CFG_VEH_STATUS WHERE OBJID = :objId ");
            hibernateQuery = session.createSQLQuery(tempStatusQry
                    .toString());
            hibernateQuery.setParameter(RMDCommonConstants.OBJ_ID,
                    vehStatusObjId);
            Object[] vehTempStatusList = (Object[]) hibernateQuery.uniqueResult();
            if (null != vehTempStatusList) {
                offBoardStatus = RMDCommonUtility
                        .convertObjectToString(vehTempStatusList[0]);
                onBoardStatus = RMDCommonUtility
                        .convertObjectToString(vehTempStatusList[1]);
                
                if(RMDCommonConstants.FAILED_INSTALLATION.equalsIgnoreCase(offBoardStatus))
                {
                    result = RMDCommonConstants.FAILED_INSTALLATION;
                }
                else if(RMDCommonConstants.UPGRADED.equalsIgnoreCase(offBoardStatus))
                {
                    result = RMDCommonConstants.UPGRADED;
                }       
                else if(RMDCommonConstants.PENDING_DELETION.equalsIgnoreCase(offBoardStatus))
                {
                    result = RMDCommonConstants.PENDING_DELETION;
                }
                else if(RMDCommonConstants.OBSOLETE.equalsIgnoreCase(onBoardStatus))
                {
                    result = RMDCommonConstants.OBSOLETE;
                }else{
                    result =RMDCommonConstants.SUCCESS;
                }
                
                
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VALIDATE_VEHICLE_TEMPLATE_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return result;
    }
    /**
     * @Author :
     * @return :
     * @param : String input, String userName
     * @throws :RMDDAOException
     * @Description:This method is used to send RCI DELETE messages to TRANS.EOA.EGA.ADMIN.ALIAS
     * 
     */
    private void sendRCIMessageToMQ(String input, String userName)throws RMDDAOException {
    	RCIMessageRequestVO rciMessageRequestVO=null;
    	String xmlOutput=null;
    	try {
    		vehCfgLogger.info("Start - Sending RCI message to EGA Agent");
    		 rciMessageRequestVO=ConfigMaintenanceUtility.transformStringToRCIObject(input,userName);
    		 xmlOutput=ConfigMaintenanceUtility.generateRCIXMLOutput(rciMessageRequestVO);
    		 mqSender.sendMessageToRCIMQ(xmlOutput);
    		 vehCfgLogger.info("End - Sent RCI message to EGA Agent");
		} catch (Exception e) {
			vehCfgLogger.error(
	                    "Exception while passing the message to MQ "
	                            + e.getMessage(), e);
	            throw new RMDDAOException(
	                    RMDCommonConstants.PASS_TO_QUEUE_EXCEPTION, e.getMessage());
		}
    	
    }

}

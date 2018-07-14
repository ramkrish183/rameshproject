package com.ge.trans.pp.services.asset.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.jdbc.OracleTypes;

import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.cache.annotation.Cacheable;

import com.ge.rmd.conversion.util.UnitOfMeasureConversionManager;
import com.ge.rmd.conversion.vo.UOMConversion;
import com.ge.rmd.conversion.vo.UnitOfMeasure;
import com.ge.trans.pp.services.asset.dao.intf.PPAssetDAOIntf;
import com.ge.trans.pp.services.asset.service.valueobjects.MetricsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryHdrVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryParmDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHstDtlsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusHistoryVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusResponseVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPMetricsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesResponseVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesVO;
import com.ge.trans.pp.services.asset.service.valueobjects.StatesVO;
import com.ge.trans.pp.services.asset.service.valueobjects.UnitConversionDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.UnitConversionVO;
import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: May 21, 2012
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/

public class PPAssetDAOImpl extends RMDCommonDAO implements PPAssetDAOIntf {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(PPAssetDAOImpl.class);
	public static final DecimalFormat df = new DecimalFormat("#.00");
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.pp.services.asset.dao.intf.PPAssetDAOIntf#getCurrentPPStatus
	 * (com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusVO)
	 * This method is used to fetch all the assets and assetrelated data
	 * required to plot the map in pinpoint screen
	 */
	@Override
	public List getCurrentPPStatus(final PPAssetStatusVO objPPAssetStatusVO) {

		Session hibernateSession = null;
		final StringBuilder strQuery = new StringBuilder();
		final StringBuilder vehicleObjIdQry = new StringBuilder();
		final List<PPAssetStatusResponseVO> objAssetStatusResponseVOLst = new ArrayList<PPAssetStatusResponseVO>();
		PPAssetStatusResponseVO objAssetStatusResponseVO = null;
		String activNotification = null;
		ResultSet ppAssetStatusRs = null;
		String objId = null;
		Connection con = null;
		CallableStatement callableStatement = null;
		try {

			if ((null != objPPAssetStatusVO.getAssetNumber() && objPPAssetStatusVO.getAssetNumber().length() > 0)
					&& (objPPAssetStatusVO.getCustomerId() != null && objPPAssetStatusVO.getCustomerId().length() > 0)
					&& (objPPAssetStatusVO.getAssetGroupName() != null && objPPAssetStatusVO.getAssetGroupName().length() > 0)) {
				hibernateSession = getHibernateSession();
				vehicleObjIdQry.append("SELECT VEHICLE_OBJID,VEHICLE_HDR_NO FROM GETS_RMD_CUST_RNH_RN_V ");
				vehicleObjIdQry.append("WHERE ORG_ID = :customerId AND VEHICLE_HDR_CUST = :assetGrpName AND VEHICLE_NO = :assetNumber ");

				final Query objIdQuery = hibernateSession.createSQLQuery(vehicleObjIdQry.toString());

				objIdQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, objPPAssetStatusVO.getCustomerId());
				objIdQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, objPPAssetStatusVO.getAssetNumber());
				objIdQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, objPPAssetStatusVO.getAssetGroupName());

				List<Object> lsObjId = objIdQuery.list();
				if (RMDCommonUtility.isCollectionNotEmpty(lsObjId)) {
					objId = RMDCommonUtility.convertObjectToString(lsObjId.get(0));
				}
				con = hibernateSession.connection();
				try {
					callableStatement = con.prepareCall("{ ? =call gets_sd_notify_hist_pkg.Find_Active_Notify_fn(?)}");
					callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
					callableStatement.setString(2, objId);
					callableStatement.executeQuery();
					ppAssetStatusRs = (ResultSet) callableStatement.getObject(1);
					final int statusSize = ppAssetStatusRs.getFetchSize();
					if (statusSize > 0) {
						while (ppAssetStatusRs.next()) {
							activNotification = RMDCommonUtility.checkData(ppAssetStatusRs.getString(1));
						}
					}
				} catch (RMDDAOConnectionException ex) {
					String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATUS);
					throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
									new String[] {}, objPPAssetStatusVO.getStrLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
				} catch (Exception e) {
					String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATUS);
					throw new RMDDAOException(errorCode, new String[] {},
							RMDCommonUtility.getMessage(errorCode,
									new String[] {},
									objPPAssetStatusVO.getStrLanguage()), e,
							RMDCommonConstants.MAJOR_ERROR);
				} finally {

					try {
						if (null != ppAssetStatusRs) {
							ppAssetStatusRs.close();
						}

						if (null != callableStatement) {
							callableStatement.close();
						}
						if (null != con) {
							con.close();
						}
						releaseSession(hibernateSession);
					} catch (SQLException e) {
						LOG.error(
								"Unexpected Error occured in PPAssetDAOImpl getCurrentPPStatus() while closing the resultset. "
										+ e.getMessage(), e);
					}

				}
			}
			hibernateSession = getHibernateSession();
			strQuery.append("SELECT SH.GPS_LAT_DISPLAY,");
			strQuery.append(" TO_CHAR(SH.LAST_COMM_DATE+(0/24),'MM/DD/YY HH24:MI:SS'),");
			strQuery.append(" SH.GPS_LON_DISPLAY,");
			strQuery.append(" SH.FUEL_LEVEL,");
			strQuery.append(" TO_CHAR(SH.CREATION_DATE,'MM/DD/YY HH24:MI:SS'),");
			strQuery.append(" SH.LAST_KNOWN_FUEL_LEVEL,");
			strQuery.append(" TO_CHAR(SH.LAST_KNOWN_FUEL_DATE+(-4/24),'MM/DD/YY HH24:MI:SS'),");
			strQuery.append(" DECODE(SIGN(SH.GPS_VELOCITY    -1),-1,0,ROUND(SH.GPS_VELOCITY,1)),");
			strQuery.append(" ROUND(SH.NEAREST_LOCATION_MILES,1),");
			strQuery.append(" SH.SMS_FUEL_PUMP_RELAY,");
			strQuery.append(" ROUND((SH.NOT_MOVING_TIMER/60),1),");
			strQuery.append(" ROUND((LEAST(NOT_MOVING_TIMER,ENGINE_ON_TIMER)/60),1),");
			strQuery.append(" SH.STATUS_HIST2MILEPOST,");
			strQuery.append(" SH.LEAD_TRAIL,");
			strQuery.append(" SH.VEHICLE_ORIENTATION,");
			strQuery.append(" SH.SMS_ENG_CTRL_ISOLATED,");
			strQuery.append(" SH.SMS_ENG_CTRL_RUN,");
			strQuery.append(" SH.LOCATION_DESC,");
			strQuery.append(" SH.STATE_PROVINCE,");
			strQuery.append(" MP.REGION,");
			strQuery.append(" MP.SUB_REGION,");
			strQuery.append(" SH.GPS_HEADING,");
			strQuery.append(" MP.MILEPOST_ID,");
			strQuery.append(" SH.DIRECTION_FROM_LOCATION,");
			strQuery.append(" NVL(FLEET.CWC_LOCO_CLASS, MODEL.MODEL_NAME),");
			strQuery.append(" TSP.SERIAL_NO,TSP.X_VEH_HDR,TBO.ORG_ID,");
			strQuery.append(" SH.NOT_MOVE_ALARM2NOTIFY_HIST,");
			strQuery.append(" SH.FUEL_ALARM2NOTIFY_HIST,");
			strQuery.append(" SH.NOT_MOVE_ALERT2NOTIFY_HIST,");
			strQuery.append(" SH.FUEL_ALERT2NOTIFY_HIST,");
			strQuery.append(" SH.DWELL2NOTIFY_HIST,");
			strQuery.append(" TBO.NAME,");
			strQuery.append(" FLEET.FLEET_NUMBER,TSP.X_VEH_HDR_NO,");
			strQuery.append(" SNAP.GPS_LAT, ");
			strQuery.append(" SNAP.GPS_LON, ");
			strQuery.append(" TO_CHAR(SNAP.LAST_GPSLATLON_UPDATED_DATE,'MM/DD/YY HH24:MI:SS'), ");
			strQuery.append(" SNAP.ATS_LAT, ");
			strQuery.append(" SNAP.ATS_LON, ");
			strQuery.append(" TO_CHAR(SNAP.ATS_LAST_COMM_DATE,'MM/DD/YY HH24:MI:SS'), ");
			strQuery.append(" CASE WHEN SNAP.LAST_GPSLATLON_UPDATED_DATE > CAST(FROM_TZ(CAST(SH.LAST_COMM_DATE AS TIMESTAMP), 'GMT') AT TIME ZONE 'US/Eastern' AS DATE) THEN 'Y' END, ");
			strQuery.append(" SH.loco_throttle_position, ");
			strQuery.append(" SH.ATS_MESSAGE_REASON ");
			strQuery.append(" FROM GETS_TOOL_PP_STATUS_HIST SH,");
			strQuery.append(" GETS_RMD_PP_MILEPOST_DEF MP,");
			strQuery.append(" GETS_TOOL_PP_STATUS_CURR CR,");
			strQuery.append(" TABLE_SITE_PART TSP,");
			strQuery.append(" GETS_RMD_VEHICLE VEH,");
			strQuery.append(" GETS_RMD_VEH_HDR VEHHDR,");
			strQuery.append(" TABLE_BUS_ORG TBO,");
			strQuery.append(" GETS_RMD_FLEET FLEET,");
			strQuery.append(" GETS_RMD_MODEL MODEL,");
			strQuery.append(" GETS_RMD_ASSET_SNAPSHOT SNAP");
			strQuery.append(" WHERE SH.STATUS_HIST2MILEPOST  = MP.OBJID(+)");
			strQuery.append(" AND CR.STATUS_CURR2STATUS_HIST = SH.OBJID");
			strQuery.append(" AND CR.STATUS_CURR2VEHICLE     = VEH.OBJID");
			strQuery.append(" AND VEH.OBJID = SNAP.VEHICLE_OBJID (+) ");
			strQuery.append(" AND TSP.OBJID                  = VEH.VEHICLE2SITE_PART");
			strQuery.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%'");
			strQuery.append(" AND VEH.VEHICLE2VEH_HDR        = VEHHDR.OBJID");
			strQuery.append(" AND VEHHDR.VEH_HDR2BUSORG      = TBO.OBJID");
			strQuery.append(" AND MODEL.OBJID =  VEH.VEHICLE2MODEL");
			strQuery.append(" AND FLEET.OBJID =  VEH.VEHICLE2FLEET");
			// added for look back days
			if (objPPAssetStatusVO.getPpLookBackDays() > 0) {
				strQuery.append(" AND SH.LAST_COMM_DATE > (SYSDATE - :LOOKBACK)");
			}
			if (null != objPPAssetStatusVO.getCustomerId()
					&& objPPAssetStatusVO.getCustomerId().length() > 0) {
				strQuery.append("	AND TBO.ORG_ID = :customerId");
			}
			if (null != objPPAssetStatusVO.getAssetNumber()
					&& objPPAssetStatusVO.getAssetNumber().length() > 0) {
				strQuery.append(" AND TSP.SERIAL_NO = :assetNumber");
			}

			if (null != objPPAssetStatusVO.getAssetGroupName()
					&& objPPAssetStatusVO.getAssetGroupName().length() > 0) {

				strQuery.append(" AND TSP.X_VEH_HDR         = :assetGrpName");
			}

			// Adding assetId where clause to get only the product configured
			// assets
			if (null != objPPAssetStatusVO.getProducts() && !RMDCommonUtility.checkNull(objPPAssetStatusVO.getProducts())
					&& !objPPAssetStatusVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
					strQuery.append(" AND TSP.OBJID in ("
							+ getProductQuery(objPPAssetStatusVO
									.getCustomerId()) + ")");
			}
			// Adding assetId where clause to get only the product configured
			// assets

			final Query query = hibernateSession.createSQLQuery(strQuery
					.toString());
			// added for look back days
			if (objPPAssetStatusVO.getPpLookBackDays() > 0) {
				query.setParameter(RMDCommonConstants.LOOKBACK,
						objPPAssetStatusVO.getPpLookBackDays());
			}
			if (null != objPPAssetStatusVO.getCustomerId()
					&& objPPAssetStatusVO.getCustomerId().length() > 0) {
				query.setParameter(RMDCommonConstants.CUSTOMER_ID,
						objPPAssetStatusVO.getCustomerId());
			}

			if (null != objPPAssetStatusVO.getAssetNumber()
					&& objPPAssetStatusVO.getAssetNumber().length() > 0) {
				query.setParameter(RMDCommonConstants.ASSET_NUMBER,
						objPPAssetStatusVO.getAssetNumber());
			}

			if (null != objPPAssetStatusVO.getAssetGroupName()
					&& objPPAssetStatusVO.getAssetGroupName().length() > 0) {

				query.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
						objPPAssetStatusVO.getAssetGroupName());
			}

			/* Added for Product Asset Configuration */
			if (null != objPPAssetStatusVO.getProducts() && !RMDCommonUtility.checkNull(objPPAssetStatusVO.getProducts())
					&& !objPPAssetStatusVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
					query.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST, objPPAssetStatusVO.getProducts());
			}
			/* Added for Product Asset Configuration */
			final List<Object> lsPPAssetStatus = query.list();

			if (RMDCommonUtility.isCollectionNotEmpty(lsPPAssetStatus)) {
				String customerId = null;
				String s_FuelLvl = null;
				String engine = null;
				String velocity = null;
				String notMovingTimer = null;
				String engineOn = null;
				String distance = null;
				String ctrlIsolated = null;
				String locoOrientation = null;
				String s_vel_substr = null;
				String notifyLevel = null;
				int i_velocity = 0;
				String s_heading_degree = null;
				String atsMessageReason = null;

				final DateFormat dFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT);
				Map<String, String> lookUpMap = getLookUpValues(RMDServiceConstants.FUEL_ERROR_CODES);
				for (int idx = 0; idx < lsPPAssetStatus.size(); idx++) {

					s_FuelLvl = null;
					engine = null;
					velocity = null;
					notMovingTimer = null;
					engineOn = null;
					distance = null;
					ctrlIsolated = null;
					locoOrientation = null;
					s_vel_substr = null;
					notifyLevel = RMDCommonConstants.PP_MAP_NORMAL;
					i_velocity = 0;
					s_heading_degree = null;

					objAssetStatusResponseVO = new PPAssetStatusResponseVO();
					Object[] ppStatusData = (Object[]) lsPPAssetStatus.get(idx);

					if (RMDCommonUtility.convertObjectToString(ppStatusData[42]) != null
							&& "Y".equals(RMDCommonUtility.convertObjectToString(ppStatusData[42]).trim())) {
						objAssetStatusResponseVO
								.setSnapLatitude(RMDCommonUtility
										.convertObjectToString(ppStatusData[36]));
						objAssetStatusResponseVO
								.setSnapLongitude(RMDCommonUtility
										.convertObjectToString(ppStatusData[37]));
						objAssetStatusResponseVO
								.setSnapShotTime(dFormat.parse(RMDCommonUtility
										.convertObjectToString(ppStatusData[38])));
					} else if ((RMDCommonUtility.convertObjectToString(ppStatusData[0]) == null 
							|| "0".equals(RMDCommonUtility.convertObjectToString(ppStatusData[0]).trim()))
							&& RMDCommonUtility.convertObjectToString(ppStatusData[39]) != null
							&& RMDCommonUtility.convertObjectToString(ppStatusData[40]) != null) {
						objAssetStatusResponseVO
								.setHistLatitude(RMDCommonUtility
										.convertObjectToString(ppStatusData[39]));
						objAssetStatusResponseVO
								.setHistLongitude(RMDCommonUtility
										.convertObjectToString(ppStatusData[40]));
						objAssetStatusResponseVO
								.setHistTime(dFormat.parse(RMDCommonUtility
										.convertObjectToString(ppStatusData[41])));
					}

					objAssetStatusResponseVO.setAssetNumber(RMDCommonUtility
							.convertObjectToString(ppStatusData[25]));
					objAssetStatusResponseVO.setAssetGroupName(RMDCommonUtility
							.convertObjectToString(ppStatusData[26]));
					customerId = RMDCommonUtility
							.convertObjectToString(ppStatusData[27]);
					objAssetStatusResponseVO.setCustomerId(customerId);
					objAssetStatusResponseVO.setModel(RMDCommonUtility
							.convertObjectToString(ppStatusData[24]));
					objAssetStatusResponseVO.setLatitude(RMDCommonUtility
							.convertObjectToString(ppStatusData[0]));
					objAssetStatusResponseVO.setCustomerName(RMDCommonUtility
							.convertObjectToString(ppStatusData[33]));
					// rally US 17165 : Setting the Fleet Number
					objAssetStatusResponseVO.setFleetNumber(RMDCommonUtility
							.convertObjectToString(ppStatusData[34]));
					objAssetStatusResponseVO.setRoadHdrNumber(RMDCommonUtility
							.convertObjectToString(ppStatusData[35]));
					objAssetStatusResponseVO
							.setThrottlePosition(RMDCommonUtility
									.convertObjectToString(ppStatusData[43]));

					atsMessageReason = RMDCommonUtility
							.convertObjectToString(ppStatusData[44]);

					if (atsMessageReason != null
							&& atsMessageReason
									.equalsIgnoreCase(RMDCommonConstants.ATS_MESSAGE_REASON_CODE)) {
						objAssetStatusResponseVO
								.setAtsMessageReason(RMDCommonConstants.ATS_MESSAGE_REASON_YES);
					} else {
						objAssetStatusResponseVO
								.setAtsMessageReason(RMDCommonConstants.ATS_MESSAGE_REASON_NO);
					}

					s_FuelLvl = RMDCommonUtility
							.convertObjectToString(ppStatusData[3]);

					/* Added for notify level */

					if (RMDCommonUtility.convertObjectToInt(ppStatusData[28]) > 0
							|| RMDCommonUtility
									.convertObjectToInt(ppStatusData[29]) > 0) {

						notifyLevel = RMDCommonConstants.PP_MAP_ALARM;
					} else if (RMDCommonUtility
							.convertObjectToInt(ppStatusData[30]) > 0
							|| RMDCommonUtility
									.convertObjectToInt(ppStatusData[31]) > 0
							|| RMDCommonUtility
									.convertObjectToInt(ppStatusData[32]) > 0) {

						notifyLevel = RMDCommonConstants.PP_MAP_ALERT;
					}
					objAssetStatusResponseVO.setNotificationType(notifyLevel);
					/* Added for notify level */

					if (s_FuelLvl == null) {
						s_FuelLvl = RMDCommonConstants.NA;
					} else if(Integer.parseInt(s_FuelLvl) > 60000){
						String desc = lookUpMap.get(RMDCommonConstants.ERC+s_FuelLvl);
						StringBuilder codeStr = new StringBuilder();
						codeStr.append(s_FuelLvl).append("~").append(desc);
					    s_FuelLvl = codeStr.toString();
					}

					objAssetStatusResponseVO.setFuelLevel(s_FuelLvl);
					objAssetStatusResponseVO.setLongitude(RMDCommonUtility
							.convertObjectToString(ppStatusData[2]));
					engine = RMDCommonUtility.convertObjectToString(ppStatusData[9]);
					if (null != engine) {
						if (engine.equalsIgnoreCase(RMDCommonConstants.ACTIVE_STATUS)) {
							engine = RMDCommonConstants.ON;
						}/* Preparing Engine data */
						else {
							engine = RMDCommonConstants.OFF;
						}
					} else {
						engine = RMDCommonConstants.OFF;
					}
					objAssetStatusResponseVO.setEngine(engine);

					velocity = RMDCommonUtility
							.convertObjectToString(ppStatusData[7]); /*
																	 * Preparing
																	 * velocity
																	 * data
																	 */
					objAssetStatusResponseVO.setVelocity(velocity);

					notMovingTimer = RMDCommonUtility
							.convertObjectToString(ppStatusData[10]);

					objAssetStatusResponseVO.setNotMoving(notMovingTimer);

					velocity = RMDCommonUtility
							.convertObjectToString(ppStatusData[7]); /*
																	 * Preparing
																	 * heading
																	 * data
																	 */
					s_vel_substr = null;
					i_velocity = 0;
					if (null != velocity) {
						if (velocity.indexOf(RMDCommonConstants.DOT) != -1) {
							s_vel_substr = velocity.substring(0,
									velocity.indexOf(RMDCommonConstants.DOT));
						} else {
							s_vel_substr = velocity;
						}
						i_velocity = Integer.parseInt(s_vel_substr);
					}
					s_heading_degree = null;
					if (null != RMDCommonUtility
							.convertObjectToString(ppStatusData[21])) {
						if (i_velocity < 1) {
							s_heading_degree = RMDCommonConstants.EMPTY_STRING;
						} else {
							s_heading_degree = RMDCommonUtility
									.convertObjectToString(ppStatusData[21])
									+ RMDCommonConstants.DEG;
						}
					}
					objAssetStatusResponseVO.setHeading(s_heading_degree);

					engineOn = RMDCommonUtility
							.convertObjectToString(ppStatusData[11]);

					objAssetStatusResponseVO.setEngineOn(engineOn);

					distance = RMDCommonUtility
							.convertObjectToString(ppStatusData[8]); /*
																	 * Preparing
																	 * Distance
																	 * data
																	 */

					objAssetStatusResponseVO.setDistance(distance);

					objAssetStatusResponseVO.setDirection(RMDCommonUtility
							.convertObjectToString(ppStatusData[23]));

					ctrlIsolated = RMDCommonUtility
							.convertObjectToString(ppStatusData[15]); /*
																	 * Preparing
																	 * EngineControl
																	 * data
																	 */
					if (null != ctrlIsolated && ctrlIsolated.equals(RMDCommonConstants.ACTIVE_STATUS)) {
							ctrlIsolated = RMDCommonConstants.ISOLATE;
					}
					if (null != RMDCommonUtility.convertObjectToString(ppStatusData[16])
							&& RMDCommonUtility.convertObjectToString(ppStatusData[16]).equalsIgnoreCase(RMDCommonConstants.ACTIVE_STATUS)) {
						ctrlIsolated = RMDCommonConstants.RUN;
						if (null != RMDCommonUtility.convertObjectToString(ppStatusData[15])
								&& RMDCommonUtility.convertObjectToString(ppStatusData[15]).equalsIgnoreCase(RMDCommonConstants.ACTIVE_STATUS)) {
								ctrlIsolated = RMDCommonConstants.EMPTY_STRING;
						}
					}
					

					objAssetStatusResponseVO.setEngControl(ctrlIsolated);

					objAssetStatusResponseVO.setLocation(RMDCommonUtility
							.convertObjectToString(ppStatusData[17])); /*
																		 * Preparing
																		 * Locomotive
																		 * Orientation
																		 * data
																		 */

					if (customerId != null
							&& customerId
									.equalsIgnoreCase(RMDCommonConstants.ARZN)) {
						locoOrientation = RMDCommonUtility
								.convertObjectToString(ppStatusData[14]);
						if (RMDCommonConstants.SIMPLE_RULE
								.equalsIgnoreCase(locoOrientation)) {
							locoOrientation = RMDCommonConstants.SHORTHOOD_LEAD_ARZN;
						} else if (RMDCommonConstants.LONG_RULE
								.equalsIgnoreCase(locoOrientation)) {
							locoOrientation = RMDCommonConstants.LONGHOOD_LEAD_ARZN;
						} else {
							locoOrientation = RMDCommonConstants.NA_ARZN;
						}
					} else {
						locoOrientation = RMDCommonUtility
								.convertObjectToString(ppStatusData[14]);
						if (RMDCommonConstants.SIMPLE_RULE
								.equalsIgnoreCase(locoOrientation)) {
							locoOrientation = RMDCommonConstants.SHORTHOOD_LEAD;
						} else if (RMDCommonConstants.LONG_RULE
								.equalsIgnoreCase(locoOrientation)) {
							locoOrientation = RMDCommonConstants.LONGHOOD_LEAD;
						} else {
							locoOrientation = RMDCommonConstants.NA;
						}
					}
					objAssetStatusResponseVO.setLocoOrientation(locoOrientation);

					objAssetStatusResponseVO.setState(RMDCommonUtility.convertObjectToString(ppStatusData[18]));

					if (null == activNotification || activNotification.equals(RMDCommonConstants.LETTER_N)) { 
						activNotification = RMDCommonConstants.LETTER_N;															
					} else {
						if (Integer.parseInt(activNotification) > 0) {
							activNotification = RMDCommonConstants.LETTER_Y;
						} else {
							activNotification = RMDCommonConstants.LETTER_N;
						}
					}
					objAssetStatusResponseVO
							.setActvNotifications(activNotification);
					objAssetStatusResponseVO.setMilePost(RMDCommonUtility
							.convertObjectToString(ppStatusData[22]));
					objAssetStatusResponseVO.setRegion(RMDCommonUtility
							.convertObjectToString(ppStatusData[19]));
					objAssetStatusResponseVO.setSubRegion(RMDCommonUtility
							.convertObjectToString(ppStatusData[20]));

					/* Converting the below into date format */
					if (null != RMDCommonUtility
							.convertObjectToString(ppStatusData[6])) {
						objAssetStatusResponseVO
								.setDtLstFuelReadingRcvd(dFormat.parse(RMDCommonUtility
										.convertObjectToString(ppStatusData[6])));
					} else {
						objAssetStatusResponseVO.setDtLstFuelReadingRcvd(null);
					}

					if (null != RMDCommonUtility
							.convertObjectToString(ppStatusData[1])) {
						Date lastMsgTime = RMDCommonUtility
								.stringToGMTDate(
										RMDCommonUtility
												.convertObjectToString(ppStatusData[1]),
										RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT);
						objAssetStatusResponseVO.setDtMessageTime(lastMsgTime);
					} else {
						objAssetStatusResponseVO.setDtMessageTime(null);
					}

					objAssetStatusResponseVOLst.add(objAssetStatusResponseVO);
				}

				return objAssetStatusResponseVOLst;
			}

		} catch (RMDDAOConnectionException ex) {
			
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATUS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objPPAssetStatusVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATUS);
			LOG.error("Unexpected Error occured in PPAssetDAOImpl getCurrentPPStatus():"
					+ e.getMessage());
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objPPAssetStatusVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {

			try {
				if (ppAssetStatusRs != null) {
					ppAssetStatusRs.close();
				}

				if (callableStatement != null) {
					callableStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LOG.error(
						"Unexpected Error occured in PPAssetDAOImpl getCurrentPPStatus() while closing the resultset. "
								+ e.getMessage(), e);
			}
			releaseSession(hibernateSession);
		}

		return objAssetStatusResponseVOLst;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.pp.services.asset.dao.intf.PPAssetDAOIntf#getPPStatusHistory
	 * (
	 * com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusHistoryVO
	 * ) This method is used to fetch the history related information of an
	 * individual asset
	 */
	public List<PPAssetHistoryDetailsVO> getPPStatusHistory(
			final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO) {
		Session objSession = null;
		Session objSession2 = null;
		Session objSession3 = null;
		Session objSession4 = null;
		Session objSession5 = null;
		List<Object[]> vehObjIdLst = null;
		List<Object[]> configObjIdLst = null;
		int vehObjId = 0;
		String contConfig = RMDCommonConstants.EMPTY_STRING;
		int contSourceId = 0;
		int countRecord = 0;
		String groupName = objPPAssetStatusHistoryVO.getAssetGroupName();

		List<Map<String, Object>> ppAssetHistoryRs1 = null;
		List<Object[]> ppAssetHistoryRs2 = null;
		String headers = "LAST_COMM_DATE,GPS_LAT_DISPLAY,GPS_LON_DISPLAY,GPS_VELOCITY,GPS_HEADING,DIRECTION_FROM_LOCATION,NEAREST_LOCATION_MILES,LOCATION_DESC,STATE_PROVINCE,MILEPOST_ID,REGION,SUB_REGION,FUEL_LEVEL,FUEL_ADDED,SMS_FUEL_PUMP_RELAY,NOT_MOVING_TIMER,ENGINE_ON_TIMER,SMS_ENG_CTRL_ISOLATED,SMS_ENG_CTRL_RUN,LEAD_TRAIL,VEHICLE_ORIENTATION,NOT_MOVE_ALARM2NOTIFY_HIST,FUEL_ALARM2NOTIFY_HIST,NOT_MOVE_ALERT2NOTIFY_HIST,FUEL_ALERT2NOTIFY_HIST,DWELL2NOTIFY_HIST,LOCO_THROTTLE_POSITION,ATS_MESSAGE_REASON";
		String[] headersArr = null;
		String headers1 = "D/T Last Message,Lat/Long,Velocity,Heading,Distance,Location,State,Milepost,Region,Sub Region,Fuel Level,Fuel Added,Engine (On/Off),Not Moving Time,Engine On Not Moving,Engine Control,Engine Direction,Throttle Notch,Locomotive Moving After not Moving for X minutes";
		String headers2 = "D/T Last Message,Lat/Long,Velocity,Heading,Distance,Location,State,Milepost,Region,Sub Region,Fuel Level,Fuel Added,Engine (On/Off),Not Moving Time,Engine On Not Moving,Engine Control,Locomotive Orientation,Throttle Notch";
		String[] headersArr1 = null;
		headersArr = headers.split(RMDServiceConstants.COMMA);
		if (objPPAssetStatusHistoryVO.getFlagMPData() != null
				&& objPPAssetStatusHistoryVO.getFlagMPData().equalsIgnoreCase(
						RMDCommonConstants.LETTER_Y)) {
			headersArr1 = headers1.split(RMDServiceConstants.COMMA);
		} else {
			headersArr1 = headers2.split(RMDServiceConstants.COMMA);
		}
		PPAssetHistoryVO objPPAssetHistoryVO = null;
		List<PPAssetHistoryVO> ppAssetHistoryVOList = new ArrayList<PPAssetHistoryVO>();
		PPAssetHistoryDetailsVO objPPAssetHistoryDetailsVODetails = new PPAssetHistoryDetailsVO();
		List<PPAssetHistoryDetailsVO> ppAssetHistoryDtlsVOLst = new ArrayList<PPAssetHistoryDetailsVO>();
		PPAssetHistoryHdrVO objPPAssetHistoryHdrVO = new PPAssetHistoryHdrVO();
		PPAssetHistoryHdrVO objPPAssetHistoryHdrDetails;
		objSession = getHibernateSession();
		Connection connection =	objSession.connection();
		try {
			objSession2 = getHibernateSession();
			// Get vehicle obj Id
			StringBuilder vehicleObjIdQry = new StringBuilder();
			vehicleObjIdQry
					.append("SELECT VEHICLE_OBJID,BUS_ORG_OBJID FROM GETS_RMD_CUST_RNH_RN_V ");
			vehicleObjIdQry
					.append("WHERE ORG_ID = :custId AND VEHICLE_NO = :assetNum ");
			if (null != groupName
					&& !RMDCommonConstants.EMPTY_STRING.equals(groupName)) {
				vehicleObjIdQry.append("AND VEHICLE_HDR_CUST = :groupName ");
			}
			Query getVehObjIdHQry = objSession.createSQLQuery(vehicleObjIdQry
					.toString());
			getVehObjIdHQry.setParameter(RMDCommonConstants.CUST_ID,
					objPPAssetStatusHistoryVO.getCustomerId());
			getVehObjIdHQry.setParameter(RMDCommonConstants.ASSET_NUM,
					objPPAssetStatusHistoryVO.getAssetNumber());
			if (null != groupName
					&& !RMDCommonConstants.EMPTY_STRING.equals(groupName)) {
				getVehObjIdHQry.setParameter(RMDCommonConstants.GROUP_NAME,
						objPPAssetStatusHistoryVO.getAssetGroupName());
			}
			
			vehObjIdLst = (ArrayList) getVehObjIdHQry.list();
			if (null != vehObjIdLst && !vehObjIdLst.isEmpty()) {

				for (final Iterator<Object[]> obj = vehObjIdLst.iterator(); obj
						.hasNext();) {
					final Object[] units =  obj.next();

					vehObjId = RMDCommonUtility.convertObjectToInt(units[0]);
				}
			}

			StringBuilder configObjIdQry = new StringBuilder();

			configObjIdQry
					.append("select model_name, objid from gets_rmd_model where objid =(select vehicle2model from gets_rmd_vehicle where objid=:model)");
			Query getConfigObjIdHQry = objSession.createSQLQuery(configObjIdQry
					.toString());

			getConfigObjIdHQry.setParameter("model", vehObjId);
			configObjIdLst = (ArrayList) getConfigObjIdHQry.list();
			if (null != configObjIdLst && !configObjIdLst.isEmpty()) {
				for (final Iterator<Object[]> obj = configObjIdLst.iterator(); obj
						.hasNext();) {
					final Object[] units =  obj.next();
					contConfig = RMDCommonUtility
							.convertObjectToString(units[0]);
					contSourceId = RMDCommonUtility
							.convertObjectToInt(units[1]);
				}
			}
			LOG.info("model---       ");
			List<PPAssetHistoryHdrVO> ppAssetHistoryHdrVOList = new ArrayList<PPAssetHistoryHdrVO>();
			for (String strHeader : headersArr) {
				objPPAssetHistoryHdrDetails = new PPAssetHistoryHdrVO();
				objPPAssetHistoryHdrDetails.setDisplayName(strHeader);
				objPPAssetHistoryHdrDetails.setColumnAliasName(strHeader);
				objPPAssetHistoryHdrDetails.setExportColumnName(strHeader);
				ppAssetHistoryHdrVOList.add(objPPAssetHistoryHdrDetails);// To
																			// Take
																			// from
																			// Procedure
			}

			List<PPAssetHistoryHdrVO> ppAssetHistoryHdrVOList1 = new ArrayList<PPAssetHistoryHdrVO>();
			for (String strHeader : headersArr1) {
				objPPAssetHistoryHdrDetails = new PPAssetHistoryHdrVO();
				objPPAssetHistoryHdrDetails.setDisplayName(strHeader);
				objPPAssetHistoryHdrDetails.setColumnAliasName(strHeader);
				objPPAssetHistoryHdrDetails.setExportColumnName(strHeader);
				ppAssetHistoryHdrVOList1.add(objPPAssetHistoryHdrDetails);// To
																			// Show
																			// in
																			// UI
			}

			LOG.info("look up--------        ");
			StringBuilder lookupbuffer = new StringBuilder();
			lookupbuffer
					.append("SELECT look_value FROM GETS_RMD.GETS_RMD_LOOKUP WHERE list_name=:Modelname");
			Query lookupQuery = objSession.createSQLQuery(lookupbuffer
					.toString());
			lookupQuery.setParameter("Modelname", contConfig);
			Object val = lookupQuery.uniqueResult();
			String model = null;
			if (RMDCommonUtility.convertObjectToString(val) != null
					&& !RMDCommonUtility.convertObjectToString(val).trim()
							.equals(RMDCommonConstants.EMPTY_STRING)) {
				model = RMDCommonUtility.convertObjectToString(val);
			}
			LOG.info("query");
			StringBuilder configObjIdQry2 = new StringBuilder();
			StringBuilder ppHistoryCountQry = new StringBuilder();
			ppHistoryCountQry.append("select count(*)	");
			ppHistoryCountQry.append(" FROM GETS_TOOL_PP_STATUS_HIST SH");
			ppHistoryCountQry
			.append(" WHERE SH.STATUS_HIST2VEHICLE =:vehobjid ");
			if (null != objPPAssetStatusHistoryVO.getFromDate()
					&& !RMDCommonConstants.EMPTY_STRING.equals(objPPAssetStatusHistoryVO.getFromDate().trim())) {

				ppHistoryCountQry
						.append("AND SH.LAST_COMM_DATE BETWEEN TO_DATE(:P_FROM_DATE,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(:P_TO_DATE,'mm/dd/yyyy hh24:mi:ss') ");
			} else if(!RMDCommonConstants.ALL.equalsIgnoreCase(objPPAssetStatusHistoryVO.getNoOfDays())) {
				ppHistoryCountQry
						.append("AND SH.LAST_COMM_DATE BETWEEN (SYSDATE - :P_NO_OF_DAYS) AND (SYSDATE + 1) ");
			} else {
				ppHistoryCountQry
				.append("AND SH.LAST_COMM_DATE < (SYSDATE + 1) ");
			}
				
			
			objSession5 = getHibernateSession();
			Query getCountQuery = objSession5
					.createSQLQuery(ppHistoryCountQry.toString());
			getCountQuery.setParameter("vehobjid", vehObjId);
			if (null != objPPAssetStatusHistoryVO.getFromDate()
					&& !RMDCommonConstants.EMPTY_STRING
							.equals(objPPAssetStatusHistoryVO.getFromDate()
									.trim())) {
				getCountQuery.setParameter("P_FROM_DATE",
						objPPAssetStatusHistoryVO.getFromDate());
				getCountQuery.setParameter("P_TO_DATE",
						objPPAssetStatusHistoryVO.getToDate());
			} else if (objPPAssetStatusHistoryVO.getNoOfDays() != null && !RMDCommonConstants.ALL
					.equalsIgnoreCase(objPPAssetStatusHistoryVO.getNoOfDays())) {
				getCountQuery.setParameter("P_NO_OF_DAYS", Integer
						.parseInt(objPPAssetStatusHistoryVO.getNoOfDays()));
			}
			BigDecimal count1 = (BigDecimal) getCountQuery.uniqueResult();
			countRecord = count1.intValue();
			configObjIdQry2.append("select 	");
			configObjIdQry2
					.append("TO_CHAR(sh.LAST_COMM_DATE,'MM/DD/YY HH24:MI:SS') as LAST_COMM_DATE ,	");
			configObjIdQry2.append("SH.GPS_LAT_DISPLAY as GPS_LAT_DISPLAY,	");
			configObjIdQry2.append("SH.GPS_LON_DISPLAY as GPS_LON_DISPLAY,	");
			configObjIdQry2
					.append("ROUND(SH.GPS_VELOCITY,1) as GPS_VELOCITY,	");
			configObjIdQry2.append("SH.GPS_HEADING as GPS_HEADING,	");
			configObjIdQry2
					.append("SH.DIRECTION_FROM_LOCATION as DIRECTION_FROM_LOCATION ,	");
			configObjIdQry2
					.append("ROUND(SH.NEAREST_LOCATION_MILES,1) as NEAREST_LOCATION_MILES,	");
			configObjIdQry2.append("SH.LOCATION_DESC as LOCATION_DESC,	");
			configObjIdQry2.append("SH.STATE_PROVINCE as STATE_PROVINCE,	");
			configObjIdQry2.append("MP.MILEPOST_ID as MILEPOST_ID,	");
			configObjIdQry2.append("MP.REGION as REGION,	");
			configObjIdQry2.append("MP.SUB_REGION as SUB_REGION,	");
			configObjIdQry2.append("SH.FUEL_LEVEL as FUEL_LEVEL,	");
			configObjIdQry2.append("SH.FUEL_ADDED as FUEL_ADDED,	");
			configObjIdQry2
					.append("SH.SMS_FUEL_PUMP_RELAY as SMS_FUEL_PUMP_RELAY,	");
			configObjIdQry2
					.append("ROUND((SH.NOT_MOVING_TIMER                    /60),1) as NOT_MOVING_TIMER,	");
			configObjIdQry2
					.append("ROUND((LEAST(NOT_MOVING_TIMER,ENGINE_ON_TIMER)/60),1) as ENGINE_ON_TIMER,	");
			configObjIdQry2
					.append("SH.SMS_ENG_CTRL_ISOLATED as SMS_ENG_CTRL_ISOLATED,	");
			configObjIdQry2.append("SH.SMS_ENG_CTRL_RUN as SMS_ENG_CTRL_RUN,	");
			configObjIdQry2.append("SH.LEAD_TRAIL as LEAD_TRAIL,	");
			configObjIdQry2
					.append("SH.VEHICLE_ORIENTATION as VEHICLE_ORIENTATION,	");
			configObjIdQry2
					.append("SH.NOT_MOVE_ALARM2NOTIFY_HIST as NOT_MOVE_ALARM2NOTIFY_HIST,	");
			configObjIdQry2
					.append("SH.FUEL_ALARM2NOTIFY_HIST as FUEL_ALARM2NOTIFY_HIST,	");
			configObjIdQry2
					.append("SH.NOT_MOVE_ALERT2NOTIFY_HIST as NOT_MOVE_ALERT2NOTIFY_HIST,	");
			configObjIdQry2
					.append("SH.FUEL_ALERT2NOTIFY_HIST as FUEL_ALERT2NOTIFY_HIST,	");
			configObjIdQry2
					.append("SH.DWELL2NOTIFY_HIST as DWELL2NOTIFY_HIST,	");
			configObjIdQry2
					.append("SH.LOCO_THROTTLE_POSITION as LOCO_THROTTLE_POSITION	");
			if (objPPAssetStatusHistoryVO.getFlagMPData() != null
					&& objPPAssetStatusHistoryVO.getFlagMPData().equalsIgnoreCase(
							RMDCommonConstants.LETTER_Y)) {
				configObjIdQry2
						.append(",SH.ATS_MESSAGE_REASON as ATS_MESSAGE_REASON,	");
				configObjIdQry2.append("a.*	");
			}
			configObjIdQry2.append(" FROM GETS_TOOL_PP_STATUS_HIST SH,");
			configObjIdQry2.append("GETS_RMD_PP_MILEPOST_DEF MP");
			if (objPPAssetStatusHistoryVO.getFlagMPData() != null
					&& objPPAssetStatusHistoryVO.getFlagMPData().equalsIgnoreCase(
							RMDCommonConstants.LETTER_Y)) {
				configObjIdQry2
				.append(",GETS_RMD"+RMDCommonConstants.DOT+model+" a");
				configObjIdQry2
						.append(" WHERE a.STATUS_HIST2VEHICLE =:vehobjid ");
			}else{
			configObjIdQry2
			.append(" WHERE SH.STATUS_HIST2VEHICLE =:vehobjid ");
			}
			configObjIdQry2.append("and SH.STATUS_HIST2MILEPOST= MP.OBJID(+) ");
			if (objPPAssetStatusHistoryVO.getFlagMPData() != null
					&& objPPAssetStatusHistoryVO.getFlagMPData().equalsIgnoreCase(
							RMDCommonConstants.LETTER_Y)) {

				if (null != objPPAssetStatusHistoryVO.getFromDate()
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objPPAssetStatusHistoryVO.getFromDate()
										.trim())) {

					configObjIdQry2
							.append("AND a.LAST_COMM_DT BETWEEN TO_DATE(:P_FROM_DATE,'mm/dd/yyyy hh24:mi:ss') AND  ");
					configObjIdQry2
							.append("TO_DATE(:P_TO_DATE,'mm/dd/yyyy hh24:mi:ss')");
				} else if(!RMDCommonConstants.ALL
						.equalsIgnoreCase(objPPAssetStatusHistoryVO.getNoOfDays()))  {
					configObjIdQry2
							.append("AND a.LAST_COMM_DT BETWEEN (SYSDATE - :P_NO_OF_DAYS) AND (SYSDATE + 1)");
				} else {
					configObjIdQry2
					.append("AND a.LAST_COMM_DATE < (SYSDATE + 1) ");
				}

				configObjIdQry2.append("and a.PPHIST_OBJID(+)=SH.OBJID	");
				configObjIdQry2
						.append("and a.STATUS_HIST2VEHICLE=sh.STATUS_HIST2VEHICLE");
			} else {

				if (null != objPPAssetStatusHistoryVO.getFromDate()
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objPPAssetStatusHistoryVO.getFromDate()
										.trim())) {

					configObjIdQry2
							.append("AND SH.LAST_COMM_DATE BETWEEN TO_DATE(:P_FROM_DATE,'mm/dd/yyyy hh24:mi:ss') AND  ");
					configObjIdQry2
							.append("TO_DATE(:P_TO_DATE,'mm/dd/yyyy hh24:mi:ss')");
				} else if(!RMDCommonConstants.ALL
						.equalsIgnoreCase(objPPAssetStatusHistoryVO.getNoOfDays())) {
					configObjIdQry2
							.append("AND SH.LAST_COMM_DATE BETWEEN (SYSDATE - :P_NO_OF_DAYS) AND (SYSDATE + 1) ");
				} else {
					configObjIdQry2
					.append("AND SH.LAST_COMM_DATE < (SYSDATE + 1) ");
				}
				
			}
			configObjIdQry2
			.append(" ORDER BY SH.LAST_COMM_DATE DESC");
			objSession3 = getHibernateSession();
			Query getConfigObjIdHQry1 = objSession3
					.createSQLQuery(configObjIdQry2.toString());
			
			getConfigObjIdHQry1.setParameter("vehobjid", vehObjId);
			if (null != objPPAssetStatusHistoryVO.getFromDate()
					&& !RMDCommonConstants.EMPTY_STRING
							.equals(objPPAssetStatusHistoryVO.getFromDate()
									.trim())) {

				getConfigObjIdHQry1.setParameter("P_FROM_DATE",
						objPPAssetStatusHistoryVO.getFromDate());

				getConfigObjIdHQry1.setParameter("P_TO_DATE",
						objPPAssetStatusHistoryVO.getToDate());
			} else if (objPPAssetStatusHistoryVO.getNoOfDays() != null && !RMDCommonConstants.ALL
					.equalsIgnoreCase(objPPAssetStatusHistoryVO.getNoOfDays())) {
				getConfigObjIdHQry1.setParameter("P_NO_OF_DAYS", Integer
						.parseInt(objPPAssetStatusHistoryVO.getNoOfDays()));
			}
			getConfigObjIdHQry1
					.setResultTransformer(new AliasToEntityMapResultTransformer());
			
			if (Integer.parseInt(objPPAssetStatusHistoryVO.getRecordsPerPage()) >= 0) {
				getConfigObjIdHQry1.setFirstResult(Integer.parseInt(objPPAssetStatusHistoryVO.getStartRow()));
				getConfigObjIdHQry1.setMaxResults(Integer.parseInt(objPPAssetStatusHistoryVO.getRecordsPerPage()));}
			List<Map<String, Object>> configObjIdLst1 = getConfigObjIdHQry1.list();
			Date date1 = new Date();
			// display time and date using toString()
			String str1 = String.format("Current Date/Time : %tc", date1);

			
			LOG.info("After main before Mp col query------------" + str1);
			objSession4 = getHibernateSession();
			if (objPPAssetStatusHistoryVO.getFlagMPData() != null
					&& objPPAssetStatusHistoryVO.getFlagMPData().equalsIgnoreCase(
							RMDCommonConstants.LETTER_Y)) {
			StringBuilder configObjIdQry1 = new StringBuilder();
			configObjIdQry1.append("SELECT ");
			configObjIdQry1.append("LOAD.PARM_LOAD_COLUMN,");
			configObjIdQry1.append(" info.header_html,");
			configObjIdQry1.append(" info.sort_order,");
			configObjIdQry1.append(" info.format,");
			configObjIdQry1.append(" info.COLUMN_NAME");
			configObjIdQry1.append(" FROM gets_rmd_parmdef_load_info load,	");
			configObjIdQry1.append("gets_rmd_parmdef def,");
			configObjIdQry1.append("GETS_TOOL_CE_PARM_INFO_NEW info,");
			configObjIdQry1.append(" GETS_TOOL_CE_PARM_INFO_EXTAPP ext");
			configObjIdQry1.append(" WHERE load.PARM_OBJID =def.OBJID");
			configObjIdQry1.append(" AND info.parm_info2parmdef =def.objid");
			configObjIdQry1.append(" AND INFO.OBJID=EXT.EXTAPP2CE_PARM_INFO	");
			configObjIdQry1.append("AND INFO.CE_PARAM2MODEL = :contSourceId ");
			configObjIdQry1.append("AND LOAD.SOURCE_TYPE='ATS' ");
			configObjIdQry1.append("AND EXT.EXTAPP2BUS_ORG =268435482	");
			configObjIdQry1
					.append("AND info.ATS_COL_AVAIL ='Y' order by info.sort_order");
			Query getConfigObjIdHQry2 = objSession4
					.createSQLQuery(configObjIdQry1.toString());
			getConfigObjIdHQry2.setParameter("contSourceId", contSourceId);
			List<Object[]> configObjIdLst2 = (List<Object[]>) getConfigObjIdHQry2.list();
			ppAssetHistoryRs2 =  configObjIdLst2;
			}
			ppAssetHistoryRs1 =  configObjIdLst1;
			
			Date date2 = new Date();

			// display time and date using toString()
			String str2 = String.format("Current Date/Time : %tc", date2);

			LOG.info("After MP header query------------" + str2);
			
			// New unit conversion implementation
			Date date3 = new Date();
			LOG.info("Unit conversion process starts------------" + date3);
			UnitOfMeasureConversionManager unitOfMeasureConversionManager = new UnitOfMeasureConversionManager();
			unitOfMeasureConversionManager.initialize(connection);
			UOMConversion uomConversion = new UOMConversion();
			
			 Map<String,Long> unitConversionData = getUnitConversionData();
			 Set<String> columnList = new HashSet<String>();
			 columnList.addAll(unitConversionData.keySet());
			List<String> headersList = new ArrayList<String>(Arrays.asList(headers.split(",")));
			for(String s:headersList){
			columnList.add(s);
			}
			String strVelocity = null;
			String strDirection = null;
			
			if (null != ppAssetHistoryRs2 && !ppAssetHistoryRs2.isEmpty()) {
				
				for (final Iterator<Object[]> obj = ppAssetHistoryRs2
						.iterator(); obj.hasNext();) {
					final Object[] series =  obj.next();

					objPPAssetHistoryHdrDetails = new PPAssetHistoryHdrVO();
					objPPAssetHistoryHdrDetails.setColumnAliasName(String
							.valueOf(series[0]));
					objPPAssetHistoryHdrDetails.setDisplayName(String
							.valueOf(series[1]));
					objPPAssetHistoryHdrDetails.setExportColumnName(String
							.valueOf(series[4]));
					ppAssetHistoryHdrVOList.add(objPPAssetHistoryHdrDetails);
					ppAssetHistoryHdrVOList1.add(objPPAssetHistoryHdrDetails);
				}
			}
			Map<String, String> lookUpMap = getLookUpValues(RMDServiceConstants.FUEL_ERROR_CODES);
			if (null != ppAssetHistoryRs1 && !ppAssetHistoryRs1.isEmpty()) {
				for (final Iterator<Map<String, Object>> objct = ppAssetHistoryRs1
						.iterator(); objct.hasNext();) {
					final Map<String, Object> series =  objct
							.next();
					objPPAssetHistoryVO = new PPAssetHistoryVO();
					List<PPAssetHistoryParmDetailsVO> lstAssetHistoryParmDetailsVOs = new ArrayList<PPAssetHistoryParmDetailsVO>();
					String engineIsolated = null;
					String engineRun = null;
					String strLatitude = null;
					String strLongitude = null;
					int notifyLevel1 = 0;
					int notifyLevel2 = 0;
					int notifyLevel3 = 0;
					int notifyLevel4 = 0;
					int notifyLevel5 = 0;


					for (int i = 0; i < ppAssetHistoryHdrVOList.size(); i++) {
						objPPAssetHistoryHdrVO = ppAssetHistoryHdrVOList.get(i);
						PPAssetHistoryParmDetailsVO obj = new PPAssetHistoryParmDetailsVO();
						String columnName = objPPAssetHistoryHdrVO
								.getColumnAliasName();
						String strValue = null;

						if (RMDCommonConstants.SMS_ENG_CTRL_ISOLATED.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName()
								)) {
							engineIsolated = RMDCommonUtility
									.convertObjectToString(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));
						} else if (RMDCommonConstants.SMS_ENG_CTRL_RUN.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								&& engineIsolated != null) {
							engineRun = RMDCommonUtility
									.convertObjectToString(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));
							if (RMDCommonConstants.ONE_STRING
									.equals(engineIsolated)
									&& RMDCommonConstants.ONE_STRING
											.equals(engineRun)) {
								strValue = RMDCommonConstants.EMPTY_STRING;
							} else {
								if (RMDCommonConstants.ONE_STRING
										.equals(engineIsolated)) {
									strValue = RMDCommonConstants.ISOLATE;
								} else if (RMDCommonConstants.ONE_STRING
										.equals(engineRun)) {
									strValue = RMDCommonConstants.RUN;
								}

							}
							obj.setColumnName(columnName);
							obj.setColumnValue(strValue);
							lstAssetHistoryParmDetailsVOs.add(obj);
							engineIsolated = null;
							engineRun = null;
						} else if (RMDCommonConstants.NOT_MOVE_ALARM2NOTIFY_HIST.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
							notifyLevel1 = RMDCommonUtility
									.convertObjectToInt(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));
						} else if (RMDCommonConstants.FUEL_ALARM2NOTIFY_HIST.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
							notifyLevel2 = RMDCommonUtility
									.convertObjectToInt(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));
						} else if (RMDCommonConstants.NOT_MOVE_ALERT2NOTIFY_HIST.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
							notifyLevel3 = RMDCommonUtility
									.convertObjectToInt(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));
						} else if (RMDCommonConstants.FUEL_ALERT2NOTIFY_HIST.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
							notifyLevel4 = RMDCommonUtility
									.convertObjectToInt(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));
						} else if (RMDCommonConstants.DWELL2NOTIFY_HIST.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
							notifyLevel5 = RMDCommonUtility
									.convertObjectToInt(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));

							if (notifyLevel1 > 0 || notifyLevel2 > 0) {
								strValue = RMDCommonConstants.PP_MAP_ALARM;
								obj.setColumnName(columnName);
								obj.setColumnValue(strValue);
								lstAssetHistoryParmDetailsVOs.add(obj);
							} else if (notifyLevel3 > 0 || notifyLevel4 > 0
									|| notifyLevel5 > 0) {
								strValue = RMDCommonConstants.PP_MAP_ALERT;
								obj.setColumnName(columnName);
								obj.setColumnValue(strValue);
								lstAssetHistoryParmDetailsVOs.add(obj);
							} else {
								strValue = RMDCommonConstants.PP_MAP_NORMAL;
								obj.setColumnName(columnName);
								obj.setColumnValue(strValue);
								lstAssetHistoryParmDetailsVOs.add(obj);
							}
						}

						else if (RMDCommonConstants.GPS_LAT_DISPLAY.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
							strLatitude = RMDCommonUtility
									.convertObjectToString(convertLatLong(String.valueOf(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()))));
						} else if (RMDCommonConstants.GPS_LON_DISPLAY.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								&& strLatitude != null) {
							strLongitude = RMDCommonUtility
									.convertObjectToString(convertLatLong(String.valueOf(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()))));
							strValue = strLatitude + "/" + strLongitude;
							obj.setColumnName(columnName);
							obj.setColumnValue(strValue);
							lstAssetHistoryParmDetailsVOs.add(obj);
							strLatitude = null;
							strLongitude = null;
						} else if (RMDCommonConstants.GPS_HEADING
								.equalsIgnoreCase(objPPAssetHistoryHdrVO
										.getColumnAliasName())) {
							obj.setColumnName(objPPAssetHistoryHdrVO
									.getColumnAliasName());
							if (!RMDCommonUtility
									.isNullOrEmpty(RMDCommonUtility.convertObjectToString(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName())))) {
								String velSubstr;
								int iVelocity = 0;
								if (strVelocity != null) {
									if (strVelocity.indexOf('.') != -1)
										velSubstr = strVelocity.substring(0,
												strVelocity.indexOf('.'));
									else
										velSubstr = strVelocity;
									iVelocity = Integer.parseInt(velSubstr);
								}
								String heading = null;
								if (RMDCommonUtility
										.convertObjectToString(series
												.get(objPPAssetHistoryHdrVO
														.getColumnAliasName())) != null) {
									if (iVelocity < 1)
										heading = "";
									else
										heading = RMDCommonUtility
												.convertObjectToString(series
														.get(objPPAssetHistoryHdrVO
																.getColumnAliasName()))
												+ "";
								}
								if (heading != null)
									obj.setColumnValue(heading);
								else
									obj.setColumnValue("");
							} else {
								obj.setColumnValue("");
							}
							lstAssetHistoryParmDetailsVOs.add(obj);
						}
						else if (RMDCommonConstants.LEAD_TRAIL.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
						}
						else if (RMDCommonConstants.SMS_FUEL_PUMP_RELAY.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
							strValue = RMDCommonUtility
									.convertObjectToString(checkEngine(String.valueOf(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()))));
							obj.setColumnName(columnName);
							obj.setColumnValue(strValue);
							lstAssetHistoryParmDetailsVOs.add(obj);
						} else if (RMDCommonConstants.VEHICLE_ORIENTATION.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								) {
							String strOrientation = null;
							strOrientation = RMDCommonUtility
									.convertObjectToString(String.valueOf(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName())));
							if (objPPAssetStatusHistoryVO.getFlagMPData() != null
									&& objPPAssetStatusHistoryVO
											.getFlagMPData()
											.equalsIgnoreCase(
													RMDCommonConstants.LETTER_Y)) {
								if (RMDCommonConstants.SHORT_LOCO_S
										.equalsIgnoreCase(strOrientation)) {
									strValue = RMDCommonConstants.VIEW_SHORT_LOCO_ARZN;
								} else if (RMDCommonConstants.LONG_LOCO_L
										.equalsIgnoreCase(strOrientation)) {
									strValue = RMDCommonConstants.VIEW_LONG_ARZN;
								} else {
									strValue = RMDCommonConstants.VIEW_NA_ARZN;
								}
							} else {
								if (RMDCommonConstants.SHORT_LOCO_S
										.equalsIgnoreCase(strOrientation)) {
									strValue = RMDCommonConstants.VIEW_SHORT_LOCO;
								} else if (RMDCommonConstants.LONG_LOCO_L
										.equalsIgnoreCase(strOrientation)) {
									strValue = RMDCommonConstants.VIEW_LONG_LOCO;
								} else {
									strValue = RMDCommonConstants.VIEW_NA;
								}
							}
							obj.setColumnName(columnName);
							obj.setColumnValue(strValue);
							lstAssetHistoryParmDetailsVOs.add(obj);
						}

						else if (RMDCommonConstants.ATS_MESSAGE_REASON.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								&& objPPAssetStatusHistoryVO.getFlagMPData() != null
								&& objPPAssetStatusHistoryVO.getFlagMPData()
										.equalsIgnoreCase(
												RMDCommonConstants.LETTER_Y)) {
							strValue = RMDCommonUtility
									.convertObjectToString(String.valueOf(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName())));
							obj.setColumnName(columnName);
							if (strValue != null
									&& strValue
											.equalsIgnoreCase(RMDCommonConstants.ATS_MESSAGE_REASON_CODE)) {
								obj.setColumnValue(RMDCommonConstants.ATS_MESSAGE_REASON_YES);
							} else {
								obj.setColumnValue(RMDCommonConstants.ATS_MESSAGE_REASON_NO);
							}
							lstAssetHistoryParmDetailsVOs.add(obj);
						} else if (RMDCommonConstants.ATS_MESSAGE_REASON.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
								&& objPPAssetStatusHistoryVO.getFlagMPData() != null
								&& !objPPAssetStatusHistoryVO.getFlagMPData()
										.equalsIgnoreCase(
												RMDCommonConstants.LETTER_Y)) {
						} else if (RMDCommonConstants.DIRECTION_FROM_LOCATION
								.equalsIgnoreCase(objPPAssetHistoryHdrVO
										.getColumnAliasName())) {
							strDirection = RMDCommonUtility
									.convertObjectToString(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));
							
						} 
						else if (columnList.contains(objPPAssetHistoryHdrVO
								.getColumnAliasName())) {
							long conversionId = 0;
							for (Map.Entry<String, Long> entry : unitConversionData
									.entrySet()) {
								if (entry.getKey().equalsIgnoreCase(
										objPPAssetHistoryHdrVO
												.getColumnAliasName())) {

									conversionId = entry.getValue();
								}
							}

							if (conversionId != 0L) {
								if (RMDCommonConstants.METRIC
										.equalsIgnoreCase(objPPAssetStatusHistoryVO
												.getConversionPreference())) {
									uomConversion = UnitOfMeasureConversionManager.getUoMConversion(conversionId);
									StringBuilder direction = new StringBuilder();
									direction.append(strDirection);

									strValue = RMDCommonUtility
											.convertObjectToString(series.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));

									UnitOfMeasure targetUnitOfMeasure = uomConversion
											.getTarget();
									UnitOfMeasure unitOfMeasure = UnitOfMeasureConversionManager.getUnitOfMeasure(targetUnitOfMeasure.getId());
									if ((RMDCommonConstants.FUEL_LEVEL.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName())
											|| RMDCommonConstants.FUEL_ADDED.equalsIgnoreCase(objPPAssetHistoryHdrVO.getColumnAliasName()))
											&& strValue != null && Integer.parseInt(strValue) > 60000) {
										obj.setColumnName(objPPAssetHistoryHdrVO.getColumnAliasName());
										obj.setColumnValue(strValue+"~"+lookUpMap.get(RMDCommonConstants.ERC+strValue));
									}
									else if (RMDCommonConstants.GPS_VELOCITY
											.equalsIgnoreCase(objPPAssetHistoryHdrVO
													.getColumnAliasName())) {
										strVelocity = RMDCommonUtility
												.convertObjectToString(series
														.get(objPPAssetHistoryHdrVO
																.getColumnAliasName()));

										String displayValue = "0";

										if (strValue != null
												&& !"null"
														.equals(strValue + "")) {
											if(strValue.equals(RMDCommonConstants.ZERO_STRING))
											{
												String displayUnit = unitOfMeasure
														.getAbbreviation();
												obj.setColumnName(objPPAssetHistoryHdrVO
														.getColumnAliasName());
												obj.setColumnValue(displayValue
														+ " " + displayUnit);
											}
											else
											{
											displayValue = df
													.format(uomConversion.convert(Double
															.valueOf(strValue)));
											String displayUnit = unitOfMeasure
													.getAbbreviation();
											
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue(displayValue
													+ " " + displayUnit);
											}
										} else {
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue("");
										}

									} else if (RMDCommonConstants.NEAREST_LOCATION_MILES
											.equalsIgnoreCase(objPPAssetHistoryHdrVO
													.getColumnAliasName())
											&& !RMDCommonUtility
													.isNullOrEmpty(direction
															.toString())) {
										direction.append(" of");

										String displayValue = "0";

										if (strValue != null
												&& !"null"
														.equals(strValue + "")) {
											if(strValue.equals(RMDCommonConstants.ZERO_STRING))
											{
												String displayUnit = unitOfMeasure
														.getAbbreviation() + " ";
												
												obj.setColumnName(objPPAssetHistoryHdrVO
														.getColumnAliasName());
												obj.setColumnValue(displayValue
														+ " " + displayUnit
														+ direction);
											}
											else
											{
											displayValue = df
													.format(uomConversion.convert(Double
															.valueOf(strValue)));

											String displayUnit = unitOfMeasure
													.getAbbreviation() + " ";
											
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue(displayValue
													+ " " + displayUnit
													+ direction);
											}
										} else {
											displayValue = "";
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue(displayValue);
										}

									} else {

										String displayValue = "0";

										if (strValue != null
												&& !"null"
														.equals(strValue + "")) {

											displayValue = String
													.valueOf(df
															.format(uomConversion
																	.convert(Double
																			.valueOf(strValue))));
											if (Double.valueOf(displayValue) == 0.0)
												displayValue = "0";
											String displayUnit = unitOfMeasure.getAbbreviation();
											obj.setColumnName(objPPAssetHistoryHdrVO.getColumnAliasName());
											obj.setColumnValue(displayValue
													+ " " + displayUnit);
										} else {
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue("");
										}

									}
								} else {

									uomConversion = UnitOfMeasureConversionManager.getUoMConversion(conversionId);
									StringBuilder direction = new StringBuilder();
									direction.append(strDirection);

									strValue = RMDCommonUtility
											.convertObjectToString(series.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));

									UnitOfMeasure srcUnitOfMeasure = uomConversion.getSource();
									UnitOfMeasure unitOfMeasure = UnitOfMeasureConversionManager.getUnitOfMeasure(srcUnitOfMeasure.getId());
									if (RMDCommonConstants.FUEL_LEVEL
											.equalsIgnoreCase(objPPAssetHistoryHdrVO
													.getColumnAliasName())
											&& strValue != null
											&& Integer.parseInt(strValue) > 60000) {
										obj.setColumnName(objPPAssetHistoryHdrVO
												.getColumnAliasName());
										obj.setColumnValue(strValue+"~"+lookUpMap.get(RMDCommonConstants.ERC+strValue));
									} 
									else if (RMDCommonConstants.FUEL_ADDED
											.equalsIgnoreCase(objPPAssetHistoryHdrVO
													.getColumnAliasName())
											&& strValue != null
											&& Integer.parseInt(strValue) > 60000) {
										obj.setColumnName(objPPAssetHistoryHdrVO
												.getColumnAliasName());
										obj.setColumnValue("N/A");
									} 
									else if (RMDCommonConstants.GPS_VELOCITY
											.equalsIgnoreCase(objPPAssetHistoryHdrVO
													.getColumnAliasName())) {
										strVelocity = RMDCommonUtility
												.convertObjectToString(series
														.get(objPPAssetHistoryHdrVO
																.getColumnAliasName()));

										String displayValue = "0";

										if (strValue != null
												&& !"null"
														.equals(strValue + "")) {

											displayValue = strValue;
											String displayUnit = unitOfMeasure
													.getAbbreviation();
											
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue(displayValue
													+ " " + displayUnit);
										} else {
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue("");
										}

									} else if (RMDCommonConstants.NEAREST_LOCATION_MILES
											.equalsIgnoreCase(objPPAssetHistoryHdrVO
													.getColumnAliasName())
											&& !RMDCommonUtility
													.isNullOrEmpty(direction
															.toString())) {
										direction.append(" of");

										String displayValue = "0";

										if (strValue != null
												&& !"null"
														.equals(strValue + "")) {

											displayValue = strValue;

											String displayUnit = unitOfMeasure
													.getAbbreviation() + " ";
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue(displayValue
													+ " " + displayUnit
													+ direction);
										} else {
											displayValue = "";
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue(displayValue);
										}

									} else {

										//fhgfhfh
										String displayValue = "0";

										if (strValue != null
												&& !"null"
														.equals(strValue + "")) {

											displayValue = strValue;
											if (Double.valueOf(displayValue) == 0.0)
												displayValue = "0";
											String displayUnit = unitOfMeasure.getAbbreviation();
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue(displayValue
													+ " " + displayUnit);
										} else {
											obj.setColumnName(objPPAssetHistoryHdrVO
													.getColumnAliasName());
											obj.setColumnValue("");
										}

									}

								}
							} else {
								obj.setColumnName(objPPAssetHistoryHdrVO
										.getColumnAliasName());
								obj.setColumnValue(RMDCommonUtility
										.convertObjectToString(series
												.get(objPPAssetHistoryHdrVO
														.getColumnAliasName())));
							}
							lstAssetHistoryParmDetailsVOs.add(obj);
						} else {

							strValue = RMDCommonUtility
									.convertObjectToString(series
											.get(objPPAssetHistoryHdrVO
													.getColumnAliasName()));
							obj.setColumnName(columnName);
							obj.setColumnValue(strValue);
							lstAssetHistoryParmDetailsVOs.add(obj);
						}
					}
					objPPAssetHistoryVO
							.setPpAssetHistoryVOlst(lstAssetHistoryParmDetailsVOs);
					ppAssetHistoryVOList.add(objPPAssetHistoryVO);
				}
			}
				objPPAssetHistoryDetailsVODetails
						.setPpAssetHistoryHdrlst(ppAssetHistoryHdrVOList1);
				objPPAssetHistoryDetailsVODetails
						.setPpAssetHistorylst(ppAssetHistoryVOList);
				objPPAssetHistoryDetailsVODetails.setTotalRecord(countRecord);
				ppAssetHistoryDtlsVOLst.add(objPPAssetHistoryDetailsVODetails);
				
				Date date4 = new Date();
				// display time and date using toString()
				LOG.info("Unit conversion process ends------------" + date4);
				String str4 = String.format("Current Date/Time : %tc", date4);
				LOG.info("After DAO------------" + str4);
			
		}  catch (Exception e) {
			
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objPPAssetStatusHistoryVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
			releaseSession(objSession2);
			releaseSession(objSession3);
			releaseSession(objSession4);
			releaseSession(objSession5);
		}
		return ppAssetHistoryDtlsVOLst;

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.pp.services.asset.dao.intf.PPAssetDAOIntf#getPPStates(com
	 * .ge.trans.pp.services.asset.service.valueobjects.PPStatesVO) This method
	 * is used to fetch the country and its corresponding states from the
	 * database for pinpoint filter
	 */
	@Override
	public List<PPStatesResponseVO> getPPStates(final PPStatesVO objPPStatesVO) {

		List<PPStatesResponseVO> ppStateRespList = new ArrayList<PPStatesResponseVO>();
		PPStatesResponseVO ppStateResp;
		PPStatesResponseVO ppTempResp;
		List<StatesVO> statesVOLst;
		StatesVO statesVOResp = null;
		Session hibernateSession = null;
		final StringBuilder strStateQuery = new StringBuilder();
		hibernateSession = getHibernateSession();
		List<String> countryIdList = new ArrayList<String>();
		Map<String, PPStatesResponseVO> selectStateMap = new LinkedHashMap<String, PPStatesResponseVO>();
		try {
			strStateQuery
					.append(" SELECT COUNTRY.S_NAME AS COUNTRY_NAME, STATE.S_NAME AS STATE_NAME");
			strStateQuery
					.append(" FROM TABLE_COUNTRY COUNTRY,  TABLE_STATE_PROV STATE");
			strStateQuery
					.append(" WHERE  STATE.STATE_PROV2COUNTRY = COUNTRY.OBJID");
			strStateQuery
					.append(" ORDER BY COUNTRY.S_NAME DESC, STATE.S_NAME ASC");

			final Query query = hibernateSession.createSQLQuery(strStateQuery
					.toString());
			final List<Object> lsPPResp = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(lsPPResp)) {
				for (int idx = 0; idx < lsPPResp.size(); idx++) {
					ppStateResp = new PPStatesResponseVO();
					Object[] ppRespData = (Object[]) lsPPResp.get(idx);

					statesVOResp = new StatesVO();
					statesVOResp.setStateName(RMDCommonUtility.convertObjectToString(ppRespData[1]));

					/* Logic for country and state response */
					if (countryIdList.contains(ppRespData[0].toString())) {

						if (selectStateMap
								.containsKey(ppRespData[0].toString())) {

							ppTempResp = null;
							statesVOLst = null;

							ppTempResp = selectStateMap.get(ppRespData[0]
									.toString());
							statesVOLst = ppTempResp.getStateList();
							statesVOLst.add(statesVOResp);
							ppTempResp.setStateList(statesVOLst);
							selectStateMap.put(ppRespData[0].toString(),
									ppTempResp);
						}

					} else {/* Adding if single state info in country */
						countryIdList.add(ppRespData[0].toString());
						statesVOLst = new ArrayList<StatesVO>();
						statesVOLst.add(statesVOResp);
						ppStateResp.setStateList(statesVOLst);
						ppStateResp.setCountryName(RMDCommonUtility.convertObjectToString(ppRespData[0]));
						
						selectStateMap.put(ppRespData[0].toString(), ppStateResp);

					}

				}

				Collection<PPStatesResponseVO> collObject = selectStateMap.values();

				// obtain an Iterator for Collection
				Iterator<PPStatesResponseVO> itr = collObject.iterator();

				// iterate through HashMap values iterator to add the values in
				// list
				while (itr.hasNext()) {
					ppStateRespList.add(itr.next());
				}
				/* Logic for country and state response */
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objPPStatesVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objPPStatesVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return ppStateRespList;
	}

	/*
	 * @Description: Function to get the assets for pinpoint screens
	 */
	@Override
	public List<PPAssetDetailsVO> getPPAssets(PPAssetDetailsVO assetVO)
			throws RMDDAOException {
		List<PPAssetDetailsVO> assetList = new ArrayList<PPAssetDetailsVO>();
		Session hibernateSession = null;
		final StringBuilder assetQry = new StringBuilder();
		hibernateSession = getHibernateSession();
		List<Object[]> arlAssetNo = null;
		PPAssetDetailsVO assetResponseVO = null;
		try {

			assetQry.append("SELECT TBO.ORG_ID,TSP.X_VEH_HDR,TSP.SERIAL_NO ");
			assetQry.append("FROM TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO ");
			assetQry.append("WHERE TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
			assetQry.append("AND TSP.SERIAL_NO NOT LIKE '%BAD%'");

			if (null != assetVO.getAssetNumber()
					&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO
							.getAssetNumber())) {
				assetQry.append(" AND TSP.SERIAL_NO LIKE :assetNumber");
			}
			if (null != assetVO.getAssetGrpName()
					&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO
							.getAssetGrpName())) {
				assetQry.append(" AND TSP.X_VEH_HDR =:groupName");
			}
			if (null != assetVO.getCustomerId()
					&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO
							.getCustomerId())) {
				assetQry.append(" AND TBO.ORG_ID =:customerId");
			}
			assetQry.append(" ORDER BY SERIAL_NO, X_VEH_HDR, ORG_ID ASC");
			if (null != hibernateSession) {
				Query assetHqry = hibernateSession.createSQLQuery(assetQry
						.toString());

				if (null != assetVO.getAssetNumber()
						&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO
								.getAssetNumber())) {
					String assetNo = RMDCommonConstants.MODE_SYMBOL
							+ assetVO.getAssetNumber()
							+ RMDCommonConstants.MODE_SYMBOL;
					assetHqry.setParameter(RMDCommonConstants.ASSET_NUMBER,
							assetNo);
				}
				if (null != assetVO.getAssetGrpName()
						&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO
								.getAssetGrpName())) {
					assetHqry.setParameter(RMDCommonConstants.GROUP_NAME,
							assetVO.getAssetGrpName());
				}
				if (null != assetVO.getCustomerId()
						&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO
								.getCustomerId())) {
					assetHqry.setParameter(RMDCommonConstants.CUSTOMER_ID,
							assetVO.getCustomerId());
				}

				assetHqry.setFetchSize(2000);
				arlAssetNo = (ArrayList) assetHqry.list();

				if (RMDCommonUtility.isCollectionNotEmpty(arlAssetNo)) {
					for (Object[] obj : arlAssetNo) {
						assetResponseVO = new PPAssetDetailsVO();
						assetResponseVO.setAssetGrpName(RMDCommonUtility
								.convertObjectToString(obj[1]));
						assetResponseVO.setCustomerId(RMDCommonUtility
								.convertObjectToString(obj[0]));
						assetResponseVO.setAssetNumber(RMDCommonUtility
								.convertObjectToString(obj[2]));
						assetList.add(assetResponseVO);
					}
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_GET_ASSET);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							assetVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_GET_ASSET);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							assetVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return assetList;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to fetch the Status value for
	 *              particular Road Number.
	 * 
	 */
	@Override
	public String getStatusValue(String rnhId, String rnId)
			throws RMDDAOException {
		Session session = null;
		String statusValue = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT GRV.PP_VEHICLE_CONDITION FROM GETS_RMD_VEHICLE GRV, TABLE_SITE_PART TSP ");
			caseQry.append(" WHERE GRV.VEHICLE2SITE_PART = TSP.OBJID ");
			caseQry.append(" AND UPPER(TSP.X_VEH_HDR) =UPPER(:rnhId) AND TSP.SERIAL_NO =UPPER(:rnId)");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.RNH_ID, rnhId);
			caseHqry.setParameter(RMDCommonConstants.RN_ID, rnId);
			if (null != caseHqry.uniqueResult()) {
				statusValue = caseHqry.uniqueResult().toString();
			} else {
				statusValue = RMDCommonConstants.STRING_NULL;
			}

		} catch (Exception e) {
			statusValue = RMDCommonConstants.STRING_NULL;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_STATUS_VALUE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);

		} finally {
			releaseSession(session);
		}
		return statusValue;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to get the Status OBJID for particular
	 *              Road Number.
	 * 
	 */

	public String getStatusObjid(String rnhId, String rnId)
			throws RMDDAOException {
		Session session = null;
		String objId = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT DISTINCT GRV.OBJID FROM GETS_RMD_VEHICLE GRV, TABLE_SITE_PART TSP ");
			caseQry.append(" WHERE GRV.VEHICLE2SITE_PART = TSP.OBJID ");
			caseQry.append(" AND UPPER(TSP.X_VEH_HDR) =UPPER(:rnhId) AND TSP.SERIAL_NO =UPPER(:rnId)");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.RNH_ID, rnhId);
			caseHqry.setParameter(RMDCommonConstants.RN_ID, rnId);
			BigDecimal objid = (BigDecimal) caseHqry.uniqueResult();
			objId = objid.toString();
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHANGE_STATUS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return objId;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to update the Status value for
	 *              particular Road Number.
	 * 
	 */
	@Override
	public void changeStatus(String status, String rnhId, String rnId)
			throws RMDDAOException {
		Session session = null;
		String query = null;
		Query hibernateQuery = null;
		String objId = null;
		try {
			session = getHibernateSession();
			objId = getStatusObjid(rnhId, rnId);

			query = "UPDATE GETS_RMD_VEHICLE SET PP_VEHICLE_CONDITION=:status WHERE OBJID = :objId";
			hibernateQuery = session.createSQLQuery(query);
			hibernateQuery.setParameter(RMDCommonConstants.STATUS, status);
			hibernateQuery.setParameter(RMDCommonConstants.OBJ_ID, objId);
			hibernateQuery.executeUpdate();

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHANGE_STATUS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to get the Model value for particular
	 *              Road Number.
	 * 
	 */
	@Override
	public String getPPAssetModel(String rnhId, String rnId)
			throws RMDDAOException {
		Session hibernateSession = null;
		String modelValue = null;
		String objId = null;
		Connection con = null;
		CallableStatement callableStmt = null;
		try {
			hibernateSession = getHibernateSession();
			con = hibernateSession.connection();
			objId = getStatusObjid(rnhId, rnId);
			callableStmt = con
					.prepareCall("{ ? = call GETS_SD_LOCOCLASS_PKG.CWC_LOCOCLASS_FN(?) }");
			callableStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
			callableStmt.setString(2, objId);
			callableStmt.execute();
			modelValue = callableStmt.getString(1);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_STATUS_VALUE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return modelValue;
	}

	/**
	 * 
	 * @param
	 * @return List<PPAssetHistoryDetailsVO>
	 * @Description * This method is used to get the PP Asset History data for
	 *              Customer.
	 * 
	 */
	@Override
	public List<PPAssetHistoryDetailsVO> getAssetHistory(
			PPAssetHstDtlsVO objPPAssetHstDtlsVO) throws RMDDAOException {
		Connection con = null;
		CallableStatement callableStmt = null;
		Session hibernateSession = null;
		String searchCriteria = null;
		String currentStatus = RMDCommonConstants.PP_CURRENT_STATUS;
		String sortBy=objPPAssetHstDtlsVO.getSortBy();
		if (sortBy == null){
			sortBy = RMDCommonConstants.PP_SORT_BY;
		}
		int noDays = 0;
		ResultSet ppAssetHistoryRs = null;
		List<PPAssetHistoryVO> ppAssetHistoryVOList = new ArrayList<PPAssetHistoryVO>();
		List<PPAssetHistoryDetailsVO> ppAssetHistoryDtlsVOLst = new ArrayList<PPAssetHistoryDetailsVO>();
		PPAssetHistoryVO objPPAssetHistoryVO = null;
		PPAssetHistoryDetailsVO objPPAssetHistoryDetailsVODetails = new PPAssetHistoryDetailsVO();
		PPAssetHistoryHdrVO objPPAssetHistoryHdrVO = new PPAssetHistoryHdrVO();
		List<PPAssetHistoryHdrVO> ppHistoryHeadersList = new ArrayList<PPAssetHistoryHdrVO>();
		List<PPAssetHistoryHdrVO> ppHistoryHeadersLst = new ArrayList<PPAssetHistoryHdrVO>();
		Map<String, List<PPAssetHistoryHdrVO>> ppHistoryHeadersMap = new LinkedHashMap<String, List<PPAssetHistoryHdrVO>>();
		int count;
		count = Integer
				.parseInt(getLookUpValue(RMDCommonConstants.OMD_PPASSETHISTORY_RECORD_LIMIT));
		String rnhId = objPPAssetHstDtlsVO.getRnhId();
		String rnId = objPPAssetHstDtlsVO.getRnId();
		String customerName = objPPAssetHstDtlsVO.getCustomerName();
		String days = objPPAssetHstDtlsVO.getDays();
		String fromDate = objPPAssetHstDtlsVO.getFromDate();
		String toDate = objPPAssetHstDtlsVO.getToDate();

		ppHistoryHeadersMap = getPPHistoryHeaders();

		if (days != null || fromDate != null) {
			ppHistoryHeadersList = ppHistoryHeadersMap
					.get(RMDCommonConstants.CAPS_ALL);
		} else {
			if (!(rnId.equalsIgnoreCase(RMDCommonConstants.ALL))) {// For Export
				ppHistoryHeadersList = ppHistoryHeadersMap
						.get(RMDCommonConstants.CAPS_ALL);
			} else {
				ppHistoryHeadersList = ppHistoryHeadersMap
						.get(RMDCommonConstants.PP_MULTIPLE_ASSET);
				ppHistoryHeadersLst = ppHistoryHeadersMap
						.get(RMDCommonConstants.CAPS_ALL);
				ppHistoryHeadersList.addAll(ppHistoryHeadersLst);
			}
		}

		try {
			Map<String, String> lookUpMap = getLookUpValues(RMDServiceConstants.FUEL_ERROR_CODES);
			hibernateSession = getHibernateSession();
			con = hibernateSession.connection();
			if (days != null) {
				noDays = Integer.parseInt(days);
				callableStmt = con
						.prepareCall("{ ? = call gets_sd_assetinfo_pkg.get_assethistoryinfo(?,?,?,?,?,?,?) }");
				callableStmt.registerOutParameter(1, OracleTypes.CURSOR);
				callableStmt.setString(2, rnhId);
				callableStmt.setString(3, rnId);
				callableStmt.setInt(4, noDays);
				callableStmt.setString(5, sortBy);
				callableStmt.setString(6, objPPAssetHstDtlsVO.getRoleId());
				callableStmt.registerOutParameter(7, java.sql.Types.INTEGER);
				callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
			} else if (fromDate != null) {
				callableStmt = con
						.prepareCall("{ ? = call gets_sd_assetinfo_pkg.get_assethistoryinfoNew(?,?,?,?,?,?,?,?) }");
				callableStmt.registerOutParameter(1, OracleTypes.CURSOR);
				callableStmt.setString(2, rnhId);
				callableStmt.setString(3, rnId);
				callableStmt.setString(4, fromDate);
				callableStmt.setString(5, toDate);
				callableStmt.setString(6, sortBy);
				callableStmt.setString(7, objPPAssetHstDtlsVO.getRoleId());
				callableStmt.registerOutParameter(8, java.sql.Types.INTEGER);
				callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			} else {
				if ((customerName != null)
						&& (customerName.trim().length() > 1)
						&& (rnId.equalsIgnoreCase(RMDCommonConstants.ALL))) {
					searchCriteria = RMDCommonConstants.PP_SEARCH_CRITERIA
							+ customerName
							+ RMDCommonConstants.PP_SEARCH_CRITERIAEND;
				} else if ((customerName != null)
						&& (customerName.trim().length() > 1)) {// For Export
					searchCriteria = RMDCommonConstants.PP_SEARCH_CRITERIA
							+ customerName
							+ RMDCommonConstants.PP_SEARCH_CRITERIAALL + rnId
							+ RMDCommonConstants.PP_SEARCH_CRITERIAEND;
				} else {
					searchCriteria = RMDCommonConstants.EMPTY_STRING;
				}
				callableStmt = con
						.prepareCall("{ ? = call gets_sd_assetinfo_pkg.get_asset_curr_info(?,?,?,?,?,?) }");
				callableStmt.registerOutParameter(1, OracleTypes.CURSOR);
				callableStmt.setString(2, searchCriteria);
				callableStmt.setString(3, currentStatus);
				callableStmt.setString(4, sortBy);
				callableStmt.setString(5, objPPAssetHstDtlsVO.getRoleId());
				callableStmt.registerOutParameter(6, java.sql.Types.INTEGER);
				callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			}
			callableStmt.executeQuery();
			ppAssetHistoryRs = (ResultSet) callableStmt.getObject(1);
			// final int statusSize = ppAssetHistoryRs.getFetchSize();
			ppAssetHistoryRs.setFetchSize(1000);
			int ppListCount = 0;
			while (ppAssetHistoryRs.next() && ppListCount < count) {
				objPPAssetHistoryVO = new PPAssetHistoryVO();
				List<PPAssetHistoryParmDetailsVO> lstAssetHistoryParmDetailsVOs = new ArrayList<PPAssetHistoryParmDetailsVO>();
				for (int i = 0; i < ppHistoryHeadersList.size(); i++) {
					objPPAssetHistoryHdrVO = ppHistoryHeadersList.get(i);
					PPAssetHistoryParmDetailsVO obj = new PPAssetHistoryParmDetailsVO();
					String columnName = objPPAssetHistoryHdrVO.getDisplayName();
					String strValue = null;
					if (objPPAssetHistoryHdrVO.getColumnAliasName()
							.equalsIgnoreCase(RMDCommonConstants.PP_MOVED)
							|| objPPAssetHistoryHdrVO.getColumnAliasName()
									.equalsIgnoreCase(
											RMDCommonConstants.PP_STATE_CHANGE)
							|| objPPAssetHistoryHdrVO
									.getColumnAliasName()
									.equalsIgnoreCase(RMDCommonConstants.PP_TL8)
							|| objPPAssetHistoryHdrVO
									.getColumnAliasName()
									.equalsIgnoreCase(RMDCommonConstants.PP_TL9)
							|| objPPAssetHistoryHdrVO.getColumnAliasName()
									.equalsIgnoreCase(
											RMDCommonConstants.PP_PROXLOADED)
							|| objPPAssetHistoryHdrVO.getColumnAliasName()
									.equalsIgnoreCase(
											RMDCommonConstants.PP_ECRUM)
							|| objPPAssetHistoryHdrVO.getColumnAliasName()
									.equalsIgnoreCase(
											RMDCommonConstants.PP_ECISO)) {
						strValue = RMDCommonUtility
								.convertObjectToString(checkOne(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName()
							.equalsIgnoreCase(
									RMDCommonConstants.PP_ENGINE_ONOFF)) {
						strValue = RMDCommonUtility
								.convertObjectToString(checkEngine(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName()
							.equalsIgnoreCase(RMDCommonConstants.PP_RVR)) {
						strValue = RMDCommonUtility
								.convertObjectToString(checkRVR(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName()
							.equalsIgnoreCase(RMDCommonConstants.PP_GPS)
							|| objPPAssetHistoryHdrVO.getColumnAliasName()
									.equalsIgnoreCase(
											RMDCommonConstants.PP_CMUSTATUS)) {
						strValue = RMDCommonUtility
								.convertObjectToString(checkValid(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName()
							.equalsIgnoreCase(RMDCommonConstants.PP_CONDITION)) {
						strValue = RMDCommonUtility
								.convertObjectToString(checkCondition(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName()
							.equalsIgnoreCase(RMDCommonConstants.PP_ACTUAL)) {
						strValue = RMDCommonUtility
								.convertObjectToString(checkActual(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName()
							.equalsIgnoreCase(RMDCommonConstants.PP_SMS_BCCB)
							&& objPPAssetHistoryHdrVO.getDisplayName()
									.equalsIgnoreCase(RMDCommonConstants.PP_IN)) {
						strValue = RMDCommonUtility
								.convertObjectToString(checkIn(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName()
							.equalsIgnoreCase(RMDCommonConstants.PP_SMS_BCCB)
							&& objPPAssetHistoryHdrVO.getDisplayName()
									.equalsIgnoreCase(
											RMDCommonConstants.PP_BCCB_STATUS)) {
						strValue = RMDCommonUtility
								.convertObjectToString(checkOne(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName().equalsIgnoreCase(RMDCommonConstants.PP_SMS_BCCB)
							&& objPPAssetHistoryHdrVO.getDisplayName().equalsIgnoreCase(RMDCommonConstants.PP_CONSIST_CAN)) {
						strValue = RMDCommonConstants.EMPTY_STRING;
					} else if (objPPAssetHistoryHdrVO.getColumnAliasName().equalsIgnoreCase(RMDCommonConstants.PP_HEADING)) {
						strValue = RMDCommonConstants.EMPTY_STRING;
					} else {
						strValue = RMDCommonUtility
								.convertObjectToString(checkNullData(ppAssetHistoryRs
										.getString(objPPAssetHistoryHdrVO
												.getColumnAliasName())));
					}
					obj.setColumnName(columnName);
					if(columnName.equalsIgnoreCase("Fuel (gals) ") && strValue != null && strValue.length() != 0){
						if(Integer.parseInt(strValue) > 60000 ){
							String desc = lookUpMap.get(RMDCommonConstants.ERC+strValue);
							
							obj.setColumnValue(strValue+"~"+desc);
						}else{
							obj.setColumnValue(strValue);
						}
					}else{
					obj.setColumnValue(strValue);
					}
					lstAssetHistoryParmDetailsVOs.add(obj);
				}
				objPPAssetHistoryVO
						.setPpAssetHistoryVOlst(lstAssetHistoryParmDetailsVOs);
				ppAssetHistoryVOList.add(objPPAssetHistoryVO);
				ppListCount++;
			}
			objPPAssetHistoryDetailsVODetails
					.setPpAssetHistorylst(ppAssetHistoryVOList);
			objPPAssetHistoryDetailsVODetails
					.setPpAssetHistoryHdrlst(ppHistoryHeadersList);
			ppAssetHistoryDtlsVOLst.add(objPPAssetHistoryDetailsVODetails);
			callableStmt.close();
			con.close();
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_HISTORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return ppAssetHistoryDtlsVOLst;
	}

	/**
	 * 
	 * @param
	 * @return List<PPAssetHistoryHdrVO>
	 * @Description * This method is used to get the Headers of PP Asset History
	 *              Table for Customer.
	 * 
	 */
	@Cacheable(value = "PPAssetHstHdrsCache")
	public Map<String, List<PPAssetHistoryHdrVO>> getPPHistoryHeaders()
			throws RMDDAOException {
		List<PPAssetHistoryHdrVO> ppAssetHistoryHdrVOList = null;
		List<Object[]> resultList = null;
		Session session = null;
		PPAssetHistoryHdrVO objPPAssetHistoryHdrDetails;
		Map<String, List<PPAssetHistoryHdrVO>> ppHistoryHeadersMap = new LinkedHashMap<String, List<PPAssetHistoryHdrVO>>();
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT GETS_RMD_PP_ASET_HDR_SEQ_ID,DSPLY_NAME,COL_ALIAS_NAME,TABLE_HDR_TYPE,ASET_SRCH_TYPE,TOOL_TIP_INFO,COL_COLR,SORT_ORDER ");
			caseQry.append(" FROM GETS_RMD.GETS_RMD_PP_ASET_HDR ORDER BY SORT_ORDER");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			resultList = caseHqry.list();

			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					objPPAssetHistoryHdrDetails = new PPAssetHistoryHdrVO();
					final Object[] ppAssetHistoryHdrDetails =  obj.next();

					objPPAssetHistoryHdrDetails
							.setDisplayName(RMDCommonUtility
									.convertObjectToString(ppAssetHistoryHdrDetails[1]));
					objPPAssetHistoryHdrDetails
							.setColumnAliasName(RMDCommonUtility
									.convertObjectToString(ppAssetHistoryHdrDetails[2]));
					objPPAssetHistoryHdrDetails
							.setTableHdType(RMDCommonUtility
									.convertObjectToString(ppAssetHistoryHdrDetails[3]));
					objPPAssetHistoryHdrDetails
							.setAsetSrchType(RMDCommonUtility
									.convertObjectToString(ppAssetHistoryHdrDetails[4]));
					objPPAssetHistoryHdrDetails
							.setToolTipInfo(RMDCommonUtility
									.convertObjectToString(ppAssetHistoryHdrDetails[5]));
					objPPAssetHistoryHdrDetails
							.setColColp(RMDCommonUtility
									.convertObjectToString(ppAssetHistoryHdrDetails[6]));
					objPPAssetHistoryHdrDetails
							.setSortOrder(RMDCommonUtility
									.convertObjectToString(ppAssetHistoryHdrDetails[7]));
					String searchType = RMDCommonUtility
							.convertObjectToString(ppAssetHistoryHdrDetails[4]);

					if (ppHistoryHeadersMap.containsKey(searchType)) {
						ppAssetHistoryHdrVOList = new ArrayList<PPAssetHistoryHdrVO>();
						ppAssetHistoryHdrVOList = ppHistoryHeadersMap
								.get(searchType);
						ppAssetHistoryHdrVOList
								.add(objPPAssetHistoryHdrDetails);
						ppHistoryHeadersMap.put(searchType,
								ppAssetHistoryHdrVOList);

					} else {
						ppAssetHistoryHdrVOList = new ArrayList<PPAssetHistoryHdrVO>();
						ppAssetHistoryHdrVOList
								.add(objPPAssetHistoryHdrDetails);
						ppHistoryHeadersMap.put(searchType,
								ppAssetHistoryHdrVOList);

					}
				}
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_PPHISTORY_HEADERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return ppHistoryHeadersMap;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to check data is null or not.
	 * 
	 */
	public static String checkNullData(final String data) {
		String inputData = data;
		if (inputData == null
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData)
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) {
			inputData = RMDCommonConstants.EMPTY_STRING;
		}
		return inputData;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to check data is having Valid or not.
	 * 
	 */
	public static String checkValid(final String data) {
		String inputData = data;
		if (inputData == null
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData)
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) {
			inputData = RMDCommonConstants.EMPTY_STRING;
		} else {
			if (RMDCommonConstants.VALID.equals(inputData)) {
				inputData = RMDCommonConstants.Y_LETTER_UPPER;
			} else {
				inputData = RMDCommonConstants.EMPTY_STRING;
			}
		}
		return inputData;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to check data is having Model or not.
	 * 
	 */
	public static String checkCondition(final String data) {
		String inputData = data;
		if (inputData == null
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData)
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) {
			inputData = RMDCommonConstants.EMPTY_STRING;
		} else {
			if (RMDCommonConstants.DEGRADED.equals(inputData)) {
				inputData = RMDCommonConstants.PP_DEGRADED;
			} else if (RMDCommonConstants.NORMAL.equals(inputData)) {
				inputData = RMDCommonConstants.PP_NORMAL;
			} else if (RMDCommonConstants.MAINTENANCE.equals(inputData)) {
				inputData = RMDCommonConstants.PP_MAINTENANCE;
			}
		}
		return inputData;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to check data is having Engine on/off
	 *              or not.
	 * 
	 */
	public static String checkEngine(final String data) {
		String inputData = data;
		if (inputData == null
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData)
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) {
			inputData = RMDCommonConstants.EMPTY_STRING;
		} else {
			if (RMDCommonConstants.ONE_STRING.equals(inputData)) {
				inputData = RMDCommonConstants.ON;
			} else {
				inputData = RMDCommonConstants.OFF;
			}
		}
		return inputData;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to check data is having One(1) or not.
	 * 
	 */
	public static String checkOne(final String data) {
		String inputData = data;
		if (inputData == null
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData)
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) {
			inputData = RMDCommonConstants.EMPTY_STRING;
		} else {
			if (RMDCommonConstants.ONE_STRING.equals(inputData)) {
				inputData = RMDCommonConstants.Y_LETTER_UPPER;
			} else {
				inputData = RMDCommonConstants.EMPTY_STRING;
			}
		}
		return inputData;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to check data is having RVR or not.
	 * 
	 */
	public static String checkRVR(final String data) {
		String inputData = data;
		if (inputData == null
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData)
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) {
			inputData = RMDCommonConstants.EMPTY_STRING;
		} else {
			if (RMDCommonConstants.ONE_STRING.equals(inputData)) {
				inputData = RMDCommonConstants.FR;
			} else {
				inputData = RMDCommonConstants.N_LETTER_UPPER;
			}
		}
		return inputData;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to check data is having Actual or not.
	 * 
	 */
	public static String checkActual(final String data) {
		String inputData = data;
		if (inputData == null
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData)
				|| RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) {
			inputData = RMDCommonConstants.EMPTY_STRING;
		} else {
			if (RMDCommonConstants.ZERO_STRING.equals(inputData)) {
				inputData = RMDCommonConstants.Y_LETTER_UPPER;
			} else {
				inputData = RMDCommonConstants.EMPTY_STRING;
			}
		}
		return inputData;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to add * symbol.
	 * 
	 */
	public static String checkIn(final String data) {
		String inputData = data;
		if (RMDCommonConstants.ZERO_STRING.equals(inputData)) {
			inputData = RMDCommonConstants.STAR_SYMBOL;
		} else {
			inputData = RMDCommonConstants.EMPTY_STRING;
		}
		return inputData;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<PPAssetDetailsVO>
	 * @throws:RMDDAOException,Exception
	 * @Description: This method is used for fetching the the pin point enabled
	 *               assets.
	 */
	@Override
	public List<PPAssetDetailsVO> getPPSearchAssets(PPAssetStatusVO ppAssetStatusVO) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		PPAssetDetailsVO ppAssetDetailsVO = null;
		List<PPAssetDetailsVO> ppHeaderSearchVOList = new ArrayList<PPAssetDetailsVO>();
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT  /*+  NO_PARALLEL  USE_NL (SH)*/ TSP.SERIAL_NO,TSP.X_VEH_HDR,TSP.X_VEH_HDR_NO,TBO.ORG_ID,GRM.MODEL_NAME,GRF.FLEET_NUMBER  FROM ");
			caseQry.append(" TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH,GETS_RMD_MODEL GRM,GETS_RMD_FLEET GRF, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO, GETS_TOOL_PP_STATUS_CURR CR ");
			caseQry.append(" WHERE TSP.OBJID= VEH.VEHICLE2SITE_PART AND TSP.SERIAL_NO NOT LIKE '%BAD%' AND ");
			caseQry.append(" VEH.VEHICLE2VEH_HDR= VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG= TBO.OBJID  AND CR.STATUS_CURR2VEHICLE= VEH.OBJID AND GRM.OBJID = VEH.VEHICLE2MODEL AND GRF.OBJID = VEH.VEHICLE2FLEET ");
			caseQry.append(" AND VEH.OBJID IN ((SELECT vc.veh_cfg2vehicle FROM gets_rmd_vehcfg vc, gets_omi_mdsc_sup_def gsup WHERE vc.current_version  = '1' AND vc.veh_cfg2vehicle = gsup.mdsc_sup_def2vehicle "
					+ "AND gsup.service_pinpoint = 1 and (gsup.active_config = 'Y' OR gsup.active_config IS NULL) AND vehcfg2master_bom IN ( SELECT objid FROM gets_rmd_master_bom bom WHERE config_item = 'CMU')) ");
			caseQry.append(" UNION ALL ");
			caseQry.append(" (SELECT vehicle_objid FROM gets_mcs.mcs_asset a, gets_mcs.mcs_lookup_value st, gets_mcs.mcs_application service, gets_mcs.mcs_asset_application app, ");
			caseQry.append(" gets_rmd_cust_rnh_rn_v v WHERE status = st.objid AND app.application_objid = service.objid AND app.asset_objid = a.objid ");
			caseQry.append(" AND v.vehicle2asset_objid = a.objid AND application_name = 'ATS')) ");
			if (null != ppAssetStatusVO.getCustomerId()
					&& ppAssetStatusVO.getCustomerId().length() > 0) {
				caseQry.append("AND TBO.ORG_ID IN ( :customerId )");
			}

			if (null != ppAssetStatusVO.getAssetNumber()
					&& ppAssetStatusVO.getAssetNumber().length() > 0) {
				caseQry.append("AND TSP.SERIAL_NO LIKE :assetNumber");
			}

			if (null != ppAssetStatusVO.getAssetGroupName()
					&& ppAssetStatusVO.getAssetGroupName().length() > 0) {

				caseQry.append("AND TSP.X_VEH_HDR= :assetGrpName");
			}
			if (null != ppAssetStatusVO.getProducts() && !RMDCommonUtility.checkNull(ppAssetStatusVO.getProducts())
					&& !ppAssetStatusVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
					caseQry.append(" AND TSP.OBJID in ("
							+ getProductQuery(ppAssetStatusVO.getCustomerId())
							+ " ) ");
			}
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			if (null != ppAssetStatusVO.getCustomerId()
					&& !RMDCommonConstants.EMPTY_STRING.equals(ppAssetStatusVO
							.getCustomerId())) {
				List<String> customerList = Arrays.asList(ppAssetStatusVO
						.getCustomerId().split(
								RMDCommonConstants.COMMMA_SEPARATOR));
				caseHqry.setParameterList(RMDServiceConstants.CUSTOMER_ID,
						customerList);
			}

			if (null != ppAssetStatusVO.getAssetNumber()
					&& ppAssetStatusVO.getAssetNumber().length() > 0) {
				caseHqry.setParameter(
						RMDCommonConstants.ASSET_NUMBER,
						RMDServiceConstants.PERCENTAGE
								+ ppAssetStatusVO.getAssetNumber()
								+ RMDServiceConstants.PERCENTAGE);
			}

			if (null != ppAssetStatusVO.getAssetGroupName()
					&& ppAssetStatusVO.getAssetGroupName().length() > 0) {

				caseHqry.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
						ppAssetStatusVO.getAssetGroupName());
			}
			if (null != ppAssetStatusVO.getProducts() && !RMDCommonUtility.checkNull(ppAssetStatusVO.getProducts())) {
				if (!ppAssetStatusVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)
					&& null != ppAssetStatusVO.getProducts() && !RMDCommonUtility.checkNull(ppAssetStatusVO.getProducts())) {
					caseHqry.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST, ppAssetStatusVO.getProducts());
					if (null != ppAssetStatusVO.getCustomerId()
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(ppAssetStatusVO.getCustomerId())) {
						List<String> customerList = Arrays
								.asList(ppAssetStatusVO
										.getCustomerId()
										.split(RMDCommonConstants.COMMMA_SEPARATOR));
						caseHqry.setParameterList(
								RMDServiceConstants.CUSTOMER_ID,
								customerList);
					}
				}
			}
			caseHqry.setFetchSize(1000);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

				Collections.sort(resultList, new Comparator<Object[]>() {
					@Override
					public int compare(Object[] obj1, Object[] obj2) {
						int result = 0;
						try {
							int assetNumber1 = RMDCommonUtility
									.convertObjectToInt(obj1[0]);
							int assetNumber2 = RMDCommonUtility
									.convertObjectToInt(obj2[0]);
							if (assetNumber1 == assetNumber2) {
								result = 0;
							}
							result = (assetNumber1 < assetNumber2) ? -1 : 1;
						} catch (Exception e) {
							LOG.error(e.getMessage(), e);
							String assetNumber1 = RMDCommonUtility
									.convertObjectToString(obj1[0]);
							String assetNumber2 = RMDCommonUtility
									.convertObjectToString(obj2[0]);

							result = assetNumber1
									.compareToIgnoreCase(assetNumber2);
						}
						return result;
					}
				});
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] ppAssetSearch = obj.next();
					ppAssetDetailsVO = new PPAssetDetailsVO();

					ppAssetDetailsVO.setAssetNumber(RMDCommonUtility
							.convertObjectToString(ppAssetSearch[0]));
					ppAssetDetailsVO.setAssetGrpName(RMDCommonUtility
							.convertObjectToString(ppAssetSearch[1]));
					ppAssetDetailsVO.setAssetGroupNumber(RMDCommonUtility
							.cnvrtBigDecimalObjectToLong(ppAssetSearch[2]));
					ppAssetDetailsVO.setCustomerId(RMDCommonUtility
							.convertObjectToString(ppAssetSearch[3]));
					ppAssetDetailsVO.setModel(RMDCommonUtility
							.convertObjectToString(ppAssetSearch[4]));
					ppAssetDetailsVO.setFleet(RMDCommonUtility
							.convertObjectToString(ppAssetSearch[5]));
					ppHeaderSearchVOList.add(ppAssetDetailsVO);
				}
			}
		} catch (RMDDAOConnectionException ex) {
			
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_GET_ASSET);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							ppAssetStatusVO.getLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_GET_ASSET);
			LOG.error("Unexpected Error occured in PPAssetDAOImpl getPPSearchAssets():"
					+ e.getMessage());
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							ppAssetStatusVO.getLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}

		return ppHeaderSearchVOList;
	}

	@Override
	public List<PPMetricsVO> getMetricsConversion() throws RMDDAOException {
		Session objHibernateSession = null;
		final StringBuilder caseQry = new StringBuilder();
		PPMetricsVO objPPAssetStatusHistoryVO = null;
		List<PPMetricsVO> aurizonMetricsList = new ArrayList<PPMetricsVO>();
		Map<String, String> unitMap = new HashMap<String, String>();
		try {
			objHibernateSession = getHibernateSession();
			if (null != objHibernateSession) {
				caseQry.append("SELECT ORG_ID,SCRN_NAME,COL_NAME, FROM_UNIT,TO_UNIT,CONV_REQ FROM GETS_RMD_UNIT_CONV");
				Query caseHqry = objHibernateSession.createSQLQuery(caseQry
						.toString());

				List<Object[]> metrics = caseHqry.list();

				if (RMDCommonUtility.isCollectionNotEmpty(metrics)) {
					for (final Iterator<Object[]> obj = metrics.iterator(); obj
							.hasNext();) {
						final Object[] units = obj.next();
						objPPAssetStatusHistoryVO = new PPMetricsVO();
						objPPAssetStatusHistoryVO
								.setCustomerName(RMDCommonUtility
										.convertObjectToString(units[0]));
						objPPAssetStatusHistoryVO
								.setScreenName(RMDCommonUtility
										.convertObjectToString(units[1]));
						objPPAssetStatusHistoryVO
								.setColumnName(RMDCommonUtility
										.convertObjectToString(units[2]));
						objPPAssetStatusHistoryVO.setFromUnit(RMDCommonUtility
								.convertObjectToString(units[3]));
						objPPAssetStatusHistoryVO.setToUnit(RMDCommonUtility
								.convertObjectToString(units[4]));
						objPPAssetStatusHistoryVO.setConvReq(RMDCommonUtility
								.convertObjectToString(units[5]));
						aurizonMetricsList.add(objPPAssetStatusHistoryVO);
					}
				}

				Set<String> unit = new HashSet<String>();

				for (PPMetricsVO responseType : aurizonMetricsList) {

					unit.add(responseType.getFromUnit());
					unit.add(responseType.getToUnit());
				}

				String unitStr = listToStringValue(unit);
				final StringBuilder caseQryUnit = new StringBuilder();

				caseQryUnit
						.append("SELECT OBJID,LOOK_VALUE FROM GETS_RMD_LOOKUP WHERE OBJID IN ("
								+ unitStr + ")");
				Query caseQryunitQry = objHibernateSession
						.createSQLQuery(caseQryUnit.toString());

				List<Object[]> metricsValue = caseQryunitQry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(metricsValue)) {
					for (final Iterator<Object[]> obj = metricsValue.iterator(); obj
							.hasNext();) {
						final Object[] units = obj.next();

						unitMap.put(RMDCommonUtility
								.convertObjectToString(units[0]),
								RMDCommonUtility
										.convertObjectToString(units[1]));
					}
				}

				for (PPMetricsVO responseType : aurizonMetricsList) {
					responseType.setFromUnit(unitMap.get(responseType
							.getFromUnit()));
					responseType
							.setToUnit(unitMap.get(responseType.getToUnit()));

				}

			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_MODEL_DETAILS);
		} finally {
			releaseSession(objHibernateSession);
		}
		return aurizonMetricsList;
	}

	private String listToStringValue(Set<String> val) {
		StringBuilder sb = new StringBuilder();
		for (String str : val) {
			sb.append("'" + str + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private String getLookUpValue(String listName) throws RMDDAOException {
		String lookUpValue = null;
		Session objHibernateSession = null;
		Object value = null;
		try {
			objHibernateSession = getHibernateSession();
			StringBuilder lookupbuffer = new StringBuilder();
			lookupbuffer
					.append("SELECT look_value FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME = :listName");
			Query lookupQuery = objHibernateSession.createSQLQuery(lookupbuffer
					.toString());
			lookupQuery.setParameter(RMDCommonConstants.LIST_NAME, listName);
			value = lookupQuery.uniqueResult();
			if (null != RMDCommonUtility.convertObjectToString(value)
					&& !RMDCommonUtility.convertObjectToString(value).isEmpty()) {
				lookUpValue = RMDCommonUtility.convertObjectToString(value);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_LOOKUP_VALUE);
		} finally {
			releaseSession(objHibernateSession);
		}
		return lookUpValue;
	}
	
	public static String convertLatLong(final String data) 
	{
		String inputData = data;
		if (inputData == null || ("null").equalsIgnoreCase(inputData) || RMDCommonConstants.EMPTY_STRING.equals(inputData) || RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) 
		{
			inputData = RMDCommonConstants.EMPTY_STRING;
		}
		else
		{//System.out.println("))))))))))))))))_____--------------data   " + data);
			final BigDecimal aDecimal = new BigDecimal(data);
			final BigDecimal another = aDecimal.setScale(5,BigDecimal.ROUND_HALF_DOWN);

			inputData = another.toString();
		}
		return inputData;
	}
	
	@Cacheable(value = { "UnitConversionCache" })
	private Map<String,Long> getUnitConversionData() throws RMDDAOException {
		Session objHibernateSession = null;
		final StringBuilder caseQry = new StringBuilder();
		Map<String, Long> unitMap = new HashMap<String, Long>();
		try {
			objHibernateSession = getHibernateSession();
			if (null != objHibernateSession) {
				caseQry.append("SELECT COL_NAME,UNIT_CONV2CONVERSION FROM GETS_RMD_UNIT_CONV where SCRN_NAME ='ATSHISTORY' OR SCRN_NAME = 'EMAILER'");
				Query caseHqry = objHibernateSession.createSQLQuery(caseQry
						.toString());

				List<Object[]> metrics = caseHqry.list();

				if (RMDCommonUtility.isCollectionNotEmpty(metrics)) {
					for (final Iterator<Object[]> obj = metrics.iterator(); obj
							.hasNext();) {
						final Object[] units =  obj.next();
						unitMap.put(RMDCommonUtility
								.convertObjectToString(units[0]), RMDCommonUtility
								.convertObjectToLong(units[1]));
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_MODEL_DETAILS);
		} finally {
			releaseSession(objHibernateSession);
		}
		return unitMap;
	}

	@Override
	public String getPPAssetHistoryHeaders(final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO) throws RMDDAOException {
		Session objSession = null;
		Session objSession2 = null;
		Session objSession3 = null;
		Session objSession4 = null;
		String result = null;
		List<Object[]> vehObjIdLst = null;
		List<Object[]> configObjIdLst = null;
		int vehObjId = 0;
		int contSourceId = 0;
		String groupName = objPPAssetStatusHistoryVO.getAssetGroupName();
		List<Object[]> ppAssetHistoryRs2 = null;
		String headers1 = "D/T Last Message,Lat/Long,Velocity,Heading,Distance,Location,State,Milepost,Region,Sub Region,Fuel Level,Fuel Added,Engine (On/Off),Not Moving Time,Engine On Not Moving,Engine Control,Engine Direction,Throttle Notch,Locomotive Moving After not Moving for X minutes";
		String headers2 = "D/T Last Message,Lat/Long,Velocity,Heading,Distance,Location,State,Milepost,Region,Sub Region,Fuel Level,Fuel Added,Engine (On/Off),Not Moving Time,Engine On Not Moving,Engine Control,Locomotive Orientation,Throttle Notch";
		String[] headersArr1 = null;
		String headerValue = null;
		if (objPPAssetStatusHistoryVO.getFlagMPData() != null
				&& objPPAssetStatusHistoryVO.getFlagMPData().equalsIgnoreCase(
						RMDCommonConstants.LETTER_Y)) {
			headersArr1 = headers1.split(RMDServiceConstants.COMMA);
		} else {
			headersArr1 = headers2.split(RMDServiceConstants.COMMA);
		}
		Map<String,String> mapHeader = new LinkedHashMap<String, String>();
		objSession = getHibernateSession();
		try {
			objSession2 = getHibernateSession();
			// Get vehicle obj Id
			StringBuilder vehicleObjIdQry = new StringBuilder();
			vehicleObjIdQry
					.append("SELECT VEHICLE_OBJID,BUS_ORG_OBJID FROM GETS_RMD_CUST_RNH_RN_V ");
			vehicleObjIdQry
					.append("WHERE ORG_ID = :custId AND VEHICLE_NO = :assetNum ");
			if (null != groupName
					&& !RMDCommonConstants.EMPTY_STRING.equals(groupName)) {
				vehicleObjIdQry.append("AND VEHICLE_HDR_CUST = :groupName ");
			}
			Query getVehObjIdHQry = objSession.createSQLQuery(vehicleObjIdQry
					.toString());
			getVehObjIdHQry.setParameter(RMDCommonConstants.CUST_ID,
					objPPAssetStatusHistoryVO.getCustomerId());
			getVehObjIdHQry.setParameter(RMDCommonConstants.ASSET_NUM,
					objPPAssetStatusHistoryVO.getAssetNumber());
			if (null != groupName
					&& !RMDCommonConstants.EMPTY_STRING.equals(groupName)) {
				getVehObjIdHQry.setParameter(RMDCommonConstants.GROUP_NAME,
						objPPAssetStatusHistoryVO.getAssetGroupName());
			}
			
			vehObjIdLst = (ArrayList) getVehObjIdHQry.list();
			if (null != vehObjIdLst && !vehObjIdLst.isEmpty()) {

				for (final Iterator<Object[]> obj = vehObjIdLst.iterator(); obj
						.hasNext();) {
					final Object[] units =  obj.next();

					vehObjId = RMDCommonUtility.convertObjectToInt(units[0]);
				}
			}

			StringBuilder configObjIdQry = new StringBuilder();

			configObjIdQry
					.append("select model_name, objid from gets_rmd_model where objid =(select vehicle2model from gets_rmd_vehicle where objid=:model)");
			Query getConfigObjIdHQry = objSession.createSQLQuery(configObjIdQry
					.toString());

			getConfigObjIdHQry.setParameter("model", vehObjId);
			configObjIdLst = (ArrayList) getConfigObjIdHQry.list();
			if (null != configObjIdLst && !configObjIdLst.isEmpty()) {
				for (final Iterator<Object[]> obj = configObjIdLst.iterator(); obj
						.hasNext();) {
					final Object[] units =  obj.next();
					contSourceId = RMDCommonUtility.convertObjectToInt(units[1]);
				}
			}

			for (String strHeader : headersArr1) {
				if(("D/T Last Message").equalsIgnoreCase(strHeader))
				{
					headerValue = "LAST_COMM_DATE";
				}
				else if(("Lat/Long").equalsIgnoreCase(strHeader))
				{
					headerValue = "GPS_LON_DISPLAY";
				}
				else if(("Velocity").equalsIgnoreCase(strHeader))
				{
					headerValue = "GPS_VELOCITY";
				}
				else if(("Heading").equalsIgnoreCase(strHeader))
				{
					headerValue = "GPS_HEADING";
				}
				else if(("Distance").equalsIgnoreCase(strHeader))
				{
					headerValue = "NEAREST_LOCATION_MILES";
				}
				else if(("Location").equalsIgnoreCase(strHeader))
				{
					headerValue = "LOCATION_DESC";
				}
				else if(("State").equalsIgnoreCase(strHeader))
				{
					headerValue = "STATE_PROVINCE";
				}
				else if(("Milepost").equalsIgnoreCase(strHeader))
				{
					headerValue = "MILEPOST_ID";
				}
				else if(("Region").equalsIgnoreCase(strHeader))
				{
					headerValue = "REGION";
				}
				else if(("Sub Region").equalsIgnoreCase(strHeader))
				{
					headerValue = "SUB_REGION";
				}
				else if(("Fuel Level").equalsIgnoreCase(strHeader))
				{
					headerValue = "FUEL_LEVEL";
				}
				else if(("Fuel Added").equalsIgnoreCase(strHeader))
				{
					headerValue = "FUEL_ADDED";
				}
				else if(("Engine (On/Off)").equalsIgnoreCase(strHeader))
				{
					headerValue = "SMS_FUEL_PUMP_RELAY";
				}
				else if(("Not Moving Time").equalsIgnoreCase(strHeader))
				{
					headerValue = "NOT_MOVING_TIMER";
				}
				else if(("Engine On Not Moving").equalsIgnoreCase(strHeader))
				{
					headerValue = "ENGINE_ON_TIMER";
				}
				else if(("Engine Control").equalsIgnoreCase(strHeader))
				{
					headerValue = "SMS_ENG_CTRL_RUN";
				}
				else if(("Engine Direction").equalsIgnoreCase(strHeader) || ("Locomotive Orientation").equalsIgnoreCase(strHeader))
				{
					headerValue = RMDCommonConstants.VEHICLE_ORIENTATION;
				}
				else if(("Throttle Notch").equalsIgnoreCase(strHeader))
				{
					headerValue = "LOCO_THROTTLE_POSITION";
				}
				else if(("Locomotive Moving After not Moving for X minutes").equalsIgnoreCase(strHeader))
				{
					headerValue = "ATS_MESSAGE_REASON";
				}
				else 
				{
					headerValue = strHeader;
				}
				mapHeader.put(headerValue, strHeader);// To																// Show																	// in																// UI
			}
			
			objSession4 = getHibernateSession();
			if (objPPAssetStatusHistoryVO.getFlagMPData() != null
					&& objPPAssetStatusHistoryVO.getFlagMPData().equalsIgnoreCase(
							RMDCommonConstants.LETTER_Y)) {
			StringBuilder configObjIdQry1 = new StringBuilder();
			configObjIdQry1.append("SELECT ");
			configObjIdQry1.append("LOAD.PARM_LOAD_COLUMN,");
			configObjIdQry1.append(" info.header_html,");
			configObjIdQry1.append(" info.sort_order,");
			configObjIdQry1.append(" info.format,");
			configObjIdQry1.append(" info.COLUMN_NAME");
			configObjIdQry1.append(" FROM gets_rmd_parmdef_load_info load,	");
			configObjIdQry1.append("gets_rmd_parmdef def,");
			configObjIdQry1.append("GETS_TOOL_CE_PARM_INFO_NEW info,");
			configObjIdQry1.append(" GETS_TOOL_CE_PARM_INFO_EXTAPP ext");
			configObjIdQry1.append(" WHERE load.PARM_OBJID =def.OBJID");
			configObjIdQry1.append(" AND info.parm_info2parmdef =def.objid");
			configObjIdQry1.append(" AND INFO.OBJID=EXT.EXTAPP2CE_PARM_INFO	");
			configObjIdQry1.append("AND INFO.CE_PARAM2MODEL = :contSourceId ");
			configObjIdQry1.append("AND LOAD.SOURCE_TYPE='ATS' ");
			configObjIdQry1.append("AND EXT.EXTAPP2BUS_ORG =268435482	");
			configObjIdQry1
					.append("AND info.ATS_COL_AVAIL ='Y' order by info.sort_order");
			Query getConfigObjIdHQry2 = objSession4
					.createSQLQuery(configObjIdQry1.toString());
			getConfigObjIdHQry2.setParameter("contSourceId", contSourceId);
			List<Object[]> configObjIdLst2 = (List<Object[]>) getConfigObjIdHQry2.list();
			ppAssetHistoryRs2 =  configObjIdLst2;
			}
			Date date2 = new Date();

			// display time and date using toString()
			String str2 = String.format("Current Date/Time : %tc", date2);

			LOG.info("After MP header query------------" + str2);
			
			if (null != ppAssetHistoryRs2 && !ppAssetHistoryRs2.isEmpty()) {
				
				for (final Iterator<Object[]> obj = ppAssetHistoryRs2
						.iterator(); obj.hasNext();) {
					final Object[] series =  obj.next();
					mapHeader.put(String.valueOf(series[0]), String.valueOf(series[1]));
				}
			}
			JSONObject header = new JSONObject(mapHeader);
			result = header.toString();
	}
		catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PP_HISTORY_HEADERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objPPAssetStatusHistoryVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
			releaseSession(objSession2);
			releaseSession(objSession3);
			releaseSession(objSession4);
		}
		return result;
	}

	@Override
	public List<PPMetricsVO> getUnitConversionData(
			List<UnitConversionDetailsVO> objUnitConversionDetailsVO)
			throws RMDDAOException {
		Session objHibernateSession = null;
		final StringBuilder caseQry = new StringBuilder();
		List<PPMetricsVO> unitMap = new ArrayList<PPMetricsVO>();
		PPMetricsVO ppMetricsVO = null;
		List<String> paramId = new ArrayList<String>();
		String displayValue = "0";
		String displayUnit = null;
		Map<String,String> unitConversionMap = new HashMap<String, String>();
		MetricsVO metric = null;
		List<MetricsVO> lstMetricsVO = null;
		try {
			objHibernateSession = getHibernateSession();
			UnitOfMeasureConversionManager unitOfMeasureConversionManager = new UnitOfMeasureConversionManager();
			Connection connection =	objHibernateSession.connection();
			unitOfMeasureConversionManager.initialize(connection);
			UOMConversion uomConversion = new UOMConversion();
			if (null != objHibernateSession) {
				caseQry.append("select PARM_ID,UNIT_CONV2CONVERSION from GETS_RMD_UNIT_CONV where PARM_ID is not null AND UNIT_CONV2CONVERSION is not null");
				Query caseHqry = objHibernateSession.createSQLQuery(caseQry
						.toString());
				List<Object[]> unitConvObjId = caseHqry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(unitConvObjId)) {
					for (final Iterator<Object[]> obj = unitConvObjId.iterator(); obj
							.hasNext();) {
						final Object[] units = obj.next();
						unitConversionMap.put(RMDCommonUtility
								.convertObjectToString(units[0]), RMDCommonUtility
								.convertObjectToString(units[1]));
					}
				}
			
				
				for(UnitConversionDetailsVO unitConversionDetailsVO : objUnitConversionDetailsVO){
					ppMetricsVO = new PPMetricsVO();
					lstMetricsVO = new ArrayList<MetricsVO>();
					String unitObjId = null;
					for(UnitConversionVO unitVO : unitConversionDetailsVO.getLstUnitConversionVO()){
						metric = new MetricsVO();
					if(unitVO.getParamId()!= null && !unitVO.getParamId().equals(RMDCommonConstants.EMPTY_STRING))
					{
						unitObjId= unitConversionMap.get(unitVO.getParamId());
					}
					
				if(unitConversionDetailsVO.getMeasurement()!= null && unitConversionDetailsVO.getMeasurement().equals("Metric")){
					if(unitObjId != null && !unitObjId.equals(RMDCommonConstants.EMPTY_STRING)){
					uomConversion = UnitOfMeasureConversionManager.getUoMConversion(Long.parseLong(unitObjId));
					UnitOfMeasure targetUnitOfMeasure = uomConversion
							.getTarget();
					UnitOfMeasure unitOfMeasure = UnitOfMeasureConversionManager.getUnitOfMeasure(targetUnitOfMeasure.getId());
					displayValue = df
							.format(uomConversion.convert(Double
									.valueOf(unitVO.getConvertValue())));
					displayUnit = unitOfMeasure
							.getAbbreviation();
					metric.setConvertedValue(displayValue);
					metric.setParamId(unitVO.getParamId());
					metric.setUnitName(displayUnit);
					}
					else
					{
						metric.setConvertedValue(unitVO.getConvertValue());
						metric.setParamId(unitVO.getParamId());
						metric.setUnitName(RMDCommonConstants.EMPTY_STRING);
					}
				}
				else
				{
					if(unitObjId != null && !unitObjId.equals(RMDCommonConstants.EMPTY_STRING)){
					uomConversion = UnitOfMeasureConversionManager.getUoMConversion(Long.parseLong(unitObjId));
							UnitOfMeasure srcUnitOfMeasure = uomConversion.getSource();
					UnitOfMeasure unitOfMeasure = UnitOfMeasureConversionManager.getUnitOfMeasure(srcUnitOfMeasure.getId());
					displayValue = unitVO.getConvertValue();
					displayUnit = unitOfMeasure
							.getAbbreviation();
					metric.setConvertedValue(displayValue);
					metric.setParamId(unitVO.getParamId());
					metric.setUnitName(displayUnit);
					}
					else
					{
						metric.setConvertedValue(unitVO.getConvertValue());
						metric.setParamId(unitVO.getParamId());
						metric.setUnitName(RMDCommonConstants.EMPTY_STRING);	
					}
					
				}
				lstMetricsVO.add(metric);
				}
					ppMetricsVO.setUserId(unitConversionDetailsVO.getUserId());
					
					ppMetricsVO.setLstMetricsVO(lstMetricsVO);
					unitMap.add(ppMetricsVO);
				}
				
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_MODEL_DETAILS);
		} finally {
			releaseSession(objHibernateSession);
		}
		return unitMap;
	}
}

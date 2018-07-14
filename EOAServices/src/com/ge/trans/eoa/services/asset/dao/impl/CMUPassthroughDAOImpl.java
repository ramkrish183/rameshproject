/**
 * ============================================================
 * Classification: GE Confidential
 * File : CMUPassthroughDAOImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.dao.impl;
 * Author : Capgemini.
 * Last Edited By :
 * Version : 1.0
 * Created on : 08-Mar-2017
 * 
 * Modified By : Initial Release
 *
 * Copyright (C) 2017 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.asset.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.services.asset.dao.intf.CMUPassthroughDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CMUPassthroughVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.logging.RMDLogger;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created : 08-March-2017
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public class CMUPassthroughDAOImpl extends BaseDAO implements
		CMUPassthroughDAOIntf {

	public static final RMDLogger cmuPassthroughLogger = RMDLogger
			.getLogger(CMUPassthroughDAOImpl.class);

	private static final long serialVersionUID = 113318229101960569L;

	@Override
	public List<String> getCmuCtrlCfgList() {

		List<String> cmuCtrlCfgTypesList = null;

		Session session = null;
		try {
			session = getHibernateSession();
			final String queryString = "select LOOK_VALUE FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME=:listName";
			Query query = session.createSQLQuery(queryString);
			query.setParameter(RMDCommonConstants.LIST_NAME,
					RMDCommonConstants.CMU_CTRL_CFG_LIST);
			query.setFetchSize(10);
			cmuCtrlCfgTypesList = query.list();
		} catch (Exception e) {
			cmuPassthroughLogger
					.error("Exception occured in getCmuCtrlCfgList while fetching CMU_CTRL_CFG_LIST from GETS_RMD_LOOKUP as "
							+ e);
		} finally {
			releaseSession(session);
		}
		return cmuCtrlCfgTypesList;
	}

	@Override
	public Map<String, String> getCmuMQDetails() {

		List<Object[]> resultList = null;
		Session session = null;
		Map<String, String> cmuMqDetails = new HashMap<String, String>(5);
		String cmuQuery = null;
		try {
			session = getHibernateSession();
			cmuQuery = "SELECT TITLE,VALUE FROM GETS_RMD_SYSPARM WHERE TITLE IN (?,?,?,?,?)";
			Query caseHqry = session.createSQLQuery(cmuQuery);
			caseHqry.setParameter(0, RMDServiceConstants.EGA_MQ_PORT);
			caseHqry.setParameter(1,
					RMDServiceConstants.EGA_MQ_ADMIN_INPUT_QUEUE);
			caseHqry.setParameter(2, RMDServiceConstants.EGA_MQ_CHANNEL);
			caseHqry.setParameter(3, RMDServiceConstants.EGA_MQ_HOST_NAME);
			caseHqry.setParameter(4, RMDServiceConstants.EGA_MQ_QUEUE_MANAGER);
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (resultList != null) {
				for (final Iterator<Object[]> recordObject = resultList
						.iterator(); recordObject.hasNext();) {
					final Object[] cmuMQdetails = recordObject.next();
					if (cmuMQdetails[1] != null) {
						cmuMqDetails.put(String.valueOf(cmuMQdetails[0]),
								String.valueOf(cmuMQdetails[1]));
					}
				}
			}
		} catch (Exception e) {
			cmuPassthroughLogger
					.error("Exception occured in getCmuMQDetails while fetching CMU Queue details from GETS_RMD_SYSPARM as "
							+ e);
		} finally {
			releaseSession(session);
		}
		return cmuMqDetails;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<CMUPassthroughVO> getCmuMQMessages(
			List<String> vehicleOjectIdList) {

		final DateFormat headerDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SSS");
		final DateFormat requestNoFormat = new SimpleDateFormat("MMddHHmmssSSS");
		Date currentDate = null;
		Session session = null;
		List<CMUPassthroughVO> cmuPassthroughVOList = new ArrayList<CMUPassthroughVO>(
				vehicleOjectIdList.size());
		StringBuilder cmuQuery = new StringBuilder();
		CMUPassthroughVO cmuPassthroughVO = null;
		List<Object[]> resultList = null;
		List<String> vehicleOjectIdTempList = null;
		Object[] vehicleDetails = null;

		try {
			session = getHibernateSession();
			cmuQuery.append("SELECT VEHICLE_NO,VEHICLE_HDR,ORG_ID,NX_MACHINE_ID FROM GETS_RMD_CUST_RNH_RN_V WHERE VEHICLE_OBJID IN (:vehicleObjIdList)");
			Query caseHqry = null;
			vehicleOjectIdTempList = new ArrayList<String>();

			for (int i = 0; i < vehicleOjectIdList.size(); i++) {

				vehicleOjectIdTempList.add(vehicleOjectIdList.get(i));

				if (vehicleOjectIdTempList.size() == 100
						|| ((vehicleOjectIdList.size() - 1) == i)) {

					caseHqry = session.createSQLQuery(cmuQuery.toString());
					caseHqry.setParameterList("vehicleObjIdList",
							vehicleOjectIdTempList);

					caseHqry.setTimeout(180);

					resultList = caseHqry.list();
					if (resultList != null) {
						for (final Iterator<Object[]> recordObject = resultList
								.iterator(); recordObject.hasNext();) {

							currentDate = Calendar.getInstance().getTime();
							cmuPassthroughVO = new CMUPassthroughVO();
							vehicleDetails = recordObject.next();

							if (vehicleDetails[0] != null) {
								cmuPassthroughVO.setVehicleNo(vehicleDetails[0]
										.toString());
							}
							cmuPassthroughVO
									.setVehicleInitial((vehicleDetails[1] == null) ? RMDCommonConstants.EMPTY_STRING
											: vehicleDetails[1].toString());
							cmuPassthroughVO
									.setCustId((vehicleDetails[2] == null) ? RMDCommonConstants.EMPTY_STRING
											: vehicleDetails[2].toString());
							cmuPassthroughVO
									.setPhyResId((vehicleDetails[3] == null) ? RMDCommonConstants.EMPTY_STRING
											: vehicleDetails[3].toString());
							cmuPassthroughVO.setHeaderDate(headerDateFormat
									.format(currentDate));
							cmuPassthroughVO.setRequestNo(requestNoFormat
									.format(currentDate));

							cmuPassthroughVOList.add(cmuPassthroughVO);

						}
					}

					vehicleOjectIdTempList = null;
					vehicleOjectIdTempList = new ArrayList<String>();

				}
			}

		} catch (Exception e) {
			cmuPassthroughLogger
					.error("Exception occured in getCmuMQMessages while fetching xml message details from GETS_RMD_CUST_RNH_RN_V as "
							+ e);
		} finally {
			releaseSession(session);
		}

		return cmuPassthroughVOList;
	}

}

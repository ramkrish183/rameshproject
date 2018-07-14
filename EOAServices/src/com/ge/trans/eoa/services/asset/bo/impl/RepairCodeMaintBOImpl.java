/**
 * ============================================================
 * Classification: GE Confidential
 * File : RepairCodeMaintBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.bo.impl;
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
package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.RepairCodeMaintBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.RepairCodeMaintDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class RepairCodeMaintBOImpl implements RepairCodeMaintBOIntf{

	private RepairCodeMaintDAOIntf objRepairCodeMaintDAOIntf;

	/**
	 * @param objVehicleCfgBOIntf
	 */
	public RepairCodeMaintBOImpl(RepairCodeMaintDAOIntf objRepairCodeMaintDAOIntf) {
		this.objRepairCodeMaintDAOIntf = objRepairCodeMaintDAOIntf;
	}
	
	public static final RMDLogger LOG = RMDLoggerHelper
	.getLogger(RepairCodeMaintBOImpl.class);

	/**
	 * @Author:
	 * @param:String selectBy, String condition, String status, String models,
	 *               String value
	 * @return:List<RepairCodeEoaDetailsVO>
	 * @throws:RMDBOException
	 * @Description: This method is used for fetching repair codes based on
	 *               search criteria.
	 */
	@Override
	public List<RepairCodeEoaDetailsVO> getRepairCodesList(String selectBy,
			String condition, String status, String models, String value)
			throws RMDBOException {
		List<RepairCodeEoaDetailsVO> objRepairCodeEoaDetailsVOList = null;
		try {
			objRepairCodeEoaDetailsVOList = objRepairCodeMaintDAOIntf
					.getRepairCodesList(selectBy, condition, status, models,
							value);
		} catch (RMDDAOException e) {
			LOG.error("Unexpected Error occured in getRepairCodesList()", e);
			throw new RMDBOException(e.getErrorDetail());
		}
		return objRepairCodeEoaDetailsVOList;
	}

	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDBOException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	@Override
	public String repairCodeValidations(String repairCode,
			String repairCodeDesc, String flag) throws RMDBOException {
		String result = null;
		try {
			result = objRepairCodeMaintDAOIntf.repairCodeValidations(
					repairCode, repairCodeDesc, flag);
		} catch (RMDDAOException e) {
			LOG.error("Unexpected Error occured in repairCodeValidations()", e);
			throw new RMDBOException(e.getErrorDetail());
		}
		return result;
	}

	@Override
	public String addRepairCodes(String repairCode, String repairCodeDesc,
			String status, String model) throws RMDBOException {
		String result = null;
		try {
			result = objRepairCodeMaintDAOIntf.addRepairCodes(
					repairCode, repairCodeDesc, status,model);
		} catch (RMDDAOException e) {
			LOG.error("Unexpected Error occured in addRepairCodes()", e);
			throw new RMDBOException(e.getErrorDetail());
		}
		return result;
	}

	@Override
	public String updateRepairCodes(String repairCode, String repairCodeDesc,
			String status, String model) throws RMDBOException {
		String result = null;
		try {
			result = objRepairCodeMaintDAOIntf.updateRepairCodes(
					repairCode, repairCodeDesc, status,model);
		} catch (RMDDAOException e) {
			LOG.error("Unexpected Error occured in updateRepairCodes()", e);
			throw new RMDBOException(e.getErrorDetail());
		}
		return result;
	}
}

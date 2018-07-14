/**
 * ============================================================
 * Classification: GE Confidential
 * File : RepairCodeMaintServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.service.impl;
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
package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.RepairCodeMaintBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.RepairCodeMaintServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class RepairCodeMaintServiceImpl implements RepairCodeMaintServiceIntf{

	private RepairCodeMaintBOIntf objRepairCodeMaintBOIntf;

	/**
	 * @param objVehicleCfgBOIntf
	 */
	public RepairCodeMaintServiceImpl(RepairCodeMaintBOIntf objRepairCodeMaintBOIntf) {
		this.objRepairCodeMaintBOIntf = objRepairCodeMaintBOIntf;
	}

	/**
	 * @Author:
	 * @param:String selectBy, String condition, String status, String models,
	 *               String value
	 * @return:List<RepairCodeEoaDetailsVO>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching repair codes based on
	 *               search criteria.
	 */
	@Override
	public List<RepairCodeEoaDetailsVO> getRepairCodesList(String selectBy,
			String condition, String status, String models, String value)
			throws RMDServiceException {
		List<RepairCodeEoaDetailsVO> objRepairCodeEoaDetailsVOList = null;
		try {
			objRepairCodeEoaDetailsVOList = objRepairCodeMaintBOIntf
					.getRepairCodesList(selectBy, condition, status, models,
							value);
		} catch (RMDDAOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		}
		return objRepairCodeEoaDetailsVOList;
	}

	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	@Override
	public String repairCodeValidations(String repairCode,
			String repairCodeDesc, String flag) throws RMDServiceException {
		String result = null;
		try {
			result = objRepairCodeMaintBOIntf.repairCodeValidations(repairCode,
					repairCodeDesc, flag);
		} catch (RMDDAOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		}
		return result;
	}

	@Override
	public String addRepairCodes(String repairCode, String repairCodeDesc,
			String status, String model) throws RMDServiceException {
		String result = null;
		try {
			result = objRepairCodeMaintBOIntf.addRepairCodes(repairCode,
					repairCodeDesc, status,model);
		} catch (RMDDAOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		}
		return result;
	}
	@Override
	public String updateRepairCodes(String repairCode, String repairCodeDesc,
			String status, String model) throws RMDServiceException {
		String result = null;
		try {
			result = objRepairCodeMaintBOIntf.updateRepairCodes(repairCode,
					repairCodeDesc, status,model);
		} catch (RMDDAOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		}
		return result;
	}
}

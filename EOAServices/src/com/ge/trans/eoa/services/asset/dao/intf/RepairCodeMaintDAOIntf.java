/**
 * ============================================================
 * Classification: GE Confidential
 * File : RepairCodeMaintDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.dao.intf;
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
package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface RepairCodeMaintDAOIntf {
	/**
	 * @Author:
	 * @param:String selectBy, String condition, String status, String models,
	 *               String value
	 * @return:List<RepairCodeEoaDetailsVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching repair codes based on
	 *               search criteria.
	 */
	public List<RepairCodeEoaDetailsVO> getRepairCodesList(String selectBy,
			String condition, String status, String models, String value)
			throws RMDDAOException;

	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	public String repairCodeValidations(String repairCode,
			String repairCodeDesc, String flag) throws RMDDAOException;
	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String status,String models
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for adding the repair codes.
	 */
	public String addRepairCodes(String repairCode, String repairCodeDesc,
			String status, String models) throws RMDDAOException;

	public String updateRepairCodes(String repairCode, String repairCodeDesc,
			String status, String model)throws RMDDAOException;

}

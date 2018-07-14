/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.bo.intf;
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
package com.ge.trans.eoa.services.asset.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.BOMMaintenanceVO;
import com.ge.trans.rmd.exception.RMDBOException;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: March,17 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public interface BOMMaintenanceBOIntf {

	/**
	 * @Author :
	 * @return :List<BOMMaintenanceVO>
	 * @param :
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching config controller
	 *               list from database
	 * 
	 */
	public List<BOMMaintenanceVO> getConfigList() throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<BOMMaintenanceVO>
	 * @param : configId
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching config controller
	 *               details from database
	 * 
	 */
	public List<BOMMaintenanceVO> populateConfigDetails(String configId)
			throws RMDBOException;

	/**
	 * @Author :
	 * @return :List<BOMMaintenanceVO>
	 * @param : configId
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching parameter details
	 *               from database.
	 * 
	 */
	public List<BOMMaintenanceVO> getParameterList(String configId)
			throws RMDBOException;

	/**
	 * @Author :
	 * @return :String
	 * @param : List<BOMMaintenanceVO>
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for saving config controller
	 *               details in database
	 * 
	 */
	public String saveBOMDetails(List<BOMMaintenanceVO> bomMaintenanceVO)
			throws RMDBOException;
}

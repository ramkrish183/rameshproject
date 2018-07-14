/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgServiceImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.service.impl;
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
import com.ge.trans.eoa.services.asset.bo.intf.BOMMaintenanceBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.BOMServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.BOMMaintenanceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

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

public class BOMServiceImpl implements BOMServiceIntf {

	private BOMMaintenanceBOIntf objBOMMaintenanceBOIntf;

	/**
	 * @param objVehicleCfgBOIntf
	 */
	public BOMServiceImpl(BOMMaintenanceBOIntf objBOMMaintenanceBOIntf) {
		this.objBOMMaintenanceBOIntf = objBOMMaintenanceBOIntf;
	}

	@Override
	public List<BOMMaintenanceVO> getConfigList() throws RMDServiceException {

		List<BOMMaintenanceVO> bomVO = null;
		try {
			bomVO = objBOMMaintenanceBOIntf.getConfigList();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return bomVO;
	}

	@Override
	public List<BOMMaintenanceVO> populateConfigDetails(String configId)
			throws RMDServiceException {
		List<BOMMaintenanceVO> bOMMaintenanceVO = null;
		try {
			bOMMaintenanceVO = objBOMMaintenanceBOIntf
					.populateConfigDetails(configId);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return bOMMaintenanceVO;
	}

	@Override
	public List<BOMMaintenanceVO> getParameterList(String configId)
			throws RMDServiceException {
		List<BOMMaintenanceVO> bOMMaintenanceVO = null;
		try {
			bOMMaintenanceVO = objBOMMaintenanceBOIntf
					.getParameterList(configId);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return bOMMaintenanceVO;
	}

	@Override
	public String saveBOMDetails(List<BOMMaintenanceVO> bomMaintenanceVO)
			throws RMDServiceException {
		String bomCfgVOStatus = null;
		try {
			bomCfgVOStatus = objBOMMaintenanceBOIntf
					.saveBOMDetails(bomMaintenanceVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return bomCfgVOStatus;
	}

}

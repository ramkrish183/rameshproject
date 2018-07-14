/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgBOImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.bo.impl;
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

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.asset.bo.intf.BOMMaintenanceBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.BOMMaintenanceDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.BOMMaintenanceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;

public class BOMMaintenanceBOImpl implements BOMMaintenanceBOIntf {

	private BOMMaintenanceDAOIntf objBOMMaintenanceDAOIntf;
	public static final RMDLogger LOG = RMDLogger.getLogger(BOMMaintenanceBOImpl.class);

	/**
	 * @param objVehicleCfgDAOIntf
	 */
	public BOMMaintenanceBOImpl(BOMMaintenanceDAOIntf objBOMMaintenanceDAOIntf) {
		this.objBOMMaintenanceDAOIntf = objBOMMaintenanceDAOIntf;
	}

	@Override
	public List<BOMMaintenanceVO> getConfigList() throws RMDBOException {
		List<BOMMaintenanceVO> bomMainVO = null;
		try {
			bomMainVO = objBOMMaintenanceDAOIntf.getConfigList();
		} catch (RMDDAOException e) {
			throw e;
		}
		return bomMainVO;
	}

	@Override
	public List<BOMMaintenanceVO> populateConfigDetails(String configId)
			throws RMDBOException {
		List<BOMMaintenanceVO> bomMainVO = null;
		try {
			bomMainVO = objBOMMaintenanceDAOIntf
					.populateConfigDetails(configId);
		} catch (RMDDAOException e) {
			throw e;
		}
		return bomMainVO;
	}

	@Override
	public List<BOMMaintenanceVO> getParameterList(String configId)
			throws RMDBOException {
		List<BOMMaintenanceVO> bomMainVO = null;
		try {
			bomMainVO = objBOMMaintenanceDAOIntf.getParameterList(configId);
		} catch (RMDDAOException e) {
			throw e;
		}
		return bomMainVO;
	}

	@Override
	public String saveBOMDetails(List<BOMMaintenanceVO> bomMaintenanceVO)
			throws RMDBOException {
		String bomCfgVOStatus = null;
		try {
			bomCfgVOStatus = objBOMMaintenanceDAOIntf
					.saveBOMDetails(bomMaintenanceVO);
		} catch (RMDDAOException ex) {
			try {
				throw new RMDBOException(ex.getErrorDetail(), ex);
			} catch (RMDBOException e) {
				 LOG.error(e);
			}
		}
		return bomCfgVOStatus;
	}

}

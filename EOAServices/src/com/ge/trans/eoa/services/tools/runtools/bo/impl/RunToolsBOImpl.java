package com.ge.trans.eoa.services.tools.runtools.bo.impl;

/**
 * ============================================================
 * File : RunToolsBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.tools.runtools.bo.impl
 * Author : 
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.services.asset.dao.intf.AssetEoaDAOIntf;
import com.ge.trans.eoa.services.tools.runtools.bo.intf.RunToolsBOIntf;
import com.ge.trans.eoa.services.tools.runtools.dao.intf.RunToolsDAOIntf;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolDiagnosticRunDetailsVO;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolRunRequestVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class RunToolsBOImpl implements RunToolsBOIntf {

	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(RunToolsBOImpl.class);
	private RunToolsDAOIntf objRunToolsDAOIntf;
	private AssetEoaDAOIntf objAssetEoaDAOIntf;
	public RunToolsBOImpl(final RunToolsDAOIntf objRunToolsDAOIntf,final AssetEoaDAOIntf objAssetEoaDAOIntf) {
		this.objRunToolsDAOIntf = objRunToolsDAOIntf;
		this.objAssetEoaDAOIntf = objAssetEoaDAOIntf;
	}
	/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDBOException
	 * @Description: This method is used to get the next schedule run
	 */
	public String getNextScheduleRun(String vehicleObjid) throws RMDBOException {

		String result = null;
		try {
			result = objRunToolsDAOIntf.getNextScheduleRun(vehicleObjid);
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		return result;
	}
	/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDBOException
	 * @Description: This method is used to get the last diagnostic run details
	 */
	public ToolDiagnosticRunDetailsVO getLastDiagnosticRunDetails(
			String vehicleObjid) throws RMDBOException {

		ToolDiagnosticRunDetailsVO objToolDiagnosticRunDetailsVO = null;
		try {
			objToolDiagnosticRunDetailsVO = objRunToolsDAOIntf
					.getLastDiagnosticRunDetails(vehicleObjid);
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		return objToolDiagnosticRunDetailsVO;
	}
	/**
	 * @Author:
	 * @param:ToolRunRequestVO ObjToolRunRequestVO
	 * @return:String
	 * @throws:RMDBOException
	 * @Description: This method is used to run the tools
	 */

	public String runTools(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDBOException {

		String result = RMDCommonConstants.FAILURE;
		try {
			result = objRunToolsDAOIntf.runTools(ObjToolRunRequestVO);
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		return result;

	}
	
	/**
	 * @Author:
	 * @param:ToolRunRequestVO ObjToolRunRequestVO
	 * @return:String
	 * @throws:RMDBOException
	 * @Description: This method is used to run the tools
	 */

	public String runToolsNow(ToolRunRequestVO objToolRunRequestVO)
			throws RMDBOException {

		String result = RMDCommonConstants.FAILURE;
		try {
			String vehicleObjid=objAssetEoaDAOIntf.getVehicleObjectId(objToolRunRequestVO.getCustomerId(), objToolRunRequestVO.getAssetNumber(), objToolRunRequestVO.getAssetHeader());
			objToolRunRequestVO.setVehicleObjid(vehicleObjid);
			result = objRunToolsDAOIntf.runTools(objToolRunRequestVO);
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		return result;

	}
	
		/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDBOException
	 * @Description: This method is used to get the diagnostic services
	 */
	public List<ElementVO> getDiagnosticServices(String vehicleObjid)
			throws RMDBOException {

		List<ElementVO> lstElementVO = new ArrayList<ElementVO>();
		try {
			lstElementVO = objRunToolsDAOIntf
					.getDiagnosticServices(vehicleObjid);
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		return lstElementVO;
	}

}

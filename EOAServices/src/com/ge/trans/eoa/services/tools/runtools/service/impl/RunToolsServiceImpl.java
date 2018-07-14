package com.ge.trans.eoa.services.tools.runtools.service.impl;

/**
 * ============================================================
 * File : RunToolsServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.tools.runtools.service.impl
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

import com.ge.trans.eoa.services.tools.runtools.bo.intf.RunToolsBOIntf;
import com.ge.trans.eoa.services.tools.runtools.service.intf.RunToolsServiceIntf;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolDiagnosticRunDetailsVO;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolRunRequestVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class RunToolsServiceImpl implements RunToolsServiceIntf {

	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(RunToolsServiceImpl.class);
	private RunToolsBOIntf objRunToolsBOIntf;

	public RunToolsServiceImpl(RunToolsBOIntf objRunToolsBOIntf) {
		this.objRunToolsBOIntf = objRunToolsBOIntf;
	}

	/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the next schedule run
	 */
	public String getNextScheduleRun(String vehicleObjid)
			throws RMDServiceException {

		String result = null;
		try {
			result = objRunToolsBOIntf.getNextScheduleRun(vehicleObjid);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return result;
	}

	/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the last diagnostic run details
	 */
	public ToolDiagnosticRunDetailsVO getLastDiagnosticRunDetails(
			String vehicleObjid) throws RMDServiceException {

		ToolDiagnosticRunDetailsVO objToolDiagnosticRunDetailsVO = null;
		try {
			objToolDiagnosticRunDetailsVO = objRunToolsBOIntf
					.getLastDiagnosticRunDetails(vehicleObjid);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return objToolDiagnosticRunDetailsVO;
	}

	/**
	 * @Author:
	 * @param:ToolRunRequestVO ObjToolRunRequestVO
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to run the tools
	 */
	public String runTools(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDServiceException {

		String result = RMDCommonConstants.FAILURE;
		try {
			result = objRunToolsBOIntf.runTools(ObjToolRunRequestVO);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return result;

	}

	/**
	 * @Author:
	 * @param:ToolRunRequestVO ObjToolRunRequestVO
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to run the tools
	 */
	public String runToolsNow(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDServiceException {

		String result = RMDCommonConstants.FAILURE;
		try {
			result = objRunToolsBOIntf.runToolsNow(ObjToolRunRequestVO);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return result;

	}

/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the diagnostic services
	 */
	public List<ElementVO> getDiagnosticServices(String vehicleObjid)
			throws RMDServiceException {

		List<ElementVO> lstElementVO = new ArrayList<ElementVO>();
		try {
			lstElementVO = objRunToolsBOIntf
					.getDiagnosticServices(vehicleObjid);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return lstElementVO;
	}
}

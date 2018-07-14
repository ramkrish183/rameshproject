package com.ge.trans.eoa.services.tools.runtools.dao.intf;

/**
 * ============================================================
 * File : RunToolsDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.tools.runtools.dao.intf
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
import java.util.List;

import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolDiagnosticRunDetailsVO;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolRunRequestVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface RunToolsDAOIntf {

	/**
	 * @Author:
	 * @param:String nextScheduleRun
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the next schedule run
	 */
	public String getNextScheduleRun(String vehicleObjid)
			throws RMDDAOException;

	/**
	 * @Author:
	 * @param:String nextScheduleRun
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the next schedule run
	 */
	public ToolDiagnosticRunDetailsVO getLastDiagnosticRunDetails(
			String vehicleObjid) throws RMDDAOException;

	/**
	 * @Author:
	 * @param:String nextScheduleRun
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the next schedule run
	 */
	public String runTools(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDDAOException;

	/**
	 * @Author:
	 * @param:String nextScheduleRun
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the next schedule run
	 */
	public List<ElementVO> getDiagnosticServices(String vehicleObjid)
			throws RMDDAOException;

}

package com.ge.trans.eoa.services.tools.runtools.service.intf;
/**
 * ============================================================
 * File : RunToolsServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.tools.runtools.service.intf
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
import com.ge.trans.rmd.exception.RMDServiceException;

public interface RunToolsServiceIntf {

	/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the next schedule run
	 */
	public String getNextScheduleRun(String vehicleObjid)
			throws RMDServiceException;

	/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the last diagnostic run details
	 */
	public ToolDiagnosticRunDetailsVO getLastDiagnosticRunDetails(
			String vehicleObjid) throws RMDServiceException;

	/**
	 * @Author:
	 * @param:ToolRunRequestVO ObjToolRunRequestVO
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to run the tools
	 */
	public String runTools(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDServiceException;

	/**
	 * @Author:
	 * @param:ToolRunRequestVO ObjToolRunRequestVO
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to run the tools
	 */
	public String runToolsNow(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDServiceException;
				/**
	 * @Author:
	 * @param:String vehicleObjid
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to get the diagnostic services
	 */
	public List<ElementVO> getDiagnosticServices(String vehicleObjid)
			throws RMDServiceException;

}

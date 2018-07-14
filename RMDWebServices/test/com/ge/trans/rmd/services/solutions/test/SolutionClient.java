/**
 * ============================================================
 * Classification: GE Confidential
 * File : SolutionClient.java
 * Description :
 * Package : com.ge.trans.rmd.services.solutions.test
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 5, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.solutions.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionConfigType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionDetailType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionExecutionRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionFeedbackRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionHistoryType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.TaskDetailType;
import com.ge.trans.rmd.services.solutions.valueobjects.TaskDocType;
import com.ge.trans.rmd.common.constants.OMDConstants;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as  Testing to post uris
 * @History :
 ******************************************************************************/
public class SolutionClient {
	private JAXBContext jc;
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(SolutionClient.class);
	private static final String URL = "http://localhost:8080/RMDWebServices/solutionservice";

	public static void main(String[] args) {

		final SolutionClient objTestClient = new SolutionClient();
		try {
			objTestClient.addSolution();
		} catch (Exception excep) {
			LOG.error("Error in the Main() method of SolutionClient class"+excep);
		}
	}

	public SolutionClient() {
		try {
			jc = JAXBContext.newInstance("com.ge.trans.rmd.services.solutions.valueobjects");			
		} catch (JAXBException jexcep) {
			LOG.error("Cannot create JAXBContext " + jexcep);
		}
	}


	/**
	 * This is the sample method which is used to call the webservice
	 * @param
	 * @return
	 * @throws RMDServiceException
	 */
	public void addSolution() {
		SolutionDetailType objSolutionDetailType;
		TaskDetailType objTaskDetailType;
		TaskDetailType objTaskDetailType1;
		TaskDocType objTaskDocType;
		TaskDocType objTaskDocType1; 
		SolutionConfigType solutionConfigType; 
		List<TaskDocType> arlTaskDocTypes;
		try {
			String strUrl = this.URL + "/addSolution";
			final URL url = new URL(strUrl);
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			connection.setRequestProperty(OMDConstants.USERID, OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.DEFAULT_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, OMDConstants.DEFAULT_LANGUAGE);

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final SolutionRequestType objSolutionRequestType = new SolutionRequestType();
			arlTaskDocTypes = new ArrayList<TaskDocType>();
			objSolutionRequestType.setSolutionType("Custom");
			objSolutionRequestType.setAssetImpact("Intermittently drops load");

			objSolutionDetailType = new SolutionDetailType();
			objSolutionDetailType.setEstmTimeRepair("3");
			objSolutionDetailType.setSolutionStatus("Approved");
			objSolutionDetailType.setSolutionTitle("New Solution for Testing 19");
			objSolutionDetailType.setUrgRepair("R");
			objSolutionDetailType.setSolutionRevNo("");
			objSolutionDetailType.setRevisionComments("Created by omd dev");
			objSolutionDetailType.setLockedBy("OMD");
			objSolutionDetailType.setProcessType("new");
			objSolutionDetailType.setLocomotiveImpact("Intermittently drops load");
			objSolutionDetailType.getSolutionSubSystems().add("497|Electrical Integration");
			objSolutionDetailType.getSolutionSubSystems().add("507|Truck");
			objSolutionDetailType.getSolutionModel().add("268437203|ACCCA");
			objSolutionDetailType.getSolutionModel().add("1|CCA");
			objSolutionDetailType.setExportControl("7E994");
			objSolutionDetailType.setSolutionQueue("Work");

			objSolutionRequestType.setSolutionDetail(objSolutionDetailType);

			objTaskDetailType = new TaskDetailType();
			objTaskDetailType.setTaskID("1");
			objTaskDetailType.setLsl("3");
			objTaskDetailType.setUsl("9");
			objTaskDetailType.setTarget("6");
			objTaskDetailType.setSubTask("N");
			objTaskDetailType.setTaskDesc("first and best");
			//objTaskDetailType.setFeedback("testFeedback");
			objTaskDetailType.setMandatory("9");
			objTaskDetailType.setClosurePerc("6");

			objTaskDocType = new TaskDocType();

			objTaskDocType.setDelete("N");			
			objTaskDocType.setDocPath("http://testPath");
			objTaskDocType.setDocTitle("testTitle");
			arlTaskDocTypes.add(objTaskDocType);

			objTaskDocType1 = new TaskDocType();
			objTaskDocType1.setDelete("N1");			
			objTaskDocType1.setDocPath("http://testPath1");
			objTaskDocType1.setDocTitle("testTitle1");
			arlTaskDocTypes.add(objTaskDocType1);
			objTaskDetailType.setTaskDoc(arlTaskDocTypes);		

			solutionConfigType = new SolutionConfigType();

			solutionConfigType.setConfigFunction("< OR >");
			solutionConfigType.setConfigName("testConfig");
			solutionConfigType.setConfigValue1("2");
			solutionConfigType.setConfigValue2("7");

			objSolutionRequestType.getTaskDetail().add(objTaskDetailType);	
			objSolutionRequestType.getSolutionDetail().getSolutionConfig().add(solutionConfigType);

			objTaskDetailType1 = new TaskDetailType();
			objTaskDetailType1.setTaskID("1.1");
			objTaskDetailType1.setLsl("1");
			objTaskDetailType1.setUsl("4");
			objTaskDetailType1.setTarget("3");
			objTaskDetailType1.setSubTask("Y");
			objTaskDetailType1.setTaskDesc("first sub task");
			//objTaskDetailType1.setFeedback("testFeedback");
			objTaskDetailType1.setMandatory("9");
			objTaskDetailType1.setClosurePerc("6");

			objSolutionRequestType.getTaskDetail().add(objTaskDetailType1);			


			jc.createMarshaller().marshal(objSolutionRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();

		} catch (Exception excep) {
			LOG.error("Error at addSolution()of SolutionClient class "+excep);
		}
	}

	/**
	 * This is the sample method which is used to call the webservice
	 * @param
	 * @return
	 * @throws RMDServiceException
	 */
	public void updateSolution() {
		try {
			String strUrl = this.URL + "/updateSolution";
			final URL url = new URL(strUrl);
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			connection.setRequestProperty(OMDConstants.USERID, OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.CHINESE_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, OMDConstants.CHINESE_LANGUAGE);

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final SolutionRequestType objSolutionRequestType = new SolutionRequestType();
			objSolutionRequestType.setSolutionType("Standard");
			objSolutionRequestType.setAssetImpact("No performance impact");

			final SolutionDetailType objSolutionDetailType = new SolutionDetailType();
			objSolutionDetailType.setSolutionID("11941");
			objSolutionDetailType.setEstmTimeRepair("2");
			objSolutionDetailType.setSolutionStatus("Approved");
			objSolutionDetailType.setSolutionTitle("test Recomm inserted for WS new on aug 31");
			objSolutionDetailType.setUrgRepair("R");
			objSolutionDetailType.setProcessType("edit");
			objSolutionDetailType.setRevisionComments("updated the status for testing purpose");

			final TaskDetailType objTaskDetailType = new TaskDetailType();
			objTaskDetailType.setTaskID("1");
			objTaskDetailType.setSubTask("N");

			objSolutionRequestType.getTaskDetail().add(objTaskDetailType);
			objSolutionRequestType.setSolutionDetail(objSolutionDetailType);

			jc.createMarshaller().marshal(objSolutionRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error at updateSolution()of SolutionClient class "+excep);
		}
	}

	/**
	 * This is the sample method which is used to call the webservice
	 * @param
	 * @return
	 * @throws RMDServiceException
	 */
	public void addSolutionExecutionDetails() {

		List<String> recomTaskList;
		TaskDetailType objTaskDetailType;
		List<TaskDetailType> lstTaskDetailType;
		try {
			String strUrl = this.URL + "/saveSolutionExecutionDetails";
			URL url = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.CHINESE_LANGUAGE);
			OutputStream os = connection.getOutputStream();

			SolutionExecutionRequestType objSolutionExecutionRequestType = new SolutionExecutionRequestType();
			objSolutionExecutionRequestType.setCaseID("11-007404");
			objSolutionExecutionRequestType.setRepairAction("Testing on Aug 31");
			objSolutionExecutionRequestType.setSolutionCloseFlag("YES");
			objSolutionExecutionRequestType.setSolutionExecutionId("2162");

			recomTaskList = new ArrayList<String>();
			recomTaskList.add("68882");
			recomTaskList.add("68883");

			objSolutionExecutionRequestType.setTaskListID(recomTaskList);

			lstTaskDetailType = new ArrayList<TaskDetailType>();

			objTaskDetailType = new TaskDetailType();
			objTaskDetailType.setRecomTaskId("68882");
			objTaskDetailType.setTaskID("1");
			objTaskDetailType.setLsl("");
			objTaskDetailType.setTaskDesc("");
			objTaskDetailType.setCheckedFlag("true");
			objTaskDetailType.setUsl("");
			lstTaskDetailType.add(objTaskDetailType);

			objTaskDetailType = new TaskDetailType();
			objTaskDetailType.setRecomTaskId("68883");
			objTaskDetailType.setTaskID("2");
			objTaskDetailType.setLsl("");
			objTaskDetailType.setTaskDesc("");
			objTaskDetailType.setCheckedFlag("true");
			objTaskDetailType.setUsl("");
			lstTaskDetailType.add(objTaskDetailType);

			objTaskDetailType = new TaskDetailType();
			objTaskDetailType.setRecomTaskId("68884");
			objTaskDetailType.setTaskID("3");
			objTaskDetailType.setLsl("");
			objTaskDetailType.setTaskDesc("");
			objTaskDetailType.setCheckedFlag("false");
			objTaskDetailType.setUsl("");
			lstTaskDetailType.add(objTaskDetailType);

			objSolutionExecutionRequestType.setTaskDetail(lstTaskDetailType);

			jc.createMarshaller().marshal(objSolutionExecutionRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();

		} catch (Exception excep) {
			LOG.error("Error at addSolutionExecutionDetails()of SolutionClient class "+excep);
		}
	}

	/**
	 * This is the sample method which is used to call the webservice
	 * @param
	 * @return
	 * @throws RMDServiceException
	 */
	public void saveSolutionFeedback() {
		try {
			URL url = new URL("http://localhost:8080/RMDWebServices/solutionservice/saveSolutionFeedback");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.CHINESE_LANGUAGE);
			OutputStream os = connection.getOutputStream();

			SolutionFeedbackRequestType objSolutionFbkRequestType = new SolutionFeedbackRequestType();
			objSolutionFbkRequestType.setCaseID("11-006765");
			objSolutionFbkRequestType.setSolutionFeedback("Closed by Web Service");
			objSolutionFbkRequestType.setSolutionSaveMissCode("Miss 4");

			jc.createMarshaller().marshal(objSolutionFbkRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error at saveSolutionFeedback()of SolutionClient class "+excep);
		}
	}
	/**
	 * This is the sample method which is used to call the webservice
	 * @param
	 * @return
	 * @throws RMDServiceException
	 */
	public void updateSolutionStatus(){
		SolutionResponseType objResponseType=new SolutionResponseType();
		SolutionRequestType objSolutionRequestType=new SolutionRequestType();
		SolutionDetailType objSolutionDetailType=new SolutionDetailType();
		SolutionHistoryType objSolutionHistoryType= new SolutionHistoryType(); 
		try {
			objSolutionDetailType.setSolutionID("176931");			
			objSolutionDetailType.setSolutionStatus("Draft");
			objSolutionRequestType.setSolutionDetail(objSolutionDetailType);
			//objSolutionRequestType.setSolutionHistory(objSolutionHistoryType);
			String strUrl = this.URL + "/updateSolutionStatus";
			URL url = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.DEFAULT_LANGUAGE);
			OutputStream os = connection.getOutputStream();

			jc.createMarshaller().marshal(objSolutionRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception e) {
		}
	}
}

/**
 * ============================================================
 * Classification: GE Confidential
 * File : NoteClient.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.test
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.cases.test;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.services.notes.valueobjects.NotesInfo;
import com.ge.trans.rmd.services.notes.valueobjects.NotesRequestType;

/*******************************************************************************
 *
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as NoteClient Webservices and used to test
 *              the PUT,POST,DELETE HttpRequest in Notesresource.java
 * @History :
 *
 ******************************************************************************/
public class NoteClient {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(NoteClient.class);

	public static void main(final String[] args) {
		final NoteClient objNoteClient=new NoteClient();
		try {
			objNoteClient.deleteNoteTest();
		}
		catch (Exception excep) {
			LOG.error("Error occured in NoteClient - main method."+excep);
		}
	}

	private void addNotesClient() throws JAXBException, IOException{
		try{
			JAXBContext jc;
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.notes.valueobjects");
			final	URL url = new URL("http://localhost:8080/omdservices/notesservice/addNotes");
			final	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/xml");
			//Start--Setting the header values
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			//End--Setting the header values
			final	OutputStream os = connection.getOutputStream();
			final	NotesRequestType objNotesRequestType=new NotesRequestType();
			final	NotesInfo objNotesInfo=new NotesInfo();

			//from Menu
			objNotesRequestType.setApplyLevel("Unit");
			objNotesRequestType.setStickyExist(false);
			objNotesRequestType.setAssetStickyExist(false);
			objNotesInfo.setCaseID("");
			objNotesRequestType.setEmail("");
			objNotesRequestType.setFromAsset("61");
			objNotesRequestType.setToAsset("61");
			objNotesRequestType.setNotes("Notes Testing");
			objNotesRequestType.setSticky("No");
			objNotesRequestType.setNoteType("menu");
			objNotesInfo.setAssetHeader("");
			objNotesRequestType.setSave("YES");
			objNotesRequestType.setNotesInfo(objNotesInfo);

			//From inside Case

			jc.createMarshaller().marshal(objNotesRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		}catch (Exception excep) {
			LOG.error("Error occured in NoteClient - addNotesClient method."+excep);
		}
	}

	/**
	 * This method will edit notes from the based on the input given
	 */
	public void editNotesTest()throws JAXBException, IOException{
		try{
			JAXBContext jc;
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.notes.valueobjects");
			final	URL url = new URL("http://localhost:8080/omdservices/notesservice/editNotes");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/xml");
			//Start--Setting the header values
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			//End--Setting the header values

			final OutputStream os = connection.getOutputStream();
			final NotesRequestType objNotesRequestType=new NotesRequestType();
			final NotesInfo objNotesInfo=new NotesInfo();
			//Edit Notes
			objNotesRequestType.setApplyLevel("Unit");
			objNotesRequestType.setStickyExist(true);
			objNotesRequestType.setAssetStickyExist(false);
			objNotesInfo.setCaseID("11-007224");
			objNotesRequestType.setEmail("");
			objNotesRequestType.setFromAsset("61");
			objNotesRequestType.setToAsset("61");
			objNotesRequestType.setNotes("rewrites NOtes Testing for Webservices here");
			objNotesRequestType.setSticky("Yes");
			objNotesRequestType.setNoteType("menu");
			objNotesInfo.setAssetHeader("QTR");
			objNotesInfo.setCreatedBy("admin");
			objNotesRequestType.setSave("NO");
			objNotesRequestType.setExistMsg("Stickymessage");
			objNotesRequestType.setExistNotes("Notes Testing");

			objNotesRequestType.setStatus("");
			objNotesRequestType.setNotesInfo(objNotesInfo);
			jc.createMarshaller().marshal(objNotesRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		}
		catch(Exception excep){
			LOG.error("Error occured in NoteClient - editNotesTest method."+excep);
		}
	}

	/**
	 * This method will delete notes based on the input given
	 */
	public void deleteNoteTest()throws JAXBException, IOException{
		JAXBContext jc;
		try{
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.notes.valueobjects");
			final URL url = new URL("http://localhost:8080/omdservices/notesservice/deletenote");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");
			//Start--Setting the header values
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			//End--Setting the header values
			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final NotesRequestType objNotesRequestType=new NotesRequestType();
			final NotesInfo objNotesInfo=new NotesInfo();
			//Edit Notes
			objNotesRequestType.setApplyLevel("Unit");
			objNotesRequestType.setStickyExist(false);
			objNotesRequestType.setAssetStickyExist(false);
			objNotesInfo.setCaseID("11-007224");
			objNotesRequestType.setEmail("");
			objNotesRequestType.setFromAsset("61");
			objNotesRequestType.setToAsset("61");
			objNotesRequestType.setNotes("rewrites NOtes Testing sfsdfds ");
			objNotesRequestType.setSticky("NO");
			objNotesRequestType.setNoteType("menu");
			objNotesInfo.setAssetHeader("QTR");
			objNotesInfo.setCreatedBy("admin");
			objNotesRequestType.setSave("YES");
			objNotesRequestType.setStatus("");
			objNotesRequestType.setNotesInfo(objNotesInfo);
			jc.createMarshaller().marshal(objNotesRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		}
		catch (Exception excep) {
			LOG.error("Error occured in NoteClient - deleteNoteTest method."+excep);
		}
	}
}

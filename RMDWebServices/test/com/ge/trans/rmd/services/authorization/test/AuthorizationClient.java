/**
 * ============================================================
 * Classification: GE Confidential
 * File : AuthorizationClient.java
 * Description :
 * Package : com.ge.trans.rmd.services.authorization.test
 * Author : iGATE-Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Aug 3 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.authorization.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.authorization.valueobjects.RolePrivilegesType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolesDetailsType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolesRequestType;

/***********************************************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ****************************************************************************************************************/
public class AuthorizationClient {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(AuthorizationClient.class);

	private JAXBContext jc;

	public static void main(final String[] args) {

		final AuthorizationClient objAdminClient = new AuthorizationClient();
		try {
			objAdminClient.saveRoleDetails();
		} catch (Exception excep) {
			LOG.error("Error occured in AuthorizationClient- main method"+excep);
		}
	}

	public AuthorizationClient() {
		try {
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.authorization.valueobjects");
		} catch (JAXBException jexcep) {
			LOG.error("Cannot create JAXBContext " + jexcep);
		}
	}

	public void saveRoleDetails() {
		try {
			final URL url = new URL("http://localhost:8080/RMDService/authorizationservice/saveRoleDetails");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final RolesRequestType  objRequestType = new RolesRequestType();

			final RolesDetailsType objRolesDetailsType = new RolesDetailsType();
			objRolesDetailsType.setRoleDescription("This is the test role");
			objRolesDetailsType.setRoleName("TESTROLE");
			objRolesDetailsType.setStatus(1);
			objRequestType.setRolesDetails(objRolesDetailsType);

			final RolePrivilegesType objRolePrivilegesType = new RolePrivilegesType();
			objRolePrivilegesType.setAccessLevel("Show");
			objRolePrivilegesType.setResourceType("page");
			objRolePrivilegesType.setRolePrivilege("CASE_DETAILS");
			objRequestType.setRolePrivileges(objRolePrivilegesType);


			jc.createMarshaller().marshal(objRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in AuthorizationClient- saveRoleDetails method"+excep);
		}
	}

}

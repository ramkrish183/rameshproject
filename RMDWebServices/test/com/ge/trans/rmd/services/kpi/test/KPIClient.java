package com.ge.trans.rmd.services.kpi.test;

import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

import com.ge.trans.rmd.services.cases.test.CasesClient;

public class KPIClient {
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(KPIClient.class);

	private String strURL="http://localhost:8080/omdservices/kpiservice";

	public static void main(final String[] args) {
		final KPIClient objKPIClient=new KPIClient();
		
	}

}

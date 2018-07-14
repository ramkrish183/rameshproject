package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.SoftwareHistoryBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.SoftwareHistoryServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.SoftwareHistoryRequestVO;
import com.ge.trans.rmd.common.valueobjects.SoftwareHistoryVO;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class SoftwareHistoryServiceImpl implements SoftwareHistoryServiceIntf {
	
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(SoftwareHistoryServiceImpl.class);
	SoftwareHistoryBOIntf softwareHistoryBOIntf;
	SoftwareHistoryServiceImpl(SoftwareHistoryBOIntf softwareHistoryBOIntf){
		this.softwareHistoryBOIntf = softwareHistoryBOIntf;
	}
	

	@Override
	public List<SoftwareHistoryVO> getSoftwareHistory(SoftwareHistoryRequestVO historyRequestVO) {
		// TODO Auto-generated method stub
		LOG.info("getSoftwareHistory Service is invoked");
		return	softwareHistoryBOIntf.getSoftwareHistory(historyRequestVO);
	}

}

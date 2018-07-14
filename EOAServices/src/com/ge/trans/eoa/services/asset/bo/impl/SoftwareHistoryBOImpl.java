package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.SoftwareHistoryBOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.SoftwareHistoryRequestVO;
import com.ge.trans.eoa.services.reports.bo.impl.OHVReportsBOImpl;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.SoftwareHistoryDAOIntf;
import com.ge.trans.rmd.common.valueobjects.SoftwareHistoryVO;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class SoftwareHistoryBOImpl implements SoftwareHistoryBOIntf{

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(OHVReportsBOImpl.class);
	SoftwareHistoryDAOIntf softwareHistoryDAOIntf;
	SoftwareHistoryBOImpl(SoftwareHistoryDAOIntf softwareHistoryDAOIntf){
		this.softwareHistoryDAOIntf = softwareHistoryDAOIntf;
	}
	@Override
	public List<SoftwareHistoryVO> getSoftwareHistory(SoftwareHistoryRequestVO historyRequestVO) {
		// TODO Auto-generated method stub
		LOG.info("getSoftwareHistory BO is invoked");
		return softwareHistoryDAOIntf.getSoftwareHistory(historyRequestVO);
		
	}

}

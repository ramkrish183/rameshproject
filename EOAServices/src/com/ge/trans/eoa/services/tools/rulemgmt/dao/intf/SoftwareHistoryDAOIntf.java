package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.SoftwareHistoryRequestVO;
import com.ge.trans.rmd.common.valueobjects.SoftwareHistoryVO;

public interface SoftwareHistoryDAOIntf {
	
	List<SoftwareHistoryVO> getSoftwareHistory(SoftwareHistoryRequestVO historyRequestVO);

}

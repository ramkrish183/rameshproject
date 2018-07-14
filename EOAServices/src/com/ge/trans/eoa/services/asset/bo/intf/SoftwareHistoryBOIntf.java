package com.ge.trans.eoa.services.asset.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.SoftwareHistoryRequestVO;
import com.ge.trans.rmd.common.valueobjects.SoftwareHistoryVO;

public interface SoftwareHistoryBOIntf {
	
	List<SoftwareHistoryVO> getSoftwareHistory(SoftwareHistoryRequestVO historyRequestVO);

}

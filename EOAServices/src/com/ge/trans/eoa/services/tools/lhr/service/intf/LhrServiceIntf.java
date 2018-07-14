package com.ge.trans.eoa.services.tools.lhr.service.intf;


import java.util.List;

import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRuleFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LhrResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesCommResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesResponseVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthScoreRequestVO;

public interface LhrServiceIntf {

	public List<HealthRuleFiringVO> getHealthRulesData(String lmsLocoId, List<Long> ruleList)
			throws RMDServiceException;

	public List<HealthRulesVO> getHealthRules() throws RMDServiceException;

	public List<LocomotivesResponseVO> getHealthFiringDetails(List<LocomotivesRequestVO> locoList,String ruleType)
			throws RMDServiceException;

	public LhrResponseVO getVehicleHealthData(List<HealthScoreRequestVO> assetList) throws RMDServiceException;
	
	public List<LocomotivesCommResponseVO> getLocoCommunicationDetails(List<Long> locoList) throws RMDServiceException;

}
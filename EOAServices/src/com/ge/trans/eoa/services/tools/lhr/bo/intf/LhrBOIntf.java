package com.ge.trans.eoa.services.tools.lhr.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRuleFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LhrResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesCommResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesResponseVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthScoreRequestVO;

public interface LhrBOIntf {

	public List<HealthRuleFiringVO> getHealthRulesData(String lmsLocoId, List<Long> ruleList) throws RMDBOException;

	public List<HealthRulesVO> getHealthRules() throws RMDBOException;

	public List<LocomotivesResponseVO> getHealthFiringDetails(List<LocomotivesRequestVO> locoList,String ruleType)
			throws RMDBOException;

	public LhrResponseVO getVehicleHealthData(List<HealthScoreRequestVO> assetList) throws RMDBOException;
	
	public List<LocomotivesCommResponseVO> getLocoCommunicationDetails(List<Long> locoList) throws RMDBOException;
}
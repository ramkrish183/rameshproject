package com.ge.trans.eoa.services.tools.lhr.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRuleFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LhrResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesCommResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesResponseVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthScoreRequestVO;

public interface LhrDAOIntf {

	public List<HealthRuleFiringVO> getHealthRulesData(String lmsLocoId, List<Long> ruleList) throws RMDDAOException;

	public List<HealthRulesVO> getHealthRules() throws RMDDAOException;

	public List<LocomotivesResponseVO> getHealthFiringDetails(List<LocomotivesRequestVO> locoList, String ruleType)
			throws RMDDAOException;

	public LhrResponseVO getVehicleHealthData(List<HealthScoreRequestVO> assetList) throws RMDDAOException;
	
	public List<LocomotivesCommResponseVO> getLocoCommunicationDetails(List<Long> locoList) throws RMDDAOException;

}

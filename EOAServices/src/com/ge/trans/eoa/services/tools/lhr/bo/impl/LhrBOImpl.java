package com.ge.trans.eoa.services.tools.lhr.bo.impl;

import java.util.List;
import com.ge.trans.eoa.services.tools.lhr.bo.intf.LhrBOIntf;
import com.ge.trans.eoa.services.tools.lhr.dao.intf.LhrDAOIntf;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRuleFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LhrResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesCommResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesResponseVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthScoreRequestVO;

public class LhrBOImpl implements LhrBOIntf { 
	 
	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(LhrBOImpl.class);

	private LhrDAOIntf lhrDAOIntf;
	
	public LhrBOImpl(LhrDAOIntf lhrDAOIntf) {
		super();
		this.lhrDAOIntf = lhrDAOIntf;
	}

	@Override
	public List<HealthRuleFiringVO> getHealthRulesData(String lmsLocoId,
			List<Long> ruleList) throws RMDBOException {
		List<HealthRuleFiringVO> healthRuleFiringVO = null;
		try {
			healthRuleFiringVO = lhrDAOIntf.getHealthRulesData(lmsLocoId, ruleList);

		} catch (RMDDAOException e) {
			throw e;	
		}
		return healthRuleFiringVO;
	}
	@Override
	public List<HealthRulesVO> getHealthRules() throws RMDBOException {
		List<HealthRulesVO> healthRulesVO = null;
		try {
			healthRulesVO = lhrDAOIntf.getHealthRules();

		} catch (RMDDAOException e) {
			throw e;	
		}
		return healthRulesVO;
	}
	@Override
	public List<LocomotivesResponseVO> getHealthFiringDetails(List<LocomotivesRequestVO> locoList, String ruleType)
			throws RMDBOException {

		List<LocomotivesResponseVO> healthRuleFiringVO = null;
		try {
			healthRuleFiringVO = lhrDAOIntf.getHealthFiringDetails(locoList, ruleType);
		} catch (RMDDAOException e) {
			throw e;
		}
		return healthRuleFiringVO;
	} 
	@Override
	public LhrResponseVO getVehicleHealthData(
			List<HealthScoreRequestVO> assetList) throws RMDBOException {
		LhrResponseVO lhrResponseVO = null;
		try {
			lhrResponseVO = lhrDAOIntf.getVehicleHealthData(assetList);

		} catch (RMDDAOException e) {
			throw e;
		}
		return lhrResponseVO;
	}

	@Override
	public List<LocomotivesCommResponseVO> getLocoCommunicationDetails(
			List<Long> locoList) throws RMDBOException {
		List<LocomotivesCommResponseVO> locoCommResponseVO = null;
		try {
			locoCommResponseVO = lhrDAOIntf.getLocoCommunicationDetails(locoList);
		} catch (RMDDAOException e) {
			throw e;
		}
		return locoCommResponseVO;
	}
}
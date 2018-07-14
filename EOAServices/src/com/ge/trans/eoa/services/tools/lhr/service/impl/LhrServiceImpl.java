package com.ge.trans.eoa.services.tools.lhr.service.impl;

import java.io.Serializable;
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.tools.lhr.bo.intf.LhrBOIntf;
import com.ge.trans.eoa.services.tools.lhr.service.intf.LhrServiceIntf;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRuleFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LhrResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesCommResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesResponseVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthScoreRequestVO;

public class LhrServiceImpl implements Serializable, LhrServiceIntf { 
	  
	private static final long serialVersionUID = 1455L;
	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(LhrServiceImpl.class);
	private LhrBOIntf lhrBOIF;

	public LhrServiceImpl(final LhrBOIntf lhrBOIF) {
		this.lhrBOIF = lhrBOIF;
	}
	
	@Override
	public List<HealthRuleFiringVO> getHealthRulesData(String lmsLocoId, List<Long> ruleList)
			throws RMDServiceException {
		List<HealthRuleFiringVO> healthRuleFiringVO = null;
		 
		try {
			healthRuleFiringVO = lhrBOIF.getHealthRulesData(lmsLocoId, ruleList);

		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			ex.printStackTrace();
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					RMDCommonConstants.ENGLISH_LANGUAGE);
		}
		return healthRuleFiringVO;
	}
	@Override
	public List<HealthRulesVO> getHealthRules() throws RMDServiceException {
		List<HealthRulesVO> healthRulesVO = null;
		 
		try {
			healthRulesVO = lhrBOIF.getHealthRules();

		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			ex.printStackTrace();
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					RMDCommonConstants.ENGLISH_LANGUAGE);
		}
		return healthRulesVO;
	}
	@Override
	public List<LocomotivesResponseVO> getHealthFiringDetails(List<LocomotivesRequestVO> locoList,String ruleType)
			throws RMDServiceException {
		List<LocomotivesResponseVO> healthRuleFiringVO = null;
		 
		try {
			healthRuleFiringVO = lhrBOIF.getHealthFiringDetails(locoList,ruleType);

		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					RMDCommonConstants.ENGLISH_LANGUAGE);
		}
		return healthRuleFiringVO;
	}
	@Override
	public LhrResponseVO getVehicleHealthData(
			List<HealthScoreRequestVO> assetList) throws RMDServiceException {
		LhrResponseVO lhrResponseVO = null;
		try {
			lhrResponseVO = lhrBOIF
					.getVehicleHealthData(assetList);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			ex.printStackTrace();
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					RMDCommonConstants.ENGLISH_LANGUAGE);
		}
		return lhrResponseVO;
	}

	@Override
	public List<LocomotivesCommResponseVO> getLocoCommunicationDetails(
			List<Long> locoList) throws RMDServiceException {
		List<LocomotivesCommResponseVO> locoCommResponseVO = null;
		try {
			locoCommResponseVO = lhrBOIF
					.getLocoCommunicationDetails(locoList);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			ex.printStackTrace();
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					RMDCommonConstants.ENGLISH_LANGUAGE);
		}
		return locoCommResponseVO;
	}
}
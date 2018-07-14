package com.ge.trans.eoa.services.rxchange.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.rxchange.bo.intf.RxChangeBOIntf;
import com.ge.trans.eoa.services.rxchange.service.intf.RxChangeServiceIntf;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeAdminVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeSearchVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class RxChangeServiceImpl implements RxChangeServiceIntf {

	private static final RMDLogger rxChangeServiceLog = RMDLoggerHelper
            .getLogger(RxChangeServiceImpl.class);
    
	
    private RxChangeBOIntf objRxChangeBOIntf;

    
    public RxChangeServiceImpl(RxChangeBOIntf objRxChangeBOIntf) {
        this.objRxChangeBOIntf = objRxChangeBOIntf;
    }

    @Override
    public List<RxChangeVO> getRxChangeOverviewData(RxChangeSearchVO rxChangeSearchVO)
            throws RMDServiceException {
    	List<RxChangeVO> response = null;
        try {
            response = objRxChangeBOIntf
                    .getRxChangeOverviewData(rxChangeSearchVO);
        }catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return response;
    }
    @Override
    public boolean getUserCases(String userId, String caseId)
    throws RMDServiceException {
        boolean result = RMDCommonConstants.FALSE;
        try {
            result = objRxChangeBOIntf.getUserCases(userId, caseId);
        }catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }
    
    @Override
    public Map<String, String> getRxTitles(String strTitle, String strObjIdLst)
    throws RMDServiceException {
        Map<String, String> rxTitleMap = new HashMap<String, String>();
        try {
            rxTitleMap = objRxChangeBOIntf.getRxTitles(strTitle, strObjIdLst);
        }catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxTitleMap;
    }
    
    @Override
    public Map<String, String> getOmdUsers(String fName, String sName, String sso) throws RMDServiceException
    {
        Map<String, String> userMap = new HashMap<String, String>();
        try {
            userMap = objRxChangeBOIntf.getOmdUsers(fName, sName, sso);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return userMap;   
    }
    
    @Override
    public String saveRxChangeDetails(RxChangeVO rxChangeVO) throws RMDServiceException{
            String result = null;
            try {
                result = objRxChangeBOIntf.saveRxChangeDetails(rxChangeVO);
            } catch (RMDDAOException ex) {
                throw new RMDServiceException(ex.getErrorDetail(), ex);
            } catch (RMDBOException ex) {
                throw new RMDServiceException(ex.getErrorDetail(), ex);
            } catch (Exception ex) {
                RMDServiceErrorHandler.handleGeneralException(ex, rxChangeVO.getStrLanguage());
            }
            return result;
        }

	@Override
	public List<ModelVO> getModelForRxTitle(RxChangeSearchVO rxChangeSearchVO)
			throws RMDServiceException {
		List<ModelVO> response = null;
        try {
            response = objRxChangeBOIntf
                    .getModelForRxTitle(rxChangeSearchVO);
        }catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return response;
	}

	@Override
	public String saveUpdateRxChangeAdminDetails(RxChangeAdminVO rxChangeAdminVO)
			throws RMDServiceException {
		String response = null;
        try {
            response = objRxChangeBOIntf
                    .saveUpdateRxChangeAdminDetails(rxChangeAdminVO);
        }catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return response;
	}

	@Override
	public RxChangeAdminVO getRxChangeAdminData(RxChangeSearchVO rxChangeSearchVO)
			throws RMDServiceException {
		RxChangeAdminVO response = null;
        try {
            response = objRxChangeBOIntf
                    .getRxChangeAdminData(rxChangeSearchVO);
        }catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return response;
	}
	
	@Override
	public List<UserServiceVO> getRxChangeAdminUsers() throws RMDServiceException
    {
	    List<UserServiceVO> userLst = new ArrayList<UserServiceVO>();
        try {
            userLst = objRxChangeBOIntf.getRxChangeAdminUsers();
        } catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return userLst;   
    }
	
	@Override
    public List<RxChangeVO> getRxChangeAuditTrailInfo(String rxChngObjid)
            throws RMDServiceException {
        List<RxChangeVO> rxChangeVOLst = new ArrayList<RxChangeVO>();
        try {
            rxChangeVOLst = objRxChangeBOIntf.getRxChangeAuditTrailInfo(rxChngObjid);
        } catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxChangeVOLst;   
    }
	
	public void sendRxChangeEscalation(String escalationData)
            throws RMDServiceException {
        
        try {
           objRxChangeBOIntf.sendRxChangeEscalation(escalationData);
        } catch (RMDBOException ex) {
            rxChangeServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            rxChangeServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }  
    }
}

package com.ge.trans.eoa.services.rxchange.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.rxchange.bo.intf.RxChangeBOIntf;
import com.ge.trans.eoa.services.rxchange.dao.intf.RxChangeDAOIntf;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeAdminVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeSearchVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/**
 * @author 212338353
 *
 */
public class RxChangeBOImpl implements RxChangeBOIntf {
    
    private RxChangeDAOIntf objRxChangeDAOIntf;
    
    public static final RMDLogger rxChangeBoLog = RMDLoggerHelper
            .getLogger(RxChangeBOImpl.class);

    /**
     * @param objRxChangeDAOIntf
     */
    public RxChangeBOImpl(final RxChangeDAOIntf objRxChangeDAOIntf) {
        super();
        this.objRxChangeDAOIntf = objRxChangeDAOIntf;
    }
    @Override
    public List<RxChangeVO> getRxChangeOverviewData(RxChangeSearchVO rxChangeSearchVO)
            throws RMDBOException {
    	List<RxChangeVO> result = null;
        try {
            result = objRxChangeDAOIntf
                    .getRxChangeOverviewData(rxChangeSearchVO);
        } catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
    }
    
    @Override
    public boolean getUserCases(String userId, String caseId)
    throws RMDBOException {
        boolean result ;
        
        try {
            result = objRxChangeDAOIntf.getUserCases(userId, caseId);
        }catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
    }

    @Override
    public Map<String, String> getOmdUsers(String fName, String sName, String sso) throws RMDBOException
    {
        Map<String, String> userMap = new HashMap<String, String>();
        try {
            userMap = objRxChangeDAOIntf.getOmdUsers(fName, sName, sso);
        }catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return userMap;   
    }

    
    @Override
    public Map<String, String> getRxTitles(String strTitle, String strObjIdLst)
    throws RMDBOException {
        Map<String, String> rxTitleMap = new HashMap<String, String>();
        try {
            rxTitleMap = objRxChangeDAOIntf.getRxTitles(strTitle, strObjIdLst);
        }catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return rxTitleMap;
    }
    
    @Override
    public String saveRxChangeDetails(RxChangeVO rxChangeVO) throws RMDBOException {
        try {

            return objRxChangeDAOIntf.saveRxChangeInfo(rxChangeVO);            

        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            String errorCode= RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_USER_MANAGEMENT);

            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, rxChangeVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }
	@Override
	public List<ModelVO> getModelForRxTitle(RxChangeSearchVO rxChangeSearchVO)
			throws RMDBOException {
		List<ModelVO> result = null;
        try {
            result = objRxChangeDAOIntf
                    .getModelForRxTitle(rxChangeSearchVO);
        } catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
	}
	@Override
	public String saveUpdateRxChangeAdminDetails(RxChangeAdminVO rxChangeAdminVO)
			throws RMDBOException {
		String result = null;
        try {
            result = objRxChangeDAOIntf
                    .saveUpdateRxChangeAdminDetails(rxChangeAdminVO);
        } catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
	}
	@Override
	public RxChangeAdminVO getRxChangeAdminData(
			RxChangeSearchVO rxChangeSearchVO) throws RMDBOException {
		RxChangeAdminVO result = null;
        try {
            result = objRxChangeDAOIntf
                    .getRxChangeAdminData(rxChangeSearchVO);
        } catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
	}
	
	@Override
    public List<UserServiceVO> getRxChangeAdminUsers() throws RMDBOException
    {
        List<UserServiceVO> userLst = new ArrayList<UserServiceVO>();
        try {
            userLst = objRxChangeDAOIntf.getRxChangeAdminUsers();
        } catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return userLst;   
    }
	
	@Override
	public List<RxChangeVO> getRxChangeAuditTrailInfo(String rxChngObjid)
            throws RMDBOException {
	    List<RxChangeVO> rxChangeVOLst = new ArrayList<RxChangeVO>();
        try {
            rxChangeVOLst = objRxChangeDAOIntf.getRxChangeAuditTrailInfo(rxChngObjid);
        } catch (RMDDAOException e) {
            rxChangeBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return rxChangeVOLst;   
    }
	
	@Override
    public void sendRxChangeEscalation(String escalationData)
            throws RMDBOException {
        
            try {
                 objRxChangeDAOIntf.sendRxChangeEscalation(escalationData);
            } catch (RMDDAOException e) {
                rxChangeBoLog.error(e, e);
                throw new RMDBOException(e.getErrorDetail(), e);
            }               
        }
}
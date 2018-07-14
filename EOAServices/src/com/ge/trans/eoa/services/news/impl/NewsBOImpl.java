package com.ge.trans.eoa.services.news.impl;


import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class NewsBOImpl implements NewsBOIntf {
	private NewsDAOIntf objNewsDAOIntf;
	
	public NewsBOImpl(final NewsDAOIntf objNewsDAOIntf) {
        super();
        this.objNewsDAOIntf = objNewsDAOIntf;
    }
	public static final RMDLogger newsBoLog = RMDLoggerHelper
            .getLogger(NewsBOImpl.class);
	@Override
	public String saveNews(SaveNewsVO saveNewsVO) throws RMDBOException {
		String response = null;
		try{
			response = objNewsDAOIntf.saveNews(saveNewsVO);
		}
		catch (RMDDAOException e) {
			newsBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
		
		return response;
	}
	@Override
	public List<AllNewsVO> getAllNews(String userId,String isAdminPage,String customerId) throws RMDBOException {
		
		try{
			return objNewsDAOIntf.getAllNews(userId,isAdminPage,customerId);
		}
		catch (RMDDAOException e) {
			newsBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
		
		
	}
	@Override
	public String deleteNews(String objId) throws RMDServiceException {
		String response = null;
		try {
		 	
	response = objNewsDAOIntf.deleteNews(objId);
        }catch (Exception ex) {
        	newsBoLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
		return response;
	}
	@Override
	public String getBackgroundImage(String customerId) throws RMDBOException {
		try{
			return objNewsDAOIntf.getBackgroundImage(customerId);
		}
		catch (RMDDAOException e) {
			newsBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
	}
	@Override
	public String updateReadFlag(String objId) throws RMDServiceException {
		String response = null;
		try {
		 	
	response = objNewsDAOIntf.updateNewsFlag(objId);
        }catch (Exception ex) {
        	newsBoLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
		return response;
	}

}

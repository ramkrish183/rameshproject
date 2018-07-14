package com.ge.trans.eoa.services.news.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class NewsServiceImpl implements NewsServiceIntf {

	private static final RMDLogger newsServiceLog = RMDLoggerHelper
            .getLogger(NewsServiceImpl.class);
    
    private NewsBOIntf objNewsBOIntf;

    public NewsServiceImpl(NewsBOIntf objNewsBOIntf) {
        this.objNewsBOIntf = objNewsBOIntf;
    }
    
	@Override
	public String saveNews(SaveNewsVO saveNewsVO) throws RMDServiceException {
		String response = null;
		 try {
			 	
			 	response = objNewsBOIntf
	                    .saveNews(saveNewsVO);
	        }catch (RMDBOException ex) {
	        	newsServiceLog.error(ex, ex);
	            throw new RMDServiceException(ex.getErrorDetail(), ex);
	        } catch (Exception ex) {
	        	newsServiceLog.error(ex, ex);
	            RMDServiceErrorHandler.handleGeneralException(ex,
	                    RMDCommonConstants.ENGLISH_LANGUAGE);
	        }
	      
		return response;
	}

	@Override
	public List<AllNewsVO> getAllNews(String userId,String isAdminPage,String customerId) throws RMDServiceException {
		
		List<AllNewsVO> response = new ArrayList<AllNewsVO>();
		try {
		 	
		 	response = objNewsBOIntf
                    .getAllNews(userId,isAdminPage,customerId);
        }catch (RMDBOException ex) {
        	newsServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
        	newsServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
      
		return response;
	}

	@Override
	public String deleteNews(String objId) throws RMDServiceException {
		String response = null;
		
try {
		 	
	response=objNewsBOIntf.deleteNews(objId);
        }catch (Exception ex) {
        	newsServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
		return response;
	}

	@Override
	public String getBackgroundImage(String customerId)
			throws RMDServiceException {
		String response = null;
		try {
		 	
		 	response = objNewsBOIntf
                    .getBackgroundImage(customerId);
        }catch (RMDBOException ex) {
        	newsServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
        	newsServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
      
		return response;
	}
	@Override
	public String updateReadFlag(String objId) throws RMDServiceException {	
		String response = null;
		try {
			response = objNewsBOIntf.updateReadFlag(objId);
        }catch (Exception ex) {
        	newsServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
		return response;
	}

}

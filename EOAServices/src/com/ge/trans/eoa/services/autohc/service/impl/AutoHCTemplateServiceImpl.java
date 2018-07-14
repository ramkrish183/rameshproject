package com.ge.trans.eoa.services.autohc.service.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.autohc.bo.intf.AutoHCTemplateBoIntf;
import com.ge.trans.eoa.services.autohc.service.intf.AutoHCTemplateServiceIntf;
import com.ge.trans.eoa.services.autohc.service.valueobjects.AutoHCTemplateSaveVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;


public class AutoHCTemplateServiceImpl implements AutoHCTemplateServiceIntf {

	
	AutoHCTemplateBoIntf autoHCTemplateBOIntf;
	AutoHCTemplateServiceImpl(AutoHCTemplateBoIntf autoHCTemplateBOIntf){
		this.autoHCTemplateBOIntf = autoHCTemplateBOIntf;
	}
	
	@Override
	public List<String> getTemplateDropDwn(String ctrlConfig, String configFile)  throws RMDServiceException{
		List<String> dataInfo = null;
		try{
			dataInfo = autoHCTemplateBOIntf.getTemplateDropDwn(ctrlConfig, configFile);
		}
		catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "en");
        }
		return dataInfo;
	}
	
	@Override
	public List<String> getFaultCodeRecTypeDropDwn(String ctrlConfig, String configFile)  throws RMDServiceException{
		List<String> dataInfo = null;
		try{
			dataInfo = autoHCTemplateBOIntf.getFaultCodeRecTypeDropDwn(ctrlConfig, configFile);
		}
		catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "en");
        }
		return dataInfo;
	}
	
	@Override
	public List<String> getMessageDefIdDropDwn()
			throws RMDServiceException {
		List<String> dataInfo = null;
		try{
			dataInfo = autoHCTemplateBOIntf.getMessageDefIdDropDwn();
		}
		catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "en");
        }
		return dataInfo;
	}
	
	@Override
	public String postAutoHCNewTemplate(
			AutoHCTemplateSaveVO autoHCTemplateSaveVO)
			throws RMDServiceException {
			String result = null;
			try{
				result = autoHCTemplateBOIntf.postAutoHCNewTemplate(autoHCTemplateSaveVO);
			}
			catch (RMDDAOException ex) {
	            throw new RMDServiceException(ex.getErrorDetail(), ex);
	        } catch (RMDBOException ex) {
	            throw new RMDServiceException(ex.getErrorDetail(), ex);
	        } catch (Exception ex) {
	            RMDServiceErrorHandler.handleGeneralException(ex, "en");
	        }
			return result;
	}
	
	@Override
	public Map<String, String> getComAHCDetails(String templateNo,
			String versionNo, String ctrlCfgObjId) throws RMDServiceException {
		Map<String,String> result = null;
		try{
			result = autoHCTemplateBOIntf.getComAHCDetails(templateNo,versionNo,ctrlCfgObjId);
		}
		catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "en");
        }
		return result;
	}

}

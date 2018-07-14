package com.ge.trans.eoa.services.autohc.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ge.trans.eoa.services.autohc.bo.intf.AutoHCTemplateBoIntf;
import com.ge.trans.eoa.services.autohc.dao.intf.AutoHCTemplateDaoIntf;
import com.ge.trans.eoa.services.autohc.service.valueobjects.AutoHCTemplateSaveVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public class AutoHCTemplateBoImpl implements AutoHCTemplateBoIntf{

	private AutoHCTemplateDaoIntf autoHCTemplateDaoIntf;

    public AutoHCTemplateBoImpl(AutoHCTemplateDaoIntf autoHCTemplateDaoIntf) {
        this.autoHCTemplateDaoIntf = autoHCTemplateDaoIntf;
    }
    
	@Override
	public ArrayList<String> getTemplateDropDwn(String ctrlConfig, String configFile) throws RMDBOException {
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			data = autoHCTemplateDaoIntf.getTemplateDropDwn(ctrlConfig, configFile);
		}catch (RMDDAOException e) {
            throw e;
        }
		return data;
	}
	
	@Override
	public ArrayList<String> getFaultCodeRecTypeDropDwn(String ctrlConfig, String configFile) throws RMDBOException {
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			data = autoHCTemplateDaoIntf.getFaultCodeRecTypeDropDwn(ctrlConfig, configFile);
		}catch (RMDDAOException e) {
            throw e;
        }
		return data;
	}

	@Override
	public ArrayList<String> getMessageDefIdDropDwn() throws RMDBOException {
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			data = autoHCTemplateDaoIntf.getMessageDefIdDropDwn();
		}catch (RMDDAOException e) {
            throw e;
        }
		return data;
	}

	@Override
	public String postAutoHCNewTemplate(AutoHCTemplateSaveVO autoHCTemplateSaveVO) throws RMDBOException {
		String result = new String();
		try{
			result = autoHCTemplateDaoIntf.postAutoHCNewTemplate(autoHCTemplateSaveVO);
		}catch (RMDDAOException e) {
            throw e;
        }
		return result;
	}

	@Override
	public Map<String, String> getComAHCDetails(String templateNo,
			String versionNo, String ctrlCfgObjId) throws RMDBOException {
		Map<String,String> result = new HashMap<String, String>();
		try{
			result = autoHCTemplateDaoIntf.getComAHCDetails(templateNo,versionNo,ctrlCfgObjId);
		}catch (RMDDAOException e) {
            throw e;
        }
		return result;
		
	}

	
	
}

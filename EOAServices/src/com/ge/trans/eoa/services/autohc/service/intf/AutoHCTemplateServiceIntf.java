package com.ge.trans.eoa.services.autohc.service.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.autohc.service.valueobjects.AutoHCTemplateSaveVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface AutoHCTemplateServiceIntf {

	public List<String> getTemplateDropDwn(String ctrlConfig ,String configFile)  throws RMDServiceException;
	public List<String> getFaultCodeRecTypeDropDwn(String ctrlConfig ,String configFile)  throws RMDServiceException;
	public List<String> getMessageDefIdDropDwn()  throws RMDServiceException;
	public String postAutoHCNewTemplate(AutoHCTemplateSaveVO autoHCTemplateSaveVO)  throws RMDServiceException;
	public Map<String,String> getComAHCDetails(String templateNo,String versionNo,String ctrlCfgObjId)  throws RMDServiceException  ;
}

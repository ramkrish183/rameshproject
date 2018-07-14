package com.ge.trans.eoa.services.autohc.bo.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.autohc.service.valueobjects.AutoHCTemplateSaveVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface AutoHCTemplateBoIntf {

	public List<String> getTemplateDropDwn(String ctrlConfig ,String configFile) throws RMDBOException ;
	public List<String> getFaultCodeRecTypeDropDwn(String ctrlConfig ,String configFile) throws RMDBOException ;
	public List<String> getMessageDefIdDropDwn() throws RMDBOException ;
	public String postAutoHCNewTemplate(AutoHCTemplateSaveVO autoHCTemplateSaveVO) throws RMDBOException ;
	public Map<String,String> getComAHCDetails(String templateNo,String versionNo,String ctrlCfgObjId)  throws RMDBOException  ;
}

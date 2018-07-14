package com.ge.trans.eoa.services.autohc.dao.intf;

import java.util.ArrayList;
import java.util.Map;

import com.ge.trans.eoa.services.autohc.service.valueobjects.AutoHCTemplateSaveVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface AutoHCTemplateDaoIntf {

	public ArrayList<String> getTemplateDropDwn(String ctrlConfig ,String configFile)  throws RMDDAOException  ;
	public ArrayList<String> getFaultCodeRecTypeDropDwn(String ctrlConfig ,String configFile)  throws RMDDAOException  ;
	public ArrayList<String> getMessageDefIdDropDwn()  throws RMDDAOException  ;
	public String postAutoHCNewTemplate(AutoHCTemplateSaveVO autoHCTemplateSaveVO)  throws RMDDAOException  ;
	public Map<String,String> getComAHCDetails(String templateNo,String versionNo,String ctrlCfgObjId) throws RMDDAOException  ;
}

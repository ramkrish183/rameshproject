package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.MessageDefVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MessageQueuesVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface MessageDefinitionDAOIntf {
	
	public MessageDefVO getEFIMsgDefObjid() throws RMDDAOException;
	
	/**
	 * @Author :
	 * @return :List<String>
	 * @param : String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Services Types which will allow EDP,FFD,FRD Configuration Templates.
	 * 
	 */
	
	public  void  sendMessageToQueue(List<MessageQueuesVO> arlMessageQueuesVOs)
			throws RMDDAOException ;

	
	/**
	 * @Author :
	 * @return :List<String>
	 * @param : String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Services Types which will allow EFI Configuration Templates.
	 * 
	 */
	
	public List<String> getEnabledServicesEFI() throws RMDDAOException;

}

package com.ge.trans.eoa.services.rxtranslation.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualFilterVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualRxVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface RxTranslationServiceIntf {

	/**
	 * 
	 * @return
	 * @throws RMDServiceException
	 */
	public List<String> getTranslationLanguage() throws RMDServiceException;
	
	/**
	 * 
	 * @return
	 * @throws RMDServiceException
	 */
	public List<String> getTranslationLastModifiedBy() throws RMDServiceException;
	
	/**
	 * 
	 * @return
	 * @throws RMDServiceException
	 */
	public List<MultiLingualRxVO> getTranslationRxList(MultiLingualFilterVO multiLingualFilter) throws RMDServiceException;
	
	/**
	 * 
	 * @param rxObjid
	 * @param strLanguage
	 * @return
	 * @throws RMDServiceException
	 */
	public RxTransDetailVO getRxTransDetail(String rxObjid,String strLanguage) throws RMDServiceException;
	
	/**
	 * 
	 * @param rxTransDetailVO
	 * @return
	 * @throws RMDDAOException
	 */
	public String saveRxTranslation(RxTransDetailVO rxTransDetailVO)throws RMDServiceException;
	
	/**
	 * 
	 * @param transRxDetail
	 * @return
	 * @throws RMDServiceException
	 */
	public String previewRxPdf(RxTransDetailVO transRxDetail)
			throws RMDServiceException;
}

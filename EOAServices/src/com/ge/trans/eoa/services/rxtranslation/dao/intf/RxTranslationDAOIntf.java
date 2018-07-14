package com.ge.trans.eoa.services.rxtranslation.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualFilterVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualRxVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface RxTranslationDAOIntf {

	/**
	 * 
	 * @return
	 * @throws RMDDAOException
	 */
	public List<String> getTranslationLanguage() throws RMDDAOException;
	
	/**
	 * 
	 * @return
	 * @throws RMDDAOException
	 */
	public List<String> getTranslationLastModifiedBy() throws RMDDAOException;
	
	/**
	 * 
	 * @return
	 * @throws RMDDAOException
	 */
	public List<MultiLingualRxVO> getTranslationRxList(MultiLingualFilterVO multiLingualFilter) throws RMDDAOException;

	/**
	 * 
	 * @param rxObjid
	 * @param strLanguage
	 * @return
	 * @throws RMDDAOException
	 */
	public RxTransDetailVO getRxTransDetail(String rxObjid,String strLanguage) throws RMDDAOException;
	
	/**
	 * 
	 * @param rxTransDetailVO
	 * @return
	 * @throws RMDDAOException
	 */
	public String saveRxTranslation(RxTransDetailVO rxTransDetailVO)throws RMDDAOException;
	
	/**
	 * 
	 * @param transRxDetail
	 * @return
	 * @throws RMDDAOException
	 */
	public String previewRxPdf(RxTransDetailVO transRxDetail)
			throws RMDDAOException;
}

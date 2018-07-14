package com.ge.trans.eoa.services.rxtranslation.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualFilterVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualRxVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface RxTranslationBOIntf {
	/**
	 * 
	 * @return
	 * @throws RMDBOException
	 */
	public List<String> getTranslationLanguage() throws RMDBOException;	

	/**
	 * 
	 * @return
	 * @throws RMDBOException
	 */
	public List<String> getTranslationLastModifiedBy() throws RMDBOException;
	
	/**
	 * 
	 * @return
	 * @throws RMDBOException
	 */
	public List<MultiLingualRxVO> getTranslationRxList(MultiLingualFilterVO multiLingualFilter) throws RMDBOException;

	/**
	 * 
	 * @param rxObjid
	 * @param strLanguage
	 * @return
	 * @throws RMDBOException
	 */
	public RxTransDetailVO getRxTransDetail(String rxObjid,String strLanguage) throws RMDBOException;
	
	/**
	 * 
	 * @param rxTransDetailVO
	 * @return
	 * @throws RMDDAOException
	 */
	public String saveRxTranslation(RxTransDetailVO rxTransDetailVO)throws RMDBOException;
	
	/**
	 * 
	 * @param transRxDetail
	 * @return
	 * @throws RMDBOException
	 */
	public String previewRxPdf(RxTransDetailVO transRxDetail)
			throws RMDBOException;
	

}

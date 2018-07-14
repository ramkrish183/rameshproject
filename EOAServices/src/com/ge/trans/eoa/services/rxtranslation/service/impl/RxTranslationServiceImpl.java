package com.ge.trans.eoa.services.rxtranslation.service.impl;

import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.rxtranslation.bo.intf.RxTranslationBOIntf;
import com.ge.trans.eoa.services.rxtranslation.service.intf.RxTranslationServiceIntf;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualFilterVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualRxVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class RxTranslationServiceImpl implements RxTranslationServiceIntf {

	private RxTranslationBOIntf objRxTranslationBOIntf;

	public RxTranslationServiceImpl(RxTranslationBOIntf objRxTranslationBOIntf) {
		this.objRxTranslationBOIntf = objRxTranslationBOIntf;
	}
	
	@Override
	public List<String> getTranslationLanguage() throws RMDServiceException {
		List<String> transLanguages;
		try {
			transLanguages = objRxTranslationBOIntf.getTranslationLanguage();
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return transLanguages;
	}

	@Override
	public List<String> getTranslationLastModifiedBy()
			throws RMDServiceException {
		List<String> transLastModifiedList;
		try {
			transLastModifiedList = objRxTranslationBOIntf.getTranslationLastModifiedBy();
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return transLastModifiedList;
	}

	@Override
	public List<MultiLingualRxVO> getTranslationRxList(MultiLingualFilterVO multiLingualFilter) throws RMDServiceException {
		List<MultiLingualRxVO> translationRxList;
		try {
			translationRxList = objRxTranslationBOIntf.getTranslationRxList(multiLingualFilter);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return translationRxList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.rxtranslation.service.intf.RxTranslationServiceIntf#getRxTransDetail(java.lang.String, java.lang.String)
	 */
	public RxTransDetailVO getRxTransDetail(String rxObjid,String strLanguage)throws RMDServiceException {
    	RxTransDetailVO objRxTransDetailVO = null;
        try {
        	objRxTransDetailVO = objRxTranslationBOIntf.getRxTransDetail(rxObjid,strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return objRxTransDetailVO;
    }
	
	public String saveRxTranslation(RxTransDetailVO rxTransDetailVO) throws RMDServiceException{
		String updatedRxId = null;
		try {
			updatedRxId = objRxTranslationBOIntf.saveRxTranslation(rxTransDetailVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
		return updatedRxId;
	}
	
	public String previewRxPdf(RxTransDetailVO transRxDetail)
			throws RMDServiceException {
		String path = null;
		try {
			path = objRxTranslationBOIntf.previewRxPdf(transRxDetail);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
		return path;
	}
}

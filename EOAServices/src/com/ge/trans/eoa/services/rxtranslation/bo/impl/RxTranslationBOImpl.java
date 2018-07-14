package com.ge.trans.eoa.services.rxtranslation.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.rxtranslation.bo.intf.RxTranslationBOIntf;
import com.ge.trans.eoa.services.rxtranslation.dao.intf.RxTranslationDAOIntf;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualFilterVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualRxVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public class RxTranslationBOImpl implements RxTranslationBOIntf {

	private RxTranslationDAOIntf objRxTranslationDAOIntf;
	public RxTranslationBOImpl(final RxTranslationDAOIntf objRxTranslationDAOIntf) {
		this.objRxTranslationDAOIntf = objRxTranslationDAOIntf;
	}
	@Override
	public List<String> getTranslationLanguage() throws RMDBOException {
		List<String> transLanguageList = null;
		try {
			transLanguageList = objRxTranslationDAOIntf.getTranslationLanguage();
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		return transLanguageList;
	}

	@Override
	public List<String> getTranslationLastModifiedBy() throws RMDBOException {
		List<String> transModifiedBy = null;
		try {
			transModifiedBy = objRxTranslationDAOIntf.getTranslationLastModifiedBy();
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		return transModifiedBy;
	}
	@Override
	public List<MultiLingualRxVO> getTranslationRxList(MultiLingualFilterVO multiLingualFilter) throws RMDBOException {
		List<MultiLingualRxVO> translationRx = null;
		try {
			translationRx = objRxTranslationDAOIntf.getTranslationRxList(multiLingualFilter);
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		return translationRx;
	}
	
	@Override
    public RxTransDetailVO getRxTransDetail(String rxObjid,String strLanguage) throws RMDBOException {
    	RxTransDetailVO objRxTransDetailVO=null;
        try {
        	objRxTransDetailVO = objRxTranslationDAOIntf.getRxTransDetail(rxObjid,strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objRxTransDetailVO;
    }

	@Override
	public String saveRxTranslation(RxTransDetailVO rxTransDetailVO)
			throws RMDBOException {
		String updatedRxId;
		try {
			updatedRxId = objRxTranslationDAOIntf.saveRxTranslation(rxTransDetailVO);
        } catch (RMDDAOException e) {
            throw e;
        }
		return updatedRxId;
	}
	
	public String previewRxPdf(RxTransDetailVO transRxDetail)
			throws RMDBOException {
		String path;
		try {
			path = objRxTranslationDAOIntf.previewRxPdf(transRxDetail);
        } catch (RMDDAOException e) {
            throw e;
        }
		return path;
	}
}

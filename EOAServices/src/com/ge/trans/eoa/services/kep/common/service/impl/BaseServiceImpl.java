package com.ge.trans.eoa.services.kep.common.service.impl;

import java.util.List;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.kep.common.bo.intf.BaseBOIntf;
import com.ge.trans.eoa.services.kep.common.service.intf.BaseServiceIntf;

/**
 * @author krishbal
 */
public class BaseServiceImpl implements BaseServiceIntf {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(BaseServiceImpl.class);
    private BaseBOIntf baseBOIntf;

    public BaseServiceImpl(BaseBOIntf baseBOIntf) {
        this.baseBOIntf = baseBOIntf;
    }

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strLanguage
     * @return list of ElementVO
     * @throws KEPServiceException
     */
    @Override
    public List<ElementVO> getLookUPDetails(final String strListName, final String strLanguage)
            throws RMDServiceException {
        // TODO Auto-generated method stub
        List<ElementVO> arlStatus = null;
        try {
            arlStatus = baseBOIntf.getLookUPDetails(strListName, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlStatus;
    }

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    @Override
    public List getSymptom(final String strCustomer) throws RMDServiceException {
        List<ElementVO> arlSymptom = null;
        try {
            arlSymptom = baseBOIntf.getSymptom(strCustomer);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlSymptom;

    }

    /**
     * This method is used for fetching the root cause from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    @Override
    public List getRootCause(final String strCustomer) throws RMDServiceException {

        List<ElementVO> arlRootCause = null;

        try {
            arlRootCause = baseBOIntf.getRootCause(strCustomer);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlRootCause;

    }

}

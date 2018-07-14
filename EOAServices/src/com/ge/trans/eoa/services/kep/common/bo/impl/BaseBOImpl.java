package com.ge.trans.eoa.services.kep.common.bo.impl;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

import com.ge.trans.eoa.services.kep.common.bo.intf.BaseBOIntf;
import com.ge.trans.eoa.services.kep.common.dao.intf.BaseDAOIntf;

/**
 * @author krishbal
 */
public class BaseBOImpl implements BaseBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(BaseBOImpl.class);
    private BaseDAOIntf baseDAOIntf;

    public BaseBOImpl(BaseDAOIntf baseDAOIntf) {
        this.baseDAOIntf = baseDAOIntf;
    }

    @Override
    public List<ElementVO> getLookUPDetails(final String strListName, String strLanguage) throws RMDBOException {
        List<ElementVO> arlStatus = null;
        try {
            arlStatus = baseDAOIntf.getLookUPDetails(strListName, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlStatus;
    }

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */
    @Override
    public List getSymptom(final String strCustomer) throws RMDBOException {
        List<ElementVO> arlSymptom = null;
        try {
            arlSymptom = baseDAOIntf.getSymptom(strCustomer);
        } catch (RMDDAOException e) {
            throw e;
        }

        return arlSymptom;

    }

    /**
     * This method is used for fetching the root cause from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */
    @Override
    public List getRootCause(final String strCustomer) throws RMDBOException {
        List<ElementVO> arlRootCause = null;
        try {
            arlRootCause = baseDAOIntf.getRootCause(strCustomer);
        } catch (RMDDAOException e) {
            throw e;
        }

        return arlRootCause;

    }

}

/**
 * @author krishbal
 *
 */
package com.ge.trans.eoa.services.kep.common.service.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/**
 * @author krishbal
 */
public interface BaseServiceIntf {
    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strLanguage
     * @return list of BaseVO
     * @throws RMDServiceException
     */
    public List<ElementVO> getLookUPDetails(String listName, String strLanguage) throws RMDServiceException;

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    public List getSymptom(String strCustomer) throws RMDServiceException;

    /**
     * This method is used for fetching the root cause from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    public List getRootCause(String strCustomer) throws RMDServiceException;

}

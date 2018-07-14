/**
 * @author krishbal
 *
 */
package com.ge.trans.eoa.services.kep.common.bo.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;

/**
 * @author krishbal
 */
public interface BaseBOIntf {
    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDBOException;

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */

    public List getSymptom(String strCustomer) throws RMDBOException;

    /**
     * This method is used for fetching the root cause from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */
    public List getRootCause(String strCustomer) throws RMDBOException;

}

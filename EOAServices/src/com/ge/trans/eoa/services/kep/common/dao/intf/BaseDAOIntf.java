/**
 * @author krishbal
 *
 */
package com.ge.trans.eoa.services.kep.common.dao.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/**
 * @author krishbal
 */
public interface BaseDAOIntf {
    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strlanguage
     * @return list of BaseVO
     * @throws RMDDAOException
     */

    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDDAOException;

    /**
     * This method is used for loading the Symptoms
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */

    public List getSymptom(String strCustomer) throws RMDDAOException;

    /**
     * This method is used for loading the RootCause
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */
    public List getRootCause(String strCustomer) throws RMDDAOException;

}

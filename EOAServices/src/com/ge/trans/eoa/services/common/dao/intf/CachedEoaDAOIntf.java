package com.ge.trans.eoa.services.common.dao.intf;

import java.util.Map;

import com.ge.trans.rmd.exception.RMDDAOException;

public interface CachedEoaDAOIntf {

    /**
     * @return
     * @throws RMDDAOException
     * @Description Method to cache all the locomoative impacts
     */
    public Map<String, String> getAllLocoImpactMap() throws RMDDAOException;

    /**
     * @return
     * @throws RMDDAOException
     * @Description Method to cache all the rx title
     */
    public Map<String, String> getAllRxTitleMap() throws RMDDAOException;

}

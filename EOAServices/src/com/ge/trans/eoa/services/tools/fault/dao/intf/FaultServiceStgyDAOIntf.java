/**
 * ============================================================
 * File : FaultServiceStgyDAOIntf.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.fault.dao.intf;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.fault.dao.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultServiceStrategyVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceStgyServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 3, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface FaultServiceStgyDAOIntf {

    /**
     * @Author:
     * @param objFaultServiceStgyServiceVO
     * @return List<FaultServiceStgyServiceVO>
     * @throws RMDDAOException
     * @Description:
     */
	List<FaultServiceStgyServiceVO> findFaultCode(
			String strFamily, String strFaultCode,boolean isExternalRuleAuthor)
			throws RMDDAOException;

    /**
     * @Author:
     * @param objFaultServiceStgyServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    FaultServiceStgyServiceVO findFault(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objFaultServiceStgyServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    String saveUpdate(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, String strUserId) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    @SuppressWarnings("unchecked")
    List getLoadFaultClasses(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO) throws RMDDAOException;

    /**
     * @param strFaultCode
     * @param strUserId
     * @return
     * @throws RMDDAOException
     */
    String checkFaultCode(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, String strUserId)
            throws RMDDAOException;

    /**
     * @param strModelName
     * @param
     * @return
     * @throws RMDDAOException
     */
    List<FaultServiceStgyServiceVO> getFaultCodeforModels(String strModelName) throws RMDDAOException;

    /**
     * @param fsObjId
     * @param FaultServiceStrategyVO
     * @return
     * @throws RMDDAOException
     */
    public FaultServiceStrategyVO getFaultStrategyDetails(String fsObjId) throws RMDDAOException;

    /**
     * @param faultCode
     * @param List<FaultServiceStrategyVO>
     * @return
     * @throws RMDDAOException
     */
    public List<FaultServiceStrategyVO> getRuleDesc(String faultCode) throws RMDDAOException;

    /**
     * @param FaultServiceStrategyVO
     * @param
     * @return
     * @throws RMDDAOException
     */
    void updateFaultServiceStrategy(FaultServiceStrategyVO objFaultServiceStrategyVO) throws RMDDAOException;

    /**
     * @param
     * @return List<FaultCodeVO>
     * @throws RMDDAOException
     * @Description This method is used to fetch Fault Origin values to populate
     *              Fault Origin Drop down.
     */

    public List<FaultCodeVO> getFaultOrigin() throws RMDDAOException;

    /**
     * @param faultCode,faultOrigin
     * @return List<FaultCodeVO>
     * @throws RMDDAOException
     * @Description This method is used to fetch Fault Code SubId's to populate
     *              Fault SubId Drop down.
     */

    public List<FaultCodeVO> getFaultCodeSubId(String faultCode, String faultOrigin) throws RMDDAOException;

    /**
     * @param faultCode,faultOrigin
     * @return List<FaultCodeVO>
     * @throws RMDDAOException
     * @Description This method is used to get Fault Code based upon Search
     *              Criteria.
     */

    public List<FaultCodeVO> getViewFSSFaultCode(String faultCode, String faultOrigin) throws RMDDAOException;

    /**
     * @param FaultCodeVO
     *            objFaultCodeVO
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to get Fault Code ObjId
     */
    public String getFaultStrategyObjId(FaultCodeVO objFaultCodeVO) throws RMDDAOException;

}

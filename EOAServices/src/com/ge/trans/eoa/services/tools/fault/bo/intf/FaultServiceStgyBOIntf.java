/**
 * ============================================================
 * File : FaultServiceStgyBOIntf.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.fault.bo.intf
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
package com.ge.trans.eoa.services.tools.fault.bo.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultServiceStrategyVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceStgyServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface FaultServiceStgyBOIntf {

    /**
     * @Author: iGate
     * @param List<FaultServiceStgyServiceVO>
     * @return
     * @throws RMDServiceException
     * @Description: This method find the fault code and fault code description
     *               based on the fault code
     */
    List<FaultServiceStgyServiceVO> findFaultCode(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO)
            throws RMDBOException;

    /**
     * @Author: iGate
     * @param FaultServiceStgyServiceVO
     * @return
     * @throws RMDServiceException
     * @Description: This method find the fault details based on the fault
     */
    FaultServiceStgyServiceVO findFault(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO) throws RMDBOException;

    /**
     * @Author: iGate
     * @param FaultServiceStgyServiceVO
     * @return
     * @throws RMDServiceException
     * @Description: This method saveUpdate the fault
     */
    String saveUpdate(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, String strUserId) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    @SuppressWarnings("unchecked")
    List getLoadFaultClasses(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param strFaultCode
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    String checkFaultCode(FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, String strUserId)
            throws RMDBOException;

    /**
     * @Author:
     * @param fsObjId
     * @param FaultServiceStrategyVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    public FaultServiceStrategyVO getFaultStrategyDetails(String fsObjId) throws RMDBOException;

    /**
     * @Author:
     * @param faultCode
     * @param FaultServiceStrategyVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    public List<FaultServiceStrategyVO> getRuleDesc(String faultCode) throws RMDBOException;

    /**
     * @Author:
     * @param FaultServiceStrategyVO
     * @param
     * @return
     * @throws RMDBOException
     * @Description:
     */
    void updateFaultServiceStrategy(FaultServiceStrategyVO objFaultServiceStrategyVO) throws RMDBOException;

    /**
     * @param
     * @return List<String>
     * @throws RMDBOException
     * @Description This method is used to fetch Fault Origin values to populate
     *              Fault Origin Drop down.
     */

    public List<FaultCodeVO> getFaultOrigin() throws RMDBOException;

    /**
     * @param faultCode,faultOrigin
     * @return List<String>
     * @throws RMDBOException
     * @Description This method is used to fetch Fault Code SubId's to populate
     *              Fault SubId Drop down.
     */

    public List<FaultCodeVO> getFaultCodeSubId(String faultCode, String faultOrigin) throws RMDBOException;

    /**
     * @param faultCode,faultOrigin
     * @return List<FaultCodeVO>
     * @throws RMDBOException
     * @Description This method is used to get Fault Code based upon Search
     *              Criteria.
     */

    public List<FaultCodeVO> getViewFSSFaultCode(String faultCode, String faultOrigin) throws RMDBOException;

    /**
     * @param FaultCodeVO
     *            objFaultCodeVO
     * @return String
     * @throws RMDBOException
     * @Description This method is used to get Fault Code ObjId
     */
    public String getFaultStrategyObjId(FaultCodeVO objFaultCodeVO) throws RMDBOException;

}

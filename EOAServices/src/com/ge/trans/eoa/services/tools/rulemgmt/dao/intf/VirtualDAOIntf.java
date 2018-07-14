/**
 * ============================================================
 * Classification: GE Confidential
 * File : VirtualDAOIntf.java
 * Description : DAO Interface for Virtual Screen
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalVirtualServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : DAO Interface for Virtual Screen
 * @History :
 ******************************************************************************/
public interface VirtualDAOIntf {

    /**
     * @param objFinalVirtualServiceVO
     * @return String
     * @throws RMDDAOException
     */
    String saveVirtual(FinalVirtualServiceVO objFinalVirtualServiceVO) throws RMDDAOException;

    /**
     * @param objFinalVirtualServiceVO
     * @return List<FinalVirtualServiceVO>
     * @throws RMDDAOException
     */
    FinalVirtualServiceVO getVirtualDetails(String strVirtualId, String strLanguage) throws RMDDAOException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDDAOException
     */
    List<VirtualServiceVO> getVirtualLastUpdatedBy(String strLanguage) throws RMDDAOException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDDAOException
     */
    List<VirtualServiceVO> getVirtualCreatedBy(String strLanguage) throws RMDDAOException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDDAOException
     */
    List<VirtualServiceVO> getVirtualTitles(String strVirtualTitles, String strLanguage) throws RMDDAOException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDDAOException
     */
    List<VirtualServiceVO> getVirtualFamily(String strLanguage) throws RMDDAOException;

    /**
     * @param objVirtualServiceVO
     * @return List
     * @throws RMDDAOException
     */
    List<VirtualServiceVO> searchVirtuals(VirtualServiceVO objVirtualServiceVO) throws RMDDAOException;

    /**
     * @param objFinalVirtualServiceVO
     * @return String
     * @throws RMDDAOException
     */
    String updateVirtual(FinalVirtualServiceVO objFinalVirtualServiceVO) throws RMDDAOException;

    String isVirtualColumnExist() throws RMDDAOException;

    public List<ElementVO> getVirtualActiveRules(int virtualId, String family) throws RMDBOException;
}

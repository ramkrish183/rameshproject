/**
 * ============================================================
 * Classification: GE Confidential
 * File : VirtualBOIntf.java
 * Description : BO Interface for Virtual Screen
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.bo.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalVirtualServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : BO Interface for Virtual Screen
 * @History :
 ******************************************************************************/
public interface VirtualBOIntf {

    /**
     * @param objFinalVirtualServiceVO
     * @return String
     * @throws RMDBOException
     */
    String saveVirtual(FinalVirtualServiceVO objFinalVirtualServiceVO) throws RMDBOException;

    /**
     * @param String
     *            strVirtualId, String strLanguage
     * @return FinalVirtualServiceVO
     * @throws RMDBOException
     */
    FinalVirtualServiceVO getVirtualDetails(String strVirtualId, String strLanguage) throws RMDBOException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     */
    List<VirtualServiceVO> getVirtualLastUpdatedBy(String strLanguage) throws RMDBOException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     */
    List<VirtualServiceVO> getVirtualCreatedBy(String strLanguage) throws RMDBOException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     */
    List<VirtualServiceVO> getVirtualTitles(String strVirtualTitle, String strLanguage) throws RMDBOException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     */
    List<VirtualServiceVO> getVirtualFamily(String strLanguage) throws RMDBOException;

    /**
     * @param objVirtualServiceVO
     * @return List
     * @throws RMDBOException
     */
    List<VirtualServiceVO> searchVirtuals(VirtualServiceVO objVirtualServiceVO) throws RMDBOException;

    String isVirtualColumnExist() throws RMDBOException;

    List<ElementVO> getVirtualActiveRules(int virtualId, String family) throws RMDBOException;
}

/**
 * ============================================================
 * Classification: GE Confidential
 * File : VirtualServiceIntf.java
 * Description : Service Interface for Virtual Screen
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalVirtualServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Service Interface for Virtual Screen
 * @History :
 ******************************************************************************/
public interface VirtualServiceIntf {

    /**
     * @param objFinalVirtualServiceVO
     * @return String
     * @throws RMDServiceException
     */
    String saveVirtual(FinalVirtualServiceVO objFinalVirtualServiceVO) throws RMDServiceException;

    /**
     * @param String
     *            strVirtualId,String strlanguage
     * @return FinalVirtualServiceVO
     * @throws RMDServiceException
     */
    FinalVirtualServiceVO getVirtualDetails(String strVirtualId, String strlanguage) throws RMDServiceException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDServiceException
     */
    List<VirtualServiceVO> getVirtualLastUpdatedBy(String strLanguage) throws RMDServiceException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDServiceException
     */
    List<VirtualServiceVO> getVirtualCreatedBy(String strLanguage) throws RMDServiceException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDServiceException
     */
    List<VirtualServiceVO> getVirtualTitles(String strVirtualTitle, String strLanguage) throws RMDServiceException;

    /**
     * @param
     * @return List<VirtualServiceVO>
     * @throws RMDServiceException
     */
    List<VirtualServiceVO> getVirtualFamily(String strLanguage) throws RMDServiceException;

    /**
     * @param objVirtualServiceVO
     * @return List
     * @throws RMDServiceException
     */
    List<VirtualServiceVO> searchVirtuals(VirtualServiceVO objVirtualServiceVO) throws RMDServiceException;

    String isVirtualColumnExist() throws RMDServiceException;

    List<ElementVO> getVirtualActiveRules(int virtualId, String family) throws RMDServiceException;
}

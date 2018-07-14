/**
 * ============================================================
 * File : LocationServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 14, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.service.valueobjects.CreateLocationServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocationServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 14, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface LocationServiceIntf {

    /**
     * @Author:
     * @param findLocationServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List findLocation(FindLocationServiceVO objFindLocationServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param strLocationId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getEditLocationDetails(String strLocationId, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param strLocationId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getLocationContactsList(String strLocationId, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param findLocationServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    String createLocation(CreateLocationServiceVO objCreateLocationServiceVO, String strEditCreateLocation)
            throws RMDServiceException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List loadStatusDropDown(String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List loadTypeDropDown(String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List loadTimeZoneDropDown(String strLanguage) throws RMDServiceException;
}

/**
 * ============================================================
 * File : LocationDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.dao.intf
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
package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDDAOException;
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
public interface LocationDAOIntf {

    /**
     * @Author:
     * @param findLocationServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List findLocation(FindLocationServiceVO findLocationServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objCreateLocationServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    String createLocation(CreateLocationServiceVO objCreateLocationServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List loadTypeDropDown(String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List loadStatusDropDown(String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param strLocationId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getEditLocationDetails(String strLocationId, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param objCreateLocationServiceVO
     * @return
     * @Description:
     */
    String checkLocationId(CreateLocationServiceVO objCreateLocationServiceVO);

    /**
     * @Author:
     * @param strLocationId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getLocationContactsList(String strLocationId, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param objCreateLocationServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    String updateLocation(CreateLocationServiceVO objCreateLocationServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List loadTimeZoneDropDown(String strLanguage) throws RMDDAOException;
}

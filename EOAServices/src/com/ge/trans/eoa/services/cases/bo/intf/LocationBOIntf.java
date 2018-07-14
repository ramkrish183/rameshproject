/**
 * ============================================================
 * File : LocationBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.bo.intf
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
package com.ge.trans.eoa.services.cases.bo.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
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
public interface LocationBOIntf {

    /**
     * @Author:
     * @param findLocationServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List findLocation(FindLocationServiceVO findLocationServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objCreateLocationServiceVO
     * @param strEditCreateLocation
     * @return
     * @throws RMDBOException
     * @Description:
     */
    String createLocation(CreateLocationServiceVO objCreateLocationServiceVO, String strEditCreateLocation)
            throws RMDBOException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List loadStatusDropDown(String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List loadTypeDropDown(String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strLocationId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getEditLocationDetails(String strLocationId, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strLocationId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getLocationContactsList(String strLocationId, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List loadTimeZoneDropDown(String strLanguage) throws RMDBOException;
}

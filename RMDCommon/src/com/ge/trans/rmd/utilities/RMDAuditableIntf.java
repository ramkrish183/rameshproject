/**
 * ============================================================
 * File : RMDAuditableIntf.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.caseapi.services.utils
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 30, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.utilities;

import com.ge.trans.rmd.common.valueobjects.RMDAuditInfoVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface RMDAuditableIntf {

    /**
     * Instances must always return a non-null instance of AuditInfo
     */
    RMDAuditInfoVO getAuditInfo();
}

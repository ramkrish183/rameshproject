/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDServiceErrorHandler.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.common.utils;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2008 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.pp.common.util;

import org.springframework.transaction.CannotCreateTransactionException;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.ErrorDetail;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDServiceErrorHandler {

    private static final long serialVersionUID = 393497933785610L;
    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(RMDServiceErrorHandler.class);

    /**
     * @Author:
     * @param e
     * @param language
     * @Description:
     */
    public static void handleGeneralException(final Exception e, final String language) throws RMDServiceException {
        String strErrorMessage = null;
        ErrorDetail errorDetailVO = null;
        LOG.error(strErrorMessage, e);
        if (e instanceof CannotCreateTransactionException) {
            strErrorMessage = RMDCommonUtility.getMessage(RMDServiceConstants.DB_CONNECTION_FAILURE_MESSAGE,
                    new Object[] {}, language);
            errorDetailVO = new ErrorDetail(RMDServiceConstants.DB_CONNECTION_FAILURE_MESSAGE, new String[] {},
                    strErrorMessage, e, RMDCommonConstants.MAJOR_ERROR);
        } else if (e instanceof RMDDAOException || e instanceof RMDServiceException || e instanceof RMDBOException) {
            // Preserve the error details if it is already categorized
            errorDetailVO = ((RMDException) e).getErrorDetail();
        } else {
            strErrorMessage = RMDCommonUtility.getMessage(RMDServiceConstants.EXCEPTION_GENERAL, new Object[] {},
                    language);
            errorDetailVO = new ErrorDetail(RMDServiceConstants.EXCEPTION_GENERAL, new String[] {}, strErrorMessage, e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        throw new RMDServiceException(errorDetailVO);
    }
}

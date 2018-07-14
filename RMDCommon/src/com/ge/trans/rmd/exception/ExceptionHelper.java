/**
 * ============================================================
 * File : ExceptionHelper.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.exception
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
package com.ge.trans.rmd.exception;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Helper class to construct log message for exceptions
 * @History :
 ******************************************************************************/
public final class ExceptionHelper {

    /**
     * private constructor
     */
    private ExceptionHelper() {
    }

    /**
     * @Author:
     * @param className
     * @param methodName
     * @return String
     * @Description: Method to parse the class name and method name
     */
    public static final String parseClassName(String className, String methodName) {
        String parsedClassName = "";
        if (className != null) {
            if (methodName == null) {
                parsedClassName = className.trim();
            } else {
                parsedClassName = className.trim() + "." + methodName.trim();
            }
        } else if (methodName != null) {
            parsedClassName = methodName.trim();
        }
        return parsedClassName;
    }

    /**
     * @Author:
     * @param errorDetail
     * @param defaultErrorCode
     * @return ErrorDetail
     * @Description: Method to parse the error detail
     */
    public static final ErrorDetail parseErrorDetail(ErrorDetail errorDetail, String defaultErrorCode) {
        ErrorDetail parsedErrorDetail = null;
        if (errorDetail == null) {
            parsedErrorDetail = new ErrorDetail(defaultErrorCode);
        } else if ((errorDetail.getErrorCode() == null) || (errorDetail.getErrorCode().trim().length() < 1)) {
            parsedErrorDetail = new ErrorDetail(defaultErrorCode, errorDetail.getErrorParams());
        } else {
            parsedErrorDetail = errorDetail;
        }
        return parsedErrorDetail;
    }
}
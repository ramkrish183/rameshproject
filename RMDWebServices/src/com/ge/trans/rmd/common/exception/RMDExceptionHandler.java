/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDExceptionHandler.java
 * Description :
 * Package : com.ge.trans.rmd.services.exception
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.common.exception;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@Provider
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RMDExceptionHandler implements ExceptionMapper<RMDServiceException> {

    /**
     * This method is used for handling all RMDServiceExceptions
     * 
     * @param RMDServiceException
     * @return Response
     */
    public Response toResponse(final RMDServiceException exception) {

        // Commented and added BAD REQUEST Response Code for getting the Request
        // code as 400
        // instead of 204 which is being converted to 200 when it is returned to
        // the RMDWeb Module
        // final ResponseBuilder response =
        // Response.status(Response.Status.NO_CONTENT);

        final ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
        return response.entity(new RMDException(exception)).build();
    }

    /**
     * This method is used for handling all Non-RMDServiceExceptions
     * 
     * @param RMDServiceException
     * @return Response
     */
    public Response toResponse(final Exception excep) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new RMDException(excep)).build();
    }
}

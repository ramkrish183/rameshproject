/**
 * ============================================================
 * Classification: GE Confidential
 * File : OMDApplicationExceptionHandler.java
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
package com.ge.trans.pp.common.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
public class OMDApplicationExceptionHandler implements ExceptionMapper<OMDApplicationException> {

    /**
     * @param OMDApplicationException
     * @return Response
     */
    public Response toResponse(final OMDApplicationException omdApplicationException) {
        // Commented and added BAD REQUEST Response Code for getting the Request
        // code as 400
        // instead of 204 which is being converted to 200 when it is returned to
        // the RMDWeb Module
        // ResponseBuilder rb = Response.status(Response.Status.NO_CONTENT);
        final ResponseBuilder rb = Response.status(Response.Status.BAD_REQUEST);

        return rb.entity(new OMDException(omdApplicationException)).type(MediaType.APPLICATION_XML).build();
    }
}

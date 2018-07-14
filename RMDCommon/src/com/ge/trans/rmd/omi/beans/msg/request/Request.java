/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   Request.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */
package com.ge.trans.rmd.omi.beans.msg.request;

import com.ge.trans.rmd.omi.beans.BaseVO;

public abstract class Request extends BaseVO {

    private static final long serialVersionUID = -7814844456870447976L;
    private Long requestID;

    /**
     * @return the requestID
     */
    public Long getRequestID() {
        return requestID;
    }

    /**
     * @param pRequestID
     *            the requestID to set
     */
    public void setRequestID(Long pRequestID) {
        requestID = pRequestID;
    }

}

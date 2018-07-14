package com.ge.trans.eoa.services.heatmap.service.valueobjects;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Aug 10, 2017
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class FaultVO extends BaseVO {

    static final long serialVersionUID = 94328652156L;
    private String customerId;
    private String strFaultCode;
    private String strFaultDescription;
    private String strSubId;
    private String strFaultObjId;

    /**
     * @return the strFaultCode
     */
    public String getStrFaultCode() {
        return strFaultCode;
    }

    /**
     * @param strFaultCode
     *            the strFaultCode to set
     */
    public void setStrFaultCode(final String strFaultCode) {
        this.strFaultCode = strFaultCode;
    }

    /**
     * @return the strFaultDescription
     */
    public String getStrFaultDescription() {
        return strFaultDescription;
    }

    /**
     * @param strFaultDescription
     *            the strFaultDescription to set
     */
    public void setStrFaultDescription(final String strFaultDescription) {
        this.strFaultDescription = strFaultDescription;
    }

    /**
     * @return the strSubId
     */
    public String getStrSubId() {
        return strSubId;
    }

    /**
     * @param strSubId
     *            the strSubId to set
     */
    public void setStrSubId(String strSubId) {
        this.strSubId = strSubId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    
    /**
     * @return the strFaultObjId
     */
    public String getStrFaultObjId() {
        return strFaultObjId;
    }

    /**
     * @param strFaultObjId the strFaultObjId to set
     */
    public void setStrFaultObjId(String strFaultObjId) {
        this.strFaultObjId = strFaultObjId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject())
                .append("strFaultCode", strFaultCode)
                .append("strFaultDescription", strFaultDescription)
                .append("strSubId", strSubId).append("customerId", customerId)
                .toString();
    }
}

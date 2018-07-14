package com.ge.trans.eoa.services.asset.service.valueobjects;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Nov 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Value objects for closing a case
 * @History :
 ******************************************************************************/
public class CloseCaseVO extends BaseVO {

    private static final long serialVersionUID = 12376664L;
    /**
     * Variable for storing case id
     */
    private String strCaseID;
    /*
     * Variable for storing the user alias.
     */
    private String struserAlias;

    /**
     * @Author:
     * @return String
     * @Description: return case id
     */
    public String getStrCaseID() {
        return strCaseID;
    }

    /**
     * @Author:
     * @param strCaseID
     * @Description: set Case id
     */
    public void setStrCaseID(final String strCaseID) {
        this.strCaseID = strCaseID;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strCaseID", strCaseID).toString();
    }

    /**
     * @Author:
     * @param struserAlias
     * @Description: set User Alias
     */
    public String getStruserAlias() {
        return struserAlias;
    }

    /**
     * @Author:
     * @param struserAlias
     * @Description: get User Alias
     */
    public void setStruserAlias(String struserAlias) {
        this.struserAlias = struserAlias;
    }
}

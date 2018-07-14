package com.ge.trans.eoa.services.tools.fault.service.valueobjects;

import java.util.ArrayList;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

@SuppressWarnings("unchecked")
public class FaultMobileServiceVO {

    static final long serialVersionUID = 3377651144L;
    private int totalRecords = 0;
    private ArrayList arlFaultData = null;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * @return the arlFaultData
     */
    public ArrayList getArlFaultData() {
        return arlFaultData;
    }

    /**
     * @param arlFaultData
     *            the arlFaultData to set
     */
    public void setArlFaultData(final ArrayList arlFaultData) {
        this.arlFaultData = arlFaultData;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("arlFaultData", arlFaultData).toString();
    }
    
    public static ToStringStyle getToStringStyleObject() {
        StandardToStringStyle stdToStringStyle = new StandardToStringStyle();
        stdToStringStyle.setUseIdentityHashCode(false);
        stdToStringStyle.setContentStart("{");
        stdToStringStyle.setContentEnd("}");
        stdToStringStyle.setFieldSeparator(";");
        return stdToStringStyle;
    }
}

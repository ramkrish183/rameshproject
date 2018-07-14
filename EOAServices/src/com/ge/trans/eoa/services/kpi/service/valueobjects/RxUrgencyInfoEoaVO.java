package com.ge.trans.eoa.services.kpi.service.valueobjects;

public class RxUrgencyInfoEoaVO {
    /*
     * urgency type of rx. ex: red, yellow, white
     */
    private String rxUrgencyType;
    /*
     * count for the urgency type
     */
    private Long rxCount;

    public String getRxUrgencyType() {
        return rxUrgencyType;
    }

    public void setRxUrgencyType(final String rxUrgencyType) {
        this.rxUrgencyType = rxUrgencyType;
    }

    public Long getRxCount() {
        return rxCount;
    }

    public void setRxCount(final Long rxCount) {
        this.rxCount = rxCount;
    }

}

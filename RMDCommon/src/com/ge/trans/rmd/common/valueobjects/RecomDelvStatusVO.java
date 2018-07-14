package com.ge.trans.rmd.common.valueobjects;

import java.util.Date;

public class RecomDelvStatusVO extends BaseVO {

    /**
     *
     */

    private Date delvDate;
    private Long recomDelvTorecom;
    private String serviceReqIdStatus;

    public Date getDelvDate() {
        return delvDate;
    }

    public void setDelvDate(Date delvDate) {
        this.delvDate = delvDate;
    }

    public Long getRecomDelvTorecom() {
        return recomDelvTorecom;
    }

    public void setRecomDelvTorecom(Long recomDelvTorecom) {
        this.recomDelvTorecom = recomDelvTorecom;
    }

    public String getServiceReqIdStatus() {
        return serviceReqIdStatus;
    }

    public void setServiceReqIdStatus(String serviceReqIdStatus) {
        this.serviceReqIdStatus = serviceReqIdStatus;
    }
}

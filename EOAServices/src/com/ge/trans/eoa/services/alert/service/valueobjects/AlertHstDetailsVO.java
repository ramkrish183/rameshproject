package com.ge.trans.eoa.services.alert.service.valueobjects;

import java.util.List;

public class AlertHstDetailsVO {

    private List<AlertHistoryDetailsVO> alertHistoryHdrlst;
    private String recordsTotal;

    public List<AlertHistoryDetailsVO> getAlertHistoryHdrlst() {
        return alertHistoryHdrlst;
    }

    public void setAlertHistoryHdrlst(
            List<AlertHistoryDetailsVO> alertHistoryHdrlst) {
        this.alertHistoryHdrlst = alertHistoryHdrlst;
    }

    public String getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(String recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

}

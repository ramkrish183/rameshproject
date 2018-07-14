package com.ge.trans.rmd.common.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 17, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public class CustLookupVO extends BaseVO {

    private String famPrivilege;
    private String metricsPrivilege;
    private String rxEmailCheck;
    private String dateFormat;
    private String configAlertFlg;
    private String flagMPData;
    
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getRxEmailCheck() {
        return rxEmailCheck;
    }

    public void setRxEmailCheck(String rxEmailCheck) {
        this.rxEmailCheck = rxEmailCheck;
    }

    private String customerId;

    public String getFamPrivilege() {
        return famPrivilege;
    }

    public void setFamPrivilege(String famPrivilege) {
        this.famPrivilege = famPrivilege;
    }

    public String getMetricsPrivilege() {
        return metricsPrivilege;
    }

    public void setMetricsPrivilege(String metricsPrivilege) {
        this.metricsPrivilege = metricsPrivilege;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getConfigAlertFlg() {
        return configAlertFlg;
    }

    public void setConfigAlertFlg(String configAlertFlg) {
        this.configAlertFlg = configAlertFlg;
    }

	public String getFlagMPData() {
		return flagMPData;
	}

	public void setFlagMPData(String flagMPData) {
		this.flagMPData = flagMPData;
	}

}

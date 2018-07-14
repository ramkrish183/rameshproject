package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class LastDownloadStatusResponseEoaVO extends BaseVO {

    private String custHeader;
    private String customerHeader;
    private String assetNumber;
    private String assetNoHeader;
    private String lstDownloadTm;
    private String lstDownloadHeader;
    private String state;
    private String stateHeader;
    private String vax;
    private String vaxHeader;
    private Date vaxDt;
    private String egu;
    private String eguHeader;
    private Date eguDt;

    private String inc;
    private String incHeader;
    private Date incDt;

    private String snp;
    private String snpHeader;
    private Date snpDt;

    public String getCustomerHeader() {
        return customerHeader;
    }

    public void setCustomerHeader(String customerHeader) {
        this.customerHeader = customerHeader;
    }

    public String getAssetNoHeader() {
        return assetNoHeader;
    }

    public void setAssetNoHeader(String assetNoHeader) {
        this.assetNoHeader = assetNoHeader;
    }

    public String getLstDownloadHeader() {
        return lstDownloadHeader;
    }

    public void setLstDownloadHeader(String lstDownloadHeader) {
        this.lstDownloadHeader = lstDownloadHeader;
    }

    public String getStateHeader() {
        return stateHeader;
    }

    public void setStateHeader(String stateHeader) {
        this.stateHeader = stateHeader;
    }

    public String getVaxHeader() {
        return vaxHeader;
    }

    public void setVaxHeader(String vaxHeader) {
        this.vaxHeader = vaxHeader;
    }

    public String getEguHeader() {
        return eguHeader;
    }

    public void setEguHeader(String eguHeader) {
        this.eguHeader = eguHeader;
    }

    public String getIncHeader() {
        return incHeader;
    }

    public void setIncHeader(String incHeader) {
        this.incHeader = incHeader;
    }

    public String getSnpHeader() {
        return snpHeader;
    }

    public void setSnpHeader(String snpHeader) {
        this.snpHeader = snpHeader;
    }

    public String getCustHeader() {
        return custHeader;
    }

    public void setCustHeader(String custHeader) {
        this.custHeader = custHeader;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getLstDownloadTm() {
        return lstDownloadTm;
    }

    public void setLstDownloadTm(String lstDownloadTm) {
        this.lstDownloadTm = lstDownloadTm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVax() {
        return vax;
    }

    public void setVax(String vax) {
        this.vax = vax;
    }

    public Date getVaxDt() {
        return vaxDt;
    }

    public void setVaxDt(Date vaxDt) {
        this.vaxDt = vaxDt;
    }

    public String getEgu() {
        return egu;
    }

    public void setEgu(String egu) {
        this.egu = egu;
    }

    public Date getEguDt() {
        return eguDt;
    }

    public void setEguDt(Date eguDt) {
        this.eguDt = eguDt;
    }

    public String getInc() {
        return inc;
    }

    public void setInc(String inc) {
        this.inc = inc;
    }

    public Date getIncDt() {
        return incDt;
    }

    public void setIncDt(Date incDt) {
        this.incDt = incDt;
    }

    public String getSnp() {
        return snp;
    }

    public void setSnp(String snp) {
        this.snp = snp;
    }

    public Date getSnpDt() {
        return snpDt;
    }

    public void setSnpDt(Date snpDt) {
        this.snpDt = snpDt;
    }

}

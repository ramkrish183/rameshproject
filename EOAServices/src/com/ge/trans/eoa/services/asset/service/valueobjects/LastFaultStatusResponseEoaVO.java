package com.ge.trans.eoa.services.asset.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class LastFaultStatusResponseEoaVO extends BaseVO {

    private String custHeader;
    private String customerHeader;
    private String assetNumber;
    private String assetNoHeader;
    private String lstEOAFaultHeader;
    private String lstEOAFault;
    private String lstESTPDownloadHeader;
    private String lstESTPDownload;
    private String lstPPATSMsgHeader;
    private String lstPPATSMsg;

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

    public String getLstEOAFaultHeader() {
        return lstEOAFaultHeader;
    }

    public void setLstEOAFaultHeader(String lstEOAFaultHeader) {
        this.lstEOAFaultHeader = lstEOAFaultHeader;
    }

    public String getLstEOAFault() {
        return lstEOAFault;
    }

    public void setLstEOAFault(String lstEOAFault) {
        this.lstEOAFault = lstEOAFault;
    }

    public String getLstESTPDownloadHeader() {
        return lstESTPDownloadHeader;
    }

    public void setLstESTPDownloadHeader(String lstESTPDownloadHeader) {
        this.lstESTPDownloadHeader = lstESTPDownloadHeader;
    }

    public String getLstESTPDownload() {
        return lstESTPDownload;
    }

    public void setLstESTPDownload(String lstESTPDownload) {
        this.lstESTPDownload = lstESTPDownload;
    }

    public String getLstPPATSMsgHeader() {
        return lstPPATSMsgHeader;
    }

    public void setLstPPATSMsgHeader(String lstPPATSMsgHeader) {
        this.lstPPATSMsgHeader = lstPPATSMsgHeader;
    }

    public String getLstPPATSMsg() {
        return lstPPATSMsg;
    }

    public void setLstPPATSMsg(String lstPPATSMsg) {
        this.lstPPATSMsg = lstPPATSMsg;
    }

}

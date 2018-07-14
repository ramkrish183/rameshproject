package com.ge.trans.rmd.asset.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author : Rajesh,iGate Patni
 * @Version : 1.0
 * @Date Created: Feb 14, 2012
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class AssetLocatorVO extends BaseVO {

    private String assetNumber;
    private String assetGrpName;
    private String customerId;
    private String latitude;
    private String longitude;
    private String latlonSource;
    private Date maxOccurTime;
    private String assetSeqId;
    private String fleetObjId;
    private String modelObjId;
    // modelName,lstFaultdt,lstPrcddt added - OMD Performance - 17 Dec 2014
    private String modelName;
    private Date latlongDate;
    private Date lstFaultdt;
    private Date lstPrcddt;

    // added for asset overview getLastFaultStatus//

    private Date lstEOAFaultHeader;
    private Date lstPPATSMsgHeader;
    private Date lstESTPDownloadHeader;

    // added for asset overview getFaultStatus //

    private Date dtLastFaultResetTime;
    private Date dtLastFaultReserved;
    private Date dtLastHealthChkRequest;
    private Date dtLastKeepAliveMsgRevd;
    private boolean isLastFault = false;

    private Date lstFalultDateCell;

    private String Services;
    private String nextScheduledRun;
    private String lastToolRun;
    private String lastRecord;
    
    

	public String getLastRecord() {
		return lastRecord;
	}

	public void setLastRecord(String lastRecord) {
		this.lastRecord = lastRecord;
	}

	public String getLastToolRun() {
		return lastToolRun;
	}

	public void setLastToolRun(String lastToolRun) {
		this.lastToolRun = lastToolRun;
	}

	public String getServices() {
		return Services;
	}

	public void setServices(String services) {
		Services = services;
	}

	public String getNextScheduledRun() {
		return nextScheduledRun;
	}

	public void setNextScheduledRun(String nextScheduledRun) {
		this.nextScheduledRun = nextScheduledRun;
	}

	public Date getLatlongDate() {
        return latlongDate;
    }

    public void setLatlongDate(Date latlongDate) {
        this.latlongDate = latlongDate;
    }

    public String getLatlonSource() {
        return latlonSource;
    }

    public void setLatlonSource(String latlonSource) {
        this.latlonSource = latlonSource;
    }

    public Date getLstFalultDateCell() {
        return lstFalultDateCell;
    }

    public void setLstFalultDateCell(Date lstFalultDateCell) {
        this.lstFalultDateCell = lstFalultDateCell;
    }

    /**
     * @return
     */
    public boolean isLastFault() {
        return isLastFault;
    }

    /**
     * @param isLastFault
     */
    public void setLastFault(boolean isLastFault) {
        this.isLastFault = isLastFault;
    }

    public String getAssetSeqId() {
        return assetSeqId;
    }

    public void setAssetSeqId(String assetSeqId) {
        this.assetSeqId = assetSeqId;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getAssetGrpName() {
        return assetGrpName;
    }

    public void setAssetGrpName(String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

    public Date getMaxOccurTime() {
        return maxOccurTime;
    }

    public void setMaxOccurTime(Date maxOccurTime) {
        this.maxOccurTime = maxOccurTime;
    }

    public String getFleetObjId() {
        return fleetObjId;
    }

    public void setFleetObjId(String fleetObjId) {
        this.fleetObjId = fleetObjId;
    }

    public String getModelObjId() {
        return modelObjId;
    }

    public void setModelObjId(String modelObjId) {
        this.modelObjId = modelObjId;
    }

    public Date getLstFaultdt() {
        return lstFaultdt;
    }

    public void setLstFaultdt(Date lstFaultdt) {
        this.lstFaultdt = lstFaultdt;
    }

    public Date getLstPrcddt() {
        return lstPrcddt;
    }

    public void setLstPrcddt(Date lstPrcddt) {
        this.lstPrcddt = lstPrcddt;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Date getLstEOAFaultHeader() {
        return lstEOAFaultHeader;
    }

    public void setLstEOAFaultHeader(Date lstEOAFaultHeader) {
        this.lstEOAFaultHeader = lstEOAFaultHeader;
    }

    public Date getLstESTPDownloadHeader() {
        return lstESTPDownloadHeader;
    }

    public void setLstESTPDownloadHeader(Date lstESTPDownloadHeader) {
        this.lstESTPDownloadHeader = lstESTPDownloadHeader;
    }

    public Date getLstPPATSMsgHeader() {
        return lstPPATSMsgHeader;
    }

    public void setLstPPATSMsgHeader(Date lstPPATSMsgHeader) {
        this.lstPPATSMsgHeader = lstPPATSMsgHeader;
    }

    /**
     * @return the dtLastFaultResetTime
     */
    public Date getDtLastFaultResetTime() {
        return dtLastFaultResetTime;
    }

    /**
     * @param dtLastFaultResetTime
     *            the dtLastFaultResetTime to set
     */
    public void setDtLastFaultResetTime(Date dtLastFaultResetTime) {
        this.dtLastFaultResetTime = dtLastFaultResetTime;
    }

    /**
     * @return the dtLastFaultReserved
     */
    public Date getDtLastFaultReserved() {
        return dtLastFaultReserved;
    }

    /**
     * @param dtLastFaultReserved
     *            the dtLastFaultReserved to set
     */
    public void setDtLastFaultReserved(Date dtLastFaultReserved) {
        this.dtLastFaultReserved = dtLastFaultReserved;
    }

    /**
     * @return the dtLastHealthChkRequest
     */
    public Date getDtLastHealthChkRequest() {
        return dtLastHealthChkRequest;
    }

    /**
     * @param dtLastHealthChkRequest
     *            the dtLastHealthChkRequest to set
     */
    public void setDtLastHealthChkRequest(Date dtLastHealthChkRequest) {
        this.dtLastHealthChkRequest = dtLastHealthChkRequest;
    }

    /**
     * @return the dtLastKeepAliveMsgRevd
     */
    public Date getDtLastKeepAliveMsgRevd() {
        return dtLastKeepAliveMsgRevd;
    }

    /**
     * @param dtLastKeepAliveMsgRevd
     *            the dtLastKeepAliveMsgRevd to set
     */
    public void setDtLastKeepAliveMsgRevd(Date dtLastKeepAliveMsgRevd) {
        this.dtLastKeepAliveMsgRevd = dtLastKeepAliveMsgRevd;
    }

    @Override
    public String toString() {
        String value = "Fleet Testing assetNumber,assetGrpName,customerId,latitude,longitude,maxOccurTime,assetSeqId,fleetObjId,modelObjId ::"
                + assetNumber.toString() + assetGrpName.toString() + customerId.toString() + latitude.toString()
                + longitude.toString() + maxOccurTime.toString() + assetSeqId.toString() + fleetObjId.toString()
                + modelObjId.toString() + "\n";
        return value;
    }

}

package com.ge.trans.rmd.common.valueobjects;

public class SoftwareHistoryVO {

	protected String dataSyncHdr;
	protected String dataSyncRd;
	protected String deviceReporting;
	protected String sdisVersion;
	protected String dataSoftwarewasReported;
	protected String active;
	protected String configurationFile;
	protected String updatedBy;
	public String getDataSyncHdr() {
		return dataSyncHdr;
	}
	public void setDataSyncHdr(String dataSyncHdr) {
		this.dataSyncHdr = dataSyncHdr;
	}
	public String getDataSyncRd() {
		return dataSyncRd;
	}
	public void setDataSyncRd(String dataSyncRd) {
		this.dataSyncRd = dataSyncRd;
	}
	public String getDeviceReporting() {
		return deviceReporting;
	}
	public void setDeviceReporting(String deviceReporting) {
		this.deviceReporting = deviceReporting;
	}
	public String getSdisVersion() {
		return sdisVersion;
	}
	public void setSdisVersion(String sdisVersion) {
		this.sdisVersion = sdisVersion;
	}
	public String getDataSoftwarewasReported() {
		return dataSoftwarewasReported;
	}
	public void setDataSoftwarewasReported(String dataSoftwarewasReported) {
		this.dataSoftwarewasReported = dataSoftwarewasReported;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getConfigurationFile() {
		return configurationFile;
	}
	public void setConfigurationFile(String configurationFile) {
		this.configurationFile = configurationFile;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Override
	public String toString() {
		return "SoftwareHistoryVO [dataSyncHdr=" + dataSyncHdr + ", dataSyncRd=" + dataSyncRd + ", deviceReporting="
				+ deviceReporting + ", sdisVersion=" + sdisVersion + ", dataSoftwarewasReported="
				+ dataSoftwarewasReported + ", active=" + active + ", configurationFile=" + configurationFile
				+ ", updatedBy=" + updatedBy + "]";
	}
	
	
	
	
}

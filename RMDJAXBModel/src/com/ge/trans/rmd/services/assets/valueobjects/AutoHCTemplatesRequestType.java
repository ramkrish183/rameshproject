package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autoHCTemplatesRequestType", propOrder = { "ctrlConfig", "configFile"})

@XmlRootElement
public class AutoHCTemplatesRequestType {

	protected String ctrlConfig;
	protected String configFile;
	public String getCtrlConfig() {
		return ctrlConfig;
	}
	public void setCtrlConfig(String ctrlConfig) {
		this.ctrlConfig = ctrlConfig;
	}
	public String getConfigFile() {
		return configFile;
	}
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
	public AutoHCTemplatesRequestType(){}
	
	public AutoHCTemplatesRequestType(String ctrlConfig, String configFile) {
		super();
		this.ctrlConfig = ctrlConfig;
		this.configFile = configFile;
	}
	
}

package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "vehCfgTemplateResponseType", propOrder = {
			"objId",
			"configFile",
			"template",
			"version",
			"title",
			"status",
			"cntrlCnfg",
			"offboardStatus",
			"onboardStatus",
			"vehStatusObjId"
	})


@XmlRootElement
public class VehCfgTemplateResponseType {

		@XmlElement(required = true)
		protected String objId;
		@XmlElement(required = true)
		protected String configFile;
		@XmlElement(required = true)
		protected String template;
		@XmlElement(required = true)
		protected String version;
		@XmlElement(required = true)
		protected String title;
		@XmlElement(required = true)
		protected String status;
		@XmlElement(required = true)
		protected String cntrlCnfg;
		@XmlElement(required = true)
		protected String offboardStatus;
		@XmlElement(required = true)
		protected String onboardStatus;
		@XmlElement(required = true)
        protected String vehStatusObjId;
		public String getCntrlCnfg() {
			return cntrlCnfg;
		}
		public void setCntrlCnfg(String cntrlCnfg) {
			this.cntrlCnfg = cntrlCnfg;
		}
		public String getObjId() {
			return objId;
		}
		public void setObjId(String objId) {
			this.objId = objId;
		}
		public String getConfigFile() {
			return configFile;
		}
		public void setConfigFile(String configFile) {
			this.configFile = configFile;
		}
		public String getTemplate() {
			return template;
		}
		public void setTemplate(String template) {
			this.template = template;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getOffboardStatus() {
			return offboardStatus;
		}
		public void setOffboardStatus(String offboardStatus) {
			this.offboardStatus = offboardStatus;
		}
		public String getOnboardStatus() {
			return onboardStatus;
		}
		public void setOnboardStatus(String onboardStatus) {
			this.onboardStatus = onboardStatus;
		}
        public String getVehStatusObjId() {
            return vehStatusObjId;
        }
        public void setVehStatusObjId(String vehStatusObjId) {
            this.vehStatusObjId = vehStatusObjId;
        }
		
}

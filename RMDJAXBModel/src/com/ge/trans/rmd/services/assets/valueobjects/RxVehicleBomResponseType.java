package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rxVehicleBomResponseType", propOrder = { "assetList","disabledRxList","bomMismatchRxList" })

@XmlRootElement
public class RxVehicleBomResponseType {

    
        @XmlElement(required = true)
        protected List<String> assetList;
        @XmlElement(required = true)
        protected List<String> disabledRxList;
        @XmlElement(required = true)
        protected List<String> bomMismatchRxList;

        public List<String> getAssetList() {
            return assetList;
        }

        public void setAssetList(List<String> assetList) {
            this.assetList = assetList;
        }

        public List<String> getDisabledRxList() {
            return disabledRxList;
        }

        public void setDisabledRxList(List<String> disabledRxList) {
            this.disabledRxList = disabledRxList;
        }

        public List<String> getBomMismatchRxList() {
            return bomMismatchRxList;
        }

        public void setBomMismatchRxList(List<String> bomMismatchRxList) {
            this.bomMismatchRxList = bomMismatchRxList;
        }

        
        
        
    
}

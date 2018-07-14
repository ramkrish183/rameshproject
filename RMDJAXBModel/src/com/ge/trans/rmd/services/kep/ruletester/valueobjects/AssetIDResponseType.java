package com.ge.trans.rmd.services.kep.ruletester.valueobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssetIDResponseType {

    private String assetID;

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

}

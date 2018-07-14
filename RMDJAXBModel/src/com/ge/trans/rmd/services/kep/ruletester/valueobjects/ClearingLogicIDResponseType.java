package com.ge.trans.rmd.services.kep.ruletester.valueobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClearingLogicIDResponseType {

    private String clearingLogicID;

    public String getClearingLogicID() {
        return clearingLogicID;
    }

    public void setClearingLogicID(String clearingLogicID) {
        this.clearingLogicID = clearingLogicID;
    }

}

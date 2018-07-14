package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FaultTO implements Serializable {

    /*
     * Stores the fault details
     */
    private ArrayList faultData = null;

    /*
     * Stores the Details for Sample No check
     */
    private HashMap hmSampleNoDetails = new HashMap();

    /*
     * Gets the fault details
     */
    public ArrayList getFaultData() {
        return faultData;
    }

    /*
     * Sets the fault details
     */
    public void setFaultData(final ArrayList faultData) {
        this.faultData = faultData;
    }

    /*
     * Sets the Details for Sample No in HashMap
     */
    public void setSampleDetails(final HashMap hmSampleNoDetails) {
        this.hmSampleNoDetails = hmSampleNoDetails;
    }

    /*
     * Gets the Details for Sample No in HashMap
     */
    public HashMap getSampleDetails() {
        return hmSampleNoDetails;
    }
}
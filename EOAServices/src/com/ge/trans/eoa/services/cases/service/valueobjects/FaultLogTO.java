package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.io.Serializable;

/*******************************************************************************
 * Title:		FaultLogTO.java
 * 
 * Description:	Used to store the array of fault log data as well as controller 
 * 				details and transfer the data between across layers.
 * 
 * Copyright:   Copyright (c) 2001
 * 
 * Company: 	General Electric(Remote Monitoring and Diagnostics)
 * 
 * @author		:	iGate		
 * @Created Date:	1-Feb-08
 * @version		:	1.0
 * 				
 * Revision History		  :			
 * Last Modification Date :
 * Last Modified By       :
 * Last Modification      :
 * 
 ******************************************************************************/

import java.util.ArrayList;
import java.util.HashMap;

public class FaultLogTO implements Serializable {

    /*
     * Stores the controller Id
     */
    private int controllerId = 0;

    /*
     * Stores the controller description
     */
    private String controllerDesc = null;

    /*
     * Stores the fault data
     */
    private ArrayList faultData = null;

    /*
     * Stores the Notch value
     */
    private String strNotch = null;

    /*
     * Stores the FaultStrategyID
     */
    String strFaultStrategyID = null;

    /*
     * Stores the Reocrd Type
     */
    String strRecordType = null;

    /*
     * Stores the Fault Origin
     */
    String strFaultOrigin = null;

    /*
     * Stores the Non Displayable cols
     */
    HashMap hmNonDisplayableCols = new HashMap();

    /*
     * Stores the Parent Flag
     */
    boolean blnParentFlag;

    /*
     * Stores the Link Id
     */
    int intLinkId = 0;

    /*
     * Stores the Fault Obj ID
     */
    String strFaultObjId = null;

    /*
     * Stores the Rule Details for OIL
     */
    String strRuleDetails = null;

    /*
     * Stores the Count of faults for OIL
     */
    int intFaultCount = 0;

    /*
     * Stores the TypeCheck for OIL
     */
    String strTypeCheck = null;

    /*
     * Gets the controller id
     */
    public int getControllerId() {
        return controllerId;
    }

    /*
     * Sets the controller id
     */
    public void setControllerId(final int controllerId) {
        this.controllerId = controllerId;
    }

    /*
     * Gets the controller description
     */
    public String getControllerDesc() {
        return controllerDesc;
    }

    /*
     * Sets the controller description
     */
    public void setControllerDesc(final String controllerDesc) {
        this.controllerDesc = controllerDesc;
    }

    /*
     * Gets the fault data
     */
    public ArrayList getFaultData() {
        return faultData;
    }

    /*
     * Sets the fault data
     */
    public void setFaultData(final ArrayList faultData) {
        this.faultData = faultData;
    }

    /*
     * Gets the Notch Value
     */
    public String getNotch() {
        return strNotch;
    }

    /*
     * Sets the Notch Value
     */
    public void setNotch(final String strNotch) {
        this.strNotch = strNotch;
    }

    /*
     * Gets the FaultStrategyID
     */
    public String getFaultStrategyID() {
        return strFaultStrategyID;
    }

    /*
     * Sets the FaultStrategyID
     */
    public void setFaultStrategyID(final String strFaultStrategyID) {
        this.strFaultStrategyID = strFaultStrategyID;
    }

    /*
     * Gets the RecordType
     */
    public String getRecordType() {
        return strRecordType;
    }

    /*
     * Sets the RecordType
     */
    public void setRecordType(final String strRecordType) {
        this.strRecordType = strRecordType;
    }

    /*
     * Gets the Fault Origin
     */
    public String getFaultOrigin() {
        return strFaultOrigin;
    }

    /*
     * Sets the Fault Origin
     */
    public void setFaultOrigin(final String strFaultOrigin) {
        this.strFaultOrigin = strFaultOrigin;
    }

    /*
     * Sets the NonDisplayable column's data in HashMap
     */
    public void setNonDisplayableCols(final String strColName, final String strValue) {
        hmNonDisplayableCols.put(strColName, strValue);
    }

    /*
     * Gets the NonDisplayable column's data in HashMap
     */
    public String getNonDisplayableCols(String strColName) {
        return (String) hmNonDisplayableCols.get(strColName);
    }

    /*
     * Gets the Parent Flag
     */
    public boolean getParentFlag() {
        return blnParentFlag;
    }

    /*
     * Sets the Parent Flag
     */
    public void setParentFlag(final boolean blnParentFlag) {
        this.blnParentFlag = blnParentFlag;
    }

    /*
     * Gets the Link ID
     */
    public int getLinkID() {
        return intLinkId;
    }

    /*
     * Sets the Link ID
     */
    public void setLinkID(final int intLinkId) {
        this.intLinkId = intLinkId;
    }

    /*
     * Gets the Fault Obj ID
     */
    public String getFaultObjid() {
        return strFaultObjId;
    }

    /*
     * Sets the Fault Obj ID
     */
    public void setFaultObjid(final String strFaultObjId) {
        this.strFaultObjId = strFaultObjId;
    }

    /*
     * Gets the Rule Details for OIL
     */
    public String getRuleDetails() {
        return strRuleDetails;
    }

    /*
     * Sets the Rule Details for OIL
     */
    public void setRuleDetails(final String strRuleDetails) {
        this.strRuleDetails = strRuleDetails;
    }

    /*
     * Gets the Count of faults for OIL
     */
    public int getFaultCount() {
        return intFaultCount;
    }

    /*
     * Sets the Count of faults for OIL
     */
    public void setFaultCount(final int intFaultCount) {
        this.intFaultCount = intFaultCount;
    }

    /*
     * Gets the TypeCheck for OIL
     */
    public String getTypeCheck() {
        return strTypeCheck;
    }

    /*
     * Sets the TypeCheck for OIL
     */
    public void setTypeCheck(final String strTypeCheck) {
        this.strTypeCheck = strTypeCheck;
    }
}
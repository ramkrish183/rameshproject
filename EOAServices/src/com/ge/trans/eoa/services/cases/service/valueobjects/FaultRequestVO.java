/** 
 *  
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.io.Serializable;

/*******************************************************************************
 * Title: FaultRequestVO.java Description: Copyright: Copyright (c) 2001
 * Company: General Electric(Remote Monitoring and Diagnostics)
 * 
 * @author : iGate
 * @Created Date: 18-Feb-08
 * @version : 1.0 Revision History : Last Modification Date : Last Modified By :
 *          Last Modification :
 ******************************************************************************/
public class FaultRequestVO implements Serializable {

    /*
     * Stores the Case ID
     */
    private String strCaseId = null;

    /*
     * Stores the Serial No of the vehicle
     */
    private String strSerialNo = null;

    /*
     * Stores the Vehicle Header
     */
    private String strVehicleHeader = null;

    /*
     * Stores the Vehicle Header Customer
     */
    private String strVehicleHeaderCust = null;

    /*
     * Stores the Customer name
     */
    private String strCustomer = null;

    /*
     * Stores the Bad Customer value
     */
    private String strBadActor = null;

    /*
     * Stores the Case Accepted Value
     */
    private String strCaseAccepted = null;

    /*
     * Stores the Case Status
     */
    private String strCaseStatus = null;

    /*
     * Stores the No of days selected
     */
    private String strNoOfDays = null;

    /*
     * Stores the family
     */
    private String strFamily = null;

    /*
     * Stores the selected value of JDPAD/CBR/Critical Fault
     */
    private String strJdpadCbrCrit = null;

    /*
     * Stores the Controller Source ID
     */
    private String strControllerSourceId = null;

    /*
     * Stores the Screen
     */
    private String strScreen = null;

    /*
     * Stores the From value selected in the Screen
     */
    private String strCaseFrom = null;

    /*
     * Stores the HC value
     */
    private String strHC = null;

    /*
     * Stores the EGU value
     */
    private String strEGU = null;

    /*
     * Stores the SortOrder
     */
    private String strSortOrder = null;

    /*
     * Stores the Pagination
     */
    private String strPaginationHit = null;

    /*
     * Stores the Pagination Counter
     */
    private String strPaginationCounter = null;

    /*
     * Stores the Case Type
     */
    private String strCaseType = null;

    /*
     * Stores the Notch value
     */
    private String strNotch = null;

    /*
     * Stores the Filter value
     */
    private String strFilter = null;

    /*
     * Stores the View All value
     */
    private String strViewAll = null;

    /*
     * Stores the Model Name
     */
    private String strModel = null;

    /* Added by IGS on 08/26/2009 for Faultcode search.. Start */

    /*
     * Stores the FaultCode
     */

    private String strFaultCode = null;

    /* Added by IGS on 08/26/2009 for Faultcode search.. End */

    // FOR OIL
    /*
     * Stores the selected customer in Oil Screen
     */
    private String strOilCustomer = null;

    /*
     * Stores the selected fleet in Oil Screen
     */
    private String strFleet = null;

    /*
     * Stores the selected Status in Oil Screen
     */
    private String strStatus = null;

    /*
     * Stores the selected Road No in Oil Screen
     */
    private String strRoadNo = null;

    /*
     * Stores the selected Vehicle Header in Oil Screen
     */
    private String strOilVehicleHeader = null;
    // END - OIL SCREEN

    /*
     * Stores the value of Start Date in View Vehicle Fault
     */
    private String strVvfStartDate = null;

    /*
     * Stores the value of End Date in View Vehicle Fault
     */
    private String strVvfEndDate = null;

    /*
     * Stores the value of the selected Radio(No of Days/ Start or From) in View
     * Vehicle Fault
     */
    private String strVvfRadioSelected = null;

    /*
     * Stores the value of Initial Load
     */
    private String strInitialLoad = null;

    /*
     * Stores the Entry value for OIL Screen
     */
    private String strEntryFrom = null;

    /*
     * Stores the Loco Faults/QNX Equipment Radio selected for View Vehicle
     * Faults Screen
     */
    private String strVvfViewRadioSelected = null;

    /*
     * Stores the value of RuleDetails for OIL
     */
    private String strRuleDetails = null;

    /*
     * Stores the Case ID for Rule Pop Up Page for OIL
     */
    private String strCaseIDForRule = null;

    /*
     * Stores the Fault ID for Complex Rule Pop Up Page for OIL
     */
    private String strFaultID = null;

    private String strcCompLst = null;

    private String stdt = null;

    private String enddt = null;

    private String strCustomerID = null;

    /*
     * Stores the Controller Config for OIL
     */
    private String strCtrlCfg = null;

    /*
     * Stores the Vehicle ID for OIL
     */
    private String strVehicleID = null;

    /*
     * Stores the Loco ID for OIL
     */
    private String strLocoID = null;

    /*
     * Stores the Rule Definition ID
     */
    private String strRuleDefID = null;

    private String strViewName = null;

    private String hideL3 = null;

    private String strMaxUpdatedDate = null;

    private String strCreationTime = null;

    private int caseObjID = 0;

    private String strMinObjid = null;

    private String strMaxObjid = null;
    
    private String userTimeZone;
	private String userTimeZoneCode;

    public String getStrMinObjid() {
        return strMinObjid;
    }

    public void setStrMinObjid(String strMinObjid) {
        this.strMinObjid = strMinObjid;
    }

    public String getStrMaxObjid() {
        return strMaxObjid;
    }

    public void setStrMaxObjid(String strMaxObjid) {
        this.strMaxObjid = strMaxObjid;
    }

    public int getCaseObjID() {
        return caseObjID;
    }

    public void setCaseObjID(int caseObjID) {
        this.caseObjID = caseObjID;
    }

    public String getStrCreationTime() {
        return strCreationTime;
    }

    public void setStrCreationTime(String strCreationTime) {
        this.strCreationTime = strCreationTime;
    }

    public String getStrMaxUpdatedDate() {
        return strMaxUpdatedDate;
    }

    public void setStrMaxUpdatedDate(String strMaxUpdatedDate) {
        this.strMaxUpdatedDate = strMaxUpdatedDate;
    }

    public String getHideL3() {
        return hideL3;
    }

    public void setHideL3(String hideL3) {
        this.hideL3 = hideL3;
    }

    public String getStrViewName() {
        return strViewName;
    }

    public void setStrViewName(String strViewName) {
        this.strViewName = strViewName;
    }

    /*
     * Sets the Case Id
     */
    public void setCaseId(final String strCaseId) {
        this.strCaseId = strCaseId;
    }

    /*
     * Gets the Case Id
     */
    public String getCaseId() {
        return strCaseId;
    }

    /*
     * Sets the Serial No
     */
    public void setSerialNo(final String strSerialNo) {
        this.strSerialNo = strSerialNo;
    }

    /*
     * Gets the Serial No
     */
    public String getSerialNo() {
        return strSerialNo;
    }

    /*
     * Sets the Vehicle Header
     */
    public void setVehicleHeader(final String strVehicleHeader) {
        this.strVehicleHeader = strVehicleHeader;
    }

    /*
     * Gets the Vehicle Header
     */
    public String getVehicleHeader() {
        return strVehicleHeader;
    }

    /*
     * Sets the Vehicle Header Customer
     */
    public void setVehicleHeaderCustomer(final String strVehicleHeaderCust) {
        this.strVehicleHeaderCust = strVehicleHeaderCust;
    }

    /*
     * Gets the Vehicle Header Customer
     */
    public String getVehicleHeaderCustomer() {
        return strVehicleHeaderCust;
    }

    /*
     * Sets the Customer
     */
    public void setCustomer(final String strCustomer) {
        this.strCustomer = strCustomer;
    }

    /*
     * Gets the Customer
     */
    public String getCustomer() {
        return strCustomer;
    }

    /*
     * Sets the value of Bad Actor
     */
    public void setBadActor(final String strBadActor) {
        this.strBadActor = strBadActor;
    }

    /*
     * Gets the value of Bad Actor
     */
    public String getBadActor() {
        return strBadActor;
    }

    /*
     * Sets the Case Accepted value
     */
    public void setCaseAccepted(final String strCaseAccepted) {
        this.strCaseAccepted = strCaseAccepted;
    }

    /*
     * Gets the Case Accepted value
     */
    public String getCaseAccepted() {
        return strCaseAccepted;
    }

    /*
     * Sets the Case Status
     */
    public void setCaseStatus(final String strCaseStatus) {
        this.strCaseAccepted = strCaseStatus;
    }

    /*
     * Gets the Case Status
     */
    public String getCaseStatus() {
        return strCaseStatus;
    }

    /*
     * Sets the No of Days selected
     */
    public void setNoOfDays(final String strNoOfDays) {
        this.strNoOfDays = strNoOfDays;
    }

    /*
     * Gets the No of Days selected
     */
    public String getNoOfDays() {
        return strNoOfDays;
    }

    /*
     * Sets the family
     */
    public void setFamily(final String strFamily) {
        this.strFamily = strFamily;
    }

    /*
     * Gets the famiy
     */
    public String getFamily() {
        return strFamily;
    }

    /*
     * Sets the selected value of JDPAD/CBR/Critical Fault
     */
    public void setJdpadCbrCrit(final String strJdpadCbrCrit) {
        this.strJdpadCbrCrit = strJdpadCbrCrit;
    }

    /*
     * Gets the selected value of JDPAD/CBR/Critical Fault
     */
    public String getJdpadCbrCrit() {
        return strJdpadCbrCrit;
    }

    /*
     * Sets the Controller Source ID
     */
    public void setControllerSrcID(final String strControllerSourceId) {
        this.strControllerSourceId = strControllerSourceId;
    }

    /*
     * Gets the selected value of JDPAD/CBR/Critical Fault
     */
    public String getControllerSrcID() {
        return strControllerSourceId;
    }

    /*
     * Sets the Screen
     */
    public void setScreen(final String strScreen) {
        this.strScreen = strScreen;
    }

    /*
     * Gets the Screen
     */
    public String getScreen() {
        return strScreen;
    }

    /*
     * Sets the From value from the Screen
     */
    public void setCaseFrom(final String strCaseFrom) {
        this.strCaseFrom = strCaseFrom;
    }

    /*
     * Gets the From value from the Screen
     */
    public String getCaseFrom() {
        return strCaseFrom;
    }

    /*
     * Sets the HC value
     */
    public void setHC(final String strHC) {
        this.strHC = strHC;
    }

    /*
     * Gets the HC value
     */
    public String getHC() {
        return strHC;
    }

    /*
     * Sets the EGU value
     */
    public void setEGU(final String strEGU) {
        this.strEGU = strEGU;
    }

    /*
     * Gets the EGU value
     */
    public String getEGU() {
        return strEGU;
    }

    /*
     * Sets the SortOrder value
     */
    public void setSortOrder(final String strSortOrder) {
        this.strSortOrder = strSortOrder;
    }

    /*
     * Gets the SortOrder value
     */
    public String getSortOrder() {
        return strSortOrder;
    }

    /*
     * Sets the Pagination value
     */
    public void setPaginationHit(final String strPaginationHit) {
        this.strPaginationHit = strPaginationHit;
    }

    /*
     * Gets the Pagination value
     */
    public String getPaginationHit() {
        return strPaginationHit;
    }

    /*
     * Sets the Pagination Counter value
     */
    public void setPaginationCounter(final String strPaginationCounter) {
        this.strPaginationCounter = strPaginationCounter;
    }

    /*
     * Gets the Pagination Counter value
     */
    public String getPaginationCounter() {
        return strPaginationCounter;
    }

    /*
     * Sets the CaseType
     */
    public void setCaseType(final String strCaseType) {
        this.strCaseType = strCaseType;
    }

    /*
     * Gets the CaseType
     */
    public String getCaseType() {
        return strCaseType;
    }

    /*
     * Sets the Notch8 value
     */
    public void setNotch(final String strNotch) {
        this.strNotch = strNotch;
    }

    /*
     * Gets the Notch8 value
     */
    public String getNotch() {
        return strNotch;
    }

    /*
     * Sets the Filter value
     */
    public void setFilter(final String strFilter) {
        this.strFilter = strFilter;
    }

    /*
     * Gets the Filter value
     */
    public String getFilter() {
        return strFilter;
    }

    /*
     * Sets the View All value
     */
    public void setViewAll(final String strViewAll) {
        this.strViewAll = strViewAll;
    }

    /*
     * Gets the View All value
     */
    public String getViewAll() {
        return strViewAll;
    }

    /*
     * Sets the Model Name
     */
    public void setModel(final String strModel) {
        this.strModel = strModel;
    }

    /*
     * Gets the Model Name
     */
    public String getModel() {
        return strModel;
    }

    /*
     * Sets the value of Start Date in View Vehicle Fault
     */
    public void setVvfStartDate(final String strVvfStartDate) {
        this.strVvfStartDate = strVvfStartDate;
    }

    /*
     * Gets the value of Start Date in View Vehicle Fault
     */
    public String getVvfStartDate() {
        return strVvfStartDate;
    }

    /*
     * Sets the value of End Date in View Vehicle Fault
     */
    public void setVvfEndDate(final String strVvfEndDate) {
        this.strVvfEndDate = strVvfEndDate;
    }

    /*
     * Gets the value of End Date in View Vehicle Fault
     */
    public String getVvfEndDate() {
        return strVvfEndDate;
    }

    /*
     * Sets the value of the selected Radio(No of Days/ Start or From) in View
     * Vehicle Fault
     */
    public void setRadioSelected(final String strVvfRadioSelected) {
        this.strVvfRadioSelected = strVvfRadioSelected;
    }

    /*
     * Gets the value of the selected Radio(No of Days/ Start or From) in View
     * Vehicle Fault
     */
    public String getRadioSelected() {
        return strVvfRadioSelected;
    }

    /*
     * Sets the value of Initial Load for Data Screen
     */
    public void setInitialLoad(final String strInitialLoad) {
        this.strInitialLoad = strInitialLoad;
    }

    /*
     * Gets the value of Initial Load for Data Screen
     */
    public String getInitialLoad() {
        return strInitialLoad;
    }

    /* Added by IGS on 08/26/2009 for Faultcode search.. Start */
    /*
     * Sets the value for FaultCode in DataScreen and ViewvehicleFault Screen
     */
    public void setFaultCode(final String strFaultCode) {
        this.strFaultCode = strFaultCode;
    }

    /*
     * Gets the Value of FaultCode in DataScreen and ViewvehicleFault Screen
     */
    public String getFaultCode() {
        return strFaultCode;
    }
    /* Added by IGS on 08/26/2009 for Faultcode search.. End */

    /*
     * Sets the Fleet selected for Oil Screen
     */
    public void setFleet(final String strFleet) {
        this.strFleet = strFleet;
    }

    /*
     * Gets the Fleet selected for Oil Screen
     */
    public String getFleet() {
        return strFleet;
    }

    /*
     * Sets the customer selected for Oil Screen
     */
    public void setOilCustomer(final String strOilCustomer) {
        this.strOilCustomer = strOilCustomer;
    }

    /*
     * Gets the customer selected for Oil Screen
     */
    public String getOilCustomer() {
        return strOilCustomer;
    }

    /*
     * Sets the Road No selected for Oil Screen
     */
    public void setRoadNo(final String strRoadNo) {
        this.strRoadNo = strRoadNo;
    }

    /*
     * Gets the Road No selected for Oil Screen
     */
    public String getRoadNo() {
        return strRoadNo;
    }

    /*
     * Sets the Status selected for Oil Screen
     */
    public void setStatus(final String strStatus) {
        this.strStatus = strStatus;
    }

    /*
     * Gets the Status selected for Oil Screen
     */
    public String getStatus() {
        return strStatus;
    }

    /*
     * Sets the Status selected for Oil Screen
     */
    public void setOilVehicleHeader(final String strOilVehicleHeader) {
        this.strOilVehicleHeader = strOilVehicleHeader;
    }

    /*
     * Gets the Status selected for Oil Screen
     */
    public String getOilVehicleHeader() {
        return strOilVehicleHeader;
    }

    /*
     * Gets the Entry point for Oil Screen
     */
    public String getEntryFrom() {
        return strEntryFrom;
    }

    /*
     * Sets the Entry point for Oil Screen
     */
    public void setEntryFrom(final String strEntryFrom) {
        this.strEntryFrom = strEntryFrom;
    }

    /*
     * Gets the selected LocoFaults/Radio's value for View Vehicle Fault
     */

    public String getVVfViewRadioSelected() {
        return strVvfViewRadioSelected;
    }

    /*
     * Sets the Loco Fault Radio's value for View Vehicle Fault
     */

    public void setVVfViewRadioSelected(final String strVvfViewRadioSelected) {
        this.strVvfViewRadioSelected = strVvfViewRadioSelected;
    }

    public String getEnddt() {
        return enddt;
    }

    public void setEnddt(final String enddt) {
        this.enddt = enddt;
    }

    public String getStdt() {
        return stdt;
    }

    public void setStdt(final String stdt) {
        this.stdt = stdt;
    }

    public String getStrcCompLst() {
        return strcCompLst;
    }

    public void setStrcCompLst(final String strcCompLst) {
        this.strcCompLst = strcCompLst;
    }

    /*
     * Gets the value of Rule Details for OIL
     */
    public String getRuleDetails() {
        return strRuleDetails;
    }

    /*
     * Sets the value of Rule Details for OIL
     */
    public void setRuleDetails(final String strRuleDetails) {
        this.strRuleDetails = strRuleDetails;
    }

    /*
     * Gets the value of Case ID for Rule Details Pop Up - OIL
     */
    public String getCaseIDForRule() {
        return strCaseIDForRule;
    }

    /*
     * Sets the value of Case ID for Rule Details Pop Up - OIL
     */
    public void setCaseIDForRule(final String strCaseIDForRule) {
        this.strCaseIDForRule = strCaseIDForRule;
    }

    /*
     * Gets the value of Fault ID for Complex Rule Details Pop Up - OIL
     */
    public String getFaultID() {
        return strFaultID;
    }

    /*
     * Sets the value of Fault ID for Complex Rule Details Pop Up - OIL
     */
    public void setFaultID(final String strFaultID) {
        this.strFaultID = strFaultID;
    }

    /*
     * Gets the value of Customer ID - OIL
     */
    public String getCustomerID() {
        return strCustomerID;
    }

    /*
     * Sets the value of Customer ID - OIL
     */
    public void setCustomerID(final String strCustomerID) {
        this.strCustomerID = strCustomerID;
    }

    /*
     * Gets the Controller Config for OIL
     */
    public String getCtrlCfg() {
        return strCtrlCfg;
    }

    /*
     * Sets the Controller Config for OIL
     */
    public void setCtrlCfg(final String strCtrlCfg) {
        this.strCtrlCfg = strCtrlCfg;
    }

    /*
     * Gets the Vehicle ID for OIL
     */
    public String getVehicleID() {
        return strVehicleID;
    }

    /*
     * Sets the Vehicle ID for OIL
     */
    public void setVehicleID(final String strVehicleID) {
        this.strVehicleID = strVehicleID;
    }

    public String getLocoID() {
        return strLocoID;
    }

    public void setLocoID(final String strLocoID) {
        this.strLocoID = strLocoID;
    }

    public String getRuleDefID() {
        return strRuleDefID;
    }

    public void setRuleDefID(final String strRuleDefID) {
        this.strRuleDefID = strRuleDefID;
    }

	public String getUserTimeZone() {
		return userTimeZone;
	}

	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}

	public String getUserTimeZoneCode() {
		return userTimeZoneCode;
	}

	public void setUserTimeZoneCode(String userTimeZoneCode) {
		this.userTimeZoneCode = userTimeZoneCode;
	}

}

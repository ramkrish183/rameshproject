package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.io.Serializable;
import java.util.HashMap;

/*******************************************************************************
 * Title: FaultDetailsTO.java Description: Used to store the fault details for
 * the particular controllers and transfer the data between across layers.
 * Copyright: Copyright (c) 2001 Company: General Electric(Remote Monitoring and
 * Diagnostics)
 * 
 * @author : iGate
 * @Created Date: 1-Feb-08
 * @version : 1.0 Revision History : Last Modification Date : Last Modified By :
 *          Last Modification :
 ******************************************************************************/

public class FaultDetailsTO implements Serializable {

    /*
     * Stores the fault param number
     */
    String strParamNumber = null;

    /*
     * Stores the fault data
     */
    String strData = null;

    /*
     * Stores the Upper Bound
     */
    String strUpperBound = null;

    /*
     * Stores the Lower Bound
     */
    String strLowerBound = null;

    /*
     * Stores the Header Width
     */
    String strWidth = null;

    /*
     * Stores the ResidualFlag for Basic MAP
     */
    String strResidualFlagMap = null;

    /*
     * Stores the ResidualFlag for Basic CCOP
     */
    String strResidualFlagCCOP = null;

    /*
     * Stores the ResidualFlag for Basic EWIP
     */
    String strResidualFlagEWIP = null;

    /*
     * Stores the ResidualFlag for Basic ELIP
     */
    String strResidualFlagELIP = null;

    /*
     * Stores the ResidualFlag for Basic EWIT
     */
    String strResidualFlagEWIT = null;

    /*
     * Stores the ResidualFlag for Basic EWOT
     */
    String strResidualFlagEWOT = null;

    /*
     * Stores the ResidualFlag for Basic ELIT
     */
    String strResidualFlagELIT = null;

    /*
     * Stores the ResidualFlag for Basic ELOT
     */
    String strResidualFlagELOT = null;

    /*
     * Stores the ResidualFlag for Basic MAT
     */
    String strResidualFlagMAT = null;

    /*
     * Stores the ResidualFlag for Basic PTLT
     */
    String strResidualFlagPTLT = null;

    /*
     * Stores the ResidualFlag for Basic PTRT
     */
    String strResidualFlagPTRT = null;

    /*
     * Stores the ResidualFlag for Engine FuelDemand
     */
    String strResidualFlagFuelDemand = null;

    /*
     * Stores the ResidualFlag for Engine TurboSpeed
     */
    String strResidualFlagTurboSpeed = null;

    /*
     * Stores the ResidualFlag for Engine AdvAngle
     */
    String strResidualFlagAdvAngle = null;

    /*
     * Stores the ResidualFlag for Engine DurAngle
     */
    String strResidualFlagDurAngle = null;

    /*
     * Stores the Proximity Description
     */
    String strProximityDesc = null;

    /*
     * Stores the Notch
     */
    String strNotch = null;

    /*
     * Stores the Hi/Low
     */
    String strColor = null;

    /*
     * Stores the DataToolTip
     */
    String strDataToolTip = null;

    HashMap hmNotch = new HashMap();

    /*
     * Gets the fault data
     */
    public String getData() {
        return strData;
    }

    /*
     * Sets the fault data
     */
    public void setData(final String strData) {
        this.strData = strData;
    }

    /*
     * Gets the fault param number
     */
    public String getParamNumber() {
        return strParamNumber;
    }

    /*
     * Sets the fault param number
     */
    public void setParamNumber(final String strParamNumber) {
        this.strParamNumber = strParamNumber;
    }

    /*
     * Gets the Upper Bound Value
     */
    public String getUpperBound() {
        return strUpperBound;
    }

    /*
     * Sets the Upper Bound Value
     */
    public void setUpperBound(final String strUpperBound) {
        this.strUpperBound = strUpperBound;
    }

    /*
     * Gets the Upper Bound Value
     */
    public String getLowerBound() {
        return strLowerBound;
    }

    /*
     * Sets the Upper Bound Value
     */
    public void setLowerBound(final String strLowerBound) {
        this.strLowerBound = strLowerBound;
    }

    /*
     * Gets the Width
     */
    public String getWidth() {
        return strWidth;
    }

    /*
     * Sets the decimal length of the fault data
     */
    public void setWidth(final String strWidth) {
        this.strWidth = strWidth;
    }

    /*
     * Gets the ResidualFlag for BasicMAP
     */
    public String getResidualFlagMap() {
        return strResidualFlagMap;
    }

    /*
     * Sets the ResidualFlag for BasicMAP
     */
    public void setResidualFlagMap(final String strResidualFlagMap) {
        this.strResidualFlagMap = strResidualFlagMap;
    }

    /*
     * Gets the ResidualFlag for Basic CCOP
     */
    public String getResidualFlagCCOP() {
        return strResidualFlagCCOP;
    }

    /*
     * Sets the ResidualFlag for Basic CCOP
     */
    public void setResidualFlagCCOP(final String strResidualFlagCCOP) {
        this.strResidualFlagCCOP = strResidualFlagCCOP;
    }

    /*
     * Gets the ResidualFlag for Basic EWIP
     */
    public String getResidualFlagEWIP() {
        return strResidualFlagEWIP;
    }

    /*
     * Sets the ResidualFlag for Basic EWIP
     */
    public void setResidualFlagEWIP(final String strResidualFlagEWIP) {
        this.strResidualFlagEWIP = strResidualFlagEWIP;
    }

    /*
     * Gets the ResidualFlag for Basic ELIP
     */
    public String getResidualFlagELIP() {
        return strResidualFlagELIP;
    }

    /*
     * Sets the ResidualFlag for Basic ELIP
     */
    public void setResidualFlagELIP(final String strResidualFlagELIP) {
        this.strResidualFlagELIP = strResidualFlagELIP;
    }

    /*
     * Gets the ResidualFlag for Basic EWIT
     */
    public String getResidualFlagEWIT() {
        return strResidualFlagEWIT;
    }

    /*
     * Sets the ResidualFlag for Basic EWIT
     */
    public void setResidualFlagEWIT(final String strResidualFlagEWIT) {
        this.strResidualFlagEWIT = strResidualFlagEWIT;
    }

    /*
     * Gets the ResidualFlag for Basic ELIT
     */
    public String getResidualFlagELIT() {
        return strResidualFlagELIT;
    }

    /*
     * Sets the ResidualFlag for Basic ELIT
     */
    public void setResidualFlagELIT(final String strResidualFlagELIT) {
        this.strResidualFlagELIT = strResidualFlagELIT;
    }

    /*
     * Gets the ResidualFlag for Basic EWOT
     */
    public String getResidualFlagEWOT() {
        return strResidualFlagEWOT;
    }

    /*
     * Sets the ResidualFlag for Basic EWOT
     */
    public void setResidualFlagEWOT(final String strResidualFlagEWOT) {
        this.strResidualFlagEWOT = strResidualFlagEWOT;
    }

    /*
     * Gets the ResidualFlag for Basic ELOT
     */
    public String getResidualFlagELOT() {
        return strResidualFlagELOT;
    }

    /*
     * Sets the ResidualFlag for Basic ELOT
     */
    public void setResidualFlagELOT(final String strResidualFlagELOT) {
        this.strResidualFlagELOT = strResidualFlagELOT;
    }

    /*
     * Gets the ResidualFlag for Basic MAT
     */
    public String getResidualFlagMAT() {
        return strResidualFlagMAT;
    }

    /*
     * Sets the ResidualFlag for Basic MAT
     */
    public void setResidualFlagMAT(final String strResidualFlagMAT) {
        this.strResidualFlagMAT = strResidualFlagMAT;
    }

    /*
     * Gets the ResidualFlag for Basic PTLT
     */
    public String getResidualFlagPTLT() {
        return strResidualFlagPTLT;
    }

    /*
     * Sets the ResidualFlag for Basic PTLT
     */
    public void setResidualFlagPTLT(final String strResidualFlagPTLT) {
        this.strResidualFlagPTLT = strResidualFlagPTLT;
    }

    /*
     * Gets the ResidualFlag for Basic PTRT
     */
    public String getResidualFlagPTRT() {
        return strResidualFlagPTRT;
    }

    /*
     * Sets the ResidualFlag for Basic PTRT
     */
    public void setResidualFlagPTRT(final String strResidualFlagPTRT) {
        this.strResidualFlagPTRT = strResidualFlagPTRT;
    }

    /*
     * Gets the ResidualFlag for Engine Fuel Demand
     */
    public String getResidualFlagFuelDemand() {
        return strResidualFlagFuelDemand;
    }

    /*
     * Sets the ResidualFlag for Engine Fuel Demand
     */
    public void setResidualFlagFuelDemand(final String strResidualFlagFuelDemand) {
        this.strResidualFlagFuelDemand = strResidualFlagFuelDemand;
    }

    /*
     * Gets the ResidualFlag for Engine TurboSpeed
     */
    public String getResidualFlagTurboSpeed() {
        return strResidualFlagTurboSpeed;
    }

    /*
     * Sets the ResidualFlag for Engine TurboSpeed
     */
    public void setResidualFlagTurboSpeed(final String strResidualFlagTurboSpeed) {
        this.strResidualFlagTurboSpeed = strResidualFlagTurboSpeed;
    }

    /*
     * Gets the ResidualFlag for Engine AdvAngle
     */
    public String getResidualFlagAdvAngle() {
        return strResidualFlagAdvAngle;
    }

    /*
     * Sets the ResidualFlag for Engine AdvAngle
     */
    public void setResidualFlagAdvAngle(final String strResidualFlagAdvAngle) {
        this.strResidualFlagAdvAngle = strResidualFlagAdvAngle;
    }

    /*
     * Gets the ResidualFlag for Engine DurAngle
     */
    public String getResidualFlagDurAngle() {
        return strResidualFlagDurAngle;
    }

    /*
     * Sets the ResidualFlag for Engine DurAngle
     */
    public void setResidualFlagDurAngle(final String strResidualFlagDurAngle) {
        this.strResidualFlagDurAngle = strResidualFlagDurAngle;
    }

    /*
     * Gets the Proximity Description
     */
    public String getProximityDesc() {
        return strProximityDesc;
    }

    /*
     * Sets the Proximity Description
     */
    public void setProximityDesc(final String strProximityDesc) {
        this.strProximityDesc = strProximityDesc;
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
     * Gets the Color
     */
    public String getColor() {
        return strColor;
    }

    /*
     * Sets the Color
     */
    public void setColor(final String strColor) {
        this.strColor = strColor;
    }

    /*
     * Gets the Data ToolTip
     */
    public String getDataToolTip() {
        return strDataToolTip;
    }

    /*
     * Sets the Data ToolTip
     */
    public void setDataToolTip(final String strDataToolTip) {
        this.strDataToolTip = strDataToolTip;
    }

    /*
     * public void setNonDisplayableCols(String strColName, String strValue) {
     * hmNotch.put(strColName, strValue); } public String
     * getNonDisplayableCols(String strColName) { return
     * (String)hmNotch.get(strColName); }
     */

}
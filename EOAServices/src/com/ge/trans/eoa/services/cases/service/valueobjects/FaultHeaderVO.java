package com.ge.trans.eoa.services.cases.service.valueobjects;

/*******************************************************************************
 * Title:		FaultHeaderVO.java
 * 
 * Description:	Used to store the fault header as well as group details and 
 * 				transfer the data between across layers.
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

import java.io.Serializable;
import java.util.ArrayList;

/*
 * This class holds the fault header as well as group details for the particular 
 * controller
 */
public class FaultHeaderVO implements Serializable, Cloneable {

    /*
     * Stores the controller Id
     */
    private int intcontrollerId = 0;

    /*
     * Stores the controller description
     */
    private String controllerDesc = null;

    /*
     * Stores the array of fault group details for the controller
     */
    private ArrayList arlFaultGroups = null;

    // FOR OIL
    /*
     * Stores the array of fault group details for the controller
     */
    private String strModel = null;

    /*
     * Gets the controller id
     */
    public int getControllerId() {
        return intcontrollerId;
    }

    /*
     * Sets the controller id
     */
    public void setControllerId(final int intcontrollerId) {
        this.intcontrollerId = intcontrollerId;
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
     * Gets the array of fault group details
     */
    public ArrayList getFaultGroups() {
        return arlFaultGroups;
    }

    /*
     * Sets the array of fault group details
     */
    public void setFaultGroups(final ArrayList arlFaultGroups) {
        this.arlFaultGroups = arlFaultGroups;
    }

    @Override
    public Object clone() {
        try {

            FaultHeaderVO objFaultHeaderVO = (FaultHeaderVO) super.clone();
            objFaultHeaderVO.arlFaultGroups = (ArrayList) arlFaultGroups.clone();
            return objFaultHeaderVO;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }

    /*
     * Sets the the Model Name
     */
    public void setModel(final String strModel) {
        this.strModel = strModel;
    }

    /*
     * Gets the the Model Name
     */
    public String getModel() {
        return strModel;
    }

}
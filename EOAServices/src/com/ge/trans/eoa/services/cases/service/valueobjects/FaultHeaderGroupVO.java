package com.ge.trans.eoa.services.cases.service.valueobjects;

/*******************************************************************************
 * Title:		FaultHeaderGroupVO.java
 * 
 * Description:	Used to store the fault group details and transfer the data 
 * 				between across layers.
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
 * This class holds the fault group details as well as all the header belongs to 
 * the group for the particular controller
 */
public class FaultHeaderGroupVO implements Serializable, Cloneable {

    /*
     * Stores the group id
     */
    private String groupId = null;

    /*
     * Stores the group name
     */
    private String strGroupName = null;

    /*
     * Stores the no of headers are available for the group
     */
    private int intNoOfHeaders = 0;

    /*
     * Stores the array of fault header details for the controller
     */
    private ArrayList arlFaultHeaderDetails = null;

    /*
     * Stores the Controller Cfg
     */
    private String strCtrlCfg = null;

    /*
     * Gets the group id
     */
    public String getGroupId() {
        return groupId;
    }

    /*
     * Sets the group id
     */
    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    /*
     * Gets the group name
     */
    public String getGroupName() {
        return strGroupName;
    }

    /*
     * Sets the group name
     */
    public void setGroupName(final String strGrpName) {
        strGroupName = strGrpName;
    }

    /*
     * Gets the no of headers
     */
    public int getNoOfHeaders() {
        return intNoOfHeaders;
    }

    /*
     * Sets the no of headers
     */
    public void setNoOfHeaders(final int intNoOfHeaders) {
        this.intNoOfHeaders = intNoOfHeaders;
    }

    /*
     * Gets the array of fault header details
     */
    public ArrayList getFaultHeaderDetails() {
        return arlFaultHeaderDetails;
    }

    /*
     * Sets the array of fault header details
     */
    public void setFaultHeaderDetails(final ArrayList arlFaultHeaderDetails) {
        this.arlFaultHeaderDetails = arlFaultHeaderDetails;
    }

    /*
     * Gets the Controller Cfg
     */
    public String getCtrlCfg() {
        return strCtrlCfg;
    }

    /*
     * Sets the Controller Cfg
     */
    public void setCtrlCfg(final String strCtrlCfg) {
        this.strCtrlCfg = strCtrlCfg;
    }

    @Override
    public Object clone() {
        try {

            FaultHeaderGroupVO objFaultHeaderGroupVO = (FaultHeaderGroupVO) super.clone();
            objFaultHeaderGroupVO.arlFaultHeaderDetails = (ArrayList) arlFaultHeaderDetails.clone();
            return objFaultHeaderGroupVO;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }

}

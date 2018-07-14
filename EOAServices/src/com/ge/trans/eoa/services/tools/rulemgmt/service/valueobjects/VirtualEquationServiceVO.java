/**
 * ============================================================
 * File : VirtualEquationServiceVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class VirtualEquationServiceVO extends BaseVO {

    private static final long serialVersionUID = -828125275986594386L;

    private String strEquationId;
    private String strEquation;
    private String strCustomFileName;
    private String strLookBackTime;
    private String strLookBackPoints;
    private String strActive;
    private String strVirtualType;
    private List<String> lstCustomParameter;
    private String strEquationDet;
    private String strEquationJSONTxt;
    private String strEquationDescTxt;

    /**
     * @return the strEquationId
     */
    public String getStrEquationId() {
        return strEquationId;
    }

    /**
     * @param strEquationId
     *            the strEquationId to set
     */
    public void setStrEquationId(String strEquationId) {
        this.strEquationId = strEquationId;
    }

    /**
     * @return the strEquation
     */
    public String getStrEquation() {
        return strEquation;
    }

    /**
     * @param strEquation
     *            the strEquation to set
     */
    public void setStrEquation(String strEquation) {
        this.strEquation = strEquation;
    }

    /**
     * @return the strCustomFileName
     */
    public String getStrCustomFileName() {
        return strCustomFileName;
    }

    /**
     * @param strCustomFileName
     *            the strCustomFileName to set
     */
    public void setStrCustomFileName(String strCustomFileName) {
        this.strCustomFileName = strCustomFileName;
    }

    /**
     * @return the strLookBackTime
     */
    public String getStrLookBackTime() {
        return strLookBackTime;
    }

    /**
     * @param strLookBackTime
     *            the strLookBackTime to set
     */
    public void setStrLookBackTime(String strLookBackTime) {
        this.strLookBackTime = strLookBackTime;
    }

    /**
     * @return the strLookBackPoints
     */
    public String getStrLookBackPoints() {
        return strLookBackPoints;
    }

    /**
     * @param strLookBackPoints
     *            the strLookBackPoints to set
     */
    public void setStrLookBackPoints(String strLookBackPoints) {
        this.strLookBackPoints = strLookBackPoints;
    }

    /**
     * @return the strActive
     */
    public String getStrActive() {
        return strActive;
    }

    /**
     * @param strActive
     *            the strActive to set
     */
    public void setStrActive(String strActive) {
        this.strActive = strActive;
    }

    /**
     * @return the strVirtualType
     */
    public String getStrVirtualType() {
        return strVirtualType;
    }

    /**
     * @param strVirtualType
     *            the strVirtualType to set
     */
    public void setStrVirtualType(String strVirtualType) {
        this.strVirtualType = strVirtualType;
    }

    /**
     * @return the lstCustomParameter
     */
    public List<String> getLstCustomParameter() {
        return lstCustomParameter;
    }

    /**
     * @param lstCustomParameter
     *            the lstCustomParameter to set
     */
    public void setLstCustomParameter(List<String> lstCustomParameter) {
        this.lstCustomParameter = lstCustomParameter;
    }

    /**
     * @return the strEquationDet
     */
    public String getStrEquationDet() {
        return strEquationDet;
    }

    /**
     * @param strEquationDet
     *            the strEquationDet to set
     */
    public void setStrEquationDet(String strEquationDet) {
        this.strEquationDet = strEquationDet;
    }

    public String getStrEquationJSONTxt() {
        return strEquationJSONTxt;
    }

    public void setStrEquationJSONTxt(String strEquationJSONTxt) {
        this.strEquationJSONTxt = strEquationJSONTxt;
    }

    public String getStrEquationDescTxt() {
        return strEquationDescTxt;
    }

    public void setStrEquationDescTxt(String strEquationDescTxt) {
        this.strEquationDescTxt = strEquationDescTxt;
    }

}

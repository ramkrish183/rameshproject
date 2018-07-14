/**
 * ============================================================
 * Classification: GE Confidential
 * File : ErrorMessageVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : July 20, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.common.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: July 20, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Class to get and set the value objects
 * @History :
 ******************************************************************************/
public class ErrorMessageVO extends BaseVO {

    public ErrorMessageVO() {
        super();
    }

    /**
     * UID
     */
    private static final long serialVersionUID = 1L;
    private String strType;
    private int intAssetId;
    private String strToolId;
    private String strCategory;
    private String strService;
    private int intSubRunId;
    private Exception objStack;
    private String strErrDetail;
    private String strErrCode;
    private String strErrMsg;

    public int getIntAssetId() {
        // TODO Auto-generated method stub
        return intAssetId;
    }

    public int getIntSubRunId() {
        // TODO Auto-generated method stub
        return intSubRunId;
    }

    public String getStrService() {
        // TODO Auto-generated method stub
        return strService;
    }

    public String getStrErrDetail() {
        // TODO Auto-generated method stub
        return strErrDetail;
    }

    public String getStrErrCode() {
        // TODO Auto-generated method stub
        return strErrCode;
    }

    public String getStrToolId() {
        // TODO Auto-generated method stub
        return strToolId;
    }

    public String getStrCategory() {
        // TODO Auto-generated method stub
        return strCategory;
    }

    public Exception getObjStack() {
        // TODO Auto-generated method stub
        return objStack;
    }

    public String getStrType() {
        // TODO Auto-generated method stub
        return strType;
    }

    public void setStrType(final String strType) {
        this.strType = strType;
    }

    public void setIntAssetId(final int intAssetId) {
        this.intAssetId = intAssetId;
    }

    public void setStrToolId(final String strToolId) {
        this.strToolId = strToolId;
    }

    public void setStrCategory(final String strCategory) {
        this.strCategory = strCategory;
    }

    public void setStrService(final String strService) {
        this.strService = strService;
    }

    public void setIntSubRunId(final int intSubRunId) {
        this.intSubRunId = intSubRunId;
    }

    public void setObjStack(final Exception objStack) {
        this.objStack = objStack;
    }

    public void setStrErrDetail(final String strErrDetail) {
        this.strErrDetail = strErrDetail;
    }

    public void setStrErrCode(final String strErrCode) {
        this.strErrCode = strErrCode;
    }

    public String getStrErrMsg() {
        return strErrMsg;
    }

    public void setStrErrMsg(final String strErrMsg) {
        this.strErrMsg = strErrMsg;
    }

}

/**
 * ============================================================
 * Classification: GE Confidential
 * File : PdfDetailsVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 1, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.common.valueobjects;

import java.util.ArrayList;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 1, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class PdfDetailsVO extends BaseVO {

    private static final long serialVersionUID = 123334L;
    private ArrayList pdfHdrList;
    private ArrayList headerList;
    private ArrayList infoList;
    private ArrayList taskInfoList;
    private ArrayList taskHdrList;
    private ArrayList taskDtlList;

    /**
     * @return the pdfHdrList
     */
    public ArrayList getPdfHdrList() {
        return pdfHdrList;
    }

    /**
     * @param pdfHdrList
     *            the pdfHdrList to set
     */
    public void setPdfHdrList(final ArrayList pdfHdrList) {
        this.pdfHdrList = pdfHdrList;
    }

    /**
     * @return the headerList
     */
    public ArrayList getHeaderList() {
        return headerList;
    }

    /**
     * @param headerList
     *            the headerList to set
     */
    public void setHeaderList(final ArrayList headerList) {
        this.headerList = headerList;
    }

    /**
     * @return the infoList
     */
    public ArrayList getInfoList() {
        return infoList;
    }

    /**
     * @param infoList
     *            the infoList to set
     */
    public void setInfoList(final ArrayList infoList) {
        this.infoList = infoList;
    }

    /**
     * @return the taskInfoList
     */
    public ArrayList getTaskInfoList() {
        return taskInfoList;
    }

    /**
     * @param taskInfoList
     *            the taskInfoList to set
     */
    public void setTaskInfoList(final ArrayList taskInfoList) {
        this.taskInfoList = taskInfoList;
    }

    /**
     * @return the taskHdrList
     */
    public ArrayList getTaskHdrList() {
        return taskHdrList;
    }

    /**
     * @param taskHdrList
     *            the taskHdrList to set
     */
    public void setTaskHdrList(final ArrayList taskHdrList) {
        this.taskHdrList = taskHdrList;
    }

    /**
     * @return the taskDtlList
     */
    public ArrayList getTaskDtlList() {
        return taskDtlList;
    }

    /**
     * @param taskDtlList
     *            the taskDtlList to set
     */
    public void setTaskDtlList(final ArrayList taskDtlList) {
        this.taskDtlList = taskDtlList;
    }
}

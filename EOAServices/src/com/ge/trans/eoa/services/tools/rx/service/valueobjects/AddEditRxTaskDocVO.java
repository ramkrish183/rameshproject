package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class AddEditRxTaskDocVO extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = 217544061807686942L;
    private String strTaskDocSeqId;
    private String strDocTitle;
    private String strDelete;
    private String strDocPath;
    private String strDocData;

    /**
     * @return the strTaskDocSeqId
     */
    public String getStrTaskDocSeqId() {
        return strTaskDocSeqId;
    }

    /**
     * @param strTaskDocSeqId
     *            the strTaskDocSeqId to set
     */
    public void setStrTaskDocSeqId(String strTaskDocSeqId) {
        this.strTaskDocSeqId = strTaskDocSeqId;
    }

    /**
     * @return the strDocTitle
     */
    public String getStrDocTitle() {
        return strDocTitle;
    }

    /**
     * @param strDocTitle
     *            the strDocTitle to set
     */
    public void setStrDocTitle(String strDocTitle) {
        this.strDocTitle = strDocTitle;
    }

    /**
     * @return the strDelete
     */
    public String getStrDelete() {
        return strDelete;
    }

    /**
     * @param strDelete
     *            the strDelete to set
     */
    public void setStrDelete(String strDelete) {
        this.strDelete = strDelete;
    }

    /**
     * @return the strDocPath
     */
    public String getStrDocPath() {
        return strDocPath;
    }

    /**
     * @param strDocPath
     *            the strDocPath to set
     */
    public void setStrDocPath(String strDocPath) {
        this.strDocPath = strDocPath;
    }

    /**
     * @return the strDocData
     */
    public String getStrDocData() {
        return strDocData;
    }

    /**
     * @param strDocData
     *            the strDocData to set
     */
    public void setStrDocData(String strDocData) {
        this.strDocData = strDocData;
    }

}

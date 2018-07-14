package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;
import java.util.List;

/*******************************************************************************
 * @Author :Mukund Sridhar
 * @Version : 1.0
 * @Date Created: Dec 3, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/

public class ScoreRxEoaVO {

    private String strUserName;
    private String rxCaseId;
    private String rxScoreCode;
    private String rxFeedback;
    private Date rxCloseDate;
    private String currentRxStatus;
    private String caseId;
    private List<Long> repairCodes;
    private String caseObjid;
    private String eoaUserName;
    private boolean isB2BClosed = false;
    private String recomObjid;
    private String caseSuccess;
    private String missCode;
    private int custFdbkObjid;
    private String accuRecom;
    private String goodFdbk;
    private String rxNote;
    private boolean isScoreRx = false;
    private String rxnotes;
    private String isReissue;

    public String getRxnotes() {
        return rxnotes;
    }

    public void setRxnotes(String rxnotes) {
        this.rxnotes = rxnotes;
    }

    public String getIsReissue() {
        return isReissue;
    }

    public void setIsReissue(String isReissue) {
        this.isReissue = isReissue;
    }

    /**
     * @return the accuRecom
     */
    public String getAccuRecom() {
        return accuRecom;
    }

    /**
     * @param accuRecom
     *            the accuRecom to set
     */
    public void setAccuRecom(String accuRecom) {
        this.accuRecom = accuRecom;
    }

    /**
     * @return the goodFdbk
     */
    public String getGoodFdbk() {
        return goodFdbk;
    }

    /**
     * @param goodFdbk
     *            the goodFdbk to set
     */
    public void setGoodFdbk(String goodFdbk) {
        this.goodFdbk = goodFdbk;
    }

    /**
     * @return the custFdbkObjid
     */
    public int getCustFdbkObjid() {
        return custFdbkObjid;
    }

    /**
     * @param custFdbkObjid
     *            the custFdbkObjid to set
     */
    public void setCustFdbkObjid(int custFdbkObjid) {
        this.custFdbkObjid = custFdbkObjid;
    }

    /**
     * @return the caseSuccess
     */
    public String getCaseSuccess() {
        return caseSuccess;
    }

    /**
     * @param caseSuccess
     *            the caseSuccess to set
     */
    public void setCaseSuccess(String caseSuccess) {
        this.caseSuccess = caseSuccess;
    }

    /**
     * @return the missCode
     */
    public String getMissCode() {
        return missCode;
    }

    /**
     * @param missCode
     *            the missCode to set
     */
    public void setMissCode(String missCode) {
        this.missCode = missCode;
    }

    /**
     * @return the recomObjid
     */
    public String getRecomObjid() {
        return recomObjid;
    }

    /**
     * @param recomObjid
     *            the recomObjid to set
     */
    public void setRecomObjid(String recomObjid) {
        this.recomObjid = recomObjid;
    }

    /**
     * @return the isB2BClosed
     */
    public boolean isB2BClosed() {
        return isB2BClosed;
    }

    /**
     * @param isB2BClosed
     *            the isB2BClosed to set
     */
    public void setB2BClosed(boolean isB2BClosed) {
        this.isB2BClosed = isB2BClosed;
    }

    /**
     * @return the eoaUserName
     */
    public String getEoaUserName() {
        return eoaUserName;
    }

    /**
     * @param eoaUserName
     *            the eoaUserName to set
     */
    public void setEoaUserName(String eoaUserName) {
        this.eoaUserName = eoaUserName;
    }

    /**
     * @return the caseObjid
     */
    public String getCaseObjid() {
        return caseObjid;
    }

    /**
     * @param caseObjid
     *            the caseObjid to set
     */
    public void setCaseObjid(String caseObjid) {
        this.caseObjid = caseObjid;
    }

    /**
     * @return the repairCodes
     */
    public List<Long> getRepairCodes() {
        return repairCodes;
    }

    /**
     * @param repairCodes
     *            the repairCodes to set
     */
    public void setRepairCodes(List<Long> repairCodes) {
        this.repairCodes = repairCodes;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getRxCaseId() {
        return rxCaseId;
    }

    public void setRxCaseId(String rxCaseId) {
        this.rxCaseId = rxCaseId;
    }

    public String getRxScoreCode() {
        return rxScoreCode;
    }

    public void setRxScoreCode(String rxScoreCode) {
        this.rxScoreCode = rxScoreCode;
    }

    public String getRxFeedback() {
        return rxFeedback;
    }

    public void setRxFeedback(String rxFeedback) {
        this.rxFeedback = rxFeedback;
    }

    public Date getRxCloseDate() {
        return rxCloseDate;
    }

    public void setRxCloseDate(Date rxCloseDate) {
        this.rxCloseDate = rxCloseDate;
    }

    public String getCurrentRxStatus() {
        return currentRxStatus;
    }

    public void setCurrentRxStatus(String currentRxStatus) {
        this.currentRxStatus = currentRxStatus;
    }

    public String getRxNote() {
        return rxNote;
    }

    public void setRxNote(String rxNote) {
        this.rxNote = rxNote;
    }

    public boolean isScoreRx() {
        return isScoreRx;
    }

    public void setScoreRx(boolean isScoreRx) {
        this.isScoreRx = isScoreRx;
    }

}

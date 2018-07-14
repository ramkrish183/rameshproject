package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.List;

/*******************************************************************************
 * @Author :Kiran
 * @Version : 1.0
 * @Date Created: July 19, 2014
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/

public class CaseRepairCodeEoaVO {

    private String userId;
    private String caseId;
    private List<String> repairCodeIdList = null;
    private boolean addAndClose;
    private String repairCode;
    private boolean isGPOCVote;
    private Long caseObjId;
    private String userName;
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the caseId
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * @param caseId
     *            the caseId to set
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * @return the repairCodeIdList
     */
    public List<String> getRepairCodeIdList() {
        return repairCodeIdList;
    }

    /**
     * @param repairCodeIdList
     *            the repairCodeIdList to set
     */
    public void setRepairCodeIdList(List<String> repairCodeIdList) {
        this.repairCodeIdList = repairCodeIdList;
    }

    public boolean isAddAndClose() {
        return addAndClose;
    }

    public void setAddAndClose(boolean addAndClose) {
        this.addAndClose = addAndClose;
    }

    /**
     * @return
     */
    public String getRepairCode() {
        return repairCode;
    }

    /**
     * @param repairCode
     */
    public void setRepairCode(String repairCode) {
        this.repairCode = repairCode;
    }

    public boolean isGPOCVote() {
        return isGPOCVote;
    }

    public void setGPOCVote(boolean isGPOCVote) {
        this.isGPOCVote = isGPOCVote;
    }

	public Long getCaseObjId() {
		return caseObjId;
	}

	public void setCaseObjId(Long caseObjId) {
		this.caseObjId = caseObjId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

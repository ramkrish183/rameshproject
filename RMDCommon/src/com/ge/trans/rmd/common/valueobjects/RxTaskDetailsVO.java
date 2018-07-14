package com.ge.trans.rmd.common.valueobjects;

import java.util.Date;

/**
 * @author kk800230
 */
public class RxTaskDetailsVO extends BaseVO {

    private String taskId;
    private String taskSequence;
    private String taskDescription;
    private String usl;
    private String lsl;
    private String target;
    private String checkedFlag;

    private String lastUpdateBy;
    private Date lastUpdatedDate;

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * @return the checkedFlag
     */
    public String getCheckedFlag() {
        return checkedFlag;
    }

    /**
     * @param checkedFlag
     *            the checkedFlag to set
     */
    public void setCheckedFlag(String checkedFlag) {
        this.checkedFlag = checkedFlag;
    }

    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return
     */
    public String getTaskSequence() {
        return taskSequence;
    }

    /**
     * @param taskSequence
     */
    public void setTaskSequence(String taskSequence) {
        this.taskSequence = taskSequence;
    }

    /**
     * @return
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * @param taskDescription
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * @return
     */
    public String getUsl() {
        return usl;
    }

    /**
     * @param usl
     */
    public void setUsl(String usl) {
        this.usl = usl;
    }

    /**
     * @return
     */
    public String getLsl() {
        return lsl;
    }

    /**
     * @param lsl
     */
    public void setLsl(String lsl) {
        this.lsl = lsl;
    }

    /**
     * @return
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

}

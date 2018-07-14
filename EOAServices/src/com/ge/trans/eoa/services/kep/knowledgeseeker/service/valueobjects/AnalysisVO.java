package com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects;

/*******************************************************************************
 * @Author : IgatePatni Global Solutions
 * @Version : 1.0
 * @Date Created: Nov 24, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class AnalysisVO extends BaseVO {

    static final long serialVersionUID = 83345112L;
    private String pattern;
    private String accuracy;
    private String coverages;
    private String falseAlarm;
    private String score;
    private String falseAlarmPercent;
    private String coveragesPercent;
    private int patternSeqID;
    private String faultCodes;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(final String accuracy) {
        this.accuracy = accuracy;
    }

    public String getCoverages() {
        return coverages;
    }

    public void setCoverages(final String coverages) {
        this.coverages = coverages;
    }

    public String getFalseAlarm() {
        return falseAlarm;
    }

    public void setFalseAlarm(final String falseAlarm) {
        this.falseAlarm = falseAlarm;
    }

    public String getScore() {
        return score;
    }

    public void setScore(final String score) {
        this.score = score;
    }

    public String getFalseAlarmPercent() {
        return falseAlarmPercent;
    }

    public void setFalseAlarmPercent(final String falseAlarmPercent) {
        this.falseAlarmPercent = falseAlarmPercent;
    }

    public String getCoveragesPercent() {
        return coveragesPercent;
    }

    public void setCoveragesPercent(final String coveragesPercent) {
        this.coveragesPercent = coveragesPercent;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getPatternSeqID() {
        return patternSeqID;
    }

    public void setPatternSeqID(final int patternSeqID) {
        this.patternSeqID = patternSeqID;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getFaultCodes() {
        return faultCodes;
    }

    public void setFaultCodes(final String faultCodes) {
        this.faultCodes = faultCodes;
    }
}

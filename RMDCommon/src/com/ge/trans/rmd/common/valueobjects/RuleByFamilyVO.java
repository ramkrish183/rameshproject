package com.ge.trans.rmd.common.valueobjects;

public class RuleByFamilyVO extends BaseVO {

    /**
     * 
     */
    private static final long serialVersionUID = -3004368105245885229L;

    private String ruleId;
    private String title;
    private String family;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

}

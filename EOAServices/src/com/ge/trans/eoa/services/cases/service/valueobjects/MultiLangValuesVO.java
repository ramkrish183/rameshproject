package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.io.Serializable;
import java.util.Map;

public class MultiLangValuesVO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6977524279919180542L;
    private Map<String, String> titleMLMap;
    private Map<String, String> locoImpactMLMap;

    public Map<String, String> getTitleMLMap() {
        return titleMLMap;
    }

    public void setTitleMLMap(Map<String, String> titleMLMap) {
        this.titleMLMap = titleMLMap;
    }

    public Map<String, String> getLocoImpactMLMap() {
        return locoImpactMLMap;
    }

    public void setLocoImpactMLMap(Map<String, String> locoImpactMLMap) {
        this.locoImpactMLMap = locoImpactMLMap;
    }

}

package com.ge.trans.pp.services.geofencereport.service.valueobjects;

import java.util.Map;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class AssetListResponseVo extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = -9133127506406986403L;
    private String query;
    private Map<String, String> parameterMap;

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

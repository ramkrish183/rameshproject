package com.ge.trans.eoa.services.asset.service.valueobjects;

public class VisualizationGraphDetailsVO {

    private String graphName;
    private String graphMPNum;
    private String graphMPDesc;
    private String sortOrder;
    private String controllercfg;
    private String sourceType;
    private String source;
    private String strLanguage;
    private String stackOrder;

    public String getStackOrder() {
        return stackOrder;
    }

    public void setStackOrder(String stackOrder) {
        this.stackOrder = stackOrder;
    }

    public String getControllercfg() {
        return controllercfg;
    }

    public void setControllercfg(String controllercfg) {
        this.controllercfg = controllercfg;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStrLanguage() {
        return strLanguage;
    }

    public void setStrLanguage(String strLanguage) {
        this.strLanguage = strLanguage;
    }

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public String getGraphMPNum() {
        return graphMPNum;
    }

    public void setGraphMPNum(String graphMPNum) {
        this.graphMPNum = graphMPNum;
    }

    public String getGraphMPDesc() {
        return graphMPDesc;
    }

    public void setGraphMPDesc(String graphMPDesc) {
        this.graphMPDesc = graphMPDesc;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}

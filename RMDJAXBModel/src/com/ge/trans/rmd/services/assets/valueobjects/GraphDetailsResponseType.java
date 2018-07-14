package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for headerInfoType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
* &lt;complexType name="headerInfoType">
*   &lt;complexContent>
*     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       &lt;sequence>
*         &lt;element name="graphName" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         &lt;element name="graphMPNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         &lt;element name="graphMPDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         &lt;element name="sortOrder" type="{http://www.w3.org/2001/XMLSchema}string"/>
*       &lt;/sequence>
*     &lt;/restriction>
*   &lt;/complexContent>
* &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "graphDetailsResponseType", namespace = "http://omd/omdservices/assetservice", propOrder = {
        "graphName", "graphMPNum", "graphMPDesc", "sortOrder", "sourceType", "controllerCfg", "source", "stackOrder" })
@XmlRootElement
public class GraphDetailsResponseType {

    @XmlElement(required = true)
    protected String graphName;
    @XmlElement(required = true)
    protected String graphMPNum;
    @XmlElement(required = true)
    protected String graphMPDesc;
    @XmlElement(required = true)
    protected String sortOrder;
    @XmlElement(required = true)
    protected String sourceType;
    @XmlElement(required = true)
    protected String controllerCfg;
    @XmlElement(required = true)
    protected String source;
    @XmlElement(required = true)
    protected String stackOrder;

    public String getStackOrder() {
        return stackOrder;
    }

    public void setStackOrder(String stackOrder) {
        this.stackOrder = stackOrder;
    }

    public String getControllerCfg() {
        return controllerCfg;
    }

    public void setControllerCfg(String controllerCfg) {
        this.controllerCfg = controllerCfg;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
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

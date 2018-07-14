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
@XmlType(name = "mpParmDetailsResponseType", namespace = "http://omd/omdservices/assetservice", propOrder = {
        "parmNumber", "parmDescription", "columnName", "tableName", "controllerCfg", "source", "sourceType",
        "parmDescriptionName", "parmNumColName", "controllerType" })
@XmlRootElement
public class MPParmDetailsResponseType {

    @XmlElement
    protected String parmNumber;
    @XmlElement
    protected String parmDescription;
    @XmlElement
    protected String columnName;
    @XmlElement
    protected String tableName;
    @XmlElement
    protected String controllerCfg;
    @XmlElement
    protected String source;
    @XmlElement
    protected String sourceType;
    @XmlElement
    protected String parmDescriptionName;
    @XmlElement
    protected String parmNumColName;
    @XmlElement
    protected String controllerType;

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public String getParmDescriptionName() {
        return parmDescriptionName;
    }

    public String getParmNumColName() {
        return parmNumColName;
    }

    public void setParmNumColName(String parmNumColName) {
        this.parmNumColName = parmNumColName;
    }

    public void setParmDescriptionName(String parmDescriptionName) {
        this.parmDescriptionName = parmDescriptionName;
    }

    public String getParmNumber() {
        return parmNumber;
    }

    public void setParmNumber(String parmNumber) {
        this.parmNumber = parmNumber;
    }

    public String getParmDescription() {
        return parmDescription;
    }

    public void setParmDescription(String parmDescription) {
        this.parmDescription = parmDescription;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

}

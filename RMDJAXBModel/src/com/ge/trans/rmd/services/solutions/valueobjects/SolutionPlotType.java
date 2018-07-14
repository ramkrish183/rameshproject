package com.ge.trans.rmd.services.solutions.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solutionPlotType", propOrder = { "plotObjid", "plotTitle",
		"independentAxislabel", "leftDependentAxislabel",
		"rightDependentAxislabel", "dataSetOneName", "dataSetOneLabel",
		"dataSetOneAxis", "dataSetTwoName", "dataSetTwoLabel",
		"dataSetTwoAxis", "dataSetThreeName", "dataSetThreeLabel","dataSetThreeAxis","dataSetFourName","dataSetFourLabel","dataSetFourAxis"})
@XmlRootElement
public class SolutionPlotType {

    @XmlElement(required = true)
    protected String plotObjid;
    @XmlElement(required = true)
    protected String plotTitle;
    @XmlElement(required = true)
    protected String independentAxislabel;
    @XmlElement(required = true)
    protected String leftDependentAxislabel;
    @XmlElement(required = true)
    protected String rightDependentAxislabel;
    @XmlElement(required = true)
    protected String dataSetOneName;
    @XmlElement(required = true)
    protected String dataSetOneLabel;
    @XmlElement(required = true)
    protected String dataSetOneAxis;
    @XmlElement(required = true)
    protected String dataSetTwoName;
    @XmlElement(required = true)
    protected String dataSetTwoLabel;
    @XmlElement(required = true)
    protected String dataSetTwoAxis;
    @XmlElement(required = true)
    protected String dataSetThreeName;
    @XmlElement(required = true)
    protected String dataSetThreeLabel;
    @XmlElement(required = true)
    protected String dataSetThreeAxis;
    @XmlElement(required = true)
    protected String dataSetFourName;
    @XmlElement(required = true)
    protected String dataSetFourLabel;
    @XmlElement(required = true)
    protected String dataSetFourAxis;
    
	public String getPlotObjid() {
		return plotObjid;
	}
	public void setPlotObjid(String plotObjid) {
		this.plotObjid = plotObjid;
	}
	public String getPlotTitle() {
		return plotTitle;
	}
	public void setPlotTitle(String plotTitle) {
		this.plotTitle = plotTitle;
	}
	public String getIndependentAxislabel() {
		return independentAxislabel;
	}
	public void setIndependentAxislabel(String independentAxislabel) {
		this.independentAxislabel = independentAxislabel;
	}
	public String getLeftDependentAxislabel() {
		return leftDependentAxislabel;
	}
	public void setLeftDependentAxislabel(String leftDependentAxislabel) {
		this.leftDependentAxislabel = leftDependentAxislabel;
	}
	public String getRightDependentAxislabel() {
		return rightDependentAxislabel;
	}
	public void setRightDependentAxislabel(String rightDependentAxislabel) {
		this.rightDependentAxislabel = rightDependentAxislabel;
	}
	public String getDataSetOneName() {
		return dataSetOneName;
	}
	public void setDataSetOneName(String dataSetOneName) {
		this.dataSetOneName = dataSetOneName;
	}
	public String getDataSetOneLabel() {
		return dataSetOneLabel;
	}
	public void setDataSetOneLabel(String dataSetOneLabel) {
		this.dataSetOneLabel = dataSetOneLabel;
	}
	public String getDataSetOneAxis() {
		return dataSetOneAxis;
	}
	public void setDataSetOneAxis(String dataSetOneAxis) {
		this.dataSetOneAxis = dataSetOneAxis;
	}
	public String getDataSetTwoName() {
		return dataSetTwoName;
	}
	public void setDataSetTwoName(String dataSetTwoName) {
		this.dataSetTwoName = dataSetTwoName;
	}
	public String getDataSetTwoLabel() {
		return dataSetTwoLabel;
	}
	public void setDataSetTwoLabel(String dataSetTwoLabel) {
		this.dataSetTwoLabel = dataSetTwoLabel;
	}
	public String getDataSetTwoAxis() {
		return dataSetTwoAxis;
	}
	public void setDataSetTwoAxis(String dataSetTwoAxis) {
		this.dataSetTwoAxis = dataSetTwoAxis;
	}
	public String getDataSetThreeName() {
		return dataSetThreeName;
	}
	public void setDataSetThreeName(String dataSetThreeName) {
		this.dataSetThreeName = dataSetThreeName;
	}
	public String getDataSetThreeLabel() {
		return dataSetThreeLabel;
	}
	public void setDataSetThreeLabel(String dataSetThreeLabel) {
		this.dataSetThreeLabel = dataSetThreeLabel;
	}
	public String getDataSetThreeAxis() {
		return dataSetThreeAxis;
	}
	public void setDataSetThreeAxis(String dataSetThreeAxis) {
		this.dataSetThreeAxis = dataSetThreeAxis;
	}
	public String getDataSetFourName() {
		return dataSetFourName;
	}
	public void setDataSetFourName(String dataSetFourName) {
		this.dataSetFourName = dataSetFourName;
	}
	public String getDataSetFourLabel() {
		return dataSetFourLabel;
	}
	public void setDataSetFourLabel(String dataSetFourLabel) {
		this.dataSetFourLabel = dataSetFourLabel;
	}
	public String getDataSetFourAxis() {
		return dataSetFourAxis;
	}
	public void setDataSetFourAxis(String dataSetFourAxis) {
		this.dataSetFourAxis = dataSetFourAxis;
	}
    
}

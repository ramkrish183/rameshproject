package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.ArrayList;
import java.util.List;

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
@XmlType(name = "visualizationDetailsResponseType", namespace = "http://omd/omdservices/assetservice", propOrder = {
        "parmDescription", "displayName", "parmNumber", "lstParmDetails", "stackOrder", "asset","axisSide","leftDependentAxis","rightDependentAxis","independentAxis","title"

})
@XmlRootElement
public class VisualizationDetailsResponseType {

    @XmlElement(required = true)
    protected String parmDescription;
    @XmlElement(required = true)
    protected String displayName;
    @XmlElement(required = true)
    protected String parmNumber;
    @XmlElement(required = true)
    protected List<VisualizationParmDetailsType> lstParmDetails;
	@XmlElement(required = true)
    protected String stackOrder;
    @XmlElement(required = true)
    protected String asset;
    protected String axisSide;
    protected String leftDependentAxis;
    protected String rightDependentAxis;
    protected String independentAxis;
    protected String title;    
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLeftDependentAxis() {
		return leftDependentAxis;
	}

	public void setLeftDependentAxis(String leftDependentAxis) {
		this.leftDependentAxis = leftDependentAxis;
	}

	public String getRightDependentAxis() {
		return rightDependentAxis;
	}

	public void setRightDependentAxis(String rightDependentAxis) {
		this.rightDependentAxis = rightDependentAxis;
	}

	public String getIndependentAxis() {
		return independentAxis;
	}

	public void setIndependentAxis(String independentAxis) {
		this.independentAxis = independentAxis;
	}
	
    public String getAxisSide() {
		return axisSide;
	}

	public void setAxisSide(String axisSide) {
		this.axisSide = axisSide;
	}

	public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getStackOrder() {
        return stackOrder;
    }

    public void setStackOrder(String stackOrder) {
        this.stackOrder = stackOrder;
    }

    public void setLstParmDetails(List<VisualizationParmDetailsType> lstParmDetails) {
        this.lstParmDetails = lstParmDetails;
    }

    public List<VisualizationParmDetailsType> getLstParmDetails() {
        if (lstParmDetails == null) {
            lstParmDetails = new ArrayList<VisualizationParmDetailsType>();
        }
        return this.lstParmDetails;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getParmDescription() {
        return parmDescription;
    }

    public void setParmDescription(String parmDescription) {
        this.parmDescription = parmDescription;
    }

    public String getParmNumber() {
        return parmNumber;
    }

    public void setParmNumber(String parmNumber) {
        this.parmNumber = parmNumber;
    }

}

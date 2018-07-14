package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.cases.valueobjects.CaseResponseType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assetDeliveredCasesResponseType", propOrder = { "assetLocatorResponseType", "caseResponseType"})
@XmlRootElement
public class AssetDeliveredCasesResponseType{

	@XmlElement(required = true)
	protected List<AssetLocatorResponseType> assetLocatorResponseType;
	@XmlElement(required = true)
	protected List<CaseResponseType> caseResponseType;
	
	public List<AssetLocatorResponseType> getAssetLocatorResponseType() {
		return assetLocatorResponseType;
	}
	public List<CaseResponseType> getCaseResponseType() {
		return caseResponseType;
	}
	public void setAssetLocatorResponseType(
			List<AssetLocatorResponseType> assetLocatorResponseType) {
		this.assetLocatorResponseType = assetLocatorResponseType;
	}
	public void setCaseResponseType(List<CaseResponseType> caseResponseType) {
		this.caseResponseType = caseResponseType;
	}
		
}

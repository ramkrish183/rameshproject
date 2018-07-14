package com.ge.trans.rmd.services.lhr.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "healthScoreRequest", propOrder = { "assets" })
@XmlRootElement
public class HealthScoreRequest{
	@XmlElement(required = true)
	protected List<AssetRequest>  assets;

	public List<AssetRequest> getAssets() {
		return assets;
	}

	public void setAssets(List<AssetRequest> assets) {
		this.assets = assets;
	}
}

package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class LifeStatisticsEoaVO extends BaseVO {

    private String attributeName;
    private String sortOrder;
    private Date statisticGatheringDate;
    private String attributeValue;
    private String unit;
    private String attributeToolTip;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getStatisticGatheringDate() {
        return statisticGatheringDate;
    }

    public void setStatisticGatheringDate(Date statisticGatheringDate) {
        this.statisticGatheringDate = statisticGatheringDate;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

	public String getAttributeToolTip() {
		return attributeToolTip;
	}

	public void setAttributeToolTip(String attributeToolTip) {
		this.attributeToolTip = attributeToolTip;
	}

}

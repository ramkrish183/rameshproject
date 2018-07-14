package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

public class VisualizationDetailsVO {

    private String parmNumber;
    private String parmDescription;
    private String columnName;
    private String tableName;
    private String controllerCfg;
    private String controllerSource;
    private String summaryTableName;
    private String displayName;
    private String parmDescriptionName;
    private String parmDisplayName;
    private String stackOrder;
    private String asset;
    private String parmType;
    private String parmLoadColumn;
    private String sourceUom;
    private String axisSide;
    private String leftDependentAxis;
    private String rightDependentAxis;
    private String independentAxis;
    private String title;
    
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

	public String getParmLoadColumn() {
		return parmLoadColumn;
	}

	public void setParmLoadColumn(String parmLoadColumn) {
		this.parmLoadColumn = parmLoadColumn;
	}

	public String getSourceUom() {
		return sourceUom;
	}

	public void setSourceUom(String sourceUom) {
		this.sourceUom = sourceUom;
	}

	public String getParmType() {
		return parmType;
	}

	public void setParmType(String parmType) {
		this.parmType = parmType;
	}

	public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getParmDisplayName() {
        return parmDisplayName;
    }

    public void setParmDisplayName(String parmDisplayName) {
        this.parmDisplayName = parmDisplayName;
    }

    public String getStackOrder() {
        return stackOrder;
    }

    public void setStackOrder(String stackOrder) {
        this.stackOrder = stackOrder;
    }

    public String getParmDescriptionName() {
        return parmDescriptionName;
    }

    public void setParmDescriptionName(String parmDescriptionName) {
        this.parmDescriptionName = parmDescriptionName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSummaryTableName() {
        return summaryTableName;
    }

    public void setSummaryTableName(String summaryTableName) {
        this.summaryTableName = summaryTableName;
    }

    private List<VisualizationParmDetailsVO> arlParmdetails = new ArrayList<VisualizationParmDetailsVO>();

    public String getControllerSource() {
        return controllerSource;
    }

    public void setControllerSource(String controllerSource) {
        this.controllerSource = controllerSource;
    }

    public String getParmNumber() {
        return parmNumber;
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

    public void setParmNumber(String parmNumber) {
        this.parmNumber = parmNumber;
    }

    public String getParmDescription() {
        return parmDescription;
    }

    public void setParmDescription(String parmDescription) {
        this.parmDescription = parmDescription;
    }

    public List<VisualizationParmDetailsVO> getArlParmdetails() {
        return arlParmdetails;
    }

    public void setArlParmdetails(List<VisualizationParmDetailsVO> arlParmdetails) {
        this.arlParmdetails = arlParmdetails;
    }

}

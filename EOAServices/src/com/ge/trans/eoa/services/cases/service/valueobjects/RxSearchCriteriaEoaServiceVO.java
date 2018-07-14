/**
 * ============================================================
 * Classification: GE Confidential
 * File : RxSearchCriteriaServiceVO.java
 * Description :
 *
 * Package :  com.ge.trans.rmd.services.tools.rx.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 3, 2009
 * @Date Modified : 16-Apr-2012
 * @Modified By :iGATE PATNI
 * @Contact :
 * @Description :added new attributes
 * @History :
 ******************************************************************************/
public class RxSearchCriteriaEoaServiceVO extends BaseVO {

    static final long serialVersionUID = 18976324L;
    private String strRxObjid;
    private String strSelectBy;
    private String strCondition;
    private String strCondValue;
    private String strSelUrgRepair;
    private String strSelRxType;
    private String strSelEstmTimeRepair;
    private String strRxStatus;
    private String strActiveFlag;
    private String strRxExportControl;
    private String[] strRxLocoImpact;
    private String[] strRxUrgRepairArray;
    private String[] strRxSubSystemArray;
    private String[] strRxModel;
    private String strRxSubSystem;
    private String strModel;
    private String strLastUpdatedBy;
    private String strRxNotes;
    private boolean blnDelvFlag;
    private boolean blnDefaultLoad;
    private List<String> arlRxObjid = new ArrayList<String>();
    private String isMassApplyRx;
    private String isModelQuery;
    private String strFamily;
    private boolean blnKM;
    private String addRxApply;
    
    private String customer;
    private String fromRN;;
    private String toRN;
    private String fleet;
    private String rnh;
    private String assetList;    
    private String strCreatedBy;
    private String strLastUpdatedFromDate;
    private String strLastUpdatedToDate;
    private String strCreatedByFromDate;
    private String strCreatedByToDate;
    
    

    public String getStrCreatedBy() {
		return strCreatedBy;
	}

	public void setStrCreatedBy(String strCreatedBy) {
		this.strCreatedBy = strCreatedBy;
	}

	public String getStrLastUpdatedFromDate() {
		return strLastUpdatedFromDate;
	}

	public void setStrLastUpdatedFromDate(String strLastUpdatedFromDate) {
		this.strLastUpdatedFromDate = strLastUpdatedFromDate;
	}

	public String getStrLastUpdatedToDate() {
		return strLastUpdatedToDate;
	}

	public void setStrLastUpdatedToDate(String strLastUpdatedToDate) {
		this.strLastUpdatedToDate = strLastUpdatedToDate;
	}

	public String getStrCreatedByFromDate() {
		return strCreatedByFromDate;
	}

	public void setStrCreatedByFromDate(String strCreatedByFromDate) {
		this.strCreatedByFromDate = strCreatedByFromDate;
	}

	public String getStrCreatedByToDate() {
		return strCreatedByToDate;
	}

	public void setStrCreatedByToDate(String strCreatedByToDate) {
		this.strCreatedByToDate = strCreatedByToDate;
	}

	public boolean isBlnKM() {
        return blnKM;
    }

    public void setBlnKM(boolean blnKM) {
        this.blnKM = blnKM;
    }

    public String getStrFamily() {
        return strFamily;
    }

    public void setStrFamily(String strFamily) {
        this.strFamily = strFamily;
    }

    public String getIsModelQuery() {
        return isModelQuery;
    }

    public void setIsModelQuery(String isModelQuery) {
        this.isModelQuery = isModelQuery;
    }

    public String getIsMassApplyRx() {
        return isMassApplyRx;
    }

    public void setIsMassApplyRx(String isMassApplyRx) {
        this.isMassApplyRx = isMassApplyRx;
    }

    public List<String> getArlRxObjid() {
        return arlRxObjid;
    }

    public void setArlRxObjid(List<String> arlRxObjid) {
        this.arlRxObjid = arlRxObjid;
    }

    /**
     * @return
     */
    public String getStrRxObjid() {
        return strRxObjid;
    }

    /**
     * @param strRxObjid
     */
    public void setStrRxObjid(String strRxObjid) {
        this.strRxObjid = strRxObjid;
    }

    /**
     * @return the strSelUrgRepair
     */
    public String getStrSelUrgRepair() {
        return strSelUrgRepair;
    }

    /**
     * @param strSelUrgRepair
     *            the strSelUrgRepair to set
     */
    public void setStrSelUrgRepair(final String strSelUrgRepair) {
        this.strSelUrgRepair = strSelUrgRepair;
    }

    /**
     * @return the strSelRxType
     */
    public String getStrSelRxType() {
        return strSelRxType;
    }

    /**
     * @param strSelRxType
     *            the strSelRxType to set
     */
    public void setStrSelRxType(final String strSelRxType) {
        this.strSelRxType = strSelRxType;
    }

    /**
     * @return the strSelEstmTimeRepair
     */
    public String getStrSelEstmTimeRepair() {
        return strSelEstmTimeRepair;
    }

    /**
     * @param strSelEstmTimeRepair
     *            the strSelEstmTimeRepair to set
     */
    public void setStrSelEstmTimeRepair(final String strSelEstmTimeRepair) {
        this.strSelEstmTimeRepair = strSelEstmTimeRepair;
    }

    /**
     * @return the strRxStatus
     */
    public String getStrRxStatus() {
        return strRxStatus;
    }

    /**
     * @param strRxStatus
     *            the strRxStatus to set
     */
    public void setStrRxStatus(final String strRxStatus) {
        this.strRxStatus = strRxStatus;
    }

    public RxSearchCriteriaEoaServiceVO() {
        super();
    }

    /**
     * @return the strSelectBy
     */
    public String getStrSelectBy() {
        return strSelectBy;
    }

    /**
     * @param strSelectBy
     *            the strSelectBy to set
     */
    public void setStrSelectBy(final String strSelectBy) {
        this.strSelectBy = strSelectBy;
    }

    /**
     * @return the strCondition
     */
    public String getStrCondition() {
        return strCondition;
    }

    /**
     * @param strCondition
     *            the strCondition to set
     */
    public void setStrCondition(final String strCondition) {
        this.strCondition = strCondition;
    }

    /**
     * @return the strCondValue
     */
    public String getStrCondValue() {
        return strCondValue;
    }

    /**
     * @param strCondValue
     *            the strCondValue to set
     */
    public void setStrCondValue(final String strCondValue) {
        this.strCondValue = strCondValue;
    }

    /**
     * @return the strActiveFlag
     */
    public String getStrActiveFlag() {
        return strActiveFlag;
    }

    /**
     * @param strActiveFlag
     *            the strActiveFlag to set
     */
    public void setStrActiveFlag(final String strActiveFlag) {
        this.strActiveFlag = strActiveFlag;
    }

	/*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strSelectBy", strSelectBy)
                .append("strCondition", strCondition).append("strCondValue", strCondValue)
                .append("strActiveFlag", "strActiveFlag").toString();
    }

    /**
     * @return the blnDelvFlag
     */
    public boolean isBlnDelvFlag() {
        return blnDelvFlag;
    }

    /**
     * @param blnDelvFlag
     *            the blnDelvFlag to set
     */
    public void setBlnDelvFlag(final boolean blnDelvFlag) {
        this.blnDelvFlag = blnDelvFlag;
    }

    public String getStrRxExportControl() {
        return strRxExportControl;
    }

    public void setStrRxExportControl(final String strRxExportControl) {
        this.strRxExportControl = strRxExportControl;
    }

    public String[] getStrRxLocoImpact() {
        return strRxLocoImpact;
    }

    public void setStrRxLocoImpact(final String[] strRxLocoImpact) {
        this.strRxLocoImpact = strRxLocoImpact;
    }

    public String[] getStrRxUrgRepairArray() {
        return strRxUrgRepairArray;
    }

    public void setStrRxUrgRepairArray(final String[] strRxUrgRepairArray) {
        this.strRxUrgRepairArray = strRxUrgRepairArray;
    }

    public String[] getStrRxSubSystemArray() {
        return strRxSubSystemArray;
    }

    public void setStrRxSubSystemArray(final String[] strRxSubSystemArray) {
        this.strRxSubSystemArray = strRxSubSystemArray;
    }

    public String[] getStrRxModel() {
        return strRxModel;
    }

    public void setStrRxModel(String[] strRxModel) {
        this.strRxModel = strRxModel;
    }

    public String getStrRxSubSystem() {
        return strRxSubSystem;
    }

    public void setStrRxSubSystem(final String strRxSubSystem) {
        this.strRxSubSystem = strRxSubSystem;
    }

    public String getStrModel() {
        return strModel;
    }

    public void setStrModel(final String strModel) {
        this.strModel = strModel;
    }

    public String getStrLastUpdatedBy() {
        return strLastUpdatedBy;
    }

    public void setStrLastUpdatedBy(final String strLastUpdatedBy) {
        this.strLastUpdatedBy = strLastUpdatedBy;
    }

    public String getStrRxNotes() {
        return strRxNotes;
    }

    public void setStrRxNotes(final String strRxNotes) {
        this.strRxNotes = strRxNotes;
    }

    public boolean isBlnDefaultLoad() {
        return blnDefaultLoad;
    }

    public void setBlnDefaultLoad(final boolean blnDefaultLoad) {
        this.blnDefaultLoad = blnDefaultLoad;
    }

	public String getAddRxApply() {
		return addRxApply;
	}

	public void setAddRxApply(String addRxApply) {
		this.addRxApply = addRxApply;
	}

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getFromRN() {
        return fromRN;
    }

    public void setFromRN(String fromRN) {
        this.fromRN = fromRN;
    }

    public String getToRN() {
        return toRN;
    }

    public void setToRN(String toRN) {
        this.toRN = toRN;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    public String getRnh() {
        return rnh;
    }

    public void setRnh(String rnh) {
        this.rnh = rnh;
    }

    public String getAssetList() {
        return assetList;
    }

    public void setAssetList(String assetList) {
        this.assetList = assetList;
    }
	
	
	
	

}

/**
 * ============================================================
 * File : AddEditRxServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 7, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class AddEditRxServiceVO extends BaseVO {

    private static final long serialVersionUID = 91563248L;

    private String strItemLabel;
    private String strItemValue;
    private String strFromPage;
    private String strRxId;
    private String strTitle;
    private String strRevNo;
    private String strSelAssetImp;
    private String strRxType;
    private String strRxStatus;
    private String strRxStatusLbl;
    private String strSelUrgRepair;
    private String strSelRxType;
    private String strSelEstmTimeRepair;
    private String strSelectBy;
    private String strSelectCond;
    private String strCondValue;
    private String strLastUpdatedDate;
    private String strLastUpdatedBy;
    private String strSelRepairCode;
    private String strSelRepairCodeId;
    private String strRepairCodeValue;
    private String strSelRepair;
    private String strSelRepairDesc;
    private String strOriginalId;
    private String strRevHist;
    private String strActiveFlag;
    private String strUrgForPdf;
    private String strEstForPdf;
    private String strLockedBy;
    // Added new attributes for new sprint 10
    private String strExportControl;
    private String strQueue;
    private String strModel;
    private String strDeliveries;
    private String strRank;
    private List subSystemList = new ArrayList();
    private List modelList = new ArrayList();
    private List customerList = new ArrayList();
    private List fleetList = new ArrayList();
    // Added new attributes for new sprint 10
    private ArrayList assetImpactList;
    private ArrayList rxStatusList;
    private ArrayList rxTypeList;
    private ArrayList urgencyRepList;
    private ArrayList estmTimeRepList;
    private ArrayList<RxHistServiceVO> arlRxHistory;
    private ArrayList<RxConfigVO> arlRxConfig;
    private ArrayList editRxTaskResultList;
    private ArrayList rxSearchResultList;
    private ArrayList repairCodeSearchResultList;
    private ArrayList taskSearchResultList;
    private String originalId;
    private boolean isClone;
    private String parentSolutionId;
    private String exclude;
    private String strDescription;
    
    public List getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List customerList) {
		this.customerList = customerList;
	}

	public List getFleetList() {
		return fleetList;
	}

	public void setFleetList(List fleetList) {
		this.fleetList = fleetList;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	private AddEditRxTaskResultVO editTaskRxResultVO;
    private RxSearchResultServiceVO rxSearchResultServiceVO;
    private List<AddEditRxTaskResultVO> lstEditTaskRxResultVO;

    private boolean blnDelvFlag;
    private boolean blnRxStatusChk;
    private boolean blnFleetLeader;
    private boolean blnLockRx;
    private boolean blnRecommLockedByUser;
    private String strCreatedBy;
    private Date creationDate;
    private Date lastUpdatedDate;
    // Added new items for sprint 11
    private String strPrecision;
    private AddEditRxPlotVO addEditRxPlotVO;

    public AddEditRxServiceVO(String strItemLabel, String strItemValue) {
        super();
        this.strItemLabel = strItemLabel;
        this.strItemValue = strItemValue;
    }

    public AddEditRxServiceVO() {
        editTaskRxResultVO = new AddEditRxTaskResultVO();
    }

    /**
     * @return the strItemLabel
     */
    public String getStrItemLabel() {
        return strItemLabel;
    }

    /**
     * @param strItemLabel
     *            the strItemLabel to set
     */
    public void setStrItemLabel(String strItemLabel) {
        this.strItemLabel = strItemLabel;
    }

    /**
     * @return the strItemValue
     */
    public String getStrItemValue() {
        return strItemValue;
    }

    /**
     * @param strItemValue
     *            the strItemValue to set
     */
    public void setStrItemValue(String strItemValue) {
        this.strItemValue = strItemValue;
    }

    /**
     * @return the strRxId
     */
    public String getStrRxId() {
        return strRxId;
    }

    /**
     * @param strRxId
     *            the strRxId to set
     */
    public void setStrRxId(String strRxId) {
        this.strRxId = strRxId;
    }

    /**
     * @return the strTitle
     */
    public String getStrTitle() {
        return strTitle;
    }

    /**
     * @param strTitle
     *            the strTitle to set
     */
    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    /**
     * @return the strRevNo
     */
    public String getStrRevNo() {
        return strRevNo;
    }

    /**
     * @param strRevNo
     *            the strRevNo to set
     */
    public void setStrRevNo(String strRevNo) {
        this.strRevNo = strRevNo;
    }

    /**
     * @return the strSelAssetImp
     */
    public String getStrSelAssetImp() {
        return strSelAssetImp;
    }

    /**
     * @param strSelAssetImp
     *            the strSelAssetImp to set
     */
    public void setStrSelAssetImp(String strSelAssetImp) {
        this.strSelAssetImp = strSelAssetImp;
    }

    /**
     * @return the strRxType
     */
    public String getStrRxType() {
        return strRxType;
    }

    /**
     * @param strRxType
     *            the strRxType to set
     */
    public void setStrRxType(String strRxType) {
        this.strRxType = strRxType;
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
    public void setStrRxStatus(String strRxStatus) {
        this.strRxStatus = strRxStatus;
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
    public void setStrSelUrgRepair(String strSelUrgRepair) {
        this.strSelUrgRepair = strSelUrgRepair;
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
    public void setStrSelEstmTimeRepair(String strSelEstmTimeRepair) {
        this.strSelEstmTimeRepair = strSelEstmTimeRepair;
    }

    /**
     * @return the assetImpactList
     */
    public ArrayList getAssetImpactList() {
        return assetImpactList;
    }

    /**
     * @param assetImpactList
     *            the assetImpactList to set
     */
    public void setAssetImpactList(ArrayList assetImpactList) {
        this.assetImpactList = assetImpactList;
    }

    /**
     * @return the rxStatusList
     */
    public ArrayList getRxStatusList() {
        return rxStatusList;
    }

    /**
     * @param rxStatusList
     *            the rxStatusList to set
     */
    public void setRxStatusList(ArrayList rxStatusList) {
        this.rxStatusList = rxStatusList;
    }

    /**
     * @return the urgencyRepList
     */
    public ArrayList getUrgencyRepList() {
        return urgencyRepList;
    }

    /**
     * @param urgencyRepList
     *            the urgencyRepList to set
     */
    public void setUrgencyRepList(ArrayList urgencyRepList) {
        this.urgencyRepList = urgencyRepList;
    }

    /**
     * @return the estmTimeRepList
     */
    public ArrayList getEstmTimeRepList() {
        return estmTimeRepList;
    }

    /**
     * @param estmTimeRepList
     *            the estmTimeRepList to set
     */
    public void setEstmTimeRepList(ArrayList estmTimeRepList) {
        this.estmTimeRepList = estmTimeRepList;
    }

    /**
     * @return the editTaskRxResultVO
     */
    public AddEditRxTaskResultVO getEditTaskRxResultVO() {
        return editTaskRxResultVO;
    }

    /**
     * @param editTaskRxResultVO
     *            the editTaskRxResultVO to set
     */
    public void setEditTaskRxResultVO(AddEditRxTaskResultVO editTaskRxResultVO) {
        this.editTaskRxResultVO = editTaskRxResultVO;
    }

    /**
     * @return the editRxTaskResultList
     */
    public ArrayList getEditRxTaskResultList() {
        return editRxTaskResultList;
    }

    /**
     * @param editRxTaskResultList
     *            the editRxTaskResultList to set
     */
    public void setEditRxTaskResultList(ArrayList editRxTaskResultList) {
        this.editRxTaskResultList = editRxTaskResultList;
    }

    /**
     * @return the strFromPage
     */
    public String getStrFromPage() {
        return strFromPage;
    }

    /**
     * @param strFromPage
     *            the strFromPage to set
     */
    public void setStrFromPage(String strFromPage) {
        this.strFromPage = strFromPage;
    }

    /**
     * @return the rxSearchResultServiceVO
     */
    public RxSearchResultServiceVO getRxSearchResultServiceVO() {
        return rxSearchResultServiceVO;
    }

    /**
     * @param rxSearchResultServiceVO
     *            the rxSearchResultServiceVO to set
     */
    public void setRxSearchResultServiceVO(RxSearchResultServiceVO rxSearchResultServiceVO) {
        this.rxSearchResultServiceVO = rxSearchResultServiceVO;
    }

    /**
     * @return the rxSearchResultList
     */
    public ArrayList getRxSearchResultList() {
        return rxSearchResultList;
    }

    /**
     * @param rxSearchResultList
     *            the rxSearchResultList to set
     */
    public void setRxSearchResultList(ArrayList rxSearchResultList) {
        this.rxSearchResultList = rxSearchResultList;
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
    public void setStrSelectBy(String strSelectBy) {
        this.strSelectBy = strSelectBy;
    }

    /**
     * @return the strSelectCond
     */
    public String getStrSelectCond() {
        return strSelectCond;
    }

    /**
     * @param strSelectCond
     *            the strSelectCond to set
     */
    public void setStrSelectCond(String strSelectCond) {
        this.strSelectCond = strSelectCond;
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
    public void setStrCondValue(String strCondValue) {
        this.strCondValue = strCondValue;
    }

    /**
     * @return the strLastUpdatedDate
     */
    public String getStrLastUpdatedDate() {
        return strLastUpdatedDate;
    }

    /**
     * @param strLastUpdatedDate
     *            the strLastUpdatedDate to set
     */
    public void setStrLastUpdatedDate(String strLastUpdatedDate) {
        this.strLastUpdatedDate = strLastUpdatedDate;
    }

    /**
     * @return the strLastUpdatedBy
     */
    public String getStrLastUpdatedBy() {
        return strLastUpdatedBy;
    }

    /**
     * @param strLastUpdatedBy
     *            the strLastUpdatedBy to set
     */
    public void setStrLastUpdatedBy(String strLastUpdatedBy) {
        this.strLastUpdatedBy = strLastUpdatedBy;
    }

    /**
     * @return the rxTypeList
     */
    public ArrayList getRxTypeList() {
        return rxTypeList;
    }

    /**
     * @param rxTypeList
     *            the rxTypeList to set
     */
    public void setRxTypeList(ArrayList rxTypeList) {
        this.rxTypeList = rxTypeList;
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
    public void setStrSelRxType(String strSelRxType) {
        this.strSelRxType = strSelRxType;
    }

    /**
     * @return the strSelRepairCode
     */
    public String getStrSelRepairCode() {
        return strSelRepairCode;
    }

    /**
     * @param strSelRepairCode
     *            the strSelRepairCode to set
     */
    public void setStrSelRepairCode(String strSelRepairCode) {
        this.strSelRepairCode = strSelRepairCode;
    }

    /**
     * @return the strRepairCodeValue
     */
    public String getStrRepairCodeValue() {
        return strRepairCodeValue;
    }

    /**
     * @param strRepairCodeValue
     *            the strRepairCodeValue to set
     */
    public void setStrRepairCodeValue(String strRepairCodeValue) {
        this.strRepairCodeValue = strRepairCodeValue;
    }

    /**
     * @return the repairCodeSearchResultList
     */
    public ArrayList getRepairCodeSearchResultList() {
        return repairCodeSearchResultList;
    }

    /**
     * @param repairCodeSearchResultList
     *            the repairCodeSearchResultList to set
     */
    public void setRepairCodeSearchResultList(ArrayList repairCodeSearchResultList) {
        this.repairCodeSearchResultList = repairCodeSearchResultList;
    }

    /**
     * @return the strSelRepairCodeId
     */
    public String getStrSelRepairCodeId() {
        return strSelRepairCodeId;
    }

    /**
     * @param strSelRepairCodeId
     *            the strSelRepairCodeId to set
     */
    public void setStrSelRepairCodeId(String strSelRepairCodeId) {
        this.strSelRepairCodeId = strSelRepairCodeId;
    }

    /**
     * @return the taskSearchResultList
     */
    public ArrayList getTaskSearchResultList() {
        return taskSearchResultList;
    }

    /**
     * @param taskSearchResultList
     *            the taskSearchResultList to set
     */
    public void setTaskSearchResultList(ArrayList taskSearchResultList) {
        this.taskSearchResultList = taskSearchResultList;
    }

    /**
     * @return the strSelRepair
     */
    public String getStrSelRepair() {
        return strSelRepair;
    }

    /**
     * @param strSelRepair
     *            the strSelRepair to set
     */
    public void setStrSelRepair(String strSelRepair) {
        this.strSelRepair = strSelRepair;
    }

    /**
     * @return the strSelRepairDesc
     */
    public String getStrSelRepairDesc() {
        return strSelRepairDesc;
    }

    /**
     * @param strSelRepairDesc
     *            the strSelRepairDesc to set
     */
    public void setStrSelRepairDesc(String strSelRepairDesc) {
        this.strSelRepairDesc = strSelRepairDesc;
    }

    /**
     * @return the strOriginalId
     */
    public String getStrOriginalId() {
        return strOriginalId;
    }

    /**
     * @param strOriginalId
     *            the strOriginalId to set
     */
    public void setStrOriginalId(String strOriginalId) {
        this.strOriginalId = strOriginalId;
    }

    /**
     * @return the strRevHist
     */
    public String getStrRevHist() {
        return strRevHist;
    }

    /**
     * @param strRevHist
     *            the strRevHist to set
     */
    public void setStrRevHist(String strRevHist) {
        this.strRevHist = strRevHist;
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
    public void setStrActiveFlag(String strActiveFlag) {
        this.strActiveFlag = strActiveFlag;
    }

    /**
     * @return the arlRxHistory
     */
    public ArrayList<RxHistServiceVO> getArlRxHistory() {
        return arlRxHistory;
    }

    /**
     * @param arlRxHistory
     *            the arlRxHistory to set
     */
    public void setArlRxHistory(ArrayList<RxHistServiceVO> arlRxHistory) {
        this.arlRxHistory = arlRxHistory;
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
    public void setBlnDelvFlag(boolean blnDelvFlag) {
        this.blnDelvFlag = blnDelvFlag;
    }

    /**
     * @return the blnRxStatusChk
     */
    public boolean isBlnRxStatusChk() {
        return blnRxStatusChk;
    }

    /**
     * @param blnRxStatusChk
     *            the blnRxStatusChk to set
     */
    public void setBlnRxStatusChk(boolean blnRxStatusChk) {
        this.blnRxStatusChk = blnRxStatusChk;
    }

    /**
     * @return the blnFleetLeader
     */
    public boolean isBlnFleetLeader() {
        return blnFleetLeader;
    }

    /**
     * @param blnFleetLeader
     *            the blnFleetLeader to set
     */
    public void setBlnFleetLeader(boolean blnFleetLeader) {
        this.blnFleetLeader = blnFleetLeader;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strItemLabel", strItemLabel)
                .append("strItemValue", strItemValue).append("strFromPage", strFromPage).append("strRxId", strRxId)
                .append("strTitle", strTitle).append("strRevNo", strRevNo).append("strSelAssetImp", strSelAssetImp)
                .append("strRxType", strRxType).append("strRxStatus", strRxStatus)
                .append("strSelUrgRepair", strSelUrgRepair).append("strSelEstmTimeRepair", strSelEstmTimeRepair)
                .append("strSelectBy", strSelectBy).append("strSelectCond", strSelectCond)
                .append("strCondValue", strCondValue).append("strLastUpdatedDate", strLastUpdatedDate)
                .append("strLastUpdatedBy", strLastUpdatedBy).append("assetImpactList", assetImpactList)
                .append("rxStatusList", rxStatusList).append("urgencyRepList", urgencyRepList)
                .append("estmTimeRepList", estmTimeRepList).append("editTaskRxResultVO", editTaskRxResultVO)
                .append("rxSearchResultServiceVO", rxSearchResultServiceVO)
                .append("editRxTaskResultList", editRxTaskResultList).append("rxSearchResultList", rxSearchResultList)
                .append("strOriginalId", "strOriginalId").append("strRevHist", "strRevHist")
                .append("strActiveFlag", "strActiveFlag").toString();
    }

    public String getStrUrgForPdf() {
        return strUrgForPdf;
    }

    public void setStrUrgForPdf(String strUrgForPdf) {
        this.strUrgForPdf = strUrgForPdf;
    }

    public String getStrEstForPdf() {
        return strEstForPdf;
    }

    public void setStrEstForPdf(String strEstForPdf) {
        this.strEstForPdf = strEstForPdf;
    }

    public boolean isBlnLockRx() {
        return blnLockRx;
    }

    public void setBlnLockRx(boolean blnLockRx) {
        this.blnLockRx = blnLockRx;
    }

    public String getStrLockedBy() {
        return strLockedBy;
    }

    public void setStrLockedBy(String strLockedBy) {
        this.strLockedBy = strLockedBy;
    }

    public boolean isBlnRecommLockedByUser() {
        return blnRecommLockedByUser;
    }

    public void setBlnRecommLockedByUser(boolean blnRecommLockedByUser) {
        this.blnRecommLockedByUser = blnRecommLockedByUser;
    }

    public String getStrRxStatusLbl() {
        return strRxStatusLbl;
    }

    public void setStrRxStatusLbl(String strRxStatusLbl) {
        this.strRxStatusLbl = strRxStatusLbl;
    }

    // Added new attributes for sprint 10
    public String getStrExportControl() {
        return strExportControl;
    }

    public void setStrExportControl(String strExportControl) {
        this.strExportControl = strExportControl;
    }

    public String getStrQueue() {
        return strQueue;
    }

    public void setStrQueue(String strQueue) {
        this.strQueue = strQueue;
    }

    public String getStrModel() {
        return strModel;
    }

    public void setStrModel(String strModel) {
        this.strModel = strModel;
    }

    /**
     * @return the lstEditTaskRxResultVO
     */
    public List<AddEditRxTaskResultVO> getLstEditTaskRxResultVO() {
        return lstEditTaskRxResultVO;
    }

    /**
     * @param lstEditTaskRxResultVO
     *            the lstEditTaskRxResultVO to set
     */
    public void setLstEditTaskRxResultVO(List<AddEditRxTaskResultVO> lstEditTaskRxResultVO) {
        this.lstEditTaskRxResultVO = lstEditTaskRxResultVO;
    }

    public ArrayList<RxConfigVO> getArlRxConfig() {
        return arlRxConfig;
    }

    public void setArlRxConfig(ArrayList<RxConfigVO> arlRxConfig) {
        this.arlRxConfig = arlRxConfig;
    }

    public String getStrDeliveries() {
        return strDeliveries;
    }

    public void setStrDeliveries(String strDeliveries) {
        this.strDeliveries = strDeliveries;
    }

    public String getStrRank() {
        return strRank;
    }

    public void setStrRank(String strRank) {
        this.strRank = strRank;
    }

    public List getSubSystemList() {
        return subSystemList;
    }

    public void setSubSystemList(List subSystemList) {
        this.subSystemList = subSystemList;
    }

    public List getModelList() {
        return modelList;
    }

    public void setModelList(List modelList) {
        this.modelList = modelList;
    }

    // Added new attributes for sprint 10 End

    // New Attributes added for sprint 11
    public String getStrCreatedBy() {
        return strCreatedBy;
    }

    public void setStrCreatedBy(String strCreatedBy) {
        this.strCreatedBy = strCreatedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getStrPrecision() {
        return strPrecision;
    }

    public void setStrPrecision(String strPrecision) {
        this.strPrecision = strPrecision;
    }

	public AddEditRxPlotVO getAddEditRxPlotVO() {
		return addEditRxPlotVO;
	}

	public void setAddEditRxPlotVO(AddEditRxPlotVO addEditRxPlotVO) {
		this.addEditRxPlotVO = addEditRxPlotVO;
	}
	public boolean isClone() {
		return isClone;
	}
	public void setClone(boolean isClone) {
		this.isClone = isClone;
	}

	public String getParentSolutionId() {
		return parentSolutionId;
	}

	public void setParentSolutionId(String parentSolutionId) {
		this.parentSolutionId = parentSolutionId;
	}
    // New Attributes added for sprint 11

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	
}
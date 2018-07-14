/**
 * ============================================================
 * File : FinalVirtualServiceVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.Date;
import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class FinalVirtualServiceVO extends BaseVO {

    static final long serialVersionUID = -2943619455635410586L;
    private String strFinalVirtualId;
    private String strVirtualName;
    private String strVirtualDesc;
    private String strFamily;
    private String strActive;
    private String strVirtualType;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String createdBy;
    private Date creationDate;
    private List<VirtualEquationServiceVO> arlVirtualEquation;
    private List<VirtualDefinitionServiceVO> arlVirtualDef;
    private List<VirtualFilterServiceVO> arlVirtualFilter;
    private List<VirtualHistServiceVO> virtualHistory;
    private String revisionNote;
    
    /**
     * @return the strFinalVirtualId
     */
    public String getStrFinalVirtualId() {
        return strFinalVirtualId;
    }

    /**
     * @param strFinalVirtualId
     *            the strFinalVirtualId to set
     */
    public void setStrFinalVirtualId(final String strFinalVirtualId) {
        this.strFinalVirtualId = strFinalVirtualId;
    }

    /**
     * @return the strVirtualName
     */
    public String getStrVirtualName() {
        return strVirtualName;
    }

    /**
     * @param strVirtualName
     *            the strVirtualName to set
     */
    public void setStrVirtualName(final String strVirtualName) {
        this.strVirtualName = strVirtualName;
    }

    /**
     * @return the strVirtualDesc
     */
    public String getStrVirtualDesc() {
        return strVirtualDesc;
    }

    /**
     * @param strVirtualDesc
     *            the strVirtualDesc to set
     */
    public void setStrVirtualDesc(final String strVirtualDesc) {
        this.strVirtualDesc = strVirtualDesc;
    }

    /**
     * @return the strFamily
     */
    public String getStrFamily() {
        return strFamily;
    }

    /**
     * @param strFamily
     *            the strFamily to set
     */
    public void setStrFamily(final String strFamily) {
        this.strFamily = strFamily;
    }

    /**
     * @return the strActive
     */
    public String getStrActive() {
        return strActive;
    }

    /**
     * @param strActive
     *            the strActive to set
     */
    public void setStrActive(final String strActive) {
        this.strActive = strActive;
    }

    /**
     * @return the strVirtualType
     */
    public String getStrVirtualType() {
        return strVirtualType;
    }

    /**
     * @param strVirtualType
     *            the strVirtualType to set
     */
    public void setStrVirtualType(final String strVirtualType) {
        this.strVirtualType = strVirtualType;
    }

    /**
     * @return the arlVirtualEquation
     */
    public List<VirtualEquationServiceVO> getArlVirtualEquation() {
        return arlVirtualEquation;
    }

    /**
     * @param arlVirtualEquation
     *            the arlVirtualEquation to set
     */
    public void setArlVirtualEquation(final List<VirtualEquationServiceVO> arlVirtualEquation) {
        this.arlVirtualEquation = arlVirtualEquation;
    }

    /**
     * @return the arlVirtualDef
     */
    public List<VirtualDefinitionServiceVO> getArlVirtualDef() {
        return arlVirtualDef;
    }

    /**
     * @param arlVirtualDef
     *            the arlVirtualDef to set
     */
    public void setArlVirtualDef(final List<VirtualDefinitionServiceVO> arlVirtualDef) {
        this.arlVirtualDef = arlVirtualDef;
    }

    /**
     * @return the arlVirtualFilter
     */
    public List<VirtualFilterServiceVO> getArlVirtualFilter() {
        return arlVirtualFilter;
    }

    /**
     * @param arlVirtualFilter
     *            the arlVirtualFilter to set
     */
    public void setArlVirtualFilter(final List<VirtualFilterServiceVO> arlVirtualFilter) {
        this.arlVirtualFilter = arlVirtualFilter;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	public List<VirtualHistServiceVO> getVirtualHistory() {
		return virtualHistory;
	}

	public void setVirtualHistory(List<VirtualHistServiceVO> virtualHistory) {
		this.virtualHistory = virtualHistory;
	}

	public String getRevisionNote() {
		return revisionNote;
	}

	public void setRevisionNote(String revisionNote) {
		this.revisionNote = revisionNote;
	}

}

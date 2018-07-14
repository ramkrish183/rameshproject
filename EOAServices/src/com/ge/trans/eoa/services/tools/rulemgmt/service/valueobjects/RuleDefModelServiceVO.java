/**
 * ============================================================
 * File : RuleDefModelServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;

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
@SuppressWarnings("unchecked")
public class RuleDefModelServiceVO extends BaseVO {

    static final long serialVersionUID = 212126659L;
    private ArrayList arlModel;
    private String modelId;
    private String modelName;
    private ArrayList<RuleDefConfigServiceVO> arlConfigService;

    public RuleDefModelServiceVO() {

    }

    /**
     * @return the arlModel
     */
    public ArrayList getArlModel() {
        return arlModel;
    }

    /**
     * @param arlModel
     *            the arlModel to set
     */
    public void setArlModel(final ArrayList arlModel) {
        this.arlModel = arlModel;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("arlModel", arlModel).toString();
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(final String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public ArrayList<RuleDefConfigServiceVO> getArlConfigService() {
        return arlConfigService;
    }

    public void setArlConfigService(final ArrayList<RuleDefConfigServiceVO> arlConfigService) {
        this.arlConfigService = arlConfigService;
    }
}

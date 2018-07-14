/**
 * ============================================================
 * File : TesterSummaryVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.tester.service.valueobjects
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

package com.ge.trans.eoa.services.kep.ruletester.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This Class consists of the value objects for TesterSummary
 * @History :
 ******************************************************************************/
public class TesterSummaryVO {

    private int totAssetsAnalysed;
    private int noOfAssts;
    private String classifications;
    private String normalDataSets;

    public int getTotAssetsAnalysed() {
        return totAssetsAnalysed;
    }

    public void setTotAssetsAnalysed(final int totAssetsAnalysed) {
        this.totAssetsAnalysed = totAssetsAnalysed;
    }

    public String getClassifications() {
        return classifications;
    }

    public void setClassifications(final String classifications) {
        this.classifications = classifications;
    }

    public int getNoOfAssts() {
        return noOfAssts;
    }

    public void setNoOfAssts(final int noOfAssts) {
        this.noOfAssts = noOfAssts;
    }

    public String getNormalDataSets() {
        return normalDataSets;
    }

    public void setNormalDataSets(final String normalDataSets) {
        this.normalDataSets = normalDataSets;
    }

}

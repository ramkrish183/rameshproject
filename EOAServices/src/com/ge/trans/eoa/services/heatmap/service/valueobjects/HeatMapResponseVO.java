package com.ge.trans.eoa.services.heatmap.service.valueobjects;

import java.util.List;

import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetVO;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class HeatMapResponseVO extends BaseVO {

    private List<AssetVO> assets;
    private List<ModelVO> models;
    private List<FaultVO> faults;
    private List<FaultHeatMapVO> faultHeatMapVO;

    /**
     * @return the assets
     */
    public List<AssetVO> getAssets() {
        return assets;
    }

    /**
     * @param assets
     *            the assets to set
     */
    public void setAssets(List<AssetVO> assets) {
        this.assets = assets;
    }

    /**
     * @return the models
     */
    public List<ModelVO> getModels() {
        return models;
    }

    /**
     * @param models
     *            the models to set
     */
    public void setModels(List<ModelVO> models) {
        this.models = models;
    }

    /**
     * @return the faults
     */
    public List<FaultVO> getFaults() {
        return faults;
    }

    /**
     * @param faults
     *            the faults to set
     */
    public void setFaults(List<FaultVO> faults) {
        this.faults = faults;
    }

    /**
     * @return the faultHeatMapVO
     */
    public List<FaultHeatMapVO> getFaultHeatMapVO() {
        return faultHeatMapVO;
    }

    /**
     * @param faultHeatMapVO the faultHeatMapVO to set
     */
    public void setFaultHeatMapVO(List<FaultHeatMapVO> faultHeatMapVO) {
        this.faultHeatMapVO = faultHeatMapVO;
    }

}

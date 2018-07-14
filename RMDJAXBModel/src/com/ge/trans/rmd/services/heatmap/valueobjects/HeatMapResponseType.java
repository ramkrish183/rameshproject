/**
 * 
 */
package com.ge.trans.rmd.services.heatmap.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.alert.valueobjects.ModelsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetResponseType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultCodesResponseType;

/**
 * @author 212338353
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "heatMapResponseType", propOrder = { "assets",
        "models", "faults","faultHeatMapResponseType"})

@XmlRootElement
public class HeatMapResponseType {
    private List<AssetResponseType> assets;
    private List<ModelsResponseType> models;
    private List<FaultCodesResponseType> faults;
    private List<HeatMapFaultResponseType> faultHeatMapResponseType;
    
    /**
     * @return the assets
     */
    public List<AssetResponseType> getAssets() {
        return assets;
    }
    /**
     * @param assets the assets to set
     */
    public void setAssets(List<AssetResponseType> assets) {
        this.assets = assets;
    }
    /**
     * @return the models
     */
    public List<ModelsResponseType> getModels() {
        return models;
    }
    /**
     * @param models the models to set
     */
    public void setModels(List<ModelsResponseType> models) {
        this.models = models;
    }
    /**
     * @return the faults
     */
    public List<FaultCodesResponseType> getFaults() {
        return faults;
    }
    /**
     * @param faults the faults to set
     */
    public void setFaults(List<FaultCodesResponseType> faults) {
        this.faults = faults;
    }
    /**
     * @return the faultHeatMapResponseType
     */
    public List<HeatMapFaultResponseType> getFaultHeatMapResponseType() {
        return faultHeatMapResponseType;
    }
    /**
     * @param faultHeatMapResponseType the faultHeatMapResponseType to set
     */
    public void setFaultHeatMapResponseType(
            List<HeatMapFaultResponseType> faultHeatMapResponseType) {
        this.faultHeatMapResponseType = faultHeatMapResponseType;
    }

}

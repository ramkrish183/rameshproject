package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.List;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :iGate Patni
 * @Version : 1.0
 * @Date Created: Feb 14, 2012
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Input parameter for finding aset location
 * @History :
 ******************************************************************************/
public class AssetLocatorBean extends BaseVO {

    private String lanuage;
    private String assetNumber;
    private String customerId;
    private String assetGrpName;
    private List<String> products;
    private List<String> prdNameList;
    private List<String> prdIdList;
    private String[] modelId;
    private String[] fleetId;

    public List<String> getPrdNameList() {
        return prdNameList;
    }

    public void setPrdNameList(List<String> prdNameList) {
        this.prdNameList = prdNameList;
    }

    public List<String> getPrdIdList() {
        return prdIdList;
    }

    public void setPrdIdList(List<String> prdIdList) {
        this.prdIdList = prdIdList;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getLanuage() {
        return lanuage;
    }

    public void setLanuage(final String lanuage) {
        this.lanuage = lanuage;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(final String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public String getAssetGrpName() {
        return assetGrpName;
    }

    public void setAssetGrpName(final String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

    public String[] getModelId() {
        return modelId;
    }

    public void setModelId(String[] modelId) {
        this.modelId = modelId;
    }

    public String[] getFleetId() {
        return fleetId;
    }

    public void setFleetId(String[] fleetId) {
        this.fleetId = fleetId;
    }
}

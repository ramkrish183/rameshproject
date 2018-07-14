package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.List;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class PPAssetStatusVO extends BaseVO {

    private String assetNumber;
    private String assetGroupName;
    private String customerId;
    private List<String> products;
    private int ppLookBackDays = 0;
    private String groupNumber;
    private String language;

    public int getPpLookBackDays() {
        return ppLookBackDays;
    }

    public void setPpLookBackDays(int ppLookBackDays) {
        this.ppLookBackDays = ppLookBackDays;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(final String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(final String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

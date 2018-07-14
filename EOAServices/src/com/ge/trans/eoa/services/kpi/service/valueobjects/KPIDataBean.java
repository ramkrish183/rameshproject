package com.ge.trans.eoa.services.kpi.service.valueobjects;

import java.util.List;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: APR 12, 2012
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("serial")
public class KPIDataBean extends BaseVO implements Cloneable {
    private List<KpiAssetCountResponseVO> kpiList;
    private long days;
    private String customerId;
    private String userLanguage;
    private List<String> products;
    private List<String> customerList;
    private List<String> assetList;
    private String kpiName;

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    public List<KpiAssetCountResponseVO> getKpiList() {
        return kpiList;
    }

    public void setKpiList(List<KpiAssetCountResponseVO> kpiList) {
        this.kpiList = kpiList;
    }

    public List<String> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<String> customerList) {
        this.customerList = customerList;
    }

    public List<String> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<String> assetList) {
        this.assetList = assetList;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    /**
     * @return the number of days
     */
    public long getDays() {
        return days;
    }

    /**
     * @param days
     *            to set
     */
    public void setDays(final long days) {
        this.days = days;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     *            to set
     */
    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return user Language.
     */
    public void setUserLanguage(final String userLanguage) {
        this.userLanguage = userLanguage;

    }

    /**
     * @Param set user Language
     */
    public String getUserLanguage() {
        return userLanguage;

    }

    @Override
    public KPIDataBean clone() {
        try {
            return (KPIDataBean) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

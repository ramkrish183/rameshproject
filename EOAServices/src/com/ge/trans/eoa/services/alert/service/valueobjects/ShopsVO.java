package com.ge.trans.eoa.services.alert.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 17, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class ShopsVO extends BaseVO {

    private String customerId;
    private String shopId;
    private String shopName;

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the shopId
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * @param shopId
     *            the shopId to set
     */
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    /**
     * @return the shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName
     *            the shopName to set
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

}

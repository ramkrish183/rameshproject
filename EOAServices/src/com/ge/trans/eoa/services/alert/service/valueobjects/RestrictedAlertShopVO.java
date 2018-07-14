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
public class RestrictedAlertShopVO extends BaseVO {

    private String customerId;
    private String proximityCheckFlag;
    private String alertId;
    private String alertName;

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
     * @return the proximityCheckFlag
     */
    public String getProximityCheckFlag() {
        return proximityCheckFlag;
    }

    /**
     * @param proximityCheckFlag
     *            the proximityCheckFlag to set
     */
    public void setProximityCheckFlag(String proximityCheckFlag) {
        this.proximityCheckFlag = proximityCheckFlag;
    }

    /**
     * @return the alertId
     */
    public String getAlertId() {
        return alertId;
    }

    /**
     * @param alertId
     *            the alertId to set
     */
    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    /**
     * @return the alertName
     */
    public String getAlertName() {
        return alertName;
    }

    /**
     * @param alertName
     *            the alertName to set
     */
    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

}

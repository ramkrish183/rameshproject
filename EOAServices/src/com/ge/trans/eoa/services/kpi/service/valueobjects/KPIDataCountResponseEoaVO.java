package com.ge.trans.eoa.services.kpi.service.valueobjects;

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
public class KPIDataCountResponseEoaVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    private String rxName;
    private String totalCount;
    private String customerId;
    private String rxType;
    private String rxTypeCount;
    private String lastFourWeekAvg;
    private String lastQuarterAvg;
    private String currentYearAvg;
    private String lastUpdatedDate;

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastFourWeekAvg() {
        return lastFourWeekAvg;
    }

    public void setLastFourWeekAvg(String lastFourWeekAvg) {
        this.lastFourWeekAvg = lastFourWeekAvg;
    }

    public String getLastQuarterAvg() {
        return lastQuarterAvg;
    }

    public void setLastQuarterAvg(String lastQuarterAvg) {
        this.lastQuarterAvg = lastQuarterAvg;
    }

    public String getCurrentYearAvg() {
        return currentYearAvg;
    }

    public void setCurrentYearAvg(String currentYearAvg) {
        this.currentYearAvg = currentYearAvg;
    }

    /**
     * @return rxName
     */
    public String getRxName() {
        return rxName;
    }

    /**
     * @param Rx
     *            name to set
     */
    public void setRxName(final String rxName) {
        this.rxName = rxName;
    }

    /**
     * @return TotalCount
     */
    public String getTotalCount() {
        return totalCount;
    }

    /**
     * @param TotalCount
     *            to set
     */
    public void setTotalCount(final String totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return CustomerId
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
     * @return rx type - Red, yellow, white, Blue
     */
    public String getRxType() {
        return rxType;
    }

    /**
     * @param rx
     *            type to set
     */
    public void setRxType(final String rxType) {
        this.rxType = rxType;
    }

    /**
     * @return rxTypeCount
     */
    public String getRxTypeCount() {
        return rxTypeCount;
    }

    /**
     * @param rxTypeCount
     *            to set
     */
    public void setRxTypeCount(final String rxTypeCount) {
        this.rxTypeCount = rxTypeCount;
    }

}

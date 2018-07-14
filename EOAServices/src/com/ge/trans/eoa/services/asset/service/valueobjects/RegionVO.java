package com.ge.trans.eoa.services.asset.service.valueobjects;

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
public class RegionVO extends BaseVO {

    private String regionId;
    private String regionName;
    private String customerId;
    private String SubDivisionId;
    private String SubDivisionName;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSubDivisionId() {
        return SubDivisionId;
    }

    public void setSubDivisionId(String subDivisionId) {
        SubDivisionId = subDivisionId;
    }

    public String getSubDivisionName() {
        return SubDivisionName;
    }

    public void setSubDivisionName(String subDivisionName) {
        SubDivisionName = subDivisionName;
    }

}

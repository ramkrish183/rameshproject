package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.LinkedHashMap;
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
public class RoleAssetsVO extends BaseVO {

    private String language;
    private String assetNumber;
    private String customerId;
    private String roleName;
    private LinkedHashMap<String, String> prdIdMap;

    public LinkedHashMap<String, String> getPrdIdMap() {
        return prdIdMap;
    }

    public void setPrdIdMap(LinkedHashMap<String, String> prdIdMap) {
        this.prdIdMap = prdIdMap;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}

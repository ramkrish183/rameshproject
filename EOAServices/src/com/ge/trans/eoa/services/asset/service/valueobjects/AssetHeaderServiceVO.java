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
public class AssetHeaderServiceVO extends BaseVO {

    private String strAssetNumber;
    private String assetGroupName;
    private String customerID;
	private String fleet;
	private String assetList[];
	private String vehicleObjId;

    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

	public String getFleet() {
		return fleet;
	}

	public void setFleet(String fleet) {
		this.fleet = fleet;
	}

	public String[] getAssetList() {
		return assetList;
	}

	public void setAssetList(String[] assetList) {
		this.assetList = assetList;
	}

    public String getVehicleObjId() {
        return vehicleObjId;
    }

    public void setVehicleObjId(String vehicleObjId) {
        this.vehicleObjId = vehicleObjId;
    }
}

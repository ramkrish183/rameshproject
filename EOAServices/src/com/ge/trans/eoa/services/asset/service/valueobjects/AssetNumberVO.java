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
public class AssetNumberVO extends BaseVO {

    private String strAssetNumber;
    private String customerID;
    private String model;
    private String modelGeneral;
    
    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the modelGeneral
	 */
	public String getModelGeneral() {
		return modelGeneral;
	}

	/**
	 * @param modelGeneral the modelGeneral to set
	 */
	public void setModelGeneral(String modelGeneral) {
		this.modelGeneral = modelGeneral;
	}

}

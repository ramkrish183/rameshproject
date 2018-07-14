package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.UnitRenumberBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.UnitRenumberDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class UnitRenumberBOImpl implements UnitRenumberBOIntf {

	UnitRenumberDAOIntf unitRenumberDAOIntf;
	UnitRenumberBOImpl(UnitRenumberDAOIntf unitRenumberDAO) {
		unitRenumberDAOIntf = unitRenumberDAO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.bo.intf.UnitRenumberBOIntf#updateUnitNumber(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateUnitNumber(String oldValue, String newValue, String vehicleHeader, String customerId) {
		return unitRenumberDAOIntf.updateUnitNumber(oldValue, newValue, vehicleHeader, customerId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.bo.intf.UnitRenumberBOIntf#updateUnitNumberDW(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateUnitNumberDW(String oldValue, String newValue, String vehicleHeader, String customerId) {
		return unitRenumberDAOIntf.updateUnitNumberDW(oldValue, newValue, vehicleHeader, customerId);
	}

	@Override
	public boolean updateUnitHeader(
			UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO) {
		return unitRenumberDAOIntf.updateUnitHeader(objUnitRoadHeaderUpdateVO);
	}

	@Override
	public boolean updateUnitHeaderDW(
			UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO) {
		return unitRenumberDAOIntf.updateUnitHeaderDW(objUnitRoadHeaderUpdateVO);
	}

    /*
     * This method is used to call getAssets() method in UnitRenumDAOImpl and fetch
     * the List of Assets
     */
    @Override
	public List<AssetServiceVO> getAssetsForUnitRenum(final AssetServiceVO objAssetServiceVO)
            throws RMDBOException {
        List<AssetServiceVO> arlAsset = null;

        try {
            /*SETTING PRODUCTlST AND CUSTOMERlIST AND PASSING TO DAO*/
                        
            /* Enters if block if product asset config is available */
                arlAsset = unitRenumberDAOIntf.getAssetsForUnitRenum(objAssetServiceVO);
        
        } catch (RMDDAOException ex) {

            throw ex;

        } catch (Exception exc) {

            String errorCode = RMDCommonUtility

            .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_NOS);

            throw new RMDDAOException(errorCode, new String[] {},

            RMDCommonUtility.getMessage(errorCode, new String[] {},

            objAssetServiceVO.getStrLanguage()), exc,

            RMDCommonConstants.MINOR_ERROR);
        }

        return arlAsset;
    }
}

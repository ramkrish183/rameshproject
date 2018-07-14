package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.asset.bo.intf.UnitRenumberBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.UnitRenumberServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class UnitRenumberServiceImpl implements UnitRenumberServiceIntf {

	UnitRenumberBOIntf unitRenumberBOIntf;
	UnitRenumberServiceImpl(UnitRenumberBOIntf unitRenumberBOIntf) {
		this.unitRenumberBOIntf = unitRenumberBOIntf;
	}
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.service.intf.UnitRenumberServiceIntf#updateUnitNumber(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateUnitNumber(String oldValue, String newValue, String vehicleHeader, String customerId) {
		return unitRenumberBOIntf.updateUnitNumber(oldValue, newValue, vehicleHeader, customerId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.service.intf.UnitRenumberServiceIntf#updateUnitNumberDW(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateUnitNumberDW(String oldValue, String newValue, String vehicleHeader, String customerId) {
		return unitRenumberBOIntf.updateUnitNumberDW(oldValue, newValue, vehicleHeader, customerId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.service.intf.UnitRenumberServiceIntf#updateUnitHeader(com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO)
	 */
	@Override
	public boolean updateUnitHeader(
			UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO) {
		return unitRenumberBOIntf.updateUnitHeader(objUnitRoadHeaderUpdateVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.service.intf.UnitRenumberServiceIntf#updateUnitHeaderDW(com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO)
	 */
	@Override
	public boolean updateUnitHeaderDW(
			UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO) {
		return unitRenumberBOIntf.updateUnitHeaderDW(objUnitRoadHeaderUpdateVO);
	}

	@Override
    public List<AssetServiceVO> getAssetsForUnitRenum(AssetServiceVO objAssetServiceVO) throws RMDServiceException {
        List<AssetServiceVO> arlAsset = null;
        try {
            arlAsset = unitRenumberBOIntf.getAssetsForUnitRenum(objAssetServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objAssetServiceVO.getStrLanguage());
        }
        return arlAsset;
    }
	
}

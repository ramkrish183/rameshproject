package com.ge.trans.eoa.services.asset.service.impl;

import java.io.Serializable;
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.asset.bo.intf.AssetEoaBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.AssetEoaServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetHeaderServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetLocationDetailVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetsLocationFromShopVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultCodeDetailsServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindLocationEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FleetVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LifeStatisticsEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RegionVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RoleAssetsVO;
import com.ge.trans.rmd.asset.valueobjects.AssetInstalledProductVO;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorBean;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class AssetEoaServiceImpl implements Serializable, AssetEoaServiceIntf {
    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(AssetEoaServiceImpl.class);
    private AssetEoaBOIntf objAssetEoaBOIntf;

    public AssetEoaServiceImpl(AssetEoaBOIntf objAssetBOIntf) {
        this.objAssetEoaBOIntf = objAssetBOIntf;
    }

    @Override
    public List<AssetServiceVO> getAssets(AssetServiceVO objAssetServiceVO) throws RMDServiceException {
        List<AssetServiceVO> arlAsset = null;
        try {
            arlAsset = objAssetEoaBOIntf.getAssets(objAssetServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objAssetServiceVO.getStrLanguage());
        }
        return arlAsset;
    }

    @Override
    public List<AssetInstalledProductVO> getInstalledProduct(AssetServiceVO objAssetServiceVO)
            throws RMDServiceException {
        List<AssetInstalledProductVO> arlAssetProdLst = null;
        try {
            arlAssetProdLst = objAssetEoaBOIntf.getInstalledProduct(objAssetServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);

        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objAssetServiceVO.getStrLanguage());
        }
        return arlAssetProdLst;
    }

    /**
     * @Author : iGatte Patni
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Latitude,Longitude and
     *               max occurrence time of the asset
     */
    @Override
    public List<AssetLocatorVO> getAssetLocation(final AssetLocatorBean assetLocatorBean) throws RMDServiceException {

        List<AssetLocatorVO> assetLocatorVoLst = null;
        try {
            assetLocatorVoLst = objAssetEoaBOIntf.getAssetLocation(assetLocatorBean);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, assetLocatorBean.getStrLanguage());
        }
        return assetLocatorVoLst;
    }

    /**
     * @Author : iGatte Patni
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Last Download status
     *               and other fields for an asset for diesel doctor role
     */
    @Override
    public LastDownloadStatusResponseEoaVO getLastDowloadStatus(final LastDownloadStatusEoaVO lstDownloadStatus)
            throws RMDServiceException {

        LastDownloadStatusResponseEoaVO lstDownloadStatusResp = null;
        try {
            lstDownloadStatusResp = objAssetEoaBOIntf.getLastDowloadStatus(lstDownloadStatus);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, lstDownloadStatus.getStrLanguage());
        }
        return lstDownloadStatusResp;
    }

    /**
     * @Author : GE
     * @param :
     * @return : LastFaultStatusResponseVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Last Fault status and
     *               other fields for an asset for any role
     */
    @Override
    public LastFaultStatusResponseEoaVO getLastFaultStatus(final LastFaultStatusEoaVO lstFaultStatus) {

        LastFaultStatusResponseEoaVO lstFaultStatusResp = null;
        try {
            lstFaultStatusResp = objAssetEoaBOIntf.getLastFaultStatus(lstFaultStatus);
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getLastFaultStatus()" + ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getLastFaultStatus()" + exc);
        }

        return lstFaultStatusResp;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.cm.services.tools.rulemgmt.service.intf.RuleServiceIntf
     * #getFleetList(java.lang.String[], java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String) Method to retrieve Fleet List based
     * on Customer, Model, Unit No and Configuration
     */
    @Override
    public List getFleetList(String strCustomer, String strModel, String[] strConfig, String strUnitNumber,
            String strLanguage) throws RMDServiceException, RMDBOException {
        List arlFleet = null;
        try {
            arlFleet = objAssetEoaBOIntf.getFleetList(strCustomer, strModel, strConfig, strUnitNumber, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlFleet;
    }

    @Override
    public List<ElementVO> getModels(String strLanguage) throws RMDServiceException {

        List<ElementVO> modelList = null;
        try {
            modelList = objAssetEoaBOIntf.getModels(strLanguage);
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getModels()" + ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getModels()" + exc);
        }
        return modelList;
    }

    @Override
    public List<ElementVO> getModelList(String strFamily, String strLanguage) throws RMDServiceException {
        List arlModel = null;
        try {
            arlModel = objAssetEoaBOIntf.getModelList(strFamily, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlModel;
    }

    @Override
    public List<ElementVO> getModelList(String strCustomer, String strFleet, String[] strConfig, String strUnitNumber,
            String strFamily, String strLanguage) throws RMDServiceException {

        List<ElementVO> arlModel = null;
        try {
            arlModel = objAssetEoaBOIntf.getModelList(strCustomer, strFleet, strConfig, strUnitNumber, strFamily,
                    strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlModel;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.LocationServiceIntf#
     * findLocation(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FindLocationServiceVO)
     *//*
       * This Method is used for call the findLocation method in LocationBOImpl
       */
    @Override
    public List findLocation(FindLocationEoaServiceVO objFindLocationServiceVO) throws RMDServiceException {
        LOG.debug("Begin LocationServiceImpl findLocation method");
        List arlLocation = null;
        try {
            arlLocation = objAssetEoaBOIntf.findLocation(objFindLocationServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFindLocationServiceVO.getStrLanguage());
        }
        LOG.debug("End LocationServiceImpl findLocation method");
        return arlLocation;
    }

    /**
     * @Author : iGatte Patni
     * @param :
     * @return : List<String>
     * @throws RMDServiceException
     * @Description: Calls the BO layer and returns services available for an
     *               asset
     */
    @Override
    public List<String> getAssetServices(AssetServiceVO objAssetServiceVO) throws RMDServiceException {
        List<String> serviceLst = null;
        try {
            serviceLst = objAssetEoaBOIntf.getAssetServices(objAssetServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objAssetServiceVO.getStrLanguage());
        }
        return serviceLst;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.NotesServiceIntf
     * #saveNotes(com.ge.trans.rmd.services.cases.service.valueobjects.
     * AddNotesServiceVO) This method is used to call the saveNotes method in
     * AddNotesBOImpl
     */
    @Override
    public void saveNotes(AddNotesEoaServiceVO notesServiceVO) throws RMDServiceException {
        try {
            objAssetEoaBOIntf.saveNotes(notesServiceVO);
        } catch (RMDDAOException ex) {
            LOG.error("Unexceptected Error in Service method saveNotes()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            LOG.error("Unexceptected Error in Service method saveNotes()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, notesServiceVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.asset.service.intf.AssetEoaServiceIntf#
     * getMapLastRefreshTime(com.ge.trans.rmd.common.valueobjects.
     * GetSysLookupVO)
     */
    @Override
    public List<GetSysLookupVO> getMapLastRefreshTime(GetSysLookupVO sysLookupVO)
            throws RMDServiceException {
        try {
            return objAssetEoaBOIntf.getMapLastRefreshTime(sysLookupVO);
        } catch (RMDDAOException ex) {
            LOG.error("Unexceptected Error in Service method saveNotes()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            LOG.error("Unexceptected Error in Service method saveNotes()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, sysLookupVO
                    .getStrLanguage());
        }
        return null;
    }

    @Override
    public List<FaultCodeDetailsServiceVO> getFaultCode(String controllerCfg, String faultCode, String language)
            throws RMDServiceException {
        List<FaultCodeDetailsServiceVO> arlFaultCode = null;
        try {
            arlFaultCode = objAssetEoaBOIntf.getFaultCode(controllerCfg, faultCode, language);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }

        return arlFaultCode;

    }

    /**
     * @Author : iGatte Patni
     * @param :
     * @return : List<String>
     * @throws RMDServiceException
     * @Description: Calls the BO layer and returns services available for an
     *               asset
     */
    @Override
    public List<LifeStatisticsEoaVO> getLifeStatisticsData(AssetServiceVO objAssetServiceVO)
            throws RMDServiceException {
        List<LifeStatisticsEoaVO> resultLst = null;
        try {
            resultLst = objAssetEoaBOIntf.getLifeStatisticsData(objAssetServiceVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return resultLst;
    }

    @Override
    public List<AssetServiceVO> getAssetGroups(AssetServiceVO objAssetServiceVO) throws Exception {
        List<AssetServiceVO> arlAsset = null;
        try {
            arlAsset = objAssetEoaBOIntf.getAssetGroups(objAssetServiceVO);
        } catch (Exception e) {
            throw e;
        }
        return arlAsset;

    }

    /**
     * @Author : iGatte Patni
     * @param :
     * @return : RoleAssetsVO
     * @throws RMDServiceException
     * @Description: Calls the BO layer and returns the list of Assets
     */
    @Override
    public RoleAssetsVO getAssetsForRole(final RoleAssetsVO objRoleAssetsVO) throws RMDServiceException {

        RoleAssetsVO objRoleAssetsVOLst = null;
        try {
            objRoleAssetsVOLst = objAssetEoaBOIntf.getAssetsForRole(objRoleAssetsVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objRoleAssetsVO.getStrLanguage());
        }
        return objRoleAssetsVOLst;
    }

    @Override
    public List<FleetVO> getAllFleets() throws RMDServiceException {

        List<FleetVO> objFleetsVOLst = null;
        try {
            objFleetsVOLst = objAssetEoaBOIntf.getAllFleets();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objFleetsVOLst;
    }

    @Override
    public List<RegionVO> getAllRegions() throws RMDServiceException {

        List<RegionVO> objRegionsVOLst = null;
        try {
            objRegionsVOLst = objAssetEoaBOIntf.getAllRegions();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objRegionsVOLst;
    }

    @Override
    public List<RegionVO> getAllSubDivisions(String region) throws RMDServiceException {

        List<RegionVO> objRegionsVOLst = null;
        try {
            objRegionsVOLst = objAssetEoaBOIntf.getAllSubDivisions(region);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objRegionsVOLst;
    }

    /**
     * @Author:
     * @param:String vehicleObjId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used check whether PP Asset History Button
     *               Has to Disable or Enable.
     */
    @Override
    public String displayPPAssetHistoryBtn(String vehicleObjId) throws RMDServiceException {
        String result = null;
        try {
            result = objAssetEoaBOIntf.displayPPAssetHistoryBtn(vehicleObjId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return result;
    }

    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    @Override
    public List<AssetServiceVO> getCustNameRNH() throws RMDServiceException {
        List<AssetServiceVO> eoaAssetsLst = null;
        try {
            eoaAssetsLst = objAssetEoaBOIntf.getCustNameRNH();

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return eoaAssetsLst;
    }

    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    @Override
    public List<AssetServiceVO> getRoadNumbers(AssetServiceVO objAssetServiceVO) throws RMDServiceException {
        List<AssetServiceVO> eoaAssetsLst = null;
        try {
            eoaAssetsLst = objAssetEoaBOIntf.getRoadNumbers(objAssetServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return eoaAssetsLst;
    }

    /**
     * @Author :Mohamed
     * @return :List<AssetLocatorVO>
     * @param :AssetOverviewBean
     *            assetBean
     * @throws :RMDWebException
     * @Description:This method fetches the common section of the asset case by
     *                   invoking web services getAssetCaseCommSection() method.
     */
    @Override
    public List<AssetLocatorVO> getAssetCaseCommSection(final AssetLocatorBean assetLocatorBean)
            throws RMDServiceException {
        List<AssetLocatorVO> assetLocatorVoLst = null;
        try {
            assetLocatorVoLst = objAssetEoaBOIntf.getAssetCaseCommSection(assetLocatorBean);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, assetLocatorBean.getStrLanguage());
        }
        return assetLocatorVoLst;
    }

    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching road numbers based on
     *               filter options
     */

    @Override
    public List<AssetServiceVO> getRoadNumbersWithFilter(String customer, String rnhId, String rnSearchString,
            String rnFilter) throws RMDServiceException {
        List<AssetServiceVO> eoaAssetsLst = null;
        try {
            eoaAssetsLst = objAssetEoaBOIntf.getRoadNumbersWithFilter(customer, rnhId, rnSearchString, rnFilter);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return eoaAssetsLst;
    }

    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching Customer Id based on
     *               assetNumber and assetGrpName.
     * 
     */
    @Override
    public String getCustomerId(String assetNumber, String assetGrpName) throws RMDServiceException {
        String customerId = null;
        try {
            customerId = objAssetEoaBOIntf.getCustomerId(assetNumber, assetGrpName);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return customerId;
    }
    


    @Override
    public List<AssetHeaderServiceVO> getAssetsData(
            AssetServiceVO objAssetServiceVO) throws RMDServiceException {
        // TODO Auto-generated method stub
        List<AssetHeaderServiceVO> arlAsset = null;
        try{
            arlAsset=objAssetEoaBOIntf.getAssetsData(objAssetServiceVO);
        }catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex,
                    objAssetServiceVO.getStrLanguage());
        }
        return arlAsset;
    }
    @Override
	public List<AssetNumberVO> getModelByFilter(
			AssetHeaderServiceVO assetHeaderServiceVO)
			throws RMDServiceException {
		List<AssetNumberVO> arlAsset = null;
		try{
			arlAsset=objAssetEoaBOIntf.getModelByFilter(assetHeaderServiceVO);
		}catch (RMDDAOException ex) {
			throw new RMDServiceException(ex);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex);
		} 
		return arlAsset;
	}
    
    @Override
   	public List<AssetLocationDetailVO> getAssetsLocation(AssetsLocationFromShopVO objAssetsLocationFromShopVO)throws RMDServiceException {
   		List<AssetLocationDetailVO> assetDeatils = null;
   		try{
   			assetDeatils=objAssetEoaBOIntf.getAssetsLocationDeatil(objAssetsLocationFromShopVO);
   		}catch (RMDDAOException ex) {
   			throw new RMDServiceException(ex.getErrorDetail(),ex);
   		} catch (RMDBOException ex) {
   			throw new RMDServiceException(ex.getErrorDetail(),ex);
   		} 
   		return assetDeatils;
   	}
    @Override
   	public String getLocoId(AssetServiceVO objAssetServiceVO)throws RMDServiceException {
   		String locoId = null;
   		try{
   			locoId=objAssetEoaBOIntf.getLocoId(objAssetServiceVO);
   		}catch (RMDDAOException ex) {
   			throw new RMDServiceException(ex.getErrorDetail(),ex);
   		} catch (RMDBOException ex) {
   			throw new RMDServiceException(ex.getErrorDetail(),ex);
   		} 
   		return locoId;
   	}
}

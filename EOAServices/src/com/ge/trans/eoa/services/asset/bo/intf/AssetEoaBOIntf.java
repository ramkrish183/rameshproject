package com.ge.trans.eoa.services.asset.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetHeaderServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetLocationDetailVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetsLocationFromShopVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindLocationEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FleetVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LifeStatisticsEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RegionVO;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorBean;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.asset.service.valueobjects.RoleAssetsVO;
import com.ge.trans.rmd.asset.valueobjects.AssetInstalledProductVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultCodeDetailsServiceVO;

public interface AssetEoaBOIntf {

    List<AssetServiceVO> getAssets(AssetServiceVO objAssetServiceVO) throws RMDBOException;
    /**
     * @Author : iGatte Patni
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Latitude,Longitude and
     *               max occurrence time of the asset
     */
    List<AssetLocatorVO> getAssetLocation(
            final AssetLocatorBean assetLocatorBean) throws RMDBOException;
    /**
     * @Author : iGatte Patni
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Last Download status
     *               and other fields for an asset for diesel doctor role
     */
    LastDownloadStatusResponseEoaVO getLastDowloadStatus(
            final LastDownloadStatusEoaVO lstDownloadStatus) throws RMDBOException;
      /**
         * @Author : GE
         * @param : 
         * @return : LastFaultStatusResponseVO
         * @throws RMDDAOException
         * @Description: Calls the DAO layer and returns the Last Fault status and other fields for an asset for any role
         */
       LastFaultStatusResponseEoaVO getLastFaultStatus(final LastFaultStatusEoaVO lstFaultStatus) throws RMDDAOException;
       
      /**
       *  Method to fetch model list
       * @return 
       * @throws RMDDAOException
       */
       List <ElementVO>  getModels( String strLanguage ) throws RMDBOException;
    
       /**
        * 
        * @param strCustomer
        * @param strFleet
        * @param strConfig
        * @param strUnitNumber
        * @param strFamily
        * @param strLanguage
        * @return
        * @throws RMDDAOException
        */
       List<ElementVO> getModelList(String strCustomer, String strFleet, String[] strConfig, String strUnitNumber, String strFamily,
            String strLanguage)  throws RMDBOException;
    
       /**
        * 
        * @param strFamily
        * @param strLanguage
        * @return
        * @throws RMDBOException
        */
       List<ElementVO>   getModelList(String strFamily, String strLanguage) throws RMDBOException;

       /**
     * @param strCustomer
     * @param strModel
     * @param strConfig
     * @param strUnitNumber
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    List getFleetList(String strCustomer,String strModel,String strConfig[],String strUnitNumber,String strLanguage) throws RMDDAOException;
    
    /**
     * @param objFindLocationServiceVO
     * @return
     * @throws RMDBOException
     */
    public List findLocation(FindLocationEoaServiceVO objFindLocationServiceVO)
            throws RMDBOException;
    
    /**
     * @param objAssetServiceVO
     * @return
     * @throws RMDBOException
     */
    public List<String> getAssetServices(AssetServiceVO objAssetServiceVO) throws RMDBOException;
    
    /**
     * @param notesServiceVO
     * @throws RMDBOException
     */
    public void saveNotes(AddNotesEoaServiceVO notesServiceVO)
            throws RMDBOException;
    
    /**
     * @param sysLookupVO
     * @throws RMDBOException
     */
    List<GetSysLookupVO> getMapLastRefreshTime(GetSysLookupVO sysLookupVO) throws RMDBOException;
    List<FaultCodeDetailsServiceVO> getFaultCode(
            String controllerCfg,String faultCode,String language) throws RMDBOException;
    /**
     * @param LifeStatisticsEoaVO
     * @return List<LifeStatisticsEoaVO> 
     * @throws RMDBOException
     */
    List<LifeStatisticsEoaVO> getLifeStatisticsData(
            AssetServiceVO objAssetServiceVO) throws RMDBOException;
    
    List<AssetServiceVO> getAssetGroups(AssetServiceVO objAssetServiceVO)
            throws Exception;

    /**
     * @Author : iGatte Patni
     * @param :
     * @return : RoleAssetsVO
     * @throws RMDBOException
     * @Description: Calls the BO Impl and returns list of Assets
     */
    RoleAssetsVO getAssetsForRole(
            final RoleAssetsVO objRoleAssetsVO) throws RMDBOException;
    public List<FleetVO> getAllFleets() throws RMDBOException;
    public List<RegionVO> getAllRegions() throws RMDBOException;
    public List<RegionVO> getAllSubDivisions(String region) throws RMDBOException;
    List<AssetInstalledProductVO> getInstalledProduct(
            AssetServiceVO objAssetServiceVO) throws RMDBOException;
    /**
     * @Author:
     * @param:String vehicleObjId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used check whether PP Asset History Button
     *               Has to Disable or Enable.
     */
    public String displayPPAssetHistoryBtn(String vehicleObjId) throws RMDBOException;
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    public List<AssetServiceVO> getCustNameRNH() throws RMDBOException;
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    public List<AssetServiceVO> getRoadNumbers(AssetServiceVO objAssetServiceVO) throws RMDBOException;
    
    /**
     * @Author  :Mohamed
     * @return  :List<AssetLocatorVO>
     * @param   :AssetOverviewBean assetBean
     * @throws RMDBOException 
     * @throws  :RMDWebException
     * @Description:This method fetches the common section of the asset case
     *               by invoking web services getAssetCaseCommSection() method.
     */
    public List<AssetLocatorVO> getAssetCaseCommSection(final AssetLocatorBean assetLocatorBean) throws RMDBOException;
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the road numbers based on  Customer Name and Road
     *               Number Headers and filter options
     */
    public List<AssetServiceVO> getRoadNumbersWithFilter(String customer,
            String rnhId, String rnSearchString, String rnFilter)
            throws RMDBOException;
    
    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching Customer Id based on
     *               assetNumber and assetGrpName.
     * 
     */
    public String getCustomerId(String assetNumber, String assetGrpName)
            throws RMDBOException;
    
    List<AssetHeaderServiceVO> getAssetsData(AssetServiceVO objAssetServiceVO) throws RMDBOException;
    public List<AssetNumberVO> getModelByFilter(AssetHeaderServiceVO assetHeaderServiceVO) throws RMDBOException;
    public List<AssetLocationDetailVO> getAssetsLocationDeatil(AssetsLocationFromShopVO objAssetsLocationFromShopVO) throws RMDBOException;
    public String getLocoId(AssetServiceVO objAssetServiceVO)throws RMDBOException;
    
}

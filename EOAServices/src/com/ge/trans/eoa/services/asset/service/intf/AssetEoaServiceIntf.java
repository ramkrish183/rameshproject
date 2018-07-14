package com.ge.trans.eoa.services.asset.service.intf;


import java.util.List;

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
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
public interface AssetEoaServiceIntf {
    List<AssetServiceVO> getAssets(AssetServiceVO objAssetServiceVO)
            throws RMDServiceException;

    /**
     * @param assetLocatorBean
     * @return
     * @throws RMDServiceException
     */
    List<AssetLocatorVO> getAssetLocation(AssetLocatorBean assetLocatorBean)
            throws RMDServiceException;
    /**
     * @Author : GE
     * @param : 
     * @return : LastFaultStatusResponseVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Last Fault status and other fields for an asset for any role
     */
    LastFaultStatusResponseEoaVO getLastFaultStatus(final LastFaultStatusEoaVO lstFaultStatus) throws RMDServiceException;
    /**
     * @Author : iGatte Patni
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Last Download status
     *               and other fields for an asset for diesel doctor role
     */
    LastDownloadStatusResponseEoaVO getLastDowloadStatus(
            final LastDownloadStatusEoaVO lstDownloadStatus)
            throws RMDServiceException;
    
    /**
     * @param strCustomer
     * @param strModel
     * @param strConfig
     * @param strUnitNumber
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     * @throws RMDBOException
     */
    List getFleetList(String strCustomer,String strModel,String strConfig[],String strUnitNumber,String strLanguage) throws RMDServiceException,RMDBOException;
    
    /**
     * 
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    public List <ElementVO> getModels ( String strLanguage ) throws RMDServiceException;
    

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
    */
    //List<ElementVO> getModel(String strLanguage,String strCustomerId) throws RMDServiceException;
    

    /**
     * 
     * @param strFamily
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<ElementVO> getModelList(String strFamily,String strLanguage) throws RMDServiceException;
    
    /**
     * 
     * @param strCustomer
     * @param strFleet
     * @param strConfig
     * @param strUnitNumber
     * @param strFamily
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<ElementVO> getModelList(String strCustomer,String strFleet,String strConfig[],String strUnitNumber,String strFamily, String strLanguage) throws RMDServiceException;
    /**
     * @param objFindLocationServiceVO
     * @return
     * @throws RMDServiceException
     */
    public List findLocation(FindLocationEoaServiceVO objFindLocationServiceVO)
            throws RMDServiceException;

    
    /**
     * @param objAssetServiceVO
     * @return
     * @throws RMDServiceException
     */
    public List<String> getAssetServices(AssetServiceVO objAssetServiceVO) throws RMDServiceException;  
    
    /**
     * @param notesServiceVO
     * @throws RMDServiceException
     */
    public void saveNotes(AddNotesEoaServiceVO notesServiceVO)
            throws RMDServiceException;

    /**
     * @param sysLookupVO
     * @return
     * @throws RMDServiceException
     */
    List<GetSysLookupVO> getMapLastRefreshTime(GetSysLookupVO sysLookupVO) throws RMDServiceException;

    List<FaultCodeDetailsServiceVO> getFaultCode(String controllerCfg,
            String faultCode, String strLanguage) throws RMDServiceException;
    /**
     * @param AssetServiceVO
     * @returnList<LifeStatisticsVO> 
     * @throws RMDServiceException
     */
    List<LifeStatisticsEoaVO> getLifeStatisticsData(
            AssetServiceVO objAssetServiceVO) throws RMDServiceException;
    
    List<AssetServiceVO> getAssetGroups(AssetServiceVO objAssetServiceVO)
            throws RMDServiceException, Exception;
    
    /**
     * @Author : iGatte Patni
     * @param :
     * @return : RoleAssetsVO
     * @throws RMDServiceException
     * @Description: Calls the Service Impl and returns list of Assets
     */
    RoleAssetsVO getAssetsForRole(
            final RoleAssetsVO objRoleAssetsVO) throws RMDServiceException;

    public List<FleetVO> getAllFleets() throws RMDServiceException;

    public List<RegionVO> getAllRegions() throws RMDServiceException;
    public List<RegionVO> getAllSubDivisions(String region) throws RMDServiceException;
    List<AssetInstalledProductVO> getInstalledProduct(
            AssetServiceVO objAssetServiceVO) throws RMDServiceException;
    /**
     * @Author:
     * @param:String vehicleObjId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used check whether PP Asset History Button
     *               Has to Disable or Enable.
     */
    public String displayPPAssetHistoryBtn(String vehicleObjId)
            throws RMDServiceException;
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    public List<AssetServiceVO> getCustNameRNH()    throws RMDServiceException; 
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    public List<AssetServiceVO>getRoadNumbers(AssetServiceVO objAssetServiceVO) throws RMDServiceException;
    
    /**
     * @Author  :Mohamed
     * @return  :List<AssetLocatorVO>
     * @param   :AssetOverviewBean assetBean
     * @throws  :RMDWebException
     * @Description:This method fetches the common section of the asset case
     *               by invoking web services getAssetCaseCommSection() method.
     */

    public List<AssetLocatorVO> getAssetCaseCommSection(AssetLocatorBean assetLocatorBean) throws RMDServiceException;

    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching road numbers based on filter options
     *               
     */
    public List<AssetServiceVO>getRoadNumbersWithFilter(String customer, String rnhId, String rnSearchString, String rnFilter) throws RMDServiceException;  
    
    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching Customer Id based on
     *               assetNumber and assetGrpName.
     * 
     */
    public String getCustomerId(String assetNumber, String assetGrpName)
            throws RMDServiceException;
    
    List<AssetHeaderServiceVO> getAssetsData(AssetServiceVO objAssetServiceVO)
            throws RMDServiceException;
    public List<AssetNumberVO> getModelByFilter(AssetHeaderServiceVO assetHeaderServiceVO) throws RMDServiceException;
    public List<AssetLocationDetailVO> getAssetsLocation(AssetsLocationFromShopVO objAssetsLocationFromShopVO) throws RMDServiceException;
    public String getLocoId(AssetServiceVO objAssetServiceVO) throws RMDServiceException;

    
}

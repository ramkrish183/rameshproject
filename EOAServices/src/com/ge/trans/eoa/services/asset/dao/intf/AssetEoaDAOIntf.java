package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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

public interface AssetEoaDAOIntf {

    List<AssetServiceVO> getAssets(AssetServiceVO objAssetServiceVO) throws RMDDAOException;
    /**
     * @Author : iGatte Patni
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Latitude,Longitude and
     *               max occurrence time of the asset
     */
    
    Map<String, LinkedHashMap<String, AssetLocatorVO>> getAssetLocationForCustomers(final AssetLocatorBean assetLocatorBean) throws RMDDAOException;
    
    //Map<String, LinkedHashMap<String, AssetLocatorVO>> getAssetLocation()
        //  throws RMDDAOException;
    /**
     * @Author : iGatte Patni
     * @param :
     * @return : List<RoleAssetsVO>
     * @throws RMDBOException
     * @Description: Calls the DAO Impl and returns list of Assets
     */
    LinkedHashMap<String, List<String>> getAssetNoForProducts(List<String> prdIdList,String customerId) throws RMDDAOException;
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
            throws RMDDAOException;

    /**
     * @Author : GE
     * @param : 
     * @return : LastFaultStatusResponseVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Last Fault status and other fields for an asset for any doctor role
     */
    LastFaultStatusResponseEoaVO getLastFaultStatus(final LastFaultStatusEoaVO lstFaultStatus) throws RMDDAOException;
    
    /**
     * 
     * @return list of models
     * @throws RMDDAOException
     */
    public List <ElementVO> getModels( String strLanguage ) throws RMDDAOException;
    
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
            String strLanguage)throws RMDDAOException;
    
    /**
     * 
     * @param strfamily
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    List<ElementVO> getModelList(String strfamily, String strLanguage)throws RMDDAOException;

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
     * @throws RMDDAOException
     */
    public List findLocation(FindLocationEoaServiceVO objFindLocationServiceVO)
            throws RMDDAOException;
    
    /**
     * @param objAssetServiceVO
     * @return
     * @throws RMDDAOException
     */
    public List<String> getAssetServices(AssetServiceVO objAssetServiceVO) throws RMDDAOException;
    
    /**
     * @param notesServiceVO
     * @return
     * @throws RMDDAOException
     */
    public String saveNotes(AddNotesEoaServiceVO notesServiceVO)
            throws RMDDAOException;
    /**
     * @param sysLookupVO
     * @return
     * @throws RMDDAOException
     */
    List<GetSysLookupVO> getMapLastRefreshTime(String listName) throws RMDDAOException;
    List<FaultCodeDetailsServiceVO> getFaultCode(String controllerCfg,
            String faultCode, String language) throws RMDDAOException;
    /**
     * @param sysLookupVO
     * @return
     * @throws RMDDAOException
     */
    List<LifeStatisticsEoaVO> getLifeStatisticsData(
            AssetServiceVO objAssetServiceVO) throws RMDDAOException;
    
    List<AssetServiceVO> getAssetGroups(AssetServiceVO objAssetServiceVO)
            throws RMDDAOException;
    
    RoleAssetsVO getProductsForRole(
            final RoleAssetsVO objRoleAssetsVO) throws RMDDAOException;
    public List<FleetVO> getAllFleets() throws RMDDAOException;
    public  List<RegionVO> getAllRegions() throws RMDDAOException;
    public List<RegionVO> getAllSubDivisions(String region) throws RMDDAOException;
    List<AssetInstalledProductVO> getInstalledProduct(
            AssetServiceVO objAssetServiceVO);
    /**
     * @Author:
     * @param:String vehicleObjId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used check whether PP Asset History Button
     *               Has to Disable or Enable.
     */
    public String displayPPAssetHistoryBtn(String vehicleObjId) throws RMDDAOException;
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    public List<AssetServiceVO> getCustNameRNH()throws RMDDAOException;
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    public List<AssetServiceVO> getRoadNumbers(AssetServiceVO objAssetServiceVO) throws RMDDAOException;
    
    /**
     * @Author:Mohamed
     * @param:customerId, assetNumber, assetGrpName
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the vehicle object id of the asset
     */
    public String getVehicleObjectId(String customerId, String assetNumber,
            String assetGrpName) throws RMDDAOException;
    /**
     * @Author:Mohamed
     * @param:vehicleObjectId
     * @return:Date
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the last EOA fault header of the asset
     */
    public Future<Date> getLstEOAFaultHeader(String vehicleObjectId) throws RMDDAOException;
    
    /**
     * @Author:Mohamed
     * @param:vehicleObjectId
     * @return:Date
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the last PP/ATS Msg header of the asset
     */
    public Future<Date> getLstPPATSMsgHeader(String vehicleObjectId) throws RMDDAOException;
    
    /**
     * @Author:Mohamed
     * @param:vehicleObjectId
     * @return:Date
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the last  fault cell date of the asset
     */
    public Future<Date> getLstFaultDateCell(String vehicleObjectId) throws RMDDAOException;
    
    /**
     * @Author:Mohamed
     * @param:vehicleObjectId
     * @return:Date
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the last ESTP download header of the asset
     */
    public Future<Date> getLstESTPDownloadHeader(String vehicleObjectId) throws RMDDAOException;
    
    /**
     * @Author:Mohamed
     * @param:String customerId,String assetGrpName,String assetNumber
     * List<AssetLocatorVO>
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the asset number , latitude and longitude of the asset
     */
    public Future<AssetLocatorVO> getLatAndLongitude(String customerId,String assetGrpName,String assetNumber) throws RMDDAOException;
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the road numbers based on  Customer Name and Road
     *               Number Headers and filter options
     */
    public List<AssetServiceVO> getRoadNumbersWithFilter(String customer,
            String rnhId, String rnSearchString, String rnFilter);
    
    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching Customer Id based on
     *               assetNumber and assetGrpName.
     * 
     */
    public String getCustomerId(String assetNumber, String assetGrpName)
            throws RMDDAOException;
    
    List<AssetHeaderServiceVO> getAssetsData(AssetServiceVO objAssetServiceVO) throws RMDDAOException;
    public List<AssetNumberVO> getModelByFilter(AssetHeaderServiceVO assetHeaderServiceVO) throws RMDDAOException;
	/**
	 * @Author:
	 * @param :String vehicleObjId
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the Next Scheduled run it
	 *               takes vehicleObjId Input Parameter and returns services.
	 * 
	 */

	public Future<String> getAssetNextScheduledRun(String vehicleObjId)
			throws RMDDAOException;

	/**
	 * @Author:
	 * @param :String vehicleObjId
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the Services it takes
	 *               vehicleObjId Input Parameter and returns services.
	 * 
	 */

	public Future<String> getServices(String vehicleObjId)
			throws RMDDAOException;
	
	public List<AssetLocationDetailVO> getAssetDetailList(AssetsLocationFromShopVO objAssetsLocationFromShopVO) throws RMDDAOException;
	public Future<String> getLastToolRun(String vehicleObjId)
			throws RMDDAOException;
	public Future<String> getLstFault(String vehicleObjectId)
            throws RMDDAOException;
	 public String getLocoId(AssetServiceVO objAssetServiceVO) throws RMDDAOException;
}

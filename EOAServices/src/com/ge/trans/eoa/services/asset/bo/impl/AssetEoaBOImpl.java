package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.services.asset.bo.intf.AssetEoaBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.AssetEoaDAOIntf;
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
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.asset.valueobjects.AssetInstalledProductVO;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorBean;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;


public class AssetEoaBOImpl  implements AssetEoaBOIntf{
    public static final RMDLogger LOG = RMDLoggerHelper
            .getLogger(AssetEoaBOImpl.class);
    private AssetEoaDAOIntf objAssetEoaDAOIntf;
    @Autowired
    org.springframework.cache.ehcache.EhCacheCacheManager cacheManager;

    public AssetEoaBOImpl(final AssetEoaDAOIntf objAssetEoaDAOIntf) {
        this.objAssetEoaDAOIntf = objAssetEoaDAOIntf;
    }
    /*
     * This method is used to call getAssets() method in AssetDAOImpl and fetch
     * the List of Assets
     */
    @Override
	public List<AssetServiceVO> getAssets(final AssetServiceVO objAssetServiceVO)
            throws RMDBOException {
        List<AssetServiceVO> arlAsset = null;

        try {
            /*SETTING PRODUCTlST AND CUSTOMERlIST AND PASSING TO DAO*/
                        
            /* Enters if block if product asset config is available */
                arlAsset = objAssetEoaDAOIntf.getAssets(objAssetServiceVO);
        
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
    
    @Override
	public List<AssetInstalledProductVO> getInstalledProduct(
            final AssetServiceVO objAssetServiceVO) {
        List<AssetInstalledProductVO> arlAssetInstalledProd = null;

        try {
            arlAssetInstalledProd = objAssetEoaDAOIntf
                    .getInstalledProduct(objAssetServiceVO);

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

        return arlAssetInstalledProd;
    }
    
    /**
     * @Author : IGate
     * @param :
     * @return :
     * @throws RMDDAOException
     * @Description: Getting the map data filtered based on fleets and models.
     */
    public Map<String, LinkedHashMap<String, AssetLocatorVO>> getFilteredMap(AssetLocatorBean assetLocatorBean,Map<String, LinkedHashMap<String, AssetLocatorVO>> assetLocatorVOMap){
        Map<String,LinkedHashMap<String, AssetLocatorVO>> tmpassetLocatorVOMap=new LinkedHashMap<String, LinkedHashMap<String,AssetLocatorVO>>();
        
        if (null != assetLocatorVOMap) {
            LinkedHashMap<String, AssetLocatorVO> tmpAssetMap=null;
            AssetLocatorVO tmpAssetLocatorVO=null;
            for(Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>> entry:assetLocatorVOMap.entrySet()){
                tmpAssetMap=new LinkedHashMap<String, AssetLocatorVO>();
                if(null != entry.getValue()){
                    for(Map.Entry<String, AssetLocatorVO> entry1:entry.getValue().entrySet()){
                        tmpAssetLocatorVO=entry1.getValue();
                        /*Filtering for Model*/
                        if((null != assetLocatorBean.getModelId() && assetLocatorBean.getModelId().length>0) && (null != assetLocatorBean.getFleetId() && assetLocatorBean.getFleetId().length>0)){
                            List<String> modelIdLst = Arrays.asList(assetLocatorBean.getModelId());
                            List<String> fleetIdLst= Arrays.asList(assetLocatorBean.getFleetId());
                            if(modelIdLst.contains(tmpAssetLocatorVO.getModelObjId()) && fleetIdLst.contains(tmpAssetLocatorVO.getFleetObjId())){
                                tmpAssetMap.put(entry1.getKey(), entry1.getValue());
                            }
                        }
                        /*Filtering for Model and fleets*/
                        /*Filtering for models*/
                        else if(null != assetLocatorBean.getModelId() && assetLocatorBean.getModelId().length>0){
                            List<String> modelIdLst = Arrays.asList(assetLocatorBean.getModelId());
                            if(modelIdLst.contains(tmpAssetLocatorVO.getModelObjId())){
                                tmpAssetMap.put(entry1.getKey(), entry1.getValue());
                            }
                        }
                        /*Filtering for models*/
                        /*Filtering for fleets*/
                        else if(null != assetLocatorBean.getFleetId() && assetLocatorBean.getFleetId().length>0){
                            List<String> fleetIdLst = Arrays.asList(assetLocatorBean.getFleetId());
                            if(fleetIdLst.contains(tmpAssetLocatorVO.getFleetObjId())){
                                tmpAssetMap.put(entry1.getKey(), entry1.getValue());
                            }
                        }
                        /*Filtering for fleets*/
                    }
                    
                    if(null != tmpAssetMap && tmpAssetMap.size() >0){
                        tmpassetLocatorVOMap.put(entry.getKey(), tmpAssetMap);
                    }else if((null != assetLocatorBean.getModelId() && assetLocatorBean.getModelId().length>0) || (null != assetLocatorBean.getFleetId() && assetLocatorBean.getFleetId().length>0)){
                        tmpassetLocatorVOMap.put(entry.getKey(), tmpAssetMap);
                    }else{
                        tmpassetLocatorVOMap.put(entry.getKey(), entry.getValue());
                    }
                        
                    }
                    
                }
            }
        return tmpassetLocatorVOMap;
    }

    /**
     * @Author : iGate 
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer every 1 hour and returns the
     *               Latitude,Longitude and max occurrence time of the
     *               asset(AssetLocatorVO)
     */
    @Override
	public List<AssetLocatorVO> getAssetLocation(
            final AssetLocatorBean assetLocatorBean) throws RMDBOException {

        List<AssetLocatorVO> assetLocatorVoLst = new ArrayList<AssetLocatorVO>();
        Map<String, LinkedHashMap<String, AssetLocatorVO>> tmpassetLocatorVOMap = null;
        tmpassetLocatorVOMap = objAssetEoaDAOIntf.getAssetLocationForCustomers(assetLocatorBean);
        Map<String, LinkedHashMap<String, AssetLocatorVO>> assetLocatorVOMap = null;
        assetLocatorVOMap = getFilteredMap(assetLocatorBean,
                tmpassetLocatorVOMap);
        /* Added for Product Configuration Part */
        if (null != assetLocatorBean.getProducts()
                && !RMDCommonUtility.checkNull(assetLocatorBean.getProducts())) {
            assetLocatorVoLst = getLocDataforProduct(assetLocatorBean,
                    assetLocatorVOMap);
        }
        /* Added for Product Configuration Part */
        else {

            if (null != assetLocatorBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(assetLocatorBean
                            .getCustomerId())) {
                if (assetLocatorVOMap.containsKey(assetLocatorBean
                        .getCustomerId())) {
                    /*
                     * Getting assetLocatorVO for individual asset/group
                     * name/customer
                     */
                    if ((null != assetLocatorBean.getAssetNumber() && !RMDCommonConstants.EMPTY_STRING
                            .equals(assetLocatorBean.getAssetNumber()))
                            && (null != assetLocatorBean.getAssetGrpName() && !RMDCommonConstants.EMPTY_STRING
                                    .equals(assetLocatorBean.getAssetGrpName()))) {
                        String key = assetLocatorBean.getAssetNumber()
                                + RMDCommonConstants.HYPHEN
                                + assetLocatorBean.getCustomerId()
                                + RMDCommonConstants.HYPHEN
                                + assetLocatorBean.getAssetGrpName();
                        if (assetLocatorVOMap.get(
                                assetLocatorBean.getCustomerId()).containsKey(
                                key)) {
                            AssetLocatorVO tmpAssetLocatorVO = assetLocatorVOMap
                                    .get(assetLocatorBean.getCustomerId()).get(
                                            key);
                            assetLocatorVoLst.add(tmpAssetLocatorVO);
                        }

                    } else if (null != assetLocatorBean.getAssetNumber()
                            && !RMDCommonConstants.EMPTY_STRING
                                    .equals(assetLocatorBean.getAssetNumber())) {
                        /*
                         * Checking for individual asset
                         */
                        getLocationVOForCustomerAsset(assetLocatorVoLst,
                                assetLocatorBean.getAssetNumber(),
                                assetLocatorVOMap,
                                assetLocatorBean.getCustomerId());
                    } else if (null != assetLocatorBean.getAssetGrpName()
                            && !RMDCommonConstants.EMPTY_STRING
                                    .equals(assetLocatorBean.getAssetGrpName())) {
                        /*
                         * Checking for groupname
                         */
                        getLocationVOForCustomerGroup(assetLocatorVoLst,
                                assetLocatorBean.getAssetGrpName(),
                                assetLocatorVOMap,
                                assetLocatorBean.getCustomerId());
                    } else { /* Getting assetLocatorVO for single customer */
                        LinkedHashMap<String, AssetLocatorVO> tmpAssetLocatorVOMap = assetLocatorVOMap
                                .get(assetLocatorBean.getCustomerId());
                        assetLocatorVoLst = new ArrayList<AssetLocatorVO>();
                        Collection<AssetLocatorVO> collObject = tmpAssetLocatorVOMap
                                .values();
                        // adding null checks
                        if (null != collObject) {
                            Iterator<AssetLocatorVO> itr = collObject
                                    .iterator();

                            // iterate through HashMap values iterator to add
                            // the values in list
                            while (itr.hasNext()) {
                                assetLocatorVoLst.add(itr.next());
                            }
                        }
                    }

                }
            } else if (null != assetLocatorBean.getAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(assetLocatorBean
                            .getAssetGrpName())) {/* Checking for groupname */
                if ((null != assetLocatorBean.getAssetNumber() && !RMDCommonConstants.EMPTY_STRING
                        .equals(assetLocatorBean.getAssetNumber()))
                        && (null != assetLocatorBean.getAssetGrpName() && !RMDCommonConstants.EMPTY_STRING
                                .equals(assetLocatorBean.getAssetGrpName()))) {
                    /*
                     * Checking for assetNumber and groupname
                     */
                    getLocationVOForAssetGroup(assetLocatorVoLst,
                            assetLocatorBean.getAssetGrpName(),
                            assetLocatorVOMap,
                            assetLocatorBean.getAssetNumber());
                } else {/* Checking for groupname */
                    getLocationVOForCustomerGroup(assetLocatorVoLst,
                            assetLocatorBean.getAssetGrpName(),
                            assetLocatorVOMap, assetLocatorBean.getCustomerId());
                }
            } else if (null != assetLocatorBean.getAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equals(assetLocatorBean
                            .getAssetNumber())) {/* Checking for assetNumber */
                getLocationVOForCustomerAsset(assetLocatorVoLst,
                        assetLocatorBean.getAssetNumber(), assetLocatorVOMap,
                        assetLocatorBean.getCustomerId());
            } else { /* Getting assetLocatorVO for all customers */
                if (null != assetLocatorVOMap) {
                    Iterator<Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>>> it = assetLocatorVOMap
                            .entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>> pairs = it
                                .next();
                        LinkedHashMap<String, AssetLocatorVO> assetLocatorMap = pairs
                                .getValue();
                        Collection<AssetLocatorVO> collObject = assetLocatorMap
                                .values();
                        Iterator<AssetLocatorVO> itr = collObject.iterator();
                        while (itr.hasNext()) {
                            assetLocatorVoLst.add(itr.next());
                        }
                    }
                }
            }
        }
        return assetLocatorVoLst;
    }
    public List<AssetLocatorVO> getLocDataforProduct(AssetLocatorBean assetLocatorBean,Map<String, LinkedHashMap<String, AssetLocatorVO>> assetLocatorVOMap){

        List<AssetLocatorVO> assetLocatorVoLst = new ArrayList<AssetLocatorVO>();
        if (assetLocatorBean.getProducts().size() > 0) {
            LinkedHashMap<String,List<String>> custAssetMap=null;
            /*Setting the values in prdList and calling the dao Method for getting asset Nos*/
            /*SETTING PRODUCTlST AND CUSTOMERlIST AND PASSING TO DAO*/
            
            if(!assetLocatorBean.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)){
                custAssetMap=objAssetEoaDAOIntf.getAssetNoForProducts(assetLocatorBean.getProducts(),assetLocatorBean.getCustomerId());
            }
            
            if(custAssetMap == null || RMDCommonConstants.EMPTY_STRING.equals(custAssetMap)){
                
                 /* Getting assetLocatorVO for single customer */
                LinkedHashMap<String, AssetLocatorVO> tmpLocatorVOMap=null;
                assetLocatorVoLst = new ArrayList<AssetLocatorVO>();
                for(Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>> entry:assetLocatorVOMap.entrySet()){
                
                    if(null !=assetLocatorBean.getCustomerId() && !assetLocatorBean.getCustomerId().isEmpty()){
                        if(entry.getKey().equals(assetLocatorBean.getCustomerId())){
                            tmpLocatorVOMap = entry.getValue();
                            Collection<AssetLocatorVO> collObject = tmpLocatorVOMap
                                    .values();
                            // adding null checks
                            if (null != collObject) {
                                Iterator<AssetLocatorVO> itr = collObject
                                        .iterator();

                                // iterate through HashMap values iterator to add the values in list
                                while (itr.hasNext()) {
                                    assetLocatorVoLst.add(itr.next());
                                }
                            }
                            break;
                        }
                        
                    }
                    else{
                    tmpLocatorVOMap = entry.getValue();
                    Collection<AssetLocatorVO> collObject = tmpLocatorVOMap
                            .values();
                    // adding null checks
                    if (null != collObject) {
                        Iterator<AssetLocatorVO> itr = collObject
                                .iterator();

                        // iterate through HashMap values iterator to add the values in list
                        while (itr.hasNext()) {
                            assetLocatorVoLst.add(itr.next());
                        }
                    }
                }
                }
            }else{
            /*SETTING PRODUCTlST AND CUSTOMERlIST AND PASSING TO DAO*/
            
            /*Setting the values in prdList and calling the dao Method for getting asset Nos*/
            /* Getting the customer and asset List */
            List<String> customerIdLst = new ArrayList<String>();
            List<List<String>> assetNoMultiLst = new ArrayList<List<String>>();
            List<String> assetNoLst = null;
            if (null != custAssetMap
                    && !RMDCommonUtility.checkNull(custAssetMap)) {

                for (Map.Entry<String, List<String>> entry : custAssetMap.entrySet()) {
                    assetNoLst = new ArrayList<String>();
                    assetNoLst = entry.getValue();
                    if (null == assetLocatorBean.getCustomerId()
                            || RMDCommonConstants.EMPTY_STRING
                                    .equals(assetLocatorBean
                                            .getCustomerId())) {
                        customerIdLst.add(entry.getKey());
                    }
                    assetNoMultiLst.add(assetNoLst);
                }

                assetNoLst = RMDCommonUtility
                        .getAssetNoList(assetNoMultiLst);
            }
            /* Getting the customer and asset List */

            /* For Individual Customers */
            if (null != assetLocatorBean.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equals(assetLocatorBean.getCustomerId())) {

                if (assetLocatorVOMap.containsKey(assetLocatorBean
                        .getCustomerId())) {
                    /*
                     * Getting assetLocatorVO for individual customer
                     */
                    if (null != assetNoLst && assetNoLst.size() > 0) {
                        for (int i = 0; i < assetNoLst.size(); i++) {
                            getLocationVOForCustomerAsset(
                                    assetLocatorVoLst, assetNoLst.get(i),
                                    assetLocatorVOMap,
                                    assetLocatorBean.getCustomerId());
                        }
                    } else { /* Getting assetLocatorVO for single customer */
                        LinkedHashMap<String, AssetLocatorVO> tmpAssetLocatorVOMap = assetLocatorVOMap
                                .get(assetLocatorBean.getCustomerId());
                        assetLocatorVoLst = new ArrayList<AssetLocatorVO>();
                        Collection<AssetLocatorVO> collObject = tmpAssetLocatorVOMap
                                .values();
                        // adding null checks
                        if (null != collObject) {
                            Iterator<AssetLocatorVO> itr = collObject
                                    .iterator();

                            // iterate through HashMap values iterator to add the values in list
                            while (itr.hasNext()) {
                                assetLocatorVoLst.add(itr.next());
                            }
                        }
                    }

                }

            }else{
                for(int index=0;index<customerIdLst.size();index++){
                if (assetLocatorVOMap.containsKey(customerIdLst.get(index))) {
                    /*
                     * Getting assetLocatorVO for individual customer
                     */
                    if (null != assetNoLst && assetNoLst.size() > 0) {
                        for (int i = 0; i < assetNoLst.size(); i++) {
                            getLocationVOForCustomerAsset(
                                    assetLocatorVoLst, assetNoLst.get(i),
                                    assetLocatorVOMap,
                                    assetLocatorBean.getCustomerId());
                        }
                    } else { /* Getting assetLocatorVO for single customer */
                        LinkedHashMap<String, AssetLocatorVO> tmpAssetLocatorVOMap = assetLocatorVOMap
                                .get(assetLocatorBean.getCustomerId());
                        assetLocatorVoLst = new ArrayList<AssetLocatorVO>();
                        Collection<AssetLocatorVO> collObject = tmpAssetLocatorVOMap
                                .values();
                        // adding null checks
                        if (null != collObject) {
                            Iterator<AssetLocatorVO> itr = collObject
                                    .iterator();

                            // iterate through HashMap values iterator to add the values in list
                            while (itr.hasNext()) {
                                assetLocatorVoLst.add(itr.next());
                            }
                        }
                    }

                }
            }
            }
            /* For Individual Customers */
            }
        }
        
        return assetLocatorVoLst;
    
    }
    /**
     * @Author : iGate
     * @param :
     * @return :
     * @throws RMDDAOException
     * @Description: Getting the data for asset and asset,customer.
     */
    private void getLocationVOForCustomerAsset(
            List<AssetLocatorVO> assetLocatorVoLst,
            String assetNumber,
            Map<String, LinkedHashMap<String, AssetLocatorVO>> assetLocatorVOMap,
            String customerId) {
        if (null != assetLocatorVOMap) {
            Iterator<Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>>> it = assetLocatorVOMap
                    .entrySet().iterator();
            AssetLocatorVO tmpAssetLocatorVo = null;
            String key = null;
            String[] keyArr = null;
            while (it.hasNext()) {
                Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>> pairs = it
                        .next();
                LinkedHashMap<String, AssetLocatorVO> assetLocatorMap = pairs
                        .getValue();

                Iterator<Entry<String, AssetLocatorVO>> iter = assetLocatorMap
                        .entrySet().iterator();
                String delimiter = RMDCommonConstants.HYPHEN;
                while (iter.hasNext()) {
                    Map.Entry<String, AssetLocatorVO> innerMap = iter.next();
                    tmpAssetLocatorVo = (AssetLocatorVO) innerMap.getValue();
                    key = (String) innerMap.getKey();
                    keyArr = key.split(delimiter, 3);

                    if (null != customerId
                            && !RMDCommonConstants.EMPTY_STRING
                                    .equals(customerId)) {
                        if (assetNumber.equalsIgnoreCase(keyArr[0])
                                && customerId.equalsIgnoreCase(keyArr[1])) {
                            assetLocatorVoLst.add(tmpAssetLocatorVo);
                            break;
                        }
                    } else {
                        if (assetNumber.equalsIgnoreCase(keyArr[0])) {
                            assetLocatorVoLst.add(tmpAssetLocatorVo);

                        }
                    }

                }
            }
        }
    }

    /**
     * @Author : iGate
     * @param :
     * @return :
     * @throws RMDDAOException
     * @Description: Getting the data for a group and group,customer.
     */
    private void getLocationVOForCustomerGroup(
            List<AssetLocatorVO> assetLocatorVoLst,
            String assetGrpName,
            Map<String, LinkedHashMap<String, AssetLocatorVO>> assetLocatorVOMap,
            String customerId) {
        if (null != assetLocatorVOMap) {
            Iterator<Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>>> it = assetLocatorVOMap
                    .entrySet().iterator();
            AssetLocatorVO tmpAssetLocatorVo = null;
            String key = null;
            String[] keyArr = null;
            while (it.hasNext()) {
                Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>> pairs = it
                        .next();
                LinkedHashMap<String, AssetLocatorVO> assetLocatorMap = pairs
                        .getValue();

                Iterator<Entry<String, AssetLocatorVO>> iter = assetLocatorMap
                        .entrySet().iterator();
                String delimiter = RMDCommonConstants.HYPHEN;
                while (iter.hasNext()) {
                    Map.Entry<String, AssetLocatorVO> innerMap = iter.next();
                    tmpAssetLocatorVo = (AssetLocatorVO) innerMap.getValue();
                    key = (String) innerMap.getKey();
                    keyArr = key.split(delimiter, 3);
                    if (null != customerId
                            && !RMDCommonConstants.EMPTY_STRING
                                    .equals(customerId)) {
                        if (assetGrpName.equalsIgnoreCase(keyArr[2])
                                && customerId.equalsIgnoreCase(keyArr[1])) {
                            assetLocatorVoLst.add(tmpAssetLocatorVo);

                        }
                    } else {
                        if (assetGrpName.equalsIgnoreCase(keyArr[2])) {
                            assetLocatorVoLst.add(tmpAssetLocatorVo);

                        }
                    }
                }
            }
        }
    }

    /**
     * @Author : IGate
     * @param :
     * @return :
     * @throws RMDDAOException
     * @Description: Getting the data for a asset and group.
     */
    private void getLocationVOForAssetGroup(
            List<AssetLocatorVO> assetLocatorVoLst,
            String assetGrpName,
            Map<String, LinkedHashMap<String, AssetLocatorVO>> assetLocatorVOMap,
            String assetNumber) {
        if (null != assetLocatorVOMap) {
            Iterator<Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>>> it = assetLocatorVOMap
                    .entrySet().iterator();
            AssetLocatorVO tmpAssetLocatorVo = null;
            String key = null;
            String[] keyArr = null;
            while (it.hasNext()) {
                Map.Entry<String, LinkedHashMap<String, AssetLocatorVO>> pairs = it
                        .next();
                LinkedHashMap<String, AssetLocatorVO> assetLocatorMap = pairs
                        .getValue();

                Iterator<Entry<String, AssetLocatorVO>> iter = assetLocatorMap
                        .entrySet().iterator();
                String delimiter = RMDCommonConstants.HYPHEN;
                while (iter.hasNext()) {
                    Map.Entry<String, AssetLocatorVO> innerMap = iter.next();
                    tmpAssetLocatorVo = (AssetLocatorVO) innerMap.getValue();
                    key = (String) innerMap.getKey();
                    keyArr = key.split(delimiter, 3);

                    if (assetGrpName.equalsIgnoreCase(keyArr[2])
                            && assetNumber.equalsIgnoreCase(keyArr[0])) {
                        assetLocatorVoLst.add(tmpAssetLocatorVo);

                    }

                }
            }
        }
    }
    
    /**
     * @Author : iGate 
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Last Download status
     *               and other fields for an asset for diesel doctor role
     */
    @Override
	public LastDownloadStatusResponseEoaVO getLastDowloadStatus(
            final LastDownloadStatusEoaVO lstDownloadStatus)
                    throws RMDBOException  {

        LastDownloadStatusResponseEoaVO lstDownloadStatusResp = null;
        try {
            lstDownloadStatusResp = objAssetEoaDAOIntf
                    .getLastDowloadStatus(lstDownloadStatus);
        } catch (RMDDAOException ex) {
                throw ex;
        } catch (Exception exc) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.ERROR_LAST_DOWNLOAD_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            lstDownloadStatus.getStrLanguage()), exc,
                    RMDCommonConstants.MINOR_ERROR);
        }
        return lstDownloadStatusResp;
    }
    
    /**
     * @Author : GE
     * @param : 
     * @return : LastFaultStatusResponseVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the Last Fault status and other fields for an asset for any role
     */
    @Override
	public LastFaultStatusResponseEoaVO getLastFaultStatus(final LastFaultStatusEoaVO lstFaultStatus) throws RMDDAOException {
        
        LastFaultStatusResponseEoaVO lstFaultStatusResp = null;
        try {
            lstFaultStatusResp = objAssetEoaDAOIntf.getLastFaultStatus(lstFaultStatus);
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getLastFaultStatus()"+ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getLastFaultStatus()"+exc);
        }
        return lstFaultStatusResp;  
    }
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ge.trans.rmd.cm.services.tools.rulemgmt.bo.intf.RuleBOIntf#getFleetList
     * (java.lang.String[], java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String) Method to retrieve Fleet List based
     * on Customer, Configuration, Unit No and Model
     */
    @Override
	public List getFleetList(final String strCustomer,final  String strModel,
            final String strConfig[],final  String strUnitNumber, final String strLanguage) {
        List arlFleetList = null;
        try {
            arlFleetList = objAssetEoaDAOIntf.getFleetList(strCustomer, strModel,
                    strConfig, strUnitNumber, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFleetList;
    }

    @Override
    public List <ElementVO> getModels( String strLanguage ) throws RMDBOException {
        // TODO Auto-generated method stub
        
        List <ElementVO>  modelList  = null;
        try{
            modelList = objAssetEoaDAOIntf.getModels( strLanguage );
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getModels()"+ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getModels()"+exc);
        }

        return modelList;
    }
    @Override
    public List<ElementVO> getModelList(String strCustomer, String strFleet,
            String[] strConfig, String strUnitNumber, String strFamily,
            String strLanguage) throws RMDBOException {
        List<ElementVO> arlModelList = null;
        try {
            arlModelList = objAssetEoaDAOIntf.getModelList(strCustomer, strFleet,
                    strConfig, strUnitNumber,strFamily, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlModelList;
    }
    
    
    @Override
	public  List<ElementVO> getModelList(final String strfamily,final  String strLanguage){
        List<ElementVO>  arlModelList = null;
        try{
            arlModelList = objAssetEoaDAOIntf.getModelList( strfamily,strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlModelList;
    }
    /* (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.LocationBOIntf
     * #findLocation(com.ge.trans.rmd.services.cases.service.valueobjects.FindLocationServiceVO)
     *//* This Method is used for call the findLocation method in LocationDAOImpl*/
    @Override
	public List findLocation(FindLocationEoaServiceVO objFindLocationServiceVO)
            throws RMDBOException {
        List arlLocation = null;
        try {
            arlLocation = objAssetEoaDAOIntf
                    .findLocation(objFindLocationServiceVO);
        }
        catch (RMDDAOException e) {
            throw e;
        }
        return arlLocation;
    }
    
    /**
     * @Author : iGatte Patni
     * @param : 
     * @return : List<String>
     * @throws RMDBOException
     * @Description: Calls the DAO layer and returns the available services for an asset
     */
    @Override
	public List<String> getAssetServices(AssetServiceVO objAssetServiceVO) throws RMDBOException{
        List<String> serviceLst = null;
        try {
            serviceLst = objAssetEoaDAOIntf.getAssetServices(objAssetServiceVO);
        } catch (RMDDAOException ex) {
                throw ex;
        } catch (Exception exc) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_SERVICES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), exc,
                    RMDCommonConstants.MINOR_ERROR);
        }
        return serviceLst;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see com.ge.trans.rmd.services.cases.bo.intf.NotesBOIntf#saveNotes(com.ge.trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO)
     *      This method is used to call the saveNotes method in AddNotesDAOImpl
     */
    @Override
	public void saveNotes(AddNotesEoaServiceVO notesServiceVO)
            throws RMDBOException {
        try {
            
            objAssetEoaDAOIntf.saveNotes(notesServiceVO);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in  saveNotes method",
                e);
            throw e;
        }
    }
    @Override
    public List<GetSysLookupVO> getMapLastRefreshTime(GetSysLookupVO sysLookupVO)
            throws RMDBOException {
        try {
            
            String lookupString = sysLookupVO.getListName();
            return objAssetEoaDAOIntf.getMapLastRefreshTime(lookupString);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in AssetEoaBOImpl getMapLastRefreshTime method",
                e);
            throw e;
        }
    }
    
    @Override
	public List<FaultCodeDetailsServiceVO> getFaultCode(
            String controllerCfg,String faultCode,String language) throws RMDBOException{
        List<FaultCodeDetailsServiceVO> arlFaultCode=null;
        try{
            arlFaultCode=objAssetEoaDAOIntf.getFaultCode(controllerCfg, faultCode, language);
        }catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getFaultCode()"+ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getFaultCode()"+exc);
        }
        return arlFaultCode;
        
    }
    @Override
    public List<LifeStatisticsEoaVO> getLifeStatisticsData(
            AssetServiceVO objAssetServiceVO) throws RMDBOException {
        List<LifeStatisticsEoaVO> arlFaultCode=null;
        try{
            arlFaultCode=objAssetEoaDAOIntf.getLifeStatisticsData(objAssetServiceVO);
        }catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getFaultCode()"+ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getFaultCode()"+exc);
        }
        return arlFaultCode;
        
    }
    
    @Override
	public List<AssetServiceVO> getAssetGroups(
            final AssetServiceVO objAssetServiceVO) throws Exception {
        List<AssetServiceVO> arlAsset = null;
        try {
            arlAsset = objAssetEoaDAOIntf.getAssetGroups(objAssetServiceVO);
        } 
        catch (Exception e) {
             throw e;
        }
        return arlAsset;        
    }
    
    /**
     * @Author : GE
     * @param : 
     * @return : RoleAssetsVO
     * @throws RMDDAOException
     * @Description: Calls the DAO layer and returns the List of Assets
     */
    @Override
	public RoleAssetsVO getAssetsForRole(
            final RoleAssetsVO objRoleAssetsVO) throws RMDDAOException {        
        RoleAssetsVO objRoleAssetsVOLst = null;
        try {
            objRoleAssetsVOLst = objAssetEoaDAOIntf.getProductsForRole(objRoleAssetsVO);
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getLastFaultStatus()"+ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in AssetBOImpl getLastFaultStatus()"+exc);
        }
        return objRoleAssetsVOLst;  
    }
    
    @Override
	public List<FleetVO> getAllFleets() throws RMDDAOException {        
        List<FleetVO> objFleetVOLst = null;
        try {
            objFleetVOLst = objAssetEoaDAOIntf.getAllFleets();
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in getAllFleets getLastFaultStatus()"+ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in getAllFleets getLastFaultStatus()"+exc);
        }
        return objFleetVOLst;   
    }
    
    @Override
	public List<RegionVO> getAllRegions() throws RMDDAOException {      
        List<RegionVO> objRegionVOLst = null;
        try {
            objRegionVOLst = objAssetEoaDAOIntf.getAllRegions();
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in getAllRegions getLastFaultStatus()"+ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in getAllRegions getLastFaultStatus()"+exc);
        }
        return objRegionVOLst;  
    }
    
    @Override
	public List<RegionVO> getAllSubDivisions(String region) throws RMDDAOException {        
        List<RegionVO> objRegionVOLst = null;
        try {
            objRegionVOLst = objAssetEoaDAOIntf.getAllSubDivisions(region);
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in getAllRegions getLastFaultStatus()"+ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in getAllRegions getLastFaultStatus()"+exc);
        }
        return objRegionVOLst;  
    }
    /**
     * @Author:
     * @param:String vehicleObjId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used check whether PP Asset History Button
     *               Has to Disable or Enable.
     */
    @Override
    public String displayPPAssetHistoryBtn(String vehicleObjId) throws RMDBOException {
        String result = null;
        try {
            result = objAssetEoaDAOIntf.displayPPAssetHistoryBtn(vehicleObjId);
        } catch (RMDDAOException e) {
        	 LOG.error(e);
            throw new RMDBOException(e.getErrorDetail());
        }
        return result;
    }

    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDBOException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    @Override
    public List<AssetServiceVO> getCustNameRNH() throws RMDBOException {
        List<AssetServiceVO> eoaAssetsLst = null;
        try {
            eoaAssetsLst = objAssetEoaDAOIntf.getCustNameRNH();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {},
                    ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return eoaAssetsLst;
    }

    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDBOException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    @Override
    public List<AssetServiceVO> getRoadNumbers(AssetServiceVO objAssetServiceVO)
            throws RMDBOException {
        List<AssetServiceVO> eoaAssetsLst = null;
        try {
            eoaAssetsLst = objAssetEoaDAOIntf.getRoadNumbers(objAssetServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {},
                    ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return eoaAssetsLst;
    }
    
    /**
     * @Author  :Mohamed
     * @return  :List<AssetLocatorVO>
     * @param   :AssetOverviewBean assetBean
     * @throws  :RMDWebException
     * @Description:This method fetches the common section of the asset case
     *               by invoking web services getAssetCaseCommSection() method.
     */
    @Override
    public List<AssetLocatorVO> getAssetCaseCommSection(final AssetLocatorBean assetLocatorBean) throws RMDBOException{
        List<AssetLocatorVO> assetLocatorVoLst = new ArrayList<AssetLocatorVO>();
        AssetLocatorVO assetLocatorVO = null;
        Future<AssetLocatorVO> locatorVO = null;
        String vehicleObjectId= null;
        Future<Date> lstEOAFaultHeader= null;
        Future<Date> lstPPATSMsgHeader= null;
        Future<Date> lstESTPDownloadHeader= null;
        Future<Date> lstFaultDateCell= null;
        Future<String> futureServices=null;
        Future<String> futureNextScheduledRun=null;
        Future<String> futureLastToolRun=null;
        Future<String> futureLastFlt=null;
        AssetLocatorVO futureassetLocatorVO = null;
        try {
            vehicleObjectId = objAssetEoaDAOIntf.getVehicleObjectId(assetLocatorBean.getCustomerId(),assetLocatorBean.getAssetNumber(),assetLocatorBean.getAssetGrpName());
            if (null != vehicleObjectId
                    && !RMDCommonConstants.EMPTY_STRING .equalsIgnoreCase(vehicleObjectId)) {
                lstEOAFaultHeader = objAssetEoaDAOIntf.getLstEOAFaultHeader(vehicleObjectId);
                lstPPATSMsgHeader = objAssetEoaDAOIntf.getLstPPATSMsgHeader(vehicleObjectId);
                lstESTPDownloadHeader = objAssetEoaDAOIntf.getLstESTPDownloadHeader(vehicleObjectId);
                lstFaultDateCell = objAssetEoaDAOIntf.getLstFaultDateCell(vehicleObjectId);
                locatorVO = objAssetEoaDAOIntf.getLatAndLongitude(assetLocatorBean.getCustomerId(),assetLocatorBean.getAssetGrpName(),assetLocatorBean.getAssetNumber());
                futureServices=objAssetEoaDAOIntf.getServices(vehicleObjectId);
                futureNextScheduledRun=objAssetEoaDAOIntf.getAssetNextScheduledRun(vehicleObjectId);
                futureLastToolRun=objAssetEoaDAOIntf.getLastToolRun(vehicleObjectId);
               futureLastFlt=objAssetEoaDAOIntf.getLstFault(vehicleObjectId);
            }
            assetLocatorVO = new AssetLocatorVO();
            if(null != locatorVO){
                futureassetLocatorVO=locatorVO.get();
            }
            if(null != lstEOAFaultHeader){
                assetLocatorVO.setLstEOAFaultHeader(lstEOAFaultHeader.get());
            }
            if(null != lstPPATSMsgHeader){
                assetLocatorVO.setLstPPATSMsgHeader(lstPPATSMsgHeader.get());
            }
            if(null != lstESTPDownloadHeader){
                assetLocatorVO.setLstESTPDownloadHeader(lstESTPDownloadHeader.get());
            }
            if(null != lstFaultDateCell){
                assetLocatorVO.setLstFalultDateCell(lstFaultDateCell.get());
            }
            if(null != futureassetLocatorVO )
            {
              assetLocatorVO.setLongitude(futureassetLocatorVO.getLongitude());
              assetLocatorVO.setLatitude(futureassetLocatorVO.getLatitude());
            }
            
            if(null != futureServices )
            {
              assetLocatorVO.setServices(futureServices.get());
            }
            
            if(null != futureNextScheduledRun )
            {
              assetLocatorVO.setNextScheduledRun(futureNextScheduledRun.get());
            }
            
            if(null != futureLastToolRun )
            {
              assetLocatorVO.setLastToolRun(futureLastToolRun.get());
            }
            if(null != futureLastFlt )
            {
              assetLocatorVO.setLastRecord(futureLastFlt.get());
            }
            assetLocatorVoLst.add(assetLocatorVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {},
                    ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
		} finally {
			lstEOAFaultHeader = null;
			lstPPATSMsgHeader = null;
			lstESTPDownloadHeader = null;
			lstFaultDateCell = null;
			futureServices = null;
			futureNextScheduledRun = null;
			futureassetLocatorVO = null;
			futureLastToolRun=null;
		}
		return assetLocatorVoLst;
	}
    
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDBOException
     * @Description: This method is used for fetching the road numbers based on filer options
     *               
     */
    @Override
    public List<AssetServiceVO> getRoadNumbersWithFilter(String customer, String rnhId,
            String rnSearchString, String rnFilter)
            throws RMDBOException {
        List<AssetServiceVO> eoaAssetsLst = null;
        try {
            eoaAssetsLst = objAssetEoaDAOIntf.getRoadNumbersWithFilter(customer, rnhId, rnSearchString, rnFilter);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {},
                    ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
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
    public String getCustomerId(String assetNumber, String assetGrpName)
            throws RMDBOException {
        String customerId = null;
        try {
            customerId = objAssetEoaDAOIntf.getCustomerId(assetNumber,
                    assetGrpName);
        } catch (RMDDAOException e) {
        	 LOG.error(e);
            throw new RMDBOException(e.getErrorDetail());
        }
        return customerId;
    }
    @Override
    public List<AssetHeaderServiceVO> getAssetsData(
            AssetServiceVO objAssetServiceVO) throws RMDBOException {
        // TODO Auto-generated method stub
        List<AssetHeaderServiceVO> arlAsset = null;

        try {
            /*SETTING PRODUCTlST AND CUSTOMERlIST AND PASSING TO DAO*/
                        
            /* Enters if block if product asset config is available */
                arlAsset = objAssetEoaDAOIntf.getAssetsData(objAssetServiceVO);
        
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
    @Override
	public List<AssetNumberVO> getModelByFilter(
			AssetHeaderServiceVO assetHeaderServiceVO) throws RMDBOException {
		List<AssetNumberVO> assetList=null;
		try {
			assetList = objAssetEoaDAOIntf.getModelByFilter(assetHeaderServiceVO);
		} catch (RMDDAOException e) {
			LOG.error(e);
			throw new RMDBOException(e.getErrorDetail());
		}
		return assetList;
	}
    
    @Override
    public List<AssetLocationDetailVO> getAssetsLocationDeatil(AssetsLocationFromShopVO objAssetsLocationFromShopVO) throws RMDBOException {
		List<AssetLocationDetailVO> assetDetailList=null;
		try {
			assetDetailList = objAssetEoaDAOIntf.getAssetDetailList(objAssetsLocationFromShopVO);
		} catch (RMDDAOException e) {
			LOG.error(e);
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		 catch (Exception e) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDCommonConstants.BOEXCEPTION);
	            throw new RMDBOException(errorCode, new String[] {},
	                    e.getMessage(), e, RMDCommonConstants.MINOR_ERROR);
	        }
		return assetDetailList;
	}
    @Override
    public String getLocoId(AssetServiceVO objAssetServiceVO) throws RMDBOException {
		String locoId=null;
		try {
			locoId = objAssetEoaDAOIntf.getLocoId(objAssetServiceVO);
		} catch (RMDDAOException e) {
			LOG.error(e);
			throw new RMDBOException(e.getErrorDetail(), e);
		}
		 catch (Exception e) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDCommonConstants.BOEXCEPTION);
	            throw new RMDBOException(errorCode, new String[] {},
	                    e.getMessage(), e, RMDCommonConstants.MINOR_ERROR);
	        }
		return locoId;
	}
}

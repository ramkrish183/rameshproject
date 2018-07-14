package com.ge.trans.pp.services.manageGeofence.service.impl;

import java.util.List;

import com.ge.trans.pp.services.manageGeofence.bo.intf.ConfigGeofenceBOIntf;
import com.ge.trans.pp.services.manageGeofence.service.intf.ConfigGeofenceServiceIntf;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.AddNotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.CreateRegionVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.MilePostDetailsVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.NotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ProximityDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class ConfigGeofenceServiceImpl implements ConfigGeofenceServiceIntf {

    private ConfigGeofenceBOIntf objConfigGeofenceBOIntf;

    /**
     * @param objConfigGeofenceBOIntf
     */

    public ConfigGeofenceServiceImpl(ConfigGeofenceBOIntf objConfigGeofenceBOIntf) {
        this.objConfigGeofenceBOIntf = objConfigGeofenceBOIntf;
    }

    /**
     * @Author :
     * @return :List<NotifyConfigVO>
     * @param :String
     *            customer
     * @throws :RMDServiceException
     * @Description: This method is used for fetching Notification
     *               Configurations.
     */
    @Override
    public List<NotifyConfigVO> getNotifyConfigs(String customer) throws RMDServiceException {
        List<NotifyConfigVO> arlConfigVOs = null;
        try {
            arlConfigVOs = objConfigGeofenceBOIntf.getNotifyConfigs(customer);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlConfigVOs;
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDServiceException
     * @Description: This method is used for adding a Notify Configuration.
     */
    @Override
    public String addNotifyConfig(AddNotifyConfigVO objAddNotifyConfigVO) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigGeofenceBOIntf.addNotifyConfig(objAddNotifyConfigVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :List<NotifyConfigVO>
     *            arlNotifyConfigVOs
     * @throws :RMDServiceException
     * @Description: This method is used for Updating a Notify Configuration.
     */
    @Override
    public String updateNotifyConfig(List<NotifyConfigVO> arlNotifyConfigVOs) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigGeofenceBOIntf.updateNotifyConfig(arlNotifyConfigVOs);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDServiceException
     * @Description: This method is used for fetching notify flags.
     */
    @Override
    public List<String> getNotifyFlags() throws RMDServiceException {
        List<String> arlNotifyFlags = null;
        try {
            arlNotifyFlags = objConfigGeofenceBOIntf.getNotifyFlags();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlNotifyFlags;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDServiceException
     * @Description:This method is used for fetching Threshold values.
     */

    @Override
    public List<String> getThreshold() throws RMDServiceException {
        List<String> arlThresholds = null;
        try {
            arlThresholds = objConfigGeofenceBOIntf.getThreshold();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlThresholds;
    }

    // Murali Changes

    /**
     * @Author :
     * @return :List<ProximityDetailsVO>
     * @param :String
     *            customerID, String proxStatus
     * @throws :RMDServiceException
     * @Description: This method is used to get the proximity details for the
     *               selected customer.
     */
    @Override
    public List<ProximityDetailsVO> getProximityDetails(String customerID, String proxStatus)
            throws RMDServiceException {
        List<ProximityDetailsVO> proxDetailsList = null;
        try {
            proxDetailsList = objConfigGeofenceBOIntf.getProximityDetails(customerID, proxStatus);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return proxDetailsList;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<ProximityDetailsVO>
     * @throws :RMDServiceException
     * @Description: This method is used to update selected proximity details
     */
    @Override
    public String updateProximity(List<ProximityDetailsVO> proxDetailsList) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigGeofenceBOIntf.updateProximity(proxDetailsList);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :ProximityDetailsVO
     * @throws :RMDServiceException
     * @Description: This method adds new proximity..
     */
    @Override
    public String addNewProximity(ProximityDetailsVO objProxDetails) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigGeofenceBOIntf.addNewProximity(objProxDetails);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author:
     * @param :ProximityDetailsVO
     * @throws:RMDServiceException
     * @Description: This method is used to delete the proximities
     */
    @Override
    public void deleteProximity(ProximityDetailsVO objProximityDetailsVO) throws RMDServiceException {
        try {
            objConfigGeofenceBOIntf.deleteProximity(objProximityDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

    }

    /**
     * @Author :
     * @return :List<MilePostDetailsVO>
     * @param :String
     *            customerID, String proxStatus
     * @throws :RMDServiceException
     * @Description: This method is used to get the milepost details for the
     *               selected customer.
     */
    @Override
    public List<MilePostDetailsVO> getMilePostDetails(String customerID, String milePostStatus)
            throws RMDServiceException {
        List<MilePostDetailsVO> milePostDetailsList = null;
        try {
            milePostDetailsList = objConfigGeofenceBOIntf.getMilePostDetails(customerID, milePostStatus);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return milePostDetailsList;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<MilePostDetailsVO>
     * @throws :RMDServiceException
     * @Description: This method is used to update selected milepost details
     */
    @Override
    public String updateMilePost(List<MilePostDetailsVO> mpDetailsList) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigGeofenceBOIntf.updateMilePost(mpDetailsList);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :MilePostDetailsVO
     * @throws :RMDServiceException
     * @Description: This method adds new milepost.
     */
    @Override
    public String addNewMilePost(MilePostDetailsVO objMilePostDetails) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigGeofenceBOIntf.addNewMilePost(objMilePostDetails);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author:
     * @param :MilePostDetailsVO
     * @throws:RMDServiceException
     * @Description: This method is used to delete the milepost
     */
    @Override
    public void deleteMilePost(MilePostDetailsVO objMilePostDetailsVO) throws RMDServiceException {
        try {
            objConfigGeofenceBOIntf.deleteMilePost(objMilePostDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of stateprovince.
     */
    @Override
    public List<String> getStateProvince() throws RMDServiceException {

        List<String> arlStateList = null;
        try {
            arlStateList = objConfigGeofenceBOIntf.getStateProvince();

        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlStateList;
    }

    /**
     * @Author:
     * @param :customerId
     * @return:List<String>
     * @throws:RMDServiceException
     * @Description: This method is used to get proximity parent for the
     *               selected customer
     */
    @Override
    public List<String> getProximityParent(String customerId) throws RMDServiceException {
        List<String> proxParentList = null;
        try {
            proxParentList = objConfigGeofenceBOIntf.getProximityParent(customerId);

        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return proxParentList;
    }

    /**
     * @Author:
     * @param :CreateRegionVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used to add New Region/sub region
     */
    @Override
    public String addRegionSubRegion(CreateRegionVO regionVO) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigGeofenceBOIntf.addRegionSubRegion(regionVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author:
     * @param :customerId
     * @return:List<String>
     * @throws:RMDBOException
     * @Description: This method is used to get region for the selected customer
     */
    @Override
    public List<String> getRegion(String customerId) throws RMDServiceException {
        List<String> regionList = null;
        try {
            regionList = objConfigGeofenceBOIntf.getRegion(customerId);

        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return regionList;
    }

    /**
     * @Author:
     * @param :region
     * @return:List<String>
     * @throws:RMDBOException
     * @Description: This method is used to get sub region for the selected
     *               region
     */
    @Override
    public List<String> getSubRegion(String region) throws RMDServiceException {
        List<String> subRegionList = null;
        try {
            subRegionList = objConfigGeofenceBOIntf.getSubRegion(region);

        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return subRegionList;
    }

}

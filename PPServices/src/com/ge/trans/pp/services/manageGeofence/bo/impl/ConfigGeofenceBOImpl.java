package com.ge.trans.pp.services.manageGeofence.bo.impl;

import java.util.List;

import com.ge.trans.pp.services.manageGeofence.bo.intf.ConfigGeofenceBOIntf;
import com.ge.trans.pp.services.manageGeofence.dao.intf.ConfigGeofenceDAOIntf;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.AddNotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.CreateRegionVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.MilePostDetailsVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.NotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ProximityDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public class ConfigGeofenceBOImpl implements ConfigGeofenceBOIntf {

    private ConfigGeofenceDAOIntf objConfigGeofenceDAOIntf;

    /**
     * @param objConfigGeofenceDAOIntf
     */
    public ConfigGeofenceBOImpl(ConfigGeofenceDAOIntf objConfigGeofenceDAOIntf) {
        this.objConfigGeofenceDAOIntf = objConfigGeofenceDAOIntf;
    }

    /**
     * @Author :
     * @return : List<NotifyConfigVO>
     * @param :String
     *            customer
     * @throws :RMDBOException
     * @Description: This method is used for fetching a Notify Configuration.
     */
    @Override
    public List<NotifyConfigVO> getNotifyConfigs(String customer) throws RMDBOException {
        List<NotifyConfigVO> arlConfigVOs = null;
        try {
            arlConfigVOs = objConfigGeofenceDAOIntf.getNotifyConfigs(customer);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlConfigVOs;
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDBOException
     * @Description: This method is used for adding a Notify Configuration.
     */
    @Override
    public String addNotifyConfig(AddNotifyConfigVO objAddNotifyConfigVO) throws RMDBOException {
        String status = null;
        try {
            status = objConfigGeofenceDAOIntf.addNotifyConfig(objAddNotifyConfigVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :List<NotifyConfigVO>
     *            arlNotifyConfigVOs
     * @throws :RMDBOException
     * @Description: This method is used for Updating a Notify Configuration.
     */
    @Override
    public String updateNotifyConfig(List<NotifyConfigVO> arlNotifyConfigVOs) throws RMDBOException {
        String status = null;
        try {
            status = objConfigGeofenceDAOIntf.updateNotifyConfig(arlNotifyConfigVOs);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDBOException
     * @Description: This method is used for fetching notify flags.
     */
    @Override
    public List<String> getNotifyFlags() throws RMDBOException {
        List<String> arlNotifyFlags = null;
        try {
            arlNotifyFlags = objConfigGeofenceDAOIntf.getNotifyFlags();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlNotifyFlags;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDBOException
     * @Description:This method is used for fetching Threshold values.
     */

    @Override
    public List<String> getThreshold() throws RMDBOException {
        List<String> arlThresholds = null;
        try {
            arlThresholds = objConfigGeofenceDAOIntf.getThreshold();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlThresholds;
    }

    /**
     * @Author :
     * @return :List<ProximityDetailsVO>
     * @param :String
     *            customerID, String proxStatus
     * @throws :RMDDAOException
     * @Description: This method is used to get the proximity details for the
     *               selected customer.
     */
    @Override
    public List<ProximityDetailsVO> getProximityDetails(String customerID, String proxStatus) throws RMDBOException {
        List<ProximityDetailsVO> proximityList = null;
        try {
            if (customerID != null && proxStatus != null) {

                proximityList = objConfigGeofenceDAOIntf.getProximityDetails(customerID, proxStatus);
            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return proximityList;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<ProximityDetailsVO>
     * @throws :RMDBOException
     * @Description: This method is used to update selected proximity details
     */
    @Override
    public String updateProximity(List<ProximityDetailsVO> proxDetailsList) throws RMDBOException {
        String status = null;
        try {
            status = objConfigGeofenceDAOIntf.updateProximity(proxDetailsList);
        } catch (RMDDAOException ex) {
            try {
                throw new RMDBOException(ex.getErrorDetail(), ex);
            } catch (RMDBOException e) {
                throw e;
            }
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :ProximityDetailsVO
     * @throws :RMDBOException
     * @Description: This method adds new proximity..
     */
    @Override
    public String addNewProximity(ProximityDetailsVO objProxDetails) throws RMDBOException {
        String status = null;
        try {
            status = objConfigGeofenceDAOIntf.addNewProximity(objProxDetails);
        } catch (RMDDAOException ex) {
            try {
                throw new RMDBOException(ex.getErrorDetail(), ex);
            } catch (RMDBOException e) {
                throw e;
            }
        }
        return status;
    }

    /**
     * @Author:
     * @param :ProximityDetailsVO
     * @throws:RMDBOException
     * @Description: This method is used to delete the proximities
     */
    @Override
    public void deleteProximity(ProximityDetailsVO objProximityDetailsVO) throws RMDBOException {
        try {
            objConfigGeofenceDAOIntf.deleteProximity(objProximityDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

    }

    /**
     * @Author :
     * @return :List<MilePostDetailsVO>
     * @param :String
     *            customerID, String milePostStatus
     * @throws :RMDBOException
     * @Description: This method is used to get the milepost details for the
     *               selected customer.
     */
    @Override
    public List<MilePostDetailsVO> getMilePostDetails(String customerID, String milePostStatus) throws RMDDAOException {
        List<MilePostDetailsVO> milePostList = null;
        try {
            if (customerID != null && milePostStatus != null) {
                milePostList = objConfigGeofenceDAOIntf.getMilePostDetails(customerID, milePostStatus);
            }
        } catch (RMDDAOException e) {
            throw e;
        }
        return milePostList;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<MilePostDetailsVO>
     * @throws :RMDBOException
     * @Description: This method is used to update selected milepost details
     */
    @Override
    public String updateMilePost(List<MilePostDetailsVO> mpDetailsList) throws RMDBOException {
        String status = null;
        try {
            status = objConfigGeofenceDAOIntf.updateMilePost(mpDetailsList);
        } catch (RMDDAOException ex) {
            try {
                throw new RMDBOException(ex.getErrorDetail(), ex);
            } catch (RMDBOException e) {
                throw e;
            }
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :MilePostDetailsVO
     * @throws :RMDBOException
     * @Description: This method adds new milepost.
     */
    @Override
    public String addNewMilePost(MilePostDetailsVO objMilePostDetails) throws RMDBOException {
        String status = null;
        try {
            status = objConfigGeofenceDAOIntf.addNewMilePost(objMilePostDetails);
        } catch (RMDDAOException ex) {
            try {
                throw new RMDBOException(ex.getErrorDetail(), ex);
            } catch (RMDBOException e) {
                throw e;
            }
        }
        return status;
    }

    /**
     * @Author:
     * @param :MilePostDetailsVO
     * @throws:RMDBOException
     * @Description: This method is used to delete the milepost
     */
    @Override
    public void deleteMilePost(MilePostDetailsVO objMilePostDetailsVO) throws RMDBOException {
        try {
            objConfigGeofenceDAOIntf.deleteMilePost(objMilePostDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDBOException
     * @Description: This Method Fetches the list of stateprovince.
     */
    @Override
    public List<String> getStateProvince() throws RMDBOException {
        List<String> arlStateList = null;
        try {
            arlStateList = objConfigGeofenceDAOIntf.getStateProvince();

        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlStateList;
    }

    /**
     * @Author:
     * @param :customerId
     * @return:List<String>
     * @throws:RMDBOException
     * @Description: This method is used to get proximity parent for the
     *               selected customer
     */
    @Override
    public List<String> getProximityParent(String customerId) throws RMDBOException {
        List<String> proxParentList = null;
        try {
            proxParentList = objConfigGeofenceDAOIntf.getProximityParent(customerId);

        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return proxParentList;
    }

    /**
     * @Author:
     * @param :CreateRegionVO
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used to add New Region/sub region
     */
    @Override
    public String addRegionSubRegion(CreateRegionVO regionVO) throws RMDBOException {
        String status = null;
        try {
            status = objConfigGeofenceDAOIntf.addRegionSubRegion(regionVO);
        } catch (RMDDAOException ex) {
            try {
                throw new RMDBOException(ex.getErrorDetail(), ex);
            } catch (RMDBOException e) {
                throw e;
            }
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
    public List<String> getRegion(String customerId) throws RMDBOException {
        List<String> regionList = null;
        try {
            regionList = objConfigGeofenceDAOIntf.getRegion(customerId);

        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
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
    public List<String> getSubRegion(String region) throws RMDBOException {
        List<String> subRegionList = null;
        try {
            subRegionList = objConfigGeofenceDAOIntf.getSubRegion(region);

        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return subRegionList;
    }

}

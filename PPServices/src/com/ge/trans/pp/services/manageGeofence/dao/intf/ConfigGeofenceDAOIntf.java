package com.ge.trans.pp.services.manageGeofence.dao.intf;

import java.util.List;

import com.ge.trans.pp.services.manageGeofence.service.valueobjects.AddNotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.CreateRegionVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.MilePostDetailsVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.NotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ProximityDetailsVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface ConfigGeofenceDAOIntf {

    /**
     * @Author :
     * @return :List<NotifyConfigVO>
     * @param :
     *            String customer
     * @throws :RMDDAOException
     * @Description: This method fetches Notify Configuration details.
     */
    public List<NotifyConfigVO> getNotifyConfigs(String customer) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDDAOException
     * @Description: This method is used for Adding a Notify Configuration.
     */
    public String addNotifyConfig(AddNotifyConfigVO objAddNotifyConfigVO) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDDAOException
     * @Description: This method is used for Updating a Notify Configuration.
     */
    public String updateNotifyConfig(List<NotifyConfigVO> arlNotifyConfigVOs) throws RMDDAOException;

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDDAOException
     * @Description: This method is used for fetching notify flags.
     */
    public List<String> getNotifyFlags() throws RMDDAOException;

    /**
     * @Author :
     * @return :List<String>
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDDAOException
     * @Description:This method is used for fetching Threshold values.
     */
    public List<String> getThreshold() throws RMDDAOException;

    // Murali Changes

    /**
     * @Author :
     * @return :List<ProximityDetailsVO>
     * @param :String
     *            customerID, String proxStatus
     * @throws :RMDDAOException
     * @Description: This method is used to get the proximity details for the
     *               selected customer.
     */
    public List<ProximityDetailsVO> getProximityDetails(String customerID, String proxStatus) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<ProximityDetailsVO>
     * @throws :RMDDAOException
     * @Description: This method is used to update selected proximity details
     */
    public String updateProximity(List<ProximityDetailsVO> proxDetailsList) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param :ProximityDetailsVO
     * @throws :RMDDAOException
     * @Description: This method adds new proximity..
     */
    public String addNewProximity(ProximityDetailsVO objProxDetails) throws RMDDAOException;

    /**
     * @Author:
     * @param :ProximityDetailsVO
     * @throws:RMDDAOException
     * @Description: This method is used to delete the proximities
     */
    public void deleteProximity(ProximityDetailsVO objProximityDetailsVO) throws RMDDAOException;

    /**
     * @Author :
     * @return :List<MilePostDetailsVO>
     * @param :String
     *            customerID, String milePostStatus
     * @throws :RMDDAOException
     * @Description: This method is used to get the milepost details for the
     *               selected customer.
     */
    public List<MilePostDetailsVO> getMilePostDetails(String customerID, String milePostStatus) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<MilePostDetailsVO>
     * @throws :RMDDAOException
     * @Description: This method is used to update selected milepost details
     */
    public String updateMilePost(List<MilePostDetailsVO> mpDetailsList) throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param :MilePostDetailsVO
     * @throws :RMDDAOException
     * @Description: This method adds new milepost.
     */
    public String addNewMilePost(MilePostDetailsVO objMilePostDetails) throws RMDDAOException;

    /**
     * @Author:
     * @param :MilePostDetailsVO
     * @throws:RMDDAOException
     * @Description: This method is used to delete the milepost
     */
    public void deleteMilePost(MilePostDetailsVO objMilePostDetailsVO) throws RMDDAOException;

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDDAOException
     * @Description: This Method Fetches the list of stateprovince.
     */
    public List<String> getStateProvince() throws RMDDAOException;

    /**
     * @Author:
     * @param :customerId
     * @return:List<String>
     * @throws:RMDDAOException
     * @Description: This method is used to get proximity parent for the
     *               selected customer
     */
    public List<String> getProximityParent(String customerId) throws RMDDAOException;

    /**
     * @Author:
     * @param :customerId,region,subRegion,userId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used to add New Region/sub region
     */
    public String addRegionSubRegion(CreateRegionVO regionVO) throws RMDDAOException;

    /**
     * @Author:
     * @param :customerId
     * @return:List<String>
     * @throws:RMDDAOException;
     * @Description: This method is used to get region for the selected customer
     */
    public List<String> getRegion(String customerId) throws RMDDAOException;;

    /**
     * @Author:
     * @param :region
     * @return:List<String>
     * @throws:RMDDAOException;
     * @Description: This method is used to get sub region for the selected
     *               region
     */
    public List<String> getSubRegion(String region) throws RMDDAOException;;

}

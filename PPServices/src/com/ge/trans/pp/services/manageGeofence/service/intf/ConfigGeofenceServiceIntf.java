package com.ge.trans.pp.services.manageGeofence.service.intf;

import java.util.List;

import com.ge.trans.pp.services.manageGeofence.service.valueobjects.AddNotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.CreateRegionVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.MilePostDetailsVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.NotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ProximityDetailsVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface ConfigGeofenceServiceIntf {

    /**
     * @Author :
     * @return :List<NotifyConfigVO>
     * @param :
     *            String customer
     * @throws :RMDServiceException
     * @Description: This method fetches Notify Configuration details.
     */
    public List<NotifyConfigVO> getNotifyConfigs(String customer) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDServiceException
     * @Description: This method is used for Adding a Notify Configuration.
     */
    public String addNotifyConfig(AddNotifyConfigVO objAddNotifyConfigVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDServiceException
     * @Description: This method is used for Updating a Notify Configuration.
     */
    public String updateNotifyConfig(List<NotifyConfigVO> arlNotifyConfigVOs) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDServiceException
     * @Description: This method is used for fetching notify flags.
     */
    public List<String> getNotifyFlags() throws RMDServiceException;

    /**
     * @Author :
     * @return :List<String>
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDServiceException
     * @Description:This method is used for fetching Threshold values.
     */
    public List<String> getThreshold() throws RMDServiceException;

    // MUrali Changes
    /**
     * @Author :
     * @return :List<ProximityDetailsVO>
     * @param :@Context
     *            UriInfo ui
     * @throws :RMDServiceException
     * @Description: This method is used to get the proximity details for the
     *               selected customer.
     */
    public List<ProximityDetailsVO> getProximityDetails(String customerID, String proxStatus)
            throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<ProximityDetailsVO>
     * @throws :RMDServiceException
     * @Description: This method is used to update selected proximity details
     */
    public String updateProximity(List<ProximityDetailsVO> proxDetailsList) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :ProximityDetailsVO
     * @throws :RMDServiceException
     * @Description: This method adds new proximity..
     */
    public String addNewProximity(ProximityDetailsVO objProxDetails) throws RMDServiceException;

    /**
     * @Author:
     * @param :ProximityDetailsVO
     * @throws:RMDServiceException
     * @Description: This method is used to delete the proximities
     */
    public void deleteProximity(ProximityDetailsVO objProximityDetailsVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<MilePostDetailsVO>
     * @param :String
     *            customerID,String milePostStatus
     * @throws :RMDServiceException
     * @Description: This method is used to get the milepost details for the
     *               selected customer.
     */
    public List<MilePostDetailsVO> getMilePostDetails(String customerID, String milePostStatus)
            throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<MilePostDetailsVO>
     * @throws :RMDServiceException
     * @Description: This method is used to update selected milepost details
     */
    public String updateMilePost(List<MilePostDetailsVO> mpDetailsList) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :MilePostDetailsVO
     * @throws :RMDServiceException
     * @Description: This method adds new milepost.
     */
    public String addNewMilePost(MilePostDetailsVO objMilePostDetails) throws RMDServiceException;

    /**
     * @Author:
     * @param :MilePostDetailsVO
     * @throws:RMDServiceException
     * @Description: This method is used to delete the milepost
     */
    public void deleteMilePost(MilePostDetailsVO objMilePostDetailsVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of stateprovince.
     */
    public List<String> getStateProvince() throws RMDServiceException;

    /**
     * @Author:
     * @param :customerId
     * @return:List<String>
     * @throws:RMDServiceException
     * @Description: This method is used to get proximity parent for the
     *               selected customer
     */
    public List<String> getProximityParent(String customerId) throws RMDServiceException;

    public List<String> getRegion(String customerId) throws RMDServiceException;

    /**
     * @Author:
     * @param :region
     * @return:List<String>
     * @throws:RMDServiceException
     * @Description: This method is used to get sub region for the selected
     *               region
     */
    public List<String> getSubRegion(String region) throws RMDServiceException;

    /**
     * @param userId
     * @Author:
     * @param :CreateRegionVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used to add New Region/sub region
     */
    public String addRegionSubRegion(CreateRegionVO regionVO) throws RMDServiceException;;

}

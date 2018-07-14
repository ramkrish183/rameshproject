package com.ge.trans.pp.services.manageGeofence.dao.intf;

import java.util.List;

import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceReqVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceRespVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface ManageGeofenceDAOIntf {

    /**
     * @param ManageGeofenceReqVO
     * @return List<ManageGeofenceRespVO>
     * @throws RMDDAOException
     * @Description:get the GeofenceProximityData for the loged in user
     */
    public List<ManageGeofenceRespVO> getGeofenceProximityData(ManageGeofenceReqVO objManageGeofenceReqVO)
            throws RMDDAOException;

    /**
     * @param ManageGeofenceReqVO
     * @return String
     * @throws RMDDAOException
     * @Description: validate the user entered GeofenceData with DB
     */
    public String validateGeofenceData(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDDAOException;

    /**
     * @param userServiceVO
     * @throws RMDDAOException
     * @Description:SaveOrUpdate the GeofenceDetails into DataBase
     */
    public void saveUpdateGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDDAOException;

    /**
     * @param ManageGeofenceReqVO
     * @return
     * @throws RMDDAOException
     * @Description: Delete the GeofenceDetails from DataBase
     */
    public void deleteGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDDAOException;
}

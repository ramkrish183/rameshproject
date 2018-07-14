package com.ge.trans.pp.services.manageGeofence.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.pp.common.constants.OMDConstants;
import com.ge.trans.pp.common.exception.OMDInValidInputException;
import com.ge.trans.pp.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.pp.common.resources.BaseResource;
import com.ge.trans.pp.common.util.BeanUtility;
import com.ge.trans.pp.common.util.PPRMDWebServiceErrorHandler;
import com.ge.trans.pp.services.manageGeofence.valueobjects.ManageGeofenceRequestType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.ManageGeofenceResponseType;
import com.ge.trans.pp.services.manageGeofence.service.intf.ManageGeofenceServiceIntf;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceReqVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceRespVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: June 23, 2014
 * @Date Modified : June 23, 2014
 * @Modified By :
 * @Contact :
 * @Description : This Class act as ManageGeofenceResource Webservices and
 *              provide the proximity related funtionalities
 * @History :
 ******************************************************************************/
@Path(OMDConstants.MANAGE_GEOFENCE_SERVICE)
@Component
public class ManageGeofenceResource extends BaseResource {

    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(ManageGeofenceResource.class);

    @Autowired
    private ManageGeofenceServiceIntf objManageGeofenceServiceIntf;

    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    /**
     * This Method is used for retrieving GeofenceProximityData.
     * 
     * @param ManageGeofenceRequestType
     * @return List<ManageGeofenceResponseType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.MANAGE_GEOFENCE_DATA)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ManageGeofenceResponseType> getGeofenceProximityData(final ManageGeofenceRequestType mngGeofenceReqType)
            throws RMDServiceException {
        RMDLOGGER.debug("ManageGeofenceResource : getGeofenceProximityData() method Starts");
        ManageGeofenceReqVO objManageGeofenceReqVO = null;
        ManageGeofenceResponseType objManageGeofenceRespType = null;
        List<ManageGeofenceResponseType> objManageGeofenceRespTypeList = new ArrayList<ManageGeofenceResponseType>();
        List<ManageGeofenceRespVO> objManageGeofenceRespVOList = null;
        try {
            if (mngGeofenceReqType != null && mngGeofenceReqType.getCustomerId() != null
                    && !mngGeofenceReqType.getCustomerId().isEmpty()) {
                objManageGeofenceReqVO = new ManageGeofenceReqVO();

                objManageGeofenceReqVO
                        .setCustomerId(BeanUtility.stripXSSCharacters(mngGeofenceReqType.getCustomerId()));

                objManageGeofenceRespVOList = objManageGeofenceServiceIntf
                        .getGeofenceProximityData(objManageGeofenceReqVO);

                if (RMDCommonUtility.isCollectionNotEmpty(objManageGeofenceRespVOList)) {

                    for (ManageGeofenceRespVO objManageGeofenceRespVO : objManageGeofenceRespVOList) {
                        objManageGeofenceRespType = new ManageGeofenceResponseType();
                        objManageGeofenceRespType.setGeofenceSeqId(objManageGeofenceRespVO.getGeofenceSeqId());
                        objManageGeofenceRespType.setCustomerId(objManageGeofenceRespVO.getCustomerId());
                        objManageGeofenceRespType.setGeofenceName(objManageGeofenceRespVO.getGeofenceName());
                        objManageGeofenceRespType.setProximityEvent(objManageGeofenceRespVO.getProximityEvent());
                        objManageGeofenceRespType.setUpLeftLat(objManageGeofenceRespVO.getUpLeftLat());
                        objManageGeofenceRespType.setUpLeftLong(objManageGeofenceRespVO.getUpLeftLong());
                        objManageGeofenceRespType.setLowerRightLat(objManageGeofenceRespVO.getLowerRightLat());
                        objManageGeofenceRespType.setLowerRightLong(objManageGeofenceRespVO.getLowerRightLong());
                        objManageGeofenceRespType.setGeofenceNotes(objManageGeofenceRespVO.getGeofenceNotes());

                        objManageGeofenceRespTypeList.add(objManageGeofenceRespType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {
            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        RMDLOGGER.debug("ManageGeofenceResource : getGeofenceProximityData() method::END");
        return objManageGeofenceRespTypeList;
    }

    /**
     * This Method is used to SaveOrUpdate the GeofenceDetails.
     * 
     * @param GeofenceReportRequestType
     * @return List<GeofenceReportResponseType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.SAVE_UPDATE_GEOFENCE_DETAILS)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void saveUpdateGeofenceDetails(final ManageGeofenceRequestType mngGeofenceReqType)
            throws RMDServiceException {
        RMDLOGGER.debug("ManageGeofenceResource : saveUpdateGeofenceDetails() method::Starts");
        ManageGeofenceReqVO objManageGeofenceReqVO = null;
        try {
            if (mngGeofenceReqType != null && validateGeofenceProximityData(mngGeofenceReqType)) {
                objManageGeofenceReqVO = new ManageGeofenceReqVO();

                objManageGeofenceReqVO.setStrUserName(mngGeofenceReqType.getUserId());
                objManageGeofenceReqVO.setCustomerId(mngGeofenceReqType.getCustomerId());
                objManageGeofenceReqVO.setGeofenceName(mngGeofenceReqType.getGeofenceName());
                objManageGeofenceReqVO.setProximityEvent(mngGeofenceReqType.getProximityEvent());
                objManageGeofenceReqVO.setUpLeftLat(mngGeofenceReqType.getUpLeftLat());
                objManageGeofenceReqVO.setUpLeftLong(mngGeofenceReqType.getUpLeftLong());
                objManageGeofenceReqVO.setLowerRightLat(mngGeofenceReqType.getLowerRightLat());
                objManageGeofenceReqVO.setLowerRightLong(mngGeofenceReqType.getLowerRightLong());
                objManageGeofenceReqVO.setGeofenceNotes(mngGeofenceReqType.getGeofenceNotes());
                objManageGeofenceReqVO.setActionType(mngGeofenceReqType.getActionType());
                objManageGeofenceReqVO.setGeofenceSeqId(mngGeofenceReqType.getGeofenceSeqId());
                objManageGeofenceServiceIntf.saveUpdateGeofenceDetails(objManageGeofenceReqVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("ManageGeofenceResource : saveUpdateGeofenceDetails() method:::END");
    }

    /**
     * This Method is used to delete the GeofenceDetails from DB.
     * 
     * @param GeofenceReportRequestType
     * @return List<GeofenceReportResponseType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.DELETE_GEOFENCE_DETAILS)
    @Consumes(MediaType.APPLICATION_XML)
    public void deleteGeofenceDetails(final ManageGeofenceRequestType mngGeofenceReqType) throws RMDServiceException {
        RMDLOGGER.debug("ManageGeofenceResource : deleteGeofenceDetails() method::Starts");
        ManageGeofenceReqVO objManageGeofenceReqVO = null;
        try {
            if (mngGeofenceReqType != null && mngGeofenceReqType.getGeofenceSeqId() != null
                    && !mngGeofenceReqType.getGeofenceSeqId().isEmpty()) {
                objManageGeofenceReqVO = new ManageGeofenceReqVO();
                objManageGeofenceReqVO.setGeofenceSeqId(mngGeofenceReqType.getGeofenceSeqId());
                objManageGeofenceServiceIntf.deleteGeofenceDetails(objManageGeofenceReqVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {
            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        RMDLOGGER.debug("ManageGeofenceResource : deleteGeofenceDetails() method::END");
    }

    /**
     * This Method is used to Validate the input GeofenceDetails with DB.
     * 
     * @param ManageGeofenceRequestType
     * @return String
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.VALIDATE_GEOFENCE_DATA)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String validateGeofenceData(final ManageGeofenceRequestType mngGeofenceReqType) throws RMDServiceException {
        String isGeofenceValid = null;
        ManageGeofenceReqVO objManageGeofenceReqVO = null;
        try {
            if (mngGeofenceReqType != null && validateGeofenceProximityData(mngGeofenceReqType)) {
                objManageGeofenceReqVO = new ManageGeofenceReqVO();
                objManageGeofenceReqVO
                        .setCustomerId(BeanUtility.stripXSSCharacters(mngGeofenceReqType.getCustomerId()));
                objManageGeofenceReqVO
                        .setGeofenceName(BeanUtility.stripXSSCharacters(mngGeofenceReqType.getGeofenceName()));
                objManageGeofenceReqVO
                        .setProximityEvent(BeanUtility.stripXSSCharacters(mngGeofenceReqType.getProximityEvent()));
                objManageGeofenceReqVO
                        .setActionType(BeanUtility.stripXSSCharacters(mngGeofenceReqType.getActionType()));
                objManageGeofenceReqVO
                        .setGeofenceSeqId(BeanUtility.stripXSSCharacters(mngGeofenceReqType.getGeofenceSeqId()));

                isGeofenceValid = objManageGeofenceServiceIntf.validateGeofenceData(objManageGeofenceReqVO);

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {

            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        RMDLOGGER.debug("ManageGeofenceResource : Inside getGeofenceReport() method:::END");
        return isGeofenceValid;
    }

    /*
     * This method is used to validate the GeofenceDetails Input
     */
    private static boolean validateGeofenceProximityData(final ManageGeofenceRequestType mngGeofenceReqType) {

        if (null != mngGeofenceReqType.getCustomerId() && !mngGeofenceReqType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(mngGeofenceReqType.getCustomerId())) {
                return false;
            }
        }
        if (null != mngGeofenceReqType.getProximityEvent() && !mngGeofenceReqType.getProximityEvent().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(mngGeofenceReqType.getProximityEvent())) {
                return false;
            }
        }

        if (null != mngGeofenceReqType.getGeofenceSeqId() && !mngGeofenceReqType.getGeofenceSeqId().isEmpty()) {
            if (!AppSecUtil.checkIntNumber(mngGeofenceReqType.getGeofenceSeqId())) {
                return false;
            }
        }

        if (null != mngGeofenceReqType.getActionType() && !mngGeofenceReqType.getActionType().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(mngGeofenceReqType.getActionType())) {
                return false;
            }
        }
        if (RMDCommonUtility.isSpecialCharactersFound(mngGeofenceReqType.getGeofenceName())) {
            return false;
        }
        if (RMDCommonUtility.isSpecialCharactersFound(mngGeofenceReqType.getGeofenceNotes())) {
            return false;
        }

        return true;
    }

}

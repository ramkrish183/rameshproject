package com.ge.trans.rmd.services.assets.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.asset.service.intf.RepairFacilityServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CustomerSiteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RepairFacilityDetailsVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.CustomerSiteResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.RepairFacilityDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.RepairFacilityRequestType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_REPAIR_MAINTENANCE_SERVICE)
@Component
public class RepairMaintenanceResource {

    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    RepairFacilityServiceIntf repairFacilityServiceIntf;

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(VehicleCfgResource.class);

    /**
     * @Author :
     * @return :List<CustomerSiteResponseType>
     * @param :
     * @Context final UriInfo uriParam
     * @throws :RMDServiceException
     * @Description: This method is used for fetching the sites for the selected
     *               customer.
     */
    @GET
    @Path(OMDConstants.GET_CUST_SITES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CustomerSiteResponseType> getCustomerSites(@Context final UriInfo uriParam) throws RMDServiceException {

        List<CustomerSiteResponseType> listCustSiteResponse = new ArrayList<CustomerSiteResponseType>();
        MultivaluedMap<String, String> queryParams = null;
        List<CustomerSiteVO> arlCustSites = null;
        String customerId = null;
        CustomerSiteResponseType objCustResponse = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
                }

                arlCustSites = repairFacilityServiceIntf.getCustomerSites(customerId);
                if (RMDCommonUtility.isCollectionNotEmpty(arlCustSites)) {
                    listCustSiteResponse = new ArrayList<CustomerSiteResponseType>(arlCustSites.size());
                    for (final Iterator iterator = arlCustSites.iterator(); iterator.hasNext();) {
                        final CustomerSiteVO objCustomerSiteVO = (CustomerSiteVO) iterator.next();
                        objCustResponse = new CustomerSiteResponseType();
                        objCustResponse.setSiteObjId(objCustomerSiteVO.getSiteObjId());
                        objCustResponse.setSiteName(objCustomerSiteVO.getSiteName());
                        listCustSiteResponse.add(objCustResponse);
                    }
                    arlCustSites = null;
                }
            }

        } catch (Exception ex) {
            LOG.error("Exception occured in getCustomerSites() method of RepairMaintenanceResource");
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return listCustSiteResponse;
    }

    /**
     * @param @Context
     *            UriInfo
     * @return List<RepairFacilityDetailsResponseType>
     * @throws RMDServiceException
     * @Description This method is used to get the Repair Facility Details.
     */
    @GET
    @Path(OMDConstants.GET_REPAIR_FACILITY_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RepairFacilityDetailsResponseType> getRepairFacilityDetails(@Context UriInfo ui)
            throws RMDServiceException {
        List<RepairFacilityDetailsResponseType> repairFacilityDetailsList = new ArrayList<RepairFacilityDetailsResponseType>();
        List<RepairFacilityDetailsVO> objRepairDetails = new ArrayList<RepairFacilityDetailsVO>();
        RepairFacilityDetailsResponseType objRepairFacilityResponse = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            String customerId = OMDConstants.EMPTY_STRING, site = OMDConstants.EMPTY_STRING,
                    status = OMDConstants.EMPTY_STRING;
            try {
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                }
                if (queryParams.containsKey(OMDConstants.SITE)) {
                    site = queryParams.getFirst(OMDConstants.SITE);
                }
                if (queryParams.containsKey(OMDConstants.STATUS)) {
                    status = queryParams.getFirst(OMDConstants.STATUS);
                }
                if (RMDCommonUtility.isNullOrEmpty(customerId)) {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(site)) {
                    throw new OMDInValidInputException(OMDConstants.SITE_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(status)) {
                    throw new OMDInValidInputException(OMDConstants.STATUS_NOT_PROVIDED);
                }
                objRepairDetails = repairFacilityServiceIntf.getRepairFacilityDetails(customerId, site, status);
                if (RMDCommonUtility.isCollectionNotEmpty(objRepairDetails)) {
                    repairFacilityDetailsList = new ArrayList<RepairFacilityDetailsResponseType>(
                            objRepairDetails.size());
                    for (RepairFacilityDetailsVO objRepairFacilityDetailsVO : objRepairDetails) {
                        objRepairFacilityResponse = new RepairFacilityDetailsResponseType();
                        objRepairFacilityResponse.setObjId(objRepairFacilityDetailsVO.getObjId());
                        objRepairFacilityResponse.setRailWayDesigCode(EsapiUtil.resumeSpecialChars(objRepairFacilityDetailsVO.getRailWayDesigCode()));
                        objRepairFacilityResponse.setRepairLocation(EsapiUtil.resumeSpecialChars(objRepairFacilityDetailsVO.getRepairLocation()));
                        objRepairFacilityResponse.setLocationCode(EsapiUtil.resumeSpecialChars(objRepairFacilityDetailsVO.getLocationCode()));
                        objRepairFacilityResponse.setStatus(objRepairFacilityDetailsVO.getStatus());
                        repairFacilityDetailsList.add(objRepairFacilityResponse);

                    }
                }

                objRepairDetails = null;

            } catch (Exception e) {
                LOG.debug("", e);
                throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

        } catch (Exception e) {
            LOG.debug("", e);
        }

        return repairFacilityDetailsList;
    }

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to insert new repair/site location
     *              details.
     */
    @POST
    @Path(OMDConstants.INSERT_REPAIR_SITE_LOCATION)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String insertRepairSiteLoc(RepairFacilityRequestType objRepairFacilityRequestType)
            throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;

        try {
            if (null != objRepairFacilityRequestType) {
                RepairFacilityDetailsVO objRepairDetailsVO = new RepairFacilityDetailsVO();

                if (!RMDCommonUtility.isNullOrEmpty(objRepairFacilityRequestType.getCustomerId())) {
                    objRepairDetailsVO.setCustomerId(objRepairFacilityRequestType.getCustomerId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_VALUE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairFacilityRequestType.getSite())) {
                    objRepairDetailsVO.setSite(objRepairFacilityRequestType.getSite());
                } else {
                    throw new OMDInValidInputException(OMDConstants.SITE_VALUE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairFacilityRequestType.getRailwayDesigCode())) {
                    objRepairDetailsVO.setRailWayDesigCode(objRepairFacilityRequestType.getRailwayDesigCode());
                } else {
                    throw new OMDInValidInputException(OMDConstants.RAILWAY_DESIG_CODE_VALUE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairFacilityRequestType.getRepairLocation())) {
                    objRepairDetailsVO.setRepairLocation(objRepairFacilityRequestType.getRepairLocation());
                } else {
                    throw new OMDInValidInputException(OMDConstants.REPAIR_LOCATION_VALUE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairFacilityRequestType.getLocationCode())) {
                    objRepairDetailsVO.setLocationCode(objRepairFacilityRequestType.getLocationCode());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairFacilityRequestType.getStatus())) {
                    objRepairDetailsVO.setStatus(objRepairFacilityRequestType.getStatus());
                } else {
                    throw new OMDInValidInputException(OMDConstants.STATUS_VALUE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairFacilityRequestType.getUserName())) {
                    objRepairDetailsVO.setUserName(objRepairFacilityRequestType.getUserName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.USER_NAME_VALUE_NOT_PROVIDED);
                }
                result = repairFacilityServiceIntf.insertRepairSiteLoc(objRepairDetailsVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception e) {
            LOG.error("Exception occuered in insertRepairSiteLoc() method of RepairMaintenanceResource" + e);
            result = RMDCommonConstants.FAILURE;
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return result;
    }

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to update existing repair/site location
     *              details.
     */
    @POST
    @Path(OMDConstants.UPDATE_REPAIR_LOCATION_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String updateRepairSiteLoc(RepairFacilityRequestType objRequestType) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            List<RepairFacilityDetailsVO> arlReapirDetailsVO = new ArrayList<RepairFacilityDetailsVO>();
            List<RepairFacilityRequestType> arlRepairRequestTypes = objRequestType.getArlRepairFaciityRequestType();
            for (RepairFacilityRequestType objRepairRequestType : arlRepairRequestTypes) {
                RepairFacilityDetailsVO objRepairDetailsVO = new RepairFacilityDetailsVO();
                if (!RMDCommonUtility.isNullOrEmpty(objRepairRequestType.getCustomerId())) {
                    objRepairDetailsVO.setCustomerId(objRepairRequestType.getCustomerId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairRequestType.getObjId())) {
                    objRepairDetailsVO.setObjId(objRepairRequestType.getObjId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.SITE_OBJID_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairRequestType.getRailwayDesigCode())) {
                    objRepairDetailsVO.setRailWayDesigCode(objRepairRequestType.getRailwayDesigCode());
                } else {
                    throw new OMDInValidInputException(OMDConstants.RAILWAY_DESIG_CODE_VALUE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairRequestType.getRepairLocation())) {
                    objRepairDetailsVO.setRepairLocation(objRepairRequestType.getRepairLocation());
                } else {
                    throw new OMDInValidInputException(OMDConstants.REPAIR_LOCATION_VALUE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairRequestType.getLocationCode())) {
                    objRepairDetailsVO.setLocationCode(objRepairRequestType.getLocationCode());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairRequestType.getStatus())) {
                    objRepairDetailsVO.setStatus(objRepairRequestType.getStatus());
                } else {
                    throw new OMDInValidInputException(OMDConstants.STATUS_VALUE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objRepairRequestType.getUserName())) {
                    objRepairDetailsVO.setUserName(objRepairRequestType.getUserName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.USER_NAME_VALUE_NOT_PROVIDED);
                }

                arlReapirDetailsVO.add(objRepairDetailsVO);
            }
            result = repairFacilityServiceIntf.updateRepairSiteLoc(arlReapirDetailsVO);

        } catch (Exception e) {
            LOG.error("Exception occuered in updateRepairSiteLoc() method of RepairMaintenanceResource" + e);
            result = RMDCommonConstants.FAILURE;
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return result;
    }

}

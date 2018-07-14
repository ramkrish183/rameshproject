package com.ge.trans.rmd.services.assets.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

import com.ge.trans.eoa.services.asset.service.intf.ContactSiteServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddRemoveSecondarySiteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddressDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddressSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ContactDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ContactSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ContactSiteDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ISDCodeVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.SiteDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.SiteSearchVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.AddEditAddressRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.AddRemoveSecondarySiteRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.AddUpdateContactRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.AddressDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ContactDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ContactSiteDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ISDCodeResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.SecondarySiteDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.SiteDetailsRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.SiteDetailsResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_CONTACT_SITE_SERVICE)
@Component
public class ContactSiteResource extends BaseResource {

    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    ContactSiteServiceIntf contactSiteServiceIntf;
    public static final RMDLogger LOG = RMDLoggerHelper
            .getLogger(VehicleCfgResource.class);

    /**
     * @Author:
     * @param:
     * @return:List<ContactDetailsResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the contact details for
     *               the given search combinations
     */
    @GET
    @Path(OMDConstants.GET_CONTACTS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ContactDetailsResponseType> getContacts(@Context UriInfo ui)
            throws RMDServiceException {
        List<ContactDetailsResponseType> contactDetailsResponseTypeList = null;
        List<ContactDetailsVO> contactDetailsVOList = null;
        ContactDetailsResponseType contactDetailsResponseType = null;
        ContactSearchVO objContactSearchVO = null;
        MultivaluedMap<String, String> queryParams = null;
        try {

            queryParams = ui.getQueryParameters();
            objContactSearchVO = new ContactSearchVO();
            if (queryParams.containsKey(OMDConstants.FIRST_NAME)) {
                objContactSearchVO.setFirstName(queryParams
                        .getFirst(OMDConstants.FIRST_NAME));
            }
            if (queryParams.containsKey(OMDConstants.LAST_NAME)) {
                objContactSearchVO.setLastName(queryParams
                        .getFirst(OMDConstants.LAST_NAME));
            }
            if (queryParams.containsKey(OMDConstants.PH_NO)) {
                objContactSearchVO.setPhNo(queryParams
                        .getFirst(OMDConstants.PH_NO));
            }
            if (queryParams.containsKey(OMDConstants.SITE_ID)) {
                objContactSearchVO.setSiteId(queryParams
                        .getFirst(OMDConstants.SITE_ID));
            }
            if (queryParams.containsKey(OMDConstants.SITE_NAME)) {
                objContactSearchVO.setSiteName(queryParams
                        .getFirst(OMDConstants.SITE_NAME));
            }
            if (queryParams.containsKey(OMDConstants.CONTACT_STATUS)) {
                objContactSearchVO.setContactStatus(queryParams
                        .getFirst(OMDConstants.CONTACT_STATUS));
            }

            contactDetailsVOList = contactSiteServiceIntf
                    .getContacts(objContactSearchVO);
            if (RMDCommonUtility.isCollectionNotEmpty(contactDetailsVOList)) {
                contactDetailsResponseTypeList = new ArrayList<ContactDetailsResponseType>(
                        contactDetailsVOList.size());
                for (ContactDetailsVO objContactDetailsVO : contactDetailsVOList) {
                    contactDetailsResponseType = new ContactDetailsResponseType();
                    contactDetailsResponseType
                            .setContactObjId(objContactDetailsVO
                                    .getContactObjId());
                    contactDetailsResponseType.setFirstName(objContactDetailsVO
                            .getFirstName());
                    contactDetailsResponseType.setLastName(objContactDetailsVO
                            .getLastName());
                    contactDetailsResponseType.setSiteName(objContactDetailsVO
                            .getSiteName());
                    contactDetailsResponseType.setPhNo(objContactDetailsVO
                            .getPhNo());
                    contactDetailsResponseType.setCity(objContactDetailsVO
                            .getCity());
                    contactDetailsResponseType.setCountry(objContactDetailsVO
                            .getCountry());
                    contactDetailsResponseType
                            .setContactRole(objContactDetailsVO
                                    .getContactRole());
                    contactDetailsResponseTypeList
                            .add(contactDetailsResponseType);

                }

            }
        } catch (Exception e) {
            LOG.error("Exception occuered in getContacts() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        } finally {
            queryParams = null;
            contactDetailsVOList = null;
        }

        return contactDetailsResponseTypeList;

    }

    /**
     * 
     * @param UriInfo
     *            ui
     * @return ContactSiteDetailsResponseType
     * @throws RMDServiceException
     * @Description This method is used to get the all details for the selected
     *              contact
     * 
     */
    @GET
    @Path(OMDConstants.VIEW_CONTACT_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ContactSiteDetailsResponseType viewContactDetails(@Context UriInfo ui)
            throws RMDServiceException {
        ContactSiteDetailsResponseType objContactSiteDetailsResponseType = null;
        ContactSiteDetailsVO objContactSiteDetailsVO = null;
        MultivaluedMap<String, String> queryParams = null;
        String contactObjId = OMDConstants.EMPTY_STRING;
        try {

            queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CONTACT_OBJID)) {
                contactObjId = queryParams.getFirst(OMDConstants.CONTACT_OBJID);
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.CONTACT_OBJID_NOT_PROVIDED);
            }
            objContactSiteDetailsVO = contactSiteServiceIntf
                    .viewContactDetails(contactObjId);
            if (null != objContactSiteDetailsVO) {
                objContactSiteDetailsResponseType = new ContactSiteDetailsResponseType();
                objContactSiteDetailsResponseType
                        .setContactObjId(objContactSiteDetailsVO
                                .getContactObjId());
                objContactSiteDetailsResponseType
                        .setJobTitle(objContactSiteDetailsVO.getJobTitle());
                objContactSiteDetailsResponseType
                        .setContactStatus(objContactSiteDetailsVO
                                .getContactStatus());
                objContactSiteDetailsResponseType
                        .setFirstName(objContactSiteDetailsVO.getFirstName());
                objContactSiteDetailsResponseType
                        .setLastName(objContactSiteDetailsVO.getLastName());
                objContactSiteDetailsResponseType
                        .setPhNo(objContactSiteDetailsVO.getPhNo());
                objContactSiteDetailsResponseType
                        .setFax(objContactSiteDetailsVO.getFax());
                objContactSiteDetailsResponseType
                        .setEmailId(objContactSiteDetailsVO.getEmailId());
                objContactSiteDetailsResponseType
                        .setTimeZone(objContactSiteDetailsVO.getTimeZone());
                objContactSiteDetailsResponseType
                        .setSiteId(objContactSiteDetailsVO.getSiteId());
                objContactSiteDetailsResponseType
                        .setSiteName(objContactSiteDetailsVO.getSiteName());
                objContactSiteDetailsResponseType
                        .setSiteType(objContactSiteDetailsVO.getSiteType());
                objContactSiteDetailsResponseType
                        .setAddress1(objContactSiteDetailsVO.getAddress1());
                objContactSiteDetailsResponseType
                        .setAddress2(objContactSiteDetailsVO.getAddress2());
                objContactSiteDetailsResponseType
                        .setCity(objContactSiteDetailsVO.getCity());
                objContactSiteDetailsResponseType
                        .setState(objContactSiteDetailsVO.getState());
                objContactSiteDetailsResponseType
                        .setCountry(objContactSiteDetailsVO.getCountry());
                objContactSiteDetailsResponseType
                        .setZipCode(objContactSiteDetailsVO.getZipCode());
                objContactSiteDetailsResponseType
                        .setSalutation(objContactSiteDetailsVO.getSalutation());
                objContactSiteDetailsResponseType
                        .setContactRole(objContactSiteDetailsVO
                                .getContactRole());
                objContactSiteDetailsResponseType
                        .setDailComm(objContactSiteDetailsVO.getDailComm());
                objContactSiteDetailsResponseType
                        .setHomePh(objContactSiteDetailsVO.getHomePh());
                objContactSiteDetailsResponseType
                        .setCellPh(objContactSiteDetailsVO.getCellPh());
                objContactSiteDetailsResponseType
                        .setLocObjId(objContactSiteDetailsVO.getLocObjId());
                objContactSiteDetailsResponseType
                        .setVoiceMail(objContactSiteDetailsVO.getVoiceMail());
            }

        } catch (Exception e) {
            LOG.error("Exception occuered in viewContactDetails() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        } finally {
            queryParams = null;
            objContactSiteDetailsVO = null;
        }
        return objContactSiteDetailsResponseType;
    }

    /**
     * 
     * @param UriInfo
     *            ui
     * @return List<SiteDetailsResponseType>
     * @throws RMDServiceException
     * @throws
     * @Description This method is used to get the secondary site details
     * 
     */
    @GET
    @Path(OMDConstants.GET_CONTACT_SECONDARY_SITES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SecondarySiteDetailsResponseType> getContactSecondarySites(
            @Context UriInfo ui) throws RMDServiceException {
        List<SecondarySiteDetailsResponseType> siteDetailsResponseTypeList = null;
        List<SiteDetailsVO> siteDetailsVOList = null;
        SecondarySiteDetailsResponseType objSiteDetailsResponseType = null;
        MultivaluedMap<String, String> queryParams = null;
        String contactObjId = OMDConstants.EMPTY_STRING;
        try {

            queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CONTACT_OBJID)) {
                contactObjId = queryParams.getFirst(OMDConstants.CONTACT_OBJID);
            }
            siteDetailsVOList = contactSiteServiceIntf
                    .getContactSecondarySites(contactObjId);
            if (RMDCommonUtility.isCollectionNotEmpty(siteDetailsVOList)) {
                siteDetailsResponseTypeList = new ArrayList<SecondarySiteDetailsResponseType>(
                        siteDetailsVOList.size());
                for (SiteDetailsVO objSiteDetailsVO : siteDetailsVOList) {
                    objSiteDetailsResponseType = new SecondarySiteDetailsResponseType();
                    objSiteDetailsResponseType.setSiteObjId(objSiteDetailsVO
                            .getSiteObjId());
                    objSiteDetailsResponseType.setContactRole(objSiteDetailsVO
                            .getContactRole());
                    objSiteDetailsResponseType.setSiteId(objSiteDetailsVO
                            .getSiteId());
                    objSiteDetailsResponseType.setSiteName(objSiteDetailsVO
                            .getSiteName());
                    objSiteDetailsResponseType.setAddress(objSiteDetailsVO
                            .getAddress());
                    objSiteDetailsResponseType.setCity(objSiteDetailsVO
                            .getCity());
                    objSiteDetailsResponseType.setState(objSiteDetailsVO
                            .getState());
                    objSiteDetailsResponseType.setZipCode(objSiteDetailsVO
                            .getZipCode());
                    siteDetailsResponseTypeList.add(objSiteDetailsResponseType);
                    objSiteDetailsResponseType = null;
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occuered in getContactSecondarySites() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        } finally {
            queryParams = null;
            siteDetailsVOList = null;
        }
        return siteDetailsResponseTypeList;
    }

    /**
     * 
     * @param AddRemoveSecondarySiteRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add the secondary site to a contact
     * 
     */
    @POST
    @Path(OMDConstants.ADD_CONTACT_SECONDARY_SITE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String addContactSecondarySite(
            AddRemoveSecondarySiteRequestType objAddRemoveSecondarySiteRequestType)
            throws RMDServiceException {
        String status = RMDCommonConstants.FAILURE;
        AddRemoveSecondarySiteVO objAddRemoveSecondarySiteVO = null;
        try {
            if (null != objAddRemoveSecondarySiteRequestType) {
                objAddRemoveSecondarySiteVO = new AddRemoveSecondarySiteVO();
                objAddRemoveSecondarySiteVO
                        .setContactObjId(objAddRemoveSecondarySiteRequestType
                                .getContactObjId());
                objAddRemoveSecondarySiteVO
                        .setSiteObjId(objAddRemoveSecondarySiteRequestType
                                .getSiteObjId());
                objAddRemoveSecondarySiteVO
                        .setContactRole(objAddRemoveSecondarySiteRequestType
                                .getContactRole());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }

            status = contactSiteServiceIntf
                    .addContactSecondarySite(objAddRemoveSecondarySiteVO);
        } catch (Exception e) {
            status = RMDCommonConstants.FAILURE;
            LOG.error("Exception occuered in addContactSecondarySite() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        } finally {
            objAddRemoveSecondarySiteVO = null;
        }
        return status;
    }

    /**
     * 
     * @param AddRemoveSecondarySiteRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to remove the secondary sites from the
     *              contact
     */
    @POST
    @Path(OMDConstants.REMOVE_CONTACT_SECONDARY_SITE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String removeContactSecondarySite(
            AddRemoveSecondarySiteRequestType objAddRemoveSecondarySiteRequestType)
            throws RMDServiceException {
        String status = RMDCommonConstants.FAILURE;
        List<AddRemoveSecondarySiteVO> arlAddRemoveSecondarySiteVO = null;
        AddRemoveSecondarySiteVO objAddRemoveSecondarySiteVO = null;
        List<AddRemoveSecondarySiteRequestType> arlAddRemoveSecondarySiteRequestType = null;
        try {
            if (null != objAddRemoveSecondarySiteRequestType
                    .getArlAddRemoveSecondarySiteRequestType()) {
                arlAddRemoveSecondarySiteRequestType = objAddRemoveSecondarySiteRequestType
                        .getArlAddRemoveSecondarySiteRequestType();
                arlAddRemoveSecondarySiteVO = new ArrayList<AddRemoveSecondarySiteVO>(
                        arlAddRemoveSecondarySiteRequestType.size());
                for (AddRemoveSecondarySiteRequestType objSecSiteRequestType : arlAddRemoveSecondarySiteRequestType) {
                    objAddRemoveSecondarySiteVO = new AddRemoveSecondarySiteVO();
                    objAddRemoveSecondarySiteVO
                            .setContactObjId(objSecSiteRequestType
                                    .getContactObjId());
                    objAddRemoveSecondarySiteVO
                            .setSiteObjId(objSecSiteRequestType.getSiteObjId());
                    objAddRemoveSecondarySiteVO
                            .setContactRole(objSecSiteRequestType
                                    .getContactRole());
                    arlAddRemoveSecondarySiteVO
                            .add(objAddRemoveSecondarySiteVO);
                    objAddRemoveSecondarySiteVO = null;
                }

            } else {
                throw new OMDInValidInputException(
                        OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }

            status = contactSiteServiceIntf
                    .removeContactSecondarySite(arlAddRemoveSecondarySiteVO);

        } catch (Exception e) {
            status = RMDCommonConstants.FAILURE;
            LOG.error("Exception occuered in removeContactSecondarySite() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        } finally {
            arlAddRemoveSecondarySiteVO = null;
            arlAddRemoveSecondarySiteRequestType = null;
        }
        return status;
    }

    /**
     * 
     * @param AddUpdateContactRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add or update the Contact details
     * 
     */
    @POST
    @Path(OMDConstants.UPDATE_CONTACT)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String addOrUpdateContact(
            AddUpdateContactRequestType objAddUpdateContactRequestType)
            throws RMDServiceException {
        String status = RMDCommonConstants.FAILURE;
        ContactSiteDetailsVO objContactSiteDetailsVO = null;
        try {
            if (null != objAddUpdateContactRequestType) {
                objContactSiteDetailsVO = new ContactSiteDetailsVO();
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getContactObjId())) {
                    objContactSiteDetailsVO
                            .setContactObjId(objAddUpdateContactRequestType
                                    .getContactObjId());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getJobTitle())) {
                    objContactSiteDetailsVO
                            .setJobTitle(objAddUpdateContactRequestType
                                    .getJobTitle());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getContactStatus())) {
                    objContactSiteDetailsVO
                            .setContactStatus(objAddUpdateContactRequestType
                                    .getContactStatus());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getFirstName())) {
                    objContactSiteDetailsVO
                            .setFirstName(objAddUpdateContactRequestType
                                    .getFirstName());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getLastName())) {
                    objContactSiteDetailsVO
                            .setLastName(objAddUpdateContactRequestType
                                    .getLastName());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType.getPhNo())) {
                    objContactSiteDetailsVO
                            .setPhNo(objAddUpdateContactRequestType.getPhNo());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType.getFax())) {
                    objContactSiteDetailsVO
                            .setFax(objAddUpdateContactRequestType.getFax());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getEmailId())) {
                    objContactSiteDetailsVO
                            .setEmailId(objAddUpdateContactRequestType
                                    .getEmailId());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getTimeZone())) {
                    objContactSiteDetailsVO
                            .setTimeZone(objAddUpdateContactRequestType
                                    .getTimeZone());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getSiteId())) {
                    objContactSiteDetailsVO
                            .setSiteId(objAddUpdateContactRequestType
                                    .getSiteId());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getSiteName())) {
                    objContactSiteDetailsVO
                            .setSiteName(objAddUpdateContactRequestType
                                    .getSiteName());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getSiteType())) {
                    objContactSiteDetailsVO
                            .setSiteType(objAddUpdateContactRequestType
                                    .getSiteType());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getAddress1())) {
                    objContactSiteDetailsVO
                            .setAddress1(objAddUpdateContactRequestType
                                    .getAddress1());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getAddress2())) {
                    objContactSiteDetailsVO
                            .setAddress2(objAddUpdateContactRequestType
                                    .getAddress2());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType.getCity())) {
                    objContactSiteDetailsVO
                            .setCity(objAddUpdateContactRequestType.getCity());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getState())) {
                    objContactSiteDetailsVO
                            .setState(objAddUpdateContactRequestType.getState());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getCountry())) {
                    objContactSiteDetailsVO
                            .setCountry(objAddUpdateContactRequestType
                                    .getCountry());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getZipCode())) {
                    objContactSiteDetailsVO
                            .setZipCode(objAddUpdateContactRequestType
                                    .getZipCode());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getSalutation())) {
                    objContactSiteDetailsVO
                            .setSalutation(objAddUpdateContactRequestType
                                    .getSalutation());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getContactRole())) {
                    objContactSiteDetailsVO
                            .setContactRole(objAddUpdateContactRequestType
                                    .getContactRole());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getDailComm())) {
                    objContactSiteDetailsVO
                            .setDailComm(objAddUpdateContactRequestType
                                    .getDailComm());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getHomePh())) {
                    objContactSiteDetailsVO
                            .setHomePh(objAddUpdateContactRequestType
                                    .getHomePh());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getCellPh())) {
                    objContactSiteDetailsVO
                            .setCellPh(objAddUpdateContactRequestType
                                    .getCellPh());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getLocObjId())) {
                    objContactSiteDetailsVO
                            .setLocObjId(objAddUpdateContactRequestType
                                    .getLocObjId());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getVoiceMail())) {
                    objContactSiteDetailsVO
                            .setVoiceMail(objAddUpdateContactRequestType
                                    .getVoiceMail());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getCreater())) {
                    objContactSiteDetailsVO
                            .setCreater(objAddUpdateContactRequestType
                                    .getCreater());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getActionFrom())) {
                    objContactSiteDetailsVO
                            .setActionFrom(objAddUpdateContactRequestType
                                    .getActionFrom());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getCheckDupContact())) {
                    objContactSiteDetailsVO
                            .setCheckDupContact(objAddUpdateContactRequestType
                                    .getCheckDupContact());
                }
                objContactSiteDetailsVO
                        .setRoleChanged(objAddUpdateContactRequestType
                                .isRoleChanged());
                objContactSiteDetailsVO
                        .setSiteChanged(objAddUpdateContactRequestType
                                .isSiteChanged());
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddUpdateContactRequestType
                                .getOldLocObjId())) {
                    objContactSiteDetailsVO
                            .setOldLocObjId(objAddUpdateContactRequestType
                                    .getOldLocObjId());
                }

            } else {
                throw new OMDInValidInputException(
                        OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }

            status = contactSiteServiceIntf
                    .addOrUpdateContact(objContactSiteDetailsVO);
        } catch (Exception e) {
            status = RMDCommonConstants.FAILURE;
            LOG.error("Exception occuered in addOrUpdateContact() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * @Author:
     * @param:SiteDetailsResponseType
     * @return:List<SiteDetailsResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used to get Sites.
     */
    @POST
    @Path(OMDConstants.GET_SITES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SiteDetailsResponseType> getSites(
            SiteDetailsRequestType objSiteDetailsRequestType)
            throws RMDServiceException {
        SiteSearchVO objSiteSearchVO = new SiteSearchVO();
        List<SiteSearchVO> objSiteSearchVOlst = new ArrayList<SiteSearchVO>();
        List<SiteDetailsResponseType> objSiteDetailsResponseTypelst = new ArrayList<SiteDetailsResponseType>();
        SiteDetailsResponseType objSiteDetailsResponseType = null;
        try {
            if (null != objSiteDetailsRequestType.getStrType()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrType())) {
                objSiteSearchVO.setStrType(objSiteDetailsRequestType
                        .getStrType());
            }
            if (null != objSiteDetailsRequestType.getStrSiteID()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrSiteID())) {
                objSiteSearchVO.setStrSiteID(objSiteDetailsRequestType
                        .getStrSiteID());
            }
            if (null != objSiteDetailsRequestType.getStrSiteName()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrSiteName())) {
                objSiteSearchVO.setStrSiteName(objSiteDetailsRequestType
                        .getStrSiteName());
            }
            if (null != objSiteDetailsRequestType.getStrAddress()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrAddress())) {
                objSiteSearchVO.setStrAddress(objSiteDetailsRequestType
                        .getStrAddress());
            }
            if (null != objSiteDetailsRequestType.getStrCity()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrCity())) {
                objSiteSearchVO.setStrCity(objSiteDetailsRequestType
                        .getStrCity());
            }
            if (null != objSiteDetailsRequestType.getStrState()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrState())) {
                objSiteSearchVO.setStrState(objSiteDetailsRequestType
                        .getStrState());
            }
            if (null != objSiteDetailsRequestType.getStrAccountID()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrAccountID())) {
                objSiteSearchVO.setStrAccountID(objSiteDetailsRequestType
                        .getStrAccountID());
            }
            if (null != objSiteDetailsRequestType.getStrAccountName()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrAccountName())) {
                objSiteSearchVO.setStrAccountName(objSiteDetailsRequestType
                        .getStrAccountName());
            }
            if (null != objSiteDetailsRequestType.getStrInclInactiveContacts()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrInclInactiveContacts())) {
                objSiteSearchVO
                        .setStrInclInactiveContacts(objSiteDetailsRequestType
                                .getStrInclInactiveContacts());
            }

            objSiteSearchVOlst = contactSiteServiceIntf
                    .getSites(objSiteSearchVO);
            if (RMDCommonUtility.isCollectionNotEmpty(objSiteSearchVOlst)) {
                objSiteDetailsResponseTypelst = new ArrayList<SiteDetailsResponseType>(
                        objSiteSearchVOlst.size());
                for (SiteSearchVO obj : objSiteSearchVOlst) {
                    objSiteDetailsResponseType = new SiteDetailsResponseType();
                    objSiteDetailsResponseType.setStrSiteID(obj.getStrSiteID());
                    objSiteDetailsResponseType.setStrSiteName(obj
                            .getStrSiteName());
                    objSiteDetailsResponseType.setStrAddress(obj
                            .getStrAddress());
                    objSiteDetailsResponseType.setStrCity(obj.getStrCity());
                    objSiteDetailsResponseType.setStrState(obj.getStrState());
                    objSiteDetailsResponseType.setStrType(obj.getStrType());
                    objSiteDetailsResponseType.setStrSiteObjId(obj
                            .getStrSiteObjId());
                    objSiteDetailsResponseType.setStrAddressEx(obj
                            .getStrAddressEx());
                    objSiteDetailsResponseType.setStrCountry(obj
                            .getStrCountry());
                    objSiteDetailsResponseType.setStrZipCode(obj
                            .getStrZipCode());
                    objSiteDetailsResponseTypelst
                            .add(objSiteDetailsResponseType);
                }
            }
            objSiteSearchVOlst = null;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        } finally {
            objSiteSearchVOlst = null;
        }
        return objSiteDetailsResponseTypelst;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<SiteDetailsResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used to get View Site Details.
     */
    @GET
    @Path(OMDConstants.VIEW_SITE_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public SiteDetailsResponseType viewSiteDetails(
            @Context final UriInfo uriParam) throws RMDServiceException {
        SiteSearchVO objSiteSearchVO = new SiteSearchVO();
        SiteDetailsResponseType objSiteDetailsResponseType = new SiteDetailsResponseType();
        String siteObjId = null;
        MultivaluedMap<String, String> queryParams = null;
        List<ContactSiteDetailsVO> listContactSiteDetailsVO = null;
        ListIterator<ContactSiteDetailsVO> listIterator = null;
        List<ContactDetailsResponseType> listContactDetailsResponseType = new ArrayList<ContactDetailsResponseType>();
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.SITE_OBJID)) {
                    siteObjId = queryParams.getFirst(OMDConstants.SITE_OBJID);
                }
                objSiteSearchVO = contactSiteServiceIntf
                        .viewSiteDetails(siteObjId);
                if (!RMDCommonUtility.checkNull(objSiteSearchVO)) {
                    objSiteDetailsResponseType.setStrSiteObjId(objSiteSearchVO
                            .getStrSiteObjId());
                    objSiteDetailsResponseType.setPrimAddObjId(objSiteSearchVO
                            .getPrimAddObjId());
                    objSiteDetailsResponseType.setShipAddObjId(objSiteSearchVO
                            .getShipAddObjId());
                    objSiteDetailsResponseType.setBillAddObjId(objSiteSearchVO
                            .getBillAddObjId());
                    objSiteDetailsResponseType.setStrSiteID(objSiteSearchVO
                            .getStrSiteID());
                    objSiteDetailsResponseType.setStrSiteName(objSiteSearchVO
                            .getStrSiteName());
                    objSiteDetailsResponseType.setStrStatus(objSiteSearchVO
                            .getStrStatus());
                    objSiteDetailsResponseType.setStrCreateType(objSiteSearchVO
                            .getStrCreateType());
                    objSiteDetailsResponseType.setStrAddress(objSiteSearchVO
                            .getStrAddress());
                    objSiteDetailsResponseType.setStrBillTo(objSiteSearchVO
                            .getStrBillTo());
                    objSiteDetailsResponseType.setStrShipTo(objSiteSearchVO
                            .getStrShipTo());
                    objSiteDetailsResponseType.setStrAddressEx(objSiteSearchVO
                            .getStrAddressEx());
                    objSiteDetailsResponseType.setStrBillToEx(objSiteSearchVO
                            .getStrBillToEx());
                    objSiteDetailsResponseType.setStrShipToEx(objSiteSearchVO
                            .getStrShipToEx());
                    objSiteDetailsResponseType.setStrCellPhone(objSiteSearchVO
                            .getStrCellPhone());
                    objSiteDetailsResponseType.setStrFax(objSiteSearchVO
                            .getStrFax());
                    objSiteDetailsResponseType
                            .setStrPrefShipMethod(objSiteSearchVO
                                    .getStrPrefShipMethod());
                    objSiteDetailsResponseType.setStrCustomer(objSiteSearchVO
                            .getStrCustomer());
                    if (objSiteSearchVO.getArlContactSiteDetailsVO() != null
                            && !objSiteSearchVO.getArlContactSiteDetailsVO()
                                    .isEmpty()) {
                        listContactSiteDetailsVO = objSiteSearchVO
                                .getArlContactSiteDetailsVO();
                        listIterator = listContactSiteDetailsVO.listIterator();
                        while (listIterator.hasNext()) {
                            ContactSiteDetailsVO contactSiteDetailsVO = new ContactSiteDetailsVO();
                            final ContactDetailsResponseType contactDetailsResponseType = new ContactDetailsResponseType();
                            contactSiteDetailsVO = listIterator.next();
                            contactDetailsResponseType
                                    .setContactObjId(contactSiteDetailsVO
                                            .getContactObjId());
                            contactDetailsResponseType
                                    .setSiteObjId(contactSiteDetailsVO
                                            .getLocObjId());
                            contactDetailsResponseType
                                    .setFirstName(contactSiteDetailsVO
                                            .getFirstName());
                            contactDetailsResponseType
                                    .setLastName(contactSiteDetailsVO
                                            .getLastName());
                            contactDetailsResponseType
                                    .setPhNo(contactSiteDetailsVO.getPhNo());
                            contactDetailsResponseType
                                    .setFax(contactSiteDetailsVO.getFax());
                            contactDetailsResponseType
                                    .setEmailId(contactSiteDetailsVO
                                            .getEmailId());
                            listContactDetailsResponseType
                                    .add(contactDetailsResponseType);
                        }
                        objSiteDetailsResponseType
                                .setContactDetailsResponseType(listContactDetailsResponseType);
                    }
                }
            }
        } catch (Exception ex) {
            LOG.debug(ex.getMessage(), ex);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        } finally {
            queryParams = null;
            objSiteSearchVO = null;
        }
        return objSiteDetailsResponseType;
    }

    /**
     * @Author:
     * @param:SiteDetailsRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used to update Site Details.
     */
    @POST
    @Path(OMDConstants.UPDATE_SITE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String updateSite(SiteDetailsRequestType objSiteDetailsRequestType)
            throws RMDServiceException {
        SiteSearchVO objSiteSearchVO = new SiteSearchVO();
        String strResult = null;
        try {
            if (null != objSiteDetailsRequestType.getStrCellPhone()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrCellPhone())) {
                objSiteSearchVO.setStrCellPhone(objSiteDetailsRequestType
                        .getStrCellPhone());
            }
            if (null != objSiteDetailsRequestType.getStrCreateType()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrCreateType())) {
                objSiteSearchVO.setStrCreateType(objSiteDetailsRequestType
                        .getStrCreateType());
            }
            if (null != objSiteDetailsRequestType.getStrCustomer()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrCustomer())) {
                objSiteSearchVO.setStrCustomer(objSiteDetailsRequestType
                        .getStrCustomer());
            }
            if (null != objSiteDetailsRequestType.getStrFax()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrFax())) {
                objSiteSearchVO
                        .setStrFax(objSiteDetailsRequestType.getStrFax());
            }
            if (null != objSiteDetailsRequestType.getStrPrefShipMethod()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrPrefShipMethod())) {
                objSiteSearchVO.setStrPrefShipMethod(objSiteDetailsRequestType
                        .getStrPrefShipMethod());
            }
            if (null != objSiteDetailsRequestType.getStrSiteID()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrSiteID())) {
                objSiteSearchVO.setStrSiteID(objSiteDetailsRequestType
                        .getStrSiteID());
            }
            if (null != objSiteDetailsRequestType.getStrSiteName()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrSiteName())) {
                objSiteSearchVO.setStrSiteName(objSiteDetailsRequestType
                        .getStrSiteName());
            }
            if (null != objSiteDetailsRequestType.getStrStatus()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrStatus())) {
                objSiteSearchVO.setStrStatus(objSiteDetailsRequestType
                        .getStrStatus());
            }
            if (null != objSiteDetailsRequestType.getStrSiteObjId()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrSiteObjId())) {
                objSiteSearchVO.setStrSiteObjId(objSiteDetailsRequestType
                        .getStrSiteObjId());
            }
            if (null != objSiteDetailsRequestType.getPrimAddObjId()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getPrimAddObjId())) {
                objSiteSearchVO.setPrimAddObjId(objSiteDetailsRequestType
                        .getPrimAddObjId());
            }
            if (null != objSiteDetailsRequestType.getShipAddObjId()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getShipAddObjId())) {
                objSiteSearchVO.setShipAddObjId(objSiteDetailsRequestType
                        .getShipAddObjId());
            }
            if (null != objSiteDetailsRequestType.getBillAddObjId()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getBillAddObjId())) {
                objSiteSearchVO.setBillAddObjId(objSiteDetailsRequestType
                        .getBillAddObjId());
            }

            strResult = contactSiteServiceIntf.updateSite(objSiteSearchVO);

        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        } finally {
            objSiteSearchVO = null;
        }
        return strResult;
    }

    /**
     * @Author:
     * @param:SiteDetailsRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used to create a Site.
     */
    @POST
    @Path(OMDConstants.CREATE_SITE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String createSite(SiteDetailsRequestType objSiteDetailsRequestType)
            throws RMDServiceException {
        SiteSearchVO objSiteSearchVO = new SiteSearchVO();
        String strResult = null;
        try {
            if (null != objSiteDetailsRequestType.getPrimAddObjId()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getPrimAddObjId())) {
                objSiteSearchVO.setPrimAddObjId(objSiteDetailsRequestType
                        .getPrimAddObjId());
            }
            if (null != objSiteDetailsRequestType.getBillAddObjId()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getBillAddObjId())) {
                objSiteSearchVO.setBillAddObjId(objSiteDetailsRequestType
                        .getBillAddObjId());
            }
            if (null != objSiteDetailsRequestType.getStrCellPhone()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrCellPhone())) {
                objSiteSearchVO.setStrCellPhone(objSiteDetailsRequestType
                        .getStrCellPhone());
            }
            if (null != objSiteDetailsRequestType.getStrCreateType()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrCreateType())) {
                objSiteSearchVO.setStrCreateType(objSiteDetailsRequestType
                        .getStrCreateType());
            }
            if (null != objSiteDetailsRequestType.getStrCustomer()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrCustomer())) {
                objSiteSearchVO.setStrCustomer(objSiteDetailsRequestType
                        .getStrCustomer());
            }
            if (null != objSiteDetailsRequestType.getStrFax()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrFax())) {
                objSiteSearchVO
                        .setStrFax(objSiteDetailsRequestType.getStrFax());
            }
            if (null != objSiteDetailsRequestType.getStrPrefShipMethod()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrPrefShipMethod())) {
                objSiteSearchVO.setStrPrefShipMethod(objSiteDetailsRequestType
                        .getStrPrefShipMethod());
            }
            if (null != objSiteDetailsRequestType.getShipAddObjId()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getShipAddObjId())) {
                objSiteSearchVO.setShipAddObjId(objSiteDetailsRequestType
                        .getShipAddObjId());
            }
            if (null != objSiteDetailsRequestType.getStrSiteID()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrSiteID())) {
                objSiteSearchVO.setStrSiteID(objSiteDetailsRequestType
                        .getStrSiteID());
            }
            if (null != objSiteDetailsRequestType.getStrSiteName()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrSiteName())) {
                objSiteSearchVO.setStrSiteName(objSiteDetailsRequestType
                        .getStrSiteName());
            }
            if (null != objSiteDetailsRequestType.getStrStatus()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSiteDetailsRequestType
                                    .getStrStatus())) {
                objSiteSearchVO.setStrStatus(objSiteDetailsRequestType
                        .getStrStatus());
            }

            strResult = contactSiteServiceIntf.createSite(objSiteSearchVO);

        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        } finally {
            objSiteSearchVO = null;
        }
        return strResult;
    }

    /**
     * 
     * @param
     * @return List<AddressDetailsResponseType>
     * @throws RMDServiceException
     * @Description This method is used to get the address details for the given
     *              search combination.
     * 
     */
    @GET
    @Path(OMDConstants.GET_ADDRESS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AddressDetailsResponseType> getAddress(@Context UriInfo ui)
            throws RMDServiceException {
        List<AddressDetailsResponseType> addressDetailsResponseTypeList = null;
        List<AddressDetailsVO> addressDetailsVOList = null;
        AddressDetailsResponseType addressDetailsResponseType = null;
        AddressSearchVO objAddresstSearchVO = null;
        MultivaluedMap<String, String> queryParams = null;
        try {

            queryParams = ui.getQueryParameters();
            objAddresstSearchVO = new AddressSearchVO();
            if (queryParams.containsKey(OMDConstants.ADDRESS)) {
                objAddresstSearchVO.setAddress(queryParams
                        .getFirst(OMDConstants.ADDRESS));
            }
            if (queryParams.containsKey(OMDConstants.CITY)) {
                objAddresstSearchVO.setCity(queryParams
                        .getFirst(OMDConstants.CITY));
            }
            if (queryParams.containsKey(OMDConstants.STATE)) {
                objAddresstSearchVO.setState(queryParams
                        .getFirst(OMDConstants.STATE));
            }
            if (queryParams.containsKey(OMDConstants.ZIP_CODE)) {
                objAddresstSearchVO.setZipCode(queryParams
                        .getFirst(OMDConstants.ZIP_CODE));
            }
            if (queryParams.containsKey(OMDConstants.ADDRESS_FILTER)) {
                objAddresstSearchVO.setAddrFilter(queryParams
                        .getFirst(OMDConstants.ADDRESS_FILTER));
            }
            if (queryParams.containsKey(OMDConstants.CITY_FILTER)) {
                objAddresstSearchVO.setCityFilter(queryParams
                        .getFirst(OMDConstants.CITY_FILTER));
            }
            if (queryParams.containsKey(OMDConstants.STATE_FILTER)) {
                objAddresstSearchVO.setStateFilter(queryParams
                        .getFirst(OMDConstants.STATE_FILTER));
            }
            if (queryParams.containsKey(OMDConstants.ZIPCODE_FILTER)) {
                objAddresstSearchVO.setZipCodeFilter(queryParams
                        .getFirst(OMDConstants.ZIPCODE_FILTER));
            }

            addressDetailsVOList = contactSiteServiceIntf
                    .getAddress(objAddresstSearchVO);
            if (RMDCommonUtility.isCollectionNotEmpty(addressDetailsVOList)) {
                addressDetailsResponseTypeList = new ArrayList<AddressDetailsResponseType>(
                        addressDetailsVOList.size());
                for (AddressDetailsVO objContactDetailsVO : addressDetailsVOList) {
                    addressDetailsResponseType = new AddressDetailsResponseType();
                    addressDetailsResponseType.setObjId(objContactDetailsVO
                            .getObjId());
                    addressDetailsResponseType.setAddress1(objContactDetailsVO
                            .getAddress1());
                    addressDetailsResponseType.setAddress2(objContactDetailsVO
                            .getAddress2());
                    addressDetailsResponseType.setState(objContactDetailsVO
                            .getState());
                    addressDetailsResponseType.setCity(objContactDetailsVO
                            .getCity());
                    addressDetailsResponseType.setZipCode(objContactDetailsVO
                            .getZipCode());
                    addressDetailsResponseType.setCountry(objContactDetailsVO
                            .getCountry());
                    addressDetailsResponseTypeList
                            .add(addressDetailsResponseType);
                    addressDetailsResponseType = null;

                }

            }
        } catch (Exception e) {
            LOG.error("Exception occuered in getAddress() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        } finally {
            queryParams = null;
            addressDetailsVOList = null;
        }
        return addressDetailsResponseTypeList;
    }

    /**
     * 
     * @param UriInfo
     *            ui
     * @return AddressDetailsResponseType
     * @throws RMDServiceException
     * @Description This method is used to get the all details for the selected
     *              address
     * 
     */
    @GET
    @Path(OMDConstants.VIEW_ADDRESS_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public AddressDetailsResponseType viewAddressDetails(@Context UriInfo ui)
            throws RMDServiceException {
        AddressDetailsResponseType objAddressDetailsResponseType = null;
        AddressDetailsVO objAddressDetailsVO = null;
        MultivaluedMap<String, String> queryParams = null;
        String addrObjId = OMDConstants.EMPTY_STRING;
        try {

            queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.ADDRESS_OBJID)) {
                addrObjId = queryParams.getFirst(OMDConstants.ADDRESS_OBJID);
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.ADDRESS_OBJID_NOT_PROVIDED);
            }
            objAddressDetailsVO = contactSiteServiceIntf
                    .viewAddressDetails(addrObjId);
            if (null != objAddressDetailsVO) {
                objAddressDetailsResponseType = new AddressDetailsResponseType();
                objAddressDetailsResponseType.setObjId(objAddressDetailsVO
                        .getObjId());
                objAddressDetailsResponseType.setAddress1(objAddressDetailsVO
                        .getAddress1());
                objAddressDetailsResponseType.setAddress2(objAddressDetailsVO
                        .getAddress2());
                objAddressDetailsResponseType.setCity(objAddressDetailsVO
                        .getCity());
                objAddressDetailsResponseType.setState(objAddressDetailsVO
                        .getState());
                objAddressDetailsResponseType.setCountry(objAddressDetailsVO
                        .getCountry());
                objAddressDetailsResponseType.setTimeZone(objAddressDetailsVO
                        .getTimeZone());
                objAddressDetailsResponseType.setZipCode(objAddressDetailsVO
                        .getZipCode());
            }

        } catch (Exception e) {
            LOG.error("Exception occuered in viewAddressDetails() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        } finally {
            queryParams = null;
            objAddressDetailsVO = null;
        }
        return objAddressDetailsResponseType;
    }

    /**
     * 
     * @param
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to get the all country List
     * 
     */
    @GET
    @Path(OMDConstants.GET_COUNTRY_LIST)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getCountryList() throws RMDServiceException {
        String countryString = RMDCommonConstants.EMPTY_STRING;
        try {
            countryString = contactSiteServiceIntf.getCountryList();

        } catch (Exception e) {
            LOG.error("Exception occuered in getCountryList() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return countryString;
    }

    /**
     * 
     * @param UriInfo
     *            ui
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to get the states for the selected
     *              Country
     * 
     */
    @GET
    @Path(OMDConstants.GET_COUNTRY_STATES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getCountryStates(@Context UriInfo ui)
            throws RMDServiceException {
        String stateString = RMDCommonConstants.EMPTY_STRING;
        MultivaluedMap<String, String> queryParams = null;
        String country = OMDConstants.COUNTRY;
        try {
            queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.COUNTRY)) {
                country = queryParams.getFirst(OMDConstants.COUNTRY);
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.COUNTRY_NOT_PROVIDED);
            }
            stateString = contactSiteServiceIntf.getCountryStates(country);

        } catch (Exception e) {
            LOG.error("Exception occuered in getCountryStates() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return stateString;
    }

    /**
     * 
     * @param UriInfo
     *            ui
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to get the time zones for the selected
     *              Country
     * 
     */
    @GET
    @Path(OMDConstants.GET_COUNTRY_TIMEZONES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getCountryTimeZones(@Context UriInfo ui)
            throws RMDServiceException {
        String timeZoneString = RMDCommonConstants.EMPTY_STRING;
        MultivaluedMap<String, String> queryParams = null;
        String country = OMDConstants.COUNTRY;
        try {
            queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.COUNTRY)) {
                country = queryParams.getFirst(OMDConstants.COUNTRY);
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.COUNTRY_NOT_PROVIDED);
            }
            timeZoneString = contactSiteServiceIntf
                    .getCountryTimeZones(country);

        } catch (Exception e) {
            LOG.error("Exception occuered in getCountryTimeZones() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return timeZoneString;
    }

    /**
     * 
     * @param AddEditAddressRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add or update the Address details
     * 
     */
    @POST
    @Path(OMDConstants.ADD_OR_UPDATE_ADDRESS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String addOrUpdateAddress(
            AddEditAddressRequestType objAddEditAddressRequestType)
            throws RMDServiceException {
        String status = RMDCommonConstants.FAILURE;
        AddressDetailsVO objAddressDetailsVO = null;
        try {
            if (null != objAddEditAddressRequestType) {
                objAddressDetailsVO = new AddressDetailsVO();
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType.getObjId())) {
                    objAddressDetailsVO.setObjId(objAddEditAddressRequestType
                            .getObjId());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType
                                .getAddress1())) {
                    objAddressDetailsVO
                            .setAddress1(objAddEditAddressRequestType
                                    .getAddress1());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType
                                .getAddress2())) {
                    objAddressDetailsVO
                            .setAddress2(objAddEditAddressRequestType
                                    .getAddress2());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType.getCity())) {
                    objAddressDetailsVO.setCity(objAddEditAddressRequestType
                            .getCity());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType.getState())) {
                    objAddressDetailsVO.setState(objAddEditAddressRequestType
                            .getState());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType
                                .getCountry())) {
                    objAddressDetailsVO.setCountry(objAddEditAddressRequestType
                            .getCountry());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType
                                .getZipCode())) {
                    objAddressDetailsVO.setZipCode(objAddEditAddressRequestType
                            .getZipCode());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType
                                .getTimeZone())) {
                    objAddressDetailsVO
                            .setTimeZone(objAddEditAddressRequestType
                                    .getTimeZone());
                }
                if (!RMDCommonUtility
                        .isNullOrEmpty(objAddEditAddressRequestType
                                .getFromScreen())) {
                    objAddressDetailsVO
                            .setFromScreen(objAddEditAddressRequestType
                                    .getFromScreen());
                }

            } else {
                throw new OMDInValidInputException(
                        OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }

            status = contactSiteServiceIntf
                    .addOrUpdateAddress(objAddressDetailsVO);
        } catch (Exception e) {
            status = RMDCommonConstants.FAILURE;
            LOG.error("Exception occuered in addOrUpdateAddress() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * 
     * @param
     * @return List<ISDCodeResponseType>
     * @throws RMDServiceException
     * @Description This method is used to get the all country List
     * 
     */
    @GET
    @Path(OMDConstants.GET_ISD_CODE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ISDCodeResponseType> getISDCode() throws RMDServiceException {
    	List<ISDCodeResponseType> isdCodeResponseList = null;
    	List<ISDCodeVO> isdCodeList = null;
        try {
        	isdCodeList = contactSiteServiceIntf.getISDCode();
        	if (RMDCommonUtility.isCollectionNotEmpty(isdCodeList)) {
        		isdCodeResponseList = new ArrayList<ISDCodeResponseType>();        		
        		for(ISDCodeVO isdCode : isdCodeList){
        			ISDCodeResponseType isdResp = new ISDCodeResponseType();
        			isdResp.setCountryName(isdCode.getCountry());
        			isdResp.setIsdCode(isdCode.getIsdCode());
        			isdCodeResponseList.add(isdResp);
        		}
        	}

        } catch (Exception e) {
            LOG.error("Exception occuered in getISDCode() method of ContactSiteResource"
                    + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return isdCodeResponseList;
    }
}

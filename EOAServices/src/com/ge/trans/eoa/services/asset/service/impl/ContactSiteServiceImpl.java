package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.ContactSiteBOIntf;
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
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class ContactSiteServiceImpl implements ContactSiteServiceIntf {

    private ContactSiteBOIntf objContactSiteBOIntf;

    public ContactSiteServiceImpl(ContactSiteBOIntf objContactSiteBOIntf) {
        this.objContactSiteBOIntf = objContactSiteBOIntf;
    }

    /**
     * @Author:
     * @param:objContactSearchVO
     * @return:List<ContactDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the contact details for
     *               the given search combinations
     */
    @Override
    public List<ContactDetailsVO> getContacts(ContactSearchVO objContactSearchVO)
            throws RMDServiceException {
        List<ContactDetailsVO> contactDetailsVOList = null;
        try {
            contactDetailsVOList = objContactSiteBOIntf
                    .getContacts(objContactSearchVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return contactDetailsVOList;

    }

    /**
     * 
     * @param contactObjId
     * @return ContactSiteDetailsVO
     * @throws RMDServiceException
     * @Description This method is used to get the all details for the selected
     *              contact
     * 
     */
    @Override
    public ContactSiteDetailsVO viewContactDetails(String contactObjId)
            throws RMDServiceException {
        ContactSiteDetailsVO objContactSiteDetailsVO = null;
        try {
            objContactSiteDetailsVO = objContactSiteBOIntf
                    .viewContactDetails(contactObjId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objContactSiteDetailsVO;
    }

    /**
     * 
     * @param contactObjId
     * @return List<SiteDetailsVO>
     * @throws RMDServiceException
     * @throws
     * @Description This method is used to get the secondary site details
     * 
     */
    @Override
    public List<SiteDetailsVO> getContactSecondarySites(String contactObjId)
            throws RMDServiceException {
        List<SiteDetailsVO> siteDetailsVOVOList = null;
        try {
            siteDetailsVOVOList = objContactSiteBOIntf
                    .getContactSecondarySites(contactObjId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return siteDetailsVOVOList;

    }

    /**
     * 
     * @param AddRemoveSecondarySiteVO
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add the secondary site to a contact
     * 
     */
    @Override
    public String addContactSecondarySite(
            AddRemoveSecondarySiteVO objAddRemoveSecondarySiteVO)
            throws RMDServiceException {
        String status = null;
        try {
            status = objContactSiteBOIntf
                    .addContactSecondarySite(objAddRemoveSecondarySiteVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * 
     * @param List
     *            <AddRemoveSecondarySiteVO>
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to remove the secondary sites from the
     *              contact
     */
    @Override
    public String removeContactSecondarySite(
            List<AddRemoveSecondarySiteVO> arlAddRemoveSecondarySiteVO)
            throws RMDServiceException {
        String status = null;
        try {
            status = objContactSiteBOIntf
                    .removeContactSecondarySite(arlAddRemoveSecondarySiteVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * 
     * @param ContactSiteDetailsVO
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add or update the Contact details
     * 
     */
    @Override
    public String addOrUpdateContact(
            ContactSiteDetailsVO objContactSiteDetailsVO)
            throws RMDServiceException {
        String status = null;
        try {
            status = objContactSiteBOIntf
                    .addOrUpdateContact(objContactSiteDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    @Override
    public List<SiteSearchVO> getSites(SiteSearchVO objSiteSearchVO)
            throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objContactSiteBOIntf.getSites(objSiteSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arlSearchResults;
    }

    @Override
    public SiteSearchVO viewSiteDetails(String siteObjId)
            throws RMDServiceException {
        SiteSearchVO arlSearchResults = null;
        try {
            arlSearchResults = objContactSiteBOIntf.viewSiteDetails(siteObjId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arlSearchResults;
    }

    @Override
    public String updateSite(SiteSearchVO objSiteSearchVO)
            throws RMDServiceException {
        String arlSearchResults = null;
        try {
            arlSearchResults = objContactSiteBOIntf.updateSite(objSiteSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arlSearchResults;
    }

    @Override
    public String createSite(SiteSearchVO objSiteSearchVO)
            throws RMDServiceException {
        String arlSearchResults = null;
        try {
            arlSearchResults = objContactSiteBOIntf.createSite(objSiteSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arlSearchResults;
    }

    /**
     * 
     * @param
     * @return List<AddressDetailsVO>
     * @throws RMDServiceException
     * @Description This method is used to get the address details for the given
     *              search combination.
     * 
     */
    @Override
    public List<AddressDetailsVO> getAddress(AddressSearchVO objAddresstSearchVO)
            throws RMDServiceException {
        List<AddressDetailsVO> addressDetailsVOList = null;
        try {
            addressDetailsVOList = objContactSiteBOIntf
                    .getAddress(objAddresstSearchVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return addressDetailsVOList;
    }

    /**
     * 
     * @param addrObjId
     * @return AddressDetailsVO
     * @throws RMDServiceException
     * @Description This method is used to get the all details for the selected
     *              address
     * 
     */
    @Override
    public AddressDetailsVO viewAddressDetails(String addrObjId)
            throws RMDServiceException {
        AddressDetailsVO objAddressDetailsVO = null;
        try {
            objAddressDetailsVO = objContactSiteBOIntf
                    .viewAddressDetails(addrObjId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objAddressDetailsVO;
    }

    /**
     * 
     * @param
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to get the all country List
     * 
     */
    @Override
    public String getCountryList() throws RMDServiceException {
        String countryString = RMDCommonConstants.EMPTY_STRING;
        try {
            countryString = objContactSiteBOIntf.getCountryList();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return countryString;
    }

    /**
     * 
     * @param String
     *            country
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to get the states for the selected
     *              Country
     * 
     */
    @Override
    public String getCountryStates(String country) throws RMDServiceException {
        String stateString = RMDCommonConstants.EMPTY_STRING;
        try {
            stateString = objContactSiteBOIntf.getCountryStates(country);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return stateString;
    }

    /**
     * 
     * @param String
     *            country
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to get the time zones for the selected
     *              Country
     * 
     */
    @Override
    public String getCountryTimeZones(String country)
            throws RMDServiceException {
        String timeZoneString = RMDCommonConstants.EMPTY_STRING;
        try {
            timeZoneString = objContactSiteBOIntf.getCountryTimeZones(country);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return timeZoneString;
    }

    /**
     * 
     * @param AddressDetailsVO
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add or update the Address details
     * 
     */
    @Override
    public String addOrUpdateAddress(AddressDetailsVO objAddressDetailsVO)
            throws RMDServiceException {
        String status = null;
        try {
            status = objContactSiteBOIntf
                    .addOrUpdateAddress(objAddressDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * 
     * @param
     * @return List<ISDCodeVO>
     * @throws RMDServiceException
     * @Description This method is used to get ISD Code list
     * 
     */
    @Override
    public List<ISDCodeVO> getISDCode() throws RMDServiceException {
    	List<ISDCodeVO> isdCodeMap = null;
        try {
        	isdCodeMap = objContactSiteBOIntf.getISDCode();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return isdCodeMap;
    }
}

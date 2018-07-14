package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.ContactSiteBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.ContactSiteDAOIntf;
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

public class ContactSiteBOImpl implements ContactSiteBOIntf {

    private ContactSiteDAOIntf objContactSiteDAOIntf;

    public ContactSiteBOImpl(ContactSiteDAOIntf objContactSiteDAOIntf) {
        this.objContactSiteDAOIntf = objContactSiteDAOIntf;
    }

    /**
     * @Author:
     * @param:objContactSearchVO
     * @return:List<ContactDetailsVO>
     * @throws:RMDBOException
     * @Description: This method is used for fetching the contact details for
     *               the given search combinations
     */
    @Override
    public List<ContactDetailsVO> getContacts(ContactSearchVO objContactSearchVO)
            throws RMDBOException {
        List<ContactDetailsVO> contactDetailsVOList = null;
        try {
            contactDetailsVOList = objContactSiteDAOIntf
                    .getContacts(objContactSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return contactDetailsVOList;
    }

    /**
     * 
     * @param contactObjId
     * @return ContactSiteDetailsVO
     * @throws RMDBOException
     * @Description This method is used to get the all details for the selected
     *              contact
     * 
     */
    @Override
    public ContactSiteDetailsVO viewContactDetails(String contactObjId)
            throws RMDBOException {
        ContactSiteDetailsVO objContactSiteDetailsVO = null;
        try {
            objContactSiteDetailsVO = objContactSiteDAOIntf
                    .viewContactDetails(contactObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return objContactSiteDetailsVO;
    }

    /**
     * 
     * @param contactObjId
     * @return List<SiteDetailsVO>
     * @throws RMDBOException
     * @throws
     * @Description This method is used to get the secondary site details
     * 
     */
    @Override
    public List<SiteDetailsVO> getContactSecondarySites(String contactObjId)
            throws RMDBOException {
        List<SiteDetailsVO> siteDetailsVOList = null;
        try {
            siteDetailsVOList = objContactSiteDAOIntf
                    .getContactSecondarySites(contactObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return siteDetailsVOList;
    }

    /**
     * 
     * @param AddRemoveSecondarySiteVO
     * @return String
     * @throws RMDBOException
     * @Description This method is used to add the secondary site to a contact
     * 
     */
    public String addContactSecondarySite(
            AddRemoveSecondarySiteVO objAddRemoveSecondarySiteVO)
            throws RMDBOException {
        String status = null;
        try {
            status = objContactSiteDAOIntf
                    .addContactSecondarySite(objAddRemoveSecondarySiteVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * 
     * @param List
     *            <AddRemoveSecondarySiteVO>
     * @return String
     * @throws RMDBOException
     * @Description This method is used to remove the secondary sites from the
     *              contact
     */
    @Override
    public String removeContactSecondarySite(
            List<AddRemoveSecondarySiteVO> arlAddRemoveSecondarySiteVO)
            throws RMDBOException {
        String status = null;
        try {
            status = objContactSiteDAOIntf
                    .removeContactSecondarySite(arlAddRemoveSecondarySiteVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * 
     * @param ContactSiteDetailsVO
     * @return String
     * @throws RMDBOException
     * @Description This method is used to add or update the Contact details
     * 
     */
    @Override
    public String addOrUpdateContact(
            ContactSiteDetailsVO objContactSiteDetailsVO) throws RMDBOException {
        String status = null;
        try {
            status = objContactSiteDAOIntf
                    .addOrUpdateContact(objContactSiteDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    @Override
    public List getSites(SiteSearchVO objSiteSearchVO)
            throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objContactSiteDAOIntf.getSites(objSiteSearchVO);
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
            arlSearchResults = objContactSiteDAOIntf.viewSiteDetails(siteObjId);
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
            arlSearchResults = objContactSiteDAOIntf
                    .updateSite(objSiteSearchVO);
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
            arlSearchResults = objContactSiteDAOIntf
                    .createSite(objSiteSearchVO);
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
     * @throws RMDBOException
     * @Description This method is used to get the address details for the given
     *              search combination.
     * 
     */
    @Override
    public List<AddressDetailsVO> getAddress(AddressSearchVO objAddresstSearchVO)
            throws RMDBOException {
        List<AddressDetailsVO> addressDetailsVOList = null;
        try {
            addressDetailsVOList = objContactSiteDAOIntf
                    .getAddress(objAddresstSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return addressDetailsVOList;
    }

    /**
     * 
     * @param addrObjId
     * @return AddressDetailsVO
     * @throws RMDBOException
     * @Description This method is used to get the all details for the selected
     *              address
     * 
     */
    @Override
    public AddressDetailsVO viewAddressDetails(String addrObjId)
            throws RMDBOException {
        AddressDetailsVO objAddressDetailsVO = null;
        try {
            objAddressDetailsVO = objContactSiteDAOIntf
                    .viewAddressDetails(addrObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return objAddressDetailsVO;
    }

    /**
     * 
     * @param
     * @return String
     * @throws RMDBOException
     * @Description This method is used to get the all country List
     * 
     */
    @Override
    public String getCountryList() throws RMDBOException {
        String countryString = RMDCommonConstants.EMPTY_STRING;
        try {
            countryString = objContactSiteDAOIntf.getCountryList();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return countryString;
    }

    /**
     * 
     * @param String
     *            country
     * @return String
     * @throws RMDBOException
     * @Description This method is used to get the states for the selected
     *              Country
     * 
     */
    @Override
    public String getCountryStates(String country) throws RMDBOException {
        String stateString = RMDCommonConstants.EMPTY_STRING;
        try {
            stateString = objContactSiteDAOIntf.getCountryStates(country);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return stateString;
    }

    /**
     * 
     * @param String
     *            country
     * @return String
     * @throws RMDBOException
     * @Description This method is used to get the time zones for the selected
     *              Country
     * 
     */
    @Override
    public String getCountryTimeZones(String country) throws RMDBOException {
        String timeZoneString = RMDCommonConstants.EMPTY_STRING;
        try {
            timeZoneString = objContactSiteDAOIntf.getCountryTimeZones(country);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return timeZoneString;
    }

    /**
     * 
     * @param AddressDetailsVO
     * @return String
     * @throws RMDBOException
     * @Description This method is used to add or update the Address details
     * 
     */
    @Override
    public String addOrUpdateAddress(AddressDetailsVO objAddressDetailsVO)
            throws RMDBOException {
        String status = null;
        try {
            status = objContactSiteDAOIntf
                    .addOrUpdateAddress(objAddressDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * 
     * @param
     * @return Map<String, String>
     * @throws RMDBOException
     * @Description This method is used to get ISD Code list
     * 
     */
    @Override
    public List<ISDCodeVO> getISDCode() throws RMDBOException {
    	List<ISDCodeVO> isdCodeMap = null;
        try {
        	isdCodeMap = objContactSiteDAOIntf.getISDCode();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return isdCodeMap;
    }
}

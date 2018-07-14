package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.AddRemoveSecondarySiteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddressDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddressSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ContactDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ContactSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ContactSiteDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ISDCodeVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.SiteDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.SiteSearchVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface ContactSiteServiceIntf {

    /**
     * @Author:
     * @param:objContactSearchVO
     * @return:List<ContactDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the contact details for
     *               the given search combinations
     */
    public List<ContactDetailsVO> getContacts(ContactSearchVO objContactSearchVO)
            throws RMDServiceException;

    /**
     * 
     * @param contactObjId
     * @return ContactSiteDetailsVO
     * @throws RMDServiceException
     * @Description This method is used to get the all details for the selected
     *              contact
     * 
     */
    public ContactSiteDetailsVO viewContactDetails(String contactObjId)
            throws RMDServiceException;

    /**
     * 
     * @param contactObjId
     * @return List<SiteDetailsVO>
     * @throws RMDServiceException
     * @Description This method is used to get the secondary site details
     * 
     */
    public List<SiteDetailsVO> getContactSecondarySites(String contactObjId)
            throws RMDServiceException;

    /**
     * 
     * @param AddRemoveSecondarySiteVO
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add the secondary site to a contact
     * 
     */
    public String addContactSecondarySite(
            AddRemoveSecondarySiteVO objAddRemoveSecondarySiteVO)
            throws RMDServiceException;

    /**
     * 
     * @param List
     *            <AddRemoveSecondarySiteVO>
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to remove the secondary sites from the
     *              contact
     */
    public String removeContactSecondarySite(
            List<AddRemoveSecondarySiteVO> arlAddRemoveSecondarySiteVO)
            throws RMDServiceException;

    /**
     * 
     * @param ContactSiteDetailsVO
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add or update the Contact details
     * 
     */
    public String addOrUpdateContact(
            ContactSiteDetailsVO objContactSiteDetailsVO)
            throws RMDServiceException;

    public List<SiteSearchVO> getSites(SiteSearchVO objSiteSearchVO)
            throws RMDServiceException;

    public SiteSearchVO viewSiteDetails(String siteObjId)
            throws RMDServiceException;

    public String updateSite(SiteSearchVO objSiteSearchVO)
            throws RMDServiceException;

    public String createSite(SiteSearchVO objSiteSearchVO)
            throws RMDServiceException;

    /**
     * 
     * @param
     * @return List<AddressDetailsVO>
     * @throws RMDServiceException
     * @Description This method is used to get the address details for the given
     *              search combination.
     * 
     */
    public List<AddressDetailsVO> getAddress(AddressSearchVO objAddresstSearchVO)
            throws RMDServiceException;

    /**
     * 
     * @param addrObjId
     * @return AddressDetailsVO
     * @throws RMDServiceException
     * @Description This method is used to get the all details for the selected
     *              address
     * 
     */
    public AddressDetailsVO viewAddressDetails(String addrObjId)
            throws RMDServiceException;

    /**
     * 
     * @param
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to get the all country List
     * 
     */
    public String getCountryList() throws RMDServiceException;

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
    public String getCountryStates(String country) throws RMDServiceException;

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
    public String getCountryTimeZones(String country)
            throws RMDServiceException;

    /**
     * 
     * @param AddressDetailsVO
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to add or update the Address details
     * 
     */
    public String addOrUpdateAddress(AddressDetailsVO objAddressDetailsVO)
            throws RMDServiceException;
    
    /**
     * 
     * @param 
     * @return List<ISDCodeVO>
     * @throws RMDServiceException
     * @Description This method is used to get ISD Code list
     * 
     */
	List<ISDCodeVO> getISDCode() throws RMDServiceException;
}

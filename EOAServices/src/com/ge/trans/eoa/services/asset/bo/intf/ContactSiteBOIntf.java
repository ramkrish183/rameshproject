package com.ge.trans.eoa.services.asset.bo.intf;

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
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface ContactSiteBOIntf {

    /**
     * @Author:
     * @param:objContactSearchVO
     * @return:List<ContactDetailsVO>
     * @throws:RMDBOException
     * @Description: This method is used for fetching the contact details for
     *               the given search combinations
     */
    public List<ContactDetailsVO> getContacts(ContactSearchVO objContactSearchVO)
            throws RMDBOException;

    /**
     * 
     * @param contactObjId
     * @return ContactSiteDetailsVO
     * @throws RMDBOException
     * @Description This method is used to get the all details for the selected
     *              contact
     * 
     */
    public ContactSiteDetailsVO viewContactDetails(String contactObjId)
            throws RMDBOException;

    /**
     * 
     * @param contactObjId
     * @return List<SiteDetailsVO>
     * @throws RMDBOException
     * @throws
     * @Description This method is used to get the secondary site details
     * 
     */
    public List<SiteDetailsVO> getContactSecondarySites(String contactObjId)
            throws RMDBOException;

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
            throws RMDBOException;

    /**
     * 
     * @param List
     *            <AddRemoveSecondarySiteVO>
     * @return String
     * @throws RMDBOException
     * @Description This method is used to remove the secondary sites from the
     *              contact
     */
    public String removeContactSecondarySite(
            List<AddRemoveSecondarySiteVO> arlAddRemoveSecondarySiteVO)
            throws RMDBOException;

    /**
     * 
     * @param ContactSiteDetailsVO
     * @return String
     * @throws RMDBOException
     * @Description This method is used to update the existing Contact details
     * 
     */
    public String addOrUpdateContact(
            ContactSiteDetailsVO objContactSiteDetailsVO) throws RMDBOException;

    List getSites(SiteSearchVO objSiteSearchVO) throws RMDServiceException;

    SiteSearchVO viewSiteDetails(String siteObjId) throws RMDServiceException;

    String updateSite(SiteSearchVO objSiteSearchVO) throws RMDServiceException;

    String createSite(SiteSearchVO objSiteSearchVO) throws RMDServiceException;

    /**
     * 
     * @param
     * @return List<AddressDetailsVO>
     * @throws RMDBOException
     * @Description This method is used to get the address details for the given
     *              search combination.
     * 
     */
    public List<AddressDetailsVO> getAddress(AddressSearchVO objAddresstSearchVO)
            throws RMDBOException;

    /**
     * 
     * @param addrObjId
     * @return AddressDetailsVO
     * @throws RMDBOException
     * @Description This method is used to get the all details for the selected
     *              address
     * 
     */
    public AddressDetailsVO viewAddressDetails(String addrObjId)
            throws RMDBOException;

    /**
     * 
     * @param
     * @return String
     * @throws RMDBOException
     * @Description This method is used to get the all country List
     * 
     */
    public String getCountryList() throws RMDBOException;

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
    public String getCountryStates(String country) throws RMDBOException;

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
    public String getCountryTimeZones(String country) throws RMDBOException;

    /**
     * 
     * @param AddressDetailsVO
     * @return String
     * @throws RMDBOException
     * @Description This method is used to add or update the Address details
     * 
     */
    public String addOrUpdateAddress(AddressDetailsVO objAddressDetailsVO)
            throws RMDBOException;

    /**
     * 
     * @param
     * @return List<ISDCodeVO>
     * @throws RMDBOException
     * @Description This method is used to get ISD Code list
     * 
     */
	List<ISDCodeVO> getISDCode() throws RMDBOException;

}

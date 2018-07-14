package com.ge.trans.eoa.services.asset.dao.intf;

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
import com.ge.trans.rmd.exception.RMDDAOException;

public interface ContactSiteDAOIntf {

    /**
     * @Author:
     * @param:objContactSearchVO
     * @return:List<ContactDetailsResponseType>
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the contact details for
     *               the given search combinations
     */
    public List<ContactDetailsVO> getContacts(ContactSearchVO objContactSearchVO)
            throws RMDDAOException;

    /**
     * 
     * @param contactObjId
     * @return ContactSiteDetailsVO
     * @throws RMDDAOException
     * @Description This method is used to get the all details for the selected
     *              contact
     * 
     */
    public ContactSiteDetailsVO viewContactDetails(String contactObjId)
            throws RMDDAOException;

    /**
     * 
     * @param contactObjId
     * @return List<SiteDetailsVO>
     * @throws RMDDAOException
     * @throws
     * @Description This method is used to get the secondary site details
     * 
     */
    public List<SiteDetailsVO> getContactSecondarySites(String contactObjId)
            throws RMDDAOException;

    /**
     * 
     * @param AddRemoveSecondarySiteVO
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to add the secondary site to a contact
     * 
     */
    public String addContactSecondarySite(
            AddRemoveSecondarySiteVO objAddRemoveSecondarySiteVO)
            throws RMDDAOException;

    /**
     * 
     * @param List
     *            <AddRemoveSecondarySiteVO>
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to remove the secondary sites from the
     *              contact
     */
    public String removeContactSecondarySite(
            List<AddRemoveSecondarySiteVO> arlAddRemoveSecondarySiteVO)
            throws RMDDAOException;

    /**
     * 
     * @param ContactSiteDetailsVO
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to add or update the Contact details
     * 
     */
    public String addOrUpdateContact(
            ContactSiteDetailsVO objContactSiteDetailsVO)
            throws RMDDAOException;

    List getSites(SiteSearchVO objSiteSearchVO) throws RMDDAOException;

    SiteSearchVO viewSiteDetails(String siteObjId) throws RMDDAOException;

    String updateSite(SiteSearchVO objSiteSearchVO) throws RMDDAOException;

    String createSite(SiteSearchVO objSiteSearchVO) throws RMDDAOException;

    /**
     * 
     * @param
     * @return List<AddressDetailsVO>
     * @throws RMDDAOException
     * @Description This method is used to get the address details for the given
     *              search combination.
     * 
     */
    public List<AddressDetailsVO> getAddress(AddressSearchVO objAddresstSearchVO)
            throws RMDDAOException;

    /**
     * 
     * @param addrObjId
     * @return AddressDetailsVO
     * @throws RMDDAOException
     * @Description This method is used to get the all details for the selected
     *              address
     * 
     */
    public AddressDetailsVO viewAddressDetails(String addrObjId)
            throws RMDDAOException;

    /**
     * 
     * @param
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to get the all country List
     * 
     */
    public String getCountryList() throws RMDDAOException;

    /**
     * 
     * @param String
     *            country
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to get the states for the selected
     *              Country
     * 
     */
    public String getCountryStates(String country) throws RMDDAOException;

    /**
     * 
     * @param String
     *            country
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to get the time zones for the selected
     *              Country
     * 
     */
    public String getCountryTimeZones(String country) throws RMDDAOException;

    /**
     * 
     * @param AddressDetailsVO
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to add or update the Address details
     * 
     */
    public String addOrUpdateAddress(AddressDetailsVO objAddressDetailsVO)
            throws RMDDAOException;

    /**
     * 
     * @param
     * @return List<ISDCodeVO>
     * @throws RMDDAOException
     * @Description This method is used to get ISD Code list
     * 
     */
	List<ISDCodeVO> getISDCode() throws RMDDAOException;

}

package com.ge.trans.eoa.services.asset.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class ContactSiteDAOImpl extends BaseDAO implements ContactSiteDAOIntf {

    public static final RMDLogger LOG = RMDLogger
            .getLogger(NotesEoaDAOImpl.class);

    /**
     * @Author:
     * @param:objContactSearchVO
     * @return:List<ContactDetailsResponseType>
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the contact details for
     *               the given search combinations
     */
    @Override
    public List<ContactDetailsVO> getContacts(ContactSearchVO objContactSearchVO)
            throws RMDDAOException {
        Session session = null;
        StringBuilder contactQuery = new StringBuilder();
        String whereClause = RMDCommonConstants.EMPTY_STRING;
        List<ContactDetailsVO> contactDetailsVOList = null;
        ContactDetailsVO objContactDetailsVO = null;
        List<Object> resultList = null;
        try {
            session = getHibernateSession();
            contactQuery
                    .append("SELECT FIRST_NAME,LAST_NAME,PHONE,SITE,CITY,COUNTRY,ROLE_NAME,CON_OBJID FROM TABLE_ROL_CONTCT WHERE");
            if (null != objContactSearchVO.getFirstName()) {
                whereClause = whereClause
                        + " LOWER(FIRST_NAME) LIKE LOWER(:firstName)";
            }
            if (null != objContactSearchVO.getLastName()) {
                if (!whereClause
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    whereClause = whereClause + " AND";
                }
                whereClause = whereClause
                        + " LOWER(LAST_NAME) LIKE LOWER(:lastName)";
            }
            if (null != objContactSearchVO.getSiteId()) {
                if (!whereClause
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    whereClause = whereClause + " AND";
                }
                whereClause = whereClause
                        + " LOWER(SITE_ID) LIKE LOWER(:siteId)";
            }
            if (null != objContactSearchVO.getSiteName()) {
                if (!whereClause
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    whereClause = whereClause + " AND";
                }
                whereClause = whereClause
                        + " LOWER(SITE) LIKE LOWER(:siteName)";
            }
            if (null != objContactSearchVO.getPhNo()) {
                if (!whereClause
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    whereClause = whereClause + " AND";
                }
                whereClause = whereClause + " PHONE LIKE :phNo";
            }
            if (null != objContactSearchVO.getContactStatus()) {
                if (!whereClause
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    whereClause = whereClause + " AND";
                }
                if (objContactSearchVO.getContactStatus().equalsIgnoreCase(
                        RMDCommonConstants.ZERO_STRING))
                    whereClause = whereClause + " STATUS = 0";
                else
                    whereClause = whereClause + " STATUS <= 1";
            }
            contactQuery.append(whereClause);
            contactQuery.append(" ORDER BY FIRST_NAME,LAST_NAME");
            final Query contactHQuery = session.createSQLQuery(contactQuery
                    .toString());
            if (null != objContactSearchVO.getFirstName()) {
                contactHQuery.setParameter(
                        RMDCommonConstants.FIRST_NAME,
                        RMDServiceConstants.PERCENTAGE
                                + objContactSearchVO.getFirstName()
                                + RMDServiceConstants.PERCENTAGE);
            }
            if (null != objContactSearchVO.getLastName()) {
                contactHQuery.setParameter(
                        RMDCommonConstants.LAST_NAME,
                        RMDServiceConstants.PERCENTAGE
                                + objContactSearchVO.getLastName()
                                + RMDServiceConstants.PERCENTAGE);
            }
            if (null != objContactSearchVO.getSiteId()) {
                contactHQuery.setParameter(
                        RMDCommonConstants.SITE_ID,
                        RMDServiceConstants.PERCENTAGE
                                + objContactSearchVO.getSiteId()
                                + RMDServiceConstants.PERCENTAGE);
            }
            if (null != objContactSearchVO.getSiteName()) {
                contactHQuery.setParameter(
                        RMDCommonConstants.SITE_NAME,
                        RMDServiceConstants.PERCENTAGE
                                + objContactSearchVO.getSiteName()
                                + RMDServiceConstants.PERCENTAGE);
            }
            if (null != objContactSearchVO.getPhNo()) {
                contactHQuery.setParameter(
                        RMDCommonConstants.PH_NO,
                        RMDServiceConstants.PERCENTAGE
                                + objContactSearchVO.getPhNo()
                                + RMDServiceConstants.PERCENTAGE);
            }
            resultList = contactHQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                contactDetailsVOList = new ArrayList<ContactDetailsVO>(
                        resultList.size());
                for (final Iterator<Object> obj = resultList.iterator(); obj
                        .hasNext();) {
                    final Object[] contactDetails = (Object[]) obj.next();
                    objContactDetailsVO = new ContactDetailsVO();
                    objContactDetailsVO.setFirstName(RMDCommonUtility
                            .convertObjectToString(contactDetails[0]));
                    objContactDetailsVO.setLastName(RMDCommonUtility
                            .convertObjectToString(contactDetails[1]));
                    objContactDetailsVO.setPhNo(RMDCommonUtility
                            .convertObjectToString(contactDetails[2]));
                    objContactDetailsVO.setSiteName(RMDCommonUtility
                            .convertObjectToString(contactDetails[3]));
                    objContactDetailsVO.setCity(RMDCommonUtility
                            .convertObjectToString(contactDetails[4]));
                    objContactDetailsVO.setCountry(RMDCommonUtility
                            .convertObjectToString(contactDetails[5]));
                    objContactDetailsVO.setContactRole(RMDCommonUtility
                            .convertObjectToString(contactDetails[6]));
                    objContactDetailsVO.setContactObjId(RMDCommonUtility
                            .convertObjectToString(contactDetails[7]));
                    contactDetailsVOList.add(objContactDetailsVO);
                    objContactDetailsVO = null;
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTACTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTACTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);
            resultList = null;
            contactQuery = null;

        }
        return contactDetailsVOList;
    }

    /**
     * 
     * @param contactObjId
     * @return ContactSiteDetailsVO
     * @throws RMDDAOException
     * @Description This method is used to get the all details for the selected
     *              contact
     * 
     */
    @Override
    public ContactSiteDetailsVO viewContactDetails(String contactObjId)
            throws RMDDAOException {
        Session session = null;
        StringBuilder viewContactQuery = new StringBuilder();
        ContactSiteDetailsVO objContactSiteDetailsVO = null;
        List<Object> resultList = null;
        try {
            session = getHibernateSession();
            viewContactQuery
                    .append("SELECT CON_OBJID, CON.TITLE, CON.STATUS, FIRST_NAME, LAST_NAME, PHONE, FAX_NUMBER, E_MAIL, SITE.SITE_ID, CON.SITE, CON.SITE_TYPE,");
            viewContactQuery
                    .append(" SITE.ADDRESS, SITE.ADDRESS_2, SITE.CITY, SITE.STATE, SITE.COUNTRY_NAME, SITE.ZIPCODE, SALUTATION, CON.ROLE_NAME, X_DIAL_COMM, X_HOME_PHONE,");
            viewContactQuery
                    .append(" X_CELL_PHONE,TME.NAME, X_VOICE_MAIL,LOC_OBJID FROM TABLE_ROL_CONTCT CON, TABLE_SITE_VIEW SITE, TABLE_ADDRESS ADDR, TABLE_TIME_ZONE TME ");
            viewContactQuery
                    .append(" WHERE CON.ADDR_OBJID=ADDR.OBJID AND SITE.ADDRESS=ADDR.ADDRESS AND ADDR.ADDRESS2TIME_ZONE=TME.OBJID AND SITE.SITE_TYPE =CON.SITE_TYPE");
            viewContactQuery
                    .append(" AND CON.PRIMARY_SITE=1 AND CON.CON_OBJID=:contactObjId AND SITE.OBJID=(SELECT CONTACT_ROLE2SITE FROM TABLE_CONTACT_ROLE WHERE PRIMARY_SITE=1");
            viewContactQuery
                    .append(" AND CONTACT_ROLE2CONTACT=:contactObjId )");
            final Query viewContactHQuery = session
                    .createSQLQuery(viewContactQuery.toString());
            viewContactHQuery.setParameter(RMDCommonConstants.CONTACT_OBJID,
                    contactObjId);
            resultList = viewContactHQuery.list();
            if (!resultList.isEmpty()) {
                final Object[] contactDetails = (Object[]) resultList.get(0);
                if (null != contactDetails) {
                    objContactSiteDetailsVO = new ContactSiteDetailsVO();
                    objContactSiteDetailsVO.setContactObjId(RMDCommonUtility
                            .convertObjectToString(contactDetails[0]));
                    objContactSiteDetailsVO.setJobTitle(RMDCommonUtility
                            .convertObjectToString(contactDetails[1]));
                    objContactSiteDetailsVO.setContactStatus(RMDCommonUtility
                            .convertObjectToString(contactDetails[2]));
                    objContactSiteDetailsVO.setFirstName(RMDCommonUtility
                            .convertObjectToString(contactDetails[3]));
                    objContactSiteDetailsVO.setLastName(RMDCommonUtility
                            .convertObjectToString(contactDetails[4]));
                    objContactSiteDetailsVO.setPhNo(RMDCommonUtility
                            .convertObjectToString(contactDetails[5]));
                    objContactSiteDetailsVO.setFax(RMDCommonUtility
                            .convertObjectToString(contactDetails[6]));
                    objContactSiteDetailsVO.setEmailId(RMDCommonUtility
                            .convertObjectToString(contactDetails[7]));
                    objContactSiteDetailsVO.setSiteId(RMDCommonUtility
                            .convertObjectToString(contactDetails[8]));
                    objContactSiteDetailsVO.setSiteName(RMDCommonUtility
                            .convertObjectToString(contactDetails[9]));
                    objContactSiteDetailsVO.setSiteType(RMDCommonUtility
                            .convertObjectToString(contactDetails[10]));
                    objContactSiteDetailsVO.setAddress1(RMDCommonUtility
                            .convertObjectToString(contactDetails[11]));
                    objContactSiteDetailsVO.setAddress2(RMDCommonUtility
                            .convertObjectToString(contactDetails[12]));
                    objContactSiteDetailsVO.setCity(RMDCommonUtility
                            .convertObjectToString(contactDetails[13]));
                    objContactSiteDetailsVO.setState(RMDCommonUtility
                            .convertObjectToString(contactDetails[14]));
                    objContactSiteDetailsVO.setCountry(RMDCommonUtility
                            .convertObjectToString(contactDetails[15]));
                    objContactSiteDetailsVO.setZipCode(RMDCommonUtility
                            .convertObjectToString(contactDetails[16]));
                    objContactSiteDetailsVO.setSalutation(RMDCommonUtility
                            .convertObjectToString(contactDetails[17]));
                    objContactSiteDetailsVO.setContactRole(RMDCommonUtility
                            .convertObjectToString(contactDetails[18]));
                    objContactSiteDetailsVO.setDailComm(RMDCommonUtility
                            .convertObjectToString(contactDetails[19]));
                    objContactSiteDetailsVO.setHomePh(RMDCommonUtility
                            .convertObjectToString(contactDetails[20]));
                    objContactSiteDetailsVO.setCellPh(RMDCommonUtility
                            .convertObjectToString(contactDetails[21]));
                    objContactSiteDetailsVO.setTimeZone(RMDCommonUtility
                            .convertObjectToString(contactDetails[22]));
                    objContactSiteDetailsVO.setVoiceMail(RMDCommonUtility
                            .convertObjectToString(contactDetails[23]));
                    objContactSiteDetailsVO.setLocObjId(RMDCommonUtility
                            .convertObjectToString(contactDetails[24]));

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VIEW_CONTACT_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VIEW_CONTACT_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            resultList = null;
            viewContactQuery = null;

        }
        return objContactSiteDetailsVO;
    }

    /**
     * 
     * @param contactObjId
     * @return List<SiteDetailsVO>
     * @throws RMDDAOException
     * @throws
     * @Description This method is used to get the secondary site details
     * 
     */
    @Override
    public List<SiteDetailsVO> getContactSecondarySites(String contactObjId)
            throws RMDDAOException {
        Session session = null;
        StringBuilder secSiteQuery = null;
        List<SiteDetailsVO> siteDetailsVOList = null;
        SiteDetailsVO objSiteDetailsVO = null;
        List<Object> resultList = null;
        try {
            session = getHibernateSession();
            secSiteQuery = new StringBuilder();
            secSiteQuery
                    .append("SELECT SITE.OBJID ,ROLE_NAME,SITE_ID,SITE_NAME,ADDRESS,CITY,STATE,ZIPCODE FROM TABLE_SITE_VIEW SITE,TABLE_CONTACT_ROLE CON ");
            secSiteQuery
                    .append("WHERE CON.CONTACT_ROLE2SITE=SITE.OBJID AND CONTACT_ROLE2CONTACT=:contactObjId AND PRIMARY_SITE=2");
            final Query secSiteHQuery = session.createSQLQuery(secSiteQuery
                    .toString());
            secSiteHQuery.setParameter(RMDCommonConstants.CONTACT_OBJID,
                    contactObjId);
            resultList = secSiteHQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                siteDetailsVOList = new ArrayList<SiteDetailsVO>(
                        resultList.size());
                for (final Iterator<Object> obj = resultList.iterator(); obj
                        .hasNext();) {
                    final Object[] siteDetails = (Object[]) obj.next();
                    objSiteDetailsVO = new SiteDetailsVO();
                    objSiteDetailsVO.setSiteObjId(RMDCommonUtility
                            .convertObjectToString(siteDetails[0]));
                    objSiteDetailsVO.setContactRole(RMDCommonUtility
                            .convertObjectToString(siteDetails[1]));
                    objSiteDetailsVO.setSiteId(RMDCommonUtility
                            .convertObjectToString(siteDetails[2]));
                    objSiteDetailsVO.setSiteName(RMDCommonUtility
                            .convertObjectToString(siteDetails[3]));
                    objSiteDetailsVO.setAddress(RMDCommonUtility
                            .convertObjectToString(siteDetails[4]));
                    objSiteDetailsVO.setCity(RMDCommonUtility
                            .convertObjectToString(siteDetails[5]));
                    objSiteDetailsVO.setState(RMDCommonUtility
                            .convertObjectToString(siteDetails[6]));
                    objSiteDetailsVO.setZipCode(RMDCommonUtility
                            .convertObjectToString(siteDetails[7]));
                    siteDetailsVOList.add(objSiteDetailsVO);
                    objSiteDetailsVO = null;
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTACT_SECONDARY_SITE_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTACT_SECONDARY_SITE_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            resultList = null;
            secSiteQuery = null;

        }
        return siteDetailsVOList;

    }

    /**
     * 
     * @param AddRemoveSecondarySiteVO
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to add the secondary site to a contact
     * 
     */
    @Override
    public String addContactSecondarySite(
            AddRemoveSecondarySiteVO objAddRemoveSecondarySiteVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        String dupSiteOrRole = RMDCommonConstants.EMPTY_STRING;
        String fromScreen = RMDCommonConstants.SEC_SITE_SCREEN;
        boolean roleChanged = RMDCommonConstants.FALSE;
        boolean siteChanged = RMDCommonConstants.FALSE;
        Session session = null;
        StringBuilder addSecSiteQuery = null;
        try {

            dupSiteOrRole = isDuplicateSiteOrRole(
                    objAddRemoveSecondarySiteVO.getContactObjId(),
                    objAddRemoveSecondarySiteVO.getSiteObjId(),
                    objAddRemoveSecondarySiteVO.getContactRole(), roleChanged,
                    siteChanged, fromScreen);
            if (!RMDCommonUtility.isNullOrEmpty(dupSiteOrRole)
                    && !dupSiteOrRole
                            .equalsIgnoreCase(RMDCommonConstants.NO_DUPLICATES)) {
                result = dupSiteOrRole;
            } else {
                session = getHibernateSession();
                addSecSiteQuery = new StringBuilder();
                addSecSiteQuery
                        .append("INSERT INTO TABLE_CONTACT_ROLE (OBJID,ROLE_NAME,S_ROLE_NAME,PRIMARY_SITE,DEV,UPDATE_STAMP,CONTACT_ROLE2SITE,CONTACT_ROLE2CONTACT,");
                addSecSiteQuery
                        .append("CONTACT_ROLE2GBST_ELM) values ((select max(objid)+1 from table_contact_role),:contactRole,upper(:contactRole),:secondarySiteId,");
                addSecSiteQuery
                        .append("null,SYSDATE,:siteObjId,:contactObjId,:role2GBSTELM)");
                final Query addSecSiteHQuery = session
                        .createSQLQuery(addSecSiteQuery.toString());
                addSecSiteHQuery.setParameter(RMDCommonConstants.CONTACT_ROLE,
                        objAddRemoveSecondarySiteVO.getContactRole());
                addSecSiteHQuery.setParameter(
                        RMDCommonConstants.SECONDARY_SITE_ID,
                        RMDCommonConstants.TWO);
                addSecSiteHQuery.setParameter(RMDCommonConstants.SITE_OBJID,
                        objAddRemoveSecondarySiteVO.getSiteObjId());
                addSecSiteHQuery.setParameter(RMDCommonConstants.CONTACT_OBJID,
                        objAddRemoveSecondarySiteVO.getContactObjId());
                addSecSiteHQuery.setParameter(RMDCommonConstants.ROLE2GBSTELM,
                        RMDCommonConstants.ROLE2GBSTELM_VAL);
                addSecSiteHQuery.executeUpdate();
                result = RMDCommonConstants.SUCCESS;
            }
        } catch (RMDDAOConnectionException ex) {
            result = RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_CONTACT_SECONDARY_SITE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            result = RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_CONTACT_SECONDARY_SITE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            addSecSiteQuery = null;

        }
        return result;

    }

    /**
     * 
     * @param List
     *            <AddRemoveSecondarySiteVO>
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to remove the secondary sites from the
     *              contact
     */
    @Override
    public String removeContactSecondarySite(
            List<AddRemoveSecondarySiteVO> arlAddRemoveSecondarySiteVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        StringBuilder removeSecSiteQuery = null;
        try {
            session = getHibernateSession();
            removeSecSiteQuery = new StringBuilder();
            removeSecSiteQuery
                    .append("DELETE FROM TABLE_CONTACT_ROLE WHERE ROLE_NAME=:contactRole and CONTACT_ROLE2SITE=:siteObjId and CONTACT_ROLE2CONTACT=:contactObjId");
            final Query removeSecSiteHQuery = session
                    .createSQLQuery(removeSecSiteQuery.toString());
            for (AddRemoveSecondarySiteVO objAddRemoveSecondarySiteVO : arlAddRemoveSecondarySiteVO) {
                removeSecSiteHQuery.setParameter(
                        RMDCommonConstants.CONTACT_ROLE,
                        objAddRemoveSecondarySiteVO.getContactRole());
                removeSecSiteHQuery.setParameter(RMDCommonConstants.SITE_OBJID,
                        objAddRemoveSecondarySiteVO.getSiteObjId());
                removeSecSiteHQuery.setParameter(
                        RMDCommonConstants.CONTACT_OBJID,
                        objAddRemoveSecondarySiteVO.getContactObjId());
                removeSecSiteHQuery.executeUpdate();

            }

            result = RMDCommonConstants.SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            result = RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_REMOVE_CONTACT_SECONDARY_SITE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            result = RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_REMOVE_CONTACT_SECONDARY_SITE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            removeSecSiteQuery = null;
        }
        return result;
    }

    /**
     * 
     * @param ContactSiteDetailsVO
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to update the existing Contact details
     * 
     */
    @Override
    public String addOrUpdateContact(
            ContactSiteDetailsVO objContactSiteDetailsVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        boolean dupContact = false;
        String actionFrom = null;
        String checkDupContact = null;
        try {

            actionFrom = objContactSiteDetailsVO.getActionFrom();
            checkDupContact = objContactSiteDetailsVO.getCheckDupContact();
            if (actionFrom.equalsIgnoreCase(RMDCommonConstants.ADD_CONTACT)
                    || checkDupContact
                            .equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
                dupContact = isDuplicateContact(
                        objContactSiteDetailsVO.getFirstName(),
                        objContactSiteDetailsVO.getLastName(),
                        objContactSiteDetailsVO.getPhNo());
            }
            if (dupContact) {
                result = RMDCommonConstants.CONTACT_EXIST;
            } else if (actionFrom
                    .equalsIgnoreCase(RMDCommonConstants.ADD_CONTACT)) {
                result = addContact(objContactSiteDetailsVO);
            } else if (actionFrom
                    .equalsIgnoreCase(RMDCommonConstants.UPDATE_CONTACT)) {
                result = updateContact(objContactSiteDetailsVO);
            }

        } catch (Exception e) {
            String errorCode = RMDCommonConstants.EMPTY_STRING;
            if (e.getMessage().contains(
                    RMDServiceConstants.DAO_EXCEPTION_UPDATE_CONTACT)) {
                errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.USER_MANAGEMENT_FAILED_TO_UPDATE_EOA_TABLES);
            } else if (e.getMessage().contains(
                    RMDServiceConstants.DAO_EXCEPTION_CREATE_CONTACT)) {
                errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            }
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return result;
    }

    private String updateContact(ContactSiteDetailsVO objContactSiteDetailsVO) {
        String result = RMDCommonConstants.SUCCESS;
        String dupSiteOrRole = RMDCommonConstants.EMPTY_STRING;
        String fromScreen = RMDCommonConstants.UPDATE_CONTACT;
        Session session = null;
        Transaction transaction = null;
        StringBuilder conUpdateQuery = null;
        StringBuilder conRoleUpdateQuery = null;
        int updateStatus = RMDCommonConstants.INT_CONST_ZERO;
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();
            conUpdateQuery = new StringBuilder();
            conUpdateQuery
                    .append("UPDATE TABLE_CONTACT SET FIRST_NAME=:firstName , S_FIRST_NAME=upper(:firstName), LAST_NAME=:lastName, S_LAST_NAME=upper(:lastName),");
            conUpdateQuery
                    .append("PHONE=:phNo, FAX_NUMBER=:faxNo,E_MAIL=:emailId,TITLE=:title,SALUTATION=:salutation,STATUS=:status, UPDATE_STAMP=sysdate,X_CELL_PHONE=:cellPhNo,");
            conUpdateQuery
                    .append("X_DIAL_COMM=:dailComm,X_HOME_PHONE=:homePhNo,X_VOICE_MAIL=:voiceMail where objid=:contactObjId");
            final Query conUpdateHQuery = session.createSQLQuery(conUpdateQuery
                    .toString());
            conUpdateHQuery.setParameter(RMDCommonConstants.FIRST_NAME,
                    objContactSiteDetailsVO.getFirstName());
            conUpdateHQuery.setParameter(RMDCommonConstants.LAST_NAME,
                    objContactSiteDetailsVO.getLastName());
            conUpdateHQuery.setParameter(RMDCommonConstants.PH_NO,
                    objContactSiteDetailsVO.getPhNo());
            conUpdateHQuery.setParameter(RMDCommonConstants.FAX_NO,
                    objContactSiteDetailsVO.getFax());
            conUpdateHQuery.setParameter(RMDCommonConstants.EMAIL_ID,
                    objContactSiteDetailsVO.getEmailId());
            conUpdateHQuery.setParameter(RMDCommonConstants.TITLE,
                    objContactSiteDetailsVO.getJobTitle());
            conUpdateHQuery.setParameter(RMDCommonConstants.SALUTATION,
                    objContactSiteDetailsVO.getSalutation());
            conUpdateHQuery.setParameter(RMDCommonConstants.STATUS,
                    objContactSiteDetailsVO.getContactStatus());
            conUpdateHQuery.setParameter(RMDCommonConstants.CELL_PH_NO,
                    objContactSiteDetailsVO.getCellPh());
            conUpdateHQuery.setParameter(RMDCommonConstants.DAIL_COMM,
                    objContactSiteDetailsVO.getDailComm());
            conUpdateHQuery.setParameter(RMDCommonConstants.HOME_PH_NO,
                    objContactSiteDetailsVO.getHomePh());
            conUpdateHQuery.setParameter(RMDCommonConstants.VOICE_MAIL,
                    objContactSiteDetailsVO.getVoiceMail());
            conUpdateHQuery.setParameter(RMDCommonConstants.CONTACT_OBJID,
                    objContactSiteDetailsVO.getContactObjId());
            updateStatus = conUpdateHQuery.executeUpdate();
            if (updateStatus == RMDCommonConstants.UPDATE_SUCCESS
                    && (objContactSiteDetailsVO.isRoleChanged() || objContactSiteDetailsVO
                            .isSiteChanged())) {

                dupSiteOrRole = isDuplicateSiteOrRole(
                        objContactSiteDetailsVO.getContactObjId(),
                        objContactSiteDetailsVO.getLocObjId(),
                        objContactSiteDetailsVO.getContactRole(),
                        objContactSiteDetailsVO.isRoleChanged(),
                        objContactSiteDetailsVO.isSiteChanged(), fromScreen);
                if (!RMDCommonUtility.isNullOrEmpty(dupSiteOrRole)
                        && !dupSiteOrRole
                                .equalsIgnoreCase(RMDCommonConstants.NO_DUPLICATES)) {
                    transaction.rollback();
                    result = dupSiteOrRole;
                } else {
                    conRoleUpdateQuery = new StringBuilder();
                    conRoleUpdateQuery
                            .append("UPDATE TABLE_CONTACT_ROLE SET ROLE_NAME=:contactRole, S_ROLE_NAME=upper(:contactRole), UPDATE_STAMP=SYSDATE,");
                    conRoleUpdateQuery
                            .append("CONTACT_ROLE2SITE=:siteObjId where CONTACT_ROLE2CONTACT=:contactObjId and CONTACT_ROLE2SITE=:oldSiteObjId");
                    final Query conRoleUpdateHQuery = session
                            .createSQLQuery(conRoleUpdateQuery.toString());
                    conRoleUpdateHQuery.setParameter(
                            RMDCommonConstants.CONTACT_ROLE,
                            objContactSiteDetailsVO.getContactRole());
                    conRoleUpdateHQuery.setParameter(
                            RMDCommonConstants.SITE_OBJID,
                            objContactSiteDetailsVO.getLocObjId());
                    conRoleUpdateHQuery.setParameter(
                            RMDCommonConstants.CONTACT_OBJID,
                            objContactSiteDetailsVO.getContactObjId());
                    conRoleUpdateHQuery.setParameter(
                            RMDCommonConstants.OLD_SITE_OBJID,
                            objContactSiteDetailsVO.getOldLocObjId());
                    conRoleUpdateHQuery.executeUpdate();                   
                }
            }

            if (updateStatus == RMDCommonConstants.UPDATE_SUCCESS
                    && result.equalsIgnoreCase(RMDCommonConstants.SUCCESS)) {
                transaction.commit();
            }

        } catch (Exception e) {
            if (null != transaction) {
                transaction.rollback();
            }
            result = RMDCommonConstants.FAILURE;
            LOG.error("Exception occured in updateContact() method of ContactSiteDAOImpl "
                    + e.getLocalizedMessage());
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_CONTACT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            conUpdateQuery = null;
            conRoleUpdateQuery = null;           
            transaction = null;
            releaseSession(session);
        }
        return result;
    }

    public String addContact(ContactSiteDetailsVO objContactSiteDetailsVO) {
        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        Transaction transaction = null;
        String contactObjId = RMDCommonConstants.EMPTY_STRING;
        StringBuilder conInsertQuery = null;
        StringBuilder conRoleInsertQuery = null;
        StringBuilder timeBombInsertQuery = null;
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();
            // insert details into table_contact table
            conInsertQuery = new StringBuilder();
            conInsertQuery
                    .append("INSERT INTO TABLE_CONTACT (OBJID,FIRST_NAME,S_FIRST_NAME,LAST_NAME,S_LAST_NAME,PHONE,FAX_NUMBER,E_MAIL,MAIL_STOP,EXPERTISE_LEV,TITLE,");
            conInsertQuery
                    .append("HOURS,SALUTATION,MDBK,STATE_CODE,STATE_VALUE,ADDRESS_1,ADDRESS_2,CITY,STATE,ZIPCODE,COUNTRY,STATUS,ARCH_IND,ALERT_IND,DEV,UPDATE_STAMP,");
            conInsertQuery
                    .append("CALLER2USER,X_CELL_PHONE,X_DIAL_COMM,X_HOME_PHONE, X_VOICE_MAIL) values ((select max(objid)+1 from table_contact),:firstName,");
            conInsertQuery
                    .append("upper(:firstName),:lastName,upper(:lastName),:phNo,:faxNo,:emailId,null,0,:title,null,:salutation,null,0,null,null,null,null,null,null,null,");
            conInsertQuery
                    .append(":status,0,0,null,sysdate,null,:cellPhNo,:dailComm,:homePhNo,:voiceMail)");
            final Query conInsertHQuery = session.createSQLQuery(conInsertQuery
                    .toString());
            conInsertHQuery.setParameter(RMDCommonConstants.FIRST_NAME,
                    objContactSiteDetailsVO.getFirstName());
            conInsertHQuery.setParameter(RMDCommonConstants.LAST_NAME,
                    objContactSiteDetailsVO.getLastName());
            conInsertHQuery.setParameter(RMDCommonConstants.PH_NO,
                    objContactSiteDetailsVO.getPhNo());
            conInsertHQuery.setParameter(RMDCommonConstants.FAX_NO,
                    objContactSiteDetailsVO.getFax());
            conInsertHQuery.setParameter(RMDCommonConstants.EMAIL_ID,
                    objContactSiteDetailsVO.getEmailId());
            conInsertHQuery.setParameter(RMDCommonConstants.TITLE,
                    objContactSiteDetailsVO.getJobTitle());
            conInsertHQuery.setParameter(RMDCommonConstants.SALUTATION,
                    objContactSiteDetailsVO.getSalutation());
            conInsertHQuery.setParameter(RMDCommonConstants.STATUS,
                    objContactSiteDetailsVO.getContactStatus());
            conInsertHQuery.setParameter(RMDCommonConstants.CELL_PH_NO,
                    objContactSiteDetailsVO.getCellPh());
            conInsertHQuery.setParameter(RMDCommonConstants.DAIL_COMM,
                    objContactSiteDetailsVO.getDailComm());
            conInsertHQuery.setParameter(RMDCommonConstants.HOME_PH_NO,
                    objContactSiteDetailsVO.getHomePh());
            conInsertHQuery.setParameter(RMDCommonConstants.VOICE_MAIL,
                    objContactSiteDetailsVO.getVoiceMail());
            conInsertHQuery.executeUpdate();
            // insert role details into table_contact_role table
            conRoleInsertQuery = new StringBuilder();
            conRoleInsertQuery
                    .append("INSERT INTO TABLE_CONTACT_ROLE (OBJID,ROLE_NAME,S_ROLE_NAME,PRIMARY_SITE,DEV,UPDATE_STAMP,CONTACT_ROLE2SITE,CONTACT_ROLE2CONTACT,");
            conRoleInsertQuery
                    .append("CONTACT_ROLE2GBST_ELM) VALUES ((select max(objid)+1 from table_contact_role),:contactRole,UPPER(:contactRole),:primarySiteId,null,");
            conRoleInsertQuery
                    .append("SYSDATE,:siteObjId,(SELECT OBJID FROM TABLE_CONTACT WHERE UPPER(FIRST_NAME)=UPPER(:firstName) AND UPPER(LAST_NAME)=UPPER(:lastName)");
            conRoleInsertQuery.append(" AND PHONE=:phNo),:role2GBSTELM) ");
            final Query conRoleInsertHQuery = session
                    .createSQLQuery(conRoleInsertQuery.toString());
            conRoleInsertHQuery.setParameter(RMDCommonConstants.CONTACT_ROLE,
                    objContactSiteDetailsVO.getContactRole());
            conRoleInsertHQuery.setParameter(
                    RMDCommonConstants.PRIMARY_SITE_ID,
                    RMDCommonConstants.ONE_STRING);
            conRoleInsertHQuery.setParameter(RMDCommonConstants.SITE_OBJID,
                    objContactSiteDetailsVO.getLocObjId());
            conRoleInsertHQuery.setParameter(RMDCommonConstants.FIRST_NAME,
                    objContactSiteDetailsVO.getFirstName());
            conRoleInsertHQuery.setParameter(RMDCommonConstants.LAST_NAME,
                    objContactSiteDetailsVO.getLastName());
            conRoleInsertHQuery.setParameter(RMDCommonConstants.PH_NO,
                    objContactSiteDetailsVO.getPhNo());
            conRoleInsertHQuery.setParameter(RMDCommonConstants.ROLE2GBSTELM,
                    RMDCommonConstants.ROLE2GBSTELM_VAL);
            conRoleInsertHQuery.executeUpdate();
            // insert contact details into table_time_bomb table
            timeBombInsertQuery = new StringBuilder();
            timeBombInsertQuery
                    .append("INSERT INTO TABLE_TIME_BOMB (OBJID,TITLE,ESCALATE_TIME,END_TIME,FOCUS_LOWID,FOCUS_TYPE,SUPPL_INFO,TIME_PERIOD,FLAGS,LEFT_REPEAT,");
            timeBombInsertQuery
                    .append("REPORT_TITLE,PROPERTY_SET,USERS,DEV,CREATION_TIME,TRCKR_INFO2COM_TMPLTE,CMIT_CREATOR2EMPLOYEE,RULE2COM_TMPLTE,TIME_BOMB2PARAM)");
            timeBombInsertQuery
                    .append(" VALUES ((select max(objid)+1 from table_time_bomb),null,to_date('01-JAN-53','DD-MON-RR'),SYSDATE,(SELECT OBJID FROM TABLE_CONTACT");
            timeBombInsertQuery
                    .append(" WHERE UPPER(FIRST_NAME)=UPPER(:firstName) AND UPPER(LAST_NAME)=UPPER(:lastName) AND PHONE=:phNo),45,null,:siteObjId,524681218,");
            timeBombInsertQuery
                    .append("0,null,null,null,null,sysdate,null,(SELECT te.objid from table_employee te,table_user tu where tu.objid=te.EMPLOYEE2USER and tu.LOGIN_NAME=:userName),null,null)");
            final Query timeBombInsertHQuery = session
                    .createSQLQuery(timeBombInsertQuery.toString());
            timeBombInsertHQuery.setParameter(RMDCommonConstants.FIRST_NAME,
                    objContactSiteDetailsVO.getFirstName());
            timeBombInsertHQuery.setParameter(RMDCommonConstants.LAST_NAME,
                    objContactSiteDetailsVO.getLastName());
            timeBombInsertHQuery.setParameter(RMDCommonConstants.PH_NO,
                    objContactSiteDetailsVO.getPhNo());
            timeBombInsertHQuery.setParameter(RMDCommonConstants.SITE_OBJID,
                    objContactSiteDetailsVO.getLocObjId());
            timeBombInsertHQuery.setParameter(RMDCommonConstants.USER_NAME,
                    objContactSiteDetailsVO.getCreater());
            timeBombInsertHQuery.executeUpdate();
            transaction.commit();
            contactObjId = getContactObjId(
                    objContactSiteDetailsVO.getFirstName(),
                    objContactSiteDetailsVO.getLastName(),
                    objContactSiteDetailsVO.getPhNo());
            result = RMDCommonConstants.SUCCESS + RMDCommonConstants.HYPHEN
                    + contactObjId;
        } catch (Exception e) {
            if (null != transaction) {
                transaction.rollback();
            }
            LOG.error("Exception occured in addContact() method of ContactSiteDAOImpl "
                    + e.getLocalizedMessage());
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_CONTACT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            conInsertQuery = null;
            conRoleInsertQuery = null;
            timeBombInsertQuery = null;
            transaction = null;
            releaseSession(session);
        }
        return result;
    }

    public boolean isDuplicateContact(String firstName, String lastName,
            String phNo) {
        Session session = null;
        StringBuilder dupContactQuery = null;
        boolean dupContact = RMDCommonConstants.FALSE;
        int dupCount = 0;
        try {
            session = getHibernateSession();
            dupContactQuery = new StringBuilder();
            dupContactQuery
                    .append("SELECT COUNT(1) FROM TABLE_CONTACT WHERE UPPER(FIRST_NAME)=UPPER(:firstName) AND UPPER(LAST_NAME)=UPPER(:lastName) AND PHONE=:phNo");
            final Query dupContactHQuery = session
                    .createSQLQuery(dupContactQuery.toString());
            dupContactHQuery.setParameter(RMDCommonConstants.FIRST_NAME,
                    firstName);
            dupContactHQuery.setParameter(RMDCommonConstants.LAST_NAME,
                    lastName);
            dupContactHQuery.setParameter(RMDCommonConstants.PH_NO, phNo);
            dupCount = ((BigDecimal) dupContactHQuery.uniqueResult())
                    .intValue();
            if (dupCount > 0) {
                dupContact = RMDCommonConstants.TRUE;
            }

        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_IS_DUPLICATE_CONTACT_METHOD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            dupContactQuery = null;

        }

        return dupContact;
    }

    public String isDuplicateSiteOrRole(String contactObjId, String siteObjId,
            String contactRole, boolean roleChanged, boolean siteChanged,
            String fromScreen) {
        Session session = null;
        StringBuilder dupSiteQuery = null;
        List<Object> dupRoleOrSiteList = null;
        List<String> roleList = null;
        List<String> siteObjIdList = null;
        String result = RMDCommonConstants.NO_DUPLICATES;
        try {
            session = getHibernateSession();
            dupSiteQuery = new StringBuilder();
            dupSiteQuery
                    .append("SELECT CONTACT_ROLE2SITE,ROLE_NAME FROM TABLE_CONTACT_ROLE WHERE CONTACT_ROLE2CONTACT=:contactObjId AND (CONTACT_ROLE2SITE=:siteObjId OR ROLE_NAME=:contactRole)");
            final Query dupSiteHQuery = session.createSQLQuery(dupSiteQuery
                    .toString());
            dupSiteHQuery.setParameter(RMDCommonConstants.CONTACT_OBJID,
                    contactObjId);
            dupSiteHQuery
                    .setParameter(RMDCommonConstants.SITE_OBJID, siteObjId);
            dupSiteHQuery.setParameter(RMDCommonConstants.CONTACT_ROLE,
                    contactRole);
            dupRoleOrSiteList = dupSiteHQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(dupRoleOrSiteList)) {
                roleList = new ArrayList<String>(dupRoleOrSiteList.size());
                siteObjIdList = new ArrayList<String>(dupRoleOrSiteList.size());
                for (final Iterator<Object> obj = dupRoleOrSiteList.iterator(); obj
                        .hasNext();) {
                    final Object[] siteRoleDetails = (Object[]) obj.next();
                    siteObjIdList.add(RMDCommonUtility
                            .convertObjectToString(siteRoleDetails[0]));
                    roleList.add(RMDCommonUtility
                            .convertObjectToString(siteRoleDetails[1]));
                }
                if (RMDCommonConstants.UPDATE_CONTACT
                        .equalsIgnoreCase(fromScreen)) {
                    if ((roleChanged && siteChanged)
                            && siteObjIdList.contains(siteObjId)
                            && roleList.contains(contactRole)) {
                        result = RMDCommonConstants.SITE_AND_ROLE_EXIST;
                    } else if ((roleChanged && siteChanged)
                            && siteObjIdList.contains(siteObjId)) {
                        result = RMDCommonConstants.SITE_EXISTS;
                    } else if ((roleChanged && siteChanged)
                            && roleList.contains(contactRole)) {
                        result = RMDCommonConstants.ROLE_EXISTS;
                    }
                    if ((!roleChanged && siteChanged)
                            && siteObjIdList.contains(siteObjId)) {
                        result = RMDCommonConstants.SITE_EXISTS;
                    } else if ((!siteChanged && roleChanged)
                            && roleList.contains(contactRole)) {
                        result = RMDCommonConstants.ROLE_EXISTS;
                    }
                } else if (RMDCommonConstants.SEC_SITE_SCREEN
                        .equalsIgnoreCase(fromScreen)) {
                    if (siteObjIdList.contains(siteObjId)
                            && roleList.contains(contactRole)) {
                        result = RMDCommonConstants.SITE_AND_ROLE_EXIST;
                    } else if (siteObjIdList.contains(siteObjId)
                            && !roleList.contains(contactRole)) {
                        result = RMDCommonConstants.SITE_EXISTS;
                    } else if (roleList.contains(contactRole)
                            && !siteObjIdList.contains(siteObjId)) {
                        result = RMDCommonConstants.ROLE_EXISTS;
                    }
                }
            }

        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_IS_DUPLICATE_SITE_OR_ROLE_METHOD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            dupSiteQuery = null;
            dupRoleOrSiteList = null;
            roleList = null;
            siteObjIdList = null;
            releaseSession(session);
        }

        return result;
    }

    public String getContactObjId(String firstName, String lastName, String phNo) {
        Session session = null;
        StringBuilder conObjIdQuery = null;
        List<Object> resultList = null;
        String contactObjId = RMDCommonConstants.EMPTY_STRING;
        try {
            session = getHibernateSession();
            conObjIdQuery = new StringBuilder();
            conObjIdQuery
                    .append("SELECT OBJID FROM TABLE_CONTACT WHERE UPPER(FIRST_NAME)=UPPER(:firstName) AND UPPER(LAST_NAME)=UPPER(:lastName) AND PHONE=:phNo");
            final Query conObjIdHQuery = session.createSQLQuery(conObjIdQuery
                    .toString());
            conObjIdHQuery.setParameter(RMDCommonConstants.FIRST_NAME,
                    firstName);
            conObjIdHQuery.setParameter(RMDCommonConstants.LAST_NAME, lastName);
            conObjIdHQuery.setParameter(RMDCommonConstants.PH_NO, phNo);
            resultList = conObjIdHQuery.list();
            if (null != resultList && !resultList.isEmpty()) {
                contactObjId = RMDCommonUtility
                        .convertObjectToString(resultList.get(0));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_GET_CONTACT_OBJID_METHOD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            conObjIdQuery = null;
            resultList = null;
        }

        return contactObjId;
    }

    /**
     * @Author:
     * @param:SiteSearchVO
     * @return:List<SiteSearchVO>
     * @throws:RMDDAOException
     * @Description: This method is used to get Sites Details.
     */
    @Override
    public List getSites(SiteSearchVO objSiteSearchVO) throws RMDDAOException {
        List<SiteSearchVO> lstSiteSearchVO = new ArrayList<SiteSearchVO>();
        Session objSession = null;
        List<Object[]> objSites = null;
        StringBuilder siteQry = new StringBuilder();
        try {
            objSession = getHibernateSession();
            siteQry.append("select site_id, site_name,address,city,state,site_type,objid,address_2,country_name,zipcode from table_site_view where  ");
            if (objSiteSearchVO.getStrInclInactiveContacts().equalsIgnoreCase(
                    RMDCommonConstants.STRING_FALSE)) {
                siteQry.append(" status <= 0 ");
            } else {
                siteQry.append(" status <= 1 ");
            }
            if (null != objSiteSearchVO.getStrType()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                            .getStrType())) {
                if (objSiteSearchVO.getStrType().equalsIgnoreCase(
                        RMDCommonConstants.CUSTOMERSTR)) {
                    siteQry.append(" AND site_type = '1' ");
                } else if (objSiteSearchVO.getStrType().equalsIgnoreCase(
                        RMDCommonConstants.INTERNAL)) {
                    siteQry.append(" AND site_type = '2' ");
                } else if (objSiteSearchVO.getStrType().equalsIgnoreCase(
                        RMDCommonConstants.RESELLER)) {
                    siteQry.append(" AND site_type = '3' ");
                } else if (objSiteSearchVO.getStrType().equalsIgnoreCase(
                        RMDCommonConstants.INDIVIDUAL)) {
                    siteQry.append(" AND site_type = '4' ");
                } else if (objSiteSearchVO.getStrType().equalsIgnoreCase(
                        RMDCommonConstants.VENDOR)) {
                    siteQry.append(" AND site_type = '5' ");
                }
            }
            if (null != objSiteSearchVO.getStrSiteID()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                            .getStrSiteID())) {
                siteQry.append(" AND lower(site_id) LIKE lower(:siteId) ");
            }
            if (null != objSiteSearchVO.getStrSiteName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                            .getStrSiteName())) {
                siteQry.append(" AND lower(site_name) LIKE lower(:siteName) ");
            }
            if (null != objSiteSearchVO.getStrAddress()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                            .getStrAddress())) {
                siteQry.append(" AND lower(address) LIKE lower(:address) ");
            }
            if (null != objSiteSearchVO.getStrCity()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                            .getStrCity())) {
                siteQry.append(" AND lower(city) LIKE lower(:city) ");
            }
            if (null != objSiteSearchVO.getStrState()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                            .getStrState())) {
                siteQry.append(" AND lower(state) LIKE lower(:state) ");
            }
            if (null != objSiteSearchVO.getStrAccountID()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                            .getStrAccountID())) {
                siteQry.append(" AND lower(org_id) LIKE lower(:orgId) ");
            }
            if (null != objSiteSearchVO.getStrAccountName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                            .getStrAccountName())) {
                siteQry.append(" AND lower(org_name) LIKE lower(:orgName) ");
            }
            siteQry.append(" ORDER BY site_id,site_name ");
            if (null != objSession) {
                Query siteHqry = objSession.createSQLQuery(siteQry.toString());
                if (null != objSiteSearchVO.getStrSiteID()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrSiteID())) {
                    siteHqry.setParameter(
                            RMDCommonConstants.SITEID,
                            RMDServiceConstants.PERCENTAGE
                                    + objSiteSearchVO.getStrSiteID()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (null != objSiteSearchVO.getStrSiteName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrSiteName())) {
                    siteHqry.setParameter(
                            RMDCommonConstants.SITE_NAME,
                            RMDServiceConstants.PERCENTAGE
                                    + objSiteSearchVO.getStrSiteName()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (null != objSiteSearchVO.getStrAddress()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrAddress())) {
                    siteHqry.setParameter(
                            RMDCommonConstants.ADDRESS,
                            RMDServiceConstants.PERCENTAGE
                                    + objSiteSearchVO.getStrAddress()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (null != objSiteSearchVO.getStrCity()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrCity())) {
                    siteHqry.setParameter(
                            RMDCommonConstants.CITY,
                            RMDServiceConstants.PERCENTAGE
                                    + objSiteSearchVO.getStrCity()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (null != objSiteSearchVO.getStrState()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrState())) {
                    siteHqry.setParameter(
                            RMDCommonConstants.STATECODE,
                            RMDServiceConstants.PERCENTAGE
                                    + objSiteSearchVO.getStrState()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (null != objSiteSearchVO.getStrAccountID()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrAccountID())) {
                    siteHqry.setParameter(
                            RMDCommonConstants.ORGID,
                            RMDServiceConstants.PERCENTAGE
                                    + objSiteSearchVO.getStrAccountID()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (null != objSiteSearchVO.getStrAccountName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrAccountName())) {
                    siteHqry.setParameter(
                            RMDCommonConstants.ORG_NAME,
                            RMDServiceConstants.PERCENTAGE
                                    + objSiteSearchVO.getStrAccountName()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                siteHqry.setFetchSize(100);
                objSites = (ArrayList) siteHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(objSites)) {
                    for (Object[] obj : objSites) {
                        objSiteSearchVO = new SiteSearchVO();
                        objSiteSearchVO.setStrSiteID(RMDCommonUtility
                                .convertObjectToString(obj[0]));
                        objSiteSearchVO.setStrSiteName(RMDCommonUtility
                                .convertObjectToString(obj[1]));
                        objSiteSearchVO.setStrAddress(RMDCommonUtility
                                .convertObjectToString(obj[2]));
                        objSiteSearchVO.setStrCity(RMDCommonUtility
                                .convertObjectToString(obj[3]));
                        objSiteSearchVO.setStrState(RMDCommonUtility
                                .convertObjectToString(obj[4]));
                        objSiteSearchVO.setStrSiteObjId(RMDCommonUtility
                                .convertObjectToString(obj[6]));
                        String siteType = RMDCommonUtility
                                .convertObjectToString(obj[5]);
                        if (null != siteType
                                && !RMDCommonConstants.EMPTY_STRING
                                        .equals(siteType)) {
                            if (siteType
                                    .equalsIgnoreCase(RMDCommonConstants.ONE_STRING)) {
                                objSiteSearchVO
                                        .setStrType(RMDCommonConstants.CUSTOMERSTR);
                            } else if (siteType
                                    .equalsIgnoreCase(RMDCommonConstants.TWO)) {
                                objSiteSearchVO
                                        .setStrType(RMDCommonConstants.INTERNAL);
                            } else if (siteType
                                    .equalsIgnoreCase(RMDCommonConstants.THREE)) {
                                objSiteSearchVO
                                        .setStrType(RMDCommonConstants.RESELLER);
                            } else if (siteType
                                    .equalsIgnoreCase(RMDCommonConstants.FOUR)) {
                                objSiteSearchVO
                                        .setStrType(RMDCommonConstants.INDIVIDUAL);
                            } else if (siteType
                                    .equalsIgnoreCase(RMDCommonConstants.FIVE)) {
                                objSiteSearchVO
                                        .setStrType(RMDCommonConstants.VENDOR);
                            }
                        }
                        objSiteSearchVO.setStrAddressEx(RMDCommonUtility
                                .convertObjectToString(obj[7]));
                        objSiteSearchVO.setStrZipCode(RMDCommonUtility
                                .convertObjectToString(obj[8]));
                        objSiteSearchVO.setStrZipCode(RMDCommonUtility
                                .convertObjectToString(obj[9]));
                        lstSiteSearchVO.add(objSiteSearchVO);
                    }
                }
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SITES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            siteQry = null;
            objSites = null;
            releaseSession(objSession);
        }
        return lstSiteSearchVO;
    }

    /**
     * @Author:
     * @param:String siteObjId
     * @return:SiteSearchVO
     * @throws:RMDDAOException
     * @Description: This method is used to get View Site Details.
     */
    @Override
    public SiteSearchVO viewSiteDetails(String siteObjId)
            throws RMDDAOException {
        Session objSession = null;
        List<Object[]> objSites = null;
        SiteSearchVO objSiteSearchVO = null;
        StringBuilder siteQry = new StringBuilder();
        StringBuilder caseQry = new StringBuilder();
        StringBuilder contactQry = new StringBuilder();
        try {
            objSession = getHibernateSession();
            siteQry.append("SELECT SITE.OBJID,SITE.CUST_PRIMADDR2ADDRESS,SITE.CUST_SHIPADDR2ADDRESS,SITE.CUST_BILLADDR2ADDRESS, SITE.SITE_ID,  ");
            siteQry.append("SITE.NAME AS SITE_NAME, SITE.TYPE, SITE.STATUS, ZON.NAME AS ZONES, SITE.PHONE, SITE.FAX, SITE.SHIP_VIA,AD.ADDRESS,   ");
            siteQry.append("AD.ADDRESS_2, AD.ADDRESS ||' '|| AD.ADDRESS_2  PRIMADD1, AD.CITY ||' '|| AD.STATE ||' '|| AD.ZIPCODE ||' '|| COUN.NAME PRIMADD2,  ");
            siteQry.append("(SELECT AD.ADDRESS ||' '|| AD.ADDRESS_2 FROM TABLE_SITE SITE,  TABLE_TIME_ZONE ZON, TABLE_ADDRESS AD, TABLE_COUNTRY COUN  ");
            siteQry.append("WHERE  AD.ADDRESS2COUNTRY=COUN.OBJID AND SITE.CUST_SHIPADDR2ADDRESS=AD.OBJID AND AD.ADDRESS2TIME_ZONE =ZON.OBJID  ");
            siteQry.append("AND SITE.OBJID =:siteObjId) SHIPADD1, (SELECT AD.CITY ||' '|| AD.STATE ||' '|| AD.ZIPCODE ||' '|| COUN.NAME  ");
            siteQry.append("FROM TABLE_SITE SITE,  TABLE_TIME_ZONE ZON, TABLE_ADDRESS AD, TABLE_COUNTRY COUN WHERE  AD.ADDRESS2COUNTRY=COUN.OBJID  ");
            siteQry.append("AND SITE.CUST_SHIPADDR2ADDRESS=AD.OBJID AND AD.ADDRESS2TIME_ZONE =ZON.OBJID AND SITE.OBJID =:siteObjId) SHIPADD2,  ");
            siteQry.append("(SELECT AD.ADDRESS ||' '|| AD.ADDRESS_2 FROM TABLE_SITE SITE,  TABLE_TIME_ZONE ZON, TABLE_ADDRESS AD, TABLE_COUNTRY COUN  ");
            siteQry.append("WHERE  AD.ADDRESS2COUNTRY=COUN.OBJID AND SITE.CUST_BILLADDR2ADDRESS=AD.OBJID AND AD.ADDRESS2TIME_ZONE =ZON.OBJID  ");
            siteQry.append("AND SITE.OBJID =:siteObjId) BILLADD1, (SELECT AD.CITY ||' '|| AD.STATE ||' '|| AD.ZIPCODE ||' '|| COUN.NAME  ");
            siteQry.append("FROM TABLE_SITE SITE,  TABLE_TIME_ZONE ZON, TABLE_ADDRESS AD, TABLE_COUNTRY COUN WHERE  AD.ADDRESS2COUNTRY=COUN.OBJID  ");
            siteQry.append("AND SITE.CUST_BILLADDR2ADDRESS=AD.OBJID AND AD.ADDRESS2TIME_ZONE =ZON.OBJID AND SITE.OBJID =:siteObjId) BILLADD2, AD.CITY,  ");
            siteQry.append("AD.STATE, AD.ZIPCODE, COUN.NAME FROM TABLE_SITE SITE,  TABLE_TIME_ZONE ZON, TABLE_ADDRESS AD, TABLE_COUNTRY COUN  ");
            siteQry.append("WHERE  AD.ADDRESS2COUNTRY=COUN.OBJID AND SITE.CUST_PRIMADDR2ADDRESS=AD.OBJID AND AD.ADDRESS2TIME_ZONE  =ZON.OBJID AND SITE.OBJID =:siteObjId  ");
            if (null != objSession) {
                Query assetHqry = objSession.createSQLQuery(siteQry.toString());
                if (null != siteObjId
                        && !RMDCommonConstants.EMPTY_STRING.equals(siteObjId)) {
                    assetHqry.setParameter(RMDCommonConstants.SITE_OBJ_ID,
                            siteObjId);
                }
                assetHqry.setFetchSize(100);
                objSites = (ArrayList) assetHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(objSites)) {
                    for (Object[] obj : objSites) {
                        objSiteSearchVO = new SiteSearchVO();
                        objSiteSearchVO.setStrSiteObjId(RMDCommonUtility
                                .convertObjectToString(obj[0]));
                        objSiteSearchVO.setPrimAddObjId(RMDCommonUtility
                                .convertObjectToString(obj[1]));
                        objSiteSearchVO.setShipAddObjId(RMDCommonUtility
                                .convertObjectToString(obj[2]));
                        objSiteSearchVO.setBillAddObjId(RMDCommonUtility
                                .convertObjectToString(obj[3]));
                        objSiteSearchVO.setStrSiteID(RMDCommonUtility
                                .convertObjectToString(obj[4]));
                        objSiteSearchVO.setStrSiteName(RMDCommonUtility
                                .convertObjectToString(obj[5]));
                        String siteStatus = RMDCommonUtility
                                .convertObjectToString(obj[7]);
                        if (null != siteStatus
                                && !RMDCommonConstants.EMPTY_STRING
                                        .equals(siteStatus)) {
                            if (siteStatus
                                    .equalsIgnoreCase(RMDCommonConstants.ZERO_STRING)) {
                                objSiteSearchVO
                                        .setStrStatus(RMDCommonConstants.ACTIVE_FLAG);
                            } else if (siteStatus
                                    .equalsIgnoreCase(RMDCommonConstants.ONE_STRING)) {
                                objSiteSearchVO
                                        .setStrStatus(RMDCommonConstants.INACTIVE_FLAG);
                            } else {
                                objSiteSearchVO
                                        .setStrStatus(RMDCommonConstants.OBSOLETE);
                            }
                        }
                        String siteCreateType = RMDCommonUtility
                                .convertObjectToString(obj[6]);
                        if (null != siteCreateType
                                && !RMDCommonConstants.EMPTY_STRING
                                        .equals(siteCreateType)) {
                            if (siteCreateType
                                    .equalsIgnoreCase(RMDCommonConstants.ONE_STRING)) {
                                objSiteSearchVO
                                        .setStrCreateType(RMDCommonConstants.CUST);
                            } else if (siteCreateType
                                    .equalsIgnoreCase(RMDCommonConstants.TWO)) {
                                objSiteSearchVO
                                        .setStrCreateType(RMDCommonConstants.INTR);
                            } else if (siteCreateType
                                    .equalsIgnoreCase(RMDCommonConstants.THREE)) {
                                objSiteSearchVO
                                        .setStrCreateType(RMDCommonConstants.RSEL);
                            } else if (siteCreateType
                                    .equalsIgnoreCase(RMDCommonConstants.FOUR)) {
                                objSiteSearchVO
                                        .setStrCreateType(RMDCommonConstants.INDV);
                            } else if (siteCreateType
                                    .equalsIgnoreCase(RMDCommonConstants.FIVE)) {
                                objSiteSearchVO
                                        .setStrCreateType(RMDCommonConstants.VEND);
                            }
                        }
                        objSiteSearchVO.setStrAddress(RMDCommonUtility
                                .convertObjectToString(obj[14]));
                        objSiteSearchVO.setStrAddressEx(RMDCommonUtility
                                .convertObjectToString(obj[15]));
                        objSiteSearchVO.setStrBillTo(RMDCommonUtility
                                .convertObjectToString(obj[18]));
                        objSiteSearchVO.setStrBillToEx(RMDCommonUtility
                                .convertObjectToString(obj[19]));
                        objSiteSearchVO.setStrShipTo(RMDCommonUtility
                                .convertObjectToString(obj[16]));
                        objSiteSearchVO.setStrShipToEx(RMDCommonUtility
                                .convertObjectToString(obj[17]));
                        objSiteSearchVO.setStrCellPhone(RMDCommonUtility
                                .convertObjectToString(obj[9]));
                        objSiteSearchVO.setStrFax(RMDCommonUtility
                                .convertObjectToString(obj[10]));
                        objSiteSearchVO.setStrPrefShipMethod(RMDCommonUtility
                                .convertObjectToString(obj[11]));
                    }
                }
            }

            caseQry.append(" SELECT TBO.ORG_ID FROM TABLE_SITE TS, TABLE_BUS_ORG TBO  WHERE TS.PRIMARY2BUS_ORG = TBO.OBJID  AND TS.SITE_ID=:siteId ");
            Query caseHqry = objSession.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDCommonConstants.SITEID,
                    objSiteSearchVO.getStrSiteID());
            if (null != caseHqry.uniqueResult()) {
                String strCustomer = caseHqry.uniqueResult().toString();
                objSiteSearchVO.setStrCustomer(strCustomer);
            }

            ContactSiteDetailsVO objContactSiteDetailsVO = null;
            List<ContactSiteDetailsVO> lstContactSiteDetailsVO = new ArrayList<ContactSiteDetailsVO>();
            contactQry
                    .append("SELECT CON_OBJID,T.LOC_OBJID,T.FIRST_NAME,T.LAST_NAME,T.PHONE,T.FAX_NUMBER,T.E_MAIL FROM TABLE_ROL_CONTCT T,TABLE_SITE S WHERE T.LOC_OBJID=S.OBJID AND S.OBJID=:siteObjId  ");
            if (null != objSession) {
                Query assetHqry = objSession.createSQLQuery(contactQry
                        .toString());
                assetHqry.setParameter(RMDCommonConstants.SITE_OBJ_ID,
                        objSiteSearchVO.getStrSiteObjId());
                assetHqry.setFetchSize(100);
                objSites = (ArrayList) assetHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(objSites)) {
                    for (Object[] obj : objSites) {
                        objContactSiteDetailsVO = new ContactSiteDetailsVO();
                        objContactSiteDetailsVO
                                .setContactObjId(RMDCommonUtility
                                        .convertObjectToString(obj[0]));
                        objContactSiteDetailsVO.setLocObjId(RMDCommonUtility
                                .convertObjectToString(obj[1]));
                        objContactSiteDetailsVO.setFirstName(RMDCommonUtility
                                .convertObjectToString(obj[2]));
                        objContactSiteDetailsVO.setLastName(RMDCommonUtility
                                .convertObjectToString(obj[3]));
                        objContactSiteDetailsVO.setPhNo(RMDCommonUtility
                                .convertObjectToString(obj[4]));
                        objContactSiteDetailsVO.setFax(RMDCommonUtility
                                .convertObjectToString(obj[5]));
                        objContactSiteDetailsVO.setEmailId(RMDCommonUtility
                                .convertObjectToString(obj[6]));
                        lstContactSiteDetailsVO.add(objContactSiteDetailsVO);
                    }
                    objSiteSearchVO
                            .setArlContactSiteDetailsVO(lstContactSiteDetailsVO);
                }
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VIEW_SITE_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            siteQry = null;
            objSites = null;
            caseQry = null;
            contactQry = null;
            releaseSession(objSession);
        }
        return objSiteSearchVO;
    }

    /**
     * @Author:
     * @param:SiteSearchVO objSiteSearchVO
     * @return:String
     * @throws SQLException
     * @throws:RMDDAOException
     * @Description: This method is used to update a Site.
     */
    @Override
    public String updateSite(SiteSearchVO objSiteSearchVO)
            throws RMDDAOException {
        Session session = null;
        StringBuilder updateTableCaseQry = new StringBuilder();
        int siteTypeId = RMDCommonConstants.INT_CONST_ZERO;
        int siteStatsus = RMDCommonConstants.INT_CONST_ZERO;
        String busOrg = RMDCommonConstants.EMPTY_STRING;
        int[] status = { 0, 0, 0 };
        int busOrgID = RMDCommonConstants.INT_CONST_ZERO;
        String result = RMDServiceConstants.FAILURE;
        Connection objConnection = null;
        CallableStatement callableStmt = null;
        siteTypeId = getsiteTypeId(objSiteSearchVO.getStrCreateType());
        siteStatsus = getsiteStatusId(objSiteSearchVO.getStrStatus());
        StringBuilder caseQry = new StringBuilder();
        String codeQuery = null;
        List<Object[]> existingSite = null;
        boolean hasDuplicateSiteId = false;
        try {
            session = getHibernateSession();
            codeQuery = "SELECT SITE_ID,OBJID FROM TABLE_SITE WHERE SITE_ID=:siteId";
            Query query = session.createSQLQuery(codeQuery.toString());
            query.setParameter(RMDCommonConstants.SITEID,
                    objSiteSearchVO.getStrSiteID());
            existingSite = (ArrayList) query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(existingSite)) {
                for (Object[] obj : existingSite) {                 
                    String siteObjID = RMDCommonUtility
                            .convertObjectToString(obj[1]);
                    if (null != siteObjID
                            && !RMDCommonConstants.EMPTY_STRING
                                    .equals(siteObjID)) {
                        if (siteObjID.equalsIgnoreCase(objSiteSearchVO
                                .getStrSiteObjId())) {
                            hasDuplicateSiteId = false;
                        } else {
                            hasDuplicateSiteId = true;
                            result = RMDCommonConstants.SITE_EXISTS;
                        }
                    }
                }
            }
            if (!hasDuplicateSiteId) {
                if (null != objSiteSearchVO.getStrCustomer()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrCustomer())) {
                    caseQry.append(" SELECT OBJID FROM SA.TABLE_BUS_ORG WHERE ORG_ID = :customerID ");
                    Query caseHqry = session.createSQLQuery(caseQry.toString());
                    caseHqry.setParameter(RMDCommonConstants.CUSTOMERID,
                            objSiteSearchVO.getStrCustomer());
                    if (null != caseHqry.uniqueResult()) {
                        busOrg = caseHqry.uniqueResult().toString();
                    }
                    busOrgID = Integer.parseInt(busOrg);
                }
                updateTableCaseQry
                        .append("UPDATE TABLE_SITE SET SITE_ID=:siteId, NAME=:siteName,S_NAME=UPPER(:siteName),TYPE=:siteTypeId,SITE_TYPE=:siteType, ");
                updateTableCaseQry
                        .append("STATUS=:siteStatus,PHONE=:sitePhone,FAX=:siteFax,UPDATE_STAMP=SYSDATE,SHIP_VIA=:siteShipping,PRIMARY2BUS_ORG=:busOrg ");
                if (null != objSiteSearchVO.getPrimAddObjId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getPrimAddObjId())) {
                    updateTableCaseQry
                            .append(",CUST_PRIMADDR2ADDRESS=:primAddObjId");
                }
                if (null != objSiteSearchVO.getBillAddObjId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getBillAddObjId())) {
                    updateTableCaseQry
                            .append(",CUST_BILLADDR2ADDRESS=:billAddObjId");
                }
                if (null != objSiteSearchVO.getShipAddObjId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getShipAddObjId())) {
                    updateTableCaseQry
                            .append(",CUST_SHIPADDR2ADDRESS=:shipAddObjId");
                }
                updateTableCaseQry.append(" where objid=:siteObjId ");
                Query updateTableCaseHQry = session
                        .createSQLQuery(updateTableCaseQry.toString());
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITEID,
                        objSiteSearchVO.getStrSiteID());
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_NAME,
                        objSiteSearchVO.getStrSiteName());
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_NAME,
                        objSiteSearchVO.getStrSiteName());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_TYPE_ID, siteTypeId);
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_TYPE,
                        objSiteSearchVO.getStrCreateType());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_STATUS, siteStatsus);
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_PHONE,
                        objSiteSearchVO.getStrCellPhone());
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_FAX,
                        objSiteSearchVO.getStrFax());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_SHIPPING,
                        objSiteSearchVO.getStrPrefShipMethod());
                updateTableCaseHQry.setParameter(RMDCommonConstants.BUS_ORG,
                        busOrg);
                if (null != objSiteSearchVO.getPrimAddObjId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getPrimAddObjId())) {
                    updateTableCaseHQry.setParameter(
                            RMDCommonConstants.SITE_PRIM_ADD_OBJID,
                            objSiteSearchVO.getPrimAddObjId());
                }
                if (null != objSiteSearchVO.getBillAddObjId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getBillAddObjId())) {
                    updateTableCaseHQry.setParameter(
                            RMDCommonConstants.SITE_BILL_ADD_OBJID,
                            objSiteSearchVO.getBillAddObjId());
                }
                if (null != objSiteSearchVO.getShipAddObjId()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getShipAddObjId())) {
                    updateTableCaseHQry.setParameter(
                            RMDCommonConstants.SITE_SHIP_ADD_OBJID,
                            objSiteSearchVO.getShipAddObjId());
                }
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_OBJ_ID,
                        objSiteSearchVO.getStrSiteObjId());
                status[0] = updateTableCaseHQry.executeUpdate();
                if (status[0] == 1) {
                    objConnection = getConnection(session);
                    callableStmt = objConnection
                            .prepareCall("BEGIN GETS_SD_SITEPART_PKG.UPDATESITEPART_PR(?,?,?,?); END;");
                    callableStmt.setInt(1, busOrgID);
                    callableStmt.setString(2, objSiteSearchVO.getStrSiteID());
                    callableStmt
                            .registerOutParameter(3, java.sql.Types.VARCHAR);
                    callableStmt
                            .registerOutParameter(4, java.sql.Types.VARCHAR);
                    callableStmt.execute();
                    String errormsg = callableStmt.getString(3);
                    int errorcode = callableStmt.getInt(4);

                    if (errorcode > 0) {
                        result = RMDCommonConstants.ERROR_UPDATE;
                    }
                    result = RMDServiceConstants.SUCCESS;
                    LOG.debug("Exception occurred in updateSite:" + errormsg);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in updateSite:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_SITE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            caseQry = null;
            updateTableCaseQry = null;
            releaseSession(session);
            if (objConnection != null) {
                try {
                    objConnection.close();
                } catch (SQLException e) {
                    LOG.error(e);
                    LOG.error("Unexpected Error occured in ContactSiteDAOImpl in updateSite() while closing the resultset."
                            + e.getMessage());
                }
            }
            if (callableStmt != null) {
                try {
                    callableStmt.close();
                } catch (SQLException e) {
                    LOG.error(e);
                    LOG.error("Unexpected Error occured in ContactSiteDAOImpl in updateSite() while closing the resultset."
                            + e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * @Author:
     * @param:SiteSearchVO objSiteSearchVO
     * @return:String
     * @throws SQLException
     * @throws:RMDDAOException
     * @Description: This method is used to create a Site.
     */
    @Override
    public String createSite(SiteSearchVO objSiteSearchVO)
            throws RMDDAOException {
        Session session = null;
        StringBuilder updateTableCaseQry = new StringBuilder();
        int siteTypeId = RMDCommonConstants.INT_CONST_ZERO;
        int siteStatsus = RMDCommonConstants.INT_CONST_ZERO;
        int busOrgID = RMDCommonConstants.INT_CONST_ZERO;
        String busOrg = RMDCommonConstants.EMPTY_STRING;
        List<String> existingSiteId = null;
        boolean hasDuplicateSiteId = false;
        int[] status = { 0, 0, 0 };
        String result = RMDServiceConstants.FAILURE;
        String codeQuery = null;
        Connection objConnection = null;
        CallableStatement callableStmt = null;
        siteTypeId = getsiteTypeId(objSiteSearchVO.getStrCreateType());
        siteStatsus = getsiteStatusId(objSiteSearchVO.getStrStatus());
        StringBuilder caseQry = new StringBuilder();
        if (null == objSiteSearchVO.getBillAddObjId()
                && RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                        .getBillAddObjId())) {
            objSiteSearchVO.setBillAddObjId(objSiteSearchVO.getPrimAddObjId());
        }
        if (null == objSiteSearchVO.getShipAddObjId()
                && RMDCommonConstants.EMPTY_STRING.equals(objSiteSearchVO
                        .getShipAddObjId())) {
            objSiteSearchVO.setShipAddObjId(objSiteSearchVO.getPrimAddObjId());
        }
        try {
            session = getHibernateSession();
            codeQuery = "SELECT SITE_ID FROM TABLE_SITE WHERE SITE_ID=:siteId";
            Query query = session.createSQLQuery(codeQuery.toString());
            query.setParameter(RMDCommonConstants.SITEID,
                    objSiteSearchVO.getStrSiteID());
            existingSiteId = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(existingSiteId)) {
                hasDuplicateSiteId = true;
                result = RMDCommonConstants.SITE_EXISTS;
            }
            if (!hasDuplicateSiteId) {
                if (null != objSiteSearchVO.getStrCustomer()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSiteSearchVO.getStrCustomer())) {
                    caseQry.append(" SELECT OBJID FROM SA.TABLE_BUS_ORG WHERE ORG_ID = :customerID ");
                    Query caseHqry = session.createSQLQuery(caseQry.toString());
                    caseHqry.setParameter(RMDCommonConstants.CUSTOMERID,
                            objSiteSearchVO.getStrCustomer());
                    if (null != caseHqry.uniqueResult()) {
                        busOrg = caseHqry.uniqueResult().toString();
                    }
                    busOrgID = Integer.parseInt(busOrg);
                }
                updateTableCaseQry
                        .append(" Insert into table_site (OBJID,SITE_ID,NAME,S_NAME,EXTERNAL_ID,TYPE,LOGISTICS_TYPE,IS_SUPPORT,REGION,S_REGION,DISTRICT,S_DISTRICT,DEPOT,CONTR_LOGIN,CONTR_PASSWD,");
                updateTableCaseQry
                        .append(" IS_DEFAULT,NOTES,SPEC_CONSID,MDBK,STATE_CODE,STATE_VALUE,INDUSTRY_TYPE,APPL_TYPE,CUT_DATE,SITE_TYPE,STATUS,ARCH_IND,ALERT_IND,PHONE,FAX,DEV,UPDATE_STAMP,SHIP_VIA,");
                updateTableCaseQry
                        .append(" CHILD_SITE2SITE,SUPPORT_OFFICE2SITE,CUST_PRIMADDR2ADDRESS,CUST_BILLADDR2ADDRESS,CUST_SHIPADDR2ADDRESS,SITE_SUPPORT2EMPLOYEE,SITE_ALTSUPP2EMPLOYEE,REPORT_SITE2BUG,PRIMARY2BUS_ORG,SITE2EXCH_PROTOCOL,X_CORP_FLAG)");
                updateTableCaseQry
                        .append(" values ((select max(objid)+1 from table_site) ,:siteId,:siteName,upper(:siteName),null,:siteTypeId,0,0,null,null,null,null,null,null,null,0,null,0,null,0,null,null,null,to_date('01-JAN-53','DD-MON-RR'),:siteType,");
                updateTableCaseQry
                        .append(":siteStatus,0,0,:sitePhone,:siteFax,null,sysdate,:siteShipping,null,null,:primAddObjId,:billAddObjId,:shipAddObjId,null,null,null,:busOrg,null,'N')");
                Query updateTableCaseHQry = session
                        .createSQLQuery(updateTableCaseQry.toString());
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITEID,
                        objSiteSearchVO.getStrSiteID());
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_NAME,
                        objSiteSearchVO.getStrSiteName());
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_NAME,
                        objSiteSearchVO.getStrSiteName());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_TYPE_ID, siteTypeId);
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_TYPE,
                        objSiteSearchVO.getStrCreateType());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_STATUS, siteStatsus);
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_PHONE,
                        objSiteSearchVO.getStrCellPhone());
                updateTableCaseHQry.setParameter(RMDCommonConstants.SITE_FAX,
                        objSiteSearchVO.getStrFax());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_SHIPPING,
                        objSiteSearchVO.getStrPrefShipMethod());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_PRIM_ADD_OBJID,
                        objSiteSearchVO.getPrimAddObjId());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_BILL_ADD_OBJID,
                        objSiteSearchVO.getBillAddObjId());
                updateTableCaseHQry.setParameter(
                        RMDCommonConstants.SITE_SHIP_ADD_OBJID,
                        objSiteSearchVO.getShipAddObjId());
                updateTableCaseHQry.setParameter(RMDCommonConstants.BUS_ORG,
                        busOrg);
                status[0] = updateTableCaseHQry.executeUpdate();
                if (status[0] == 1) {
                    objConnection = getConnection(session);
                    callableStmt = objConnection
                            .prepareCall("BEGIN GETS_SD_SITEPART_PKG.INSERTSITEPART_PR(?,?,?,?); END;");
                    callableStmt.setInt(1, busOrgID);
                    callableStmt.setString(2, objSiteSearchVO.getStrSiteID());
                    callableStmt
                            .registerOutParameter(3, java.sql.Types.VARCHAR);
                    callableStmt
                            .registerOutParameter(4, java.sql.Types.VARCHAR);
                    callableStmt.execute();
                    String errormsg = callableStmt.getString(3);
                    int errorcode = callableStmt.getInt(4);
                    if (errorcode > 0) {
                        result = RMDCommonConstants.ERROR_INSERT;
                    }
                    result = RMDCommonConstants.SUCCESS;
                    LOG.debug("Exception occurred in createSite:" + errormsg);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in createSite:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_SITE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            caseQry = null;
            updateTableCaseQry = null;
            releaseSession(session);
            if (objConnection != null) {
                try {
                    objConnection.close();
                } catch (SQLException e) {
                    LOG.error(e);
                    LOG.error("Unexpected Error occured in ContactSiteDAOImpl in createSite() while closing the resultset."
                            + e.getMessage());
                }
            }
            if (callableStmt != null) {
                try {
                    callableStmt.close();
                } catch (SQLException e) {
                    LOG.error(e);
                    LOG.error("Unexpected Error occured in ContactSiteDAOImpl in createSite() while closing the resultset."
                            + e.getMessage());
                }
            }
        }
        return result;
    }

    public static int getsiteTypeId(final String data) {
        int siteTypeId = RMDCommonConstants.INT_CONST_ZERO;
        if (data.equalsIgnoreCase(RMDCommonConstants.CUST)) {
            siteTypeId = RMDCommonConstants.INT_CONST_ONE;
        } else if (data.equalsIgnoreCase(RMDCommonConstants.INTR)) {
            siteTypeId = RMDCommonConstants.INT_CONST_TWO;
        } else if (data.equalsIgnoreCase(RMDCommonConstants.RSEL)) {
            siteTypeId = RMDCommonConstants.INT_CONST_THREE;
        } else if (data.equalsIgnoreCase(RMDCommonConstants.INDV)) {
            siteTypeId = RMDCommonConstants.INT_CONST_FOUR;
        } else if (data.equalsIgnoreCase(RMDCommonConstants.VEND)) {
            siteTypeId = RMDCommonConstants.INT_CONST_FIVE;
        }
        return siteTypeId;
    }

    public static int getsiteStatusId(final String data) {
        int siteStatusId = RMDCommonConstants.INT_CONST_ZERO;
        if (data.equalsIgnoreCase(RMDCommonConstants.ACTIVATE)) {
            siteStatusId = RMDCommonConstants.INT_CONST_ZERO;
        } else if (data.equalsIgnoreCase(RMDCommonConstants.SITE_INACTIVE)) {
            siteStatusId = RMDCommonConstants.INT_CONST_ONE;
        } else if (data.equalsIgnoreCase(RMDCommonConstants.OBSOLETE)) {
            siteStatusId = RMDCommonConstants.INT_CONST_FOUR;
        }
        return siteStatusId;
    }

    /**
     * 
     * @param
     * @return List<AddressDetailsVO>
     * @throws RMDDAOException
     * @Description This method is used to get the address details for the given
     *              search combination.
     * 
     */
    @Override
    public List<AddressDetailsVO> getAddress(AddressSearchVO objAddresstSearchVO)
            throws RMDDAOException {
        Session session = null;
        StringBuilder addressQuery = new StringBuilder();
        String whereClause = RMDCommonConstants.EMPTY_STRING;
        List<AddressDetailsVO> addressDetailsVOList = null;
        AddressDetailsVO objAddressDetailsVO = null;
        List<Object> resultList = null;
        try {
            session = getHibernateSession();
            addressQuery
                    .append("SELECT ADDR.OBJID,ADDR.ADDRESS,ADDR.ADDRESS_2,ADDR.CITY,ADDR.STATE,ADDR.ZIPCODE,CON.S_NAME COUNTRY FROM TABLE_ADDRESS ADDR,TABLE_COUNTRY CON ");
            if (null != objAddresstSearchVO.getAddress()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objAddresstSearchVO.getAddress())) {
                whereClause = whereClause
                        + "UPPER(ADDRESS) LIKE UPPER(:address) ";
            }
            if (null != objAddresstSearchVO.getCity()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objAddresstSearchVO.getCity())) {
                if (!whereClause
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    whereClause = whereClause + "AND ";
                }
                whereClause = whereClause + "UPPER(CITY) LIKE UPPER(:city) ";
            }
            if (null != objAddresstSearchVO.getState()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objAddresstSearchVO.getState())) {
                if (!whereClause
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    whereClause = whereClause + "AND ";
                }
                whereClause = whereClause + " UPPER(STATE) LIKE UPPER(:state) ";
            }
            if (null != objAddresstSearchVO.getZipCode()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objAddresstSearchVO.getZipCode())) {
                if (!whereClause
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    whereClause = whereClause + "AND ";
                }
                whereClause = whereClause
                        + "UPPER(ZIPCODE) LIKE UPPER(:zipCode) ";
            }

            if (!whereClause.equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                whereClause = " WHERE ADDR.ADDRESS2COUNTRY=CON.OBJID AND "
                        + whereClause;
            } else {
                whereClause = " WHERE ADDR.ADDRESS2COUNTRY=CON.OBJID ";
            }

            addressQuery.append(whereClause);
            addressQuery.append(" ORDER BY ADDRESS ASC");

            final Query addressHQuery = session.createSQLQuery(addressQuery
                    .toString());
            if (null != objAddresstSearchVO.getAddress()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objAddresstSearchVO.getAddress())) {
                if (RMDCommonConstants.STARTS_WITH
                        .equalsIgnoreCase(objAddresstSearchVO.getAddrFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.ADDRESS,
                            objAddresstSearchVO.getAddress()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (RMDCommonConstants.ENDS_WITH
                        .equalsIgnoreCase(objAddresstSearchVO.getAddrFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.ADDRESS,
                            RMDServiceConstants.PERCENTAGE
                                    + objAddresstSearchVO.getAddress());
                }
                if (RMDCommonConstants.CONTAINS
                        .equalsIgnoreCase(objAddresstSearchVO.getAddrFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.ADDRESS,
                            RMDServiceConstants.PERCENTAGE
                                    + objAddresstSearchVO.getAddress()
                                    + RMDServiceConstants.PERCENTAGE);
                }
            }
            if (null != objAddresstSearchVO.getCity()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objAddresstSearchVO.getCity())) {
                if (RMDCommonConstants.STARTS_WITH
                        .equalsIgnoreCase(objAddresstSearchVO.getCityFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.CITY,
                            objAddresstSearchVO.getCity()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (RMDCommonConstants.ENDS_WITH
                        .equalsIgnoreCase(objAddresstSearchVO.getCityFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.CITY,
                            RMDServiceConstants.PERCENTAGE
                                    + objAddresstSearchVO.getCity());
                }
                if (RMDCommonConstants.CONTAINS
                        .equalsIgnoreCase(objAddresstSearchVO.getCityFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.CITY,
                            RMDServiceConstants.PERCENTAGE
                                    + objAddresstSearchVO.getCity()
                                    + RMDServiceConstants.PERCENTAGE);
                }
            }
            if (null != objAddresstSearchVO.getState()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objAddresstSearchVO.getState())) {
                if (RMDCommonConstants.STARTS_WITH
                        .equalsIgnoreCase(objAddresstSearchVO.getStateFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.STATE,
                            objAddresstSearchVO.getState()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (RMDCommonConstants.ENDS_WITH
                        .equalsIgnoreCase(objAddresstSearchVO.getStateFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.STATE,
                            RMDServiceConstants.PERCENTAGE
                                    + objAddresstSearchVO.getState());
                }
                if (RMDCommonConstants.CONTAINS
                        .equalsIgnoreCase(objAddresstSearchVO.getStateFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.STATE,
                            RMDServiceConstants.PERCENTAGE
                                    + objAddresstSearchVO.getState()
                                    + RMDServiceConstants.PERCENTAGE);
                }
            }
            if (null != objAddresstSearchVO.getZipCode()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objAddresstSearchVO.getZipCode())) {
                if (RMDCommonConstants.STARTS_WITH
                        .equalsIgnoreCase(objAddresstSearchVO
                                .getZipCodeFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.ZIP_CODE,
                            objAddresstSearchVO.getZipCode()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (RMDCommonConstants.ENDS_WITH
                        .equalsIgnoreCase(objAddresstSearchVO
                                .getZipCodeFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.ZIP_CODE,
                            RMDServiceConstants.PERCENTAGE
                                    + objAddresstSearchVO.getZipCode());
                }
                if (RMDCommonConstants.CONTAINS
                        .equalsIgnoreCase(objAddresstSearchVO
                                .getZipCodeFilter())) {
                    addressHQuery.setParameter(RMDCommonConstants.ZIP_CODE,
                            RMDServiceConstants.PERCENTAGE
                                    + objAddresstSearchVO.getZipCode()
                                    + RMDServiceConstants.PERCENTAGE);
                }
            }

            resultList = addressHQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                addressDetailsVOList = new ArrayList<AddressDetailsVO>(
                        resultList.size());
                for (final Iterator<Object> obj = resultList.iterator(); obj
                        .hasNext();) {
                    final Object[] contactDetails = (Object[]) obj.next();
                    objAddressDetailsVO = new AddressDetailsVO();
                    objAddressDetailsVO.setObjId(RMDCommonUtility
                            .convertObjectToString(contactDetails[0]));
                    objAddressDetailsVO.setAddress1(RMDCommonUtility
                            .convertObjectToString(contactDetails[1]));
                    objAddressDetailsVO.setAddress2(RMDCommonUtility
                            .convertObjectToString(contactDetails[2]));
                    objAddressDetailsVO.setCity(RMDCommonUtility
                            .convertObjectToString(contactDetails[3]));
                    objAddressDetailsVO.setState(RMDCommonUtility
                            .convertObjectToString(contactDetails[4]));
                    objAddressDetailsVO.setZipCode(RMDCommonUtility
                            .convertObjectToString(contactDetails[5]));
                    objAddressDetailsVO.setCountry(RMDCommonUtility
                            .convertObjectToString(contactDetails[6]));
                    addressDetailsVOList.add(objAddressDetailsVO);
                    objAddressDetailsVO = null;
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDRESS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDRESS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            resultList = null;
            addressQuery = null;
            whereClause = null;
            releaseSession(session);
        }

        return addressDetailsVOList;
    }

    /**
     * 
     * @param addrObjId
     * @return AddressDetailsVO
     * @throws RMDDAOException
     * @Description This method is used to get the all details for the selected
     *              address
     * 
     */
    @Override
    public AddressDetailsVO viewAddressDetails(String addrObjId)
            throws RMDDAOException {
        Session session = null;
        StringBuilder viewAddressQuery = new StringBuilder();
        AddressDetailsVO objAddressDetailsVO = null;
        List<Object> resultList = null;
        try {
            session = getHibernateSession();
            viewAddressQuery
                    .append("SELECT AD.OBJID,ADDRESS,ADDRESS_2,CITY,STATE,CON.S_NAME COUNTRY,ZIPCODE,TIM.NAME TIMEZONE FROM TABLE_ADDRESS AD,TABLE_STATE_PROV ST,");
            viewAddressQuery
                    .append("TABLE_COUNTRY CON ,TABLE_TIME_ZONE TIM WHERE AD.ADDRESS2TIME_ZONE=TIM.OBJID AND  AD.ADDRESS2COUNTRY=CON.OBJID ");
            viewAddressQuery
                    .append("AND AD.ADDRESS2STATE_PROV=ST.OBJID AND AD.OBJID=:addrObjId");
            final Query viewAddressHQuery = session
                    .createSQLQuery(viewAddressQuery.toString());
            viewAddressHQuery.setParameter(RMDCommonConstants.ADDRESS_OBJID,
                    addrObjId);
            resultList = viewAddressHQuery.list();
            if (!resultList.isEmpty()) {
                final Object[] addressDetails = (Object[]) resultList.get(0);
                if (null != addressDetails) {
                    objAddressDetailsVO = new AddressDetailsVO();
                    objAddressDetailsVO.setObjId(RMDCommonUtility
                            .convertObjectToString(addressDetails[0]));
                    objAddressDetailsVO.setAddress1(RMDCommonUtility
                            .convertObjectToString(addressDetails[1]));
                    objAddressDetailsVO.setAddress2(RMDCommonUtility
                            .convertObjectToString(addressDetails[2]));
                    objAddressDetailsVO.setCity(RMDCommonUtility
                            .convertObjectToString(addressDetails[3]));
                    objAddressDetailsVO.setState(RMDCommonUtility
                            .convertObjectToString(addressDetails[4]));
                    objAddressDetailsVO.setCountry(RMDCommonUtility
                            .convertObjectToString(addressDetails[5]));
                    objAddressDetailsVO.setZipCode(RMDCommonUtility
                            .convertObjectToString(addressDetails[6]));
                    objAddressDetailsVO.setTimeZone(RMDCommonUtility
                            .convertObjectToString(addressDetails[7]));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VIEW_ADDRESS_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VIEW_ADDRESS_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            resultList = null;
            viewAddressQuery = null;

        }
        return objAddressDetailsVO;
    }

    /**
     * 
     * @param
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to get the all country List
     * 
     */
    @Override
    public String getCountryList() throws RMDDAOException {
        Session session = null;
        StringBuilder countryQuery = null;
        List<Object> resultList = null;
        List<String> countryList = null;
        String countryDetails = RMDCommonConstants.EMPTY_STRING;
        try {
            countryQuery = new StringBuilder();
            session = getHibernateSession();
            countryQuery
                    .append("SELECT DISTINCT S_NAME,IS_DEFAULT  FROM TABLE_COUNTRY ORDER BY IS_DEFAULT DESC");
            final Query countryHQuery = session.createSQLQuery(countryQuery
                    .toString());
            resultList = countryHQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                countryList = new ArrayList<String>();
                for (final Iterator<Object> obj = resultList.iterator(); obj
                        .hasNext();) {
                    final Object[] objCountry = (Object[]) obj.next();
                    countryList.add(RMDCommonUtility
                            .convertObjectToString(objCountry[0]));
                }
                Iterator<String> it = countryList.iterator();
                countryDetails = StringUtils.join(it,
                        RMDCommonConstants.COMMMA_SEPARATOR);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_COUNTRY_LIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_COUNTRY_LIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            resultList = null;
            countryQuery = null;
            countryList = null;

        }
        return countryDetails;
    }

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
    @Override
    public String getCountryStates(String country) throws RMDDAOException {
        Session session = null;
        StringBuilder countryStateQuery = null;
        List<Object> resultList = null;
        List<String> stateList = null;
        String stateDetails = RMDCommonConstants.EMPTY_STRING;
        try {
            countryStateQuery = new StringBuilder();
            session = getHibernateSession();
            countryStateQuery
                    .append("SELECT DISTINCT ST.NAME, ST.IS_DEFAULT FROM TABLE_STATE_PROV ST,TABLE_COUNTRY WHERE  STATE_PROV2COUNTRY=TABLE_COUNTRY.OBJID ");
            countryStateQuery
                    .append("AND TABLE_COUNTRY.S_NAME=:country ORDER BY IS_DEFAULT DESC");
            final Query stateHQuery = session.createSQLQuery(countryStateQuery
                    .toString());
            stateHQuery.setParameter(RMDCommonConstants.COUNTRY, country);
            resultList = stateHQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                stateList = new ArrayList<String>();
                for (final Iterator<Object> obj = resultList.iterator(); obj
                        .hasNext();) {
                    final Object[] objCountry = (Object[]) obj.next();
                    stateList.add(RMDCommonUtility
                            .convertObjectToString(objCountry[0]));
                }
                Iterator<String> it = stateList.iterator();
                stateDetails = StringUtils.join(it,
                        RMDCommonConstants.COMMMA_SEPARATOR);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_COUNTRY_STATES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_COUNTRY_STATES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            resultList = null;
            countryStateQuery = null;
            stateList = null;

        }
        return stateDetails;
    }

    /**
     * 
     * @param String
     *            ADMIN_SERVICE_GET_SYSTEM_TIME_ZONES country
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to get the time zones for the selected
     *              Country
     * 
     */
    @Override
    public String getCountryTimeZones(String country) throws RMDDAOException {
        Session session = null;
        StringBuilder countryTimeZoneQuery = null;
        List<Object> resultList = null;
        List<String> timeZoneList = null;
        String timeZoneDetails = RMDCommonConstants.EMPTY_STRING;
        try {
            countryTimeZoneQuery = new StringBuilder();
            session = getHibernateSession();
            countryTimeZoneQuery
                    .append("SELECT DISTINCT ZONES.NAME,ZONES.IS_DEFAULT  FROM MTM_COUNTRY1_TIME_ZONE0  t, TABLE_TIME_ZONE ZONES , TABLE_COUNTRY CON ");
            countryTimeZoneQuery
                    .append("WHERE t.country2time_zone=CON.OBJID AND t.time_zone2country=ZONES.OBJID AND CON.S_NAME=:country ORDER BY IS_DEFAULT DESC");
            final Query timeZoneHQuery = session
                    .createSQLQuery(countryTimeZoneQuery.toString());
            timeZoneHQuery.setParameter(RMDCommonConstants.COUNTRY, country);
            resultList = timeZoneHQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                timeZoneList = new ArrayList<String>();
                for (final Iterator<Object> obj = resultList.iterator(); obj
                        .hasNext();) {
                    final Object[] objCountry = (Object[]) obj.next();
                    timeZoneList.add(RMDCommonUtility
                            .convertObjectToString(objCountry[0]));
                }
                if (timeZoneList.contains(RMDCommonConstants.NONE)) {
                    timeZoneList.remove(RMDCommonConstants.NONE);
                }
                Iterator<String> it = timeZoneList.iterator();
                timeZoneDetails = StringUtils.join(it,
                        RMDCommonConstants.COMMMA_SEPARATOR);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_COUNTRY_TIMEZONES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_COUNTRY_TIMEZONES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            resultList = null;
            countryTimeZoneQuery = null;
            timeZoneList = null;

        }
        return timeZoneDetails;
    }

    /**
     * 
     * @param AddressDetailsVO
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to add or update the Address details
     * 
     */
    @Override
    public String addOrUpdateAddress(AddressDetailsVO objAddressDetailsVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        StringBuilder insertQry = null;
        StringBuilder updateQry = null;
        StringBuilder addrObjIdQry = null;
        List<Object> resultList = null;
        String addressObjId = RMDCommonConstants.EMPTY_STRING;
        try {
            session = getHibernateSession();
            if (!RMDCommonUtility.isNullOrEmpty(objAddressDetailsVO
                    .getFromScreen())
                    && RMDCommonConstants.ADD_ADDRESS
                            .equalsIgnoreCase(objAddressDetailsVO
                                    .getFromScreen())) {
                addrObjIdQry = new StringBuilder();
                addrObjIdQry.append("SELECT MAX(OBJID)+1 FROM TABLE_ADDRESS");
                final Query addrObjIdHQry = session.createSQLQuery(addrObjIdQry
                        .toString());
                resultList = addrObjIdHQry.list();
                if (null != resultList && !resultList.isEmpty()) {
                    addressObjId = RMDCommonUtility
                            .convertObjectToString(resultList.get(0));
                }
                if (!RMDCommonUtility.isNullOrEmpty(addressObjId)) {
                    insertQry = new StringBuilder();
                    insertQry
                            .append("INSERT INTO TABLE_ADDRESS (OBJID,ADDRESS,S_ADDRESS,CITY,S_CITY,STATE,S_STATE,ZIPCODE,ADDRESS_2,DEV,UPDATE_STAMP,ADDRESS2TIME_ZONE,");
                    insertQry
                            .append("ADDRESS2COUNTRY,ADDRESS2STATE_PROV) VALUES (:addrObjId,:address1,UPPER(:address1),:city,UPPER(:city),");
                    insertQry
                            .append(":state,UPPER(:state),:zipCode,:address2,null,SYSDATE,(SELECT DISTINCT OBJID FROM TABLE_TIME_ZONE WHERE NAME=:timeZone),");
                    insertQry
                            .append("(SELECT DISTINCT OBJID FROM TABLE_COUNTRY WHERE S_NAME=:country),(SELECT DISTINCT OBJID FROM TABLE_STATE_PROV WHERE NAME=:state AND STATE_PROV2COUNTRY = (SELECT objid FROM TABLE_COUNTRY WHERE S_NAME=:stateCountry)))");
                    final Query insertHQry = session.createSQLQuery(insertQry
                            .toString());
                    insertHQry.setParameter(RMDCommonConstants.ADDRESS_OBJID,
                            addressObjId);
                    insertHQry.setParameter(RMDCommonConstants.ADDRESS_1,
                            objAddressDetailsVO.getAddress1());
                    insertHQry.setParameter(RMDCommonConstants.CITY,
                            objAddressDetailsVO.getCity());
                    insertHQry.setParameter(RMDCommonConstants.STATE,
                            objAddressDetailsVO.getState());
                    insertHQry.setParameter(RMDCommonConstants.ZIP_CODE,
                            objAddressDetailsVO.getZipCode());
                    insertHQry.setParameter(RMDCommonConstants.ADDRESS_2,
                            objAddressDetailsVO.getAddress2());
                    insertHQry.setParameter(RMDCommonConstants.TIME_ZONE,
                            objAddressDetailsVO.getTimeZone());
                    insertHQry.setParameter(RMDCommonConstants.COUNTRY,
                            objAddressDetailsVO.getCountry());
                    insertHQry.setParameter(RMDCommonConstants.STATE_COUNTRY,
                            objAddressDetailsVO.getCountry());
                    insertHQry.executeUpdate();
                    result = RMDCommonConstants.SUCCESS
                            + RMDCommonConstants.HYPHEN + addressObjId;
                }
            } else if (!RMDCommonUtility.isNullOrEmpty(objAddressDetailsVO
                    .getFromScreen())
                    && RMDCommonConstants.UPDATE_ADDRESS
                            .equalsIgnoreCase(objAddressDetailsVO
                                    .getFromScreen())) {
                updateQry = new StringBuilder();
                updateQry
                        .append("UPDATE TABLE_ADDRESS SET ADDRESS=:address1,ADDRESS_2=:address2,CITY=:city,STATE=:state,ZIPCODE=:zipCode, ");
                updateQry
                        .append("ADDRESS2COUNTRY=(SELECT OBJID FROM TABLE_COUNTRY WHERE S_NAME=:country),ADDRESS2TIME_ZONE=(SELECT OBJID FROM TABLE_TIME_ZONE ");
                updateQry
                        .append("WHERE NAME=:timeZone),ADDRESS2STATE_PROV=(SELECT OBJID FROM TABLE_STATE_PROV WHERE NAME=:state AND STATE_PROV2COUNTRY = (SELECT objid FROM TABLE_COUNTRY WHERE S_NAME=:stateCountry)) WHERE OBJID=:addrObjId");
                final Query updateHQry = session.createSQLQuery(updateQry
                        .toString());
                updateHQry.setParameter(RMDCommonConstants.ADDRESS_1,
                        objAddressDetailsVO.getAddress1());
                updateHQry.setParameter(RMDCommonConstants.ADDRESS_2,
                        objAddressDetailsVO.getAddress2());
                updateHQry.setParameter(RMDCommonConstants.CITY,
                        objAddressDetailsVO.getCity());
                updateHQry.setParameter(RMDCommonConstants.STATE,
                        objAddressDetailsVO.getState());
                updateHQry.setParameter(RMDCommonConstants.ZIP_CODE,
                        objAddressDetailsVO.getZipCode());
                updateHQry.setParameter(RMDCommonConstants.COUNTRY,
                        objAddressDetailsVO.getCountry());
                updateHQry.setParameter(RMDCommonConstants.STATE_COUNTRY,
                        objAddressDetailsVO.getCountry());
                updateHQry.setParameter(RMDCommonConstants.TIME_ZONE,
                        objAddressDetailsVO.getTimeZone());
                updateHQry.setParameter(RMDCommonConstants.ADDRESS_OBJID,
                        objAddressDetailsVO.getObjId());
                updateHQry.executeUpdate();
                result = RMDCommonConstants.SUCCESS + RMDCommonConstants.HYPHEN
                        + objAddressDetailsVO.getObjId();
            }
        } catch (RMDDAOConnectionException ex) {
            result = RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_OR_UPDATE_ADDRESS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            result = RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_OR_UPDATE_ADDRESS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            insertQry = null;
            updateQry = null;
            addrObjIdQry = null;
            resultList = null;
            releaseSession(session);
        }
        return result;
    }
    
    /**
     * 
     * @param
     * @return Map<String, String>
     * @throws RMDDAOException
     * @Description This method is used to get ISD Code list
     * 
     */
    @Override
    public List<ISDCodeVO> getISDCode() throws RMDDAOException {
        Session session = null;
        StringBuilder isdCodeQuery = null;
        List<Object> resultList = null;
        List<ISDCodeVO> isdCodeList = new ArrayList<ISDCodeVO>();
        ISDCodeVO isdCode = null;
        try {
        	isdCodeQuery = new StringBuilder();
            session = getHibernateSession();
            isdCodeQuery
                    .append("SELECT S_NAME,CODE  FROM TABLE_COUNTRY ORDER BY IS_DEFAULT desc, S_NAME ASC");
            final Query isdHQuery = session.createSQLQuery(isdCodeQuery
                    .toString());
            resultList = isdHQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (final Iterator<Object> obj = resultList.iterator(); obj
                        .hasNext();) {
                    final Object[] objISDCode = (Object[]) obj.next();
                    isdCode = new ISDCodeVO(RMDCommonUtility
                            .convertObjectToString(objISDCode[0]), RMDCommonUtility
                            .convertObjectToString(objISDCode[1]));
                    isdCodeList.add(isdCode);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_COUNTRY_LIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_COUNTRY_LIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            resultList = null;
            isdCodeQuery = null;
        }
        return isdCodeList;
    }
}

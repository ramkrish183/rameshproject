/**
 * 
 */
package com.ge.trans.eoa.services.kep.common.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.kep.hibernate.valueobjects.GetKmKepLookupMultilangHVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ge.trans.eoa.services.kep.common.constants.KEPServiceConstants;
import com.ge.trans.eoa.services.kep.common.dao.intf.BaseDAOIntf;

/**
 * @author krishbal
 */
public class BaseDAOImpl extends RMDCommonDAO implements BaseDAOIntf {
    /**
     * This is a common Lookup Method used to load the frequently accessed
     * values
     * 
     * @param strListName
     * @return list of ElementVO
     * @throws KEPDAOException
     */
    @Override
    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDDAOException {
        Session hibernateSession = null;
        List<GetKmKepLookupMultilangHVO> arlResult = null;
        List<ElementVO> arlLookUpDetails = new ArrayList<ElementVO>();
        try {

            hibernateSession = getHibernateSession();
            Criteria criteria = hibernateSession.createCriteria(GetKmKepLookupMultilangHVO.class)
                    .setFetchMode(RMDCommonConstants.GETKMKEP_LOOKUP, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKMKEP_LOOKUP, RMDCommonConstants.LOOKUP)
                    .add(Restrictions.eq(RMDCommonConstants.LANGUAGE, strLanguage))
                    .add(Restrictions.eq(RMDCommonConstants.LOOKUP_LISTNAME, strListName));

            arlResult = criteria.list();
            for (Iterator iterator = arlResult.iterator(); iterator.hasNext();) {
                GetKmKepLookupMultilangHVO getKmKepLookupMultilangHVO = (GetKmKepLookupMultilangHVO) iterator.next();
                ElementVO objElementVO = new ElementVO();
                objElementVO.setId(Integer.toString(getKmKepLookupMultilangHVO.getGetKmKepLookupMultilSeqId()));
                objElementVO.setName(getKmKepLookupMultilangHVO.getDisplayName());
                arlLookUpDetails.add(objElementVO);

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_DATADETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlLookUpDetails;
    }

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */
    @Override
    public List getSymptom(final String strCustomer) throws RMDDAOException {
        List<ElementVO> arlSymptom = new ArrayList<ElementVO>();
        Session hibernateSession = null;
        Query hibQuery = null;
        String strSymptom = null;
        List result;
        try {
            hibernateSession = getHibernateSession();
            Codec ORACLE_CODEC = new OracleCodec();
            String Qry = "SELECT DISTINCT(ID.CAT3) FROM GETKMRMFAILUREINFOHVO WHERE ID.CAT3 IS NOT NULL";
            if (null != strCustomer && !strCustomer.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                Qry = Qry + " AND ID.LINKCUSTOMER IN('"
                        + ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strCustomer).replace(",", "','") + "')";
            }
            hibQuery = hibernateSession.createQuery(Qry);
            result = hibQuery.list();

            for (Iterator iterator = result.iterator(); iterator.hasNext();) {
                ElementVO objElementVO = new ElementVO();
                strSymptom = (String) iterator.next();
                objElementVO.setId(strSymptom);
                objElementVO.setName(strSymptom);
                arlSymptom.add(objElementVO);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_DATADETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }

        return arlSymptom;

    }

    /**
     * This method is used for fetching the RootCause from DB
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */
    @Override
    public List getRootCause(final String strCustomer) throws RMDDAOException {
        List<ElementVO> arlRootCause = new ArrayList<ElementVO>();
        Session hibernateSession = null;
        Query hibQuery = null;
        String strRootCause = null;
        List result;
        Codec ORACLE_CODEC = new OracleCodec();
        try {
            hibernateSession = getHibernateSession();
            String Qry = "SELECT DISTINCT(ID.CAT1) FROM GETKMRMFAILUREINFOHVO WHERE ID.CAT1 IS NOT NULL";
            if (null != strCustomer && !strCustomer.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                Qry = Qry + " AND ID.LINKCUSTOMER IN('"
                        + ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strCustomer).replace(",", "','") + "')";
            }

            hibQuery = hibernateSession.createQuery(Qry);
            result = hibQuery.list();
            for (Iterator iterator = result.iterator(); iterator.hasNext();) {

                ElementVO objElementVO = new ElementVO();
                strRootCause = (String) iterator.next();
                objElementVO.setId(strRootCause);
                objElementVO.setName(strRootCause);
                arlRootCause.add(objElementVO);

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_DATADETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlRootCause;

    }

}

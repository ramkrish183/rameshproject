package com.ge.trans.eoa.services.kep.common.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class BaseDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @Author:
     * @param uName
     * @return
     * @Description:This method is used for get the hibernate session.
     */
    protected Session getHibernateSession() throws RMDDAOConnectionException {
        Session hibernateSession = null;
        try {
            hibernateSession = SessionFactoryUtils.getNewSession(sessionFactory);
        } catch (Exception e) {

            final String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOConnectionException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.FATAL_ERROR);
        }
        return hibernateSession;
    }

    protected void releaseSession(final Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}

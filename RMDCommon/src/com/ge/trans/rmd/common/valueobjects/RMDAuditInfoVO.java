/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDAuditInfoVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.services.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.common.valueobjects;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.HibernateException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDAuditInfoVO extends BaseVO {

    public RMDAuditInfoVO() {
        super();
    }

    public static final long serialVersionUID = 63565412L;
    private Date lastUpdatedDate;
    private Date creationDate;
    private String lastUpdatedBy;
    private String createdBy;
    private static final int[] SQL_TYPES = new int[] { Types.TIMESTAMP, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT };

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    /**
     * @Author:
     * @param lastUpdatedDate
     * @Description:
     */
    public void setLastUpdatedDate(final Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @Author:
     * @param creationDate
     * @Description:
     */
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @Author:
     * @param lastUpdatedBy
     * @Description:
     */
    public void setLastUpdatedBy(final String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @Author:
     * @param createdBy
     * @Description:
     */
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public boolean isMutable() {
        return true;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Class returnedClass() {
        return RMDAuditInfoVO.class;
    }

    /**
     * @Author:
     * @param x
     * @param y
     * @return
     * @Description:
     */
    public boolean equals(Object x, Object y) {
        return false;
    }

    /**
     * @Author:
     * @param value
     * @return
     * @Description:
     */
    public Object deepCopy(Object value) {
        return null;
    }

    /**
     * @Author:
     * @param rs
     * @param names
     * @param owner
     * @return
     * @throws HibernateException
     * @throws SQLException
     * @Description:
     */
    public Object nullSafeGet(ResultSet rs, final String[] names, final Object owner)
            throws HibernateException, SQLException {
        return null;
    }

    /**
     * @Author:
     * @param st
     * @param value
     * @param index
     * @throws HibernateException
     * @throws SQLException
     * @Description:
     */
    public void nullSafeSet(PreparedStatement st, Object value, final int index)
            throws HibernateException, SQLException {
    }

    /**
     * @Author:
     * @param arg0
     * @param arg1
     * @return
     * @throws HibernateException
     * @Description:
     */
    public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
        return null;
    }

    /**
     * @Author:
     * @param arg0
     * @return
     * @throws HibernateException
     * @Description:
     */
    public Serializable disassemble(Object arg0) throws HibernateException {
        return null;
    }

    /**
     * @Author:
     * @param arg0
     * @return
     * @throws HibernateException
     * @Description:
     */
    public int hashCode(Object arg0) throws HibernateException {
        return 0;
    }

    /**
     * @Author:
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @throws HibernateException
     * @Description:
     */
    public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("lastUpdatedDate", lastUpdatedDate)
                .append("creationDate", creationDate).append("lastUpdatedBy", lastUpdatedBy)
                .append("createdBy", createdBy).toString();
    }
}

/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   BaseMessage.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */

package com.ge.trans.rmd.omi.beans.msg;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ge.trans.rmd.omi.beans.Date;

public abstract class BaseMessage implements Serializable {

    private static final long serialVersionUID = 1554061500756371898L;
    protected Map<String, String> descriptions = null;

    /**
     * Gets the to string style.
     * 
     * @return the to string style
     */
    public ToStringStyle getToStringStyle() {
        StandardToStringStyle stdStyle = new StandardToStringStyle();

        stdStyle.setUseIdentityHashCode(false);
        stdStyle.setContentStart("{");
        stdStyle.setContentEnd("}");
        stdStyle.setFieldSeparator(";");
        return stdStyle;
    }

    /**
     * Gets the string.
     * 
     * @param value
     *            the value
     * @return the string
     */
    public final String getString(String value) {
        return trimToNull(value);
    }

    /**
     * Gets the string.
     * 
     * @param value
     *            the value
     * @return the string
     */
    public final String getString(Object value) {
        return trimToNull(String.valueOf(value));
    }

    /**
     * Gets the integer.
     * 
     * @param value
     *            the value
     * @return the integer
     */
    public final Integer getInteger(String value) {
        String localvalue = value;
        localvalue = trimToNull(localvalue);
        return ((localvalue != null) ? (Integer.valueOf(localvalue)) : null);
    }

    /**
     * Gets the long.
     * 
     * @param value
     *            the value
     * @return the long
     */
    public final Long getLong(String value) {
        String localvalue = value;
        localvalue = trimToNull(localvalue);
        return ((localvalue != null) ? (Long.valueOf(localvalue)) : null);
    }

    /**
     * Gets the float.
     * 
     * @param value
     *            the value
     * @return the float
     */
    public final Float getFloat(String value) {
        String localvalue = value;
        localvalue = trimToNull(localvalue);
        return ((localvalue != null) ? (new Float(localvalue)) : null);
    }

    /**
     * Gets the double.
     * 
     * @param value
     *            the value
     * @return the double
     */
    public final Double getDouble(String value) {
        String localvalue = value;
        localvalue = trimToNull(localvalue);
        return ((localvalue != null) ? (new Double(localvalue)) : null);
    }

    /**
     * Gets the date.
     * 
     * @param value
     *            the value
     * @return the date
     */
    public final com.ge.trans.rmd.omi.beans.Date getDate(Long value) {
        java.util.Date date = new java.util.Date(value * 1000);
        return (new Date(date));
    }

    /**
     * Trim to null.
     * 
     * @param value
     *            the value
     * @return the string
     */
    public static final String trimToNull(String value) {
        return StringUtils.trimToNull(value);
    }

    /**
     * Trim to empty.
     * 
     * @param value
     *            the value
     * @return the string
     */
    public static final String trimToEmpty(String value) {
        return StringUtils.trimToEmpty(value);
    }

    /**
     * Gets the description.
     * 
     * @param attrName
     *            the attr name
     * @return the description
     */
    protected String getDescription(String attrName) {
        Map<String, String> descriptions = getAttributeDescriptions();
        if (descriptions != null && !descriptions.isEmpty()) {
            return descriptions.get(attrName);
        }

        return null;
    }

    /**
     * Gets the attribute descriptions.
     * 
     * @return the attribute descriptions
     */
    protected Map<String, String> getAttributeDescriptions() {
        return descriptions;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return toString(this.getClass());
    }

    /**
     * To string.
     * 
     * @param clsObj
     *            the cls obj
     * @return the string
     */
    public final String toString(Class<?> clsObj) {
        StringBuilder sBuffer = new StringBuilder();
        sBuffer.append(clsObj.getName());
        sBuffer.append("@");
        sBuffer.append(hashCode());
        sBuffer.append("[\n");

        Class<?> classObject = clsObj;
        do {
            sBuffer.append(getFieldNameValue(classObject));
            classObject = classObject.getSuperclass();
        } while (classObject != BaseMessage.class);

        sBuffer.append("]");

        return ((sBuffer != null) ? sBuffer.toString() : "null");
    }

    /**
     * Gets the field name value.
     * 
     * @param clsObj
     *            the cls obj
     * @return the field name value
     */
    private String getFieldNameValue(Class<?> clsObj) {
        Field[] fields = clsObj.getDeclaredFields();
        StringBuilder sBuffer = null;
        Object obj = null;
        boolean condition = false;

        if (fields != null && fields.length > 0) {
            sBuffer = new StringBuilder();
            Set<String> nameValue = null;

            for (Field field : fields) {
                if (field != null) {
                    if (nameValue == null) {
                        nameValue = new TreeSet<String>();
                    }

                    try {
                        if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isProtected(field.getModifiers())) {
                            field.setAccessible(true);
                            obj = field.get(this);

                            if (obj != null) {

                                if (obj instanceof String)
                                    condition = !"".equals(obj);
                                else if (obj instanceof Collection<?>)
                                    condition = !((Collection<?>) obj).isEmpty();
                                else if (obj instanceof Map<?, ?>)
                                    condition = !((Map<?, ?>) obj).isEmpty();
                                else
                                    condition = true;

                                if (condition) {
                                    if (getAttributeDescriptions() != null && !getAttributeDescriptions().isEmpty()) {
                                        if (getAttributeDescriptions().containsKey(field.getName())) {
                                            nameValue.add(getDescription(field.getName()) + "=" + obj);
                                        } else {
                                            nameValue.add(field.getName() + "=" + obj);
                                        }
                                    } else {
                                        nameValue.add(field.getName() + "=" + obj);
                                    }
                                }
                            }
                        }
                    } catch (IllegalAccessException iae) {
                    }
                }
            }

            int count = 0;
            for (String str : nameValue) {
                count++;
                sBuffer.append("  ");
                sBuffer.append(str);
                sBuffer.append("\n");
            }

            return sBuffer.toString();
        }

        return null;
    }
}
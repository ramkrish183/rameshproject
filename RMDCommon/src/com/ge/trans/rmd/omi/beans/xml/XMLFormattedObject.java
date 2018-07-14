/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   XMLFormattedObject.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   XMLFormattedObject.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */

package com.ge.trans.rmd.omi.beans.xml;

import org.apache.commons.lang.StringUtils;

import com.ge.trans.rmd.omi.beans.BaseVO;

public abstract class XMLFormattedObject extends BaseVO {

    protected final String XML_TAG_START = "<";
    protected final String XML_TAG_END = ">";
    protected final String XML_TAG_SLASH = "/";
    protected final String XML_B2B_ROOT = "ROOT";
    protected final String XML_ENCODING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    protected final String XML_NAMESPACE = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
    protected final String XML_NAMESPACE_URL = "xmlns=\"http://www.getransportation.com\"";
    protected final String XML_SCHEMA = "xsi:noNamespaceSchemaLocation=\"T8.xsd\"";
    public static final int MSG_TRIGGER_TYPE_COMMISSIONING_ACK = 0;
    public static final int MSG_TRIGGER_TYPE_ACKNOWLEDGEMENT = 1;
    public static final int MSG_TRIGGER_TYPE_ASSET_UPDATE = 2;

    /**
     * To xml.
     * 
     * @return the string
     */
    public abstract String toXML();

    /**
     * Gets the xML for tag.
     * 
     * @param strTagName
     *            the str tag name
     * @param strValue
     *            the str value
     * @return the xML for tag
     */
    protected String getXMLForTag(final String strTagName, String strValue) {
        String localstrValue = strValue;
        if (strTagName != null && !"".equals(strTagName)) {
            localstrValue = StringUtils.trimToEmpty(localstrValue);

            StringBuilder strBuffer = new StringBuilder("");
            strBuffer.append(XML_TAG_START).append(strTagName).append(XML_TAG_END)

                    .append(localstrValue)

                    .append(XML_TAG_START).append(XML_TAG_SLASH).append(strTagName).append(XML_TAG_END);

            return strBuffer.toString();
        }

        return null;
    }

    /**
     * Gets the attribute.
     * 
     * @param strAttr
     *            the str attr
     * @param strValue
     *            the str value
     * @return the attribute
     */
    protected String getAttribute(String strAttr, String strValue) {
        return getAttribute(strAttr, strValue, false);
    }

    /**
     * Gets the attribute.
     * 
     * @param strAttr
     *            the str attr
     * @param strValue
     *            the str value
     * @param returnEmptyAttribute
     *            the return empty attribute
     * @return the attribute
     */
    protected String getAttribute(final String strAttr, String strValue, boolean returnEmptyAttribute) {
        String strReturn = "";
        String localstrValue = strValue;

        if (localstrValue == null) {
            localstrValue = "";
        }

        strReturn = " " + strAttr + "=\"" + localstrValue + "\"";

        if (!returnEmptyAttribute && (localstrValue == null || "".equals(localstrValue))) {
            strReturn = "";
        }

        return strReturn;
    }
}
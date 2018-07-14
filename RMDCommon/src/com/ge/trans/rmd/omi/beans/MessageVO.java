/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   MessageVO.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   MessageVO.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */
package com.ge.trans.rmd.omi.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.omi.beans.xml.XMLMessageHeaderVO;
import com.ge.trans.rmd.omi.beans.xml.XMLMessageRetryVO;
import com.ge.trans.rmd.omi.exception.CommException;
import com.ge.trans.rmd.omi.exception.MessagingException;

/**
 * This class contains setter and getter methods for Message Header attributes.
 */

public class MessageVO extends BaseVO {

    private static final long serialVersionUID = -3760936416007450897L;

    private XMLMessageHeaderVO objMessageHeader = null;

    private XMLMessageRetryVO objMessageRetryHeader = null;

    private byte[] arrMessageData = null;

    private byte[] arrHashCode = null;

    private String strMessageHeader = null;

    private String strMessageRetryHeader = null;

    private int intJMSPriority = -1;// priority of received message

    public static final String MESSAGE_ID_SEPARATOR = ".";

    /**
     * @return the objMessageRetryHeader
     */
    public XMLMessageRetryVO getObjMessageRetryHeader() {
        return objMessageRetryHeader;
    }

    /**
     * @param objMessageRetryHeader
     *            the objMessageRetryHeader to set
     */
    public void setObjMessageRetryHeader(XMLMessageRetryVO objMessageRetryHeader) {
        this.objMessageRetryHeader = objMessageRetryHeader;
    }

    /**
     * @return byte[]arrMessageData
     * @param void
     */
    public byte[] getArrMessageData() {
        return arrMessageData;
    }

    /**
     * @return void
     * @param arrMessageData
     *            the arrMessageData to set
     */
    public void setArrMessageData(byte[] arrMessageData) {
        this.arrMessageData = arrMessageData;
    }

    /**
     * @return the arrHashCode
     */
    public byte[] getArrHashCode() {
        return arrHashCode;
    }

    /**
     * @param arrHashCode
     *            the arrHashCode to set
     */
    public void setArrHashCode(byte[] arrHashCode) {
        this.arrHashCode = arrHashCode;
    }

    /**
     * @return the objMessageHeader
     * @param void
     */
    public XMLMessageHeaderVO getObjMessageHeader() {
        return objMessageHeader;
    }

    /**
     * @return void
     * @param objMessageHeader
     *            the objMessageHeader to set
     */
    public void setObjMessageHeader(XMLMessageHeaderVO objMessageHeader) {
        this.objMessageHeader = objMessageHeader;
    }

    /**
     * @return byte[]
     * @param void
     * @throws MessagingException
     */
    public byte[] getMessageBytesWithHeader() throws MessagingException {
        byte[] arrByteData = null;
        try {
            if (null != getStrMessageRetryHeader()) {
                arrByteData = concatBytes(getStrMessageRetryHeader().getBytes(), getStrMessageHeader().getBytes(),
                        getArrHashCode(), getArrMessageData());
            } else {
                arrByteData = concatBytes(getStrMessageHeader().getBytes(), getArrHashCode(), getArrMessageData());
            }
        } catch (Exception e) {
            throw new MessagingException("Exception Occured in MessageVO : getMessageDataAsHexString() method", null,
                    e);
        }
        return arrByteData;
    }

    /**
     * @return String
     * @param void
     */
    public String getMessageID() {
        String msgId = null;
        if (null != objMessageHeader.getStrMMMM() && null != objMessageHeader.getStrFRV()) {
            msgId = objMessageHeader.getStrMMMM() + MESSAGE_ID_SEPARATOR + objMessageHeader.getStrFRV();
        }
        return msgId;
    }

    /**
     * @return String
     * @param void
     * @throws MessagingException
     */
    public String getMessageDataAsHexString() throws MessagingException {
        String strHexToString = null;
        try {
            strHexToString = convertToHexString(getArrMessageData());
        } catch (Exception e) {
            throw new MessagingException("Exception Occured in MessageVO : getMessageDataAsHexString() method", null,
                    e);
        }
        return strHexToString;
    }

    /**
     * @return String
     * @param void
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("objMessageHeader", objMessageHeader.toXML())
                .append("arrMessageData", arrMessageData).toString();

    }

    /**
     * This method will concatenates four byte arrays in order resulting in a
     * byte array.
     * 
     * @param byte[]
     *            a, byte[] b, byte[] c, byte[] d
     * @return byte[]
     * @throws CommException
     */

    private byte[] concatBytes(byte[] a, byte[] b, byte[] c, byte[] d) throws MessagingException {

        byte[] f = new byte[a.length + b.length + c.length + d.length];

        try {
            for (int i = 0; i < a.length; i++) {
                f[i] = a[i];
            }

            for (int i = 0; i < b.length; i++) {
                f[i + a.length] = b[i];
            }

            for (int i = 0; i < c.length; i++) {
                f[i + a.length + b.length] = c[i];
            }

            for (int i = 0; i < d.length; i++) {
                f[i + a.length + b.length + c.length] = d[i];
            }

        } catch (Exception e) {
            throw new MessagingException("Exception Occured in MessageVO : concatBytes() method", null, e);
        }
        return f;
    }

    /**
     * This method will concatenates 3 byte arrays in order resulting in a byte
     * array.
     * 
     * @param byte[]
     *            a, byte[] b, byte[] c
     * @return byte[]
     * @throws CommException
     */

    private byte[] concatBytes(byte[] a, byte[] b, byte[] c) throws MessagingException {

        byte[] f = new byte[a.length + b.length + c.length];

        try {
            for (int i = 0; i < a.length; i++) {
                f[i] = a[i];
            }

            for (int i = 0; i < b.length; i++) {
                f[i + a.length] = b[i];
            }

            for (int i = 0; i < c.length; i++) {
                f[i + a.length + b.length] = c[i];
            }

        } catch (Exception e) {
            throw new MessagingException("Exception Occured in MessageVO : concatBytes() method", null, e);
        }
        return f;
    }

    /**
     * This method will convert the byte array into string by spliting byte to
     * nibble in HEX format.
     * 
     * @param byte[]
     *            bfour
     * @return string
     * @throws CommException
     */

    private String convertToHexString(byte[] bfour) throws MessagingException {
        byte bnew[] = null;
        String retfour = null;

        try {
            bnew = bfour;

            for (int count = 0; count < bfour.length; count++) {
                int inew = bnew[count];

                if (inew < 0) {
                    inew = inew + 256;
                }

                // for first byte
                String strnew = Integer.toString(inew, 16);
                String strsub1;
                String strsub2;

                if (strnew.length() < 2) {
                    strsub1 = strnew.substring(0, 1);
                    if (retfour == null) {
                        retfour = "0" + strsub1;
                    } else {
                        retfour = retfour + "0" + strsub1;
                    }
                } else if (strnew.length() == 2) {
                    strsub1 = strnew.substring(0, 1);
                    strsub2 = strnew.substring(1, 2);

                    if (retfour == null) {
                        retfour = strsub1 + strsub2;
                    } else {
                        retfour = retfour + strsub1 + strsub2;
                    }
                } else {
                    strsub1 = strnew.substring(1, 2);
                    strsub2 = strnew.substring(2, 3);

                    if (retfour == null) {
                        retfour = strsub1 + strsub2;
                    } else {
                        retfour = retfour + strsub1 + strsub2;
                    }
                }
            }
        } catch (Exception e) {
            throw new MessagingException("Exception Occured in MessageVO : convertToHexString() method", null, e);
        }

        return retfour;
    }

    /**
     * @return the intJMSPriority
     */
    public int getIntJMSPriority() {
        return intJMSPriority;
    }

    /**
     * @param intJMSPriority
     *            the intJMSPriority to set
     */
    public void setIntJMSPriority(int intJMSPriority) {
        this.intJMSPriority = intJMSPriority;
    }

    /**
     * @return the strMessageHeader
     */
    public String getStrMessageHeader() {
        return strMessageHeader;
    }

    /**
     * @param strMessageHeader
     *            the strMessageHeader to set
     */
    public void setStrMessageHeader(String strMessageHeader) {
        this.strMessageHeader = strMessageHeader;
    }

    /**
     * @return the strMessageRetryHeader
     */
    public String getStrMessageRetryHeader() {
        return strMessageRetryHeader;
    }

    /**
     * @param strMessageRetryHeader
     *            the strMessageRetryHeader to set
     */
    public void setStrMessageRetryHeader(String strMessageRetryHeader) {
        this.strMessageRetryHeader = strMessageRetryHeader;
    }

}
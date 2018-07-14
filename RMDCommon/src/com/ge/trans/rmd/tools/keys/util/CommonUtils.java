/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   CommonUtils.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   CommonUtils .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ge.trans.mcs.codec.util.CodecUtil;

public final class CommonUtils {

    private static AppLogger log = new AppLogger(CommonUtils.class);

    private CommonUtils() {
    }

    /**
     * @param value
     * @return
     */
    public static String trimToNull(String value) {
        return StringUtils.trimToNull(value);
    }

    /**
     * @param value
     * @return
     */
    public static String trimToEmpty(String value) {
        return StringUtils.trimToEmpty(value);
    }

    /**
     * @param pDate
     * @param pPattern
     * @return
     */
    public static String getFormattedDate(Date pDate, String pPattern) {
        DateFormat dFormat = null;
        String localpPattern = pPattern;

        localpPattern = trimToNull(localpPattern);
        if (pDate != null) {
            if (localpPattern != null) {
                dFormat = new SimpleDateFormat(localpPattern);
            } else {
                dFormat = new SimpleDateFormat();
            }

            return dFormat.format(new Date());
        }

        return null;
    }

    /**
     * @param pText
     * @param pSize
     * @param pPadText
     * @return
     */
    public static String leftPad(String pText, int pSize, String pPadText) {
        return StringUtils.leftPad(pText, pSize, pPadText);
    }

    /**
     * @param pText
     * @param pSize
     * @param pPadText
     * @return
     */
    public static String rightPad(String pText, int pSize, String pPadText) {
        return StringUtils.rightPad(pText, pSize, pPadText);
    }

    /**
     * @param pInput
     * @param pMaxLength
     * @return
     */
    public static String leftPadZeros(String pInput, int pMaxLength) {
        if (pInput.length() < pMaxLength)
            return StringUtils.leftPad(pInput, pMaxLength, "0");

        return pInput;
    }

    /**
     * @param pValue
     * @return
     */
    public static String getSwappedValue(String pValue) {
        try {
            return CodecUtil.getOrderedHexFields(pValue, pValue.length());
        } catch (Exception e) {
            log.error("Exception in CommonUtils.getSwappedValue()", e);
            return null;
        }
    }

    /**
     * @param strPath
     * @return
     */
    public static String checkReplacePathSeparator(String strPath) {
        String localstrPath = strPath;
        localstrPath = StringUtils.trimToNull(localstrPath);

        final String SEPARATOR_1 = "\\";
        final String SEPARATOR_2 = "/";
        String separatorNotToBeUsed = null;

        if (localstrPath == null) {
            return null;
        }

        if (SEPARATOR_1.equals(File.separator)) {
            separatorNotToBeUsed = SEPARATOR_2;
        } else {
            separatorNotToBeUsed = SEPARATOR_1;
        }

        if (localstrPath.indexOf(separatorNotToBeUsed) != -1) {
            localstrPath = StringUtils.replaceChars(localstrPath, separatorNotToBeUsed, File.separator);
        }

        if (!localstrPath.endsWith(File.separator)) {
            localstrPath += File.separator;
        }

        return localstrPath;
    }

    /**
     * @param pNumber
     * @param pGroup
     * @return
     */
    public static String getAssetSpec(Integer pNumber, Integer pGroup) {
        if (pNumber != null && pNumber > 0 && pGroup != null && pGroup > 0) {
            String hexNumber = Integer.toHexString(pNumber);
            String hexGroup = Integer.toHexString(pGroup);
            String assetSpec = null;

            // RRRRR
            hexNumber = CommonUtils.leftPadZeros(hexNumber, 5);

            // CCC
            hexGroup = CommonUtils.leftPadZeros(hexGroup, 3);

            assetSpec = hexNumber + hexGroup;
            return CommonUtils.getSwappedValue(assetSpec).toUpperCase();
        }

        return null;
    }
}

/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AssetKeysGenerator.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   Asset Keys Generator .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.ge.trans.mcs.codec.exception.CodecException;
import com.ge.trans.mcs.codec.util.CodecUtil;
import com.ge.trans.mcs.codec.util.CrcGenerator;
import com.ge.trans.rmd.tools.keys.AssetKey;
import com.ge.trans.rmd.tools.keys.util.AppConstants;
import com.ge.trans.rmd.tools.keys.util.AppLogger;
import com.ge.trans.rmd.tools.keys.util.CommonUtils;

public final class AssetKeysGenerator {

    private static AppLogger log = new AppLogger(AssetKeysGenerator.class);

    private AssetKeysGenerator() {
    }

    /**
     * @param pKeyList
     * @param pOutputPath
     */
    public static void generateKeys(EncodeAssetKeys pKeyList, String pOutputPath) {
        String localpOutputPath = pOutputPath;
        localpOutputPath = CommonUtils.checkReplacePathSeparator(localpOutputPath);
        List<AssetKey> assetList = pKeyList.getAssetList();

        if (pKeyList != null && !assetList.isEmpty()) {
            if (pKeyList.getIsHashSupported() != null && pKeyList.getIsZipped() != null) {
                if (localpOutputPath != null) {
                    File folder = new File(localpOutputPath);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    File outFile = new File(localpOutputPath + AppConstants.DEFAULT_KEY_FILE_NAME);
                    if (outFile.exists() && outFile.canWrite()) {
                        String bakFile = (localpOutputPath + AppConstants.DEFAULT_KEY_FILE_NAME
                                + AppConstants.DEFAULT_KEY_BAK_FILE_SUFFIX
                                + CommonUtils.getFormattedDate(new Date(), AppConstants.DEFAULT_TIMESTAMP_FORMAT));
                        outFile.renameTo(new File(bakFile));
                        log.warn("Warning: A file with the specified name already exist. Renamed it to " + bakFile);

                        outFile = new File(localpOutputPath + AppConstants.DEFAULT_KEY_FILE_NAME);
                    }

                    populateAssetHashList(pKeyList, assetList);
                    String dataHex = convertKeysToHex(pKeyList);
                    try {
                        writeBinaryFile(outFile, CodecUtil.getByteArrayFromString(CodecUtil.getBinaryFromHex(dataHex)));
                    } catch (CodecException e) {
                        log.error("CodecException in AssetKeysGenerator.generateKeys()", e);
                    }
                } else {
                    log.error("Invalid path for output file. outputFile = " + localpOutputPath);
                }
            } else {
                log.error("Please provide values for mandatory parameters -- isZipped and isHashSupported");
            }
        } else {
            log.error("AbstractAssetKeyList is a mandatory parameter. It is found to be NULL or empty.");
        }
    }

    /**
     * @param pKeyList
     * @param pAssetList
     */
    private static void populateAssetHashList(EncodeAssetKeys pKeyList, List<AssetKey> pAssetList) {
        List<String> assetHashList = new ArrayList<String>();
        Iterator<AssetKey> assetKeyItr = pAssetList.iterator();
        AssetKey key = null;
        Integer number, group = null;
        String assetSpec = null;
        String assetSpecHash = null;
        int index = 0;
        while (assetKeyItr.hasNext()) {
            key = assetKeyItr.next();

            number = key.getNumber();
            group = key.getGroup();
            if (number != null && number > 0 && group != null && group > 0) {
                assetSpec = CommonUtils.getAssetSpec(number, group);

                if (assetSpec != null) {
                    if (pKeyList.getIsHashSupported()) {
                        assetSpecHash = DigestUtils.md5Hex(assetSpec);
                        assetHashList.add(assetSpecHash);
                        log.trace("Key(" + number + ", " + group + ") assetSpec(" + assetSpec + ") assetSpecHash("
                                + assetSpecHash + ")");
                    } else {
                        assetHashList.add(assetSpec);
                        log.trace("Key(" + number + ", " + group + ") assetSpec(" + assetSpec + ")");
                    }
                } else {
                    log.warn("Warning: Unable to apply asset spec conversion for AssetKey at list.index " + index);
                }
            } else {
                log.warn("Warning: Ignoring current key. Invalid AssetKey found at list.index " + index);
            }

            index++;
        }

        if (!assetHashList.isEmpty()) {
            pKeyList.setAssetHashList(assetHashList);
        }
    }

    /**
     * @param pKeyList
     * @return
     */
    private static String convertKeysToHex(EncodeAssetKeys pKeyList) {
        StringBuilder sBufferPrefix = new StringBuilder();
        StringBuilder sBufferSuffix = new StringBuilder();
        StringBuilder sBufferFinal = new StringBuilder();
        List<String> assetHashList = pKeyList.getAssetHashList();
        byte[] dataBytes = null;

        sBufferSuffix.append(CommonUtils.leftPadZeros(Integer.toHexString(pKeyList.getHashed()), 2));
        sBufferSuffix.append(
                CommonUtils.getSwappedValue(CommonUtils.leftPadZeros(Integer.toHexString(assetHashList.size()), 4)));
        sBufferSuffix.append(CommonUtils.leftPadZeros(Integer.toHexString(pKeyList.getSizePerAsset()), 2));

        for (String entry : assetHashList) {
            sBufferSuffix.append(CommonUtils.leftPadZeros(entry, (pKeyList.getSizePerAsset() * 2)));
        }

        sBufferPrefix.append(CommonUtils.leftPadZeros(Integer.toHexString(pKeyList.getZipped()), 2));
        if (pKeyList.getIsZipped()) {
            sBufferPrefix.append(CommonUtils
                    .getSwappedValue(CommonUtils.leftPadZeros(Integer.toHexString((sBufferSuffix.length() / 2)), 8)));
            try {
                dataBytes = CodecUtil.getByteArrayFromString(CodecUtil.getBinaryFromHex(sBufferSuffix.toString()));
                dataBytes = CodecUtil.compress(dataBytes);
                sBufferSuffix = new StringBuilder(CodecUtil.convertToHexString(dataBytes));
            } catch (Exception e) {
                log.error("Exception in CommonUtils.convertKeysToHex()", e);
                return null;
            }
        }

        sBufferFinal.append(sBufferPrefix);
        sBufferFinal.append(sBufferSuffix);

        int crc = 0;
        try {
            crc = CrcGenerator.calculateCRC(0,
                    CodecUtil.getByteArrayFromString(CodecUtil.getBinaryFromHex(sBufferFinal.toString())));
        } catch (CodecException e) {
            log.error("CodecException in CommonUtils.convertKeysToHex()", e);
            return null;
        }

        sBufferFinal.append(CommonUtils.getSwappedValue(CommonUtils.leftPadZeros(Integer.toHexString(crc), 4)));
        return sBufferFinal.toString().toUpperCase();
    }

    /**
     * @param pFile
     * @param pData
     */
    private static void writeBinaryFile(File pFile, byte[] pData) {
        log.debug("Writing to file... : " + pFile.getAbsolutePath());
        FileOutputStream fileStream = null;
        try {
            fileStream = new FileOutputStream(pFile);
            fileStream.write(pData);
            fileStream.flush();
            fileStream.close();
        } catch (Exception e) {
            log.error("Exception in writeBinaryFile()", e);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<AssetKey> list = new ArrayList<AssetKey>();
        for (int i = 1; i < 79; i++)
            list.add(new AssetKey(i, 78));

        EncodeAssetKeys keys = new EncodeAssetKeys(true, true, list);
        generateKeys(keys, "D:/OMI_WRK/test/");
    }
}

/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AssetKeyValidator.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   AssetKeyValidator .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys.validator;

import org.apache.commons.codec.digest.DigestUtils;

import com.ge.trans.mcs.codec.beans.XMLMessageVO;
import com.ge.trans.mcs.codec.decoder.MessageDecoder;
import com.ge.trans.mcs.codec.xml.MCSMessageInitializer;
import com.ge.trans.rmd.tools.keys.reader.AssetKeyFileReader;
import com.ge.trans.rmd.tools.keys.reader.DecodedAssetKeys;
import com.ge.trans.rmd.tools.keys.util.AppConstants;
import com.ge.trans.rmd.tools.keys.util.AppLogger;
import com.ge.trans.rmd.tools.keys.util.CommonUtils;

public final class AssetKeyValidator {

    private static AppLogger log = new AppLogger(AssetKeyValidator.class);
    private static AssetKeyValidator utility = null;
    private static DecodedAssetKeys keys = null;
    private static MCSMessageInitializer initializer = null;
    private static AssetKeyFileReader reader = null;

    /**
    * 
    */
    private AssetKeyValidator() {
        try {
            initializer = MCSMessageInitializer.getMessageInitializerInstance();
            reader = AssetKeyFileReader.getInstance();

            final XMLMessageVO xml = initializer.getMessageForKey(AppConstants.XML_KEY_ASSET_KEY_FILE);
            keys = (DecodedAssetKeys) MessageDecoder.getMessageDecoded(xml, reader.readBytes());
        } catch (Exception e) {
            log.debug("exception occured in asset key validator");
        }
    }

    /**
     * @return AssetKeyValidator
     */
    public static synchronized AssetKeyValidator getInstance() {
        if (utility == null) {
            utility = new AssetKeyValidator();
        }

        return utility;
    }

    /**
     * @param pNumber
     * @param pGroup
     * @return
     */
    public boolean validate(Integer pNumber, Integer pGroup) {
        String key = CommonUtils.getAssetSpec(pNumber, pGroup);
        log.trace("Input Key: " + key);

        if (key != null) {
            if (keys.getIsHashSupported()) {
                key = DigestUtils.md5Hex(key);
                log.trace("Modified Key: " + key);
            }
        }

        if (key != null) {
            key = key.toUpperCase();
            if (keys.getAssetList() != null && !keys.getAssetList().isEmpty()) {
                log.trace("Allowed Assets List: " + keys.getAssetList());

                if (keys.getAssetList().contains(key)) {
                    log.trace("Match found returning true!!");
                    return true;
                }
            }
        }

        log.trace("No Match found. Returning false!!");
        return false;
    }
}

/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AssetKeyFileReader.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   AssetKeyFileReader .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys.reader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import com.ge.trans.mcs.codec.util.CodecUtil;
import com.ge.trans.rmd.tools.keys.util.AppConstants;
import com.ge.trans.rmd.tools.keys.util.AppLogger;

public final class AssetKeyFileReader {

    private static AssetKeyFileReader reader = null;
    private static AppLogger log = new AppLogger(AssetKeyFileReader.class);
    private static String hex = null;
    private static byte[] data = null;

    private AssetKeyFileReader() {
    }

    /**
     * @return
     * @throws Exception
     */
    public static synchronized AssetKeyFileReader getInstance() throws Exception {
        String filePath = null;

        if (reader == null) {
            reader = new AssetKeyFileReader();

            java.net.URL url = AssetKeyFileReader.class.getResource("/" + AppConstants.DEFAULT_KEY_FILE_NAME);
            filePath = url.getFile();

            log.trace("Keys File: " + filePath);
            data = readBinary(filePath);
            hex = CodecUtil.convertToHexString(data);
        }

        return reader;
    }

    public String readHex() {
        return hex;
    }

    public byte[] readBytes() {
        return data;
    }

    /**
     * @param pFilePath
     * @return
     * @throws Exception
     */
    private static byte[] readBinary(String pFilePath) throws Exception {
        log.debug("Reading file..., name: " + pFilePath);

        File file = new File(pFilePath);
        FileInputStream fileStream = new FileInputStream(file);
        DataInputStream inStream = new DataInputStream(fileStream);

        byte[] fileBytes = new byte[(int) file.length()];
        inStream.read(fileBytes);

        inStream.close();
        fileStream.close();

        return fileBytes;
    }
}

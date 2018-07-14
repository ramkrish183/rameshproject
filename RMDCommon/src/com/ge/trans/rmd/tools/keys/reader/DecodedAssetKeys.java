/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   DecodedAssetKeys.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   DecodedAssetKeys .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys.reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ge.trans.rmd.tools.keys.BaseVO;

public class DecodedAssetKeys extends BaseVO {

    private static final long serialVersionUID = -571951305885302468L;
    protected final String HASH_FUNCTION = "MD5";

    private Boolean isZipped = null;
    private Integer zipped = null;
    private Long numOfUnzipBytes = null;
    private Boolean isHashSupported = null;
    private Integer hashed = null;
    private Integer numOfAssets = null;
    private Integer sizePerAsset = null;
    private List<String> assetList = null;
    private Integer cRC = null;

    /**
     * @param pIsZipped
     *            the isZipped to set
     */
    public void setIsZipped(String pIsZipped) {
        String localpIsZipped = pIsZipped;
        localpIsZipped = trimToNull(localpIsZipped);
        zipped = getInteger(localpIsZipped);

        isZipped = ((zipped != null) ? ((zipped == 1) ? true : false) : null);
    }

    /**
     * @param pIsHashSupported
     *            the isHashSupported to set
     */
    public void setIsHashSupported(String pIsHashSupported) {
        String localpIsHashSupported = pIsHashSupported;
        localpIsHashSupported = trimToNull(localpIsHashSupported);
        hashed = getInteger(localpIsHashSupported);

        isHashSupported = ((hashed != null) ? ((hashed == 1) ? true : false) : null);
    }

    /**
     * @return the zipped
     */
    public Integer getZipped() {
        return (isZipped) ? 1 : 0;
    }

    /**
     * @return the hashed
     */
    public Integer getHashed() {
        return (isHashSupported) ? 1 : 0;
    }

    /**
     * @return the sizePerAsset
     */
    public Integer getSizePerAsset() {
        return sizePerAsset;
    }

    /**
     * @param pSizePerAsset
     *            the sizePerAsset to set
     */
    public void setSizePerAsset(String pSizePerAsset) {
        sizePerAsset = getInteger(pSizePerAsset);
    }

    /**
     * @return the isZipped
     */
    public Boolean getIsZipped() {
        return isZipped;
    }

    /**
     * @return the numOfUnzipBytes
     */
    public Long getNumOfUnzipBytes() {
        return numOfUnzipBytes;
    }

    /**
     * @param pNumOfUnzipBytes
     *            the numOfUnzipBytes to set
     */
    public void setNumOfUnzipBytes(String pNumOfUnzipBytes) {
        numOfUnzipBytes = getLong(pNumOfUnzipBytes);
    }

    /**
     * @return the isHashSupported
     */
    public Boolean getIsHashSupported() {
        return isHashSupported;
    }

    /**
     * @return the numOfAssets
     */
    public Integer getNumOfAssets() {
        return numOfAssets;
    }

    /**
     * @param pNumOfAssets
     *            the numOfAssets to set
     */
    public void setNumOfAssets(String pNumOfAssets) {
        numOfAssets = getInteger(pNumOfAssets);
    }

    /**
     * @return the assetList
     */
    public List<String> getAssetList() {
        return Collections.unmodifiableList(assetList);
    }

    /**
     * @param pAssetList
     *            the assetList to set
     */
    public void setAssetList(ArrayList<String> pAssetList) {
        assetList = pAssetList;
    }

    /**
     * @return the hashingFunction
     */
    public String getHashingFunction() {
        return HASH_FUNCTION;
    }

    /**
     * @return the cRC
     */
    public Integer getcRC() {
        return cRC;
    }

    /**
     * @param pCRC
     *            the cRC to set
     */
    public void setCRC(String pCRC) {
        cRC = getInteger(pCRC);
    }
}
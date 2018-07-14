/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   EncodeAssetKeys.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   Encoder for Asset Keys .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys.generator;

import java.util.Collections;
import java.util.List;

import com.ge.trans.rmd.tools.keys.AssetKey;
import com.ge.trans.rmd.tools.keys.BaseVO;

public class EncodeAssetKeys extends BaseVO {

    public static final long serialVersionUID = 364041379611024681L;
    public static final String HASH_FUNCTION = "MD5";
    public static final Integer HASH_OUTPUT_SIZE = 16;
    public static final Integer NOHASH_OUTPUT_SIZE = 4;

    private Boolean isZipped = null;
    private Long numOfUnzipBytes = null;
    private Boolean isHashSupported = null;
    private List<AssetKey> assetList = null;
    private List<String> assetHashList = null;

    public EncodeAssetKeys(Boolean pIsZipped, Boolean pIsHashSupported, List<AssetKey> pAssetList) {
        isZipped = pIsZipped;
        isHashSupported = pIsHashSupported;
        assetList = pAssetList;
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
        return (isHashSupported) ? HASH_OUTPUT_SIZE : NOHASH_OUTPUT_SIZE;
    }

    /**
     * @return the assetHashList
     */
    public List<String> getAssetHashList() {
        return Collections.unmodifiableList(assetHashList);
    }

    /**
     * @param pAssetHashList
     *            the assetHashList to set
     */
    protected void setAssetHashList(List<String> pAssetHashList) {
        assetHashList = pAssetHashList;
    }

    /**
     * @return the isZipped
     */
    public Boolean getIsZipped() {
        return isZipped;
    }

    /**
     * @param pIsZipped
     *            the isZipped to set
     */
    protected void setIsZipped(Boolean pIsZipped) {
        isZipped = pIsZipped;
    }

    /**
     * @return the numOfUnzipBytes
     */
    protected Long getNumOfUnzipBytes() {
        return numOfUnzipBytes;
    }

    /**
     * @param pNumOfUnzipBytes
     *            the numOfUnzipBytes to set
     */
    protected void setNumOfUnzipBytes(Long pNumOfUnzipBytes) {
        numOfUnzipBytes = pNumOfUnzipBytes;
    }

    /**
     * @return the isHashSupported
     */
    public Boolean getIsHashSupported() {
        return isHashSupported;
    }

    /**
     * @param pIsHashSupported
     *            the isHashSupported to set
     */
    protected void setIsHashSupported(Boolean pIsHashSupported) {
        isHashSupported = pIsHashSupported;
    }

    /**
     * @return the numOfAssets
     */
    protected Integer getNumOfAssets() {
        return (assetList != null && !assetList.isEmpty()) ? assetList.size() : null;
    }

    /**
     * @return the assetList
     */
    public List<AssetKey> getAssetList() {
        return Collections.unmodifiableList(assetList);
    }

    /**
     * @param pAssetList
     *            the assetList to set
     */
    protected void setAssetList(List<AssetKey> pAssetList) {
        assetList = pAssetList;
    }

    /**
     * @return the hashingFunction
     */
    protected String getHashingFunction() {
        return HASH_FUNCTION;
    }
}

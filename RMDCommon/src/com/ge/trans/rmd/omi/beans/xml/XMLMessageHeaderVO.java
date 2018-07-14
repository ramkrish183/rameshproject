/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   XMLMessageHeaderVO.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   This is the bean Class for the XML Message Header for QTR OMI.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */
package com.ge.trans.rmd.omi.beans.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * This is the bean Class for the XML Message Header for QTR OMI. Along with the
 * getters and setter , this class define a toXML() The toXML method returns the
 * XML Header required for every message.
 */

public class XMLMessageHeaderVO extends XMLFormattedObject {

    private static final long serialVersionUID = 3273854187764030983L;

    public static final String XML_MSG_HEADER = "message-header";
    public static final String XML_MSG_ATTR_ID = "id";
    public static final String XML_MSG_ATTR_TIMESTAMP = "timestamp";
    public static final String XML_MSG_ATTR_MMMM = "MMMM";
    public static final String XML_MSG_ATTR_GGGG = "GGGG";
    public static final String XML_MSG_ATTR_FRV = "FRV";
    public static final String XML_MSG_ATTR_ACK_REQD = "ack-req";
    public static final String XML_MSG_ATTR_SIZE = "size";
    public static final String XML_MSG_ATTR_FILE_NAME = "filename";
    public static final String XML_MSG_ATTR_FILE_PATH = "filepath";
    public static final String XML_MSG_ATTR_IN_OUT = "in-out";
    public static final String XML_MSG_HDR_IN = "in";
    public static final String XML_MSG_HDR_OUT = "out";
    public static final String XML_MSG_HDR_SOURCE = "source";
    /* Message Header elements */

    private String strMessageID = null;
    private String strTimestamp = null;
    private String strMMMM = null;
    private String strGGGG = null;
    private String strFRV = null;
    private String strIsAckRequired = null;
    private String strMessageSize = null;
    private String strFileName = null;
    private String strFilePath = null;
    private String strInOut = null;
    private String strSource = null;
    public static final String XML_ASSET = "asset";
    public static final String XML_ASSET_SOURCE = "source";
    public static final String XML_ASSET_ATTR_ID = "id";
    public static final String XML_ASSET_ATTR_ROAD_INITIAL = "initial";
    public static final String XML_ASSET_ATTR_ROAD_INITIAL_ID = "initial-id";
    public static final String XML_ASSET_ATTR_ROAD_NUMBER = "number";
    private List<AssetVO> objAsset = new ArrayList<AssetVO>();
    public static final String XML_COMM = "comm";
    public static final String XML_COMM_ATTR_IP = "ip-addr";
    public static final String XML_COMM_ATTR_PORT = "port";
    public static final String XML_API_RETRY = "api-retry";
    public static final String XML_APP_RETRY = "app-retry";
    public static final String XML_RETRY_ATTR_ATTEMPTS = "attempts";
    public static final String XML_RETRY_ATTR_INTERVAL = "interval";
    public static final String XML_RETRY_ATTR_COUNT = "count";

    /* Comm elements */

    private String strCommIPAddr = null;
    private String strCommPort = null;
    private String strAPIRetryAttempts = null;
    private String strAPIRetryInterval = null;
    private String strAPIRetryCount = null;
    private String strAppRetryAttempts = null;
    private String strAppRetryInterval = null;
    private String strAppRetryCount = null;
    public static final String XML_MSG_REQUESTOR = "requestor";
    public static final String XML_MSG_ATTR_APP = "app";
    public static final String XML_MSG_ATTR_USER = "user";
    private String strApp = null;
    private String strUser = null;

    /**
     * Gets the str api retry attempts.
     * 
     * @return the str api retry attempts
     */
    public String getStrAPIRetryAttempts() {
        return strAPIRetryAttempts;
    }

    /**
     * Sets the str api retry attempts.
     * 
     * @param strAPIRetryAttempts
     *            the new str api retry attempts
     */
    public void setStrAPIRetryAttempts(String strAPIRetryAttempts) {
        this.strAPIRetryAttempts = StringUtils.trimToNull(strAPIRetryAttempts);
    }

    /**
     * Gets the str api retry count.
     * 
     * @return the str api retry count
     */
    public String getStrAPIRetryCount() {
        return strAPIRetryCount;
    }

    /**
     * Sets the str api retry count.
     * 
     * @param strAPIRetryCount
     *            the new str api retry count
     */
    public void setStrAPIRetryCount(String strAPIRetryCount) {
        this.strAPIRetryCount = StringUtils.trimToNull(strAPIRetryCount);
    }

    /**
     * Gets the str api retry interval.
     * 
     * @return the str api retry interval
     */
    public String getStrAPIRetryInterval() {
        return strAPIRetryInterval;
    }

    /**
     * Sets the str api retry interval.
     * 
     * @param strAPIRetryInterval
     *            the new str api retry interval
     */
    public void setStrAPIRetryInterval(String strAPIRetryInterval) {
        this.strAPIRetryInterval = StringUtils.trimToNull(strAPIRetryInterval);
    }

    /**
     * Gets the str app retry attempts.
     * 
     * @return the str app retry attempts
     */
    public String getStrAppRetryAttempts() {
        return strAppRetryAttempts;
    }

    /**
     * Sets the str app retry attempts.
     * 
     * @param strAppRetryAttempts
     *            the new str app retry attempts
     */
    public void setStrAppRetryAttempts(String strAppRetryAttempts) {
        this.strAppRetryAttempts = StringUtils.trimToNull(strAppRetryAttempts);
    }

    /**
     * Gets the str app retry count.
     * 
     * @return the str app retry count
     */
    public String getStrAppRetryCount() {
        return strAppRetryCount;
    }

    /**
     * Sets the str app retry count.
     * 
     * @param strAppRetryCount
     *            the new str app retry count
     */
    public void setStrAppRetryCount(String strAppRetryCount) {
        this.strAppRetryCount = StringUtils.trimToNull(strAppRetryCount);
    }

    /**
     * Gets the str app retry interval.
     * 
     * @return the str app retry interval
     */
    public String getStrAppRetryInterval() {
        return strAppRetryInterval;
    }

    /**
     * Sets the str app retry interval.
     * 
     * @param strAppRetryInterval
     *            the new str app retry interval
     */
    public void setStrAppRetryInterval(String strAppRetryInterval) {
        this.strAppRetryInterval = StringUtils.trimToNull(strAppRetryInterval);
    }

    /**
     * Gets the str mmmm.
     * 
     * @return the str mmmm
     */
    public String getStrMMMM() {
        return strMMMM;
    }

    /**
     * Sets the str mmmm.
     * 
     * @param strMMMM
     *            the new str mmmm
     */
    public void setStrMMMM(String strMMMM) {
        this.strMMMM = StringUtils.trimToNull(strMMMM);
    }

    /**
     * Gets the str comm ip addr.
     * 
     * @return the str comm ip addr
     */
    public String getStrCommIPAddr() {
        return strCommIPAddr;
    }

    /**
     * Sets the str comm ip addr.
     * 
     * @param strCommIPAddr
     *            the new str comm ip addr
     */
    public void setStrCommIPAddr(String strCommIPAddr) {
        this.strCommIPAddr = StringUtils.trimToNull(strCommIPAddr);
    }

    /**
     * Gets the str comm port.
     * 
     * @return the str comm port
     */
    public String getStrCommPort() {
        return strCommPort;
    }

    /**
     * Sets the str comm port.
     * 
     * @param strCommPort
     *            the new str comm port
     */
    public void setStrCommPort(String strCommPort) {
        this.strCommPort = StringUtils.trimToNull(strCommPort);
    }

    /**
     * Gets the str file name.
     * 
     * @return the str file name
     */
    public String getStrFileName() {
        return strFileName;
    }

    /**
     * Sets the str file name.
     * 
     * @param strFileName
     *            the new str file name
     */
    public void setStrFileName(String strFileName) {
        this.strFileName = StringUtils.trimToNull(strFileName);
    }

    /**
     * Gets the str frv.
     * 
     * @return the str frv
     */
    public String getStrFRV() {
        return strFRV;
    }

    /**
     * Sets the str frv.
     * 
     * @param strFRV
     *            the new str frv
     */
    public void setStrFRV(String strFRV) {
        this.strFRV = StringUtils.trimToNull(strFRV);
    }

    /**
     * Gets the str in out.
     * 
     * @return the str in out
     */
    public String getStrInOut() {
        return strInOut;
    }

    /**
     * Sets the str in out.
     * 
     * @param strInOut
     *            the new str in out
     */
    public void setStrInOut(String strInOut) {
        this.strInOut = StringUtils.trimToNull(strInOut);
    }

    /**
     * Gets the str is ack required.
     * 
     * @return the str is ack required
     */
    public String getStrIsAckRequired() {
        return strIsAckRequired;
    }

    /**
     * Sets the str is ack required.
     * 
     * @param strIsAckRequired
     *            the new str is ack required
     */
    public void setStrIsAckRequired(String strIsAckRequired) {
        this.strIsAckRequired = StringUtils.trimToNull(strIsAckRequired);
    }

    /**
     * Gets the str message id.
     * 
     * @return the str message id
     */
    public String getStrMessageID() {
        return strMessageID;
    }

    /**
     * Sets the str message id.
     * 
     * @param strMessageID
     *            the new str message id
     */
    public void setStrMessageID(String strMessageID) {
        this.strMessageID = StringUtils.trimToNull(strMessageID);
    }

    /**
     * Gets the str message size.
     * 
     * @return the str message size
     */
    public String getStrMessageSize() {
        return strMessageSize;
    }

    /**
     * Sets the str message size.
     * 
     * @param strMessageSize
     *            the new str message size
     */
    public void setStrMessageSize(String strMessageSize) {
        this.strMessageSize = StringUtils.trimToNull(strMessageSize);
    }

    /**
     * Gets the str timestamp.
     * 
     * @return the str timestamp
     */
    public String getStrTimestamp() {
        return strTimestamp;
    }

    /**
     * Sets the str timestamp.
     * 
     * @param strTimestamp
     *            the new str timestamp
     */
    public void setStrTimestamp(String strTimestamp) {
        this.strTimestamp = StringUtils.trimToNull(strTimestamp);
    }

    /**
     * Gets the str gggg.
     * 
     * @return the strGGGG
     */
    public String getStrGGGG() {
        return strGGGG;
    }

    /**
     * Sets the str gggg.
     * 
     * @param strGGGG
     *            the strGGGG to set
     */
    public void setStrGGGG(String strGGGG) {
        this.strGGGG = strGGGG;
    }

    /**
     * Gets the str file path.
     * 
     * @return the strFilePath
     */
    public String getStrFilePath() {
        return strFilePath;
    }

    /**
     * Sets the str file path.
     * 
     * @param strFilePath
     *            the strFilePath to set
     */
    public void setStrFilePath(String strFilePath) {
        this.strFilePath = strFilePath;
    }

    /**
     * Gets the obj asset.
     * 
     * @return the objAsset
     */
    public List<AssetVO> getObjAsset() {
        return objAsset;
    }

    /**
     * Adds the obj asset.
     * 
     * @param objAsset
     *            the objAsset to set
     */
    public void addObjAsset(AssetVO objAsset) {
        this.objAsset.add(objAsset);
    }

    /**
     * Gets the str app.
     * 
     * @return the strApp
     */
    public String getStrApp() {
        return strApp;
    }

    /**
     * Sets the str app.
     * 
     * @param strApp
     *            the strApp to set
     */
    public void setStrApp(String strApp) {
        this.strApp = strApp;
    }

    /**
     * Gets the str user.
     * 
     * @return the strUser
     */
    public String getStrUser() {
        return strUser;
    }

    /**
     * Sets the str user.
     * 
     * @param strUser
     *            the strUser to set
     */
    public void setStrUser(String strUser) {
        this.strUser = strUser;
    }

    @Override
    public String toXML() {
        boolean comFlag = true;
        StringBuilder strBuffer = new StringBuilder("");
        if (checkMessageHeaderTag()) {
            strBuffer.append(XML_TAG_START).append(XML_MSG_HEADER);
        }
        if (StringUtils.isNotBlank(getStrMessageID()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_ID, getStrMessageID()));
        if (StringUtils.isNotBlank(getStrGGGG()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_GGGG, getStrGGGG()));
        if (StringUtils.isNotBlank(getStrTimestamp()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_TIMESTAMP, getStrTimestamp()));
        if (StringUtils.isNotBlank(getStrMMMM()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_MMMM, getStrMMMM()));
        if (StringUtils.isNotBlank(getStrFRV()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_FRV, getStrFRV()));
        if (StringUtils.isNotBlank(getStrIsAckRequired()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_ACK_REQD, getStrIsAckRequired()));
        if (StringUtils.isNotBlank(getStrInOut()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_IN_OUT, getStrInOut()));
        if (StringUtils.isNotBlank(getStrSource()))
            strBuffer.append(getAttribute(XML_MSG_HDR_SOURCE, getStrSource()));
        if (StringUtils.isNotBlank(getStrFileName()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_FILE_NAME, getStrFileName()));
        if (StringUtils.isNotBlank(getStrFilePath()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_FILE_PATH, getStrFilePath()));
        if (StringUtils.isNotBlank(getStrMessageSize()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_SIZE, getStrMessageSize()));
        if (checkMessageHeaderTag()) {
            strBuffer.append(XML_TAG_END);
        }
        if (checkRequestorTag()) {
            strBuffer.append(XML_TAG_START).append(XML_MSG_REQUESTOR);
        }
        if (StringUtils.isNotBlank(getStrApp()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_APP, getStrApp()));
        if (StringUtils.isNotBlank(getStrUser()))
            strBuffer.append(getAttribute(XML_MSG_ATTR_USER, getStrUser()));
        if (checkRequestorTag()) {
            strBuffer.append(XML_TAG_SLASH).append(XML_TAG_END);
        }

        for (Iterator<AssetVO> iterator = objAsset.iterator(); iterator.hasNext();) {
            AssetVO objAsset = iterator.next();
            if (checkAssetTag(objAsset)) {
                strBuffer.append(XML_TAG_START).append(XML_ASSET);
            }
            if (StringUtils.isNotBlank(objAsset.getStrAssetID()))
                strBuffer.append(getAttribute(XML_ASSET_ATTR_ID, objAsset.getStrAssetID()));
            if (StringUtils.isNotBlank(objAsset.getStrRoadInitial()))
                strBuffer.append(getAttribute(XML_ASSET_ATTR_ROAD_INITIAL, objAsset.getStrRoadInitial()));
            if (StringUtils.isNotBlank(objAsset.getStrRoadInitialID()))
                strBuffer.append(getAttribute(XML_ASSET_ATTR_ROAD_INITIAL_ID, objAsset.getStrRoadInitialID()));
            if (StringUtils.isNotBlank(objAsset.getStrRoadNumber()))
                strBuffer.append(getAttribute(XML_ASSET_ATTR_ROAD_NUMBER, objAsset.getStrRoadNumber()));
            if (StringUtils.isNotBlank(objAsset.getStrSource()))
                strBuffer.append(getAttribute(XML_ASSET_SOURCE, objAsset.getStrSource()));
            if (checkAssetTag(objAsset)) {
                strBuffer.append(XML_TAG_SLASH).append(XML_TAG_END);
            }
        }
        if (checkCommTag()) {
            strBuffer.append(XML_TAG_START).append(XML_COMM);
        }
        if (StringUtils.isNotBlank(getStrCommIPAddr()))
            strBuffer.append(getAttribute(XML_COMM_ATTR_IP, getStrCommIPAddr()));
        if (StringUtils.isNotBlank(getStrCommPort()))
            strBuffer.append(getAttribute(XML_COMM_ATTR_PORT, getStrCommPort()));
        if (checkCommTag()) {
            if (!(checkApiRetryTag() || checkAppRetryTag())) {
                comFlag = false;
                strBuffer.append(XML_TAG_SLASH);
            }
            strBuffer.append(XML_TAG_END);
        }

        if (checkApiRetryTag()) {
            strBuffer.append(XML_TAG_START).append(XML_API_RETRY);
        }
        if (StringUtils.isNotBlank(getStrAPIRetryAttempts()))
            strBuffer.append(getAttribute(XML_RETRY_ATTR_ATTEMPTS, getStrAPIRetryAttempts()));
        if (StringUtils.isNotBlank(getStrAPIRetryInterval()))
            strBuffer.append(getAttribute(XML_RETRY_ATTR_INTERVAL, getStrAPIRetryInterval()));
        if (StringUtils.isNotBlank(getStrAPIRetryCount()))
            strBuffer.append(getAttribute(XML_RETRY_ATTR_COUNT, getStrAPIRetryCount()));
        if (checkApiRetryTag()) {
            strBuffer.append(XML_TAG_SLASH).append(XML_TAG_END);
        }
        if (checkAppRetryTag()) {
            strBuffer.append(XML_TAG_START).append(XML_APP_RETRY);
        }
        if (StringUtils.isNotBlank(getStrAppRetryAttempts()))
            strBuffer.append(getAttribute(XML_RETRY_ATTR_ATTEMPTS, getStrAppRetryAttempts()));
        if (StringUtils.isNotBlank(getStrAppRetryInterval()))
            strBuffer.append(getAttribute(XML_RETRY_ATTR_INTERVAL, getStrAppRetryInterval()));
        if (StringUtils.isNotBlank(getStrAppRetryCount()))
            strBuffer.append(getAttribute(XML_RETRY_ATTR_COUNT, getStrAppRetryCount()));
        if (checkAppRetryTag()) {
            strBuffer.append(XML_TAG_SLASH).append(XML_TAG_END);
        }

        if (checkCommTag()) {
            if (comFlag) {
                strBuffer.append(XML_TAG_START).append(XML_TAG_SLASH).append(XML_COMM).append(XML_TAG_END);
            }
        }
        if (checkMessageHeaderTag()) {
            strBuffer.append(XML_TAG_START).append(XML_TAG_SLASH).append(XML_MSG_HEADER).append(XML_TAG_END);
        }
        return strBuffer.toString();
    }

    /**
     * Check message header tag.
     * 
     * @return true, if successful
     */
    private boolean checkMessageHeaderTag() {
        if (StringUtils.isNotBlank(getStrMessageID()) || StringUtils.isNotBlank(getStrTimestamp())
                || StringUtils.isNotBlank(getStrMMMM()) || StringUtils.isNotBlank(getStrFRV())
                || StringUtils.isNotBlank(getStrIsAckRequired()) || StringUtils.isNotBlank(getStrMessageSize())
                || StringUtils.isNotBlank(getStrInOut()) || StringUtils.isNotBlank(getStrFileName())
                || StringUtils.isNotBlank(getStrSource())) {
            return true;
        } else
            return false;
    }

    /**
     * Check requestor tag.
     * 
     * @return true, if successful
     */
    private boolean checkRequestorTag() {
        if (StringUtils.isNotBlank(getStrApp()) || StringUtils.isNotBlank(getStrUser())) {
            return true;
        } else
            return false;
    }

    /**
     * Check asset tag.
     * 
     * @param assetVO
     *            the asset vo
     * @return true, if successful
     */
    private boolean checkAssetTag(AssetVO assetVO) {
        if (StringUtils.isNotBlank(assetVO.getStrAssetID()) || StringUtils.isNotBlank(assetVO.getStrRoadInitial())
                || StringUtils.isNotBlank(assetVO.getStrRoadInitialID())
                || StringUtils.isNotBlank(assetVO.getStrRoadNumber())
                || StringUtils.isNotBlank(assetVO.getStrSource())) {
            return true;
        } else
            return false;
    }

    /**
     * Check api retry tag.
     * 
     * @return true, if successful
     */
    private boolean checkApiRetryTag() {
        if (StringUtils.isNotBlank(getStrAPIRetryAttempts()) || StringUtils.isNotBlank(getStrAPIRetryInterval())
                || StringUtils.isNotBlank(getStrAPIRetryCount())) {
            return true;
        } else
            return false;
    }

    /**
     * Check app retry tag.
     * 
     * @return true, if successful
     */
    private boolean checkAppRetryTag() {
        if (StringUtils.isNotBlank(getStrAppRetryAttempts()) || StringUtils.isNotBlank(getStrAppRetryInterval())
                || StringUtils.isNotBlank(getStrAppRetryCount())) {
            return true;
        } else
            return false;
    }

    /**
     * @return the strSource
     */
    public String getStrSource() {
        return strSource;
    }

    /**
     * @param strSource
     *            the strSource to set
     */
    public void setStrSource(String strSource) {
        this.strSource = strSource;
    }

    /**
     * Check comm tag.
     * 
     * @return true, if successful
     */

    private boolean checkCommTag() {
        if (StringUtils.isNotBlank(getStrCommIPAddr()) || StringUtils.isNotBlank(getStrCommPort())) {
            return true;
        } else if (checkApiRetryTag() || checkAppRetryTag()) {
            return true;
        } else
            return false;
    }

}

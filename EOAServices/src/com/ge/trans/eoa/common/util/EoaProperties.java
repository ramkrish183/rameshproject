package com.ge.trans.eoa.common.util;

import java.io.*;
import java.util.*;

/**
 * This class obtains the general eoa properties (environment variables) from a
 * file
 *
 * @author: Lynn Chenette
 * @version: 1.0, 28/03/2001 History: 1. 25 Apr 01 - Lynn Chenette - added new
 *           values (for the second time) 2. 27 Apr 01 - Lynn Chenette - added
 *           comments 3. 30 May 01 - Lynn Chenette - added EOA_DATA_URL 4. 30
 *           May 01 - Lynn Chenette - added EOA_BIN_CPROGS_DIR 5. 17 Jul 01 -
 *           Lynn Chenette - added five AMA_X properties 6. 27 Jul 01 - Lynn
 *           Chenette - added mail server 7. 09 Jan 02 - Bharath - added the
 *           from address for RMD Emailer. 8. 09 Jan 02 - Bharath - added the
 *           Reply to Field for RMD Emailer 9. 09 Jan 02 - Bharath - added four
 *           parameters for e-Services Connection Pooling as of staging 10 15
 *           Feb 02 - Bharath - Added new parameters and methods for Queue
 *           monitor program. 11 26 Jun 02 - Madhavi - For kavachart. 12 05 Dec
 *           03 - Babu Danda - For SMPP .
 */
public class EoaProperties {

    // *************************************************************
    // Member variables
    // *************************************************************
    // -------------------------------------------------------------
    /** Fully qualified name of the properties file */
    // PROD LOC
    // private String mPropFileName =
    // "/geteoaghp/apps/GETS/prd/eoa/1.0/bin/java/EoaProperties";

    // STG Loc
    // private String mPropFileName =
    // "/geteoaalq/apps/GETS/qa/eoa/1.0/bin/java/EoaProperties";

    // DEV LOC
    private String mPropFileName = "/com/ge/trans/rmd/services/common/resources/EoaProperties.properties";

    private Properties mProp = null;

    private String mEoaTop = null;
    private String mEoaLog = null;
    private String mEoaData = null;
    private String mVehicleCfg = null;
    private String mEoaDBName = null;
    private String mEoaDBUsr = null;
    private String mEoaDBPwd = null;
    private String mEoaDBUrl = null;

    private String mEoaDBMaxUse = null;
    private String mEoaDBTimeOut = null;
    private String mEoaDBConnections = null;
    private String mEoaDriver = null;

    private String mEoaDWDBUsr = null;
    private String mEoaDWDBPwd = null;
    private String mEoaDWDBUrl = null;

    private String mEoaDataUrl = null;
    private String mEoaBinCProgsDir = null;

    private String mAmaDbDriver = null;
    private String mAmaDbName = null;
    private String mAmaDbUsr = null;
    private String mAmaDbPwd = null;
    private String mAmaDbUrl = null;

    private String mEoaEmailServerName = null;
    private String mPptEmailFromName = null;

    private String mPptEmailReplyTo = null;
    private String mLmsDbName = null;

    private String mLmsDbUsr = null;

    private String mLmsDbPwd = null;

    private String mLmsDbUrl = null;

    private String mEoaCommHostName = null;

    private String mEoaCommQMgr = null;

    private String mEoaApplHostName = null;

    private String mEoaApplQMgr = null;

    private String mEoaChannel = null;

    private String mEoaQPrfx = null;

    private String mEoaPortNo = null;

    private String mRmdQPrfx = null;

    private String emailPropertyFile = null;

    // Madhavi --- For KavaChart --Start

    private String mKavaChartUrl = null;

    private String mKavaChartDir = null;

    // Madhavi --- For KavaChart --End

    // SMPP
    private String mdbDriver = null;
    private String mdbUrl = null;
    private String mdbUser = null;
    private String mdbPwd = null;
    // SMPP

    private String MQHost = null;
    private int MQPort = 0;

    // -------------------------------------------------------------

    // *************************************************************
    // Constructors
    // *************************************************************
    // -------------------------------------------------------------
    public EoaProperties() throws FileNotFoundException, IOException {

        File fromFile = null;
        FileInputStream fis = null;
        InputStream is = null;
        this.mProp = new Properties();

        /*
         * fromFile = new File(this.mPropFileName); fis = new
         * FileInputStream(fromFile);
         */
        is = EoaProperties.class
                .getResourceAsStream("/com/ge/trans/rmd/services/common/resources/EoaProperties.properties");

        this.mProp.load(is);

        this.mEoaTop = this.mProp.getProperty("EOA_TOP");

        this.mEoaLog = this.mProp.getProperty("EOA_LOG");

        this.mEoaData = this.mProp.getProperty("EOA_DATA");

        mVehicleCfg = this.mProp.getProperty("VEHICLE_CFG");

        this.mEoaDBName = this.mProp.getProperty("EOA_DBNAME");

        this.mEoaDBUsr = this.mProp.getProperty("EOA_DBUSR");

        this.mEoaDBPwd = this.mProp.getProperty("EOA_DBPWD");

        this.mEoaDBUrl = this.mProp.getProperty("EOA_DBURL");

        this.mEoaDBMaxUse = this.mProp.getProperty("EOA_DB_CONNECTION_MAX_USE");
        this.mEoaDBTimeOut = this.mProp.getProperty("EOA_DB_CONNECTION_TIME_OUT");
        this.mEoaDBConnections = this.mProp.getProperty("EOA_DB_CONNECTIONS");
        this.mEoaDriver = this.mProp.getProperty("EOA_DB_DRIVER");

        this.mEoaDWDBUsr = this.mProp.getProperty("EOA_DW_DBUSR");
        this.mEoaDWDBPwd = this.mProp.getProperty("EOA_DW_DBPWD");
        this.mEoaDWDBUrl = this.mProp.getProperty("EOA_DW_DBURL");

        this.mEoaDataUrl = this.mProp.getProperty("EOA_DATA_URL");

        this.mEoaBinCProgsDir = this.mProp.getProperty("EOA_BIN_CPROGS_DIR");

        this.mAmaDbDriver = this.mProp.getProperty("AMA_DB_DRIVER");
        this.mAmaDbName = this.mProp.getProperty("AMA_DBNAME");
        this.mAmaDbUsr = this.mProp.getProperty("AMA_DBUSR");
        this.mAmaDbPwd = this.mProp.getProperty("AMA_DBPWD");
        this.mAmaDbUrl = this.mProp.getProperty("AMA_DBURL");

        this.mEoaEmailServerName = this.mProp.getProperty("EOA_EMAIL_SERVER_NAME");
        this.mPptEmailFromName = this.mProp.getProperty("PPT_FROM_EMAIL_NAME");

        this.mPptEmailReplyTo = this.mProp.getProperty("PPT_REPLY_TO_NAME");
        this.mLmsDbName = this.mProp.getProperty("LMS_DBNAME");

        this.mLmsDbUsr = this.mProp.getProperty("LMS_DBUSR");

        this.mLmsDbPwd = this.mProp.getProperty("LMS_DBPWD");

        this.mLmsDbUrl = this.mProp.getProperty("LMS_DBURL");

        this.mEoaCommHostName = this.mProp.getProperty("EOA_COMM_HOST_NAME");

        this.mEoaCommQMgr = this.mProp.getProperty("EOA_COMM_Q_MGR");

        this.mEoaApplHostName = this.mProp.getProperty("EOA_APPL_HOST_NAME");

        this.mEoaApplQMgr = this.mProp.getProperty("EOA_APPL_Q_MGR");

        this.mEoaChannel = this.mProp.getProperty("EOA_CHANNEL");

        this.mEoaQPrfx = this.mProp.getProperty("EOA_Q_PRFX");

        this.mEoaPortNo = this.mProp.getProperty("EOA_PORT_NO");

        this.mRmdQPrfx = this.mProp.getProperty("RMD_Q_PRFX");

        // Madhavi --- For KavaChart --Start

        this.mKavaChartUrl = this.mProp.getProperty("KAVACHART_URL");

        this.mKavaChartDir = this.mProp.getProperty("KAVACHART_DIR");

        // Madhavi --- For KavaChart --End
        // SMPP
        this.mdbDriver = this.mProp.getProperty("SMPP_DB_DRIVER");
        this.mdbUrl = this.mProp.getProperty("SMPP_DBURL");
        this.mdbUser = this.mProp.getProperty("SMPP_DBUSR");
        this.mdbPwd = this.mProp.getProperty("SMPP_DBPWD");
        // SMPP

        this.MQHost = this.mProp.getProperty("MQHOST");
        this.MQPort = Integer.parseInt(this.mProp.getProperty("MQPORT"));

        this.emailPropertyFile = this.mProp.getProperty("EMAIL_PROP_FILE_NAME");

    } // em
      // -------------------------------------------------------------

    // *************************************************************
    // Setter/getter Methods
    // *************************************************************
    // -------------------------------------------------------------

    public String getEmailPropFile() {
        return emailPropertyFile;
    }

    public String getEoaTop() {
        return this.mEoaTop;
    } // em

    public String getEoaLog() {
        return this.mEoaLog;
    } // em

    public String getEoaData() {
        return this.mEoaData;
    } // em

    public String getVehicleCfg() {
        return this.mVehicleCfg;
    } // em

    public String getEoaDBUsr() {
        return this.mEoaDBUsr;
    } // em

    public String getEoaDBName() {
        return this.mEoaDBName;
    } // em

    public String getEoaDBPwd() {
        return this.mEoaDBPwd;
    } // em

    public String getEoaDBUrl() {
        return this.mEoaDBUrl;
    } // em

    public String getEoaDBMaxUse() {
        return this.mEoaDBMaxUse;
    } // em

    public String getEoaDBTimeOut() {
        return this.mEoaDBTimeOut;
    } // em

    public String getEoaDBConnections() {
        return this.mEoaDBConnections;
    } // em

    public String getEoaDriver() {
        return this.mEoaDriver;
    } // em

    public String getEoaDWDBUsr() {
        return this.mEoaDWDBUsr;
    } // em

    public String getEoaDWDBPwd() {
        return this.mEoaDWDBPwd;
    } // em

    public String getEoaDWDBUrl() {
        return this.mEoaDWDBUrl;
    } // em

    public String getEoaDataUrl() {
        return this.mEoaDataUrl;
    } // em

    public String getEoaBinCProgsDir() {
        return this.mEoaBinCProgsDir;
    } // em

    public String getAmaDbDriver() {
        return this.mAmaDbDriver;
    } // em

    public String getAmaDbName() {
        return this.mAmaDbName;
    } // em

    public String getAmaDbUsr() {
        return this.mAmaDbUsr;
    } // em

    public String getAmaDbPwd() {
        return this.mAmaDbPwd;
    } // em

    public String getAmaDbUrl() {
        return this.mAmaDbUrl;
    } // em

    public String getEoaEmailServerName() {
        return this.mEoaEmailServerName;
    } // em

    public String getPptFromEmailName() {
        return this.mPptEmailFromName;
    }// End method

    public String getPptReplyToName() {
        return this.mPptEmailReplyTo;
    }// End method

    // -------------------------------------------------------------
    public String getLmsDbName() {
        return this.mLmsDbName;
    } // em

    public String getLmsDbUsr() {
        return this.mLmsDbUsr;
    } // em

    public String getLmsDbPwd() {
        return this.mLmsDbPwd;
    } // em

    public String getLmsDbUrl() {
        return this.mLmsDbUrl;
    } // em
      // -------------------------------------------------------------

    //
    public String getEoaCommHostName() {

        return this.mEoaCommHostName;

    } // em

    public String getEoaCommQMgr() {

        return this.mEoaCommQMgr;

    } // em

    public String getEoaApplHostName() {

        return this.mEoaApplHostName;

    } // em

    public String getEoaApplQMgr() {

        return this.mEoaApplQMgr;

    } // em

    public String getEoaChannel() {

        return this.mEoaChannel;

    } // em

    public String getEoaQPrfx() {

        return this.mEoaQPrfx;

    } // em

    public String getEoaPortNo() {

        return this.mEoaPortNo;

    } // em

    // Madhavi - For KavaChart - 060502 --Start

    public String getKavaChartDir() {

        return this.mKavaChartDir;

    } // em

    public String getKavaChartURL() {

        return this.mKavaChartUrl;

    } // em

    // Madhavi - For KavaChart - 060502 --End

    // SMPP***************************************************
    public String getSmppDriver() {
        return this.mdbDriver;
    }

    public String getSmppUrl() {
        return this.mdbUrl;
    }

    public String getSmppDbUsr() {
        return this.mdbUser;
    }

    public String getSmppDbPwd() {
        return this.mdbPwd;
    }
    // SMPP*****************************************************

    public String getMQHost() {
        return MQHost;
    }

    public int getMQPort() {
        return MQPort;
    }

    public String getRmdQPrfx() {
        return mRmdQPrfx;
    }

} // ec

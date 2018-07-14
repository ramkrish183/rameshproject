/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   StandardDatapackParameters.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */
package com.ge.trans.rmd.omi.beans.msg.in;

import java.util.HashMap;
import java.util.Map;

import com.ge.trans.rmd.omi.beans.msg.BaseMessage;

public class StandardDatapackParameters extends BaseMessage {
    private static final long serialVersionUID = 9021897153976264801L;

    private Float monitorParam6268 = null;
    private Float monitorParam6269 = null;
    private Float monitorParam6301 = null;
    private Float monitorParam6302 = null;
    private Float monitorParam6303 = null;
    private Float monitorParam6900 = null;
    private Float monitorParam6901 = null;
    private Float monitorParam3000 = null;
    private String validityBitSet1 = null;

    private Float monitorParam3002 = null;
    private Float monitorParam3004 = null;
    private Float monitorParam3205 = null;
    private Float monitorParam4000 = null;
    private Float monitorParam4340 = null;
    private Float monitorParam4341 = null;
    private Float monitorParam4342 = null;
    private Float monitorParam4343 = null;
    private String validityBitSet2 = null;

    private Float monitorParam4344 = null;
    private Float monitorParam4345 = null;
    private Float monitorParam5566 = null;
    private Float monitorParam5567 = null;
    private Float monitorParam5568 = null;
    private Float monitorParam5569 = null;
    private Float monitorParam5570 = null;
    private Float monitorParam5571 = null;
    private String validityBitSet3 = null;

    private Float monitorParam6205 = null;
    private Float monitorParam2200 = null;
    private Float monitorParam2202 = null;
    private Float monitorParam2209 = null;
    private Float monitorParam2211 = null;
    private Float monitorParam2213 = null;
    private Float monitorParam2035 = null;
    private Float monitorParam2050 = null;
    private String validityBitSet4 = null;

    private Float monitorParam2053 = null;
    private Float monitorParam1833 = null;
    private Float monitorParam2002 = null;
    private Float monitorParam2010 = null;
    private Float monitorParam2011 = null;
    private Float monitorParam2012 = null;
    private Float monitorParam2020 = null;
    private Float monitorParam2021 = null;
    private String validityBitSet5 = null;

    private Float monitorParam2022 = null;
    private Float monitorParam2025 = null;
    private Float monitorParam1005 = null;
    private Float monitorParam1025 = null;
    private Float monitorParam1055 = null;
    private Float monitorParam1056 = null;
    private Float monitorParam1057 = null;
    private Float monitorParam1070 = null;
    private String validityBitSet6 = null;

    private Float monitorParam1500 = null;
    private Float monitorParam1501 = null;
    private Float monitorParam1506 = null;
    private Float monitorParam1507 = null;
    private Float monitorParam1510 = null;
    private Float monitorParam1511 = null;
    private Float monitorParam1525 = null;
    private Float monitorParam1526 = null;
    private String validityBitSet7 = null;

    private Float monitorParam1531 = null;
    private Float monitorParam1535 = null;
    private Float monitorParam1570 = null;
    private Float monitorParam1571 = null;
    private Float monitorParam1580 = null;
    private Float monitorParam1581 = null;
    private Float monitorParam1582 = null;
    private Float monitorParam1583 = null;
    private String validityBitSet8 = null;

    private Float monitorParam1584 = null;
    private Float monitorParam1585 = null;
    private Float monitorParam6926 = null;
    private Float monitorParam7103 = null;
    private Float monitorParam7104 = null;
    private Float monitorParam7105 = null;
    private Float monitorParam7106 = null;
    private Float monitorParam7107 = null;
    private String validityBitSet9 = null;

    private Float monitorParam7108 = null;
    private Float monitorParam8002 = null;
    private Float monitorParam8005 = null;
    private Float monitorParam8006 = null;
    private Float monitorParam8007 = null;
    private Float monitorParam8030 = null;
    private Float monitorParam100 = null;
    private Float monitorParam102 = null;
    private String validityBitSet10 = null;

    private Float monitorParam109 = null;
    private Float monitorParam110 = null;
    private Float monitorParam111 = null;
    private Float monitorParam165 = null;
    private Float monitorParam185 = null;
    private Float monitorParam195 = null;
    private Float monitorParam205 = null;
    private Float monitorParam4009 = null;
    private String validityBitSet11 = null;

    private Float monitorParam1000 = null;
    private Float monitorParam1050 = null;
    private Float monitorParam1051 = null;
    private Float monitorParam1151 = null;
    private Float monitorParam1207 = null;
    private Float monitorParam1208 = null;
    private Float monitorParam1224 = null;
    private Float monitorParam1235 = null;
    private String validityBitSet12 = null;

    private Float monitorParam1562 = null;
    private Float monitorParam2220 = null;
    private Float monitorParam2221 = null;
    private Float monitorParam2222 = null;
    private Float monitorParam4045 = null;
    private Float monitorParam4046 = null;
    private Float monitorParam4047 = null;
    private Float monitorParam4048 = null;
    private String validityBitSet13 = null;

    private Float monitorParam4049 = null;
    private Float monitorParam4050 = null;
    private Float monitorParam5005 = null;
    private Float monitorParam5006 = null;
    private Float monitorParam5007 = null;
    private Float monitorParam5008 = null;
    private Float monitorParam5009 = null;
    private Float monitorParam5010 = null;
    private String validityBitSet14 = null;

    private Float monitorParam192 = null;
    private Float monitorParam1006 = null;
    private Float monitorParam1014 = null;
    private Float monitorParam9502 = null;
    private Float monitorParam9503 = null;
    private Float monitorParam2226 = null;
    private Float monitorParam1598 = null;
    private Float monitorParam509 = null;
    private String validityBitSet15 = null;

    private Float monitorParam514 = null;
    private Float monitorParam2472 = null;
    private Float monitorParam2479 = null;
    private Float monitorParam2480 = null;
    private Float monitorParam2486 = null;
    private Float monitorParam2041 = null;
    private Float monitorParam2042 = null;
    private Float monitorParam2043 = null;
    private String validityBitSet16 = null;

    /**
     * @return the monitorParam6268
     */
    public Float getMonitorParam6268() {
        return monitorParam6268;
    }

    /**
     * @param pMonitorParam6268
     *            the monitorParam6268 to set
     */
    public void setMonitorParam6268(String pMonitorParam6268) {
        monitorParam6268 = getFloat(pMonitorParam6268);
    }

    /**
     * @return the monitorParam6269
     */
    public Float getMonitorParam6269() {
        return monitorParam6269;
    }

    /**
     * @param pMonitorParam6269
     *            the monitorParam6269 to set
     */
    public void setMonitorParam6269(String pMonitorParam6269) {
        monitorParam6269 = getFloat(pMonitorParam6269);
    }

    /**
     * @return the monitorParam6301
     */
    public Float getMonitorParam6301() {
        return monitorParam6301;
    }

    /**
     * @param pMonitorParam6301
     *            the monitorParam6301 to set
     */
    public void setMonitorParam6301(String pMonitorParam6301) {
        monitorParam6301 = getFloat(pMonitorParam6301);
    }

    /**
     * @return the monitorParam6302
     */
    public Float getMonitorParam6302() {
        return monitorParam6302;
    }

    /**
     * @param pMonitorParam6302
     *            the monitorParam6302 to set
     */
    public void setMonitorParam6302(String pMonitorParam6302) {
        monitorParam6302 = getFloat(pMonitorParam6302);
    }

    /**
     * @return the monitorParam6303
     */
    public Float getMonitorParam6303() {
        return monitorParam6303;
    }

    /**
     * @param pMonitorParam6303
     *            the monitorParam6303 to set
     */
    public void setMonitorParam6303(String pMonitorParam6303) {
        monitorParam6303 = getFloat(pMonitorParam6303);
    }

    /**
     * @return the monitorParam6900
     */
    public Float getMonitorParam6900() {
        return monitorParam6900;
    }

    /**
     * @param pMonitorParam6900
     *            the monitorParam6900 to set
     */
    public void setMonitorParam6900(String pMonitorParam6900) {
        monitorParam6900 = getFloat(pMonitorParam6900);
    }

    /**
     * @return the monitorParam6901
     */
    public Float getMonitorParam6901() {
        return monitorParam6901;
    }

    /**
     * @param pMonitorParam6901
     *            the monitorParam6901 to set
     */
    public void setMonitorParam6901(String pMonitorParam6901) {
        monitorParam6901 = getFloat(pMonitorParam6901);
    }

    /**
     * @return the monitorParam3000
     */
    public Float getMonitorParam3000() {
        return monitorParam3000;
    }

    /**
     * @param pMonitorParam3000
     *            the monitorParam3000 to set
     */
    public void setMonitorParam3000(String pMonitorParam3000) {
        monitorParam3000 = getFloat(pMonitorParam3000);
    }

    /**
     * @return the monitorParam3002
     */
    public Float getMonitorParam3002() {
        return monitorParam3002;
    }

    /**
     * @param pMonitorParam3002
     *            the monitorParam3002 to set
     */
    public void setMonitorParam3002(String pMonitorParam3002) {
        monitorParam3002 = getFloat(pMonitorParam3002);
    }

    /**
     * @return the monitorParam3004
     */
    public Float getMonitorParam3004() {
        return monitorParam3004;
    }

    /**
     * @param pMonitorParam3004
     *            the monitorParam3004 to set
     */
    public void setMonitorParam3004(String pMonitorParam3004) {
        monitorParam3004 = getFloat(pMonitorParam3004);
    }

    /**
     * @return the monitorParam3205
     */
    public Float getMonitorParam3205() {
        return monitorParam3205;
    }

    /**
     * @param pMonitorParam3205
     *            the monitorParam3205 to set
     */
    public void setMonitorParam3205(String pMonitorParam3205) {
        monitorParam3205 = getFloat(pMonitorParam3205);
    }

    /**
     * @return the monitorParam4000
     */
    public Float getMonitorParam4000() {
        return monitorParam4000;
    }

    /**
     * @param pMonitorParam4000
     *            the monitorParam4000 to set
     */
    public void setMonitorParam4000(String pMonitorParam4000) {
        monitorParam4000 = getFloat(pMonitorParam4000);
    }

    /**
     * @return the monitorParam4340
     */
    public Float getMonitorParam4340() {
        return monitorParam4340;
    }

    /**
     * @param pMonitorParam4340
     *            the monitorParam4340 to set
     */
    public void setMonitorParam4340(String pMonitorParam4340) {
        monitorParam4340 = getFloat(pMonitorParam4340);
    }

    /**
     * @return the monitorParam4341
     */
    public Float getMonitorParam4341() {
        return monitorParam4341;
    }

    /**
     * @param pMonitorParam4341
     *            the monitorParam4341 to set
     */
    public void setMonitorParam4341(String pMonitorParam4341) {
        monitorParam4341 = getFloat(pMonitorParam4341);
    }

    /**
     * @return the monitorParam4342
     */
    public Float getMonitorParam4342() {
        return monitorParam4342;
    }

    /**
     * @param pMonitorParam4342
     *            the monitorParam4342 to set
     */
    public void setMonitorParam4342(String pMonitorParam4342) {
        monitorParam4342 = getFloat(pMonitorParam4342);
    }

    /**
     * @return the monitorParam4343
     */
    public Float getMonitorParam4343() {
        return monitorParam4343;
    }

    /**
     * @param pMonitorParam4343
     *            the monitorParam4343 to set
     */
    public void setMonitorParam4343(String pMonitorParam4343) {
        monitorParam4343 = getFloat(pMonitorParam4343);
    }

    /**
     * @return the monitorParam4344
     */
    public Float getMonitorParam4344() {
        return monitorParam4344;
    }

    /**
     * @param pMonitorParam4344
     *            the monitorParam4344 to set
     */
    public void setMonitorParam4344(String pMonitorParam4344) {
        monitorParam4344 = getFloat(pMonitorParam4344);
    }

    /**
     * @return the monitorParam4345
     */
    public Float getMonitorParam4345() {
        return monitorParam4345;
    }

    /**
     * @param pMonitorParam4345
     *            the monitorParam4345 to set
     */
    public void setMonitorParam4345(String pMonitorParam4345) {
        monitorParam4345 = getFloat(pMonitorParam4345);
    }

    /**
     * @return the monitorParam5566
     */
    public Float getMonitorParam5566() {
        return monitorParam5566;
    }

    /**
     * @param pMonitorParam5566
     *            the monitorParam5566 to set
     */
    public void setMonitorParam5566(String pMonitorParam5566) {
        monitorParam5566 = getFloat(pMonitorParam5566);
    }

    /**
     * @return the monitorParam5567
     */
    public Float getMonitorParam5567() {
        return monitorParam5567;
    }

    /**
     * @param pMonitorParam5567
     *            the monitorParam5567 to set
     */
    public void setMonitorParam5567(String pMonitorParam5567) {
        monitorParam5567 = getFloat(pMonitorParam5567);
    }

    /**
     * @return the monitorParam5568
     */
    public Float getMonitorParam5568() {
        return monitorParam5568;
    }

    /**
     * @param pMonitorParam5568
     *            the monitorParam5568 to set
     */
    public void setMonitorParam5568(String pMonitorParam5568) {
        monitorParam5568 = getFloat(pMonitorParam5568);
    }

    /**
     * @return the monitorParam5569
     */
    public Float getMonitorParam5569() {
        return monitorParam5569;
    }

    /**
     * @param pMonitorParam5569
     *            the monitorParam5569 to set
     */
    public void setMonitorParam5569(String pMonitorParam5569) {
        monitorParam5569 = getFloat(pMonitorParam5569);
    }

    /**
     * @return the monitorParam5570
     */
    public Float getMonitorParam5570() {
        return monitorParam5570;
    }

    /**
     * @param pMonitorParam5570
     *            the monitorParam5570 to set
     */
    public void setMonitorParam5570(String pMonitorParam5570) {
        monitorParam5570 = getFloat(pMonitorParam5570);
    }

    /**
     * @return the monitorParam5571
     */
    public Float getMonitorParam5571() {
        return monitorParam5571;
    }

    /**
     * @param pMonitorParam5571
     *            the monitorParam5571 to set
     */
    public void setMonitorParam5571(String pMonitorParam5571) {
        monitorParam5571 = getFloat(pMonitorParam5571);
    }

    /**
     * @return the monitorParam6205
     */
    public Float getMonitorParam6205() {
        return monitorParam6205;
    }

    /**
     * @param pMonitorParam6205
     *            the monitorParam6205 to set
     */
    public void setMonitorParam6205(String pMonitorParam6205) {
        monitorParam6205 = getFloat(pMonitorParam6205);
    }

    /**
     * @return the monitorParam2200
     */
    public Float getMonitorParam2200() {
        return monitorParam2200;
    }

    /**
     * @param pMonitorParam2200
     *            the monitorParam2200 to set
     */
    public void setMonitorParam2200(String pMonitorParam2200) {
        monitorParam2200 = getFloat(pMonitorParam2200);
    }

    /**
     * @return the monitorParam2202
     */
    public Float getMonitorParam2202() {
        return monitorParam2202;
    }

    /**
     * @param pMonitorParam2202
     *            the monitorParam2202 to set
     */
    public void setMonitorParam2202(String pMonitorParam2202) {
        monitorParam2202 = getFloat(pMonitorParam2202);
    }

    /**
     * @return the monitorParam2209
     */
    public Float getMonitorParam2209() {
        return monitorParam2209;
    }

    /**
     * @param pMonitorParam2209
     *            the monitorParam2209 to set
     */
    public void setMonitorParam2209(String pMonitorParam2209) {
        monitorParam2209 = getFloat(pMonitorParam2209);
    }

    /**
     * @return the monitorParam2211
     */
    public Float getMonitorParam2211() {
        return monitorParam2211;
    }

    /**
     * @param pMonitorParam2211
     *            the monitorParam2211 to set
     */
    public void setMonitorParam2211(String pMonitorParam2211) {
        monitorParam2211 = getFloat(pMonitorParam2211);
    }

    /**
     * @return the monitorParam2213
     */
    public Float getMonitorParam2213() {
        return monitorParam2213;
    }

    /**
     * @param pMonitorParam2213
     *            the monitorParam2213 to set
     */
    public void setMonitorParam2213(String pMonitorParam2213) {
        monitorParam2213 = getFloat(pMonitorParam2213);
    }

    /**
     * @return the monitorParam2035
     */
    public Float getMonitorParam2035() {
        return monitorParam2035;
    }

    /**
     * @param pMonitorParam2035
     *            the monitorParam2035 to set
     */
    public void setMonitorParam2035(String pMonitorParam2035) {
        monitorParam2035 = getFloat(pMonitorParam2035);
    }

    /**
     * @return the monitorParam2050
     */
    public Float getMonitorParam2050() {
        return monitorParam2050;
    }

    /**
     * @param pMonitorParam2050
     *            the monitorParam2050 to set
     */
    public void setMonitorParam2050(String pMonitorParam2050) {
        monitorParam2050 = getFloat(pMonitorParam2050);
    }

    /**
     * @return the monitorParam2053
     */
    public Float getMonitorParam2053() {
        return monitorParam2053;
    }

    /**
     * @param pMonitorParam2053
     *            the monitorParam2053 to set
     */
    public void setMonitorParam2053(String pMonitorParam2053) {
        monitorParam2053 = getFloat(pMonitorParam2053);
    }

    /**
     * @return the monitorParam1833
     */
    public Float getMonitorParam1833() {
        return monitorParam1833;
    }

    /**
     * @param pMonitorParam1833
     *            the monitorParam1833 to set
     */
    public void setMonitorParam1833(String pMonitorParam1833) {
        monitorParam1833 = getFloat(pMonitorParam1833);
    }

    /**
     * @return the monitorParam2002
     */
    public Float getMonitorParam2002() {
        return monitorParam2002;
    }

    /**
     * @param pMonitorParam2002
     *            the monitorParam2002 to set
     */
    public void setMonitorParam2002(String pMonitorParam2002) {
        monitorParam2002 = getFloat(pMonitorParam2002);
    }

    /**
     * @return the monitorParam2010
     */
    public Float getMonitorParam2010() {
        return monitorParam2010;
    }

    /**
     * @param pMonitorParam2010
     *            the monitorParam2010 to set
     */
    public void setMonitorParam2010(String pMonitorParam2010) {
        monitorParam2010 = getFloat(pMonitorParam2010);
    }

    /**
     * @return the monitorParam2011
     */
    public Float getMonitorParam2011() {
        return monitorParam2011;
    }

    /**
     * @param pMonitorParam2011
     *            the monitorParam2011 to set
     */
    public void setMonitorParam2011(String pMonitorParam2011) {
        monitorParam2011 = getFloat(pMonitorParam2011);
    }

    /**
     * @return the monitorParam2012
     */
    public Float getMonitorParam2012() {
        return monitorParam2012;
    }

    /**
     * @param pMonitorParam2012
     *            the monitorParam2012 to set
     */
    public void setMonitorParam2012(String pMonitorParam2012) {
        monitorParam2012 = getFloat(pMonitorParam2012);
    }

    /**
     * @return the monitorParam2020
     */
    public Float getMonitorParam2020() {
        return monitorParam2020;
    }

    /**
     * @param pMonitorParam2020
     *            the monitorParam2020 to set
     */
    public void setMonitorParam2020(String pMonitorParam2020) {
        monitorParam2020 = getFloat(pMonitorParam2020);
    }

    /**
     * @return the monitorParam2021
     */
    public Float getMonitorParam2021() {
        return monitorParam2021;
    }

    /**
     * @param pMonitorParam2021
     *            the monitorParam2021 to set
     */
    public void setMonitorParam2021(String pMonitorParam2021) {
        monitorParam2021 = getFloat(pMonitorParam2021);
    }

    /**
     * @return the monitorParam2022
     */
    public Float getMonitorParam2022() {
        return monitorParam2022;
    }

    /**
     * @param pMonitorParam2022
     *            the monitorParam2022 to set
     */
    public void setMonitorParam2022(String pMonitorParam2022) {
        monitorParam2022 = getFloat(pMonitorParam2022);
    }

    /**
     * @return the monitorParam2025
     */
    public Float getMonitorParam2025() {
        return monitorParam2025;
    }

    /**
     * @param pMonitorParam2025
     *            the monitorParam2025 to set
     */
    public void setMonitorParam2025(String pMonitorParam2025) {
        monitorParam2025 = getFloat(pMonitorParam2025);
    }

    /**
     * @return the monitorParam1005
     */
    public Float getMonitorParam1005() {
        return monitorParam1005;
    }

    /**
     * @param pMonitorParam1005
     *            the monitorParam1005 to set
     */
    public void setMonitorParam1005(String pMonitorParam1005) {
        monitorParam1005 = getFloat(pMonitorParam1005);
    }

    /**
     * @return the monitorParam1025
     */
    public Float getMonitorParam1025() {
        return monitorParam1025;
    }

    /**
     * @param pMonitorParam1025
     *            the monitorParam1025 to set
     */
    public void setMonitorParam1025(String pMonitorParam1025) {
        monitorParam1025 = getFloat(pMonitorParam1025);
    }

    /**
     * @return the monitorParam1055
     */
    public Float getMonitorParam1055() {
        return monitorParam1055;
    }

    /**
     * @param pMonitorParam1055
     *            the monitorParam1055 to set
     */
    public void setMonitorParam1055(String pMonitorParam1055) {
        monitorParam1055 = getFloat(pMonitorParam1055);
    }

    /**
     * @return the monitorParam1056
     */
    public Float getMonitorParam1056() {
        return monitorParam1056;
    }

    /**
     * @param pMonitorParam1056
     *            the monitorParam1056 to set
     */
    public void setMonitorParam1056(String pMonitorParam1056) {
        monitorParam1056 = getFloat(pMonitorParam1056);
    }

    /**
     * @return the monitorParam1057
     */
    public Float getMonitorParam1057() {
        return monitorParam1057;
    }

    /**
     * @param pMonitorParam1057
     *            the monitorParam1057 to set
     */
    public void setMonitorParam1057(String pMonitorParam1057) {
        monitorParam1057 = getFloat(pMonitorParam1057);
    }

    /**
     * @return the monitorParam1070
     */
    public Float getMonitorParam1070() {
        return monitorParam1070;
    }

    /**
     * @param pMonitorParam1070
     *            the monitorParam1070 to set
     */
    public void setMonitorParam1070(String pMonitorParam1070) {
        monitorParam1070 = getFloat(pMonitorParam1070);
    }

    /**
     * @return the monitorParam1500
     */
    public Float getMonitorParam1500() {
        return monitorParam1500;
    }

    /**
     * @param pMonitorParam1500
     *            the monitorParam1500 to set
     */
    public void setMonitorParam1500(String pMonitorParam1500) {
        monitorParam1500 = getFloat(pMonitorParam1500);
    }

    /**
     * @return the monitorParam1501
     */
    public Float getMonitorParam1501() {
        return monitorParam1501;
    }

    /**
     * @param pMonitorParam1501
     *            the monitorParam1501 to set
     */
    public void setMonitorParam1501(String pMonitorParam1501) {
        monitorParam1501 = getFloat(pMonitorParam1501);
    }

    /**
     * @return the monitorParam1506
     */
    public Float getMonitorParam1506() {
        return monitorParam1506;
    }

    /**
     * @param pMonitorParam1506
     *            the monitorParam1506 to set
     */
    public void setMonitorParam1506(String pMonitorParam1506) {
        monitorParam1506 = getFloat(pMonitorParam1506);
    }

    /**
     * @return the monitorParam1507
     */
    public Float getMonitorParam1507() {
        return monitorParam1507;
    }

    /**
     * @param pMonitorParam1507
     *            the monitorParam1507 to set
     */
    public void setMonitorParam1507(String pMonitorParam1507) {
        monitorParam1507 = getFloat(pMonitorParam1507);
    }

    /**
     * @return the monitorParam1510
     */
    public Float getMonitorParam1510() {
        return monitorParam1510;
    }

    /**
     * @param pMonitorParam1510
     *            the monitorParam1510 to set
     */
    public void setMonitorParam1510(String pMonitorParam1510) {
        monitorParam1510 = getFloat(pMonitorParam1510);
    }

    /**
     * @return the monitorParam1511
     */
    public Float getMonitorParam1511() {
        return monitorParam1511;
    }

    /**
     * @param pMonitorParam1511
     *            the monitorParam1511 to set
     */
    public void setMonitorParam1511(String pMonitorParam1511) {
        monitorParam1511 = getFloat(pMonitorParam1511);
    }

    /**
     * @return the monitorParam1525
     */
    public Float getMonitorParam1525() {
        return monitorParam1525;
    }

    /**
     * @param pMonitorParam1525
     *            the monitorParam1525 to set
     */
    public void setMonitorParam1525(String pMonitorParam1525) {
        monitorParam1525 = getFloat(pMonitorParam1525);
    }

    /**
     * @return the monitorParam1526
     */
    public Float getMonitorParam1526() {
        return monitorParam1526;
    }

    /**
     * @param pMonitorParam1526
     *            the monitorParam1526 to set
     */
    public void setMonitorParam1526(String pMonitorParam1526) {
        monitorParam1526 = getFloat(pMonitorParam1526);
    }

    /**
     * @return the monitorParam1531
     */
    public Float getMonitorParam1531() {
        return monitorParam1531;
    }

    /**
     * @param pMonitorParam1531
     *            the monitorParam1531 to set
     */
    public void setMonitorParam1531(String pMonitorParam1531) {
        monitorParam1531 = getFloat(pMonitorParam1531);
    }

    /**
     * @return the monitorParam1535
     */
    public Float getMonitorParam1535() {
        return monitorParam1535;
    }

    /**
     * @param pMonitorParam1535
     *            the monitorParam1535 to set
     */
    public void setMonitorParam1535(String pMonitorParam1535) {
        monitorParam1535 = getFloat(pMonitorParam1535);
    }

    /**
     * @return the monitorParam1570
     */
    public Float getMonitorParam1570() {
        return monitorParam1570;
    }

    /**
     * @param pMonitorParam1570
     *            the monitorParam1570 to set
     */
    public void setMonitorParam1570(String pMonitorParam1570) {
        monitorParam1570 = getFloat(pMonitorParam1570);
    }

    /**
     * @return the monitorParam1571
     */
    public Float getMonitorParam1571() {
        return monitorParam1571;
    }

    /**
     * @param pMonitorParam1571
     *            the monitorParam1571 to set
     */
    public void setMonitorParam1571(String pMonitorParam1571) {
        monitorParam1571 = getFloat(pMonitorParam1571);
    }

    /**
     * @return the monitorParam1580
     */
    public Float getMonitorParam1580() {
        return monitorParam1580;
    }

    /**
     * @param pMonitorParam1580
     *            the monitorParam1580 to set
     */
    public void setMonitorParam1580(String pMonitorParam1580) {
        monitorParam1580 = getFloat(pMonitorParam1580);
    }

    /**
     * @return the monitorParam1581
     */
    public Float getMonitorParam1581() {
        return monitorParam1581;
    }

    /**
     * @param pMonitorParam1581
     *            the monitorParam1581 to set
     */
    public void setMonitorParam1581(String pMonitorParam1581) {
        monitorParam1581 = getFloat(pMonitorParam1581);
    }

    /**
     * @return the monitorParam1582
     */
    public Float getMonitorParam1582() {
        return monitorParam1582;
    }

    /**
     * @param pMonitorParam1582
     *            the monitorParam1582 to set
     */
    public void setMonitorParam1582(String pMonitorParam1582) {
        monitorParam1582 = getFloat(pMonitorParam1582);
    }

    /**
     * @return the monitorParam1583
     */
    public Float getMonitorParam1583() {
        return monitorParam1583;
    }

    /**
     * @param pMonitorParam1583
     *            the monitorParam1583 to set
     */
    public void setMonitorParam1583(String pMonitorParam1583) {
        monitorParam1583 = getFloat(pMonitorParam1583);
    }

    /**
     * @return the monitorParam1584
     */
    public Float getMonitorParam1584() {
        return monitorParam1584;
    }

    /**
     * @param pMonitorParam1584
     *            the monitorParam1584 to set
     */
    public void setMonitorParam1584(String pMonitorParam1584) {
        monitorParam1584 = getFloat(pMonitorParam1584);
    }

    /**
     * @return the monitorParam1585
     */
    public Float getMonitorParam1585() {
        return monitorParam1585;
    }

    /**
     * @param pMonitorParam1585
     *            the monitorParam1585 to set
     */
    public void setMonitorParam1585(String pMonitorParam1585) {
        monitorParam1585 = getFloat(pMonitorParam1585);
    }

    /**
     * @return the monitorParam6926
     */
    public Float getMonitorParam6926() {
        return monitorParam6926;
    }

    /**
     * @param pMonitorParam6926
     *            the monitorParam6926 to set
     */
    public void setMonitorParam6926(String pMonitorParam6926) {
        monitorParam6926 = getFloat(pMonitorParam6926);
    }

    /**
     * @return the monitorParam7103
     */
    public Float getMonitorParam7103() {
        return monitorParam7103;
    }

    /**
     * @param pMonitorParam7103
     *            the monitorParam7103 to set
     */
    public void setMonitorParam7103(String pMonitorParam7103) {
        monitorParam7103 = getFloat(pMonitorParam7103);
    }

    /**
     * @return the monitorParam7104
     */
    public Float getMonitorParam7104() {
        return monitorParam7104;
    }

    /**
     * @param pMonitorParam7104
     *            the monitorParam7104 to set
     */
    public void setMonitorParam7104(String pMonitorParam7104) {
        monitorParam7104 = getFloat(pMonitorParam7104);
    }

    /**
     * @return the monitorParam7105
     */
    public Float getMonitorParam7105() {
        return monitorParam7105;
    }

    /**
     * @param pMonitorParam7105
     *            the monitorParam7105 to set
     */
    public void setMonitorParam7105(String pMonitorParam7105) {
        monitorParam7105 = getFloat(pMonitorParam7105);
    }

    /**
     * @return the monitorParam7106
     */
    public Float getMonitorParam7106() {
        return monitorParam7106;
    }

    /**
     * @param pMonitorParam7106
     *            the monitorParam7106 to set
     */
    public void setMonitorParam7106(String pMonitorParam7106) {
        monitorParam7106 = getFloat(pMonitorParam7106);
    }

    /**
     * @return the monitorParam7107
     */
    public Float getMonitorParam7107() {
        return monitorParam7107;
    }

    /**
     * @param pMonitorParam7107
     *            the monitorParam7107 to set
     */
    public void setMonitorParam7107(String pMonitorParam7107) {
        monitorParam7107 = getFloat(pMonitorParam7107);
    }

    /**
     * @return the monitorParam7108
     */
    public Float getMonitorParam7108() {
        return monitorParam7108;
    }

    /**
     * @param pMonitorParam7108
     *            the monitorParam7108 to set
     */
    public void setMonitorParam7108(String pMonitorParam7108) {
        monitorParam7108 = getFloat(pMonitorParam7108);
    }

    /**
     * @return the monitorParam8002
     */
    public Float getMonitorParam8002() {
        return monitorParam8002;
    }

    /**
     * @param pMonitorParam8002
     *            the monitorParam8002 to set
     */
    public void setMonitorParam8002(String pMonitorParam8002) {
        monitorParam8002 = getFloat(pMonitorParam8002);
    }

    /**
     * @return the monitorParam8005
     */
    public Float getMonitorParam8005() {
        return monitorParam8005;
    }

    /**
     * @param pMonitorParam8005
     *            the monitorParam8005 to set
     */
    public void setMonitorParam8005(String pMonitorParam8005) {
        monitorParam8005 = getFloat(pMonitorParam8005);
    }

    /**
     * @return the monitorParam8006
     */
    public Float getMonitorParam8006() {
        return monitorParam8006;
    }

    /**
     * @param pMonitorParam8006
     *            the monitorParam8006 to set
     */
    public void setMonitorParam8006(String pMonitorParam8006) {
        monitorParam8006 = getFloat(pMonitorParam8006);
    }

    /**
     * @return the monitorParam8007
     */
    public Float getMonitorParam8007() {
        return monitorParam8007;
    }

    /**
     * @param pMonitorParam8007
     *            the monitorParam8007 to set
     */
    public void setMonitorParam8007(String pMonitorParam8007) {
        monitorParam8007 = getFloat(pMonitorParam8007);
    }

    /**
     * @return the monitorParam8030
     */
    public Float getMonitorParam8030() {
        return monitorParam8030;
    }

    /**
     * @param pMonitorParam8030
     *            the monitorParam8030 to set
     */
    public void setMonitorParam8030(String pMonitorParam8030) {
        monitorParam8030 = getFloat(pMonitorParam8030);
    }

    /**
     * @return the monitorParam100
     */
    public Float getMonitorParam100() {
        return monitorParam100;
    }

    /**
     * @param pMonitorParam100
     *            the monitorParam100 to set
     */
    public void setMonitorParam100(String pMonitorParam100) {
        monitorParam100 = getFloat(pMonitorParam100);
    }

    /**
     * @return the monitorParam102
     */
    public Float getMonitorParam102() {
        return monitorParam102;
    }

    /**
     * @param pMonitorParam102
     *            the monitorParam102 to set
     */
    public void setMonitorParam102(String pMonitorParam102) {
        monitorParam102 = getFloat(pMonitorParam102);
    }

    /**
     * @return the monitorParam109
     */
    public Float getMonitorParam109() {
        return monitorParam109;
    }

    /**
     * @param pMonitorParam109
     *            the monitorParam109 to set
     */
    public void setMonitorParam109(String pMonitorParam109) {
        monitorParam109 = getFloat(pMonitorParam109);
    }

    /**
     * @return the monitorParam110
     */
    public Float getMonitorParam110() {
        return monitorParam110;
    }

    /**
     * @param pMonitorParam110
     *            the monitorParam110 to set
     */
    public void setMonitorParam110(String pMonitorParam110) {
        monitorParam110 = getFloat(pMonitorParam110);
    }

    /**
     * @return the monitorParam111
     */
    public Float getMonitorParam111() {
        return monitorParam111;
    }

    /**
     * @param pMonitorParam111
     *            the monitorParam111 to set
     */
    public void setMonitorParam111(String pMonitorParam111) {
        monitorParam111 = getFloat(pMonitorParam111);
    }

    /**
     * @return the monitorParam165
     */
    public Float getMonitorParam165() {
        return monitorParam165;
    }

    /**
     * @param pMonitorParam165
     *            the monitorParam165 to set
     */
    public void setMonitorParam165(String pMonitorParam165) {
        monitorParam165 = getFloat(pMonitorParam165);
    }

    /**
     * @return the monitorParam185
     */
    public Float getMonitorParam185() {
        return monitorParam185;
    }

    /**
     * @param pMonitorParam185
     *            the monitorParam185 to set
     */
    public void setMonitorParam185(String pMonitorParam185) {
        monitorParam185 = getFloat(pMonitorParam185);
    }

    /**
     * @return the monitorParam195
     */
    public Float getMonitorParam195() {
        return monitorParam195;
    }

    /**
     * @param pMonitorParam195
     *            the monitorParam195 to set
     */
    public void setMonitorParam195(String pMonitorParam195) {
        monitorParam195 = getFloat(pMonitorParam195);
    }

    /**
     * @return the monitorParam205
     */
    public Float getMonitorParam205() {
        return monitorParam205;
    }

    /**
     * @param pMonitorParam205
     *            the monitorParam205 to set
     */
    public void setMonitorParam205(String pMonitorParam205) {
        monitorParam205 = getFloat(pMonitorParam205);
    }

    /**
     * @return the monitorParam4009
     */
    public Float getMonitorParam4009() {
        return monitorParam4009;
    }

    /**
     * @param pMonitorParam4009
     *            the monitorParam4009 to set
     */
    public void setMonitorParam4009(String pMonitorParam4009) {
        monitorParam4009 = getFloat(pMonitorParam4009);
    }

    /**
     * @return the monitorParam1000
     */
    public Float getMonitorParam1000() {
        return monitorParam1000;
    }

    /**
     * @param pMonitorParam1000
     *            the monitorParam1000 to set
     */
    public void setMonitorParam1000(String pMonitorParam1000) {
        monitorParam1000 = getFloat(pMonitorParam1000);
    }

    /**
     * @return the monitorParam1050
     */
    public Float getMonitorParam1050() {
        return monitorParam1050;
    }

    /**
     * @param pMonitorParam1050
     *            the monitorParam1050 to set
     */
    public void setMonitorParam1050(String pMonitorParam1050) {
        monitorParam1050 = getFloat(pMonitorParam1050);
    }

    /**
     * @return the monitorParam1051
     */
    public Float getMonitorParam1051() {
        return monitorParam1051;
    }

    /**
     * @param pMonitorParam1051
     *            the monitorParam1051 to set
     */
    public void setMonitorParam1051(String pMonitorParam1051) {
        monitorParam1051 = getFloat(pMonitorParam1051);
    }

    /**
     * @return the monitorParam1151
     */
    public Float getMonitorParam1151() {
        return monitorParam1151;
    }

    /**
     * @param pMonitorParam1151
     *            the monitorParam1151 to set
     */
    public void setMonitorParam1151(String pMonitorParam1151) {
        monitorParam1151 = getFloat(pMonitorParam1151);
    }

    /**
     * @return the monitorParam1207
     */
    public Float getMonitorParam1207() {
        return monitorParam1207;
    }

    /**
     * @param pMonitorParam1207
     *            the monitorParam1207 to set
     */
    public void setMonitorParam1207(String pMonitorParam1207) {
        monitorParam1207 = getFloat(pMonitorParam1207);
    }

    /**
     * @return the monitorParam1208
     */
    public Float getMonitorParam1208() {
        return monitorParam1208;
    }

    /**
     * @param pMonitorParam1208
     *            the monitorParam1208 to set
     */
    public void setMonitorParam1208(String pMonitorParam1208) {
        monitorParam1208 = getFloat(pMonitorParam1208);
    }

    /**
     * @return the monitorParam1224
     */
    public Float getMonitorParam1224() {
        return monitorParam1224;
    }

    /**
     * @param pMonitorParam1224
     *            the monitorParam1224 to set
     */
    public void setMonitorParam1224(String pMonitorParam1224) {
        monitorParam1224 = getFloat(pMonitorParam1224);
    }

    /**
     * @return the monitorParam1235
     */
    public Float getMonitorParam1235() {
        return monitorParam1235;
    }

    /**
     * @param pMonitorParam1235
     *            the monitorParam1235 to set
     */
    public void setMonitorParam1235(String pMonitorParam1235) {
        monitorParam1235 = getFloat(pMonitorParam1235);
    }

    /**
     * @return the monitorParam1562
     */
    public Float getMonitorParam1562() {
        return monitorParam1562;
    }

    /**
     * @param pMonitorParam1562
     *            the monitorParam1562 to set
     */
    public void setMonitorParam1562(String pMonitorParam1562) {
        monitorParam1562 = getFloat(pMonitorParam1562);
    }

    /**
     * @return the monitorParam2220
     */
    public Float getMonitorParam2220() {
        return monitorParam2220;
    }

    /**
     * @param pMonitorParam2220
     *            the monitorParam2220 to set
     */
    public void setMonitorParam2220(String pMonitorParam2220) {
        monitorParam2220 = getFloat(pMonitorParam2220);
    }

    /**
     * @return the monitorParam2221
     */
    public Float getMonitorParam2221() {
        return monitorParam2221;
    }

    /**
     * @param pMonitorParam2221
     *            the monitorParam2221 to set
     */
    public void setMonitorParam2221(String pMonitorParam2221) {
        monitorParam2221 = getFloat(pMonitorParam2221);
    }

    /**
     * @return the monitorParam2222
     */
    public Float getMonitorParam2222() {
        return monitorParam2222;
    }

    /**
     * @param pMonitorParam2222
     *            the monitorParam2222 to set
     */
    public void setMonitorParam2222(String pMonitorParam2222) {
        monitorParam2222 = getFloat(pMonitorParam2222);
    }

    /**
     * @return the monitorParam4045
     */
    public Float getMonitorParam4045() {
        return monitorParam4045;
    }

    /**
     * @param pMonitorParam4045
     *            the monitorParam4045 to set
     */
    public void setMonitorParam4045(String pMonitorParam4045) {
        monitorParam4045 = getFloat(pMonitorParam4045);
    }

    /**
     * @return the monitorParam4046
     */
    public Float getMonitorParam4046() {
        return monitorParam4046;
    }

    /**
     * @param pMonitorParam4046
     *            the monitorParam4046 to set
     */
    public void setMonitorParam4046(String pMonitorParam4046) {
        monitorParam4046 = getFloat(pMonitorParam4046);
    }

    /**
     * @return the monitorParam4047
     */
    public Float getMonitorParam4047() {
        return monitorParam4047;
    }

    /**
     * @param pMonitorParam4047
     *            the monitorParam4047 to set
     */
    public void setMonitorParam4047(String pMonitorParam4047) {
        monitorParam4047 = getFloat(pMonitorParam4047);
    }

    /**
     * @return the monitorParam4048
     */
    public Float getMonitorParam4048() {
        return monitorParam4048;
    }

    /**
     * @param pMonitorParam4048
     *            the monitorParam4048 to set
     */
    public void setMonitorParam4048(String pMonitorParam4048) {
        monitorParam4048 = getFloat(pMonitorParam4048);
    }

    /**
     * @return the monitorParam4049
     */
    public Float getMonitorParam4049() {
        return monitorParam4049;
    }

    /**
     * @param pMonitorParam4049
     *            the monitorParam4049 to set
     */
    public void setMonitorParam4049(String pMonitorParam4049) {
        monitorParam4049 = getFloat(pMonitorParam4049);
    }

    /**
     * @return the monitorParam4050
     */
    public Float getMonitorParam4050() {
        return monitorParam4050;
    }

    /**
     * @param pMonitorParam4050
     *            the monitorParam4050 to set
     */
    public void setMonitorParam4050(String pMonitorParam4050) {
        monitorParam4050 = getFloat(pMonitorParam4050);
    }

    /**
     * @return the monitorParam5005
     */
    public Float getMonitorParam5005() {
        return monitorParam5005;
    }

    /**
     * @param pMonitorParam5005
     *            the monitorParam5005 to set
     */
    public void setMonitorParam5005(String pMonitorParam5005) {
        monitorParam5005 = getFloat(pMonitorParam5005);
    }

    /**
     * @return the monitorParam5006
     */
    public Float getMonitorParam5006() {
        return monitorParam5006;
    }

    /**
     * @param pMonitorParam5006
     *            the monitorParam5006 to set
     */
    public void setMonitorParam5006(String pMonitorParam5006) {
        monitorParam5006 = getFloat(pMonitorParam5006);
    }

    /**
     * @return the monitorParam5007
     */
    public Float getMonitorParam5007() {
        return monitorParam5007;
    }

    /**
     * @param pMonitorParam5007
     *            the monitorParam5007 to set
     */
    public void setMonitorParam5007(String pMonitorParam5007) {
        monitorParam5007 = getFloat(pMonitorParam5007);
    }

    /**
     * @return the monitorParam5008
     */
    public Float getMonitorParam5008() {
        return monitorParam5008;
    }

    /**
     * @param pMonitorParam5008
     *            the monitorParam5008 to set
     */
    public void setMonitorParam5008(String pMonitorParam5008) {
        monitorParam5008 = getFloat(pMonitorParam5008);
    }

    /**
     * @return the monitorParam5009
     */
    public Float getMonitorParam5009() {
        return monitorParam5009;
    }

    /**
     * @param pMonitorParam5009
     *            the monitorParam5009 to set
     */
    public void setMonitorParam5009(String pMonitorParam5009) {
        monitorParam5009 = getFloat(pMonitorParam5009);
    }

    /**
     * @return the monitorParam5010
     */
    public Float getMonitorParam5010() {
        return monitorParam5010;
    }

    /**
     * @param pMonitorParam5010
     *            the monitorParam5010 to set
     */
    public void setMonitorParam5010(String pMonitorParam5010) {
        monitorParam5010 = getFloat(pMonitorParam5010);
    }

    /**
     * @return the monitorParam192
     */
    public Float getMonitorParam192() {
        return monitorParam192;
    }

    /**
     * @param pMonitorParam192
     *            the monitorParam192 to set
     */
    public void setMonitorParam192(String pMonitorParam192) {
        monitorParam192 = getFloat(pMonitorParam192);
    }

    /**
     * @return the monitorParam1006
     */
    public Float getMonitorParam1006() {
        return monitorParam1006;
    }

    /**
     * @param pMonitorParam1006
     *            the monitorParam1006 to set
     */
    public void setMonitorParam1006(String pMonitorParam1006) {
        monitorParam1006 = getFloat(pMonitorParam1006);
    }

    /**
     * @return the monitorParam1014
     */
    public Float getMonitorParam1014() {
        return monitorParam1014;
    }

    /**
     * @param pMonitorParam1014
     *            the monitorParam1014 to set
     */
    public void setMonitorParam1014(String pMonitorParam1014) {
        monitorParam1014 = getFloat(pMonitorParam1014);
    }

    /**
     * @return the monitorParam9502
     */
    public Float getMonitorParam9502() {
        return monitorParam9502;
    }

    /**
     * @param pMonitorParam9502
     *            the monitorParam9502 to set
     */
    public void setMonitorParam9502(String pMonitorParam9502) {
        monitorParam9502 = getFloat(pMonitorParam9502);
    }

    /**
     * @return the monitorParam9503
     */
    public Float getMonitorParam9503() {
        return monitorParam9503;
    }

    /**
     * @param pMonitorParam9503
     *            the monitorParam9503 to set
     */
    public void setMonitorParam9503(String pMonitorParam9503) {
        monitorParam9503 = getFloat(pMonitorParam9503);
    }

    /**
     * @return the monitorParam2226
     */
    public Float getMonitorParam2226() {
        return monitorParam2226;
    }

    /**
     * @param pMonitorParam2226
     *            the monitorParam2226 to set
     */
    public void setMonitorParam2226(String pMonitorParam2226) {
        monitorParam2226 = getFloat(pMonitorParam2226);
    }

    /**
     * @return the monitorParam1598
     */
    public Float getMonitorParam1598() {
        return monitorParam1598;
    }

    /**
     * @param pMonitorParam1598
     *            the monitorParam1598 to set
     */
    public void setMonitorParam1598(String pMonitorParam1598) {
        monitorParam1598 = getFloat(pMonitorParam1598);
    }

    /**
     * @return the monitorParam509
     */
    public Float getMonitorParam509() {
        return monitorParam509;
    }

    /**
     * @param pMonitorParam509
     *            the monitorParam509 to set
     */
    public void setMonitorParam509(String pMonitorParam509) {
        monitorParam509 = getFloat(pMonitorParam509);
    }

    /**
     * @return the monitorParam514
     */
    public Float getMonitorParam514() {
        return monitorParam514;
    }

    /**
     * @param pMonitorParam514
     *            the monitorParam514 to set
     */
    public void setMonitorParam514(String pMonitorParam514) {
        monitorParam514 = getFloat(pMonitorParam514);
    }

    /**
     * @return the monitorParam2472
     */
    public Float getMonitorParam2472() {
        return monitorParam2472;
    }

    /**
     * @param pMonitorParam2472
     *            the monitorParam2472 to set
     */
    public void setMonitorParam2472(String pMonitorParam2472) {
        monitorParam2472 = getFloat(pMonitorParam2472);
    }

    /**
     * @return the monitorParam2479
     */
    public Float getMonitorParam2479() {
        return monitorParam2479;
    }

    /**
     * @param pMonitorParam2479
     *            the monitorParam2479 to set
     */
    public void setMonitorParam2479(String pMonitorParam2479) {
        monitorParam2479 = getFloat(pMonitorParam2479);
    }

    /**
     * @return the monitorParam2480
     */
    public Float getMonitorParam2480() {
        return monitorParam2480;
    }

    /**
     * @param pMonitorParam2480
     *            the monitorParam2480 to set
     */
    public void setMonitorParam2480(String pMonitorParam2480) {
        monitorParam2480 = getFloat(pMonitorParam2480);
    }

    /**
     * @return the monitorParam2486
     */
    public Float getMonitorParam2486() {
        return monitorParam2486;
    }

    /**
     * @param pMonitorParam2486
     *            the monitorParam2486 to set
     */
    public void setMonitorParam2486(String pMonitorParam2486) {
        monitorParam2486 = getFloat(pMonitorParam2486);
    }

    /**
     * @return the monitorParam2041
     */
    public Float getMonitorParam2041() {
        return monitorParam2041;
    }

    /**
     * @param pMonitorParam2041
     *            the monitorParam2041 to set
     */
    public void setMonitorParam2041(String pMonitorParam2041) {
        monitorParam2041 = getFloat(pMonitorParam2041);
    }

    /**
     * @return the monitorParam2042
     */
    public Float getMonitorParam2042() {
        return monitorParam2042;
    }

    /**
     * @param pMonitorParam2042
     *            the monitorParam2042 to set
     */
    public void setMonitorParam2042(String pMonitorParam2042) {
        monitorParam2042 = getFloat(pMonitorParam2042);
    }

    /**
     * @return the monitorParam2043
     */
    public Float getMonitorParam2043() {
        return monitorParam2043;
    }

    /**
     * @param pMonitorParam2043
     *            the monitorParam2043 to set
     */
    public void setMonitorParam2043(String pMonitorParam2043) {
        monitorParam2043 = getFloat(pMonitorParam2043);
    }

    /**
     * @return the validityBitSet1
     */
    public String getValidityBitSet1() {
        return validityBitSet1;
    }

    /**
     * @param pValidityBitSet1
     *            the validityBitSet1 to set
     */
    public void setValidityBitSet1(String pValidityBitSet1) {
        validityBitSet1 = pValidityBitSet1;
    }

    /**
     * @return the validityBitSet2
     */
    public String getValidityBitSet2() {
        return validityBitSet2;
    }

    /**
     * @param pValidityBitSet2
     *            the validityBitSet2 to set
     */
    public void setValidityBitSet2(String pValidityBitSet2) {
        validityBitSet2 = pValidityBitSet2;
    }

    /**
     * @return the validityBitSet3
     */
    public String getValidityBitSet3() {
        return validityBitSet3;
    }

    /**
     * @param pValidityBitSet3
     *            the validityBitSet3 to set
     */
    public void setValidityBitSet3(String pValidityBitSet3) {
        validityBitSet3 = pValidityBitSet3;
    }

    /**
     * @return the validityBitSet4
     */
    public String getValidityBitSet4() {
        return validityBitSet4;
    }

    /**
     * @param pValidityBitSet4
     *            the validityBitSet4 to set
     */
    public void setValidityBitSet4(String pValidityBitSet4) {
        validityBitSet4 = pValidityBitSet4;
    }

    /**
     * @return the validityBitSet5
     */
    public String getValidityBitSet5() {
        return validityBitSet5;
    }

    /**
     * @param pValidityBitSet5
     *            the validityBitSet5 to set
     */
    public void setValidityBitSet5(String pValidityBitSet5) {
        validityBitSet5 = pValidityBitSet5;
    }

    /**
     * @return the validityBitSet6
     */
    public String getValidityBitSet6() {
        return validityBitSet6;
    }

    /**
     * @param pValidityBitSet6
     *            the validityBitSet6 to set
     */
    public void setValidityBitSet6(String pValidityBitSet6) {
        validityBitSet6 = pValidityBitSet6;
    }

    /**
     * @return the validityBitSet7
     */
    public String getValidityBitSet7() {
        return validityBitSet7;
    }

    /**
     * @param pValidityBitSet7
     *            the validityBitSet7 to set
     */
    public void setValidityBitSet7(String pValidityBitSet7) {
        validityBitSet7 = pValidityBitSet7;
    }

    /**
     * @return the validityBitSet8
     */
    public String getValidityBitSet8() {
        return validityBitSet8;
    }

    /**
     * @param pValidityBitSet8
     *            the validityBitSet8 to set
     */
    public void setValidityBitSet8(String pValidityBitSet8) {
        validityBitSet8 = pValidityBitSet8;
    }

    /**
     * @return the validityBitSet9
     */
    public String getValidityBitSet9() {
        return validityBitSet9;
    }

    /**
     * @param pValidityBitSet9
     *            the validityBitSet9 to set
     */
    public void setValidityBitSet9(String pValidityBitSet9) {
        validityBitSet9 = pValidityBitSet9;
    }

    /**
     * @return the validityBitSet10
     */
    public String getValidityBitSet10() {
        return validityBitSet10;
    }

    /**
     * @param pValidityBitSet10
     *            the validityBitSet10 to set
     */
    public void setValidityBitSet10(String pValidityBitSet10) {
        validityBitSet10 = pValidityBitSet10;
    }

    /**
     * @return the validityBitSet11
     */
    public String getValidityBitSet11() {
        return validityBitSet11;
    }

    /**
     * @param pValidityBitSet11
     *            the validityBitSet11 to set
     */
    public void setValidityBitSet11(String pValidityBitSet11) {
        validityBitSet11 = pValidityBitSet11;
    }

    /**
     * @return the validityBitSet12
     */
    public String getValidityBitSet12() {
        return validityBitSet12;
    }

    /**
     * @param pValidityBitSet12
     *            the validityBitSet12 to set
     */
    public void setValidityBitSet12(String pValidityBitSet12) {
        validityBitSet12 = pValidityBitSet12;
    }

    /**
     * @return the validityBitSet13
     */
    public String getValidityBitSet13() {
        return validityBitSet13;
    }

    /**
     * @param pValidityBitSet13
     *            the validityBitSet13 to set
     */
    public void setValidityBitSet13(String pValidityBitSet13) {
        validityBitSet13 = pValidityBitSet13;
    }

    /**
     * @return the validityBitSet14
     */
    public String getValidityBitSet14() {
        return validityBitSet14;
    }

    /**
     * @param pValidityBitSet14
     *            the validityBitSet14 to set
     */
    public void setValidityBitSet14(String pValidityBitSet14) {
        validityBitSet14 = pValidityBitSet14;
    }

    /**
     * @return the validityBitSet15
     */
    public String getValidityBitSet15() {
        return validityBitSet15;
    }

    /**
     * @param pValidityBitSet15
     *            the validityBitSet15 to set
     */
    public void setValidityBitSet15(String pValidityBitSet15) {
        validityBitSet15 = pValidityBitSet15;
    }

    /**
     * @return the validityBitSet16
     */
    public String getValidityBitSet16() {
        return validityBitSet16;
    }

    /**
     * @param pValidityBitSet16
     *            the validityBitSet16 to set
     */
    public void setValidityBitSet16(String pValidityBitSet16) {
        validityBitSet16 = pValidityBitSet16;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.omi.beans.msg.BaseMessage#getAttributeDescriptions()
     */
    @Override
    protected Map<String, String> getAttributeDescriptions() {
        if (descriptions == null) {
            descriptions = new HashMap<String, String>();

            descriptions.put("monitorParam6268", "Gnd_Flt_Ohm_AC");
            descriptions.put("monitorParam6269", "Gnd_Flt_Ohm_DC");
            descriptions.put("monitorParam6301", "Grid_Blwr_1_Spd");
            descriptions.put("monitorParam6302", "Grid_Blwr_2_Spd");
            descriptions.put("monitorParam6303", "Grid_Blwr_3_Spd");
            descriptions.put("monitorParam6900", "TAC_Field_Curr");
            descriptions.put("monitorParam6901", "TAC_Link_Volts");
            descriptions.put("monitorParam3000", "MR1_Pressure");
            descriptions.put("validityBitSet1", "validityBitSet1");
            descriptions.put("monitorParam3002", "BP_Pressure");
            descriptions.put("monitorParam3004", "BC_Pressure");
            descriptions.put("monitorParam3205", "Air_Comp_Spd");
            descriptions.put("monitorParam4000", "Loco_Speed");
            descriptions.put("monitorParam4340", "MPH_Raw_1");
            descriptions.put("monitorParam4341", "MPH_Raw_2");
            descriptions.put("monitorParam4342", "MPH_Raw_3");
            descriptions.put("monitorParam4343", "MPH_Raw_4");
            descriptions.put("validityBitSet2", "validityBitSet2");
            descriptions.put("monitorParam4344", "MPH_Raw_5");
            descriptions.put("monitorParam4345", "MPH_Raw_6");
            descriptions.put("monitorParam5566", "TE_Fdbk_1");
            descriptions.put("monitorParam5567", "TE_Fdbk_2");
            descriptions.put("monitorParam5568", "TE_Fdbk_3");
            descriptions.put("monitorParam5569", "TE_Fdbk_4");
            descriptions.put("monitorParam5570", "TE_Fdbk_5");
            descriptions.put("monitorParam5571", "TE_Fdbk_6");
            descriptions.put("validityBitSet3", "validityBitSet3");
            descriptions.put("monitorParam6205", "Prop_Volt_Lim");
            descriptions.put("monitorParam2200", "Batt_+Gnd_DC");
            descriptions.put("monitorParam2202", "Battery_Volts");
            descriptions.put("monitorParam2209", "Batt_+Gnd_AC");
            descriptions.put("monitorParam2211", "BCC_Current");
            descriptions.put("monitorParam2213", "BCC_Ph_Angle");
            descriptions.put("monitorParam2035", "AA_In_Volts");
            descriptions.put("monitorParam2050", "Aux_Hp");
            descriptions.put("validityBitSet4", "validityBitSet4");
            descriptions.put("monitorParam2053", "Air_Comp_HP");
            descriptions.put("monitorParam1833", "Xfr_Sw_Position");
            descriptions.put("monitorParam2002", "AA_Volts_Hertz");
            descriptions.put("monitorParam2010", "AA_Phase_A_Curr");
            descriptions.put("monitorParam2011", "AA_Phase_B_Curr");
            descriptions.put("monitorParam2012", "AA_Phase_C_Curr");
            descriptions.put("monitorParam2020", "AA_Out_Volts");
            descriptions.put("monitorParam2021", "AA_Field_Curr");
            descriptions.put("validityBitSet5", "validityBitSet5");
            descriptions.put("monitorParam2022", "AAC_Ph_Angle");
            descriptions.put("monitorParam2025", "AA_Gnd_AC");
            descriptions.put("monitorParam1005", "Engine_Speed");
            descriptions.put("monitorParam1025", "HP_Available");
            descriptions.put("monitorParam1055", "Fuel_Demand");
            descriptions.put("monitorParam1056", "Fuel_Value");
            descriptions.put("monitorParam1057", "Fuel_Limit");
            descriptions.put("monitorParam1070", "FPR_Fdbk_ECU");
            descriptions.put("validityBitSet6", "validityBitSet6");
            descriptions.put("monitorParam1500", "Water_In_Temp");
            descriptions.put("monitorParam1501", "Water_Pressure");
            descriptions.put("monitorParam1506", "Oil_Pressure");
            descriptions.put("monitorParam1507", "Oil_Inlet_Temp");
            descriptions.put("monitorParam1510", "Manifold_Temp");
            descriptions.put("monitorParam1511", "Manifold_Press");
            descriptions.put("monitorParam1525", "Preturbine_Left");
            descriptions.put("monitorParam1526", "Preturbine_Right");
            descriptions.put("validityBitSet7", "validityBitSet7");
            descriptions.put("monitorParam1531", "Fuel_Pressure");
            descriptions.put("monitorParam1535", "Crankcase_Press");
            descriptions.put("monitorParam1570", "RF_1_I2T");
            descriptions.put("monitorParam1571", "RF_2_I2T");
            descriptions.put("monitorParam1580", "RF_1_A_Current");
            descriptions.put("monitorParam1581", "RF_1_B_Current");
            descriptions.put("monitorParam1582", "RF_1_C_Current");
            descriptions.put("monitorParam1583", "RF_2_A_Current");
            descriptions.put("validityBitSet8", "validityBitSet8");
            descriptions.put("monitorParam1584", "RF_2_B_Current");
            descriptions.put("monitorParam1585", "RF_2_C_Current");
            descriptions.put("monitorParam6926", "TAC_Ph_Angle");
            descriptions.put("monitorParam7103", "Link_Volts_1");
            descriptions.put("monitorParam7104", "Link_Volts_2");
            descriptions.put("monitorParam7105", "Link_Volts_3");
            descriptions.put("monitorParam7106", "Link_Volts_4");
            descriptions.put("monitorParam7107", "Link_Volts_5");
            descriptions.put("validityBitSet9", "validityBitSet9");
            descriptions.put("monitorParam7108", "Link_Volts_6");
            descriptions.put("monitorParam8002", "TMB_I2T");
            descriptions.put("monitorParam8005", "TMB_A_Current");
            descriptions.put("monitorParam8006", "TMB_B_Current");
            descriptions.put("monitorParam8007", "TMB_C_Current");
            descriptions.put("monitorParam8030", "Exhauster_CB");
            descriptions.put("monitorParam100", "Loco_State");
            descriptions.put("monitorParam102", "Notch");
            descriptions.put("validityBitSet10", "validityBitSet10");
            descriptions.put("monitorParam109", "Eng_In_Air_Temp");
            descriptions.put("monitorParam110", "Ambient_Temp");
            descriptions.put("monitorParam111", "Barometric_Press");
            descriptions.put("monitorParam165", "Barrier_Bar");
            descriptions.put("monitorParam185", "Brake_Percent");
            descriptions.put("monitorParam195", "Direction_Call");
            descriptions.put("monitorParam205", "LCCB");
            descriptions.put("monitorParam4009", "Active_Tachometers");
            descriptions.put("validityBitSet11", "validityBitSet11");
            descriptions.put("monitorParam1000", "Gross_Hp");
            descriptions.put("monitorParam1050", "ECU_Mode");
            descriptions.put("monitorParam1051", "ECU_Eng_State");
            descriptions.put("monitorParam1151", "Turbo_Speed_Right");
            descriptions.put("monitorParam1207", "Advance_Angle");
            descriptions.put("monitorParam1208", "Duration_Angle");
            descriptions.put("monitorParam1224", "Adaptive_Fuel_Limit");
            descriptions.put("monitorParam1235", "Max_HP_Limiter");
            descriptions.put("validityBitSet12", "validityBitSet12");
            descriptions.put("monitorParam1562", "ECS_State");
            descriptions.put("monitorParam2220", "LPS_-15_Volt");
            descriptions.put("monitorParam2221", "LPS_+15_Volt");
            descriptions.put("monitorParam2222", "LPS_+5_Volt");
            descriptions.put("monitorParam4045", "Wheel_Diam_1");
            descriptions.put("monitorParam4046", "Wheel_Diam_2");
            descriptions.put("monitorParam4047", "Wheel_Diam_3");
            descriptions.put("monitorParam4048", "Wheel_Diam_4");
            descriptions.put("validityBitSet13", "validityBitSet13");
            descriptions.put("monitorParam4049", "Wheel_Diam_5");
            descriptions.put("monitorParam4050", "Wheel_Diam_6");
            descriptions.put("monitorParam5005", "Trq_Cmd_1");
            descriptions.put("monitorParam5006", "Trq_Cmd_2");
            descriptions.put("monitorParam5007", "Trq_Cmd_3");
            descriptions.put("monitorParam5008", "Trq_Cmd_4");
            descriptions.put("monitorParam5009", "Trq_Cmd_5");
            descriptions.put("monitorParam5010", "Trq_Cmd_6");
            descriptions.put("validityBitSet14", "validityBitSet14");
            descriptions.put("monitorParam192", "Lead_Trail_T_F");
            descriptions.put("monitorParam1006", "Engine_Speed_Demand");
            descriptions.put("monitorParam1014", "Load_Pot_HP");
            descriptions.put("monitorParam9502", "Ntwk_0_Recon");
            descriptions.put("monitorParam9503", "Ntwk_1_Recon");
            descriptions.put("monitorParam2226", "BCC_Junc_Temp");
            descriptions.put("monitorParam1598", "RFC1_Junc_Tmp");
            descriptions.put("monitorParam509", "GPS_Latitude");
            descriptions.put("validityBitSet15", "validityBitSet15");
            descriptions.put("monitorParam514", "GPS_Longitude");
            descriptions.put("monitorParam2472", "BCC_Trainline_Vfb");
            descriptions.put("monitorParam2479", "WBC_Output_Current");
            descriptions.put("monitorParam2480", "WBC_Source_Volts");
            descriptions.put("monitorParam2486", "WBC_Junc_Temp");
            descriptions.put("monitorParam2041", "RFC1_Spc_Mc_Spr_Mr_Sbr");
            descriptions.put("monitorParam2042", "RFC2_Spc_Mc_Spr_Mr_Sbr");
            descriptions.put("monitorParam2043", "TBC_Spc_Mc_Spr_Mr_Sbr");
            descriptions.put("validityBitSet16", "validityBitSet16");
        }

        return descriptions;
    }
}
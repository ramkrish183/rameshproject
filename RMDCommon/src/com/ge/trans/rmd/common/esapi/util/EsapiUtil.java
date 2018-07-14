package com.ge.trans.rmd.common.esapi.util;

import java.util.ArrayList;
import java.util.List;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/*******************************************************************************
 * @Author : 307009968
 * @Version : 1.0
 * @Date Created: Oct 27, 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : ESAPI utilities : retrofit security into existing applications
 * @History :
 ******************************************************************************/
public class EsapiUtil {

    /**
     * @Author:
     * @param : Long
     * @return: Long
     * @Description: This method will Strip the XSS Characters from String.
     */

    public static String stripXSSCharacters(String input) {
        String encodedString = null;
        String decodedString = null;
        if (null != input) {
            encodedString = ESAPI.encoder().encodeForHTML(input);
            decodedString = ESAPI.encoder().decodeForHTML(encodedString);
        }
        return decodedString;
    }

    /**
     * @Author:
     * @param : Long
     * @return: Long
     * @Description: This method will Strip the XSS Characters from integer.
     */
    public static int stripXSSCharctersFromInteger(int f) {
        String encodedString = null;
        String decodedString = null;
        String strInput = String.valueOf(f);
        int outPutValue = 0;
        if (null != strInput) {
            encodedString = ESAPI.encoder().encodeForHTML(strInput);
            decodedString = ESAPI.encoder().decodeForHTML(encodedString);
            outPutValue = Integer.parseInt(decodedString);
        }
        return outPutValue;
    }

    /**
     * @Author:
     * @param : Long
     * @return: Long
     * @Description: This method will Strip the XSS Characters from an Array
     *               List of Strings.
     */
    public static float stripXSSCharctersFromFloat(float f) {
        String encodedString = null;
        String decodedString = null;
        String strInput = String.valueOf(f);
        float outPutValue = 0;
        if (null != strInput) {
            encodedString = ESAPI.encoder().encodeForHTML(strInput);
            decodedString = ESAPI.encoder().decodeForHTML(encodedString);
            outPutValue = Float.parseFloat(decodedString);
        }
        return outPutValue;
    }

    /**
     * @Author:
     * @param : Long
     * @return: Long
     * @Description: This method will Strip the XSS Characters from an Array
     *               List of Strings.
     */
    public static List<String> stripXSSCharctersfromList(List<String> arrList) {
        List<String> strippedArray = new ArrayList<String>();

        for (String input : arrList) {
            String encodedString = null;
            String decodedString = null;
            if (null != input) {
                encodedString = ESAPI.encoder().encodeForHTML(input);
                decodedString = ESAPI.encoder().decodeForHTML(encodedString);
                strippedArray.add(decodedString);
            }
        }
        return strippedArray;
    }

    /**
     * @Author:
     * @param : Long
     * @return: Long
     * @Description: This method will Strip the XSS Characters from an boolean
     *               value.
     */
    public static boolean stripXSSCharactersFromBoolean(boolean input) {

        String encodedString = null;
        String  decodedString = null;
        String strInput = String.valueOf(input);
        boolean outPutValue = false;
        if (null != strInput) {
            encodedString = ESAPI.encoder().encodeForHTML(strInput);
            decodedString = ESAPI.encoder().decodeForHTML(encodedString);
            outPutValue = Boolean.parseBoolean(decodedString);
        }
        return outPutValue;
    }
    
    /**
     * @Author:
     * @param : Long
     * @return: Long
     * @Description: This method will Strip the XSS Characters from an Array
     *               List of Strings.
     */
    public static List<String> encodeForSQLfromList(List<String> arrList) {
        List<String> strippedArray = new ArrayList<String>();
        Codec ORACLE_CODEC = new OracleCodec();

        for (String input : arrList) {
            String encodedString = null;
            if (null != input) {
                encodedString = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,input);
                strippedArray.add(encodedString);
            }
        }
        return strippedArray;
    }
    
    public static float stripXSSCharctersFromFloat(long f) {
        String encodedString = null;
        String decodedString = null;
        String strInput = String.valueOf(f);
        long outPutValue = 0;
        if (null != strInput) {
            encodedString = ESAPI.encoder().encodeForHTML(strInput);
            decodedString = ESAPI.encoder().decodeForHTML(encodedString);
            outPutValue = Long.parseLong(decodedString);
        }
        return outPutValue;
    }
    
    public static String escapeSpecialChars(String strToReplace) {
        String newStrReplace = strToReplace;
        try {
        	//Hex A0 to AF
        	//Character.toString(c)
        	newStrReplace = newStrReplace.replace(String.valueOf((char)161), "&#161;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)162), "&#162;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)163), "&#163;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)164), "&#164;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)165), "&#165;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)166), "&#166;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)167), "&#167;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)168), "&#168;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)169), "&#169;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)170), "&#170;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)171), "&#171;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)172), "&#172;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)173), "&#173;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)174), "&#174;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)175), "&#175;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)176), "&#176;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)177), "&#177;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)178), "&#178;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)179), "&#179;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)180), "&#180;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)181), "&#181;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)182), "&#182;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)183), "&#183;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)184), "&#184;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)185), "&#185;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)186), "&#186;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)187), "&#187;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)188), "&#188;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)189), "&#189;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)190), "&#190;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)191), "&#191;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)192), "&#192;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)193), "&#193;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)194), "&#194;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)195), "&#195;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)196), "&#196;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)197), "&#197;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)198), "&#198;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)199), "&#199;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)200), "&#200;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)201), "&#201;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)202), "&#202;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)203), "&#203;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)204), "&#204;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)205), "&#205;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)206), "&#206;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)207), "&#207;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)208), "&#208;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)209), "&#209;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)210), "&#210;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)211), "&#211;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)212), "&#212;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)213), "&#213;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)214), "&#214;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)215), "&#215;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)216), "&#216;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)217), "&#217;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)218), "&#218;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)219), "&#219;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)220), "&#220;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)221), "&#221;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)222), "&#222;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)223), "&#223;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)224), "&#224;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)225), "&#225;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)226), "&#226;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)227), "&#227;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)228), "&#228;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)229), "&#229;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)230), "&#230;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)231), "&#231;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)232), "&#232;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)233), "&#233;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)234), "&#234;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)235), "&#235;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)236), "&#236;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)237), "&#237;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)238), "&#238;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)239), "&#239;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)240), "&#240;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)241), "&#241;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)242), "&#242;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)243), "&#243;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)244), "&#244;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)245), "&#245;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)246), "&#246;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)247), "&#247;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)248), "&#248;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)249), "&#249;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)250), "&#250;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)251), "&#251;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)252), "&#252;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)253), "&#253;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)254), "&#254;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)255), "&#255;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)338), "&#338;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)339), "&#339;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)352), "&#352;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)353), "&#353;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)376), "&#376;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)402), "&#402;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8194), "&#8194;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8195), "&#8195;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8196), "&#8196;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8211), "&#8211;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8212), "&#8212;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8216), "&#8216;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8217), "&#8217;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8218), "&#8218;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8220), "&#8220;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8221), "&#8221;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8222), "&#8222;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8224), "&#8224;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8225), "&#8225;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8226), "&#8226;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8230), "&#8230;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8240), "&#8240;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8364), "&#8364;");
        	newStrReplace = newStrReplace.replace(String.valueOf((char)8482), "&#8482;");      	

            
        } catch (Exception e) {
            return newStrReplace;
        }
        return newStrReplace;
    }
    
    public static String resumeSpecialChars(String strToReplace) {
        String newStrReplace = strToReplace;
        try {
        	//Hex A0 to AF
        	//Character.toString(c)
        	newStrReplace = newStrReplace.replace("&#161;", String.valueOf((char)161));
        	newStrReplace = newStrReplace.replace("&#162;", String.valueOf((char)162));
        	newStrReplace = newStrReplace.replace("&#163;", String.valueOf((char)163));
        	newStrReplace = newStrReplace.replace("&#164;", String.valueOf((char)164));
        	newStrReplace = newStrReplace.replace("&#165;", String.valueOf((char)165));
        	newStrReplace = newStrReplace.replace("&#166;", String.valueOf((char)166));
        	newStrReplace = newStrReplace.replace("&#167;", String.valueOf((char)167));
        	newStrReplace = newStrReplace.replace("&#168;", String.valueOf((char)168));
        	newStrReplace = newStrReplace.replace("&#169;", String.valueOf((char)169));
        	newStrReplace = newStrReplace.replace("&#170;", String.valueOf((char)170));
        	newStrReplace = newStrReplace.replace("&#171;", String.valueOf((char)171));
        	newStrReplace = newStrReplace.replace("&#172;", String.valueOf((char)172));
        	newStrReplace = newStrReplace.replace("&#173;", String.valueOf((char)173));
        	newStrReplace = newStrReplace.replace("&#174;", String.valueOf((char)174));
        	newStrReplace = newStrReplace.replace("&#175;", String.valueOf((char)175));
        	newStrReplace = newStrReplace.replace("&#176;", String.valueOf((char)176));
        	newStrReplace = newStrReplace.replace("&#177;", String.valueOf((char)177));
        	newStrReplace = newStrReplace.replace("&#178;", String.valueOf((char)178));
        	newStrReplace = newStrReplace.replace("&#179;", String.valueOf((char)179));
        	newStrReplace = newStrReplace.replace("&#180;", String.valueOf((char)180));
        	newStrReplace = newStrReplace.replace("&#181;", String.valueOf((char)181));
        	newStrReplace = newStrReplace.replace("&#182;", String.valueOf((char)182));
        	newStrReplace = newStrReplace.replace("&#183;", String.valueOf((char)183));
        	newStrReplace = newStrReplace.replace("&#184;", String.valueOf((char)184));
        	newStrReplace = newStrReplace.replace("&#185;", String.valueOf((char)185));
        	newStrReplace = newStrReplace.replace("&#186;", String.valueOf((char)186));
        	newStrReplace = newStrReplace.replace("&#187;", String.valueOf((char)187));
        	newStrReplace = newStrReplace.replace("&#188;", String.valueOf((char)188));
        	newStrReplace = newStrReplace.replace("&#189;", String.valueOf((char)189));
        	newStrReplace = newStrReplace.replace("&#190;", String.valueOf((char)190));
        	newStrReplace = newStrReplace.replace("&#191;", String.valueOf((char)191));
        	newStrReplace = newStrReplace.replace("&#192;", String.valueOf((char)192));
        	newStrReplace = newStrReplace.replace("&#193;", String.valueOf((char)193));
        	newStrReplace = newStrReplace.replace("&#194;", String.valueOf((char)194));
        	newStrReplace = newStrReplace.replace("&#195;", String.valueOf((char)195));
        	newStrReplace = newStrReplace.replace("&#196;", String.valueOf((char)196));
        	newStrReplace = newStrReplace.replace("&#197;", String.valueOf((char)197));
        	newStrReplace = newStrReplace.replace("&#198;", String.valueOf((char)198));
        	newStrReplace = newStrReplace.replace("&#199;", String.valueOf((char)199));
        	newStrReplace = newStrReplace.replace("&#200;", String.valueOf((char)200));
        	newStrReplace = newStrReplace.replace("&#201;", String.valueOf((char)201));
        	newStrReplace = newStrReplace.replace("&#202;", String.valueOf((char)202));
        	newStrReplace = newStrReplace.replace("&#203;", String.valueOf((char)203));
        	newStrReplace = newStrReplace.replace("&#204;", String.valueOf((char)204));
        	newStrReplace = newStrReplace.replace("&#205;", String.valueOf((char)205));
        	newStrReplace = newStrReplace.replace("&#206;", String.valueOf((char)206));
        	newStrReplace = newStrReplace.replace("&#207;", String.valueOf((char)207));
        	newStrReplace = newStrReplace.replace("&#208;", String.valueOf((char)208));
        	newStrReplace = newStrReplace.replace("&#209;", String.valueOf((char)209));
        	newStrReplace = newStrReplace.replace("&#210;", String.valueOf((char)210));
        	newStrReplace = newStrReplace.replace("&#211;", String.valueOf((char)211));
        	newStrReplace = newStrReplace.replace("&#212;", String.valueOf((char)212));
        	newStrReplace = newStrReplace.replace("&#213;", String.valueOf((char)213));
        	newStrReplace = newStrReplace.replace("&#214;", String.valueOf((char)214));
        	newStrReplace = newStrReplace.replace("&#215;", String.valueOf((char)215));
        	newStrReplace = newStrReplace.replace("&#216;", String.valueOf((char)216));
        	newStrReplace = newStrReplace.replace("&#217;", String.valueOf((char)217));
        	newStrReplace = newStrReplace.replace("&#218;", String.valueOf((char)218));
        	newStrReplace = newStrReplace.replace("&#219;", String.valueOf((char)219));
        	newStrReplace = newStrReplace.replace("&#220;", String.valueOf((char)220));
        	newStrReplace = newStrReplace.replace("&#221;", String.valueOf((char)221));
        	newStrReplace = newStrReplace.replace("&#222;", String.valueOf((char)222));
        	newStrReplace = newStrReplace.replace("&#223;", String.valueOf((char)223));
        	newStrReplace = newStrReplace.replace("&#224;", String.valueOf((char)224));
        	newStrReplace = newStrReplace.replace("&#225;", String.valueOf((char)225));
        	newStrReplace = newStrReplace.replace("&#226;", String.valueOf((char)226));
        	newStrReplace = newStrReplace.replace("&#227;", String.valueOf((char)227));
        	newStrReplace = newStrReplace.replace("&#228;", String.valueOf((char)228));
        	newStrReplace = newStrReplace.replace("&#229;", String.valueOf((char)229));
        	newStrReplace = newStrReplace.replace("&#230;", String.valueOf((char)230));
        	newStrReplace = newStrReplace.replace("&#231;", String.valueOf((char)231));
        	newStrReplace = newStrReplace.replace("&#232;", String.valueOf((char)232));
        	newStrReplace = newStrReplace.replace("&#233;", String.valueOf((char)233));
        	newStrReplace = newStrReplace.replace("&#234;", String.valueOf((char)234));
        	newStrReplace = newStrReplace.replace("&#235;", String.valueOf((char)235));
        	newStrReplace = newStrReplace.replace("&#236;", String.valueOf((char)236));
        	newStrReplace = newStrReplace.replace("&#237;", String.valueOf((char)237));
        	newStrReplace = newStrReplace.replace("&#238;", String.valueOf((char)238));
        	newStrReplace = newStrReplace.replace("&#239;", String.valueOf((char)239));
        	newStrReplace = newStrReplace.replace("&#240;", String.valueOf((char)240));
        	newStrReplace = newStrReplace.replace("&#241;", String.valueOf((char)241));
        	newStrReplace = newStrReplace.replace("&#242;", String.valueOf((char)242));
        	newStrReplace = newStrReplace.replace("&#243;", String.valueOf((char)243));
        	newStrReplace = newStrReplace.replace("&#244;", String.valueOf((char)244));
        	newStrReplace = newStrReplace.replace("&#245;", String.valueOf((char)245));
        	newStrReplace = newStrReplace.replace("&#246;", String.valueOf((char)246));
        	newStrReplace = newStrReplace.replace("&#247;", String.valueOf((char)247));
        	newStrReplace = newStrReplace.replace("&#248;", String.valueOf((char)248));
        	newStrReplace = newStrReplace.replace("&#249;", String.valueOf((char)249));
        	newStrReplace = newStrReplace.replace("&#250;", String.valueOf((char)250));
        	newStrReplace = newStrReplace.replace("&#251;", String.valueOf((char)251));
        	newStrReplace = newStrReplace.replace("&#252;", String.valueOf((char)252));
        	newStrReplace = newStrReplace.replace("&#253;", String.valueOf((char)253));
        	newStrReplace = newStrReplace.replace("&#254;", String.valueOf((char)254));
        	newStrReplace = newStrReplace.replace("&#255;", String.valueOf((char)255));
        	newStrReplace = newStrReplace.replace("&#338;", String.valueOf((char)338));
        	newStrReplace = newStrReplace.replace("&#339;", String.valueOf((char)339));
        	newStrReplace = newStrReplace.replace("&#352;", String.valueOf((char)352));
        	newStrReplace = newStrReplace.replace("&#353;", String.valueOf((char)353));
        	newStrReplace = newStrReplace.replace("&#376;", String.valueOf((char)376));
        	newStrReplace = newStrReplace.replace("&#402;", String.valueOf((char)402));
        	newStrReplace = newStrReplace.replace("&#8194;", String.valueOf((char)8194));
        	newStrReplace = newStrReplace.replace("&#8195;", String.valueOf((char)8195));
        	newStrReplace = newStrReplace.replace("&#8196;", String.valueOf((char)8196));
        	newStrReplace = newStrReplace.replace("&#8211;", String.valueOf((char)8211));
        	newStrReplace = newStrReplace.replace("&#8212;", String.valueOf((char)8212));
        	newStrReplace = newStrReplace.replace("&#8216;", String.valueOf((char)8216));
        	newStrReplace = newStrReplace.replace("&#8217;", String.valueOf((char)8217));
        	newStrReplace = newStrReplace.replace("&#8218;", String.valueOf((char)8218));
        	newStrReplace = newStrReplace.replace("&#8220;", String.valueOf((char)8220));
        	newStrReplace = newStrReplace.replace("&#8221;", String.valueOf((char)8221));
        	newStrReplace = newStrReplace.replace("&#8222;", String.valueOf((char)8222));
        	newStrReplace = newStrReplace.replace("&#8224;", String.valueOf((char)8224));
        	newStrReplace = newStrReplace.replace("&#8225;", String.valueOf((char)8225));
        	newStrReplace = newStrReplace.replace("&#8226;", String.valueOf((char)8226));
        	newStrReplace = newStrReplace.replace("&#8230;", String.valueOf((char)8230));
        	newStrReplace = newStrReplace.replace("&#8240;", String.valueOf((char)8240));
        	newStrReplace = newStrReplace.replace("&#8364;", String.valueOf((char)8364));
        	newStrReplace = newStrReplace.replace("&#8482;", String.valueOf((char)8482));

            
        } catch (Exception e) {
            return newStrReplace;
        }
        return newStrReplace;
    }
    

}

package com.ge.trans.rmd.utilities;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.core.MultivaluedMap;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.tools.keys.util.AppConstants;

@SuppressWarnings("unchecked")
public final class AppSecUtil {
    public static final RMDLogger LOG = RMDLogger.getLogger(AppSecUtil.class);

    static List<String> configList = null;
    static Hashtable<String, String> htmlspecialcharstable = new Hashtable<String, String>();
    static {
        htmlspecialcharstable.put("&lt;", "<");
        htmlspecialcharstable.put("&gt;", ">");
        htmlspecialcharstable.put("&amp;", "&");
        htmlspecialcharstable.put("&quot;", "\"");
        htmlspecialcharstable.put("&#033;", "!");
        htmlspecialcharstable.put("&#035;", "#");
        htmlspecialcharstable.put("&#036;", "$");
        htmlspecialcharstable.put("&#037;", "%");
        htmlspecialcharstable.put("&#039;", "'");
        htmlspecialcharstable.put("&#040;", "(");
        htmlspecialcharstable.put("&#041;", ")");
        htmlspecialcharstable.put("&#042;", "*");
        htmlspecialcharstable.put("&#043;", "+");
        htmlspecialcharstable.put("&#044;", ",");
        htmlspecialcharstable.put("&#045;", "-");
        htmlspecialcharstable.put("&#046;", ".");
        htmlspecialcharstable.put("&#047;", "/");
        htmlspecialcharstable.put("&#058;", ":");
        htmlspecialcharstable.put("&#059;", ";");
        htmlspecialcharstable.put("&#061;", "=");
        htmlspecialcharstable.put("&#063;", "?");
        htmlspecialcharstable.put("&#064;", "@");
        htmlspecialcharstable.put("&#091;", "[");
        htmlspecialcharstable.put("&#093;", "]");
        htmlspecialcharstable.put("&#094;", "^");
        htmlspecialcharstable.put("&#095;", "_");
        htmlspecialcharstable.put("&#096;", "`");
        htmlspecialcharstable.put("&#123;", "{");
        htmlspecialcharstable.put("&#124;", "|");
        htmlspecialcharstable.put("&#125;", "}");
        htmlspecialcharstable.put("&#126;", "~");
        htmlspecialcharstable.put("&#092;", "\\");
        htmlspecialcharstable.put("&#092;t", "\t");
        htmlspecialcharstable.put("&#009;", "	");
    }

    static Hashtable<String, String> htmlspecialcharstableFSS = new Hashtable<String, String>();
    static {
        htmlspecialcharstableFSS.put("&lt;", "<");
        htmlspecialcharstableFSS.put("&gt;", ">");
        htmlspecialcharstableFSS.put("&amp;", "&");
        htmlspecialcharstableFSS.put("&quot;", "\"");
        htmlspecialcharstableFSS.put("&#123;", "{");
        htmlspecialcharstableFSS.put("&#124;", "|");
        htmlspecialcharstableFSS.put("&#125;", "}");
        htmlspecialcharstableFSS.put("&#126;", "~");
        htmlspecialcharstableFSS.put("&#33;", "!");
        htmlspecialcharstableFSS.put("&#35;", "#");
        htmlspecialcharstableFSS.put("&#36;", "$");
        htmlspecialcharstableFSS.put("&#37;", "%");
        htmlspecialcharstableFSS.put("&#39;", "'");
        htmlspecialcharstableFSS.put("&#40;", "(");
        htmlspecialcharstableFSS.put("&#41;", ")");
        htmlspecialcharstableFSS.put("&#42;", "*");
        htmlspecialcharstableFSS.put("&#43;", "+");
        htmlspecialcharstableFSS.put("&#44;", ",");
        htmlspecialcharstableFSS.put("&#45;", "-");
        htmlspecialcharstableFSS.put("&#46;", ".");
        htmlspecialcharstableFSS.put("&#47;", "/");
        htmlspecialcharstableFSS.put("&#58;", ":");
        htmlspecialcharstableFSS.put("&#59;", ";");
        htmlspecialcharstableFSS.put("&#61;", "=");
        htmlspecialcharstableFSS.put("&#63;", "?");
        htmlspecialcharstableFSS.put("&#64;", "@");
        htmlspecialcharstableFSS.put("&#91;", "[");
        htmlspecialcharstableFSS.put("&#93;", "]");
        htmlspecialcharstableFSS.put("&#94;", "^");
        htmlspecialcharstableFSS.put("&#95;", "_");
        htmlspecialcharstableFSS.put("&#96;", "`");
        htmlspecialcharstableFSS.put("&#92;", "\\");
        htmlspecialcharstableFSS.put("&#9;", "	");
    }
    static List<Integer> arlAsciiCodes = new ArrayList<Integer>();
    static {
        arlAsciiCodes.add(177);
        arlAsciiCodes.add(188);
        arlAsciiCodes.add(189);
        arlAsciiCodes.add(190);
        arlAsciiCodes.add(215);
        arlAsciiCodes.add(247);
        arlAsciiCodes.add(8704);
        arlAsciiCodes.add(8706);
        arlAsciiCodes.add(8707);
        arlAsciiCodes.add(8709);
        arlAsciiCodes.add(8711);
        arlAsciiCodes.add(8712);
        arlAsciiCodes.add(8713);
        arlAsciiCodes.add(8715);
        arlAsciiCodes.add(8719);
        arlAsciiCodes.add(8721);
        arlAsciiCodes.add(8730);
        arlAsciiCodes.add(8733);
        arlAsciiCodes.add(8734);
        arlAsciiCodes.add(8736);
        arlAsciiCodes.add(8743);
        arlAsciiCodes.add(8744);
        arlAsciiCodes.add(8745);
        arlAsciiCodes.add(8746);
        arlAsciiCodes.add(8747);
        arlAsciiCodes.add(8756);
        arlAsciiCodes.add(8764);
        arlAsciiCodes.add(8773);
        arlAsciiCodes.add(8776);
        arlAsciiCodes.add(8800);
        arlAsciiCodes.add(8801);
        arlAsciiCodes.add(8804);
        arlAsciiCodes.add(8805);
        arlAsciiCodes.add(8834);
        arlAsciiCodes.add(8835);
        arlAsciiCodes.add(8836);
        arlAsciiCodes.add(8838);
        arlAsciiCodes.add(8839);
        arlAsciiCodes.add(8853);
        arlAsciiCodes.add(8855);
        arlAsciiCodes.add(8869);
        arlAsciiCodes.add(8901);
        arlAsciiCodes.add(8722);
        arlAsciiCodes.add(8727);
        arlAsciiCodes.add(8465);
        arlAsciiCodes.add(8472);
        arlAsciiCodes.add(8476);
        arlAsciiCodes.add(8482);
        arlAsciiCodes.add(8501);
        arlAsciiCodes.add(9674);
        arlAsciiCodes.add(8194);
        arlAsciiCodes.add(8195);
        arlAsciiCodes.add(8201);
        arlAsciiCodes.add(8204);
        arlAsciiCodes.add(8205);
        arlAsciiCodes.add(8206);
        arlAsciiCodes.add(8207);
        arlAsciiCodes.add(8211);
        arlAsciiCodes.add(8212);
        arlAsciiCodes.add(8216);
        arlAsciiCodes.add(8217);
        arlAsciiCodes.add(8218);
        arlAsciiCodes.add(8220);
        arlAsciiCodes.add(8221);
        arlAsciiCodes.add(8222);
        arlAsciiCodes.add(8224);
        arlAsciiCodes.add(8225);
        arlAsciiCodes.add(8226);
        arlAsciiCodes.add(8230);
        arlAsciiCodes.add(8240);
        arlAsciiCodes.add(8242);
        arlAsciiCodes.add(8243);
        arlAsciiCodes.add(8249);
        arlAsciiCodes.add(8250);
        arlAsciiCodes.add(8254);
        arlAsciiCodes.add(8260);
        arlAsciiCodes.add(8592);
        arlAsciiCodes.add(8593);
        arlAsciiCodes.add(8594);
        arlAsciiCodes.add(8595);
        arlAsciiCodes.add(8596);
        arlAsciiCodes.add(8656);
        arlAsciiCodes.add(8657);
        arlAsciiCodes.add(8658);
        arlAsciiCodes.add(8659);
        arlAsciiCodes.add(8660);
    }

    static List<Integer> arlAsciiCodesForHyphen = new ArrayList<Integer>();
    static {
        arlAsciiCodesForHyphen.add(8212);
        arlAsciiCodesForHyphen.add(8211);
        arlAsciiCodesForHyphen.add(173);
        arlAsciiCodesForHyphen.add(175);
        arlAsciiCodesForHyphen.add(172);
    }

    static List<Integer> arlAsciiCodesForSpace = new ArrayList<Integer>();
    static {
        arlAsciiCodesForSpace.add(8195);
        arlAsciiCodesForSpace.add(8194);
        arlAsciiCodesForSpace.add(182);
        arlAsciiCodesForSpace.add(167);

    }
    static List<Integer> arlAsciiCodesForSingleQuotes = new ArrayList<Integer>();
    static {
    	arlAsciiCodesForSingleQuotes.add(8216);
    	arlAsciiCodesForSingleQuotes.add(8217);

    }
    static List<Integer> arlAsciiCodesForDoubleQuotes = new ArrayList<Integer>();
    static {
    	arlAsciiCodesForDoubleQuotes.add(8220);
    	arlAsciiCodesForDoubleQuotes.add(8221);

    }
    
	static List<Integer> arlAsciiCodesForNewLine = new ArrayList<Integer>();
	static {
		arlAsciiCodesForNewLine.add(10);
	}

    /**
     * @param string
     * @return
     * @Description Method validates, String contains only numbers
     */
    public static boolean checkIntNumber(String string) {
        boolean isNumber = false;
        if (null != string && !string.isEmpty()) {
            final Pattern numberPattern = Pattern.compile(RMDCommonConstants.INTNUMBER);
            isNumber = numberPattern.matcher(string).matches();
        }
        return isNumber;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, String contains only numbers
     */
    public static boolean checkIntNumberComma(String string) {
        boolean isNumber = false;
        if (null != string && !string.isEmpty()) {
            final Pattern numberPattern = Pattern.compile(RMDCommonConstants.INTNUMBER_COMMA);
            isNumber = numberPattern.matcher(string).matches();
        }
        return isNumber;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string is a combination alphabets and
     *              numbers
     */
    public static boolean checkAlphaNumeric(String string) {
        boolean isAlphaNumericString = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericPattern = Pattern.compile(RMDCommonConstants.ALPHANUMERIC);
            isAlphaNumericString = alphaNumericPattern.matcher(string).matches();
        }
        return isAlphaNumericString;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string is a combination alphabets and
     *              numbers
     */
    public static boolean checkAlphaNumericComma(String string) {
        boolean isAlphaNumericString = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericPattern = Pattern.compile(RMDCommonConstants.ALPHANUMERIC_COMMA);
            isAlphaNumericString = alphaNumericPattern.matcher(string).matches();
        }
        return isAlphaNumericString;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string is a combination alphabets, numbers
     *              and hypen white space allowed
     */
    public static boolean checkAlphaNumericHypen(String string) {
        boolean isAlphaNumericHypenString = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericHypenPattern = Pattern.compile(RMDCommonConstants.ALPHNUMHYPHEN);
            isAlphaNumericHypenString = alphaNumericHypenPattern.matcher(string).matches();
        }
        return isAlphaNumericHypenString;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string is a combination alphabets, numbers
     *              and hypen white space allowed
     */
    public static boolean checkSeqVal(String string) {
        boolean isSeqVal = false;
        if (null != string && !string.isEmpty()) {
            final Pattern seqValPattern = Pattern.compile(RMDCommonConstants.SEQVALPATTERN);
            isSeqVal = seqValPattern.matcher(string).matches();
        }
        return isSeqVal;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string is a combination alphabets, numbers
     *              and hypen underscore and white space allowed
     */
    public static boolean checkAlphaNumericUnderscore(String string) {
        boolean isAlphaNumericUnderScore = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericUnderScorePattern = Pattern.compile(RMDCommonConstants.ALPHNUMUNDERSCR);
            isAlphaNumericUnderScore = alphaNumericUnderScorePattern.matcher(string).matches();
        }
        return isAlphaNumericUnderScore;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string is a combination alphabets, numbers
     *              and brackets and white space allowed
     */
    public static boolean checkAlphaNumericBrackets(String string) {
        boolean isAlphaNumericBrackets = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericBracketsPattern = Pattern.compile(RMDCommonConstants.ALPHNUMBRACKETS);
            isAlphaNumericBrackets = alphaNumericBracketsPattern.matcher(string).matches();
        }
        return isAlphaNumericBrackets;
    }

    /**
     * @param string
     * @return
     * @Description Method returns true for upper case, vice versa for lower
     *              case
     */
    public static boolean checkCase(String string) {
        boolean isUpperCase = false;
        if (null != string && !string.isEmpty()) {
            final Pattern casePattern = Pattern.compile(RMDCommonConstants.CASEPATTERN);
            isUpperCase = casePattern.matcher(string).matches();
        }
        return isUpperCase;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string has a proper email format
     */
    public static boolean validateEmailAddress(String string) {
        boolean isValidEmail = false;
        if (null != string && !string.isEmpty()) {
            final Pattern emailPattern = Pattern.compile(RMDCommonConstants.EMAILPATTERN);
            isValidEmail = emailPattern.matcher(string).matches();
        }
        return isValidEmail;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string has a proper date format
     */
    public static boolean validateDate(String string, String format) {
        boolean isValidDate = false;
        if ((null != string && !string.isEmpty()) && (null != format && !format.isEmpty())) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                dateFormat.parse(string);
                return true;
            } catch (Exception exception) {
                LOG.debug(exception.getMessage(), exception);
                return false;
            }
        }
        return isValidDate;
    }

    /**
     * @param string
     * @return
     * @Description Method validates file type
     */
    public static boolean validFileType(String fileName) {
        List<String> fileTypes = new ArrayList<String>();
        fileTypes.add(RMDCommonConstants.PDFTYPE);
        fileTypes.add(RMDCommonConstants.TXTTYPE);
        if (null != fileName && !fileName.isEmpty()) {
            String del = RMDCommonConstants.FILEDELIMETER;
            String[] temp = fileName.split(del);
            if (fileTypes.contains(temp[1])) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * @param string
     * @return
     * @Description Method validates checks whether the given file size is in
     *              specified size range only
     */
    public boolean checkFileSize(File file, double size) {
        long fileSize = file.length();
        double len = (double) fileSize / 1024;
        if (len <= size) {
            return true;
        } else {
            return false;
        }
    }

    public static String htmlEscapingWithoutEncoding(String fieldValue) {
        StringBuilder result = new StringBuilder();
        if (null != fieldValue && !fieldValue.isEmpty()) {
            for (int i = 0; i < fieldValue.length(); i++) {
                char character = fieldValue.charAt(i);
                if (character == '<') {
                    result.append(RMDCommonConstants.LESSTHAN);
                } else if (character == '>') {
                    result.append(RMDCommonConstants.GRTTHAN);
                } else if (character == '&') {
                    result.append(RMDCommonConstants.AMPERSAND);
                } else if (character == '\"') {
                    result.append(RMDCommonConstants.DBLQOUTE);
                } else if (character == '\t') {
                    addCharEntity(9, result);
                } else if (character == '!') {
                    addCharEntity(33, result);
                } else if (character == '#') {
                    addCharEntity(35, result);
                } else if (character == '$') {
                    addCharEntity(36, result);
                } else if (character == '%') {
                    addCharEntity(37, result);
                } else if (character == '\'') {
                    addCharEntity(39, result);
                } else if (character == '(') {
                    addCharEntity(40, result);
                } else if (character == ')') {
                    addCharEntity(41, result);
                } else if (character == '*') {
                    addCharEntity(42, result);
                } else if (character == '+') {
                    addCharEntity(43, result);
                } else if (character == ',') {
                    addCharEntity(44, result);
                } else if (character == '-') {
                    addCharEntity(45, result);
                } else if (character == '.') {
                    addCharEntity(46, result);
                } else if (character == '/') {
                    addCharEntity(47, result);
                } else if (character == ':') {
                    addCharEntity(58, result);
                } else if (character == ';') {
                    addCharEntity(59, result);
                } else if (character == '=') {
                    addCharEntity(61, result);
                } else if (character == '?') {
                    addCharEntity(63, result);
                } else if (character == '@') {
                    addCharEntity(64, result);
                } else if (character == '[') {
                    addCharEntity(91, result);
                } else if (character == '\\') {
                    addCharEntity(92, result);
                } else if (character == ']') {
                    addCharEntity(93, result);
                } else if (character == '^') {
                    addCharEntity(94, result);
                } else if (character == '_') {
                    addCharEntity(95, result);
                } else if (character == '`') {
                    addCharEntity(96, result);
                } else if (character == '{') {
                    addCharEntity(123, result);
                } else if (character == '|') {
                    addCharEntity(124, result);
                } else if (character == '}') {
                    addCharEntity(125, result);
                } else if (character == '~') {
                    addCharEntity(126, result);
                } else {
                    // the char is not a special one
                    // add it to the result as is
                    result.append(character);
                }
            }

        }
        return result.toString();
    }

    /**
     * @param string
     * @return
     * @Description Method replaces all the special characters with their html
     *              escape characters
     */
    public static String htmlEscaping(String fieldValue) {
        StringBuilder result = new StringBuilder();

        if (null != fieldValue && !fieldValue.isEmpty()) {
            fieldValue = fieldValue.trim();
            String encodedStr = encodeSpecialCharacter(fieldValue);
            for (int i = 0; i < encodedStr.length(); i++) {
                char character = encodedStr.charAt(i);
                if (character == '<') {
                    result.append(RMDCommonConstants.LESSTHAN);
                } else if (character == '>') {
                    result.append(RMDCommonConstants.GRTTHAN);
                } else if (character == '&') {
                    result.append(RMDCommonConstants.AMPERSAND);
                } else if (character == '\"') {
                    result.append(RMDCommonConstants.DBLQOUTE);
                } else if (character == '\t') {
                    addCharEntity(9, result);
                } else if (character == '!') {
                    addCharEntity(33, result);
                } else if (character == '#') {
                    addCharEntity(35, result);
                } else if (character == '$') {
                    addCharEntity(36, result);
                } else if (character == '%') {
                    addCharEntity(37, result);
                } else if (character == '\'') {
                    addCharEntity(39, result);
                } else if (character == '(') {
                    addCharEntity(40, result);
                } else if (character == ')') {
                    addCharEntity(41, result);
                } else if (character == '*') {
                    addCharEntity(42, result);
                } else if (character == '+') {
                    addCharEntity(43, result);
                } else if (character == ',') {
                    addCharEntity(44, result);
                } else if (character == '-') {
                    addCharEntity(45, result);
                } else if (character == '.') {
                    addCharEntity(46, result);
                } else if (character == '/') {
                    addCharEntity(47, result);
                } else if (character == ':') {
                    addCharEntity(58, result);
                } else if (character == ';') {
                    addCharEntity(59, result);
                } else if (character == '=') {
                    addCharEntity(61, result);
                } else if (character == '?') {
                    addCharEntity(63, result);
                } else if (character == '@') {
                    addCharEntity(64, result);
                } else if (character == '[') {
                    addCharEntity(91, result);
                } else if (character == '\\') {
                    addCharEntity(92, result);
                } else if (character == ']') {
                    addCharEntity(93, result);
                } else if (character == '^') {
                    addCharEntity(94, result);
                } else if (character == '_') {
                    addCharEntity(95, result);
                } else if (character == '`') {
                    addCharEntity(96, result);
                } else if (character == '{') {
                    addCharEntity(123, result);
                } else if (character == '|') {
                    addCharEntity(124, result);
                } else if (character == '}') {
                    addCharEntity(125, result);
                } else if (character == '~') {
                    addCharEntity(126, result);
                } else {
                    // the char is not a special one
                    // add it to the result as is
                    result.append(character);
                }
            }

        }
        return result.toString();
    }

    /**
     * @param string
     * @return
     * @Description Method replaces all the special characters with their html
     *              escape characters for FSS
     */
    public static String htmlEscapingFSS(String fieldValue) {
        StringBuilder result = new StringBuilder();

        if (null != fieldValue && !fieldValue.isEmpty()) {
            fieldValue = fieldValue.trim();
            String encodedStr = encodeSpecialCharacter(fieldValue);
            for (int i = 0; i < encodedStr.length(); i++) {
                char character = encodedStr.charAt(i);
                if (character == '<') {
                    result.append(RMDCommonConstants.LESSTHAN);
                } else if (character == '>') {
                    result.append(RMDCommonConstants.GRTTHAN);
                } else if (character == '&') {
                    result.append(RMDCommonConstants.AMPERSAND);
                } else if (character == '\"') {
                    result.append(RMDCommonConstants.DBLQOUTE);
                } else if (character == '!') {
                    addCharEntityFSS(33, result);
                } else if (character == '#') {
                    addCharEntityFSS(35, result);
                } else if (character == '$') {
                    addCharEntityFSS(36, result);
                } else if (character == '%') {
                    addCharEntityFSS(37, result);
                } else if (character == '\'') {
                    addCharEntityFSS(39, result);
                } else if (character == '(') {
                    addCharEntityFSS(40, result);
                } else if (character == ')') {
                    addCharEntityFSS(41, result);
                } else if (character == '*') {
                    addCharEntityFSS(42, result);
                } else if (character == '+') {
                    addCharEntityFSS(43, result);
                } else if (character == '/') {
                    addCharEntityFSS(47, result);
                } else if (character == ':') {
                    addCharEntityFSS(58, result);
                } else if (character == ';') {
                    addCharEntityFSS(59, result);
                } else if (character == '=') {
                    addCharEntityFSS(61, result);
                } else if (character == '?') {
                    addCharEntityFSS(63, result);
                } else if (character == '@') {
                    addCharEntityFSS(64, result);
                } else if (character == '[') {
                    addCharEntityFSS(91, result);
                } else if (character == '\\') {
                    addCharEntityFSS(92, result);
                } else if (character == ']') {
                    addCharEntityFSS(93, result);
                } else if (character == '^') {
                    addCharEntityFSS(94, result);
                } else if (character == '`') {
                    addCharEntityFSS(96, result);
                } else if (character == '{') {
                    addCharEntityFSS(123, result);
                } else if (character == '|') {
                    addCharEntityFSS(124, result);
                } else if (character == '}') {
                    addCharEntityFSS(125, result);
                } else if (character == '~') {
                    addCharEntityFSS(126, result);
                } else {
                    result.append(character);
                }
            }

        }
        return result.toString();
    }

    /**
     * @param fileName
     * @return
     * @Description Method validates, filename contains valid characters or not
     */
    public static boolean validateFileName(String fileName) {
        boolean isValidFileName = false;
        if (null != fileName && !fileName.isEmpty()) {
            Pattern filePattern = Pattern.compile(RMDCommonConstants.FILENAME);
            isValidFileName = filePattern.matcher(fileName).matches();
        }
        return isValidFileName;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string contains only alphabets
     */
    public static boolean checkAlphabets(String string) {
        boolean isAlphabet = false;
        if (null != string && !string.isEmpty()) {
            final Pattern numberPattern = Pattern.compile(RMDCommonConstants.ALHA);
            isAlphabet = numberPattern.matcher(string).matches();
        }
        return isAlphabet;
    }

    /**
     * @param string
     * @return
     * @Description Method validates fromDate is lesser than the toDate
     */
    public static boolean validateFromDate(String fromDateStr, String toDateStr) {
        boolean isValidDate = false;
        DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            Date fromDate = formatter.parse(fromDateStr);
            Date toDate = formatter.parse(toDateStr);
            if (fromDate.compareTo(toDate) > 0)
                isValidDate = false;
            else if (fromDate.compareTo(toDate) < 0)
                isValidDate = true;
            else
                isValidDate = false;
        } catch (Exception e) {
            isValidDate = false;
            LOG.debug(e.getMessage(), e);
        }
        return isValidDate;
    }

    /**
     * @param string
     * @return
     * @Description Method compares two list, checks whether userInputList
     *              values are present in actual list
     */
    public static boolean compareLists(List<String> actualList, List<String> userInputList) {
        if ((null != actualList && !actualList.isEmpty()) && (null != userInputList && !userInputList.isEmpty())) {
            int listSize = userInputList.size();
            // compare 2 lists
            for (int i = 0; i < listSize; i++) {
                if (!actualList.contains(userInputList.get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param string
     * @return
     * @Description Method validates whether user input is present in given list
     */
    public static boolean isListContainsValue(String userInput, List<String> actualList) {
        if ((null != actualList && !actualList.isEmpty()) && (null != userInput)) {
            if (!actualList.contains(userInput)) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * @param string
     * @return Method validates the rule title values in km related screen
     */
    public static boolean checkRuleTitleValues(String string) {
        boolean isValidTitle = false;
        if (null != string && !string.isEmpty()) {
            final Pattern numberPattern = Pattern.compile(RMDCommonConstants.RULE_TITLE_PATTERN);
            isValidTitle = numberPattern.matcher(string).matches();
        }
        return isValidTitle;
    }

    private static void addCharEntity(Integer aIdx, StringBuilder aBuilder) {
        String padding = "";
        if (aIdx <= 9) {
            padding = RMDCommonConstants.DBL_ZERO_STRING;
        } else if (aIdx <= 99) {
            padding = RMDCommonConstants.ZERO_STRING;
        }
        String number = padding + aIdx.toString();
        aBuilder.append(RMDCommonConstants.ADDCHARENTITY + number + RMDCommonConstants.SEMI_COLON);
    }

    private static void addCharEntityFSS(Integer aIdx, StringBuilder aBuilder) {
        String padding = "";
        String number = aIdx.toString();
        aBuilder.append(RMDCommonConstants.ADDCHARENTITY + number + RMDCommonConstants.SEMI_COLON);
    }

    private static String encodeSpecialCharacter(String strInput) {
        String localstrInput = strInput;
        if (null != localstrInput && !localstrInput.isEmpty()) {
            String specialCharacters[] = { "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�",
                    "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�",
                    "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�" };
            String specialCharactersReplace[] = { "1VG1", "2VG2", "3VG3", "4VG4", "5VG5", "6VG6", "7VG7", "8VG8",
                    "9VG9", "1VG2", "2VG3", "3VG4", "4VG5", "5VG6", "6VG7", "7VG8", "8VG9", "9VG1", "9VG2", "9VG3",
                    "9VG4", "9VG5", "9VG6", "9VG7", "9VG8", "8VG1", "8VG2", "8VG3", "8VG4", "8VG5", "8VG6", "8VG7",
                    "8VG9", "7VG2", "7VG3", "7VG4", "7VG5", "7VG6", "10VG1" };
            for (int i = 0; i < specialCharacters.length; i++) {
                localstrInput = localstrInput.replaceAll(specialCharacters[i], specialCharactersReplace[i]);
            }
        }
        return localstrInput;
    }

    /**
     * Method Name :revertEncode(String strInput) Description :Given a string,
     * this method replaces all occurrences of encoded Special Characters
     * defined with the Actual Special Characters
     * 
     * @param String
     * @return String
     */

    public static String revertEncode(String strInput) {
        String local = strInput;
        if (null != local && !local.isEmpty()) {
            String specialCharacters[] = { "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�",
                    "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�",
                    "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�" };
            String specialCharactersReplace[] = { "1VG1", "2VG2", "3VG3", "4VG4", "5VG5", "6VG6", "7VG7", "8VG8",
                    "9VG9", "1VG2", "2VG3", "3VG4", "4VG5", "5VG6", "6VG7", "7VG8", "8VG9", "9VG1", "9VG2", "9VG3",
                    "9VG4", "9VG5", "9VG6", "9VG7", "9VG8", "8VG1", "8VG2", "8VG3", "8VG4", "8VG5", "8VG6", "8VG7",
                    "8VG9", "7VG2", "7VG3", "7VG4", "7VG5", "7VG6", "10VG1" };
            for (int i = 0; i < specialCharacters.length; i++) {
                local = local.replaceAll(specialCharactersReplace[i], specialCharacters[i]);
            }
        }
        return local;
    }

    /**
     * Method Name :validateBooleanValues(String value) Description : method
     * verifies whether the given value is valid boolean value or not
     * 
     * @param String
     * @return boolean
     */
    public static boolean validateBooleanValues(String value) {
        if (null != value && !value.isEmpty()) {
            List<String> validValues = new ArrayList<String>();
            validValues.add(RMDCommonConstants.STRING_TRUE);
            validValues.add(RMDCommonConstants.STRING_FALSE);
            if (validValues.contains(value)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param string
     * @return
     * @Description Method validates string contains only digits, underscore and
     *              whitespace
     */
    public static boolean checkAlphaUnderScore(String string) {
        boolean isAlphaUnderScore = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericHypenPattern = Pattern.compile(RMDCommonConstants.ALPHAUNDERSCOREPAT);
            isAlphaUnderScore = alphaNumericHypenPattern.matcher(string).matches();
        }
        return isAlphaUnderScore;
    }

    /**
     * @param string
     * @return
     * @Description Method validates string contains only digits and hyphen
     */
    public static boolean checkNumbersHyphen(String string) {
        boolean isNumberHyphen = false;
        if (null != string && !string.isEmpty()) {
            final Pattern numericHypenPattern = Pattern.compile(RMDCommonConstants.NUMBERSHYPHEN);
            isNumberHyphen = numericHypenPattern.matcher(string).matches();
        }
        return isNumberHyphen;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string is a combination alphabets, numbers
     *              underscore and white space allowed
     */
    public static boolean checkAlphaNumeralsUnderscore(String string) {
        boolean isAlphaNumericUnderScore = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericUnderScorePattern = Pattern.compile(RMDCommonConstants.ALPH_NUM_UND);
            isAlphaNumericUnderScore = alphaNumericUnderScorePattern.matcher(string).matches();
        }
        return isAlphaNumericUnderScore;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string contains alphabets,
     *              numbers,%,-,whitespace this is explicitly coded for
     *              locomotive impact in rx screen
     */
    public static boolean checkAlphaBrackets(String string) {
        boolean isValidInput = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericUnderScorePattern = Pattern.compile(RMDCommonConstants.ALPHA_BRACKET_PT);
            isValidInput = alphaNumericUnderScorePattern.matcher(string).matches();
        }
        return isValidInput;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string contains alphabets,
     *              numbers,&,-,whitespace this is explicitly coded for
     *              subsystem in rx screen
     */
    public static boolean checkAlphaAmpersand(String string) {
        boolean isValidInput = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.ALPHA_AMPERSAND_PT);
            isValidInput = pattern.matcher(string).matches();
        }
        return isValidInput;
    }

    /**
     * @param string
     * @return Method validates, string must not contains 2 consecutive hyphen
     *         used for rule title and note fields in km screen
     */
    public static boolean checkDoubleHyphen(String string) {
        boolean isValidInput = false;
        if (null != string && !string.isEmpty()) {
            String pattern = "--";
            int a = string.indexOf(pattern);
            if (a != -1) {
                isValidInput = false;
            } else {
                isValidInput = true;
            }
        }
        return isValidInput;
    }

    /**
     * @Author:
     * @param strInputValue
     * @return
     * @Description: This methods checks the availability of Special Characters
     *               > < ' / \ in the input
     */
    public static boolean isSpecialCharactersFound(String strInputValue) {
        boolean isSpecialCharFound = false;
        final Pattern specialCharacterPattern = Pattern.compile(RMDCommonConstants.RESTRICTED_SPECIAL_CHAR_PATTERN);
        if (null != strInputValue) {

            if (specialCharacterPattern.matcher(strInputValue).find()) {
                isSpecialCharFound = true;
            }
        }
        return isSpecialCharFound;
    }

    /**
     * @param string
     * @return
     * @Description Method allows only certain special characters explicitly
     *              coded for root cause and symptoms
     */
    public static boolean checkForwardSlash(String string) {
        boolean isValidInput = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.ALPHA_FORWARD_SLASH_PT);
            isValidInput = pattern.matcher(string).matches();
        }
        return isValidInput;
    }

    /**
     * @param string
     * @return
     * @Description Method checks, input contains only numbers and digits coded
     *              for hrs, mins in complex rules
     */
    public static boolean checkNumbersDot(String string) {
        boolean isValidInput = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.ALPHA_DECIMAL);
            isValidInput = pattern.matcher(string).matches();
        }
        return isValidInput;
    }

    /**
     * @return @Description, list of symbols used in config drop down, user
     *         input in config window is validated against the list
     */
    public static boolean checkConfigfilterFunction(String string) {
        boolean isValidData = false;
        configList = new ArrayList<String>();
        configList.add(RMDCommonConstants.EQUALS);
        configList.add(RMDCommonConstants.GREATER);
        configList.add(RMDCommonConstants.GEQUALS);
        configList.add(RMDCommonConstants.LESS);
        configList.add(RMDCommonConstants.LEQULAS);
        configList.add(RMDCommonConstants.AND1);
        configList.add(RMDCommonConstants.AND2);
        configList.add(RMDCommonConstants.AND3);
        configList.add(RMDCommonConstants.AND4);
        configList.add(RMDCommonConstants.OR1);
        configList.add(RMDCommonConstants.OR2);
        configList.add(RMDCommonConstants.OR3);
        configList.add(RMDCommonConstants.OR4);
        configList.add(RMDCommonConstants.NQ);
        configList.add(RMDCommonConstants.MULTI);
        configList.add(RMDCommonConstants.AND5);
        configList.add(RMDCommonConstants.NOT);
        configList.add(RMDCommonConstants.OR5);
        configList.add(RMDCommonConstants.MULTIFAULT);
        configList.add(RMDCommonConstants.NOT_FORWARD);
        configList.add(RMDCommonConstants.NOT_BACKWARD);
        configList.add(RMDCommonConstants.AND_FORWARD);
        if (null != string && !string.isEmpty()) {
            isValidData = configList.contains(string);
        }
        return isValidData;
    }

    /**
     * @param string
     * @return Method explicitly coded to validate the asset impact
     */
    public static boolean validateAsset(String string) {
        boolean isValidInput = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.ALPHA_DECIMAL);
            isValidInput = pattern.matcher(string).matches();
        }
        return isValidInput;
    }

    /**
     * @param string
     * @return
     * @Description: Method used to validate the column name in complex rules
     */
    public static boolean validateColumnName(String string) {
        boolean isValidData = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile("^[A-Za-z0-9-,+:()/#_%\\s]+$");
            isValidData = pattern.matcher(string).matches();
        }
        return isValidData;
    }

    /**
     * @param string
     * @return
     * @Description , Method used to validate the target in add solution
     */
    public static boolean validateTarget(String string) {
        boolean isValidData = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.ALPHA_MINUS);
            isValidData = pattern.matcher(string).matches();
        }
        return isValidData;
    }

    /**
     * @param string
     * @return
     * @Description Method used to validate locomotive impact and asset impact
     *              in add solution
     */
    public static boolean validateTask(String string) {
        boolean isValidData = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.TASKPATTERN);
            isValidData = pattern.matcher(string).matches();
        }
        return isValidData;
    }

    /**
     * @param string
     * @return Method used to validate the revision history in add solution
     */
    public static boolean validateRevision(String string) {
        boolean isValidData = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.HISTORY_PATTERN);
            isValidData = pattern.matcher(string).matches();
        }
        return isValidData;
    }

    /**
     * @param string
     * @return Method used to validate the doc path in add solution
     */
    public static boolean validateDocPath(String string) {
        boolean isValidData = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.DOC_PATTERN);
            isValidData = pattern.matcher(string).matches();
        }
        return isValidData;
    }

    /**
     * @param string
     * @return Method used to validate the feedback in add solution
     */
    public static boolean validateFeedBack(String string) {
        boolean isValidData = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.FEEDBACK_PATTERN);
            isValidData = pattern.matcher(string).matches();
        }
        return isValidData;
    }

    /**
     * @param string
     * @return Method used to validate the RX Title in add/edit RX
     */
    public static boolean validateRXTitle(String value) {
        boolean flag = true;
        Enumeration<String> en = htmlspecialcharstable.keys();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            if (htmlspecialcharstable.containsKey(key)) {
                String val = htmlspecialcharstable.get(key);
                if (val.equals("@") && value.contains(key)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * @param paramMap
     * @param format
     * @param ch
     * @param vargs
     * @return
     * @Description Method used to validate the user input
     */
    public static boolean validateWebServiceInput(MultivaluedMap<String, String> paramMap, String format, int[] ch,
            String... vargs) {
        boolean isValidInput = true;
        int methodArraySize = ch.length;
        int inputArraySize = vargs.length;
        String strAlpArray[] = null;
        String strNumArray[] = null;

        if (methodArraySize == inputArraySize) {
            for (int i = 0; i < inputArraySize; i++) {
                if (null != vargs[i] && !vargs[i].isEmpty()) {
                    if (paramMap.containsKey(vargs[i])) {
                        switch (ch[i]) {
                        case RMDCommonConstants.ALPHABETS:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                strAlpArray = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : strAlpArray) {
                                    if (!AppSecUtil.checkAlphabets(value)) {

                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.NUMERIC:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                strNumArray = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : strNumArray) {
                                    if (!AppSecUtil.checkIntNumber(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.VALID_DATE:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                if (null != format && !format.isEmpty()) {
                                    if (!AppSecUtil.validateDate(paramMap.getFirst(vargs[i]), format)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String strUndArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : strUndArray) {
                                    if (!AppSecUtil.checkAlphaNumericUnderscore(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.ALPHA_UNDERSCORE:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String underScoreArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : underScoreArray) {
                                    if (!AppSecUtil.checkAlphaUnderScore(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.ALPHA_NUMERIC_HYPEN:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String hypenArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : hypenArray) {
                                    if (!AppSecUtil.checkAlphaNumericHypen(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.NUMERIC_HYPHEN:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String numericHyphenArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : numericHyphenArray) {
                                    if (!AppSecUtil.checkNumbersHyphen(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.AlPHA_NUMERIC:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String strAlphaNumberArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : strAlphaNumberArray) {
                                    if (!AppSecUtil.checkAlphaNumeric(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;
                        case RMDCommonConstants.ALPHA_BRACKET:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String alphaBracketArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : alphaBracketArray) {
                                    if (!AppSecUtil.checkAlphaBrackets(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.ALPHA_AMPERSAND:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String ampersandArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : ampersandArray) {
                                    if (!AppSecUtil.checkAlphaAmpersand(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.DOUBLE_HYPHEN:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String hyphenArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : hyphenArray) {
                                    if (!AppSecUtil.checkDoubleHyphen(value)) {
                                        return false;
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.CHECK_ENCODE:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String value = paramMap.getFirst(vargs[i]);
                                Enumeration en = htmlspecialcharstable.keys();
                                while (en.hasMoreElements()) {
                                    String key = (String) en.nextElement();
                                    if (htmlspecialcharstable.containsKey(key)) {
                                        String val = htmlspecialcharstable.get(key);
                                        if (value.contains(val)) {
                                            return false;
                                        }
                                    }
                                }
                            }
                            break;

                        case RMDCommonConstants.SPECIAL_CHARACTER:
                            if (paramMap.getFirst(vargs[i]).length() > 0) {
                                String specialCharArray[] = paramMap.getFirst(vargs[i]).split(",");
                                for (String value : specialCharArray) {
                                    if (AppSecUtil.isSpecialCharactersFound(value)) {
                                        return false;
                                    }
                                }
                            }

                        default:
                            break;
                        }
                    }
                }
            }
        } else {
            isValidInput = false;
        }
        return isValidInput;

    }

    public static String decodeString(String string) {
        String local = string;
        Enumeration<String> en = htmlspecialcharstable.keys();
        String returnStr = RMDCommonConstants.EMPTY_STRING;
        if (null != local && !local.equals("")) {
            local = local.trim();
            while (en.hasMoreElements()) {
                String key = en.nextElement();
                if (htmlspecialcharstable.containsKey(key)) {
                    String val = htmlspecialcharstable.get(key);
                    if (val == "\\") {
                        local = local.replaceAll(key, "\\\\");
                    } else if (val == "$") {
                        local = local.replaceAll(key, "\\$");
                    } else {
                        local = local.replaceAll(key, val);
                    }
                }
            }
            returnStr = revertEncode(local);
        }
        if (!RMDCommonUtility.isNullOrEmpty(returnStr)) {
            returnStr = returnStr.replaceAll("�", "'").replaceAll("�", "-");
        }

        return returnStr;
    }

    public static String decodeStringFSS(String string) {
        String local = string;
        Enumeration<String> en = htmlspecialcharstableFSS.keys();
        String returnStr = RMDCommonConstants.EMPTY_STRING;
        if (null != local && !local.equals("")) {
            local = local.trim();
            while (en.hasMoreElements()) {
                String key = en.nextElement();
                if (htmlspecialcharstableFSS.containsKey(key)) {
                    String val = htmlspecialcharstableFSS.get(key);
                    if (val == "\\") {
                        local = local.replaceAll(key, "\\\\");
                    } else if (val == "$") {
                        local = local.replaceAll(key, "\\$");
                    } else {
                        local = local.replaceAll(key, val);
                    }
                }
            }
            returnStr = revertEncode(local);
        }
        if (!RMDCommonUtility.isNullOrEmpty(returnStr)) {
            returnStr = returnStr.replaceAll("�", "'").replaceAll("�", "-");
        }

        return returnStr;
    }

    /**
     * @param VALUE
     * @return
     * @Description Method used to validate whether the user input is encoded or
     *              not
     */
    public static boolean checkEncode(String value) {
        boolean flag = true;
        Enumeration<String> en = htmlspecialcharstable.keys();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            if (htmlspecialcharstable.containsKey(key)) {
                String val = htmlspecialcharstable.get(key);
                if (!val.equals("&") && !val.equals(";") && !val.equals("#") && value.contains(val)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * @param VALUE
     * @return
     * @Description Method used to validate whether the Password is encoded or
     *              not
     */

    public static boolean isValidMD5(String validateMD5) {
        return validateMD5.matches("[a-fA-F0-9]{32}");
    }

    /**
     * @param string
     * @return Method validates the user input in config window
     */
    public static boolean validateConfigFunction(String string) {
        boolean isValidInput = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.CONFIG_PATTERN);
            isValidInput = pattern.matcher(string.trim()).matches();
        }
        return isValidInput;
    }

    public static boolean checkAlphabetsComma(String string) {
        boolean isAlphabet = false;
        if (null != string && !string.isEmpty()) {
            final Pattern numberPattern = Pattern.compile(RMDCommonConstants.ALPHA_COMMA);
            isAlphabet = numberPattern.matcher(string).matches();
        }
        return isAlphabet;
    }

    /**
     * @param string
     * @return String
     * @description:This method is to convert the non valid XML characters to #
     *                   symbol
     */
    public static String stripNonValidXMLCharacters(String input) {
        StringBuilder out = new StringBuilder();
        char current;
        if (input == null || ("".equals(input)))
            return input;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            Integer asciiCode = (int) current;
            if ((asciiCode >= 1 && asciiCode < 32) || arlAsciiCodes.contains(asciiCode)) {
                out.append(AppConstants.HASH_SYMBOL);
            } else {
                out.append(current);
            }
        }
        return out.toString();
    }

    /**
     * @param string
     * @return
     * @Description Method to validate if the string value is double
     */
    public static boolean isDoubleValue(String string) {
        boolean isDouble = false;
        if (null != string && !string.isEmpty()) {
            try {
                Double.valueOf(string);
                isDouble = true;
            } catch (Exception e) { // If exception, then not a float value
                isDouble = false;
                LOG.debug(e.getMessage(), e);
            }
        }
        return isDouble;
    }

    /**
     * @param string
     * @return
     * @Description Method to validate if the string value is numeric and
     *              negative
     */
    public static boolean isNumericWithNegative(final String string) {
        boolean isValidData = false;
        if (null != string && !string.isEmpty()) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.NUMERIC_PATTERN_NEGATIVE);
            isValidData = pattern.matcher(string).matches();
        }
        return isValidData;
    }

    /**
     * @param string
     * @return
     * @Description Method validates, string is a combination alphabets, numbers
     *              underscore and white space allowed
     */
    public static boolean checkAlphaNumeralsUnderscoreForRx(String string) {
        boolean isAlphaNumericUnderScore = false;
        if (null != string && !string.isEmpty()) {
            final Pattern alphaNumericUnderScorePattern = Pattern.compile(RMDCommonConstants.RULE_TITLE_PATTERN);
            isAlphaNumericUnderScore = alphaNumericUnderScorePattern.matcher(string).matches();
        }
        return isAlphaNumericUnderScore;
    }

    /**
     * @param string
     * @return String
     * @description:This method is to validate ip address
     */
    public static boolean ipAddressValidation(String string) {
        boolean isValidIP = false;
        if (null != string && !string.isEmpty() && !("".equals(string))) {
            final Pattern ipAddressPattern = Pattern.compile(RMDCommonConstants.IP_ADDRESS_VALIDATION_REGEX);
            isValidIP = ipAddressPattern.matcher(string).matches();
        }
        return isValidIP;
    }

    /**
     * @param string
     * @return String
     * @description:This method is to convert the non valid XML characters to #
     *                   symbol
     */
    public static String stripNonValidXMLCharactersForKM(String input) {
        StringBuilder out = new StringBuilder();
        char current;
        if (input == null || ("".equals(input)))
            return input;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            Integer asciiCode = (int) current;
            if (arlAsciiCodesForHyphen.contains(asciiCode)) {
                out.append(AppConstants.HYPHEN);
            } else if (arlAsciiCodesForSpace.contains(asciiCode)) {
                out.append(AppConstants.SPACE);
            } else if (168 == asciiCode) {
                out.append(AppConstants.TWO_DOT);
            } else if (8230 == asciiCode) {
                out.append(AppConstants.THREE_DOT);
            } else if (arlAsciiCodesForSingleQuotes.contains(asciiCode)) {
                out.append(AppConstants.SINGLE_QUOTES);
            } else if (arlAsciiCodesForDoubleQuotes.contains(asciiCode)) {
                out.append(AppConstants.DOUBLE_QUOTES);
            } else if ((asciiCode >= 1 && asciiCode < 32) || arlAsciiCodes.contains(asciiCode)) {
                out.append(AppConstants.EMPTY_STRING);
            } else {
                out.append(current);
            }
        }
        return out.toString();
    }
	public static String convertMeasurementSystem(String inputFormula,String value) throws ScriptException {
		String returnValue=value;
		if(null!=value&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(value)){
		inputFormula=inputFormula.replace("x", value);
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");	    
	    double doubleValue= (Double)engine.eval(inputFormula);	  
	    returnValue=String.valueOf(doubleValue);
	    if(null!=returnValue&&returnValue.length()>15)
	    returnValue=returnValue.substring(0, 14);
		}
		return returnValue;
	}
	
	/**
	 * @param string
	 * @return String
	 * @description:This method is to convert the non valid XML characters to # symbol
	 * 
	 */
	public static String escapeLikeCharacters(String input) {
		Codec code=new OracleCodec();		
		StringBuffer out = new StringBuffer();					
		if (input == null || ("".equals(input))){
			return input;
		}else{
			input=ESAPI.encoder().encodeForSQL(code, input);
			out.append(input.replaceAll("/", "//").replaceAll("%", "/%").replaceAll("_", "/_"));
		}
		return out.toString();
	}
	
	/**
	 * @param string
	 * @return
	 * @Description Method checks, input contains only numbers and digits
	 * coded for hrs, mins in complex rules
	 */
	public static boolean checkNumbersDotWithoutMinus(String string){
		boolean isValidInput = false;
		if(null != string && !string.isEmpty()){
			final Pattern pattern = Pattern.compile(RMDCommonConstants.ALPHA_DECIMAL_WITHOUT_MINUS);
			isValidInput = pattern.matcher(string).matches();
			}
		return isValidInput;
	}
	
	//Added for OMD KM 1667 
    /**
     * @param string
     * @return String
     * @description:This method is to convert the non valid XML characters to #
     *                   symbol
     */
	public static String stripNonValidXMLCharactersForRx(String input) {
		StringBuilder out = new StringBuilder();
		char current;
		if (input == null || ("".equals(input)))
			return input;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			Integer asciiCode = (int) current;
			if (arlAsciiCodesForHyphen.contains(asciiCode)) {
				out.append(AppConstants.HYPHEN);
			} else if (arlAsciiCodesForSpace.contains(asciiCode)) {
				out.append(AppConstants.SPACE);
			} else if (168 == asciiCode) {
				out.append(AppConstants.TWO_DOT);
			} else if (8230 == asciiCode) {
				out.append(AppConstants.THREE_DOT);
			} else if (arlAsciiCodesForSingleQuotes.contains(asciiCode)) {
				out.append(AppConstants.SINGLE_QUOTES);
			} else if (arlAsciiCodesForDoubleQuotes.contains(asciiCode)) {
				out.append(AppConstants.DOUBLE_QUOTES);
			} /*else if (arlAsciiCodesForNewLine.contains(asciiCode)) {
				out.append(AppConstants.NEW_LINE);
			} */else if ((asciiCode >= 1 && asciiCode < 32)
					|| arlAsciiCodes.contains(asciiCode)) {
				out.append(AppConstants.EMPTY_STRING);
			} else {
				out.append(current);
			}
		}
		return out.toString();
	}
}
package com.ge.trans.pp.services.assets.valueobjects;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pPAssetHResponseType", propOrder = { "ppAssetHistoryVOlst"

})
@XmlRootElement
public class PPAssetHResponseType {

    @XmlElement(required = true)
    protected List<PPAssetHistoryParmDtlsResponseType> ppAssetHistoryVOlst;

    public List<PPAssetHistoryParmDtlsResponseType> getPpAssetHistoryVOlst() {
        return ppAssetHistoryVOlst;
    }

    public void setPpAssetHistoryVOlst(List<PPAssetHistoryParmDtlsResponseType> ppAssetHistoryVOlst) {
        this.ppAssetHistoryVOlst = ppAssetHistoryVOlst;
    }

    /*
     * @XmlElement(required = true) protected List<HashMap<String,String>>
     * ppAssetHistoryRMaplst;
     * 
     * public List<HashMap<String, String>> getPpAssetHistoryRMaplst() { return
     * ppAssetHistoryRMaplst; }
     * 
     * public void setPpAssetHistoryRMaplst( List<HashMap<String, String>>
     * ppAssetHistoryRMaplst) { this.ppAssetHistoryRMaplst =
     * ppAssetHistoryRMaplst; }
     * 
     * @Override public String toString() { return
     * "PPAssetHResponseType [ppAssetHistoryRMaplst=" + ppAssetHistoryRMaplst +
     * "]"; }
     */

    /*
     * @XmlElement(required = true) protected String strKey;
     * 
     * @XmlElement(required = true) protected String strValue;
     */

    /*
     * public String getStrKey() { return strKey; }
     * 
     * public void setStrKey(String strKey) { this.strKey = strKey; }
     * 
     * public String getStrValue() { return strValue; }
     * 
     * public void setStrValue(String strValue) { this.strValue = strValue; }
     * 
     * @Override public String toString() { return
     * "PPAssetHResponseType [strKey=" + strKey + ", strValue=" + strValue +
     * "]"; }
     */

}
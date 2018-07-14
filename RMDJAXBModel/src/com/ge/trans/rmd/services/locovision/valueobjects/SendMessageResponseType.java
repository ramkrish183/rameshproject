
package com.ge.trans.rmd.services.locovision.valueobjects;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendMessageResponse", propOrder = {
    "strMessageOutId"
})
@XmlRootElement
public class SendMessageResponseType {

    @XmlElement(required = true)
    protected String strMessageOutId;

    /**
     * Gets the value of the messageOutId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public String getMessageOutId() {
        return strMessageOutId;
    }

    /**
     * Sets the value of the messageOutId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMessageOutId(String value) {
        this.strMessageOutId = value;
    }

}

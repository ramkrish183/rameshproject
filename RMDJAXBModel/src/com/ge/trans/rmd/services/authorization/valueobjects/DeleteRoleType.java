package com.ge.trans.rmd.services.authorization.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeleteRolesUpdateDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteRolesUpdateDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="currentRole" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="changedRoleId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="arrDeleteRolesUpdateDetailsType" type="{http://omd/omdservices/authorizationservice}DeleteRolesUpdateDetailsType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteRolesUpdateDetailsType", propOrder = {
    "userId",
    "currentRole",
    "changedRoleId",
    "userSeqId"
})
@XmlRootElement
public class DeleteRoleType {

    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String currentRole;
	@XmlElement(required = true)
    protected String changedRoleId;
    @XmlElement(required = true)
    protected String userSeqId;

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the currentRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentRole() {
        return currentRole;
    }

    /**
     * Sets the value of the currentRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentRole(String value) {
        this.currentRole = value;
    }

    /**
     * Gets the value of the changedRoleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangedRoleId() {
        return changedRoleId;
    }

    /**
     * Sets the value of the changedRoleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangedRoleId(String value) {
        this.changedRoleId = value;
    }

	public String getUserSeqId() {
		return userSeqId;
	}

	public void setUserSeqId(String userSeqId) {
		this.userSeqId = userSeqId;
	}

}

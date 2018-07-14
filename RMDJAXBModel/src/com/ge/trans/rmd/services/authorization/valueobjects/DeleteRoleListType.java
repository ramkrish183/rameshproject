package com.ge.trans.rmd.services.authorization.valueobjects;

import java.util.List;

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
@XmlType(name = "DeleteRoleListType", propOrder = {
    "userId", "deleteRoleTypeList"   
})
@XmlRootElement
public class DeleteRoleListType {
	
	@XmlElement(required = true)
    protected String userId;

    @XmlElement(required = true)
    protected List<DeleteRoleType> deleteRoleTypeList;

	public List<DeleteRoleType> getDeleteRoleTypeList() {
		return deleteRoleTypeList;
	}

	public void setDeleteRoleTypeList(List<DeleteRoleType> deleteRoleTypeList) {
		this.deleteRoleTypeList = deleteRoleTypeList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
   

    
}

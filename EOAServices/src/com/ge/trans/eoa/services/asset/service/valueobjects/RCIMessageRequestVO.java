/**
 * 
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ============================================================
 * File : RCIMessageRequestVO.java
 * Description : 
 * 
 * Package : com.ge.trans.eoa.services.asset.service.valueobjects
 * Author : Capgemini
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 8, 2018
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2014 General Electric Company. All rights reserved
 * Classification : GE Sensitive
 * ============================================================
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class RCIMessageRequestVO {
	
	@XmlElement(name = "HEADER", required = true)
	protected RCIMessageHeaderVO rciMessageHeaderVO;
	
	@XmlElement(name="CONFIG_INCIDENT_DATA", required=true)
	protected RCIMessageBodyVO rciMessageBodyVO;

	/**
	 * @return the rciMessageHeaderVO
	 */
	public final RCIMessageHeaderVO getRciMessageHeaderVO() {
		return rciMessageHeaderVO;
	}

	/**
	 * @param rciMessageHeaderVO the rciMessageHeaderVO to set
	 */
	public final void setRciMessageHeaderVO(RCIMessageHeaderVO rciMessageHeaderVO) {
		this.rciMessageHeaderVO = rciMessageHeaderVO;
	}

	/**
	 * @return the rciMessageBodyVO
	 */
	public final RCIMessageBodyVO getRciMessageBodyVO() {
		return rciMessageBodyVO;
	}

	/**
	 * @param rciMessageBodyVO the rciMessageBodyVO to set
	 */
	public final void setRciMessageBodyVO(RCIMessageBodyVO rciMessageBodyVO) {
		this.rciMessageBodyVO = rciMessageBodyVO;
	}
	
	
	

}

/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: LogMessagesResponseType.java
 * Description 			:
 * Package 				: com.ge.trans.rmd.cm.valueobjects;
 * Author 				: Capgemini India.
 * Last Edited By 		:
 * Version 				: 1.0
 * Created on 			:
 * History				:
 * Modified By 			: Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "logMessagesResponseType", 
propOrder = {
		"arlLogMessages"
		
})
@XmlRootElement
public class LogMessagesResponseType {
	
	@XmlElement(required = true)
	protected List<String> arlLogMessages;

	public List<String> getArlLogMessages() {
		return arlLogMessages;
	}

	public void setArlLogMessages(List<String> arlLogMessages) {
		this.arlLogMessages = arlLogMessages;
	}

	
}
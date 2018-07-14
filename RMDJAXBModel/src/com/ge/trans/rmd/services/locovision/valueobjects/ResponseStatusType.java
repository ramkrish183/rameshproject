/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

/**
 * @author MSHIRAJUDDIN
 *
 */
public class ResponseStatusType {
	
	private String status;
	private List<DetailsType> details;
		
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the details
	 */
	public List<DetailsType> getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(List<DetailsType> details) {
		this.details = details;
	}


}

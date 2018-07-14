package com.ge.trans.eoa.services.asset.service.valueobjects;

public class ISDCodeVO {
	protected String country;
	protected String isdCode;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIsdCode() {
		return isdCode;
	}
	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}
	public ISDCodeVO(String country, String isdCode) {
		super();
		this.country = country;
		this.isdCode = isdCode;
	}		
	
}

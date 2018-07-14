package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LDVRAssetTempReapplyRemoveResponseType {
	

	private List<LstAssetTempReapplyRemoveStatusType> lstAssetTempReapplyRemoveStatusVo;

	/**
	 * @return the lstAssetTempReapplyRemoveStatusVo
	 */
	public List<LstAssetTempReapplyRemoveStatusType> getLstAssetTempReapplyRemoveStatusVo() {
		return lstAssetTempReapplyRemoveStatusVo;
	}

	/**
	 * @param lstAssetTempReapplyRemoveStatusVo the lstAssetTempReapplyRemoveStatusVo to set
	 */
	public void setLstAssetTempReapplyRemoveStatusVo(
			List<LstAssetTempReapplyRemoveStatusType> lstAssetTempReapplyRemoveStatusVo) {
		this.lstAssetTempReapplyRemoveStatusVo = lstAssetTempReapplyRemoveStatusVo;
	}
	

}

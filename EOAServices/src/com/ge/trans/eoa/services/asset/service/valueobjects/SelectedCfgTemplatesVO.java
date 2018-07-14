package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.List;

public class SelectedCfgTemplatesVO {

	private List<String> edpObjIdList;
	private List<String> ffdObjIdList;
	private List<String> frdObjId;
	private List<String> ahcObjId;
	private List<String> rciObjId;
	private String efiObjId;

	public String getEfiObjId() {
		return efiObjId;
	}

	public void setEfiObjId(String efiObjId) {
		this.efiObjId = efiObjId;
	}

	public List<String> getEdpObjIdList() {
		return edpObjIdList;
	}

    public void setEdpObjIdList(List<String> edpObjIdList) {
		this.edpObjIdList = edpObjIdList;
	}

	public List<String> getFfdObjIdList() {
		return ffdObjIdList;
	}

	public void setFfdObjIdList(List<String> ffdObjIdList) {
		this.ffdObjIdList = ffdObjIdList;
	}

	public List<String> getFrdObjId() {
		return frdObjId;
	}

	public void setFrdObjId(List<String> frdObjId) {
		this.frdObjId = frdObjId;
	}
	
	public List<String> getAhcObjId() {
        return ahcObjId;
    }

    public void setAhcObjId(List<String> ahcObjId) {
        this.ahcObjId = ahcObjId;
    }

    public List<String> getRciObjId() {
        return rciObjId;
    }

    public void setRciObjId(List<String> rciObjId) {
        this.rciObjId = rciObjId;
    }

	
	

	

}

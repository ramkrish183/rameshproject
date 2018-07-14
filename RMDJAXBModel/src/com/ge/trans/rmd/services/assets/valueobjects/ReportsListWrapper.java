package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

public class ReportsListWrapper {

    @XmlElementWrapper(name = "wrapperList")
    private List<String> listValues;

    public void setList(List<String> list) {
        this.listValues = list;
    }

	public List<String> getList() {
		return listValues;
	}
    
}

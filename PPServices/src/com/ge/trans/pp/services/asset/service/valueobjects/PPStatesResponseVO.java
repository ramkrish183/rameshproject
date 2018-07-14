package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class PPStatesResponseVO extends BaseVO {

    private String countryName;
    private List<StatesVO> stateList;

    public List<StatesVO> getStateList() {
        return stateList;
    }

    public void setStateList(List<StatesVO> stateList) {
        this.stateList = stateList;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}

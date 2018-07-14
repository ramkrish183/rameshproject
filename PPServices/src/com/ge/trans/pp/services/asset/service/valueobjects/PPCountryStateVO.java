package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: may 24, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : VO class for geting state and country list
 * @History :
 ******************************************************************************/
public class PPCountryStateVO extends BaseVO {

    private static final long serialVersionUID = -2461104542854686253L;
    private String countryName;
    private List<String> stateList;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<String> getStateList() {
        return stateList;
    }

    public void setStateList(List<String> stateList) {
        this.stateList = stateList;
    }

}
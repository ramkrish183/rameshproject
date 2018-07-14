package com.ge.trans.eoa.services.asset.service.valueobjects;

public class AddressSearchVO {

    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String addrFilter;
    private String cityFilter;
    private String stateFilter;
    private String zipCodeFilter;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddrFilter() {
        return addrFilter;
    }

    public void setAddrFilter(String addrFilter) {
        this.addrFilter = addrFilter;
    }

    public String getCityFilter() {
        return cityFilter;
    }

    public void setCityFilter(String cityFilter) {
        this.cityFilter = cityFilter;
    }

    public String getStateFilter() {
        return stateFilter;
    }

    public void setStateFilter(String stateFilter) {
        this.stateFilter = stateFilter;
    }

    public String getZipCodeFilter() {
        return zipCodeFilter;
    }

    public void setZipCodeFilter(String zipCodeFilter) {
        this.zipCodeFilter = zipCodeFilter;
    }
}

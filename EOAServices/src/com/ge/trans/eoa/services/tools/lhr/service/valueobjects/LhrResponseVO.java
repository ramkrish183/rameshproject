package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

import java.util.List;

public class LhrResponseVO {

    private List<VehicleHealthDataVO> vehicleHealthDataVOList;

    public List<VehicleHealthDataVO> getVehicleHealthDataVOList() {
        return vehicleHealthDataVOList;
    }

    public void setVehicleHealthDataVOList(List<VehicleHealthDataVO> vehicleHealthDataVOList) {
        this.vehicleHealthDataVOList = vehicleHealthDataVOList;
    }
}

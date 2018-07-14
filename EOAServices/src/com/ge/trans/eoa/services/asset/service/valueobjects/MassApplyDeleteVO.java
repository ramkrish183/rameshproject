package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.List;

public class MassApplyDeleteVO {

    private String tempObjId;
    private String templateNo;
    private String tempVer;
    private String cfgType;
    private List<VehicleDetailsVO> arlVehicleDetailsVO;
    private String userName;
    private String ctrlCfgName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCtrlCfgName() {
        return ctrlCfgName;
    }

    public void setCtrlCfgName(String ctrlCfgName) {
        this.ctrlCfgName = ctrlCfgName;
    }

    public String getTempObjId() {
        return tempObjId;
    }

    public void setTempObjId(String tempObjId) {
        this.tempObjId = tempObjId;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }

    public String getTempVer() {
        return tempVer;
    }

    public void setTempVer(String tempVer) {
        this.tempVer = tempVer;
    }

    public String getCfgType() {
        return cfgType;
    }

    public void setCfgType(String cfgType) {
        this.cfgType = cfgType;
    }

    public List<VehicleDetailsVO> getArlVehicleDetailsVO() {
        return arlVehicleDetailsVO;
    }

    public void setArlVehicleDetailsVO(
            List<VehicleDetailsVO> arlVehicleDetailsVO) {
        this.arlVehicleDetailsVO = arlVehicleDetailsVO;
    }

}

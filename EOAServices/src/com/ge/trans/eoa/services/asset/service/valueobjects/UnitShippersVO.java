/**
 * ============================================================
 * Classification: GE Confidential
 * File : UnitShippersVO.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.service.valueobjects;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.asset.service.valueobjects;



public class UnitShippersVO {

    private  String objId;
    private  String rnh;
    private  String roadNumber;
    private  String shipDate;
    private  String testTrackDate;
    
    
    public String getObjId() {
        return objId;
    }
    public void setObjId(String objId) {
        this.objId = objId;
    }
    public String getRnh() {
        return rnh;
    }
    public void setRnh(String rnh) {
        this.rnh = rnh;
    }
    public String getRoadNumber() {
        return roadNumber;
    }
    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }
    public String getShipDate() {
        return shipDate;
    }
    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }
    public String getTestTrackDate() {
        return testTrackDate;
    }
    public void setTestTrackDate(String testTrackDate) {
        this.testTrackDate = testTrackDate;
    }
}

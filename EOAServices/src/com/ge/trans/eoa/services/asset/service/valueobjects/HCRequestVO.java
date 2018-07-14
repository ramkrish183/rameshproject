package com.ge.trans.eoa.services.asset.service.valueobjects;

/**
 * ============================================================
 * File : HCRequestVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.egahc.vo
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 6, 2014
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2014 General Electric Company. All rights reserved
 * Classification : GE Sensitive
 * ============================================================
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class HCRequestVO implements Serializable {
    /**
     * Serial Version Id
     */
    private static final long serialVersionUID = 8625111373874752205L;
    @XmlElement(name = "HEADER")
    protected HeaderVO objHeaderVO;
    @XmlElement(name = "HEALTH_CHECK_DATA")
    protected HealthCheckDataVO objHealthCheckDataVO;

    /**
     * @return the objHeaderVO
     */
    public HeaderVO getObjHeaderVO() {
        return objHeaderVO;
    }

    /**
     * @param objHeaderVO
     *            the objHeaderVO to set
     */
    public void setObjHeaderVO(HeaderVO objHeaderVO) {
        this.objHeaderVO = objHeaderVO;
    }

    /**
     * @return the objHealthCheckDataVO
     */
    public HealthCheckDataVO getObjHealthCheckDataVO() {
        return objHealthCheckDataVO;
    }

    /**
     * @param objHealthCheckDataVO
     *            the objHealthCheckDataVO to set
     */
    public void setObjHealthCheckDataVO(HealthCheckDataVO objHealthCheckDataVO) {
        this.objHealthCheckDataVO = objHealthCheckDataVO;
    }

}

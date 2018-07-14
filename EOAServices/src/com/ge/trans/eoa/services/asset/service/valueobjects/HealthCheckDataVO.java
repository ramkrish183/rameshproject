package com.ge.trans.eoa.services.asset.service.valueobjects;

/**
 * ============================================================
 * File : HealthCheckDataVO.java
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
@XmlRootElement(name = "HEALTH_CHECK_DATA")
public class HealthCheckDataVO implements Serializable {
    /**
     * Serial Version Id
     */
    private static final long serialVersionUID = 8625111373874752205L;

    @XmlElement(name = "SAMPLE_RATE")
    protected String sampleRate;

    @XmlElement(name = "NO_OF_SAMPLES")
    protected String noOfSamples;

    @XmlElement(name = "MP_NUMBERS")
    protected String mpNumbers;

    /**
     * @return the sampleRate
     */
    public String getSampleRate() {
        return sampleRate;
    }

    /**
     * @param sampleRate
     *            the sampleRate to set
     */
    public void setSampleRate(String sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * @return the noOfSamples
     */
    public String getNoOfSamples() {
        return noOfSamples;
    }

    /**
     * @param noOfSamples
     *            the noOfSamples to set
     */
    public void setNoOfSamples(String noOfSamples) {
        this.noOfSamples = noOfSamples;
    }

    /**
     * @return the mpNumbers
     */
    public String getMpNumbers() {
        return mpNumbers;
    }

    /**
     * @param mpNumbers
     *            the mpNumbers to set
     */
    public void setMpNumbers(String mpNumbers) {
        this.mpNumbers = mpNumbers;
    }

}

package com.ge.trans.rmd.common.valueobjects;

import java.util.List;

public class HealthCheckAvailableVO {

    private List<String> platform;
    private String defaultPlatform;
    private String strHCMessage;
    private String device;
    private List<String> allDevices;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public List<String> getAllDevices() {
        return allDevices;
    }

    public void setAllDevices(List<String> allDevices) {
        this.allDevices = allDevices;
    }

    /**
     * @return the platform
     */
    public List<String> getPlatform() {
        return platform;
    }

    /**
     * @param platform
     *            the platform to set
     */
    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

    /**
     * @return the defaultPlatform
     */
    public String getDefaultPlatform() {
        return defaultPlatform;
    }

    /**
     * @param defaultPlatform
     *            the defaultValue to set
     */
    public void setDefaultPlatform(String defaultValue) {
        this.defaultPlatform = defaultValue;
    }

    /**
     * @return the strHCMessage
     */
    public String getStrHCMessage() {
        return strHCMessage;
    }

    /**
     * @param strHCMessage
     *            the strHCMessage to set
     */
    public void setStrHCMessage(String strHCMessage) {
        this.strHCMessage = strHCMessage;
    }

}

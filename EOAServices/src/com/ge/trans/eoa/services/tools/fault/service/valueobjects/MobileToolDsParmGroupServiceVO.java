
package com.ge.trans.eoa.services.tools.fault.service.valueobjects;


public class MobileToolDsParmGroupServiceVO {

    private static final long serialVersionUID = 1L;
    private String paramGroup;
    private String toolTip;
    private String style;


	/**
     * @return the paramGroup
     */
    public String getParamGroup() {
        return paramGroup;
    }

    /**
     * @param paramGroup
     *            the paramGroup to set
     */
    public void setParamGroup(final String paramGroup) {
        this.paramGroup = paramGroup;
    }
    /**
     * @return the toolTip
     */
    public String getToolTip() {
        return toolTip;
    }

    /**
     * @param toolTip
     *            the toolTip to set
     */
    public void setToolTip(final String toolTip) {
        this.toolTip = toolTip;
    }    
    
    public String getStyle() {
		return style;
	}

	public void setStyle(final String style) {
		this.style = style;
	}
}
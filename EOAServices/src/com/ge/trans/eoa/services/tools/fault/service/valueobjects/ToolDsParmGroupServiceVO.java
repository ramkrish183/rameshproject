/*******************************************************************************
 * 
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: May 19, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
package com.ge.trans.eoa.services.tools.fault.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 19, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class ToolDsParmGroupServiceVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    private String paramGroup;
    private String groupHeaderWidth;
    private String colspan;
    private String variableHeaderWidth;
    private String style;
    private String toolTip;
    private String dummyParamGroup;

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
     * @return the groupHeaderWidth
     */
    public String getGroupHeaderWidth() {
        return groupHeaderWidth;
    }

    /**
     * @param groupHeaderWidth
     *            the groupHeaderWidth to set
     */
    public void setGroupHeaderWidth(final String groupHeaderWidth) {
        this.groupHeaderWidth = groupHeaderWidth;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style
     *            the style to set
     */
    public void setStyle(final String style) {
        this.style = style;
    }

    /**
     * @return the dummyParamGroup
     */
    public String getDummyParamGroup() {
        return dummyParamGroup;
    }

    /**
     * @param dummyParamGroup
     *            the dummyParamGroup to set
     */
    public void setDummyParamGroup(final String dummyParamGroup) {
        this.dummyParamGroup = dummyParamGroup;
    }

    /**
     * @return the variableHeaderWidth
     */
    public String getVariableHeaderWidth() {
        return variableHeaderWidth;
    }

    /**
     * @param variableHeaderWidth
     *            the variableHeaderWidth to set
     */
    public void setVariableHeaderWidth(final String variableHeaderWidth) {
        this.variableHeaderWidth = variableHeaderWidth;
    }

    /**
     * @return the colspan
     */
    public String getColspan() {
        return colspan;
    }

    /**
     * @param colspan
     *            the colspan to set
     */
    public void setColspan(final String colspan) {
        this.colspan = colspan;
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
}
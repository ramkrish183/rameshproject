/**
 * ============================================================
 * File : GetSysLookupMultilangVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.rmd.common.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class GetSysLookupMultilangVO extends BaseVO implements Comparable<GetSysLookupMultilangVO> {

    private static final long serialVersionUID = -3065299574191844286L;
    private Long getSysLookupMultilSeqId;
    private Long getSysLookupSeqId;
    private String displayName;
    private String language;
    private Integer rowVersion;

    public GetSysLookupMultilangVO() {
        super();
    }

    public GetSysLookupMultilangVO(final String language) {
        super();
        this.language = language;
    }

    public Long getGetSysLookupMultilSeqId() {
        return this.getSysLookupMultilSeqId;
    }

    public void setGetSysLookupMultilSeqId(final Long getSysLookupMultilSeqId) {
        this.getSysLookupMultilSeqId = getSysLookupMultilSeqId;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public Integer getRowVersion() {
        return this.rowVersion;
    }

    @Override
    public void setRowVersion(final Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Long getGetSysLookupSeqId() {
        return getSysLookupSeqId;
    }

    public void setGetSysLookupSeqId(Long getSysLookupSeqId) {
        this.getSysLookupSeqId = getSysLookupSeqId;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return (this == obj) || (obj instanceof GetSysLookupMultilangVO
                && this.language.equals(((GetSysLookupMultilangVO) obj).language));
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return language.hashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(GetSysLookupMultilangVO another) {
        return this.language.compareTo(another.getLanguage());
    }
}
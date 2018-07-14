/**
 * ============================================================
 * Classification: GE Confidential
 * File : UsersVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.security.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 31, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.security.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Contains user details
 * @History :
 ******************************************************************************/
public class FavFilterDetailServiceVO extends BaseVO {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = 6245186534L;

    private Long filterId;
    private String filterName;
    private List<FilterDetailVO> filterDetail;

    public FavFilterDetailServiceVO() {
    }

    public FavFilterDetailServiceVO(Long filterId, String filterName, List<FilterDetailVO> filterDetail) {
        super();
        this.filterId = filterId;
        this.filterName = filterName;
        this.filterDetail = filterDetail;
    }

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public List<FilterDetailVO> getFilterDetail() {
        return filterDetail;
    }

    public void setFilterDetail(List<FilterDetailVO> filterDetail) {
        this.filterDetail = filterDetail;
    }

}
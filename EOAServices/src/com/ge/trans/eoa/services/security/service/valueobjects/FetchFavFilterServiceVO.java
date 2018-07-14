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
public class FetchFavFilterServiceVO extends BaseVO {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = 6245186534L;

    private Long usrRoleSeqId;

    private List<FavFilterDetailServiceVO> filterDetail;

    public FetchFavFilterServiceVO() {
    }

    public Long getUsrRoleSeqId() {
        return usrRoleSeqId;
    }

    public void setUsrRoleSeqId(Long usrRoleSeqId) {
        this.usrRoleSeqId = usrRoleSeqId;
    }

    public List<FavFilterDetailServiceVO> getFilterDetail() {
        return filterDetail;
    }

    public void setFilterDetail(List<FavFilterDetailServiceVO> filterDetail) {
        this.filterDetail = filterDetail;
    }

}
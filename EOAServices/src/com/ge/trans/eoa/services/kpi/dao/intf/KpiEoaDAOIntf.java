package com.ge.trans.eoa.services.kpi.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataCountResponseEoaVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataEoaBean;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountEoaServiceVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountResponseVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface KpiEoaDAOIntf {

    /**
     * @Author:
     * @param userVO
     * @return UserVO
     * @Description: Method for retrieving user all user information
     */
    List<KpiAssetCountResponseVO> getAssetKPICounts(KpiAssetCountEoaServiceVO kpiAssetCountVO);

    List<KPIDataCountResponseEoaVO> getRxTotalCount(final KPIDataEoaBean kpiDataBean) throws RMDDAOException;

    List<KPIDataCountResponseEoaVO> getKPICounts(final KPIDataEoaBean kpiDataBean) throws RMDDAOException;

    public String getFirstCustomer(KPIDataEoaBean kpiDataBean) throws RMDDAOException;
}

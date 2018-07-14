package com.ge.trans.eoa.services.kpi.service.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataCountResponseEoaVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataEoaBean;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountResponseVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountEoaServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface KpiEoaServiceIntf {

    /**
     * @Author:
     * @param uservo
     * @return boolean
     * @throws RMDServiceException
     * @Description: Method to check if the user is valid
     */
    List<KpiAssetCountResponseVO> getAssetKPICounts(KpiAssetCountEoaServiceVO kpiAssetCountVO)
            throws RMDServiceException;

    public Map<String, List<KPIDataCountResponseEoaVO>> getRxTotalCount(final KPIDataEoaBean kpiDataBean)
            throws RMDServiceException;

}

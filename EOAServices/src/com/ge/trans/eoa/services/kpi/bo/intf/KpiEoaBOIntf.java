package com.ge.trans.eoa.services.kpi.bo.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataCountResponseEoaVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataEoaBean;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountEoaServiceVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountResponseVO;

public interface KpiEoaBOIntf {

    /**
     * @Author:
     * @param userVO
     * @return
     * @throws RMDBOException
     * @Description: Method to check if a user is valid
     */
    List<KpiAssetCountResponseVO> getAssetKPICounts(KpiAssetCountEoaServiceVO kpiAssetCountVO) throws RMDBOException;

    Map<String, List<KPIDataCountResponseEoaVO>> getRxTotalCount(final KPIDataEoaBean kpiDataBean)
            throws RMDBOException;
}

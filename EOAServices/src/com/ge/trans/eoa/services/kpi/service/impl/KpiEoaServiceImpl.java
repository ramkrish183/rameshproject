package com.ge.trans.eoa.services.kpi.service.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.kpi.bo.intf.KpiEoaBOIntf;
import com.ge.trans.eoa.services.kpi.service.intf.KpiEoaServiceIntf;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataCountResponseEoaVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataEoaBean;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountEoaServiceVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountResponseVO;

public class KpiEoaServiceImpl implements KpiEoaServiceIntf {

    private KpiEoaBOIntf objKpiBOIntf;

    /**
     * @param objauthenticatorBOIntf
     */
    public KpiEoaServiceImpl(KpiEoaBOIntf objKpiBOIntf) {
        this.objKpiBOIntf = objKpiBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see Method to get Asset counts
     */
    @Override
    public List<KpiAssetCountResponseVO> getAssetKPICounts(final KpiAssetCountEoaServiceVO kpiAssetCountVO)
            throws RMDServiceException {
        List<KpiAssetCountResponseVO> kpiAsstCountResVO = null;
        try {
            kpiAsstCountResVO = objKpiBOIntf.getAssetKPICounts(kpiAssetCountVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, kpiAssetCountVO.getStrLanguage());
        }
        return kpiAsstCountResVO;
    }

    @Override
    public Map<String, List<KPIDataCountResponseEoaVO>> getRxTotalCount(final KPIDataEoaBean kpiDataBean)
            throws RMDServiceException {

        Map<String, List<KPIDataCountResponseEoaVO>> kpiMap = null;
        try {
            kpiMap = objKpiBOIntf.getRxTotalCount(kpiDataBean);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, kpiDataBean.getStrLanguage());
        }
        return kpiMap;

    }
}

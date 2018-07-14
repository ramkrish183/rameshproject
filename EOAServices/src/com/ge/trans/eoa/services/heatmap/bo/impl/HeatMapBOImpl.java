package com.ge.trans.eoa.services.heatmap.bo.impl;

import com.ge.trans.eoa.services.heatmap.bo.intf.HeatMapBOIntf;
import com.ge.trans.eoa.services.heatmap.dao.intf.HeatMapDAOIntf;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapResponseVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapSearchVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

/**
 * @author 212338353
 *
 */
public class HeatMapBOImpl implements HeatMapBOIntf {
    
    private HeatMapDAOIntf objHeatMapDAOIntf;

    /**
     * @param objHeatMapDAOIntf
     */
    public HeatMapBOImpl(final HeatMapDAOIntf objHeatMapDAOIntf) {
        super();
        this.objHeatMapDAOIntf = objHeatMapDAOIntf;
    }

    
    public static final RMDLogger heatMapBoLog = RMDLoggerHelper
            .getLogger(HeatMapBOImpl.class);
    
    @Override
    public HeatMapResponseVO getHeatMapModels(HeatMapSearchVO heatmapSearchVO)
            throws RMDBOException {
        HeatMapResponseVO heatMapResponseVO;
        try {
            heatMapResponseVO = objHeatMapDAOIntf
                    .getHeatMapModels(heatmapSearchVO);
        } catch (RMDDAOException e) {
            heatMapBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return heatMapResponseVO;

    }

    @Override
    public HeatMapResponseVO getHeatMapFaults(HeatMapSearchVO heatmapSearchVO)
            throws RMDBOException {
        HeatMapResponseVO heatMapResponseVO;
        try {
            heatMapResponseVO = objHeatMapDAOIntf
                    .getHeatMapFaults(heatmapSearchVO);
        } catch (RMDDAOException e) {
            heatMapBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return heatMapResponseVO;

    }

    @Override
    public HeatMapResponseVO getHeatMapAssetHeaders(
            HeatMapSearchVO heatmapSearchVO) throws RMDBOException {
        HeatMapResponseVO heatMapResponseVO;
        try {
            heatMapResponseVO = objHeatMapDAOIntf
                    .getHeatMapAssetHeaders(heatmapSearchVO);
        } catch (RMDDAOException e) {
            heatMapBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return heatMapResponseVO;
    }

    @Override
    public HeatMapResponseVO getHeatMapAssetNumbers(
            HeatMapSearchVO heatmapSearchVO) throws RMDBOException {
        HeatMapResponseVO heatMapResponseVO;
        try {
            heatMapResponseVO = objHeatMapDAOIntf
                    .getHeatMapAssetNumbers(heatmapSearchVO);
        } catch (RMDDAOException e) {
            heatMapBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return heatMapResponseVO;
    }

    @Override
    public HeatMapResponseVO getHeatMapFilterFaults(
            HeatMapSearchVO heatmapSearchVO) throws RMDBOException {
        HeatMapResponseVO heatMapResponseVO;
        try {
            heatMapResponseVO = objHeatMapDAOIntf
                    .getHeatMapFilterFaults(heatmapSearchVO);
        } catch (RMDDAOException e) {
            heatMapBoLog.error(e, e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return heatMapResponseVO;
    }

}

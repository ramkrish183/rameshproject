package com.ge.trans.eoa.services.heatmap.service.impl;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.heatmap.bo.intf.HeatMapBOIntf;
import com.ge.trans.eoa.services.heatmap.service.intf.HeatMapServiceIntf;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapResponseVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapSearchVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class HeatMapServiceImpl implements HeatMapServiceIntf {

    private static final RMDLogger heatMapServiceLog = RMDLoggerHelper
            .getLogger(HeatMapServiceImpl.class);
    
    private HeatMapBOIntf objHeatMapBOIntf;

    public HeatMapServiceImpl(HeatMapBOIntf objHeatMapBOIntf) {
        this.objHeatMapBOIntf = objHeatMapBOIntf;
    }

    @Override
    public HeatMapResponseVO getHeatMapModels(HeatMapSearchVO heatMapSearchVO)
            throws RMDServiceException {
        HeatMapResponseVO heatMapResponseVO = null;
        try {
            heatMapResponseVO = objHeatMapBOIntf
                    .getHeatMapModels(heatMapSearchVO);
        }catch (RMDBOException ex) {
            heatMapServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            heatMapServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return heatMapResponseVO;
    }

    @Override
    public HeatMapResponseVO getHeatMapFaults(HeatMapSearchVO heatmapSearchVO)
            throws RMDServiceException {
        HeatMapResponseVO heatMapResponseVO = null;
        try {
            heatMapResponseVO = objHeatMapBOIntf
                    .getHeatMapFaults(heatmapSearchVO);
        }catch (RMDBOException ex) {
            heatMapServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            heatMapServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return heatMapResponseVO;
    }

    @Override
    public HeatMapResponseVO getHeatMapAssetHeaders(
            HeatMapSearchVO heatmapSearchVO) throws RMDServiceException {
        HeatMapResponseVO heatMapResponseVO = null;
        try {
            heatMapResponseVO = objHeatMapBOIntf
                    .getHeatMapAssetHeaders(heatmapSearchVO);
        }catch (RMDBOException ex) {
            heatMapServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            heatMapServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return heatMapResponseVO;
    }

    @Override
    public HeatMapResponseVO getHeatMapAssetNumbers(
            HeatMapSearchVO heatmapSearchVO) throws RMDServiceException {
        HeatMapResponseVO heatMapResponseVO = null;
        try {
            heatMapResponseVO = objHeatMapBOIntf
                    .getHeatMapAssetNumbers(heatmapSearchVO);
        } catch (RMDBOException ex) {
            heatMapServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            heatMapServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return heatMapResponseVO;
    }

    @Override
    public HeatMapResponseVO getHeatMapFilterFaults(
            HeatMapSearchVO heatmapSearchVO) throws RMDServiceException {
        HeatMapResponseVO heatMapResponseVO = null;
        try {
            heatMapResponseVO = objHeatMapBOIntf
                    .getHeatMapFilterFaults(heatmapSearchVO);
        }catch (RMDBOException ex) {
            heatMapServiceLog.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            heatMapServiceLog.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return heatMapResponseVO;
    }

}

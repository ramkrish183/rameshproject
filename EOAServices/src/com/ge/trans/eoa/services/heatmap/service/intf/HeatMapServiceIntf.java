package com.ge.trans.eoa.services.heatmap.service.intf;

import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapResponseVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapSearchVO;
import com.ge.trans.rmd.exception.RMDServiceException;


/**
 * @author 212338353
 *
 */
public interface HeatMapServiceIntf {
    
    /**
     * To populate models based on customer
     * @param heatmapSearchVO
     * @return
     * @throws RMDServiceException
     */
    public HeatMapResponseVO getHeatMapModels(HeatMapSearchVO heatmapSearchVO)
            throws RMDServiceException;
    /**
     * To populate asset headers based on model & customer
     * @param heatmapSearchVO
     * @return
     * @throws RMDServiceException
     */
    public HeatMapResponseVO getHeatMapAssetHeaders(
            HeatMapSearchVO heatmapSearchVO) throws RMDServiceException;
    /**
     * To populate asset numbers based on model & customer
     * @param heatmapSearchVO
     * @return
     * @throws RMDServiceException
     */
    public HeatMapResponseVO getHeatMapFaults(HeatMapSearchVO heatmapSearchVO)
            throws RMDServiceException;
    /**
     * To populate fault based on model,customer & days or date

     * @param heatmapSearchVO
     * @return
     * @throws RMDServiceException
     */
    public HeatMapResponseVO getHeatMapAssetNumbers(
            HeatMapSearchVO heatmapSearchVO) throws RMDServiceException;
    /**
     * To populate fault along with lat/long based on model,customer,fault & days or date 
     * @param heatmapSearchVO
     * @return
     * @throws RMDServiceException
     */
    public HeatMapResponseVO getHeatMapFilterFaults(
            HeatMapSearchVO heatmapSearchVO) throws RMDServiceException;

}

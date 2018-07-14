package com.ge.trans.eoa.services.heatmap.bo.intf;

import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapResponseVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapSearchVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

/**
 * @author 212338353
 *
 */
public interface HeatMapBOIntf {
    
    /**
     * To populate models based on customer
     * @param heatmapSearchVO
     * @return
     * @throws RMDBOException
     */    
    public HeatMapResponseVO getHeatMapModels(HeatMapSearchVO heatmapSearchVO)
            throws RMDBOException;
    /**
     * To populate asset headers based on model & customer
     * @param heatmapSearchVO
     * @return
     * @throws RMDBOException
     */
    public HeatMapResponseVO getHeatMapAssetHeaders(
            HeatMapSearchVO heatmapSearchVO) throws RMDBOException;
    /**
     * To populate faults based on model,customer & days or date
     * @param heatmapSearchVO
     * @return
     * @throws RMDBOException
     */
    public HeatMapResponseVO getHeatMapFaults(HeatMapSearchVO heatmapSearchVO)
            throws RMDBOException;
    /**
     * To populate asset numbers based on model & customer
     * @param heatmapSearchVO
     * @return
     * @throws RMDBOException
     */
    public HeatMapResponseVO getHeatMapAssetNumbers(
            HeatMapSearchVO heatmapSearchVO) throws RMDBOException;
    /**
     * To populate fault along with lat/long based on model,customer,fault & days or date 
     * @param heatmapSearchVO
     * @return
     * @throws RMDDAOException
     */
    public HeatMapResponseVO getHeatMapFilterFaults(
            HeatMapSearchVO heatmapSearchVO) throws RMDBOException;

}

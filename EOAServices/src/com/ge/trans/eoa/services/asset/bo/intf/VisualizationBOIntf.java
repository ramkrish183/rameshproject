package com.ge.trans.eoa.services.asset.bo.intf;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : VisualizationBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.assets.resources;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxVisualizationPlotInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VirtualParametersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationEventDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationGraphDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationMPParmNumVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as BO interface layer and provide the
 *              visualization related funtionalities
 * @History :
 ******************************************************************************/
public interface VisualizationBOIntf {
    /**
     * This method is used for fetching graphDetails of visualization screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDBOException
     */
    public List<VisualizationGraphDetailsVO> getGraphDetails(VisualizationGraphDetailsVO objDetailsVO)
            throws RMDBOException;

    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDBOException
     */
    public List<VisualizationDetailsVO> getVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDBOException;

    public List<VisualizationMPParmNumVO> getParmNumbers(String source, String sourceType, String language)
            throws RMDBOException;

    /**
     * This method is used for fetching event details for an asset related to
     * visualization screen
     * 
     * @param VisualizationEventDataVO
     * @return List<VisualizationEventDataVO>
     * @throws RMDBOException
     */
    public List<VisualizationEventDataVO> getAssetVisualizationEventData(
            VisualizationEventDataVO objVisualizationEventDataVO) throws RMDBOException;

    /**
     * This method is used for fetching virtual parameters for visualization
     * screen
     * 
     * @param String
     *            database
     * @return List<VirtualParametersVO>
     * @throws RMDBOException
     */
    public List<VirtualParametersVO> getVizVirtualParameters(String database) throws RMDBOException;

    /**
     * This method is used for fetching asset family for Visualization screen
     * 
     * @param String
     *            model, String language
     * @return String
     * @throws RMDBOException
     */
    public String getVizVirtualAssetFamily(String model) throws RMDBOException;
    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDBOException
     */
    public List<VisualizationDetailsVO> getRxVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDBOException;
    /**
   	 * @Author:
   	 * @param
   	 * @return Map<String, String>
   	 * @Description: This method is used to fetch the asset family visualization 
   	 */
   	public  RxVisualizationPlotInfoVO getVizPlotInformations(String rxObjid)
   			 throws RMDBOException;
}

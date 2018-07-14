package com.ge.trans.eoa.services.asset.service.intf;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : VisualizationServiceIntf.java
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

import com.ge.trans.eoa.services.asset.service.valueobjects.RxVisualizationPlotInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VirtualParametersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationEventDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationGraphDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationMPParmNumVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as service interface layer and provide the
 *              visualization related funtionalities
 * @History :
 ******************************************************************************/
public interface VisualizationServiceIntf {
    /**
     * This method is used for fetching graphDetails of visualization screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDServiceException
     */
    public List<VisualizationGraphDetailsVO> getGraphDetails(VisualizationGraphDetailsVO objDetailsVO)
            throws RMDServiceException;

    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDServiceException
     */
    public List<VisualizationDetailsVO> getVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDServiceException;

    /**
     * This method is used for fetching graphDetails of visualization screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDServiceException
     */
    public List<VisualizationMPParmNumVO> getParmNumbers(String source, String sourceType, String language)
            throws RMDServiceException;

    /**
     * This method is used for fetching event details for an asset related to
     * visualization screen
     * 
     * @param VisualizationEventDataVO
     * @return List<VisualizationEventDataVO>
     * @throws RMDServiceException
     */
    public List<VisualizationEventDataVO> getAssetVisualizationEventData(
            VisualizationEventDataVO objVisualizationEventDataVO) throws RMDServiceException;

    /**
     * This method is used for fetching virtual Parameters of visualization
     * screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDServiceException
     */
    public List<VirtualParametersVO> getVizVirtualParameters(String database, String language)
            throws RMDServiceException;

    /**
     * This method is used for fetching asset family for Visualization screen
     * 
     * @param String
     *            model, String language
     * @return String
     * @throws RMDServiceException
     */

    public String getVizVirtualAssetFamily(String model, String language) throws RMDServiceException;
    
    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDServiceException
     */
    public List<VisualizationDetailsVO> getRxVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDServiceException;
    /**
	 * @Author:
	 * @param
	 * @return Map<String, String>
	 * @Description: This method is used to fetch the asset family visualization 
	 */
	public  RxVisualizationPlotInfoVO getVizPlotInformations(String rxObjid)
			 throws RMDServiceException;
}

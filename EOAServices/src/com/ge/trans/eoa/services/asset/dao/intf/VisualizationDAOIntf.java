package com.ge.trans.eoa.services.asset.dao.intf;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : VisualizationDAOIntf.java
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

import com.ge.trans.rmd.exception.RMDDAOException;
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
 * @Description : This Class act as DAO interface layer and provide the
 *              visualization related funtionalities
 * @History :
 ******************************************************************************/
public interface VisualizationDAOIntf {
    /**
     * This method is used for fetching graphDetails of visualization screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDDAOException
     */
    public List<VisualizationGraphDetailsVO> getGraphDetails(VisualizationGraphDetailsVO objDetailsVO)
            throws RMDDAOException;

    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDDAOException
     */
    public List<VisualizationDetailsVO> getVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDDAOException;

    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDDAOException
     */
    public List<VisualizationDetailsVO> getSnapshotVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDDAOException;

    public List<VisualizationMPParmNumVO> getSnapshotParmNumbers(String source, String sourceType, String language)
            throws RMDDAOException;

    public List<VisualizationMPParmNumVO> getERParmNumbers(String source, String sourceType, String language)
            throws RMDDAOException;

    public List<VisualizationEventDataVO> getAssetVisualizationEventData(
            VisualizationEventDataVO objVisualizationEventDataVO) throws RMDDAOException;

    /**
     * This method is used for fetching virtual Parameters of visualization
     * screen
     * 
     * @param String
     *            database
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDServiceException
     */
    public List<VirtualParametersVO> getVizVirtualParameters() throws RMDDAOException;

    public String getVizVirtualAssetFamily(String model) throws RMDDAOException;
    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDDAOException
     */
    public List<VisualizationDetailsVO> getRxVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDDAOException;
    public RxVisualizationPlotInfoVO getVizPlotInformations(
			String rxObjid) throws RMDDAOException ;
    
}

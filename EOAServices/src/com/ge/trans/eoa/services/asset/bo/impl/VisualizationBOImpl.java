package com.ge.trans.eoa.services.asset.bo.impl;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : VisualizationBOImpl.java
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
import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.VisualizationBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.AssetEoaDAOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.VisualizationDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxVisualizationPlotInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VirtualParametersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationEventDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationGraphDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationMPParmNumVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as BO implementation layer and provide the
 *              visualization related funtionalities
 * @History :
 ******************************************************************************/
public class VisualizationBOImpl implements VisualizationBOIntf {

    private VisualizationDAOIntf visualizationDAOIntf;
    private AssetEoaDAOIntf objAssetEoaDAOIntf;
    public VisualizationBOImpl(VisualizationDAOIntf visualizationDAOIntf,AssetEoaDAOIntf objAssetEoaDAOIntf) {
        this.visualizationDAOIntf = visualizationDAOIntf;
        this.objAssetEoaDAOIntf = objAssetEoaDAOIntf;
    }

    /**
     * This method is used for fetching graphDetails of visualization screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDBOException
     */
    @Override
    public List<VisualizationGraphDetailsVO> getGraphDetails(VisualizationGraphDetailsVO objDetailsVO)
            throws RMDBOException {
        List<VisualizationGraphDetailsVO> arlGraphDetailsVO = null;
        try {

            arlGraphDetailsVO = visualizationDAOIntf.getGraphDetails(objDetailsVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlGraphDetailsVO;

    }

    /**
     * This method is used for fetching graphDetails of visualization screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDBOException
     */

    @Override
    public List<VisualizationDetailsVO> getVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDBOException {
        List<VisualizationDetailsVO> arlVisualizationDetailsVO = new ArrayList<VisualizationDetailsVO>();
        // List<VisualizationDetailsVO> arlVizVirtualDetails = new
        // ArrayList<VisualizationDetailsVO>();
        try {
            if (RMDServiceConstants.SNAPSHOT.equalsIgnoreCase(objVisualizationDetailsRequestVO.getSourceType())) {
                // if (null != objVisualizationDetailsRequestVO.getMpNumbers()
                // &&
                // !objVisualizationDetailsRequestVO.getMpNumbers().isEmpty()) {
                // arlVisualizationDetailsVO = visualizationDAOIntf
                // .getSnapshotVisualizationDetails(objVisualizationDetailsRequestVO);
                // }
                // if (null !=
                // objVisualizationDetailsRequestVO.getVirtualParameters()
                // &&
                // !objVisualizationDetailsRequestVO.getVirtualParameters().isEmpty())
                // {
                // arlVizVirtualDetails= visualizationDAOIntf
                // .getVizVirtualDetails(objVisualizationDetailsRequestVO);
                // }
                // arlVisualizationDetailsVO.addAll(arlVizVirtualDetails);
                arlVisualizationDetailsVO = visualizationDAOIntf
                        .getSnapshotVisualizationDetails(objVisualizationDetailsRequestVO);

            } else if (RMDServiceConstants.ENGINERECORDER
                    .equalsIgnoreCase(objVisualizationDetailsRequestVO.getSourceType())) {
                arlVisualizationDetailsVO = visualizationDAOIntf
                        .getVisualizationDetails(objVisualizationDetailsRequestVO);
            }
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlVisualizationDetailsVO;

    }

    @Override
    public List<VisualizationMPParmNumVO> getParmNumbers(String source, String sourceType, String language)
            throws RMDBOException {
        List<VisualizationMPParmNumVO> arlVisualizationMPParmNumVO = new ArrayList<VisualizationMPParmNumVO>();
        try {
            if (RMDServiceConstants.SNAPSHOT.equalsIgnoreCase(sourceType)) {
                arlVisualizationMPParmNumVO = visualizationDAOIntf.getSnapshotParmNumbers(source, sourceType, language);

            } else if (RMDServiceConstants.ENGINERECORDER.equalsIgnoreCase(sourceType)) {
                arlVisualizationMPParmNumVO = visualizationDAOIntf.getERParmNumbers(source, sourceType, language);
            }
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlVisualizationMPParmNumVO;

    }

    @Override
    public List<VisualizationEventDataVO> getAssetVisualizationEventData(
            VisualizationEventDataVO objVisualizationEventDataVO) throws RMDBOException {
        List<VisualizationEventDataVO> arlVisualizationEventData = new ArrayList<VisualizationEventDataVO>();
        try {

            arlVisualizationEventData = visualizationDAOIntf
                    .getAssetVisualizationEventData(objVisualizationEventDataVO);

        } catch (RMDDAOException e) {
            throw e;
        }
        return arlVisualizationEventData;

    }

    /**
     * This method is used for fetching virtual parameters for visualization
     * screen
     * 
     * @param String
     *            database,String language
     * @return List<VirtualParametersVO>
     * @throws RMDBOException
     */
    @Override
    public List<VirtualParametersVO> getVizVirtualParameters(String database) throws RMDBOException {

        List<VirtualParametersVO> arlVirtualParameters = new ArrayList<VirtualParametersVO>();
        try {
            arlVirtualParameters = visualizationDAOIntf.getVizVirtualParameters();
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlVirtualParameters;

    }

    /**
     * This method is used for fetching asset family for Visualization screen
     * 
     * @param String
     *            model, String language
     * @return String
     * @throws RMDBOException
     */
    @Override
    public String getVizVirtualAssetFamily(String model) throws RMDBOException {

        String family = RMDCommonConstants.EMPTY_STRING;
        try {
            family = visualizationDAOIntf.getVizVirtualAssetFamily(model);
        } catch (RMDDAOException e) {
            throw e;
        }
        return family;

    }
    
    
    /**
     * This method is used for fetching graphDetails of visualization screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDBOException
     */

    @Override
    public List<VisualizationDetailsVO> getRxVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDBOException {
        List<VisualizationDetailsVO> arlVisualizationDetailsVO = new ArrayList<VisualizationDetailsVO>();
        
        try {
        	String vehicleObjid=objAssetEoaDAOIntf.getVehicleObjectId(objVisualizationDetailsRequestVO.getCustomerId(), objVisualizationDetailsRequestVO.getAssetNumber(), objVisualizationDetailsRequestVO.getAssetGroupName());
        	objVisualizationDetailsRequestVO.setAssetObjid(vehicleObjid);
                arlVisualizationDetailsVO = visualizationDAOIntf
                        .getRxVisualizationDetails(objVisualizationDetailsRequestVO);

            
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlVisualizationDetailsVO;

    }
    
    @Override
    public RxVisualizationPlotInfoVO getVizPlotInformations(String rxObjid) throws RMDBOException {

    	RxVisualizationPlotInfoVO objRxVisualizationPlotInfoVO=null;
        try {
        	objRxVisualizationPlotInfoVO = visualizationDAOIntf.getVizPlotInformations(rxObjid);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objRxVisualizationPlotInfoVO;
    }
}

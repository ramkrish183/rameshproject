package com.ge.trans.eoa.services.asset.service.impl;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : VisualizationServiceImpl.java
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

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.asset.bo.intf.VisualizationBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.VisualizationServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxVisualizationPlotInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VirtualParametersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationEventDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationGraphDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationMPParmNumVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as service implementation layer and provide the
 *              visualization related funtionalities
 * @History :
 ******************************************************************************/
public class VisualizationServiceImpl implements VisualizationServiceIntf {

    private VisualizationBOIntf objVisualizationBOIntf;

    public VisualizationServiceImpl(VisualizationBOIntf objVisualizationBOIntf) {
        this.objVisualizationBOIntf = objVisualizationBOIntf;
    }

    /**
     * This method is used for fetching graphDetails of visualization screen
     * 
     * @param VisualizationGraphDetailsVO
     * @return List<VisualizationGraphDetailsVO>
     * @throws RMDServiceException
     */
    @Override
    public List<VisualizationGraphDetailsVO> getGraphDetails(VisualizationGraphDetailsVO objDetailsVO)
            throws RMDServiceException {
        List<VisualizationGraphDetailsVO> arlGraphDetailsVO = null;
        try {
            arlGraphDetailsVO = objVisualizationBOIntf.getGraphDetails(objDetailsVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objDetailsVO.getStrLanguage());
        }
        return arlGraphDetailsVO;

    }

    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDServiceException
     */

    @Override
    public List<VisualizationDetailsVO> getVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDServiceException {

        List<VisualizationDetailsVO> arlVisualizationDetailsVO = null;
        try {
            arlVisualizationDetailsVO = objVisualizationBOIntf
                    .getVisualizationDetails(objVisualizationDetailsRequestVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objVisualizationDetailsRequestVO.getUserLanguage());
        }
        return arlVisualizationDetailsVO;

    }

    @Override
    public List<VisualizationMPParmNumVO> getParmNumbers(String source, String sourceType, String language)
            throws RMDServiceException {
        List<VisualizationMPParmNumVO> arlVisualizationMPParmNumVO = new ArrayList<VisualizationMPParmNumVO>();
        try {

            arlVisualizationMPParmNumVO = objVisualizationBOIntf.getParmNumbers(source, sourceType, language);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return arlVisualizationMPParmNumVO;

    }

    @Override
    public List<VisualizationEventDataVO> getAssetVisualizationEventData(
            VisualizationEventDataVO objVisualizationEventDataVO) throws RMDServiceException {
        List<VisualizationEventDataVO> arlVisualizationEventData = new ArrayList<VisualizationEventDataVO>();
        try {

            arlVisualizationEventData = objVisualizationBOIntf
                    .getAssetVisualizationEventData(objVisualizationEventDataVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objVisualizationEventDataVO.getStrLanguage());
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
     * @throws RMDServiceException
     */

    @Override
    public List<VirtualParametersVO> getVizVirtualParameters(String database, String language)
            throws RMDServiceException {
        List<VirtualParametersVO> arlVirtualParameters = new ArrayList<VirtualParametersVO>();
        try {

            arlVirtualParameters = objVisualizationBOIntf.getVizVirtualParameters(database);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return arlVirtualParameters;

    }

    /**
     * This method is used for fetching asset family for Visualization screen
     * 
     * @param String
     *            model, String language
     * @return String
     * @throws RMDServiceException
     */
    @Override
    public String getVizVirtualAssetFamily(String model, String language) throws RMDServiceException {
        String family = RMDCommonConstants.EMPTY_STRING;
        try {

            family = objVisualizationBOIntf.getVizVirtualAssetFamily(model);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return family;

    }
    
    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param VisualizationDetailsRequestVO
     * @return List<VisualizationDetailsVO>
     * @throws RMDServiceException
     */

    @Override
    public List<VisualizationDetailsVO> getRxVisualizationDetails(
            VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) throws RMDServiceException {

        List<VisualizationDetailsVO> arlVisualizationDetailsVO = null;
        try {
            arlVisualizationDetailsVO = objVisualizationBOIntf
                    .getRxVisualizationDetails(objVisualizationDetailsRequestVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objVisualizationDetailsRequestVO.getUserLanguage());
        }
        return arlVisualizationDetailsVO;

    }
    
    @Override
    public RxVisualizationPlotInfoVO getVizPlotInformations(String rxObjid) throws RMDServiceException {

    	RxVisualizationPlotInfoVO objRxVisualizationPlotInfoVO=null;
        try {
        	objRxVisualizationPlotInfoVO = objVisualizationBOIntf.getVizPlotInformations(rxObjid);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex,RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return objRxVisualizationPlotInfoVO;
    }

}

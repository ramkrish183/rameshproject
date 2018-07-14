package com.ge.trans.rmd.services.heatmap.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetVO;
import com.ge.trans.eoa.services.heatmap.service.intf.HeatMapServiceIntf;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.FaultHeatMapVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.FaultVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapResponseVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapSearchVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.alert.valueobjects.ModelsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetResponseType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultCodesResponseType;
import com.ge.trans.rmd.services.heatmap.valueobjects.HeatMapFaultResponseType;
import com.ge.trans.rmd.services.heatmap.valueobjects.HeatMapRequestType;
import com.ge.trans.rmd.services.heatmap.valueobjects.HeatMapResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.HEATMAP_SERVICE)
@Component
public class HeatMapResource extends BaseResource {
    public static final RMDLogger LOG = RMDLoggerHelper
            .getLogger(HeatMapResource.class);

    @Autowired
    private HeatMapServiceIntf objHeatMapServiceIntf;
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    @POST
    @Path(OMDConstants.HEATMAP_MODELS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HeatMapResponseType getHeatMapModels(final HeatMapRequestType reqobj)
            throws RMDServiceException {
        HeatMapSearchVO objHeatMapSearchVO = new HeatMapSearchVO();
        HeatMapResponseType heatMapRespType = new HeatMapResponseType();
        List<ModelsResponseType> lstModelRespType = null;
        ModelsResponseType modelRespType = null;
        try {
            if (null != reqobj) {
                objHeatMapSearchVO.setCustomerId(reqobj.getCustomerId());

                HeatMapResponseVO heatMapRespVO = objHeatMapServiceIntf
                        .getHeatMapModels(objHeatMapSearchVO);

                if (null != heatMapRespVO) {                    
                    if (RMDCommonUtility.isCollectionNotEmpty(heatMapRespVO
                            .getModels())) {
                        lstModelRespType = new ArrayList<ModelsResponseType>(
                                heatMapRespVO.getModels().size());
                        for (ModelVO modelVO : heatMapRespVO.getModels()) {
                            modelRespType = new ModelsResponseType();
                            modelRespType.setModelName(modelVO.getModelName());
                            modelRespType
                                    .setModelObjId(modelVO.getModelObjId());
                            lstModelRespType.add(modelRespType);
                        }

                    }
                    heatMapRespType.setModels(lstModelRespType);
                } 
            }
        }catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return heatMapRespType;
    }

    @POST
    @Path(OMDConstants.HEATMAP_ASSET_HEADERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HeatMapResponseType getHeatMapAssetHeaders(
            final HeatMapRequestType reqobj) throws RMDServiceException {
        HeatMapSearchVO objHeatMapSearchVO = new HeatMapSearchVO();
        HeatMapResponseType heatMapRespType = new HeatMapResponseType();
        List<AssetResponseType> lstAssetHeaderRespType = null;
        AssetResponseType assetRespType = null;
        try {
            if (null != reqobj) {
                objHeatMapSearchVO.setCustomerId(reqobj.getCustomerId());
                objHeatMapSearchVO.setModelLst(reqobj.getModelLst());
                objHeatMapSearchVO.setAssetNumLst(reqobj.getAssetNumberLst());

                HeatMapResponseVO heatMapRespVO = objHeatMapServiceIntf
                        .getHeatMapAssetHeaders(objHeatMapSearchVO);

                if (null != heatMapRespVO) {                    
                    if (RMDCommonUtility.isCollectionNotEmpty(heatMapRespVO
                            .getAssets())) {
                        lstAssetHeaderRespType = new ArrayList<AssetResponseType>(
                                heatMapRespVO.getAssets().size());
                        for (AssetVO assetVO : heatMapRespVO.getAssets()) {
                            assetRespType = new AssetResponseType();
                            assetRespType.setAssetGroupName(assetVO.getRnh());
                            lstAssetHeaderRespType.add(assetRespType);
                        }
                    }
                    heatMapRespType.setAssets(lstAssetHeaderRespType);
                }
            }

        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return heatMapRespType;
    }

    @POST
    @Path(OMDConstants.HEATMAP_ASSET_NUMBERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HeatMapResponseType getHeatMapAssetNumbers(
            final HeatMapRequestType reqobj) throws RMDServiceException {
        HeatMapSearchVO objHeatMapSearchVO = new HeatMapSearchVO();
        HeatMapResponseType heatMapRespType = new HeatMapResponseType();
        List<AssetResponseType> lstAssetHeaderRespType = null;
        AssetResponseType assetRespType = null;
        try {
            if (null != reqobj) {
                objHeatMapSearchVO.setCustomerId(reqobj.getCustomerId());
                objHeatMapSearchVO.setModelLst(reqobj.getModelLst());
                objHeatMapSearchVO
                        .setAssetHeaderLst(reqobj.getAssetHeaderLst());

                HeatMapResponseVO heatMapRespVO = objHeatMapServiceIntf
                        .getHeatMapAssetNumbers(objHeatMapSearchVO);

                if (null != heatMapRespVO) {                    
                    if (RMDCommonUtility.isCollectionNotEmpty(heatMapRespVO
                            .getAssets())) {
                        lstAssetHeaderRespType = new ArrayList<AssetResponseType>(
                                heatMapRespVO.getAssets().size());
                        for (AssetVO assetVO : heatMapRespVO.getAssets()) {
                            assetRespType = new AssetResponseType();
                            assetRespType.setAssetNumber(assetVO
                                    .getRoadNumber());
                            lstAssetHeaderRespType.add(assetRespType);
                        }

                    }
                    heatMapRespType.setAssets(lstAssetHeaderRespType);
                }
            }

        }catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return heatMapRespType;
    }

    @POST
    @Path(OMDConstants.HEATMAP_FAULTS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HeatMapResponseType getHeatMapFaults(final HeatMapRequestType reqobj)
            throws RMDServiceException {
        HeatMapSearchVO objHeatMapSearchVO = new HeatMapSearchVO();
        HeatMapResponseType heatMapRespType = new HeatMapResponseType();
        List<FaultCodesResponseType> lstFaultCodesResponseType = null;
        FaultCodesResponseType faultRespType = null;
        try {
            if (null != reqobj) {
                objHeatMapSearchVO.setCustomerId(reqobj.getCustomerId());
                objHeatMapSearchVO.setModelLst(reqobj.getModelLst());
                objHeatMapSearchVO
                        .setAssetHeaderLst(reqobj.getAssetHeaderLst());
                objHeatMapSearchVO.setAssetNumLst(reqobj.getAssetNumberLst());
                objHeatMapSearchVO.setFromDate(reqobj.getFromDate());
                objHeatMapSearchVO.setToDate(reqobj.getToDate());
                objHeatMapSearchVO.setNoOfDays(reqobj.getNoOfdays());
                objHeatMapSearchVO.setIsNonGPOCUser(reqobj.getIsNonGPOCUser());
                HeatMapResponseVO heatMapRespVO = objHeatMapServiceIntf
                        .getHeatMapFaults(objHeatMapSearchVO);

                if (null != heatMapRespVO) {                    
                    if (RMDCommonUtility.isCollectionNotEmpty(heatMapRespVO
                            .getFaults())) {
                        lstFaultCodesResponseType = new ArrayList<FaultCodesResponseType>(
                                heatMapRespVO.getFaults().size());
                        for (FaultVO faultVO : heatMapRespVO.getFaults()) {
                            faultRespType = new FaultCodesResponseType();
                            faultRespType.setFaultCode(faultVO
                                    .getStrFaultCode());
                            faultRespType.setFaultDescription(faultVO
                                    .getStrFaultDescription());
                            faultRespType.setFaultSubId(faultVO.getStrSubId());
                            lstFaultCodesResponseType.add(faultRespType);
                        }
                    }
                    heatMapRespType.setFaults(lstFaultCodesResponseType);
                }
            }

        }catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return heatMapRespType;
    }

    @POST
    @Path(OMDConstants.HEATMAP_FILTERS_RESULT)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HeatMapResponseType getHeatMapFilterFaults(
            final HeatMapRequestType reqobj) throws RMDServiceException {
        HeatMapSearchVO objHeatMapSearchVO = new HeatMapSearchVO();
        HeatMapResponseType heatMapRespType = new HeatMapResponseType();
        List<HeatMapFaultResponseType> lstFaultCodesResponseType = null;
        HeatMapFaultResponseType faultRespType = null;
        try {
            if (null != reqobj) {
                objHeatMapSearchVO.setCustomerId(reqobj.getCustomerId());
                objHeatMapSearchVO.setModelLst(reqobj.getModelLst());
                objHeatMapSearchVO
                        .setAssetHeaderLst(reqobj.getAssetHeaderLst());
                objHeatMapSearchVO.setAssetNumLst(reqobj.getAssetNumberLst());
                objHeatMapSearchVO.setFaultCodeLst(reqobj.getFaultCodeLst());
                objHeatMapSearchVO.setFromDate(reqobj.getFromDate());
                objHeatMapSearchVO.setToDate(reqobj.getToDate());
                objHeatMapSearchVO.setNoOfDays(reqobj.getNoOfdays());
                objHeatMapSearchVO.setIsNonGPOCUser(reqobj.getIsNonGPOCUser());

                HeatMapResponseVO heatMapRespVO = objHeatMapServiceIntf
                        .getHeatMapFilterFaults(objHeatMapSearchVO);

                if (null != heatMapRespVO) {                    
                    if (RMDCommonUtility.isCollectionNotEmpty(heatMapRespVO
                            .getFaultHeatMapVO())) {
                        lstFaultCodesResponseType = new ArrayList<HeatMapFaultResponseType>(
                                heatMapRespVO.getFaultHeatMapVO().size());
                        for (FaultHeatMapVO faultVO : heatMapRespVO.getFaultHeatMapVO()) {
                            faultRespType = new HeatMapFaultResponseType();
                            faultRespType.setOccurDate(faultVO.getOccurDate());
                            faultRespType.setOffBoardLoadDate(faultVO.getOffBoardLoadDate());
                            faultRespType.setStrGPSLatitude(faultVO.getStrGPSLatitude());
                            faultRespType.setStrGPSLongitude(faultVO.getStrGPSLongitude());
                            faultRespType.setStrAssetHeader(faultVO.getStrAssetHeader());
                            faultRespType.setStrAssetNumber(faultVO.getStrAssetNumber());
                            faultRespType.setStrCustomerId(faultVO.getStrCustomerId());
                            faultRespType.setStrCustomerName(faultVO.getStrCustomerName());
                            faultRespType.setStrSerialNo(faultVO.getStrSerialNo());
                            faultRespType.setStrSubId(faultVO.getStrSubId());
                            faultRespType.setStrFaultCode(faultVO.getStrFaultCode());
                            faultRespType.setStrFaultDescription(faultVO.getStrFaultDescription());
                            faultRespType.setStrFaultObjId(faultVO.getStrFaultObjId());
                            faultRespType.setStrModelId(faultVO.getStrModelId());
                            faultRespType.setStrModelName(faultVO.getStrModelName());
                            faultRespType.setWorstUrgency(faultVO.getWorstUrgency());
                            lstFaultCodesResponseType.add(faultRespType);
                        }
                    }
                    heatMapRespType.setFaultHeatMapResponseType(lstFaultCodesResponseType); 
                }
            }

        }catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return heatMapRespType;
    }

}

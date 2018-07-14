package com.ge.trans.rmd.services.kpi.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.kpi.service.intf.KpiEoaServiceIntf;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataCountResponseEoaVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataEoaBean;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountEoaServiceVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountResponseVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.RxUrgencyInfoEoaVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.resources.AssetResource;
import com.ge.trans.rmd.services.kpi.valueobjects.KpiAssetCountsResponseType;
import com.ge.trans.rmd.services.kpi.valueobjects.KpiInfoType;
import com.ge.trans.rmd.services.kpi.valueobjects.KpiRequestType;
import com.ge.trans.rmd.services.kpi.valueobjects.KpiTotalCountRequestType;
import com.ge.trans.rmd.services.kpi.valueobjects.KpiTotalCountResponseType;
import com.ge.trans.rmd.services.kpi.valueobjects.ParameterInfoType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.KPISERVICE)
@Component
public class KPIResource extends BaseResource {

    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(AssetResource.class);

    @Autowired
    OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    KpiEoaServiceIntf objKpiEoaServiceIntf;

    @GET
    @Path(OMDConstants.GETASSETKPICOUNTS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<KpiAssetCountsResponseType> getAssetKPICounts(@PathParam(OMDConstants.KPINAME) final String kpiName,
            @Context UriInfo uriParam) throws RMDServiceException {
        RMDLOGGER.debug("KPIResource : Inside getAssetKPICounts() method:::START");
        KpiAssetCountEoaServiceVO kpiAssetCountVO = null;
        MultivaluedMap<String, String> queryParams = null;
        List<KpiAssetCountResponseVO> kpiAsstCountRespVO = null;
        List<KpiAssetCountsResponseType> lsKpiAsstCountRespType = new ArrayList<KpiAssetCountsResponseType>();
        try {
            kpiAssetCountVO = new KpiAssetCountEoaServiceVO();
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_NUMERIC_UNDERSCORE, OMDConstants.ALPHABETS,
                    OMDConstants.AlPHA_NUMERIC, OMDConstants.NUMERIC, OMDConstants.ALPHABETS };
            String[] userInput = { OMDConstants.ASSET_NUMBER, OMDConstants.ASSET_GROUP_NAME, OMDConstants.CUSTOMER_ID,
                    OMDConstants.DAYS, OMDConstants.KPI_NAME };
            queryParams.add(OMDConstants.KPI_NAME, kpiName);
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, userInput)) {
                // copying the query params
                if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                    kpiAssetCountVO.setAssetNumber(queryParams.getFirst(OMDConstants.ASSET_NUMBER));
                } else {
                    kpiAssetCountVO.setAssetNumber(OMDConstants.EMPTY_STRING);
                }

                if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                    kpiAssetCountVO.setAssetGroupName(queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME));
                } else {
                    kpiAssetCountVO.setAssetGroupName(OMDConstants.EMPTY_STRING);
                }

                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    kpiAssetCountVO.setCustomerId(queryParams.getFirst(OMDConstants.CUSTOMER_ID));
                } else {
                    kpiAssetCountVO.setCustomerId(OMDConstants.EMPTY_STRING);
                }

                if (kpiName != null) {
                    kpiAssetCountVO.setKpiName(kpiName);
                } else {
                    kpiAssetCountVO.setKpiName(OMDConstants.EMPTY_STRING);
                }

                if (queryParams.containsKey(OMDConstants.DAYS)) {
                    kpiAssetCountVO.setNumDays(Long.valueOf(queryParams.getFirst(OMDConstants.DAYS)));
                }

                kpiAsstCountRespVO = objKpiEoaServiceIntf.getAssetKPICounts(kpiAssetCountVO);

                if (RMDCommonUtility.isCollectionNotEmpty(kpiAsstCountRespVO)) {
                    for (final Iterator<KpiAssetCountResponseVO> iterAsstCountResp = kpiAsstCountRespVO
                            .iterator(); iterAsstCountResp.hasNext();) {

                        final KpiAssetCountResponseVO assetCountResponseVO = iterAsstCountResp.next();
                        KpiAssetCountsResponseType kpiAsstCountResp = new KpiAssetCountsResponseType();
                        List<ParameterInfoType> lsParameterInfoType = new ArrayList<ParameterInfoType>();

                        kpiAsstCountResp.setAssetNumber(assetCountResponseVO.getAssetNumber());
                        kpiAsstCountResp.setAssetGroupName(assetCountResponseVO.getAssetGroupName());
                        kpiAsstCountResp.setCustomerId(assetCountResponseVO.getCustomerId());
                        kpiAsstCountResp.setKpiName(assetCountResponseVO.getKpiName());
                        kpiAsstCountResp.setTotalCount(assetCountResponseVO.getTotalCount());

                        for (RxUrgencyInfoEoaVO urgInfoVO : assetCountResponseVO.getUrgInfoVO()) {
                            ParameterInfoType urgInfoType = new ParameterInfoType();
                            urgInfoType.setParameterCount(urgInfoVO.getRxCount());
                            urgInfoType.setParameterName(urgInfoVO.getRxUrgencyType());
                            lsParameterInfoType.add(urgInfoType);
                        }

                        kpiAsstCountResp.setParameter(lsParameterInfoType);
                        lsKpiAsstCountRespType.add(kpiAsstCountResp);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }*/
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            LOG.error("Error Occurred in KPIResource-getAssetKPICounts method" + e);
        }
        RMDLOGGER.debug("KPIResource : Inside getAssetKPICounts() method:::END");
        return lsKpiAsstCountRespType;
    }

    /**
     * This method invokes the web service to get the Rx total count
     * 
     * @param kpiname
     * @return KpiTotalCountResponseType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GETTOTALCOUNTS)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<KpiTotalCountResponseType> getTotalCounts(final KpiTotalCountRequestType objKpiTotCntReqType)
            throws RMDServiceException {
        LOG.debug("In EOAWebservices : KpiResource : getRxTotalCounts():::START");
        Map<String, List<KPIDataCountResponseEoaVO>> kpiMap;
        List<KpiTotalCountResponseType> kpiResponseList = new ArrayList<KpiTotalCountResponseType>();
        List<KPIDataCountResponseEoaVO> kpiResponseVOList = null;
        List<KpiAssetCountResponseVO> kpiRequestList = null;
        KPIDataCountResponseEoaVO ResponseVO;
        KpiInfoType rxInfoType;
        KpiTotalCountResponseType kpiTotalCountResponseType = null;

        final KPIDataEoaBean kpiDataBean = new KPIDataEoaBean();
        try {
            String strLanguage = objKpiTotCntReqType.getLanguage();
            kpiDataBean.setUserLanguage(strLanguage);
            // Copy product assets from request type to service
            if (null != objKpiTotCntReqType.getProdAssetMap() && objKpiTotCntReqType.getProdAssetMap().size() > 0) {
                CMBeanUtility.copyCustomerAssetToServiceVO(objKpiTotCntReqType.getProdAssetMap(), kpiDataBean);
            }
            // validating the Object before copying to service VO
            if (validateKPITotCnt(objKpiTotCntReqType)) {

                if (null != objKpiTotCntReqType.getCustomerId() && !objKpiTotCntReqType.getCustomerId().isEmpty()) {
                    kpiDataBean.setCustomerId(objKpiTotCntReqType.getCustomerId());
                } else {
                    kpiDataBean.setCustomerId(OMDConstants.EMPTY_STRING);
                }
                if (null != objKpiTotCntReqType.getKpiRequestType()
                        && !objKpiTotCntReqType.getKpiRequestType().isEmpty()) {
                    kpiRequestList = new ArrayList<KpiAssetCountResponseVO>();
                    Iterator<KpiRequestType> it = objKpiTotCntReqType.getKpiRequestType().iterator();
                    while (it.hasNext()) {
                        KpiRequestType tempReqType = it.next();
                        KpiAssetCountResponseVO kpiVO = new KpiAssetCountResponseVO();
                        if (null != tempReqType.getKpiName() && !tempReqType.getKpiName().isEmpty()) {
                            kpiVO.setKpiName(tempReqType.getKpiName());
                        } else {
                            kpiVO.setKpiName(OMDConstants.EMPTY_STRING);
                        }
                        kpiVO.setNumDays(tempReqType.getNoDays());
                        kpiRequestList.add(kpiVO);
                    }

                    kpiDataBean.setKpiList(kpiRequestList);
                }

                // get info about Rx Total count
                kpiMap = objKpiEoaServiceIntf.getRxTotalCount(kpiDataBean);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

            Iterator<KpiRequestType> kpiNamesIt = objKpiTotCntReqType.getKpiRequestType().iterator();
            while (kpiNamesIt.hasNext()) {
                List<KpiInfoType> kpiInfoTypeList = new ArrayList<KpiInfoType>();
                String kpiName = kpiNamesIt.next().getKpiName();
                kpiResponseVOList = kpiMap.get(kpiName);
                if (RMDCommonUtility.isCollectionNotEmpty(kpiResponseVOList)) {

                    for (int i = 0; i < kpiResponseVOList.size(); i++) {
                        rxInfoType = new KpiInfoType();
                        kpiTotalCountResponseType = new KpiTotalCountResponseType();
                        ResponseVO = kpiResponseVOList.get(i);
                        if (null != ResponseVO.getRxName()) {
                            kpiTotalCountResponseType.setKpiName(ResponseVO.getRxName());
                        }
                        if (null != ResponseVO.getCustomerId()) {
                            kpiTotalCountResponseType.setCustomerId(ResponseVO.getCustomerId());
                        }
                        if (null != ResponseVO.getTotalCount()) {
                            kpiTotalCountResponseType.setTotalCount(ResponseVO.getTotalCount());
                        }
                        if (null != ResponseVO.getLastFourWeekAvg()) {
                            kpiTotalCountResponseType.setLastFourWeekAvg(ResponseVO.getLastFourWeekAvg());
                        }
                        if (null != ResponseVO.getLastQuarterAvg()) {
                            kpiTotalCountResponseType.setLastQuarterAvg(ResponseVO.getLastQuarterAvg());
                        }
                        if (null != ResponseVO.getCurrentYearAvg()) {
                            kpiTotalCountResponseType.setCurrentYearAvg(ResponseVO.getCurrentYearAvg());
                        }
                        if (null != ResponseVO.getLastUpdatedDate()) {
                            kpiTotalCountResponseType.setLastUpdatedDate(ResponseVO.getLastUpdatedDate());
                        }
                        rxInfoType.setParameterName(ResponseVO.getRxType());
                        rxInfoType.setParameterCount(ResponseVO.getRxTypeCount());
                        kpiInfoTypeList.add(rxInfoType);
                    }
                    if (RMDCommonUtility.isCollectionNotEmpty(kpiInfoTypeList))
                        kpiTotalCountResponseType.getParameter().addAll(kpiInfoTypeList);
                    kpiResponseList.add(kpiTotalCountResponseType);

                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        LOG.debug("In KpiResource : getRxTotalCounts():::END");
        return kpiResponseList;
    }

    /**
     * @param objKpiTotCntReqType
     * @return This method is used for Validating KPI total counts.
     */
    public static boolean validateKPITotCnt(final KpiTotalCountRequestType objKpiTotCntReqType) {

        if (null != objKpiTotCntReqType.getCustomerId() && !objKpiTotCntReqType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objKpiTotCntReqType.getCustomerId())) {
                return false;
            }
        }

        return true;
    }
}
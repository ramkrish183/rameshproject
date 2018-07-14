package com.ge.trans.rmd.services.assets.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.asset.service.intf.ConfigMaintenanceServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddEditEDPDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ConfigTemplateDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CtrlCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.EDPHeaderDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.EDPParamDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.EDPSearchParamVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.EFIDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultFilterDefVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultRangeDefVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultValueVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.TemplateInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.TemplateSearchVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.configmaintenance.valueobjects.TemplateReportResponseType;
import com.ge.trans.rmd.services.assets.configmaintenance.valueobjects.TemplateRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.AddEditEDPRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.ConfigRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.ConfigTemplateDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.CtrlCfgResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.EDPHeaderResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.EDPParamDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.EFIDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FFDRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.FFDResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FRDRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.FRDResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultValueResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_CONFIG_MAINTENANCE_SERVICE)
@Component
public class ConfigMaintenanceResource extends BaseResource {

    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    ConfigMaintenanceServiceIntf configMaintenanceServiceIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ConfigMaintenanceResource.class);

    /**
     * @Author:
     * @param:
     * @return:List<CtrlCfgResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the all controller configs
     */
    @GET
    @Path(OMDConstants.GET_CTRL_CFGS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CtrlCfgResponseType> getControllerConfigs() throws RMDServiceException {

        List<CtrlCfgResponseType> listCtrlCfgResponse = new ArrayList<CtrlCfgResponseType>();
        List<CtrlCfgVO> ctrlCfgList = null;
        CtrlCfgResponseType objCtrlCfgResponseType = null;
        CtrlCfgVO objCtrlCfgVO = null;
        try {

            ctrlCfgList = configMaintenanceServiceIntf.getControllerConfigs();
            if (RMDCommonUtility.isCollectionNotEmpty(ctrlCfgList)) {
                listCtrlCfgResponse = new ArrayList<CtrlCfgResponseType>(ctrlCfgList.size());
                for (final Iterator iterator = ctrlCfgList.iterator(); iterator.hasNext();) {
                    objCtrlCfgVO = (CtrlCfgVO) iterator.next();
                    objCtrlCfgResponseType = new CtrlCfgResponseType();
                    objCtrlCfgResponseType.setCtrlCfgObjId(objCtrlCfgVO.getCtrlCfgObjId());
                    objCtrlCfgResponseType.setCtrlCfgName(objCtrlCfgVO.getCtrlCfgName());
                    listCtrlCfgResponse.add(objCtrlCfgResponseType);
                }
                ctrlCfgList = null;
            }

        } catch (Exception e) {
            LOG.error("Exception occuered in getControllerConfigs() method of ConfigMaintenanceResource" + e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return listCtrlCfgResponse;
    }

    /**
     * @param @Context
     *            UriInfo ui
     * @return List<ConfigTemplateDetailsVO>
     * @throws RMDServiceException
     * @Description This method is used to get the Templates for the selected
     *              Controller Config and Config File.
     */
    @GET
    @Path(OMDConstants.GET_CONFIG_TEMPLATE_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ConfigTemplateDetailsResponseType> getCtrlCfgTemplates(@Context UriInfo ui) throws RMDServiceException {
        List<ConfigTemplateDetailsResponseType> cfgTemplateDetailsList = new ArrayList<ConfigTemplateDetailsResponseType>();
        List<ConfigTemplateDetailsVO> objTemplateDetails = new ArrayList<ConfigTemplateDetailsVO>();
        ConfigTemplateDetailsResponseType cfgTempResponseType = null;
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            String ctrlCfgObjId = OMDConstants.EMPTY_STRING, cfgFileName = OMDConstants.EMPTY_STRING;
            try {
                if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJ_ID)) {
                    ctrlCfgObjId = queryParams.getFirst(OMDConstants.CTRL_CFG_OBJ_ID);
                }
                if (queryParams.containsKey(OMDConstants.CFG_FILE_NAME)) {
                    cfgFileName = queryParams.getFirst(OMDConstants.CFG_FILE_NAME);
                }
                if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
                    throw new OMDInValidInputException(OMDConstants.CONTROLLER_CONFIG_VALUE_IS_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(cfgFileName)) {
                    throw new OMDInValidInputException(OMDConstants.CONFIG_FILE_VALUE_IS_NOT_PROVIDED);
                }

                objTemplateDetails = configMaintenanceServiceIntf.getCtrlCfgTemplates(ctrlCfgObjId, cfgFileName);
                if (RMDCommonUtility.isCollectionNotEmpty(objTemplateDetails)) {
                    cfgTemplateDetailsList = new ArrayList<ConfigTemplateDetailsResponseType>(
                            objTemplateDetails.size());
                    for (ConfigTemplateDetailsVO objTempDetailsVO : objTemplateDetails) {
                        cfgTempResponseType = new ConfigTemplateDetailsResponseType();
                        cfgTempResponseType.setTempObjId(objTempDetailsVO.getTempObjId());
                        cfgTempResponseType.setTemplateNo(objTempDetailsVO.getTemplateNo());
                        cfgTempResponseType.setVersionNo(objTempDetailsVO.getVersionNo());
                        cfgTempResponseType.setTitle(ESAPI.encoder().encodeForXML(EsapiUtil.resumeSpecialChars(objTempDetailsVO.getTitle())));
                        cfgTempResponseType.setStatus(objTempDetailsVO.getStatus());
                        cfgTemplateDetailsList.add(cfgTempResponseType);

                    }
                }

                objTemplateDetails = null;

            } catch (Exception e) {
                LOG.error("Exception occuered in getCtrlCfgTemplates() method of ConfigMaintenanceResource" + e);
                RMDWebServiceErrorHandler.handleException(e,
                        omdResourceMessagesIntf);
            }

        return cfgTemplateDetailsList;
    }

    /**
     * @Author:
     * @param:
     * @return:List<EFIDetailsResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    @GET
    @Path(OMDConstants.GET_EFI_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<EFIDetailsResponseType> getEFIDetails() throws RMDServiceException {
        List<EFIDetailsResponseType> efiDetailsList = new ArrayList<EFIDetailsResponseType>();
        List<EFIDetailsVO> arrEFIDetailsList = new ArrayList<EFIDetailsVO>();
        EFIDetailsResponseType efiResponseType = null;
        try {
            arrEFIDetailsList = configMaintenanceServiceIntf.getEFIDetails();
            if (RMDCommonUtility.isCollectionNotEmpty(arrEFIDetailsList)) {
                efiDetailsList = new ArrayList<EFIDetailsResponseType>(arrEFIDetailsList.size());
                for (EFIDetailsVO objTempDetailsVO : arrEFIDetailsList) {
                    efiResponseType = new EFIDetailsResponseType();
                    efiResponseType.setTempObjId(objTempDetailsVO.getTempObjId());
                    efiResponseType.setTemplateNo(objTempDetailsVO.getTemplateNo());
                    efiResponseType.setVersionNo(objTempDetailsVO.getVersionNo());
                    efiResponseType.setIsActive(objTempDetailsVO.getIsActive());
                    efiDetailsList.add(efiResponseType);

                }
            }
            arrEFIDetailsList = null;
        } catch (Exception e) {
            LOG.error("Exception occuered in getEFIDetails() method of ConfigMaintenanceResource" + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return efiDetailsList;
    }

    /**
     * @param
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to create new EFI template
     */
    @POST
    @Path(OMDConstants.CREATE_NEW_EFI)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String createNewEFI(@Context UriInfo ui) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            String userName = getRequestHeader(OMDConstants.CM_ALIAS_NAME);
            if (RMDCommonUtility.isNullOrEmpty(userName)) {
                throw new OMDInValidInputException(OMDConstants.USER_ID_NOT_PROVIDED);
            }
            result = configMaintenanceServiceIntf.createNewEFI(userName);

        } catch (Exception e) {
            LOG.error("Exception occuered in createNewEFI() method of ConfigMaintenanceResource" + e);
            result = RMDCommonConstants.FAILURE;
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }

        return result;
    }

    /**
     * @param @Context
     *            UriInfo ui
     * @return String
     * @throws RMDServiceException
     * @Description This method is used for fetching the latest template Number
     */
    @GET
    @Path(OMDConstants.GET_MAX_TEMPLATE_NUMBER)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getMaxTemplateNumber(@Context UriInfo ui) throws RMDServiceException {
        String maxTempNo = null;
      
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            String ctrlCfgObjId = OMDConstants.EMPTY_STRING, cfgFileName = OMDConstants.EMPTY_STRING;
            try {
                if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJ_ID)) {
                    ctrlCfgObjId = queryParams.getFirst(OMDConstants.CTRL_CFG_OBJ_ID);
                }
                if (queryParams.containsKey(OMDConstants.CFG_FILE_NAME)) {
                    cfgFileName = queryParams.getFirst(OMDConstants.CFG_FILE_NAME);
                }
                if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
                    throw new OMDInValidInputException(OMDConstants.CONTROLLER_CONFIG_VALUE_IS_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(cfgFileName)) {
                    throw new OMDInValidInputException(OMDConstants.CONFIG_FILE_VALUE_IS_NOT_PROVIDED);
                }

                maxTempNo = configMaintenanceServiceIntf.getMaxTemplateNumber(ctrlCfgObjId, cfgFileName);

            } catch (Exception e) {
                LOG.error("Exception occuered in getMaxTemplateNumber() method of ConfigMaintenanceResource" + e);
                RMDWebServiceErrorHandler.handleException(e,
                        omdResourceMessagesIntf);
            }

            return maxTempNo;
    }

    /**
     * @param @Context
     *            UriInfo ui
     * @return String
     * @throws RMDServiceException
     * @Description This method is used for fetching the latest version number
     *              of selected template
     */
    @GET
    @Path(OMDConstants.GET_TEMPLATE_MAX_VER_NUMBER)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getTempMaxVerNumber(@Context UriInfo ui) throws RMDServiceException {
        String tempMaxVerNO = null;
      
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
            String cfgFileName = OMDConstants.EMPTY_STRING;
            String templateNo = OMDConstants.EMPTY_STRING;
            try {
                if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJ_ID)) {
                    ctrlCfgObjId = queryParams.getFirst(OMDConstants.CTRL_CFG_OBJ_ID);
                }
                if (queryParams.containsKey(OMDConstants.CFG_FILE_NAME)) {
                    cfgFileName = queryParams.getFirst(OMDConstants.CFG_FILE_NAME);
                }
                if (queryParams.containsKey(OMDConstants.TEMPLATE_NO)) {
                    templateNo = queryParams.getFirst(OMDConstants.TEMPLATE_NO);
                }
                if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
                    throw new OMDInValidInputException(OMDConstants.CONTROLLER_CONFIG_VALUE_IS_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(cfgFileName)) {
                    throw new OMDInValidInputException(OMDConstants.CONFIG_FILE_VALUE_IS_NOT_PROVIDED);
                }
                if (RMDCommonUtility.isNullOrEmpty(templateNo)) {
                    throw new OMDInValidInputException(OMDConstants.TEMPLATE_NUMBER_IS_NOT_PROVIDED);
                }

                tempMaxVerNO = configMaintenanceServiceIntf.getTempMaxVerNumber(ctrlCfgObjId, cfgFileName, templateNo);

            } catch (Exception e) {
                LOG.error("Exception occuered in getTempMaxVerNumber() method of ConfigMaintenanceResource" + e);
                RMDWebServiceErrorHandler.handleException(e,
                        omdResourceMessagesIntf);
            }

        return tempMaxVerNO;
    }

    /**
     * @param @Context
     *            UriInfo ui
     * @return List<EDPParamDetailsResponseType>
     * @throws RMDServiceException
     * @Description This method is used to get the parameters that can be added
     *              for EDP templates.
     */
    @GET
    @Path(OMDConstants.GET_EDP_PARAMETRES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<EDPParamDetailsResponseType> getEDPParameters(@Context UriInfo ui) throws RMDServiceException {
        List<EDPParamDetailsResponseType> edpParamDetailsList = new ArrayList<EDPParamDetailsResponseType>();
        List<EDPParamDetailsVO> objEDPDetailsList = new ArrayList<EDPParamDetailsVO>();
        EDPSearchParamVO objEDPSearchParamVo = new EDPSearchParamVO();
        EDPParamDetailsResponseType edpTempResponseType = null;
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            String searchBy = OMDConstants.EMPTY_STRING;
            String condition = OMDConstants.EMPTY_STRING;
            String searchVal = OMDConstants.EMPTY_STRING;
            String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
            String cfgFileName = OMDConstants.EMPTY_STRING;
            try {

                if (queryParams.containsKey(OMDConstants.SEARCH_BY)) {
                    searchBy = queryParams.getFirst(OMDConstants.SEARCH_BY);
                }
                if (queryParams.containsKey(OMDConstants.CONDITION)) {
                    condition = queryParams.getFirst(OMDConstants.CONDITION);
                }
                if (queryParams.containsKey(OMDConstants.SEARCH_VAL)) {
                    searchVal = queryParams.getFirst(OMDConstants.SEARCH_VAL);
                }
                if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJ_ID)) {
                    ctrlCfgObjId = queryParams.getFirst(OMDConstants.CTRL_CFG_OBJ_ID);
                }
                if (queryParams.containsKey(OMDConstants.CFG_FILE_NAME)) {
                    cfgFileName = queryParams.getFirst(OMDConstants.CFG_FILE_NAME);
                }
                if (RMDCommonUtility.isNullOrEmpty(searchBy)) {
                    throw new OMDInValidInputException(OMDConstants.SEARCH_BY_VALUE_IS_NOT_PROVIDED);
                }
                if (RMDCommonUtility.isNullOrEmpty(condition)) {
                    throw new OMDInValidInputException(OMDConstants.SEARCH_CONDITION_IS_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
                    throw new OMDInValidInputException(OMDConstants.CONTROLLER_CONFIG_VALUE_IS_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(searchVal)) {
                    throw new OMDInValidInputException(OMDConstants.SEARCH_VAL_IS_NOT_PROVIDED);
                }
                if (RMDCommonUtility.isNullOrEmpty(cfgFileName)) {
                    throw new OMDInValidInputException(OMDConstants.CONFIG_FILE_VALUE_IS_NOT_PROVIDED);
                }
                objEDPSearchParamVo.setSearchBy(searchBy);
                objEDPSearchParamVo.setCondition(condition);
                objEDPSearchParamVo.setSearchVal(ESAPI.encoder().decodeForHTML(searchVal));
                objEDPSearchParamVo.setCtrlCfgObjId(ctrlCfgObjId);
                objEDPSearchParamVo.setCfgFileName(cfgFileName);
                objEDPDetailsList = configMaintenanceServiceIntf.getEDPParameters(objEDPSearchParamVo);
                if (RMDCommonUtility.isCollectionNotEmpty(objEDPDetailsList)) {
                    edpParamDetailsList = new ArrayList<EDPParamDetailsResponseType>(objEDPDetailsList.size());
                    for (EDPParamDetailsVO objParamDetailsVO : objEDPDetailsList) {
                        edpTempResponseType = new EDPParamDetailsResponseType();
                        edpTempResponseType.setParamObjId(objParamDetailsVO.getParamObjId());
                        edpTempResponseType.setParamNo(objParamDetailsVO.getParamNo());
                        edpTempResponseType.setCtrlName(objParamDetailsVO.getCtrlName());
                        edpTempResponseType
                                .setParamDesc(ESAPI.encoder().encodeForXML(objParamDetailsVO.getParamDesc()));
                        edpTempResponseType.setUom(objParamDetailsVO.getUom());
                        edpTempResponseType.setUsageRate(objParamDetailsVO.getUsageRate());
                        edpTempResponseType.setDestType(objParamDetailsVO.getDestType());
                        edpParamDetailsList.add(edpTempResponseType);

                    }
                    objEDPDetailsList = null;
                }

            } catch (Exception e) {
                LOG.error("Exception occuered in getEDPParameters() method of ConfigMaintenanceResource" + e);
                RMDWebServiceErrorHandler.handleException(e,
                        omdResourceMessagesIntf);
            }

            return edpParamDetailsList;
    }

    /**
     * @Author:
     * @param:tempObjId
     * @return:List<EDPParamDetailsResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the opened template added
     *               parameters
     */
    @GET
    @Path(OMDConstants.GET_ADDED_EDP_PARAMETRES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<EDPParamDetailsResponseType> getAddedEDPParameters(@Context UriInfo ui) throws RMDServiceException {
        List<EDPParamDetailsResponseType> addedEDPParamRespList = new ArrayList<EDPParamDetailsResponseType>();
        List<EDPParamDetailsVO> objAddedParamList = new ArrayList<EDPParamDetailsVO>();
        EDPParamDetailsResponseType edpTempResponseType = null;
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            String tempObjId = OMDConstants.EMPTY_STRING;
            try {

                if (queryParams.containsKey(OMDConstants.TEMP_OBJ_ID)) {
                    tempObjId = queryParams.getFirst(OMDConstants.TEMP_OBJ_ID);
                }
                if (RMDCommonUtility.isNullOrEmpty(tempObjId)) {
                    throw new OMDInValidInputException(OMDConstants.TEMPLATE_OBJ_ID_VALUE_IS_NOT_PROVIDED);
                }

                objAddedParamList = configMaintenanceServiceIntf.getAddedEDPParameters(tempObjId);
                if (RMDCommonUtility.isCollectionNotEmpty(objAddedParamList)) {
                    addedEDPParamRespList = new ArrayList<EDPParamDetailsResponseType>(objAddedParamList.size());
                    for (EDPParamDetailsVO objParamDetailsVO : objAddedParamList) {
                        edpTempResponseType = new EDPParamDetailsResponseType();
                        edpTempResponseType.setParamObjId(objParamDetailsVO.getParamObjId());
                        edpTempResponseType.setParamNo(objParamDetailsVO.getParamNo());
                        edpTempResponseType.setCtrlName(objParamDetailsVO.getCtrlName());
                        edpTempResponseType
                                .setParamDesc(ESAPI.encoder().encodeForXML(objParamDetailsVO.getParamDesc()));
                        edpTempResponseType.setUom(objParamDetailsVO.getUom());
                        edpTempResponseType.setUsageRate(objParamDetailsVO.getUsageRate());
                        edpTempResponseType.setDestType(objParamDetailsVO.getDestType());
                        addedEDPParamRespList.add(edpTempResponseType);

                    }
                    objAddedParamList = null;
                }

            } catch (Exception e) {
                LOG.error("Exception occuered in getAddedEDPParameters() method of ConfigMaintenanceResource" + e);
                RMDWebServiceErrorHandler.handleException(e,
                        omdResourceMessagesIntf);
            }
            return addedEDPParamRespList;
    }

    /**
     * @Author:
     * @param:objAddEditEDPRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for creating or updating EDP templates
     */
    @POST
    @Path(OMDConstants.SAVE_EDP_TEMPLATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String saveEDPTemplate(final AddEditEDPRequestType objAddEditEDPRequestType) throws RMDServiceException {
        String status = null;
        AddEditEDPDetailsVO objAddEditEDPDetailsVO = null;

        try {
            if (null != objAddEditEDPRequestType) {
                objAddEditEDPDetailsVO = new AddEditEDPDetailsVO();
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getTempObjId())) {
                    objAddEditEDPDetailsVO.setTempObjId(objAddEditEDPRequestType.getTempObjId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getCtrlCfgObjId())) {
                    objAddEditEDPDetailsVO.setCtrlCfgObjId(objAddEditEDPRequestType.getCtrlCfgObjId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getCtrlCfgName())) {
                    objAddEditEDPDetailsVO.setCtrlCfgName(objAddEditEDPRequestType.getCtrlCfgName());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getCfgFileName())) {
                    objAddEditEDPDetailsVO.setCfgFileName(objAddEditEDPRequestType.getCfgFileName());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getParamObjId())) {
                    objAddEditEDPDetailsVO.setParamObjId(objAddEditEDPRequestType.getParamObjId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getAddedParamObjId())) {
                    objAddEditEDPDetailsVO.setAddedParamObjId(objAddEditEDPRequestType.getAddedParamObjId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getRemovedParamObjId())) {
                    objAddEditEDPDetailsVO.setRemovedParamObjId(objAddEditEDPRequestType.getRemovedParamObjId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getTemplateNo())) {
                    objAddEditEDPDetailsVO.setTemplateNo(objAddEditEDPRequestType.getTemplateNo());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getVersionNo())) {
                    objAddEditEDPDetailsVO.setVersionNo(objAddEditEDPRequestType.getVersionNo());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getStatus())) {
                    objAddEditEDPDetailsVO.setStatus(objAddEditEDPRequestType.getStatus());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getTitle())) {
                    objAddEditEDPDetailsVO.setTitle(ESAPI.encoder().decodeForHTML(objAddEditEDPRequestType.getTitle()));
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getWhatNew())) {
                    objAddEditEDPDetailsVO.setWhatNew(objAddEditEDPRequestType.getWhatNew());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objAddEditEDPRequestType.getUserName())) {
                    objAddEditEDPDetailsVO.setUserName(objAddEditEDPRequestType.getUserName());
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
            status = configMaintenanceServiceIntf.saveEDPTemplate(objAddEditEDPDetailsVO);
        } catch (Exception e) {
            LOG.error("Exception occuered in saveEDPTemplate() method of ConfigMaintenanceResource" + e);
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
        }
        return status;

    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:EDPHeaderResponseType
     * @throws:RMDServiceException
     * @Description: This method is used for getting the Next/Previous EDP
     *               template templates
     */
    @GET
    @Path(OMDConstants.GET_PRE_NEXT_EDP_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public EDPHeaderResponseType getPreNextEDPDetails(@Context UriInfo ui) throws RMDServiceException {
        EDPHeaderResponseType objEDPHeaderResponseType = null;
        EDPHeaderDetailsVO objEDPHeaderDetailsVO = new EDPHeaderDetailsVO();
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            String cfgFileName = OMDConstants.EMPTY_STRING;
            String templateNo = OMDConstants.EMPTY_STRING;            
            String versionNo = OMDConstants.EMPTY_STRING;
            String ctrlCfgObjId = OMDConstants.EMPTY_STRING;
            try {

                if (queryParams.containsKey(OMDConstants.CFG_FILE_NAME)) {
                    cfgFileName = queryParams.getFirst(OMDConstants.CFG_FILE_NAME);
                }
                if (queryParams.containsKey(OMDConstants.TEMPLATE_NO)) {
                    templateNo = queryParams.getFirst(OMDConstants.TEMPLATE_NO);
                }
                if (queryParams.containsKey(OMDConstants.VERSION_NO)) {
                    versionNo = queryParams.getFirst(OMDConstants.VERSION_NO);
                }
                if (queryParams.containsKey(OMDConstants.CTRL_CFG_OBJ_ID)) {
                    ctrlCfgObjId = queryParams.getFirst(OMDConstants.CTRL_CFG_OBJ_ID);
                }
                if (RMDCommonUtility.isNullOrEmpty(cfgFileName)) {
                    throw new OMDInValidInputException(OMDConstants.CONFIG_FILE_VALUE_IS_NOT_PROVIDED);
                }
                if (RMDCommonUtility.isNullOrEmpty(templateNo)) {
                    throw new OMDInValidInputException(OMDConstants.TEMPLATE_NUMBER_IS_NOT_PROVIDED);
                }
                if (RMDCommonUtility.isNullOrEmpty(versionNo)) {
                    throw new OMDInValidInputException(OMDConstants.VERSION_NUMBER_IS_NOT_PROVIDED);
                }
                if (RMDCommonUtility.isNullOrEmpty(ctrlCfgObjId)) {
                    throw new OMDInValidInputException(OMDConstants.CONTROLLER_CONFIG_VALUE_IS_NOT_PROVIDED);
                }

                objEDPHeaderDetailsVO = configMaintenanceServiceIntf.getPreNextEDPDetails(cfgFileName, templateNo,
                        versionNo, ctrlCfgObjId);
                if (null != objEDPHeaderDetailsVO) {
                    objEDPHeaderResponseType = new EDPHeaderResponseType();
                    objEDPHeaderResponseType.setTempObjId(objEDPHeaderDetailsVO.getTempObjId());
                    objEDPHeaderResponseType.setCtrlCfgObjId(objEDPHeaderDetailsVO.getCtrlCfgObjId());
                    objEDPHeaderResponseType.setCfgFileName(objEDPHeaderDetailsVO.getCfgFileName());
                    objEDPHeaderResponseType.setCtrlCfgName(objEDPHeaderDetailsVO.getCtrlCfgName());
                    objEDPHeaderResponseType.setTemplateNo(objEDPHeaderDetailsVO.getTemplateNo());
                    objEDPHeaderResponseType.setVesrionNo(objEDPHeaderDetailsVO.getVesrionNo());
                    objEDPHeaderResponseType.setTitle(ESAPI.encoder().encodeForXML(objEDPHeaderDetailsVO.getTitle()));
                    objEDPHeaderResponseType.setStatus(objEDPHeaderDetailsVO.getStatus());
                    objEDPHeaderDetailsVO = null;
                }

            } catch (Exception e) {
                LOG.error("Exception occuered in getAddedEDPParameters() method of ConfigMaintenanceResource" + e);
                RMDWebServiceErrorHandler.handleException(e,
                        omdResourceMessagesIntf);
            }

        return objEDPHeaderResponseType;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:List<FaultValueResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for getting the parameter title
     */
    @GET
    @Path(OMDConstants.GET_PARAMETER_TITLE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultValueResponseType> getParameterTitle(@Context UriInfo ui) throws RMDServiceException {
        List<FaultValueResponseType> responseList = new ArrayList<FaultValueResponseType>();
        List<FaultValueVO> valueList = new ArrayList<FaultValueVO>();
        FaultValueResponseType objFFDResponseType = null;
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        String configId = OMDConstants.EMPTY_STRING;
        try {
            LOG.debug("*****getParameterTitle WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
            }
            valueList = configMaintenanceServiceIntf.getParameterTitle(configId);
            if (valueList != null && !valueList.isEmpty()) {
                responseList = new ArrayList<FaultValueResponseType>(valueList.size());
            }
            for (FaultValueVO objFaultValueVO : valueList) {
                objFFDResponseType = new FaultValueResponseType();
                objFFDResponseType.setParameterTitle(objFaultValueVO.getParameterTitle());
                objFFDResponseType.setObjId(objFaultValueVO.getObjId());
                responseList.add(objFFDResponseType);
            }
            valueList = null;

        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return responseList;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:List<FaultValueResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for getting the EDP Template values
     */
    @GET
    @Path(OMDConstants.GET_EDP_TEMPLATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultValueResponseType> getEDPTemplate(@Context UriInfo ui) throws RMDServiceException {
        List<FaultValueResponseType> responseList = new ArrayList<FaultValueResponseType>();
        List<FaultValueVO> valueList = new ArrayList<FaultValueVO>();
        FaultValueResponseType objFFDResponseType = null;
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        String configId = OMDConstants.EMPTY_STRING;
        try {
            LOG.debug("*****getEDPTemplate WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
            }
            valueList = configMaintenanceServiceIntf.getEDPTemplate(configId);
            if (valueList != null && !valueList.isEmpty()) {
                responseList = new ArrayList<FaultValueResponseType>(valueList.size());
            }
            for (FaultValueVO objFaultValueVO : valueList) {
                objFFDResponseType = new FaultValueResponseType();
                objFFDResponseType.setObjId(objFaultValueVO.getObjId());
                objFFDResponseType.setCfgVersion(objFaultValueVO.getCfgVersion());
                objFFDResponseType.setCfgDesc(objFaultValueVO.getCfgDesc());
                objFFDResponseType.setCfgDetailDesc(objFaultValueVO.getCfgDetailDesc());
                responseList.add(objFFDResponseType);
            }
            valueList = null;

        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return responseList;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:List<FaultValueResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for getting Default Values Range
     */
    @GET
    @Path(OMDConstants.GET_DEFAULT_VALUES_RANGE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultValueResponseType> getDefaultValuesRange(@Context UriInfo ui) throws RMDServiceException {
        List<FaultValueResponseType> responseList = new ArrayList<FaultValueResponseType>();
        List<FaultValueVO> valueList = new ArrayList<FaultValueVO>();
        FaultValueResponseType objFFDResponseType = null;
        try {
            LOG.debug("*****getDefaultValuesRange WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            valueList = configMaintenanceServiceIntf.getDefaultValuesRange();
            if (valueList != null && !valueList.isEmpty()) {
                responseList = new ArrayList<FaultValueResponseType>(valueList.size());
            }
            for (FaultValueVO objFaultValueVO : valueList) {
                objFFDResponseType = new FaultValueResponseType();
                objFFDResponseType.setPreValue(objFaultValueVO.getPreValue());
                objFFDResponseType.setPostValue(objFaultValueVO.getPostValue());
                objFFDResponseType.setBiasValue(objFaultValueVO.getBiasValue());
                responseList.add(objFFDResponseType);
            }
            valueList = null;

        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        	 }

        return responseList;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:List<FFDResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for populate FFD Template details
     */
    @GET
    @Path(OMDConstants.POPULATE_FFD_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FFDResponseType> populateFFDDetails(@Context UriInfo ui) throws RMDServiceException {
        List<FFDResponseType> responseList = new ArrayList<FFDResponseType>();
        List<FaultFilterDefVo> valueList = new ArrayList<FaultFilterDefVo>();
        FFDResponseType objFFDResponseType = null;
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        FaultDetailsVO faultDetails = new FaultDetailsVO();
        try {
            LOG.debug("*****populateFFDDetails WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                faultDetails.setConfigId(queryParams.getFirst(OMDConstants.CONFIG_ID));
            }
            if (queryParams.containsKey(OMDConstants.TEMPLATE_ID)) {
                faultDetails.setTemplateId(queryParams.getFirst(OMDConstants.TEMPLATE_ID));
            }
            if (queryParams.containsKey(OMDConstants.VERSION_ID)) {
                faultDetails.setVersion(queryParams.getFirst(OMDConstants.VERSION_ID));
            }
            valueList = configMaintenanceServiceIntf.populateFFDDetails(faultDetails);
            if (valueList != null && !valueList.isEmpty()) {
                responseList = new ArrayList<FFDResponseType>(valueList.size());
            }
            for (FaultFilterDefVo objFaultFilterDefVo : valueList) {
                objFFDResponseType = new FFDResponseType();
                objFFDResponseType.setParameterTitle(objFaultFilterDefVo.getParameterTitle());
                objFFDResponseType.setOperatorFrom(objFaultFilterDefVo.getOperatorFrom());
                objFFDResponseType.setOpertorTo(objFaultFilterDefVo.getOperatorTo());
                objFFDResponseType.setValueFrom(objFaultFilterDefVo.getValueFrom());
                objFFDResponseType.setValueTo(objFaultFilterDefVo.getValueTo());
                objFFDResponseType.setConjunction(objFaultFilterDefVo.getConjunction());
                objFFDResponseType.setParameterObjId(objFaultFilterDefVo.getParameterObjId());
                objFFDResponseType.setObjId(objFaultFilterDefVo.getObjId());
                responseList.add(objFFDResponseType);
            }
            valueList = null;

        	} catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return responseList;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:List<FRDResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for populate FRD Template details
     */
    @GET
    @Path(OMDConstants.POPULATE_FRD_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FRDResponseType> populateFRDDetails(@Context UriInfo ui) throws RMDServiceException {
        List<FRDResponseType> responseList = new ArrayList<FRDResponseType>();
        List<FaultRangeDefVo> valueList = new ArrayList<FaultRangeDefVo>();
        FRDResponseType objFRDResponseType = null;
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        FaultDetailsVO faultDetails = new FaultDetailsVO();
        try {
            LOG.debug("*****populateFFDDetails WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                faultDetails.setConfigId(queryParams.getFirst(OMDConstants.CONFIG_ID));
            }
            if (queryParams.containsKey(OMDConstants.TEMPLATE_ID)) {
                faultDetails.setTemplateId(queryParams.getFirst(OMDConstants.TEMPLATE_ID));
            }
            if (queryParams.containsKey(OMDConstants.VERSION_ID)) {
                faultDetails.setVersion(queryParams.getFirst(OMDConstants.VERSION_ID));
            }
            if (queryParams.containsKey(OMDConstants.CONFIG_VALUE)) {
                faultDetails.setConfigValue(queryParams.getFirst(OMDConstants.CONFIG_VALUE));
            }
            valueList = configMaintenanceServiceIntf.populateFRDDetails(faultDetails);
            if (valueList != null && !valueList.isEmpty()) {
                responseList = new ArrayList<FRDResponseType>(valueList.size());
            }
            for (FaultRangeDefVo objFaultRangeDefVo : valueList) {
                objFRDResponseType = new FRDResponseType();
                objFRDResponseType.setFaultSource(objFaultRangeDefVo.getFaultSource());
                objFRDResponseType.setFaultCodeFrom(objFaultRangeDefVo.getFaultCodeFrom());
                objFRDResponseType.setFaultCodeTo(objFaultRangeDefVo.getFaultCodeTo());
                objFRDResponseType.setSubIdFrom(objFaultRangeDefVo.getSubIdFrom());
                objFRDResponseType.setSubIdTo(objFaultRangeDefVo.getSubIdTo());
                objFRDResponseType.setEdpTemplate(objFaultRangeDefVo.getEdpTemplate());
                objFRDResponseType.setVersion(objFaultRangeDefVo.getVersion());
                objFRDResponseType.setBiasValue(objFaultRangeDefVo.getBiasValue());
                objFRDResponseType.setPreValue(objFaultRangeDefVo.getPreValue());
                objFRDResponseType.setPostValue(objFaultRangeDefVo.getPostValue());
                objFRDResponseType.setObjId(objFaultRangeDefVo.getObjId());
                objFRDResponseType.setFaultStart(objFaultRangeDefVo.getFaultStart());
                objFRDResponseType.setFaultEnd(objFaultRangeDefVo.getFaultEnd());
                objFRDResponseType.setControllerName(objFaultRangeDefVo.getControllerName());
                responseList.add(objFRDResponseType);
            }
            valueList = null;

        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return responseList;
    }

    /**
     * @Author:
     * @param:FFDRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for save FFD Template details
     */
    @POST
    @Path(OMDConstants.SAVE_FFD_TEMPLATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String saveFFDTemplate(FFDRequestType objFFDRequestType) throws RMDServiceException {
        String status = OMDConstants.FAILURE;
        List<FaultFilterDefVo> arlVehicleCfgVOs = new ArrayList<FaultFilterDefVo>();
        List<FFDRequestType> arlFFDRequestType = objFFDRequestType.getArlFFDRequestType();
        FaultFilterDefVo objFaultFilterDefVo = null;
        try {
            for (FFDRequestType ffdRequestType : arlFFDRequestType) {
                objFaultFilterDefVo = new FaultFilterDefVo();
                if (null != ffdRequestType.getConfigId()) {
                    objFaultFilterDefVo.setConfigId(ffdRequestType.getConfigId());
                }
                if (null != ffdRequestType.getConfigValue()) {
                    objFaultFilterDefVo.setConfigValue(ffdRequestType.getConfigValue());
                }
                if (null != ffdRequestType.getConjunction()) {
                    objFaultFilterDefVo.setConjunction(ffdRequestType.getConjunction());
                }
                if (null != ffdRequestType.getOperatorFrom()) {
                    objFaultFilterDefVo.setOperatorFrom(ffdRequestType.getOperatorFrom());
                }
                if (null != ffdRequestType.getOpertorTo()) {
                    objFaultFilterDefVo.setOperatorTo(ffdRequestType.getOpertorTo());
                }
                if (null != ffdRequestType.getParameterTitle()) {
                    objFaultFilterDefVo.setParameterTitle(ffdRequestType.getParameterTitle());
                }
                if (null != ffdRequestType.getStatus()) {
                    objFaultFilterDefVo.setStatus(ffdRequestType.getStatus());
                }
                if (null != ffdRequestType.getTemplate()) {
                    objFaultFilterDefVo.setTemplate(ffdRequestType.getTemplate());
                }
                if (null != ffdRequestType.getVersion()) {
                    objFaultFilterDefVo.setVersion(ffdRequestType.getVersion());
                }
                if (null != ffdRequestType.getTitle()) {
                    objFaultFilterDefVo.setTitle(ffdRequestType.getTitle());
                }
                if (null != ffdRequestType.getUserName()) {
                    objFaultFilterDefVo.setUserName(ffdRequestType.getUserName());
                }
                if (null != ffdRequestType.getValueFrom()) {
                    objFaultFilterDefVo.setValueFrom(ffdRequestType.getValueFrom());
                }
                if (null != ffdRequestType.getValueTo()) {
                    objFaultFilterDefVo.setValueTo(ffdRequestType.getValueTo());
                }
                if (null != ffdRequestType.getAction()) {
                    objFaultFilterDefVo.setAction(ffdRequestType.getAction());
                }
                if (null != ffdRequestType.getObjId()) {
                    objFaultFilterDefVo.setDescObjId(ffdRequestType.getObjId());
                }
                if (null != ffdRequestType.getParameterObjId()) {
                    objFaultFilterDefVo.setParameterObjId(ffdRequestType.getParameterObjId());
                }
                arlVehicleCfgVOs.add(objFaultFilterDefVo);
            }
            status = configMaintenanceServiceIntf.saveFFDTemplate(arlVehicleCfgVOs);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * @Author:
     * @param:FRDRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for save FRD Template details
     */
    @POST
    @Path(OMDConstants.SAVE_FRD_TEMPLATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String saveFRDTemplate(FRDRequestType objFRDRequestType) throws RMDServiceException {
        String status = OMDConstants.FAILURE;
        List<FaultRangeDefVo> arlVehicleCfgVOs = new ArrayList<FaultRangeDefVo>();
        List<FRDRequestType> arlFRDRequestType = objFRDRequestType.getArlFRDRequestType();
        FaultRangeDefVo objFaultRangeDefVo = null;
        try {
            for (FRDRequestType frdRequestType : arlFRDRequestType) {
                objFaultRangeDefVo = new FaultRangeDefVo();
                if (null != frdRequestType.getConfigId()) {
                    objFaultRangeDefVo.setConfigId(frdRequestType.getConfigId());
                }
                if (null != frdRequestType.getConfigValue()) {
                    objFaultRangeDefVo.setConfigValue(frdRequestType.getConfigValue());
                }
                if (null != frdRequestType.getEdpTemplate()) {
                    objFaultRangeDefVo.setEdpTemplate(frdRequestType.getEdpTemplate());
                }
                if (null != frdRequestType.getFaultCodeFrom()) {
                    objFaultRangeDefVo.setFaultCodeFrom(frdRequestType.getFaultCodeFrom());
                }
                if (null != frdRequestType.getFaultCodeTo()) {
                    objFaultRangeDefVo.setFaultCodeTo(frdRequestType.getFaultCodeTo());
                }
                if (null != frdRequestType.getFaultSource()) {
                    objFaultRangeDefVo.setFaultSource(frdRequestType.getFaultSource());
                }
                if (null != frdRequestType.getTemplate()) {
                    objFaultRangeDefVo.setTemplate(frdRequestType.getTemplate());
                }
                if (null != frdRequestType.getVersion()) {
                    objFaultRangeDefVo.setVersion(frdRequestType.getVersion());
                }
                if (null != frdRequestType.getStatus()) {
                    objFaultRangeDefVo.setStatus(frdRequestType.getStatus());
                }
                if (null != frdRequestType.getTitle()) {
                    objFaultRangeDefVo.setTitle(frdRequestType.getTitle());
                }
                if (null != frdRequestType.getSubIdFrom()) {
                    objFaultRangeDefVo.setSubIdFrom(frdRequestType.getSubIdFrom());
                }
                if (null != frdRequestType.getSubIdTo()) {
                    objFaultRangeDefVo.setSubIdTo(frdRequestType.getSubIdTo());
                }
                if (null != frdRequestType.getPreValue()) {
                    objFaultRangeDefVo.setPreValue(frdRequestType.getPreValue());
                }
                if (null != frdRequestType.getPostValue()) {
                    objFaultRangeDefVo.setPostValue(frdRequestType.getPostValue());
                }
                if (null != frdRequestType.getBiasValue()) {
                    objFaultRangeDefVo.setBiasValue(frdRequestType.getBiasValue());
                }
                if (null != frdRequestType.getUserName()) {
                    objFaultRangeDefVo.setUserName(frdRequestType.getUserName());
                }
                if (null != frdRequestType.getAction()) {
                    objFaultRangeDefVo.setAction(frdRequestType.getAction());
                }
                if (null != frdRequestType.getTempVersion()) {
                    objFaultRangeDefVo.setTempVersion(frdRequestType.getTempVersion());
                }
                if (null != frdRequestType.getCtrlSourceId()) {
                    objFaultRangeDefVo.setCtrlSourceId(frdRequestType.getCtrlSourceId());
                }
                if (null != frdRequestType.getFaultStart()) {
                    objFaultRangeDefVo.setFaultStart(frdRequestType.getFaultStart());
                }
                if (null != frdRequestType.getFaultEnd()) {
                    objFaultRangeDefVo.setFaultEnd(frdRequestType.getFaultEnd());
                }
                arlVehicleCfgVOs.add(objFaultRangeDefVo);
            }
            status = configMaintenanceServiceIntf.saveFRDTemplate(arlVehicleCfgVOs);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * @Author:
     * @param:ConfigRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for remove FFD Template details
     */
    @POST
    @Path(OMDConstants.REMOVE_FFD_TEMPLATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String removeFFDTemplate(ConfigRequestType objconfigRequestType) throws RMDServiceException {
        String status = OMDConstants.FAILURE;
        List<FaultDetailsVO> arlVehicleCfgVOs = new ArrayList<FaultDetailsVO>();
        List<ConfigRequestType> arlConfigRequestType = objconfigRequestType.getArlConfigRequestType();
        FaultDetailsVO objFaultDetailsVO = null;
        try {
            for (ConfigRequestType configRequestType : arlConfigRequestType) {
                objFaultDetailsVO = new FaultDetailsVO();
                if (null != configRequestType.getObjId()) {
                    objFaultDetailsVO.setObjId(configRequestType.getObjId());
                }

                arlVehicleCfgVOs.add(objFaultDetailsVO);
            }
            status = configMaintenanceServiceIntf.removeFFDTemplate(arlVehicleCfgVOs);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * @Author:
     * @param:ConfigRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for remove FRD Template details
     */
    @POST
    @Path(OMDConstants.REMOVE_FRD_TEMPLATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String removeFRDTemplate(ConfigRequestType objconfigRequestType) throws RMDServiceException {
        String status = OMDConstants.FAILURE;
        List<FaultDetailsVO> arlVehicleCfgVOs = new ArrayList<FaultDetailsVO>();
        List<ConfigRequestType> arlConfigRequestType = objconfigRequestType.getArlConfigRequestType();
        FaultDetailsVO objFaultDetailsVO = null;
        try {
            for (ConfigRequestType configRequestType : arlConfigRequestType) {
                objFaultDetailsVO = new FaultDetailsVO();
                if (null != configRequestType.getObjId()) {
                    objFaultDetailsVO.setObjId(configRequestType.getObjId());
                }
                arlVehicleCfgVOs.add(objFaultDetailsVO);
            }
            status = configMaintenanceServiceIntf.removeFRDTemplate(arlVehicleCfgVOs);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetch maximum version count.
     */
    @GET
    @Path(OMDConstants.GET_MAXIMUM_VERSION)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getMaximumVersion(@Context UriInfo ui) throws RMDServiceException {
        String valueList = null;
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        String configId = OMDConstants.EMPTY_STRING;
        String templateId = OMDConstants.EMPTY_STRING;
        String configFile = OMDConstants.EMPTY_STRING;
        try {
            LOG.debug("*****getMaximumVersion WEBSERVICE BEGIN****"
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
            }
            if (queryParams.containsKey(OMDConstants.TEMPLATE_ID)) {
                templateId = queryParams.getFirst(OMDConstants.TEMPLATE_ID);
            }
            if (queryParams.containsKey(OMDConstants.CONFIG_FILE)) {
                configFile = queryParams.getFirst(OMDConstants.CONFIG_FILE);
            }
            valueList = configMaintenanceServiceIntf.getMaximumVersion(configId, templateId, configFile);
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return valueList;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:List<FaultValueResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetch fault source details.
     */
    @GET
    @Path(OMDConstants.GET_FAULT_SOURCE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultValueResponseType> getFaultSource(@Context UriInfo ui) throws RMDServiceException {
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        String configId = OMDConstants.EMPTY_STRING;
        String configValue = OMDConstants.EMPTY_STRING;
        List<FaultValueVO> faultSource = new ArrayList<FaultValueVO>();
        List<FaultValueResponseType> lstfaultSource = new ArrayList<FaultValueResponseType>();
        FaultValueResponseType faultValueResponseType = null;
        try {
            LOG.debug("*****getFaultSource WEBSERVICE BEGIN****"
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
            }
            if (queryParams.containsKey(OMDConstants.CONFIG_VALUE)) {
                configValue = queryParams.getFirst(OMDConstants.CONFIG_VALUE);
            }
            faultSource = configMaintenanceServiceIntf.getFaultSource(configId, configValue);
            if (faultSource != null && !faultSource.isEmpty()) {
                lstfaultSource = new ArrayList<FaultValueResponseType>(faultSource.size());
            }
            for (FaultValueVO objFaultValueVO : faultSource) {
                faultValueResponseType = new FaultValueResponseType();
                faultValueResponseType.setFaultSource(objFaultValueVO.getFaultSource());
                faultValueResponseType.setObjId(objFaultValueVO.getObjId());
                faultValueResponseType.setControllerName(objFaultValueVO.getCfgDetailDesc());
                lstfaultSource.add(faultValueResponseType);
            }
            faultSource = null;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return lstfaultSource;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetch current template details.
     */
    @GET
    @Path(OMDConstants.GET_CURRENT_TEMPLATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getCurrentTemplate(@Context UriInfo ui) throws RMDServiceException {
        String valueList = null;
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        String configId = OMDConstants.EMPTY_STRING, configFile = OMDConstants.EMPTY_STRING;
        try {
            LOG.debug("*****getCurrentTemplate WEBSERVICE BEGIN****"
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
            }
            if (queryParams.containsKey(OMDConstants.CONFIG_FILE)) {
                configFile = queryParams.getFirst(OMDConstants.CONFIG_FILE);
            }
            valueList = configMaintenanceServiceIntf.getCurrentTemplate(configId, configFile);
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return valueList;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetch current status details.
     */
    @GET
    @Path(OMDConstants.GET_CURRENT_STATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getCurrentStatus(@Context UriInfo ui) throws RMDServiceException {
        String valueList = null;
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        String configId = OMDConstants.EMPTY_STRING;
        String templateId = OMDConstants.EMPTY_STRING;
        String versionId = OMDConstants.EMPTY_STRING;
        try {
            LOG.debug("*****getCurrentStatus WEBSERVICE BEGIN****"
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
            }
            if (queryParams.containsKey(OMDConstants.TEMPLATE_ID)) {
                templateId = queryParams.getFirst(OMDConstants.TEMPLATE_ID);
            }
            if (queryParams.containsKey(OMDConstants.VERSION_ID)) {
                versionId = queryParams.getFirst(OMDConstants.VERSION_ID);
            }
            valueList = configMaintenanceServiceIntf.getCurrentStatus(configId, templateId, versionId);
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return valueList;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetch title details.
     */
    @GET
    @Path(OMDConstants.GET_TITLE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getTitle(@Context UriInfo ui) throws RMDServiceException {
        String valueList = null;
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        String configId = OMDConstants.EMPTY_STRING;
        String templateId = OMDConstants.EMPTY_STRING;
        String versionId = OMDConstants.EMPTY_STRING;
        try {
            LOG.debug("*****getTitle WEBSERVICE BEGIN****"
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
            }
            if (queryParams.containsKey(OMDConstants.TEMPLATE_ID)) {
                templateId = queryParams.getFirst(OMDConstants.TEMPLATE_ID);
            }
            if (queryParams.containsKey(OMDConstants.VERSION_ID)) {
                versionId = queryParams.getFirst(OMDConstants.VERSION_ID);
            }
            valueList = configMaintenanceServiceIntf.getTitle(configId, templateId, versionId);
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return valueList;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:List<FaultValueResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetch status details.
     */
    @GET
    @Path(OMDConstants.GET_STATUS_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultValueResponseType> getStatusDetails(@Context UriInfo ui) throws RMDServiceException {
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        String configId = OMDConstants.EMPTY_STRING;
        String templateId = OMDConstants.EMPTY_STRING;
        String versionId = OMDConstants.EMPTY_STRING;
        List<FaultValueVO> faultSource = new ArrayList<FaultValueVO>();
        List<FaultValueResponseType> lstfaultSource = new ArrayList<FaultValueResponseType>();
        FaultValueResponseType faultValueResponseType = null;
        try {
            LOG.debug("*****getTitle WEBSERVICE BEGIN****"
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
                configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
            }
            if (queryParams.containsKey(OMDConstants.TEMPLATE_ID)) {
                templateId = queryParams.getFirst(OMDConstants.TEMPLATE_ID);
            }
            if (queryParams.containsKey(OMDConstants.VERSION_ID)) {
                versionId = queryParams.getFirst(OMDConstants.VERSION_ID);
            }
            faultSource = configMaintenanceServiceIntf.getStatusDetails(configId, templateId, versionId);
            if (faultSource != null && !faultSource.isEmpty()) {
                lstfaultSource = new ArrayList<FaultValueResponseType>(faultSource.size());
            }
            for (FaultValueVO objFaultValueVO : faultSource) {
                faultValueResponseType = new FaultValueResponseType();
                faultValueResponseType.setStatus(objFaultValueVO.getStatus());
                faultValueResponseType.setObjId(objFaultValueVO.getObjId());
                faultValueResponseType.setCfgDesc(objFaultValueVO.getCfgDesc());
                lstfaultSource.add(faultValueResponseType);
            }
            faultSource = null;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return lstfaultSource;
    }

    /**
     * @Author:
     * @param:FRDRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for check fault range details.
     */
    @POST
    @Path(OMDConstants.CHECK_FAULT_RANGE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String checkFaultRange(FRDRequestType objFRDRequestType) throws RMDServiceException {
        String status = OMDConstants.FAILURE;
        List<FaultRangeDefVo> arlVehicleCfgVOs = new ArrayList<FaultRangeDefVo>();
        List<FRDRequestType> arlConfigRequestType = objFRDRequestType.getArlFRDRequestType();
        FaultRangeDefVo objFaultRangeDefVo = null;
        try {
            for (FRDRequestType fRDRequestType : arlConfigRequestType) {
                objFaultRangeDefVo = new FaultRangeDefVo();
                if (null != fRDRequestType.getFaultSource()) {
                    objFaultRangeDefVo.setFaultSource(fRDRequestType.getFaultSource());
                }
                if (null != fRDRequestType.getFaultStart()) {
                    objFaultRangeDefVo.setFaultStart(fRDRequestType.getFaultStart());
                }
                if (null != fRDRequestType.getFaultEnd()) {
                    objFaultRangeDefVo.setFaultEnd(fRDRequestType.getFaultEnd());
                }
                if (null != fRDRequestType.getFaultCodeFrom()) {
                    objFaultRangeDefVo.setFaultCodeFrom(fRDRequestType.getFaultCodeFrom());
                }
                if (null != fRDRequestType.getFaultCodeTo()) {
                    objFaultRangeDefVo.setFaultCodeTo(fRDRequestType.getFaultCodeTo());
                }
                arlVehicleCfgVOs.add(objFaultRangeDefVo);
            }
            status = configMaintenanceServiceIntf.checkFaultRange(arlVehicleCfgVOs);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * @Author:
     * @param:@Context UriInfo
     *                     ui
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for get number of parameter allowed
     *               count.
     */
    @GET
    @Path(OMDConstants.GET_MAX_PARAMETER_COUNT)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getMaxParameterCount(@Context UriInfo ui) throws RMDServiceException {
        String maxParameterCount = null;
        try {
			LOG.debug("*****getMaxParameterCount WEBSERVICE BEGIN****"
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
      maxParameterCount = configMaintenanceServiceIntf.getMaxParameterCount();
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }

        return maxParameterCount;
    }
    /**
     * @Author:
     * @param:@Context UriInfo
     * @return:List<TemplateReportResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching Templates across all configs based on the selected search criteria.
     */
    @GET
    @Path(OMDConstants.GET_TEMPLATE_REPORT)
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<TemplateReportResponseType> getTemplateReport(@Context UriInfo ui)throws RMDServiceException{
    	List<TemplateReportResponseType> lstTemplateReportResponseTypes=null;
    	TemplateReportResponseType templateReportResponseType=null;
    	TemplateSearchVO templateSearchVO=null;
    	LOG.info("START /getTemplateReport Webservice");
    	try {
    		 final MultivaluedMap<String, String> queryParams =ui.getQueryParameters();
    		 templateSearchVO=new TemplateSearchVO();
    		 if(queryParams.containsKey(RMDCommonConstants.VEHICLE_NO)){    			 
    			templateSearchVO.setAssetNumber(queryParams.getFirst(RMDCommonConstants.VEHICLE_NO));
    			templateSearchVO.setVehiclesBasedSearch(true);
    		 }
    		 if(queryParams.containsKey(RMDCommonConstants.CUSTOMER_ID)){
    			 templateSearchVO.setCustomerId(queryParams.getFirst(RMDCommonConstants.CUSTOMER_ID));
    			 templateSearchVO.setVehiclesBasedSearch(true);
    		 }
    		 if(queryParams.containsKey(RMDCommonConstants.VEHICLE_HEADER)){
     			templateSearchVO.setAssetGroupName(queryParams.getFirst(RMDCommonConstants.VEHICLE_HEADER));
     			templateSearchVO.setVehiclesBasedSearch(true);
     		 }
    		 if(queryParams.containsKey(RMDCommonConstants.CONFIG_TYPE)){
      			templateSearchVO.setConfigFile(queryParams.getFirst(RMDCommonConstants.CONFIG_TYPE));
      			templateSearchVO.setConfigInvolved(true);
      		 }
    		 if(queryParams.containsKey(RMDCommonConstants.CONTROLLER_ID)){
       			templateSearchVO.setCtrlCfgObjId(queryParams.getFirst(RMDCommonConstants.CONTROLLER_ID));
       		 }
    		 if(queryParams.containsKey(RMDCommonConstants.TEMPLATE_NO)){
        			templateSearchVO.setTemplateNo(queryParams.getFirst(RMDCommonConstants.TEMPLATE_NO));
        			templateSearchVO.setTemplateInvolved(true);
        	}
    		 if(queryParams.containsKey(RMDCommonConstants.TEMPLATE)){
     			templateSearchVO.setTemplateName(queryParams.getFirst(RMDCommonConstants.TEMPLATE));
     			templateSearchVO.setTemplateInvolved(true);
    		 }
    		 if(queryParams.containsKey(RMDCommonConstants.VERSION_NO)){
      			templateSearchVO.setTemplateVersion(queryParams.getFirst(RMDCommonConstants.VERSION_NO));
      			templateSearchVO.setTemplateInvolved(true);
     		 }
    		 if(queryParams.containsKey(RMDCommonConstants.TEMPLATE_STATUS)){
       			templateSearchVO.setTemplateStatus(queryParams.getFirst(RMDCommonConstants.TEMPLATE_STATUS));
       			templateSearchVO.setTemplateInvolved(true);
      		 }
    		 
    		final List<TemplateInfoVO> lstTemplateInfoVO=configMaintenanceServiceIntf.getTemplateReport(templateSearchVO);
    		lstTemplateReportResponseTypes=new ArrayList<TemplateReportResponseType>();
    		for (final Iterator<TemplateInfoVO> iterator = lstTemplateInfoVO.iterator(); iterator
					.hasNext();) {
				final TemplateInfoVO templateInfoVO = iterator.next();
				templateReportResponseType=new TemplateReportResponseType();
				templateReportResponseType.setTemplateName(templateInfoVO.getTemplateDescription());
				templateReportResponseType.setTemplateDescription(templateInfoVO.getTemplateDescription());
				templateReportResponseType.setConfigType(templateInfoVO.getCfgFile());
				templateReportResponseType.setTemplateNumber(templateInfoVO.getTemplate());
				templateReportResponseType.setTemplateVersion(templateInfoVO.getVersion());
				templateReportResponseType.setTemplateStatus(templateInfoVO.getTemplateStatus());
				templateReportResponseType.setOffboardTemplateStatus(templateInfoVO.getOffboardStatus());
				templateReportResponseType.setOnboardTemplateStatus(templateInfoVO.getOnboardStatus());
				templateReportResponseType.setAssetNumber(templateInfoVO.getRoadNumber());
				templateReportResponseType.setAssetGroupName(templateInfoVO.getRoadNumberHeader());
				templateReportResponseType.setCustomerName(templateInfoVO.getCustomerName());
				templateReportResponseType.setCustomerID(templateInfoVO.getCustomerID());
				templateReportResponseType.setControllerObjid(templateInfoVO.getCtrlCfgObjId());
				templateReportResponseType.setControllerCfg(templateInfoVO.getControllerConfig());
				templateReportResponseType.setOnboardTemplateDate(templateInfoVO.getOnboardStatusDate());
				templateReportResponseType.setOffboardTemplateDate(templateInfoVO.getOffboardStatusDate());
				lstTemplateReportResponseTypes.add(templateReportResponseType);
			}
    		LOG.info("END /getTemplateReport Webservice");
		} catch (Exception e) {
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}
    	return lstTemplateReportResponseTypes;
    }
    
    /**
     * @Author:
     * @param:objAddEditEDPRequestType
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for creating or updating EDP templates
     */
    @POST
    @Path(OMDConstants.SAVE_RCI_TEMPLATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String saveRCITemplate(final TemplateRequestType templateRequestType) throws RMDServiceException {
        TemplateInfoVO templateInfo=null;
         String status = RMDCommonConstants.FAIL;
        try { 
            LOG.info("START /saveRCITemplate Webservice");
            if(null!=templateRequestType){
                templateInfo=new TemplateInfoVO();
                if(null!=templateRequestType.getConfigType()){
                    templateInfo.setCfgFile(templateRequestType.getConfigType());
                }
                if (null != templateRequestType.getTemplateDescription()) {
                    templateInfo.setTemplateDescription(ESAPI.encoder().decodeForHTML(
                                    templateRequestType
                                            .getTemplateDescription()));
                }
                if(null!=templateRequestType.getTemplateStatus()){
                    templateInfo.setTemplateStatus(templateRequestType.getTemplateStatus());
                }
                if(null!=templateRequestType.getTemplateVersion()){
                    templateInfo.setVersion(templateRequestType.getTemplateVersion());
                }
                if (null != templateRequestType.getTemplateFileContent()) {
                    templateInfo.setTemplateFileContent(ESAPI.encoder().decodeForHTML(
                                    templateRequestType
                                            .getTemplateFileContent()));
                }
                if(null!=templateRequestType.getTemplateFileName()){
                    templateInfo.setTemplateFileName(templateRequestType.getTemplateFileName());
                }
                if(null!=templateRequestType.getFaultCode()){
                    templateInfo.setFaultCode(templateRequestType.getFaultCode());
                }
                if(null!=templateRequestType.getTemplateNumber()){
                    templateInfo.setTemplate(templateRequestType.getTemplateNumber());
                }
                if(null!=templateRequestType.getUserName()){
                    templateInfo.setUserName(templateRequestType.getUserName());
                }
                status=configMaintenanceServiceIntf.saveRCITemplate(templateInfo);              
            }  
            
            LOG.info("End /saveRCITemplate Webservice");
        } catch (Exception e) {
             RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return status;
    }
    
}

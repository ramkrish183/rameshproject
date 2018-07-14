package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.asset.bo.intf.ConfigMaintenanceBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.ConfigMaintenanceDAOIntf;
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
import com.ge.trans.eoa.services.common.constants.QueryConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class ConfigMaintenanceBOImpl implements ConfigMaintenanceBOIntf {

    private ConfigMaintenanceDAOIntf objConfigMaintenanceDAOIntf;

    public ConfigMaintenanceBOImpl(ConfigMaintenanceDAOIntf objConfigMaintenanceDAOIntf) {
        this.objConfigMaintenanceDAOIntf = objConfigMaintenanceDAOIntf;
    }

    /**
     * @Author:
     * @param:
     * @return:List<CtrlCfgVO>
     * @throws:RMDBOException
     * @Description: This method is used for fetching the all controller configs
     */
    @Override
	public List<CtrlCfgVO> getControllerConfigs() throws RMDBOException {
        List<CtrlCfgVO> ctrlCfgVOList = null;
        try {
            ctrlCfgVOList = objConfigMaintenanceDAOIntf.getControllerConfigs();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return ctrlCfgVOList;
    }

    /**
     * @Author:
     * @param:ctrlCfgObjId,cfgFileName
     * @return:List<ConfigTemplateDetailsVO>
     * @throws:RMDBOException
     * @Description: This method is used to get the Templates for the selected
     *               Controller Config and Config File.
     */
    @Override
	public List<ConfigTemplateDetailsVO> getCtrlCfgTemplates(String ctrlCfgObjId, String cfgFileName)
            throws RMDBOException {
        List<ConfigTemplateDetailsVO> cfgTemplateDetailsVOList = null;
        try {
            cfgTemplateDetailsVOList = objConfigMaintenanceDAOIntf.getCtrlCfgTemplates(ctrlCfgObjId, cfgFileName);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return cfgTemplateDetailsVOList;
    }

    /**
     * @Author:
     * @param:
     * @return:List<EFIDetailsVO>
     * @throws:RMDBOException
     * @Description: This method is used for fetching the existing EFI templates
     */
    @Override
	public List<EFIDetailsVO> getEFIDetails() throws RMDBOException {
        List<EFIDetailsVO> efiDetailsVOList = null;
        try {
            efiDetailsVOList = objConfigMaintenanceDAOIntf.getEFIDetails();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return efiDetailsVOList;
    }

    /**
     * @Author:
     * @param userName
     * @return String
     * @throws RMDBOException
     * @Description This method is used to create new EFI template
     */
    @Override
	public String createNewEFI(String userName) throws RMDBOException {
        String result = null;
        try {
            result = objConfigMaintenanceDAOIntf.createNewEFI(userName);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return result;
    }

    /**
     * @Author:
     * @param ctrlCfgObjId
     *            ,cfgFileName
     * @return String
     * @throws RMDBOException
     * @Description This method is used for fetching the latest template Number
     */
    @Override
	public String getMaxTemplateNumber(String ctrlCfgObjId, String cfgFileName) throws RMDBOException {
        String maxTempNo = null;
        try {
            maxTempNo = objConfigMaintenanceDAOIntf.getMaxTemplateNumber(ctrlCfgObjId, cfgFileName);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return maxTempNo;
    }

    /**
     * @Author:
     * @param ctrlCfgObjId
     *            ,cfgFileName,templateNo
     * @return String
     * @throws RMDBOException
     * @Description This method is used for fetching the latest version number
     *              of selected template
     */
    @Override
	public String getTempMaxVerNumber(String ctrlCfgObjId, String cfgFileName, String templateNo)
            throws RMDBOException {
        String tempMaxVerNo = null;
        try {
            tempMaxVerNo = objConfigMaintenanceDAOIntf.getTempMaxVerNumber(ctrlCfgObjId, cfgFileName, templateNo);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return tempMaxVerNo;
    }

    /**
     * @Author:
     * @param objEDPSearchParamVo
     * @return List<EDPParamDetailsVO>
     * @throws RMDBOException
     * @Description This method is used to get the parameters that can be added
     *              for EDP templates.
     */
    @Override
	public List<EDPParamDetailsVO> getEDPParameters(EDPSearchParamVO objEDPSearchParamVo) throws RMDBOException {
        List<EDPParamDetailsVO> edpParamDetailsList = null;
        try {
            edpParamDetailsList = objConfigMaintenanceDAOIntf.getEDPParameters(objEDPSearchParamVo);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return edpParamDetailsList;
    }

    /**
     * @Author:
     * @param:tempObjId
     * @return:List<EDPParamDetailsVO>
     * @throws:RMDBOException
     * @Description: This method is used for fetching the opened template added
     *               parameters
     */
    @Override
	public List<EDPParamDetailsVO> getAddedEDPParameters(String tempObjId) throws RMDBOException {
        List<EDPParamDetailsVO> addedEDPParamDetailsList = null;
        try {
            addedEDPParamDetailsList = objConfigMaintenanceDAOIntf.getAddedEDPParameters(tempObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return addedEDPParamDetailsList;
    }

    /**
     * @Author:
     * @param:objAddEditEDPDetailsVO
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for creating or updating EDP templates
     */
    @Override
	public String saveEDPTemplate(AddEditEDPDetailsVO objAddEditEDPDetailsVO) throws RMDBOException {
        String result = null;
        try {
            result = objConfigMaintenanceDAOIntf.saveEDPTemplate(objAddEditEDPDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author:
     * @param:String cfgFileName,
     *                   String templateNo, String versionNo,String ctrlCfgObjId
     * @return:EDPHeaderDetailsVO
     * @throws:RMDBOException
     * @Description: This method is used for getting the Next/Previous EDP
     *               template templates
     */
    @Override
	public EDPHeaderDetailsVO getPreNextEDPDetails(String cfgFileName, String templateNo, String versionNo,
            String ctrlCfgObjId) throws RMDBOException {
        EDPHeaderDetailsVO objEDPHeaderDetailsVO = null;
        try {
            objEDPHeaderDetailsVO = objConfigMaintenanceDAOIntf.getPreNextEDPDetails(cfgFileName, templateNo, versionNo,
                    ctrlCfgObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return objEDPHeaderDetailsVO;
    }

    @Override
    public List<FaultValueVO> getParameterTitle(String configId) throws RMDBOException {
        List<FaultValueVO> getParameterTitle = null;
        try {
            getParameterTitle = objConfigMaintenanceDAOIntf.getParameterTitle(configId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return getParameterTitle;
    }

    @Override
    public List<FaultValueVO> getEDPTemplate(String configId) throws RMDBOException {
        List<FaultValueVO> getEDPTemplate = null;
        try {
            getEDPTemplate = objConfigMaintenanceDAOIntf.getEDPTemplate(configId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return getEDPTemplate;
    }

    @Override
    public List<FaultValueVO> getDefaultValuesRange() throws RMDBOException {
        List<FaultValueVO> getDefaultValuesRange = null;
        try {
            getDefaultValuesRange = objConfigMaintenanceDAOIntf.getDefaultValuesRange();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return getDefaultValuesRange;
    }

    @Override
    public List<FaultFilterDefVo> populateFFDDetails(FaultDetailsVO faultDetailsVO) throws RMDBOException {
        List<FaultFilterDefVo> populateFFDDetails = null;
        try {
            populateFFDDetails = objConfigMaintenanceDAOIntf.populateFFDDetails(faultDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return populateFFDDetails;
    }

    @Override
    public List<FaultRangeDefVo> populateFRDDetails(FaultDetailsVO faultDetailsVO) throws RMDBOException {
        List<FaultRangeDefVo> populateFRDDetails = null;
        try {
            populateFRDDetails = objConfigMaintenanceDAOIntf.populateFRDDetails(faultDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return populateFRDDetails;
    }

    @Override
    public String saveFFDTemplate(List<FaultFilterDefVo> lstFaultFilterDefVo) throws RMDBOException {
        String status = null;
        try {
            status = objConfigMaintenanceDAOIntf.saveFFDTemplate(lstFaultFilterDefVo);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String saveFRDTemplate(List<FaultRangeDefVo> lstFaultRangeDefVo) throws RMDBOException {
        String status = null;
        try {
            status = objConfigMaintenanceDAOIntf.saveFRDTemplate(lstFaultRangeDefVo);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String removeFFDTemplate(List<FaultDetailsVO> lstFaultDetailsVO) throws RMDBOException {
        String status = null;
        try {
            status = objConfigMaintenanceDAOIntf.removeFFDTemplate(lstFaultDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String removeFRDTemplate(List<FaultDetailsVO> lstFaultDetailsVO) throws RMDBOException {
        String status = null;
        try {
            status = objConfigMaintenanceDAOIntf.removeFRDTemplate(lstFaultDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String getMaximumVersion(String configId, String templateId, String configFile) throws RMDBOException {
        String status = null;
        try {
            status = objConfigMaintenanceDAOIntf.getMaximumVersion(configId, templateId, configFile);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public List<FaultValueVO> getFaultSource(String configId, String configValue) throws RMDBOException {
        List<FaultValueVO> getFaultSource = null;
        try {
            getFaultSource = objConfigMaintenanceDAOIntf.getFaultSource(configId, configValue);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return getFaultSource;
    }

    @Override
    public String getCurrentTemplate(String configId, String configFile) throws RMDBOException {
        String status = null;
        try {
            status = objConfigMaintenanceDAOIntf.getCurrentTemplate(configId, configFile);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String getCurrentStatus(String configId, String templateId, String versionId) throws RMDBOException {
        String status = null;
        try {
            status = objConfigMaintenanceDAOIntf.getCurrentStatus(configId, templateId, versionId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String getTitle(String configId, String templateId, String versionId) throws RMDBOException {
        String title = null;
        try {
            title = objConfigMaintenanceDAOIntf.getTitle(configId, templateId, versionId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return title;
    }

    @Override
    public List<FaultValueVO> getStatusDetails(String configId, String templateId, String versionId)
            throws RMDBOException {
        List<FaultValueVO> getStatusDetails = null;
        try {
            getStatusDetails = objConfigMaintenanceDAOIntf.getStatusDetails(configId, templateId, versionId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return getStatusDetails;
    }

    @Override
    public String checkFaultRange(List<FaultRangeDefVo> lstFaultFilterDefVo) throws RMDBOException {
        String status = null;
        try {
            status = objConfigMaintenanceDAOIntf.checkFaultRange(lstFaultFilterDefVo);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String getMaxParameterCount() throws RMDBOException {
        String getParameterCount = null;
        try {
            getParameterCount = objConfigMaintenanceDAOIntf.getMaxParameterCount();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return getParameterCount;
    }

    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for get the list of Templates associated with a unit.
     */
	@Override
	public List<TemplateInfoVO> getTemplateReport(
			final TemplateSearchVO templateSearchVO) throws RMDBOException {
		List<TemplateInfoVO> lstTemplateDetails = null;
		String configFileName = null;
		boolean isconfigfile = false;
		StringBuilder templateQueryBuffer=null;
		try {
			if (templateSearchVO.getConfigFile() != null) {
				isconfigfile = QueryConstants.LIST_CONFIG_FILES
						.contains(templateSearchVO.getConfigFile()) ? true
						: false;
				configFileName = isconfigfile ? templateSearchVO
						.getConfigFile() : null;
			}
			templateQueryBuffer=prepareQueryForTemplateReport(
					configFileName,
					templateSearchVO);
			lstTemplateDetails = objConfigMaintenanceDAOIntf.getTemplateReport(
					templateQueryBuffer.toString(), templateSearchVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		}
		catch(Exception ex){
			throw new RMDBOException(ex.getMessage());
		}
		return lstTemplateDetails;
	}
    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDBOException
     * @Description: Dynamically preparing the Query based on the search criteria from Template Report UI.
     * 				
     */
	private StringBuilder prepareQueryForTemplateReport(final String configFileName,
			final TemplateSearchVO templateSearchVO ) {
		Map<String, String> mapTemplateList = QueryConstants.TEMPLATE_QUERY_MAP;
		StringBuilder templateQueryBuffer = new StringBuilder();
		templateQueryBuffer.append(QueryConstants.TEMPLATE_REPORT_SELECT_QUERY);
		
		if(null == configFileName){
			final Iterator<Map.Entry<String, String>> it = mapTemplateList.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				queryHelper(templateQueryBuffer, entry.getValue(),
						templateSearchVO);
				if (it.hasNext())
					templateQueryBuffer.append(RMDCommonConstants.SPACE_UNION_SPACE);
			}
		}
		else{
			queryHelper(templateQueryBuffer,
					mapTemplateList.get(configFileName), templateSearchVO);
		}
		
		templateQueryBuffer.append(RMDCommonConstants.CLOSE_BRACKET);
		templateQueryBuffer.append(formTemplateSearchClause(templateSearchVO));
		templateQueryBuffer
				.append(QueryConstants.TEMPLATE_REPORT_ORDER_BY_CLAUSE);
		return templateQueryBuffer;
	}

	/**
	 * @param templateSearchVO
	 * @param templateSearchQuery
	 */
	private String formTemplateSearchClause(
			final TemplateSearchVO templateSearchVO) {
		String templateSearchQuery=RMDCommonConstants.EMPTY_STRING;
		if(templateSearchVO.isTemplateInvolved()){
			if(null!=templateSearchVO.getTemplateName()){
				templateSearchQuery=QueryConstants.TEMPLATE_NAME_SEARCH;
			}
			if(null!=templateSearchVO.getTemplateNo()){
				templateSearchQuery=RMDCommonUtility.isNullOrEmpty(templateSearchQuery)?QueryConstants.TEMPLATE_ID_SEARCH:templateSearchQuery+RMDCommonConstants.SPACE_AND_SPACE+QueryConstants.TEMPLATE_ID_SEARCH;
			}
			
			if(null!=templateSearchVO.getTemplateVersion()){
				templateSearchQuery=RMDCommonUtility.isNullOrEmpty(templateSearchQuery)?QueryConstants.TEMPLATE_VERSION_SEARCH:templateSearchQuery+RMDCommonConstants.SPACE_AND_SPACE+QueryConstants.TEMPLATE_VERSION_SEARCH;
			}
			
			if(null!=templateSearchVO.getTemplateStatus()){
				templateSearchQuery=RMDCommonUtility.isNullOrEmpty(templateSearchQuery)?QueryConstants.TEMPLATE_STATUS_SEARCH:templateSearchQuery+RMDCommonConstants.SPACE_AND_SPACE+QueryConstants.TEMPLATE_STATUS_SEARCH;
			}
			templateSearchQuery=QueryConstants.WHERE_CLAUSE+templateSearchQuery;
		}
		return templateSearchQuery;
	}
	 /**
     * @Description: Private method, helps to form the query based on the search values.
     * 				This method is created to do the common activities performed in the prepareQueryForTemplateReport()
     */
	private void queryHelper(StringBuilder templateQueryBuffer,
			String query,  final TemplateSearchVO templateSearchVO) {
		templateQueryBuffer.append(query);
		if(null!=templateSearchVO.getCtrlCfgObjId()){			
			templateQueryBuffer.append(RMDCommonConstants.SPACE_AND_SPACE);
			templateQueryBuffer
			.append(QueryConstants.CONTROLLER_CONFIG_SEARCH);
		}
		if(templateSearchVO.isVehiclesBasedSearch()){
			if(null!=templateSearchVO.getAssetNumber()){
				templateQueryBuffer.append(RMDCommonConstants.SPACE_AND_SPACE);
				templateQueryBuffer.append(QueryConstants.VEHICLE_NO_SEARCH);
			}
			if(null!=templateSearchVO.getAssetGroupName()){
				templateQueryBuffer.append(RMDCommonConstants.SPACE_AND_SPACE);
				templateQueryBuffer.append(QueryConstants.VEHICLE_HEADER_SEARCH);				
			}
			if(null!=templateSearchVO.getCustomerId()){
				templateQueryBuffer.append(RMDCommonConstants.SPACE_AND_SPACE);
				templateQueryBuffer.append(QueryConstants.ORG_ID_SEARCH);
			}				
		}
	}

	@Override
    public String checkIfRCITemplateExists(TemplateInfoVO templateInfo) throws RMDBOException {
        return objConfigMaintenanceDAOIntf.checkIfRCITemplateExists(templateInfo);
    }
    
	

	@Override
	public String saveRCITemplate(TemplateInfoVO templateInfo) 
			throws RMDBOException {
		String status=RMDCommonConstants.SUCCESS;
		try {
			status=objConfigMaintenanceDAOIntf.checkIfRCITemplateExists(templateInfo);			
			if(RMDCommonConstants.SUCCESS.equalsIgnoreCase(status)){
			    status=	objConfigMaintenanceDAOIntf.saveRCITemplate(templateInfo);
			}						
		} catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
		return status;
	}

}

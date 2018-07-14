package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.ConfigMaintenanceBOIntf;
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
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class ConfigMaintenanceServiceImpl implements ConfigMaintenanceServiceIntf {

    private ConfigMaintenanceBOIntf objConfigMaintenanceBOIntf;

    public ConfigMaintenanceServiceImpl(ConfigMaintenanceBOIntf objConfigMaintenanceBOIntf) {
        this.objConfigMaintenanceBOIntf = objConfigMaintenanceBOIntf;
    }

    /**
     * @Author:
     * @param:
     * @return:List<CtrlCfgVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the all controller configs
     */
	@Override
	public List<CtrlCfgVO> getControllerConfigs() throws RMDServiceException {
        List<CtrlCfgVO> ctrlCfgVOList = null;
        try {
            ctrlCfgVOList = objConfigMaintenanceBOIntf.getControllerConfigs();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return ctrlCfgVOList;
    }

    /**
     * @Author:
     * @param:ctrlCfgObjId,cfgFileName
     * @return:List<ConfigTemplateDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used to get the Templates for the selected
     *               Controller Config and Config File.
     */
    @Override
	public List<ConfigTemplateDetailsVO> getCtrlCfgTemplates(String ctrlCfgObjId, String cfgFileName)
            throws RMDServiceException {
        List<ConfigTemplateDetailsVO> cfgTemplateDetailsVOList = null;
        try {
            cfgTemplateDetailsVOList = objConfigMaintenanceBOIntf.getCtrlCfgTemplates(ctrlCfgObjId, cfgFileName);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return cfgTemplateDetailsVOList;
    }

    /**
     * @Author:
     * @param:
     * @return:List<EFIDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    @Override
	public List<EFIDetailsVO> getEFIDetails() throws RMDServiceException {
        List<EFIDetailsVO> efiDetailsVOList = null;
        try {
            efiDetailsVOList = objConfigMaintenanceBOIntf.getEFIDetails();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return efiDetailsVOList;
    }

    /**
     * @Author:
     * @param userName
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to create new EFI template
     */
    @Override
	public String createNewEFI(String userName) throws RMDServiceException {
        String result = null;
        try {
            result = objConfigMaintenanceBOIntf.createNewEFI(userName);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author:
     * @param ctrlCfgObjId
     *            ,cfgFileName
     * @return String
     * @throws RMDServiceException
     * @Description This method is used for fetching the latest template Number
     */
    @Override
	public String getMaxTemplateNumber(String ctrlCfgObjId, String cfgFileName) throws RMDServiceException {
        String maxTempNo = null;
        try {
            maxTempNo = objConfigMaintenanceBOIntf.getMaxTemplateNumber(ctrlCfgObjId, cfgFileName);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return maxTempNo;
    }

    /**
     * @Author:
     * @param ctrlCfgObjId
     *            ,cfgFileName,templateNo
     * @return String
     * @throws RMDServiceException
     * @Description This method is used for fetching the latest version number
     *              of selected template
     */
    @Override
	public String getTempMaxVerNumber(String ctrlCfgObjId, String cfgFileName, String templateNo)
            throws RMDServiceException {
        String tempMaxVerNo = null;
        try {
            tempMaxVerNo = objConfigMaintenanceBOIntf.getTempMaxVerNumber(ctrlCfgObjId, cfgFileName, templateNo);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return tempMaxVerNo;
    }

    /**
     * @Author:
     * @param objEDPSearchParamVo
     * @return List<EDPParamDetailsVO>
     * @throws RMDServiceException
     * @Description This method is used to get the parameters that can be added
     *              for EDP templates.
     */
    @Override
	public List<EDPParamDetailsVO> getEDPParameters(EDPSearchParamVO objEDPSearchParamVo) throws RMDServiceException {
        List<EDPParamDetailsVO> edpParamDetailsList = null;
        try {
            edpParamDetailsList = objConfigMaintenanceBOIntf.getEDPParameters(objEDPSearchParamVo);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return edpParamDetailsList;
    }

    /**
     * @Author:
     * @param:tempObjId
     * @return:List<EDPParamDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the opened template added
     *               parameters
     */
    @Override
	public List<EDPParamDetailsVO> getAddedEDPParameters(String tempObjId) throws RMDServiceException {
        List<EDPParamDetailsVO> addedEDPParamDetailsList = null;
        try {
            addedEDPParamDetailsList = objConfigMaintenanceBOIntf.getAddedEDPParameters(tempObjId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return addedEDPParamDetailsList;
    }

    /**
     * @Author:
     * @param:objAddEditEDPDetailsVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for creating or updating EDP templates
     */
    @Override
	public String saveEDPTemplate(AddEditEDPDetailsVO objAddEditEDPDetailsVO) throws RMDServiceException {
        String result = null;
        try {
            result = objConfigMaintenanceBOIntf.saveEDPTemplate(objAddEditEDPDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author:
     * @param:String cfgFileName,
     *                   String templateNo, String versionNo,String ctrlCfgObjId
     * @return:EDPHeaderDetailsVO
     * @throws:RMDServiceException
     * @Description: This method is used for getting the Next/Previous EDP
     *               template templates
     */
    @Override
	public EDPHeaderDetailsVO getPreNextEDPDetails(String cfgFileName, String templateNo, String versionNo,
            String ctrlCfgObjId) throws RMDServiceException {
        EDPHeaderDetailsVO objEDPHeaderDetailsVO = null;
        try {
            objEDPHeaderDetailsVO = objConfigMaintenanceBOIntf.getPreNextEDPDetails(cfgFileName, templateNo, versionNo,
                    ctrlCfgObjId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objEDPHeaderDetailsVO;
    }

    @Override
    public List<FaultValueVO> getParameterTitle(String configId) throws RMDServiceException {
        List<FaultValueVO> getParameterTitle = null;
        try {
            getParameterTitle = objConfigMaintenanceBOIntf.getParameterTitle(configId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return getParameterTitle;
    }

    @Override
    public List<FaultValueVO> getEDPTemplate(String configId) throws RMDServiceException {
        List<FaultValueVO> getEDPTemplate = null;
        try {
            getEDPTemplate = objConfigMaintenanceBOIntf.getEDPTemplate(configId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return getEDPTemplate;
    }

    @Override
    public List<FaultValueVO> getDefaultValuesRange() throws RMDServiceException {
        List<FaultValueVO> getDefaultValuesRange = null;
        try {
            getDefaultValuesRange = objConfigMaintenanceBOIntf.getDefaultValuesRange();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return getDefaultValuesRange;
    }

    @Override
    public List<FaultFilterDefVo> populateFFDDetails(FaultDetailsVO faultDetailsVO) throws RMDServiceException {
        List<FaultFilterDefVo> populateFFDDetails = null;
        try {
            populateFFDDetails = objConfigMaintenanceBOIntf.populateFFDDetails(faultDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return populateFFDDetails;
    }

    @Override
    public List<FaultRangeDefVo> populateFRDDetails(FaultDetailsVO faultDetailsVO) throws RMDServiceException {
        List<FaultRangeDefVo> populateFRDDetails = null;
        try {
            populateFRDDetails = objConfigMaintenanceBOIntf.populateFRDDetails(faultDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return populateFRDDetails;
    }

    @Override
    public String saveFFDTemplate(List<FaultFilterDefVo> lstFaultFilterDefVo) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigMaintenanceBOIntf.saveFFDTemplate(lstFaultFilterDefVo);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String saveFRDTemplate(List<FaultRangeDefVo> lstFaultRangeDefVo) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigMaintenanceBOIntf.saveFRDTemplate(lstFaultRangeDefVo);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String removeFFDTemplate(List<FaultDetailsVO> lstFaultDetailsVO) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigMaintenanceBOIntf.removeFFDTemplate(lstFaultDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String removeFRDTemplate(List<FaultDetailsVO> lstFaultDetailsVO) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigMaintenanceBOIntf.removeFRDTemplate(lstFaultDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String getMaximumVersion(String configId, String templateId, String configFile) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigMaintenanceBOIntf.getMaximumVersion(configId, templateId, configFile);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public List<FaultValueVO> getFaultSource(String configId, String configValue) throws RMDServiceException {
        List<FaultValueVO> getFaultSource = null;
        try {
            getFaultSource = objConfigMaintenanceBOIntf.getFaultSource(configId, configValue);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return getFaultSource;
    }

    @Override
    public String getCurrentTemplate(String configId, String configFile) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigMaintenanceBOIntf.getCurrentTemplate(configId, configFile);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String getCurrentStatus(String configId, String templateId, String versionId) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigMaintenanceBOIntf.getCurrentStatus(configId, templateId, versionId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String getTitle(String configId, String templateId, String versionId) throws RMDServiceException {
        String title = null;
        try {
            title = objConfigMaintenanceBOIntf.getTitle(configId, templateId, versionId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return title;
    }

    @Override
    public List<FaultValueVO> getStatusDetails(String configId, String templateId, String versionId)
            throws RMDServiceException {
        List<FaultValueVO> getFaultSource = null;
        try {
            getFaultSource = objConfigMaintenanceBOIntf.getStatusDetails(configId, templateId, versionId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return getFaultSource;
    }

    @Override
    public String checkFaultRange(List<FaultRangeDefVo> lstFaultFilterDefVo) throws RMDServiceException {
        String status = null;
        try {
            status = objConfigMaintenanceBOIntf.checkFaultRange(lstFaultFilterDefVo);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return status;
    }

    @Override
    public String getMaxParameterCount() throws RMDServiceException {
        String getParameterCount = null;
        try {
            getParameterCount = objConfigMaintenanceBOIntf.getMaxParameterCount();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return getParameterCount;
    }

    /**
     * @Author:
     * @param:
     * @return:List<TemplateInfoVO>
     * @throws:RMDServiceException
     * @Description: This method is used for get the list of Templates associated with a unit.
     */
	@Override
	public List<TemplateInfoVO> getTemplateReport(final TemplateSearchVO templateSearchVO) throws RMDServiceException {
		List<TemplateInfoVO> lstTemplateDetails=null;
		try {
			lstTemplateDetails=objConfigMaintenanceBOIntf.getTemplateReport(templateSearchVO);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(),e);			
		}
		return lstTemplateDetails;
	}


	@Override
    public String saveRCITemplate(final TemplateInfoVO templateInfo)    
            throws RMDServiceException {
        String success=RMDCommonConstants.FAIL;
        try {
            success=objConfigMaintenanceBOIntf.saveRCITemplate(templateInfo);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(),e);    
        }
        return success;
    }
	
	

}

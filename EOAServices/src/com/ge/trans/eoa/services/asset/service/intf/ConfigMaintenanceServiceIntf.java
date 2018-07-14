package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;

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
import com.ge.trans.rmd.exception.RMDServiceException;

public interface ConfigMaintenanceServiceIntf {

    /**
     * @Author:
     * @param:
     * @return:List<CtrlCfgResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the all controller configs
     */
    public List<CtrlCfgVO> getControllerConfigs() throws RMDServiceException;

    /**
     * @Author:
     * @param:
     * @return:List<CtrlCfgVO>
     * @throws:RMDServiceException
     * @Description: This method is used to get the Templates for the selected
     *               Controller Config and Config File.
     */
    public List<ConfigTemplateDetailsVO> getCtrlCfgTemplates(String ctrlCfgObjId, String cfgFileName)
            throws RMDServiceException;

    /**
     * @Author:
     * @param:
     * @return:List<EFIDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    public List<EFIDetailsVO> getEFIDetails() throws RMDServiceException;

    /**
     * @Author:
     * @param
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to create new EFI template
     */
    public String createNewEFI(String userName) throws RMDServiceException;

    /**
     * @Author:
     * @param ctrlCfgObjId
     *            ,cfgFileName
     * @return String
     * @throws RMDServiceException
     * @Description This method is used for fetching the latest template Number
     */
    public String getMaxTemplateNumber(String ctrlCfgObjId, String cfgFileName) throws RMDServiceException;

    /**
     * @Author:
     * @param ctrlCfgObjId
     *            ,cfgFileName,templateNo
     * @return String
     * @throws RMDServiceException
     * @Description This method is used for fetching the latest version number
     *              of selected template
     */
    public String getTempMaxVerNumber(String ctrlCfgObjId, String cfgFileName, String templateNo)
            throws RMDServiceException;

    /**
     * @Author:
     * @param objEDPSearchParamVo
     * @return List<EDPParamDetailsVO>
     * @throws RMDServiceException
     * @Description This method is used to get the parameters that can be added
     *              for EDP templates.
     */
    public List<EDPParamDetailsVO> getEDPParameters(EDPSearchParamVO objEDPSearchParamVo) throws RMDServiceException;

    /**
     * @Author:
     * @param:tempObjId
     * @return:List<EDPParamDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the opened template added
     *               parameters
     */
    public List<EDPParamDetailsVO> getAddedEDPParameters(String tempObjId) throws RMDServiceException;

    /**
     * @Author:
     * @param:objAddEditEDPDetailsVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for creating or updating EDP templates
     */
    public String saveEDPTemplate(AddEditEDPDetailsVO objAddEditEDPDetailsVO) throws RMDServiceException;

    /**
     * @Author:
     * @param:String cfgFileName,
     *                   String templateNo, String versionNo,String ctrlCfgObjId
     * @return:EDPHeaderDetailsVO
     * @throws:RMDServiceException
     * @Description: This method is used for getting the Next/Previous EDP
     *               template templates
     */
    public EDPHeaderDetailsVO getPreNextEDPDetails(String cfgFileName, String templateNo, String versionNo,
            String ctrlCfgObjId) throws RMDServiceException;

    /**
     * @Author:
     * @param:String configId
     * @return:List<FaultValueVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the parameter title
     */
    public List<FaultValueVO> getParameterTitle(String configId) throws RMDServiceException;

    /**
     * @Author:
     * @param:String configId
     * @return:List<FaultValueVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the EDP templates
     */
    public List<FaultValueVO> getEDPTemplate(String configId) throws RMDServiceException;

    /**
     * @Author:
     * @param:
     * @return:List<FaultValueVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching existing pre,post,bias
     *               values.
     */
    public List<FaultValueVO> getDefaultValuesRange() throws RMDServiceException;

    /**
     * @Author:
     * @param:FaultDetailsVO
     * @return:List<FaultFilterDefVo>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the FFD Template details.
     */
    public List<FaultFilterDefVo> populateFFDDetails(FaultDetailsVO faultDetailsVO) throws RMDServiceException;

    /**
     * @Author:
     * @param:FaultDetailsVO
     * @return:List<FaultRangeDefVo>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the FRD Template details.
     */
    public List<FaultRangeDefVo> populateFRDDetails(FaultDetailsVO faultDetailsVO) throws RMDServiceException;

    /**
     * @Author:
     * @param:List<FaultFilterDefVo>
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for save the FFD Template details.
     */
    public String saveFFDTemplate(List<FaultFilterDefVo> lstFaultFilterDefVo) throws RMDServiceException;

    /**
     * @Author:
     * @param:List<FaultRangeDefVo>
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for save the FRD Template details.
     */
    public String saveFRDTemplate(List<FaultRangeDefVo> lstFaultFilterDefVo) throws RMDServiceException;

    /**
     * @Author:
     * @param:String
     * @return:List<FaultDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used for remove the FFD templates
     */
    public String removeFFDTemplate(List<FaultDetailsVO> lstFaultDetailsVO) throws RMDServiceException;

    /**
     * @Author:
     * @param:String
     * @return:List<FaultDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used for remove the FRD templates
     */
    public String removeFRDTemplate(List<FaultDetailsVO> lstFaultDetailsVO) throws RMDServiceException;

    /**
     * @Author:
     * @param:String configId,String
     *                   templateId,String configFile
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching maximum template version.
     */
    public String getMaximumVersion(String configId, String templateId, String configFile) throws RMDServiceException;

    /**
     * @Author:
     * @param:String configId,String
     *                   templateId
     * @return:List<FaultValueVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching fault source
     */
    public List<FaultValueVO> getFaultSource(String configId, String templateId) throws RMDServiceException;

    /**
     * @Author:
     * @param:String configId,String
     *                   configFile
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    public String getCurrentTemplate(String configId, String configFile) throws RMDServiceException;

    /**
     * @Author:
     * @param:String configId,String
     *                   templateId,String versionId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    public String getCurrentStatus(String configId, String templateId, String versionId) throws RMDServiceException;

    /**
     * @Author:
     * @param:String configId,String
     *                   templateId,String versionId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    public String getTitle(String configId, String templateId, String versionId) throws RMDServiceException;

    /**
     * @Author:
     * @param:String configId,String
     *                   templateId,String versionId
     * @return:List<FaultValueVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    public List<FaultValueVO> getStatusDetails(String configId, String templateId, String versionId)
            throws RMDServiceException;

    /**
     * @Author:
     * @param:List<FaultRangeDefVo>
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    public String checkFaultRange(List<FaultRangeDefVo> lstFaultFilterDefVo) throws RMDServiceException;

    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the existing EFI templates
     */
    public String getMaxParameterCount() throws RMDServiceException;
    
    
    /**
     * @Author:
     * @param:
     * @return:List<TemplateInfoVO>
     * @throws:RMDServiceException
     * @Description: This method is used for get the list of Templates associated with a unit.
     */
    public List<TemplateInfoVO> getTemplateReport(final TemplateSearchVO templateSearchVO) throws RMDServiceException; 
    
  
    
    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is save new RCI Template
     */    
    public String saveRCITemplate(final TemplateInfoVO templateInfo) throws RMDServiceException;    

}

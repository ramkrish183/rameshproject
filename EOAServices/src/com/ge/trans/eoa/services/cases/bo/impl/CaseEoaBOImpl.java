package com.ge.trans.eoa.services.cases.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CaseTypeEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CloseCaseVO;
import com.ge.trans.eoa.services.cases.bo.intf.CaseEoaBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.CaseEoaDAOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.FaultLogDAOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.VehicleFaultEoaDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.AcceptCaseEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CMPrivilegeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseAppendServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseConvertionVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseMergeServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseMgmtUsersDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseRepairCodeEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseScoreRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CasesHeaderVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CloseOutRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ControllerModelVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CustomerFdbkVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderDetailVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderGroupVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultRequestVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.HistoyVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MassApplyRxVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MaterialUsageVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ReCloseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecommDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxStatusHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ScoreRxEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SolutionBean;
import com.ge.trans.eoa.services.cases.service.valueobjects.SortOrderLkBackDaysVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputActEntryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleConfigVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleFaultEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewCaseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewLogVO;
import com.ge.trans.eoa.services.common.constants.FaultLogConstants;
import com.ge.trans.eoa.services.common.constants.FaultLogHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultMobileDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultMobileServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetMobileToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.MobileToolDsParmGroupServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.ToolDsParmGroupServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDNullObjectException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.omi.exception.DataException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class CaseEoaBOImpl implements CaseEoaBOIntf {
    private CaseEoaDAOIntf objCaseEoaDAOIntf;
    private VehicleFaultEoaDAOIntf objVehFltDAOIntf;
    private FaultLogDAOIntf objFaultLogDAO;
    private String strDpEabConst="DP/EAB";

    public static final RMDLogger LOG = RMDLogger.getLogger(CaseEoaBOImpl.class);

    public CaseEoaBOImpl(CaseEoaDAOIntf objCaseEoaDAOIntf, VehicleFaultEoaDAOIntf objVehFltDAOIntf,
            FaultLogDAOIntf objFaultLogDAO) {
        super();
        this.objCaseEoaDAOIntf = objCaseEoaDAOIntf;
        this.objVehFltDAOIntf = objVehFltDAOIntf;
        this.objFaultLogDAO = objFaultLogDAO;
    }

    /**
     * @return the objVehFltDAOIntf
     */
    public VehicleFaultEoaDAOIntf getObjVehicleFaultDAOIntf() {
        return objVehFltDAOIntf;
    }

    /**
     * @param objVehFltDAOIntf
     *            the objVehFltDAOIntf to set
     */
    public void setObjVehicleFaultDAOIntf(VehicleFaultEoaDAOIntf objVehFltDAOIntf) {
        this.objVehFltDAOIntf = objVehFltDAOIntf;
    }

    /*
     * (non-Javadoc) This method is used for calling ABSFaultLogDAO and
     * FaultLogDAO class. This code is copied from EOA for EOA Datascreen Story.
     * The steps followed in this method are taken from FaultLogEntry file of
     * EOA code.
     */
    @Override
    public FaultDataDetailsServiceVO getFaultDetails(VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDBOException, RMDNullObjectException {
    	List<String> unitConversionRoleList = objCaseEoaDAOIntf.getUnitConversionRoles();
        FaultRequestVO objFaultRequestVO = new FaultRequestVO();
        String strControllerCfg = null;
        GetToolDsParminfoServiceVO objFaultHeaderDetailVOClone = null;
        ToolDsParmGroupServiceVO parmGroupInfo = null;
        ArrayList<ToolDsParmGroupServiceVO> arlHeaderDet = new ArrayList<ToolDsParmGroupServiceVO>();
        ArrayList<GetToolDsParminfoServiceVO> faultDataList = new ArrayList<GetToolDsParminfoServiceVO>();
        ArrayList<ToolDsParmGroupServiceVO> faultGroupList = new ArrayList<ToolDsParmGroupServiceVO>();
        FaultServiceVO objFaultServiceVO = null;
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        GetToolDsParminfoServiceVO objParminfoServiceVO;
        GetToolDsParminfoServiceVO objParminfoServiceVOClone;
        ArrayList<GetToolDsParminfoServiceVO> finalHeaderList = new ArrayList<GetToolDsParminfoServiceVO>();
        final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat(RMDServiceConstants.DSDATE_FORMAT);
        String ctrlCfg = null;
        String partStatus = null;
        String caseDefSortOrder = null;
        String caseDefLookDays = null;

        if (objVehicleFaultServiceVO.getCustomerId() != null
                && !(("").equals(objVehicleFaultServiceVO.getCustomerId().trim()))) {
            objFaultRequestVO.setCustomer(objVehicleFaultServiceVO.getCustomerId()); // CustID
        }
        if (objVehicleFaultServiceVO.getAssetGrpName() != null
                && !(("").equals(objVehicleFaultServiceVO.getAssetGrpName().trim()))) {
            objFaultRequestVO.setVehicleHeader(objVehicleFaultServiceVO.getAssetGrpName()); // Road
                                                                                            // Initial
        }
        if (objVehicleFaultServiceVO.getStrRoadNo() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrRoadNo().trim()))) {
            objFaultRequestVO.setSerialNo(objVehicleFaultServiceVO.getStrRoadNo()); // Road
                                                                                    // No
        }
        if (objVehicleFaultServiceVO.getStrDays() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrDays().trim()))
                && objVehicleFaultServiceVO.getStrJDPADRadio() == null) {
            objFaultRequestVO.setNoOfDays(objVehicleFaultServiceVO.getStrDays()); // No
                                                                                  // of
                                                                                  // Days
        } else if (objVehicleFaultServiceVO.getFromDate() != null && objVehicleFaultServiceVO.getToDate() != null
                && objVehicleFaultServiceVO.getStrJDPADRadio() == null) {
            objFaultRequestVO.setVvfStartDate(SDF_FORMAT.format(objVehicleFaultServiceVO.getFromDate())); // From
                                                                                                          // Date
            objFaultRequestVO.setVvfEndDate(SDF_FORMAT.format(objVehicleFaultServiceVO.getToDate())); // To
                                                                                                      // Date
        }
        if (objVehicleFaultServiceVO.getStrAllRecords() != null
                && !("").equals(objVehicleFaultServiceVO.getStrAllRecords().trim())) {
            objFaultRequestVO.setViewAll(objVehicleFaultServiceVO.getStrAllRecords()); // View
                                                                                       // ALL
                                                                                       // Records
        }
        if (objVehicleFaultServiceVO.isBlnHealtCheck()) {
            objFaultRequestVO.setHC("YES"); // Health Check
        }
        if (objVehicleFaultServiceVO.getCaseId() != null
                && !(("").equals(objVehicleFaultServiceVO.getCaseId().trim()))) {
            objFaultRequestVO.setCaseId(objVehicleFaultServiceVO.getCaseId()); // Case
                                                                               // ID
        }
        if (objVehicleFaultServiceVO.getStrCaseType() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrCaseType().trim()))) {
            objFaultRequestVO.setCaseType(objVehicleFaultServiceVO.getStrCaseType()); // Case
                                                                                      // Type
        }
        if (objVehicleFaultServiceVO.getStrSortOption() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrSortOption().trim()))) {
            objFaultRequestVO.setSortOrder(objVehicleFaultServiceVO.getStrSortOption()); // Sort
                                                                                         // Order
        } else if (objFaultRequestVO.getCaseId() == null) {
            objFaultRequestVO.setSortOrder(FaultLogConstants.STR_OCCUR_DATE);
        }
        if (objVehicleFaultServiceVO.getIntStartPos() != 0) {
            int pageNo = objVehicleFaultServiceVO.getIntStartPos() / FaultLogConstants.INT_DEF_PAGINATION_COUNTER;
            objFaultRequestVO.setPaginationHit(String.valueOf(pageNo)); // Page
                                                                        // No
        }
        if (objVehicleFaultServiceVO.getStrScreen() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrScreen().trim()))) {
            objFaultRequestVO.setScreen(objVehicleFaultServiceVO.getStrScreen().trim()); // Screen
        }
        if (objFaultRequestVO.getCaseId() != null && !(("").equals(objFaultRequestVO.getCaseId().trim()))) {
            if (objVehicleFaultServiceVO.isInitLoad()) {
                objFaultRequestVO.setInitialLoad("Y");
            }
            if (objVehicleFaultServiceVO.getStrCaseFrom() == null) {
                objFaultRequestVO.setCaseFrom("NOW");
            } else {
                objFaultRequestVO.setCaseFrom(objVehicleFaultServiceVO.getStrCaseFrom()); // Case
                                                                                          // From
            }
            if (("Oil Anomaly").equals(objFaultRequestVO.getCaseType())) {
                objFaultRequestVO.setScreen("CASE-OIL");
            } else if (("QNX Equipment").equals(objFaultRequestVO.getCaseType())) {
                objFaultRequestVO.setScreen("CASE-QNX");
            } else if (("DHMS Problem").equals(objFaultRequestVO.getCaseType())) {
                objFaultRequestVO.setScreen("CASE-DHMS");
            } else {
                objFaultRequestVO.setScreen("CASE-REM");
            }
            if (objVehicleFaultServiceVO.getStrRuleDefId() != null
                    && !(("").equals(objVehicleFaultServiceVO.getStrRuleDefId().trim()))) {
                objFaultRequestVO.setRuleDefID(objVehicleFaultServiceVO.getStrRuleDefId()); // Rule
                                                                                            // definition
                                                                                            // ID
            }
            if (objVehicleFaultServiceVO.getStrJDPADRadio() != null
                    && !(("").equals(objVehicleFaultServiceVO.getStrJDPADRadio().trim()))) {
                objFaultRequestVO.setJdpadCbrCrit(objVehicleFaultServiceVO.getStrJDPADRadio()); // Rule
                                                                                                // Type
                objFaultRequestVO.setCaseFrom(null);
            }
        }
        try {
            ControllerModelVO ctrlModelVO = objFaultLogDAO.getControllerSrcAndModel(objFaultRequestVO.getCustomer(),
                    objFaultRequestVO.getVehicleHeader(), objFaultRequestVO.getSerialNo());
            ctrlCfg = ctrlModelVO.getControllerCfg();
            if (("EQUIP").equals(objFaultRequestVO.getScreen()) || ("RR-EQUIP").equals(objFaultRequestVO.getScreen())) {
                if (ctrlModelVO.getPartStatus() != null
                        && ("QNX INSTALLED").equalsIgnoreCase(ctrlModelVO.getPartStatus())) {
                    partStatus = "QNX";
                    objFaultRequestVO.setScreen(objFaultRequestVO.getScreen() + "-QNX");
                } else if (ctrlModelVO.getPartStatus() != null
                        && ("INSTALLED/GOOD").equalsIgnoreCase(ctrlModelVO.getPartStatus())) {
                    partStatus = "NT";
                    objFaultRequestVO.setScreen(objFaultRequestVO.getScreen() + "-NT");
                } else {
                    partStatus = "NONE";
                    objFaultRequestVO.setScreen(objFaultRequestVO.getScreen() + "-QNX");
                }
            }
            objFaultRequestVO.setStrViewName(ctrlModelVO.getDsViewName());
            objFaultRequestVO.setVehicleID(ctrlModelVO.getVehicleObjid());

            if (objFaultRequestVO.getCaseId() != null && !(("").equals(objFaultRequestVO.getCaseId().trim()))) {
                SortOrderLkBackDaysVO sortOrderVO = null;
                String sortOrderKey = null;
                if (("CASE-DHMS").equals(objFaultRequestVO.getScreen())) {
                	sortOrderKey = objFaultRequestVO.getCaseType() + "_" + FaultLogConstants.STR_DHMS_CTRL_SRC_ID + "_" + ctrlModelVO.getModelName();
                    sortOrderVO = objFaultLogDAO.getSortOrderLookbackDays().get(sortOrderKey);
                } else {
                	sortOrderKey = objFaultRequestVO.getCaseType() + "_" + ctrlModelVO.getControllerSrcId() + "_" + ctrlModelVO.getModelName();
                    sortOrderVO = objFaultLogDAO.getSortOrderLookbackDays().get(sortOrderKey);
                }
                if ((objFaultRequestVO.getNoOfDays() == null || ("").equals(objFaultRequestVO.getNoOfDays().trim()))
                        && objVehicleFaultServiceVO.getStrJDPADRadio() == null) {
                    if (sortOrderVO != null) {
                        objFaultRequestVO.setNoOfDays(sortOrderVO.getDefaultLookBack());
                        caseDefLookDays = sortOrderVO.getDefaultLookBack();
                        objFaultRequestVO.setSortOrder(sortOrderVO.getDefaultSortOrder());
                        caseDefSortOrder = sortOrderVO.getDefaultSortOrder();
                    } else {
                    	LOG.error("Entry not available in GETS_SD_CASETYPE_TO_DEFLKB table for CASE_PROBLEM_TYPE, CONTROLLER_SOURCE_ID and MODEL : " +sortOrderKey);
                        String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DATA_SCREEN_MISSING_CONFIGURATION);
                        throw new RMDBOException(errorCode, new String[] {},
                                RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), new RMDBOException(),
                                RMDCommonConstants.MINOR_ERROR);
                    }
                }
                if ((objFaultRequestVO.getViewAll() == null || ("N").equals(objFaultRequestVO.getViewAll().trim()))
                        && objFaultRequestVO.getPaginationHit() == null) {
                    objFaultRequestVO.setPaginationHit("0"); // Default
                                                             // Pagination Hit
                }
                if (objVehicleFaultServiceVO.isNotch8()) {
                    objFaultRequestVO.setNotch("8");
                }
            }
            boolean isCustColEnabled = false;
            if(objVehicleFaultServiceVO.isEnableCustomColumns() && !"GMT".equals(objVehicleFaultServiceVO.getUserTimeZoneCode())){
            	isCustColEnabled = true;
            }
            
            FaultHeaderVO fixedFaultHeaderVO = objFaultLogDAO.getHeaderDetails(isCustColEnabled).get("Fixed-" + ctrlModelVO.getModelObjId());
            FaultHeaderVO nonFixedFaultHeaderVO = objFaultLogDAO.getHeaderDetails(isCustColEnabled).get(ctrlModelVO.getModelObjId());
            FaultHeaderVO faultHeaderVO = new FaultHeaderVO();
            faultHeaderVO.setFaultGroups(new ArrayList());
            if (fixedFaultHeaderVO != null && !fixedFaultHeaderVO.getFaultGroups().isEmpty()) {
                faultHeaderVO.setControllerDesc(fixedFaultHeaderVO.getControllerDesc());
                faultHeaderVO.setControllerId(fixedFaultHeaderVO.getControllerId());
                faultHeaderVO.setModel(fixedFaultHeaderVO.getModel());
                faultHeaderVO.getFaultGroups().addAll(fixedFaultHeaderVO.getFaultGroups());
            }
            if (nonFixedFaultHeaderVO != null && !nonFixedFaultHeaderVO.getFaultGroups().isEmpty()) {
                faultHeaderVO.setControllerDesc(nonFixedFaultHeaderVO.getControllerDesc());
                strControllerCfg = nonFixedFaultHeaderVO.getControllerDesc();
                faultHeaderVO.setControllerId(nonFixedFaultHeaderVO.getControllerId());
                faultHeaderVO.setModel(nonFixedFaultHeaderVO.getModel());
                faultHeaderVO.getFaultGroups().addAll(nonFixedFaultHeaderVO.getFaultGroups());
            }
            
            /*Modified by Murali C to get the customer specific tool tips  */ 
            HashMap<String,String> toolTipMap = null;
            HashMap<String,String> hdrMap = null;
            HashMap<String,String> formatMap = null;
            List<String> arlParamInfoObjid = null;
            if (((RMDCommonConstants.VEHICLE_UPPER).equals(objFaultRequestVO.getScreen())
                            || (RMDCommonConstants.EQUIP_NT).equals(objFaultRequestVO.getScreen())) && !objVehicleFaultServiceVO.isMobileRequest()) {
                arlParamInfoObjid = new ArrayList<String>();
                toolTipMap = objFaultLogDAO.getCustLevelDetails().get(RMDCommonConstants.CUST_TOOLTIP_MAP)
                        .get(objFaultRequestVO.getCustomer() + "_" + ctrlModelVO.getModelObjId());
                hdrMap = objFaultLogDAO.getCustLevelDetails().get(RMDCommonConstants.CUST_HEADER_MAP)
                        .get(objFaultRequestVO.getCustomer() + "_" + ctrlModelVO.getModelObjId());
                formatMap = objFaultLogDAO.getCustLevelDetails().get(RMDCommonConstants.CUST_FILTER_MAP)
                        .get(objFaultRequestVO.getCustomer() + "_" + ctrlModelVO.getModelObjId());
                if(null !=toolTipMap && !toolTipMap.isEmpty()){
                    arlParamInfoObjid .addAll(toolTipMap.keySet());
                }
            }
            /*End of customer specific tool tips */
            
            boolean isUCApplied = "ARZN".equals(objVehicleFaultServiceVO.getUserCustomer()) &&
        	unitConversionRoleList.contains(objVehicleFaultServiceVO.getRoleName()) && formatMap != null;
            
            
            if (objVehicleFaultServiceVO.isMobileRequest()) {
                arlParamInfoObjid = objFaultLogDAO.getCustLevelMobileDetails()
                        .get(objFaultRequestVO.getCustomer() + "_" + ctrlModelVO.getModelObjId());
            }
            
            if ((RMDCommonConstants.VEHICLE_UPPER).equals(objFaultRequestVO.getScreen()) && objVehicleFaultServiceVO.isHideL3Faults()) {
                objFaultRequestVO.setHideL3("Y");
            }
            if ((strDpEabConst).equals(objFaultRequestVO.getScreen())) {
                strControllerCfg = null;
                faultHeaderVO = objFaultLogDAO.getDPEABHeaders(isCustColEnabled);
            }
            if ((("DHMS").equals(objFaultRequestVO.getScreen()) || ("CASE-DHMS").equals(objFaultRequestVO.getScreen()))) {
                strControllerCfg = null;
                faultHeaderVO = objFaultLogDAO.getDHMSHeaderDetails(isCustColEnabled);
            }
            if (("CASE-OIL").equals(objFaultRequestVO.getScreen())) {
                FaultHeaderVO oilFixedHeaderVO = objFaultLogDAO.getOilHeaderDetails(isCustColEnabled).get("FixedParameters");
                FaultHeaderVO oilCustHeaderVO = objFaultLogDAO.getOilHeaderDetails(isCustColEnabled).get(objFaultRequestVO.getCustomer());
                faultHeaderVO = new FaultHeaderVO();
                faultHeaderVO.setFaultGroups(new ArrayList());
                if (oilFixedHeaderVO != null && !oilFixedHeaderVO.getFaultGroups().isEmpty()) {
                    faultHeaderVO.getFaultGroups().addAll(oilFixedHeaderVO.getFaultGroups());
                }
                if (oilCustHeaderVO != null && !oilCustHeaderVO.getFaultGroups().isEmpty()) {
                    faultHeaderVO.getFaultGroups().addAll(oilCustHeaderVO.getFaultGroups());
                }
            }

            if (faultHeaderVO != null) {
                ArrayList<FaultHeaderGroupVO> headerGrpList = faultHeaderVO.getFaultGroups();
                if (headerGrpList != null && !headerGrpList.isEmpty()) {
                    for (int i = 0; i < headerGrpList.size(); i++) {
                        ArrayList<FaultHeaderDetailVO> faultDetailsList = headerGrpList.get(i).getFaultHeaderDetails();
                        if (faultDetailsList != null && !faultDetailsList.isEmpty()) {
                            int noOfCols = 0;
                            for (int j = 0; j < faultDetailsList.size(); j++) {
                                FaultHeaderDetailVO faultDetailsVO = faultDetailsList.get(j);
                                if (objVehicleFaultServiceVO.isLimitedParam() && arlParamInfoObjid != null && !arlParamInfoObjid.isEmpty()
                                        && !arlParamInfoObjid.contains(faultDetailsVO.getParmObjid())
                                        && ((RMDCommonConstants.VEHICLE_UPPER).equals(objFaultRequestVO.getScreen())
                                                || (RMDCommonConstants.EQUIP_NT).equals(objFaultRequestVO.getScreen()))) {
                                    if ((faultDetailsList.size() - 1) == j && noOfCols > 0) {
                                        parmGroupInfo = new ToolDsParmGroupServiceVO();
                                        parmGroupInfo.setParamGroup(headerGrpList.get(i).getGroupName());
                                        if (objFaultHeaderDetailVOClone.getStyle() == null) {
                                            parmGroupInfo.setStyle("fixedheaders");
                                        } else {
                                            parmGroupInfo.setStyle(objFaultHeaderDetailVOClone.getStyle().trim().toLowerCase());
                                        }
                                        parmGroupInfo.setColspan(String.valueOf(noOfCols));
                                        arlHeaderDet.add(parmGroupInfo);
                                        faultGroupList.add(parmGroupInfo);
                                    }
                                    continue;
                                }
                                String colAvail = "";
                                if ((RMDCommonConstants.VEHICLE_UPPER).equals(objFaultRequestVO.getScreen()) || (RMDCommonConstants.EQUIP_NT).equals(objFaultRequestVO.getScreen()) 
                                		|| (strDpEabConst).equals(objFaultRequestVO.getScreen())) {
                                    colAvail = faultDetailsVO.getVvfFlag();
                                } else if (("EQUIP-QNX").equals(objFaultRequestVO.getScreen()) || ("RR-EQUIP-QNX").equals(objFaultRequestVO.getScreen()) 
                                		|| ("CASE-QNX").equals(objFaultRequestVO.getScreen())) {
                                    colAvail = faultDetailsVO.getQnxColAvail();
                                } else if (("RR-VEHICLE").equals(objFaultRequestVO.getScreen()) || ("RR-EQUIP-NT").equals(objFaultRequestVO.getScreen())) {
                                    colAvail = faultDetailsVO.getRapidResponseFlag();
                                } else if (("INCIDENT").equals(objFaultRequestVO.getScreen())) {
                                    colAvail = faultDetailsVO.getIncidentFlag();
                                } else if (("DHMS").equals(objFaultRequestVO.getScreen()) || ("CASE-DHMS").equals(objFaultRequestVO.getScreen())) {
                                    colAvail = faultDetailsVO.getStrDhmsColAvail();
                                } else if (("CASE-OIL").equals(objFaultRequestVO.getScreen())) {
                                    colAvail = faultDetailsVO.getInternalColAvail();
                                } else if (("CASE-REM").equals(objFaultRequestVO.getScreen())) {
                                    colAvail = faultDetailsVO.getDataScreenFlag();
                                } 
                                if (colAvail != null && ("Y").equalsIgnoreCase(colAvail)) {
                                    noOfCols++;
                                    objFaultHeaderDetailVOClone = new GetToolDsParminfoServiceVO();

                                    objFaultHeaderDetailVOClone.setParmObjid(faultDetailsVO.getParmObjid());
                                    objFaultHeaderDetailVOClone.setCustID(faultDetailsVO.getStrCustID());
                                    objFaultHeaderDetailVOClone.setDBColumnName(faultDetailsVO.getDBColumnName());
                                    objFaultHeaderDetailVOClone.setExternalColAvail(faultDetailsVO.getExternalColAvail());
                                    objFaultHeaderDetailVOClone.setInternalColAvail(faultDetailsVO.getInternalColAvail());
                                    objFaultHeaderDetailVOClone.setHeaderHtml(faultDetailsVO.getHeaderDesc());
                                    /*Modified by Murali C to show different tool tips for customers */ 
                                    if (((RMDCommonConstants.VEHICLE_UPPER).equals(objFaultRequestVO.getScreen())
                                                    || (RMDCommonConstants.EQUIP_NT).equals(objFaultRequestVO.getScreen())) 
                                                    && !objVehicleFaultServiceVO.isMobileRequest()) {
                                        if (null != arlParamInfoObjid && !arlParamInfoObjid.isEmpty()
                                                && arlParamInfoObjid.contains(faultDetailsVO.getParmObjid())) {
                                            if(RMDCommonUtility.isNullOrEmpty(faultDetailsVO.getToolTip()) || objVehicleFaultServiceVO.isLimitedParam())
                                                objFaultHeaderDetailVOClone.setToolTip(toolTipMap.get(faultDetailsVO.getParmObjid()));
                                            else
                                                objFaultHeaderDetailVOClone.setToolTip(faultDetailsVO.getToolTip());
                                            
                                            if(objVehicleFaultServiceVO.isLimitedParam() && null!=hdrMap.get(faultDetailsVO.getParmObjid())){
                                                    objFaultHeaderDetailVOClone.setHeaderHtml(hdrMap.get(faultDetailsVO.getParmObjid()));
                                            }

                                        }
                                    }else{
                                        objFaultHeaderDetailVOClone.setToolTip(faultDetailsVO.getToolTip());
                                    }
                                    /*End of show different tool tips for customers */
                                    objFaultHeaderDetailVOClone.setColumnName(faultDetailsVO.getColumnName());
                                    objFaultHeaderDetailVOClone.setParamNumber(faultDetailsVO.getParamNumber());
                                    objFaultHeaderDetailVOClone.setHeaderWidth(faultDetailsVO.getHeaderWidth());
                                    objFaultHeaderDetailVOClone.setCharLength(faultDetailsVO.getCharLength());
                                    if (faultDetailsVO.getUpperBound() == null) {
                                    	objFaultHeaderDetailVOClone.setUpperBound(Double.valueOf("0.0"));
                                    } else {
                                    	objFaultHeaderDetailVOClone.setUpperBound(Double.parseDouble(faultDetailsVO.getUpperBound()));
                                    }
                                    if (faultDetailsVO.getLowerBound() == null) {
                                    	objFaultHeaderDetailVOClone.setLowerBound(Double.valueOf("0.0"));
                                    } else {
                                    	objFaultHeaderDetailVOClone.setLowerBound(Double.parseDouble(faultDetailsVO.getLowerBound()));
                                    }
                                    objFaultHeaderDetailVOClone.setDataTooltipFlag(faultDetailsVO.getDataToolTipFlag());
                                    if(isUCApplied){
                                		String formatValue = formatMap.get(faultDetailsVO.getParmObjid());
                                		if(formatValue == null || formatValue.trim().equals("")){
                                			objFaultHeaderDetailVOClone.setFormat(faultDetailsVO.getFormat());
                                		}else{
                                			objFaultHeaderDetailVOClone.setFormat(formatValue);
                                		}
                                	}else{
                                		objFaultHeaderDetailVOClone.setFormat(faultDetailsVO.getFormat());
                                	}
                                    
                                    objFaultHeaderDetailVOClone.setStyle(faultDetailsVO.getStyle());
                                    objFaultHeaderDetailVOClone.setDataScreenFlag(faultDetailsVO.getDataScreenFlag());
                                    objFaultHeaderDetailVOClone.setVvfFlag(faultDetailsVO.getVvfFlag());
                                    objFaultHeaderDetailVOClone.setRapidResponseFlag(faultDetailsVO.getRapidResponseFlag());
                                    objFaultHeaderDetailVOClone.setIncidentFlag(faultDetailsVO.getIncidentFlag());
                                    objFaultHeaderDetailVOClone.setQnxColAvail(faultDetailsVO.getQnxColAvail());
                                    objFaultHeaderDetailVOClone.setAnomInd(faultDetailsVO.getAnomInd());
                                    objFaultHeaderDetailVOClone.setParmName(faultDetailsVO.getParmName());
                                    faultDataList.add(objFaultHeaderDetailVOClone);

                                    if ((faultDetailsList.size() - 1) == j && noOfCols > 0) {
                                        parmGroupInfo = new ToolDsParmGroupServiceVO();
                                        parmGroupInfo.setParamGroup(headerGrpList.get(i).getGroupName());
                                        if (objFaultHeaderDetailVOClone.getStyle() == null) {
                                            parmGroupInfo.setStyle("fixedheaders");
                                        } else {
                                            parmGroupInfo.setStyle(objFaultHeaderDetailVOClone.getStyle().trim().toLowerCase());
                                        }
                                        parmGroupInfo.setColspan(String.valueOf(noOfCols));
                                        arlHeaderDet.add(parmGroupInfo);
                                        faultGroupList.add(parmGroupInfo);
                                    }
                                } else if ((faultDetailsList.size() - 1) == j && noOfCols > 0) {
                                    parmGroupInfo = new ToolDsParmGroupServiceVO();
                                    parmGroupInfo.setParamGroup(headerGrpList.get(i).getGroupName());

                                    if (objFaultHeaderDetailVOClone.getStyle() == null) {
                                        parmGroupInfo.setStyle("fixedheaders");
                                    } else {
                                        parmGroupInfo.setStyle(objFaultHeaderDetailVOClone.getStyle().trim().toLowerCase());
                                    }
                                    parmGroupInfo.setColspan(String.valueOf(noOfCols));
                                    arlHeaderDet.add(parmGroupInfo);
                                    faultGroupList.add(parmGroupInfo);
                                }
                            }
                        }
                    }
                }
            }
            if (strControllerCfg != null && !("").equals(strControllerCfg.trim())) {
                objFaultRequestVO.setCtrlCfg(strControllerCfg);
            }
            objFaultRequestVO.setUserTimeZoneCode(objVehicleFaultServiceVO.getUserTimeZoneCode());
            objFaultRequestVO.setUserTimeZone(objVehicleFaultServiceVO.getUserTimeZone());
            objFaultServiceVO = objFaultLogDAO.getFaultData(objFaultRequestVO, faultDataList, strControllerCfg);
            objFaultDataDetailsServiceVO = new FaultDataDetailsServiceVO();
            objFaultDataDetailsServiceVO.setObjFaultServiceVO(objFaultServiceVO);
            if (partStatus != null) {
                objFaultDataDetailsServiceVO.setPartStatus(partStatus);
            }
            if (ctrlCfg != null) {
                objFaultDataDetailsServiceVO.setCtrlCfg(ctrlCfg);
            }
            if (caseDefSortOrder != null) {
                objFaultDataDetailsServiceVO.setCaseSortOrder(caseDefSortOrder);
            }
            if (caseDefLookDays != null) {
                objFaultDataDetailsServiceVO.setCaseLookBackDays(caseDefLookDays);
            }
            if (objFaultServiceVO.getTotalRecords() > 0) {
                objFaultDataDetailsServiceVO.setRecordsTotal(String.valueOf(objFaultServiceVO.getTotalRecords()));
            }
            Map<String, String> filterList = objFaultLogDAO.getDataScreenFilters();
            for (int i = 0; i < faultDataList.size(); i++) {
                objParminfoServiceVO = faultDataList.get(i);
                objParminfoServiceVOClone = new GetToolDsParminfoServiceVO();

                objParminfoServiceVOClone.setColumnName(objParminfoServiceVO.getColumnName());
                if (objParminfoServiceVO.getColumnName() != null
                        && filterList.get(objParminfoServiceVO.getColumnName()) != null) {
                    objParminfoServiceVOClone.setFilter(filterList.get(objParminfoServiceVO.getColumnName()));
                }
                if("OCCUR_DATE_CUSTOM".equals(objParminfoServiceVO.getColumnName()) || "FAULT_RESET_DATE_CUSTOM".equals(objParminfoServiceVO.getColumnName())){
                	objParminfoServiceVOClone.setHeaderHtml(objParminfoServiceVO.getHeaderHtml()+"("+objVehicleFaultServiceVO.getUserTimeZoneCode()+")");
                }else{
                	objParminfoServiceVOClone.setHeaderHtml(objParminfoServiceVO.getHeaderHtml());
                }
                if (objParminfoServiceVO.getStyle() != null && !("").equals(objParminfoServiceVO.getStyle().trim())) {
                    objParminfoServiceVOClone.setStyle(objParminfoServiceVO.getStyle().trim().toLowerCase());
                }
                objParminfoServiceVOClone.setInfo(objParminfoServiceVO.getInfo());
                objParminfoServiceVOClone.setLowerBound(objParminfoServiceVO.getLowerBound());
                objParminfoServiceVOClone.setUpperBound(objParminfoServiceVO.getUpperBound());
                objParminfoServiceVOClone.setDataTooltipFlag(objParminfoServiceVO.getDataTooltipFlag());
                objParminfoServiceVOClone.setToolTip(objParminfoServiceVO.getToolTip());
                objParminfoServiceVOClone.setHeaderWidth(objParminfoServiceVO.getHeaderWidth());
                objParminfoServiceVOClone.setCharLength(objParminfoServiceVO.getCharLength());
                objParminfoServiceVOClone.setDummyHeaderHtml(objParminfoServiceVO.getDummyHeaderHtml());
                objParminfoServiceVOClone.setFormat(objParminfoServiceVO.getFormat());
                objParminfoServiceVOClone.setFixedHeaderWidthTotal(objParminfoServiceVO.getFixedHeaderWidthTotal());
                objParminfoServiceVOClone.setVariableHeaderWidthTotal(objParminfoServiceVO.getVariableHeaderWidthTotal());
                objParminfoServiceVOClone.setStrHeaderWidth(objParminfoServiceVO.getStrHeaderWidth());
                finalHeaderList.add(objParminfoServiceVOClone);
            }

            objFaultDataDetailsServiceVO.setArlHeader(finalHeaderList);
            objFaultDataDetailsServiceVO.setArlGrpHeader(faultGroupList);

            if (finalHeaderList == null || finalHeaderList.isEmpty()
                    || (finalHeaderList != null && finalHeaderList.size() <= 1)) {
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DATA_SCREEN_NO_PARAMS);
                throw new RMDBOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                objVehicleFaultServiceVO.getStrLanguage()),
                        new RMDBOException(), RMDCommonConstants.MINOR_ERROR);
            }
            objFaultDataDetailsServiceVO.setUnitConversionRoleList(unitConversionRoleList);
        } catch (RMDBOException e) {
        	LOG.error(e,e);
            throw e;
        } catch (DataException e) {
            LOG.error(e,e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DATA_SCREEN_NOT_SUPPORTED);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), new RMDBOException(),
                    RMDCommonConstants.MINOR_ERROR);
        } catch (Exception e) {
            LOG.error(e,e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DATA_SCREEN_ERROR);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), new RMDBOException(),
                    RMDCommonConstants.MINOR_ERROR);
        }
        return objFaultDataDetailsServiceVO;
    }
    
    
    @Override
    public FaultMobileDataDetailsServiceVO getMobileFaultDetails(VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDBOException, RMDNullObjectException {

        FaultRequestVO objFaultRequestVO = new FaultRequestVO();
        String strControllerCfg = null;
        GetMobileToolDsParminfoServiceVO objMobileFaultHeaderDetailVOClone = null;
        MobileToolDsParmGroupServiceVO parmGroupMobileInfo = null;
        ArrayList<MobileToolDsParmGroupServiceVO> arlHeaderDet = new ArrayList<MobileToolDsParmGroupServiceVO>();
        ArrayList<GetMobileToolDsParminfoServiceVO> faultDataList = new ArrayList<GetMobileToolDsParminfoServiceVO>();
        ArrayList<MobileToolDsParmGroupServiceVO> faultGroupList = new ArrayList<MobileToolDsParmGroupServiceVO>();
        FaultMobileServiceVO objFaultServiceVO = new FaultMobileServiceVO();
        FaultMobileDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        GetMobileToolDsParminfoServiceVO objMobileParminfoServiceVO;
        GetMobileToolDsParminfoServiceVO objMobileParminfoServiceVOClone;
        ArrayList<GetMobileToolDsParminfoServiceVO> finalMobileHeaderList = new ArrayList<GetMobileToolDsParminfoServiceVO>();
        final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat(RMDServiceConstants.DSDATE_FORMAT);
        String ctrlCfg = null;
        String partStatus = null;
        String caseDefSortOrder = null;
        String caseDefLookDays = null;

        if (objVehicleFaultServiceVO.getCustomerId() != null
                && !(("").equals(objVehicleFaultServiceVO.getCustomerId().trim()))) {
            objFaultRequestVO.setCustomer(objVehicleFaultServiceVO.getCustomerId()); // CustID
        }
        if (objVehicleFaultServiceVO.getAssetGrpName() != null
                && !(("").equals(objVehicleFaultServiceVO.getAssetGrpName().trim()))) {
            objFaultRequestVO.setVehicleHeader(objVehicleFaultServiceVO.getAssetGrpName()); // Road
                                                                                            // Initial
        }
        if (objVehicleFaultServiceVO.getStrRoadNo() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrRoadNo().trim()))) {
            objFaultRequestVO.setSerialNo(objVehicleFaultServiceVO.getStrRoadNo()); // Road
                                                                                    // No
        }
        if (objVehicleFaultServiceVO.getStrDays() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrDays().trim()))
                && objVehicleFaultServiceVO.getStrJDPADRadio() == null) {
            objFaultRequestVO.setNoOfDays(objVehicleFaultServiceVO.getStrDays()); // No
                                                                                  // of
                                                                                  // Days
        } else if (objVehicleFaultServiceVO.getFromDate() != null && objVehicleFaultServiceVO.getToDate() != null
                && objVehicleFaultServiceVO.getStrJDPADRadio() == null) {
            objFaultRequestVO.setVvfStartDate(SDF_FORMAT.format(objVehicleFaultServiceVO.getFromDate())); // From
                                                                                                          // Date
            objFaultRequestVO.setVvfEndDate(SDF_FORMAT.format(objVehicleFaultServiceVO.getToDate())); // To
                                                                                                      // Date
        }
        if (objVehicleFaultServiceVO.getStrAllRecords() != null
                && !("").equals(objVehicleFaultServiceVO.getStrAllRecords().trim())) {
            objFaultRequestVO.setViewAll(objVehicleFaultServiceVO.getStrAllRecords()); // View
                                                                                       // ALL
                                                                                       // Records
        }
        if (objVehicleFaultServiceVO.isBlnHealtCheck()) {
            objFaultRequestVO.setHC("YES"); // Health Check
        }
        if (objVehicleFaultServiceVO.getCaseId() != null
                && !(("").equals(objVehicleFaultServiceVO.getCaseId().trim()))) {
            objFaultRequestVO.setCaseId(objVehicleFaultServiceVO.getCaseId()); // Case
                                                                               // ID
        }
        if (objVehicleFaultServiceVO.getStrCaseType() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrCaseType().trim()))) {
            objFaultRequestVO.setCaseType(objVehicleFaultServiceVO.getStrCaseType()); // Case
                                                                                      // Type
        }
        if (objVehicleFaultServiceVO.getStrSortOption() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrSortOption().trim()))) {
            objFaultRequestVO.setSortOrder(objVehicleFaultServiceVO.getStrSortOption()); // Sort
                                                                                         // Order
        } else if (objFaultRequestVO.getCaseId() == null) {
            objFaultRequestVO.setSortOrder(FaultLogConstants.STR_OCCUR_DATE);
        }
        if (objVehicleFaultServiceVO.getIntStartPos() != 0) {
            int pageNo = objVehicleFaultServiceVO.getIntStartPos() / FaultLogConstants.INT_DEF_PAGINATION_COUNTER;
            objFaultRequestVO.setPaginationHit(String.valueOf(pageNo)); // Page
                                                                        // No
        }
        if (objVehicleFaultServiceVO.getStrScreen() != null
                && !(("").equals(objVehicleFaultServiceVO.getStrScreen().trim()))) {
            objFaultRequestVO.setScreen(objVehicleFaultServiceVO.getStrScreen()); // Screen
        }
        if (objFaultRequestVO.getCaseId() != null && !(("").equals(objFaultRequestVO.getCaseId().trim()))) {
            if (objVehicleFaultServiceVO.isInitLoad()) {
                objFaultRequestVO.setInitialLoad("Y");
            }
            if (objVehicleFaultServiceVO.getStrCaseFrom() == null) {
                objFaultRequestVO.setCaseFrom("NOW");
            } else {
                objFaultRequestVO.setCaseFrom(objVehicleFaultServiceVO.getStrCaseFrom()); // Case
                                                                                          // From
            }
            if (("Oil Anomaly").equals(objFaultRequestVO.getCaseType())) {
                objFaultRequestVO.setScreen("CASE-OIL");
            } else if (("QNX Equipment").equals(objFaultRequestVO.getCaseType())) {
                objFaultRequestVO.setScreen("CASE-QNX");
            } else if (("DHMS Problem").equals(objFaultRequestVO.getCaseType())) {
                objFaultRequestVO.setScreen("CASE-DHMS");
            } else {
                objFaultRequestVO.setScreen("CASE-REM");
            }
            if (objVehicleFaultServiceVO.getStrRuleDefId() != null
                    && !(("").equals(objVehicleFaultServiceVO.getStrRuleDefId().trim()))) {
                objFaultRequestVO.setRuleDefID(objVehicleFaultServiceVO.getStrRuleDefId()); // Rule
                                                                                            // definition
                                                                                            // ID
            }
            if (objVehicleFaultServiceVO.getStrJDPADRadio() != null
                    && !(("").equals(objVehicleFaultServiceVO.getStrJDPADRadio().trim()))) {
                objFaultRequestVO.setJdpadCbrCrit(objVehicleFaultServiceVO.getStrJDPADRadio()); // Rule
                                                                                                // Type
                objFaultRequestVO.setCaseFrom(null);
            }
        }
        try {
            ControllerModelVO ctrlModelVO = objFaultLogDAO.getControllerSrcAndModel(objFaultRequestVO.getCustomer(),
                    objFaultRequestVO.getVehicleHeader(), objFaultRequestVO.getSerialNo());
            ctrlCfg = ctrlModelVO.getControllerCfg();
            if (("EQUIP").equals(objFaultRequestVO.getScreen().trim())
                    || ("RR-EQUIP").equals(objFaultRequestVO.getScreen().trim())) {
                if (ctrlModelVO.getPartStatus() != null
                        && ("QNX INSTALLED").equalsIgnoreCase(ctrlModelVO.getPartStatus())) {
                    partStatus = "QNX";
                    objFaultRequestVO.setScreen(objFaultRequestVO.getScreen() + "-QNX");
                } else if (ctrlModelVO.getPartStatus() != null
                        && ("INSTALLED/GOOD").equalsIgnoreCase(ctrlModelVO.getPartStatus())) {
                    partStatus = "NT";
                    objFaultRequestVO.setScreen(objFaultRequestVO.getScreen() + "-NT");
                } else {
                    partStatus = "NONE";
                    objFaultRequestVO.setScreen(objFaultRequestVO.getScreen() + "-QNX");
                }
            }
            objFaultRequestVO.setStrViewName(ctrlModelVO.getDsViewName());
            objFaultRequestVO.setVehicleID(ctrlModelVO.getVehicleObjid());

            if (objFaultRequestVO.getCaseId() != null && !(("").equals(objFaultRequestVO.getCaseId().trim()))) {
                SortOrderLkBackDaysVO sortOrderVO = null;
                if (objFaultRequestVO.getScreen() != null && ("CASE-DHMS").equals(objFaultRequestVO.getScreen())) {
                    sortOrderVO = objFaultLogDAO.getSortOrderLookbackDays().get(objFaultRequestVO.getCaseType() + "_"
                            + FaultLogConstants.STR_DHMS_CTRL_SRC_ID + "_" + ctrlModelVO.getModelName());
                } else {
                    sortOrderVO = objFaultLogDAO.getSortOrderLookbackDays().get(objFaultRequestVO.getCaseType() + "_"
                            + ctrlModelVO.getControllerSrcId() + "_" + ctrlModelVO.getModelName());
                }
                if ((objFaultRequestVO.getNoOfDays() == null || ("").equals(objFaultRequestVO.getNoOfDays().trim()))
                        && objVehicleFaultServiceVO.getStrJDPADRadio() == null) {
                    if (sortOrderVO != null) {
                        objFaultRequestVO.setNoOfDays(sortOrderVO.getDefaultLookBack());
                        caseDefLookDays = sortOrderVO.getDefaultLookBack();
                        objFaultRequestVO.setSortOrder(sortOrderVO.getDefaultSortOrder());
                        caseDefSortOrder = sortOrderVO.getDefaultSortOrder();
                    } else {
                        String errorCode = RMDCommonUtility
                                .getErrorCode(RMDServiceConstants.DATA_SCREEN_MISSING_CONFIGURATION);
                        throw new RMDBOException(errorCode, new String[] {},
                                RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), new RMDBOException(),
                                RMDCommonConstants.MINOR_ERROR);
                    }
                }
                if ((objFaultRequestVO.getViewAll() == null || ("N").equals(objFaultRequestVO.getViewAll().trim()))
                        && objFaultRequestVO.getPaginationHit() == null) {
                    objFaultRequestVO.setPaginationHit("0"); // Default
                                                             // Pagination Hit
                }
                if (objVehicleFaultServiceVO.isNotch8()) {
                    objFaultRequestVO.setNotch("8");
                }
            }
            FaultHeaderVO fixedFaultHeaderVO = objFaultLogDAO.getHeaderDetails(false).get("Fixed-" + ctrlModelVO.getModelObjId());
            FaultHeaderVO nonFixedFaultHeaderVO = objFaultLogDAO.getHeaderDetails(false).get(ctrlModelVO.getModelObjId());
            FaultHeaderVO faultHeaderVO = new FaultHeaderVO();
            faultHeaderVO.setFaultGroups(new ArrayList());
            if (fixedFaultHeaderVO != null && !fixedFaultHeaderVO.getFaultGroups().isEmpty()) {
                faultHeaderVO.setControllerDesc(fixedFaultHeaderVO.getControllerDesc());
                faultHeaderVO.setControllerId(fixedFaultHeaderVO.getControllerId());
                faultHeaderVO.setModel(fixedFaultHeaderVO.getModel());
                faultHeaderVO.getFaultGroups().addAll(fixedFaultHeaderVO.getFaultGroups());
            }
            if (nonFixedFaultHeaderVO != null && !nonFixedFaultHeaderVO.getFaultGroups().isEmpty()) {
                faultHeaderVO.setControllerDesc(nonFixedFaultHeaderVO.getControllerDesc());
                strControllerCfg = nonFixedFaultHeaderVO.getControllerDesc();
                faultHeaderVO.setControllerId(nonFixedFaultHeaderVO.getControllerId());
                faultHeaderVO.setModel(nonFixedFaultHeaderVO.getModel());
                faultHeaderVO.getFaultGroups().addAll(nonFixedFaultHeaderVO.getFaultGroups());
            }
                       
            List<String> arlParamInfoObjid = null;
            if (objVehicleFaultServiceVO.isMobileRequest()) {
                arlParamInfoObjid = (ArrayList<String>) objFaultLogDAO.getCustLevelMobileDetails()
                        .get(objFaultRequestVO.getCustomer() + "_" + ctrlModelVO.getModelObjId());
            }
            
            if ((RMDCommonConstants.VEHICLE_UPPER).equals(objFaultRequestVO.getScreen().trim()) && objVehicleFaultServiceVO.isHideL3Faults()) {
                objFaultRequestVO.setHideL3("Y");
            }
            if (objFaultRequestVO.getScreen() != null && (strDpEabConst).equals(objFaultRequestVO.getScreen().trim())) {
                strControllerCfg = null;
                faultHeaderVO = objFaultLogDAO.getDPEABHeaders(false);
            }
            if (objFaultRequestVO.getScreen() != null && (("DHMS").equals(objFaultRequestVO.getScreen().trim())
                    || ("CASE-DHMS").equals(objFaultRequestVO.getScreen().trim()))) {
                strControllerCfg = null;
                faultHeaderVO = objFaultLogDAO.getDHMSHeaderDetails(false);
            }
            if (objFaultRequestVO.getScreen() != null && ("CASE-OIL").equals(objFaultRequestVO.getScreen().trim())) {
                FaultHeaderVO oilFixedHeaderVO = objFaultLogDAO.getOilHeaderDetails(false).get("FixedParameters");
                FaultHeaderVO oilCustHeaderVO = objFaultLogDAO.getOilHeaderDetails(false).get(objFaultRequestVO.getCustomer());
                faultHeaderVO = new FaultHeaderVO();
                faultHeaderVO.setFaultGroups(new ArrayList());
                if (oilFixedHeaderVO != null && !oilFixedHeaderVO.getFaultGroups().isEmpty()) {
                    faultHeaderVO.getFaultGroups().addAll(oilFixedHeaderVO.getFaultGroups());
                }
                if (oilCustHeaderVO != null && !oilCustHeaderVO.getFaultGroups().isEmpty()) {
                    faultHeaderVO.getFaultGroups().addAll(oilCustHeaderVO.getFaultGroups());
                }
            }

            if (faultHeaderVO != null) {
                ArrayList<FaultHeaderGroupVO> headerGrpList = faultHeaderVO.getFaultGroups();
                if (headerGrpList != null && !headerGrpList.isEmpty()) {
                    for (int i = 0; i < headerGrpList.size(); i++) {
                        ArrayList<FaultHeaderDetailVO> faultDetailsList = headerGrpList.get(i).getFaultHeaderDetails();
                        if (faultDetailsList != null && !faultDetailsList.isEmpty()) {
                            int noOfCols = 0;
                            for (int j = 0; j < faultDetailsList.size(); j++) {
                                FaultHeaderDetailVO faultDetailsVO = faultDetailsList.get(j);
                                if (arlParamInfoObjid != null && !arlParamInfoObjid.isEmpty()
                                        && !arlParamInfoObjid.contains(faultDetailsVO.getParmObjid())
                                        && objFaultRequestVO.getScreen() != null
                                        && ((RMDCommonConstants.VEHICLE_UPPER).equals(objFaultRequestVO.getScreen().trim())
                                                || ("EQUIP-NT").equals(objFaultRequestVO.getScreen().trim()))) {
                                    if ((faultDetailsList.size() - 1) == j && noOfCols > 0) {
                                        parmGroupMobileInfo = new MobileToolDsParmGroupServiceVO();
                                        parmGroupMobileInfo.setParamGroup(headerGrpList.get(i).getGroupName());
                                        if (objMobileFaultHeaderDetailVOClone.getStyle() == null) {
                                            parmGroupMobileInfo.setStyle("fixedheaders");
                                        } else {
                                            parmGroupMobileInfo.setStyle(
                                                    objMobileFaultHeaderDetailVOClone.getStyle().trim().toLowerCase());
                                        }
                                        arlHeaderDet.add(parmGroupMobileInfo);
                                        faultGroupList.add(parmGroupMobileInfo);
                                    }
                                    continue;
                                }
                                String colAvail = "";
                                if ((RMDCommonConstants.VEHICLE_UPPER).equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getVvfFlag();
                                } else if (("EQUIP-NT").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getVvfFlag();
                                } else if (("EQUIP-QNX").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getQnxColAvail();
                                } else if (("RR-VEHICLE").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getRapidResponseFlag();
                                } else if (("RR-EQUIP-NT").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getRapidResponseFlag();
                                } else if (("RR-EQUIP-QNX").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getQnxColAvail();
                                } else if (("INCIDENT").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getIncidentFlag();
                                } else if ((strDpEabConst).equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getVvfFlag();
                                } else if (("DHMS").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getStrDhmsColAvail();
                                } else if (("CASE-QNX").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getQnxColAvail();
                                } else if (("CASE-OIL").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getInternalColAvail();
                                } else if (("CASE-REM").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getDataScreenFlag();
                                } else if (("CASE-DHMS").equals(objFaultRequestVO.getScreen().trim())) {
                                    colAvail = faultDetailsVO.getStrDhmsColAvail();
                                }
                                if (colAvail != null && ("Y").equalsIgnoreCase(colAvail)) {
                                    noOfCols++;
                                    objMobileFaultHeaderDetailVOClone = new GetMobileToolDsParminfoServiceVO();


                                    objMobileFaultHeaderDetailVOClone.setCustID(faultDetailsVO.getStrCustID());
                                    objMobileFaultHeaderDetailVOClone.setHeaderHtml(faultDetailsVO.getHeaderDesc());                                  
                                    objMobileFaultHeaderDetailVOClone.setToolTip(faultDetailsVO.getToolTip());
                                    objMobileFaultHeaderDetailVOClone.setColumnName(faultDetailsVO.getColumnName());
                                    objMobileFaultHeaderDetailVOClone.setHeaderWidth(faultDetailsVO.getHeaderWidth());
                                    objMobileFaultHeaderDetailVOClone.setCharLength(faultDetailsVO.getCharLength());
                                    if (faultDetailsVO.getUpperBound() != null) {
                                        objMobileFaultHeaderDetailVOClone
                                                .setUpperBound(Double.parseDouble(faultDetailsVO.getUpperBound()));
                                    } else {
                                        objMobileFaultHeaderDetailVOClone.setUpperBound(Double.valueOf("0.0"));
                                    }
                                    if (faultDetailsVO.getLowerBound() != null) {
                                        objMobileFaultHeaderDetailVOClone
                                                .setLowerBound(Double.parseDouble(faultDetailsVO.getLowerBound()));
                                    } else {
                                        objMobileFaultHeaderDetailVOClone.setLowerBound(Double.valueOf("0.0"));
                                    }
                                    objMobileFaultHeaderDetailVOClone.setDataTooltipFlag(faultDetailsVO.getDataToolTipFlag());
                                    objMobileFaultHeaderDetailVOClone.setFormat(faultDetailsVO.getFormat());
                                    objMobileFaultHeaderDetailVOClone.setStyle(faultDetailsVO.getStyle());
                                    faultDataList.add(objMobileFaultHeaderDetailVOClone);

                                    if ((faultDetailsList.size() - 1) == j && noOfCols > 0) {
                                        parmGroupMobileInfo = new MobileToolDsParmGroupServiceVO();
                                        parmGroupMobileInfo.setParamGroup(headerGrpList.get(i).getGroupName());
                                        if (objMobileFaultHeaderDetailVOClone.getStyle() == null) {
                                            parmGroupMobileInfo.setStyle("fixedheaders");
                                        } else {
                                            parmGroupMobileInfo.setStyle(
                                                    objMobileFaultHeaderDetailVOClone.getStyle().trim().toLowerCase());
                                        }
                                        arlHeaderDet.add(parmGroupMobileInfo);
                                        faultGroupList.add(parmGroupMobileInfo);
                                    }
                                } else if ((faultDetailsList.size() - 1) == j && noOfCols > 0) {
                                    parmGroupMobileInfo = new MobileToolDsParmGroupServiceVO();
                                    parmGroupMobileInfo.setParamGroup(headerGrpList.get(i).getGroupName());

                                    if (objMobileFaultHeaderDetailVOClone.getStyle() == null) {
                                        parmGroupMobileInfo.setStyle("fixedheaders");
                                    } else {
                                        parmGroupMobileInfo
                                                .setStyle(objMobileFaultHeaderDetailVOClone.getStyle().trim().toLowerCase());
                                    }
                                    arlHeaderDet.add(parmGroupMobileInfo);
                                    faultGroupList.add(parmGroupMobileInfo);
                                }
                            }
                        }
                    }
                }
            }
            if (strControllerCfg != null && !("").equals(strControllerCfg.trim())) {
                objFaultRequestVO.setCtrlCfg(strControllerCfg);
            }
            objFaultServiceVO = objFaultLogDAO.getMobileFaultData(objFaultRequestVO, faultDataList, strControllerCfg);
            objFaultDataDetailsServiceVO = new FaultMobileDataDetailsServiceVO();
            objFaultDataDetailsServiceVO.setObjFaultServiceVO(objFaultServiceVO);
            if (partStatus != null) {
                objFaultDataDetailsServiceVO.setPartStatus(partStatus);
            }
            if (ctrlCfg != null) {
                objFaultDataDetailsServiceVO.setCtrlCfg(ctrlCfg);
            }
            if (caseDefSortOrder != null) {
                objFaultDataDetailsServiceVO.setCaseSortOrder(caseDefSortOrder);
            }
            if (caseDefLookDays != null) {
                objFaultDataDetailsServiceVO.setCaseLookBackDays(caseDefLookDays);
            }
            if (objFaultServiceVO.getTotalRecords() > 0) {
                objFaultDataDetailsServiceVO.setRecordsTotal(String.valueOf(objFaultServiceVO.getTotalRecords()));
            }
            Map<String, String> filterList = objFaultLogDAO.getDataScreenFilters();
            for (int i = 0; i < faultDataList.size(); i++) {
                objMobileParminfoServiceVO = faultDataList.get(i);
                objMobileParminfoServiceVOClone = new GetMobileToolDsParminfoServiceVO();

                objMobileParminfoServiceVOClone.setColumnName(objMobileParminfoServiceVO.getColumnName());
                if (objMobileParminfoServiceVO.getColumnName() != null
                        && filterList.get(objMobileParminfoServiceVO.getColumnName()) != null) {
                    objMobileParminfoServiceVOClone.setFilter(filterList.get(objMobileParminfoServiceVO.getColumnName()));
                }
                objMobileParminfoServiceVOClone.setHeaderHtml(objMobileParminfoServiceVO.getHeaderHtml());
                if (objMobileParminfoServiceVO.getStyle() != null &&    !("").equals(objMobileParminfoServiceVO.getStyle().trim())) {
                    objMobileParminfoServiceVOClone.setStyle(objMobileParminfoServiceVO.getStyle().trim().toLowerCase());
                }
                objMobileParminfoServiceVOClone.setInfo(objMobileParminfoServiceVO.getInfo());
                objMobileParminfoServiceVOClone.setLowerBound(objMobileParminfoServiceVO.getLowerBound());
                objMobileParminfoServiceVOClone.setUpperBound(objMobileParminfoServiceVO.getUpperBound());
                objMobileParminfoServiceVOClone.setDataTooltipFlag(objMobileParminfoServiceVO.getDataTooltipFlag());
                objMobileParminfoServiceVOClone.setToolTip(objMobileParminfoServiceVO.getToolTip());
                objMobileParminfoServiceVOClone.setHeaderWidth(objMobileParminfoServiceVO.getHeaderWidth());
                objMobileParminfoServiceVOClone.setCharLength(objMobileParminfoServiceVO.getCharLength());
                objMobileParminfoServiceVOClone.setFormat(objMobileParminfoServiceVO.getFormat());
                objMobileParminfoServiceVOClone.setStrHeaderWidth(objMobileParminfoServiceVO.getStrHeaderWidth());
                finalMobileHeaderList.add(objMobileParminfoServiceVOClone);
            }

            objFaultDataDetailsServiceVO.setArlHeader(finalMobileHeaderList);
            objFaultDataDetailsServiceVO.setArlGrpHeader(faultGroupList);

            if (finalMobileHeaderList == null || finalMobileHeaderList.isEmpty()
                    || (finalMobileHeaderList != null && finalMobileHeaderList.size() <= 1)) {
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DATA_SCREEN_NO_PARAMS);
                throw new RMDBOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                objVehicleFaultServiceVO.getStrLanguage()),
                        new RMDBOException(), RMDCommonConstants.MINOR_ERROR);
            }
        } catch (RMDBOException e) {
            throw e;
        } catch (DataException e) {
            LOG.debug(e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DATA_SCREEN_NOT_SUPPORTED);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), new RMDBOException(),
                    RMDCommonConstants.MINOR_ERROR);
        } catch (Exception e) {
            LOG.debug(e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DATA_SCREEN_ERROR);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), new RMDBOException(),
                    RMDCommonConstants.MINOR_ERROR);
        }
        return objFaultDataDetailsServiceVO;
    }

    /**
     * @param objFaultRequestVO
     * @param objFaultLogBO
     */
    private HashMap<String, List> formatFaultData(ArrayList arlData, ArrayList arlGrpData, String strScreen,
            String strCaseType, String[] strVvfViewSelected, String strOilEntryFrom, FaultRequestVO objFaultRequestVO,
            ArrayList arlParamInfoObjid) throws Exception {
        ArrayList faultHeaderList = new ArrayList();
        ArrayList grpHeaderList = new ArrayList();
        HashMap<String, List> formatHeaderMap = new HashMap<String, List>();
        String strColAvail = null;
        ToolDsParmGroupServiceVO parmGroupInfo = null;
        GetToolDsParminfoServiceVO objFaultHeaderDetailVOClone = null;
        ArrayList arlHeaderDet = new ArrayList();
        HashSet hsGroupName1 = new HashSet();
        int cnt = 0;
        ArrayList arr = new ArrayList();

        for (int j = 0; j < arlData.size(); j++) {
            strColAvail = null;
            objFaultHeaderDetailVOClone = (GetToolDsParminfoServiceVO) arlData.get(j);
            if (FaultLogConstants.STR_CASE.equalsIgnoreCase(strScreen)) {
                if (RMDCommonConstants.QNX_EQUIPMENT.equalsIgnoreCase(strCaseType)) {
                    strColAvail = objFaultHeaderDetailVOClone.getQnxColAvail();
                } else if (RMDCommonConstants.OIL_ANOMALY.equalsIgnoreCase(strCaseType)) {
                    strColAvail = objFaultHeaderDetailVOClone.getInternalColAvail();
                } else {
                    strColAvail = objFaultHeaderDetailVOClone.getDataScreenFlag();
                }
            } else if (FaultLogConstants.STR_VVF.equalsIgnoreCase(strScreen)) {
                String datset = null;
                for (int i = 0; i < strVvfViewSelected.length; i++) {
                    datset = strVvfViewSelected[i];
                    if (RMDCommonConstants.LOCO.equalsIgnoreCase(datset)
                            || RMDCommonConstants.RMD.equalsIgnoreCase(datset)
                            || RMDCommonConstants.DPEAB.equalsIgnoreCase(datset)) {
                        strColAvail = objFaultHeaderDetailVOClone.getVvfFlag();
                        if (arlParamInfoObjid != null && !arlParamInfoObjid.isEmpty()) {
                            strColAvail = null;
                            if (arlParamInfoObjid.contains(objFaultHeaderDetailVOClone.getParmObjid())) {
                                strColAvail = RMDCommonConstants.LETTER_Y;
                            }
                        }
                    }
                    if (RMDCommonConstants.QNX.equalsIgnoreCase(strVvfViewSelected[i])) {
                        strColAvail = objFaultHeaderDetailVOClone.getQnxColAvail();
                    }
                }
            } else if (FaultLogConstants.STR_INCIDENT.equalsIgnoreCase(strScreen)) {
                strColAvail = objFaultHeaderDetailVOClone.getIncidentFlag();
            } else if (FaultLogConstants.STR_OIL.equalsIgnoreCase(strScreen)) {
                if (FaultLogConstants.STR_ESERVICES.equalsIgnoreCase(strOilEntryFrom)) {
                    strColAvail = objFaultHeaderDetailVOClone.getExternalColAvail();
                } else {
                    strColAvail = objFaultHeaderDetailVOClone.getInternalColAvail();
                }
            }
            if (FaultLogConstants.STR_Y.equalsIgnoreCase(strColAvail)) {
                faultHeaderList.add(
                        objFaultHeaderDetailVOClone); /*
                                                       * Added for group logic
                                                       */
                if (!hsGroupName1.contains(objFaultHeaderDetailVOClone.getGroupName())) {
                    parmGroupInfo = new ToolDsParmGroupServiceVO();
                    parmGroupInfo.setParamGroup(objFaultHeaderDetailVOClone.getGroupName());
                    parmGroupInfo.setStyle(objFaultHeaderDetailVOClone.getStyle());
                    parmGroupInfo.setColspan(String.valueOf(cnt));
                    arlHeaderDet.add(parmGroupInfo);
                    hsGroupName1.add(objFaultHeaderDetailVOClone.getGroupName());
                    arr.add(cnt);
                    cnt = 1;
                } else {
                    cnt++;
                }
            }
        }
        arr.add(cnt); /* Added for group logic */
        for (int i = 0; i < arlHeaderDet.size(); i++) {
            parmGroupInfo = (ToolDsParmGroupServiceVO) arlHeaderDet.get(i);
            parmGroupInfo.setColspan(String.valueOf(arr.get(i + 1)));
            grpHeaderList.add(parmGroupInfo);
        }
        formatHeaderMap.put(RMDCommonConstants.FAULTHEADERLIST, faultHeaderList);
        formatHeaderMap.put(RMDCommonConstants.GRPHEADERLIST, grpHeaderList);
        return formatHeaderMap;
    }

    /**
     * The purpose of this method is to return DPEAB fault details
     * 
     * @param objVehicleFaultServiceVO
     * @return
     * @throws RMDBOException
     * @throws RMDNullObjectException
     */
    @Override
    public FaultDataDetailsServiceVO getDPEABFaultDetails(VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDBOException, RMDNullObjectException {
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        GetToolDsParminfoServiceVO objParminfoServiceVO;
        GetToolDsParminfoServiceVO objParminfoServiceVOClone;
        StringBuilder strFixedHeader = new StringBuilder();
        StringBuilder strVariableHeader = new StringBuilder();

        try {

            String[] ddDataSets = new String[3];
            // 1. Intialize FaultRequestVO
            FaultRequestVO objFaultRequestVO = new FaultRequestVO();

            objFaultRequestVO.setRuleDefID(objVehicleFaultServiceVO.getStrRuleDefId());
            String strScreen = null;

            String availableDataSets = objVehicleFaultServiceVO.getDataSets();
            if ((availableDataSets != null) && (availableDataSets.length() > 0)) {
                strScreen = FaultLogConstants.STR_VVF;

                if (objVehicleFaultServiceVO.getDataSets().contains(RMDCommonConstants.COMMMA_SEPARATOR)) {
                    ddDataSets = objVehicleFaultServiceVO.getDataSets().split(RMDCommonConstants.COMMMA_SEPARATOR);
                } else {
                    ddDataSets[0] = objVehicleFaultServiceVO.getDataSets();
                }
            }

            objFaultRequestVO.setScreen(strScreen);

            String strSerialNo = objVehicleFaultServiceVO.getStrRoadNo();// --RoadNumber
            objFaultRequestVO.setSerialNo(strSerialNo);

            String strVehicleHeader = objVehicleFaultServiceVO.getAssetGrpName();
            objFaultRequestVO.setVehicleHeader(strVehicleHeader);
            String strCustomer = objVehicleFaultServiceVO.getCustomerId();
            objFaultRequestVO.setCustomer(strCustomer);

            String strCaseFrom = objVehicleFaultServiceVO.getStrCaseFrom();
            if (null == strCaseFrom || strCaseFrom.equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                strCaseFrom = FaultLogConstants.STR_Y;
            }

            objFaultRequestVO.setCaseFrom(strCaseFrom);

            String strIsJdpadCbrCrit = objVehicleFaultServiceVO.getStrJDPADRadio(); // -->
            // Hardcode
            // to days
            objFaultRequestVO.setJdpadCbrCrit(strIsJdpadCbrCrit);

            String strVvfRadioSelected = null;
            if (null != ddDataSets[1] && FaultLogConstants.STR_DAYS_BETWEEN.equalsIgnoreCase(ddDataSets[1])) {
                strVvfRadioSelected = FaultLogConstants.STR_DAYS_BETWEEN;
            } else {
                strVvfRadioSelected = FaultLogConstants.STR_NO_OF_DAYS;
            }

            objFaultRequestVO.setRadioSelected(strVvfRadioSelected);

            String strVVfViewRadioSelected = null;
            if (FaultLogConstants.STR_VVF.equalsIgnoreCase(strScreen)
                    && (RMDCommonConstants.DPEAB).equalsIgnoreCase(ddDataSets[0])) {
                strVVfViewRadioSelected = FaultLogConstants.STR_DPEAB;// ddDataSets[0]
            }

            objFaultRequestVO.setVVfViewRadioSelected(strVVfViewRadioSelected);

            String strModel = RMDCommonConstants.EMPTY_STRING;

            String strViewAll = FaultLogConstants.STR_Y;
            objFaultRequestVO.setViewAll(strViewAll);

            if (null == strModel || RMDCommonConstants.EMPTY_STRING.equals(strModel)) {

                FaultLogHelper.debug("Start  getControllerSrcId :" + System.currentTimeMillis());

                strScreen = objFaultRequestVO.getScreen();
                objFaultLogDAO.getControllerSrcId(objFaultRequestVO);

                strModel = objFaultRequestVO.getModel();
                FaultLogHelper.debug("calling  the controller source id  method:" + strModel);
            } else {
                FaultLogHelper.debug("strModel  from request " + strModel);
                objFaultRequestVO.setModel(strModel);
            }

            // 4. Get Model Id
            String strModelId;
            strModelId = objFaultLogDAO.getAllModels().get(objFaultRequestVO.getModel());

            // Default Sort Order and Default Lookback are based on CaseType.
            // Here we will hardcode (OR assume a default case type???)
            // datacsreen sort order change for DD role: Phase II Sprint 6
            // change - Start
            String strSortOrder = null;
            if (null != objVehicleFaultServiceVO.getStrSortOption()
                    && objVehicleFaultServiceVO.getStrSortOption().equalsIgnoreCase(FaultLogConstants.STR_OCCUR_TIME)) {
                strSortOrder = FaultLogConstants.STR_OCCUR_DATE;
            } else {
                strSortOrder = FaultLogConstants.STR_OCCUR_DATE;
            }
            // datacsreen sort order change for DD role: Phase II Sprint 6
            // change - End

            if (null == strSortOrder || RMDCommonConstants.EMPTY_STRING.equals(strSortOrder)) {
                strSortOrder = objFaultLogDAO.getSortOrders().get(objFaultRequestVO.getCaseType() + "_"
                        + objFaultRequestVO.getControllerSrcID() + "_" + objFaultRequestVO.getModel());
            }
            objFaultRequestVO.setSortOrder(strSortOrder);
            String strNoOfDays = null;
            if (null == objVehicleFaultServiceVO.getStrDaysSelected()
                    || RMDCommonConstants.EMPTY_STRING.equals(objVehicleFaultServiceVO.getStrDaysSelected())) {
                Long days = RMDCommonUtility.dayDiff(objVehicleFaultServiceVO.getToDate(),
                        objVehicleFaultServiceVO.getFromDate());

                strNoOfDays = String.valueOf(days);
            } else {
                strNoOfDays = objVehicleFaultServiceVO.getStrDaysSelected();
            }

            String strInitialLoad = null;

            if (null == strNoOfDays || RMDCommonConstants.EMPTY_STRING.equals(strNoOfDays)) {
                strInitialLoad = FaultLogConstants.STR_Y;
                objFaultRequestVO.setInitialLoad(strInitialLoad);
                strNoOfDays = objFaultLogDAO.getSortOrders().get(objFaultRequestVO.getCaseType() + "_"
                        + objFaultRequestVO.getControllerSrcID() + "_" + objFaultRequestVO.getModel());
            }
            objFaultRequestVO.setNoOfDays(strNoOfDays);

            // 5.Get Header Details
            HashMap hmHeaders = new HashMap<String, FaultHeaderVO>();

            strScreen = objFaultRequestVO.getScreen();
            ArrayList<String> arlParamInfoObjid = null;

            // YJ: 19SEP2012 Start: Added method to fetch customer specific
            // headers
            if (FaultLogConstants.STR_VVF.equals(strScreen)
                    && (RMDCommonConstants.DPEAB).equalsIgnoreCase(ddDataSets[0])) {
                arlParamInfoObjid = objVehFltDAOIntf.getCustLevelDPEABDetails(strCustomer, strModelId);
            }
            // YJ: 19SEP2012 Change End
            ArrayList arlHeaderList = new ArrayList();
            ArrayList arlGrpHeaderList = new ArrayList();
            ArrayList finalHeaderList = new ArrayList();
            String finalHeaderWidth = null;
            String fixedHeaderWidth = null;
            String variableHeaderWidth = null;
            if (null != hmHeaders) {
                arlHeaderList = (ArrayList) hmHeaders.get(RMDCommonConstants.HEADERDETAIL);
                arlGrpHeaderList = (ArrayList) hmHeaders.get(RMDCommonConstants.HEADERGRPDETAIL);
                finalHeaderWidth = (String) hmHeaders.get(RMDCommonConstants.FINALHEADERWIDTH);
                fixedHeaderWidth = (String) hmHeaders.get(RMDCommonConstants.FIXEDHEADERWIDTH);
                variableHeaderWidth = (String) hmHeaders.get(RMDCommonConstants.VARIABLEHEADERWIDTH);
            }
            objFaultDataDetailsServiceVO = new FaultDataDetailsServiceVO();

            // 6. Initialize FaultBuilder

            strVVfViewRadioSelected = RMDCommonConstants.EMPTY_STRING;

            HashMap<String, List> formattedHeader = formatFaultData(arlHeaderList, arlGrpHeaderList, strScreen, null,
                    ddDataSets, null, objFaultRequestVO, arlParamInfoObjid);
            ArrayList faultDataList = (ArrayList) formattedHeader.get(RMDCommonConstants.FAULTHEADERLIST);
            ArrayList faultGroupList = (ArrayList) formattedHeader.get(RMDCommonConstants.GRPHEADERLIST);

            // 8. Extract Header and Group Information and Call execute method
            // to getFaultData
            FaultServiceVO objFaultServiceVO = new FaultServiceVO();
            String strControllerCfg = "";
            if (null != hmHeaders) {
                strControllerCfg = (String) hmHeaders.get(RMDCommonConstants.CONTROLLERFLAG);
            }
            strScreen = objFaultRequestVO.getScreen();
            /*
             * Commented code for FaultDAOFactory class kept for future
             * reference
             */
            /*
             * Commented code for FaultDAOFactory class kept for future
             * reference
             */

            if (FaultLogConstants.STR_VVF.equalsIgnoreCase(strScreen)) {
                objFaultServiceVO = objFaultLogDAO.getFaultData(objFaultRequestVO, faultDataList, strControllerCfg);
            } else {
                objFaultServiceVO = objFaultLogDAO.getFaultData(objFaultRequestVO, faultDataList, strControllerCfg);
            }
            objFaultDataDetailsServiceVO.setObjFaultServiceVO(objFaultServiceVO);

            /* Added for MP_ */

            for (int i = 0; i < faultDataList.size(); i++) {
                objParminfoServiceVO = (GetToolDsParminfoServiceVO) faultDataList.get(i);
                objParminfoServiceVOClone = new GetToolDsParminfoServiceVO();

                objParminfoServiceVOClone.setColumnName(objParminfoServiceVO.getParamNumber());
                objParminfoServiceVOClone.setHeaderHtml(objParminfoServiceVO.getHeaderHtml());
                objParminfoServiceVOClone.setStyle(objParminfoServiceVO.getStyle());
                objParminfoServiceVOClone.setInfo(objParminfoServiceVO.getInfo());
                objParminfoServiceVOClone.setLowerBound(objParminfoServiceVO.getLowerBound());
                objParminfoServiceVOClone.setUpperBound(objParminfoServiceVO.getUpperBound());
                objParminfoServiceVOClone.setDataTooltipFlag(objParminfoServiceVO.getDataTooltipFlag());
                objParminfoServiceVOClone.setHeaderWidth(objParminfoServiceVO.getHeaderWidth());
                objParminfoServiceVOClone.setDummyHeaderHtml(objParminfoServiceVO.getDummyHeaderHtml());
                objParminfoServiceVOClone.setFormat(objParminfoServiceVO.getFormat());

                objParminfoServiceVOClone.setFixedHeaderWidthTotal(objParminfoServiceVO.getFixedHeaderWidthTotal());
                objParminfoServiceVOClone
                        .setVariableHeaderWidthTotal(objParminfoServiceVO.getVariableHeaderWidthTotal());
                objParminfoServiceVOClone.setStrHeaderWidth(objParminfoServiceVO.getStrHeaderWidth());
                finalHeaderList.add(objParminfoServiceVOClone);
            }

            objFaultDataDetailsServiceVO.setArlHeader(finalHeaderList);
            objFaultDataDetailsServiceVO.setArlGrpHeader(faultGroupList);
            objFaultDataDetailsServiceVO.setStrHeaderWidth(finalHeaderWidth);
            objFaultDataDetailsServiceVO.setFixedHeaderWidthTotal(fixedHeaderWidth);
            objFaultDataDetailsServiceVO.setVariableHeaderWidthTotal(variableHeaderWidth);
            /* Added for MP_ */

            String strHeader = objFaultDataDetailsServiceVO.getStrHeaderWidth();
            int iCount = 0;
            StringTokenizer strToken = new StringTokenizer(strHeader, RMDCommonConstants.COMMMA_SEPARATOR, false);
            while (strToken.hasMoreElements()) {
                if (iCount < 2) {
                    strFixedHeader.append(strToken.nextElement()).append(RMDCommonConstants.COMMMA_SEPARATOR);
                    iCount++;
                } else {
                    strVariableHeader.append(strToken.nextElement()).append(RMDCommonConstants.COMMMA_SEPARATOR);
                    iCount++;
                }
            }
            String strFinalFixedHeaderWidth;
            String strFinalVarHeaderWidth;
            strFinalFixedHeaderWidth = strFixedHeader.substring(0, strFixedHeader.length() - 1);
            strFinalVarHeaderWidth = strVariableHeader.substring(0, strVariableHeader.length() - 1);
            objFaultDataDetailsServiceVO.setStrFixedHeaderWidth(strFinalFixedHeaderWidth);
            objFaultDataDetailsServiceVO.setStrHeaderWidth(strFinalVarHeaderWidth);

            if (finalHeaderList == null || finalHeaderList.isEmpty()
                    || (finalHeaderList != null && finalHeaderList.size() <= 1)) {
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DATA_SCREEN_NO_PARAMS);
                throw new RMDBOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                objVehicleFaultServiceVO.getStrLanguage()),
                        new RMDBOException(), RMDCommonConstants.MINOR_ERROR);

            }

        } catch (RMDDAOException e) {
            throw e;
        } catch (RMDBOException e) {
            throw e;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_DETAILS);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objVehicleFaultServiceVO.getStrLanguage()),
                    ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objFaultDataDetailsServiceVO;
    }

    /**
     * This method is used for real time check for owners in OMD
     * 
     * @param String
     * @throws RMDBOException
     */
    @Override
    public String getEoaCurrentOwnership(String caseId) throws RMDBOException {
        try {
            return objCaseEoaDAOIntf.getEoaCurrentOwnership(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.GENERAL_BO_EXCEPTION);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.cases.bo.intf.CaseEoaBOIntf#takeOwnership(com.
     * ge.trans.eoa.services.cases.service.valueobjects.AcceptCaseEoaVO)
     */
    @Override
    public void takeOwnership(AcceptCaseEoaVO acceptCaseVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.takeOwnership(acceptCaseVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
    }

    /**
     * This method is used to deliver an RX to a case
     * 
     * @param RecomDelvInfoServiceVO
     * @return void
     * @throws RMDBOException
     */
    @Override
    public void yankCase(AcceptCaseEoaVO acceptCaseVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.yankCase(acceptCaseVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
    }

    /**
     * This method is used to deliver an RX to a case
     * 
     * @param RecomDelvInfoServiceVO
     * @return void
     * @throws RMDBOException
     */
    @Override
    public String deliverRX(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.deliverRX(recomDelvInfoServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.CaseBOIntf#getToolOutputDetails
     * (java.lang.String)
     *//*
       * This Method is used for call the getToolOutputDetails method in
       * CaseDAOImpl
       */
    @Override
    public List getToolOutputDetails(String strCaseId) throws RMDBOException {
        ArrayList arlToolOutput;
        try {
            arlToolOutput = (ArrayList) objCaseEoaDAOIntf.getToolOutputDetails(strCaseId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlToolOutput;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.cases.bo.intf.CaseEoaBOIntf#getCloseCaseStatus(
     * java.lang.String)
     */
    @Override
    public boolean isCaseClosed(String caseId) throws RMDBOException {

        boolean isCaseClosed = false;
        try {
            isCaseClosed = objCaseEoaDAOIntf.isCaseClosed(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        }

        return isCaseClosed;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.cases.bo.intf.CaseEoaBOIntf#
     * generateCaseIdNumberNextValue()
     */
    @Override
    public String generateCaseIdNumberNextValue() throws RMDBOException {

        String caseId;

        try {
            caseId = objCaseEoaDAOIntf.generateCaseIdNumberNextValue();
        } catch (RMDDAOException ex) {
            throw ex;
        }

        return caseId;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.cases.bo.intf.CaseEoaBOIntf#closeCase(com.ge.
     * trans.eoa.services.asset.service.valueobjects.CloseCaseVO)
     */
    @Override
    public void closeCase(CloseCaseVO closeCaseVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.closeCase(closeCaseVO);
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_DETAILS);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /**
     * This method is used to modify an RX to a case
     * 
     * @param RecomDelvInfoServiceVO
     * @return void
     * @throws RMDBOException
     */
    @Override
    public String modifyRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {

            result = objCaseEoaDAOIntf.modifyRx(recomDelvInfoServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /**
     * This method is used to replace an RX to a case
     * 
     * @param RecomDelvInfoServiceVO
     * @return void
     * @throws RMDBOException
     */
    @Override
    public String replaceRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.replaceRx(recomDelvInfoServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /**
     * This method is used to replace an RX to a case
     * 
     * @param RepairCodeEoaDetailsVO
     * @return void
     * @throws RMDBOException
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getCloseOutRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType)
            throws RMDBOException {
        try {
            return objCaseEoaDAOIntf.getCloseOutRepairCodes(repairCodeInputType);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<RepairCodeEoaDetailsVO> getRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType)
            throws RMDBOException {
        try {
            return objCaseEoaDAOIntf.getRepairCodes(repairCodeInputType);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<RepairCodeEoaDetailsVO> getCaseRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType)
            throws RMDBOException {
        try {
            return objCaseEoaDAOIntf.getCaseRepairCodes(repairCodeInputType);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @Author:
     * @param:caseId,userName
     * @return:String
     * @throws:RMDBOException
     * @Description: This method reopens case by invoking
     *               caseeoadaoimpl.reOpenCase() method.
     */
    @Override
    public String reOpenCase(String caseID, String userName) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.reOpenCase(caseID, userName);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return result;

    }

    @Override
    public void scoreRx(ScoreRxEoaVO scoreRxEoaVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.scoreRx(scoreRxEoaVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public String getCustRxCaseIdPrefix(String caseId) throws RMDBOException {
        String customer = null;
        String custRxCasePrefix = null;
        try {

            customer = objCaseEoaDAOIntf.getCustomerNameForCase(caseId);
            custRxCasePrefix = objCaseEoaDAOIntf.getCustRxCaseIdPrefix(customer);
        } catch (RMDDAOException e) {
            throw e;
        }

        return custRxCasePrefix;
    }

    /**
     * This method is used for add case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDBOException
     */
    @Override
    public void addCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.addCaseRepairCodes(caseRepairCodeEoaVO);
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_CASE_REPAIR_CODES);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /**
     * This method is used for remove case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDBOException
     */
    @Override
    public void removeCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.removeCaseRepairCodes(caseRepairCodeEoaVO);
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_REMOVE_CASE_REPAIR_CODES);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /**
     * @Author:
     * @param:scoreRxEoaVO
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for saving the manual feedback
     */
    @Override
    public String saveSolutionFeedback(ScoreRxEoaVO scoreRxEoaVO) throws RMDBOException {
        String message = null;
        try {
            message = objCaseEoaDAOIntf.saveSolutionFeedback(scoreRxEoaVO);
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.GENERAL_BO_EXCEPTION);
            throw new RMDBOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
        return message;
    }

    @Override
    public List<SelectCaseHomeVO> getUserCases(FindCaseServiceVO findCaseServiceVO) throws RMDBOException {
        List<SelectCaseHomeVO> userCases = null;

        try {
            userCases = objCaseEoaDAOIntf.getUserCases(findCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return userCases;
    }

    @Override
    public CMPrivilegeVO hasCMPrivilege(String userId) throws RMDBOException {

        CMPrivilegeVO cmPrivilegeVO = null;
        try {
            cmPrivilegeVO = objCaseEoaDAOIntf.hasCMPrivilege(userId);
            if (null != cmPrivilegeVO && null != cmPrivilegeVO.getCmAliasName()
                    && !cmPrivilegeVO.getCmAliasName().equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                cmPrivilegeVO.setCMPrivilege(true);
            } else {
                cmPrivilegeVO.setCMPrivilege(false);
            }

            return cmPrivilegeVO;
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.CaseBOIntf#save
     * (com.ge.trans.rmd.services.cases.service.valueobjects.CaseInfoServiceVO,
     * java.lang.String)
     *//* This Method is used for call the save method in CaseDAOImpl */
    @Override
    public String save(CaseInfoServiceVO caseInfoServiceVO, String strUserName)
            throws RMDBOException, RMDNullObjectException {
        String strResult = RMDCommonConstants.EMPTY_STRING;
        try {
            if (RMDCommonUtility.checkNull(caseInfoServiceVO)) {
                throw new RMDNullObjectException();
            } else {
                strResult = objCaseEoaDAOIntf.save(caseInfoServiceVO, strUserName);
            }
        } catch (RMDDAOException e) {
            throw e;
        }
        return strResult;
    }

    @Override
    public List<CaseTypeEoaVO> getCaseType(CaseTypeEoaVO caseTypeVO) throws RMDBOException {
        List<CaseTypeEoaVO> caseTypeLst;
        try {
            caseTypeLst = objCaseEoaDAOIntf.getCaseType(caseTypeVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return caseTypeLst;
    }

    /* Added by Vamsee for Dispatch Functionlaity */

    /**
     * @Author :
     * @return :List<QueueDetailsVO>
     * @param :
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for fetching a list of Dynamic work
     *                   QueueNames from Data Base.
     */
    @Override
    public List<QueueDetailsVO> getQueueNames(String caseId) throws RMDBOException {

        List<QueueDetailsVO> queueDetailsVoList = null;
        try {
            queueDetailsVoList = objCaseEoaDAOIntf.getQueueNames(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return queueDetailsVoList;
    }

    /**
     * @Author :
     * @return :String
     * @param :queueId,caseId,userId
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for a dispatching case to dynamic work
     *                   queues selected by the user.
     */
    @Override
    public String dispatchCaseToWorkQueue(final long queueId, final String caseId, final String userId)
            throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.dispatchCaseToWorkQueue(queueId, caseId, userId);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return result;
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for adding Case notes for a given case.
     */
    @Override
    public String addNotesToCase(final AddNotesEoaServiceVO addnotesVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.addNotesToCase(addnotesVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return result;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used fetching unit Sticky notes details for a
     *                   given case.
     */
    @Override
    public StickyNotesDetailsVO fetchStickyUnitNotes(final String caseId) throws RMDBOException {
        StickyNotesDetailsVO details = null;
        try {
            details = objCaseEoaDAOIntf.fetchStickyUnitNotes(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return details;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used fetching case Sticky notes details for a
     *                   given case.
     */
    @Override
    public StickyNotesDetailsVO fetchStickyCaseNotes(final String caseId) throws RMDBOException {
        StickyNotesDetailsVO details = null;
        try {
            details = objCaseEoaDAOIntf.fetchStickyCaseNotes(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return details;
    }

    /**
     * @Author:
     * @param:
     * @return:List<UserNamesVO>
     * @throws:RMDBOException
     * @Description:This method fetches the list of users by invoking
     *                   caseeoadaoimpl.getCaseMgmtUsersDetails() method.
     */
    @Override
    public List<CaseMgmtUsersDetailsVO> getCaseMgmtUsersDetails() throws RMDBOException {
        List<CaseMgmtUsersDetailsVO> objUserNamesVO = null;
        try {
            objUserNamesVO = objCaseEoaDAOIntf.getCaseMgmtUsersDetails();

        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objUserNamesVO;
    }

    /**
     * @Author:
     * @param:userId,caseId
     * @return:String
     * @throws:RMDBOException
     * @Description:This method assigns owner particular caseId by invoking
     *                   caseeoaboimpl.assignCaseToUser() method.
     */
    @Override
    public String assignCaseToUser(final String userId, final String caseId) throws RMDBOException {
        String result = RMDServiceConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.assignCaseToUser(userId, caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }

        return result;
    }

    /**
     * @Author:
     * @param:caseId
     * @return:String
     * @throws:RMDBOException
     * @Description:This method return the owner for particular caseId by
     *                   invoking caseeoadaoimpl.getOwnerForCase() method.
     */
    @Override
    public SelectCaseHomeVO getCaseCurrentOwnerDetails(final String caseId) throws RMDBOException {
        SelectCaseHomeVO objDetailsVO = new SelectCaseHomeVO();
        try {
            objDetailsVO = objCaseEoaDAOIntf.getCaseCurrentOwnerDetails(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }

        return objDetailsVO;
    }

    /**
     * @Author:
     * @param:caseId
     * @return:List<CaseHistoryVO>
     * @throws:RMDBOException
     * @Description: This method return the set of activities for particular
     *               caseId by invoking caseeoadaoimpl.getCaseHistory() method.
     */
    @Override
	public List<HistoyVO> getCaseHistory(final String caseId)
			throws RMDBOException {
		List<HistoyVO> caseHistoryVOList = null;
        try {
            caseHistoryVOList = objCaseEoaDAOIntf.getCaseHistory(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return caseHistoryVOList;
    }

	@Override
	public List<ViewCaseVO> getViewCases(
			final FindCaseServiceVO objFindCaseServiceVO)
			throws RMDBOException {
		List<ViewCaseVO> SelectCaseHomeVOList = null;
		try {
			SelectCaseHomeVOList = objCaseEoaDAOIntf
					.getViewCases(objFindCaseServiceVO);
		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		} catch (Exception ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDCommonConstants.BOEXCEPTION);
			throw new RMDBOException(errorCode, new String[] {},
					ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
		}
		return SelectCaseHomeVOList;
	}
	
    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDBOException
     * @Description: This method return the cases for that asset by invoking
     *               caseeoadaoimpl.getAssetCases() method.
     */
    @Override
    public List<SelectCaseHomeVO> getAssetCases(final FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException {
        List<SelectCaseHomeVO> SelectCaseHomeVOList = null;
        try {
            SelectCaseHomeVOList = objCaseEoaDAOIntf.getAssetCases(objFindCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return SelectCaseHomeVOList;
    }

    /**
     * @Author :
     * @return :String
     * @param :unitStickyObjId,caseStickyObjId,
     *            applyLevel
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for removing a unit Level as well as
     *                   case Level Sticky Notes for a given case.
     */

    @Override
    public String removeStickyNotes(String unitStickyObjId, String caseStickyObjId, String applyLevel)
            throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.removeStickyNotes(unitStickyObjId, caseStickyObjId, applyLevel);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDBOEXCEPTION
     * @Description:This method is for updating case details based upon user
     *                   choice.
     */
    @Override
    public String updateCaseDetails(final FindCaseServiceVO FindCaseServiceVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.updateCaseDetails(FindCaseServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    @Override
    public List<CaseTypeEoaVO> getCaseTypes(CaseTypeEoaVO caseTypeVO) throws RMDBOException {
        List<CaseTypeEoaVO> caseTypeLst;
        try {
            caseTypeLst = objCaseEoaDAOIntf.getCaseTypes(caseTypeVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return caseTypeLst;
    }

    /**
     * @Author:
     * @return:List<SolutionBean>
     * @param String
     *            caseObjid,String language
     * @throws RMDBOException
     * @Description:This method is used for fetching the selected
     *                   solutions/Recommendations for a Case.
     */
    @Override
    public List<SolutionBean> getSolutionsForCase(String caseObjid, String language) throws RMDBOException {
        List<SolutionBean> caseDetailsVOsList = null;
        try {
            caseDetailsVOsList = objCaseEoaDAOIntf.getSolutionsForCase(caseObjid, language);
        } catch (RMDDAOException e) {
            throw e;
        }
        return caseDetailsVOsList;
    }

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO
     *            objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDBOException
     * @Description: This method in CaseResource.java is used to add a
     *               recommendation to a given Case.
     */
    @Override
    public String addRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.addRxToCase(objRecomDelvInfoServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO
     *            objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDBOException
     * @Description: This method in CaseResource.java is used to delete a
     *               recommendation to a given Case.
     */
    @Override
    public String deleteRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.deleteRxToCase(objRecomDelvInfoServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /**
     * @Author:
     * @param :String
     *            caseId,String language
     * @return:CaseInfoServiceVO
     * @throws:RMDBOException
     * @Description: This method is used for fetching the case Information.It
     *               accepts caseId as an Input Parameter and returns caseBean
     *               List.
     */
    @Override
    public CaseInfoServiceVO getCaseInfo(String caseId, String language) throws RMDBOException {

        CaseInfoServiceVO objCaseInfoEoaServiceVO = null;
        try {
            objCaseInfoEoaServiceVO = objCaseEoaDAOIntf.getCaseInfo(caseId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objCaseInfoEoaServiceVO;
    }

    /**
     * @Author :
     * @return :List<RxStatusHistoryVO>
     * @param :serviceReqId
     * @throws :RMDBOException
     * @Description:This method fetches the RxStatus History based on
     *                   servicerReq id by invoking
     *                   caseeoadaoimpl.getRxstatusHistory() method.
     */
    @Override
    public List<RxStatusHistoryVO> getRxstatusHistory(String serviceReqId) throws RMDBOException {
        List<RxStatusHistoryVO> rxStatusHistoryList = null;

        try {
            rxStatusHistoryList = objCaseEoaDAOIntf.getRxstatusHistory(serviceReqId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return rxStatusHistoryList;
    }

    /**
     * @Author :
     * @return :List<RxHistoryVO>
     * @param :caseObjId
     * @throws :RMDBOException
     * @Description:This method fetches the Rx History based on caseObj id by
     *                   invoking caseeoadaoimpl.getRxHistory() method.
     */
    @Override
    public List<RxHistoryVO> getRxHistory(String caseObjId) throws RMDBOException {
        List<RxHistoryVO> rxHistoryVOList = null;
        try {
            rxHistoryVOList = objCaseEoaDAOIntf.getRxHistory(caseObjId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return rxHistoryVOList;
    }

    /**
     * @Author :
     * @return :List<CustomerFdbkVO>
     * @param :caseObjId
     * @throws :RMDBOException
     * @Description:This method is used fetching the ServiceReqId &
     *                   CustFdbkObjId based on caseObj id by invoking
     *                   caseeoadaoimpl.getServiceReqId() method.
     */
    @Override
    public List<CustomerFdbkVO> getServiceReqId(String caseObjId) throws RMDBOException {
        List<CustomerFdbkVO> objCustomerFdbkList = new ArrayList<CustomerFdbkVO>();
        try {
            objCustomerFdbkList = objCaseEoaDAOIntf.getServiceReqId(caseObjId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objCustomerFdbkList;
    }

    /**
     * @Author :
     * @return :String
     * @param :caseObjId
     * @throws :RMDBOException
     * @Description:This method is used fetching the Good Feedback based on
     *                   caseObj id by invoking caseeoadaoimpl.getClosureFdbk()
     *                   method.
     */
    @Override
    public String getClosureFdbk(String rxCaseId) throws RMDBOException {
        String objClosureFdbk = null;
        try {
            objClosureFdbk = objCaseEoaDAOIntf.getClosureFdbk(rxCaseId);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objClosureFdbk;
    }

    /**
     * @Author :
     * @return :List<CloseOutRepairCodeVO>
     * @param :custFdbkObjId
     *            , serviceReqId
     * @throws :RMDBOException
     * @Description:This method is used fetching the CloseOut Repair Codes based
     *                   on custFdbkObj id & serviceReqId by invoking
     *                   caseeoadaoimpl.getCloseOutRepairCode() method.
     */
    @Override
    public List<CloseOutRepairCodeVO> getCloseOutRepairCode(String custFdbkObjId, String servicerRqId)
            throws RMDBOException {
        List<CloseOutRepairCodeVO> closeOutRepairCodeVOList = new ArrayList<CloseOutRepairCodeVO>();
        try {
            closeOutRepairCodeVOList = objCaseEoaDAOIntf.getCloseOutRepairCode(custFdbkObjId, servicerRqId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return closeOutRepairCodeVOList;
    }

    /**
     * @Author :
     * @return :List<CloseOutRepairCodeVO>
     * @param :caseId
     * @throws :RMDBOException
     * @Description:This method fetches the Attached Details based on case id by
     *                   invoking caseeoadaoimpl.getAttachedDetails() method.
     */
    @Override
    public List<CloseOutRepairCodeVO> getAttachedDetails(String caseId) throws RMDBOException {
        List<CloseOutRepairCodeVO> attachedDetailsList = new ArrayList<CloseOutRepairCodeVO>();
        try {
            attachedDetailsList = objCaseEoaDAOIntf.getAttachedDetails(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return attachedDetailsList;
    }

    /**
     * @Author :
     * @return :String
     * @param :caseObjId
     * @throws :RMDBOException
     * @Description:This method is used fetching the Rx Notes based on caseObj
     *                   id by invoking caseeoadaoimpl.getRxNote() method.
     */
    @Override
    public String getRxNote(String caseObjId) throws RMDBOException {
        String rxNote = null;
        try {
            rxNote = objCaseEoaDAOIntf.getRxNote(caseObjId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return rxNote;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching pendingFdbkServiceStatus
     *               by invoking caseEoaDAOIntf.pendingFdbkServiceStatus()
     *               method.
     */
    @Override
    public List<RecomDelvInfoServiceVO> pendingFdbkServiceStatus(String caseId) throws RMDBOException {
        List<RecomDelvInfoServiceVO> arlRecomDelvInfoServiceVO = null;
        try {
            arlRecomDelvInfoServiceVO = objCaseEoaDAOIntf.pendingFdbkServiceStatus(caseId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlRecomDelvInfoServiceVO;
    }

    /**
     * @Author:
     * @param:String fdbkObjid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching ServiceReqId by invoking
     *               caseEoaDAOIntf.getServiceReqIdStatus() method.
     */
    @Override
    public String getServiceReqIdStatus(String fdbkObjid) throws RMDBOException {

        String serviceReqId = null;
        try {
            serviceReqId = objCaseEoaDAOIntf.getServiceReqIdStatus(fdbkObjid);
        } catch (RMDDAOException e) {
            throw e;
        }
        return serviceReqId;
    }

    /**
     * @Author:
     * @param:String caseObjid,String
     *                   rxObjid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching DeliveryDate by invoking
     *               caseEoaDAOIntf.getDelvDateForRx() method.
     */
    @Override
    public String getDelvDateForRx(String caseObjid, String rxObjid) throws RMDBOException {
        String deiveryDate = null;
        try {
            deiveryDate = objCaseEoaDAOIntf.getDelvDateForRx(caseObjid, rxObjid);
        } catch (RMDDAOException e) {
            throw e;
        }
        return deiveryDate;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching requestId by invoking
     *               caseEoaDAOIntf.getT2Req() method.
     */
    @Override
    public String getT2Req(String caseId) throws RMDBOException {
        String reqStatus = null;
        try {
            reqStatus = objCaseEoaDAOIntf.getT2Req(caseId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return reqStatus;
    }

    /**
     * @Author:
     * @param:String caseObjid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching unit shipping details by
     *               invoking caseEoaDAOIntf.getUnitShipDetails() method.
     */
    @Override
    public String getUnitShipDetails(String caseObjid) throws RMDBOException {
        String unitShippingDetails = null;
        try {
            unitShippingDetails = objCaseEoaDAOIntf.getUnitShipDetails(caseObjid);
        } catch (RMDDAOException e) {
            throw e;
        }
        return unitShippingDetails;
    }

    /**
     * @Author:
     * @param:String caseid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching case Score by invoking by
     *               invoking caseEoaDAOIntf..getCaseScore() method.
     */
    @Override
    public List<RecomDelvInfoServiceVO> getCaseScore(String caseid) throws RMDBOException {
        List<RecomDelvInfoServiceVO> arlCaseScore = null;
        try {
            arlCaseScore = objCaseEoaDAOIntf.getCaseScore(caseid);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlCaseScore;
    }

    /**
     * @Author:
     * @param:String rxObjid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching readyToDeliver date by
     *               invoking caseEoaDAOIntf.getReadyToDelv() method.
     */

    @Override
    public String getReadyToDelv(String rxObjid) throws RMDBOException {
        String readyDeliver = null;
        try {
            readyDeliver = objCaseEoaDAOIntf.getReadyToDelv(rxObjid);
        } catch (RMDDAOException e) {
            throw e;
        }
        return readyDeliver;
    }

    /**
     * @Author:
     * @param:String caseId,String
     *                   rxObjid
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDServiceException
     * @Description:This method is used for fetching pending recommendation
     *                   details by invoking
     *                   caseEoaBoIntf.getPendingRcommendation() method.
     */
    @Override
    public RecomDelvInfoServiceVO getPendingRcommendation(String caseid) throws RMDBOException {
        RecomDelvInfoServiceVO objDelvInfoEoaServiceVO = null;
        try {
            objDelvInfoEoaServiceVO = objCaseEoaDAOIntf.getPendingRcommendation(caseid);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objDelvInfoEoaServiceVO;
    }

    /**
     * @Author:
     * @param:String customerName
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for checking Whether delivery mechanism
     *               is present for particular Customer are not by invoking
     *               caseEoaDAOIntf.checkForDelvMechanism() method.
     */
    @Override
    public String checkForDelvMechanism(String customerName) throws RMDBOException {
        String result = null;
        try {
            result = objCaseEoaDAOIntf.checkForDelvMechanism(customerName);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /**
     * @Author:
     * @param:String caseObjid,String
     *                   rxObjid,String fromScreen,String custFdbkObjId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching Msdc Notes by invoking
     *               caseEoaDAOIntf.getMsdcNotes() method.
     */
    @Override
    public String getMsdcNotes(String caseObjid, String rxObjid, String fromScreen,String custFdbkObjId) throws RMDBOException {
        String msdcNotes = null;
        try {
            msdcNotes = objCaseEoaDAOIntf.getMsdcNotes(caseObjid, rxObjid,fromScreen,custFdbkObjId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return msdcNotes;
    }

    /**
     * @Author :
     * @return :CustomerFdbkVO
     * @param :caseObjId
     * @throws :RMDBOException
     * @Description:This method is used for fetching closure details based on
     *                   caseObj id by invoking
     *                   caseeoadaoimpl.getClosureDetails() method..
     */
    @Override
    public CustomerFdbkVO getClosureDetails(String caseObjId) throws RMDBOException {
        CustomerFdbkVO objCustomerFdbk = new CustomerFdbkVO();
        try {
            objCustomerFdbk = objCaseEoaDAOIntf.getClosureDetails(caseObjId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objCustomerFdbk;
    }

    /**
     * @Author :
     * @return: String
     * @param coreRxEoaVO
     * @throws :RMDBOException
     * @Description:This method does eservice validation invoking
     *                   doEserviceValidation()method in CaseEoaDAOImpl.java.
     */
    @Override
    public String doEserviceValidation(ScoreRxEoaVO objScoreRxEoaVO) throws RMDBOException {
        String result = null;
        try {
            result = objCaseEoaDAOIntf.doEServiceValidation(objScoreRxEoaVO);
        } catch (Exception e) {
            result = e.getMessage();
            LOG.debug(e);
        }

        return result;
    }

    @Override
    public String checkForContollerConfig(VehicleConfigVO objVehicleConfigVO) throws RMDBOException {
        String result = null;
        try {
            result = objCaseEoaDAOIntf.checkForContollerConfig(objVehicleConfigVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /**
     * @Author:
     * @param customerId
     * @return:List<ElementVO>
     * @throws RMDBOException
     * @Description: This method is used for fetching the list of all Road
     *               Initials based upon CustomerId.
     */
    @Override
    public List<ElementVO> getRoadNumberHeaders(String customerId) throws RMDBOException {
        List<ElementVO> arlElementVOs = new ArrayList<ElementVO>();
        try {
            arlElementVOs = objCaseEoaDAOIntf.getRoadNumberHeaders(customerId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlElementVOs;
    }

    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used to checking foe maximum numbers of
     *               units on which mass apply rx can be applied.
     */
    @Override
    public String getMaxMassApplyUnits() throws RMDBOException {
        String maxLimit = null;
        try {
            maxLimit = objCaseEoaDAOIntf.getMaxMassApplyUnits();
        } catch (RMDDAOException e) {
            throw e;
        }
        return maxLimit;
    }

    /**
     * @Author:
     * @param :MassApplyRxVO
     *            objMassApplyRxVO
     * @return:List<ViewLogVO>
     * @throws:RMDBOException
     * @Description:This method is used for creating a new Case and delivering
     *                   Recommendations for the assets selected by user.
     */
    @Override
    public List<ViewLogVO> massApplyRx(MassApplyRxVO objMassApplyRxVO) throws RMDBOException {
        List<ViewLogVO> arlViewLogVOs = new ArrayList<ViewLogVO>();
        try {
            arlViewLogVOs = objCaseEoaDAOIntf.massApplyRx(objMassApplyRxVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlViewLogVOs;
    }

    /**
     * This method is used to get the list of RXs for which append and close
     * buttons are enabled
     * 
     * @param scoreRxEoaVO
     * @throws RMDBOException
     */
    @Override
    public List<String> getEnabledRxsAppendClose(CaseAppendServiceVO caseAppendServiceVO) throws RMDBOException {

        boolean isCaseTypeRFTrigger = false;
        boolean isCaseClosed = false;
        SelectCaseHomeVO selectCaseHomeVO = null;
        List<String> disabledRxs = new ArrayList<String>();
        String caseId = caseAppendServiceVO.getCaseId();
        String caseType = caseAppendServiceVO.getCaseType();
        String owner = null;

        boolean isLoggedinUseOwner = false;

        try {
            if (caseId != null && !("").equals(caseId)) {
                selectCaseHomeVO = objCaseEoaDAOIntf.getCaseCurrentOwnerDetails(caseId);
                owner = selectCaseHomeVO.getStrOwner();
                if (caseAppendServiceVO.getUserId() != null
                        && caseAppendServiceVO.getUserId().equalsIgnoreCase(owner)) {
                    isLoggedinUseOwner = true;
                }
            }
            if (isLoggedinUseOwner) {
                if (caseType != null && ("RF Trigger").equalsIgnoreCase(caseType)) {
                    isCaseTypeRFTrigger = true;
                    disabledRxs.add("ALL");
                }
                if (!isCaseTypeRFTrigger) {
                    isCaseClosed = objCaseEoaDAOIntf.isCaseClosed(caseId);
                    if (isCaseClosed)
                        disabledRxs.add("ALL");
                }
                // If case Type is not RF trigger then check for delivered RXs
                if (!isCaseClosed && !isCaseTypeRFTrigger) {
                    disabledRxs = objCaseEoaDAOIntf.getDeliveredRxs(caseAppendServiceVO);
                }
            } else {
                disabledRxs.add("ALL");
            }
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return disabledRxs;
    }

    /**
     * This method is used to get the list of previous cases for an asset
     * 
     * @param objFindCaseServiceVO
     * @throws RMDBOException
     */
    @Override
    public List<SelectCaseHomeVO> getPreviousCases(FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException {
        List<SelectCaseHomeVO> prevCases = null;

        try {
            prevCases = objCaseEoaDAOIntf.getPreviousCases(objFindCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }

        return prevCases;
    }

    @Override
    public void appendRx(CaseAppendServiceVO caseAppendServiceVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.appendRx(caseAppendServiceVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }

    }

    /**
     * This method returns a count of Open FL work order and will be called
     * while closing the Case will return the open work order count from the
     * eservices.
     * 
     * @param lmsLocoId
     * @return
     * @throws RMDBOException
     */
    @Override
    public int getOpenFLCount(String lmsLocoId) throws RMDBOException {

        int openFLCount = RMDCommonConstants.INT_CONST_ZERO;
        try {

            openFLCount = objCaseEoaDAOIntf.getOpenFLCount(lmsLocoId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return openFLCount;
    }

    @Override
    public String getLmsLocoID(String caseId) throws RMDBOException {
        String result = null;
        try {
            result = objCaseEoaDAOIntf.getLmsLocoID(caseId);
        } catch (RMDDAOException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return result;
    }

    @Override
    public List<ToolOutputEoaServiceVO> getToolOutput(String strCaseId) throws RMDBOException {
        List<ToolOutputEoaServiceVO> arlToolOutput = new ArrayList<ToolOutputEoaServiceVO>();
        try {
            arlToolOutput = objCaseEoaDAOIntf.getToolOutput(strCaseId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlToolOutput;
    }

    @Override
    public String getCaseTitle(String strCaseId) throws RMDBOException {
        String caseTitle = null;
        try {
            caseTitle = objCaseEoaDAOIntf.getCaseTitle(strCaseId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return caseTitle;
    }

    @Override
    public String moveToolOutput(CaseAppendServiceVO caseAppendServiceVO) throws RMDBOException {
        String idNumb = null;
        try {
            idNumb = objCaseEoaDAOIntf.moveToolOutput(caseAppendServiceVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return idNumb;
    }

    @Override
    public String saveToolOutputActEntry(ToolOutputActEntryVO objToolOutputActEntryVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.saveToolOutputActEntry(objToolOutputActEntryVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return result;
    }

    @Override
    public List<RepairCodeEoaDetailsVO> getRepairCodes(String repairCode) throws RMDBOException {
        try {
            return objCaseEoaDAOIntf.getRepairCodes(repairCode);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public String moveDeliverToolOutput(String userId, String currCaseId, String newCaseId, String RxId,
            String assetNumber, String assetGrpName, String customerId, String ruleDefId, String toolId,
            String caseType, String toolObjId) throws RMDBOException {
        String message = null;
        try {
            message = objCaseEoaDAOIntf.moveDeliverToolOutput(userId, currCaseId, newCaseId, RxId, assetNumber,
                    assetGrpName, customerId, ruleDefId, toolId, caseType, toolObjId);

        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return message;
    }

    @Override
    public boolean activeRxExistsInCase(String caseId) throws RMDBOException {
        boolean activeRxExistsInCase = false;
        try {
            activeRxExistsInCase = objCaseEoaDAOIntf.activeRxExistsInCase(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return activeRxExistsInCase;
    }

    @Override
    public Map<String, List<String>> getEnabledUnitRxsDeliver(String customerId, String assetGroupName, String assetNumber,
            String caseId, String caseType,String currentUser) throws RMDBOException {
        Map<String, List<String>> rxs = null;
        try {

            rxs = objCaseEoaDAOIntf.getEnabledUnitRxsDeliver(customerId, assetGroupName, assetNumber, caseId, caseType,currentUser);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return rxs;
    }

    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDBOEXCEPTION
     * @Description:This method is for updating case title
     */
    @Override
    public String updateCaseTitle(final FindCaseServiceVO FindCaseServiceVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.updateCaseTitle(FindCaseServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDBOException
     * @Description: This method return the cases for that asset by invoking
     *               caseeoadaoimpl.getAssetCases() method.
     */
    @Override
    public List<SelectCaseHomeVO> getHeaderSearchCases(final FindCaseServiceVO objFindCaseServiceVO)
            throws RMDBOException {
        List<SelectCaseHomeVO> SelectCaseHomeVOList = null;
        try {
            SelectCaseHomeVOList = objCaseEoaDAOIntf.getHeaderSearchCases(objFindCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return SelectCaseHomeVOList;
    }

    /**
     * @Author :
     * @return :RxDetailsVO
     * @param :
     *            caseObjId,vehicleObjId
     * @throws :RMDBOException
     * @Description: This method is used to get Rx Details of the Case by
     *               invoking caseEoaDAOIntf.getRxDetails() method.
     */
    @Override
    public RecommDetailsVO getRxDetails(String caseObjId, String vehicleObjId) throws RMDBOException {

        RecommDetailsVO objRecommDetailsVO = null;
        try {
            objRecommDetailsVO = objCaseEoaDAOIntf.getRxDetails(caseObjId, vehicleObjId);

        } catch (RMDDAOException ex) {
            throw ex;
        }
        return objRecommDetailsVO;

    }

    /**
     * @Author:Vamsee
     * @param :UnitShipDetailsVO
     * @return :String
     * @throws RMDDAOException
     * @Description:This method is used for Checking weather unit is Shipped or
     *                   not.
     */

    @Override
    public String checkForUnitShipDetails(UnitShipDetailsVO objUnitShipDetailsVO) throws RMDBOException {
        String unitShippingDetails = null;
        try {
            unitShippingDetails = objCaseEoaDAOIntf.checkForUnitShipDetails(objUnitShipDetailsVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return unitShippingDetails;
    }

    /**
     * @Author:
     * @param:FindCasesDetailsVO
     * @return:List<FindCasesDetailsVO>
     * @throws:RMDBOException
     * @Description: This method is used to get Find Cases Details.
     */
    @Override
    public List<FindCasesDetailsVO> getFindCases(FindCasesVO objFindCasesVO) throws RMDBOException {
        List<FindCasesDetailsVO> objFindCasesDetailsVO = null;
        try {
            objFindCasesDetailsVO = objCaseEoaDAOIntf.getFindCases(objFindCasesVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objFindCasesDetailsVO;
    }

    /**
     * @Author:Mohamed
     * @param :serviceReqId,
     *            lookUpDays
     * @return :List<MaterialUsageVO>
     * @throws RMDServiceException
     * @Description:This method is used to fetch the list of part for particular
     *                   case.
     */
    @Override
    public List<MaterialUsageVO> getMaterialUsage(String serviceReqId, String lookUpDays) throws RMDBOException {
        List<MaterialUsageVO> objMaterialUsageVOList = new ArrayList<MaterialUsageVO>();
        try {
            objMaterialUsageVOList = objCaseEoaDAOIntf.getMaterialUsage(serviceReqId, lookUpDays);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objMaterialUsageVOList;
    }

    @Override
    public void mergeRx(CaseMergeServiceVO caseMergeServiceVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.mergeRx(caseMergeServiceVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }

    }

    /**
     * @Author :Mohamed
     * @return :List<FindCaseServiceVO>
     * @param :
     *            FindCaseServiceVO
     * @throws :RMDWebException
     * @Description: This method is used to check whether the rx is closed or
     *               not
     */
    @Override
    public List<FindCaseServiceVO> getRxDetailsForReClose(FindCaseServiceVO objFindCaseServiceVO)
            throws RMDBOException {
        List<FindCaseServiceVO> objFindCaseServiceVOList = new ArrayList<FindCaseServiceVO>();
        try {
            objFindCaseServiceVOList = objCaseEoaDAOIntf.getRxDetailsForReClose(objFindCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objFindCaseServiceVOList;
    }

    @Override
    public void updateCloseCaseResult(ReCloseVO objReCloseVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.updateCloseCaseResult(objReCloseVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }

    }

    @Override
    public void reCloseResetFaults(ReCloseVO reCloseVO) throws RMDBOException {
        try {
            objCaseEoaDAOIntf.reCloseResetFaults(reCloseVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }

    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for adding Case notes for a given case.
     */
    @Override
    public String addNotesToUnit(final AddNotesEoaServiceVO addnotesVO) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaDAOIntf.addNotesToUnit(addnotesVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return result;
    }

    @Override
    public StickyNotesDetailsVO fetchStickyUnitLevelNotes(String assetNumber, String customerId, String assetGrpName)
            throws RMDBOException {
        StickyNotesDetailsVO details = null;
        try {
            details = objCaseEoaDAOIntf.fetchStickyUnitLevelNotes(assetNumber, customerId, assetGrpName);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return details;
    }

    /**
     * @Author :Vamshi
     * @return :String
     * @param :CaseRepairCodeEoaVO
     *            objCaseRepairCodeEoaVO
     * @throws :RMDServiceException
     * @Description:This method is responsible for Casting the GPOC Users Vote.
     */

    @Override
    public String castGPOCVote(CaseRepairCodeEoaVO objCaseRepairCodeEoaVO) throws RMDBOException {
        String result = null;
        try {
            result = objCaseEoaDAOIntf.castGPOCVote(objCaseRepairCodeEoaVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return result;
    }
    /**
     * @Author :Vamshi
     * @return :String
     * @param :String
     *            caseObjId
     * @throws :RMDServiceException
     * @Description:This method is responsible for fetching previously Casted
     *                   vote.
     */
    @Override
    public String getPreviousVote(String caseObjId) throws RMDBOException {
        String previousVote = null;
        try {
            previousVote = objCaseEoaDAOIntf.getPreviousVote(caseObjId);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return previousVote;
    }

    @Override
    public List<CasesHeaderVO> getHeaderSearchCasesData(FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException {
      List<CasesHeaderVO> SelectCaseHomeVOList = null;
        try {
            SelectCaseHomeVOList = objCaseEoaDAOIntf.getHeaderSearchCasesData(objFindCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return SelectCaseHomeVOList;

    }

	@Override
	public String getDeliverRxURL(String caseId) throws RMDBOException {
		String result = null;
        try {
        	result = objCaseEoaDAOIntf.getDeliverRxURL(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return result;
	}

	@Override
	public List<CaseTrendVO> getOpenCommRxCount() throws RMDBOException {
		List<CaseTrendVO> getOpenCommRxCount = null;
		try {
			getOpenCommRxCount = objCaseEoaDAOIntf.getOpenCommRxCount();
		} catch (RMDDAOException e) {
			throw e;
		}
		return getOpenCommRxCount;
	}

	@Override
	public List<CaseConvertionVO> getCaseConversionDetails()
			throws RMDBOException {
		List<CaseConvertionVO> getOpenCommRxCount = null;
		try {
			getOpenCommRxCount = objCaseEoaDAOIntf.getCaseConversionDetails();
		} catch (RMDDAOException e) {
			throw e;
		}
		return getOpenCommRxCount;
	}

	@Override
	public String getCaseConversionPercentage() throws RMDBOException {
		String percentage = null;
		try {
			percentage = objCaseEoaDAOIntf.getCaseConversionPercentage();
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return percentage;
	}

	@Override
	public List<CaseConvertionVO> getTopNoActionRXDetails()
			throws RMDBOException {
		List<CaseConvertionVO> getTopNoActionRXDetails = null;
		try {
			getTopNoActionRXDetails = objCaseEoaDAOIntf.getTopNoActionRXDetails();
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return getTopNoActionRXDetails;
	}

	@Override
	public List<GeneralNotesEoaServiceVO> getCommNotesDetails() throws RMDBOException {
		List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = null;
		try {
		    arlGeneralNotesEoaServiceVO = objCaseEoaDAOIntf.getCommNotesDetails();
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return arlGeneralNotesEoaServiceVO;
	}
	
    @Override
    public boolean getAddRepCodeDetails(String caseId) throws RMDBOException {
    	boolean retValue = true;
        try {
        	retValue = objCaseEoaDAOIntf.getAddRepCodeDetails(caseId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return retValue;
    }
    
    @Override
    public boolean getLookUpRepCodeDetails(String repairCodeList) throws RMDBOException {
    	boolean retValue = true;
        try {
        	retValue = objCaseEoaDAOIntf.getLookUpRepCodeDetails(repairCodeList);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return retValue;
    }

	@Override
	public List<CaseScoreRepairCodeVO> getCaseScoreRepairCodes(String rxCaseId)
			throws RMDBOException {
        List<CaseScoreRepairCodeVO> closeOutRepairCodeVOList = new ArrayList<CaseScoreRepairCodeVO>();
        try {
            closeOutRepairCodeVOList = objCaseEoaDAOIntf.getCaseScoreRepairCodes(rxCaseId);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return closeOutRepairCodeVOList;
    }

    @Override
    public List<String> validateVehBoms(String customer, String rnh, String rn,
            String rxObjId,String fromScreen) throws RMDBOException {
        List<String> result=null;
        try {
            result = objCaseEoaDAOIntf.validateVehBoms(customer,rnh,rn,rxObjId,fromScreen);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return result;
    }


    /**
     * This method is used to get the RXs which are added for a case
     * 
     * @param String
     *            caseId
     * @return void
     * @throws RMDBOException
     */
    @Override
    public List<RecomDelvInfoServiceVO> getCaseRXDelvDetails(String caseId) throws RMDBOException {
        // TODO Auto-generated method stub
        try {
            return objCaseEoaDAOIntf.getCaseRXDelvDetails(caseId);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
    }

}

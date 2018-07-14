package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.cases.service.valueobjects.ControllerModelVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultRequestVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SortOrderLkBackDaysVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultMobileServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;

public interface FaultLogDAOIntf extends BaseLogDAOIntf {

    public void getControllerSrcId(FaultRequestVO objFaultRequestVO) throws Exception;

    public FaultServiceVO getFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception;
    
    public FaultMobileServiceVO getMobileFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception;

    public HashMap<String, ControllerModelVO> getControllerSrcAndModel() throws Exception;

    public ControllerModelVO getControllerSrcAndModel(String custID, String rnh, String rn) throws Exception;

    public Map<String,HashMap<String, HashMap<String,String>>> getCustLevelDetails() throws Exception;
    
    public HashMap<String, List<String>> getCustLevelMobileDetails() throws Exception;

    public HashMap<String, SortOrderLkBackDaysVO> getSortOrderLookbackDays() throws Exception;

    public Map<String, FaultHeaderVO> getHeaderDetails(boolean isCustColEnabled) throws Exception;

    public FaultHeaderVO getDHMSHeaderDetails(boolean isCustColEnabled) throws Exception;

    public Map<String, FaultHeaderVO> getOilHeaderDetails(boolean isCustColEnabled) throws Exception;

    public HashMap<String, String> getDataScreenFilters() throws Exception;
}

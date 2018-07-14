package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.HashMap;

import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderVO;

public interface BaseLogDAOIntf {

    public HashMap getHeaderDetails(String strModelId) throws Exception;

    public FaultHeaderVO getDPEABHeaders(boolean isCustColEnabled) throws Exception;

    public HashMap getPaginationDetails() throws Exception;

    public HashMap<String, String> getAllModels() throws Exception;

    public HashMap<String, String> getSortOrders() throws Exception;

    public HashMap<String, String> getLookbackDays() throws Exception;
}

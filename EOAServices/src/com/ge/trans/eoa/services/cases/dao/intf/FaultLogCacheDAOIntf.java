package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import com.ge.trans.rmd.exception.RMDDAOException;

public interface FaultLogCacheDAOIntf {
    public HashMap<String, ArrayList<String>> getAllDefaultRecordTypes() throws Exception;

    public HashMap<String, String> getAllRecordTypes() throws Exception;

    public Map<String, ArrayList<HashMap<String, String>>> getCommonQueryMap() throws Exception;

    public Map<String, String> getSelectQueryMap() throws Exception;

    public String getDataScreenLimit() throws RMDDAOException;
}

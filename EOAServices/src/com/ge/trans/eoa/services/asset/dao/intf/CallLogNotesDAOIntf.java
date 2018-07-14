package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.CallLogNotesVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface CallLogNotesDAOIntf {

    public List<CallLogNotesVO> getCallLogNotes(CallLogNotesVO callLogNotesVO) throws RMDDAOException;

}

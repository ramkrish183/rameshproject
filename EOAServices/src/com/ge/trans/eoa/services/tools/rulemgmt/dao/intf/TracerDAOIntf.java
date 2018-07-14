package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;

public interface TracerDAOIntf {

    FaultDataDetailsServiceVO loadFaults(TracerServiceVO faultServiceVO);

}

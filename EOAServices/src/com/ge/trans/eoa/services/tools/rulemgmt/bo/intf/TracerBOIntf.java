package com.ge.trans.eoa.services.tools.rulemgmt.bo.intf;

import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;

public interface TracerBOIntf {

    FaultDataDetailsServiceVO loadFaults(TracerServiceVO faultServiceVO);

}

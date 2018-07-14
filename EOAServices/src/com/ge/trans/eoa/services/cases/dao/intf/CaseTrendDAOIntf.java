package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface CaseTrendDAOIntf {
	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching delivered open rx count for past 8 days.
	 */
	public List<CaseTrendVO> getOpenRXCount() throws RMDDAOException;
	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching created case type count for past 24 hours.
	 */
	public List<CaseTrendVO> getCaseTrend() throws RMDDAOException;
	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDWebException
	 * @Description: This method is used for fetching created case type count for past 7 days.
	 */
	public List<CaseTrendVO> getCaseTrendStatistics() throws RMDDAOException;
	/**
	 * @Author:
	 * @param:
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching general note info which has flag as Y.
	 */
	public  List<GeneralNotesEoaServiceVO> getGeneralNotesDetails() throws RMDDAOException;
	

}

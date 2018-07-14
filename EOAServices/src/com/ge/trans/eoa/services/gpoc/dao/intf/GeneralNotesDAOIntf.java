/**
 * ============================================================
 * Classification: GE Confidential
 * File : GeneralNotesDAOIntf.java
 * Description : DAO Interface for general and comm ntes for GPOC turnover
 *
 * Package : com.ge.trans.rmd.services.admin.dao.intf
 * Author : General Electric
 * Last Edited By : sonal gupta
 * Version : 1.0
 * Created on : March 22, 2017
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.gpoc.dao.intf;
import java.util.List;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author : General Electric
 * @Version : 1.0
 * @DateCreated : March 10, 2017
 * @DateModified : March 10, 2017
 * @ModifiedBy : sonal gupta
 * @Contact :
 * @Description : DAO Interface for general and comm ntes for GPOC turnover
 * @History :
 ******************************************************************************/

public interface GeneralNotesDAOIntf {
	/**
    * @Author:
    * @param generalnotesVO
    * @return String
    * @throws RMDDAOException
    * @Description:
    */
	
	public String addGeneralNotes(GeneralNotesEoaServiceVO generalnotesVO) throws RMDDAOException;
	/**
	    * @Author:
	    * @param language
	    * @return String
	    * @throws RMDDAOException
	    * @Description:
	    */
	
	public List<GeneralNotesEoaServiceVO> showAllGeneralNotes(final String language) throws RMDDAOException;
	/**
	    * @Author:
	    * @param generalnotesVO
	    * @return String
	    * @throws RMDDAOException
	    * @Description:
	    */
	
	
	public String removeGeneralNotes(List<GeneralNotesEoaServiceVO> generalnotesVO) throws RMDDAOException;
	
	//started for comm notes
	/**
	    * @Author:
	    * @param generalnotesVO
	    * @return
	    * @throws String
	    * @Description:
	    */
	
	public String addCommNotes(GeneralNotesEoaServiceVO generalnotesVO) throws RMDDAOException;
	/**
	    * @Author:
	    * @param List<GeneralNotesEoaServiceVO>
	    * @return
	    * @throws language
	    * @Description:
	    */
	public List<GeneralNotesEoaServiceVO> showAllcommnotes(final String language) throws RMDDAOException;
	/**
	    * @Author:
	    * @param generalnotesVO
	    * @return String
	    * @throws RMDDAOException
	    * @Description:
	    */
	
	
	public String removeCommNotes(List<GeneralNotesEoaServiceVO> generalnotesVO) throws RMDDAOException;
	/**
     * @param List<GeneralNotesEoaServiceVO>
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to update existing general/comm notes
     *              visibility flag value
     */
	public String updateGenOrCommNotes(
            List<GeneralNotesEoaServiceVO> generalnotesVO) throws RMDDAOException;
	
	//end for comm notes
	

}

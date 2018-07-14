/**
 * ============================================================
 * Classification: GE Confidential
 * File : GeneralNotesEoaBOIntf.java
 * Description : BO Interface for General and comm notes for GPOC Turnover
 *
 * Package : com.ge.trans.eoa.services.gpoc.bo.intf;
 * Author : General Electric
 * Last Edited By : Sonal Gupta
 * Version : 1.0
 * Created on : March 22, 2017
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.gpoc.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface GeneralNotesEoaBOIntf {
	
	//started for general notes
	/**
     * @Author:
     * @param generalnotesVO
     * @return string
     * @throws RMDBOException
     * @Description:
     */
	 public String addGeneralNotes(GeneralNotesEoaServiceVO generalnotesVO) throws RMDBOException;
	 /**
	     * @Author:
	     * @param language
	     * @return List<GeneralNotesEoaServiceVO>
	     * @throws RMDBOException
	     * @Description:
	     */
	 public List<GeneralNotesEoaServiceVO> showAllGeneralNotes(final String language) throws RMDBOException;
	 /**
	     * @Author:
	     * @param generalnotesVO
	     * @return string
	     * @throws RMDBOException
	     * @Description:
	     */
	 public String removeGeneralNotes(List<GeneralNotesEoaServiceVO> generalnotesVO) throws RMDBOException;
	//end for general notes
	 
	 //started for comm Notes
	 
	 /**
	     * @Author:
	     * @param generalnotesVO
	     * @return string
	     * @throws RMDBOException
	     * @Description:
	     */
	 public String addCommNotes(GeneralNotesEoaServiceVO generalnotesVO) throws RMDBOException;
	 /**
	     * @Author:
	     * @param language
	     * @return List<GeneralNotesEoaServiceVO>
	     * @throws RMDBOException
	     * @Description:
	     */
	 public List<GeneralNotesEoaServiceVO> showAllcommnotes(final String language) throws RMDBOException;
	 /**
	     * @Author:
	     * @param generalnotesVO
	     * @return String
	     * @throws RMDBOException
	     * @Description:
	     */
	 public String removeCommNotes(List<GeneralNotesEoaServiceVO> generalnotesVO) throws RMDBOException;
	//end for comm Notes
	 /**
	     * @param List<GeneralNotesEoaServiceVO>
	     * @return String
	     * @throws RMDBOException
	     * @Description This method is used to update existing general/comm notes
	     *              visibility flag value
	     */
	 public String updateGenOrCommNotes(
            List<GeneralNotesEoaServiceVO> objGeneralNotesEoaServiceVO) throws RMDBOException;
	
	
	

}

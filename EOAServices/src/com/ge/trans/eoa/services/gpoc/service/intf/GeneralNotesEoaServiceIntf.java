/**
 * ============================================================
 * Classification: GE Confidential
 * File : PopupListAdminServiceIntf.java
 * Description : Service Interface for General Notes GPOC
 *
 * Package : com.ge.trans.eoa.services.gpoc.service.intf
 * Author : General Electric
 * Last Edited By : Sonal
 * Version : 1.0
 * Created on : May 10, 2017
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.gpoc.service.intf;


import java.util.List;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface GeneralNotesEoaServiceIntf {
	
	//start for general notes
	
	/**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to add general notes
     */
	public String addGeneralNotes(GeneralNotesEoaServiceVO generalnotesVO) throws RMDServiceException;
    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to display all general notes
     */
    public List<GeneralNotesEoaServiceVO> showAllGeneralNotes(final String language) throws RMDServiceException;
    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to remove general notes
     */
    public String removeGeneralNotes(List<GeneralNotesEoaServiceVO> objGeneralNotesEoaServiceVO) throws RMDServiceException;
    
    //end for general notes
    
    ///start for comm notes
    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to add comm notes
     */
    public String addCommNotes(GeneralNotesEoaServiceVO generalnotesVO) throws RMDServiceException;
    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to display comm notes
     */
    public List<GeneralNotesEoaServiceVO> showAllcommnotes(final String language) throws RMDServiceException;
    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:this is a method to remove comm notes
     */
    public String removeCommNotes(List<GeneralNotesEoaServiceVO> objGeneralNotesEoaServiceVO) throws RMDServiceException;
    
    /**
     * @param List<GeneralNotesEoaServiceVO>
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to update existing General/Comm notes visibility flag value
     */
    public String updateGenOrCommNotes(
            List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO) throws RMDServiceException;
    
  //end for comm notes
   
}

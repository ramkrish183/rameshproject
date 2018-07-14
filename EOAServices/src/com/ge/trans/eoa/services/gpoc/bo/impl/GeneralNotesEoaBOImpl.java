/**
 * ============================================================
 * Classification: GE Confidential
 * File : GeneralNotesEoaBOImpl.java
 * Description : BO Impl for general and comm notes for GPOC Turnover
 *
 * Package : com.ge.trans.rmd.services.admin.bo.impl
 * Author : General Electric
 * Last Edited By  : Sonal Gupta
 * Version : 1.0
 * Created on : March 22, 2017
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */package com.ge.trans.eoa.services.gpoc.bo.impl;


import java.util.List;
import com.ge.trans.eoa.services.gpoc.bo.intf.GeneralNotesEoaBOIntf;
import com.ge.trans.eoa.services.gpoc.dao.intf.GeneralNotesDAOIntf;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated:  March 22, 2017
 * @DateModified : March 22, 2017
 * @ModifiedBy : Sonal Gupta
 * @Contact :
 * @Description : BO Impl for general and comm notes for GPOC Turnover
 * @History :
 ******************************************************************************/
public class GeneralNotesEoaBOImpl implements GeneralNotesEoaBOIntf {
	private GeneralNotesDAOIntf objgeneralNotesDAO;
	public static final RMDLogger LOG = RMDLoggerHelper
            .getLogger(GeneralNotesEoaBOImpl.class);
	
	/**
     * @param objgeneralNotesDAO
     */
	
	public GeneralNotesEoaBOImpl(GeneralNotesDAOIntf objgeneralNotesDAO){
		this.objgeneralNotesDAO = objgeneralNotesDAO;
		
	}
	
	
	//Start for general notes
	/*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.gpoc.bo.intf.GeneralNotesEoaBOIntf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO.
     */

	@Override
	public String addGeneralNotes(GeneralNotesEoaServiceVO generalnotesVO)
			throws RMDBOException  {
		 String Status = null;
	        try {
	        	Status=objgeneralNotesDAO.addGeneralNotes(generalnotesVO);
	        }catch (RMDDAOException ex) {
	            throw new RMDBOException(ex.getErrorDetail(), ex);
	        }
	        return Status;
	}

	/*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.gpoc.bo.intf.GeneralNotesEoaBOIntf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO.
     */

	@Override
	public List<GeneralNotesEoaServiceVO> showAllGeneralNotes(String language)
			throws RMDBOException { 
		try {
	    return objgeneralNotesDAO.showAllGeneralNotes(language);
	    
	        } 
		catch (RMDDAOException e) {
	            throw e;
	        }
		}
	/*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.gpoc.bo.intf.GeneralNotesEoaBOIntf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO.
     */
	@Override
	public String removeGeneralNotes(
			List<GeneralNotesEoaServiceVO> generalnotesVO)
			throws RMDBOException {
		String status = null;
        try {
        	status=objgeneralNotesDAO.removeGeneralNotes(generalnotesVO); ;
        } catch (RMDDAOException e) {
            throw e;
        }
        
        return status;
    }
	
	
	
	//end for general notes
	
	/*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.gpoc.bo.intf.GeneralNotesEoaBOIntf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO.
     */
    
	
	//started for comm notes
	@Override
	public String addCommNotes(GeneralNotesEoaServiceVO generalnotesVO)
			throws RMDBOException {
		 String Status = null;
	        try {
	        	Status=objgeneralNotesDAO.addCommNotes(generalnotesVO);
	        }catch (RMDDAOException ex) {
	            throw new RMDBOException(ex.getErrorDetail(), ex);
	        }
	        return Status;
	}
	/*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.gpoc.bo.intf.GeneralNotesEoaBOIntf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO.
     */
	@Override
	public List<GeneralNotesEoaServiceVO> showAllcommnotes(String language)
			throws RMDBOException { 
		try {
		    return objgeneralNotesDAO.showAllcommnotes(language);
		    
		        } 
			catch (RMDDAOException e) {
		            throw e;
		        }
			}
	/*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.gpoc.bo.intf.GeneralNotesEoaBOIntf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO.
     */
	@Override
	public String removeCommNotes(List<GeneralNotesEoaServiceVO> generalnotesVO)
			throws RMDBOException {
		String status = null;
        try {
        	status=objgeneralNotesDAO.removeCommNotes(generalnotesVO); ;
        } catch (RMDDAOException e) {
            throw e;
        }
        
        return status;
    }
	
	//end for comm notes
	
	/**
     * @param List<GeneralNotesEoaServiceVO>
     * @return String
     * @throws RMDBOException
     * @Description This method is used to update existing general/comm notes
     *              visibility flag value
     */
	@Override
    public String updateGenOrCommNotes(List<GeneralNotesEoaServiceVO> generalnotesVO)
            throws RMDBOException {
        String status = null;
        try {
            status=objgeneralNotesDAO.updateGenOrCommNotes(generalnotesVO); ;
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in updateGenOrCommNotes() of GeneralNotesEoaBOImpl ", e);
            throw new RMDBOException(e.getErrorDetail());
        }
        
        return status;
    }
}

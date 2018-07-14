/**
 * ============================================================
 * Classification: GE Confidential
 * File : GeneralNotesEoaServiceimpl.java
 * Description : Service Impl for General and comm notes for GPOC
 *
 * Package : com.ge.trans.eoa.services.gpoc.service.impl;
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
 */package com.ge.trans.eoa.services.gpoc.service.impl;


import java.util.List;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.gpoc.bo.intf.GeneralNotesEoaBOIntf;
import com.ge.trans.eoa.services.gpoc.service.intf.GeneralNotesEoaServiceIntf;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class GeneralNotesEoaServiceimpl implements GeneralNotesEoaServiceIntf {
	
	 private GeneralNotesEoaBOIntf objGeneralNotesEoaBOIntf;
	 public static final RMDLogger LOG = RMDLoggerHelper
             .getLogger(GeneralNotesEoaServiceimpl.class);
	 
	 /**
	     * @param objGeneralNotesEoaBOIntf
	     */
	 
	 public GeneralNotesEoaServiceimpl(GeneralNotesEoaBOIntf objGeneralNotesEoaBOIntf) {
	        this.objGeneralNotesEoaBOIntf = objGeneralNotesEoaBOIntf;
	 }

	//started for general notes
	 /*
	     * (non-Javadoc)
	     * @see
	     * com.ge.trans.eoa.services.gpoc.service.intf#
	     * com.ge.trans.eoa.services.gpoc.service.valueobjects;
	     */
	@Override
	public String addGeneralNotes(GeneralNotesEoaServiceVO generalnotesVO)
			throws RMDServiceException {
		String status=null;

		try {
			status=objGeneralNotesEoaBOIntf.addGeneralNotes(generalnotesVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }  catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
		return status;
	
	}
	/*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.gpoc.service.intf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects;
     */


	@Override
	public List<GeneralNotesEoaServiceVO> showAllGeneralNotes(String language)
			throws RMDServiceException {
		List<GeneralNotesEoaServiceVO> volst = null;
        try {
        	volst = objGeneralNotesEoaBOIntf.showAllGeneralNotes(language);
        }  catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return volst;
        }

	/*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.gpoc.service.intf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects;
     */
	@Override
	public String removeGeneralNotes(
			List<GeneralNotesEoaServiceVO> objGeneralNotesEoaServiceVO)
			throws RMDServiceException {
		String status=null;

		try {
			status=objGeneralNotesEoaBOIntf.removeGeneralNotes(objGeneralNotesEoaServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }  catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
		return status;
	
	}
	//end for general notes
	
	
	/*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.gpoc.service.intf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects;
     */
    //started for comm notes
	@Override
	public String addCommNotes(GeneralNotesEoaServiceVO generalnotesVO)
			throws RMDServiceException {
		String status=null;

		try {
			status=objGeneralNotesEoaBOIntf.addCommNotes(generalnotesVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }  catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
		return status;
	
	}
	/*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.gpoc.service.intf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects;
     */

	@Override
	public List<GeneralNotesEoaServiceVO> showAllcommnotes(String language)
			throws RMDServiceException {
		List<GeneralNotesEoaServiceVO> volst = null;
        try {
        	volst = objGeneralNotesEoaBOIntf.showAllcommnotes(language);
        }  catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return volst;
        }

	/*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.gpoc.service.intf#
     * com.ge.trans.eoa.services.gpoc.service.valueobjects;
     */
	@Override
	public String removeCommNotes(
			List<GeneralNotesEoaServiceVO> objGeneralNotesEoaServiceVO)
			throws RMDServiceException {
		String status=null;

		try {
			status=objGeneralNotesEoaBOIntf.removeCommNotes(objGeneralNotesEoaServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }  catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
		return status;
	
	}
	
	//end for comm notes
    /**
     * @param List<GeneralNotesEoaServiceVO>
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to update existing general/comm notes
     *              visibility flag value
     */
    @Override
    public String updateGenOrCommNotes(
            List<GeneralNotesEoaServiceVO> objGeneralNotesEoaServiceVO)
            throws RMDServiceException {
        String status = null;

        try {
            status = objGeneralNotesEoaBOIntf
                    .updateGenOrCommNotes(objGeneralNotesEoaServiceVO);
        } catch (RMDBOException ex) {
            LOG.error("Unexpected Error occured in updateGenOrCommNotes() of GeneralNotesServiceImpl ", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } 
        return status;

    }

}

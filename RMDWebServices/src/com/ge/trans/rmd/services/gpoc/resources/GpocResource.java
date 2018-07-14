/**
 * ============================================================
 * Classification: GE Confidential
 * File : GeneralNotesResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.gpoc.resources
 * Author : General Electric
 * Last Edited By : Sonal
 * Version : 1.0
 * Created on : Mar 10 2017
 * History
 * Modified By : General Electric
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.gpoc.resources;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ge.trans.eoa.services.gpoc.service.intf.GeneralNotesEoaServiceIntf;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.authorization.resources.AuthorizationResource;
import com.ge.trans.rmd.services.gpocnotes.valueobjects.GeneralNotesRequestType;
import com.ge.trans.rmd.services.gpocnotes.valueobjects.GeneralNotesResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.GPOC_SERVICE)
@Component
public class GpocResource extends BaseResource {
	public static final RMDLogger logger = RMDLoggerHelper
			.getLogger(AuthorizationResource.class);
	@Autowired
	GeneralNotesEoaServiceIntf objGeneralNotesEoaServiceIntf;
	@Autowired
	OMDResourceMessagesIntf omdResourceMessagesIntf;

	/**
	 * This method is used to add notes
	 * 
	 * @param reqobj
	 * @return uniquerecord
	 * @throws RMDServiceException
	 */
	@POST
	@Path(OMDConstants.ADD_GENERAL_NOTES)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String addGeneralNotes(final GeneralNotesRequestType reqobj)
			throws RMDServiceException {
		String uniquerecord = null;
		GeneralNotesEoaServiceVO objvo = null;

		try {
			if (null != reqobj) {

				objvo = new GeneralNotesEoaServiceVO();
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getNotesdesc())) {
					objvo.setNotesdesc(reqobj.getNotesdesc());
				}
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getEnteredby())) {
					objvo.setEnteredby(reqobj.getEnteredby());
				}
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getVisibilityFlag())) {
                    objvo.setVisibilityFlag(reqobj.getVisibilityFlag());
                }


			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			uniquerecord = objGeneralNotesEoaServiceIntf.addGeneralNotes(objvo);

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}

		return uniquerecord;

	}

	/**
	 * This method is used to display all general notes
	 * 
	 * @param 
	 * @return arlGnType
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_All_GENERAL_NOTES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<GeneralNotesResponseType> showAllGeneralNotes() throws RMDBOException {
		Iterator<GeneralNotesEoaServiceVO> iterGneralNotes = null;
		List<GeneralNotesEoaServiceVO> arlGn;
		GeneralNotesEoaServiceVO objGeneralNotesEoaServiceVO;
		String strLanguage = null;
		GeneralNotesResponseType objGeneralNotesResponseType;
		final List<GeneralNotesResponseType> arlGnType = new ArrayList<GeneralNotesResponseType>();
		try {
			strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
			arlGn = objGeneralNotesEoaServiceIntf.showAllGeneralNotes(strLanguage);

			if (RMDCommonUtility.isCollectionNotEmpty(arlGn)) {
				if (RMDCommonUtility.isCollectionNotEmpty(arlGn)) {
					iterGneralNotes = arlGn.iterator();
					while (iterGneralNotes.hasNext()) {
						objGeneralNotesResponseType = new GeneralNotesResponseType();
						objGeneralNotesEoaServiceVO = iterGneralNotes.next();
						BeanUtility.copyBeanProperty(
								objGeneralNotesEoaServiceVO,
								objGeneralNotesResponseType);
						objGeneralNotesResponseType
								.setGeneralnotesSeqId(objGeneralNotesEoaServiceVO
										.getGeneralnotesSeqId());
						objGeneralNotesResponseType
								.setNotesdesc(objGeneralNotesEoaServiceVO
										.getNotesdesc());
						objGeneralNotesResponseType
								.setEnteredby(objGeneralNotesEoaServiceVO
										.getEnteredby());
						objGeneralNotesResponseType.setLastUpdatedTime(objGeneralNotesEoaServiceVO.getLastUpdatedTime());
						objGeneralNotesResponseType.setVisibilityFlag(objGeneralNotesEoaServiceVO.getVisibilityFlag());
						arlGnType.add(objGeneralNotesResponseType);

					}
				}
			}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return arlGnType;

	}
	
	/**
	 * This method is used to delete general notes
	 * 
	 * @param reqobj
	 * @return status
	 * @throws RMDServiceException
	 */

	@POST
	@Path(OMDConstants.REMOVE_GENERAL_NOTES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String removeGeneralNotes(final GeneralNotesRequestType reqobj)
			throws RMDServiceException {

		String status = OMDConstants.FAILURE;
		List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = new ArrayList<GeneralNotesEoaServiceVO>();
		List<GeneralNotesRequestType> arlGeneralNotesRequestType = reqobj
				.getArlGeneralNotesRequestType();
		GeneralNotesEoaServiceVO objGeneralNotesEoaServiceVO = null;

		try {
			for (GeneralNotesRequestType generalNotesRequestType : arlGeneralNotesRequestType) {
				objGeneralNotesEoaServiceVO = new GeneralNotesEoaServiceVO();
				if (null != (Long.toString(generalNotesRequestType
						.getGetnotesSeqId()))) {
					objGeneralNotesEoaServiceVO
							.setGeneralnotesSeqId(generalNotesRequestType
									.getGetnotesSeqId());
				}
				arlGeneralNotesEoaServiceVO.add(objGeneralNotesEoaServiceVO);
			}
			status = objGeneralNotesEoaServiceIntf
					.removeGeneralNotes(arlGeneralNotesEoaServiceVO);
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return status;
	}

	/**
	 * This method is used to add comm notes
	 * 
	 * @param  reqobj
	 * @return uniquerecord
	 * @throws RMDServiceException
	 */

	@POST
	@Path(OMDConstants.ADD_COMM_NOTES)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String addCommNotes(final GeneralNotesRequestType reqobj)
			throws RMDServiceException {
		String uniquerecord = null;
		GeneralNotesEoaServiceVO objvo = null;

		try {
			if (null != reqobj) {

				objvo = new GeneralNotesEoaServiceVO();
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getNotesdesc())) {
					objvo.setNotesdesc(reqobj.getNotesdesc());
				}
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getEnteredby())) {
					objvo.setEnteredby(reqobj.getEnteredby());
				}
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getVisibilityFlag())) {
                    objvo.setVisibilityFlag(reqobj.getVisibilityFlag());
                }

			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			uniquerecord = objGeneralNotesEoaServiceIntf.addCommNotes(objvo);

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}

		return uniquerecord;

	}
	
	/**
	 * This method is used to display all comm notes
	 * 
	 * @param 
	 * @return
	 * @throws RMDServiceException
	 */

	@GET
	@Path(OMDConstants.GET_All_COMM_NOTES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<GeneralNotesResponseType> showAllcommnotes()
			throws RMDBOException {
		Iterator<GeneralNotesEoaServiceVO> iterGneralNotes = null;
		List<GeneralNotesEoaServiceVO> arlGn;
		GeneralNotesEoaServiceVO objGeneralNotesEoaServiceVO;
		String strLanguage = null;
		GeneralNotesResponseType objGeneralNotesResponseType;
		final List<GeneralNotesResponseType> arlGnType = new ArrayList<GeneralNotesResponseType>();
		try {
			strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
			arlGn = objGeneralNotesEoaServiceIntf.showAllcommnotes(strLanguage);

			if (RMDCommonUtility.isCollectionNotEmpty(arlGn)) {
				if (RMDCommonUtility.isCollectionNotEmpty(arlGn)) {
					iterGneralNotes = arlGn.iterator();
					while (iterGneralNotes.hasNext()) {
						objGeneralNotesResponseType = new GeneralNotesResponseType();
						objGeneralNotesEoaServiceVO = iterGneralNotes.next();
						BeanUtility.copyBeanProperty(
								objGeneralNotesEoaServiceVO,
								objGeneralNotesResponseType);
						objGeneralNotesResponseType
								.setCommnotesSeqId(objGeneralNotesEoaServiceVO
										.getCommnotesSeqId());
						objGeneralNotesResponseType
								.setNotesdesc(objGeneralNotesEoaServiceVO
										.getNotesdesc());
						objGeneralNotesResponseType
								.setEnteredby(objGeneralNotesEoaServiceVO
										.getEnteredby());
						objGeneralNotesResponseType.setLastUpdatedTime(objGeneralNotesEoaServiceVO.getLastUpdatedTime());
						objGeneralNotesResponseType.setVisibilityFlag(objGeneralNotesEoaServiceVO.getVisibilityFlag());
						arlGnType.add(objGeneralNotesResponseType);

					}
				}
			} 
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return arlGnType;

	}
	/**
	 * This method is used to remove comm notes
	 * 
	 * @param  reqobj
	 * @return status
	 * @throws RMDServiceException
	 */

	@POST
	@Path(OMDConstants.REMOVE_COMM_NOTES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String removeCommNotes(final GeneralNotesRequestType reqobj)
			throws RMDServiceException {

		String status = OMDConstants.FAILURE;
		List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = new ArrayList<GeneralNotesEoaServiceVO>();
		List<GeneralNotesRequestType> arlGeneralNotesRequestType = reqobj
				.getArlGeneralNotesRequestType();
		GeneralNotesEoaServiceVO objGeneralNotesEoaServiceVO = null;

		try {
			for (GeneralNotesRequestType generalNotesRequestType : arlGeneralNotesRequestType) {
				objGeneralNotesEoaServiceVO = new GeneralNotesEoaServiceVO();
				if (null != (Long.toString(generalNotesRequestType
						.getGetnotesSeqId()))) {
					objGeneralNotesEoaServiceVO
							.setCommnotesSeqId(generalNotesRequestType
									.getGetnotesSeqId());
				}
				arlGeneralNotesEoaServiceVO.add(objGeneralNotesEoaServiceVO);
			}
			status = objGeneralNotesEoaServiceIntf
					.removeCommNotes(arlGeneralNotesEoaServiceVO);
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return status;
	}
	
	/**
     * @param GeneralNotesRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to update existing general/comm notes visibility flag value
     */
	@POST
    @Path(OMDConstants.UPDATE_GEN_OR_COMM_NOTES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String updateGenOrCommNotes(final GeneralNotesRequestType reqobj)
            throws RMDServiceException {

        String status = OMDConstants.FAILURE;
        List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = new ArrayList<GeneralNotesEoaServiceVO>();
        List<GeneralNotesRequestType> arlGeneralNotesRequestType = reqobj
                .getArlGeneralNotesRequestType();
        GeneralNotesEoaServiceVO objGeneralNotesEoaServiceVO = null;

        try {
            for (GeneralNotesRequestType generalNotesRequestType : arlGeneralNotesRequestType) {
                objGeneralNotesEoaServiceVO = new GeneralNotesEoaServiceVO();
                if (null != (Long.toString(generalNotesRequestType
                        .getGetnotesSeqId()))) {
                    objGeneralNotesEoaServiceVO
                            .setCommnotesSeqId(generalNotesRequestType
                                    .getGetnotesSeqId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(generalNotesRequestType.getVisibilityFlag())) {
                    objGeneralNotesEoaServiceVO
                            .setVisibilityFlag(generalNotesRequestType
                                    .getVisibilityFlag());
                }
                if (!RMDCommonUtility.isNullOrEmpty(generalNotesRequestType.getEnteredby())) {
                    objGeneralNotesEoaServiceVO
                            .setLastUpdatedBy(generalNotesRequestType
                                    .getEnteredby());
                }
                if (!RMDCommonUtility.isNullOrEmpty(generalNotesRequestType.getFromScreen())) {
                    objGeneralNotesEoaServiceVO
                            .setFromScreen(generalNotesRequestType
                                    .getFromScreen());
                }
                
                arlGeneralNotesEoaServiceVO.add(objGeneralNotesEoaServiceVO);
            }
            status = objGeneralNotesEoaServiceIntf
                    .updateGenOrCommNotes(arlGeneralNotesEoaServiceVO);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
            logger.error("Exception occured in updateGenOrCommNotes() method of GPOCResource ", ex);
        }
        return status;
    }

}

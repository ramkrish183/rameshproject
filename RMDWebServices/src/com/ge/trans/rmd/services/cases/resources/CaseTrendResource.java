package com.ge.trans.rmd.services.cases.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.cases.service.intf.CaseTrendServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.CaseTrendResponseType;
import com.ge.trans.rmd.services.gpocnotes.valueobjects.GeneralNotesResponseType;
import com.ge.trans.rmd.tools.keys.util.AppConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_CASE_TREND_SERVICE)
@Component
public class CaseTrendResource extends BaseResource {
	@Autowired
	private CaseTrendServiceIntf objCaseTrendServiceIntf;
	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;

	public static final RMDLogger LOGGER = RMDLoggerHelper
			.getLogger(CaseTrendResource.class);

	/**
	 * @Author:
	 * @param: UriInfo ui
	 * @return:List<CaseTrendResponseType>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching delivered open rx count
	 *               for past 8 days.
	 */
	@GET
	@Path(OMDConstants.GET_OPEN_RX_COUNT)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<CaseTrendResponseType> getOpenRXCount(@Context UriInfo ui)
			throws RMDServiceException {
		List<CaseTrendResponseType> responseList = new ArrayList<CaseTrendResponseType>();
		List<CaseTrendVO> valueList = new ArrayList<CaseTrendVO>();
		CaseTrendResponseType objCaseTrendResponseType = null;
		try {
			LOGGER.debug("*****getOpenRXCount WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			valueList = objCaseTrendServiceIntf.getOpenRXCount();
			if (valueList != null && !valueList.isEmpty()) {
				responseList = new ArrayList<CaseTrendResponseType>(
						valueList.size());
			}
			for (CaseTrendVO objCaseTrendVO : valueList) {
				objCaseTrendResponseType = new CaseTrendResponseType();
				objCaseTrendResponseType.setCreationRXDate(objCaseTrendVO
						.getCreationRXDate());
				objCaseTrendResponseType.setDay(objCaseTrendVO.getDay());
				objCaseTrendResponseType.setRxCount(objCaseTrendVO.getCount());
				responseList.add(objCaseTrendResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			LOGGER.error(e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}

		return responseList;
	}

	/**
	 * @Author:
	 * @param: UriInfo ui
	 * @return:List<CaseTrendResponseType>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching created case type count
	 *               for past 24 hours.
	 */
	@GET
	@Path(OMDConstants.GET_CASE_TREND_COUNT)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<CaseTrendResponseType> getCaseTrend(@Context UriInfo ui)
			throws RMDServiceException {
		List<CaseTrendResponseType> responseList = new ArrayList<CaseTrendResponseType>();
		List<CaseTrendVO> valueList = new ArrayList<CaseTrendVO>();
		CaseTrendResponseType objCaseTrendResponseType = null;
		try {
			LOGGER.debug("*****getCaseTrend WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			valueList = objCaseTrendServiceIntf.getCaseTrend();
			if (valueList != null && !valueList.isEmpty()) {
				responseList = new ArrayList<CaseTrendResponseType>(
						valueList.size());
			}
			for (CaseTrendVO objCaseTrendVO : valueList) {
				objCaseTrendResponseType = new CaseTrendResponseType();
				objCaseTrendResponseType.setCreationRXDate(objCaseTrendVO
						.getCreationRXDate());
				objCaseTrendResponseType.setRxCount(objCaseTrendVO.getCount());
				objCaseTrendResponseType.setProblemDesc(objCaseTrendVO
						.getProblemDesc());
				responseList.add(objCaseTrendResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			LOGGER.error(e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}

		return responseList;
	}

	/**
	 * @Author:
	 * @param: UriInfo ui
	 * @return:List<CaseTrendResponseType>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching created case type count
	 *               for past 7 days.
	 */
	@GET
	@Path(OMDConstants.GET_CASE_TREND_STAT_COUNT)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<CaseTrendResponseType> getCaseTrendStatistics(
			@Context UriInfo ui) throws RMDServiceException {
		List<CaseTrendResponseType> responseList = new ArrayList<CaseTrendResponseType>();
		List<CaseTrendVO> valueList = new ArrayList<CaseTrendVO>();
		CaseTrendResponseType objCaseTrendResponseType = null;
		try {
			LOGGER.debug("*****getCaseTrendStatistics WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			valueList = objCaseTrendServiceIntf.getCaseTrendStatistics();
			if (valueList != null && !valueList.isEmpty()) {
				responseList = new ArrayList<CaseTrendResponseType>(
						valueList.size());
			}
			for (CaseTrendVO objCaseTrendVO : valueList) {
				objCaseTrendResponseType = new CaseTrendResponseType();
				objCaseTrendResponseType.setCreationRXDate(objCaseTrendVO
						.getCreationRXDate());
				objCaseTrendResponseType.setRxCount(objCaseTrendVO.getCount());
				objCaseTrendResponseType.setProblemDesc(objCaseTrendVO
						.getProblemDesc());
				responseList.add(objCaseTrendResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			LOGGER.error(e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}

		return responseList;
	}
	 /**
     * @Author :
     * @return :String
     * @param : UriInfo ui
     * @throws :RMDWebException
     * @Description: This method is used to get genaral note info having the flag as Y.
     * 
     */
    @GET
    @Path(OMDConstants.GET_GENERAL_NOTES_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<GeneralNotesResponseType> getGeneralNotesDetails(@Context UriInfo ui)
            throws RMDServiceException {
		List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = null;
		GeneralNotesResponseType objGeneralNotesResponseType =null;
        List<GeneralNotesResponseType> arlGeneralNotesResponseType = null;
		try {
			LOG.debug("*****getGeneralNotesDetails WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			arlGeneralNotesEoaServiceVO = objCaseTrendServiceIntf.getGeneralNotesDetails();
			if(RMDCommonUtility.isCollectionNotEmpty(arlGeneralNotesEoaServiceVO)){
			    arlGeneralNotesResponseType = new ArrayList<GeneralNotesResponseType>(arlGeneralNotesEoaServiceVO.size());
				for(GeneralNotesEoaServiceVO objNotes : arlGeneralNotesEoaServiceVO){
				    objGeneralNotesResponseType = new GeneralNotesResponseType();
				    objGeneralNotesResponseType.setNotesdesc(objNotes.getNotesdesc());
				    objGeneralNotesResponseType.setEnteredby(objNotes.getEnteredby());
				    arlGeneralNotesResponseType.add(objGeneralNotesResponseType);
				}
			}
			
		} catch (Exception e) {
			LOGGER.error(e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}

		return arlGeneralNotesResponseType;
    }

}

package com.ge.trans.rmd.services.autohc.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.autohc.service.intf.AutoHCTemplateServiceIntf;
import com.ge.trans.eoa.services.autohc.service.valueobjects.AutoHCTemplateSaveVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.assets.valueobjects.AutoHCDetailsOfTempResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ListWrapperResponseType;

@Path(OMDConstants.REQ_URI_AUTO_HC_TEMPLATE)
@Component
public class AutoHCService extends BaseResource {

	@Autowired
	private AutoHCTemplateServiceIntf autoHCTemplateServiceIntf;
	@Autowired
	OMDResourceMessagesIntf omdResourceMessagesIntf;

	@GET
	@Path(OMDConstants.AUTO_HC_TEMPLATE_GET)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ListWrapperResponseType getTemplateDropDwn(
			@QueryParam(OMDConstants.CTRL_CONFIG_NAMEVALUE) String ctrlConfig,
			@QueryParam(OMDConstants.CONFIG_FILE_NAMEVALUE) String configFile)
			throws RMDServiceException {
		List<String> info = new ArrayList<String>();
		ListWrapperResponseType data =  new ListWrapperResponseType();
		try {
			info = autoHCTemplateServiceIntf.getTemplateDropDwn(ctrlConfig,
					configFile);
			data.setElements(info);
		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return data;
	}
	
	@GET
	@Path(OMDConstants.AUTO_HC_FAULT_CODE_REC_TYPE_GET)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ListWrapperResponseType getFaultCodeRecTypeDropDwn(
			@QueryParam(OMDConstants.CTRL_CONFIG_NAMEVALUE) String ctrlConfig,
			@QueryParam(OMDConstants.CONFIG_FILE_NAMEVALUE) String configFile)
			throws RMDServiceException {
		List<String> info = new ArrayList<String>();
		ListWrapperResponseType data =  new ListWrapperResponseType();
		try {
			info = autoHCTemplateServiceIntf.getFaultCodeRecTypeDropDwn(ctrlConfig,
					configFile);
			data.setElements(info);
		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return data;
	}
	
	@GET
	@Path(OMDConstants.MESSAGE_DEF_ID_DATA_GET)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ListWrapperResponseType getMessageDefIdDropDwn()
			throws RMDServiceException {
		List<String> info = new ArrayList<String>();
		ListWrapperResponseType data =  new ListWrapperResponseType();
		try {
			info = autoHCTemplateServiceIntf.getMessageDefIdDropDwn();
			data.setElements(info);
		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return data;
	}
	
	@GET
	@Path(OMDConstants.POST_NEW_TEMPLATE_DATA)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String postAutoHCNewTemplate(@Context UriInfo ui) throws RMDServiceException{
		AutoHCTemplateSaveVO autoHCTemplateSaveVO = new AutoHCTemplateSaveVO();
		final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		 String tempObjId = OMDConstants.EMPTY_STRING, sampleRate = OMDConstants.EMPTY_STRING,
				 postSamples = OMDConstants.EMPTY_STRING, frequency = OMDConstants.EMPTY_STRING,
						 fcrtObjid = OMDConstants.EMPTY_STRING,templateNo = OMDConstants.EMPTY_STRING, status = OMDConstants.EMPTY_STRING,
								 versionNo = OMDConstants.EMPTY_STRING, title = OMDConstants.EMPTY_STRING,
										 configCtrlObjid = OMDConstants.EMPTY_STRING;
		 String result = new String();
		try {
			

            if (queryParams.containsKey(OMDConstants.AH_TEMPLATE_OBJ_ID)) {
            	tempObjId = queryParams.getFirst(OMDConstants.AH_TEMPLATE_OBJ_ID);
            }
            if (queryParams.containsKey(OMDConstants.AH_SAMPLE_RATE)) {
            	sampleRate = queryParams.getFirst(OMDConstants.AH_SAMPLE_RATE);
            }
            if (queryParams.containsKey(OMDConstants.AH_POST_SAMPLES)) {
            	postSamples = queryParams.getFirst(OMDConstants.AH_POST_SAMPLES);
            }
            if (queryParams.containsKey(OMDConstants.AH_FREQUENCY)) {
            	frequency = queryParams.getFirst(OMDConstants.AH_FREQUENCY);
            }
            if (queryParams.containsKey(OMDConstants.AH_FCRT_OBJID)) {
            	fcrtObjid = queryParams.getFirst(OMDConstants.AH_FCRT_OBJID);
            }
            if (queryParams.containsKey(OMDConstants.AH_TEMPLATE_NO)) {
            	templateNo = queryParams.getFirst(OMDConstants.AH_TEMPLATE_NO);
            }
            if (queryParams.containsKey(OMDConstants.AH_VERSION_NO)) {
            	versionNo = queryParams.getFirst(OMDConstants.AH_VERSION_NO);
            }
            if (queryParams.containsKey(OMDConstants.AH_TITLE)) {
            	title = queryParams.getFirst(OMDConstants.AH_TITLE);
            }
            if (queryParams.containsKey(OMDConstants.AH_STATUS)) {
            	status = queryParams.getFirst(OMDConstants.AH_STATUS);
            }
            if (queryParams.containsKey(OMDConstants.AH_CTRL_CONFIG_OBJID)) {
            	configCtrlObjid = queryParams.getFirst(OMDConstants.AH_CTRL_CONFIG_OBJID);
            }
           
            autoHCTemplateSaveVO.setTempObjId(tempObjId);
            autoHCTemplateSaveVO.setSampleRate(sampleRate);
            autoHCTemplateSaveVO.setPostSamples(postSamples);
            autoHCTemplateSaveVO.setFrequency(frequency);
            autoHCTemplateSaveVO.setFcrtObjid(fcrtObjid);
            autoHCTemplateSaveVO.setTemplateNo(templateNo);
            autoHCTemplateSaveVO.setVersionNo(versionNo);
            autoHCTemplateSaveVO.setTitle(title);
            autoHCTemplateSaveVO.setStatus(status);
            autoHCTemplateSaveVO.setConfigCtrlObjid(configCtrlObjid);
            result = autoHCTemplateServiceIntf.postAutoHCNewTemplate(autoHCTemplateSaveVO);
            
		}catch (Exception e) {
            LOG.error("Exception occuered in postAutoHCNewTemplate() method of AutoHCService" + e);
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
		return result;
	}
	
	@GET
	@Path(OMDConstants.GET_AUTO_HC_COMPLETE_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<AutoHCDetailsOfTempResponseType> getComAHCDetails(
			@QueryParam(OMDConstants.AH_TEMPLATE_NO) String templateNo,
			@QueryParam(OMDConstants.AH_VERSION_NO) String versionNo,
			@QueryParam(OMDConstants.AH_CTRL_CONFIG_OBJID) String ctrlCfgObjId
			)
			throws RMDServiceException {
		Map<String,String> info = new HashMap<String, String>();
		List<AutoHCDetailsOfTempResponseType> data =  null;
		try {
			info = autoHCTemplateServiceIntf.getComAHCDetails(templateNo,versionNo,ctrlCfgObjId);
			data = new ArrayList<AutoHCDetailsOfTempResponseType>(
					info.size());
			for (Map.Entry<String, String> entry : info.entrySet())
			{
				AutoHCDetailsOfTempResponseType resType = new AutoHCDetailsOfTempResponseType();
				resType.setId(entry.getKey());
				resType.setIdValue(entry.getValue());
				data.add(resType);
			}
			
		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return data;
	}
	
}

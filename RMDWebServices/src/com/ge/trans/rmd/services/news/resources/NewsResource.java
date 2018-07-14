package com.ge.trans.rmd.services.news.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import com.ge.trans.rmd.services.news.valueobjects.NewsAllResponseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.admin.service.valueobjects.RoleEoaServiceVO;
import com.ge.trans.eoa.services.news.impl.AllNewsVO;
import com.ge.trans.eoa.services.news.impl.NewsServiceIntf;
import com.ge.trans.eoa.services.news.impl.SaveNewsVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.admin.valueobjects.NewsDeleteRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.UpdateReadFlagRequestType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolesResponseType;
import com.ge.trans.rmd.services.heatmap.resources.HeatMapResource;
import com.ge.trans.rmd.services.news.valueobjects.NewsInsertRequestType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;


@Path(OMDConstants.NEWS_SERVICE)
@Component
public class NewsResource extends BaseResource{
	
	 @Autowired
	 private NewsServiceIntf newsServiceIntf;
	 @Autowired
	 private OMDResourceMessagesIntf omdResourceMessagesIntf;
	 public static final RMDLogger LOG = RMDLoggerHelper
	            .getLogger(HeatMapResource.class);
	 
	 @POST
	 @Path(OMDConstants.SAVE_NEWS)
	 @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public String saveNews(final NewsInsertRequestType reqobj)
	            throws RMDServiceException {
		 String response = null;
		 RecommDelvDocVO objRecommDelvDocVO=null;
		 try{
    		 SaveNewsVO saveNewsVO = new SaveNewsVO();
    		 saveNewsVO.setCustomerarray(reqobj.getCustomerarray());
    		 saveNewsVO.setExpiryDate(reqobj.getExpiryDate());
    		 saveNewsVO.setNewsDesc(reqobj.getNewsDesc());
    		 saveNewsVO.setUserId(reqobj.getUserId());
    		 saveNewsVO.setObjId(reqobj.getObjId());
    		 saveNewsVO.setStatus(reqobj.getStatus());
    		 saveNewsVO.setFileRemoved(reqobj.getFileRemoved());
    		 if(null!=reqobj.getObjDocDetails()){
    		     objRecommDelvDocVO = new RecommDelvDocVO();
    		     objRecommDelvDocVO.setDocData(reqobj.getObjDocDetails().getDocData());
    		     objRecommDelvDocVO.setDocPath(reqobj.getObjDocDetails().getDocPath());
    		     objRecommDelvDocVO.setDocTitle(reqobj.getObjDocDetails().getDocTitle());
    		     saveNewsVO.setObjDocDetails(objRecommDelvDocVO);
    		 }
    		 if(null!=reqobj.getObjImgDetails()){
                 objRecommDelvDocVO = new RecommDelvDocVO();
                 objRecommDelvDocVO.setDocData(reqobj.getObjImgDetails().getDocData());
                 objRecommDelvDocVO.setDocPath(reqobj.getObjImgDetails().getDocPath());
                 objRecommDelvDocVO.setDocTitle(reqobj.getObjImgDetails().getDocTitle());
                 saveNewsVO.setObjImgDetails(objRecommDelvDocVO);
             }
    		 response = newsServiceIntf.saveNews(saveNewsVO);
		 }
		 catch (Exception ex) {
	            RMDWebServiceErrorHandler.handleException(ex,
	                    omdResourceMessagesIntf);
	            ex.printStackTrace();
	        }
		 return response;
	 }
	 
	 @GET
	 @Path(OMDConstants.GET_ALL_NEWS)
	 @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public List<NewsAllResponseType> getAllNews(@Context UriInfo ui)
	            throws RMDServiceException {
		 List<AllNewsVO> lstAllNews = null;
		 Iterator<AllNewsVO> iter = null;
		 NewsAllResponseType objResponseType = null;
		 AllNewsVO allNewsVO = null;
		 final List<NewsAllResponseType> response = new ArrayList<NewsAllResponseType>();
		 final MultivaluedMap<String, String> queryParams = ui
                 .getQueryParameters();
         String userId = OMDConstants.EMPTY_STRING;
         String isAdminPage = OMDConstants.EMPTY_STRING;
         String customerId = OMDConstants.EMPTY_STRING;
		 try{
			 if (queryParams.containsKey(OMDConstants.USER_ID)) {
				 userId = queryParams.getFirst(OMDConstants.USER_ID);
             }
			 if (queryParams.containsKey(OMDConstants.IS_ADMIN_PAGE)) {
				 isAdminPage = queryParams.getFirst(OMDConstants.IS_ADMIN_PAGE);
             }
			 if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
				 customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
             }
			 lstAllNews = newsServiceIntf.getAllNews(userId,isAdminPage,customerId);
	            if (RMDCommonUtility.isCollectionNotEmpty(lstAllNews)) {
	                    iter = lstAllNews.iterator();
	                  while (iter.hasNext()) {
	                        objResponseType = new NewsAllResponseType();
	                        allNewsVO = iter.next();
	                        objResponseType.setAttachmentPath(allNewsVO.getAttachmentPath());
	                        objResponseType.setCreatedBy(allNewsVO.getCreatedBy());
	                        objResponseType.setCustomerId(allNewsVO.getCustomerId());
	                        objResponseType.setDescription(allNewsVO.getDescription());
	                        objResponseType.setExpiryDate(allNewsVO.getExpiryDate());
	                        objResponseType.setObjid(allNewsVO.getObjid());
	                        objResponseType.setStatus(allNewsVO.getStatus());
	                        objResponseType.setCreationDate(allNewsVO.getCreationDate());
	                        objResponseType.setAttName(allNewsVO.getAttName());
	                        objResponseType.setReadNewsFlag(allNewsVO.getReadNewsFlag());
	                        objResponseType.setNewsObjId(allNewsVO.getNewsObjId());
	                        objResponseType.setLastUpdatedBy(allNewsVO.getLastUpdatedBy());
	                        objResponseType.setLastUpdatedDate(allNewsVO.getLastUpdatedDate());
	                        response.add(objResponseType);
	                    }
	               }   
		 }
		 catch (Exception ex) {
	            RMDWebServiceErrorHandler.handleException(ex,
	                    omdResourceMessagesIntf);
	            ex.printStackTrace();
	        }
		 return response;
	 }
	 
	 
	 @POST
	 @Path(OMDConstants.DELETE_NEWS)
	 @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public String deleteNews(NewsDeleteRequestType newsDeleteRequestType)
	            throws RMDServiceException {
		 String response = null;
		 String newsIds =newsDeleteRequestType.getObjId();
 		 
		 try{
			 response = newsServiceIntf.deleteNews(newsIds);
		 }
		 catch (Exception ex) {
	            RMDWebServiceErrorHandler.handleException(ex,
	                    omdResourceMessagesIntf);
	            ex.printStackTrace();
	        }
		 return response;
	 }
	 @GET
	 @Path(OMDConstants.GET_BACKGROUND_IMAGE)
	 @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public String getBackgroundImage(@Context UriInfo ui)
	            throws RMDServiceException {
		 String bgImageUrl = null;
		 final MultivaluedMap<String, String> queryParams = ui
                 .getQueryParameters();
         String customerId = OMDConstants.EMPTY_STRING;
		 try{
	            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
	            	customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                }
	            bgImageUrl = newsServiceIntf.getBackgroundImage(customerId); 
		 }
		 catch (Exception ex) {
	            RMDWebServiceErrorHandler.handleException(ex,
	                    omdResourceMessagesIntf);
	            ex.printStackTrace();
	        }
		 return bgImageUrl;
	 }
	 @POST
	 @Path(OMDConstants.ADMIN_UPDATE_READ_FLAG)
	 @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public String updateReadFlag(UpdateReadFlagRequestType updateReadFlagRequestType)
	            throws RMDServiceException {
		 String response = null;
		 String newsIds =updateReadFlagRequestType.getObjId();
 		 
		 try{
			 response = newsServiceIntf.updateReadFlag(newsIds);
		 }
		 catch (Exception ex) {
	            RMDWebServiceErrorHandler.handleException(ex,
	                    omdResourceMessagesIntf);
	            ex.printStackTrace();
	        }
		 return response;
	 }
	 
}

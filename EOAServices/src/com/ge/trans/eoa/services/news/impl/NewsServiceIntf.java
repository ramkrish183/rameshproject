package com.ge.trans.eoa.services.news.impl;


import java.util.List;


import com.ge.trans.rmd.exception.RMDServiceException;

public interface NewsServiceIntf {

	 public String saveNews(SaveNewsVO saveNewsVO)
	            throws RMDServiceException;
	 public List<AllNewsVO> getAllNews(String userId,String isAdminPage,String customerId)
				throws RMDServiceException;
	 public String deleteNews(String objId)
				throws RMDServiceException;
	 public String getBackgroundImage(String customerId)
				throws RMDServiceException;
	 public String updateReadFlag(String objId)
				throws RMDServiceException;
	 
}

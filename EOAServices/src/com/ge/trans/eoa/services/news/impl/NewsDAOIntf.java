package com.ge.trans.eoa.services.news.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface NewsDAOIntf {

	 public String saveNews(SaveNewsVO saveNewsVO) throws RMDDAOException;
	 public List<AllNewsVO> getAllNews(String userId,String isAdminPage,String customerId)
				throws RMDDAOException;
	 public String deleteNews(String objId)
				throws RMDServiceException;
	 public String getBackgroundImage(String customerId)
				throws RMDDAOException;
	 public String updateNewsFlag(String objId) throws RMDServiceException;
	 
}

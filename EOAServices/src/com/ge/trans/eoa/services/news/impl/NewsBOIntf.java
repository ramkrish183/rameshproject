package com.ge.trans.eoa.services.news.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;


public interface NewsBOIntf {

	public String saveNews(SaveNewsVO saveNewsVO)
            throws RMDBOException;
	public List<AllNewsVO> getAllNews(String userId,String isAdminPage,String customerId)
			throws RMDBOException;
	 public String deleteNews(String objId)
				throws RMDServiceException;
	 public String getBackgroundImage(String customerId)
				throws RMDBOException;
	 public String updateReadFlag(String objId)
				throws RMDServiceException;
	 
}

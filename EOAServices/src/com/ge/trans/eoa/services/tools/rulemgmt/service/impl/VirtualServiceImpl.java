/**
 * ============================================================
 * Classification: GE Confidential
 * File :VirtualServiceImpl.java
 * Description : Service Implements for Virtual Screen
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.VirtualBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.VirtualServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalVirtualServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Service Implements for Virtual Screen
 * @History :
 ******************************************************************************/
public class VirtualServiceImpl implements VirtualServiceIntf {

    private static final long serialVersionUID = -8996666015061376574L;

    private VirtualBOIntf objVirtualBOIF;

    public VirtualServiceImpl(VirtualBOIntf objVirtualBOIF) {
        this.objVirtualBOIF = objVirtualBOIF;
    }

    /**
     * @param objFinalVirtualServiceVO
     * @return String
     * @throws RMDServiceException
     * @Description: This method is used to call the saveVirtual method in
     *               VirtualBOIntf for save/update the virtual
     */
    @Override
    public String saveVirtual(final FinalVirtualServiceVO objFinalVirtualServiceVO) throws RMDServiceException {
        String strSaved = null;
        try {
            strSaved = objVirtualBOIF.saveVirtual(objFinalVirtualServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFinalVirtualServiceVO.getStrLanguage());
        }
        return strSaved;
    }

    /**
     * @param String
     *            strVirtualId,String strlanguage
     * @return FinalVirtualServiceVO
     * @throws RMDServiceException
     * @Description: This method is used to call the getVirtualDetails method in
     *               VirtualBOIntf, for list out all the virtual details
     */
    @Override
    public FinalVirtualServiceVO getVirtualDetails(String strVirtualId, String strlanguage) throws RMDServiceException {
        try {
            return objVirtualBOIF.getVirtualDetails(strVirtualId, strlanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strlanguage);
        }
        return null;
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDServiceException
     * @Description: This method is used to fetch Virtuals Lastupdated By
     */
    @Override
    public List<VirtualServiceVO> getVirtualLastUpdatedBy(String strLanugage) throws RMDServiceException {
        try {
            return objVirtualBOIF.getVirtualLastUpdatedBy(strLanugage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanugage);
        }
        return null;
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDServiceException
     * @Description: This method is used to fetch Virtuals Created By
     */
    @Override
    public List<VirtualServiceVO> getVirtualCreatedBy(String strLanugage) throws RMDServiceException {
        try {
            return objVirtualBOIF.getVirtualCreatedBy(strLanugage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanugage);
        }
        return null;
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDServiceException
     * @Description: This method is used to fetch Virtuals Titles
     */
    @Override
    public List<VirtualServiceVO> getVirtualTitles(String strVirtualTitle, String strLanugage)
            throws RMDServiceException {
        try {
            return objVirtualBOIF.getVirtualTitles(strVirtualTitle, strLanugage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanugage);
        }
        return null;
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDServiceException
     * @Description: This method is used to fetch virtual families
     */
    @Override
    public List<VirtualServiceVO> getVirtualFamily(String strLanugage) throws RMDServiceException {
        try {
            return objVirtualBOIF.getVirtualFamily(strLanugage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanugage);
        }
        return null;
    }

    /**
     * @param objVirtualServiceVO
     * @return List
     * @throws RMDServiceException
     * @Description: This method is used to call the searchVirtual method in
     *               VirtualBOIntf, for list out all the existing virtual
     */
    @Override
    public List<VirtualServiceVO> searchVirtuals(VirtualServiceVO objVirtualServiceVO) throws RMDServiceException {
        List<VirtualServiceVO> searchResultLst = null;
        try {
            searchResultLst = objVirtualBOIF.searchVirtuals(objVirtualServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objVirtualServiceVO.getStrLanguage());
        }
        return searchResultLst;
    }

    @Override
    public String isVirtualColumnExist() throws RMDServiceException {
        String strVirtualColumn = null;
        try {
            strVirtualColumn = objVirtualBOIF.isVirtualColumnExist();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, null);
        }
        return strVirtualColumn;
    }

    @Override
    public List<ElementVO> getVirtualActiveRules(int virtualId, String family) throws RMDServiceException {
        List<ElementVO> activeVirtuals = null;
        try {
            activeVirtuals = objVirtualBOIF.getVirtualActiveRules(virtualId, family);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, null);
        }
        return activeVirtuals;
    }
}

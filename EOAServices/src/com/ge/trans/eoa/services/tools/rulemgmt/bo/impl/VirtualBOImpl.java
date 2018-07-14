/**
 * ============================================================
 * Classification: GE Confidential
 * File :VirtualBOImpl.java
 * Description : BO Implements for Virtual Screen
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.VirtualBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.VirtualDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalVirtualServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.VirtualServiceVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : BO Implements for Virtual Screen
 * @History :
 ******************************************************************************/
public class VirtualBOImpl implements VirtualBOIntf {

    private VirtualDAOIntf objVirtualDAOIntf;

    public VirtualBOImpl(final VirtualDAOIntf objVirtualDAOIntf) {
        this.objVirtualDAOIntf = objVirtualDAOIntf;
    }

    /**
     * @param objFinalVirtualServiceVO
     * @return String
     * @throws RMDBOException
     * @Description: This method is used to call the saveVirtual method in
     *               VirtualDAOIntf for save/update the virtual
     */
    @Override
    public String saveVirtual(FinalVirtualServiceVO objFinalVirtualServiceVO) throws RMDBOException {
        String strSaved = null;
        String strFinalVirtualId = null;
        try {
            strFinalVirtualId = objFinalVirtualServiceVO.getStrFinalVirtualId();
            if (RMDCommonUtility.isNullOrEmpty(strFinalVirtualId)) {
                strSaved = objVirtualDAOIntf.saveVirtual(objFinalVirtualServiceVO);
            } else {
                strSaved = objVirtualDAOIntf.updateVirtual(objFinalVirtualServiceVO);
            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return strSaved;
    }

    /**
     * @param strVirtualId,
     *            strLanguage
     * @return List
     * @throws RMDBOException
     * @Description: This method is used to call the searchVirtual method in
     *               VirtualDAOIntf, for list out all Virtual Details
     */
    @Override
    public FinalVirtualServiceVO getVirtualDetails(String strVirtualId, String strLanguage) throws RMDBOException {
        try {
            return objVirtualDAOIntf.getVirtualDetails(strVirtualId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     * @Description: This method is used to fetch Virtuals Lastupdated By
     */
    @Override
    public List<VirtualServiceVO> getVirtualLastUpdatedBy(String strLanugage) throws RMDBOException {
        try {
            return objVirtualDAOIntf.getVirtualLastUpdatedBy(strLanugage);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     * @Description: This method is used to fetch Virtuals Created By
     */
    @Override
    public List<VirtualServiceVO> getVirtualCreatedBy(String strLanugage) throws RMDBOException {
        try {
            return objVirtualDAOIntf.getVirtualCreatedBy(strLanugage);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     * @Description: This method is used to fetch Virtuals Titles
     */
    @Override
    public List<VirtualServiceVO> getVirtualTitles(String strVirtualTitle, String strLanugage) throws RMDBOException {
        try {
            return objVirtualDAOIntf.getVirtualTitles(strVirtualTitle, strLanugage);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @param strLanguage
     * @return List<VirtualServiceVO>
     * @throws RMDBOException
     * @Description: This method is used to fetch virtual families
     */
    @Override
    public List<VirtualServiceVO> getVirtualFamily(String strLanugage) throws RMDBOException {
        try {
            return objVirtualDAOIntf.getVirtualFamily(strLanugage);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @param objVirtualServiceVO
     * @return List
     * @throws RMDBOException
     * @Description: This method is used to call the searchVirtual method in
     *               VirtualDAOIntf, for list out all the existing virtual
     */
    @Override
    public List<VirtualServiceVO> searchVirtuals(VirtualServiceVO objVirtualServiceVO) throws RMDBOException {
        List<VirtualServiceVO> searchResultLst = null;
        try {
            searchResultLst = objVirtualDAOIntf.searchVirtuals(objVirtualServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return searchResultLst;
    }

    @Override
    public String isVirtualColumnExist() throws RMDBOException {
        return objVirtualDAOIntf.isVirtualColumnExist();
    }

    @Override
    public List<ElementVO> getVirtualActiveRules(int virtualId, String family) throws RMDBOException {
        return objVirtualDAOIntf.getVirtualActiveRules(virtualId, family);
    }
}

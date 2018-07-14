/**
 * 
 */
package com.ge.trans.eoa.services.asset.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.asset.bo.intf.NotesEoaBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.AddNotesEoaServiceintf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ControllerListVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May,10 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class AddNotesEoaServiceimpl implements AddNotesEoaServiceintf {

    private NotesEoaBOIntf objNotesEoaBOIntf;

    public AddNotesEoaServiceimpl(NotesEoaBOIntf objNotesEoaBOIntf) {
        this.objNotesEoaBOIntf = objNotesEoaBOIntf;
    }

    /**
     * @Author :
     * @return :List<ControllerListVO>
     * @param :
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of controllers.
     */
    @Override
    public List<ControllerListVO> getAllControllers() throws RMDServiceException {

        List<ControllerListVO> arlController = null;
        try {
            arlController = objNotesEoaBOIntf.getAllControllers();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlController;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of creators.
     */
    @Override
    public List<String> getNotesCreatersList() throws RMDServiceException {

        List<String> arlCreatorList = null;
        try {
            arlCreatorList = objNotesEoaBOIntf.getNotesCreatersList();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlCreatorList;
    }

    /**
     * @Author :
     * @return :String
     * @param :NotesBean
     * @throws :RMDServiceException,
     *             SQLException
     * @Description: This method adds the notes for the selected customer.
     */
    @Override
    public String addNotesToVehicle(AddNotesEoaServiceVO objAddNotesServiceVO)
            throws RMDServiceException, SQLException {
        String status = null;
        try {
            status = objNotesEoaBOIntf.addNotesToVehicle(objAddNotesServiceVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return status;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :customerID,fromRN
     * @throws :RMDServiceException
     * @Description: This method is used to get the existing sticky details.
     */
    @Override
    public List<StickyNotesDetailsVO> fetchVehicleStickyCaseNotes(String customerID, String fromRN, String noOfUnits)
            throws RMDServiceException {
        List<StickyNotesDetailsVO> objStickyNotesDetailsVO = null;
        try {
            objStickyNotesDetailsVO = objNotesEoaBOIntf.fetchVehicleStickyCaseNotes(customerID, fromRN, noOfUnits);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return objStickyNotesDetailsVO;
    }

    /**
     * @Author:
     * @param:FindNotesEoaServiceVO
     * @return:List<NotesResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used to get Find Notes Details.
     */
    @Override
    public List<FindNotesDetailsVO> getFindNotes(FindNotesEoaServiceVO objFindNotesEoaServiceVO)
            throws RMDServiceException {
        List<FindNotesDetailsVO> objFindNotesDetailsVO = null;
        try {
            objFindNotesDetailsVO = objNotesEoaBOIntf.getFindNotes(objFindNotesEoaServiceVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return objFindNotesDetailsVO;

    }

    /**
     * @Author:
     * @param:unitStickyObjId,caseStickyObjId
     * @return:String
     * @throws SQLException
     * @throws:RMDServiceException
     * @Description: This method is used to remove sticky for case and unit
     *               level notes.
     */
    @Override
    public String removeSticky(String unitStickyObjId, String caseStickyObjId) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objNotesEoaBOIntf.removeSticky(unitStickyObjId, caseStickyObjId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

}

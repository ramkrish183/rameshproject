package com.ge.trans.eoa.services.asset.bo.impl;

import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.NotesEoaBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.NotesEoaDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ControllerListVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class NotesEoaBOImpl implements NotesEoaBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(NotesEoaBOImpl.class);
    private NotesEoaDAOIntf objNotesEoaDAOIntf;

    public NotesEoaBOImpl(final NotesEoaDAOIntf objNotesEoaDAOIntf) {
        this.objNotesEoaDAOIntf = objNotesEoaDAOIntf;
    }

    /**
     * @Author :
     * @return :List<ControllerListVO>
     * @param :
     * @throws :RMDBOException
     * @Description: This Method Fetches the list of controllers.
     */
    @Override
    public List<ControllerListVO> getAllControllers() throws RMDBOException {
        List<ControllerListVO> controllerList = null;
        try {
            controllerList = objNotesEoaDAOIntf.getAllControllers();
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in NotesEoaBOImpl getAllControllerList()" + ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in NotesEoaBOImpl getAllControllerList()" + exc);
        }
        return controllerList;
    }

    @Override
    public List<String> getNotesCreatersList() throws RMDBOException {
        List<String> creatorList = null;
        try {
            creatorList = objNotesEoaDAOIntf.getNotesCreatersList();
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in NotesEoaBOImpl getNotesCreatersList()" + ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in NotesEoaBOImpl getNotesCreatersList()" + exc);
        }
        return creatorList;
    }

    /**
     * @Author :
     * @return :String
     * @param :NotesBean
     * @throws :RMDBOException,
     *             SQLException
     * @Description: This method adds the notes for the selected customer.
     */

    @Override
    public String addNotesToVehicle(AddNotesEoaServiceVO objAddNotesServiceVO) throws RMDBOException, SQLException {
        String Status = null;
        try {
            Status = objNotesEoaDAOIntf.addNotesToVehicle(objAddNotesServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return Status;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :customerID,fromRN
     * @throws :RMDBOException
     * @Description: This method is used to get the existing sticky details.
     */
    @Override
    public List<StickyNotesDetailsVO> fetchVehicleStickyCaseNotes(String customerID, String fromRN, String noOfUnits)
            throws RMDBOException {
        List<StickyNotesDetailsVO> objStickyNotesDetailsVO = null;
        try {
            objStickyNotesDetailsVO = objNotesEoaDAOIntf.fetchVehicleStickyCaseNotes(customerID, fromRN, noOfUnits);

        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return objStickyNotesDetailsVO;
    }

    /**
     * @Author:
     * @param:FindNotesEoaServiceVO
     * @return:List<NotesResponseType>
     * @throws:RMDBOException
     * @Description: This method is used to get Find Notes Details.
     */
    @Override
    public List<FindNotesDetailsVO> getFindNotes(FindNotesEoaServiceVO objFindNotesEoaServiceVO) throws RMDBOException {
        List<FindNotesDetailsVO> objFindNotesDetailsVO = null;
        try {
            objFindNotesDetailsVO = objNotesEoaDAOIntf.getFindNotes(objFindNotesEoaServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objFindNotesDetailsVO;
    }

    /**
     * @Author :
     * @return :String
     * @param :unitStickyObjId,caseStickyObjId
     * @throws SQLException
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for removing a unit Level as well as
     *                   case Level Sticky Notes.
     */
    @Override
    public String removeSticky(String unitStickyObjId, String caseStickyObjId) throws RMDBOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objNotesEoaDAOIntf.removeSticky(unitStickyObjId, caseStickyObjId);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return result;
    }

}

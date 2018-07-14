package com.ge.trans.eoa.services.asset.bo.intf;

import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.rmd.common.valueobjects.ControllerListVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface NotesEoaBOIntf {

    /**
     * @Author :
     * @return :List<ControllerListVO>
     * @param :
     * @throws :RMDBOException
     * @Description: This Method Fetches the list of controllers.
     */
    public List<ControllerListVO> getAllControllers() throws RMDBOException;

    /**
     * @Author :
     * @return :String
     * @param :NotesBean
     * @throws :RMDBOException,
     *             SQLException
     * @Description: This method adds the notes for the selected customer.
     */
    public String addNotesToVehicle(AddNotesEoaServiceVO objAddNotesServiceVO) throws RMDBOException, SQLException;

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :customerID,fromRN
     * @throws :RMDBOException
     * @Description: This method is used to get the existing sticky details.
     */
    public List<StickyNotesDetailsVO> fetchVehicleStickyCaseNotes(String customerID, String fromRN, String noOfUnits)
            throws RMDBOException;

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDBOException
     * @Description: This Method Fetches the list of creators.
     */
    public List<String> getNotesCreatersList() throws RMDBOException;

    /**
     * @Author:
     * @param:FindNotesEoaServiceVO
     * @return:List<NotesResponseType>
     * @throws:RMDBOException
     * @Description: This method is used to get Find Notes Details.
     */
    public List<FindNotesDetailsVO> getFindNotes(FindNotesEoaServiceVO objFindNotesEoaServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param:unitStickyObjId,caseStickyObjId
     * @return:String
     * @throws SQLException
     * @throws:RMDBOException
     * @Description: This method is used to remove sticky for case and unit
     *               level notes.
     */
    public String removeSticky(String unitStickyObjId, String caseStickyObjId) throws RMDBOException;

}

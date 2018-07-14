/**
 * 
 */
package com.ge.trans.eoa.services.asset.service.intf;

import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.rmd.common.valueobjects.ControllerListVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface AddNotesEoaServiceintf {

    /**
     * @Author :
     * @return :List<ControllerListVO>
     * @param :
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of controllers.
     */
    public List<ControllerListVO> getAllControllers() throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :NotesBean
     * @throws :RMDServiceException,
     *             SQLException
     * @Description: This method adds the notes for the selected customer.
     */
    public String addNotesToVehicle(AddNotesEoaServiceVO objAddNotesServiceVO) throws RMDServiceException, SQLException;

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :customerID,fromRN
     * @throws :RMDServiceException
     * @Description: This method is used to get the existing sticky details.
     */
    public List<StickyNotesDetailsVO> fetchVehicleStickyCaseNotes(String customerID, String fromRN, String noOfUnits)
            throws RMDServiceException;

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of creators.
     */
    public List<String> getNotesCreatersList() throws RMDServiceException;

    /**
     * @Author:
     * @param:FindNotesEoaServiceVO
     * @return:List<NotesResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used to get Find Notes Details.
     */
    public List<FindNotesDetailsVO> getFindNotes(FindNotesEoaServiceVO objFindNotesEoaServiceVO)
            throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :unitStickyObjId,
     *            caseStickyObjId
     * @throws :RMDServiceException
     * @Description:This method is used for removing a unit Level as well as
     *                   case Level Sticky Notes from find notes screen.
     */
    public String removeSticky(String unitStickyObjId, String caseStickyObjId) throws RMDServiceException;

}

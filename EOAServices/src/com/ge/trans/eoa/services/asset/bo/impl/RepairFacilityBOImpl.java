package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.RepairFacilityBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.RepairFacilityDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CustomerSiteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RepairFacilityDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public class RepairFacilityBOImpl implements RepairFacilityBOIntf {

    RepairFacilityDAOIntf objRepairFacilityDAOIntf;

    public RepairFacilityBOImpl(RepairFacilityDAOIntf objRepairFacilityDAOIntf) {
        this.objRepairFacilityDAOIntf = objRepairFacilityDAOIntf;
    }

    /**
     * @Author :
     * @return :List<CustomerSiteVO>
     * @param :
     *            String customerId
     * @throws :RMDBOException
     * @Description: This method is used for fetching the sites for the selected
     *               customer.
     */
    @Override
    public List<CustomerSiteVO> getCustomerSites(String customerId) throws RMDBOException {
        List<CustomerSiteVO> custSiteVOList = null;
        try {
            custSiteVOList = objRepairFacilityDAOIntf.getCustomerSites(customerId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return custSiteVOList;
    }

    /**
     * @param String
     *            customerId, String site, String status
     * @return List<RepairFacilityDetailsResponseType>
     * @throws RMDBOException
     * @Description This method is used to get the Repair Facility Details.
     */
    @Override
    public List<RepairFacilityDetailsVO> getRepairFacilityDetails(String customerId, String site, String status)
            throws RMDBOException {
        List<RepairFacilityDetailsVO> repairFacilityDetailsVOList = null;
        try {
            repairFacilityDetailsVOList = objRepairFacilityDAOIntf.getRepairFacilityDetails(customerId, site, status);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }

        return repairFacilityDetailsVOList;
    }

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDBOException
     * @Description This method is used to insert new repair/site location
     *              details.
     */
    @Override
    public String insertRepairSiteLoc(RepairFacilityDetailsVO objRepairDetailsVO) throws RMDBOException {
        String result = null;
        try {
            result = objRepairFacilityDAOIntf.insertRepairSiteLoc(objRepairDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @param List<RepairFacilityRequestType>
     * @return String
     * @throws RMDBOException
     * @Description This method is used to update existing repair/site location
     *              details.
     */
    @Override
    public String updateRepairSiteLoc(List<RepairFacilityDetailsVO> arlReapirDetailsVO) throws RMDBOException {
        String result = null;
        try {
            result = objRepairFacilityDAOIntf.updateRepairSiteLoc(arlReapirDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return result;
    }

}

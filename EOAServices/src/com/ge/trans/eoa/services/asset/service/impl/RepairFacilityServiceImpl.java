package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.RepairFacilityBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.RepairFacilityServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CustomerSiteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RepairFacilityDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class RepairFacilityServiceImpl implements RepairFacilityServiceIntf {

    private RepairFacilityBOIntf objRepairFacilityBOIntf;

    public RepairFacilityServiceImpl(RepairFacilityBOIntf objRepairFacilityBOIntf) {
        this.objRepairFacilityBOIntf = objRepairFacilityBOIntf;
    }

    /**
     * @Author :
     * @return :List<CustomerSiteVO>
     * @param :
     *            String customerId
     * @throws :RMDServiceException
     * @Description: This method is used for fetching the sites for the selected
     *               customer.
     */
    @Override
    public List<CustomerSiteVO> getCustomerSites(String customerId) throws RMDServiceException {
        List<CustomerSiteVO> custSiteVOList = null;
        try {
            custSiteVOList = objRepairFacilityBOIntf.getCustomerSites(customerId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return custSiteVOList;
    }

    /**
     * @param String
     *            customerId, String site, String status
     * @return List<RepairFacilityDetailsResponseType>
     * @throws RMDServiceException
     * @Description This method is used to get the Repair Facility Details.
     */
    @Override
    public List<RepairFacilityDetailsVO> getRepairFacilityDetails(String customerId, String site, String status)
            throws RMDServiceException {
        List<RepairFacilityDetailsVO> repairFacilityDetailsVOList = null;
        try {
            repairFacilityDetailsVOList = objRepairFacilityBOIntf.getRepairFacilityDetails(customerId, site, status);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return repairFacilityDetailsVOList;
    }

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to insert new repair/site location
     *              details.
     */
    @Override
    public String insertRepairSiteLoc(RepairFacilityDetailsVO objRepairDetailsVO) throws RMDServiceException {
        String result = null;
        try {
            result = objRepairFacilityBOIntf.insertRepairSiteLoc(objRepairDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to update existing repair/site location
     *              details.
     */
    @Override
    public String updateRepairSiteLoc(List<RepairFacilityDetailsVO> arlReapirDetailsVO) throws RMDServiceException {
        String result = null;
        try {
            result = objRepairFacilityBOIntf.updateRepairSiteLoc(arlReapirDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

}

package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.CustomerSiteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RepairFacilityDetailsVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface RepairFacilityDAOIntf {

    /**
     * @Author :
     * @return :List<CustomerSiteVO>
     * @param :
     *            String customerId
     * @throws :RMDDAOException
     * @Description: This method is used for fetching the sites for the selected
     *               customer.
     */
    public List<CustomerSiteVO> getCustomerSites(String customerId) throws RMDDAOException;

    /**
     * @param String
     *            customerId, String site, String status
     * @return List<RepairFacilityDetailsResponseType>
     * @throws RMDDAOException
     * @Description This method is used to get the Repair Facility Details.
     */
    public List<RepairFacilityDetailsVO> getRepairFacilityDetails(String customerId, String site, String status)
            throws RMDDAOException;

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to insert new repair/site location
     *              details.
     */
    public String insertRepairSiteLoc(RepairFacilityDetailsVO objRepairDetailsVO) throws RMDDAOException;

    /**
     * @param List<RepairFacilityRequestType>
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to update existing repair/site location
     *              details.
     */
    public String updateRepairSiteLoc(List<RepairFacilityDetailsVO> arlReapirDetailsVO) throws RMDDAOException;

}

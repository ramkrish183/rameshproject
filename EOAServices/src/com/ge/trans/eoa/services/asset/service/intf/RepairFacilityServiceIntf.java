package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.CustomerSiteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RepairFacilityDetailsVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface RepairFacilityServiceIntf {

    /**
     * @Author :
     * @return :List<CustomerSiteVO>
     * @param :
     *            String customerId
     * @throws :RMDServiceException
     * @Description: This method is used for fetching the sites for the selected
     *               customer.
     */
    List<CustomerSiteVO> getCustomerSites(String customerId) throws RMDServiceException;

    /**
     * @param String
     *            customerId, String site, String status
     * @return List<RepairFacilityDetailsResponseType>
     * @throws RMDServiceException
     * @Description This method is used to get the Repair Facility Details.
     */
    List<RepairFacilityDetailsVO> getRepairFacilityDetails(String customerId, String site, String status)
            throws RMDServiceException;;

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to insert new repair/site location
     *              details.
     */
    String insertRepairSiteLoc(RepairFacilityDetailsVO objRepairDetailsVO) throws RMDServiceException;;

    /**
     * @param RepairFacilityRequestType
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to update existing repair/site location
     *              details.
     */
    String updateRepairSiteLoc(List<RepairFacilityDetailsVO> arlReapirDetailsVO) throws RMDServiceException;

}

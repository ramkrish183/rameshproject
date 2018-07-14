package com.ge.trans.eoa.services.kpi.bo.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.kpi.bo.intf.KpiEoaBOIntf;
import com.ge.trans.eoa.services.kpi.dao.intf.KpiEoaDAOIntf;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataCountResponseEoaVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataEoaBean;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountEoaServiceVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KpiAssetCountResponseVO;

public class KpiEoaBOImpl implements KpiEoaBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(KpiEoaBOImpl.class);

    /**
     * Declare Authenticator BO interface
     */
    private KpiEoaDAOIntf objKpiDAOIntf;

    /**
     * @param objAuthenticatorDAO
     */
    public KpiEoaBOImpl(KpiEoaDAOIntf objKpiDAOIntf) {
        this.objKpiDAOIntf = objKpiDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.AuthenticatorServiceIntf#
     * authorizeUser(com.ge.trans.rmd.services.cases.service.valueobjects.
     * UserVO) Method to check is a user is valid
     */
    @Override
    public List<KpiAssetCountResponseVO> getAssetKPICounts(final KpiAssetCountEoaServiceVO kpiAssetCountVO)
            throws RMDBOException {
        List<KpiAssetCountResponseVO> kpiAsstCountResVO = null;
        try {
            kpiAsstCountResVO = objKpiDAOIntf.getAssetKPICounts(kpiAssetCountVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return kpiAsstCountResVO;
    }

    /**
     * Callable class to make concurrent calls to KpiDAOImpl.getKPICounts()
     */
    public class KPICountsCallable implements Callable<Map<String, List<KPIDataCountResponseEoaVO>>> {
        @Override
        public Map<String, List<KPIDataCountResponseEoaVO>> call() throws Exception {
            Map<String, List<KPIDataCountResponseEoaVO>> map = new HashMap<String, List<KPIDataCountResponseEoaVO>>();
            map.put(kpiName, objKpiDAOIntf.getKPICounts(kpiDataBean));
            return map;
        }

        private String kpiName;
        private KPIDataEoaBean kpiDataBean;

        public KPICountsCallable(KPIDataEoaBean kpiDataBean) {
            this.kpiName = kpiDataBean.getKpiName();
            this.kpiDataBean = kpiDataBean.clone();
        }
    }

    /**
     * Callable class to make concurrent calls to KpiDAOImpl.getRxTotalCount()
     */
    public class RxTotalCountsCallable implements Callable<Map<String, List<KPIDataCountResponseEoaVO>>> {
        @Override
        public Map<String, List<KPIDataCountResponseEoaVO>> call() throws Exception {
            Map<String, List<KPIDataCountResponseEoaVO>> map = new HashMap<String, List<KPIDataCountResponseEoaVO>>();
            map.put(kpiName, objKpiDAOIntf.getRxTotalCount(kpiDataBean));
            return map;
        }

        private String kpiName;
        private KPIDataEoaBean kpiDataBean;

        public RxTotalCountsCallable(KPIDataEoaBean kpiDataBean) {
            this.kpiName = kpiDataBean.getKpiName();
            this.kpiDataBean = kpiDataBean.clone();
        }
    }

    @Override
    public Map<String, List<KPIDataCountResponseEoaVO>> getRxTotalCount(final KPIDataEoaBean kpiDataBean) {
        Map<String, List<KPIDataCountResponseEoaVO>> kpiMap = new HashMap<String, List<KPIDataCountResponseEoaVO>>();
        // Customer changed flag used for all customer scenario
        boolean isCustomerChanged = false;
        try {
            Iterator<KpiAssetCountResponseVO> it = kpiDataBean.getKpiList().iterator();
            ExecutorService executor = Executors.newFixedThreadPool(kpiDataBean.getKpiList().size());
            // create a list to hold the Future object associated with Callable
            List<Future<Map<String, List<KPIDataCountResponseEoaVO>>>> list = new ArrayList<Future<Map<String, List<KPIDataCountResponseEoaVO>>>>();
            // Create MyCallable instance
            while (it.hasNext()) {
                KpiAssetCountResponseVO tempVO = it.next();
                kpiDataBean.setKpiName(tempVO.getKpiName());
                kpiDataBean.setDays(tempVO.getNumDays());
                // Setting back the customer to 'all' for 3 KPIs
                if (isCustomerChanged) {
                    kpiDataBean.setCustomerId(RMDCommonConstants.EMPTY_STRING);
                }

                if (kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_RX_ACCURACY)
                        || kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_NTF)
                        || kpiDataBean.getKpiName().equals(RMDCommonConstants.KPI_RESPONSE_TIME)) {
                    // Setting the first customer to the KPIDataBean if the KPi
                    // are
                    // rx accuracy, ntf or response time
                    if (RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId()) || isCustomerChanged) {
                        String customerId = objKpiDAOIntf.getFirstCustomer(kpiDataBean);
                        if (null != customerId && !RMDCommonConstants.EMPTY_STRING.equals(customerId)) {
                            kpiDataBean.setCustomerId(customerId);
                            isCustomerChanged = true;
                        }
                    }
                    // Calling the DAO only if there is a valid customer
                    if (!RMDCommonConstants.EMPTY_STRING.equals(kpiDataBean.getCustomerId())) {

                        KPICountsCallable callable = new KPICountsCallable(kpiDataBean);
                        Future<Map<String, List<KPIDataCountResponseEoaVO>>> future = executor.submit(callable);
                        list.add(future);
                        // rxDataResponseVOList = objKpiDAOIntf
                        // .getKPICounts(kpiDataBean);
                    }
                }

                else {
                    RxTotalCountsCallable callable = new RxTotalCountsCallable(kpiDataBean);
                    Future<Map<String, List<KPIDataCountResponseEoaVO>>> future = executor.submit(callable);
                    list.add(future);
                    // rxDataResponseVOList = objKpiDAOIntf
                    // .getRxTotalCount(kpiDataBean);
                }
                // if(null != rxDataResponseVOList)
                // kpiMap.put(kpiName, rxDataResponseVOList);
            }
            for (Future<Map<String, List<KPIDataCountResponseEoaVO>>> fut : list) {
                try {
                    Map<String, List<KPIDataCountResponseEoaVO>> kpiItemsMap = fut.get();
                    for (Entry<String, List<KPIDataCountResponseEoaVO>> kpiItem : kpiItemsMap.entrySet()) {
                        if (null != kpiItem.getValue()) {
                            kpiMap.put(kpiItem.getKey(), kpiItem.getValue());
                        }
                    }
                } catch (InterruptedException ex) {
                    LOG.debug("Unexpected Error occured in KpiBOImpl getRxTotalCount()" + ex);
                } catch (ExecutionException ex) {
                    LOG.debug("Unexpected Error occured in KpiBOImpl getRxTotalCount()" + ex);
                }
            }
        } catch (RMDDAOException ex) {
            LOG.debug("Unexpected Error occured in RxDataBOImpl getRxClosedByUrgencyCount()" + ex);
            throw ex;
        } catch (Exception exc) {
            LOG.debug("Unexpected Error occured in RxDataBOImpl getRxClosedByUrgencyCount()" + exc);
        }
        return kpiMap;
    }
}

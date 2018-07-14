/**
* ============================================================
* Classification: GE Confidential
* File : BeanUtils.java
* Description :
* Package : com.ge.trans.rmd.services.util
* Author : iGATE Global Solutions Ltd.
* Last Edited By :
* Version : 1.0
* Created on : July 21, 2011
* History
* Modified By : iGATE
* Copyright (C) 2011 General Electric Company. All rights reserved
* ============================================================
*/
package com.ge.trans.pp.services.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.pp.common.constants.OMDConstants;
import com.ge.trans.pp.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusHistoryVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusResponseVO;
import com.ge.trans.pp.services.assets.valueobjects.PpAssetStatusHstryResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PpAssetStatusResponseType;
import com.ge.trans.pp.services.assets.valueobjects.NotificationTypeEnum;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

/*******************************************************************************
* @Author : iGATE
* @Version : 1.0
* @Date Created: August 31, 2012
* @Date Modified : August 31, 2012
* @Modified By :
* @Contact :
* @Description : This Class act as Common File for copy the properties of bean to bean
* @History :
******************************************************************************/
public class PPBeanUtility {

                public static final RMDLogger LOG = RMDLoggerHelper.getLogger(PPBeanUtility.class);

                @Autowired
                OMDResourceMessagesIntf omdResourceMessagesIntf;
                /**
                * @Author:
                * @Description:This method is used copy PPAssetStatusHistoryVO to PpAssetStatusHstryResponseType
                */
                public static void copyAsstStatHstryVOToAsstStatHstryResType(
                                                final PPAssetStatusHistoryVO pPAssetStatusHistoryVO,
                                                final PpAssetStatusHstryResponseType ppAssetStatusHstryResponseType) {
                                                String notificationType = null;
                                try {
                                                
                                                ppAssetStatusHstryResponseType.setAssetNumber(pPAssetStatusHistoryVO.getAssetNumber());
                                                ppAssetStatusHstryResponseType.setAssetGroupName(pPAssetStatusHistoryVO.getAssetGroupName());
                                                ppAssetStatusHstryResponseType.setCustomerId(pPAssetStatusHistoryVO.getCustomerId());
                                                ppAssetStatusHstryResponseType.setLatitude(pPAssetStatusHistoryVO.getLatitude());
                                                ppAssetStatusHstryResponseType.setLongitude(pPAssetStatusHistoryVO.getLongitude());
                                                ppAssetStatusHstryResponseType.setVelocity(pPAssetStatusHistoryVO.getVelocity());
                                                ppAssetStatusHstryResponseType.setHeading(pPAssetStatusHistoryVO.getHeading());
                                                ppAssetStatusHstryResponseType.setDirection(pPAssetStatusHistoryVO.getDirection());
                                                ppAssetStatusHstryResponseType.setDistance(pPAssetStatusHistoryVO.getDistance());
                                                ppAssetStatusHstryResponseType.setLocation(pPAssetStatusHistoryVO.getLocation());
                                                ppAssetStatusHstryResponseType.setFuelLvl(pPAssetStatusHistoryVO.getFuelLevel());
                                                ppAssetStatusHstryResponseType.setFuelAdded(pPAssetStatusHistoryVO.getFuelAdded());
                                                ppAssetStatusHstryResponseType.setEngine(pPAssetStatusHistoryVO.getEngine());
                                                ppAssetStatusHstryResponseType.setNotMoving(pPAssetStatusHistoryVO.getNotMoving());
                                                ppAssetStatusHstryResponseType.setState(pPAssetStatusHistoryVO.getState());
                                                ppAssetStatusHstryResponseType.setMilePost(pPAssetStatusHistoryVO.getMilePost());
                                                ppAssetStatusHstryResponseType.setRegion(pPAssetStatusHistoryVO.getRegion());
                                                ppAssetStatusHstryResponseType.setSubRegion(pPAssetStatusHistoryVO.getSubRegion());
                                                ppAssetStatusHstryResponseType.setEngineOn(pPAssetStatusHistoryVO.getEngineOn());
                                                ppAssetStatusHstryResponseType.setEngineCtrl(pPAssetStatusHistoryVO.getEngineCtrl());
                                                ppAssetStatusHstryResponseType.setLocoOrientation(pPAssetStatusHistoryVO.getLocoOrientation());
                                                ppAssetStatusHstryResponseType.setAtsMessageReason(pPAssetStatusHistoryVO.getAtsMessageReason());
                                                notificationType=pPAssetStatusHistoryVO.getNotificationType();
                                                if (OMDConstants.PP_NOTIFICATION_ALARM.equals(notificationType)) {
                                                                ppAssetStatusHstryResponseType
                                                                                                .setAssetStatusNotificationType(NotificationTypeEnum.ALARM);
                                                } 
                                                else if (OMDConstants.PP_NOTIFICATION_ALERT
                                                                                .equals(notificationType)) {
                                                                ppAssetStatusHstryResponseType
                                                                                                .setAssetStatusNotificationType(NotificationTypeEnum.ALERT);

                                                } 
                                                else {
                                                                ppAssetStatusHstryResponseType
                                                                                                .setAssetStatusNotificationType(NotificationTypeEnum.NORMAL);

                                                }
                                                ppAssetStatusHstryResponseType.setThrottlePosition(pPAssetStatusHistoryVO.getThrottlePosition());
                                                
                                } catch (Exception excep) {
                                                LOG.error("Error occured in CMBeanUtility - copyAsstStatHstryVOToAsstStatHstryResType" + excep);
                                }

                }
                /**
                * @Author:
                * @Description:This method is used copy PPAssetStatusResponseVO to PpAssetStatusResponseType
                */
                public static void copyPPAsstStatResVOToPpAsstStatResType(
                                                final PPAssetStatusResponseVO pPAssetStatusResponseVO,
                                                final PpAssetStatusResponseType ppAssetStatusResponseType) {

                                try {
                                                
                                                ppAssetStatusResponseType.setAssetNumber(pPAssetStatusResponseVO.getAssetNumber());
                                                ppAssetStatusResponseType.setAssetGroupName(pPAssetStatusResponseVO.getAssetGroupName());
                                                ppAssetStatusResponseType.setCustomerId(pPAssetStatusResponseVO.getCustomerId());
                                                ppAssetStatusResponseType.setCustomerName(pPAssetStatusResponseVO.getCustomerName());
                                                ppAssetStatusResponseType.setModel(pPAssetStatusResponseVO.getModel());
                                                ppAssetStatusResponseType.setLatitude(pPAssetStatusResponseVO.getLatitude());
                                                ppAssetStatusResponseType.setFuelLvl(pPAssetStatusResponseVO.getFuelLevel());
                                                ppAssetStatusResponseType.setLongitude(pPAssetStatusResponseVO.getLongitude());
                                                ppAssetStatusResponseType.setEngine(pPAssetStatusResponseVO.getEngine());
                                                ppAssetStatusResponseType.setVelocity(pPAssetStatusResponseVO.getVelocity());
                                                ppAssetStatusResponseType.setNotMoving(pPAssetStatusResponseVO.getNotMoving());
                                                ppAssetStatusResponseType.setHeading(pPAssetStatusResponseVO.getHeading());
                                                ppAssetStatusResponseType.setEngineOn(pPAssetStatusResponseVO.getEngineOn());
                                                ppAssetStatusResponseType.setDistance(pPAssetStatusResponseVO.getDistance());
                                                ppAssetStatusResponseType.setEngineCtrl(pPAssetStatusResponseVO.getEngControl());
                                                ppAssetStatusResponseType.setLocation(pPAssetStatusResponseVO.getLocation());
                                                ppAssetStatusResponseType.setLocoOrientation(pPAssetStatusResponseVO.getLocoOrientation());
                                                ppAssetStatusResponseType.setState(pPAssetStatusResponseVO.getState());
                                                ppAssetStatusResponseType.setActvNotifications(pPAssetStatusResponseVO.getActvNotifications());
                                                ppAssetStatusResponseType.setMilePost(pPAssetStatusResponseVO.getMilePost());
                                                ppAssetStatusResponseType.setRegion(pPAssetStatusResponseVO.getRegion());
                                                ppAssetStatusResponseType.setSubRegion(pPAssetStatusResponseVO.getSubRegion());
                                                ppAssetStatusResponseType.setNotificationType(pPAssetStatusResponseVO.getNotificationType());
                                                ppAssetStatusResponseType.setFleetNumber(pPAssetStatusResponseVO.getFleetNumber());
                                                ppAssetStatusResponseType.setRoadHdrNumber(pPAssetStatusResponseVO.getRoadHdrNumber());
                                                ppAssetStatusResponseType.setDirection(pPAssetStatusResponseVO.getDirection());
                                                ppAssetStatusResponseType.setThrottlePosition(pPAssetStatusResponseVO.getThrottlePosition());
                                                ppAssetStatusResponseType.setAtsMessageReason(pPAssetStatusResponseVO.getAtsMessageReason());
                                                
                                } catch (Exception excep) {
                                                LOG.error("Error occured in CMBeanUtility - copyPPAsstStatResVOToPpAsstStatResType" + excep);
                                }

                }
                
                

}

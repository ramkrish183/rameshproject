package com.ge.trans.eoa.services.asset.service.valueobjects;

public class MessageDefVO {

	
	private int  msgDefObjidApply;
	private int  msgDefObjidDelete;
	private String  msgNumCode;
	private String messagePriority;
	
	public String getMessagePriority() {
		return messagePriority;
	}
	public void setMessagePriority(String messagePriority) {
		this.messagePriority = messagePriority;
	}
	public int getMsgDefObjidApply() {
		return msgDefObjidApply;
	}
	public void setMsgDefObjidApply(int msgDefObjidApply) {
		this.msgDefObjidApply = msgDefObjidApply;
	}
	public int getMsgDefObjidDelete() {
		return msgDefObjidDelete;
	}
	public void setMsgDefObjidDelete(int msgDefObjidDelete) {
		this.msgDefObjidDelete = msgDefObjidDelete;
	}
	public String getMsgNumCode() {
		return msgNumCode;
	}
	public void setMsgNumCode(String msgNumCode) {
		this.msgNumCode = msgNumCode;
	}
	
	
	
}

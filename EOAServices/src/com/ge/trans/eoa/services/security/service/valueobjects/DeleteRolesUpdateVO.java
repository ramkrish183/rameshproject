
package com.ge.trans.eoa.services.security.service.valueobjects;

public class DeleteRolesUpdateVO {

	public String userId;
	public String currentRole;
	public String changedRoleId;
	private String userSeqId;
	public String getUserSeqId() {
		return userSeqId;
	}
	public void setUserSeqId(String userSeqId) {
		this.userSeqId = userSeqId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCurrentRole() {
		return currentRole;
	}
	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}
	public String getChangedRoleId() {
		return changedRoleId;
	}
	public void setChangedRoleId(String changedRoleId) {
		this.changedRoleId = changedRoleId;
	}
}

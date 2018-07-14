package com.ge.trans.eoa.services.cbr.service.valueobjects;

public class CBRAdminRequestVO {
    private String objid;
    private String flag;
    private String rxComment;
    private String strUserName;

    public String getObjid() {
        return objid;
    }

    public void setObjid(String objid) {
        this.objid = objid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRxComment() {
        return rxComment;
    }

    public void setRxComment(String rxComment) {
        this.rxComment = rxComment;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }
}

package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ctrlCfgResponseType", propOrder={"ctrlCfgObjId", "ctrlCfgName"})
@XmlRootElement
public class CtrlCfgResponseType
{

  @XmlElement(required=true)
  protected String ctrlCfgObjId;

  @XmlElement(required=true)
  protected String ctrlCfgName;

  public String getCtrlCfgObjId()
  {
    return this.ctrlCfgObjId;
  }
  public void setCtrlCfgObjId(String ctrlCfgObjId) {
    this.ctrlCfgObjId = ctrlCfgObjId;
  }
  public String getCtrlCfgName() {
    return this.ctrlCfgName;
  }
  public void setCtrlCfgName(String ctrlCfgName) {
    this.ctrlCfgName = ctrlCfgName;
  }
  
}
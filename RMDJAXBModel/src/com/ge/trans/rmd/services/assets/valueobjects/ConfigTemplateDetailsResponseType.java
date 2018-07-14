package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="configTemplateDetailsResponseType", propOrder={"tempObjId", "templateNo", "versionNo", "title", "status"})
@XmlRootElement
public class ConfigTemplateDetailsResponseType
{

  @XmlElement(required=true)
  protected String tempObjId;

  @XmlElement(required=true)
  protected String templateNo;

  @XmlElement(required=true)
  protected String versionNo;

  @XmlElement(required=true)
  protected String title;

  @XmlElement(required=true)
  protected String status;

  public String getTempObjId()
  {
    return this.tempObjId;
  }
  public void setTempObjId(String tempObjId) {
    this.tempObjId = tempObjId;
  }
  public String getTemplateNo() {
    return this.templateNo;
  }
  public void setTemplateNo(String templateNo) {
    this.templateNo = templateNo;
  }
  public String getVersionNo() {
    return this.versionNo;
  }
  public void setVersionNo(String versionNo) {
    this.versionNo = versionNo;
  }
  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getStatus() {
    return this.status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
}
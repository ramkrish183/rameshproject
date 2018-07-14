package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "creatorResponseType", propOrder = { "creatorList" })

@XmlRootElement
public class CreatorResponseType {

    @XmlElement(required = true)
    protected List<String> creatorList;

    /**
     * @return the creatorList
     */
    public List<String> getCreatorList() {
        return creatorList;
    }

    /**
     * @param creatorList
     *            the creatorList to set
     */
    public void setCreatorList(List<String> creatorList) {
        this.creatorList = creatorList;
    }

}

package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assetInstalledProductResponseType", propOrder = { "partNumber", "description", "warrantyEndDate" })

@XmlRootElement
public class AssetInstalledProductResponseType {

    @XmlElement(required = true)
    protected String partNumber;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar warrantyEndDate;

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public XMLGregorianCalendar getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(XMLGregorianCalendar warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

}


package com.ge.trans.rmd.cm.mcs.assetslistservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for assetsListServiceRequest complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="assetsListServiceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assetOwnerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roadInitial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roadNumberFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roadNumberTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assetLike" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assetsListServiceRequest", propOrder = { "assetOwnerId", "roadInitial", "roadNumberFrom",
        "roadNumberTo", "assetLike" })

public class AssetsListServiceRequest {

    protected String assetOwnerId;
    protected String roadInitial;
    protected String roadNumberFrom;
    protected String roadNumberTo;
    protected String assetLike;

    /**
     * Gets the value of the assetOwnerId property.
     * 
     * @return possible object is {@link String }
     */
    public String getAssetOwnerId() {
        return assetOwnerId;
    }

    /**
     * Sets the value of the assetOwnerId property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setAssetOwnerId(String value) {
        this.assetOwnerId = value;
    }

    /**
     * Gets the value of the roadInitial property.
     * 
     * @return possible object is {@link String }
     */
    public String getRoadInitial() {
        return roadInitial;
    }

    /**
     * Sets the value of the roadInitial property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setRoadInitial(String value) {
        this.roadInitial = value;
    }

    /**
     * Gets the value of the roadNumberFrom property.
     * 
     * @return possible object is {@link String }
     */
    public String getRoadNumberFrom() {
        return roadNumberFrom;
    }

    /**
     * Sets the value of the roadNumberFrom property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setRoadNumberFrom(String value) {
        this.roadNumberFrom = value;
    }

    /**
     * Gets the value of the roadNumberTo property.
     * 
     * @return possible object is {@link String }
     */
    public String getRoadNumberTo() {
        return roadNumberTo;
    }

    /**
     * Sets the value of the roadNumberTo property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setRoadNumberTo(String value) {
        this.roadNumberTo = value;
    }

    /**
     * Gets the value of the assetLike property.
     * 
     * @return possible object is {@link String }
     */
    public String getAssetLike() {
        return assetLike;
    }

    /**
     * Sets the value of the assetLike property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setAssetLike(String value) {
        this.assetLike = value;
    }

}


package com.ge.trans.rmd.cm.mcs.assetslistservice;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p>
 * Java class for assetsListServiceResponse complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="assetsListServiceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assetsData">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="asset" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="assetObjId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="assetOwnerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="roadInitial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="roadNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="controllerConfig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="fleet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="cmuSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="cmuIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="egaCommId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="installDate" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="swManifest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="antennaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="lastMsgReceived" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="assetStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assetsListServiceResponse", propOrder = { "assetsData" })
public class AssetsListServiceResponse {

    @XmlElement(required = true)
    protected AssetsListServiceResponse.AssetsData assetsData;

    /**
     * Gets the value of the assetsData property.
     * 
     * @return possible object is {@link AssetsListServiceResponse.AssetsData }
     */
    public AssetsListServiceResponse.AssetsData getAssetsData() {
        return assetsData;
    }

    /**
     * Sets the value of the assetsData property.
     * 
     * @param value
     *            allowed object is
     *            {@link AssetsListServiceResponse.AssetsData }
     */
    public void setAssetsData(AssetsListServiceResponse.AssetsData value) {
        this.assetsData = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="asset" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="assetObjId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="assetOwnerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="roadInitial" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="roadNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="controllerConfig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="fleet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="cmuSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="cmuIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="egaCommId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="installDate" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="swManifest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="antennaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="lastMsgReceived" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="assetStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "asset" })
    public static class AssetsData {

        protected List<AssetsListServiceResponse.AssetsData.Asset> asset;

        /**
         * Gets the value of the asset property.
         * <p>
         * This accessor method returns a reference to the live list, not a
         * snapshot. Therefore any modification you make to the returned list
         * will be present inside the JAXB object. This is why there is not a
         * <CODE>set</CODE> method for the asset property.
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getAsset().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AssetsListServiceResponse.AssetsData.Asset }
         */
        public List<AssetsListServiceResponse.AssetsData.Asset> getAsset() {
            if (asset == null) {
                asset = new ArrayList<AssetsListServiceResponse.AssetsData.Asset>();
            }
            return this.asset;
        }

        /**
         * <p>
         * Java class for anonymous complex type.
         * <p>
         * The following schema fragment specifies the expected content
         * contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="assetObjId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="assetOwnerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="roadInitial" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="roadNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="controllerConfig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="fleet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="cmuSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="cmuIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="egaCommId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="installDate" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="swManifest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="antennaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="lastMsgReceived" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="assetStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "assetObjId", "assetOwnerId", "roadInitial", "roadNumber", "model",
                "controllerConfig", "fleet", "cmuSN", "cmuIP", "egaCommId", "installDate", "swManifest", "antennaType",
                "lastMsgReceived", "assetStatus" })

        public static class Asset {

            @XmlElement(required = true)
            protected BigInteger assetObjId;
            @XmlElement(required = true)
            protected String assetOwnerId;
            @XmlElement(required = true)
            protected String roadInitial;
            @XmlElement(required = true)
            protected String roadNumber;
            protected String model;
            protected String controllerConfig;
            protected String fleet;
            protected String cmuSN;
            protected String cmuIP;
            protected String egaCommId;
            protected AssetsListServiceResponse.AssetsData.Asset.InstallDate installDate;
            protected String swManifest;
            protected String antennaType;
            protected AssetsListServiceResponse.AssetsData.Asset.LastMsgReceived lastMsgReceived;
            protected String assetStatus;

            /**
             * Gets the value of the assetObjId property.
             * 
             * @return possible object is {@link BigInteger }
             */
            public BigInteger getAssetObjId() {
                return assetObjId;
            }

            /**
             * Sets the value of the assetObjId property.
             * 
             * @param value
             *            allowed object is {@link BigInteger }
             */
            public void setAssetObjId(BigInteger value) {
                this.assetObjId = value;
            }

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
             * Gets the value of the roadNumber property.
             * 
             * @return possible object is {@link String }
             */
            public String getRoadNumber() {
                return roadNumber;
            }

            /**
             * Sets the value of the roadNumber property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setRoadNumber(String value) {
                this.roadNumber = value;
            }

            /**
             * Gets the value of the model property.
             * 
             * @return possible object is {@link String }
             */
            public String getModel() {
                return model;
            }

            /**
             * Sets the value of the model property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setModel(String value) {
                this.model = value;
            }

            /**
             * Gets the value of the controllerConfig property.
             * 
             * @return possible object is {@link String }
             */
            public String getControllerConfig() {
                return controllerConfig;
            }

            /**
             * Sets the value of the controllerConfig property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setControllerConfig(String value) {
                this.controllerConfig = value;
            }

            /**
             * Gets the value of the fleet property.
             * 
             * @return possible object is {@link String }
             */
            public String getFleet() {
                return fleet;
            }

            /**
             * Sets the value of the fleet property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setFleet(String value) {
                this.fleet = value;
            }

            /**
             * Gets the value of the cmuSN property.
             * 
             * @return possible object is {@link String }
             */
            public String getCmuSN() {
                return cmuSN;
            }

            /**
             * Sets the value of the cmuSN property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setCmuSN(String value) {
                this.cmuSN = value;
            }

            /**
             * Gets the value of the cmuIP property.
             * 
             * @return possible object is {@link String }
             */
            public String getCmuIP() {
                return cmuIP;
            }

            /**
             * Sets the value of the cmuIP property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setCmuIP(String value) {
                this.cmuIP = value;
            }

            /**
             * Gets the value of the egaCommId property.
             * 
             * @return possible object is {@link String }
             */
            public String getEgaCommId() {
                return egaCommId;
            }

            /**
             * Sets the value of the egaCommId property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setEgaCommId(String value) {
                this.egaCommId = value;
            }

            /**
             * Gets the value of the installDate property.
             * 
             * @return possible object is
             *         {@link AssetsListServiceResponse.AssetsData.Asset.InstallDate }
             */
            public AssetsListServiceResponse.AssetsData.Asset.InstallDate getInstallDate() {
                return installDate;
            }

            /**
             * Sets the value of the installDate property.
             * 
             * @param value
             *            allowed object is
             *            {@link AssetsListServiceResponse.AssetsData.Asset.InstallDate }
             */
            public void setInstallDate(AssetsListServiceResponse.AssetsData.Asset.InstallDate value) {
                this.installDate = value;
            }

            /**
             * Gets the value of the swManifest property.
             * 
             * @return possible object is {@link String }
             */
            public String getSwManifest() {
                return swManifest;
            }

            /**
             * Sets the value of the swManifest property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setSwManifest(String value) {
                this.swManifest = value;
            }

            /**
             * Gets the value of the antennaType property.
             * 
             * @return possible object is {@link String }
             */
            public String getAntennaType() {
                return antennaType;
            }

            /**
             * Sets the value of the antennaType property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setAntennaType(String value) {
                this.antennaType = value;
            }

            /**
             * Gets the value of the lastMsgReceived property.
             * 
             * @return possible object is
             *         {@link AssetsListServiceResponse.AssetsData.Asset.LastMsgReceived }
             */
            public AssetsListServiceResponse.AssetsData.Asset.LastMsgReceived getLastMsgReceived() {
                return lastMsgReceived;
            }

            /**
             * Sets the value of the lastMsgReceived property.
             * 
             * @param value
             *            allowed object is
             *            {@link AssetsListServiceResponse.AssetsData.Asset.LastMsgReceived }
             */
            public void setLastMsgReceived(AssetsListServiceResponse.AssetsData.Asset.LastMsgReceived value) {
                this.lastMsgReceived = value;
            }

            /**
             * Gets the value of the assetStatus property.
             * 
             * @return possible object is {@link String }
             */
            public String getAssetStatus() {
                return assetStatus;
            }

            /**
             * Sets the value of the assetStatus property.
             * 
             * @param value
             *            allowed object is {@link String }
             */
            public void setAssetStatus(String value) {
                this.assetStatus = value;
            }

            /**
             * <p>
             * Java class for anonymous complex type.
             * <p>
             * The following schema fragment specifies the expected content
             * contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "value" })
            public static class InstallDate {

                @XmlValue
                protected String value;
                @XmlAttribute
                protected String timezone;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return possible object is {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the timezone property.
                 * 
                 * @return possible object is {@link String }
                 */
                public String getTimezone() {
                    return timezone;
                }

                /**
                 * Sets the value of the timezone property.
                 * 
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setTimezone(String value) {
                    this.timezone = value;
                }

            }

            /**
             * <p>
             * Java class for anonymous complex type.
             * <p>
             * The following schema fragment specifies the expected content
             * contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "value" })
            public static class LastMsgReceived {

                @XmlValue
                protected String value;
                @XmlAttribute
                protected String timezone;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return possible object is {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the timezone property.
                 * 
                 * @return possible object is {@link String }
                 */
                public String getTimezone() {
                    return timezone;
                }

                /**
                 * Sets the value of the timezone property.
                 * 
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setTimezone(String value) {
                    this.timezone = value;
                }

            }

        }

    }

}


package com.ge.trans.rmd.cm.mcs.assetslistservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.ge.trans.rmd.cm.mcs.assetslistservice
 * package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AssetsListServiceResponse_QNAME = new QName(
            "http://www.getransporatation.com/railsolutions/mcs", "assetsListServiceResponse");
    private final static QName _AssetsListServiceRequest_QNAME = new QName(
            "http://www.getransporatation.com/railsolutions/mcs", "assetsListServiceRequest");
    private final static QName _FaultInfo_QNAME = new QName("http://www.getransporatation.com/railsolutions/mcs",
            "faultInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package:
     * com.ge.trans.rmd.cm.mcs.assetslistservice
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AssetsListServiceResponse }
     */
    public AssetsListServiceResponse createAssetsListServiceResponse() {
        return new AssetsListServiceResponse();
    }

    /**
     * Create an instance of
     * {@link AssetsListServiceResponse.AssetsData.Asset.InstallDate }
     */
    public AssetsListServiceResponse.AssetsData.Asset.InstallDate createAssetsListServiceResponseAssetsDataAssetInstallDate() {
        return new AssetsListServiceResponse.AssetsData.Asset.InstallDate();
    }

    /**
     * Create an instance of
     * {@link AssetsListServiceResponse.AssetsData.Asset.LastMsgReceived }
     */
    public AssetsListServiceResponse.AssetsData.Asset.LastMsgReceived createAssetsListServiceResponseAssetsDataAssetLastMsgReceived() {
        return new AssetsListServiceResponse.AssetsData.Asset.LastMsgReceived();
    }

    /**
     * Create an instance of {@link FaultInfo }
     */
    public FaultInfo createFaultInfo() {
        return new FaultInfo();
    }

    /**
     * Create an instance of {@link AssetsListServiceRequest }
     */
    public AssetsListServiceRequest createAssetsListServiceRequest() {
        return new AssetsListServiceRequest();
    }

    /**
     * Create an instance of {@link AssetsListServiceResponse.AssetsData.Asset }
     */
    public AssetsListServiceResponse.AssetsData.Asset createAssetsListServiceResponseAssetsDataAsset() {
        return new AssetsListServiceResponse.AssetsData.Asset();
    }

    /**
     * Create an instance of {@link AssetsListServiceResponse.AssetsData }
     */
    public AssetsListServiceResponse.AssetsData createAssetsListServiceResponseAssetsData() {
        return new AssetsListServiceResponse.AssetsData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}
     * {@link AssetsListServiceResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://www.getransporatation.com/railsolutions/mcs", name = "assetsListServiceResponse")
    public JAXBElement<AssetsListServiceResponse> createAssetsListServiceResponse(AssetsListServiceResponse value) {
        return new JAXBElement<AssetsListServiceResponse>(_AssetsListServiceResponse_QNAME,
                AssetsListServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}
     * {@link AssetsListServiceRequest }{@code >}}
     */
    @XmlElementDecl(namespace = "http://www.getransporatation.com/railsolutions/mcs", name = "assetsListServiceRequest")
    public JAXBElement<AssetsListServiceRequest> createAssetsListServiceRequest(AssetsListServiceRequest value) {
        return new JAXBElement<AssetsListServiceRequest>(_AssetsListServiceRequest_QNAME,
                AssetsListServiceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultInfo }
     * {@code >}}
     */
    @XmlElementDecl(namespace = "http://www.getransporatation.com/railsolutions/mcs", name = "faultInfo")
    public JAXBElement<FaultInfo> createFaultInfo(FaultInfo value) {
        return new JAXBElement<FaultInfo>(_FaultInfo_QNAME, FaultInfo.class, null, value);
    }

}

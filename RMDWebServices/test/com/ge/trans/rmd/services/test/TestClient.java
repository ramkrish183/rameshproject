package com.ge.trans.rmd.services.test;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;

import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.services.admin.valueobjects.ObjectFactory;
import com.ge.trans.rmd.services.admin.valueobjects.ProximityResponseType;


public class TestClient {
    private Service service;
	private JAXBContext jc;

	public static final RMDLogger LOG = RMDLogger.getLogger(TestClient.class);
	private static final String URL = "http://localhost:8080/omdservices/adminservice/saveProximityDetails";
	
	
    private static final QName QNAME = new QName("http://omd/omdservices/adminservice", "proximityResponse");
	public static void main(String[] args) {
		
		TestClient objTestClient=new TestClient();
		try{
		//objTestClient.acceptPOJAXB();
		objTestClient.newacceptPOJAXB();
		}
		catch (Exception e) {
			LOG.debug("exception occured");
		}
	}

	public TestClient() {
        try {
        	
            jc = JAXBContext.newInstance("com.ge.trans.omd.services.admin.valueobjects");
        } catch(JAXBException je) {
        	LOG.debug("exception occured");
        }
	
    }
	private  void acceptPOJAXB(){
		try{
		//HttpClient objHttpClient =new HttpClient();
		ProximityResponseType objProximityResponseType=new ProximityResponseType();
        objProximityResponseType.setNwLatitude("12345");
     
		//RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		service = Service.create(QNAME);
		service.addPort(QNAME, HTTPBinding.HTTP_BINDING, URL );
        Dispatch<Object> dispatcher = service.createDispatch(QNAME, jc, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "POST");
     /*   JAXBContext ctx=JAXBContext.newInstance(ProximityResponseType.class);
		StringWriter writer=new StringWriter();
		ctx.createMarshaller().marshal(objProximityResponseType, writer);
		String strempl=writer.toString();
		
	*/
        //JAXBElement<PurchaseOrder> order = new ObjectFactory().createPurchaseOrderDocument(createPO());
        JAXBElement<ProximityResponseType> order = new ObjectFactory().createProximityResponse(objProximityResponseType);
       
        @SuppressWarnings("unchecked")
		JAXBElement<ProximityResponseType> response = (JAXBElement<ProximityResponseType>)dispatcher.invoke(order);
        ProximityResponseType result= response.getValue();
        


		}	
		catch (Exception e) {
			LOG.debug("exception occured in acceptPOJAXB");
		}
	}

	private void newacceptPOJAXB() {
		try {
			URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/saveProximityDetails");
			HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			connection.setRequestProperty("Content-Type", "application/xml");
			OutputStream os = connection.getOutputStream();

			ProximityResponseType objProximityResponseType = new ProximityResponseType();
			objProximityResponseType.setNwLatitude("12345");
			objProximityResponseType.setNwLongitude("12");
			objProximityResponseType.setProximityLabel("sds");
			objProximityResponseType.setSeLongitude("sds");
			objProximityResponseType.setStatus("y");
			jc.createMarshaller().marshal(objProximityResponseType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception e) {
			LOG.debug("exception occured ");
		}
	}
}

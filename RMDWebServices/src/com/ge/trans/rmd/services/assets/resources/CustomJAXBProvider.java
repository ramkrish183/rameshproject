package com.ge.trans.rmd.services.assets.resources;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultEoaServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.ToolDsParmGroupServiceVO;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

@Provider
@Component
public class CustomJAXBProvider implements MessageBodyWriter<Object> {

    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(CustomJAXBProvider.class);

    @Autowired
    private static OMDResourceMessagesIntf omdResourceMessagesIntf;

    @Context
    protected Providers providers;

    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

        if (type == FaultDataDetailsServiceVO.class) {
            return true;
        } else {
            return false;
        }

    }

    public long getSize(Object les, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    private static boolean storeXMLFiles;

    @Value("${perf.store.client.unmaarshelled.xml}")
    String LOG_FILES;

    private static String urlPath = "Output";

    @Context
    UriInfo ui;
    @Context
    private ServletContext context;

    public void writeTo(Object responseObject, Class<?> responseType, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream out)
            throws IOException, WebApplicationException {
        RMDLOGGER.debug("Inside CustomJAXBProvider.writeTo()");
        try {
            if (ui != null) {
                URI abs = ui.getAbsolutePath();
                if (abs != null) {
                    String string = abs.getPath();
                    string = string.replaceAll("http:\\/\\/[^\\/]+\\/", "");
                    string = string.replaceAll("/", "#");
                    string = string.replaceAll("[\\W]", "$");
                    urlPath = string;
                }
            }
        } catch (Exception e) {
            RMDLOGGER.debug("", e);
            urlPath = "";
        }

        ContextResolver<JAXBContext> resolver = providers.getContextResolver(JAXBContext.class, mediaType);
        if (null != resolver) {
            JAXBContext context2 = resolver.getContext(responseType);
        }

        Class<? extends Object> responseClass = responseObject.getClass();

        if (responseClass.isArray()) {
            Class itemClass = responseClass.getComponentType();

            boolean hasXMLRootAnnotation = hasXMLRootAnnotation(itemClass);
            if (hasXMLRootAnnotation) {

                marshallArrayWithAnnotationItem(responseObject, new Class[] { itemClass, responseClass },
                        itemClass.getSimpleName() + "Array", itemClass.getSimpleName(), out);
                if (storeXMLFiles) {
                    String path = "./xml/" + urlPath + "-Array-" + System.currentTimeMillis() + ".xml";
                    File file = new File(path);
                    FileOutputStream fout = new FileOutputStream(file);
                    marshallArrayWithAnnotationItem(responseObject, new Class[] { itemClass, responseClass },
                            itemClass.getSimpleName() + "Array", itemClass.getSimpleName(), fout);
                }
            }
        } else if (responseObject instanceof List) {
            Class itemClass;
            List l = ((List) responseObject);
            if (!l.isEmpty()) {
                itemClass = l.get(0).getClass();
            } else {
                itemClass = responseClass;
            }

            boolean hasXMLRootAnnotation = hasXMLRootAnnotation(itemClass);
            if (hasXMLRootAnnotation) {
                marshallList(l, new Class[] { itemClass }, itemClass.getSimpleName() + "List",
                        itemClass.getSimpleName(), out);
                if (storeXMLFiles) {
                    String path = "./xml/" + urlPath + "-List-" + System.currentTimeMillis() + ".xml";
                    File file = new File(path);
                    FileOutputStream fout = new FileOutputStream(file);
                    marshallList(l, new Class[] { itemClass, responseClass }, itemClass.getSimpleName() + "List",
                            itemClass.getSimpleName(), fout);
                }

            } else {
                marshallList(l, new Class[] { itemClass }, itemClass.getSimpleName() + "List",
                        itemClass.getSimpleName(), out);
                if (storeXMLFiles) {
                    String path = "./xml/" + urlPath + "-List-" + System.currentTimeMillis() + ".xml";
                    File file = new File(path);
                    FileOutputStream fout = new FileOutputStream(file);
                    marshallList(l, new Class[] { itemClass, responseClass }, itemClass.getSimpleName() + "List",
                            itemClass.getSimpleName(), fout);
                }
            }

        } else if (responseObject instanceof ByteArrayInputStream) {
            copyStream((InputStream) responseObject, out);
            return;
        } else {

            boolean hasXMLRootAnnotation = hasXMLRootAnnotation(responseClass);
            if (hasXMLRootAnnotation) {
                marshallToXMLWithAnnotations(responseObject, responseClass.getSimpleName(), out,
                        new Class[] { responseClass });
            } else {
                marshallToXMLWithoutAnnotation(responseObject, responseClass.getSimpleName(), out,
                        new Class[] { responseClass });
            }
        }
        RMDLOGGER.debug("Exiting CustomJAXBProvider.writeTo()");
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024]; // Adjust if you want
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private boolean hasXMLRootAnnotation(Class responseClass) {

        Object a = responseClass.getAnnotation(XmlRootElement.class);
        if (a == null) {
            return false;
        } else {
            return true;
        }
    }

    private StringBuffer marshall(Class class1, Object e2, String rootName) {
        StringWriter out = new StringWriter();
        try {

            JAXBContext jc = JAXBContext.newInstance(e2.getClass());
            Marshaller m = jc.createMarshaller();
            QName qn = getQname(jc, e2.getClass(), "", rootName);
            JAXBElement jx = new JAXBElement(qn, e2.getClass(), e2);
            m.marshal(jx, out);
        } catch (JAXBException e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return out.getBuffer();

    }

    public static void marshallArrayWithAnnotationItem(Object arrayResponse, Class[] classes, String listElementName,
            String itemElementName, OutputStream out) {

        try {

            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(out);

            writer.writeStartDocument();
            writer.writeStartElement(listElementName);

            JAXBContext jc = JAXBContext.newInstance(classes);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            int length = Array.getLength(arrayResponse);
            for (int i = 0; i < length; i++) {
                Object arrayElement = Array.get(arrayResponse, i);
                QName qn = getQname(jc, classes[0], "", itemElementName);
                JAXBElement element = new JAXBElement(qn, classes[0], arrayElement);
                m.marshal(element, writer);
            }

            writer.writeEndDocument(); // this will close any open tags
            writer.close();

        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

    }

    public static Writer marshallList(List list, Class[] classes, String listElementName, String itemElementName,
            Writer out) {

        try {

            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(out);

            xmlWriter.writeStartDocument();
            xmlWriter.writeStartElement(listElementName);

            JAXBContext jc = JAXBContext.newInstance(classes);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            Iterator i = list.iterator();
            while (i.hasNext()) {
                Object next = i.next();
                QName qn = getQname(jc, next.getClass(), "", next.getClass().getSimpleName());
                JAXBElement element = new JAXBElement(qn, classes[0], next);
                m.marshal(element, xmlWriter);
            }
            xmlWriter.writeEndDocument(); // this will close any open tags
            xmlWriter.close();

        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return out;
    }

    public static void marshallList(List list, Class[] classes, String listElementName, String itemElementName,
            OutputStream out) {

        try {

            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(out);

            writer.writeStartDocument();
            writer.writeStartElement(listElementName);

            JAXBContext jc = JAXBContext.newInstance(classes);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            Iterator i = list.iterator();
            while (i.hasNext()) {
                Object next = i.next();
                QName qn = getQname(jc, next.getClass(), "", itemElementName);
                JAXBElement element = new JAXBElement(qn, next.getClass(), next);
                m.marshal(element, writer);
            }
            writer.writeEndDocument(); // this will close any open tags
            writer.close();

        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
    }

    public static void marshallListWithAnnotations(List list, Class[] classes, String listElementName,
            String itemElementName, OutputStream out) {
        try {

            JAXBContext context = JAXBContext.newInstance(classes[0], ArrayList.class, LinkedList.class, HashMap.class,
                    HashSet.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            QName qn = getQname(context, classes[0], listElementName, itemElementName);

            Class itemClass;
            if (!list.isEmpty()) {
                itemClass = list.get(0).getClass();
            } else {
                itemClass = list.getClass();
            }

            JAXBElement jx = new JAXBElement(qn, itemClass, list);

            m.marshal(jx, out);

        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
    }

    public static void marshallToXMLWithoutAnnotation(Object e2, String rootName, OutputStream out,
            Class... classesToBound) {
        try {
            Class[] classesToBound1 = getClassesToBound(e2);
            JAXBContext jc = JAXBContext.newInstance(classesToBound1);
            Marshaller m = jc.createMarshaller();
            QName qn = getQname(jc, e2.getClass(), "", rootName);
            JAXBElement jx = new JAXBElement(qn, e2.getClass(), e2);
            m.marshal(jx, out);
            writeToFile(jx, e2);

        } catch (JAXBException e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
    }

    private static void writeToFile(JAXBElement jx, Object e2) {
        if (storeXMLFiles) {
            String path = "./xml/" + urlPath + "-" + System.currentTimeMillis() + ".xml";
            File file = new File(path);

            try {
                FileWriter writer = new FileWriter(file);
                Class[] classesToBound1 = getClassesToBound(e2);
                JAXBContext jc = JAXBContext.newInstance(classesToBound1);
                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                m.marshal(jx, writer);
                writer.close();
            } catch (IOException e) {
                RMDLOGGER.debug("NOT_SEVERE:: IOException in writing back responses :: \n" + e.toString(), e);
                try {
                    RMDLOGGER.debug("Path ::" + file.getCanonicalPath());
                } catch (IOException e1) {
                    RMDLOGGER.debug("", e1);
                }
            } catch (JAXBException e) {
                RMDLOGGER.debug("NOT_SEVERE:: JAXBException in writing back responses :: \n" + e.toString(), e);
            }
        }
    }

    private static Class[] getClassesToBound(Object e2) {

        HashMap<String, Class[]> map = new HashMap<String, Class[]>();
        map.put("FaultDataDetailsServiceVO",
                new Class[] { FaultDataDetailsServiceVO.class, GetToolDsParminfoServiceVO.class,
                        FaultEoaServiceVO.class, ToolDsParmGroupServiceVO.class, FaultLogServiceVO.class,
                        FaultDetailsServiceVO.class });
        Class<? extends Object> class1 = e2.getClass();
        Class[] classesToReturn = map.get(class1.getSimpleName());

        if (classesToReturn == null) {
            classesToReturn = new Class[] { class1 };
        }
        return classesToReturn;
    }

    private static QName getQname(JAXBContext jc, Class class1, String newNamespace, String newQnameElementName) {
        JAXBIntrospector ji = jc.createJAXBIntrospector();
        QName qName = null;
        try {
            if (class1.isArray()) {
                class1 = class1.getComponentType();
                newQnameElementName = class1.getSimpleName() + "Array";
            }
            Object newInstance = class1.newInstance();
            qName = ji.getElementName(newInstance);
            if (null != qName) {
                if (qName.getNamespaceURI() == "" || qName.getNamespaceURI() == null) {
                    String nameSpaceStr = searchNameSpace(class1.getSimpleName());
                    if (nameSpaceStr != "") {
                        qName = new QName(nameSpaceStr, createEntityName(newQnameElementName));
                    }
                    return qName;
                }
                return qName;
            } else {
                String nameSpaceStr = searchNameSpace(class1.getSimpleName());
                if (nameSpaceStr != "") {

                    qName = new QName(nameSpaceStr, createEntityName(newQnameElementName));
                } else {
                    qName = new QName(newNamespace, createEntityName(newQnameElementName));
                }

            }
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return qName;
    }

    public static void marshallToXMLWithAnnotations(Object e2, String rootName, OutputStream out,
            Class... classesToBound) {
        try {
            JAXBContext jc = JAXBContext.newInstance(classesToBound);
            Marshaller m = jc.createMarshaller();
            QName qn = getQname(jc, e2.getClass(), "", e2.getClass().getSimpleName());
            JAXBElement jx = new JAXBElement(qn, e2.getClass(), e2);
            writeToFile(jx, e2);
            m.marshal(jx, out);
        } catch (JAXBException e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
    }

    public static String createEntityName(String inp) {
        String out = inp.substring(0, 1).toLowerCase() + inp.substring(1, inp.length());
        return out;
    }

    public static String searchNameSpace(String className) {
        String strNameSpace = "";
        Map<String, String> m1 = new HashMap<String, String>();
        m1.put("SelectCaseHome", "http://omd/omdservices/caseservice");
        m1.put("CaseInfoType", "http://omd/omdservices/caseservice");
        m1.put("SolutionInfoType", "http://omd/omdservices/caseservice");
        m1.put("SelectCaseHomeListVO", "http://omd/omdservices/caseservice");
        m1.put("VehicleCommStatusResponseType", "http://omd/omdservices/assetservice");
        m1.put("SelectCaseHomeListVO", "http://omd/omdservices/caseservice");
        m1.put("VehicleCommStatusResponseType", "http://omd/omdservices/assetservice");
        m1.put("FaultDataDetailsServiceVO", "http://omd/omdservices/assetservice");

        if (m1.get(className) != null) {
            strNameSpace = m1.get(className);
        }
        return strNameSpace;
    }

}

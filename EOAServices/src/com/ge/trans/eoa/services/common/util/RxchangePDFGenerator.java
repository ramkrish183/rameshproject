package com.ge.trans.eoa.services.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.xml.sax.SAXException;

import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeWhitePaperPDFVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.omi.exception.AppException;

public class RxchangePDFGenerator {

    private static final String PDF_FILE_EXTENSION = ".pdf";
    private static final String XML_FILE_EXTENSION = ".xml";
    private static final String XSL_FILE_EXTENSION = ".xsl";
    private static final String BASE_XSL_FILE_NAME = "rxchange_white_paper";
    private static final String CLASSPATH_SEPARATOR = "/";
    private static final String EXPECTED_CONFIG_DIR_NAME = "conf";
    private static final String EXPECTED_CONFIG_FILE_NAME = EXPECTED_CONFIG_DIR_NAME + CLASSPATH_SEPARATOR
            + "config.xconf";
    private static final String EXPECTED_FONT_DIR_NAME = "fonts";
    private static FopFactory fopFactory = null;
    private Logger logger;
   
    final List<String> utfLangList = Arrays.asList("Chinese", "Russian");
   

    public RxchangePDFGenerator(Logger log) {
        if (log == null) {
            logger = Logger.getLogger(RxchangePDFGenerator.class);
        } else {
            logger = log;
        }
    }

    public RxchangePDFGenerator() {

    }

    public File createPdfFile(RxChangeWhitePaperPDFVO data, String baseFilePath, String baseFileName) throws Exception {
        File pdfFile = null;
        String xmlPath = null;
        String pdfPath = null;

        if (!baseFilePath.endsWith(File.separator)) {
            baseFilePath += File.separator;
        }
        xmlPath = baseFilePath + baseFileName + XML_FILE_EXTENSION;
        pdfPath = baseFilePath + baseFileName + PDF_FILE_EXTENSION;

        try {
            if (data == null) {

                throw new AppException("Exception in createPdfFile, null Rx specification");
            } else if (xmlPath == null || xmlPath.isEmpty()) {

                throw new AppException("Exception in createPdfFile, null XML output path specification");
            } else if (pdfPath == null || pdfPath.isEmpty()) {

                throw new AppException("Exception in createPdfFile, null PDF output path specification");
            } else {

                if (buildJaxbXml(data, xmlPath)) {

                    InputStream xsltStream = getStyleSheetInputStream(data);

                    if (xsltStream != null) {

                        pdfFile = createPdfFile(xsltStream, xmlPath, pdfPath);
                        if (pdfFile == null) {

                            throw new AppException("Exception in createPdfFile, Null PDF File for expected output at:"
                                    + pdfPath);
                        } else if (!pdfFile.exists()) {

                            throw new AppException("Exception in createPdfFile, PDF file does not exist:"
                                    + pdfFile.getAbsolutePath());
                        } else if (!pdfFile.canRead()) {

                            throw new AppException("Exception in createPdfFile, PDF file is not readable:"
                                    + pdfFile.getAbsolutePath());
                        } else {
                            logger.debug("In createPdfFile, PDF File created at:" + pdfFile.getAbsolutePath());
                        }
                        xsltStream.close();
                    } else {

                        throw new AppException("Exception in createPdfFile, XSLinput stream is not readable");
                    }
                }
            }
        } catch (TransformerException e) {
            throw new AppException("TransformerException in createPdfFile:" + e.toString());
        } catch (Exception e) {
            throw new AppException("Exception in createPdfFile:" + e.toString());
        }
        return pdfFile;
    }

    private boolean buildJaxbXml(RxChangeWhitePaperPDFVO xmlData, String xmlPath) throws Exception {
        boolean result = false;
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RxChangeWhitePaperPDFVO.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            File xmlFile = new File(xmlPath);
            xmlFile.getParentFile().mkdirs();

            FileOutputStream fileOutputStream = new FileOutputStream(xmlPath);

            jaxbMarshaller.marshal(xmlData, fileOutputStream);
            fileOutputStream.close();
            result = true;
        } catch (JAXBException e) {
            throw new AppException("JAXBException in buildJaxbXml:" + e.toString());
        } catch (FileNotFoundException e) {
            throw new AppException("FileNotFoundException in buildJaxbXml:" + e.toString());
        } catch (Exception e) {
            throw new AppException("Exception in buildJaxbXml:" + e.toString());
        }
        return result;
    }

    /**
     * @Purpose To get xslpath for based on language pdf is to be generated.
     * @param rxVO
     * @return InputStream
     * @throws Exception
     */
    private InputStream getStyleSheetInputStream(RxChangeWhitePaperPDFVO rxVO) throws Exception {
        logger.debug("START :: getStyleSheetInputStream ");
        String baseXslPath = "";
        String xslPath = getStyleSheetFilePath(rxVO, baseXslPath);
        InputStream xsltStream = getInputStreamAtPath(xslPath);

        if (xsltStream == null || xsltStream.available() == 0) {
            xslPath = getDefaultStyleSheetFilePath(baseXslPath);
            xsltStream = getInputStreamAtPath(xslPath);
        }
        logger.debug("END :: getStyleSheetInputStream ");
        return xsltStream;
    }

    private String getStyleSheetFilePath(RxChangeWhitePaperPDFVO rxVO, String baseXslPath) {
        
        String xslFileName = null;

        if (baseXslPath != null && !baseXslPath.isEmpty()) {
            xslFileName = baseXslPath + CLASSPATH_SEPARATOR + BASE_XSL_FILE_NAME;
        } else {
            xslFileName = BASE_XSL_FILE_NAME;
        }

        xslFileName += XSL_FILE_EXTENSION;
       
        return xslFileName;
    }

    /**
     * @Purpose To get InputStream for filePath passed.
     * @param filePath
     * @return InputStream
     * @throws Exception
     */
    public InputStream getInputStreamAtPath(String filePath) throws Exception {
        
        InputStream returnStream = null;

        if (filePath != null && !filePath.isEmpty()) {

            returnStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            try {
                if (returnStream == null) {
                    // If returnStream is null we then try to read the file from system path
                    
                    ClassLoader loader = RxchangePDFGenerator.class.getClassLoader();
                    URL resourceURL = loader.getResource("com/ge/trans/eoa/services/common/resources/" + filePath);

                    if (resourceURL != null) {
                        String fileName = resourceURL.getFile();
                        

                        if (RxchangePDFGenerator.class.getClassLoader().getResourceAsStream(
                                RMDCommonConstants.PDF_RESOURCES_FILE_PATH + filePath) != null) {
                            returnStream = RxchangePDFGenerator.class.getClassLoader().getResourceAsStream(
                                    RMDCommonConstants.PDF_RESOURCES_FILE_PATH + filePath);
                        } else if (RxchangePDFGenerator.class.getClassLoader().getResourceAsStream(
                                RMDCommonConstants.PDF_RESOURCES_FILE_PATH_DOT + filePath) != null) {
                            returnStream = RxchangePDFGenerator.class.getClassLoader().getResourceAsStream(
                                    RMDCommonConstants.PDF_RESOURCES_FILE_PATH_DOT + filePath);
                        } else if (fileName != null && !fileName.isEmpty()) {
                            try {
                                File xslFile = new java.io.File(fileName);
                                returnStream = new FileInputStream(xslFile);
                                
                            } catch (Exception e) {
                                logger.debug("In getInputStreamAtPath, unable to get configuration file from classpath");
                            }
                        } else {
                            logger.debug("In getInputStreamAtPath, unable to get configuration file from classpath");
                            if (returnStream == null) {
                                returnStream = null;
                            }
                        }
                    } else {
                        logger.debug("In getInputStreamAtPath, unable to get configuration resource URL from classpath");
                        returnStream = null;
                    }
                }
            } catch (Exception e) {
                logger.debug("Exception in getInputStreamAtPath getting InputStream for:" + filePath + ", :"
                        + e.toString());
                returnStream = null;
            }
        }
        return returnStream;
    }

    /**
     * @Purpose To get default stylesheet path
     * @param baseXslPath
     * @return String
     */
    private String getDefaultStyleSheetFilePath(String baseXslPath) {
        String xslFileName = null;

        if (baseXslPath != null && !baseXslPath.isEmpty()) {
            xslFileName = baseXslPath + CLASSPATH_SEPARATOR + BASE_XSL_FILE_NAME;
        } else {
            xslFileName = BASE_XSL_FILE_NAME;
        }
        xslFileName += XSL_FILE_EXTENSION;

        return xslFileName;
    }

    /**
     * @Purpose To create Pdf
     * @param xsltStream
     * @param xmlPath
     * @param pdfPath
     * @return File
     * @throws Exception
     */
    public File createPdfFile(InputStream xsltStream, String xmlPath, String pdfPath) throws Exception {
        FileOutputStream outStream = null;
        File outFile = null;

        if (pdfPath != null && !pdfPath.isEmpty()) {
            try {
                
                File pdfFile = new File(pdfPath);
                pdfFile.getParentFile().mkdirs();

                // create new output streams.
                outStream = new FileOutputStream(pdfPath);
                
                if (generatePdf(xsltStream, xmlPath, outStream)) {
                    outFile = new File(pdfPath);
                    if (!outFile.exists()) {                        
                        throw new AppException("In createPdfFile, unable to create file at:" + pdfPath);
                    } else if (!outFile.canRead()) {
                        throw new AppException("In createPdfFile, unable to read file at:" + pdfPath);
                    }
                } else {
                    throw new AppException("In createPdfFile, unable to generate PDF at:" + pdfPath);
                }
            } catch (TransformerException e) {
                throw new AppException("TransformerException in createPdfFile:" + e.toString());
            } catch (Exception e) {
                throw new AppException("Exception in createPdfFile:" + e.toString());
            } finally {
                // releases any system resources associated with the stream
                if (outStream != null) {
                    outStream.close();
                }
            }
        }
        return outFile;
    }

    /**
     * @Purpose To generate pdf
     * @param xsltStream
     * @param xmlPath
     * @param pdfPath
     * @return Result of Pdf creation. True on succesful pdf creation and returns false on failure.
     * @throws Exception
     */
    private boolean generatePdf(InputStream xsltStream, String xmlPath, OutputStream out) throws Exception {
        boolean result = false;
        FopFactoryBuilder builder = new FopFactoryBuilder(new File(".").toURI());
        builder.setStrictFOValidation(false);
        FopFactory fopFactory = builder.build();
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        
        try {
            setFopFactory();
            StreamSource xmlSrc = new StreamSource(new File(xmlPath));
            
            // A UserAgent is required for transformation
            Fop fop = null;
            // Construct fop with desired output format
            try {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            } catch (Exception e) {
                logger.debug(" generatePdf :: "+e.getMessage());
            }

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();

            Transformer transformer = factory.newTransformer(new StreamSource(xsltStream));

            // Resulting SAX Events (the generated FO) must be piped thru to FOP
            Result saxResult = new SAXResult(fop.getDefaultHandler());

            // Start XSLT Transformation and FOP Processing
            // Thats where the XML is first transformed to XSL-FO and then pdf is created
            transformer.transform(xmlSrc, saxResult);
            result = true;
        } catch (SAXException e) {
            throw new AppException("SAXException in generatePdf:" + e.toString());
        } catch (IOException e) {
            throw new AppException("IOException in generatePdf:" + e.toString());
        } catch (TransformerConfigurationException e) {
            throw new AppException("TransformerConfigurationException in generatePdf:" + e.toString());
        } catch (TransformerException e) {
            throw new AppException("TransformerException in generatePdf:" + e.toString());
        } catch (Exception e) {
            throw new AppException("Exception in generatePdf:" + e.toString());
        } finally {
            if (xsltStream != null)
                xsltStream.close();
        }
        return result;
    }

    /**
     * @Purpose To set FopFactory
     * @throws Exception
     */
    private void setFopFactory() throws Exception {
        if (fopFactory == null) {
            try {
                File configFile = null;

                InputStream fopConfigStream = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(EXPECTED_CONFIG_FILE_NAME);
                

                if (fopConfigStream != null) {
                    CustomFontPathResolver customPathResolver = new CustomFontPathResolver();

                    customPathResolver.setLogger(logger);

                    URI defaultBaseURI = new File(".").toURI();
                    
                    FopFactoryBuilder fopFactoryBuilder = new FopFactoryBuilder(defaultBaseURI, customPathResolver);

                    DefaultConfigurationBuilder defaultConfigurationBuilder = new DefaultConfigurationBuilder();
                    Configuration customConfig = defaultConfigurationBuilder.build(fopConfigStream);

                    RxChangeWhitePaperPDFVO.fopFactory = fopFactoryBuilder.setConfiguration(customConfig).build();

                    fopConfigStream.close();
                } else {
                    ClassLoader loader = RxchangePDFGenerator.class.getClassLoader();
                    URL resourceURL = loader.getResource(EXPECTED_CONFIG_FILE_NAME);

                    if (resourceURL != null) {
                        String fileName = resourceURL.getFile();

                        if (fileName != null && !fileName.isEmpty()) {
                            configFile = new java.io.File(fileName);
                            RxchangePDFGenerator.fopFactory = FopFactory.newInstance(configFile);
                        } else {
                            throw new AppException("In setFopFactory, unable to get configuration file from classpath");
                        }
                    } else {
                        throw new AppException("In setFopFactory, unable to get configuration resource URL from classpath");
                    }
                }
            } catch (SAXException e) {
                throw new AppException("SAXException in setFopFactory:" + e.toString());
            } catch (IOException e) {
                throw new AppException("Exception in setFopFactory:" + e.toString());
            }
        }
    }

    /**
     * @CustomPathResolver to resolve resource urls.
     * @author 212556286
     */
    private static final class CustomFontPathResolver implements ResourceResolver {

        Logger logger;

        public void setLogger(Logger logger) {
            this.logger = logger;
        }

        public OutputStream getOutputStream(URI uri) throws IOException {
            return Thread.currentThread().getContextClassLoader().getResource(uri.toString()).openConnection()
                    .getOutputStream();
        }

        public Resource getResource(URI uri) throws IOException {           

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(EXPECTED_FONT_DIR_NAME + CLASSPATH_SEPARATOR
                    + FilenameUtils.getName(uri.toString()));

            return new Resource(inputStream);
        }
    }

    /**
     * @Purpose To get FopFactory object
     * @return Instance of FopFactory
     * @throws Exception
     */
    private FopFactory getFopFactory() throws Exception {
        if (fopFactory != null) {
            return RxchangePDFGenerator.fopFactory;
        } else {
            setFopFactory();
        }
        return RxchangePDFGenerator.fopFactory;
    }
}

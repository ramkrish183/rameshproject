package com.ge.trans.eoa.services.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.owasp.esapi.ESAPI;

import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeAdminVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class EmailContentGenerator {
    
    private static final RMDLogger emailContentLog = RMDLoggerHelper
            .getLogger(EmailContentGenerator.class);

    public String getEmailContent(RxChangeAdminVO rxChangeAdminVO, RxChangeVO rxChangeVO, String referredEnvStr) {
        
        VelocityEngine engine = null;
        String myTemplateBody = null;
        VelocityContext context = null;
        StringResourceRepository repository = null;
        Template template = null;
        StringWriter writer = null;
        String emailContent = null;

        try {
            // Getting Velocity Engine
            engine = getVelocityEngine(engine);

            // Reading Template Body from the template file(.vm file) in the jar
            myTemplateBody = getTemplateFromJar();

            // Setting the template body in string repository with a template
            // name. Here the template name is used as a key for future mapping.
            repository = StringResourceLoader.getRepository();
            repository.putStringResource("myTemplateName", myTemplateBody);

            // Getting the context with placeholder values
            context = getVelocityContext(rxChangeAdminVO, rxChangeVO, referredEnvStr);

            // Fetch Template to a StringWriter
            template = engine.getTemplate("myTemplateName");
            writer = new StringWriter();
            template.merge(context, writer);

            emailContentLog.debug("VM Template:\n" + myTemplateBody);
            emailContentLog.debug("Output:\n" + writer.toString());

            emailContent = writer.toString();            
            writer.flush();
            writer.close();
        } catch (Exception e) {
            emailContentLog.error("Oops! We have an exception", e);            
        }
        return emailContent;
    }

    private VelocityEngine getVelocityEngine(VelocityEngine engine) throws Exception {
        // Initializes the velocity engine with properties. We should specify
        // the resource loader as string and the class for
        // string.resource.loader in properties
        Properties p = new Properties();

        p.setProperty("resource.loader", "string");
        p.setProperty("string.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        p.put("eventhandler.referenceinsertion.class",
                "org.apache.velocity.app.event.implement.EscapeXmlReference");
        engine = new VelocityEngine();
        engine.init(p);

        return (engine);
    }

    public String getTemplateFromJar() {
        InputStream inStream = EmailContentGenerator.class
                .getResourceAsStream("/com/ge/trans/eoa/services/common/resources/RxChangeMailTemplate.vm");
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(inStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            emailContentLog.error("Oops! We have an exception : getTemplateFromJar ", e);  
        }
        return stringBuilder.toString();
    }

    public static VelocityContext getVelocityContext(RxChangeAdminVO rxChangeAdminVO, RxChangeVO rxChangeVO,
            String referredEnvStr) {
        VelocityContext context = new VelocityContext();

        context.put("RxChangeRequestnumber", rxChangeVO.getObjid());
        
        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getRevisionType())) {
            context.put("RevisionType", rxChangeVO.getRevisionType());
        }

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getRxTitle())) {
            context.put("RxTitle", rxChangeVO.getRxTitle());
        }

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getChangesSuggested())){
            String changesrequested = EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(rxChangeVO.getChangesSuggested()));
            context.put("Changesrequested", changesrequested);
           }
        
        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getUserName()))
            context.put("RequestorName", rxChangeVO.getUserName());

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getStatus()))
            context.put("RequestStatus", rxChangeVO.getStatus());

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getRequestLoggedDate()))
            context.put("Requestloggeddate", rxChangeVO.getRequestLoggedDate());

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getCustomer()))
            context.put("CustomerName", rxChangeVO.getCustomer());

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getModel()))
            context.put("ModelName", rxChangeVO.getModel());

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getRoadNumber()))
            context.put("RoadNumber", rxChangeVO.getRoadNumber());

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getCaseId()))
            context.put("CaseId", rxChangeVO.getCaseId());

        if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getNotestoRequestor()))
            context.put("Notestorequestor", EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(rxChangeVO.getNotestoRequestor())));

		if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getNotes())
				&& (rxChangeVO.getStatus().equalsIgnoreCase(
								RMDCommonConstants.RX_CHANGE_STATUS_OPEN)))
			context.put(
					"NotestoReviewer",
					EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(
							rxChangeVO.getNotes())));

        if (!RMDCommonUtility.isNullOrEmpty(referredEnvStr))
            context.put("strurl", referredEnvStr);

        if (null != rxChangeAdminVO) {
            if (!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getSummaryOfChanges()))
                context.put("ChangesSuggested", EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(rxChangeAdminVO.getSummaryOfChanges())));

            if (!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getReviewerNotes()))
                context.put("NotesfromReviewer", EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(rxChangeAdminVO.getReviewerNotes())));
        }

        return context;
    }

}

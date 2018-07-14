package com.ge.trans.eoa.services.rxchange.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.fop.apps.FopFactory;
/**
 * @author 307009968
 * @Purpose This is a POJO class for the main XML that will be generated for RxChange White Paper PDF
 */
@XmlRootElement(name="WHITE_PAPER_PDF_REPORT")
@XmlType(propOrder = { "reportHeader","issueDate","newRxCreated","rxsImpacted", "rxNote", "noOfRxAttachment", "rxChangeReasons", "modelsImpacted", "rxChangeRequestId", "requestedBy", "summaryOfChanges", "triggerLogicText", "requestedChanges", "lineBreak", "copyRight", "geLogoPath"})
public class RxChangeWhitePaperPDFVO {

        
        public static FopFactory fopFactory;
        private String issueDate="";        
        private String newRxCreated="";
        private List<String> rxsImpacted = new ArrayList<String>();
        private List<String> rxChangeReasons = new ArrayList<String>();
        private String modelsImpacted = "";
        private String rxChangeRequestId="";
        private String summaryOfChanges="";
        private String triggerLogicText="";
        private String requestedBy="";
        private String requestedChanges="";
        private String noOfRxAttachment;
        private final String lineBreak=" ";
        private String rxNote = "";
        private String geLogoPath = "";
        
        
        private String copyRight = "The information contained in this transmission, to the extent it is non-public, is the confidential, proprietary information of the General Electric Company and, in addition to any additional restrictions that may be imposed by Confidentiality or Non-Disclosure Agreements between GE and the recipient (or the recipient's employer), may not be disclosed, copied or used for other than its intended purpose without written permission from GE.";

       private static final String reportHeader = "GE Global Peformance Optimization Center (GPOC) Rx Change Notice";
        
        @XmlElement(name="REPORT_HEADER")
        public String getReportHeader() {
            return reportHeader;
        }

        @XmlElement(name="ISSUE_DATE")
        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        @XmlElement(name="EXISTING_OR_NEW_RX")
        public String getNewRxCreated() {
            return newRxCreated;
        }

        public void setNewRxCreated(String newRxCreated) {
            this.newRxCreated = newRxCreated;
        }

        @XmlElementWrapper(name="RX_TITLES")
        @XmlElement(name="RX_TITLES_IMPACTED")
        public List<String> getRxsImpacted() {
            return rxsImpacted;
        }

        public void setRxsImpacted(List<String> rxsImpacted) {
            this.rxsImpacted = rxsImpacted;
        }
        
        @XmlElementWrapper(name="TYPE_OF_CHANGE")
        @XmlElement(name="CHANGE_TYPE")
        public List<String> getRxChangeReasons() {
            return rxChangeReasons;
        }

        public void setRxChangeReasons(List<String> rxChangeReasons) {
            this.rxChangeReasons = rxChangeReasons;
        }

        @XmlElement(name="AFFECTED_MODELS")
        public String getModelsImpacted() {
            return modelsImpacted;
        }

        

        public void setModelsImpacted(String modelsImpacted) {
            this.modelsImpacted = modelsImpacted;
        }

        @XmlElement(name="GE_REQUEST_ID")
        public String getRxChangeRequestId() {
            return rxChangeRequestId;
        }


        public void setRxChangeRequestId(String rxChangeRequestId) {
            this.rxChangeRequestId = rxChangeRequestId;
        }

        @XmlElement(name="REQUESTED_BY")
        public String getRequestedBy() {
            return requestedBy;
        }

        public void setRequestedBy(String requestedBy) {
            this.requestedBy = requestedBy;
        }

        @XmlElement(name="SUMMARY")
        public String getSummaryOfChanges() {
            return summaryOfChanges;
        }

        public void setSummaryOfChanges(String summaryOfChanges) {
            this.summaryOfChanges = summaryOfChanges;
        }

        @XmlElement(name="TRIGGER_LOGIC_TEXT")
        public String getTriggerLogicText() {
            return triggerLogicText;
        }

        public void setTriggerLogicText(String triggerLogicText) {
            this.triggerLogicText = triggerLogicText;
        }

        @XmlElement(name="REQUESTED_CHANGES")
        public String getRequestedChanges() {
            return requestedChanges;
        }

        public void setRequestedChanges(String requestedChanges) {
            this.requestedChanges = requestedChanges;
        }

        @XmlElement(name="NUMBER_OF_RX_TITLE_IMPACTED")
        public String getNoOfRxAttachment() {
            return noOfRxAttachment;
        }

        public void setNoOfRxAttachment(String noOfRxAttachment) {
            this.noOfRxAttachment = noOfRxAttachment;
        }

        @XmlElement(name="COPYRIGHT")
        public String getCopyRight() {
            return copyRight;
        }
        
        public String setCopyRight(String copyRight) {
            return this.copyRight = copyRight;
        }
        
        @XmlElement(name="LINE")
        public String getLineBreak() {
            return lineBreak;
        }

        @XmlElement(name="RXTITLENOTE")
        public String getRxNote() {
            return rxNote;
        }

        public void setRxNote(String rxNote) {
            this.rxNote = rxNote;
        }
        
        
        @XmlElement(name="GE_LOGO_PATH")
        public String getGeLogoPath() {
            return geLogoPath;
        }

        public void setGeLogoPath(String geLogoPath) {
            this.geLogoPath = geLogoPath;
        }

        @Override
        public String toString() {
            return "RxChangeWhitePaperPDFVO [issueDate=" + issueDate + ", newRxCreated=" + newRxCreated
                    + ", rxsImpacted=" + rxsImpacted + ", rxChangeReasons=" + rxChangeReasons + ", modelsImpacted="
                    + modelsImpacted + ", rxChangeRequestId=" + rxChangeRequestId + ", summaryOfChanges="
                    + summaryOfChanges + ", triggerLogicText=" + triggerLogicText + ", requestedBy=" + requestedBy
                    + ", requestedChanges=" + requestedChanges + ", noOfRxAttachment=" + noOfRxAttachment
                    + ", lineBreak=" + lineBreak + ", rxNote=" + rxNote + ", copyRight=" + copyRight + ", geLogoPath="+geLogoPath + "]";
        }

           
    }

package com.ge.trans.eoa.services.cases.service.valueobjects;

public class FindCasesDetailsVO
{
        private String caseID;
        private String title;
        private String condition;
        private String status ;
        private String contact;
        private String creationTime;
        private String caseType;
        private String custRNH;
        private String rn;
        private String queue ;
        private String customerId;
        
        public String getCaseID() {
            return caseID;
        }
        public void setCaseID(String caseID) {
            this.caseID = caseID;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getCondition() {
            return condition;
        }
        public void setCondition(String condition) {
            this.condition = condition;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getContact() {
            return contact;
        }
        public void setContact(String contact) {
            this.contact = contact;
        }
        
        public String getCreationTime() {
            return creationTime;
        }
        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }
        public String getCaseType() {
            return caseType;
        }
        public void setCaseType(String caseType) {
            this.caseType = caseType;
        }
        public String getCustRNH() {
            return custRNH;
        }
        public void setCustRNH(String custRNH) {
            this.custRNH = custRNH;
        }
        public String getRn() {
            return rn;
        }
        public void setRn(String rn) {
            this.rn = rn;
        }
        public String getQueue() {
            return queue;
        }
        public void setQueue(String queue) {
            this.queue = queue;
        }
        public String getCustomerId() {
            return customerId;
        }
        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }
        
}

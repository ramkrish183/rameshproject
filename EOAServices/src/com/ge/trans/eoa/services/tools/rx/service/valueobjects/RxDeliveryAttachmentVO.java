
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class RxDeliveryAttachmentVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	private String strDocumentTitle;
    private String strDocumentPath;
    
	public String getStrDocumentTitle() {
		return strDocumentTitle;
	}
	public void setStrDocumentTitle(String strDocumentTitle) {
		this.strDocumentTitle = strDocumentTitle;
	}
	public String getStrDocumentPath() {
		return strDocumentPath;
	}
	public void setStrDocumentPath(String strDocumentPath) {
		this.strDocumentPath = strDocumentPath;
	}

}

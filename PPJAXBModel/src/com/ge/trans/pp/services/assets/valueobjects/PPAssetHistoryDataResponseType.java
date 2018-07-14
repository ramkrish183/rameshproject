package com.ge.trans.pp.services.assets.valueobjects;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jettison.json.JSONObject;
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "ppAssetHistoryDataResponseType", propOrder = { "ppAssetHistorylst", "recordsTotal" })
	@XmlRootElement
	public class PPAssetHistoryDataResponseType {

	    @XmlElement(required = true)
	    protected List<JSONObject> ppAssetHistorylst;
	    @XmlElement(required = true)
	    protected int recordsTotal;
		public List<JSONObject> getPpAssetHistorylst() {
			return ppAssetHistorylst;
		}
		public void setPpAssetHistorylst(List<JSONObject> ppAssetHistorylst) {
			this.ppAssetHistorylst = ppAssetHistorylst;
		}
		public int getRecordsTotal() {
			return recordsTotal;
		}
		public void setRecordsTotal(int recordsTotal) {
			this.recordsTotal = recordsTotal;
		}

	    

	}




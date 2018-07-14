package com.ge.trans.pp.services.assets.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pPAssetHistoryDetailsResponseType", propOrder = { "ppAssetHistoryHdrlst", "ppAssetHistorylst","recordCount" })
@XmlRootElement
public class PPAssetHistoryDetailsResponseType {

    @XmlElement(required = true)
    protected List<PPAssetHistHdrResponseType> ppAssetHistoryHdrlst;
    @XmlElement(required = true)
    protected List<PPAssetHResponseType> ppAssetHistorylst;
    @XmlElement(required = true)
    protected int recordCount;
    

    public List<PPAssetHistHdrResponseType> getPpAssetHistoryHdrlst() {
        if (ppAssetHistoryHdrlst == null) {
            ppAssetHistoryHdrlst = new ArrayList<PPAssetHistHdrResponseType>();
        }
        return this.ppAssetHistoryHdrlst;
    }

    public void setPpAssetHistoryHdrlst(List<PPAssetHistHdrResponseType> ppAssetHistoryHdrlst) {
        this.ppAssetHistoryHdrlst = ppAssetHistoryHdrlst;

    }

    public List<PPAssetHResponseType> getPpAssetHistorylst() {
        if (ppAssetHistorylst == null) {
            ppAssetHistorylst = new ArrayList<PPAssetHResponseType>();
        }
        return this.ppAssetHistorylst;
    }

    public void setPpAssetHistorylst(List<PPAssetHResponseType> ppAssetHistorylst) {
        this.ppAssetHistorylst = ppAssetHistorylst;
    }

    public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	@Override
    public String toString() {
        return "PPAssetHistoryDetailsResponseType [ppAssetHistoryHdrlst=" + ppAssetHistoryHdrlst
                + ", ppAssetHistorylst=" + ppAssetHistorylst + "]";
    }

}

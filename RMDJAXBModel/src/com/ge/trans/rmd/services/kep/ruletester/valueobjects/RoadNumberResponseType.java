package com.ge.trans.rmd.services.kep.ruletester.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoadNumberResponseType {

    private List<String> roadNumber;

    public List<String> getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(List<String> roadNumber) {
        this.roadNumber = roadNumber;
    }

}

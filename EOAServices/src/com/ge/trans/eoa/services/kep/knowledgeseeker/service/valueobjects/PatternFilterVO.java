package com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 24, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class PatternFilterVO {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public PatternFilterVO(final String key, final String value) {

        this.key = key;
        this.value = value;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}

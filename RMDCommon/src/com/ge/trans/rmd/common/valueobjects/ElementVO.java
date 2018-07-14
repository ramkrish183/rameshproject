/**
 * ============================================================
 * Classification: GE Confidential
 * File : ElementVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.services.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 1, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.common.valueobjects;

import org.apache.commons.lang.builder.ToStringBuilder;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 1, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class ElementVO extends BaseVO {

    private static final long serialVersionUID = 355555222L;
    private String id;
    private String name;
    private String type;
    private String customerSeqId;
    private String secNavigationURL;
    private String images;
    private String newFlag;
    private String resourceType;
    private String accesslevel;
    private Long sortOrder;
    private boolean isdefault = false;
    private boolean isAllCustomerdefault;
    private boolean isAllCustomer;
    private String customerFullName;
    // for getting the description and model family.

    private String description;
    private String family;

    public boolean isAllCustomerdefault() {
        return isAllCustomerdefault;
    }

    public void setAllCustomerdefault(boolean isAllCustomerdefault) {
        this.isAllCustomerdefault = isAllCustomerdefault;
    }

    public boolean isAllCustomer() {
        return isAllCustomer;
    }

    public void setAllCustomer(boolean isAllCustomer) {
        this.isAllCustomer = isAllCustomer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    /**
     * @return
     */
    public Long getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder
     */
    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the resourceType
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * @param resourceType
     *            the resourceType to set
     */
    public void setResourceType(final String resourceType) {
        this.resourceType = resourceType;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    /**
     * 
     */
    public ElementVO() {
        super();
    }

    /**
     * @param id
     * @param name
     */
    public ElementVO(final String id, final String name, final String type, final String secNavigationURL,
            final String images, final String newFlag) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.secNavigationURL = secNavigationURL;
        this.images = images;
        this.newFlag = newFlag;
    }

    /**
     * @param id
     * @param name
     */
    public ElementVO(final String name, final String accesslevel, final String secNavigationURL, final String images,
            final String newFlag) {
        super();

        this.name = name;
        this.accesslevel = accesslevel;
        this.secNavigationURL = secNavigationURL;
        this.images = images;
        this.newFlag = newFlag;
    }

    /**
     * @return the newFlag
     */
    public String getNewFlag() {
        return newFlag;
    }

    /**
     * @param newFlag
     *            the newFlag to set
     */
    public void setNewFlag(final String newFlag) {
        this.newFlag = newFlag;
    }

    /**
     * @param id
     * @param name
     */
    public ElementVO(final String id, final String name, final String type) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
    }

    /**
     * @param id
     * @param name
     */
    public ElementVO(final String id, final String name, final String type, final Long sortOrder) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.sortOrder = sortOrder;
    }

    public ElementVO(final String id, final String name) {
        super();
        this.id = id;
        this.name = name;

    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the secNavigationURL
     */
    public String getSecNavigationURL() {
        return secNavigationURL;
    }

    /**
     * @param secNavigationURL
     *            the secNavigationURL to set
     */
    public void setSecNavigationURL(final String secNavigationURL) {
        this.secNavigationURL = secNavigationURL;
    }

    /**
     * @return the images
     */
    public String getImages() {
        return images;
    }

    /**
     * @param images
     *            the images to set
     */
    public void setImages(final String images) {
        this.images = images;
    }

    /**
     * @return the accesslevel
     */
    public String getAccesslevel() {
        return accesslevel;
    }

    /**
     * @param accesslevel
     *            the accesslevel to set
     */
    public void setAccesslevel(final String accesslevel) {
        this.accesslevel = accesslevel;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).toString();
    }

    public String getCustomerSeqId() {
        return customerSeqId;
    }

    public void setCustomerSeqId(String customerSeqId) {
        this.customerSeqId = customerSeqId;
    }

    public boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(boolean b) {
        this.isdefault = b;
    }

	/**
	 * @return the customerFullName
	 */
	public String getCustomerFullName() {
		return customerFullName;
	}

	/**
	 * @param customerFullName the customerFullName to set
	 */
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

}

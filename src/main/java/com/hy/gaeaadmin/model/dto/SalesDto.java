/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.dto;

import java.util.Date;

/**
 *
 * @author hendryyu
 */
public class SalesDto {
    private Integer id;
    private String trxNum;
    private Date trxDate;
    private Date podDateOut;
    private Date podDateIn;
    private String podDeliveryAddr;
    private String occassionType;
    private Date eventDate;
    private String eventLocation;
    private String trxNote;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private CustomerDto customerDto;
    private int salesTypeId;
    private String salesTypeName;
    private int universityId;
    private int universityName;
    private boolean updateCustomer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrxNum() {
        return trxNum;
    }

    public void setTrxNum(String trxNum) {
        this.trxNum = trxNum;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public Date getPodDateOut() {
        return podDateOut;
    }

    public void setPodDateOut(Date podDateOut) {
        this.podDateOut = podDateOut;
    }

    public Date getPodDateIn() {
        return podDateIn;
    }

    public void setPodDateIn(Date podDateIn) {
        this.podDateIn = podDateIn;
    }

    public String getPodDeliveryAddr() {
        return podDeliveryAddr;
    }

    public void setPodDeliveryAddr(String podDeliveryAddr) {
        this.podDeliveryAddr = podDeliveryAddr;
    }

    public String getOccassionType() {
        return occassionType;
    }

    public void setOccassionType(String occassionType) {
        this.occassionType = occassionType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getTrxNote() {
        return trxNote;
    }

    public void setTrxNote(String trxNote) {
        this.trxNote = trxNote;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public int getSalesTypeId() {
        return salesTypeId;
    }

    public void setSalesTypeId(int salesTypeId) {
        this.salesTypeId = salesTypeId;
    }

    public String getSalesTypeName() {
        return salesTypeName;
    }

    public void setSalesTypeName(String salesTypeName) {
        this.salesTypeName = salesTypeName;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public int getUniversityName() {
        return universityName;
    }

    public void setUniversityName(int universityName) {
        this.universityName = universityName;
    }

    public boolean isUpdateCustomer() {
        return updateCustomer;
    }

    public void setUpdateCustomer(boolean updateCustomer) {
        this.updateCustomer = updateCustomer;
    }
    
    
    
}

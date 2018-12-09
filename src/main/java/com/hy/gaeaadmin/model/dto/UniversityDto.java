/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.dto;

import com.hy.gaeaadmin.model.entity.Sales;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hendryyu
 */
public class UniversityDto {
    private Integer id;
    private String universityName;
    private String universityAddress1;
    private String universityAddress2;
    private String universityPhone1;
    private String universityPhone2;
    private int enable;
    private String createdBy;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private List<SalesDto> salesList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityAddress1() {
        return universityAddress1;
    }

    public void setUniversityAddress1(String universityAddress1) {
        this.universityAddress1 = universityAddress1;
    }

    public String getUniversityAddress2() {
        return universityAddress2;
    }

    public void setUniversityAddress2(String universityAddress2) {
        this.universityAddress2 = universityAddress2;
    }

    public String getUniversityPhone1() {
        return universityPhone1;
    }

    public void setUniversityPhone1(String universityPhone1) {
        this.universityPhone1 = universityPhone1;
    }

    public String getUniversityPhone2() {
        return universityPhone2;
    }

    public void setUniversityPhone2(String universityPhone2) {
        this.universityPhone2 = universityPhone2;
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

    public List<SalesDto> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<SalesDto> salesList) {
        this.salesList = salesList;
    }

    
    
}

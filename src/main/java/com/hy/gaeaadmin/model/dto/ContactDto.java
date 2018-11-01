/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hendryyu
 */
public class ContactDto {
    private Integer id;
    private String value;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private Integer contactTypeId;
    private String contactTypeName;
    private List<EmployeeDto> employeeList = new ArrayList<>();
    private List<CustomerDto> customerList = new ArrayList<>();
    private List<UserDto> userContactList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EmployeeDto> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeDto> employeeList) {
        this.employeeList = employeeList;
    }

    public List<CustomerDto> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerDto> customerList) {
        this.customerList = customerList;
    }

    public List<UserDto> getUserContactList() {
        return userContactList;
    }

    public void setUserContactList(List<UserDto> userContactList) {
        this.userContactList = userContactList;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }

    public Integer getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(Integer contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
    
    
}

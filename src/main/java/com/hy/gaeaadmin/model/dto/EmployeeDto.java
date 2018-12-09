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
 * @author ndry93
 */
public class EmployeeDto {
    private Integer id;
    private String employeeNum;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String placeOfBirth;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private List<PositionDto> positionList = new ArrayList<>();
    private List<SalaryDto> salaryList;
    private String addrStreet1;
    private String addrStreet2;
    private Integer addrPostalCode;
    private Integer addrCityId;
    private String contactPhone1;
    private String contactPhone2;
    private String contactEmail1;
    private String contactEmail2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PositionDto> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<PositionDto> positionList) {
        this.positionList = positionList;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
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

    public List<SalaryDto> getSalaryList() {
        return salaryList;
    }

    public void setSalaryList(List<SalaryDto> salaryList) {
        this.salaryList = salaryList;
    }

    public String getAddrStreet1() {
        return addrStreet1;
    }

    public void setAddrStreet1(String addrStreet1) {
        this.addrStreet1 = addrStreet1;
    }

    public String getAddrStreet2() {
        return addrStreet2;
    }

    public void setAddrStreet2(String addrStreet2) {
        this.addrStreet2 = addrStreet2;
    }

    public Integer getAddrPostalCode() {
        return addrPostalCode;
    }

    public void setAddrPostalCode(Integer addrPostalCode) {
        this.addrPostalCode = addrPostalCode;
    }

    public Integer getAddrCityId() {
        return addrCityId;
    }

    public void setAddrCityId(Integer addrCityId) {
        this.addrCityId = addrCityId;
    }

    public String getContactPhone1() {
        return contactPhone1;
    }

    public void setContactPhone1(String contactPhone1) {
        this.contactPhone1 = contactPhone1;
    }

    public String getContactPhone2() {
        return contactPhone2;
    }

    public void setContactPhone2(String contactPhone2) {
        this.contactPhone2 = contactPhone2;
    }

    public String getContactEmail1() {
        return contactEmail1;
    }

    public void setContactEmail1(String contactEmail1) {
        this.contactEmail1 = contactEmail1;
    }

    public String getContactEmail2() {
        return contactEmail2;
    }

    public void setContactEmail2(String contactEmail2) {
        this.contactEmail2 = contactEmail2;
    }
    
    
    
}

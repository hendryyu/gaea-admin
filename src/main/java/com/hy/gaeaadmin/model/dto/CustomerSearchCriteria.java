/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author hendryyu
 */
public class CustomerSearchCriteria {
    private Integer id;
//    @NotBlank(message = "customer phone can't empty!")
//    private String phone;

//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
}

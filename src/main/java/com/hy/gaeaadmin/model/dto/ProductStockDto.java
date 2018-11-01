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
public class ProductStockDto {
    private Integer id;
    private double quantity;
    private double basePrice;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private CurrencyDto currencyDto;
    private ProductDto productDto;
    private UomDto uomDto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
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

    public CurrencyDto getCurrencyDto() {
        return currencyDto;
    }

    public void setCurrencyDto(CurrencyDto currencyDto) {
        this.currencyDto = currencyDto;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public UomDto getUomDto() {
        return uomDto;
    }

    public void setUomDto(UomDto uomDto) {
        this.uomDto = uomDto;
    }
    
    
    
}

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
public class ProductDto {
    private Integer id;
    private String productCode;
    private int productName;
    private int productDesc;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private ProductTypeDto productTypeDto;
    private List<ProductPictureDto> productPictureDtoList = new ArrayList<>();
    private List<ProductStockDto> productStockList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getProductName() {
        return productName;
    }

    public void setProductName(int productName) {
        this.productName = productName;
    }

    public int getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(int productDesc) {
        this.productDesc = productDesc;
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

    public ProductTypeDto getProductTypeDto() {
        return productTypeDto;
    }

    public void setProductTypeDto(ProductTypeDto productTypeDto) {
        this.productTypeDto = productTypeDto;
    }

    public List<ProductPictureDto> getProductPictureDtoList() {
        return productPictureDtoList;
    }

    public void setProductPictureDtoList(List<ProductPictureDto> productPictureDtoList) {
        this.productPictureDtoList = productPictureDtoList;
    }

    public List<ProductStockDto> getProductStockList() {
        return productStockList;
    }

    public void setProductStockList(List<ProductStockDto> productStockList) {
        this.productStockList = productStockList;
    }
    
    
}

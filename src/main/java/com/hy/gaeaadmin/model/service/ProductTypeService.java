/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.ProductTypeDto;
import com.hy.gaeaadmin.model.entity.MstProductType;
import com.hy.gaeaadmin.model.repo.ProductTypeRepo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hendryyu
 */
@Service
public class ProductTypeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductTypeRepo productTypeRepo;
    
    public List<ProductTypeDto> list(){
        List<ProductTypeDto> positionList = new ArrayList<>();
        for(MstProductType mstProductType : productTypeRepo.findAll()){
            positionList.add(this.convertToDto(mstProductType));
        }
        return positionList;
    }
    
    public void delete(Integer positionId){
        MstProductType mstProductType = productTypeRepo.findById(positionId).orElse(null);
        if(mstProductType != null)
        if(mstProductType.getProductList()!= null && mstProductType.getProductList().size() > 0) {
            throw new RuntimeException("Product Type cannot be deleted because data is already in use");
        }else{
            productTypeRepo.delete(mstProductType);
        }
    }
    
    public ProductTypeDto findById(Integer positionId){
        if(positionId == null) return null;
        MstProductType mstProductType = productTypeRepo.findById(positionId).orElse(null);
        return mstProductType == null ? null : this.convertToDto(mstProductType);
    }
    
    public ProductTypeDto save(ProductTypeDto productTypeDto){
        ProductTypeDto result = null;
        try {
            ProductTypeDto productTypeDtoTemp = this.findById(productTypeDto.getId());
            if(productTypeDtoTemp == null){
                productTypeDtoTemp = productTypeDto;
                productTypeDtoTemp.setCreatedBy("spring");
                productTypeDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(productTypeRepo.save(this.convertToEntity(productTypeDtoTemp)));
            }else{
                productTypeDto.setUpdatedBy("1234");
                productTypeDto.setUpdatedDate(new Date());
                
                MstProductType mstProductType = this.convertToEntity(productTypeDto);
                result = this.convertToDto(productTypeRepo.save(mstProductType));
            }
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ProductTypeDto convertToDto(MstProductType mstProductType) {
        ProductTypeDto productTypeDto = modelMapper.map(mstProductType, ProductTypeDto.class);
        return productTypeDto;
    }
    
    public MstProductType convertToEntity(ProductTypeDto productTypeDto) throws ParseException {
        MstProductType mstProductType = modelMapper.map(productTypeDto, MstProductType.class);
        return mstProductType;
    }
}

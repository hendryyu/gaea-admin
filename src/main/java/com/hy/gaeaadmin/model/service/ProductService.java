/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.ProductDto;
import com.hy.gaeaadmin.model.dto.ProductTypeDto;
import com.hy.gaeaadmin.model.entity.MstProductType;
import com.hy.gaeaadmin.model.entity.Product;
import com.hy.gaeaadmin.model.repo.ProductRepo;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author hendryyu
 */
@Service
public class ProductService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductTypeService productTypeService;
    
    public List<ProductDto> list(){
        List<ProductDto> positionList = new ArrayList<>();
        for(Product product : productRepo.findAll(new Sort(Sort.Direction.ASC,"productCode"))){
            positionList.add(this.convertToDto(product));
        }
        return positionList;
    }
    
    public void delete(Integer positionId){
        Product product = productRepo.findById(positionId).orElse(null);
        if(product != null)
//        if(product.getProductList()!= null && product.getProductList().size() > 0) {
//            throw new RuntimeException("Product Type cannot be deleted because data is already in use");
//        }else{
            productRepo.delete(product);
//        }
    }
    
    public ProductDto findById(Integer positionId){
        if(positionId == null) return null;
        Product product = productRepo.findById(positionId).orElse(null);
        return product == null ? null : this.convertToDto(product);
    }
    
    public ProductDto save(ProductDto productDto){
        ProductDto result = null;
        try {
            ProductDto productDtoTemp = this.findById(productDto.getId());
            if(productDtoTemp == null){
                productDtoTemp = productDto;
                productDtoTemp.setCreatedBy("spring");
                productDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(productRepo.save(this.convertToEntity(productDtoTemp)));
            }else{
                System.out.println("----- "+productDto.getNote());
                productDto.setUpdatedBy("1234");
                productDto.setUpdatedDate(new Date());
                
                Product product = this.convertToEntity(productDto);
                result = this.convertToDto(productRepo.save(product));
            }
        } catch (ParseException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        
        ProductTypeDto productTypeDto = productTypeService.convertToDto(product.getMstProductType());
        productDto.setProductTypeId(productTypeDto.getId());
        productDto.setProductTypeName(productTypeDto.getTypeName());
        return productDto;
    }
    
    public Product convertToEntity(ProductDto productDto) throws ParseException {
        Product product = modelMapper.map(productDto, Product.class);
        
        MstProductType mstProductType = new MstProductType();
        mstProductType.setId(productDto.getProductTypeId());
        product.setMstProductType(mstProductType);
        
        
        
        
        return product;
    }
}

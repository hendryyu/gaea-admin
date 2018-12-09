/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.ProductPictureDto;
import com.hy.gaeaadmin.model.entity.ProductPicture;
import com.hy.gaeaadmin.model.repo.ProductPictureRepo;
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
public class ProductPictureService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductPictureRepo productPictureRepo;
    
    public List<ProductPictureDto> list(){
        List<ProductPictureDto> positionList = new ArrayList<>();
        for(ProductPicture productPicture : productPictureRepo.findAll()){
            positionList.add(this.convertToDto(productPicture));
        }
        return positionList;
    }
    
    public void delete(Integer id){
        ProductPicture productPicture = productPictureRepo.findById(id).orElse(null);
        if(productPicture != null)
//        if(productPicture.get()!= null && productPicture.getAddressList().size() > 0) {
//            throw new RuntimeException("City cannot be deleted because data is already in use");
//        }else{
            productPictureRepo.delete(productPicture);
//        }
    }
    
    public ProductPictureDto findById(Integer id){
        if(id == null) return null;
        ProductPicture productPicture = productPictureRepo.findById(id).orElse(null);
        return productPicture == null ? null : this.convertToDto(productPicture);
    }
    
    public List<ProductPictureDto> findByProductId(Integer productId){
        List<ProductPictureDto> productPictureDtoList = new ArrayList<>();
        List<ProductPictureDto> dataList = this.list();
        if(dataList!=null){
            System.out.println("-----dataList "+dataList);
            for(ProductPictureDto data : dataList){
                System.out.println("-----data "+data);
                System.out.println("-----data.getProductId() "+data.getProductId());
                if(data.getProductId() == productId){
                    productPictureDtoList.add(data);
                }
            }
        }
        return productPictureDtoList;
    }
    
    public ProductPictureDto save(ProductPictureDto productPictureDto){
        ProductPictureDto result = null;
        try {
            ProductPictureDto productPictureDtoTemp = this.findById(productPictureDto.getId());
            if(productPictureDtoTemp == null){
                productPictureDtoTemp = productPictureDto;
                productPictureDtoTemp.setCreatedBy("spring");
                productPictureDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(productPictureRepo.save(this.convertToEntity(productPictureDtoTemp)));
            }else{
                productPictureDto.setUpdatedBy("1234");
                productPictureDto.setUpdatedDate(new Date());
                
                ProductPicture productPicture = this.convertToEntity(productPictureDto);
                result = this.convertToDto(productPictureRepo.save(productPicture));
            }
        } catch (ParseException ex) {
            Logger.getLogger(ProductPictureService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ProductPictureDto convertToDto(ProductPicture productPicture) {
        ProductPictureDto productPictureDto = modelMapper.map(productPicture, ProductPictureDto.class);
        
        
        return productPictureDto;
    }
    
    public ProductPicture convertToEntity(ProductPictureDto productPictureDto) throws ParseException {
        ProductPicture productPicture = modelMapper.map(productPictureDto, ProductPicture.class);
        return productPicture;
    }
}

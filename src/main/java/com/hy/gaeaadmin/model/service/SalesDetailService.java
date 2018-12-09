/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.ProductDto;
import com.hy.gaeaadmin.model.dto.SalesDetailDto;
import com.hy.gaeaadmin.model.dto.SalesDetailFittingDto;
import com.hy.gaeaadmin.model.dto.SalesDto;
import com.hy.gaeaadmin.model.entity.Product;
import com.hy.gaeaadmin.model.entity.Sales;
import com.hy.gaeaadmin.model.entity.SalesDetail;
import com.hy.gaeaadmin.model.entity.SalesDetailFitting;
import com.hy.gaeaadmin.model.repo.SalesDetailRepo;
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
public class SalesDetailService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SalesDetailRepo salesDetailRepo;
    @Autowired
    private SalesService salesService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SalesDetailFittingService salesDetailFittingService;
    
    public List<SalesDetailDto> list(){
        List<SalesDetailDto> salesDetailList = new ArrayList<>();
        for(SalesDetail salesDetail : salesDetailRepo.findAll()){
            salesDetailList.add(this.convertToDto(salesDetail));
        }
        return salesDetailList;
    }
    
    public void delete(Integer salesDetailId){
        SalesDetail salesDetail = salesDetailRepo.findById(salesDetailId).orElse(null);
        if(salesDetail != null)
        if(salesDetail.getSalesDetailFittingList()!= null && salesDetail.getSalesDetailFittingList().size() > 0) {
            throw new RuntimeException("Sales detail product cannot be deleted because data is already in use");
        }else{
            salesDetailRepo.delete(salesDetail);
        }
    }
    
    public SalesDetailDto findById(Integer salesDetailId){
        if(salesDetailId == null) return null;
        SalesDetail salesDetail = salesDetailRepo.findById(salesDetailId).orElse(null);
        return salesDetail == null ? null : this.convertToDto(salesDetail);
    }
    
    public List<SalesDetailDto> findBySalesId(Integer salesId){
        List<SalesDetailDto> salesDetailDtoList = new ArrayList<>();
        List<SalesDetailDto> dataList = this.list();
        if(dataList!=null){
            for(SalesDetailDto data : dataList){
                if(data.getSalesId()== salesId){
                    salesDetailDtoList.add(data);
                }
            }
        }
        return salesDetailDtoList;
    }
    
    public SalesDetailDto save(SalesDetailDto salesDetailDto){
        SalesDetailDto result = null;
        try {
            SalesDetailDto salesDetailDtoTemp = this.findById(salesDetailDto.getId());
            if(salesDetailDtoTemp == null){
                salesDetailDtoTemp = salesDetailDto;
                salesDetailDtoTemp.setCreatedBy("spring");
                salesDetailDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(salesDetailRepo.save(this.convertToEntity(salesDetailDtoTemp)));
            }else{
                salesDetailDto.setUpdatedBy("1234");
                salesDetailDto.setUpdatedDate(new Date());
                
                SalesDetail salesDetail = this.convertToEntity(salesDetailDto);
                result = this.convertToDto(salesDetailRepo.save(salesDetail));
            }
        } catch (ParseException ex) {
            Logger.getLogger(SalesDetailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public SalesDetailDto convertToDto(SalesDetail salesDetail) {
        SalesDetailDto salesDetailDto = modelMapper.map(salesDetail, SalesDetailDto.class);
        
        salesDetailDto.setSalesId(salesDetail.getSales().getId());
        
        ProductDto productDto = productService.convertToDto(salesDetail.getProduct());
        salesDetailDto.setProductDto(productDto);
        
        List<SalesDetailFittingDto> salesDetailFittingDtoList = new ArrayList<>();
        for(SalesDetailFitting fitting : salesDetail.getSalesDetailFittingList()){
            SalesDetailFittingDto data = salesDetailFittingService.convertToDto(fitting);
            salesDetailFittingDtoList.add(data);
        }
        
        
        salesDetailDto.setSalesDetailFittingDtoList(salesDetailFittingDtoList);
        
        return salesDetailDto;
    }
    
    public SalesDetail convertToEntity(SalesDetailDto salesDetailDto) throws ParseException {
        SalesDetail salesDetail = modelMapper.map(salesDetailDto, SalesDetail.class);
        
        Sales sales = salesService.convertToEntity(salesService.findById(salesDetailDto.getId()));
        salesDetail.setSales(sales);
        
        Product product = productService.convertToEntity(salesDetailDto.getProductDto());
        salesDetail.setProduct(product);
        
        List<SalesDetailFitting> salesDetailFittingList = new ArrayList<>();
        for(SalesDetailFittingDto fittingDto : salesDetailDto.getSalesDetailFittingDtoList()){
            SalesDetailFitting data = salesDetailFittingService.convertToEntity(fittingDto);
            salesDetailFittingList.add(data);
        }
        
        salesDetail.setSalesDetailFittingList(salesDetailFittingList);
        
        return salesDetail;
    }
}

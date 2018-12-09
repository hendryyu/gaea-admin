/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.SalesDetailFittingDto;
import com.hy.gaeaadmin.model.entity.SalesDetailFitting;
import com.hy.gaeaadmin.model.repo.SalesDetailFittingRepo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
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
public class SalesDetailFittingService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SalesDetailFittingRepo salesDetailFittingRepo;
    
    public List<SalesDetailFittingDto> list(){
        List<SalesDetailFittingDto> salesDetailFittingList = new ArrayList<>();
        for(SalesDetailFitting salesDetailFitting : salesDetailFittingRepo.findAll()){
            salesDetailFittingList.add(this.convertToDto(salesDetailFitting));
        }
        return salesDetailFittingList;
    }
    
    public void delete(Integer salesDetailFittingId){
        SalesDetailFitting salesDetailFitting = salesDetailFittingRepo.findById(salesDetailFittingId).orElse(null);
        if(salesDetailFitting != null)
//        if(salesDetailFitting.getSalesDetailFittingFittingList()!= null && salesDetailFitting.getSalesDetailFittingFittingList().size() > 0) {
//            throw new RuntimeException("Sales detail product cannot be deleted because data is already in use");
//        }else{
            salesDetailFittingRepo.delete(salesDetailFitting);
//        }
    }
    
    public List<SalesDetailFittingDto> findBySalesIdAndDetailId(int salesId, int detailId){
        List<SalesDetailFittingDto> salesDetailFittingDtoList = new ArrayList<>();
        
        for(SalesDetailFittingDto data : this.list()){
            if(data.getSalesDetailDto().getId() == detailId && data.getSalesDetailDto().getSalesId() == salesId ){
                salesDetailFittingDtoList.add(data);
            }
        }
        
        return salesDetailFittingDtoList;
    }
    
    public SalesDetailFittingDto findById(Integer salesDetailFittingId){
        if(salesDetailFittingId == null) return null;
        SalesDetailFitting salesDetailFitting = salesDetailFittingRepo.findById(salesDetailFittingId).orElse(null);
        return salesDetailFitting == null ? null : this.convertToDto(salesDetailFitting);
    }
    
    public SalesDetailFittingDto save(SalesDetailFittingDto salesDetailFittingDto){
        SalesDetailFittingDto result = null;
        try {
            SalesDetailFittingDto salesDetailFittingDtoTemp = this.findById(salesDetailFittingDto.getId());
            if(salesDetailFittingDtoTemp == null){
                salesDetailFittingDtoTemp = salesDetailFittingDto;
                salesDetailFittingDtoTemp.setCreatedBy("spring");
                salesDetailFittingDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(salesDetailFittingRepo.save(this.convertToEntity(salesDetailFittingDtoTemp)));
            }else{
                salesDetailFittingDto.setUpdatedBy("1234");
                salesDetailFittingDto.setUpdatedDate(new Date());
                
                SalesDetailFitting salesDetailFitting = this.convertToEntity(salesDetailFittingDto);
                result = this.convertToDto(salesDetailFittingRepo.save(salesDetailFitting));
            }
        } catch (ParseException ex) {
            Logger.getLogger(SalesDetailFittingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public SalesDetailFittingDto convertToDto(SalesDetailFitting salesDetailFitting) {
        SalesDetailFittingDto salesDetailFittingDto = modelMapper.map(salesDetailFitting, SalesDetailFittingDto.class);
        return salesDetailFittingDto;
    }
    
    public SalesDetailFitting convertToEntity(SalesDetailFittingDto salesDetailFittingDto) throws ParseException {
        SalesDetailFitting salesDetailFitting = modelMapper.map(salesDetailFittingDto, SalesDetailFitting.class);
        return salesDetailFitting;
    }
}

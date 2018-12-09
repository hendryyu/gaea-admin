/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.SalesTypeDto;
import com.hy.gaeaadmin.model.entity.MstSalesType;
import com.hy.gaeaadmin.model.repo.SalesTypeRepo;
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
public class SalesTypeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SalesTypeRepo salesTypeRepo;
    
    public List<SalesTypeDto> list(){
        List<SalesTypeDto> positionList = new ArrayList<>();
        for(MstSalesType mstSalesType : salesTypeRepo.findAll()){
            positionList.add(this.convertToDto(mstSalesType));
        }
        return positionList;
    }
    
    public void delete(Integer positionId){
        MstSalesType mstSalesType = salesTypeRepo.findById(positionId).orElse(null);
        if(mstSalesType != null)
        if(mstSalesType.getSalesList()!= null && mstSalesType.getSalesList().size() > 0) {
            throw new RuntimeException("Sales Type cannot be deleted because data is already in use");
        }else{
            salesTypeRepo.delete(mstSalesType);
        }
    }
    
    public SalesTypeDto findById(Integer positionId){
        if(positionId == null) return null;
        MstSalesType mstSalesType = salesTypeRepo.findById(positionId).orElse(null);
        return mstSalesType == null ? null : this.convertToDto(mstSalesType);
    }
    
    public SalesTypeDto save(SalesTypeDto salesTypeDto){
        SalesTypeDto result = null;
        try {
            SalesTypeDto salesTypeDtoTemp = this.findById(salesTypeDto.getId());
            if(salesTypeDtoTemp == null){
                salesTypeDtoTemp = salesTypeDto;
                salesTypeDtoTemp.setCreatedBy("spring");
                salesTypeDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(salesTypeRepo.save(this.convertToEntity(salesTypeDtoTemp)));
            }else{
                salesTypeDto.setUpdatedBy("1234");
                salesTypeDto.setUpdatedDate(new Date());
                
                MstSalesType mstSalesType = this.convertToEntity(salesTypeDto);
                result = this.convertToDto(salesTypeRepo.save(mstSalesType));
            }
        } catch (ParseException ex) {
            Logger.getLogger(SalesTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public SalesTypeDto convertToDto(MstSalesType mstSalesType) {
        SalesTypeDto salesTypeDto = modelMapper.map(mstSalesType, SalesTypeDto.class);
        return salesTypeDto;
    }
    
    public MstSalesType convertToEntity(SalesTypeDto salesTypeDto) throws ParseException {
        MstSalesType mstSalesType = modelMapper.map(salesTypeDto, MstSalesType.class);
        return mstSalesType;
    }
}

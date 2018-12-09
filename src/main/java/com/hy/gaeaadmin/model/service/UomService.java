/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.UomDto;
import com.hy.gaeaadmin.model.entity.MstUom;
import com.hy.gaeaadmin.model.repo.UomRepo;
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
public class UomService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UomRepo uomRepo;
    
    public List<UomDto> list(){
        List<UomDto> positionList = new ArrayList<>();
        for(MstUom mstUom : uomRepo.findAll()){
            positionList.add(this.convertToDto(mstUom));
        }
        return positionList;
    }
    
    public void delete(Integer positionId){
        MstUom mstUom = uomRepo.findById(positionId).orElse(null);
        if(mstUom != null)
        if(mstUom.getProductStockList()!= null && mstUom.getProductStockList().size() > 0) {
            throw new RuntimeException("UOM cannot be deleted because data is already in use");
        }else{
            uomRepo.delete(mstUom);
        }
    }
    
    public UomDto findById(Integer positionId){
        if(positionId == null) return null;
        MstUom mstUom = uomRepo.findById(positionId).orElse(null);
        return mstUom == null ? null : this.convertToDto(mstUom);
    }
    
    public UomDto save(UomDto uomDto){
        UomDto result = null;
        try {
            UomDto uomDtoTemp = this.findById(uomDto.getId());
            if(uomDtoTemp == null){
                uomDtoTemp = uomDto;
                uomDtoTemp.setCreatedBy("spring");
                uomDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(uomRepo.save(this.convertToEntity(uomDtoTemp)));
            }else{
                uomDto.setUpdatedBy("1234");
                uomDto.setUpdatedDate(new Date());
                
                MstUom mstUom = this.convertToEntity(uomDto);
                result = this.convertToDto(uomRepo.save(mstUom));
            }
        } catch (ParseException ex) {
            Logger.getLogger(UomService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public UomDto convertToDto(MstUom mstUom) {
        UomDto uomDto = modelMapper.map(mstUom, UomDto.class);
        return uomDto;
    }
    
    public MstUom convertToEntity(UomDto uomDto) throws ParseException {
        MstUom mstUom = modelMapper.map(uomDto, MstUom.class);
        return mstUom;
    }
}

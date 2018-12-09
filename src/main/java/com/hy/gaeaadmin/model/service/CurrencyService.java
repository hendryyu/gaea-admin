/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.CurrencyDto;
import com.hy.gaeaadmin.model.entity.MstCurrency;
import com.hy.gaeaadmin.model.repo.CurrencyRepo;
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
public class CurrencyService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CurrencyRepo currencyRepo;
    
    public CurrencyDto convertToDto(MstCurrency mstCurrency) {
        CurrencyDto currencyDto = modelMapper.map(mstCurrency, CurrencyDto.class);
        
        return currencyDto;
    }
    
    public MstCurrency convertToEntity(CurrencyDto currencyDto) throws ParseException {
        MstCurrency mstCurrency = modelMapper.map(currencyDto, MstCurrency.class);
        return mstCurrency;
    }
    
    public List<CurrencyDto> list(){
        List<CurrencyDto> currencyDtoList = new ArrayList<>();
        for(MstCurrency mstCurrency :  currencyRepo.findAll()){
            currencyDtoList.add(this.convertToDto(mstCurrency));
        }
        return currencyDtoList;
    }
    
    public CurrencyDto findById(Integer contactTypeId){
        if (contactTypeId == null) return null;
        MstCurrency mstCurrency = currencyRepo.findById(contactTypeId).orElse(null);
        if(mstCurrency == null) return null;
        return this.convertToDto(mstCurrency);
    }
    
    public void delete(Integer contactTypeId){
        MstCurrency mstCurrency = currencyRepo.findById(contactTypeId).orElse(null);
        if(mstCurrency != null)
            
        if(mstCurrency.getProductStockList()!= null && mstCurrency.getProductStockList().size() > 0) {
            throw new RuntimeException("Currency cannot be deleted because data is already in use");
        }else{
            currencyRepo.delete(mstCurrency);
        }
    }
    
    public CurrencyDto save(CurrencyDto currencyDto){
        CurrencyDto result = null;
        try {
            CurrencyDto currencyDtoTemp = this.findById(currencyDto.getId());
            if(currencyDtoTemp == null){
                currencyDtoTemp = currencyDto;
                currencyDtoTemp.setCreatedBy("spring");
                currencyDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(currencyRepo.save(this.convertToEntity(currencyDtoTemp)));
            }else{
                currencyDto.setUpdatedBy("1234");
                currencyDto.setUpdatedDate(new Date());
                
                MstCurrency mstCurrency = this.convertToEntity(currencyDto);
                result = this.convertToDto(currencyRepo.save(mstCurrency));
            }
        } catch (ParseException ex) {
            Logger.getLogger(CurrencyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}

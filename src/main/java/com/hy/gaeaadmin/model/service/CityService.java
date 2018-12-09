/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.CityDto;
import com.hy.gaeaadmin.model.entity.MstCity;
import com.hy.gaeaadmin.model.repo.CityRepo;
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
public class CityService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CityRepo cityRepo;
    
    public List<CityDto> list(){
        List<CityDto> positionList = new ArrayList<>();
        for(MstCity mstCity : cityRepo.findAll()){
            positionList.add(this.convertToDto(mstCity));
        }
        return positionList;
    }
    
    public void delete(Integer positionId){
        MstCity mstCity = cityRepo.findById(positionId).orElse(null);
        if(mstCity != null)
//        if(mstCity.get()!= null && mstCity.getAddressList().size() > 0) {
//            throw new RuntimeException("City cannot be deleted because data is already in use");
//        }else{
            cityRepo.delete(mstCity);
//        }
    }
    
    public CityDto findById(Integer positionId){
        if(positionId == null) return null;
        MstCity mstCity = cityRepo.findById(positionId).orElse(null);
        return mstCity == null ? null : this.convertToDto(mstCity);
    }
    
    public CityDto save(CityDto cityDto){
        CityDto result = null;
        try {
            CityDto cityDtoTemp = this.findById(cityDto.getId());
            if(cityDtoTemp == null){
                cityDtoTemp = cityDto;
                cityDtoTemp.setCreatedBy("spring");
                cityDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(cityRepo.save(this.convertToEntity(cityDtoTemp)));
            }else{
                cityDto.setUpdatedBy("1234");
                cityDto.setUpdatedDate(new Date());
                
                MstCity mstCity = this.convertToEntity(cityDto);
                result = this.convertToDto(cityRepo.save(mstCity));
            }
        } catch (ParseException ex) {
            Logger.getLogger(CityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public CityDto convertToDto(MstCity mstCity) {
        CityDto cityDto = modelMapper.map(mstCity, CityDto.class);
        return cityDto;
    }
    
    public MstCity convertToEntity(CityDto cityDto) throws ParseException {
        MstCity mstCity = modelMapper.map(cityDto, MstCity.class);
        return mstCity;
    }
}

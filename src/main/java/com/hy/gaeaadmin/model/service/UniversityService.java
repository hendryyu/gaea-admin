/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.UniversityDto;
import com.hy.gaeaadmin.model.entity.MstUniversity;
import com.hy.gaeaadmin.model.repo.UniversityRepo;
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
public class UniversityService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UniversityRepo universityRepo;
    
    public List<UniversityDto> list(){
        List<UniversityDto> positionList = new ArrayList<>();
        for(MstUniversity mstUniversity : universityRepo.findAll()){
            positionList.add(this.convertToDto(mstUniversity));
        }
        return positionList;
    }
    
    public void delete(Integer univerisityId){
        MstUniversity mstUniversity = universityRepo.findById(univerisityId).orElse(null);
        if(mstUniversity != null)
//        if(mstUniversity.get()!= null && mstUniversity.getAddressList().size() > 0) {
//            throw new RuntimeException("City cannot be deleted because data is already in use");
//        }else{
            universityRepo.delete(mstUniversity);
//        }
    }
    
    public UniversityDto findById(Integer univerisityId){
        if(univerisityId == null) return null;
        MstUniversity mstUniversity = universityRepo.findById(univerisityId).orElse(null);
        return mstUniversity == null ? null : this.convertToDto(mstUniversity);
    }
    
    public UniversityDto save(UniversityDto universityDto){
        UniversityDto result = null;
        try {
            UniversityDto universityDtoTemp = this.findById(universityDto.getId());
            if(universityDtoTemp == null){
                universityDtoTemp = universityDto;
                universityDtoTemp.setCreatedBy("spring");
                universityDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(universityRepo.save(this.convertToEntity(universityDtoTemp)));
            }else{
                universityDto.setUpdatedBy("1234");
                universityDto.setUpdatedDate(new Date());
                
                MstUniversity mstUniversity = this.convertToEntity(universityDto);
                result = this.convertToDto(universityRepo.save(mstUniversity));
            }
        } catch (ParseException ex) {
            Logger.getLogger(UniversityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public UniversityDto convertToDto(MstUniversity mstUniversity) {
        UniversityDto universityDto = modelMapper.map(mstUniversity, UniversityDto.class);
        return universityDto;
    }
    
    public MstUniversity convertToEntity(UniversityDto universityDto) throws ParseException {
        MstUniversity mstUniversity = modelMapper.map(universityDto, MstUniversity.class);
        return mstUniversity;
    }
}

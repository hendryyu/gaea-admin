/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.PositionDto;
import com.hy.gaeaadmin.model.entity.MstPosition;
import com.hy.gaeaadmin.model.repo.PositionRepo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
public class PositionService {
    
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PositionRepo positionRepo;
    
    public List<PositionDto> list(){
        List<PositionDto> positionList = new ArrayList<>();
        for(MstPosition mstPosition : positionRepo.findAll()){
            positionList.add(this.convertToDto(mstPosition));
        }
        return positionList;
    }
    
    public void delete(Integer positionId){
        MstPosition mstPosition = positionRepo.findById(positionId).orElse(null);
        if(mstPosition != null)
        if(mstPosition.getEmployeeList()!= null && mstPosition.getEmployeeList().size() > 0) {
            throw new RuntimeException("Position cannot be deleted because data is already in use");
        }else{
            positionRepo.delete(mstPosition);
        }
    }
    
    public PositionDto findById(Integer positionId){
        if(positionId == null) return null;
        MstPosition mstPosition = positionRepo.findById(positionId).orElse(null);
        return mstPosition == null ? null : this.convertToDto(mstPosition);
    }
    
    public PositionDto save(PositionDto positionDto){
        PositionDto result = null;
        try {
            PositionDto positionDtoTemp = this.findById(positionDto.getId());
            if(positionDtoTemp == null){
                positionDtoTemp = positionDto;
                positionDtoTemp.setCreatedBy("spring");
                positionDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(positionRepo.save(this.convertToEntity(positionDtoTemp)));
            }else{
                positionDto.setUpdatedBy("1234");
                positionDto.setUpdatedDate(new Date());
                
                MstPosition mstPosition = this.convertToEntity(positionDto);
                result = this.convertToDto(positionRepo.save(mstPosition));
            }
        } catch (ParseException ex) {
            Logger.getLogger(PositionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public PositionDto convertToDto(MstPosition mstPosition) {
        PositionDto employeeDto = modelMapper.map(mstPosition, PositionDto.class);
        return employeeDto;
    }
    
    public MstPosition convertToEntity(PositionDto employeeDto) throws ParseException {
        MstPosition mstPosition = modelMapper.map(employeeDto, MstPosition.class);
        return mstPosition;
    }
}

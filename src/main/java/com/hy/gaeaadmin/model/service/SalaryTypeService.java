/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.SalaryTypeDto;
import com.hy.gaeaadmin.model.entity.MstSalaryType;
import com.hy.gaeaadmin.model.repo.SalaryTypeRepo;
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
public class SalaryTypeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SalaryTypeRepo salaryTypeRepo;
    
    public List<SalaryTypeDto> list(){
        List<SalaryTypeDto> positionList = new ArrayList<>();
        for(MstSalaryType mstSalaryType : salaryTypeRepo.findAll()){
            positionList.add(this.convertToDto(mstSalaryType));
        }
        return positionList;
    }
    
    public void delete(Integer positionId){
        MstSalaryType mstSalaryType = salaryTypeRepo.findById(positionId).orElse(null);
        if(mstSalaryType != null)
        if(mstSalaryType.getEmployeeSalaryList()!= null && mstSalaryType.getEmployeeSalaryList().size() > 0) {
            throw new RuntimeException("Salary Type cannot be deleted because data is already in use");
        }else{
            salaryTypeRepo.delete(mstSalaryType);
        }
    }
    
    public SalaryTypeDto findById(Integer positionId){
        if(positionId == null) return null;
        MstSalaryType mstSalaryType = salaryTypeRepo.findById(positionId).orElse(null);
        return mstSalaryType == null ? null : this.convertToDto(mstSalaryType);
    }
    
    public SalaryTypeDto save(SalaryTypeDto salaryTypeDto){
        SalaryTypeDto result = null;
        try {
            SalaryTypeDto salaryTypeDtoTemp = this.findById(salaryTypeDto.getId());
            if(salaryTypeDtoTemp == null){
                salaryTypeDtoTemp = salaryTypeDto;
                salaryTypeDtoTemp.setCreatedBy("spring");
                salaryTypeDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(salaryTypeRepo.save(this.convertToEntity(salaryTypeDtoTemp)));
            }else{
                salaryTypeDto.setUpdatedBy("1234");
                salaryTypeDto.setUpdatedDate(new Date());
                
                MstSalaryType mstSalaryType = this.convertToEntity(salaryTypeDto);
                result = this.convertToDto(salaryTypeRepo.save(mstSalaryType));
            }
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public SalaryTypeDto convertToDto(MstSalaryType mstSalaryType) {
        SalaryTypeDto salaryTypeDto = modelMapper.map(mstSalaryType, SalaryTypeDto.class);
        return salaryTypeDto;
    }
    
    public MstSalaryType convertToEntity(SalaryTypeDto salaryTypeDto) throws ParseException {
        MstSalaryType mstSalaryType = modelMapper.map(salaryTypeDto, MstSalaryType.class);
        return mstSalaryType;
    }
}

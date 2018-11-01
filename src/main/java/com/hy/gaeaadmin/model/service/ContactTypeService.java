/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.ContactTypeDto;
import com.hy.gaeaadmin.model.entity.MstContactType;
import com.hy.gaeaadmin.model.repo.ContactTypeRepo;
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
public class ContactTypeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ContactTypeRepo contactTypeRepo;
    
    public ContactTypeDto convertToDto(MstContactType mstContactType) {
        ContactTypeDto contactTypeDto = modelMapper.map(mstContactType, ContactTypeDto.class);
        
        return contactTypeDto;
    }
    
    public MstContactType convertToEntity(ContactTypeDto contactTypeDto) throws ParseException {
        MstContactType mstContactType = modelMapper.map(contactTypeDto, MstContactType.class);
        return mstContactType;
    }
    
    public List<ContactTypeDto> list(){
        List<ContactTypeDto> contactTypeDtoList = new ArrayList<>();
        for(MstContactType contactType :  contactTypeRepo.findAll()){
            contactTypeDtoList.add(this.convertToDto(contactType));
        }
        return contactTypeDtoList;
    }
    
//    public ContactTypeDto save(ContactTypeDto contactTypeDto){
//        ContactTypeDto result = null;
//        try {
//            MstContactType contactTypeDtoNew = contactTypeRepo.save(this.convertToEntity(contactTypeDto));
//            result = this.convertToDto(contactTypeDtoNew);
//        } catch (ParseException ex) {
//            Logger.getLogger(ContactTypeService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }
    
    public ContactTypeDto findById(Integer contactTypeId){
        if (contactTypeId == null) return null;
        MstContactType mstContactType = contactTypeRepo.findById(contactTypeId).orElse(null);
        if(mstContactType == null) return null;
        return this.convertToDto(mstContactType);
    }
    
    public void delete(Integer contactTypeId){
        MstContactType mstContactType = contactTypeRepo.findById(contactTypeId).orElse(null);
        if(mstContactType != null)
            
        if(mstContactType.getContactList() != null && mstContactType.getContactList().size() > 0) {
            throw new RuntimeException("Contact Type cannot be deleted because data is already in use");
        }else{
            contactTypeRepo.delete(mstContactType);
        }
    }
    
    public ContactTypeDto save(ContactTypeDto contactTypeDto){
        ContactTypeDto result = null;
        try {
            ContactTypeDto contactTypeDtoTemp = this.findById(contactTypeDto.getId());
            if(contactTypeDtoTemp == null){
                contactTypeDtoTemp = contactTypeDto;
                contactTypeDtoTemp.setCreatedBy("spring");
                contactTypeDtoTemp.setCreatedDate(new Date());
                result = this.convertToDto(contactTypeRepo.save(this.convertToEntity(contactTypeDtoTemp)));
            }else{
                contactTypeDto.setUpdatedBy("1234");
                contactTypeDto.setUpdatedDate(new Date());
                
                MstContactType mstContactType = this.convertToEntity(contactTypeDto);
                result = this.convertToDto(contactTypeRepo.save(mstContactType));
            }
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}

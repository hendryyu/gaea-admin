/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.ContactDto;
import com.hy.gaeaadmin.model.dto.ContactTypeDto;
import com.hy.gaeaadmin.model.entity.Contact;
import com.hy.gaeaadmin.model.entity.MstContactType;
import com.hy.gaeaadmin.model.repo.ContactRepo;
import com.hy.gaeaadmin.model.repo.EmployeeRepo;
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
public class ContactService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ContactRepo contactRepo;
    @Autowired
    private ContactTypeService contactTypeService;
    
    public List<ContactDto> list(){
        List<ContactDto> contactList = new ArrayList<>();
        for(Contact data : contactRepo.findAll()){
            contactList.add(this.convertToDto(data));
        }
        return contactList;
    }
    
    public void deleteEmployeeContact(Integer employeeId, Integer contactId){
        System.out.println("-----deleteEmployeeContact.employeeId "+employeeId);
        System.out.println("-----deleteEmployeeContact.contactId "+ contactId);
        Contact contact = contactRepo.findById(contactId).orElse(null);
        if(contact != null){
            contactRepo.delete(contact);
        }
    }
    
    public ContactDto convertToDto(Contact contact) {
        ContactDto contactDto = modelMapper.map(contact, ContactDto.class);
        
        return contactDto;
    }
    
    public Contact convertToEntity(ContactDto contactDto) throws ParseException {
        Contact contact = modelMapper.map(contactDto, Contact.class);
        
        MstContactType mstContactType = new MstContactType();
        mstContactType.setId(contactDto.getContactTypeId());
        mstContactType.setName(contactDto.getContactTypeName());
        contact.setMstContactType(mstContactType);
        
        return contact;
    }
    
//    public void saveEmployeeContact(Integer employeeId, Integer contactTypeId, String contactValue){
//        ContactTypeDto contactTypeDto = contactTypeService.findById(contactTypeId);
//        
//        ContactDto contactDto = new ContactDto();
//        contactDto.setContactTypeDto(contactTypeDto);
//        contactDto.setValue(contactValue);
//        Contact newContact = this.save(contactDto);
//        
//        EmployeeContact employeeContact = new EmployeeContact();
//        employeeContact.setEmployee(employeeRepo.findById(employeeId).orElse(null));
//        employeeContact.setContact(newContact);
//        employeeContactRepo.save(employeeContact);
//    }
    
    public ContactDto save(ContactDto newContactDto){
        ContactDto result = null;
        System.out.println("-----newContactDto "+newContactDto);
        try {
            newContactDto.setCreatedBy("spring");
            System.out.println("----- ");
                newContactDto.setCreatedDate(new Date());
                System.out.println("----- ");
                Contact newContact = this.convertToEntity(newContactDto);
                System.out.println("----- ");
            result =  this.convertToDto(contactRepo.save(newContact));
           
        } catch (ParseException ex) {
            Logger.getLogger(ContactService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ContactDto findById(Integer contactId){
        if(contactId == null) return null;
        Optional<Contact> cont = contactRepo.findById(contactId);
        Contact contact = cont.orElse(null);
        return contact == null ? null : this.convertToDto(contact);
    }
}

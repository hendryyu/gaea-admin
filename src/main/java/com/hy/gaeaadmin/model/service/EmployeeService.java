/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.AddressDto;
import com.hy.gaeaadmin.model.dto.ContactDto;
import com.hy.gaeaadmin.model.dto.ContactTypeDto;
import com.hy.gaeaadmin.model.dto.EmployeeDto;
import com.hy.gaeaadmin.model.entity.Address;
import com.hy.gaeaadmin.model.entity.Contact;
import com.hy.gaeaadmin.model.entity.Employee;
import com.hy.gaeaadmin.model.entity.MstContactType;
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
 * @author ndry93
 */
@Service
public class EmployeeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmployeeRepo empRepo;
    
    
    public List<EmployeeDto> list(){
        List<EmployeeDto> empList = new ArrayList<>();
        for(Employee data : empRepo.findAll()){
            empList.add(this.convertToDto(data));
        }
        return empList;
    }
    
    public void delete(Integer employeeId){
        
        if (employeeId == null) return;
        Employee emp = empRepo.findById(employeeId).orElse(null);
        empRepo.delete(emp);
    }
    
    public EmployeeDto save(EmployeeDto empDto){
        EmployeeDto result = null;
        try {
            EmployeeDto empTemp = this.findById(empDto.getId());
            if(empTemp == null){
                empTemp = empDto;
                empTemp.setEmployeeNum("1234");
                empTemp.setCreatedBy("spring");
                empTemp.setCreatedDate(new Date());
                result = this.convertToDto(empRepo.save(this.convertToEntity(empTemp)));
            }else{
                empDto.setUpdatedBy("1234");
                empDto.setUpdatedDate(new Date());
                
                Employee employee = this.convertToEntity(empDto);
                result = this.convertToDto(empRepo.save(employee));
            }
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public EmployeeDto findById(Integer employeeId){
        if(employeeId == null) return null;
        Optional<Employee> emp = empRepo.findById(employeeId);
        Employee employee = emp.orElse(null);
        return employee == null ? null : this.convertToDto(employee);
    }
    
//    public EmployeeDto saveEmployeeContact(Integer employeeId,Integer contactTypeId,String contactValue){
//        EmployeeDto employeeDto = this.findById(employeeId);
//        ContactDto newContactDto = new ContactDto();
//        ContactTypeDto contactTypeDto = contactTypeService.findById(contactTypeId);
//        
//        newContactDto.setContactTypeDto(contactTypeDto);
//        newContactDto.setValue(contactValue);
//        newContactDto.setCreatedBy("spring");
//        newContactDto.setCreatedDate(new Date());
//        employeeDto.getContactList().add(newContactDto);
//        
//        return this.save(employeeDto);
//    }
    
    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
//        //address
//        List<AddressDto> addressList = new ArrayList<>();
//        for(EmployeeAddress empAddr : employee.getEmployeeAddressList()){
//            addressList.add(addressService.convertToDto(empAddr.getAddress()));
//        }
//        employeeDto.setAddressList(addressList);
//        //contact
//        List<ContactDto> contactList = new ArrayList<>();
//        for(EmployeeContact empContact : employee.getEmployeeContactList()){
//            contactList.add(contactService.convertToDto(empContact.getContact()));
//        }
//        employeeDto.setContactList(contactList);
        
        return employeeDto;
    }
    
    public Employee convertToEntity(EmployeeDto employeeDto) throws ParseException {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return employee;
    }
    
}

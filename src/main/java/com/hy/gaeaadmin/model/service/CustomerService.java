/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.error.MyCustomException;
import com.hy.gaeaadmin.model.dto.CustomerDto;
import com.hy.gaeaadmin.model.entity.Customer;
import com.hy.gaeaadmin.model.repo.CustomerRepo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author hendryyu
 */
@Service
public class CustomerService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerRepo customerRepo;
    
    public CustomerDto findByPhone(String phone){
        List<CustomerDto> result = this.list().stream()
            .filter(x -> (x.getContactPhone1().equalsIgnoreCase(phone) ||  x.getContactPhone2().equalsIgnoreCase(phone) ))
            .collect(Collectors.toList());
        return result != null && result.size()>0 ? result.get(0) : null;
    }
    
    public List<CustomerDto> list(){
        List<CustomerDto> customerList = new ArrayList<>();
        for(Customer data : customerRepo.findAll(new Sort(Sort.Direction.ASC,"firstName"))){
            customerList.add(this.convertToDto(data));
        }
        return customerList;
    }
    
    public void delete(Integer customerId){
        if (customerId == null) return;
        Customer emp = customerRepo.findById(customerId).orElse(null);
        customerRepo.delete(emp);
    }
    
    public CustomerDto save(CustomerDto customerDto) throws MyCustomException{
        CustomerDto result = null;
        try {
            CustomerDto customerTemp = this.findById(customerDto.getId());
            if(customerTemp == null){
                customerTemp = customerDto;
                customerTemp.setCreatedBy("spring");
                customerTemp.setCreatedDate(new Date());
                result = this.convertToDto(customerRepo.save(this.convertToEntity(customerTemp)));
            }else{
                
                customerDto.setUpdatedBy("1234");
                customerDto.setUpdatedDate(new Date());
                
                Customer customer = this.convertToEntity(customerDto);
                result = this.convertToDto(customerRepo.save(customer));
            }
        } catch (RuntimeException | ParseException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            throw new MyCustomException("500","Customer already exist");
        } 
        return result;
    }
    
    public CustomerDto findById(Integer customerId){
        if(customerId == null) return null;
        Customer customer = customerRepo.findById(customerId).orElse(null);
        return customer == null ? null : this.convertToDto(customer);
    }
    
    
    public CustomerDto convertToDto(Customer customer) {
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }
    
    public Customer convertToEntity(CustomerDto customerDto) throws ParseException {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customer;
    }
}

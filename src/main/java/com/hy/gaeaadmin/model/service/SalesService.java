/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.error.MyCustomException;
import com.hy.gaeaadmin.model.dto.CustomerDto;
import com.hy.gaeaadmin.model.dto.SalesDto;
import com.hy.gaeaadmin.model.entity.Customer;
import com.hy.gaeaadmin.model.entity.MstSalesType;
import com.hy.gaeaadmin.model.entity.Sales;
import com.hy.gaeaadmin.model.repo.SalesRepo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author hendryyu
 */
@Service
public class SalesService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SalesRepo salesRepo;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SalesTypeService salesTypeService;
    
    public List<SalesDto> list(){
        List<SalesDto> positionList = new ArrayList<>();
        for(Sales sales : salesRepo.findAll(new Sort(Sort.Direction.DESC,"id"))){
            positionList.add(this.convertToDto(sales));
        }
        return positionList;
    }
    
    public void delete(Integer positionId){
        Sales sales = salesRepo.findById(positionId).orElse(null);
        if(sales != null)
        salesRepo.delete(sales);
    }
    
    public SalesDto findById(Integer positionId){
        if(positionId == null) return null;
        Sales sales = salesRepo.findById(positionId).orElse(null);
        return sales == null ? null : this.convertToDto(sales);
    }
    
    private int findNextSalesId(){
        List<SalesDto> salesDtoList = this.list();
        
        int first = 1;
        if(salesDtoList != null && salesDtoList.size() > 0){
            first = salesDtoList.get(0).getId() + 1;
        }
        
        return first;
    }
    
    private String generateNextTxnNumber(SalesDto salesDto){
        String salesTypePref = salesTypeService.findById(salesDto.getSalesTypeId()).getCode().toUpperCase();
        
        SimpleDateFormat sdf = new SimpleDateFormat("YYMM");
        Date date = new Date();
        
        String formattedNextSeqVal = String.format("%03d", this.findNextSalesId());
        
        
        String txnNumNew = salesTypePref.concat("/").concat(sdf.format(date).concat(formattedNextSeqVal));
        return txnNumNew;
    } 
    
    public SalesDto save(SalesDto salesDto) throws MyCustomException {
        System.out.println("----- salesDto.getCustomerDto() "+salesDto.getCustomerDto());
        System.out.println("----- salesDto.getCustomerDto().getId() "+salesDto.getCustomerDto().getId());
        SalesDto result = null;
        try {
            if(salesDto.getCustomerDto().getId()==null || salesDto.isUpdateCustomer()){
                CustomerDto newCustomerDto = customerService.save(salesDto.getCustomerDto());
                salesDto.setCustomerDto(newCustomerDto);
            }
            
            
            SalesDto salesDtoTemp = this.findById(salesDto.getId());
            if(salesDtoTemp == null){
                salesDtoTemp = salesDto;
                salesDtoTemp.setCreatedBy("spring");
                salesDtoTemp.setCreatedDate(new Date());
                
                
                salesDtoTemp.setTrxNum(this.generateNextTxnNumber(salesDto)); //create transaction number
                result = this.convertToDto(salesRepo.save(this.convertToEntity(salesDtoTemp)));
            }else{
                salesDto.setUpdatedBy("1234");
                salesDto.setUpdatedDate(new Date());
                
                Sales sales = this.convertToEntity(salesDto);
                result = this.convertToDto(salesRepo.save(sales));
                
                
            }
        } catch(MyCustomException mce) {
            throw mce;
        } catch (RuntimeException | ParseException re) {
            Logger.getLogger(SalesService.class.getName()).log(Level.SEVERE, null, re);
        } 
        return result;
    }
    
    public SalesDto convertToDto(Sales sales) {
        SalesDto salesDto = modelMapper.map(sales, SalesDto.class);
        
        CustomerDto customerDto = customerService.convertToDto(sales.getCustomer());
        salesDto.setCustomerDto(customerDto);
        
        salesDto.setSalesTypeId(sales.getMstSalesType().getId());
        salesDto.setSalesTypeName(sales.getMstSalesType().getName());
        
        return salesDto;
    }
    
    public Sales convertToEntity(SalesDto salesDto) throws ParseException {
        Sales sales = modelMapper.map(salesDto, Sales.class);
        Customer customer = customerService.convertToEntity(salesDto.getCustomerDto());
        sales.setCustomer(customer);
        
        MstSalesType mstSalesType = new MstSalesType();
        mstSalesType.setId(salesDto.getSalesTypeId());
        mstSalesType.setName(salesDto.getSalesTypeName());
        sales.setMstSalesType(mstSalesType);
        
        return sales;
    }
}

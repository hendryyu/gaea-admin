/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.service;

import com.hy.gaeaadmin.model.dto.AddressDto;
import com.hy.gaeaadmin.model.entity.Address;
import com.hy.gaeaadmin.model.repo.AddressRepo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hendryyu
 */
@Service
public class AddressService {
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private ModelMapper modelMapper;
    
    public List<AddressDto> list(){
        List<AddressDto> addrList = new ArrayList<>();
        for(Address data : addressRepo.findAll()){
            addrList.add(this.convertToDto(data));
        }
        return addrList;
    }
    
    public AddressDto findById(Integer addressId){
        if(addressId == null) return null;
        Optional<Address> cont = addressRepo.findById(addressId);
        Address address = cont.orElse(null);
        return address == null ? null : this.convertToDto(address);
    }
    
    public AddressDto convertToDto(Address address) {
        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        return addressDto;
    }
    
    public Address convertToEntity(AddressDto addressDto) throws ParseException {
        Address address = modelMapper.map(addressDto, Address.class);
        return address;
    }
    
}

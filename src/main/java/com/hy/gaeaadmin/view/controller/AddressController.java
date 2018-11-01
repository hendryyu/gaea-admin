/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.EmployeeDto;
import com.hy.gaeaadmin.model.service.AddressService;
import com.hy.gaeaadmin.model.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hendryyu
 */
@Controller
public class AddressController {
    
    private static final String LIST_ADDRESS_V = "address";

    @Autowired
    private AddressService addressService;

    @GetMapping("/address")
    public String employee(Model model) {
        model.addAttribute("addresses", addressService.list());

        return LIST_ADDRESS_V;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.CustomerDto;
import com.hy.gaeaadmin.model.service.CityService;
import com.hy.gaeaadmin.model.service.CustomerService;
import com.hy.gaeaadmin.model.service.PositionService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hendryyu
 */
@Controller
public class CustomerController {
    
    private final String LIST_VIEW_HTML = "customer/listCustomer";
    private final String FORM_VIEW_HTML = "customer/formCustomer";
    private final String REDIRECT_LIST_VIEW = "redirect:/customer";
    private final String REDIRECT_FORM_VIEW = "redirect:/viewCustomer";


    @Autowired
    private CustomerService customerService;
    @Autowired
    private CityService cityService;
    @Autowired
    private PositionService positionService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @GetMapping("/customer")
    public String customer(Model model) {
        model.addAttribute("customers", customerService.list());
        model.addAttribute("isModal", false);
        
        return LIST_VIEW_HTML;
    }
    

    @GetMapping("/addCustomer")
    public String addCustomer(Model model) {
        CustomerDto empDto = new CustomerDto();
        model.addAttribute("customerDto", empDto);
        model.addAttribute("cities", cityService.list());
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewCustomer")
    public String viewCustomer(Model model, @RequestParam(value = "id", required = true) Integer customerId) {
        System.out.println("------customerId "+customerId);
        CustomerDto customerDto = customerService.findById(customerId);
        model.addAttribute("customerDto", customerDto);
        model.addAttribute("positions",positionService.list());
        
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addCustomer")
    public String saveCustomer(Model model, //
            @ModelAttribute("customerDto") CustomerDto customerDto) {
        String firstName = customerDto.getFirstName();
        String lastName = customerDto.getLastName();
        
//        try{
            if (firstName != null && firstName.length() > 0 //
                    && lastName != null && lastName.length() > 0) {
                CustomerDto customerDtoNew = customerService.save(customerDto);
                return REDIRECT_FORM_VIEW+"?id="+customerDtoNew.getId();
            }
//        }catch(RuntimeException re){
//            
//            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, re);
//        }
        model.addAttribute("errorMessage", "First Name & Last Name required");

        return FORM_VIEW_HTML;
    }

    @GetMapping("/removeCustomer")
    public String removeCustomer(Model model, @RequestParam(value = "id", required = true) Integer customerId) {
        customerService.delete(customerId);
        return REDIRECT_LIST_VIEW;
    }
    
}

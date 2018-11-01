/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.AddressDto;
import com.hy.gaeaadmin.model.dto.ContactDto;
import com.hy.gaeaadmin.model.dto.ContactFormDto;
import com.hy.gaeaadmin.model.dto.ContactTypeDto;
import com.hy.gaeaadmin.model.dto.EmployeeDto;
import com.hy.gaeaadmin.model.service.AddressService;
import com.hy.gaeaadmin.model.service.ContactService;
import com.hy.gaeaadmin.model.service.ContactTypeService;
import com.hy.gaeaadmin.model.service.EmployeeService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
 * @author ndry93
 * tutorial to create calendar https://code.tutsplus.com/tutorials/codeigniter-from-scratch-the-calendar-library--net-9570
 */
@Controller
public class EmployeeController {

    private static final String VIEW_EMPLOYEE_V = "employee/formEmployee";
    
    private static final String LIST_EMPLOYEE_V = "employee/listEmployee";

    @Autowired
    private EmployeeService empService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactTypeService contactTypeService;
    @Autowired
    private AddressService addressService;

    @GetMapping("/employee")
    public String employee(Model model) {
        model.addAttribute("employees", empService.list());
        return LIST_EMPLOYEE_V;
    }

    @GetMapping("/addEmployee")
    public String addEmployee(Model model) {
        EmployeeDto empDto = new EmployeeDto();
        model.addAttribute("employeeDto", empDto);
        model.addAttribute("editMode", true);
        return VIEW_EMPLOYEE_V;
    }

    @GetMapping("/viewEmployee")
    public String viewEmployee(Model model, @RequestParam(value = "id", required = true) Integer employeeId) {
        System.out.println("------employeeId "+employeeId);
        EmployeeDto employeeDto = empService.findById(employeeId);
        this.prepareModelEmployeeDetailView(model,employeeDto);
        return VIEW_EMPLOYEE_V;
    }

    @PostMapping("/addEmployee")
    public String saveEmployee(Model model, //
            @ModelAttribute("employeeDto") EmployeeDto employeeDto) {
        String firstName = employeeDto.getFirstName();
        String lastName = employeeDto.getLastName();
        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            EmployeeDto employeeDtoNew = empService.save(employeeDto);
            return "redirect:/viewEmployee?id="+employeeDtoNew.getId();
        }

        model.addAttribute("errorMessage", "First Name & Last Name required");
        model.addAttribute("editMode", true);

        return VIEW_EMPLOYEE_V;
    }

    @GetMapping("/removeEmployee")
    public String removeEmployee(Model model, @RequestParam(value = "id", required = true) Integer employeeId) {
        empService.delete(employeeId);
        return "redirect:/employee";
    }
    
    @GetMapping("/removeEmployeeContact")
    public String removeEmployeeContact(Model model, 
            @RequestParam(value = "id", required = true) Integer contactId,
            @RequestParam(value = "employeeId", required = true) Integer employeeId) {
        
        contactService.deleteEmployeeContact(employeeId, contactId);
        return "redirect:/viewEmployee?id="+employeeId;
    }
    
    
//    @PostMapping("/addEmployeeAddress")
//    public String saveEmployeeAddress(Model model, //
//            @ModelAttribute("addressDto") AddressDto addressDto) {
//        String firstName = employeeDto.getFirstName();
//        String lastName = employeeDto.getLastName();
//
//        if (firstName != null && firstName.length() > 0 //
//                && lastName != null && lastName.length() > 0) {
//            
//            empService.save(employeeDto);
//
////            boolean editMode = (boolean) model.asMap().get("editMode");
////            if (editMode) {
////                return "redirect:/employee";
////            } else {
//            model.addAttribute("editMode", false);
//            return VIEW_EMPLOYEE_V;
////            }
//        }
//
//        model.addAttribute("errorMessage", "First Name & Last Name required");
//        model.addAttribute("editMode", true);
//
//        return VIEW_EMPLOYEE_V;
//    }
    
    @PostMapping("/addEmployeeContact")
    public String saveEmployeeContact(Model model, //
            @ModelAttribute("contactFormDto") ContactFormDto contactFormDto) {
        Integer contactTypeId = contactFormDto.getTypeId();
        String contactValue = contactFormDto.getValue();
        Integer employeeId = contactFormDto.getEmployeeId();
        EmployeeDto employeeDto = empService.findById(employeeId);
        if (contactTypeId != null && contactValue != null && employeeDto != null) {
            ContactDto contactDto = new ContactDto();
            contactDto.setValue(contactValue);
            ContactTypeDto contactTypeDto = contactTypeService.findById(contactTypeId);
            contactDto.setContactTypeId(contactTypeDto.getId());
            contactDto.setContactTypeName(contactTypeDto.getName());
            contactDto.getEmployeeList().add(employeeDto);
            ContactDto newContactDto = contactService.save(contactDto);
            employeeDto.getContactList().add(newContactDto);
            return "redirect:/viewEmployee?id="+employeeDto.getId();
        }

        model.addAttribute("errorMessage", "Contact type and value required");
        model.addAttribute("editMode", true);

        return VIEW_EMPLOYEE_V;
    }
    
    private void prepareModelEmployeeDetailView(Model model, EmployeeDto employeeDto){
        Integer empIdTemp = employeeDto == null ? null : employeeDto.getId();
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("employeeId", empIdTemp);
        System.out.println("-----prepareModelEmployeeDetailView "+employeeDto);
        if (employeeDto != null){
            model.addAttribute("addresses", employeeDto.getAddressList());
            model.addAttribute("contacts", employeeDto.getContactList()); 
        }
        
        
        model.addAttribute("editMode", false);
        
        //init dropdown contact types
        model.addAttribute("contactTypes",contactTypeService.list());
        
        //init empty contact form
        ContactFormDto emptyContactForm = new ContactFormDto();
        emptyContactForm.setEmployeeId(empIdTemp);
        model.addAttribute("contactFormDto",emptyContactForm);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

}

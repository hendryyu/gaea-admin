/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.EmployeeDto;
import com.hy.gaeaadmin.model.dto.PositionDto;
import com.hy.gaeaadmin.model.dto.PositionFormDto;
import com.hy.gaeaadmin.model.service.EmployeeService;
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
 * @author ndry93
 * tutorial to create calendar https://code.tutsplus.com/tutorials/codeigniter-from-scratch-the-calendar-library--net-9570
 */
@Controller
public class EmployeeController {
    private final String LIST_VIEW_HTML = "employee/listEmployee";
    private final String FORM_VIEW_HTML = "employee/formEmployee";
    private final String REDIRECT_LIST_VIEW = "redirect:/employee";
    private final String REDIRECT_FORM_VIEW = "redirect:/viewEmployee";


    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PositionService positionService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @GetMapping("/employee")
    public String employee(Model model) {
        model.addAttribute("employees", employeeService.list());
        return LIST_VIEW_HTML;
    }

    @GetMapping("/addEmployee")
    public String addEmployee(Model model) {
        EmployeeDto empDto = new EmployeeDto();
        model.addAttribute("employeeDto", empDto);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewEmployee")
    public String viewEmployee(Model model, @RequestParam(value = "id", required = true) Integer employeeId) {
        System.out.println("------employeeId "+employeeId);
        EmployeeDto employeeDto = employeeService.findById(employeeId);
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("positions",positionService.list());
        
        
        if(employeeDto != null && employeeDto.getId() != null){
            //init empty position form
            PositionFormDto emptyPositionForm = new PositionFormDto();
            emptyPositionForm.setEmployeeId(employeeDto.getId());
            model.addAttribute("positionFormDto",emptyPositionForm);
        }
        
        
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addEmployee")
    public String saveEmployee(Model model, //
            @ModelAttribute("employeeDto") EmployeeDto employeeDto) {
        String firstName = employeeDto.getFirstName();
        String lastName = employeeDto.getLastName();
        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            EmployeeDto employeeDtoNew = employeeService.save(employeeDto);
            return REDIRECT_FORM_VIEW+"?id="+employeeDtoNew.getId();
        }

        model.addAttribute("errorMessage", "First Name & Last Name required");

        return FORM_VIEW_HTML;
    }

    @GetMapping("/removeEmployee")
    public String removeEmployee(Model model, @RequestParam(value = "id", required = true) Integer employeeId) {
        employeeService.delete(employeeId);
        return REDIRECT_LIST_VIEW;
    }
    
    @GetMapping("/removeEmployeePosition")
    public String removeEmployeePosition(Model model, 
            @RequestParam(value = "id", required = true) Integer positionId,
            @RequestParam(value = "employeeId", required = true) Integer employeeId) {
        
        employeeService.deleteEmployeePosition(employeeId, positionId);
        return REDIRECT_FORM_VIEW+"?id="+employeeId;
    }
    
    
    @PostMapping("/addEmployeePosition")
    public String addEmployeePosition(Model model, //
            @ModelAttribute("positionFormDto") PositionFormDto positionFormDto) {
        Integer positionId = positionFormDto.getPositionId();
        Integer employeeId = positionFormDto.getEmployeeId();
        
        if(positionId != null){
            EmployeeDto employeeDto = employeeService.findById(employeeId);
            PositionDto positionDto = positionService.findById(positionId);
            if (positionDto != null && employeeDto != null) {
                employeeDto.getPositionList().add(positionDto);
                EmployeeDto newEmployeeDto = employeeService.save(employeeDto);
                return REDIRECT_FORM_VIEW+"?id="+employeeDto.getId();
            }
        }

        model.addAttribute("errorMessage", "Position required");

        return FORM_VIEW_HTML;
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.SalaryTypeDto;
import com.hy.gaeaadmin.model.service.SalaryTypeService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hendryyu
 */
@Controller
public class SalaryTypeController {
    
    private final String LIST_VIEW_HTML = "salaryType/listSalaryType";
    private final String FORM_VIEW_HTML = "salaryType/formSalaryType";
    private final String REDIRECT_LIST_VIEW = "redirect:/salaryType";
    
    @Autowired
    private SalaryTypeService salaryTypeService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/salaryType")
    public String page(Model model) {
        model.addAttribute("salaryTypes", salaryTypeService.list());
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addSalaryType")
    public String addSalaryType(Model model) {
        SalaryTypeDto salaryTypeDto = new SalaryTypeDto();
        model.addAttribute("salaryTypeDto", salaryTypeDto);
        model.addAttribute("editMode", true);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewSalaryType")
    public String viewSalaryType(Model model, @RequestParam(value = "id", required = true) Integer salaryTypeId) {
        SalaryTypeDto salaryTypeDto = salaryTypeService.findById(salaryTypeId);
        model.addAttribute("salaryTypeDto", salaryTypeDto);
        model.addAttribute("editMode", false);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addSalaryType")
    public String saveSalaryType(Model model, //
            @ModelAttribute("salaryTypeDto") SalaryTypeDto salaryTypeDto) {
        String name = salaryTypeDto.getName();
        String desc = salaryTypeDto.getDescription();
        
        if ( (name != null && name.length() > 0) && (desc != null && desc.length() > 0) ) {
            SalaryTypeDto salaryTypeDtoNew = salaryTypeService.save(salaryTypeDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "Salary Type Name and Description required");
        model.addAttribute("editMode", true); 

        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removeSalaryType")
    public String removeSalaryType(Model model, @RequestParam(value = "id", required = true) Integer salaryTypeId) {
        
        try{
            salaryTypeService.delete(salaryTypeId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("salaryTypes", salaryTypeService.list());
            return LIST_VIEW_HTML;
        }
    }
    
}

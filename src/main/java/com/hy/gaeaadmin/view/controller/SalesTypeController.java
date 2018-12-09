/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.SalesTypeDto;
import com.hy.gaeaadmin.model.service.SalesTypeService;
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
public class SalesTypeController {
    
    private final String LIST_VIEW_HTML = "salesType/listSalesType";
    private final String FORM_VIEW_HTML = "salesType/formSalesType";
    private final String REDIRECT_LIST_VIEW = "redirect:/salesType";
    
    @Autowired
    private SalesTypeService salesTypeService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/salesType")
    public String page(Model model) {
        model.addAttribute("salesTypes", salesTypeService.list());
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addSalesType")
    public String addSalesType(Model model) {
        SalesTypeDto salesTypeDto = new SalesTypeDto();
        model.addAttribute("salesTypeDto", salesTypeDto);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewSalesType")
    public String viewSalesType(Model model, @RequestParam(value = "id", required = true) Integer salesTypeId) {
        SalesTypeDto salesTypeDto = salesTypeService.findById(salesTypeId);
        model.addAttribute("salesTypeDto", salesTypeDto);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addSalesType")
    public String saveSalesType(Model model, //
            @ModelAttribute("salesTypeDto") SalesTypeDto salesTypeDto) {
        String name = salesTypeDto.getName();
        String code = salesTypeDto.getCode();
        String desc = salesTypeDto.getDescription();
        
        if ( (name != null && name.length() > 0) && (code != null && code.length() > 0) && (desc != null && desc.length() > 0) ) {
            SalesTypeDto salesTypeDtoNew = salesTypeService.save(salesTypeDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "Sales Type Name, Code and Description required");

        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removeSalesType")
    public String removeSalesType(Model model, @RequestParam(value = "id", required = true) Integer salesTypeId) {
        
        try{
            salesTypeService.delete(salesTypeId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("salesTypes", salesTypeService.list());
            return LIST_VIEW_HTML;
        }
    }
    
}

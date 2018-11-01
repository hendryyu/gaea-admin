/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.ContactTypeDto;
import com.hy.gaeaadmin.model.service.ContactTypeService;
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
public class ContactTypeController {
    
    private final String LIST_VIEW_HTML = "contactType/listContactType";
    private final String FORM_VIEW_HTML = "contactType/formContactType";
    private final String REDIRECT_LIST_VIEW = "redirect:/contactType";
    
    @Autowired
    private ContactTypeService contactTypeService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/contactType")
    public String page(Model model) {
        model.addAttribute("contactTypes", contactTypeService.list());
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addContactType")
    public String addContactType(Model model) {
        ContactTypeDto contactTypeDto = new ContactTypeDto();
        model.addAttribute("contactTypeDto", contactTypeDto);
        model.addAttribute("editMode", true);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewContactType")
    public String viewContactType(Model model, @RequestParam(value = "id", required = true) Integer contactTypeId) {
        ContactTypeDto contactTypeDto = contactTypeService.findById(contactTypeId);
        model.addAttribute("contactTypeDto", contactTypeDto);
        model.addAttribute("editMode", false);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addContactType")
    public String saveContactType(Model model, //
            @ModelAttribute("contactTypeDto") ContactTypeDto contactTypeDto) {
        String name = contactTypeDto.getName();
        
        if ( (name != null && name.length() > 0) ) {
            ContactTypeDto contactTypeDtoNew = contactTypeService.save(contactTypeDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "Name required");
        model.addAttribute("editMode", true); 

        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removeContactType")
    public String removeContactType(Model model, @RequestParam(value = "id", required = true) Integer contactTypeId) {
        try{
            contactTypeService.delete(contactTypeId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("contactTypes", contactTypeService.list());
            return LIST_VIEW_HTML;
        }
        
    }
    
}

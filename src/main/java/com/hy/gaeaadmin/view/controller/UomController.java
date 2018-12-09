/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.UomDto;
import com.hy.gaeaadmin.model.service.UomService;
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
public class UomController {
    
    private final String LIST_VIEW_HTML = "uom/listUom";
    private final String FORM_VIEW_HTML = "uom/formUom";
    private final String REDIRECT_LIST_VIEW = "redirect:/uom";
    
    @Autowired
    private UomService uomService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/uom")
    public String page(Model model) {
        model.addAttribute("uoms", uomService.list());
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addUom")
    public String addUom(Model model) {
        UomDto uomDto = new UomDto();
        model.addAttribute("uomDto", uomDto);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewUom")
    public String viewUom(Model model, @RequestParam(value = "id", required = true) Integer uomId) {
        UomDto uomDto = uomService.findById(uomId);
        model.addAttribute("uomDto", uomDto);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addUom")
    public String saveUom(Model model, //
            @ModelAttribute("uomDto") UomDto uomDto) {
        String name = uomDto.getUomName();
        String desc = uomDto.getUomDesc();
        
        if ( (name != null && name.length() > 0) && (desc != null && desc.length() > 0) ) {
            UomDto uomDtoNew = uomService.save(uomDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "UOM Name and Description required");

        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removeUom")
    public String removeUom(Model model, @RequestParam(value = "id", required = true) Integer uomId) {
        
        try{
            uomService.delete(uomId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("uoms", uomService.list());
            return LIST_VIEW_HTML;
        }
    }
    
}

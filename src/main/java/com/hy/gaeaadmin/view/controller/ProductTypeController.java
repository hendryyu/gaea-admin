/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.ProductTypeDto;
import com.hy.gaeaadmin.model.service.ProductTypeService;
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
public class ProductTypeController {
    
    private final String LIST_VIEW_HTML = "productType/listProductType";
    private final String FORM_VIEW_HTML = "productType/formProductType";
    private final String REDIRECT_LIST_VIEW = "redirect:/productType";
    
    @Autowired
    private ProductTypeService productTypeService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/productType")
    public String page(Model model) {
        model.addAttribute("productTypes", productTypeService.list());
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addProductType")
    public String addProductType(Model model) {
        ProductTypeDto productTypeDto = new ProductTypeDto();
        model.addAttribute("productTypeDto", productTypeDto);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewProductType")
    public String viewProductType(Model model, @RequestParam(value = "id", required = true) Integer productTypeId) {
        ProductTypeDto productTypeDto = productTypeService.findById(productTypeId);
        model.addAttribute("productTypeDto", productTypeDto);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addProductType")
    public String saveProductType(Model model, //
            @ModelAttribute("productTypeDto") ProductTypeDto productTypeDto) {
        String name = productTypeDto.getTypeName();
        String desc = productTypeDto.getTypeDesc();
        
        if ( (name != null && name.length() > 0) && (desc != null && desc.length() > 0) ) {
            ProductTypeDto productTypeDtoNew = productTypeService.save(productTypeDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "Product Type Name and Description required");
        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removeProductType")
    public String removeProductType(Model model, @RequestParam(value = "id", required = true) Integer productTypeId) {
        
        try{
            productTypeService.delete(productTypeId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("productTypes", productTypeService.list());
            return LIST_VIEW_HTML;
        }
    }
    
}

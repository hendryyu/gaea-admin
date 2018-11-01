/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.CurrencyDto;
import com.hy.gaeaadmin.model.service.CurrencyService;
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
public class CurrencyController {
    
    private final String LIST_VIEW_HTML = "currency/listCurrency";
    private final String FORM_VIEW_HTML = "currency/formCurrency";
    private final String REDIRECT_LIST_VIEW = "redirect:/currency";
    
    @Autowired
    private CurrencyService currencyService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/currency")
    public String page(Model model) {
        model.addAttribute("currencies", currencyService.list());
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addCurrency")
    public String addCurrency(Model model) {
        CurrencyDto currencyDto = new CurrencyDto();
        model.addAttribute("currencyDto", currencyDto);
        model.addAttribute("editMode", true);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewCurrency")
    public String viewCurrency(Model model, @RequestParam(value = "id", required = true) Integer currencyId) {
        CurrencyDto currencyDto = currencyService.findById(currencyId);
        model.addAttribute("currencyDto", currencyDto);
        model.addAttribute("editMode", false);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addCurrency")
    public String saveCurrency(Model model, //
            @ModelAttribute("currencyDto") CurrencyDto currencyDto) {
        String code = currencyDto.getCurrencyCode();
        String name = currencyDto.getCurrencyName();
        
        if ( (code != null && code.length() > 0) && (name != null && name.length() > 0)) {
            CurrencyDto currencyDtoNew = currencyService.save(currencyDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "Code and Name required");
        model.addAttribute("editMode", true); 

        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removeCurrency")
    public String removeCurrency(Model model, @RequestParam(value = "id", required = true) Integer currencyId) {
        try{
            currencyService.delete(currencyId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("currencies", currencyService.list());
            return LIST_VIEW_HTML;
        }
        
    }
}

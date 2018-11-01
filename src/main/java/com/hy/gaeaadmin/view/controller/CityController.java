/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.CityDto;
import com.hy.gaeaadmin.model.service.CityService;
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
public class CityController {
    
    private final String LIST_VIEW_HTML = "city/listCity";
    private final String FORM_VIEW_HTML = "city/formCity";
    private final String REDIRECT_LIST_VIEW = "redirect:/city";
    
    @Autowired
    private CityService cityService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/city")
    public String page(Model model) {
        model.addAttribute("cities", cityService.list());
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addCity")
    public String addCity(Model model) {
        CityDto cityDto = new CityDto();
        model.addAttribute("cityDto", cityDto);
        model.addAttribute("editMode", true);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewCity")
    public String viewCity(Model model, @RequestParam(value = "id", required = true) Integer cityId) {
        CityDto cityDto = cityService.findById(cityId);
        model.addAttribute("cityDto", cityDto);
        model.addAttribute("editMode", false);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addCity")
    public String saveCity(Model model, //
            @ModelAttribute("cityDto") CityDto cityDto) {
        String name = cityDto.getName();
        String code = cityDto.getCode();
        System.out.println("-----name "+name);
        System.out.println("-----code "+code);
        
        if ( (name != null && name.length() > 0) && (code != null && code.length() > 0) ) {
            CityDto cityDtoNew = cityService.save(cityDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "City Name and Code required");
        model.addAttribute("editMode", true); 

        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removeCity")
    public String removeCity(Model model, @RequestParam(value = "id", required = true) Integer cityId) {
        
        try{
            cityService.delete(cityId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("cities", cityService.list());
            return LIST_VIEW_HTML;
        }
    }
    
}

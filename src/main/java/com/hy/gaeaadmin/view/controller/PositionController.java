/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.PositionDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hendryyu
 */
@Controller
public class PositionController {
    private final String LIST_VIEW_HTML = "position/listPosition";
    private final String FORM_VIEW_HTML = "position/formPosition";
    private final String REDIRECT_LIST_VIEW = "redirect:/position";
    @Autowired
    private PositionService positionService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/position")
    public String page(Model model) {
        model.addAttribute("positions", positionService.list());
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addPosition")
    public String addPosition(Model model) {
        PositionDto positionDto = new PositionDto();
        model.addAttribute("positionDto", positionDto);
        model.addAttribute("editMode", true);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/viewPosition")
    public String viewPosition(Model model, @RequestParam(value = "id", required = true) Integer positionId) {
        PositionDto positionDto = positionService.findById(positionId);
        model.addAttribute("positionDto", positionDto);
        model.addAttribute("editMode", false);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addPosition")
    public String savePosition(Model model, //
            @ModelAttribute("positionDto") PositionDto positionDto) {
        String positionName = positionDto.getPositionName();
        String positionDesc = positionDto.getPositionDesc();
        
        
        if ( (positionName != null && positionName.length() > 0) && (positionDesc != null && positionDesc.length() > 0) ) {
            PositionDto positionDtoNew = positionService.save(positionDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "Position Name and Description required");
        model.addAttribute("editMode", true); 

        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removePosition")
    public String removePosition(Model model, @RequestParam(value = "id", required = true) Integer positionId) {
        
        try{
            positionService.delete(positionId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("positions", positionService.list());
            return LIST_VIEW_HTML;
        }
    }
    
}

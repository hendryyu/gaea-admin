/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hendryyu
 */
@Controller
public class DashboardController {
    
    
    private final String DASHBOARD_VIEW_HTML = "dashboard/dashboard";
    
    @RequestMapping("/dashboard")
    public String page(Model model) {
        return DASHBOARD_VIEW_HTML;
    }
    
}

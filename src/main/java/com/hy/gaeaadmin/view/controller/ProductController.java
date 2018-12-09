/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.ProductDto;
import com.hy.gaeaadmin.model.dto.ProductPictureDto;
import com.hy.gaeaadmin.model.service.ProductPictureService;
import com.hy.gaeaadmin.model.service.ProductService;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hendryyu
 */
@Controller
public class ProductController {
    
    private final String LIST_VIEW_HTML = "product/listProduct";
    private final String FORM_VIEW_HTML = "product/formProduct";
    private final String REDIRECT_LIST_VIEW = "redirect:/product";
    private final String REDIRECT_FORM_VIEW = "redirect:/viewProduct";
    
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductPictureService productPictureService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @GetMapping("/product")
    public String page(Model model) {
        model.addAttribute("products", productService.list());
        model.addAttribute("isModal", false);
        return LIST_VIEW_HTML;
    }
    
    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        ProductDto productDto = new ProductDto();
        this.initProductView(model,productDto);
        return FORM_VIEW_HTML;
    }
    
    private void initProductView(Model model, ProductDto productDto){
        model.addAttribute("productDto", productDto);
//        model.addAttribute("productTypeList",productTypeService.list());
        
        if(productDto != null && productDto.getId() != null){
            ProductPictureDto productPictureDto = new ProductPictureDto();
            productPictureDto.setProductId(productDto.getId());
            model.addAttribute("productPictureDto", productPictureDto);
            model.addAttribute("productPictureList", productPictureService.findByProductId(productDto.getId()));
        }
    }

    @GetMapping("/viewProduct")
    public String viewProduct(Model model, @RequestParam(value = "id", required = true) Integer productId) {
        ProductDto productDto = productService.findById(productId);
        this.initProductView(model,productDto);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addProduct")
    public String saveProduct(Model model, //
            @ModelAttribute("productDto") ProductDto productDto) {
        String code = productDto.getProductCode();
        String name = productDto.getProductName();
        String desc = productDto.getProductDesc();
        
        if ( (code != null && code.length() > 0) && (name != null && name.length() > 0) && (desc != null && desc.length() > 0) ) {
            ProductDto productDtoNew = productService.save(productDto);
            return REDIRECT_LIST_VIEW;
        }

        model.addAttribute("errorMessage", "Product Code, Name, and Description required");

        return FORM_VIEW_HTML;
    }
    
    @GetMapping("/removeProduct")
    public String removeProduct(Model model, @RequestParam(value = "id", required = true) Integer productId) {
        
        try{
            productService.delete(productId);
            return REDIRECT_LIST_VIEW;
        }catch(RuntimeException re){
            model.addAttribute("errorMessage",re.getMessage());
            model.addAttribute("products", productService.list());
            return LIST_VIEW_HTML;
        }
    }
    
    @PostMapping("/uploadProductImage")
    public String handleFileUpload(Model model, //
            @ModelAttribute("productPictureDto") ProductPictureDto productPictureDto) {
        
        System.out.println("-----productPictureDto "+productPictureDto);
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return REDIRECT_FORM_VIEW + "?id="+productPictureDto.getProductId();
    }
    
}

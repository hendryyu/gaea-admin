/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.error.MyCustomException;
import com.hy.gaeaadmin.model.dto.CustomerDto;
import com.hy.gaeaadmin.model.dto.ProductDto;
import com.hy.gaeaadmin.model.dto.SalesDetailDto;
import com.hy.gaeaadmin.model.dto.SalesDto;
import com.hy.gaeaadmin.model.service.CustomerService;
import com.hy.gaeaadmin.model.service.ProductService;
import com.hy.gaeaadmin.model.service.SalesDetailFittingService;
import com.hy.gaeaadmin.model.service.SalesDetailService;
import com.hy.gaeaadmin.model.service.SalesService;
import com.hy.gaeaadmin.model.service.SalesTypeService;
import com.hy.gaeaadmin.model.service.UniversityService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hendryyu
 * 
 * autocomplete reference https://stackoverflow.com/questions/16851632/implementing-jquery-autocomplete-in-springmvc
 */
@Controller
public class SalesController {

    private final String LIST_VIEW_HTML = "sales/listSales";
    private final String FORM_VIEW_HTML = "sales/formSales";
    private final String REDIRECT_LIST_VIEW = "redirect:/sales";
    private final String REDIRECT_DETAIL_VIEW = "redirect:/viewSales";

    @Autowired
    private SalesService salesService;
    @Autowired
    private SalesTypeService salesTypeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SalesDetailService salesDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UniversityService universityService;
    
    @Autowired
    private SalesDetailFittingService salesDetailFittingService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @GetMapping("/sales")
    public String page(Model model) {
        model.addAttribute("sales", salesService.list());
        return LIST_VIEW_HTML;
    }

    @GetMapping("/addSales")
    public String addSales(Model model) {

        this.initSalesForm(model, null);
        return FORM_VIEW_HTML;
    }

    private void initSalesForm(Model model, String errorMessage) {
        SalesDto salesDto = (SalesDto) model.asMap().get("salesDto");
        if (salesDto == null) {
            salesDto = new SalesDto();
            salesDto.setSalesTypeId(2);
            salesDto.setOccassionType("GRADUATION");
            model.addAttribute("salesDto", salesDto);
            
        }else{
            model.addAttribute("salesDetailList", salesDetailService.list());
            SalesDetailDto salesDetailDto = new SalesDetailDto();
            salesDetailDto.setSalesId(salesDto.getId());
            model.addAttribute("salesDetailDto", salesDetailDto);
            model.addAttribute("products", productService.list());
        }
        model.addAttribute("salesTypeList", salesTypeService.list());
        model.addAttribute("universityList", universityService.list());
        model.addAttribute("customers", customerService.list());
        
        
        model.addAttribute("isModal", true);
        model.addAttribute("errorMessage", errorMessage);
    }

    @GetMapping("/viewSales")
    public String viewSales(Model model, @RequestParam(value = "id", required = true) Integer salesId) {
        SalesDto salesDto = salesService.findById(salesId);
        model.addAttribute("salesDto", salesDto);
        this.initSalesForm(model, null);
        return FORM_VIEW_HTML;
    }

    @PostMapping("/addSales")
    public String saveSales(Model model, //
            @ModelAttribute("salesDto") SalesDto salesDto) {
        CustomerDto customer = salesDto.getCustomerDto();
        
        Date trxDate = salesDto.getTrxDate();
        String errMsg = "";
        try{
            if ((customer != null && customer.getFirstName().length() > 0 && customer.getContactPhone1().length() > 0) 
                    && (trxDate != null && trxDate.toString().length() > 0)) {
                SalesDto salesDtoNew = salesService.save(salesDto);
                model.addAttribute("salesDto",salesDtoNew);
                this.initSalesForm(model, null);
                return FORM_VIEW_HTML ;
            }else{
                errMsg = "Transaction Date, Customer Name, and Customer Phone are required";
            }
        }catch(MyCustomException cve){
            errMsg = cve.getErrMessage();
//            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, mce);
        }

        
        this.initSalesForm(model, errMsg);
        return FORM_VIEW_HTML;
    }

    @GetMapping("/removeSales")
    public String removeSales(Model model, @RequestParam(value = "id", required = true) Integer salesId) {

        try {
            salesService.delete(salesId);
            return REDIRECT_LIST_VIEW;
        } catch (RuntimeException re) {
            model.addAttribute("errorMessage", re.getMessage());
            model.addAttribute("sales", salesService.list());
            return LIST_VIEW_HTML;
        }
    }
    
    @PostMapping("/addSalesProduct")
    public String saveSalesProduct(Model model, //
            @ModelAttribute("salesDetailDto") SalesDetailDto salesDetailDto) {
        ProductDto productDto = salesDetailDto.getProductDto();
        String salesNote = salesDetailDto.getNote();
        double salesPrice = salesDetailDto.getSalesPrice();
        int salesId = salesDetailDto.getSalesId();
        System.out.println("----productDto "+productDto);
        System.out.println("----productDto.getId "+productDto.getId());
        System.out.println("----productDto.getProductCode "+productDto.getProductCode());
        System.out.println("----salesNote "+salesNote);
        System.out.println("----salesPrice "+salesPrice);
        System.out.println("----salesId "+salesId);
        
        String errMsg = "";
        try{
            if ((productDto != null && productDto.getId() != null ) 
                    && (salesNote != null && salesNote.length() > 0)
                    && (salesPrice > 0 && String.valueOf(salesPrice).length() > 0)
                ) {
                SalesDetailDto salesDetailDtoNew = salesDetailService.save(salesDetailDto);
                model.addAttribute("salesDetailDto",salesDetailDtoNew);
                this.initSalesForm(model, null);
                return REDIRECT_DETAIL_VIEW + "?id="+salesId;
            }else{
                errMsg = "Transaction Daone are required";
            }
        }catch(MyCustomException cve){
            errMsg = cve.getErrMessage();
//            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, mce);
        }

        
        this.initSalesForm(model, errMsg);
        return FORM_VIEW_HTML;
    }
    
    @GetMapping(value = "/viewSalesDetailFitting")
    public String findSalesDetailFitting(
            @RequestParam("salesId") int salesId,
            @RequestParam("id") int id,
            ModelMap model) {
        
        System.out.println("-----salesId "+salesId);
        System.out.println("-----id "+id);
        
        model.addAttribute("salesDetailFittingList", salesDetailFittingService.findBySalesIdAndDetailId(salesId, id));
        
        
        return "sales/fragments::fitting-modal-tbl";
    }
    
    
//    @GetMapping(value = "/modal/products")
//    public String showModalProducts(
//            @RequestParam(value="code", required=false) String code,
//            @RequestParam(value="name", required=false) String name,
//            ModelMap model){
//        
//        List<ProductDto> productList = null;
//        
//        if(productService.list() != null & (  (name != null && name.length() > 0) || (code != null && code.length() > 0) )){
//                productList = productService.list().stream() 
//                .filter(data -> (name != null && (data.getProductName().toUpperCase().contains(name.toUpperCase())
//                        && (code != null && (data.getProductCode().contains(code) )))
//                        )).collect(Collectors.toList());      
//        }else{
//            productList = productService.list();
//        }
//        System.out.println("-----name "+name);
//        System.out.println("-----code "+code);
//        System.out.println("-----customerList.size() "+productList.size());
//        
//        model.addAttribute("products", productList);
//        return  "sales/modal :: product-modal-tbl"; 
//    }
//    
//    @GetMapping(value = "/modal/customers")
//    public String showModalCustomers(
//            @RequestParam(value="name", required=false) String name,
//            @RequestParam(value="phone", required=false) String phone,
//            @RequestParam(value="email", required=false) String email,
//            ModelMap model){
//        
//        List<CustomerDto> customerList = null;
//        
//        if(customerService.list() != null & ( 
//                (name != null && name.length() > 0) ||  
//                (phone != null && phone.length() > 0) ||  
//                (email != null && email.length() > 0)
//                )){
//            customerList = customerService.list().stream() 
//                .filter(data -> (name != null && (data.getFirstName().toUpperCase().contains(name.toUpperCase()) || data.getLastName().toUpperCase().contains(name.toUpperCase())))
//                        && (phone != null && (data.getContactPhone1().contains(phone) || data.getContactPhone2().contains(phone)))
////                        && (email != null && (data.getContactEmail1().contains(email) || data.getContactEmail2().contains(email)) )
//                ).collect(Collectors.toList());      
//        }else{
//            customerList = customerService.list();
//        }
//        System.out.println("-----name "+name);
//        System.out.println("-----phone "+phone);
//        System.out.println("-----email "+email);
//        System.out.println("-----customerList.size() "+customerList.size());
//        
//        model.addAttribute("customers", customerList);
//        return  "sales/modal :: customer-modal-tbl"; 
//    }
    
}

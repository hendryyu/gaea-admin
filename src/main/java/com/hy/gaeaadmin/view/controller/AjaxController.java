/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.view.controller;

import com.hy.gaeaadmin.model.dto.AjaxResponseBody;
import com.hy.gaeaadmin.model.dto.CustomerDto;
import com.hy.gaeaadmin.model.dto.CustomerSearchCriteria;
import com.hy.gaeaadmin.model.dto.ProductDto;
import com.hy.gaeaadmin.model.dto.ProductSearchCriteria;
import com.hy.gaeaadmin.model.service.CustomerService;
import com.hy.gaeaadmin.model.service.ProductService;
import com.hy.gaeaadmin.model.service.SalesDetailFittingService;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hendryyu
 */
@RestController
public class AjaxController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;
    

    @PostMapping("/api/customer")
    public ResponseEntity<?> findCustomerByIdViaAjax(
            @Valid @RequestBody CustomerSearchCriteria search, Errors errors) {

        AjaxResponseBody<CustomerDto> result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);

        }

        System.out.println("------search.getId() " + search.getId());
//        CustomerDto customerDto = customerService.findByPhone(search.getPhone());
        CustomerDto customerDto = customerService.findById(search.getId());
        if (customerDto == null) {
            result.setMsg("customer not found!");

        } else {
            result.setMsg("success");
            result.setResultObject(customerDto);

        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/product")
    public ResponseEntity<?> findProductByIdViaAjax(
            @Valid @RequestBody ProductSearchCriteria search, Errors errors) {

        AjaxResponseBody<ProductDto> result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);

        }

        System.out.println("------search.getId() " + search.getId());
//        CustomerDto customerDto = customerService.findByPhone(search.getPhone());
        ProductDto productDto = productService.findById(search.getId());
        if (productDto == null) {
            result.setMsg("product not found!");

        } else {
            result.setMsg("success");
            result.setResultObject(productDto);

        }
        return ResponseEntity.ok(result);
    }

    
    
    
    

}

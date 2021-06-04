package com.saber.site.controllers;

import com.saber.site.entities.CustomerEntity;
import com.saber.site.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger log= LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }

    @GetMapping(value = {"","list"})
    public String listCustomers(Model model){
        List<CustomerEntity> customerEntityList = customerService.findAllCustomers();
        log.info("customers === {}",customerEntityList);
        System.out.println("customers ===> "+customerEntityList);
        model.addAttribute("customers",customerEntityList);
        return "customer/list";
    }
}

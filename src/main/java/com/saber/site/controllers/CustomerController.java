package com.saber.site.controllers;

import com.saber.site.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }

    @GetMapping(value = {"","list"})
    public String listCustomers(Model model){
        model.addAttribute("customers",customerService.findAllCustomers());
        return "customer/list";
    }
}

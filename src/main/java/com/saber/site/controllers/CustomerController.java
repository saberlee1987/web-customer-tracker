package com.saber.site.controllers;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

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
    @GetMapping(value = "/save")
    public String addCustomer(Model model){
        model.addAttribute("customer",new CustomerDto());
        return "customer/save";
    }
    @PostMapping(value = "/save")
    public RedirectView saveCustomer(CustomerDto customerDto){
        CustomerEntity customerEntity = this.customerService.saveCustomer(customerDto);
        if (customerEntity!=null){
            return new RedirectView("/customer/list",true,false);
        }else{
            return new RedirectView("/customer/error",true,false);
        }

    }
}

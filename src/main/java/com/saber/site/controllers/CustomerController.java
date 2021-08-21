package com.saber.site.controllers;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        log.info("customers ===> "+customerEntityList);
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
        log.info("Request for add new Customer ==> {}",customerDto);
        CustomerEntity customerEntity = this.customerService.saveCustomer(customerDto);
        if (customerEntity!=null){
            return new RedirectView("/customer/list",true,false);
        }else{
            return new RedirectView("/customer/error",true,false);
        }

    }
    @GetMapping(value = "/update/{id}")
    public String updateCustomer(Model model,@PathVariable(name = "id") Integer id){
        CustomerDto customer = this.customerService.findCustomerById(id);
        if (customer!=null){
            model.addAttribute("customer",customer);
            model.addAttribute("id",id);
            return "customer/update";
        }
        else{
            model.addAttribute("message", String.format("Customer With This id %d does not exits", id));
            return "customer/list";
        }

    }
    @PostMapping(value = "/update/{id}")
    public RedirectView updateCustomer(@PathVariable(name = "id") Integer id,CustomerDto customerDto,Model model){
        CustomerEntity customerEntity = this.customerService.updateCustomer(id, customerDto);
        if (customerEntity==null){
            return new RedirectView("/customer/error",true,false);
        }else{
            return new RedirectView("/customer/list",true,false);
        }
    }

    @GetMapping(value = "/delete/{id}")
    public RedirectView deleteCustomer(Model model,@PathVariable(name = "id") Integer id){
        log.info("Delete Customer with id == {} ",id);
        boolean result = customerService.deleteCustomerById(id);
        if (result){
            return new  RedirectView("/customer/list",true,false);
        }else{
            return new RedirectView("/customer/error",true,false);
        }
    }
}

package com.saber.site.controllers;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
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
    public ModelAndView saveCustomer(@Valid CustomerDto customerDto, Errors errors,Model model){
        log.info("Request for add new Customer ==> {}",customerDto);
        if (errors.hasErrors()){
            List<FieldError> errorsList = errors.getFieldErrors();
            for (FieldError fieldError :errorsList){
                log.error("field {} ===> {}",fieldError.getField(),fieldError.getDefaultMessage());
            }
            model.addAttribute("customer",customerDto);
            model.addAttribute("errors",errorsList);
            return new ModelAndView("customer/save");

        }
        CustomerEntity customerEntity = this.customerService.saveCustomer(customerDto);
        if (customerEntity!=null){
            return new ModelAndView( new RedirectView("/customer/list",true,false));
        }else{
            return new ModelAndView(new RedirectView("/customer/error",true,false));
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
    public ModelAndView updateCustomer(@PathVariable(name = "id") Integer id, @Valid CustomerDto customerDto, Errors errors,Model model){

        if (errors.hasErrors()){
            List<FieldError> errorsList = errors.getFieldErrors();
            for (FieldError fieldError :errorsList){
                log.error("field {} ===> {}",fieldError.getField(),fieldError.getDefaultMessage());
            }
            model.addAttribute("customer",customerDto);
            model.addAttribute("errors",errorsList);
            model.addAttribute("id",id);
            return new ModelAndView("customer/update");
        }

        CustomerEntity customerEntity = this.customerService.updateCustomer(id, customerDto);
        if (customerEntity==null){
            return new ModelAndView( new RedirectView("/customer/error",true,false));
        }else{
            return new ModelAndView( new RedirectView("/customer/list",true,false));
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

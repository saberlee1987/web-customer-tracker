package com.saber.site.services.impl;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.repositories.CustomerRepository;
import com.saber.site.services.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(@Qualifier(value = "customerRepositoryJpa") CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomerEntity saveCustomer(CustomerDto customerDto) {
        return this.customerRepository.saveCustomer(customerDto);
    }

    @Override
    public List<CustomerEntity> findAllCustomers() {
        return this.customerRepository.findAllCustomers();
    }

    @Override
    public CustomerDto findCustomerById(Integer id) {
        try {
            CustomerEntity customerEntity= this.customerRepository.findCustomerById(id);
            if (customerEntity==null)
                return null;
            CustomerDto customerDto = new CustomerDto();
            customerDto.setEmail(customerEntity.getEmail());
            customerDto.setFirstName(customerEntity.getFirstName());
            customerDto.setLastName(customerEntity.getLastName());
            return customerDto;
        }catch (Exception ex){
            return null;
        }

    }

    @Override
    public CustomerEntity updateCustomer(Integer id, CustomerDto customerDto) {
        return this.customerRepository.updateCustomer(id,customerDto);
    }

    @Override
    public boolean deleteCustomerById(Integer id) {
        return false;
    }
}

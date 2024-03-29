package com.saber.site.repositories;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;

import java.util.List;

public interface CustomerRepository {

    CustomerEntity saveCustomer(CustomerDto customerDto);
    List<CustomerEntity> findAllCustomers();
    CustomerEntity findCustomerById(Integer id);
    CustomerEntity updateCustomer(Integer id,CustomerDto customerDto);
    boolean deleteCustomerById(Integer id);

}

package com.saber.site.repositories.impl;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.repositories.CustomerRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository(value = "customerRepositoryJpa")
public class CustomerRepositoryJpaImpl implements CustomerRepository {

    @PersistenceContext
    private  EntityManager entityManager;


    @Override
    @Transactional
    public CustomerEntity saveCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = createCustomerEntity(customerDto);
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    @Override
    @Transactional
    public List<CustomerEntity> findAllCustomers() {

        return this.entityManager.createNamedQuery("CustomerEntity.findAll", CustomerEntity.class).getResultList();
    }

    @Override
    @Transactional
    public CustomerEntity findCustomerById(Integer id) {
        return this.entityManager.createNamedQuery("CustomerEntity.findById",CustomerEntity.class)
                .setParameter("id",id).getSingleResult();
    }

    @Override
    @Transactional
    public CustomerEntity updateCustomer(Integer id ,CustomerDto customerDto) {

        CustomerEntity customerEntity = findCustomerById(id);
        if (customerEntity == null)
            return null;

        customerEntity.setFirstName(customerDto.getFirstName());
        customerEntity.setLastName(customerDto.getLastName());
        customerEntity.setEmail(customerDto.getEmail());

        entityManager.persist(customerEntity);

        return customerEntity;
    }

    @Override
    @Transactional
    public boolean deleteCustomerById(Integer id) {
        return false;
    }


    private CustomerEntity createCustomerEntity(CustomerDto customerDto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setEmail(customerDto.getEmail());
        entity.setFirstName(customerDto.getFirstName());
        entity.setLastName(customerDto.getLastName());
        return entity;
    }
}

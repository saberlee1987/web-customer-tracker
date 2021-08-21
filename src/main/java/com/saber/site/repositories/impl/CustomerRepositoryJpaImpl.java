package com.saber.site.repositories.impl;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository(value = "customerRepositoryJpa")
public class CustomerRepositoryJpaImpl implements CustomerRepository {

    private static  final Logger log = LoggerFactory.getLogger(CustomerRepositoryJpaImpl.class);
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public CustomerEntity saveCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = createCustomerEntity(customerDto);
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    @Override
    public List<CustomerEntity> findAllCustomers() {

        return this.entityManager.createNamedQuery("CustomerEntity.findAll", CustomerEntity.class).getResultList();
    }

    @Override
    public CustomerEntity findCustomerById(Integer id) {
        return this.entityManager.createNamedQuery("CustomerEntity.findById", CustomerEntity.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public CustomerEntity updateCustomer(Integer id, CustomerDto customerDto) {

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
    public boolean deleteCustomerById(Integer id) {
        CustomerEntity customerEntity = findCustomerById(id);
        if (customerEntity == null)
            return false;
        try {
            this.entityManager.remove(customerEntity);
            return true;
        } catch (Exception ex) {
            log.error("Error when deleting customer by id {} ===> {} ",id,ex.getMessage());
            return false;
        }

    }


    private CustomerEntity createCustomerEntity(CustomerDto customerDto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setEmail(customerDto.getEmail());
        entity.setFirstName(customerDto.getFirstName());
        entity.setLastName(customerDto.getLastName());
        return entity;
    }
}

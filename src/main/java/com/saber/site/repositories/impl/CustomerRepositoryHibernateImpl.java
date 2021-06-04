package com.saber.site.repositories.impl;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.repositories.CustomerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

//@Repository(value = "customerRepositoryHibernate")

public class CustomerRepositoryHibernateImpl implements CustomerRepository {


    private SessionFactory sessionFactory;

    public CustomerRepositoryHibernateImpl(@Qualifier(value = "hibernateSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @Transactional
    public CustomerEntity saveCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = createCustomerEntity(customerDto);
        Session session = sessionFactory.getCurrentSession();
        session.save(customerEntity);
        return customerEntity;
    }

    @Override
    @Transactional
    public List<CustomerEntity> findAllCustomers() {

        Session session =sessionFactory.getCurrentSession();
        return session.createNamedQuery("CustomerEntity.findAll", CustomerEntity.class).getResultList();
    }

    @Override
    @Transactional
    public CustomerEntity findCustomerById(Integer id) {

        Session session =sessionFactory.getCurrentSession();
        return session.createNamedQuery("CustomerEntity.findById",CustomerEntity.class)
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

        Session session =sessionFactory.getCurrentSession();

        session.save(customerEntity);

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

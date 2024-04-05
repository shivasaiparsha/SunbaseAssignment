package com.example.SunBase.Repository;

import com.example.SunBase.Models.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {





    Customer findByEmail(String emailId);


    @Transactional
    @Query(value = "select * from Customer order by username",nativeQuery = true)
    List<Customer> findAllByOrderByUsername();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Customer c WHERE c.email = :email", nativeQuery = true)
    void deleteByEmail(String email);




    List<Customer> findByCity(String value);

    List<Customer> findByPhone(String value);

    List<Customer> findByFirstName(String value);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Customer  WHERE customer_id = :customerId", nativeQuery = true)
    void deleteByCustomerId(String customerId);



    @Query(value = "SELECT * FROM Customer  WHERE customer_id = :customerId", nativeQuery = true)
    Customer findByCutomerId(String customerId);

//    boolean findByCustomerId(String customerId);


//    Customer findByCutomerId(String customerId);
}

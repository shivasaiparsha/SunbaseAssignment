package com.example.SunBase.Service;

import com.example.SunBase.Dtos.AddCustomerDto;
import com.example.SunBase.Dtos.DeleteByIdDto;
import com.example.SunBase.Dtos.GetCustomerByIdDto;
import com.example.SunBase.Models.Customer;
import com.example.SunBase.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository userRepository;

    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public Customer Update(Customer customer, AddCustomerDto adduserDto)
    {
          customer.setFirst_name(adduserDto.getFirst_name());
          customer.setLast_name(adduserDto.getLast_name());
          customer.setAddress(adduserDto.getAddress());
          customer.setCity(adduserDto.getCity());
          customer.setEmail(adduserDto.getEmail());
          customer.setPassword(passwordEncoder.encode(adduserDto.getPassword())); // encode password
          customer.setRole(adduserDto.getRole());
          customer.setState(adduserDto.getState());
          return customer;
    }
    public String addUserToDb(AddCustomerDto addUserDto) throws Exception
    {
          Customer customer=userRepository.findByEmail(addUserDto.getEmail());
          if(customer!=null)
          {
              // if customer already exist in db
              Customer updatedCustomer =Update(customer, addUserDto);
              userRepository.save(updatedCustomer);
          }
          else {
              // password encode
              String password = passwordEncoder.encode(addUserDto.getPassword());
              Customer newCustomer = new Customer(addUserDto.getUsername(), addUserDto.getEmail(), password, addUserDto.getRole());
              userRepository.save(newCustomer);

          }
          return "successfull";
    }

    public List<Customer> getAllCustomerPresentInDb() throws Exception
    {
        // Retrieve all customers from the database using the UserRepository
        List<Customer> customerList=userRepository.findAll();
         return customerList;
    }




    public List<Customer> getCustomerByOrderByName() throws Exception
    {
        // find all customers by
        List<Customer> customerList=userRepository.findAllByOrderByUsername();
        return customerList;
    }

    //get Customer Id;
    public Customer  getCustomerById(GetCustomerByIdDto getCustomerByIdDto) throws Exception
    {
        // email is unique get custoemer by email
        Customer customer=userRepository.findByEmail(getCustomerByIdDto.getEmailId());
        return customer;
    }

    // delete customer by emailId
    public String  deleteCustomerById(DeleteByIdDto deleteByIdDto) throws Exception
    {
        // check customer exist in db or not

        // delete customer by email
        userRepository.deleteByEmail(deleteByIdDto.getEmail());
          return "successful";
    }



}

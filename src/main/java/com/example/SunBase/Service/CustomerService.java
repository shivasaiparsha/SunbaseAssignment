package com.example.SunBase.Service;

import com.example.SunBase.Dtos.AddCustomerDto;
import com.example.SunBase.Dtos.DeleteByIdDto;
import com.example.SunBase.Dtos.GetCustomerByIdDto;
import com.example.SunBase.Models.Customer;
import com.example.SunBase.Repository.CustomerRepository;
import org.apache.catalina.User;
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

    public String genarateUniqueId(AddCustomerDto addCustomerDto)
    {
         List<Customer> customerList=userRepository.findAllByZipcode(addCustomerDto.getZipcode());
         int noOfCustomersPresentsInZipCodeArea=customerList.size()+1;
          String zipcode=addCustomerDto.getZipcode();
          String uuid="SUNBASE"+zipcode+noOfCustomersPresentsInZipCodeArea;
          return uuid;
    }
    public Customer Update(Customer customer, AddCustomerDto addCustomerDto)
    {
        // genarate unique id

          customer.setFirst_name(addCustomerDto.getFirst_name());
          customer.setLast_name(addCustomerDto.getLast_name());
          customer.setAddress(addCustomerDto.getAddress());
          customer.setCity(addCustomerDto.getCity());
          customer.setEmail(addCustomerDto.getEmail());
          customer.setPassword(passwordEncoder.encode(addCustomerDto.getPassword())); // encode password
          customer.setRole(addCustomerDto.getRole());
          customer.setState(addCustomerDto.getState());

          return customer;
    }
    public String addUserToDb(AddCustomerDto addCustomerDto) throws Exception
    {
          Customer customer=userRepository.findByEmail(addCustomerDto.getEmail());
          if(customer!=null)
          {
              // if customer already exist in db
              Customer updatedCustomer =Update(customer, addCustomerDto);
              userRepository.save(updatedCustomer);
          }
          else {
              // password encode
              String uniqueId=genarateUniqueId(addCustomerDto);
              customer.setZipcode(uniqueId);
              String password = passwordEncoder.encode(addCustomerDto.getPassword());
              Customer newCustomer = new Customer(addCustomerDto.getUsername(), addCustomerDto.getEmail(), password, addCustomerDto.getRole());
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
        if(userRepository.findByEmail(getCustomerByIdDto.getEmailId())==null)
        {
            throw new Exception("customer not found Exceptions");
        }

        Customer customer=userRepository.findByEmail(getCustomerByIdDto.getEmailId());
        return customer;
    }

    // delete customer by emailId
    public String  deleteCustomerById(DeleteByIdDto deleteByIdDto) throws Exception
    {
        // check customer exist in db or not
            if(userRepository.findByEmail(deleteByIdDto.getEmail())==null)
            {
                throw new Exception("customer not found Exceptions");
            }
        // delete customer by email
        userRepository.deleteByEmail(deleteByIdDto.getEmail());
          return "successful";
    }

}

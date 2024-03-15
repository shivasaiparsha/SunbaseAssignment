package com.example.SunBase.Service;

import com.example.SunBase.Dtos.RequestDto.AddCustomerDto;
import com.example.SunBase.Dtos.RequestDto.DeleteByIdDto;
import com.example.SunBase.Dtos.RequestDto.GetCustomerByIdDto;
import com.example.SunBase.Dtos.ResponseDto.UserResponseDto;
import com.example.SunBase.Models.Customer;
import com.example.SunBase.Repository.CustomerRepository;
import com.example.SunBase.TransFormer.CustomerTransformer;
import com.example.SunBase.TransFormer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository userRepository;

    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public String genarateUniqueId(AddCustomerDto addCustomerDto)
    {
        // here i'm find all customers presents in zipcode area to
         List<Customer> customerList=userRepository.findAllByZipcode(addCustomerDto.getZipcode());

               Customer customer =customerList.get(customerList.size()-1);
               String uuidOfLastCustomeradded=customer.getCustomerId();
               String  divideduuid[]=uuidOfLastCustomeradded.split("-");
               int noOfCustomersPresentsInZipCodeArea=Integer.parseInt(divideduuid[2])+1;
          String zipcode=addCustomerDto.getZipcode();
          String uuid="SUNBASE"+"-"+zipcode+"-"+noOfCustomersPresentsInZipCodeArea;
          return uuid;
    }
    public Customer Update(Customer customer, AddCustomerDto addCustomerDto)
    {
        // genarate unique id

          customer.setFirstname(addCustomerDto.getFirstname());
          customer.setLastname(addCustomerDto.getLastname());
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
              String password = passwordEncoder.encode(addCustomerDto.getPassword());
              Customer newCustomer= CustomerTransformer.BuildCustomer(addCustomerDto, uniqueId,password);
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
            if(userRepository.findByUsername(deleteByIdDto.getUsername())==null)
            {
                throw new Exception("customer not found Exceptions");
            }
        // delete customer by email
        userRepository.deleteByUsername(deleteByIdDto.getUsername());
          return "successful";
    }

    public List getUsersBy(String search, String value) {

        List<Customer> userList;

            if(search.equals("city"))  {
                userList = userRepository.findByCity(value);

            }
        else if(search.equals("phone"))   {
                userList = userRepository.findByPhone(value);

            }
       else if(search.equals("firstname"))  {
                userList = userRepository.findByFirstname(value);

            }
            else {
                userList=userRepository.findAll();
            }


        //else I'll have the value..

        //let's Convert the  Every User to UserResponce dto using our Transformer Function and I've actually Used
        List<UserResponseDto> userResponceDtos = userList.stream()
                .map(ele -> UserTransformer.userResponceDtoFromUser(ele))
                .collect(Collectors.toList());



        return userResponceDtos;
    }


}

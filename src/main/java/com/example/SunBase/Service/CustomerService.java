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
public class CustomerService implements CutomerServiceInterface{

    @Autowired
    private CustomerRepository customerRepository;

    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public String genarateUniqueId(AddCustomerDto addCustomerDto)
    {

          String randomStr="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz1234567890";

          String username= addCustomerDto.getFirstName();
          StringBuilder stringBuilder=new StringBuilder();

          for(int i=0; i<5; i++)
          {
              int randomIndex=  (int)Math.floor(Math.random()*randomStr.length());
              stringBuilder.append(randomStr.charAt(randomIndex));

              if(i<username.length())
                  stringBuilder.append(username.charAt(i));

          }
          String cutomerID= stringBuilder.toString();


          return cutomerID;
    }

    @Override
    public Customer Update(AddCustomerDto addCustomerDto) throws Exception
    {
        // genarate unique id

        Customer customer=customerRepository.findByEmail(addCustomerDto.getEmail());
//        if(customer==null)
//        {
//            throw new Exception("User with Email not found") ;
//        }

//          customer.setFirstName(addCustomerDto.getFirstName());
//          customer.setLastName(addCustomerDto.getLastName());
//          customer.setAddress(addCustomerDto.getAddress());
//          customer.setCity(addCustomerDto.getCity());
//          customer.setEmail(addCustomerDto.getEmail());
//          customer.setState(addCustomerDto.getState());

          Customer n1=CustomerTransformer.BuildCustomer(addCustomerDto, addCustomerDto.getCustomerId());

         Customer newCutomer= customerRepository.save(n1);
          return customer;
    }

    @Override
    public Customer addUserToDb(AddCustomerDto addCustomerDto) throws Exception
    {


              if(addCustomerDto.getCustomerId()!=null)
              {
//                  throw new Exception("user already Exist");
                  System.out.println(addCustomerDto);
                  Customer customer=  Update(addCustomerDto);
                  System.out.println(customer);
                  return  customer;
              }
              else {
                  // password encode
                  String uniqueId = genarateUniqueId(addCustomerDto);
                  Customer newCustomer = CustomerTransformer.BuildCustomer(addCustomerDto, uniqueId);
                  customerRepository.save(newCustomer);
                  return newCustomer;
              }



    }

    @Override
    public List<Customer> getAllCustomerPresentInDb() throws Exception
    {
        // Retrieve all customers from the database using the UserRepository

        List<Customer> customerList=customerRepository.findAll();
         return customerList;
    }






    @Override
    //get Customer Id;
    public Customer  getCustomerById(String cutomerId) throws Exception
    {
        // email is unique get custoemer by email
        if(customerRepository.findByCutomerId(cutomerId)==null)
        {
            throw new Exception("customer not found Exceptions");
        }

        Customer customer=customerRepository.findByCutomerId(cutomerId);
        return customer;
    }

    // delete customer by emailId
    @Override
    public String  deleteCustomerById(String cutomerId) throws Exception
    {
//         check customer exist in db or not
            if(customerRepository.findByCutomerId(cutomerId)==null)
            {
                throw new Exception("customer not found Exceptions");
            }
        // delete customer by email
        customerRepository.deleteByCustomerId(cutomerId);
          return "successful";
    }

    @Override
    public List<UserResponseDto> getUsersBy(String search, String value) {

        List<Customer> userList;


            if(search.equals("city"))  {

                userList = customerRepository.findByCity(value);

            }
        else if(search.equals("phone"))   {
                userList = customerRepository.findByPhone(value);

            }
       else if(search.equals("Firstname"))  {
                userList = customerRepository.findByFirstName(value);

            }
            else {
                userList=customerRepository.findAll();
            }

            System.out.println(userList);


        //else I'll have the value..

        //let's Convert the  Every User to UserResponce dto using our Transformer Function and I've actually Used
        List<UserResponseDto> userResponceDtos = userList.stream()
                .map(ele -> UserTransformer.userResponceDtoFromUser(ele))
                .collect(Collectors.toList());



        return userResponceDtos;
    }


}

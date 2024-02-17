package com.example.SunBase.Controller;

import com.example.SunBase.Dtos.DeleteByIdDto;
import com.example.SunBase.Dtos.GetCustomerByIdDto;
import com.example.SunBase.Models.Customer;
import com.example.SunBase.Service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
// all the api's presents in this class in should be pre authorize
public class CustomerController {

     @Autowired
     private CustomerService userService;

    @Autowired
    private UserDetailsService userDetailsService;


    @GetMapping("/getAllCustoemrs")
    @PreAuthorize("user")
    public ResponseEntity<List<Customer>>  getAllCustomers()
    {
        // get all customers by presents in db api
         try{
              List<Customer> customers= userService.getAllCustomerPresentInDb();
              return new ResponseEntity<>(customers, HttpStatus.OK);
         }
         catch (Exception e)
         {
             return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
         }
    }

    @GetMapping("/getALlCustoemersBySortedByUserName")
    @PreAuthorize("user")
    public ResponseEntity<List<Customer>>  getALlCustoemersBySortedByUserName()
    {
        //get list of customers by sorted by username
        try{
            List<Customer> customers= userService.getCustomerByOrderByName();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            //catch internal server errors, and  uninterrupted exceptions
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustoemrById")
    @PreAuthorize("user")
    public ResponseEntity<?> getCustomerByCustoemrId(@RequestBody GetCustomerByIdDto getCustomerById)
    {
        // get customer by customer EMAILID
         try{
             Customer customer= userService.getCustomerById(getCustomerById);
             return new ResponseEntity<>(customer, HttpStatus.OK);
         }
        catch(Exception e)
        {
            //catch any uninterrupted exceptions
            log.error("internal server exceptions");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

      @DeleteMapping("/deleteByID")
      @PreAuthorize("user")
      public ResponseEntity<String> deleteCustomerById(@RequestBody DeleteByIdDto deleteById)
      {
            try {
                //delete customer by id aoi
                String messge=userService.deleteCustomerById(deleteById);
                return new ResponseEntity<>(messge, HttpStatus.OK);
            }
            catch (Exception e)
            {
                // throw if customer not found in db
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
      }


}
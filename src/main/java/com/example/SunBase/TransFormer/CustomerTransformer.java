package com.example.SunBase.TransFormer;

import com.example.SunBase.Dtos.RequestDto.AddCustomerDto;
import com.example.SunBase.Models.Customer;
import lombok.Builder;

@Builder
public class CustomerTransformer {
    public static Customer BuildCustomer(AddCustomerDto addCustomerDto, String customerId, String password) {

        Customer customer=Customer.builder().customerId(customerId).first_name(addCustomerDto.getFirst_name())
                . last_name(addCustomerDto.getLast_name()).phone(addCustomerDto.getPhone()).city(addCustomerDto.getCity())
                .state(addCustomerDto.getState()).email(addCustomerDto.getEmail()).role(addCustomerDto.getRole())
                .address(addCustomerDto.getAddress()).street(addCustomerDto.getStreet()).zipcode(addCustomerDto.getZipcode())
                .username(addCustomerDto.getUsername()).password(password).build();
      return customer;
    }

}

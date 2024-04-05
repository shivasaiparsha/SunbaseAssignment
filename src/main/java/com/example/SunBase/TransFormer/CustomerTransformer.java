package com.example.SunBase.TransFormer;

import com.example.SunBase.Dtos.RequestDto.AddCustomerDto;
import com.example.SunBase.Models.Customer;
import lombok.Builder;

@Builder
public class CustomerTransformer {
    public static Customer BuildCustomer(AddCustomerDto addCustomerDto, String customerId) {

        Customer customer=Customer.builder().customerId(customerId).firstName(addCustomerDto.getFirstName())
                . lastName(addCustomerDto.getLastName()).phone(addCustomerDto.getPhone()).city(addCustomerDto.getCity()).street(addCustomerDto.getStreet())
                .state(addCustomerDto.getState()).email(addCustomerDto.getEmail())
                .address(addCustomerDto.getAddress()).build();
      return customer;
    }

}

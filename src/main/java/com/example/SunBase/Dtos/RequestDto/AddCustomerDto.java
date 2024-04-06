package com.example.SunBase.Dtos.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCustomerDto {


  private String customerId;
   private String email;
   private String firstName;
   private String lastName;
   private String address;
   private String street;
   private String city;
   private String state;
   private String  phone;


}

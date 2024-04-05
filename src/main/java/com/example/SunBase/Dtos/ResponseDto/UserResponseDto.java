package com.example.SunBase.Dtos.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {


    String customerId;
    String firstName;
    String lastName;
    String street;
    String address;
    String City;
    String state;
    String email;
    String phone;
}

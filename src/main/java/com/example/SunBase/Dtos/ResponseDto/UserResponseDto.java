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

    String id;
    String firstName;
    String lastName;
    String address;
    String City;
    String state;
    String email;
    String phone;
    String message;
    String street;
    String zipcode;
}

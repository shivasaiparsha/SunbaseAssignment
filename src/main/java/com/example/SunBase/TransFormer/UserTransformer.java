package com.example.SunBase.TransFormer;

import com.example.SunBase.Dtos.RequestDto.AddCustomerDto;
import com.example.SunBase.Dtos.ResponseDto.UserResponseDto;
import com.example.SunBase.Models.Customer;
import lombok.Builder;

@Builder
public class UserTransformer {
    public static Customer userFromUserRequestDto(AddCustomerDto userRequestDto){
        return Customer.
                builder()
                .email(userRequestDto.getEmail())
                .phone(userRequestDto.getPhone())
                .state(userRequestDto.getState())
                .city(userRequestDto.getCity())
                .address(userRequestDto.getAddress())
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())

                .build();

    }

    public static UserResponseDto userResponceDtoFromUser(Customer user){
        return UserResponseDto.builder()
                .City(user.getCity())
                .address(user.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())
                .state(user.getState())
                .street(user.getStreet())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .customerId(user.getCustomerId())
                .build();
    }
//
//    public static User userFromUserDto(User user,UserRequestDto dto){
//        user.setAddress(dto.getAddress());
//        user.setCity(dto.getCity());
//        user.setEmail(dto.getEmail());
//        user.setPhone(dto.getPhone());
//        user.setStreet(user.getStreet());
//        user.setFirstName(dto.getFirstName());
//        user.setLastName(dto.getLastName());
//        user.setState(dto.getState());
//        return user;
//
//    }
}

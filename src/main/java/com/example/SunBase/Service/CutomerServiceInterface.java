package com.example.SunBase.Service;

import com.example.SunBase.Dtos.RequestDto.AddCustomerDto;
import com.example.SunBase.Dtos.RequestDto.DeleteByIdDto;
import com.example.SunBase.Dtos.RequestDto.GetCustomerByIdDto;
import com.example.SunBase.Models.Customer;

import java.util.List;

public interface CutomerServiceInterface {
    Customer Update(AddCustomerDto addCustomerDto) throws Exception;
    Customer addUserToDb(AddCustomerDto addCustomerDto) throws Exception;

    List<Customer> getAllCustomerPresentInDb() throws Exception;

    Customer  getCustomerById(String customerId) throws Exception;

    String  deleteCustomerById(String customerId) throws Exception;

    List getUsersBy(String search, String value);
}

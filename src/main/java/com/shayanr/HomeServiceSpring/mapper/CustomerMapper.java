package com.shayanr.HomeServiceSpring.mapper;

import com.shayanr.HomeServiceSpring.dto.CustomerRequestDto;
import com.shayanr.HomeServiceSpring.dto.CustomerResponseDto;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer requestDtoToModel(CustomerRequestDto customerRequestDto);
    CustomerResponseDto modelToResponse(Customer customer);

    List<CustomerResponseDto> listModelToResponse(List<Customer> customers);

}

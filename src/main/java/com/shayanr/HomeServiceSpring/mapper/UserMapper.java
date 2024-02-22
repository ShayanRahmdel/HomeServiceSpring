package com.shayanr.HomeServiceSpring.mapper;

import com.shayanr.HomeServiceSpring.dto.CustomerResponseDto;
import com.shayanr.HomeServiceSpring.dto.ExpertResponseDto;
import com.shayanr.HomeServiceSpring.dto.UserDto;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;


public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserDto> userToExpertResponse(List<User> user);


}

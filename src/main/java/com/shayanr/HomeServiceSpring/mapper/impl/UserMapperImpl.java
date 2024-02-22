package com.shayanr.HomeServiceSpring.mapper.impl;

import com.shayanr.HomeServiceSpring.dto.UserDto;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.entity.users.User;
import com.shayanr.HomeServiceSpring.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public List<UserDto> userToExpertResponse(List<User> users) {
        if (users == null) {
            return null;
        }
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDto.setSignUpDate(user.getSignUpDate());
            userDto.setSignUpTime(user.getSignUpTime());

            if (user instanceof Expert expert) {
                userDto.setOverallScore(expert.getOverallScore());
                Set<SubDuty> subDuties = expert.getSubDuties();
                for (SubDuty subDuty : subDuties) {
                    if (subDuty.getTitle() != null) {
                        userDto.setSubDutyTitle(subDuty.getTitle());
                    }
                }
            }

            userDtos.add(userDto);
        }

        return userDtos;
    }



}

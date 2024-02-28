package com.shayanr.HomeServiceSpring.mapper.impl;

import com.shayanr.HomeServiceSpring.dto.ExpertRequestDto;
import com.shayanr.HomeServiceSpring.dto.ExpertResponseCustomDto;
import com.shayanr.HomeServiceSpring.dto.ExpertResponseDto;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.mapper.ExpertMapperCustom;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ExpertMapperCustomImpl implements ExpertMapperCustom {

    @Override
    public Expert requestDtoToModel(ExpertRequestDto requestDto) throws IOException {
        if (requestDto == null) {
            return null;
        }
        Expert expert = new Expert();

        expert.setFirstName(requestDto.getFirstName());
        expert.setLastName(requestDto.getLastName());
        expert.setEmail(requestDto.getEmail());
        expert.setPassword(requestDto.getPassword());
        expert.setSignUpDate(requestDto.getSignUpDate());
        expert.setSignUpTime(requestDto.getSignUpTime());
        expert.setImage(requestDto.getImage().getBytes());

        return expert;
    }

    @Override
    public ExpertResponseDto modelToResponse(Expert expert) {
        if (expert == null) {
            return null;
        }

        ExpertResponseDto expertResponseDto = new ExpertResponseDto();

        expertResponseDto.setFirstName(expert.getFirstName());
        expertResponseDto.setLastName(expert.getLastName());
        expertResponseDto.setEmail(expert.getEmail());
        expertResponseDto.setOverallScore(expert.getOverallScore());
        Set<SubDuty> subDuties = expert.getSubDuties();
        if (subDuties!=null){
            for (SubDuty sub:subDuties) {
                expertResponseDto.setSubDutyTitle(sub.getTitle());
            }
        }


        return expertResponseDto;
    }

    @Override
    public List<ExpertResponseDto> listModelToResponse(List<Expert> experts) {
        if (experts == null) {
            return null;
        }

        List<ExpertResponseDto> list = new ArrayList<>(experts.size());
        for (Expert expert : experts) {
            list.add(modelToResponse(expert));
        }

        return list;
    }

    @Override
    public ExpertResponseCustomDto modelToResponseCustom(Expert expert) {
        if (expert == null) {
            return null;
        }
        ExpertResponseCustomDto dto = new ExpertResponseCustomDto();
        dto.setFirstName(expert.getFirstName());
        dto.setLastName(expert.getLastName());
        dto.setEmail(expert.getEmail());
        dto.setOverallScore(expert.getOverallScore());

        return dto;
    }

    @Override
    public List<ExpertResponseCustomDto> listModelToResponseCustom(List<Expert> experts) {
        if (experts == null) {
            return null;
        }

        List<ExpertResponseCustomDto> list = new ArrayList<>(experts.size());
        for (Expert expert : experts) {
            list.add(modelToResponseCustom(expert));
        }

        return list;
    }
}

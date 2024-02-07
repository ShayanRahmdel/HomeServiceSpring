package com.shayanr.HomeServiceSpring.mapper;

import com.shayanr.HomeServiceSpring.dto.ExpertRequestDto;
import com.shayanr.HomeServiceSpring.dto.ExpertResponseDto;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpertMapper {


    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);

    Expert requestDtoToModel(ExpertRequestDto requestDto);

    ExpertResponseDto modelToResponse(Expert expert);
    @Mapping(source = "expert.subDuties",target = "sub-duty")
    List<ExpertResponseDto> listModelToResponse(List<Expert> experts);
}

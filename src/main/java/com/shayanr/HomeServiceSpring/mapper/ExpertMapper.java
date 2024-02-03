package com.shayanr.HomeServiceSpring.mapper;

import com.shayanr.HomeServiceSpring.dto.ExpertRequestDto;
import com.shayanr.HomeServiceSpring.dto.ExpertResponseDto;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import org.mapstruct.factory.Mappers;

public interface ExpertMapper {


    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);

    Expert requestDtoToModel(ExpertRequestDto requestDto);

    ExpertResponseDto modelToResponse(Expert expert);
}

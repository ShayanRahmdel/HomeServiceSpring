package com.shayanr.HomeServiceSpring.mapper;

import com.shayanr.HomeServiceSpring.dto.ExpertRequestDto;
import com.shayanr.HomeServiceSpring.dto.ExpertResponseCustomDto;
import com.shayanr.HomeServiceSpring.dto.ExpertResponseDto;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

public interface ExperMapperCustom {



    Expert requestDtoToModel(ExpertRequestDto requestDto) throws IOException;

    ExpertResponseDto modelToResponse(Expert expert);
    List<ExpertResponseDto> listModelToResponse(List<Expert> experts);

    ExpertResponseCustomDto modelToResponseCustom(Expert expert);

}

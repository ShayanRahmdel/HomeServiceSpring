package com.shayanr.HomeServiceSpring.mapper;

import com.shayanr.HomeServiceSpring.dto.SuggestionResponseDto;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SuggestionMapper {
    SuggestionMapper INSTANCE = Mappers.getMapper(SuggestionMapper.class);


    SuggestionResponseDto modelToResponse(WorkSuggestion workSuggestion);

    List<SuggestionResponseDto> listModeltoResponse(List<WorkSuggestion> workSuggestions);
}

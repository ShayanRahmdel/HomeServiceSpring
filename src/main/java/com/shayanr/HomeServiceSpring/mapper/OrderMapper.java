package com.shayanr.HomeServiceSpring.mapper;

import com.shayanr.HomeServiceSpring.dto.OrderResponseDto;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(source = "customerOrder.address", target = "address")
    List<OrderResponseDto> listModelToResponse(List<CustomerOrder> customerOrder);

    OrderResponseDto modelToResponse(CustomerOrder customerOrder);
}

package com.company.shipmentsprofit.mapper;

import com.company.shipmentsprofit.dto.ProfitCalculationDto;
import com.company.shipmentsprofit.entity.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfitMapper {

    ProfitMapper INSTANCE = Mappers.getMapper(ProfitMapper.class);

    @Mapping(target = "shipmentId", source = "id")
    @Mapping(target = "totalIncome", ignore = true) // We'll set them manually
    @Mapping(target = "totalCost", ignore = true)
    @Mapping(target = "profit", ignore = true)
    ProfitCalculationDto shipmentToProfitDto(Shipment shipment);
}

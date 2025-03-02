package com.company.shipmentsprofit.mapper;

import com.company.shipmentsprofit.dto.request.AddCostRequest;
import com.company.shipmentsprofit.dto.request.AddIncomeRequest;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", expression = "java(request.getCostType().name())")
    Cost toCost(AddCostRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", expression = "java(request.getIncomeType().name())")
    Income toIncome(AddIncomeRequest request);
}


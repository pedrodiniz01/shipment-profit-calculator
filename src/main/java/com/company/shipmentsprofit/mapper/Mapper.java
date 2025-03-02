package com.company.shipmentsprofit.mapper;

import com.company.shipmentsprofit.dto.request.AddCostRequest;
import com.company.shipmentsprofit.dto.request.AddIncomeRequest;
import com.company.shipmentsprofit.dto.response.ShipmentSummaryResponse;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.utils.CostUtils;
import com.company.shipmentsprofit.utils.IncomeUtils;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", expression = "java(request.getCostType().name())")
    Cost toCost(AddCostRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", expression = "java(request.getIncomeType().name())")
    Income toIncome(AddIncomeRequest request);

    @Mapping(target = "totalIncome", source = "shipment", qualifiedByName = "calculateTotalIncome")
    @Mapping(target = "totalCost", source = "shipment", qualifiedByName = "calculateTotalCost")
    ShipmentSummaryResponse toShipmentSummaryResponse(Shipment shipment);

    @Named("calculateTotalIncome")
    static Double calculateTotalIncome(Shipment shipment) {
        return IncomeUtils.calculateTotalIncome(shipment);
    }

    @Named("calculateTotalCost")
    static Double calculateTotalCost(Shipment shipment) {
        return CostUtils.calculateTotalCost(shipment);
    }
}


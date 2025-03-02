package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.enums.CostType;

import java.util.Map;
import java.util.stream.Collectors;

public class CostUtils {
    public static void addCost(Shipment shipment, Cost cost) {
        TransactionUtils.validateShipmentAndTransaction(shipment, cost, cost.getAmount());
        shipment.getCosts().add(cost);
        cost.setShipment(shipment);
    }
    public static double calculateTotalCost(Shipment shipment) {
        return shipment.getCosts()
                .stream()
                .mapToDouble(cost -> cost.getAmount() != null ? cost.getAmount() : 0.0)
                .sum();
    }
    public static Map<CostType, Double> calculateCostsByCategory(Shipment shipment) {
        return shipment.getCosts()
                .stream()
                .collect(Collectors.groupingBy(
                        cost -> CostType.fromString(cost.getDescription()),
                        Collectors.summingDouble(cost -> cost.getAmount() != null ? cost.getAmount() : 0.0)
                ));
    }
}

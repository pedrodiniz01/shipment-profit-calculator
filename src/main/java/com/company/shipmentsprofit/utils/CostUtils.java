package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Shipment;

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
}

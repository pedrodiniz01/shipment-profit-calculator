package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Shipment;

public class NetAmountUtils {
    public static void updateNetAmount(Shipment shipment) {
        double totalIncome = IncomeUtils.calculateTotalIncome(shipment);
        double totalCost = CostUtils.calculateTotalCost(shipment);

        double netAmount = totalIncome - totalCost;
        boolean isProfit = netAmount > 0;

        shipment.setNetAmount(netAmount);
        shipment.setIsProfit(isProfit);
    }
}

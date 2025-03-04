package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Shipment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class CostUtilsTest {

    private Shipment shipment;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        shipment = objectMapper.readValue(new File("src/test/java/com/company/shipmentsprofit/test-data/shipment.json"), Shipment.class);
    }

    @Nested
    class AddCost {

        @Test
        void success() {
            // Given
            Cost newCost = new Cost();
            newCost.setDescription("LABOR");
            newCost.setAmount(400.0);

            // When
            CostUtils.validateAndAddCost(shipment, newCost);

            // Then
            Assertions.assertNotNull(shipment.getCosts());
            Assertions.assertEquals(6, shipment.getCosts().size());
            Assertions.assertTrue(shipment.getCosts().contains(newCost));
            Assertions.assertEquals(shipment, newCost.getShipment());
        }
    }
}
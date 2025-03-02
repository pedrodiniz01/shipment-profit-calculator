package com.company.shipmentsprofit.service;

import com.company.shipmentsprofit.dto.response.ShipmentFinancialSummaryResponse;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.enums.CostType;
import com.company.shipmentsprofit.enums.IncomeType;
import com.company.shipmentsprofit.repository.ShipmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {
    @Mock
    private ShipmentRepository repository;

    @InjectMocks
    private ShipmentService service;

    private Shipment shipment;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        shipment = objectMapper.readValue(new File("src/test/java/com/company/shipmentsprofit/test-data/shipment.json"), Shipment.class);
    }

    @Nested
    class GetShipmentFinancialSummary {
        @Test
        void success() throws IOException {
            // Given
            ShipmentFinancialSummaryResponse expectedResponse = objectMapper.readValue(new File("src/test/java/com/company/shipmentsprofit/test-data/shipmentFinancialSummaryResponse.json"), ShipmentFinancialSummaryResponse.class);

            // When
            Mockito.when(repository.findByReferenceNumber(Mockito.anyString())).thenReturn(Optional.of(shipment));

            // Act
            ShipmentFinancialSummaryResponse actualResponse = service.getShipmentFinancialSummary(Mockito.anyString());

            // Assert
            Assertions.assertEquals(expectedResponse.getReferenceNumber(), actualResponse.getReferenceNumber());
            Assertions.assertEquals(expectedResponse.getShipmentDate(), actualResponse.getShipmentDate());
            Assertions.assertEquals(expectedResponse.getCostByCategory().get(CostType.FUEL), actualResponse.getCostByCategory().get(CostType.FUEL));
            Assertions.assertEquals(expectedResponse.getCostByCategory().get(CostType.LABOR), actualResponse.getCostByCategory().get(CostType.LABOR));
            Assertions.assertEquals(expectedResponse.getCostByCategory().get(CostType.TAXES), actualResponse.getCostByCategory().get(CostType.TAXES));
            Assertions.assertEquals(expectedResponse.getIncomeByCategory().get(IncomeType.CUSTOMER_PAYMENT), actualResponse.getIncomeByCategory().get(IncomeType.CUSTOMER_PAYMENT));
            Assertions.assertEquals(expectedResponse.getIncomeByCategory().get(IncomeType.AGENT_COMMISSION), actualResponse.getIncomeByCategory().get(IncomeType.AGENT_COMMISSION));

            // Verify
            Mockito.verify(repository, Mockito.times(1)).findByReferenceNumber(Mockito.anyString());
        }
    }
}
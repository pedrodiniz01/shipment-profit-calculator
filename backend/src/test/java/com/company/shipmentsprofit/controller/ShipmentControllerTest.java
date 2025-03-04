package com.company.shipmentsprofit.controller;

import com.company.shipmentsprofit.dto.request.CreateShipmentRequest;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.exception.DuplicatedReferenceNumberException;
import com.company.shipmentsprofit.service.ShipmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class ShipmentControllerTest {

    @Mock
    private ShipmentService shipmentService;
    @InjectMocks
    private ShipmentController shipmentController;

    @Nested
    class CreateShipment {
        @Test
        void givenValidInput_thenCreatedWithSuccess() {
            // Given
            CreateShipmentRequest request = new CreateShipmentRequest("SHIP-1001", LocalDate.of(2025, 3, 2));
            Shipment expectedShipment = Shipment.builder()
                    .referenceNumber("SHIP-1001")
                    .shipmentDate(java.time.LocalDate.parse("2025-03-02"))
                    .build();

            // When
            Mockito.when(shipmentService.createShipment("SHIP-1001", java.time.LocalDate.parse("2025-03-02")))
                    .thenReturn(expectedShipment);

            // Then
            Shipment actualResponse = shipmentController.createShipment(request).getBody();

            // Assert
            Assertions.assertEquals(expectedShipment, actualResponse);

            // Verify
            Mockito.verify(shipmentService, Mockito.times(1)).createShipment("SHIP-1001", java.time.LocalDate.parse("2025-03-02"));
        }
        @Test
        void givenReferenceNumberDuplicated_thenThrowException() {
            // Given
            CreateShipmentRequest request = new CreateShipmentRequest("SHIP-1001", LocalDate.of(2025, 3, 2));

            // When
            Mockito.when(shipmentService.createShipment("SHIP-1001", LocalDate.of(2025, 3, 2)))
                    .thenThrow(new DuplicatedReferenceNumberException("Shipment with reference number SHIP-1001 already exists."));

            // Then
            DuplicatedReferenceNumberException thrownException = Assertions.assertThrows(
                    DuplicatedReferenceNumberException.class,
                    () -> shipmentController.createShipment(request)
            );

            // Assert
            Assertions.assertEquals("Shipment with reference number SHIP-1001 already exists.", thrownException.getMessage());

            // Verify
            Mockito.verify(shipmentService, Mockito.times(1)).createShipment("SHIP-1001", LocalDate.of(2025, 3, 2));
        }
    }
}
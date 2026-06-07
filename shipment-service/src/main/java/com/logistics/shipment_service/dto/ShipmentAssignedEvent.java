package com.logistics.shipment_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentAssignedEvent {
    private String shipmentId;
    private String courierId;
    private LocalDateTime assignedAt;
}

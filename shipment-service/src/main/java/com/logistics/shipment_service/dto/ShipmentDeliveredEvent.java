package com.logistics.shipment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDeliveredEvent {
    private String shipmentId;
    private String receiverId;
    private LocalDateTime deliveredAt;
}

package com.logistics.shipment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRequest {
    private String senderId;
    private String receiverId;
    private String originAddress;
    private String destinationAddress;
}

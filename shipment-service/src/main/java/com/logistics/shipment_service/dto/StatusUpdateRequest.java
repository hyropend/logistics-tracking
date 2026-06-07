package com.logistics.shipment_service.dto;

import com.logistics.shipment_service.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateRequest {
    private ShipmentStatus status;
}

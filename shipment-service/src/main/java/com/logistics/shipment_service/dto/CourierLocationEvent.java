package com.logistics.shipment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierLocationEvent {
    private String courierId;
    private Double latitude;
    private Double longitude;
    private String status;
    private LocalDateTime timestamp;
}
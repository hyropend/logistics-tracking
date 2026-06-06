package com.logistics.courier_service.dto;

import com.logistics.courier_service.enums.CourierStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierLocationEvent {
    private String courierId;
    private Double latitude;
    private Double longitude;
    private CourierStatus  status;
    private LocalDateTime timestamp;
}

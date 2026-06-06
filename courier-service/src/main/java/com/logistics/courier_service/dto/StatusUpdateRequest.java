package com.logistics.courier_service.dto;

import com.logistics.courier_service.enums.CourierStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateRequest {
    private CourierStatus status;
}

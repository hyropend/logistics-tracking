package com.logistics.shipment_service.enums;

public enum ShipmentStatus {
    PENDING,       // kurye atanmadı
    ASSIGNED,      // kurye atandı
    IN_TRANSIT,    // yolda
    DELIVERED,     // teslim edildi
    CANCELLED      // iptal
}
package com.logistics.shipment_service.entity;

import com.logistics.shipment_service.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String senderId;      // gönderen müşteri
    private String receiverId;    // alıcı müşteri
    private String courierId;     // atanan kurye

    private String originAddress;      // çıkış adresi
    private String destinationAddress; // varış adresi

    private Double courierLatitude;   // kuryenin anlık konumu
    private Double courierLongitude;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
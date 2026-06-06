package com.logistics.courier_service.entity;

import com.logistics.courier_service.enums.CourierStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Double latitude;
    private Double longitude;
    @Enumerated(EnumType.STRING)
    private CourierStatus status; // AVAILABLE, ON_DELIVERY, OFFLINE
    private LocalDateTime updatedAt;
}
package com.logistics.shipment_service.repository;

import com.logistics.shipment_service.entity.Shipment;
import com.logistics.shipment_service.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {
    List<Shipment> findByCourierId(String courierId);
    List<Shipment> findByStatus(ShipmentStatus status);
    List<Shipment> findBySenderId(String senderId);
}

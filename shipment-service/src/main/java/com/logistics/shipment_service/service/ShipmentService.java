package com.logistics.shipment_service.service;

import com.logistics.shipment_service.dto.ShipmentAssignedEvent;
import com.logistics.shipment_service.dto.ShipmentDeliveredEvent;
import com.logistics.shipment_service.dto.ShipmentRequest;
import com.logistics.shipment_service.entity.Shipment;
import com.logistics.shipment_service.enums.ShipmentStatus;
import com.logistics.shipment_service.kafka.ShipmentEventProducer;
import com.logistics.shipment_service.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ShipmentEventProducer producer;

    // Kargo oluştur
    public Shipment createShipment(ShipmentRequest request) {
        Shipment shipment = new Shipment();
        shipment.setSenderId(request.getSenderId());
        shipment.setReceiverId(request.getReceiverId());
        shipment.setOriginAddress(request.getOriginAddress());
        shipment.setDestinationAddress(request.getDestinationAddress());
        shipment.setStatus(ShipmentStatus.PENDING);
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setUpdatedAt(LocalDateTime.now());
        return shipmentRepository.save(shipment);
    }
    // Kargoya kurye ata yani Kafkaya event bas
    public void assignCourier(String shipmentId, String courierId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + shipmentId));

        shipment.setCourierId(courierId);
        shipment.setStatus(ShipmentStatus.ASSIGNED);
        shipment.setUpdatedAt(LocalDateTime.now());
        shipmentRepository.save(shipment);

        producer.publishShipmentAssigned(new ShipmentAssignedEvent(
                shipmentId, courierId, LocalDateTime.now()
        ));

        log.info("Courier {} assigned to shipment {}", courierId, shipmentId);
    }

    // Kargo durumunu güncelle
    public void updateStatus(String shipmentId, ShipmentStatus status) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + shipmentId));

        shipment.setStatus(status);
        shipment.setUpdatedAt(LocalDateTime.now());
        shipmentRepository.save(shipment);

        // Kargo teslim edildiyse Kafka'ya event bas
        if (status == ShipmentStatus.DELIVERED) {
            producer.publishShipmentDelivered(new ShipmentDeliveredEvent(
                    shipmentId, shipment.getReceiverId(), LocalDateTime.now()
            ));
            log.info("Shipment {} delivered, notification event published", shipmentId);
        }

        log.info("Shipment {} status updated to {}", shipmentId, status);
    }

    // Tüm kargoları getir
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    // ID ile kargo getir
    public Shipment getShipmentById(String id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + id));
    }

    // Kurye'nin kargolarını getir
    public List<Shipment> getShipmentsByCourier(String courierId) {
        return shipmentRepository.findByCourierId(courierId);
    }
}

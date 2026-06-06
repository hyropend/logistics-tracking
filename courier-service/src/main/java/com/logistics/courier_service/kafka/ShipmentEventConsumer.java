package com.logistics.courier_service.kafka;

import com.logistics.courier_service.dto.ShipmentAssignedEvent;
import com.logistics.courier_service.entity.Courier;
import com.logistics.courier_service.enums.CourierStatus;
import com.logistics.courier_service.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentEventConsumer {

    private final CourierRepository courierRepository;

    @KafkaListener(topics = "shipment.assigned", groupId = "courier-service-group")
    public void onShipmentAssigned(ShipmentAssignedEvent event) {
        log.info("Shipment assigned event received: shipmentId={}, courierId={}",
                event.getShipmentId(), event.getCourierId());

        Courier courier = courierRepository.findById(event.getCourierId())
                .orElseThrow(() -> new RuntimeException("Courier not found: " + event.getCourierId()));

        courier.setStatus(CourierStatus.ON_DELIVERY);
        courier.setUpdatedAt(LocalDateTime.now());
        courierRepository.save(courier);

        log.info("Courier {} status updated to ON_DELIVERY", event.getCourierId());
    }
}
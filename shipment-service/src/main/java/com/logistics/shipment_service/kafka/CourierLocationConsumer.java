package com.logistics.shipment_service.kafka;

import com.logistics.shipment_service.dto.CourierLocationEvent;
import com.logistics.shipment_service.dto.ShipmentAssignedEvent;
import com.logistics.shipment_service.entity.Shipment;
import com.logistics.shipment_service.enums.ShipmentStatus;
import com.logistics.shipment_service.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierLocationConsumer {
    private final ShipmentRepository shipmentRepository;

    @KafkaListener(topics = "courier.location.updated", groupId = "shipment-service-group")
    public void onCourierLocationUpdate(CourierLocationEvent event){
        log.info("Received CourierLocationEvent: courierId={}", event.getCourierId());

        List<Shipment> shipments = shipmentRepository.findByCourierId(event.getCourierId());
        shipments.stream()
                .filter(s -> s.getStatus() == ShipmentStatus.IN_TRANSIT)
                .forEach(s -> {
                    s.setCourierLatitude(event.getLatitude());
                    s.setCourierLongitude(event.getLongitude());
                    s.setUpdatedAt(LocalDateTime.now());
                    shipmentRepository.save(s);
                    log.info("Shipment {} location updated", s.getId());
                });

    }
}

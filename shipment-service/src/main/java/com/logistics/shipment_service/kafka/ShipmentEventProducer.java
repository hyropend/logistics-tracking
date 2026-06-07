package com.logistics.shipment_service.kafka;

import com.logistics.shipment_service.dto.ShipmentAssignedEvent;
import com.logistics.shipment_service.dto.ShipmentDeliveredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishShipmentAssigned(ShipmentAssignedEvent event){
        kafkaTemplate.send("shipment.assigned", event.getShipmentId(), event);
    }

    public void publishShipmentDelivered(ShipmentDeliveredEvent event) {
        kafkaTemplate.send("shipment.delivered", event.getShipmentId(), event);
    }
}

package com.logistics.notification_service.kafka;

import com.logistics.notification_service.dto.ShipmentDeliveredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    @KafkaListener(topics = "shipment.delivered", groupId = "notification-service-group")
    public void onShipmentDelivered(ShipmentDeliveredEvent event) {
        log.info("Notification received: shipmentId={}, receiverId={}",
                event.getShipmentId(), event.getReceiverId());

        // Şimdilik log basıyoruz, gerçekte email/SMS gönderilir .d
        log.info("Sending notification to receiver {} - Your shipment {} has been delivered!",
                event.getReceiverId(), event.getShipmentId());
    }
}
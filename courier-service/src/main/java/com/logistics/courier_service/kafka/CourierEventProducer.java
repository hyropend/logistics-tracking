package com.logistics.courier_service.kafka;

import com.logistics.courier_service.dto.CourierLocationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierEventProducer {
    private final KafkaTemplate<String, CourierLocationEvent> kafkaTemplate;

    public void publishCourierLocationEvent(CourierLocationEvent event) {
        kafkaTemplate.send("courier.location.updated", event.getCourierId(), event);
    }
}

package com.logistics.courier_service.controller;

import com.logistics.courier_service.dto.CourierLocationEvent;
import com.logistics.courier_service.dto.LocationUpdateRequest;
import com.logistics.courier_service.dto.StatusUpdateRequest;
import com.logistics.courier_service.entity.Courier;
import com.logistics.courier_service.kafka.CourierEventProducer;
import com.logistics.courier_service.repository.CourierRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierRepository courierRepository;
    private final CourierEventProducer producer;

    // Tüm kuryeleri listele
    @GetMapping
    public ResponseEntity<List<Courier>> getAllCouriers() {
        return ResponseEntity.ok(courierRepository.findAll());
    }

    // ID ile tek kurye getir
    @GetMapping("/{id}")
    public ResponseEntity<Courier> getCourierById(@PathVariable String id) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("courier not found" + id));
        return ResponseEntity.ok(courier);
    }

    public ResponseEntity<List<Courier>> getCouriersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(courierRepository.findByStatus(status));
    }

    // Yeni kurye oluştur
    @PostMapping
    public ResponseEntity<Courier> createCourier(@RequestBody Courier courier) {
        courier.setUpdatedAt(LocalDateTime.now());
        Courier saved = courierRepository.save(courier);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Kurye konumunu güncelle → Kafka'ya event bas
    @PutMapping("/{id}/location")
    public ResponseEntity<Void> updateLocation(
            @PathVariable String id,
            @RequestBody LocationUpdateRequest request) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courier not found: " + id));
        courier.setLatitude(request.getLatitude());
        courier.setLongitude(request.getLongitude());
        courier.setUpdatedAt(LocalDateTime.now());
        courierRepository.save(courier);
        producer.publishCourierLocationEvent(new CourierLocationEvent(
                id, request.getLatitude(), request.getLongitude(),
                courier.getStatus(), LocalDateTime.now()
        ));
        return ResponseEntity.ok().build();
    }

    // Kurye statüsünü güncelle → AVAILABLE, ON_DELIVERY, OFFLINE
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String id,
            @RequestBody StatusUpdateRequest request) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courier not found: " + id));
        courier.setStatus(request.getStatus());
        courier.setUpdatedAt(LocalDateTime.now());
        courierRepository.save(courier);
        return ResponseEntity.ok().build();
    }

    // Kurye sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourier(@PathVariable String id) {
        if (!courierRepository.existsById(id)) {
            throw new RuntimeException("Courier not found: " + id);
        }
        courierRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

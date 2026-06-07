package com.logistics.shipment_service.controller;

import com.logistics.shipment_service.dto.ShipmentRequest;
import com.logistics.shipment_service.dto.StatusUpdateRequest;
import com.logistics.shipment_service.entity.Shipment;
import com.logistics.shipment_service.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;
    // Kargo oluştur
    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentRequest request) {
        Shipment saved = shipmentService.createShipment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Tüm kargoları listele
    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    // ID ile kargo getir
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable String id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    // Kurye'nin kargolarını getir
    @GetMapping("/courier/{courierId}")
    public ResponseEntity<List<Shipment>> getShipmentsByCourier(@PathVariable String courierId) {
        return ResponseEntity.ok(shipmentService.getShipmentsByCourier(courierId));
    }

    // Kargoya kurye ata yani Kafkaya shipment.assigned basılır
    @PutMapping("/{id}/assign/{courierId}")
    public ResponseEntity<Void> assignCourier(
            @PathVariable String id,
            @PathVariable String courierId) {
        shipmentService.assignCourier(id, courierId);
        return ResponseEntity.ok().build();
    }

    // Kargo durumunu güncelle
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String id,
            @RequestBody StatusUpdateRequest request) {
        shipmentService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok().build();
    }
}

package com.logistics.courier_service.repository;

import com.logistics.courier_service.entity.Courier;
import com.logistics.courier_service.enums.CourierStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierRepository extends JpaRepository<Courier, String> {
    List<Courier> findByStatus(CourierStatus status);
}
package com.logistics.courier_service.repository;

import com.logistics.courier_service.entity.Courier;
import org.jspecify.annotations.Nullable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierRepository extends CrudRepository<Courier, String> {
    @Override
    List<Courier> findAll();

    @Nullable List<Courier> findByStatus(String status);
}

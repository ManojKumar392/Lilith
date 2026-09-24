package com.lilith.repository;

import com.lilith.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {

    Optional<Area> findByGridRowAndGridColumn(
            Integer gridRow,
            Integer gridColumn
    );
}
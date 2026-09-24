package com.lilith.repository;

import com.lilith.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByAreaId(Long areaId);

    List<Report> findByUserId(Long userId);
}
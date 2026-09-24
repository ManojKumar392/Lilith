package com.lilith.service;

import com.lilith.dto.AreaResponse;
import com.lilith.entity.Area;
import com.lilith.entity.Report;
import com.lilith.entity.SeverityLevel;
import com.lilith.repository.AreaRepository;
import com.lilith.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    private final AreaRepository areaRepository;
    private final ReportRepository reportRepository;

    public AreaService(
            AreaRepository areaRepository,
            ReportRepository reportRepository) {

        this.areaRepository = areaRepository;
        this.reportRepository = reportRepository;
    }

    public List<AreaResponse> getAllAreas() {

        return areaRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public AreaResponse getAreaById(Long id) {

        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found"));

        return convertToResponse(area);
    }

    private AreaResponse convertToResponse(Area area) {

        List<Report> reports =
                reportRepository.findByAreaId(area.getId());

        int score = reports.stream()
                .mapToInt(report -> report.getSeverity().getScore())
                .sum();

        SeverityLevel severityLevel = calculateSeverityLevel(score);

        return new AreaResponse(
                area.getId(),
                area.getGridRow(),
                area.getGridColumn(),
                area.getMinLatitude(),
                area.getMaxLatitude(),
                area.getMinLongitude(),
                area.getMaxLongitude(),
                reports.size(),
                score,
                severityLevel
        );
    }

    private SeverityLevel calculateSeverityLevel(int score) {

        if (score <= 3) {
            return SeverityLevel.YELLOW;
        }

        if (score <= 7) {
            return SeverityLevel.ORANGE;
        }

        return SeverityLevel.RED;
    }
}
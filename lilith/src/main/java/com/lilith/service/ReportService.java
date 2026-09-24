package com.lilith.service;

import com.lilith.entity.Area;
import com.lilith.entity.Report;
import com.lilith.entity.User;
import com.lilith.repository.AreaRepository;
import com.lilith.repository.ReportRepository;
import com.lilith.repository.UserRepository;
import com.lilith.dto.ReportRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;

    // Approximately 500m
    private static final double GRID_SIZE = 0.0025;

    public ReportService(
            ReportRepository reportRepository,
            AreaRepository areaRepository,
            UserRepository userRepository) {

        this.reportRepository = reportRepository;
        this.areaRepository = areaRepository;
        this.userRepository = userRepository;
    }

    private Area createArea(int gridRow, int gridColumn) {

        Area area = new Area();

        area.setGridRow(gridRow);
        area.setGridColumn(gridColumn);

        area.setMinLatitude(gridRow * GRID_SIZE);
        area.setMaxLatitude((gridRow + 1) * GRID_SIZE);

        area.setMinLongitude(gridColumn * GRID_SIZE);
        area.setMaxLongitude((gridColumn + 1) * GRID_SIZE);

        return areaRepository.save(area);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report createReport(ReportRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        int gridRow = (int) Math.floor(request.getLatitude() / GRID_SIZE);
        int gridColumn = (int) Math.floor(request.getLongitude() / GRID_SIZE);

        Area area = areaRepository
                .findByGridRowAndGridColumn(gridRow, gridColumn)
                .orElseGet(() -> createArea(gridRow, gridColumn));

        Report report = new Report();

        report.setUser(user);
        report.setArea(area);
        report.setLatitude(request.getLatitude());
        report.setLongitude(request.getLongitude());
        report.setReason(request.getReason());
        report.setSeverity(request.getSeverity());
        report.setDescription(request.getDescription());
        report.setIncidentTime(request.getIncidentTime());

        return reportRepository.save(report);
    }
}
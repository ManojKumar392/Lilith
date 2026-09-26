package com.lilith.service;

import com.lilith.dto.DashboardResponse;
import com.lilith.entity.Report;
import com.lilith.repository.AreaRepository;
import com.lilith.repository.ReportRepository;
import com.lilith.dto.RecentReportResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final ReportRepository reportRepository;
    private final AreaRepository areaRepository;

    public DashboardService(
            ReportRepository reportRepository,
            AreaRepository areaRepository) {

        this.reportRepository = reportRepository;
        this.areaRepository = areaRepository;
    }

    public DashboardResponse getDashboardData() {

        List<Report> reports =
                reportRepository.findAll();

        Map<String, Long> reportsBySeverity =
                new LinkedHashMap<>();

        reportsBySeverity.put("LOW", 0L);
        reportsBySeverity.put("MEDIUM", 0L);
        reportsBySeverity.put("HIGH", 0L);

        Map<String, Long> reportsByReason =
                new LinkedHashMap<>();

        reportsByReason.put("POOR_LIGHTING", 0L);
        reportsByReason.put("HARASSMENT", 0L);
        reportsByReason.put("THEFT", 0L);
        reportsByReason.put("ISOLATED_AREA", 0L);
        reportsByReason.put("OTHER", 0L);


        for (Report report : reports) {

            String severity =
                    report.getSeverity().name();

            reportsBySeverity.put(
                    severity,
                    reportsBySeverity.get(severity) + 1
            );


            String reason =
                    report.getReason().name();

            reportsByReason.put(
                    reason,
                    reportsByReason.get(reason) + 1
            );
        }

        return new DashboardResponse(
                reports.size(),
                areaRepository.count(),
                reportsBySeverity,
                reportsByReason
        );
    }

    public List<RecentReportResponse> getRecentReports() {

        return reportRepository
                .findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(report ->
                        new RecentReportResponse(
                                report.getId(),
                                report.getReason().name(),
                                report.getSeverity().name(),
                                report.getDescription(),
                                report.getCreatedAt()
                        )
                )
                .toList();
    }
}
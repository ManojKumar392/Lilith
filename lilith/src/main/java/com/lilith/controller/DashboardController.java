package com.lilith.controller;

import com.lilith.dto.DashboardResponse;
import com.lilith.dto.RecentReportResponse;
import com.lilith.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse>
    getDashboard() {

        return ResponseEntity.ok(
                dashboardService.getDashboardData()
        );
    }

    @GetMapping("/recent")
    public ResponseEntity<List<RecentReportResponse>>
    getRecentReports() {

        return ResponseEntity.ok(
                dashboardService.getRecentReports()
        );
    }
}
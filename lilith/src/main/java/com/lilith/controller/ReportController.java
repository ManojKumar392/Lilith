package com.lilith.controller;

import com.lilith.dto.ReportRequest;
import com.lilith.entity.Report;
import com.lilith.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Report> createReport(
            @Valid @RequestBody ReportRequest request) {

        return ResponseEntity.ok(
                reportService.createReport(request)
        );
    }
}
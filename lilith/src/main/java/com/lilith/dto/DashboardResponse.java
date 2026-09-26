package com.lilith.dto;

import java.util.Map;

public class DashboardResponse {

    private long totalReports;
    private long affectedAreas;

    private Map<String, Long> reportsBySeverity;
    private Map<String, Long> reportsByReason;

    public DashboardResponse(
            long totalReports,
            long affectedAreas,
            Map<String, Long> reportsBySeverity,
            Map<String, Long> reportsByReason) {

        this.totalReports = totalReports;
        this.affectedAreas = affectedAreas;
        this.reportsBySeverity = reportsBySeverity;
        this.reportsByReason = reportsByReason;
    }

    public long getTotalReports() {
        return totalReports;
    }

    public long getAffectedAreas() {
        return affectedAreas;
    }

    public Map<String, Long> getReportsBySeverity() {
        return reportsBySeverity;
    }

    public Map<String, Long> getReportsByReason() {
        return reportsByReason;
    }
}
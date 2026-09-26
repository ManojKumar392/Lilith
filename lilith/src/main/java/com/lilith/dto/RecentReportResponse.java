package com.lilith.dto;

import java.time.LocalDateTime;

public class RecentReportResponse {

    private Long id;
    private String reason;
    private String severity;
    private String description;
    private LocalDateTime createdAt;

    public RecentReportResponse(
            Long id,
            String reason,
            String severity,
            String description,
            LocalDateTime createdAt) {

        this.id = id;
        this.reason = reason;
        this.severity = severity;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public String getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
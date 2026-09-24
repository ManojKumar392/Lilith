package com.lilith.dto;

import com.lilith.entity.SeverityLevel;

public class AreaResponse {

    private Long id;

    private Integer gridRow;
    private Integer gridColumn;

    private Double minLatitude;
    private Double maxLatitude;
    private Double minLongitude;
    private Double maxLongitude;

    private Integer reportCount;
    private Integer severityScore;

    private SeverityLevel severityLevel;

    public AreaResponse() {
    }

    public AreaResponse(
            Long id,
            Integer gridRow,
            Integer gridColumn,
            Double minLatitude,
            Double maxLatitude,
            Double minLongitude,
            Double maxLongitude,
            Integer reportCount,
            Integer severityScore,
            SeverityLevel severityLevel) {

        this.id = id;
        this.gridRow = gridRow;
        this.gridColumn = gridColumn;
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
        this.reportCount = reportCount;
        this.severityScore = severityScore;
        this.severityLevel = severityLevel;
    }

    public Long getId() {
        return id;
    }

    public Integer getGridRow() {
        return gridRow;
    }

    public Integer getGridColumn() {
        return gridColumn;
    }

    public Double getMinLatitude() {
        return minLatitude;
    }

    public Double getMaxLatitude() {
        return maxLatitude;
    }

    public Double getMinLongitude() {
        return minLongitude;
    }

    public Double getMaxLongitude() {
        return maxLongitude;
    }

    public Integer getReportCount() {
        return reportCount;
    }

    public Integer getSeverityScore() {
        return severityScore;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }
}
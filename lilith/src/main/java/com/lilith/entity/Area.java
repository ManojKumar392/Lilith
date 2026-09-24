package com.lilith.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "areas",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_grid_position",
                        columnNames = {"grid_row", "grid_column"}
                )
        }
)
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_row", nullable = false)
    private Integer gridRow;

    @Column(name = "grid_column", nullable = false)
    private Integer gridColumn;

    @Column(name = "min_latitude", nullable = false)
    private Double minLatitude;

    @Column(name = "max_latitude", nullable = false)
    private Double maxLatitude;

    @Column(name = "min_longitude", nullable = false)
    private Double minLongitude;

    @Column(name = "max_longitude", nullable = false)
    private Double maxLongitude;

    @OneToMany(mappedBy = "area")
    @JsonIgnore
    private List<Report> reports = new ArrayList<>();

    public Area() {
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

    public List<Report> getReports() {
        return reports;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGridRow(Integer gridRow) {
        this.gridRow = gridRow;
    }

    public void setGridColumn(Integer gridColumn) {
        this.gridColumn = gridColumn;
    }

    public void setMinLatitude(Double minLatitude) {
        this.minLatitude = minLatitude;
    }

    public void setMaxLatitude(Double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public void setMinLongitude(Double minLongitude) {
        this.minLongitude = minLongitude;
    }

    public void setMaxLongitude(Double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
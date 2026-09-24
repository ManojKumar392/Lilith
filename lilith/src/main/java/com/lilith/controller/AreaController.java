package com.lilith.controller;

import com.lilith.dto.AreaResponse;
import com.lilith.service.AreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public ResponseEntity<List<AreaResponse>> getAllAreas() {

        return ResponseEntity.ok(
                areaService.getAllAreas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaResponse> getAreaById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                areaService.getAreaById(id)
        );
    }
}
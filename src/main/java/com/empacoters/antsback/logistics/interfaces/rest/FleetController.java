package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.CreateFleetUseCase;
import com.empacoters.antsback.logistics.interfaces.dto.FleetResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fleets")
public class FleetController {
    private CreateFleetUseCase createFleetUseCase;

//    @GetMapping
//    public ResponseEntity<FleetResponseDTO[]> getAllFleets() {
//
//    }

}

package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<?> getPatients(){
        return ResponseEntity.ok().body(patientService.getPatients());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(
           @Valid @PathVariable UUID id,
           @Valid @RequestBody PatientRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.updatePatient(id,requestDTO));
    }

}

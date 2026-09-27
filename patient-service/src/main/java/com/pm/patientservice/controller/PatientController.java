package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidatorsGroup;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<?> createPatient(
            @Validated({Default.class, CreatePatientValidatorsGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(
           @Valid @PathVariable UUID id,
           @Validated({Default.class}) @RequestBody PatientRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.updatePatient(id,requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(
            @PathVariable UUID id
    ){
        patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

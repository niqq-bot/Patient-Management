package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientMapper patientMapper, PatientRepository patientRepository) {
        this.patientMapper = patientMapper;
        this.patientRepository = patientRepository;
    }





    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients=patientRepository.findAll();
        return patients.stream()
                .map(patient -> patientMapper
                        .toDTO(patient)
                )
                .collect(Collectors.toList());
    }

    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO){

        if(patientRepository.existsByEmail(requestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email"+" already exists : " + requestDTO.getEmail());
        }
        Patient patient= patientMapper.toPatient(requestDTO);
        Patient newPatient=patientRepository.save(patient);
        return patientMapper.toDTO(newPatient);

    }
    public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO requestDTO){

        Patient patient=patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException("Patiend not found with ID: "  +id));

        if(!patient.getEmail().equals(requestDTO.getEmail())
                && patientRepository.existsByEmail(requestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email"+" already exists : " + requestDTO.getEmail());
        }
        patientMapper.updatePatientFromDTO(requestDTO,patient);
        return patientMapper.toDTO(patientRepository.save(patient));

    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}


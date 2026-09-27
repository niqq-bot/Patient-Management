package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "id",target = "id")
    @Mapping(source = "dateOfBirth",target = "dateOfBirth")
    PatientResponseDTO toDTO(Patient patient);

    @Mapping(source = "dateOfBirth",target = "dateOfBirth")
    @Mapping(source = "registeredDate",target = "registeredDate")
    Patient toPatient(PatientRequestDTO requestDTO);

    void updatePatientFromDTO(
            PatientRequestDTO requestDTO,
            @MappingTarget Patient patient
    );
}

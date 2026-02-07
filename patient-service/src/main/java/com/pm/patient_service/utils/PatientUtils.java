package com.pm.patient_service.utils;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.model.Patient;

import java.time.LocalDate;

public class PatientUtils {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(String.valueOf(patient.getId()));
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth());
        patientResponseDTO.setRegisteredDate(patient.getRegisteredDate());
        return patientResponseDTO;
    }

    public static Patient fromDTO(PatientRequestDTO patientRequestDTO){
        Patient patient=new Patient();
        patient.setName((patientRequestDTO.getName()));
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        return patient;
    }
}

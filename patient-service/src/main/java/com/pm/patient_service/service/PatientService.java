package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.grpc.BillingServiceGrpcClient;
import com.pm.patient_service.kafka.KafkaProducer;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import com.pm.patient_service.utils.PatientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer,
                          KafkaTemplate<String, byte[]> kafkaTemplate
    ) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientUtils::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequest) {
        if (patientRepository.existsByEmail(patientRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + patientRequest.getEmail() + " already exists.");
        }
        Patient newPatient = patientRepository.save(PatientUtils.fromDTO((patientRequest)));
        billingServiceGrpcClient.createBillingAccount((newPatient.getId().toString()), newPatient.getName(), newPatient.getEmail());
        kafkaProducer.sendEvent(newPatient);
        return PatientUtils.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: ", id));

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + patientRequestDTO.getEmail() + " already exists.");
        }

        patient.setEmail(patientRequestDTO.getEmail());
        patient.setName(patientRequestDTO.getName());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientUtils.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}

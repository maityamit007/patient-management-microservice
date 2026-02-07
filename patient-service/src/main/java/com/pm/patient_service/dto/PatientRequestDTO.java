package com.pm.patient_service.dto;

import com.pm.patient_service.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(max=100, message="Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Size(max=30, message="Email must not exceed 15 characters")
    private String email;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Date of Birth is mandatory")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registration Date is mandatory")
    private String registrationDate;

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

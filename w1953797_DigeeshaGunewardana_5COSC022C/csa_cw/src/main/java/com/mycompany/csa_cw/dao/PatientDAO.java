package com.mycompany.csa_cw.dao;

import com.mycompany.csa_cw.model.Patient;
import com.mycompany.csa_cw.util.GenerateId;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    // Static list to store patients
    private static List<Patient> patients = new ArrayList<>();
    
    // Object for generating patient IDs
    private GenerateId idGenerator = new GenerateId();
    
    // Method to retrieve all patients
    public List<Patient> getAllPatients() {
        // Return a copy of the list to prevent direct modification
        return new ArrayList<>(patients);
    }
    
    // Initialize some sample data
    static {
        patients.add(new Patient("003","Alice Johnson", "alice.johnson@example.com", "123 Main Street, Anytown, USA", "Fever", "Good"));
        patients.add(new Patient("004","John Smith", " john.smith@example.com", "456 Elm Avenue, Somewhere City, USA", "Fever", "Bad"));
    }
    
    // Method to get a patient by ID    
    public Patient getPatientById(String patientId) {
        // Iterate through the list
        for (Patient patient : patients) {
            if (patient.getId().equals(patientId)) {
                return patient; // Return the patient if found
            }
        }
        return null; // Patient not found
    }
    
    // Method to get patients by health status
    public List<Patient> getPatientsByHealthStatus(String healthStatus) {
        // Create a list to store patients with the specified health status
        List<Patient> patientsWithStatus = new ArrayList<>();
        // Iterate through the list to find patients with the specified health status
        for (Patient patient : patients) {
            if (patient.getHealthStatus().equalsIgnoreCase(healthStatus)) {
                patientsWithStatus.add(patient); // Add patient to the list if health status matches
            }
        }
        return patientsWithStatus; // Return the list of patients with the specified health status
    }
    
    // Method to add a new patient
    public void addPatient(Patient patient) {
        // Generate patient ID
        String patientId = idGenerator.generatePatientId();
        // Set the generated ID to the patient object
        patient.setId(patientId);
        
        // Add patient to the list
        patients.add(patient);
    }

    // Method to update an existing patient by ID
    public void updatePatient(String id, Patient updatedPatient) {
        // Find the index of the patient with the given ID
        int index = findPatientIndexById(id);
        // Check if patient with the given ID exists
        if (index != -1) {
            patients.set(index, updatedPatient); // Update the patient at the found index
        } else {
            System.out.println("Patient not found with ID: " + id);
        }
    }

    // Method to delete a patient by ID
    public void deletePatient(String id) {
        // Find the index of the patient with the given ID
        int index = findPatientIndexById(id);
        // Check if patient with the given ID exists
        if (index != -1) {
            patients.remove(index); // Remove the patient at the found index
        } else {
            System.out.println("Patient not found with ID: " + id);
        }
    }
    
    // Helper method to find the index of a patient by ID
    private int findPatientIndexById(String id) {
        // Iterate through the list to find the index of the patient with the given ID
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) {
                return i; // Return the index if patient with ID is found
            }
        }
        return -1; // Return -1 if patient with ID is not found.
    }
}

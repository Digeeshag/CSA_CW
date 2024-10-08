package com.mycompany.csa_cw.dao;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.csa_cw.model.Prescription;
import com.mycompany.csa_cw.util.GenerateId;

public class PrescriptionDAO {
    // List to store prescriptions
    private static List<Prescription> prescriptions = new ArrayList<>();

    // Sample data
    static {
        // Sample prescriptions
        Prescription prescription1 = new Prescription("1", "Paracetamol", "500mg", "Take 1 tablet every 4 hours", "5 days", null, null);
        Prescription prescription2 = new Prescription("2", "Ibuprofen", "200mg", "Take 1 tablet every 6 hours", "3 days", null, null);
        Prescription prescription3 = new Prescription("3", "Amoxicillin", "500mg", "Take 1 tablet every 8 hours", "7 days", null, null);

        // Add sample prescriptions to the list
        prescriptions.add(prescription1);
        prescriptions.add(prescription2);
        prescriptions.add(prescription3);
    }

    // Object for generating prescription IDs
    private GenerateId idGenerator = new GenerateId();
    
    // Method to retrieve all prescriptions
    public List<Prescription> getAllPrescriptions() {
        // Return a copy of the list to prevent direct modification
        return new ArrayList<>(prescriptions);
    }
    
    // Method to get a prescription by ID
    public Prescription getPrescriptionById(String prescriptionId) {
        // Iterate through the list to find the prescription with the given ID
        for (Prescription prescription : prescriptions) {
            if (prescription.getId().equals(prescriptionId)) {
                return prescription; // Return the prescription if found
            }
        }
        return null; // Return null if prescription not found
    }

    // Method to get prescriptions by patient ID
    public List<Prescription> getPrescriptionsByPatientId(String patientId) {
        List<Prescription> prescriptionsForPatient = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if (prescription.getId().equals(patientId)) {
                prescriptionsForPatient.add(prescription);
            }
        }
        return prescriptionsForPatient;
    }
    
    // Method to get prescriptions by doctor ID
    public List<Prescription> getPrescriptionsByDoctorId(String doctorId) {
        List<Prescription> prescriptionsForDoctor = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if (prescription.getId().equals(doctorId)) {
                prescriptionsForDoctor.add(prescription);
            }
        }
        return prescriptionsForDoctor;
    }
    
    // Method to add a new prescription
    public void addPrescription(Prescription prescription) {
        // Generate prescription ID
        String prescriptionId = idGenerator.generatePrescriptionId();
        prescription.setId(prescriptionId);

        // Add prescription to the list
        prescriptions.add(prescription);
    }

    // Method to update an existing prescription by ID
    public void updatePrescription(String id, Prescription updatedPrescription) {
        // Find the index of the prescription with the given ID
        int index = findPrescriptionIndexById(id);
        // Check if prescription with the given ID exists
        if (index != -1) {
            prescriptions.set(index, updatedPrescription); // Update the prescription at the found index
        } else {
            System.out.println("Prescription not found with ID: " + id);
        }
    }
    
    // Method to delete a prescription by ID
    public void deletePrescription(String id) {
        // Find the index of the prescription with the given ID
        int index = findPrescriptionIndexById(id);
        // Check if prescription with the given ID exists
        if (index != -1) {
            prescriptions.remove(index); // Remove the prescription at the found index
        } else {
            System.out.println("Prescription not found with ID: " + id);
        }
    }

    // Helper method to find the index of a prescription by ID
    private int findPrescriptionIndexById(String id) {
        // Iterate through the list to find the index of the prescription with the given ID
        for (int i = 0; i < prescriptions.size(); i++) {
            if (prescriptions.get(i).getId().equals(id)) {
                return i; // Return the index if prescription with ID is found
            }
        }
        return -1; // Return -1 if prescription with ID is not found
    }
}

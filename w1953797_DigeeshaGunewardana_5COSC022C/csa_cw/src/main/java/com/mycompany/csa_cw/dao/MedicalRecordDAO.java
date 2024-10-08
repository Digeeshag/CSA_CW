package com.mycompany.csa_cw.dao;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.csa_cw.model.MedicalRecord;
import com.mycompany.csa_cw.util.GenerateId;

public class MedicalRecordDAO {
    // List to store medical records
    private static List<MedicalRecord> medicalRecords = new ArrayList<>();

    // Sample data
    static {
        // Sample medical records
        MedicalRecord medicalRecord1 = new MedicalRecord("001", null, "Dengue", "Paracetamol");
        MedicalRecord medicalRecord2 = new MedicalRecord("002", null, "Malaria", "Chloroquine");
        MedicalRecord medicalRecord3 = new MedicalRecord("003", null, "Typhoid", "Ciprofloxacin");

        // Add sample medical records to the list
        medicalRecords.add(medicalRecord1);
        medicalRecords.add(medicalRecord2);
        medicalRecords.add(medicalRecord3);
    }

    // Object for generating medical record IDs
    private GenerateId idGenerator = new GenerateId();
    
    // Method to retrieve all medical records
    public List<MedicalRecord> getAllMedicalRecords() {
        // Return a copy of the list to prevent direct modification
        return new ArrayList<>(medicalRecords);
    }
    
    // Method to get a medical record by ID
    public MedicalRecord getMedicalRecordById(String medicalRecordId) {
        // Iterate through the list to find the medical record with the given ID
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId().equals(medicalRecordId)) {
                return medicalRecord; // Return the medical record if found
            }
        }
        return null; // Return null if medical record not found
    }

    // Method to get medical records by patient ID
    public List<MedicalRecord> getMedicalRecordsByPatientId(String patientId) {
        List<MedicalRecord> medicalRecordsForPatient = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId().equals(patientId)) {
                medicalRecordsForPatient.add(medicalRecord);
            }
        }
        return medicalRecordsForPatient;
    }
  
    // Method to add a new medical record
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        // Generate medical record ID
        String medicalRecordId = idGenerator.generateMedicalRecordId();
        medicalRecord.setId(medicalRecordId);

        // Add medical record to the list
        medicalRecords.add(medicalRecord);
    }

    // Method to update an existing medical record by ID
    public void updateMedicalRecord(String id, MedicalRecord updatedMedicalRecord) {
        // Find the index of the medical record with the given ID
        int index = findMedicalRecordIndexById(id);
        // Check if medical record with the given ID exists
        if (index != -1) {
            medicalRecords.set(index, updatedMedicalRecord); // Update the medical record at the found index
        } else {
            System.out.println("Medical record not found with ID: " + id);
        }
    }
    
    // Method to delete a medical record by ID
    public void deleteMedicalRecord(String id) {
        // Find the index of the medical record with the given ID
        int index = findMedicalRecordIndexById(id);
        // Check if medical record with the given ID exists
        if (index != -1) {
            medicalRecords.remove(index); // Remove the medical record at the found index
        } else {
            System.out.println("Medical record not found with ID: " + id);
        }
    }

    // Helper method to find the index of a medical record by ID
    private int findMedicalRecordIndexById(String id) {
        // Iterate through the list to find the index of the medical record with the given ID
        for (int i = 0; i < medicalRecords.size(); i++) {
            if (medicalRecords.get(i).getId().equals(id)) {
                return i; // Return the index if medical record with ID is found
            }
        }
        return -1; // Return -1 if medical record with ID is not found
    }
    
}

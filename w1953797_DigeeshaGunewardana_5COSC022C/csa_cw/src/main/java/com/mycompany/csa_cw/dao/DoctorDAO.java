package com.mycompany.csa_cw.dao;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.csa_cw.model.Doctor;
import com.mycompany.csa_cw.util.GenerateId;

public class DoctorDAO {
    // Static list to store doctors
    private static List<Doctor> doctors = new ArrayList<>();
    
    // Object for generating doctor IDs
    private GenerateId idGenerator = new GenerateId();
    
    // Initialize some sample data
    static {
        doctors.add(new Doctor("003","Alice Johnson", "alice.johnson@example.com", "123 Main Street, Anytown, USA", "Orthopedics"));
        doctors.add(new Doctor("004","John Smith", " john.smith@example.com", "456 Elm Avenue, Somewhere City, USA", "Pediatrics"));
    }
    
    // Method to retrieve all doctors
    public List<Doctor> getAllDoctors() {
        // Return a copy of the list to prevent direct modification
        return new ArrayList<>(doctors);
    }
    
    // Method to get a doctor by ID
    public Doctor getDoctorById(String doctorId) {
        // Iterate through the list to find the doctor with the given ID
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(doctorId)) {
                return doctor; // Return the doctor if found
            }
        }
        return null; // Doctor not found
    }
    
    // Method to get doctors by specialization
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        // Create a list to store doctors with the specified specialization
        List<Doctor> doctorsWithSpecialization = new ArrayList<>();
        // Iterate through the list to find doctors with the specified specialization
        for (Doctor doctor : doctors) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                doctorsWithSpecialization.add(doctor); // Add doctor to the list if specialization matches
            }
        }
        return doctorsWithSpecialization; // Return the list of doctors with the specified specialization
    }
    
    // Method to add a new doctor
    public void addDoctor(Doctor doctor) {
        // Generate doctor ID
        String doctorId = idGenerator.generateDoctorId();
        doctor.setId(doctorId);

        // Add doctor to the list
        doctors.add(doctor);
    }
    
    // Method to update an existing doctor by ID
    public void updateDoctor(String id, Doctor updatedDoctor) {
        // Find the index of the doctor with the given ID
        int index = findDoctorIndexById(id);
        // Check if doctor with the given ID exists
        if (index != -1) {
            doctors.set(index, updatedDoctor); // Update the doctor at the found index
        } else {
            System.out.println("Doctor not found with ID: " + id);
        }
    }

    // Method to delete a doctor by ID
    public void deleteDoctor(String id) {
        // Find the index of the doctor with the given ID
        int index = findDoctorIndexById(id);
        // Check if doctor with the given ID exists
        if (index != -1) {
            doctors.remove(index); // Remove the doctor at the found index
        } else {
            System.out.println("Doctor not found with ID: " + id);
        }
    }
    
    // Helper method to find the index of a doctor by ID
    private int findDoctorIndexById(String id) {
        // Iterate through the list to find the index of the doctor with the given ID
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId().equals(id)) {
                return i; // Return the index if doctor with ID is found
            }
        }
        return -1; // Return -1 if doctor with ID is not found
    }
    
}

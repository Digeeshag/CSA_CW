package com.mycompany.csa_cw.dao;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.csa_cw.model.Appointment;
import com.mycompany.csa_cw.util.GenerateId;

public class AppointmentDAO {
    // Static list to store appointments
    private static List<Appointment> appointments = new ArrayList<>();
    // Object for generating appointment IDs
    private GenerateId idGenerator = new GenerateId();
    
    // Initialize some sample data
    static {
        appointments.add(new Appointment("APP001", "2024-05-06", "09:00", null, null));
    }
    
    // Method to retrieve all appointments
    public List<Appointment> getAllAppointments() {
        // Return a copy of the list to prevent direct modification
        return new ArrayList<>(appointments);
    }
    
    // Method to get an appointment by ID
    public Appointment getAppointmentById(String appointmentId) {
        // Iterate through the list to find the appointment with the given ID
        for (Appointment appointment : appointments) {
            if (appointment.getId().equals(appointmentId)) {
                return appointment; // Return the appointment if found
            }
        }
        return null; // Return null if appointment not found
    }

    // Method to get appointments by date
    public List<Appointment> getAppointmentsByDate(String date) {
        // Create a list to store appointments on the specified date
        List<Appointment> appointmentsOnDate = new ArrayList<>();
        // Iterate through the list to find appointments on the specified date
        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(date)) {
                appointmentsOnDate.add(appointment); // Add appointment to the list if date matches
            }
        }
        return appointmentsOnDate; // Return the list of appointments on the specified date
    }
    
    // Method to get appointments by patient ID
    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        // Create a list to store appointments for the specified patient
        List<Appointment> appointmentsForPatient = new ArrayList<>();
        // Iterate through the list to find appointments for the specified patient
        for (Appointment appointment : appointments) {
            if (appointment.getId() != null && appointment.getId().equals(patientId)) {
                appointmentsForPatient.add(appointment); // Add appointment to the list if patient ID matches
            }
        }
        return appointmentsForPatient;
    }

    // Method to get appointments by doctor ID
    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        // Create a list to store appointments for the specified doctor
        List<Appointment> appointmentsForDoctor = new ArrayList<>();
        // Iterate through the list to find appointments for the specified doctor
        for (Appointment appointment : appointments) {
            if (appointment.getId() != null && appointment.getId().equals(doctorId)) {
                appointmentsForDoctor.add(appointment); // Add appointment to the list if doctor ID matches
            }
        }
        return appointmentsForDoctor; // Return the list of appointments for the specified doctor
    }
    
    // Method to add a new appointment
    public void addAppointment(Appointment appointment) {
        // Generate appointment ID
        String appointmentId = idGenerator.generateAppointmentId();
        appointment.setId(appointmentId);

        // Add appointment to the list
        appointments.add(appointment);
    }

    // Method to update an existing appointment by ID
    public void updateAppointment(String id, Appointment updatedAppointment) {
        // Find the index of the appointment with the given ID
        int index = findAppointmentIndexById(id);
        // Check if appointment with the given ID exists
        if (index != -1) {
            appointments.set(index, updatedAppointment); // Update the appointment at the found index
        } else {
            System.out.println("Appointment not found with ID: " + id);
        }
    }
    
    // Method to delete an appointment by ID
    public void deleteAppointment(String id) {
        // Find the index of the appointment with the given ID
        int index = findAppointmentIndexById(id);
        // Check if appointment with the given ID exists
        if (index != -1) {
            appointments.remove(index); // Remove the appointment at the found index
        } else {
            System.out.println("Appointment not found with ID: " + id);
        }
    }

    // Helper method to find the index of an appointment by ID
    private int findAppointmentIndexById(String id) {
        // Iterate through the list to find the index of the appointment with the given ID
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId().equals(id)) {
                return i; // Return the index if appointment with ID is found
            }
        }
        return -1; // Return -1 if appointment with ID is not found
    }
    
}

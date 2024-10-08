package com.mycompany.csa_cw.util;

/**
 *
 * @author ASUS
 */
public class GenerateId {
    private static int patientCounter = 0;
    private static int doctorCounter = 0;
    private static int appointmentCounter = 0;
    private static int billingCounter = 0; 
    private static int medicalRecordCounter = 0; 
    private static int prescriptionCounter = 0; 

    public static String generatePatientId() {
        patientCounter++;
        return String.format("%03dP", patientCounter);
    }

    public static String generateDoctorId() {
        doctorCounter++;
        return String.format("%03dD", doctorCounter);
    }
    
    public static String generateAppointmentId() {
        appointmentCounter++;
        return String.format("APP%03d", appointmentCounter);
    }

    public static String generateBillingId() {
        billingCounter++;
        return String.format("B%03d", billingCounter);
    }

    public static String generateMedicalRecordId() {
        medicalRecordCounter++;
        return String.format("MR%03d", medicalRecordCounter);
    }

    public static String generatePrescriptionId() {
        prescriptionCounter++;
        return String.format("PRESC%03d", prescriptionCounter);
    }
}

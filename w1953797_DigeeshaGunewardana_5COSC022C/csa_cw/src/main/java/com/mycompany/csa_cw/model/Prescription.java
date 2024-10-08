package com.mycompany.csa_cw.model;

/**
 *
 * @author ASUS
 */
public class Prescription {
    private String Id;
    private String medications;
    private String dosage;
    private String instructions;
    private String duration;
    private Patient patient;
    private Doctor doctor;

    // constructors
    public Prescription(){
    }

    public Prescription(String Id, String medications, String dosage, String instructions, String duration, Patient patient, Doctor doctor) {
        this.Id = Id;
        this.medications = medications;
        this.dosage = dosage;
        this.instructions = instructions;
        this.duration = duration;
        this.patient = patient;
        this.doctor = doctor;
    }

    // getters and setters

    public String getId() {
        return Id;
    }

    public String getMedications() {
        return medications;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getDuration() {
        return duration;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
        
    
}

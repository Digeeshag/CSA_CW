package com.mycompany.csa_cw.model;

/**
 *
 * @author ASUS
 */
public class Appointment {
    private String id;
    private String date;
    private String time;
    private Doctor doctor;
    private Patient patient;

    // constructors
    public Appointment(){
    }

    public Appointment(String id, String date, String time, Doctor doctor, Patient patient) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.patient = patient;
    }
      
    
    // getters and setters

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    
}

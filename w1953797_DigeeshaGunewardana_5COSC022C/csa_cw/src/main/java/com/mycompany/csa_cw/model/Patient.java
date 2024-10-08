package com.mycompany.csa_cw.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Patient extends Person {
    private String medicalHistory;
    private String HealthStatus;

    // constructors
    public Patient(){
        super();
    }    

    public Patient(String Id, String name, String contactInformation, String address, String medicalHistory, String HealthStatus) {
        super(Id, name, contactInformation, address);
        this.medicalHistory = medicalHistory;
        this.HealthStatus = HealthStatus;
    }
   
    // getters and setters

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getHealthStatus() {
        return HealthStatus;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setHealthStatus(String HealthStatus) {
        this.HealthStatus = HealthStatus;
    }
    
    
    
}

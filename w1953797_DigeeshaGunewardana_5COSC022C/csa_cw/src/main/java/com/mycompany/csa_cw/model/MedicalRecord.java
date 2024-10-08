package com.mycompany.csa_cw.model;

/**
 *
 * @author ASUS
 */
public class MedicalRecord {
    private String Id;
    private Patient patient;
    private String diagnoses;
    private String treatments;

    // constructors
    public MedicalRecord(){
    }

    public MedicalRecord(String Id, Patient patient, String diagnoses, String treatments) {
        this.Id = Id;
        this.patient = patient;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
    }


    // getters and setters
    public String getDiagnoses() {
        return diagnoses;
    }

    public String getTreatments() {
        return treatments;
    }

    public String getId() {
        return Id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setDiagnoses(String diagnoses) {
        this.diagnoses = diagnoses;
    }

    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    
}

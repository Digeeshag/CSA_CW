package com.mycompany.csa_cw.model;

/**
 *
 * @author ASUS
 */
public class Billing {
    private String Id;
    private String invoiceDetails;
    private String paymentDetails;
    private double outstandingBalance;
    private Patient patient;
    private Doctor doctor;

    // constructors
    public Billing(){
    }

    public Billing(String Id, String invoiceDetails, String paymentDetails, double outstandingBalance, Patient patient, Doctor doctor) {
        this.Id = Id;
        this.invoiceDetails = invoiceDetails;
        this.paymentDetails = paymentDetails;
        this.outstandingBalance = outstandingBalance;
        this.patient = patient;
        this.doctor = doctor;
    }
    
    // getters and setters
    public String getId() {
        return Id;
    }

    public String getInvoiceDetails() {
        return invoiceDetails;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
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

    public void setInvoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
       
}

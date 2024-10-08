package com.mycompany.csa_cw.dao;

import com.mycompany.csa_cw.model.Billing;
import com.mycompany.csa_cw.util.GenerateId;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {
    // List to store billings
    private static List<Billing> billings = new ArrayList<>();

    // Sample Data
    static {
        // Sample billings
        Billing billing1 = new Billing("001", "Invoice 001", "Payment 001", 1000, null, null);
        Billing billing2 = new Billing("002", "Invoice 002", "Payment 002", 2000, null, null);
        Billing billing3 = new Billing("003", "Invoice 003", "Payment 003", 3000, null, null);

        // Add sample billings to the list
        billings.add(billing1);
        billings.add(billing2);
        billings.add(billing3);
    }

    // Object for generating billing IDs
    private GenerateId idGenerator = new GenerateId();
    
    // Method to retrieve all billings
    public List<Billing> getAllBillings() {
        // Return a copy of the list to prevent direct modification
        return new ArrayList<>(billings);
    }
    
    // Method to get a billing by ID
    public Billing getBillingById(String billingId) {
        // Iterate through the list to find the billing with the given ID
        for (Billing billing : billings) {
            if (billing.getId().equals(billingId)) {
                return billing; // Return the billing if found
            }
        }
        return null; // Return null if billing not found
    }

    // Method to get billings by patient ID
    public List<Billing> getBillingsByPatientId(String patientId) {
        List<Billing> billingsForPatient = new ArrayList<>();
        for (Billing billing : billings) {
            if (billing.getId().equals(patientId)) {
                billingsForPatient.add(billing);
            }
        }
        return billingsForPatient;
    }
    
    // Method to add a new billing
    public void addBilling(Billing billing) {
        // Generate billing ID
        String billingId = idGenerator.generateBillingId();
        billing.setId(billingId);

        // Add billing to the list
        billings.add(billing);
    }

    // Method to update an existing billing by ID
    public void updateBilling(String id, Billing updatedBilling) {
        int index = findBillingIndexById(id);
        if (index != -1) {
            billings.set(index, updatedBilling);
        } else {
            System.out.println("Billing not found with ID: " + id);
        }
    }
    
    // Method to delete a billing by ID
    public void deleteBilling(String id) {
        // Find the index of the billing with the given ID
        int index = findBillingIndexById(id);
        // Check if billing with the given ID exists
        if (index != -1) {
            billings.remove(index); // Remove the billing at the found index
        } else {
            System.out.println("Billing not found with ID: " + id);
        }
    }

    // Helper method to find the index of a billing by ID
    private int findBillingIndexById(String id) {
        // Iterate through the list to find the index of the billing with the given ID
        for (int i = 0; i < billings.size(); i++) {
            if (billings.get(i).getId().equals(id)) {
                return i; // Return the index if billing with ID is found
            }
        }
        return -1; // Return -1 if billing with ID is not found
    }

    // Method to calculate the total bill for a patient based on patient ID
    public double getTotalBillByPatientId(String patientId) {
        double totalBill = 0;
        // Iterate through the list to calculate total bill for the specified patient
        for (Billing billing : billings) {
            if (billing.getPatient().getId().equalsIgnoreCase(patientId)) {
                totalBill += billing.getOutstandingBalance(); // Add outstanding balance to the total bill
            }
        }
        return totalBill; // Return the total bill for the specified patient
    }
    
}

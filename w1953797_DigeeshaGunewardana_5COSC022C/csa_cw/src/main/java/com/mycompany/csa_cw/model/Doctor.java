package com.mycompany.csa_cw.model;

/**
 *
 * @author ASUS
 */
public class Doctor extends Person {
    private String specialization;

    // constructors
    public Doctor(){
        super();
    }

    public Doctor(String Id, String name, String contactInformation, String address, String specialization) {
        super(Id, name, contactInformation, address);
        this.specialization = specialization;
    }
    

    // getters and setters

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
      
}

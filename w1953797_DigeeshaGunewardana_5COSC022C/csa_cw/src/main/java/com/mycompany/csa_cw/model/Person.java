package com.mycompany.csa_cw.model;

/**
 *
 * @author ASUS
 */
public class Person {
    private String Id;
    private String name;
    private String contactInformation;
    private String address;
    
    // constructors
    public Person(){
    }

    public Person(String Id, String name, String contactInformation, String address) {
        this.Id = Id;
        this.name = name;
        this.contactInformation = contactInformation;
        this.address = address;
    }
    
    
    // getters and setters

    public String getName() {
        return name;
    }

    public String getId() {
        return Id;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
           
}

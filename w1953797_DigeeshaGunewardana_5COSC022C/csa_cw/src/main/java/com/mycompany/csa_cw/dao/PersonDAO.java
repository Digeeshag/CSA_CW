package com.mycompany.csa_cw.dao;

import com.mycompany.csa_cw.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    
    // Static list to store persons
    private static List<Person> persons = new ArrayList<>();
    
    // Initialize some sample data
    static {
        persons.add(new Person("003","Alice Johnson", "alice.johnson@example.com", "123 Main Street, Anytown, USA"));
        persons.add(new Person("004","John Smith", " john.smith@example.com", "456 Elm Avenue, Somewhere City, USA"));
    }
    
    // Method to retrieve all persons
    public List<Person> getAllPersons() {
        // Return a copy of the list to prevent direct modification
        return new ArrayList<>(persons); 
    }
    
    // Method to retrieve a specific person by index
    public Person getPerson(int index) {
        // Check for valid index
        if (index < 0 || index >= persons.size()) {
            throw new IllegalArgumentException("Invalid index");
        }
        // Return the person at the specified index
        return persons.get(index);
    }
    
    // Method to add a new person
    public void addPerson(Person person) {
        // Add the new person to the list
        persons.add(person);
    }

    // Method to update an existing person by index
    public void updatePerson(int index, Person person) {
        // Check for valid index
        if (index < 0 || index >= persons.size()) {
            throw new IllegalArgumentException("Invalid index");
        }
        // Update the person at the given index with the new person object
        persons.set(index, person);
    }

    // Method to delete a person by index
    public void deletePerson(int index) {
        // Check for valid index
        if (index < 0 || index >= persons.size()) {
            throw new IllegalArgumentException("Invalid index");
        }
        // Remove the person at the specified index from the list
        persons.remove(index);
    }
}

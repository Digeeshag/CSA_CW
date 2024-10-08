package com.mycompany.csa_cw.resource;

import com.mycompany.csa_cw.exception.ResourceNotFoundException;
import com.mycompany.csa_cw.exception.InvalidDataException;
import com.mycompany.csa_cw.exception.InternalServerErrorException;
import com.mycompany.csa_cw.model.Person;
import com.mycompany.csa_cw.dao.PersonDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/persons")
public class PersonResource {
    private static final Logger logger = Logger.getLogger(PersonResource.class.getName());
    private PersonDAO personDAO = new PersonDAO();
    
    /**
    * GET method to retrieve all persons.
    * 
    * @return Response containing a list of persons in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() throws InternalServerErrorException {
        try {
            List<Person> persons = personDAO.getAllPersons();
            if (persons.isEmpty()) {
                throw new ResourceNotFoundException("Person data not found.");
            }
            return Response.ok(persons).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving all persons.", e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    
    /**
    * GET method to retrieve a person by ID.
    * 
    * @param id the ID of the person to retrieve
    * @return Response containing the person details in JSON format
    * @throws InvalidDataException if an invalid ID is provided
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") int id) throws InvalidDataException, InternalServerErrorException {
        try {
            Person person = personDAO.getPerson(id);
            if (person == null) {
                throw new ResourceNotFoundException("Person with id " + id + " not found.");
            }
            return Response.ok(person).build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Invalid ID provided: {0}", id);
            throw new InvalidDataException("Invalid ID provided: " + id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving person with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
     /**
     * POST method to add a new person.
     * 
     * @param person the person object to add
     * @return Response indicating success or failure of the operation
     * @throws InternalServerErrorException if an internal server error occurs
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) throws InternalServerErrorException {
        try {
            if (person == null || person.getName() == null || person.getContactInformation() == null || person.getAddress() == null) {
                throw new InvalidDataException("Person data invalid.");
            }
            personDAO.addPerson(person);
            logger.info("Person successfully created.");
            return Response.status(Response.Status.CREATED)
                    .entity("Person successfully created.")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while adding person.", e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * PUT method to update an existing person.
    * 
    * @param id the ID of the person to update
    * @param person the updated person object
    * @return Response indicating success or failure of the operation
    * @throws ResourceNotFoundException if the person with the specified ID is not found
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") int id, Person person) throws ResourceNotFoundException, InternalServerErrorException {
        try {
            if (person.getName() == null || person.getContactInformation() == null || person.getAddress() == null) {
                throw new InvalidDataException("Person data invalid.");
            }
            personDAO.updatePerson(id, person);
            logger.info("Person successfully updated.");
            return Response.status(Response.Status.OK)
                    .entity("Person successfully updated.")
                    .build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Person not found with id: {0}", id);
            throw new ResourceNotFoundException("Person with id " + id + " not found.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while updating person with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * DELETE method to delete a person by ID.
    * 
    * @param id the ID of the person to delete
    * @return Response indicating success or failure of the operation
    * @throws ResourceNotFoundException if the person with the specified ID is not found
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") int id) throws ResourceNotFoundException, InternalServerErrorException {
        try {
            personDAO.deletePerson(id);
            logger.info("Person deleted successfully");
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("Person deleted successfully")
                    .build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Person with id {0} not found.", id);
            throw new ResourceNotFoundException("Person with id " + id + " not found.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while deleting person with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
}

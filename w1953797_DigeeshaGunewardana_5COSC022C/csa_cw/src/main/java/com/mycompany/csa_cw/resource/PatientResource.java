package com.mycompany.csa_cw.resource;

import com.mycompany.csa_cw.exception.ResourceNotFoundException;
import com.mycompany.csa_cw.exception.InvalidDataException;
import com.mycompany.csa_cw.exception.InternalServerErrorException;
import com.mycompany.csa_cw.model.Patient;
import com.mycompany.csa_cw.dao.PatientDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/patients")
public class PatientResource {
    
    private static final Logger logger = Logger.getLogger(PatientResource.class.getName());
    private PatientDAO patientDAO = new PatientDAO();

    /**
    * GET method to retrieve all patients.
    * 
    * @return Response containing a list of patients in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPatients() throws InternalServerErrorException {
        try {
            List<Patient> patients = patientDAO.getAllPatients();
            if (patients.isEmpty()) {
                throw new ResourceNotFoundException("Patient data not found.");
            }
            return Response.ok(patients).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving all patients.", e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * GET method to retrieve a patient by ID.
    * 
    * @param id the ID of the patient to retrieve
    * @return Response containing the patient details in JSON format
    * @throws InvalidDataException if an invalid ID is provided
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatient(@PathParam("id") String id) throws InvalidDataException, InternalServerErrorException {
        try {
            Patient patient = patientDAO.getPatientById(id);
            if (patient == null) {
                throw new ResourceNotFoundException("Patient with id " + id + " not found.");
            }
            return Response.ok(patient).build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Invalid ID provided: {0}", id);
            throw new InvalidDataException("Invalid ID provided: " + id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving patient with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }

    /**
    * GET method to retrieve patients by health status.
    * 
    * @param healthStatus the health status of the patients to retrieve
    * @return Response containing a list of patients with the specified health status in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET
    @Path("/status/{healthStatus}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientsByHealthStatus(@PathParam("healthStatus") String healthStatus) throws InternalServerErrorException {
        try {
            List<Patient> patients = patientDAO.getPatientsByHealthStatus(healthStatus);
            if (patients.isEmpty()) {
                throw new ResourceNotFoundException("Patients with health status not found: " + healthStatus);
            }
            return Response.ok(patients).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving patients by health status: " + healthStatus, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * POST method to add a new patient.
    * 
    * @param patient the patient object to add
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) throws InternalServerErrorException {
        try {
            if (patient == null || patient.getName() == null || patient.getContactInformation() == null || patient.getAddress() == null) {
                throw new InvalidDataException("Invalid patient data.");
            }
            patientDAO.addPatient(patient);
            logger.info("Patient successfully created.");
            return Response.status(Response.Status.CREATED).entity("Patient successfully created.").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while adding patient.", e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }

    /**
    * PUT method to update an existing patient.
    * 
    * @param id the ID of the patient to update
    * @param patient the updated patient object
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    * @throws ResourceNotFoundException if the patient with the specified ID is not found
    */ 
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("id") String id, Patient patient) throws InternalServerErrorException, ResourceNotFoundException {
        try {
            if (patient.getName() == null || patient.getContactInformation() == null || patient.getAddress() == null) {
                throw new InvalidDataException("Invalid patient data.");
            }
            patientDAO.updatePatient(id, patient);
            logger.info("Patient successfully updated.");
            return Response.status(Response.Status.OK).entity("Patient successfully updated.").build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Patient not found with id: {0}", id);
            throw new ResourceNotFoundException("Patient with id " + id + " not found.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while updating patient with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }

    /**
    * DELETE method to delete a patient by ID.
    * 
    * @param id the ID of the patient to delete
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    * @throws ResourceNotFoundException if the patient with the specified ID is not found
    */   
    @DELETE
    @Path("/{id}")
    public Response deletePatient(@PathParam("id") String id) throws InternalServerErrorException, ResourceNotFoundException {
        try {
            patientDAO.deletePatient(id);
            logger.info("Patient successfully deleted.");
            return Response.status(Response.Status.NO_CONTENT).entity("Patient successfully deleted.").build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Patient with id {0} not found.", id);
            throw new ResourceNotFoundException("Patient with id " + id + " not found.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while deleting patient with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
}

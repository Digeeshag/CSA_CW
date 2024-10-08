package com.mycompany.csa_cw.resource;

import com.mycompany.csa_cw.exception.ResourceNotFoundException;
import com.mycompany.csa_cw.exception.InvalidDataException;
import com.mycompany.csa_cw.exception.InternalServerErrorException;
import com.mycompany.csa_cw.model.Patient;
import com.mycompany.csa_cw.model.Doctor;
import com.mycompany.csa_cw.model.Prescription;
import com.mycompany.csa_cw.dao.PatientDAO;
import com.mycompany.csa_cw.dao.DoctorDAO;
import com.mycompany.csa_cw.dao.PrescriptionDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/prescriptions")
public class PrescriptionResource {
    
    private static final Logger logger = Logger.getLogger(PrescriptionResource.class.getName());
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();

    /**
    * Retrieves all prescriptions.
    * 
    * @return Response containing a list of prescriptions
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET  
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPrescriptions() throws InternalServerErrorException {
        try {
            List<Prescription> prescriptions = prescriptionDAO.getAllPrescriptions();
            if (prescriptions.isEmpty()) {
                logger.info("Prescription data not found.");
                throw new ResourceNotFoundException("Prescription data not found.");
            }
            return Response.ok(prescriptions).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching all prescriptions", e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }
    
    /**
    * Retrieves a prescription by ID.
    * 
    * @param id the ID of the prescription to retrieve
    * @return Response containing the prescription
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescription(@PathParam("id") String id) throws InternalServerErrorException {
        try {
            Prescription prescription = prescriptionDAO.getPrescriptionById(id);
            if (prescription == null) {
                logger.log(Level.INFO, "Prescription with id {0} not found.", id);
                throw new ResourceNotFoundException("Prescription with id " + id + " not found.");
            }
            return Response.ok(prescription).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching prescription with id " + id, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Retrieves prescriptions by patient ID.
    * 
    * @param patientId the ID of the patient
    * @return Response containing a list of prescriptions for the patient
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET
    @Path("/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptionsByPatientId(@PathParam("patientId") String patientId) throws InternalServerErrorException {
        try {
            List<Prescription> prescriptions = prescriptionDAO.getPrescriptionsByPatientId(patientId);
            if (prescriptions.isEmpty()) {
                logger.log(Level.INFO, "Prescriptions for patient with id {0} not found.", patientId);
                throw new ResourceNotFoundException("Prescriptions for patient with id " + patientId + " not found.");
            }
            return Response.ok(prescriptions).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching prescriptions for patient with id " + patientId, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Retrieves prescriptions by doctor ID.
    * 
    * @param doctorId the ID of the doctor
    * @return Response containing a list of prescriptions for the doctor
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/doctor/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptionsByDoctorId(@PathParam("doctorId") String doctorId) throws InternalServerErrorException {
        try {
            List<Prescription> prescriptions = prescriptionDAO.getPrescriptionsByDoctorId(doctorId);
            if (prescriptions.isEmpty()) {
                logger.log(Level.INFO, "Prescriptions for doctor with id {0} not found.", doctorId);
                throw new ResourceNotFoundException("Prescriptions for doctor with id " + doctorId + " not found.");
            }
            return Response.ok(prescriptions).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching prescriptions for doctor with id " + doctorId, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Adds a new prescription.
    * 
    * @param prescription the prescription to add
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrescription(Prescription prescription) throws InternalServerErrorException {
        try {
            prescriptionDAO.addPrescription(prescription);
            logger.info("Prescription added successfully.");
            return Response.status(Response.Status.CREATED)
                    .entity("Prescription added successfully.")
                    .build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error occurred while adding prescription.", ex);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Updates an existing prescription.
    * 
    * @param id the ID of the prescription to update
    * @param updatedPrescription the updated prescription data
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrescription(@PathParam("id") String id, Prescription updatedPrescription) throws InternalServerErrorException {
        try {
            Prescription existingPrescription = prescriptionDAO.getPrescriptionById(id);
            if (existingPrescription == null) {
                logger.log(Level.INFO, "Prescription with id {0} not found.", id);
                throw new ResourceNotFoundException("Prescription with id " + id + " not found.");
            }
            updatedPrescription.setId(id); // Ensure the ID is set correctly
            prescriptionDAO.updatePrescription(id, updatedPrescription);
            logger.info("Prescription updated successfully.");
            return Response.status(Response.Status.OK)
                    .entity("Prescription updated successfully.")
                    .build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error occurred while updating prescription.", ex);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Deletes a prescription by ID.
    * 
    * @param id the ID of the prescription to delete
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @DELETE
    @Path("/{id}")
    public Response deletePrescription(@PathParam("id") String id) throws InternalServerErrorException {
        try {
            prescriptionDAO.deletePrescription(id);
            logger.info("Prescription successfully deleted.");
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("Prescription successfully deleted.")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while deleting prescription with id " + id, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    
}

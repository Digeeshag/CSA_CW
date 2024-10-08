package com.mycompany.csa_cw.resource;

import com.mycompany.csa_cw.exception.ResourceNotFoundException;
import com.mycompany.csa_cw.exception.InvalidDataException;
import com.mycompany.csa_cw.exception.InternalServerErrorException;
import com.mycompany.csa_cw.model.MedicalRecord;
import com.mycompany.csa_cw.dao.MedicalRecordDAO;
import com.mycompany.csa_cw.model.Patient;
import com.mycompany.csa_cw.dao.PatientDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/medicalRecords")
public class MedicalRecordResource {
    
    private static final Logger logger = Logger.getLogger(MedicalRecordResource.class.getName());
    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();
    
    /**
    * Retrieves all medical records.
    * 
    * @return Response containing the list of medical records
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMedicalRecords() throws InternalServerErrorException {
        try {
            List<MedicalRecord> medicalRecords = medicalRecordDAO.getAllMedicalRecords();
            if (medicalRecords.isEmpty()) {
                logger.info("MedicalRecord data unavailable.");
                throw new ResourceNotFoundException("MedicalRecord data unavailable.");
            }
            return Response.ok(medicalRecords).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching all medical records", e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Retrieves a medical record by ID.
    * 
    * @param id the ID of the medical record to retrieve
    * @return Response containing the medical record
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecord(@PathParam("id") String id) throws InternalServerErrorException {
        try {
            MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordById(id);
            if (medicalRecord == null) {
                logger.log(Level.INFO, "MedicalRecord with id {0} not found.", id);
                throw new ResourceNotFoundException("MedicalRecord with id " + id + " not found.");
            }
            return Response.ok(medicalRecord).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching medical record with id " + id, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Retrieves medical records by patient ID.
    * 
    * @param patientId the ID of the patient
    * @return Response containing the list of medical records for the patient
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordsByPatientId(@PathParam("patientId") String patientId) throws InternalServerErrorException {
        try {
            List<MedicalRecord> medicalRecords = medicalRecordDAO.getMedicalRecordsByPatientId(patientId);
            if (medicalRecords.isEmpty()) {
                logger.log(Level.INFO, "Medical records for patient with id {0} not found.", patientId);
                throw new ResourceNotFoundException("Medical records for patient with id " + patientId + " not found.");
            }
            return Response.ok(medicalRecords).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching medical records for patient with id " + patientId, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }
    
    /**
    * Adds a new medical record.
    * 
    * @param patientId the ID of the patient associated with the medical record
    * @param medicalRecord the medical record to add
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(@QueryParam("patientId") String patientId, MedicalRecord medicalRecord) throws InternalServerErrorException {
        try {
            // Check if medical record and patientId are provided
            if (medicalRecord == null || medicalRecord.getDiagnoses() == null || medicalRecord.getTreatments() == null || patientId == null) {
                logger.info("Medical record data invalid.");
                throw new InvalidDataException("Medical record data invalid.");
            }

            // Retrieve patient using patientId
            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                logger.log(Level.INFO, "Patient with id {0} not found.", patientId);
                throw new ResourceNotFoundException("Patient with id " + patientId + " not found.");
            }

            // Set patient for the medical record
            medicalRecord.setPatient(patient);
            
            // Add medical record
            medicalRecordDAO.addMedicalRecord(medicalRecord);
            logger.info("Medical record successfully created.");
            return Response.status(Response.Status.CREATED)
                    .entity("Medical record successfully created.")
                    .build();
        } catch (InvalidDataException | ResourceNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while adding medical record", e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Updates an existing medical record.
    * 
    * @param id the ID of the medical record to update
    * @param patientId the ID of the patient associated with the medical record
    * @param medicalRecord the updated medical record
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("id") String id, @QueryParam("patientId") String patientId, MedicalRecord medicalRecord) throws InternalServerErrorException {
        try {
            // Check if medical record and patientId are provided
            if (medicalRecord.getDiagnoses() == null || medicalRecord.getTreatments() == null || patientId == null) {
                logger.info("Medical record data invalid.");
                throw new InvalidDataException("Medical record data invalid.");
            }

            // Retrieve patient using patientId
            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                logger.log(Level.INFO, "Patient with id {0} not found.", patientId);
                throw new ResourceNotFoundException("Patient with id " + patientId + " not found.");
            }

            // Set patient for the medical record
            medicalRecord.setId(id); // Ensure the ID is set correctly
            medicalRecord.setPatient(patient);

            // Update medical record
            medicalRecordDAO.updateMedicalRecord(id, medicalRecord);
            logger.info("Medical record successfully updated.");
            return Response.status(Response.Status.OK)
                    .entity("Medical record successfully updated.")
                    .build();
        } catch (InvalidDataException | ResourceNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while updating medical record with id " + id, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    
    /**
    * Deletes a medical record by ID.
    * 
    * @param id the ID of the medical record to delete
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @DELETE
    @Path("/{id}")
    public Response deleteMedicalRecord(@PathParam("id") String id) throws InternalServerErrorException {
        try {
            medicalRecordDAO.deleteMedicalRecord(id);
            logger.info("Medical record successfully deleted.");
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("Medical record successfully deleted.")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while deleting medical record with id " + id, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }
}

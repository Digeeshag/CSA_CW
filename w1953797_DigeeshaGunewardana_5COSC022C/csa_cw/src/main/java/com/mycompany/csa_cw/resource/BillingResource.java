package com.mycompany.csa_cw.resource;

import com.mycompany.csa_cw.exception.ResourceNotFoundException;
import com.mycompany.csa_cw.exception.InvalidDataException;
import com.mycompany.csa_cw.exception.InternalServerErrorException;
import com.mycompany.csa_cw.model.Billing;
import com.mycompany.csa_cw.model.Patient;
import com.mycompany.csa_cw.model.Doctor;
import com.mycompany.csa_cw.dao.BillingDAO;
import com.mycompany.csa_cw.dao.PatientDAO;
import com.mycompany.csa_cw.dao.DoctorDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/billings")
public class BillingResource {
    private static final Logger logger = Logger.getLogger(BillingResource.class.getName());
    private BillingDAO billingDAO = new BillingDAO();
    
    /**
    * Retrieve all billings.
    * 
    * @return Response containing a list of billings
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBillings() throws InternalServerErrorException {
        try {
            List<Billing> billings = billingDAO.getAllBillings();
            if (billings.isEmpty()) {
                logger.info("Billing data not found.");
                throw new ResourceNotFoundException("Billing data not found.");
            }
            return Response.ok(billings).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching all billings", e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }
    
    /**
    * Retrieve a billing by its ID.
    * 
    * @param id the ID of the billing to retrieve
    * @return Response containing the billing
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBilling(@PathParam("id") String id) throws InternalServerErrorException {
        try {
            Billing billing = billingDAO.getBillingById(id);
            if (billing == null) {
                logger.log(Level.INFO, "Billing with id {0} not found.", id);
                throw new ResourceNotFoundException("Billing with id " + id + " not found.");
            }
            return Response.ok(billing).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching billing with id " + id, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Retrieve all billings for a specific patient.
    * 
    * @param patientId the ID of the patient
    * @return Response containing a list of billings for the patient
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingsByPatientId(@PathParam("patientId") String patientId) throws InternalServerErrorException {
        try {
            List<Billing> billings = billingDAO.getBillingsByPatientId(patientId);
            if (billings.isEmpty()) {
                logger.log(Level.INFO, "Billings for patient with id {0} not found.", patientId);
                throw new ResourceNotFoundException("Billings for patient with id " + patientId + " not found.");
            }
            return Response.ok(billings).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching billings for patient with id " + patientId, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Retrieve the total bill for a specific patient.
    * 
    * @param patientId the ID of the patient
    * @return Response containing the total bill for the patient
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/total/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalBill(@PathParam("patientId") String patientId) throws InternalServerErrorException {
        try {
            double totalBill = billingDAO.getTotalBillByPatientId(patientId);
            if (totalBill < 0) {
                logger.log(Level.INFO, "Billings for patient with id {0} not found.", patientId);
                throw new ResourceNotFoundException("Billings for patient with id " + patientId + " not found.");
            }
            return Response.ok("{\"totalBill\": " + totalBill + "}").build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching total bill for patient with id " + patientId, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Add a new billing.
    * 
    * @param patientId the ID of the patient associated with the billing
    * @param doctorId the ID of the doctor associated with the billing
    * @param billing the billing object to add
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBilling(@QueryParam("patientId") String patientId, @QueryParam("doctorId") String doctorId, Billing billing) throws InternalServerErrorException {
        try {
            if (billing == null || billing.getInvoiceDetails() == null || billing.getPaymentDetails() == null || billing.getOutstandingBalance() < 0 || patientId == null || doctorId == null) {
                logger.info("Invalid billing data.");
                throw new InvalidDataException("Invalid billing data.");
            }

            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                logger.log(Level.INFO, "Patient with id {0} not found.", patientId);
                throw new ResourceNotFoundException("Patient with id " + patientId + " not found.");
            }

            DoctorDAO doctorDAO = new DoctorDAO();
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                logger.log(Level.INFO, "Doctor with id {0} not found.", doctorId);
                throw new ResourceNotFoundException("Doctor with id " + doctorId + " not found.");
            }

            billing.setPatient(patient);
            billing.setDoctor(doctor);
            billingDAO.addBilling(billing);
            return Response.status(Response.Status.CREATED)
                    .entity("Billing successfully created.")
                    .build();
        } catch (InvalidDataException | ResourceNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while adding billing.", e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Update an existing billing.
    * 
    * @param id the ID of the billing to update
    * @param patientId the ID of the patient associated with the billing
    * @param doctorId the ID of the doctor associated with the billing
    * @param billing the updated billing object
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("id") String id, @QueryParam("patientId") String patientId, @QueryParam("doctorId") String doctorId, Billing billing) throws InternalServerErrorException {
        try {
            if (billing == null || billing.getInvoiceDetails() == null || billing.getPaymentDetails() == null || billing.getOutstandingBalance() < 0 || patientId == null || doctorId == null) {
                logger.info("Invalid billing data.");
                throw new InvalidDataException("Invalid billing data.");
            }

            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                logger.log(Level.INFO, "Patient with id {0} not found.", patientId);
                throw new ResourceNotFoundException("Patient with id " + patientId + " not found.");
            }

            DoctorDAO doctorDAO = new DoctorDAO();
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                logger.log(Level.INFO, "Doctor with id {0} not found.", doctorId);
                throw new ResourceNotFoundException("Doctor with id " + doctorId + " not found.");
            }

            billing.setId(id);
            billing.setPatient(patient);
            billing.setDoctor(doctor);
            billingDAO.updateBilling(id, billing);
            return Response.status(Response.Status.OK)
                    .entity("Billing successfully updated.")
                    .build();
        } catch (InvalidDataException | ResourceNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while updating billing.", e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }

    /**
    * Delete a billing by its ID.
    * 
    * @param id the ID of the billing to delete
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    * @throws ResourceNotFoundException if the billing with the given ID is not found
    */   
    @DELETE
    @Path("/{id}")
    public Response deleteBilling(@PathParam("id") String id) throws InternalServerErrorException, ResourceNotFoundException {
        try {
            billingDAO.deleteBilling(id);
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("Billing deleted successfully")
                    .build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Billing with id {0} not found.", id);
            throw new ResourceNotFoundException("Billing with id " + id + " not found.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while deleting billing with id " + id, e);
            throw new InternalServerErrorException("Internal Server Error occurred.");
        }
    }
}
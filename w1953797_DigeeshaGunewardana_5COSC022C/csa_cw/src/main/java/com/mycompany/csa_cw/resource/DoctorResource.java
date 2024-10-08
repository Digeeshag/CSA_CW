package com.mycompany.csa_cw.resource;

import com.mycompany.csa_cw.exception.ResourceNotFoundException;
import com.mycompany.csa_cw.exception.InvalidDataException;
import com.mycompany.csa_cw.exception.InternalServerErrorException;
import com.mycompany.csa_cw.model.Doctor;
import com.mycompany.csa_cw.dao.DoctorDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/doctors")
public class DoctorResource {
    
    private static final Logger logger = Logger.getLogger(DoctorResource.class.getName());
    private DoctorDAO doctorDAO = new DoctorDAO();
    
    /**
    * GET method to retrieve all doctors.
    * 
    * @return Response containing a list of doctors in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDoctors() throws InternalServerErrorException {
        try {
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            if (doctors.isEmpty()) {
                throw new ResourceNotFoundException("Doctor data not found.");
            }
            return Response.ok(doctors).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving all doctors.", e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * GET method to retrieve a doctor by ID.
    * 
    * @param id the ID of the doctor to retrieve
    * @return Response containing the doctor details in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctor(@PathParam("id") String id) throws InternalServerErrorException {
        try {
            Doctor doctor = doctorDAO.getDoctorById(id);
            if (doctor == null) {
                throw new ResourceNotFoundException("Doctor with id " + id + " not found.");
            }
            return Response.ok(doctor).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving doctor with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * GET method to retrieve doctors by specialization.
    * 
    * @param specialization the specialization of the doctors to retrieve
    * @return Response containing a list of doctors with the specified specialization in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/specialization/{specialization}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctorsBySpecialization(@PathParam("specialization") String specialization) throws InternalServerErrorException {
        try {
            List<Doctor> doctors = doctorDAO.getDoctorsBySpecialization(specialization);
            if (doctors.isEmpty()) {
                throw new ResourceNotFoundException("Doctors with specialization " + specialization + " not found.");
            }
            return Response.ok(doctors).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving doctors by specialization: " + specialization, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * POST method to add a new doctor.
    * 
    * @param doctor the doctor object to add
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) throws InternalServerErrorException {
        try {
            if (doctor == null || doctor.getName() == null || doctor.getContactInformation() == null || doctor.getAddress() == null || doctor.getSpecialization() == null) {
                throw new InvalidDataException("Invalid doctor data.");
            }
            doctorDAO.addDoctor(doctor);
            logger.info("Doctor successfully created.");
            return Response.status(Response.Status.CREATED).entity("Doctor successfully created.").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while adding doctor.", e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }

    /**
    * PUT method to update an existing doctor.
    * 
    * @param id the ID of the doctor to update
    * @param doctor the updated doctor object
    * @return Response indicating success or failure of the operation
    * @throws ResourceNotFoundException if the doctor with the specified ID is not found
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("id") String id, Doctor doctor) throws ResourceNotFoundException, InternalServerErrorException {
        try {
            if (doctor.getName() == null || doctor.getContactInformation() == null || doctor.getAddress() == null || doctor.getSpecialization() == null) {
                throw new InvalidDataException("Invalid doctor data.");
            }
            doctorDAO.updateDoctor(id, doctor);
            logger.info("Doctor successfully updated.");
            return Response.status(Response.Status.OK).entity("Doctor successfully updated.").build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Doctor not found with id: {0}", id);
            throw new ResourceNotFoundException("Doctor with id " + id + " not found.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while updating doctor with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }

    /**
    * DELETE method to delete a doctor by ID.
    * 
    * @param id the ID of the doctor to delete
    * @return Response indicating success or failure of the operation
    * @throws ResourceNotFoundException if the doctor with the specified ID is not found
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @DELETE
    @Path("/{id}")
    public Response deleteDoctor(@PathParam("id") String id) throws ResourceNotFoundException, InternalServerErrorException {
        try {
            doctorDAO.deleteDoctor(id);
            logger.info("Doctor successfully deleted.");
            return Response.status(Response.Status.NO_CONTENT).entity("Doctor successfully deleted.").build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "Doctor with id {0} not found.", id);
            throw new ResourceNotFoundException("Doctor with id " + id + " not found.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while deleting doctor with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
}

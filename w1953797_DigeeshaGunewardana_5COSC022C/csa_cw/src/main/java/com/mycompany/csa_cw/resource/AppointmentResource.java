package com.mycompany.csa_cw.resource;

import com.mycompany.csa_cw.exception.ResourceNotFoundException;
import com.mycompany.csa_cw.exception.InvalidDataException;
import com.mycompany.csa_cw.exception.InternalServerErrorException;
import com.mycompany.csa_cw.model.Appointment;
import com.mycompany.csa_cw.model.Patient;
import com.mycompany.csa_cw.model.Doctor;
import com.mycompany.csa_cw.dao.PatientDAO;
import com.mycompany.csa_cw.dao.DoctorDAO;
import com.mycompany.csa_cw.dao.AppointmentDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appointments")
public class AppointmentResource {
    
    private static final Logger logger = Logger.getLogger(AppointmentResource.class.getName());
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    
    /**
    * GET method to retrieve all appointments.
    * 
    * @return Response containing a list of appointments in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAppointments() throws InternalServerErrorException {
        try {
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            if (appointments.isEmpty()) {
                throw new ResourceNotFoundException("Appointment data not found.");
            }
            return Response.ok(appointments).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving all appointments.", e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * GET method to retrieve an appointment by ID.
    * 
    * @param id the ID of the appointment to retrieve
    * @return Response containing the appointment details in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointment(@PathParam("id") String id) throws InternalServerErrorException {
        try {
            Appointment appointment = appointmentDAO.getAppointmentById(id);
            if (appointment == null) {
                throw new ResourceNotFoundException("Appointment with id " + id + " not found.");
            }
            return Response.ok(appointment).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving appointment with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * GET method to retrieve appointments by date.
    * 
    * @param date the date of the appointments to retrieve
    * @return Response containing a list of appointments on the specified date in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/date/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentsByDate(@PathParam("date") String date) throws InternalServerErrorException {
        try {
            List<Appointment> appointments = appointmentDAO.getAppointmentsByDate(date);
            if (appointments.isEmpty()) {
                throw new ResourceNotFoundException("Appointments on date " + date + " not found.");
            }
            return Response.ok(appointments).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving appointments by date: " + date, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }

    /**
    * GET method to retrieve appointments by patient ID.
    * 
    * @param patientId the ID of the patient
    * @return Response containing a list of appointments for the specified patient in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentsByPatientId(@PathParam("patientId") String patientId) throws InternalServerErrorException {
        try {
            List<Appointment> appointments = appointmentDAO.getAppointmentsByPatientId(patientId);
            if (appointments.isEmpty()) {
                throw new ResourceNotFoundException("Appointments for patient with id: " + patientId + " not found.");
            }
            return Response.ok(appointments).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving appointments by patient ID: " + patientId, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * GET method to retrieve appointments by doctor ID.
    * 
    * @param doctorId the ID of the doctor
    * @return Response containing a list of appointments for the specified doctor in JSON format
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @GET
    @Path("/doctor/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentsByDoctorId(@PathParam("doctorId") String doctorId) throws InternalServerErrorException {
        try {
            List<Appointment> appointments = appointmentDAO.getAppointmentsByDoctorId(doctorId);
            if (appointments.isEmpty()) {
                throw new ResourceNotFoundException("Appointments for doctor with id: " + doctorId + " not found.");
            }
            return Response.ok(appointments).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while retrieving appointments by doctor ID: " + doctorId, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
    
    /**
    * POST method to schedule a new appointment.
    * 
    * @param appointment the appointment object to schedule
    * @param doctorId the ID of the doctor associated with the appointment
    * @param patientId the ID of the patient associated with the appointment
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response scheduleAppointment(Appointment appointment, @QueryParam("doctorId") String doctorId, @QueryParam("patientId") String patientId) throws InternalServerErrorException {
        try {
            // Check if appointment and IDs are provided
            if (appointment == null || appointment.getDate() == null || appointment.getTime() == null || doctorId == null || patientId == null) {
                throw new InvalidDataException("Appointment data invalid.");
            }

            // Retrieve doctor using doctorId
            DoctorDAO doctorDAO = new DoctorDAO();
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                throw new ResourceNotFoundException("Doctor with id: " + doctorId + " not found.");
            }

            // Retrieve patient using patientId
            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                throw new ResourceNotFoundException("Patient with id: " + patientId + " not found.");
            }

            // Set doctor and patient for the appointment
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);

            // Add appointment
            appointmentDAO.addAppointment(appointment);
            logger.info("Appointment successfully scheduled.");
            return Response.status(Response.Status.CREATED)
                    .entity("Appointment successfully scheduled.")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while scheduling appointment.", e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }

    /**
    * PUT method to update an existing appointment.
    * 
    * @param id the ID of the appointment to update
    * @param appointment the updated appointment object
    * @param doctorId the ID of the doctor associated with the appointment
    * @param patientId the ID of the patient associated with the appointment
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    * @throws ResourceNotFoundException if the appointment, doctor, or patient is not found
    * @throws InvalidDataException if the appointment data is invalid
    */   
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("id") String id, Appointment appointment, @QueryParam("doctorId") String doctorId, @QueryParam("patientId") String patientId) throws InternalServerErrorException, ResourceNotFoundException, InvalidDataException {
        try {
            // Check if appointment and IDs are provided
            if (appointment.getDate() == null || appointment.getTime() == null || doctorId == null || patientId == null) {
                throw new InvalidDataException("Appointment data invalid.");
            }

            // Retrieve doctor using doctorId
            DoctorDAO doctorDAO = new DoctorDAO();
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                throw new ResourceNotFoundException("Doctor with id: " + doctorId + " not found.");
            }

            // Retrieve patient using patientId
            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                throw new ResourceNotFoundException("Patient with id: " + patientId + " not found.");
            }

            // Set doctor and patient for the appointment
            appointment.setId(id); // Ensure the ID is set correctly
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);

            // Update appointment
            appointmentDAO.updateAppointment(id, appointment);
            logger.info("Appointment successfully updated.");
            return Response.status(Response.Status.OK)
                    .entity("Appointment successfully updated.")
                    .build();
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error occurred while updating appointment with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }

    /**
    * DELETE method to delete an appointment.
    * 
    * @param id the ID of the appointment to delete
    * @return Response indicating success or failure of the operation
    * @throws InternalServerErrorException if an internal server error occurs
    */   
    @DELETE
    @Path("/{id}")
    public Response deleteAppointment(@PathParam("id") String id) throws InternalServerErrorException {
        try {
            appointmentDAO.deleteAppointment(id);
            logger.info("Appointment successfully deleted.");
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("Appointment successfully deleted.")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while deleting appointment with ID: " + id, e);
            throw new InternalServerErrorException("Internal server error occurred.");
        }
    }
}

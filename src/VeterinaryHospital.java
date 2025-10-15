import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class VeterinaryHospital {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> schedule;

    // Define a default duration for appointments, e.g., 30 minutes.
    private static final long DEFAULT_APPOINTMENT_DURATION_MINUTES = 30;

    public VeterinaryHospital(List<Doctor> doctors, List<Patient> patients, List<Appointment> schedule) {
        this.doctors = doctors;
        this.patients = patients;
        this.schedule = schedule;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Appointment> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Appointment> schedule) {
        this.schedule = schedule;
    }

    /**
     * Schedules a new appointment with a default duration.
     * This is a convenience method that calculates the end time automatically.
     *
     * @param patient   The patient for the appointment.
     * @param doctor    The doctor for the appointment.
     * @param startTime The desired start time of the appointment.
     * @return The created Appointment object if successful, or null if the time slot is unavailable.
     */
    public Appointment scheduleAppointment(Patient patient, Doctor doctor, LocalDateTime startTime) {
        LocalDateTime endTime = startTime.plus(DEFAULT_APPOINTMENT_DURATION_MINUTES, ChronoUnit.MINUTES);
        return scheduleAppointment(patient, doctor, startTime, endTime);
    }

    /**
     * Schedules a new appointment with a specific start and end time.
     * This is the core scheduling method containing all validation and state-updating logic.
     *
     * @param patient   The patient for the appointment.
     * @param doctor    The doctor for the appointment.
     * @param startTime The desired start time of the appointment.
     * @param endTime   The desired end time of the appointment.
     * @return The created Appointment object if successful, or null if the time slot is unavailable.
     */
    public Appointment scheduleAppointment(Patient patient, Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) {
        if (!isDoctorAvailable(doctor, startTime, endTime)) {
            System.out.println("Doctor " + doctor.getName() + " is not available between " + startTime + " and " + endTime);
            return null;
        }

        Billing newBilling = new Billing(100.00, false, null); // Base fee
        Appointment newAppointment = new Appointment(patient, doctor, startTime, endTime, patient.getTutor(), newBilling);
        newBilling.setAppointment(newAppointment);

        schedule.add(newAppointment);
        if (patient.getMedicalHistory() != null) {
            patient.getMedicalHistory().add(newAppointment);
        }

        return newAppointment;
    }

    /**
     * Checks if a doctor is available for a given time slot.
     * The check prevents double-booking by ensuring the new appointment does not overlap with any existing appointments.
     *
     * @param doctor        The doctor to check availability for.
     * @param newStartTime  The start time of the proposed new appointment.
     * @param newEndTime    The end time of the proposed new appointment.
     * @return true if the doctor is available, false otherwise.
     */
    private boolean isDoctorAvailable(Doctor doctor, LocalDateTime newStartTime, LocalDateTime newEndTime) {
        for (Appointment existingAppointment : this.schedule) {
            if (existingAppointment.getDoctor().equals(doctor)) {
                // An overlap occurs if the new appointment's start time is before the existing one ends,
                // AND the new appointment's end time is after the existing one starts.
                // A.startTime < B.endTime  &&  A.endTime > B.startTime
                if (newStartTime.isBefore(existingAppointment.getEndTime()) && newEndTime.isAfter(existingAppointment.getStartTime())) {
                    return false; // Found a conflicting appointment.
                }
            }
        }
        return true; // No conflicts found.
    }
}

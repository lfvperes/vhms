import com.vhms.model.Appointment;
import com.vhms.model.Doctor;
import com.vhms.model.Patient;
import com.vhms.model.Tutor;
import com.vhms.model.species.Feline; // Import the new Feline class
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) throws Exception {
        // Create a new Veterinary Hospital instance
        VeterinaryHospital hospital = new VeterinaryHospital();

        // Create and add a doctor to the hospital
        Doctor drSmith = new Doctor("smith@vhms.com", 1L, "Dr. Smith", "11995550101", "Cardiology");
        hospital.addDoctor(drSmith);

        // Create a tutor for a patient
        Tutor johnDoe = new Tutor("John Doe", 101L, "john.doe@email.com", "11995551234");

        // Create a new patient using the Feline subclass
        Patient fluffy = new Feline((byte) 5, "Persian", 12345L, true, true, "Fluffy", false, johnDoe, 5.2f);
        hospital.addPatient(fluffy);

        // Schedule an appointment for the patient with the doctor
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(30);
        Appointment newAppointment = hospital.scheduleAppointment(fluffy, drSmith, appointmentTime);

        // Print out the schedule to verify
        System.out.println(newAppointment.getSummary());
        hospital.printSchedule();
    }
}

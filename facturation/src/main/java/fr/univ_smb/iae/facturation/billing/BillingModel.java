package fr.univ_smb.iae.facturation.billing;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model class representing a billing entity stored in the MongoDB collection "billings".
 */
@Document(collection = "billings")
public class BillingModel {
    @Id
    private String id; // Unique ID of the billing record
    private String patientId; // Patient ID (linked to gestion_rdv)
    private String patientName; // Patient's name
    private String appointmentId; // Associated appointment ID
    private double amount; // Billed amount
    private String status; // Billing status ("Paid", "Pending", etc.)

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

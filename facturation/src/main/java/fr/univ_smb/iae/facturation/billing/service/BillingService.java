package fr.univ_smb.iae.facturation.billing.service;

import fr.univ_smb.iae.facturation.billing.BillingModel;
import fr.univ_smb.iae.facturation.billing.dto.AppointmentDTO;
import fr.univ_smb.iae.facturation.billing.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final MongoTemplate billingMongoTemplate; // Default for Billing DB
    private final MongoTemplate gestionRdvMongoTemplate; // For Gestion RDV DB
    private final Logger logger = Logger.getLogger(BillingService.class.getName());

    @Autowired
    public BillingService(
            BillingRepository billingRepository,
            @Qualifier("billingMongoTemplate") MongoTemplate billingMongoTemplate,
            @Qualifier("gestionRdvMongoTemplate") MongoTemplate gestionRdvMongoTemplate) {
        this.billingRepository = billingRepository;
        this.billingMongoTemplate = billingMongoTemplate;
        this.gestionRdvMongoTemplate = gestionRdvMongoTemplate;
    }

    /**
     * Fetch all appointments from the Gestion RDV database.
     */
    public List<AppointmentDTO> fetchAllAppointments() {
        logger.info("Fetching all appointments from Gestion RDV database.");
        Query query = new Query(); // Fetch all records
        return gestionRdvMongoTemplate.find(query, AppointmentDTO.class, "appointments");
    }

    /**
     * Create billings from all appointments in Gestion RDV with random amounts.
     */
    public List<BillingModel> createBillingsFromAppointments() {
        logger.info("Creating billings from appointments in Gestion RDV.");
        List<AppointmentDTO> appointments = fetchAllAppointments();

        if (appointments.isEmpty()) {
            logger.warning("No appointments found in Gestion RDV database.");
            return List.of();
        }

        Random random = new Random(); // Random generator for bill amounts
        List<BillingModel> createdBillings = appointments.stream().map(appointment -> {
            BillingModel billing = new BillingModel();
            billing.setPatientId(appointment.getPatientId());
            billing.setPatientName(appointment.getPatientName());
            billing.setAppointmentId(appointment.getId());
            billing.setAmount(50 + random.nextInt(151)); // Random amount between 50 and 200
            billing.setStatus("Pending"); // Initial status
            return billingRepository.save(billing);
        }).toList();

        logger.info("Created " + createdBillings.size() + " billing records.");
        return createdBillings;
    }

    /**
     * Fetch all billing records from the Billing database.
     */
    public List<BillingModel> getAllBillings() {
        logger.info("Fetching all billing records from Billing database.");
        return billingRepository.findAll();
    }

    /**
     * Fetch a specific billing record by its ID.
     */
    public Optional<BillingModel> getBillingById(String id) {
        logger.info("Fetching billing record by ID: " + id);
        return billingRepository.findById(id);
    }

    /**
     * Fetch all billing records for a specific patient.
     */
    public List<BillingModel> getBillingsByPatientId(String patientId) {
        logger.info("Fetching billing records for patient ID: " + patientId);
        return billingRepository.findByPatientId(patientId);
    }

    /**
     * Fetch all billing records by their status.
     */
    public List<BillingModel> getBillingsByStatus(String status) {
        logger.info("Fetching billing records with status: " + status);
        return billingRepository.findByStatus(status);
    }

    /**
     * Create a new billing record manually.
     */
    public BillingModel createBilling(BillingModel billing) {
        logger.info("Creating a new billing record for patient: " + billing.getPatientName());
        return billingRepository.save(billing);
    }

    /**
     * Update an existing billing record.
     */
    public BillingModel updateBilling(String id, BillingModel updatedBilling) {
        logger.info("Updating billing record with ID: " + id);
        Optional<BillingModel> existingBilling = billingRepository.findById(id);

        if (existingBilling.isPresent()) {
            BillingModel billing = existingBilling.get();
            billing.setPatientId(updatedBilling.getPatientId());
            billing.setPatientName(updatedBilling.getPatientName());
            billing.setAmount(updatedBilling.getAmount());
            billing.setStatus(updatedBilling.getStatus());
            return billingRepository.save(billing);
        } else {
            logger.warning("Billing record with ID: " + id + " not found.");
            return null;
        }
    }

    /**
     * Delete a billing record by its ID.
     */
    public boolean deleteBilling(String id) {
        logger.info("Deleting billing record with ID: " + id);
        Optional<BillingModel> billing = billingRepository.findById(id);

        if (billing.isPresent()) {
            billingRepository.deleteById(id);
            logger.info("Billing record with ID: " + id + " successfully deleted.");
            return true;
        } else {
            logger.warning("Billing record with ID: " + id + " not found.");
            return false;
        }
    }
}

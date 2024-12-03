package fr.univ_smb.iae.facturation.billing.repository;

import fr.univ_smb.iae.facturation.billing.BillingModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Billing operations.
 */
@Repository
public interface BillingRepository extends MongoRepository<BillingModel, String> {

    /**
     * Fetch all billings for a given patient ID.
     *
     * @param patientId The patient ID.
     * @return List of billings related to the patient.
     */
    List<BillingModel> findByPatientId(String patientId);

    /**
     * Fetch all billings by their status.
     *
     * @param status The status of the billing ("Paid", "Pending", etc.).
     * @return List of billings with the specified status.
     */
    List<BillingModel> findByStatus(String status);
}

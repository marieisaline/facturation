package fr.univ_smb.iae.facturation.billing.controller;

import fr.univ_smb.iae.facturation.billing.BillingModel;
import fr.univ_smb.iae.facturation.billing.dto.AppointmentDTO;
import fr.univ_smb.iae.facturation.billing.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/billings")
public class BillingController {

    private final BillingService billingService;
    private final Logger logger = Logger.getLogger(BillingController.class.getName());

    @Autowired
    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        logger.info("Fetching all appointments from Gestion de Rendez-Vous.");
        List<AppointmentDTO> appointments = billingService.fetchAllAppointments();
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(appointments);
    }

    @PostMapping("/create-from-appointments")
    public ResponseEntity<List<BillingModel>> createBillingsFromAppointments() {
        logger.info("Creating billings from appointments in Gestion de Rendez-Vous.");
        List<BillingModel> createdBillings = billingService.createBillingsFromAppointments();
        return createdBillings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(createdBillings);
    }

    @GetMapping
    public ResponseEntity<List<BillingModel>> getAllBillings() {
        logger.info("Fetching all billing records.");
        List<BillingModel> billings = billingService.getAllBillings();
        return billings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(billings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingModel> getBillingById(@PathVariable String id) {
        logger.info("Fetching billing record by ID: " + id);
        Optional<BillingModel> billing = billingService.getBillingById(id);
        return billing.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BillingModel> createBilling(@RequestBody BillingModel billing) {
        logger.info("Creating a new billing record.");
        return ResponseEntity.ok(billingService.createBilling(billing));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillingModel> updateBilling(@PathVariable String id, @RequestBody BillingModel updatedBilling) {
        logger.info("Updating billing record with ID: " + id);
        BillingModel updated = billingService.updateBilling(id, updatedBilling);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilling(@PathVariable String id) {
        logger.info("Deleting billing record with ID: " + id);
        boolean isDeleted = billingService.deleteBilling(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

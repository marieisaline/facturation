package fr.univ_smb.iae.facturation.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${billing.mongodb.uri}")
    private String billingDbUri;

    @Value("${gestion.rdv.mongodb.uri}")
    private String gestionRdvDbUri;

    /**
     * MongoTemplate for Billing database.
     */
    @Bean(name = "billingMongoTemplate")
    public MongoTemplate billingMongoTemplate() {
        MongoClient mongoClient = MongoClients.create(billingDbUri);
        return new MongoTemplate(mongoClient, "billing_db");
    }

    /**
     * MongoTemplate for Gestion RDV database.
     */
    @Bean(name = "gestionRdvMongoTemplate")
    public MongoTemplate gestionRdvMongoTemplate() {
        MongoClient mongoClient = MongoClients.create(gestionRdvDbUri);
        return new MongoTemplate(mongoClient, "gestion_rdv_db");
    }

    /**
     * Default MongoTemplate (used for repositories without explicit configuration).
     * Sets billingMongoTemplate as the default.
     */
    @Bean
    public MongoTemplate mongoTemplate() {
        return billingMongoTemplate(); // Default template points to the billing database
    }
}

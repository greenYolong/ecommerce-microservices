package com.ecommerce.paiement.client;


import com.ecommerce.paiement.dto.CommandeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "commande-service")
public interface CommandeClient {

    @GetMapping("/api/v1/commandes/{id}")
    CommandeDTO getCommandeById(@PathVariable("id") Long id);
}

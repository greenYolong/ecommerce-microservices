package com.ecommerce.commande.client;

import com.ecommerce.commande.dto.ProduitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogue-service")
public interface CatalogueClient {

    @GetMapping("/api/v1/produits/{id}")
    ProduitDTO getProduitById(@PathVariable("id") Long id);
}

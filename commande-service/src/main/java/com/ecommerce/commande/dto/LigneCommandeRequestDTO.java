package com.ecommerce.commande.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LigneCommandeRequestDTO {

    @NotNull
    private Long produitId;

    @Positive
    private Integer quantite;

    public LigneCommandeRequestDTO() {}

    public LigneCommandeRequestDTO(Long produitId, Integer quantite) {
        this.produitId = produitId;
        this.quantite = quantite;
    }

    public Long getProduitId() { return produitId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
}

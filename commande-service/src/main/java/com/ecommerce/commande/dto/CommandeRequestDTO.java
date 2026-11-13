package com.ecommerce.commande.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class CommandeRequestDTO {

    @NotEmpty
    @Valid
    private List<LigneCommandeRequestDTO> lignes;

    public CommandeRequestDTO() {}

    public CommandeRequestDTO(List<LigneCommandeRequestDTO> lignes) {
        this.lignes = lignes;
    }

    public List<LigneCommandeRequestDTO> getLignes() { return lignes; }
    public void setLignes(List<LigneCommandeRequestDTO> lignes) { this.lignes = lignes; }
}

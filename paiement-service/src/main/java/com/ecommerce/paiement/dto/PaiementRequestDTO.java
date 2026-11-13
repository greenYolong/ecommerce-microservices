package com.ecommerce.paiement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaiementRequestDTO {

    @NotNull
    private Long commandeId;

    @NotBlank
    private String modePaiement;

    public PaiementRequestDTO() {}

    public PaiementRequestDTO(Long commandeId, String modePaiement) {
        this.commandeId = commandeId;
        this.modePaiement = modePaiement;
    }

    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }
}

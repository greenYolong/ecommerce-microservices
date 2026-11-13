package com.ecommerce.paiement.dto;

import com.ecommerce.paiement.domain.Paiement;
import org.springframework.stereotype.Component;

@Component
public class PaiementMapper {

    public PaiementResponseDTO toDTO(Paiement paiement) {
        PaiementResponseDTO dto = new PaiementResponseDTO();
        dto.setId(paiement.getId());
        dto.setCommandeId(paiement.getCommandeId());
        dto.setMontant(paiement.getMontant());
        dto.setModePaiement(paiement.getModePaiement().name());
        dto.setStatut(paiement.getStatut().name());
        dto.setDatePaiement(paiement.getDatePaiement());
        return dto;
    }
}

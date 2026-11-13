package com.ecommerce.paiement.service;

import com.ecommerce.paiement.client.CommandeClient;
import com.ecommerce.paiement.domain.Paiement;
import com.ecommerce.paiement.dto.CommandeDTO;
import com.ecommerce.paiement.dto.PaiementMapper;
import com.ecommerce.paiement.dto.PaiementRequestDTO;
import com.ecommerce.paiement.dto.PaiementResponseDTO;
import com.ecommerce.paiement.exception.ResourceNotFoundException;
import com.ecommerce.paiement.repository.PaiementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaiementServiceTest {

    @Mock
    private PaiementRepository paiementRepository;

    @Mock
    private PaiementMapper paiementMapper;

    @Mock
    private CommandeClient commandeClient;

    @InjectMocks
    private PaiementService paiementService;

    @Test
    void createPaiement_reprendLeMontantDeLaCommandeViaFeign() {
        CommandeDTO commande = new CommandeDTO(7L, null, "CONFIRMEE", 149.90);
        when(commandeClient.getCommandeById(7L)).thenReturn(commande);

        PaiementRequestDTO request = new PaiementRequestDTO(7L, "CARTE_BANCAIRE");

        when(paiementRepository.save(any(Paiement.class))).thenAnswer(inv -> inv.getArgument(0));
        when(paiementMapper.toDTO(any(Paiement.class))).thenReturn(new PaiementResponseDTO());

        paiementService.createPaiement(request);

        ArgumentCaptor<Paiement> captor = ArgumentCaptor.forClass(Paiement.class);
        verify(paiementRepository).save(captor.capture());
        Paiement saved = captor.getValue();

        assertEquals(149.90, saved.getMontant());
        assertEquals(Paiement.ModePaiement.CARTE_BANCAIRE, saved.getModePaiement());
        assertEquals(Paiement.StatutPaiement.ACCEPTE, saved.getStatut());
        assertEquals(7L, saved.getCommandeId());
    }

    @Test
    void getPaiementById_inexistant_lanceResourceNotFoundException() {
        when(paiementRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paiementService.getPaiementById(3L));
    }

    @Test
    void createPaiement_modeInvalide_lanceIllegalArgumentException() {
        CommandeDTO commande = new CommandeDTO(8L, null, "CONFIRMEE", 10.0);
        when(commandeClient.getCommandeById(8L)).thenReturn(commande);

        PaiementRequestDTO request = new PaiementRequestDTO(8L, "BITCOIN");

        assertThrows(IllegalArgumentException.class, () -> paiementService.createPaiement(request));
    }

    @Test
    void deletePaiement_inexistant_lanceResourceNotFoundException() {
        when(paiementRepository.existsById(4L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> paiementService.deletePaiement(4L));
    }
}

package com.ecommerce.commande.service;

import com.ecommerce.commande.client.CatalogueClient;
import com.ecommerce.commande.domain.Commande;
import com.ecommerce.commande.dto.CommandeRequestDTO;
import com.ecommerce.commande.dto.CommandeResponseDTO;
import com.ecommerce.commande.dto.CommandeMapper;
import com.ecommerce.commande.dto.LigneCommandeRequestDTO;
import com.ecommerce.commande.dto.ProduitDTO;
import com.ecommerce.commande.exception.ResourceNotFoundException;
import com.ecommerce.commande.repository.CommandeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandeServiceTest {

    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private CommandeMapper commandeMapper;

    @Mock
    private CatalogueClient catalogueClient;

    @InjectMocks
    private CommandeService commandeService;

    @Test
    void createCommande_calculeLeMontantTotalViaLeCatalogue() {
        ProduitDTO produit = new ProduitDTO(10L, "Clavier", "desc", 25.0, 100);
        when(catalogueClient.getProduitById(10L)).thenReturn(produit);

        LigneCommandeRequestDTO ligne = new LigneCommandeRequestDTO(10L, 3);
        CommandeRequestDTO request = new CommandeRequestDTO(List.of(ligne));

        when(commandeRepository.save(any(Commande.class))).thenAnswer(inv -> inv.getArgument(0));
        when(commandeMapper.toDTO(any(Commande.class))).thenReturn(new CommandeResponseDTO());

        commandeService.createCommande(request);

        ArgumentCaptor<Commande> captor = ArgumentCaptor.forClass(Commande.class);
        verify(commandeRepository).save(captor.capture());
        Commande saved = captor.getValue();

        // 25.0 (prix du produit via Feign) * 3 (quantité) = 75.0
        assertEquals(75.0, saved.getMontantTotal());
        assertEquals(1, saved.getLignes().size());
        assertEquals("Clavier", saved.getLignes().get(0).getProduitNom());
        assertEquals(Commande.StatutCommande.EN_ATTENTE, saved.getStatut());
    }

    @Test
    void getCommandeById_inexistante_lanceResourceNotFoundException() {
        when(commandeRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commandeService.getCommandeById(5L));
    }

    @Test
    void updateStatut_valeurInvalide_lanceIllegalArgumentException() {
        Commande commande = new Commande();
        commande.setStatut(Commande.StatutCommande.EN_ATTENTE);
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));

        assertThrows(IllegalArgumentException.class, () -> commandeService.updateStatut(1L, "INCONNU"));
    }

    @Test
    void deleteCommande_inexistante_lanceResourceNotFoundException() {
        when(commandeRepository.existsById(7L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> commandeService.deleteCommande(7L));
    }
}

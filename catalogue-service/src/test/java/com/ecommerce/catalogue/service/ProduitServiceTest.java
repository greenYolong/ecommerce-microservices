package com.ecommerce.catalogue.service;

import com.ecommerce.catalogue.domain.Produit;
import com.ecommerce.catalogue.dto.ProduitMapper;
import com.ecommerce.catalogue.dto.ProduitRequestDTO;
import com.ecommerce.catalogue.dto.ProduitResponseDTO;
import com.ecommerce.catalogue.exception.ResourceNotFoundException;
import com.ecommerce.catalogue.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ProduitMapper produitMapper;

    @InjectMocks
    private ProduitService produitService;

    @Test
    void getProduitById_existant_renvoieLeDTO() {
        Produit produit = new Produit(1L, "Clavier", "Clavier mécanique", 49.9, 10);
        ProduitResponseDTO dto = new ProduitResponseDTO(1L, "Clavier", "Clavier mécanique", 49.9, 10);

        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        when(produitMapper.toDTO(produit)).thenReturn(dto);

        ProduitResponseDTO result = produitService.getProduitById(1L);

        assertEquals("Clavier", result.getNom());
        assertEquals(49.9, result.getPrix());
    }

    @Test
    void getProduitById_inexistant_lanceResourceNotFoundException() {
        when(produitRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produitService.getProduitById(99L));
    }

    @Test
    void createProduit_sauvegardeEtRenvoieLeDTO() {
        ProduitRequestDTO request = new ProduitRequestDTO("Souris", "Souris sans fil", 19.9, 25);
        Produit entity = new Produit(null, "Souris", "Souris sans fil", 19.9, 25);
        Produit saved = new Produit(2L, "Souris", "Souris sans fil", 19.9, 25);
        ProduitResponseDTO responseDto = new ProduitResponseDTO(2L, "Souris", "Souris sans fil", 19.9, 25);

        when(produitMapper.toEntity(request)).thenReturn(entity);
        when(produitRepository.save(entity)).thenReturn(saved);
        when(produitMapper.toDTO(saved)).thenReturn(responseDto);

        ProduitResponseDTO result = produitService.createProduit(request);

        assertEquals(2L, result.getId());
        verify(produitRepository).save(entity);
    }

    @Test
    void deleteProduit_inexistant_lanceResourceNotFoundExceptionEtNeSupprimePas() {
        when(produitRepository.existsById(42L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> produitService.deleteProduit(42L));
        verify(produitRepository, never()).deleteById(any());
    }
}

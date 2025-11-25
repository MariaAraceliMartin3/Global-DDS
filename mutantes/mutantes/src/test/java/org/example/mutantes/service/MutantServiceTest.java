package org.example.mutantes.service;

import org.example.mutantes.entity.DnaRecord;
import org.example.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTest {
    @Mock
    private MutantDetector mutantDetector;  // Mock (simulado)

    @Mock
    private DnaRecordRepository dnaRecordRepository;  // Mock (simulado)

    @InjectMocks
    private MutantService mutantService;  // Clase bajo prueba (recibe mocks)

    private String[] mutantDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    private String[] humanDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

    //Test 1: Analizar ADN mutante y guardar en BD
    @Test
    @DisplayName("Debe analizar ADN mutante y guardarlo en DB")
    void testAnalyzeMutantDnaAndSave() {
        System.out.println("========== Test 1 ==========");
        System.out.println("Analizar ADN mutante y guardar en BD");

        // ARRANGE (Preparar)
        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());  // No existe en BD
        when(mutantDetector.isMutant(mutantDna))
                .thenReturn(true);  // Es mutante
        when(dnaRecordRepository.save(any(DnaRecord.class)))
                .thenReturn(new DnaRecord());  // Guardado exitoso

        // ACT (Actuar)
        boolean result = mutantService.analyzeDna(mutantDna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        // ASSERT (Afirmar)
        assertTrue(result);

        // VERIFY (Verificar interacciones)
        verify(mutantDetector, times(1)).isMutant(mutantDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));

        System.out.println("Mutante guardado exitosamente");
    }

    //Test 2: Analizar ADN humano y guardar en BD
    @Test
    @DisplayName("Debe analizar ADN humano y guardarlo en DB")
    void testAnalyzeHumanDnaAndSave() {
        System.out.println("========== Test 2 ==========");
        System.out.println("Analizar ADN humano y guardar en BD");

        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(humanDna))
                .thenReturn(false);  // Es humano
        when(dnaRecordRepository.save(any(DnaRecord.class)))
                .thenReturn(new DnaRecord());

        boolean result = mutantService.analyzeDna(humanDna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertFalse(result);
        verify(mutantDetector, times(1)).isMutant(humanDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));

        System.out.println("Humano guardado exitosamente");
    }

    //Test 3: Retornar Resultado Cacheado
    @Test
    @DisplayName("Debe retornar resultado cacheado si el ADN ya fue analizado")
    void testReturnCachedResultForAnalyzedDna() {
        System.out.println("========== Test 3 ==========");
        System.out.println("Retornar resultado cacheado si el ADN ya fue analizado");

        // ARRANGE
        DnaRecord cachedRecord = new DnaRecord("somehash", true);
        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.of(cachedRecord));  // YA existe en BD

        // ACT
        boolean result = mutantService.analyzeDna(mutantDna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado:" + result);
        // ASSERT
        assertTrue(result);

        // VERIFY - NO debe llamar al detector ni guardar
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());

        System.out.println("Caché funcionando exitosamente");
    }

    //Test 4: Generar Hash consistente
    @Test
    @DisplayName("Debe generar hash consistente para el mismo ADN")
    void testConsistentHashGeneration() {
        System.out.println("========== Test 4 ==========");
        System.out.println("Generar hash consistente para el mismo ADN");

        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(any()))
                .thenReturn(true);

        mutantService.analyzeDna(mutantDna);
        mutantService.analyzeDna(mutantDna);  // Mismo DNA otra vez

        // Debe buscar por el mismo hash ambas veces
        verify(dnaRecordRepository, times(2)).findByDnaHash(anyString());

        System.out.println("Hash consistente funcionando exitosamente");
    }

    //Test 5: Guardar con Hash Correcto
    @Test
    @DisplayName("Debe guardar registro con hash correcto")
    void testSavesRecordWithCorrectHash() {
        System.out.println("========== Test 5 ==========");
        System.out.println("Verificar hash SHA-256 correcto al guardar");
        
        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(mutantDna))
                .thenReturn(true);

        mutantService.analyzeDna(mutantDna);

        verify(dnaRecordRepository).save(argThat(record ->
                record.getDnaHash() != null &&
                        record.getDnaHash().length() == 64 &&  // SHA-256 = 64 chars hex
                        record.isMutant()
        ));

        System.out.println("Hash SHA-256 válido");
    }
}

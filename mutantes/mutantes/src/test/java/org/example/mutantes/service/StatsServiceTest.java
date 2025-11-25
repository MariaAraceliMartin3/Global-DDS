package org.example.mutantes.service;

import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {
    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    //Test 1: Estadísticas correctamente
    @Test
    @DisplayName("Debe calcular estadísticas correctamente")
    void testGetStatsWithData() {
        System.out.println("========== Test 1 ==========");
        System.out.println("Estadísticas calculadas correctamente");

        // ARRANGE
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        // ACT
        StatsResponse stats = statsService.getStats();

        // ASSERT
        assertEquals(40, stats.getCount_mutant_dna());
        assertEquals(100, stats.getCount_human_dna());
        assertEquals(0.4, stats.getRatio(), 0.001);  // 40/100 = 0.4

        System.out.println("- Mutantes: " + stats.getCount_mutant_dna());
        System.out.println("- Humanos: " + stats.getCount_human_dna());
        System.out.println("- Ratio: " + stats.getRatio());
        System.out.println("Cálculo correcto");
    }

    //Test 2: Sin humanos
    @Test
    @DisplayName("Debe retornar ratio 0 cuando no hay humanos")
    void testGetStatsWithNoHumans() {
        System.out.println("========== Test 2 ==========");
        System.out.println("Para su correcto funcionamiento debe retornar 0 cuando no hay humanos");

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.getStats();

        assertEquals(10, stats.getCount_mutant_dna());
        assertEquals(0, stats.getCount_human_dna());
        assertEquals(10.0, stats.getRatio(), 0.001);  // Caso especial

        System.out.println("- Mutantes: " + stats.getCount_mutant_dna());
        System.out.println("- Humanos: " + stats.getCount_human_dna());
        System.out.println("- Ratio: " + stats.getRatio());
        System.out.println("Cálculo correcto");
    }

    //Test 3: Sin datos
    @Test
    @DisplayName("Debe retornar ratio 0 cuando no hay datos")
    void testGetStatsWithNoData() {
        System.out.println("========== Test 3 ==========");
        System.out.println("Para su correcto funcionamiento debe retornar 0 cuando no hay datos");

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.getStats();

        assertEquals(0, stats.getCount_mutant_dna());
        assertEquals(0, stats.getCount_human_dna());
        assertEquals(0.0, stats.getRatio(), 0.001);

        System.out.println("- Mutantes: " + stats.getCount_mutant_dna());
        System.out.println("- Humanos: " + stats.getCount_human_dna());
        System.out.println("- Ratio: " + stats.getRatio());
        System.out.println("Cálculo correcto");
    }

    //Test 4: Ratio con decimales
    @Test
    @DisplayName("Debe calcular ratio con decimales correctamente")
    void testGetStatsWithDecimalRatio() {
        System.out.println("========== Test 4 ==========");
        System.out.println("Debe calcular ratio con decimales correctamente");

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(1L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(3L);

        StatsResponse stats = statsService.getStats();

        assertEquals(1, stats.getCount_mutant_dna());
        assertEquals(3, stats.getCount_human_dna());
        assertEquals(0.333, stats.getRatio(), 0.001);  // 1/3 = 0.333...

        System.out.println("- Mutantes: " + stats.getCount_mutant_dna());
        System.out.println("- Humanos: " + stats.getCount_human_dna());
        System.out.println("- Ratio: " + stats.getRatio());
        System.out.println("Cálculo correcto");
    }

    //Test 5: Cantidades iguales
    @Test
    @DisplayName("Debe retornar ratio 1.0 cuando hay igual cantidad")
    void testGetStatsWithEqualCounts() {
        System.out.println("========== Test 5 ==========");
        System.out.println("Para su correcto funcionamiento debe retornar ratio 1.0 cuando hay igual cantidad");

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(50L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(50L);

        StatsResponse stats = statsService.getStats();

        assertEquals(50, stats.getCount_mutant_dna());
        assertEquals(50, stats.getCount_human_dna());
        assertEquals(1.0, stats.getRatio(), 0.001);  // 50/50 = 1.0

        System.out.println("- Mutantes: " + stats.getCount_mutant_dna());
        System.out.println("- Humanos: " + stats.getCount_human_dna());
        System.out.println("- Ratio: " + stats.getRatio());
        System.out.println("Cálculo correcto");
    }

    //Test 6: Grandes cantidades
    @Test
    @DisplayName("Debe manejar grandes cantidades de datos")
    void testGetStatsWithLargeNumbers() {
        System.out.println("========== Test 6 ==========");
        System.out.println("Para su correcto funcionamiento debe poder manejar grandes cantidades de datos");

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(1000000L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(2000000L);

        StatsResponse stats = statsService.getStats();

        assertEquals(1000000, stats.getCount_mutant_dna());
        assertEquals(2000000, stats.getCount_human_dna());
        assertEquals(0.5, stats.getRatio(), 0.001);  // 1M / 2M = 0.5

        System.out.println("- Mutantes: " + stats.getCount_mutant_dna());
        System.out.println("- Humanos: " + stats.getCount_human_dna());
        System.out.println("- Ratio: " + stats.getRatio());
        System.out.println("Cálculo correcto");
    }
}

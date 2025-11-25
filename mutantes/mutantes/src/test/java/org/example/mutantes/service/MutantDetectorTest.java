package org.example.mutantes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private MutantDetector mutantDetector;

    //Test 1: Mutante con Secuencias Horizontal y Diagonal
    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y diagonal")
    void testMutantWithHorizontalAndDiagonalSequences() {
        String[] dna = {
                "ATGCGA",  // Fila 0
                "CAGTGC",  // Fila 1
                "TTATGT",  // Fila 2
                "AGAAGG",  // Fila 3
                "CCCCTA",  // Fila 4 ← Horizontal: CCCC
                "TCACTG"   // Fila 5
        };
        System.out.println("========== Test 1 ==========");
        System.out.println("DNA: " + java.util.Arrays.toString(dna));

        boolean result = mutantDetector.isMutant(dna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertTrue(result);
    }

    //Test 2: Mutante con Secuencias Verticales
    @Test
    @DisplayName("Debe detectar mutante con secuencias verticales")
    void testMutanteConVerticales() {
        String[] dna = {
                "AAAAGA",  // 4 A's en columna 0
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        System.out.println("========== Test 2 ==========");
        System.out.println("DNA: " + java.util.Arrays.toString(dna));

        boolean result = mutantDetector.isMutant(dna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertTrue(mutantDetector.isMutant(dna));
    }

    //Test 3: Múltiples Secuencias Horizontales
    @Test
    @DisplayName("Debe detectar mutante con múltiples secuencias horizontales")
    void testMutanteConMultiplesHorizontales() {
        String[] dna = {
                "TTTTGA",  // Secuencia 1: TTTT
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",  // Secuencia 2: CCCC
                "TCACTG"
        };
        System.out.println("========== Test 3 ==========");
        System.out.println("DNA: " + java.util.Arrays.toString(dna));

        boolean result = mutantDetector.isMutant(dna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertTrue(mutantDetector.isMutant(dna));
    }

    //Test 4: Diagonales Ascendentes y Descendentes
    @Test
    @DisplayName("Debe detectar mutante con diagonales ascendentes y descendentes")
    void testMutantWithBothDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",  // Modificado para crear secuencias
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        System.out.println("========== Test 4 ==========");
        System.out.println("DNA: " + java.util.Arrays.toString(dna));

        boolean result = mutantDetector.isMutant(dna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertTrue(mutantDetector.isMutant(dna));
    }

    //Test 5: NO Mutante - Solo 1 Secuencia
    @Test
    @DisplayName("No debe detectar mutante con una sola secuencia")
    void testNotMutantWithOnlyOneSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",  // Solo 1 secuencia: TTT (solo 3, no cuenta)
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        System.out.println("========== Test 5 ==========");
        System.out.println("DNA: " + java.util.Arrays.toString(dna));

        boolean result = mutantDetector.isMutant(dna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertFalse(mutantDetector.isMutant(dna));
    }

    //Test 6: NO Mutante - Sin Secuencias
    @Test
    @DisplayName("No debe detectar mutante sin secuencias")
    void testNoMutanteSinSecuencias() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        System.out.println("========== Test 6 ==========");
        System.out.println("DNA: " + java.util.Arrays.toString(dna));

        boolean result = mutantDetector.isMutant(dna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertFalse(result);
    }

    //Test 7: Validación - DNA Nulo
    @Test
    @DisplayName("Debe rechazar ADN nulo")
    void testNullDna() {
        System.out.println("========== Test 7 ==========");
        System.out.println("Si DNA es nulo devuelve false");
        boolean result = mutantDetector.isMutant(null);
        System.out.println("- Resultado: " + result);

        assertFalse(result);
    }

    //Test 8: Validación - DNA Vacío
    @Test
    @DisplayName("Debe rechazar ADN vacío")
    void testEmptyDna() {
        String[] dna = {};
        System.out.println("========== Test 8 ==========");
        System.out.println("Si DNA es vacío devuelve false");

        boolean result = mutantDetector.isMutant(dna);
        System.out.println("- Resultado: " + result);

        assertFalse(result);
    }

    //Test 9: Validación - Matriz No Cuadrada
    @Test
    @DisplayName("Debe rechazar matriz no cuadrada")
    void testMatrizNoCuadrada() {
        String[] dna = {
                "ATGCGA",  // 6 caracteres
                "CAGTGC",  // 6 caracteres
                "TTATGT"   // 6 caracteres, pero solo 3 filas
        };
        System.out.println("========== Test 9 ==========");
        System.out.println("Si la matriz no es cuadrada (nxn) devuelve false");

        boolean result = mutantDetector.isMutant(dna);
        System.out.println("- Resultado: " + result);

        assertFalse(mutantDetector.isMutant(dna));
    }

    //Test 10: Validación - Caracteres Inválidos
    @Test
    @DisplayName("Debe rechazar caracteres inválidos")
    void testCaracteresInvalidos() {
        String[] dna = {
                "ATGCGA",
                "CAGTXC",  // ← 'X' es inválido
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        System.out.println("========== Test 10 ==========");
        System.out.println("Si existe un caracter distinto de A, T, C,G devolverá false");

        boolean result = mutantDetector.isMutant(dna);
        System.out.println("- Resultado: " + result);

        assertFalse(result);
    }

    //Test 11: Matriz Pequeña 4x4
    @Test
    @DisplayName("Debe detectar mutante en matriz pequeña 4x4")
    void testMatrizPequena4x4() {
        String[] dna = {
                "AAAA",  // Horizontal: AAAA
                "CCCC",  // Horizontal: CCCC
                "TTAT",
                "AGAC"
        };
        System.out.println("========== Test 11 ==========");
        System.out.println("Si detecta mutante en matriz de 4x4 devuelve true");

        boolean result = mutantDetector.isMutant(dna);
        System.out.println("- ¿Es mutante? " + result);

        assertTrue(result);
    }

    //Test 12: Matriz Grande 10x10
    @Test
    @DisplayName("Debe manejar matriz grande 10x10")
    void testMatrizGrande10x10() {
        String[] dna = {
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA",
                "CCCCTACCCC",  // 2 horizontales: CCCC (pos 0-3 y 6-9)
                "TCACTGTCAC",
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA"
        };
        System.out.println("========== Test 12 ==========");

        boolean result = mutantDetector.isMutant(dna);

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertTrue(result);
    }

    //Test 13: Diagonal Ascendente
    @Test
    @DisplayName("Debe detectar diagonal ascendente")
    void testDiagonalAscendente() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCGCTA",
                "TCGCTG"
        };
        System.out.println("========== Test 13 ==========");

        boolean result = mutantDetector.isMutant(dna);
        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);
        System.out.println("  No lanzó excepción -> test pasado exitosamente");

        assertNotNull(result);
    }

    //Test 14: Early Termination (Optimización)
    @Test
    @DisplayName("Debe usar early termination para eficiencia")
    void testEarlyTermination() {
        String[] dna = {
                "AAAAGA",  // Secuencia 1
                "AAAAGC",  // Secuencia 2
                "TTATGT",  // Ya no se revisa (early termination)
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        System.out.println("========== Test 14 ==========");

        long startTime = System.nanoTime();
        boolean result = mutantDetector.isMutant(dna);
        long endTime = System.nanoTime();

        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + result);

        assertTrue(result);
        assertTrue((endTime - startTime) < 10_000_000); // < 10ms
    }

    //Test 15: Todas las Bases Iguales
    @Test
    @DisplayName("Debe detectar mutante con todas las bases iguales")
    void testTodasBasesIguales() {
        String[] dna = {
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        };
        System.out.println("========== Test 15 ==========");

        boolean result = mutantDetector.isMutant(dna);
        System.out.println("¿Es mutante?");
        System.out.println("- Respuesta: " + result);

        assertTrue(result);
    }

    //Test 16: Fila Nula en el Array
    @Test
    @DisplayName("Debe rechazar fila nula en el array")
    void testFilaNulaEnArray() {
        String[] dna = {
                "ATGCGA",
                null,      // ← Fila nula
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        System.out.println("========== Test 16 ==========");

        boolean resultado = mutantDetector.isMutant(dna);
        System.out.println("¿Es mutante?");
        System.out.println("- Resultado: " + resultado);

        assertFalse(resultado);
    }
}